package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.CostStructure
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.NmdResourceService
import com.bionova.optimi.grails.OptimiMessageSource
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
 * @author Pasi-Markus Mäkelä
 */
class UnitConversionUtil {

    def userService
    ErrorMessageUtil errorMessageUtil
    OptimiMessageSource messageSource

    private static Log log = LogFactory.getLog(UnitConversionUtil.class)

    public Map<Boolean, List<String>> useUserGivenUnit(Resource resource, Dataset dataset = null) {
        Map<Boolean, List<String>> useWithUnits = new HashMap<Boolean, List<String>>()
        List<String> units = []
        String unitSystem = userService.getCurrentUser(Boolean.FALSE)?.unitSystem
        Boolean use = Boolean.FALSE

        if (resource?.unitForData) {
            Map<String, List<String>> matchingUnits = findUnitPair(resource)

            if ("both".equals(unitSystem)) {
                units.add(resource.unitForData)
                List<String> unitsAllowed = resource.combinedUnits

                if (unitsAllowed && !unitsAllowed.isEmpty()) {
                    units.addAll(unitsAllowed)
                }

                if (matchingUnits) {
                    units.addAll(matchingUnits.get(resource.unitForData.trim().toLowerCase()))
                }
                use = Boolean.TRUE
            } else if ("imperial".equals(unitSystem)) {
                if (matchingUnits) {
                    units.addAll(matchingUnits.get(resource.unitForData.trim().toLowerCase()))
                }
                use = Boolean.TRUE
            } else {
                units.add(resource.unitForData)
                List<String> unitsAllowed = resource.combinedUnits

                if (unitsAllowed && !unitsAllowed.isEmpty()) {
                    units.addAll(unitsAllowed)
                    use = Boolean.TRUE
                }
            }

            if (units && dataset?.userGivenUnit && !units.contains(dataset.userGivenUnit) && resource.combinedUnits?.contains(dataset.userGivenUnit)) {
                units.add(0, dataset.userGivenUnit)
            } else if (!dataset?.userGivenUnit) {
                String defaultUnit = resource.defaultUnit

                if (defaultUnit && resource.combinedUnits?.contains(defaultUnit)) {
                    if ("imperial".equals(unitSystem)) {
                        defaultUnit = europeanUnitToImperialUnit(defaultUnit)
                    }
                    units.add(0, defaultUnit)
                } else if (!units) {
                    units.add(resource.unitForData)
                }
            } else if (!units) {
                units.add(resource.unitForData)
            }
            useWithUnits.put(use, units.unique())
        }
        return useWithUnits
    }

    @Deprecated
    public Double convertQuantity(Double quantity, String quantityUnit, String targetUnit) {
        Double convertedValue

        if (quantityUnit && targetUnit && quantity) {
            if (!targetUnit.equalsIgnoreCase(quantityUnit)) {
                try {
                    ConversionRule conversionRule = ConversionRule.getConversionRule(targetUnit, quantityUnit, null)

                    if (conversionRule) {
                        if (conversionRule.requiredMultipliers || conversionRule.requiredDividers) {
                            throw new IllegalArgumentException("Unable to do conversion, this method is mainly to convert from imperial to metric and vice versa, untis that dont require resource values.")
                        }
                        convertedValue = calculateValueByConversionRule(conversionRule, quantity, null, null)
                    } else {
                        convertedValue = new Double(quantity.doubleValue())
                    }
                } catch (Exception e) {
                    convertedValue = 0D
                    log.warn("Non processable quantities (bad number or unit) found. Values set to zero. ${e}")
                }
            } else {
                convertedValue = quantity
            }
        }
        return convertedValue
    }

    /*
    * The whole method should be refactored in future. Because current method do a lot of different stuff
    * and not correspond SOLID principles. More over it can return two different objects:
    * - converted quantity
    * - thickness which multiplied on quantity
    * */

