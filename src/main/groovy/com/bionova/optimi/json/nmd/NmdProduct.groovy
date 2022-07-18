package com.bionova.optimi.json.nmd


import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import java.lang.reflect.Field


// For deserializing JSON from NMD API
/**
 * Contains the core information about constructions (name, specification, scaling Id ...)
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
class NmdProduct {

    public Integer ID
    public Integer VersieNR
    public Double zta
    public Boolean IsElementDekkend
    public Double u_waarde
    public String ProductCode
    public Integer ProductID // constructionId
    public String ProductNaam
    public Integer lambda
    public Integer ToepassingID
    public Integer CategorieID
    public Integer rc_waarde
    public Integer FunctioneleEenheidID
    public Integer SchalingsFormuleID // API might not have this, but bak file does have it
    public Double ELPR_Factor // only API has
    public Double Levensduur
    public String bim_code
    public String toelichting_eindgebruiker
    public String DatumActief
    public String DatumInActief
    public Boolean ONVZ_HGB_Toegestaan
    public Double ONVZ_HGB_Factor

    // NMD can send duplicated updates (data pollution) so we need to do their job and handle it for them!!
    @CompileStatic
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        NmdProduct that = (NmdProduct) o

        if (ID != that.ID) return false

        return true
    }

    @CompileStatic
    int hashCode() {
        return ID.hashCode()
    }
}
