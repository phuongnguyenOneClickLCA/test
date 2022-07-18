
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
 * <p>Java class for ArrayOfRTSourceBallonBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRTSourceBallonBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Source_Ballon_Base_Boucle_Solaire" type="{}RT_Data_Source_Ballon_Base_Boucle_Solaire"/>
 *         &lt;element name="Source_Ballon_Base_Combustion" type="{}RT_Data_Source_Ballon_Base_Combustion"/>
 *         &lt;element name="Source_Ballon_Base_Effet_Joule" type="{}RT_Data_Source_Ballon_Base_Effet_Joule"/>
 *         &lt;element ref="{}Source_Ballon_Base_Micro_Cogenerateur"/>
 *         &lt;element ref="{}Source_Ballon_Base_Pile_Combustible"/>
 *         &lt;element name="Source_Ballon_Base_Reseau_Fourniture" type="{}RT_Data_Source_Ballon_Base_Reseau_Fourniture"/>
 *         &lt;element ref="{}Source_Ballon_Base_Thermodynamique_AbsoGaz_DoubleService"/>
 *         &lt;element name="Source_Ballon_Base_Thermodynamique_Elec" type="{}RT_Data_Source_Ballon_Base_Thermodynamique_Elec"/>
 *         &lt;element name="Source_Ballon_Base_Thermodynamique_Elec_DoubleService" type="{}RT_Data_Source_Ballon_Base_Thermodynamique_Elec_DoubleService"/>
 *         &lt;element ref="{}Source_Ballon_Base_Thermodynamique_Elec_TripleService"/>
 *         &lt;element name="Source_Ballon_Base_Thermodynamique_Gaz" type="{}RT_Data_Source_Ballon_Base_Thermodynamique_AbsoGaz"/>
 *         &lt;element ref="{}Source_Ballon_Base_Thermodynamique_Moteur_Gaz_Avec_ECS_Reversible"/>
 *         &lt;element ref="{}Source_Ballon_Base_Thermodynamique_Moteur_Gaz_Avec_ECS_NonReversible"/>
 *         &lt;element ref="{}T5_HELIOPAC_Heliopacsystem_DS"/>
 *         &lt;element ref="{}T5_HELIOPAC_Boucle_Solaire"/>
 *         &lt;element ref="{}T5_UNICLIMA_PAC_CO2"/>
 *         &lt;element ref="{}T5_Nibe_PAC_AirExtrait_Eau"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRTSourceBallonBase", propOrder = {
    "sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTSourceBallonBase {

    @XmlElements({
        @XmlElement(name = "Source_Ballon_Base_Boucle_Solaire", type = RTDataSourceBallonBaseBoucleSolaire.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Combustion", type = RTDataSourceBallonBaseCombustion.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Effet_Joule", type = RTDataSourceBallonBaseEffetJoule.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Micro_Cogenerateur", type = RTDataSourceBallonBaseMicroCogenerationChp.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Pile_Combustible", type = RTDataSourceBallonBasePileCombustible.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Reseau_Fourniture", type = RTDataSourceBallonBaseReseauFourniture.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_AbsoGaz_DoubleService", type = RTDataSourceBallonBaseThermodynamiqueAbsoGazDoubleService.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Elec", type = RTDataSourceBallonBaseThermodynamiqueElec.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Elec_DoubleService", type = RTDataSourceBallonBaseThermodynamiqueElecDoubleService.class),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Elec_TripleService", type = RTDataSourceBallonBaseThermodynamiqueElecTripleService.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Gaz", type = RTDataSourceBallonBaseThermodynamiqueAbsoGaz.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Moteur_Gaz_Avec_ECS_Reversible", type = RTDataSourceBallonBaseThermoMoteurGazAvecECSReversible.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Moteur_Gaz_Avec_ECS_NonReversible", type = RTDataSourceBallonBaseThermoMoteurGazAvecECSNonReversible.class, nillable = true),
        @XmlElement(name = "T5_HELIOPAC_Heliopacsystem_DS", type = HeliopacsystemDSData.class, nillable = true),
        @XmlElement(name = "T5_HELIOPAC_Boucle_Solaire", type = HeliopacBoucleSolaireData.class, nillable = true),
        @XmlElement(name = "T5_UNICLIMA_PAC_CO2", type = PACCO2Data.class, nillable = true),
        @XmlElement(name = "T5_Nibe_PAC_AirExtrait_Eau", type = RTDataSourceBallonBaseThermodynamiqueElectriqueDoubleServiceNibe.class, nillable = true)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Object> sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule;

    /**
     * Gets the value of the sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSourceBallonBaseBoucleSolaire }
     * {@link RTDataSourceBallonBaseCombustion }
     * {@link RTDataSourceBallonBaseEffetJoule }
     * {@link RTDataSourceBallonBaseMicroCogenerationChp }
     * {@link RTDataSourceBallonBasePileCombustible }
     * {@link RTDataSourceBallonBaseReseauFourniture }
     * {@link RTDataSourceBallonBaseThermodynamiqueAbsoGazDoubleService }
     * {@link RTDataSourceBallonBaseThermodynamiqueElec }
     * {@link RTDataSourceBallonBaseThermodynamiqueElecDoubleService }
     * {@link RTDataSourceBallonBaseThermodynamiqueElecTripleService }
     * {@link RTDataSourceBallonBaseThermodynamiqueAbsoGaz }
     * {@link RTDataSourceBallonBaseThermoMoteurGazAvecECSReversible }
     * {@link RTDataSourceBallonBaseThermoMoteurGazAvecECSNonReversible }
     * {@link HeliopacsystemDSData }
     * {@link HeliopacBoucleSolaireData }
     * {@link PACCO2Data }
     * {@link RTDataSourceBallonBaseThermodynamiqueElectriqueDoubleServiceNibe }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Object> getSourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule() {
        if (sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule == null) {
            sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule = new ArrayList<Object>();
        }
        return this.sourceBallonBaseBoucleSolaireOrSourceBallonBaseCombustionOrSourceBallonBaseEffetJoule;
    }

}