    public Double doConversion(Double quantity, Double thicknessAnswer, String userGivenUnit, Resource resource,
                               Boolean turnUnitsViceVersa = Boolean.FALSE, Boolean useUnitForData = Boolean.FALSE,
                               Boolean returnNullOnFail = Boolean.FALSE, Boolean turnDividersAndMultipliersViceVersa = Boolean.FALSE,
                               Double givenDensity = null, String givenResourceUnit = null, Boolean allowVariableThickness = Boolean.FALSE,
                               CostStructure costStructure = null, Boolean massConversion = null, Double defaultThickness = null,
                               Boolean forceAllowNonDefaultThicknessIfAllowsVariable = null, Boolean isMassCalculation = Boolean.FALSE) {
        Double convertedValue
        Double density = givenDensity ? givenDensity : resource?.density
        String targetUnit
        String resourceUnit = givenResourceUnit ? givenResourceUnit : resource?.unitForData

        Double thickness

        // Allow user given thickness only if converting from SQ FT or M2 to any other unit, else use default thickness,
        // force allow in query form dynamic conversion because thats how its supposed to be
        if ((allowVariableThickness || resource?.allowVariableThickness) && ((["sq ft", "m2"].contains(userGivenUnit) && !massConversion) || costStructure || forceAllowNonDefaultThicknessIfAllowsVariable)) {
            thickness = thicknessAnswer != null ? thicknessAnswer : resource?.defaultThickness_mm ?: defaultThickness
        } else {
            thickness = defaultThickness ?: resource?.defaultThickness_mm
        }

        if (turnUnitsViceVersa) {
            targetUnit = userGivenUnit
            userGivenUnit = resourceUnit
        } else {
            targetUnit = resourceUnit
        }

        def params = ["thickness": thickness, "density": density]

        if (targetUnit && userGivenUnit && quantity) {
            if (!isUnitsEqual(userGivenUnit, targetUnit, isMassCalculation)) {
                try {
                    ConversionRule conversionRule = ConversionRule.getConversionRule(targetUnit, userGivenUnit, resource)

                    if (conversionRule) {
                        if (conversionRule.requiredMultipliers) {
                            if (params) {
                                conversionRule.requiredMultipliers.each { String requiredMultiplier ->
                                    if (!params.get(requiredMultiplier) || params.get(requiredMultiplier) <= 0D) {
                                        throw new IllegalArgumentException("Conversion impossible for rule ${conversionRule.name()}, resourceId: ${resource?.resourceId}, profileId: ${resource?.profileId}. Required multipliers are ${conversionRule.requiredMultipliers}, input is ${params}")
                                    }
                                }
                            } else {
                                throw new IllegalArgumentException("Conversion impossible for rule ${conversionRule.name()}, resourceId: ${resource?.resourceId}, profileId: ${resource?.profileId}. Required multipliers are ${conversionRule.requiredMultipliers}, input is ${params}")
                            }
                        }

                        if (conversionRule.requiredDividers) {
                            if (params) {
                                conversionRule.requiredDividers.each { String requiredDivider ->
                                    if (!params.get(requiredDivider) || params.get(requiredDivider) <= 0D) {
                                        throw new IllegalArgumentException("Conversion impossible for rule ${conversionRule.name()}, resourceId: ${resource?.resourceId}, profileId: ${resource?.profileId}. Required multipliers are ${conversionRule.requiredMultipliers}, input is ${params}")
                                    }
                                }
                            } else {
                                throw new IllegalArgumentException("Conversion impossible for rule ${conversionRule.name()}, resourceId: ${resource?.resourceId}, profileId: ${resource?.profileId}. Required multipliers are ${conversionRule.requiredMultipliers}, input is ${params}")
                            }
                        }
                        convertedValue = calculateValueByConversionRule(conversionRule, quantity, params, params, turnDividersAndMultipliersViceVersa)

                        if (convertedValue && conversionRule.trueTargetUnit) {
                            ConversionRule imperialToImperialRule = ConversionRule.getConversionRule(conversionRule.targetUnit, conversionRule.trueTargetUnit, resource)

                            if (imperialToImperialRule) {
                                convertedValue = calculateValueByConversionRule(imperialToImperialRule, convertedValue, null, null, turnDividersAndMultipliersViceVersa)
                            } else {
                                log.warn("IMPERIAL TO IMPERIAL SPECIAL CONVERSION: No conversionRule found for targetUnit: ${conversionRule.targetUnit} and userGivenUnit: ${conversionRule.trueTargetUnit}, value set to zero.")
                                convertedValue = 0D
                            }
                        }
                    } else {
                        log.warn("No conversionRule found for targetUnit: ${targetUnit} and userGivenUnit: ${userGivenUnit}, value set to zero.")
                        convertedValue = 0D
                    }
                } catch (Exception e) {
                    convertedValue = returnNullOnFail ? null : 0D
                    log.warn("Non processable quantities (bad number or unit) found. Values set to zero. ${e}")
                }
            } else {
                //when we have different unit system, we have to convert quantity for correct calculation, based on targetUnit
                if (!userGivenUnit?.trim()?.equalsIgnoreCase(targetUnit?.trim())) {
                    ConversionRule conversionRule = ConversionRule.getConversionRule(targetUnit, userGivenUnit, resource)
                    quantity = calculateValueByConversionRule(conversionRule, quantity, null, null)
                }

                // Allow only with variableThickness OR force if costStructure present since its given ONLY in LCC calculation!!
                if ("m2".equals(targetUnit) && (resource?.allowVariableThickness || allowVariableThickness || costStructure) && thicknessAnswer && (resource?.defaultThickness_mm || costStructure?.thickness_mm)) {
                    if (costStructure) {
                        // Implementation of cost m2 variable thickness task 11190
                        convertedValue = quantity * (thicknessAnswer / costStructure.thickness_mm)
                    } else {
                        // Implementation of m2 variable thickness task 11243
                        convertedValue = quantity * (thicknessAnswer / resource.defaultThickness_mm)
                    }
                } else {
                    convertedValue = quantity
                }
            }
        }

        log.debug("doConversion parameters. resourceName: ${resource?.nameEN}; quantity: ${quantity}; thicknessAnswer: ${thicknessAnswer}; userGivenUnit: ${userGivenUnit}; thickness ${thickness}")
        return convertedValue != null ? convertedValue : returnNullOnFail ? null : quantity
    }

