package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

@MapConstructor(
        pre = {
            List<String> fieldNames = this.class.declaredFields.findAll { Field field -> !field.synthetic }*.name

            for (String invalidKey : (args.keySet() - fieldNames)) {
                def value = args.remove(invalidKey)
                String validKey = fieldNames.find { String fieldName -> invalidKey.equalsIgnoreCase(fieldName) }

                if (validKey) {
                    args.put(validKey, value)
                }
            }
        },
        // For some reason initialization of List and Set fields doesn't call map constructors of their elements,
        // but initialization of array fields does. So there is a need to set the fields of the collection types manually.
        // TODO: find a solution of the problem.
        excludes = ['NMD_Element_Element_Versies', 'NMD_Element_Versies', 'NMD_Product_Product_Versies', 'NMD_ProfielSets_Versies',
                'NMD_ProfielMilieueffect_Versies', 'NMD_Product_Element_ProfielSet_Versies', 'NMD_Product_Element_ProfielSet_Versies',
                'NMD_Product_Versies', 'NMD_Profiel_Versies'],
        post = {
            this.NMD_Element_Element_Versies = args.NMD_Element_Element_Versies?.collect { Map arguments -> new NmdElementElement(arguments) }
            this.NMD_Element_Versies = args.NMD_Element_Versies?.collect { Map arguments -> new NmdElementVersies(arguments) }
            this.NMD_Product_Product_Versies = args.NMD_Product_Product_Versies?.collect { Map arguments -> new NmdProductProduct(arguments) }
            this.NMD_ProfielSets_Versies = args.NMD_ProfielSets_Versies?.collect { Map arguments -> new NmdProfielSet(arguments) }
            this.NMD_ProfielMilieueffect_Versies = args.NMD_ProfielMilieueffect_Versies?.collect { Map arguments -> new NmdProfielMilieueffect(arguments) }
            this.NMD_Product_Element_ProfielSet_Versies = args.NMD_Product_Element_ProfielSet_Versies?.collect { Map arguments -> new NmdProductElementProfielSet(arguments) }
            this.NMD_Product_Versies = args.NMD_Product_Versies?.collect { Map arguments -> new NmdProduct(arguments) }
            this.NMD_Profiel_Versies = args.NMD_Profiel_Versies?.collect { Map arguments -> new NmdProfiel(arguments) }
        }
)
class NmdNewResources {

    def NMD_Schalingsformules_Versies
    def NMD_Eenheid_Versies
    def NMD_Type_Kaart_Versies
    def NMD_BouwwerkFuncties_Versies
    def NMD_Fasen_Versies
    def NMD_Milieucategorie_Versies
    def NMD_ToepassingsGebieden_Versies
    List<NmdElementElement> NMD_Element_Element_Versies
    List<NmdElementVersies> NMD_Element_Versies
    List<NmdProductProduct> NMD_Product_Product_Versies
    List<NmdProfielSet> NMD_ProfielSets_Versies
    List<NmdProfielMilieueffect> NMD_ProfielMilieueffect_Versies
    List<NmdProductElementProfielSet> NMD_Product_Element_ProfielSet_Versies
    List<NmdProduct> NMD_Product_Versies
    List<NmdProfiel> NMD_Profiel_Versies
}
