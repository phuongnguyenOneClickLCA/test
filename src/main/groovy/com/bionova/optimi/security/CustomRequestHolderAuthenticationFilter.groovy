package com.bionova.optimi.security

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Configuration
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserLocation
import com.bionova.optimi.core.domain.mongo.UserLogin
import com.bionova.optimi.core.domain.mongo.UserSuspiciousActivity
import com.bionova.optimi.core.service.MongoUserDetailsService
import com.bionova.optimi.exception.MaximumFreeUsersReachedException
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import com.maxmind.geoip2.record.Country
import com.maxmind.geoip2.record.Location
import com.maxmind.geoip2.record.Subdivision
import grails.plugin.springsecurity.web.authentication.GrailsUsernamePasswordAuthenticationFilter
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.web.util.WebUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.session.SessionAuthenticationException

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä
 */
class CustomRequestHolderAuthenticationFilter extends GrailsUsernamePasswordAuthenticationFilter {
    Log log = LogFactory.getLog(CustomRequestHolderAuthenticationFilter.class)
    def userService
    def configurationService

    private static final Integer maxLoginsToPersist = 20

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String channelToken = request.getParameter("channelToken")
        boolean allowSessionCreation = getAllowSessionCreation()
        HttpSession session = request.getSession(false)

        if (session || allowSessionCreation) {
            if (!session) {
                session = request.getSession(true)
            }

            if (channelToken) {
                session?.setAttribute(Constants.SessionAttribute.CHANNEL_TOKEN.toString(), channelToken)
            }
            session?.setAttribute("showFloatingLicense", true)
        }
        Authentication authentication = super.attemptAuthentication(request, response)