    private Boolean isUnitsEqual(String sourceUnit, String targetUnit, Boolean isMassCalculation) {
        return sourceUnit.equalsIgnoreCase(targetUnit) || (isMassCalculation && sourceUnit in getUnitAndItsEquivalent(targetUnit))
    }

    public Map<String, List<String>> findUnitPair(Resource resource) {
        Map<String, List<String>> returnable

        if (resource && resource.combinedUnits && resource.unitForData) {
            resource.combinedUnits.each { String allowedUnit ->
                Map pairs = UnitPair.values().find({ it.pairs.containsKey(allowedUnit) })?.pairs

                if (pairs) {
                    if (returnable) {
                        List<String> existing = new ArrayList<String>(returnable.get(resource.unitForData.trim().toLowerCase()))

                        pairs.get(allowedUnit).each { String unit ->
                            if (!existing.contains(unit)) {
                                existing.add(unit)
                            }
                        }
                        returnable.put(resource.unitForData.trim().toLowerCase(), existing)
                    } else {
                        returnable = new LinkedHashMap<String, List<String>>()
                        returnable.put(resource.unitForData.trim().toLowerCase(), pairs.get(allowedUnit))
                    }
                }
            }
        }
        return returnable
    }

    public static final enum UnitSystem {
        METRIC("Metric", "metric"),
        IMPERIAL("Imperial", "imperial"),
        METRIC_AND_IMPERIAL("Metric and imperial", "both")

        String name
        String value

        private UnitSystem(String name, String value) {
            this.name = name
            this.value = value
        }
    }

    public UnitPair[] allUnitPairs() {
        return UnitPair.values()
    }

    public static final enum UnitPair {
        KG_LBS(["kg": ["lbs"]]),
        M2_SQFT(["m2": ["sq ft"]]),
        M3_CUFT_CUYD(["m3": ["cu ft", "cu yd"]]),
        L_CUYD_GAL(["l": ["cu yd", "gal"]]),
        KM_MI(["km": ["mi"]]),
        M_FT(["m": ["ft"]]),
        KWH_KBTU(["kWh": ["kBtu", "kWh"]]),
        UNIT(["unit": ["unit"]])


        Map<String, List<String>> pairs

        private UnitPair(Map<String, List<String>> pairs) {
            this.pairs = pairs
        }
    }

