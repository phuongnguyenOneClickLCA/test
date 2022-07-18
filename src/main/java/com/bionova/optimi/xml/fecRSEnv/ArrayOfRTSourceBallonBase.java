
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
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
 *         &lt;element name="Source_Ballon_Base_Thermodynamique_Elec" type="{}RT_Data_Source_Ballon_Base_Thermodynamique_Elec"/>
 *         &lt;element name="Source_Ballon_Base_Thermodynamique_Gaz" type="{}RT_Data_Source_Ballon_Base_Thermodynamique_Gaz"/>
 *         &lt;element name="Source_Ballon_Base_Effet_Joule" type="{}RT_Data_Source_Ballon_Base_Effet_Joule"/>
 *         &lt;element name="Source_Ballon_Base_Reseau_Fourniture" type="{}RT_Data_Source_Ballon_Base_Reseau_Fourniture"/>
 *         &lt;element name="Source_Ballon_Base_Combustion" type="{}RT_Data_Source_Ballon_Base_Combustion"/>
 *         &lt;element name="Source_Ballon_Base_Boucle_Solaire" type="{}RT_Data_Source_Ballon_Base_Boucle_Solaire"/>
 *         &lt;element name="T5_CSTB_PAC_ECS_Eauglycolee_Eau" type="{}Generateur_Thermodynamique_Elec_ECSsurEauGlycolee_Data"/>
 *         &lt;element name="T5_CSTB_GenerateurThermodynamiqueDoubleService" type="{}T5_CSTB_GenerateurThermodynamiqueDoubleService_Data"/>
 *         &lt;element name="T5_CSTB_GenerateurThermodynamiqueGazDoubleService" type="{}T5_CSTB_GenerateurThermodynamiqueGazDoubleService_Data"/>
 *         &lt;element name="T5_Panasonic_PAC_TripleService" type="{}T5_Panasonic_PAC_TripleService_Data"/>
 *         &lt;element name="T5_ECOScience_CET275S" type="{}CET275S_Data"/>
 *         &lt;element ref="{}T5_CardonnelIngenierie_Boucle_Solaire_Rotex"/>
 *         &lt;element ref="{}T5_CardonnelIngenierie_BoucleSolaire_Sonnenkraft"/>
 *         &lt;element ref="{}T5_CardonnelIngenierie_Boucle_Solaire_Giordano"/>
 *         &lt;element ref="{}T5_Cardonnel_AUER_CET"/>
 *         &lt;element ref="{}T5_YACK_QTon"/>
 *         &lt;element ref="{}T5_CSTB_PAC_MG_Avec_ECS"/>
 *         &lt;element ref="{}T5_Nibe_PAC_AirExtrait_Eau"/>
 *         &lt;element ref="{}T5_HELIOPAC_Boucle_Solaire"/>
 *         &lt;element ref="{}T5_ALDES_ToneAquaAir"/>
 *         &lt;element ref="{}T5_STIMERGY_Chaudiere_numerique_SB4"/>
 *         &lt;element ref="{}T5_Cardonnel_Boostherm"/>
 *         &lt;element ref="{}T5_AERMEC_PAC_NRP"/>
 *         &lt;element ref="{}T5_UNICLIMA_PAC_TS"/>
 *         &lt;element ref="{}T5_UNICLIMA_PAC_CO2"/>
 *         &lt;element ref="{}T5_IMERYS_CET_Heliothermique"/>
 *         &lt;element ref="{}T5_CardonnelIngenierie_Boucle_Solaire_LiMithra"/>
 *         &lt;element ref="{}T5_GECO_PKOM4_CLASSIC"/>
 *         &lt;element ref="{}T5_Generateur_Thermodynamique_Elec_Eaux_grises"/>
 *         &lt;element ref="{}T5_FRANCEAIR_MYRIADE_Thermodynamique_gaz_DS"/>
 *         &lt;element ref="{}T5_FRANCEAIR_MYRIADE_Thermodynamique_gaz_ECS"/>
 *         &lt;element ref="{}T5_RIDEL_RidelX"/>
 *         &lt;element ref="{}T5_Qarnot_Computing_Chaudiere_Numerique_QB1"/>
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
    "sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule"
})
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.T5FRANCEAIRMYRIADEBallonData.SourceBallonBaseCollection.class,
    com.bionova.optimi.xml.fecRSEnv.T5CardonnelIngenierieLiMithraData.SourceBallonBaseCollection.class,
    com.bionova.optimi.xml.fecRSEnv.T5SOLARONICSPACF7BallonStockageData.SourceBallonBaseCollection.class,
    com.bionova.optimi.xml.fecRSEnv.HeliopacData.SourceBallonBaseCollection.class
})
public class ArrayOfRTSourceBallonBase {

