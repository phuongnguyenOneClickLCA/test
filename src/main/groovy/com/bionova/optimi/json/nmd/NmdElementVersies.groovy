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
class NmdElementVersies {

    public Integer ID
    public Integer Element_ID
    public Integer CUAS_ID
    public String ElementNaam
    public String Code
    public String Functie
    public String Toelichting
    public String FunctioneleBeschrijving
    public Integer FunctioneleEenheidID
    public Integer VersieNR
    public Boolean Verplicht
    public Boolean IsNLsfB
    public Boolean IsProces
    public Boolean IsUitzondering
    public Boolean IsHoofdstuk
    public Boolean IsOnderdeel
    public Boolean IsRAW
    public Boolean IsGedeactiveerd
    public String DatumInActief
    public String DatumActief
}