    public static final enum ConversionRule {
        CUFT_TO_M3("m3", "cu ft", 0.0283168466, null, null, null),
        CUFT_TO_M2("m2", "cu ft", 28.3168466, null, ["thickness"], null),
        CUFT_TO_KG("kg", "cu ft", 0.0283168466, ["density"], null, null),
        CUFT_TO_TON("ton", "cu ft", 0.000028317, ["density"], null, null),
        CUFT_TO_CUYD("cu yd", "cu ft", 0.037037037, null, null, null),
        CUFT_TO_SQFT("sq ft", "cu ft", 28.3168466, null, ["thickness"], "m2"),
        CUFT_TO_LBS("lbs", "cu ft", 0.0283168466, ["density"], null, "kg"),

        SQFT_TO_M3("m3", "sq ft", 0.000092903, ["thickness"], null, null),
        SQFT_TO_M2("m2", "sq ft", 0.09290304, null, null, null),
        SQFT_TO_KG("kg", "sq ft", 0.000092903, ["thickness", "density"], null, null),
        SQFT_TO_TON("ton", "sq ft", 0.000000093, ["thickness", "density"], null, null),
        SQFT_TO_CUYD("cu yd", "sq ft", 0.000092903, ["thickness"], null, "m3"),
        SQFT_TO_CUFT("cu ft", "sq ft", 0.000092903, ["thickness"], null, "m3"),
        SQFT_TO_LBS("lbs", "sq ft", 0.000092903, ["thickness", "density"], null, "kg"),

        CUYD_TO_M3("m3", "cu yd", 0.764554858, null, null, null),
        CUYD_TO_L("l", "cu yd", 764.554858, null, null, null),
        CUYD_TO_M2("m2", "cu yd", 764.554858, null, ["thickness"], null),
        CUYD_TO_KG("kg", "cu yd", 0.764554858, ["density"], null, null),
        CUYD_TO_TON("ton", "cu yd", 0.000764555, ["density"], null, null),
        CUYD_TO_SQFT("sq ft", "cu yd", 764.554858, null, ["thickness"], "m2"),
        CUYD_TO_CUFT("cu ft", "cu yd", 27, null, null, null),
        CUYD_TO_LBS("lbs", "cu yd", 0.764554858, ["density"], null, "kg"),

        GAL_TO_L("l", "gal", 3.78541178, null, null, null),

        LBS_TO_KG("kg", "lbs", 0.4535923704, null, null, null),
        LBS_TO_TON("ton", "lbs", 0.000453592, null, null, null),
        LBS_TO_M2("m2", "lbs", 453.5923704, null, ["thickness", "density"], null),
        LBS_TO_M3("m3", "lbs", 0.4535923704, null, ["density"], null),
        LBS_TO_SQFT("sq ft", "lbs", 453.5923704, null, ["thickness", "density"], "m2"),
        LBS_TO_CUFT("cu ft", "lbs", 0.4535923704, null, ["density"], "m3"),
        LBS_TO_CUYD("cu yd", "lbs", 0.4535923704, null, ["density"], "m3"),

        MI_TO_KM("km", "mi", 1.609344, null, null, null),

        L_TO_M3("m3", "l", 0.001, null, null, null),
        L_TO_GAL("gal", "l", 0.264172, null, null, null),

        M_FT("m", "ft", 0.3048, null, null, null),
        FT_M("ft", "m", 3.2808399, null, null, null),

        KWH_TO_MWH("mwh", "kwh", 0.001, null, null, null),
        KWH_TO_MJ("mj", "kwh", 3.6, null, null, null),
        KWH_TO_KBTU("kbtu", "kwh", 3.41214163313, null, null, null),

        KBTU_TO_KWH("kwh", "kbtu", 0.2930710702, null, null, null),

        MWH_TO_KWH("kwh", "mwh", 1000, null, null, null),
        MWH_TO_MJ("mj", "mwh", 3600, null, null, null),

        MJ_TO_KWH("kwh", "mj", 0.277778, null, null, null),
        MJ_TO_MWH("mwh", "mj", 0.000277778, null, null, null),

