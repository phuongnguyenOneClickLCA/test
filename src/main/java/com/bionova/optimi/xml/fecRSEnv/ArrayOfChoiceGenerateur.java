
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfChoiceGenerateur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChoiceGenerateur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Generateur_Thermodynamique_Elec_NonReversible" type="{}RT_Data_Generateur_Thermodynamique_Elec_NonReversible"/>
 *         &lt;element name="Generateur_Thermodynamique_Elec_Autre" type="{}RT_Data_Generateur_Thermodynamique_Elec_Autre"/>
 *         &lt;element name="Generateur_Thermodynamique_Gaz_Reversible" type="{}RT_Data_Generateur_Thermodynamique_Gaz_Reversible"/>
 *         &lt;element name="Generateur_Thermodynamique_Gaz_NonReversible" type="{}RT_Data_Generateur_Thermodynamique_Gaz_NonReversible"/>
 *         &lt;element name="Generateur_Effet_Joule" type="{}RT_Data_Generateur_Effet_Joule"/>
 *         &lt;element name="Generateur_Poele_Insert" type="{}RT_Data_Generateur_Poele_Insert"/>
 *         &lt;element name="Generateur_Reseau_Fourniture" type="{}RT_Data_Generateur_Reseau_Fourniture"/>
 *         &lt;element name="Generateur_Combustion" type="{}RT_Data_Generateur_Combustion"/>
 *         &lt;element name="T5_EREIE_PacF7" type="{}T5_EREIE_PacF7_Data" minOccurs="0"/>
 *         &lt;element ref="{}T5_CSTB_PAC_MG" minOccurs="0"/>
 *         &lt;element ref="{}T5_AFPG_Gen_Echangeur_Geocooling" minOccurs="0"/>
 *         &lt;element ref="{}T5_Cardonnel_France_Energie_Gen_BE" minOccurs="0"/>
 *         &lt;element ref="{}T5_QARNOT_Radiateur_Numerique" minOccurs="0"/>
 *         &lt;element ref="{}T5_YACK_YACKBionic" minOccurs="0"/>
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
    "generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible"
})
public class ArrayOfChoiceGenerateur {

    @XmlElements({
        @XmlElement(name = "Generateur_Thermodynamique_Elec_NonReversible", type = RTDataGenerateurThermodynamiqueElecNonReversible.class),
        @XmlElement(name = "Generateur_Thermodynamique_Elec_Autre", type = RTDataGenerateurThermodynamiqueElecAutre.class),
        @XmlElement(name = "Generateur_Thermodynamique_Gaz_Reversible", type = RTDataGenerateurThermodynamiqueGazReversible.class),
        @XmlElement(name = "Generateur_Thermodynamique_Gaz_NonReversible", type = RTDataGenerateurThermodynamiqueGazNonReversible.class),
        @XmlElement(name = "Generateur_Effet_Joule", type = RTDataGenerateurEffetJoule.class),
        @XmlElement(name = "Generateur_Poele_Insert", type = RTDataGenerateurPoeleInsert.class),
        @XmlElement(name = "Generateur_Reseau_Fourniture", type = RTDataGenerateurReseauFourniture.class),
        @XmlElement(name = "Generateur_Combustion", type = RTDataGenerateurCombustion.class),
        @XmlElement(name = "T5_EREIE_PacF7", type = T5EREIEPacF7Data.class, nillable = true),
        @XmlElement(name = "T5_CSTB_PAC_MG", type = T5CSTBPACMG.class, nillable = true),
        @XmlElement(name = "T5_AFPG_Gen_Echangeur_Geocooling", type = T5AFPGGenEchangeurGeocoolingData.class, nillable = true),
        @XmlElement(name = "T5_Cardonnel_France_Energie_Gen_BE", type = T5CardonnelFranceEnergieGenBE.class),
        @XmlElement(name = "T5_QARNOT_Radiateur_Numerique", type = T5QARNOTRadiateurNumeriqueData.class, nillable = true),
        @XmlElement(name = "T5_YACK_YACKBionic", type = YACKBionicData.class, nillable = true)
    })
    protected List<Object> generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible;

    /**
     * Gets the value of the generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataGenerateurThermodynamiqueElecNonReversible }
     * {@link RTDataGenerateurThermodynamiqueElecAutre }
     * {@link RTDataGenerateurThermodynamiqueGazReversible }
     * {@link RTDataGenerateurThermodynamiqueGazNonReversible }
     * {@link RTDataGenerateurEffetJoule }
     * {@link RTDataGenerateurPoeleInsert }
     * {@link RTDataGenerateurReseauFourniture }
     * {@link RTDataGenerateurCombustion }
     * {@link T5EREIEPacF7Data }
     * {@link T5CSTBPACMG }
     * {@link T5AFPGGenEchangeurGeocoolingData }
     * {@link T5CardonnelFranceEnergieGenBE }
     * {@link T5QARNOTRadiateurNumeriqueData }
     * {@link YACKBionicData }
     * 
     * 
     */
    public List<Object> getGenerateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible() {
        if (generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible == null) {
            generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible = new ArrayList<Object>();
        }
        return this.generateurThermodynamiqueElecNonReversibleOrGenerateurThermodynamiqueElecAutreOrGenerateurThermodynamiqueGazReversible;
    }

}
