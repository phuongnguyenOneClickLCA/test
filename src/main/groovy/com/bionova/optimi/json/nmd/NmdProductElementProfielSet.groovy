package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

// For deserializing JSON from NMD API
/**
 * Mapping constructions with its resources / constituents and the NmdElement it corresponds to.
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
class NmdProductElementProfielSet {

    public Integer ID
    public Integer PEP_ID
    public Integer ElementID
    public Integer ProfielSetID
    public Boolean ProfielSetGekoppeld
    public Integer ScenarioID
    public Integer VersieNR
    public Integer ProductID
    public Double Hoeveelheid
    public Integer teller
    public String DatumActief
    public String DatumInActief
}