        M3_TO_L("l", "m3", 1000, null, null, null),
        M3_TO_M2("m2", "m3", 1000, null, ["thickness"], null),
        M3_TO_KG("kg", "m3", null, ["density"], null, null),
        M3_TO_CUYD("cu yd", "m3", 1.307950619, null, null, null),
        M3_TO_CUFT("cu ft", "m3", 35.314666712, null, null, null),
        M3_TO_SQFT("sq ft", "m3", 1000, null, ["thickness"], "m2"),
        M3_TO_LBS("lbs", "m3", null, ["density"], null, "kg"),
        M3_TO_TON("ton", "m3", 0.001, ["density"], null, null),

        M2_TO_KG("kg", "m2", 0.001, ["thickness", "density"], null, null),
        M2_TO_M3("m3", "m2", 0.001, ["thickness"], null, null),
        M2_TO_SQFT("sq ft", "m2", 10.7639104, null, null, null),
        M2_TO_CUYD("cu yd", "m2", 0.001, ["thickness"], null, "m3"),
        M2_TO_CUFT("cu ft", "m2", 0.001, ["thickness"], null, "m3"),
        M2_TO_LBS("lbs", "m2", 0.001, ["thickness", "density"], null, "kg"),
        M2_TO_TON("ton", "m2", 0.000001, ["thickness", "density"], null, null),

        KG_TO_M3("m3", "kg", null, null, ["density"], null),
        KG_TO_M2("m2", "kg", 1000, null, ["thickness", "density"], null),
        KG_TO_SQFT("sq ft", "kg", 1000, null, ["thickness", "density"], "m2"),
        KG_TO_CUYD("cu yd", "kg", null, null, ["density"], "m3"),
        KG_TO_CUFT("cu ft", "kg", null, null, ["density"], "m3"),
        KG_TO_LBS("lbs", "kg", 2.20462262, null, null, null),
        KG_TO_TON("ton", "kg", 0.001, null, null, null),

        TON_TO_KG("kg", "ton", 1000, null, null, null),
        TON_TO_M2("m2", "ton", 1000000, null, ["thickness", "density"], null),
        TON_TO_M3("m3", "ton", 1000, null, ["density"], null),
        TON_TO_SQFT("sq ft", "ton", 1000000, null, ["thickness", "density"], "m2"),
        TON_TO_CUYD("cu yd", "ton", 1000, null, ["density"], "m3"),
        TON_TO_CUFT("cu ft", "ton", 1000, null, ["density"], "m3"),
        TON_TO_LBS("lbs", "ton", 2204.62262, null, null, null)

        String targetUnit
        String userUnit
        Double fixedMultiplier
        List<String> requiredMultipliers
        List<String> requiredDividers
        // for imperial to imperial conversion in some cases, first to metric equivalent then back to imperial due to multipliers and dividers being in metric
        String trueTargetUnit

        private ConversionRule(String targetUnit, String userUnit, Double fixedMultiplier,
                               List<String> requiredMultipliers, List<String> requiredDividers,
                               String trueTargetUnit) {
            this.targetUnit = targetUnit
            this.userUnit = userUnit
            this.fixedMultiplier = fixedMultiplier
            this.requiredMultipliers = requiredMultipliers
            this.requiredDividers = requiredDividers
            this.trueTargetUnit = trueTargetUnit
        }

        public static ConversionRule getConversionRule(String targetUnit, String userUnit, Resource resource) {
            ConversionRule conversionRule

            if (targetUnit && userUnit) {
                if (!userUnit.trim().toLowerCase().equalsIgnoreCase(targetUnit.trim().toLowerCase())) {
                    conversionRule = ConversionRule.values()?.find({
                        it.targetUnit.equalsIgnoreCase(targetUnit.trim().toLowerCase()) && it.userUnit.equals(userUnit.trim().toLowerCase())
                    })

                    if (!conversionRule) {
                        throw new IllegalArgumentException("Couldn't find conversionRule with targetUnit: ${targetUnit} and userGivenUnit: ${userUnit} for resourceId: ${resource?.resourceId}, profileId: ${resource?.profileId}")
                    }
                }
            }
            return conversionRule
        }
    }

    public Double getConstructionMultiplierForUnitPair(Construction construction) {
        Double multiplier

        if (construction.unit) {
            String imperialPair = construction.imperialUnit ?: europeanUnitToImperialUnit(construction.unit)

            try {
                ConversionRule conversionRule = ConversionRule.getConversionRule(imperialPair, construction.unit, null)

                if (conversionRule) {
                    multiplier = conversionRule.fixedMultiplier
                }
            } catch (Exception e) {

            }
        }
        return multiplier
    }

