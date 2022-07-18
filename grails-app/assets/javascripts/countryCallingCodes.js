function loadCountryCallingCodes(select) {
    for (let country of COUNTRY_CALLING_CODES) {
        let countryCode = country['alpha2Code']
        let callingCodes = country['callingCodes']
        if (countryCode && callingCodes.length > 0) {
            for (let callCode of callingCodes) {
                if (callCode) {
                    $(select).append(new Option(countryCode + ' +' + callCode, callCode))
                }
            }
        }
    }
    $(select).select2()
}

function updatePhoneNumber() {
    const countryCode = $('#countryCallingCodes').val()
    const phoneNumber = $('#phoneNumber').val()
    const hiddenPhone = $('#hiddenPhone')

    if (phoneNumber) {
        $(hiddenPhone).val('+' + countryCode + phoneNumber)
    }
}

const COUNTRY_CALLING_CODES = [
    {
        "name": "Afghanistan",
        "alpha2Code": "AF",
        "callingCodes": [
            "93"
        ]
    },
    {
        "name": "Åland Islands",
        "alpha2Code": "AX",
        "callingCodes": [
            "358"
        ]
    },
    {
        "name": "Albania",
        "alpha2Code": "AL",
        "callingCodes": [
            "355"
        ]
    },
    {
        "name": "Algeria",
        "alpha2Code": "DZ",
        "callingCodes": [
            "213"
        ]
    },
    {
        "name": "American Samoa",
        "alpha2Code": "AS",
        "callingCodes": [
            "1684"
        ]
    },
    {
        "name": "Andorra",
        "alpha2Code": "AD",
        "callingCodes": [
            "376"
        ]
    },
    {
        "name": "Angola",
        "alpha2Code": "AO",
        "callingCodes": [
            "244"
        ]
    },
    {
        "name": "Anguilla",
        "alpha2Code": "AI",
        "callingCodes": [
            "1264"
        ]
    },
    {
        "name": "Antarctica",
        "alpha2Code": "AQ",
        "callingCodes": [
            "672"
        ]
    },
    {
        "name": "Antigua and Barbuda",
        "alpha2Code": "AG",
        "callingCodes": [
            "1268"
        ]
    },
    {
        "name": "Argentina",
        "alpha2Code": "AR",
        "callingCodes": [
            "54"
        ]
    },
    {
        "name": "Armenia",
        "alpha2Code": "AM",
        "callingCodes": [
            "374"
        ]
    },
    {
        "name": "Aruba",
        "alpha2Code": "AW",
        "callingCodes": [
            "297"
        ]
    },
    {
        "name": "Australia",
        "alpha2Code": "AU",
        "callingCodes": [
            "61"
        ]
    },
    {
        "name": "Austria",
        "alpha2Code": "AT",
        "callingCodes": [
            "43"
        ]
    },
    {
        "name": "Azerbaijan",
        "alpha2Code": "AZ",
        "callingCodes": [
            "994"
        ]
    },
    {
        "name": "Bahamas",
        "alpha2Code": "BS",
        "callingCodes": [
            "1242"
        ]
    },
    {
        "name": "Bahrain",
        "alpha2Code": "BH",
        "callingCodes": [
            "973"
        ]
    },
    {
        "name": "Bangladesh",
        "alpha2Code": "BD",
        "callingCodes": [
            "880"
        ]
    },
    {
        "name": "Barbados",
        "alpha2Code": "BB",
        "callingCodes": [
            "1246"
        ]
    },
    {
        "name": "Belarus",
        "alpha2Code": "BY",
        "callingCodes": [
            "375"
        ]
    },
    {
        "name": "Belgium",
        "alpha2Code": "BE",
        "callingCodes": [
            "32"
        ]
    },
    {
        "name": "Belize",
        "alpha2Code": "BZ",
        "callingCodes": [
            "501"
        ]
    },
    {
        "name": "Benin",
        "alpha2Code": "BJ",
        "callingCodes": [
            "229"
        ]
    },
    {
        "name": "Bermuda",
        "alpha2Code": "BM",
        "callingCodes": [
            "1441"
        ]
    },
    {
        "name": "Bhutan",
        "alpha2Code": "BT",
        "callingCodes": [
            "975"
        ]
    },
    {
        "name": "Bolivia (Plurinational State of)",
        "alpha2Code": "BO",
        "callingCodes": [
            "591"
        ]
    },
    {
        "name": "Bonaire, Sint Eustatius and Saba",
        "alpha2Code": "BQ",
        "callingCodes": [
            "5997"
        ]
    },
    {
        "name": "Bosnia and Herzegovina",
        "alpha2Code": "BA",
        "callingCodes": [
            "387"
        ]
    },
    {
        "name": "Botswana",
        "alpha2Code": "BW",
        "callingCodes": [
            "267"
        ]
    },
    {
        "name": "Bouvet Island",
        "alpha2Code": "BV",
        "callingCodes": [
            ""
        ]
    },
    {
        "name": "Brazil",
        "alpha2Code": "BR",
        "callingCodes": [
            "55"
        ]
    },
    {
        "name": "British Indian Ocean Territory",
        "alpha2Code": "IO",
        "callingCodes": [
            "246"
        ]
    },
    {
        "name": "United States Minor Outlying Islands",
        "alpha2Code": "UM",
        "callingCodes": [
            ""
        ]
    },
    {
        "name": "Virgin Islands (British)",
        "alpha2Code": "VG",
        "callingCodes": [
            "1284"
        ]
    },
    {
        "name": "Virgin Islands (U.S.)",
        "alpha2Code": "VI",
        "callingCodes": [
            "1 340"
        ]
    },
    {
        "name": "Brunei Darussalam",
        "alpha2Code": "BN",
        "callingCodes": [
            "673"
        ]
    },
    {
        "name": "Bulgaria",
        "alpha2Code": "BG",
        "callingCodes": [
            "359"
        ]
    },
    {
        "name": "Burkina Faso",
        "alpha2Code": "BF",
        "callingCodes": [
            "226"
        ]
    },
    {
        "name": "Burundi",
        "alpha2Code": "BI",
        "callingCodes": [
            "257"
        ]
    },
    {
        "name": "Cambodia",
        "alpha2Code": "KH",
        "callingCodes": [
            "855"
        ]
    },
    {
        "name": "Cameroon",
        "alpha2Code": "CM",
        "callingCodes": [
            "237"
        ]
    },
    {
        "name": "Canada",
        "alpha2Code": "CA",
        "callingCodes": [
            "1"
        ]
    },
    {
        "name": "Cabo Verde",
        "alpha2Code": "CV",
        "callingCodes": [
            "238"
        ]
    },
    {
        "name": "Cayman Islands",
        "alpha2Code": "KY",
        "callingCodes": [
            "1345"
        ]
    },
    {
        "name": "Central African Republic",
        "alpha2Code": "CF",
        "callingCodes": [
            "236"
        ]
    },
    {
        "name": "Chad",
        "alpha2Code": "TD",
        "callingCodes": [
            "235"
        ]
    },
    {
        "name": "Chile",
        "alpha2Code": "CL",
        "callingCodes": [
            "56"
        ]
    },
    {
        "name": "China",
        "alpha2Code": "CN",
        "callingCodes": [
            "86"
        ]
    },
    {
        "name": "Christmas Island",
        "alpha2Code": "CX",
        "callingCodes": [
            "61"
        ]
    },
    {
        "name": "Cocos (Keeling) Islands",
        "alpha2Code": "CC",
        "callingCodes": [
            "61"
        ]
    },
    {
        "name": "Colombia",
        "alpha2Code": "CO",
        "callingCodes": [
            "57"
        ]
    },
    {
        "name": "Comoros",
        "alpha2Code": "KM",
        "callingCodes": [
            "269"
        ]
    },
    {
        "name": "Congo",
        "alpha2Code": "CG",
        "callingCodes": [
            "242"
        ]
    },
    {
        "name": "Congo (Democratic Republic of the)",
        "alpha2Code": "CD",
        "callingCodes": [
            "243"
        ]
    },
    {
        "name": "Cook Islands",
        "alpha2Code": "CK",
        "callingCodes": [
            "682"
        ]
    },
    {
        "name": "Costa Rica",
        "alpha2Code": "CR",
        "callingCodes": [
            "506"
        ]
    },
    {
        "name": "Croatia",
        "alpha2Code": "HR",
        "callingCodes": [
            "385"
        ]
    },
    {
        "name": "Cuba",
        "alpha2Code": "CU",
        "callingCodes": [
            "53"
        ]
    },
    {
        "name": "Curaçao",
        "alpha2Code": "CW",
        "callingCodes": [
            "599"
        ]
    },
    {
        "name": "Cyprus",
        "alpha2Code": "CY",
        "callingCodes": [
            "357"
        ]
    },
    {
        "name": "Czech Republic",
        "alpha2Code": "CZ",
        "callingCodes": [
            "420"
        ]
    },
    {
        "name": "Denmark",
        "alpha2Code": "DK",
        "callingCodes": [
            "45"
        ]
    },
    {
        "name": "Djibouti",
        "alpha2Code": "DJ",
        "callingCodes": [
            "253"
        ]
    },
    {
        "name": "Dominica",
        "alpha2Code": "DM",
        "callingCodes": [
            "1767"
        ]
    },
    {
        "name": "Dominican Republic",
        "alpha2Code": "DO",
        "callingCodes": [
            "1809",
            "1829",
            "1849"
        ]
    },
    {
        "name": "Ecuador",
        "alpha2Code": "EC",
        "callingCodes": [
            "593"
        ]
    },
    {
        "name": "Egypt",
        "alpha2Code": "EG",
        "callingCodes": [
            "20"
        ]
    },
    {
        "name": "El Salvador",
        "alpha2Code": "SV",
        "callingCodes": [
            "503"
        ]
    },
    {
        "name": "Equatorial Guinea",
        "alpha2Code": "GQ",
        "callingCodes": [
            "240"
        ]
    },
    {
        "name": "Eritrea",
        "alpha2Code": "ER",
        "callingCodes": [
            "291"
        ]
    },
    {
        "name": "Estonia",
        "alpha2Code": "EE",
        "callingCodes": [
            "372"
        ]
    },
    {
        "name": "Ethiopia",
        "alpha2Code": "ET",
        "callingCodes": [
            "251"
        ]
    },
    {
        "name": "Falkland Islands (Malvinas)",
        "alpha2Code": "FK",
        "callingCodes": [
            "500"
        ]
    },
    {
        "name": "Faroe Islands",
        "alpha2Code": "FO",
        "callingCodes": [
            "298"
        ]
    },
    {
        "name": "Fiji",
        "alpha2Code": "FJ",
        "callingCodes": [
            "679"
        ]
    },
    {
        "name": "Finland",
        "alpha2Code": "FI",
        "callingCodes": [
            "358"
        ]
    },
    {
        "name": "France",
        "alpha2Code": "FR",
        "callingCodes": [
            "33"
        ]
    },
    {
        "name": "French Guiana",
        "alpha2Code": "GF",
        "callingCodes": [
            "594"
        ]
    },
    {
        "name": "French Polynesia",
        "alpha2Code": "PF",
        "callingCodes": [
            "689"
        ]
    },
    {
        "name": "French Southern Territories",
        "alpha2Code": "TF",
        "callingCodes": [
            ""
        ]
    },
    {
        "name": "Gabon",
        "alpha2Code": "GA",
        "callingCodes": [
            "241"
        ]
    },
    {
        "name": "Gambia",
        "alpha2Code": "GM",
        "callingCodes": [
            "220"
        ]
    },
    {
        "name": "Georgia",
        "alpha2Code": "GE",
        "callingCodes": [
            "995"
        ]
    },
    {
        "name": "Germany",
        "alpha2Code": "DE",
        "callingCodes": [
            "49"
        ]
    },
    {
        "name": "Ghana",
        "alpha2Code": "GH",
        "callingCodes": [
            "233"
        ]
    },
    {
        "name": "Gibraltar",
        "alpha2Code": "GI",
        "callingCodes": [
            "350"
        ]
    },
    {
        "name": "Greece",
        "alpha2Code": "GR",
        "callingCodes": [
            "30"
        ]
    },
    {
        "name": "Greenland",
        "alpha2Code": "GL",
        "callingCodes": [
            "299"
        ]
    },
    {
        "name": "Grenada",
        "alpha2Code": "GD",
        "callingCodes": [
            "1473"
        ]
    },
    {
        "name": "Guadeloupe",
        "alpha2Code": "GP",
        "callingCodes": [
            "590"
        ]
    },
    {
        "name": "Guam",
        "alpha2Code": "GU",
        "callingCodes": [
            "1671"
        ]
    },
    {
        "name": "Guatemala",
        "alpha2Code": "GT",
        "callingCodes": [
            "502"
        ]
    },
    {
        "name": "Guernsey",
        "alpha2Code": "GG",
        "callingCodes": [
            "44"
        ]
    },
    {
        "name": "Guinea",
        "alpha2Code": "GN",
        "callingCodes": [
            "224"
        ]
    },
    {
        "name": "Guinea-Bissau",
        "alpha2Code": "GW",
        "callingCodes": [
            "245"
        ]
    },
    {
        "name": "Guyana",
        "alpha2Code": "GY",
        "callingCodes": [
            "592"
        ]
    },
    {
        "name": "Haiti",
        "alpha2Code": "HT",
        "callingCodes": [
            "509"
        ]
    },
    {
        "name": "Heard Island and McDonald Islands",
        "alpha2Code": "HM",
        "callingCodes": [
            ""
        ]
    },
    {
        "name": "Holy See",
        "alpha2Code": "VA",
        "callingCodes": [
            "379"
        ]
    },
    {
        "name": "Honduras",
        "alpha2Code": "HN",
        "callingCodes": [
            "504"
        ]
    },
    {
        "name": "Hong Kong",
        "alpha2Code": "HK",
        "callingCodes": [
            "852"
        ]
    },
    {
        "name": "Hungary",
        "alpha2Code": "HU",
        "callingCodes": [
            "36"
        ]
    },
    {
        "name": "Iceland",
        "alpha2Code": "IS",
        "callingCodes": [
            "354"
        ]
    },
    {
        "name": "India",
        "alpha2Code": "IN",
        "callingCodes": [
            "91"
        ]
    },
    {
        "name": "Indonesia",
        "alpha2Code": "ID",
        "callingCodes": [
            "62"
        ]
    },
    {
        "name": "Côte d'Ivoire",
        "alpha2Code": "CI",
        "callingCodes": [
            "225"
        ]
    },
    {
        "name": "Iran (Islamic Republic of)",
        "alpha2Code": "IR",
        "callingCodes": [
            "98"
        ]
    },
    {
        "name": "Iraq",
        "alpha2Code": "IQ",
        "callingCodes": [
            "964"
        ]
    },
    {
        "name": "Ireland",
        "alpha2Code": "IE",
        "callingCodes": [
            "353"
        ]
    },
    {
        "name": "Isle of Man",
        "alpha2Code": "IM",
        "callingCodes": [
            "44"
        ]
    },
    {
        "name": "Israel",
        "alpha2Code": "IL",
        "callingCodes": [
            "972"
        ]
    },
    {
        "name": "Italy",
        "alpha2Code": "IT",
        "callingCodes": [
            "39"
        ]
    },
    {
        "name": "Jamaica",
        "alpha2Code": "JM",
        "callingCodes": [
            "1876"
        ]
    },
    {
        "name": "Japan",
        "alpha2Code": "JP",
        "callingCodes": [
            "81"
        ]
    },
    {
        "name": "Jersey",
        "alpha2Code": "JE",
        "callingCodes": [
            "44"
        ]
    },
    {
        "name": "Jordan",
        "alpha2Code": "JO",
        "callingCodes": [
            "962"
        ]
    },
    {
        "name": "Kazakhstan",
        "alpha2Code": "KZ",
        "callingCodes": [
            "76",
            "77"
        ]
    },
    {
        "name": "Kenya",
        "alpha2Code": "KE",
        "callingCodes": [
            "254"
        ]
    },
    {
        "name": "Kiribati",
        "alpha2Code": "KI",
        "callingCodes": [
            "686"
        ]
    },
    {
        "name": "Kuwait",
        "alpha2Code": "KW",
        "callingCodes": [
            "965"
        ]
    },
    {
        "name": "Kyrgyzstan",
        "alpha2Code": "KG",
        "callingCodes": [
            "996"
        ]
    },
    {
        "name": "Lao People's Democratic Republic",
        "alpha2Code": "LA",
        "callingCodes": [
            "856"
        ]
    },
    {
        "name": "Latvia",
        "alpha2Code": "LV",
        "callingCodes": [
            "371"
        ]
    },
    {
        "name": "Lebanon",
        "alpha2Code": "LB",
        "callingCodes": [
            "961"
        ]
    },
    {
        "name": "Lesotho",
        "alpha2Code": "LS",
        "callingCodes": [
            "266"
        ]
    },
    {
        "name": "Liberia",
        "alpha2Code": "LR",
        "callingCodes": [
            "231"
        ]
    },
    {
        "name": "Libya",
        "alpha2Code": "LY",
        "callingCodes": [
            "218"
        ]
    },
    {
        "name": "Liechtenstein",
        "alpha2Code": "LI",
        "callingCodes": [
            "423"
        ]
    },
    {
        "name": "Lithuania",
        "alpha2Code": "LT",
        "callingCodes": [
            "370"
        ]
    },
    {
        "name": "Luxembourg",
        "alpha2Code": "LU",
        "callingCodes": [
            "352"
        ]
    },
    {
        "name": "Macao",
        "alpha2Code": "MO",
        "callingCodes": [
            "853"
        ]
    },
    {
        "name": "Macedonia (the former Yugoslav Republic of)",
        "alpha2Code": "MK",
        "callingCodes": [
            "389"
        ]
    },
    {
        "name": "Madagascar",
        "alpha2Code": "MG",
        "callingCodes": [
            "261"
        ]
    },
    {
        "name": "Malawi",
        "alpha2Code": "MW",
        "callingCodes": [
            "265"
        ]
    },
    {
        "name": "Malaysia",
        "alpha2Code": "MY",
        "callingCodes": [
            "60"
        ]
    },
    {
        "name": "Maldives",
        "alpha2Code": "MV",
        "callingCodes": [
            "960"
        ]
    },
    {
        "name": "Mali",
        "alpha2Code": "ML",
        "callingCodes": [
            "223"
        ]
    },
    {
        "name": "Malta",
        "alpha2Code": "MT",
        "callingCodes": [
            "356"
        ]
    },
    {
        "name": "Marshall Islands",
        "alpha2Code": "MH",
        "callingCodes": [
            "692"
        ]
    },
    {
        "name": "Martinique",
        "alpha2Code": "MQ",
        "callingCodes": [
            "596"
        ]
    },
    {
        "name": "Mauritania",
        "alpha2Code": "MR",
        "callingCodes": [
            "222"
        ]
    },
    {
        "name": "Mauritius",
        "alpha2Code": "MU",
        "callingCodes": [
            "230"
        ]
    },
    {
        "name": "Mayotte",
        "alpha2Code": "YT",
        "callingCodes": [
            "262"
        ]
    },
    {
        "name": "Mexico",
        "alpha2Code": "MX",
        "callingCodes": [
            "52"
        ]
    },
    {
        "name": "Micronesia (Federated States of)",
        "alpha2Code": "FM",
        "callingCodes": [
            "691"
        ]
    },
    {
        "name": "Moldova (Republic of)",
        "alpha2Code": "MD",
        "callingCodes": [
            "373"
        ]
    },
    {
        "name": "Monaco",
        "alpha2Code": "MC",
        "callingCodes": [
            "377"
        ]
    },
    {
        "name": "Mongolia",
        "alpha2Code": "MN",
        "callingCodes": [
            "976"
        ]
    },
    {
        "name": "Montenegro",
        "alpha2Code": "ME",
        "callingCodes": [
            "382"
        ]
    },
    {
        "name": "Montserrat",
        "alpha2Code": "MS",
        "callingCodes": [
            "1664"
        ]
    },
    {
        "name": "Morocco",
        "alpha2Code": "MA",
        "callingCodes": [
            "212"
        ]
    },
    {
        "name": "Mozambique",
        "alpha2Code": "MZ",
        "callingCodes": [
            "258"
        ]
    },
    {
        "name": "Myanmar",
        "alpha2Code": "MM",
        "callingCodes": [
            "95"
        ]
    },
    {
        "name": "Namibia",
        "alpha2Code": "NA",
        "callingCodes": [
            "264"
        ]
    },
    {
        "name": "Nauru",
        "alpha2Code": "NR",
        "callingCodes": [
            "674"
        ]
    },
    {
        "name": "Nepal",
        "alpha2Code": "NP",
        "callingCodes": [
            "977"
        ]
    },
    {
        "name": "Netherlands",
        "alpha2Code": "NL",
        "callingCodes": [
            "31"
        ]
    },
    {
        "name": "New Caledonia",
        "alpha2Code": "NC",
        "callingCodes": [
            "687"
        ]
    },
    {
        "name": "New Zealand",
        "alpha2Code": "NZ",
        "callingCodes": [
            "64"
        ]
    },
    {
        "name": "Nicaragua",
        "alpha2Code": "NI",
        "callingCodes": [
            "505"
        ]
    },
    {
        "name": "Niger",
        "alpha2Code": "NE",
        "callingCodes": [
            "227"
        ]
    },
    {
        "name": "Nigeria",
        "alpha2Code": "NG",
        "callingCodes": [
            "234"
        ]
    },
    {
        "name": "Niue",
        "alpha2Code": "NU",
        "callingCodes": [
            "683"
        ]
    },
    {
        "name": "Norfolk Island",
        "alpha2Code": "NF",
        "callingCodes": [
            "672"
        ]
    },
    {
        "name": "Korea (Democratic People's Republic of)",
        "alpha2Code": "KP",
        "callingCodes": [
            "850"
        ]
    },
    {
        "name": "Northern Mariana Islands",
        "alpha2Code": "MP",
        "callingCodes": [
            "1670"
        ]
    },
    {
        "name": "Norway",
        "alpha2Code": "NO",
        "callingCodes": [
            "47"
        ]
    },
    {
        "name": "Oman",
        "alpha2Code": "OM",
        "callingCodes": [
            "968"
        ]
    },
    {
        "name": "Pakistan",
        "alpha2Code": "PK",
        "callingCodes": [
            "92"
        ]
    },
    {
        "name": "Palau",
        "alpha2Code": "PW",
        "callingCodes": [
            "680"
        ]
    },
    {
        "name": "Palestine, State of",
        "alpha2Code": "PS",
        "callingCodes": [
            "970"
        ]
    },
    {
        "name": "Panama",
        "alpha2Code": "PA",
        "callingCodes": [
            "507"
        ]
    },
    {
        "name": "Papua New Guinea",
        "alpha2Code": "PG",
        "callingCodes": [
            "675"
        ]
    },
    {
        "name": "Paraguay",
        "alpha2Code": "PY",
        "callingCodes": [
            "595"
        ]
    },
    {
        "name": "Peru",
        "alpha2Code": "PE",
        "callingCodes": [
            "51"
        ]
    },
    {
        "name": "Philippines",
        "alpha2Code": "PH",
        "callingCodes": [
            "63"
        ]
    },
    {
        "name": "Pitcairn",
        "alpha2Code": "PN",
        "callingCodes": [
            "64"
        ]
    },
    {
        "name": "Poland",
        "alpha2Code": "PL",
        "callingCodes": [
            "48"
        ]
    },
    {
        "name": "Portugal",
        "alpha2Code": "PT",
        "callingCodes": [
            "351"
        ]
    },
    {
        "name": "Puerto Rico",
        "alpha2Code": "PR",
        "callingCodes": [
            "1787",
            "1939"
        ]
    },
    {
        "name": "Qatar",
        "alpha2Code": "QA",
        "callingCodes": [
            "974"
        ]
    },
    {
        "name": "Republic of Kosovo",
        "alpha2Code": "XK",
        "callingCodes": [
            "383"
        ]
    },
    {
        "name": "Réunion",
        "alpha2Code": "RE",
        "callingCodes": [
            "262"
        ]
    },
    {
        "name": "Romania",
        "alpha2Code": "RO",
        "callingCodes": [
            "40"
        ]
    },
    {
        "name": "Russian Federation",
        "alpha2Code": "RU",
        "callingCodes": [
            "7"
        ]
    },
    {
        "name": "Rwanda",
        "alpha2Code": "RW",
        "callingCodes": [
            "250"
        ]
    },
    {
        "name": "Saint Barthélemy",
        "alpha2Code": "BL",
        "callingCodes": [
            "590"
        ]
    },
    {
        "name": "Saint Helena, Ascension and Tristan da Cunha",
        "alpha2Code": "SH",
        "callingCodes": [
            "290"
        ]
    },
    {
        "name": "Saint Kitts and Nevis",
        "alpha2Code": "KN",
        "callingCodes": [
            "1869"
        ]
    },
    {
        "name": "Saint Lucia",
        "alpha2Code": "LC",
        "callingCodes": [
            "1758"
        ]
    },
    {
        "name": "Saint Martin (French part)",
        "alpha2Code": "MF",
        "callingCodes": [
            "590"
        ]
    },
    {
        "name": "Saint Pierre and Miquelon",
        "alpha2Code": "PM",
        "callingCodes": [
            "508"
        ]
    },
    {
        "name": "Saint Vincent and the Grenadines",
        "alpha2Code": "VC",
        "callingCodes": [
            "1784"
        ]
    },
    {
        "name": "Samoa",
        "alpha2Code": "WS",
        "callingCodes": [
            "685"
        ]
    },
    {
        "name": "San Marino",
        "alpha2Code": "SM",
        "callingCodes": [
            "378"
        ]
    },
    {
        "name": "Sao Tome and Principe",
        "alpha2Code": "ST",
        "callingCodes": [
            "239"
        ]
    },
    {
        "name": "Saudi Arabia",
        "alpha2Code": "SA",
        "callingCodes": [
            "966"
        ]
    },
    {
        "name": "Senegal",
        "alpha2Code": "SN",
        "callingCodes": [
            "221"
        ]
    },
    {
        "name": "Serbia",
        "alpha2Code": "RS",
        "callingCodes": [
            "381"
        ]
    },
    {
        "name": "Seychelles",
        "alpha2Code": "SC",
        "callingCodes": [
            "248"
        ]
    },
    {
        "name": "Sierra Leone",
        "alpha2Code": "SL",
        "callingCodes": [
            "232"
        ]
    },
    {
        "name": "Singapore",
        "alpha2Code": "SG",
        "callingCodes": [
            "65"
        ]
    },
    {
        "name": "Sint Maarten (Dutch part)",
        "alpha2Code": "SX",
        "callingCodes": [
            "1721"
        ]
    },
    {
        "name": "Slovakia",
        "alpha2Code": "SK",
        "callingCodes": [
            "421"
        ]
    },
    {
        "name": "Slovenia",
        "alpha2Code": "SI",
        "callingCodes": [
            "386"
        ]
    },
    {
        "name": "Solomon Islands",
        "alpha2Code": "SB",
        "callingCodes": [
            "677"
        ]
    },
    {
        "name": "Somalia",
        "alpha2Code": "SO",
        "callingCodes": [
            "252"
        ]
    },
    {
        "name": "South Africa",
        "alpha2Code": "ZA",
        "callingCodes": [
            "27"
        ]
    },
    {
        "name": "South Georgia and the South Sandwich Islands",
        "alpha2Code": "GS",
        "callingCodes": [
            "500"
        ]
    },
    {
        "name": "Korea (Republic of)",
        "alpha2Code": "KR",
        "callingCodes": [
            "82"
        ]
    },
    {
        "name": "South Sudan",
        "alpha2Code": "SS",
        "callingCodes": [
            "211"
        ]
    },
    {
        "name": "Spain",
        "alpha2Code": "ES",
        "callingCodes": [
            "34"
        ]
    },
    {
        "name": "Sri Lanka",
        "alpha2Code": "LK",
        "callingCodes": [
            "94"
        ]
    },
    {
        "name": "Sudan",
        "alpha2Code": "SD",
        "callingCodes": [
            "249"
        ]
    },
    {
        "name": "Suriname",
        "alpha2Code": "SR",
        "callingCodes": [
            "597"
        ]
    },
    {
        "name": "Svalbard and Jan Mayen",
        "alpha2Code": "SJ",
        "callingCodes": [
            "4779"
        ]
    },
    {
        "name": "Swaziland",
        "alpha2Code": "SZ",
        "callingCodes": [
            "268"
        ]
    },
    {
        "name": "Sweden",
        "alpha2Code": "SE",
        "callingCodes": [
            "46"
        ]
    },
    {
        "name": "Switzerland",
        "alpha2Code": "CH",
        "callingCodes": [
            "41"
        ]
    },
    {
        "name": "Syrian Arab Republic",
        "alpha2Code": "SY",
        "callingCodes": [
            "963"
        ]
    },
    {
        "name": "Taiwan",
        "alpha2Code": "TW",
        "callingCodes": [
            "886"
        ]
    },
    {
        "name": "Tajikistan",
        "alpha2Code": "TJ",
        "callingCodes": [
            "992"
        ]
    },
    {
        "name": "Tanzania, United Republic of",
        "alpha2Code": "TZ",
        "callingCodes": [
            "255"
        ]
    },
    {
        "name": "Thailand",
        "alpha2Code": "TH",
        "callingCodes": [
            "66"
        ]
    },
    {
        "name": "Timor-Leste",
        "alpha2Code": "TL",
        "callingCodes": [
            "670"
        ]
    },
    {
        "name": "Togo",
        "alpha2Code": "TG",
        "callingCodes": [
            "228"
        ]
    },
    {
        "name": "Tokelau",
        "alpha2Code": "TK",
        "callingCodes": [
            "690"
        ]
    },
    {
        "name": "Tonga",
        "alpha2Code": "TO",
        "callingCodes": [
            "676"
        ]
    },
    {
        "name": "Trinidad and Tobago",
        "alpha2Code": "TT",
        "callingCodes": [
            "1868"
        ]
    },
    {
        "name": "Tunisia",
        "alpha2Code": "TN",
        "callingCodes": [
            "216"
        ]
    },
    {
        "name": "Turkey",
        "alpha2Code": "TR",
        "callingCodes": [
            "90"
        ]
    },
    {
        "name": "Turkmenistan",
        "alpha2Code": "TM",
        "callingCodes": [
            "993"
        ]
    },
    {
        "name": "Turks and Caicos Islands",
        "alpha2Code": "TC",
        "callingCodes": [
            "1649"
        ]
    },
    {
        "name": "Tuvalu",
        "alpha2Code": "TV",
        "callingCodes": [
            "688"
        ]
    },
    {
        "name": "Uganda",
        "alpha2Code": "UG",
        "callingCodes": [
            "256"
        ]
    },
    {
        "name": "Ukraine",
        "alpha2Code": "UA",
        "callingCodes": [
            "380"
        ]
    },
    {
        "name": "United Arab Emirates",
        "alpha2Code": "AE",
        "callingCodes": [
            "971"
        ]
    },
    {
        "name": "United Kingdom of Great Britain and Northern Ireland",
        "alpha2Code": "GB",
        "callingCodes": [
            "44"
        ]
    },
    {
        "name": "United States of America",
        "alpha2Code": "US",
        "callingCodes": [
            "1"
        ]
    },
    {
        "name": "Uruguay",
        "alpha2Code": "UY",
        "callingCodes": [
            "598"
        ]
    },
    {
        "name": "Uzbekistan",
        "alpha2Code": "UZ",
        "callingCodes": [
            "998"
        ]
    },
    {
        "name": "Vanuatu",
        "alpha2Code": "VU",
        "callingCodes": [
            "678"
        ]
    },
    {
        "name": "Venezuela (Bolivarian Republic of)",
        "alpha2Code": "VE",
        "callingCodes": [
            "58"
        ]
    },
    {
        "name": "Viet Nam",
        "alpha2Code": "VN",
        "callingCodes": [
            "84"
        ]
    },
    {
        "name": "Wallis and Futuna",
        "alpha2Code": "WF",
        "callingCodes": [
            "681"
        ]
    },
    {
        "name": "Western Sahara",
        "alpha2Code": "EH",
        "callingCodes": [
            "212"
        ]
    },
    {
        "name": "Yemen",
        "alpha2Code": "YE",
        "callingCodes": [
            "967"
        ]
    },
    {
        "name": "Zambia",
        "alpha2Code": "ZM",
        "callingCodes": [
            "260"
        ]
    },
    {
        "name": "Zimbabwe",
        "alpha2Code": "ZW",
        "callingCodes": [
            "263"
        ]
    }
]