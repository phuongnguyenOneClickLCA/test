package com.bionova.optimi.json.nmd


import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import java.lang.reflect.Field


// For deserializing JSON from NMD API
/**
 * Contains the core information about resources (name, specification, scaling Id…)
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
class NmdProfielSet {

    public Integer ID
    public Integer ProfielSetID
    public String ProfielSetNaam
    public String Omschrijving
    public Integer FunctioneleEenheidID
    public Integer CategorieID
    public Double Levensduur
    public Integer VersieNR
    public Double SchalingsMaat_X1
    public Double SchalingsMaat_X2
    public Integer EenheidID_SchalingsMaat
    public Integer SchalingsFormuleID
    public Double SchalingsFormuleA1
    public Double SchalingsFormuleA2
    public Double SchalingsFormuleB1
    public Double SchalingsFormuleB2
    public Double SchalingsFormuleC
    public Double SchalingMinX1
    public Double SchalingMinX2
    public Double SchalingMaxX1
    public Double SchalingMaxX2
    public Double SchalingTestWaarde1
    public Double SchalingTestWaarde2
    public Integer ScenarioNummer
    public String ProfielSetCode
    public Boolean IsExtra
    public Integer teller
    public Boolean isOH_ProfielSet
    public Double NMD23_SchaalFactor // API might not have this, but bak file does have it
    public String GrootheidSchalingsMaat
    public String OmschrijvingSchalingsMaat
    public String DatumActief
    public String DatumInActief

    // NMD can send duplicated updates (data pollution) so we need to do their job and handle it for them!!

    @CompileStatic
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        NmdProfielSet that = (NmdProfielSet) o

        if (ID != that.ID) return false

        return true
    }

    @CompileStatic
    int hashCode() {
        return ID.hashCode()
    }
}