        if (authentication?.principal) {
            User user = User.findByUsername(authentication.principal.username)
            Configuration maxConcurrentFreeUsers = configurationService.getByConfigurationName(null, "maxConcurrentFreeUsers")

            if (user && maxConcurrentFreeUsers && maxConcurrentFreeUsers.value && maxConcurrentFreeUsers.value.isNumber() && !user.getIsCommercialUser()) {
                int maxUsers = maxConcurrentFreeUsers.value.toDouble().toInteger()
                List<User> currentActiveFreeUsers = userService.getActiveFreeUsers()

                if (currentActiveFreeUsers.size() >= maxUsers) {
                    throw new MaximumFreeUsersReachedException("Maximum free users reached") // authfail
                }
            }
            setUserLogin(user)
            findOutIfUserIsLoggingInFromDifferentCountryThanPreviously(user)
            findOutIfDistanceBetweenCurrentAndPreviousLogginInImpossibleToTravel(user)
            findOutIfDifferentBrowser(user)

            try {
                user.merge(flush: true)
            } catch (Exception e) {
                log.error("ERROR: UNABLE TO SAVE USERS SUSPICIOUS ACTIVITY: ${e}")
            }
        }
        return authentication
    }

    private void setUserLogin(User user) {
        UserLogin userLogin = new UserLogin()
        userLogin.time = new Date()
        userLogin.ipAddress = getClientIp()
        userLogin.browserAndOs = userService.getBrowserAndOs()
        userLogin.browser = userService.getBrowser()

        try {
            UserLocation userLocation = getLocationForIp(userLogin.ipAddress, user)

            if (userLocation) {
                userLogin.location = userLocation
            }
        } catch (Exception e) {
            log.error("Error in setting user lastLogin location: ${e}")
        }

        if (user.logins) {
            user.logins.add(userLogin)

            if (user.logins.size() > maxLoginsToPersist) {
                List<UserLogin> sortedLogins = user.logins.sort({ -it.time.time })
                user.logins = sortedLogins.take(maxLoginsToPersist) // persist only latest maxLoginsToPersist logins
            }
        } else {
            user.logins = [userLogin]
        }
    }

    private void findOutIfDistanceBetweenCurrentAndPreviousLogginInImpossibleToTravel(User user) {
        if (user.logins && user.logins.size() > 1) {
            List<UserLogin> sortedLogins = user.logins.sort({ -it.time.time })
            UserLogin current = sortedLogins.get(0)
            UserLogin previous = sortedLogins.get(1)

            if (current.location && previous.location && current.location.longitude != null &&
                    current.location.latitude != null && previous.location.longitude != null &&
                    previous.location.latitude != null) {
                int distanceBetweenLogins = calculateDistanceInKilometer(current.location.latitude, current.location.longitude,
                        previous.location.latitude, previous.location.longitude)
                long timeDifferenceBetweenLogins = current.time.time - previous.time.time
                long timeDifferenceBetweenLoginsInHours = timeDifferenceBetweenLogins / 1000 / 60 / 60
                int maxTravelDistanceBetweenLogins = timeDifferenceBetweenLoginsInHours * 150

                if (maxTravelDistanceBetweenLogins < distanceBetweenLogins) {
                    UserSuspiciousActivity userSuspiciousActivity = user.userSuspiciousActivity
                    Map<String, List<String>> physicallyImpossibleTravelSpeed

                    if (userSuspiciousActivity) {
                        physicallyImpossibleTravelSpeed = userSuspiciousActivity.physicallyImpossibleTravelSpeed
                    } else {
                        userSuspiciousActivity = new UserSuspiciousActivity()
                    }

                    if (!physicallyImpossibleTravelSpeed) {
                        physicallyImpossibleTravelSpeed = new LinkedHashMap<String, List<String>>()
                    }
                    physicallyImpossibleTravelSpeed.put(new Date().toString(), [previous.location.countryName + " - " + previous.location.city, current.location.countryName + " - " +  current.location.city])
                    userSuspiciousActivity.physicallyImpossibleTravelSpeed = physicallyImpossibleTravelSpeed
                    user.userSuspiciousActivity = userSuspiciousActivity
                    // Temporal log for SW-1867.
                    log.debug "Setting suspicious activity as '${userSuspiciousActivity}' for user '${user.username}'."
                }
            }
        }
    }

    private int calculateDistanceInKilometer(double latitude1, double longitude1,
                                             double latitude2, double longitude2) {
        double AVERAGE_RADIUS_OF_EARTH_KM = 6371d;
        double latDistance = Math.toRadians(latitude1 - latitude2);
        double lngDistance = Math.toRadians(longitude1 - longitude2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    private void findOutIfDifferentBrowser(User user) {
        if (user.logins && user.logins.size() > 1) {
            List<UserLogin> sortedLogins = user.logins.sort({ -it.time.time })
            UserLogin current = sortedLogins.get(0)
            UserLogin previous = sortedLogins.get(1)

            if (current.browser && previous.browser && !current.browser.equals(previous.browser)) {
                UserSuspiciousActivity userSuspiciousActivity = user.userSuspiciousActivity
                Map<String, List<String>> differentBrowser

                if (userSuspiciousActivity) {
                    differentBrowser = userSuspiciousActivity.differentBrowser
                } else {
                    userSuspiciousActivity = new UserSuspiciousActivity()
                }

                if (!differentBrowser) {
                    differentBrowser = new LinkedHashMap<String, List<String>>()
                }
                differentBrowser.put(new Date().toString(), [previous.browser, current.browser])
                userSuspiciousActivity.differentBrowser = differentBrowser
                user.userSuspiciousActivity = userSuspiciousActivity
            }
        }
    }

    private void findOutIfUserIsLoggingInFromDifferentCountryThanPreviously(User user) {
        if (user.logins && user.logins.size() > 1) {
            List<UserLogin> sortedLogins = user.logins.sort({ -it.time.time })
            UserLogin current = sortedLogins.get(0)
            UserLogin previous = sortedLogins.get(1)

            if (current.location && previous.location && current.location.countryName && previous.location.countryName &&
                    !current.location.countryName.equals(previous.location.countryName)) {
                UserSuspiciousActivity userSuspiciousActivity = user.userSuspiciousActivity
                Map<String, List<String>> loggingInFromAnotherCountry

                if (userSuspiciousActivity) {
                    loggingInFromAnotherCountry = userSuspiciousActivity.loggingInFromAnotherCountry
                } else {
                    userSuspiciousActivity = new UserSuspiciousActivity()
                }

                if (!loggingInFromAnotherCountry) {
                    loggingInFromAnotherCountry = new LinkedHashMap<String, List<String>>()
                }
                loggingInFromAnotherCountry.put(new Date().toString(), [previous.location.countryName, current.location.countryName])
                userSuspiciousActivity.loggingInFromAnotherCountry = loggingInFromAnotherCountry
                user.userSuspiciousActivity = userSuspiciousActivity
            }
        }
    }

    private String getClientIp() {
        HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        String remoteAddr
        String xForwardedFor

        for (String header : request.getHeaderNames().toList()) {
            if ("x-forwarded-for".equalsIgnoreCase(header)) {
                xForwardedFor = request.getHeader(header)
                break
            }
        }

        if (xForwardedFor) {
            if (xForwardedFor.indexOf(",") != -1) {
                remoteAddr = xForwardedFor.substring(xForwardedFor.lastIndexOf(",") + 2);
            } else {
                remoteAddr = xForwardedFor;
            }
        } else {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr
    }

    private UserLocation getLocationForIp(String ipAddress, User user) {
        UserLocation userLocation

        if (ipAddress) {
            try {
                File file = new File(MongoUserDetailsService.class.getClassLoader().getResource("GeoLite2-City.mmdb").getFile())
                DatabaseReader reader = new DatabaseReader.Builder(file).build()
                InetAddress inetAddress = InetAddress.getByName(ipAddress)

                if (inetAddress) {
                    CityResponse response = reader.city(inetAddress)

                    if (response) {
                        userLocation = new UserLocation()
                        Country country = response.getCountry()
                        userLocation.countryName = country?.name
                        userLocation.countryCode = country?.isoCode
                        userLocation.postalCode = response.getPostal()?.code
                        userLocation.city = response.city?.name
                        Subdivision subdivision = response.getMostSpecificSubdivision()
                        userLocation.region = subdivision?.getName()
                        Location location = response.getLocation()
                        userLocation.longitude = location?.longitude
                        userLocation.latitude = location?.latitude
                    }
                }
            } catch (IOException e) {
                log.warn(("Error in getting user ${user?.username} geolocation: ${e}"))
            }
        }
        return userLocation
    }
}
