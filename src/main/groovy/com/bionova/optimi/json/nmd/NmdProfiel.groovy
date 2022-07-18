package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

// For deserializing JSON from NMD API
/**
 *  Contain information about impact per stage for resources, in association with {@link NmdProfielMilieueffect}
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
class NmdProfiel {

    public Integer ID
    public String ProfielCode
    public String ProfielNaam
    public Integer ProfielID
    public Integer ProfielSetID
    public Integer FaseID
    public Integer VersieNR
    public String DatumInActief
    public String DatumActief
}
