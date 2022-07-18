package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

// For deserializing JSON from NMD API
/**
 * Denotes the relationship between child construction & parent construction,
 * we use this table to map all child construction’s constituents (resources)
 * to parent construction since NMD child constructions are now shown.
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
class NmdProductProduct {

    public Integer ID
    public Integer KindProductID
    public Integer OuderProductID
    public Integer Product_ProductID
    public Integer VersieNR
    public Double Hoeveelheid
    public String DatumInActief
    public String DatumActief
    public Integer EenheidID
}
