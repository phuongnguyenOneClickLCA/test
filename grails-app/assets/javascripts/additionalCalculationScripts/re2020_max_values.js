// take into account that name for namespace variable should be equal script name

function re2020_max_values(resourceId, calculationParams){
    return re2020_max_values_variable.calculateResourceValues(resourceId, calculationParams)
}


let re2020_max_values_variable = {
    calculateResourceValues: function (resourceId, calculationParams) {
        for (let key in calculationParams) {
            calculationParams[key] = re2020_max_values_variable.convertToNumber(calculationParams[key].replace(",", "."));
        }

        let icenergie_max_moyen = re2020_max_values_variable.calculateIcenergieMaxMoyen(calculationParams);
        let mcgeo = re2020_max_values_variable.calculateMcgeo(calculationParams);
        let mccombles = re2020_max_values_variable.calculateMccombles(calculationParams);
        let smoy_lgt = re2020_max_values_variable.calculateSmoyLgt(calculationParams);
        let mcsurf_moy = re2020_max_values_variable.calculateMcsurfMoy(calculationParams, smoy_lgt);
        let mcsurf_tot = re2020_max_values_variable.calculateMcsurfTot(calculationParams);
        let mccat = re2020_max_values_variable.calculateMccat(calculationParams);
        let icenergie_max = re2020_max_values_variable.calculateIcenergieMax(icenergie_max_moyen, mcgeo, mccombles, mcsurf_moy, mcsurf_tot, mccat);

        let icconstruction_max_moyen = re2020_max_values_variable.calculateIcconstructionMaxMoyen(calculationParams);
        let micombles = re2020_max_values_variable.calculateMicombles(calculationParams);
        let misurf = re2020_max_values_variable.calculateMisurf(calculationParams, smoy_lgt);
        let migeo = re2020_max_values_variable.calculateMigeo(calculationParams);
        let icconstruction_max_int = re2020_max_values_variable.calculateIcconstructionMaxInt(icconstruction_max_moyen, micombles, misurf, migeo);

        return new Map([
            ['Icenergie_maxmoyen', icenergie_max_moyen],
            ['mcgéo', mcgeo],
            ['mccombles', mccombles],
            ['smoylgt', smoy_lgt],
            ['mcsurf_moy', mcsurf_moy],
            ['mcsurf_tot', mcsurf_tot],
            ['mccat', mccat],
            ['Icenergie_max', icenergie_max],

            ['Icconstruction_maxmoyen', icconstruction_max_moyen],
            ['micombles', micombles],
            ['misurf', misurf],
            ['migeo', migeo],
            ['Icconstruction_max_int', icconstruction_max_int]
        ])
    },

    calculateIcenergieMaxMoyen: function (calculationParams) {
        let result = null

        if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC') {
            if (calculationParams['permisConstruire'] == 'non' && calculationParams['permisAmenager'] == 'non'  ) {
                result = 160;
            } else {
                result = 280;
            }
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC') {
            result = 560;
        }

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateMcgeo: function (calculationParams) {
        let result = null
        let usageRE2020 = calculationParams['usageRE2020']
        let altitude = calculationParams['altitude']
        let zoneClimatique = calculationParams['zone_climatique']

        if (usageRE2020 == 'singleFamilyHouseFEC') {
            if (altitude == '0') {
                if (zoneClimatique == 'H1a') {
                    result = 0.1;
                } else if (zoneClimatique == 'H1b') {
                    result = 0.15;
                } else if (zoneClimatique == 'H1c') {
                    result = 0.1;
                } else if (zoneClimatique == 'H2a') {
                    result = -0.05;
                } else if (zoneClimatique == 'H2b') {
                    result = 0;
                } else if (zoneClimatique == 'H2c') {
                    result = -0.1;
                } else if (zoneClimatique == 'H2d') {
                    result = -0.15;
                } else if (zoneClimatique == 'H3') {
                    result = -0.2;
                }
            } else if (altitude == '1') {
                if (zoneClimatique == 'H1a') {
                    result = 0.4;
                } else if (zoneClimatique == 'H1b') {
                    result = 0.5;
                } else if (zoneClimatique == 'H1c') {
                    result = 0.4;
                } else if (zoneClimatique == 'H2a') {
                    result = 0.15;
                } else if (zoneClimatique == 'H2b') {
                    result = 0.3;
                } else if (zoneClimatique == 'H2c') {
                    result = 0.05;
                } else if (zoneClimatique == 'H2d') {
                    result = 0;
                } else if (zoneClimatique == 'H3') {
                    result = -0.1;
                }
            } else if (altitude == '2') { 
                if (zoneClimatique == 'H1a') {
                    result = 0.75;
                } else if (zoneClimatique == 'H1b') {
                    result = 0.85;
                } else if (zoneClimatique == 'H1c') {
                    result = 0.75;
                } else if (zoneClimatique == 'H2a') {
                    result = 0.55;
                } else if (zoneClimatique == 'H2b') {
                    result = 0.6;
                } else if (zoneClimatique == 'H2c') {
                    result = 0.35;
                } else if (zoneClimatique == 'H2d') {
                    result = 0.25;
                } else if (zoneClimatique == 'H3') {
                    result = 0.15;
                }
            }
        } else if (usageRE2020 == 'apartmentBuildingFEC') {
            if (altitude == '0') {
                if (zoneClimatique == 'H2b') {
                    result = 0;
                }else if (['H1a', 'H1b', 'H1c'].includes(zoneClimatique)) {
                    result = 0.05;
                } else if (['H2a', 'H2d'].includes(zoneClimatique) ) {
                    result = -0.1;
                } else if (['H2c','H3'].includes(zoneClimatique) ) {
                    result = -0.15;
                }
            } else if (altitude == '1') {
                if (zoneClimatique == 'H1b') {
                    result = 0.4;
                } else if (zoneClimatique == 'H3') {
                    result = -0.1;
                } else if (['H1a', 'H1c'].includes(zoneClimatique)) {
                    result = 0.35;
                } else if (['H2a', 'H2b'].includes(zoneClimatique)) {
                    result = 0.2;
                } else if (['H2c', 'H2d'].includes(zoneClimatique)) {
                    result = 0.05;
                } 
            } else if (altitude == '2') { 
                if (zoneClimatique == 'H1b') {
                    result = 0.65;
                } else if (zoneClimatique == 'H2a') {
                    result = 0.45;
                } else if (zoneClimatique == 'H2b') {
                    result = 0.5;
                } else if (zoneClimatique == 'H3') {
                    result = 0.15;
                } else if (['H1a', 'H1c'].includes(zoneClimatique)) {
                    result = 0.55;
                } else if (['H2c', 'H2d'].includes(zoneClimatique)) {
                    result = 0.3;
                } 
            }
        }

        return result != null ? result : '';
    },

    calculateMccombles: function (calculationParams) {
        let result = null

        if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC') {
            result = 0.4 * calculationParams['Scombles'] / calculationParams['quantity'];
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC') {
            result = 0;
        }

        if (result == "Infinity" || isNaN(result)){
            result = null
        }

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateSmoyLgt: function (calculationParams) {
        let result

        if (calculationParams['NL_RE2020'] != null) {
            result = calculationParams['quantity'] / calculationParams['NL_RE2020'];
        } else {
            result = 0;
        }

        if (result == "Infinity" || isNaN(result)){
            result = null
        }

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateMcsurfMoy: function (calculationParams, smoylgt) {
        let result

        if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC' && smoylgt <= 100) {
            result = (49.5 - 0.55 * smoylgt) / 55;
        } else if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC' && smoylgt > 100 && smoylgt <= 150) {
            result = (14.5 - 0.2 * smoylgt) / 55;
        } else if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC' && smoylgt > 150) {
            result = -15.5 / 55;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && smoylgt <= 40) {
            result = (45 - smoylgt) / 70;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && smoylgt > 40 && smoylgt <= 80) {
            result = (15 - 0.25 * smoylgt) / 70;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && smoylgt > 80 && smoylgt <= 120) {
            result = (3 - 0.1 * smoylgt) / 70;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && smoylgt > 120) {
            result = -9 / 70;
        } else {
            result = 0;
        }

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateMcsurfTot: function (calculationParams) {
        let result

        if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && calculationParams['srefBuilding'] <= 1300) {
            result = (13 - 0.01 * calculationParams['srefBuilding']) / 70;
        } else {
            result = 0;
        }

        return result != null ? Math.round(result * 100) / 100 : ''; //round to 2 decimal symbols
    },

    calculateMccat: function (calculationParams) {
        let result

        if (calculationParams['categorieContraintesExterieurs'] == 'CE2' && ['H2d', 'H3'].includes(calculationParams['zone_climatique'])) {
            result = 0.1;
        } else {
            result = 0;
        }

        return result
    },

    calculateIcenergieMax: function (icenergie_maxmoyen, mcgeo, mccombles, mcsurf_moy, mcsurf_tot, mccat) {
        let result

        result = icenergie_maxmoyen * (1 + mcgeo + mccombles + mcsurf_moy + mcsurf_tot + mccat)

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateIcconstructionMaxMoyen: function (calculationParams) {
        let result = null

        if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC') {
            result = 640;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC') {
            result = 740;
        }

        return result || '';
    },

    calculateMicombles: function (calculationParams) {
        let result = null

        if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC') {
            result = 0.4 * calculationParams['Scombles'] / calculationParams['quantity'];
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC') {
            result = 0;
        }

        if (result == "Infinity" || isNaN(result)){
            result = null
        }

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateMisurf: function (calculationParams, smoylgt) {
        let result = null

        if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC' && smoylgt <= 120) {
            result = 0.36 - 3.6 * smoylgt / 1000;
        } else if (calculationParams['usageRE2020'] == 'singleFamilyHouseFEC' && smoylgt > 120) {
            result = -0.072;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && calculationParams['srefBuilding'] <= 1300) {
            result = -0.169 + 1.3 * calculationParams['srefBuilding'] / 10000;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && calculationParams['srefBuilding'] > 1300 && calculationParams['srefBuilding'] <= 4000) {
            result = 0.0455 - 0.35 * calculationParams['srefBuilding'] / 10000;
        } else if (calculationParams['usageRE2020'] == 'apartmentBuildingFEC' && calculationParams['srefBuilding'] > 4000) {
            result = -0.0945;
        }

        return re2020_max_values_variable.roundingResult(result);
    },

    calculateMigeo: function (calculationParams) {
        let result

        if (calculationParams['altitude'] == '0' && ['H2d', 'H3'].includes(calculationParams['zone_climatique'])) {
            result = 30;
        } else {
            result = 0;
        }

        return result
    },

    calculateIcconstructionMaxInt: function (icconstruction_max_moyen, micombles, misurf, migeo) {
        let result

        result = icconstruction_max_moyen * (1 + micombles + misurf) + migeo

        return re2020_max_values_variable.roundingResult(result);
    },

    convertToNumber: function (value){
        if(isNaN(value)){
            return value
        } else {
            return Number(value)
        }
    },

    roundingResult: function (value){
        return value != null ? parseFloat(value.toFixed(5)) : ''
    }
};

