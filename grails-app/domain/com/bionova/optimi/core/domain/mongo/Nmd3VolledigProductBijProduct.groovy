package com.bionova.optimi.core.domain.mongo

class Nmd3VolledigProductBijProduct {
    static mapWith = "mongo"

    /*

                "ProductOnderdeel": "Product",
            "ProductID": 14189,
            "ProductNaam": "Deelproduct: Stelkozijnen, Onverduurzaamd hout; geverfd",
            "ProductCode": "",
            "Toelichting_Leverancier": "Geen toelichting beschikbaar",
            "FunctioneleEenheidID": 17,
            "ElementID": 177,
            "CategorieID": 3,
            "Levensduur": 75,
            "bim_code": "xx",
            "IsElementDekkend": false

     */

    String ProductOnderdeel
    Integer ProductID
    String ProductNaam
    String ProductCode
    String Toelichting_Leverancier
    Integer FunctioneleEenheidID
    Integer ElementID
    Integer CategorieID
    Integer Levensduur
    String bim_code
    Boolean IsElementDekkend
}
