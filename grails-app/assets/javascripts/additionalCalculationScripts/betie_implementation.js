// take into account that name for namespace variable should be equal script name
function betie_implementation(resourceId, calculationParams){
    return betie_implementation_variable.calculateResourceValues(resourceId, calculationParams)
}

let betie_implementation_variable = {
    calculateResourceValues: function (resourceId, calculationParams){
            for (let key in calculationParams) {
                calculationParams[key] = Number(calculationParams[key].replace(",", "."));
            }

            let volume = betie_implementation_variable.calculateVolume(calculationParams);
            let surface = betie_implementation_variable.calculateSurface(resourceId, calculationParams);
            let specificSurface = betie_implementation_variable.calculateSpecificSurface(resourceId, calculationParams);

                return new Map([
                    ['volume_BETie', volume],
                    ['surface_formPanel_BETie', surface],
                    ['surface_specifique_BETie', specificSurface]
                ])
        },

    calculateVolume: function (calculationParams) {
            let result

            if (calculationParams['thickness_BETie'] != null) {
                result = calculationParams['thickness_BETie'];
            } else if (calculationParams['height_BETie'] != null && calculationParams['width_BETie'] != null && calculationParams['length_BETie'] == null) {
                result = calculationParams['height_BETie'] * calculationParams['width_BETie'];
            } else if (calculationParams['height_BETie'] != null && calculationParams['width_BETie'] != null && calculationParams['length_BETie'] != null) {
                result = calculationParams['height_BETie'] * calculationParams['width_BETie']* calculationParams['length_BETie'];
            } else if (calculationParams['section_a_BETie'] != null && calculationParams['section_b_BETie'] != null) {
                result = calculationParams['section_a_BETie'] * calculationParams['section_b_BETie']
            } else if (calculationParams['diameter_BETie'] != null) {
                result = Math.PI * Math.pow(calculationParams['diameter_BETie'], 2) / 4
            } else {
                result = 1
            }

            return result != null ? Math.round(result * 100) / 100 : null; //round to 2 decimal symbols
        },

    calculateSurface: function (resourceId, calculationParams){
            let result

            switch (resourceId) {
                case 'mur_BETie':
                case 'acrotere_BETIE':
                    result = 2 + 2 * calculationParams['thickness_BETie'];
                    break;
                case 'dallePleine_BETIE':
                case 'dalleCompression_BETIE':
                case 'dallePredalle_BETIE':
                case 'dalleAlveolaire_BETIE':
                    result = 1 + 4 * calculationParams['thickness_BETie'];
                    break;
                case 'dallageTerrePlein_BETIE':
                    result = 4 * calculationParams['thickness_BETie'];
                    break;
                case 'poteau_BETIE':
                    result = 2 * calculationParams['section_a_BETie'] + 2 * calculationParams['section_b_BETie'];
                    break;
                case 'poutre_BETIE':
                    result = calculationParams['width_BETie'] + 2 * calculationParams['height_BETie'];
                    break;
                case 'semelle_BETIE':
                    result =  2 * calculationParams['height_BETie'];
                    break;
                case 'semelleIsolee_BETIE':
                    result = 2 * calculationParams['height_BETie'] * (calculationParams['width_BETie'] + calculationParams['length_BETie']);
                    break;
                case 'balcon_BETIE' : 
                    result = 1 + 3 * calculationParams['thickness_BETie'];
                    break;
                default:
                    result =  0;
            }

            return result == 0 ? result : Math.round(result * 100) / 100;
        },

    calculateSpecificSurface: function (resourceId, calculationParams){
            let result

            switch (resourceId) {
                case 'mur_BETie':
                case 'dallePleine_BETIE':
                case 'balcon_BETIE':
                case 'acrotere_BETIE':
                    result = 2 / calculationParams['thickness_BETie'];
                    break;
                case 'dalleCompression_BETIE':
                case 'dallePredalle_BETIE':
                case 'dalleAlveolaire_BETIE':
                case 'dallageTerrePlein_BETIE':
                case 'plancherSurPoutre_BETIE':
                case 'betonVoirie_BETIE':
                    result = 1 / calculationParams['thickness_BETie'];
                    break;
                case 'poteauCylindrique_BETIE':
                    result = 4 / calculationParams['diameter_BETie'];
                    break;
                case 'poteau_BETIE':
                    result = (2 * calculationParams['section_a_BETie'] + 2 * calculationParams['section_b_BETie']) /
                        (calculationParams['section_a_BETie'] * calculationParams['section_b_BETie']);
                    break;
                case 'semelle_BETIE':
                case 'semelleIsolee_BETIE':
                case 'poutre_BETIE':
                    result = (2 * calculationParams['width_BETie'] + 2 * calculationParams['height_BETie'] ) /
                        (calculationParams['width_BETie'] * calculationParams['height_BETie']);
                    break;
                default:
                    result =  0;
            }

            if (result == "Infinity" || isNaN(result)){
                result = ''
            }

            return result == 0 || result == '' ? result : Math.round(result * 100) / 100;
        }
};

