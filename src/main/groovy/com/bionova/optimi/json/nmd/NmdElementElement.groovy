package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

// For deserializing JSON from NMD API
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
class NmdElementElement {

    public Integer ID
    public Integer OuderElementID
    public Integer KindElementID
    public String DatumActief
    public String DatumInActief
}