    public String transformImperialUnitToEuropeanUnit(String imperialUnit) {
        String targetUnit

        if (imperialUnit) {
            imperialUnit = imperialUnit.trim().toLowerCase()

            if ("cu yd".equals(imperialUnit) || "cu ft".equals(imperialUnit)) {
                targetUnit = "m3"
            } else if ("sq ft".equals(imperialUnit)) {
                targetUnit = "m2"
            } else if ("lbs".equals(imperialUnit)) {
                targetUnit = "kg"
            } else if ("kBtu".equalsIgnoreCase(imperialUnit)) {
                targetUnit = "kWh"
            } else if ("ft".equalsIgnoreCase(imperialUnit)) {
                targetUnit = "m"
            } else {
                targetUnit = imperialUnit
            }
        }
        return targetUnit
    }

    public Boolean isImperialUnit(String unit) {
        Boolean imperialUnit = Boolean.FALSE

        if (unit && ["cu yd", "cu ft", "sq ft", "lbs", "kbtu", "ft"].contains(unit.trim().toLowerCase())) {
            imperialUnit = Boolean.TRUE
        }
        return imperialUnit
    }

    public String europeanUnitToImperialUnit(String imperialUnit) {
        String targetUnit

        if (imperialUnit) {
            imperialUnit = imperialUnit.trim().toLowerCase()

            if ("m3".equals(imperialUnit)) {
                targetUnit = "cu ft"
            } else if ("m2".equals(imperialUnit)) {
                targetUnit = "sq ft"
            } else if ("kg".equals(imperialUnit)) {
                targetUnit = "lbs"
            } else if ("m".equals(imperialUnit)) {
                targetUnit = "ft"
            } else if ("kWh".equalsIgnoreCase(imperialUnit)) {
                targetUnit = "kBtu"
            } else {
                targetUnit = imperialUnit
            }
        }
        return targetUnit
    }

    public List<String> getUnitAndItsEquivalent(String unit) {
        List<String> targetUnit

        if (unit) {
            unit = unit.trim().toLowerCase()

            if ("m3".equals(unit)) {
                targetUnit = ["cu yd", "cu ft"]
            } else if ("m2".equals(unit)) {
                targetUnit = ["sq ft"]
            } else if ("kg".equals(unit)) {
                targetUnit = ["lbs"]
            } else if ("m".equals(unit)) {
                targetUnit = ["ft"]
            } else if ("cu yd".equals(unit) || "cu ft".equals(unit)) {
                targetUnit = ["m3"]
            } else if ("sq ft".equals(unit)) {
                targetUnit = ["m2"]
            } else if ("lbs".equals(unit)) {
                targetUnit = ["kg"]
            } else if ("ft".equals(unit)) {
                targetUnit = ["m"]
            }

            if (targetUnit) {
                targetUnit.add(unit)
            } else {
                targetUnit = [unit]
            }
        }
        return targetUnit
    }

    public List<String> getUnitsAndTheirEquivalents(List<String> units) {
        List<String> targetUnit = []

        if (units) {
            units.each {
                String unit = it.toLowerCase().trim()

                if ("m3".equals(unit)) {
                    targetUnit.addAll(["cu yd", "cu ft"])
                } else if ("m2".equals(unit)) {
                    targetUnit.add("sq ft")
                } else if ("kg".equals(unit)) {
                    targetUnit.add("lbs")
                } else if ("m".equals(unit)) {
                    targetUnit.add("ft")
                } else if ("cu yd".equals(unit) || "cu ft".equals(unit)) {
                    targetUnit.add("m3")
                } else if ("sq ft".equals(unit)) {
                    targetUnit.add("m2")
                } else if ("lbs".equals(unit)) {
                    targetUnit.add("kg")
                } else if ("ft".equals(unit)) {
                    targetUnit.add("m")
                }
                targetUnit.add(unit)
            }
        }
        return targetUnit.unique()
    }

