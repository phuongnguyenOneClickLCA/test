package com.bionova.optimi.json.nmd

import groovy.transform.MapConstructor

import java.lang.reflect.Field

@MapConstructor(
        pre = {
            List<String> fieldNames = this.class.declaredFields.findAll { Field field -> !field.synthetic } *.name

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
        excludes = ['NMD_Element_Versies', 'NMD_Product_Versies'],
        post = {
            this.NMD_Element_Versies = args.NMD_Element_Versies?.collect { Map arguments -> new NmdElementVersies(arguments) }
            this.NMD_Product_Versies = args.NMD_Product_Versies?.collect { Map arguments -> new NmdProduct(arguments) }
        }
)
class NmdDeactivateResources {
    def NMD_Element_Element_Versies
    def NMD_Schalingsformules_Versies
    def NMD_Type_Kaart_Versies
    List<NmdElementVersies> NMD_Element_Versies
    def NMD_BouwwerkFuncties_Versies
    def NMD_Fasen_Versies
    def NMD_Milieucategorie_Versies
    List<NmdProduct> NMD_Product_Versies
    def NMD_Eenheid_Versies
    def NMD_ToepassingsGebieden_Versies
}