    @XmlElements({
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Elec", type = RTDataSourceBallonBaseThermodynamiqueElec.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Thermodynamique_Gaz", type = RTDataSourceBallonBaseThermodynamiqueGaz.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Effet_Joule", type = RTDataSourceBallonBaseEffetJoule.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Reseau_Fourniture", type = RTDataSourceBallonBaseReseauFourniture.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Combustion", type = RTDataSourceBallonBaseCombustion.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Base_Boucle_Solaire", type = RTDataSourceBallonBaseBoucleSolaire.class, nillable = true),
        @XmlElement(name = "T5_CSTB_PAC_ECS_Eauglycolee_Eau", type = GenerateurThermodynamiqueElecECSsurEauGlycoleeData.class, nillable = true),
        @XmlElement(name = "T5_CSTB_GenerateurThermodynamiqueDoubleService", type = T5CSTBGenerateurThermodynamiqueDoubleServiceData.class),
        @XmlElement(name = "T5_CSTB_GenerateurThermodynamiqueGazDoubleService", type = T5CSTBGenerateurThermodynamiqueGazDoubleServiceData.class),
        @XmlElement(name = "T5_Panasonic_PAC_TripleService", type = T5PanasonicPACTripleServiceData.class),
        @XmlElement(name = "T5_ECOScience_CET275S", type = CET275SData.class),
        @XmlElement(name = "T5_CardonnelIngenierie_Boucle_Solaire_Rotex", type = T5CardonnelIngenierieBoucleSolaireRotexData.class, nillable = true),
        @XmlElement(name = "T5_CardonnelIngenierie_BoucleSolaire_Sonnenkraft", type = T5CardonnelIngenierieBoucleSolaireSonnenkraftData.class, nillable = true),
        @XmlElement(name = "T5_CardonnelIngenierie_Boucle_Solaire_Giordano", type = T5CardonnelIngenierieBoucleSolaireGiordanoData.class, nillable = true),
        @XmlElement(name = "T5_Cardonnel_AUER_CET", type = T5CardonnelAUERCETData.class, nillable = true),
        @XmlElement(name = "T5_YACK_QTon", type = QTonData.class, nillable = true),
        @XmlElement(name = "T5_CSTB_PAC_MG_Avec_ECS", type = T5CSTBPACMGAvecECS.class, nillable = true),
        @XmlElement(name = "T5_Nibe_PAC_AirExtrait_Eau", type = T5NibePACAirExtraitEauData.class, nillable = true),
        @XmlElement(name = "T5_HELIOPAC_Boucle_Solaire", type = HeliopacBoucleSolaireData.class, nillable = true),
        @XmlElement(name = "T5_ALDES_ToneAquaAir", type = T5ALDESToneAquaAirData.class, nillable = true),
        @XmlElement(name = "T5_STIMERGY_Chaudiere_numerique_SB4", type = SB4Data.class, nillable = true),
        @XmlElement(name = "T5_Cardonnel_Boostherm", type = T5CardonnelBoosthermData.class, nillable = true),
        @XmlElement(name = "T5_AERMEC_PAC_NRP", type = T5AERMECPACNRPData.class, nillable = true),
        @XmlElement(name = "T5_UNICLIMA_PAC_TS", type = T5UNICLIMAPACTSData.class, nillable = true),
        @XmlElement(name = "T5_UNICLIMA_PAC_CO2", type = PACCO2Data.class, nillable = true),
        @XmlElement(name = "T5_IMERYS_CET_Heliothermique", type = CETHeliothermiqueData.class, nillable = true),
        @XmlElement(name = "T5_CardonnelIngenierie_Boucle_Solaire_LiMithra", type = T5CardonnelIngenierieBoucleSolaireLiMithraData.class, nillable = true),
        @XmlElement(name = "T5_GECO_PKOM4_CLASSIC", type = T5GECOPKOM4CLASSICData.class, nillable = true),
        @XmlElement(name = "T5_Generateur_Thermodynamique_Elec_Eaux_grises", type = T5GenerateurThermodynamiqueElecEauxGrisesData.class, nillable = true),
        @XmlElement(name = "T5_FRANCEAIR_MYRIADE_Thermodynamique_gaz_DS", type = T5FRANCEAIRMYRIADEThermodynamiqueGazDSData.class, nillable = true),
        @XmlElement(name = "T5_FRANCEAIR_MYRIADE_Thermodynamique_gaz_ECS", type = T5FRANCEAIRMYRIADEThermodynamiqueGazECSData.class, nillable = true),
        @XmlElement(name = "T5_RIDEL_RidelX", type = RidelXData.class, nillable = true),
        @XmlElement(name = "T5_Qarnot_Computing_Chaudiere_Numerique_QB1", type = T5QarnotComputingChaudiereNumeriqueQB1Data.class, nillable = true)
    })
    protected List<Object> sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule;

    /**
     * Gets the value of the sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSourceBallonBaseThermodynamiqueElec }
     * {@link RTDataSourceBallonBaseThermodynamiqueGaz }
     * {@link RTDataSourceBallonBaseEffetJoule }
     * {@link RTDataSourceBallonBaseReseauFourniture }
     * {@link RTDataSourceBallonBaseCombustion }
     * {@link RTDataSourceBallonBaseBoucleSolaire }
     * {@link GenerateurThermodynamiqueElecECSsurEauGlycoleeData }
     * {@link T5CSTBGenerateurThermodynamiqueDoubleServiceData }
     * {@link T5CSTBGenerateurThermodynamiqueGazDoubleServiceData }
     * {@link T5PanasonicPACTripleServiceData }
     * {@link CET275SData }
     * {@link T5CardonnelIngenierieBoucleSolaireRotexData }
     * {@link T5CardonnelIngenierieBoucleSolaireSonnenkraftData }
     * {@link T5CardonnelIngenierieBoucleSolaireGiordanoData }
     * {@link T5CardonnelAUERCETData }
     * {@link QTonData }
     * {@link T5CSTBPACMGAvecECS }
     * {@link T5NibePACAirExtraitEauData }
     * {@link HeliopacBoucleSolaireData }
     * {@link T5ALDESToneAquaAirData }
     * {@link SB4Data }
     * {@link T5CardonnelBoosthermData }
     * {@link T5AERMECPACNRPData }
     * {@link T5UNICLIMAPACTSData }
     * {@link PACCO2Data }
     * {@link CETHeliothermiqueData }
     * {@link T5CardonnelIngenierieBoucleSolaireLiMithraData }
     * {@link T5GECOPKOM4CLASSICData }
     * {@link T5GenerateurThermodynamiqueElecEauxGrisesData }
     * {@link T5FRANCEAIRMYRIADEThermodynamiqueGazDSData }
     * {@link T5FRANCEAIRMYRIADEThermodynamiqueGazECSData }
     * {@link RidelXData }
     * {@link T5QarnotComputingChaudiereNumeriqueQB1Data }
     * 
     * 
     */
    public List<Object> getSourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule() {
        if (sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule == null) {
            sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule = new ArrayList<Object>();
        }
        return this.sourceBallonBaseThermodynamiqueElecOrSourceBallonBaseThermodynamiqueGazOrSourceBallonBaseEffetJoule;
    }

}