    public List<String> getCompatibleUnitsForResource(Resource resource) {
        List<String> compatibleUnits = []

        if (resource) {
            Map<String, List<String>> matchingUnits = findUnitPair(resource)

            if (resource.combinedUnits) {
                compatibleUnits.addAll(resource.combinedUnits)
            }
            if (matchingUnits) {
                compatibleUnits.addAll(matchingUnits.get(resource.unitForData.trim().toLowerCase()))
            }
        }
        return compatibleUnits
    }

    public double calculateValueByConversionRule(ConversionRule conversionRule, Double quantity, Map<String, Double> multipliers, Map<String, Double> dividers, Boolean turnDividersAndMultipliersViceVersa = Boolean.FALSE) {
        Double converted

        if (conversionRule && quantity) {
            converted = new Double(quantity.doubleValue())

            if (turnDividersAndMultipliersViceVersa) {
                if (conversionRule.fixedMultiplier) {
                    converted = converted / conversionRule.fixedMultiplier
                }

                if (conversionRule.requiredDividers && dividers) {
                    conversionRule.requiredDividers.each {
                        Double divider = dividers.get(it)

                        if (divider) {
                            converted = converted * divider
                        }
                    }
                }

                if (conversionRule.requiredMultipliers && multipliers) {
                    conversionRule.requiredMultipliers.each {
                        Double multiplier = multipliers.get(it)

                        if (multiplier) {
                            converted = converted / multiplier
                        }
                    }
                }
            } else {
                if (conversionRule.fixedMultiplier) {
                    converted = converted * conversionRule.fixedMultiplier
                }

                if (conversionRule.requiredMultipliers && multipliers) {
                    conversionRule.requiredMultipliers.each {
                        Double multiplier = multipliers.get(it)

                        if (multiplier) {
                            converted = converted * multiplier
                        }
                    }
                }

                if (conversionRule.requiredDividers && dividers) {
                    conversionRule.requiredDividers.each {
                        Double divider = dividers.get(it)

                        if (divider) {
                            converted = converted / divider
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Cannot do conversion, because quantity is missing.")
        }
        return converted
    }

    /**
     *
     * @param quantity
     * @param factor unit conversion factor (is not 0 or 99999) this is handled in {@link NmdResourceService#extractUnitForNewNmdConstruction}
     * @param mainUnit resource.unit (the main unit of the resource)
     * @param alternativeUnit nmdAlternativeUnit
     * @param currentUnit
     * @return
     */
    static Double doNmdConversion(Double quantity, Double factor, String mainUnit, String alternativeUnit, String currentUnit) {
        if (quantity == null || !currentUnit || !factor) {
            return null
        }

        if (currentUnit == mainUnit) {
            return quantity / factor
        } else if (currentUnit == alternativeUnit) {
            return quantity * factor
        }

        return quantity
    }

    static Double doMassConversion(String currentUnit, String targetUnit, Resource resource, Double quantity) {
        if (currentUnit == resource.unitForData && targetUnit == "kg") {
            return quantity * resource.massConversionFactor
        } else if (currentUnit == "kg" && targetUnit == resource.unitForData) {
            return quantity / resource.massConversionFactor
        } else if (currentUnit == resource.unitForData && targetUnit == "ton") {
            return quantity * resource.massConversionFactor / 1000
        } else if (currentUnit == "ton" && targetUnit == resource.unitForData) {
            return quantity / resource.massConversionFactor * 1000
        }
        return null
    }

    /**
     * Convert the quantity from unit changes
     * @param resource
     * @param quantity
     * @param thickness_mm
     * @param currentUnit
     * @param targetUnit
     * @return
     */
    Double convertQuantity(Resource resource, Double quantity, Double thickness_mm, String currentUnit, String targetUnit) {
        Double convertedQuantity = null
        if (resource?.nmdUnitConversionFactor) {
            // nmd construction unit conversion
            convertedQuantity = doNmdConversion(quantity, resource.nmdUnitConversionFactor, resource.unitForData, resource.nmdAlternativeUnit, currentUnit)
        } else {
            convertedQuantity = doConversion(quantity, thickness_mm, currentUnit, resource, null, null, null, null, resource.density, targetUnit, null, null, null, null, true)
        }

        if (!convertedQuantity && resource.massConversionFactor) {
            convertedQuantity = doMassConversion(currentUnit, targetUnit, resource, quantity)
        }

        return convertedQuantity
    }
}
