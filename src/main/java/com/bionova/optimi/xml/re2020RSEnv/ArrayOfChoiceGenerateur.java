
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * LISTE des générateurs
 * 
 * <p>Java class for ArrayOfChoiceGenerateur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChoiceGenerateur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Generateur_Combustion" type="{}RT_Data_Generateur_Combustion"/>
 *         &lt;element name="Generateur_Effet_Joule" type="{}RT_Data_Generateur_Effet_Joule"/>
 *         &lt;element name="Generateur_Poele_Insert" type="{}RT_Data_Generateur_Poele_Insert"/>
 *         &lt;element name="Generateur_Reseau_Fourniture" type="{}RT_Data_Generateur_Reseau_Fourniture"/>
 *         &lt;element name="Generateur_Thermodynamique_Gaz_Reversible" type="{}RT_Data_Generateur_Thermodynamique_Gaz_Reversible"/>
 *         &lt;element name="Generateur_Thermodynamique_Gaz_NonReversible" type="{}RT_Data_Generateur_Thermodynamique_Gaz_NonReversible"/>
 *         &lt;element name="Generateur_Thermodynamique_Elec_Autre" type="{}RT_Data_Generateur_Thermodynamique_Elec_Autre"/>
 *         &lt;element name="Generateur_Thermodynamique_Elec_NonReversible" type="{}RT_Data_Generateur_Thermodynamique_Elec_NonReversible"/>
 *         &lt;element ref="{}Generateur_Thermodynamique_Hybride_Sans_Stockage"/>
 *         &lt;element ref="{}Generateur_Thermodynamique_Moteur_Gaz_NonReversible"/>
 *         &lt;element ref="{}Generateur_Thermodynamique_Moteur_Gaz_Reversible"/>
 *         &lt;element ref="{}Micro_Cogenerateur"/>
 *         &lt;element name="Gen_Echangeur_Geocooling" type="{}Generateur_Echangeur_Geocooling_Data"/>
 *         &lt;element ref="{}T5_HELIOPAC_Heliopacsystem_SS"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChoiceGenerateur", propOrder = {
    "generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfChoiceGenerateur {

    @XmlElements({
        @XmlElement(name = "Generateur_Combustion", type = RTDataGenerateurCombustion.class),
        @XmlElement(name = "Generateur_Effet_Joule", type = RTDataGenerateurEffetJoule.class),
        @XmlElement(name = "Generateur_Poele_Insert", type = RTDataGenerateurPoeleInsert.class),
        @XmlElement(name = "Generateur_Reseau_Fourniture", type = RTDataGenerateurReseauFourniture.class),
        @XmlElement(name = "Generateur_Thermodynamique_Gaz_Reversible", type = RTDataGenerateurThermodynamiqueGazReversible.class),
        @XmlElement(name = "Generateur_Thermodynamique_Gaz_NonReversible", type = RTDataGenerateurThermodynamiqueGazNonReversible.class),
        @XmlElement(name = "Generateur_Thermodynamique_Elec_Autre", type = RTDataGenerateurThermodynamiqueElecAutre.class),
        @XmlElement(name = "Generateur_Thermodynamique_Elec_NonReversible", type = RTDataGenerateurThermodynamiqueElecNonReversible.class),
        @XmlElement(name = "Generateur_Thermodynamique_Hybride_Sans_Stockage", type = RTDataGenerateurThermodynamiqueHybrideSansStockage.class, nillable = true),
        @XmlElement(name = "Generateur_Thermodynamique_Moteur_Gaz_NonReversible", type = RTDataGenerateurThermodynamiqueMoteurGazNonReversible.class, nillable = true),
        @XmlElement(name = "Generateur_Thermodynamique_Moteur_Gaz_Reversible", type = RTDataGenerateurThermodynamiqueMoteurGazReversible.class, nillable = true),
        @XmlElement(name = "Micro_Cogenerateur", type = RTDataGenerateurMicroCogenerateur.class, nillable = true),
        @XmlElement(name = "Gen_Echangeur_Geocooling", type = GenerateurEchangeurGeocoolingData.class),
        @XmlElement(name = "T5_HELIOPAC_Heliopacsystem_SS", type = HeliopacsystemSSData.class, nillable = true)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Object> generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert;

    /**
     * Gets the value of the generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataGenerateurCombustion }
     * {@link RTDataGenerateurEffetJoule }
     * {@link RTDataGenerateurPoeleInsert }
     * {@link RTDataGenerateurReseauFourniture }
     * {@link RTDataGenerateurThermodynamiqueGazReversible }
     * {@link RTDataGenerateurThermodynamiqueGazNonReversible }
     * {@link RTDataGenerateurThermodynamiqueElecAutre }
     * {@link RTDataGenerateurThermodynamiqueElecNonReversible }
     * {@link RTDataGenerateurThermodynamiqueHybrideSansStockage }
     * {@link RTDataGenerateurThermodynamiqueMoteurGazNonReversible }
     * {@link RTDataGenerateurThermodynamiqueMoteurGazReversible }
     * {@link RTDataGenerateurMicroCogenerateur }
     * {@link GenerateurEchangeurGeocoolingData }
     * {@link HeliopacsystemSSData }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Object> getGenerateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert() {
        if (generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert == null) {
            generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert = new ArrayList<Object>();
        }
        return this.generateurCombustionOrGenerateurEffetJouleOrGenerateurPoeleInsert;
    }

}
