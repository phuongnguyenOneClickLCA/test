package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

// For deserializing JSON from NMD API
/**
 *  Contain information about impact per stage for resources, in association with {@link NmdProfiel}
 */
@MapConstructor(
        includeFields = true,
        pre = {
            List<String> fieldNames = this.class.declaredFields.findAll { Field field -> !field.synthetic } *.name

            for (String invalidKey : (args.keySet() - fieldNames)) {
                def value = args.remove(invalidKey)
                String validKey = fieldNames.find { String fieldName -> invalidKey.equalsIgnoreCase(fieldName) }

                if (validKey) {
                    args.put(validKey, value)
                }
            }
        }
)
class NmdProfielMilieueffect {

    public Integer ID
    public Integer PME_ID
    public Integer ProfielID
    public Integer MilieuCategorieID
    public Integer VersieNR
    public Double Waarde
    public String DatumInActief
    public String DatumActief
}
