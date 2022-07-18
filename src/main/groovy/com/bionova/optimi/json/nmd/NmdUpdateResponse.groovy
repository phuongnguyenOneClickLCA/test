package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

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
class NmdUpdateResponse {

    public String Message
    public String zoekDatum
    public String Resource
    public NmdDeactivateResources Gedeactiveerde_Entiteiten
    public NmdNewResources Nieuwe_Publicaties
}
