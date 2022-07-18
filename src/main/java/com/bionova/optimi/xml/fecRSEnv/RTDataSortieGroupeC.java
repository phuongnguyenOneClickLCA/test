
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Groupe_C complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Groupe_C">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_aux_ventilateur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_aux_distribution_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_aux_ventilateur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_aux_distribution_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecl_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_auxv_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_auxs_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ch_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ch_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ch_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ch_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ch_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cep_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_aux_ventilateur_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_aux_distribution_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="O_Mctype" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcgeo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcalt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcsurf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcges" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_McbilanBEPOS_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_McbilanBEPOS_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_McbilanBEPOS_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_ProdRef" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_C", propOrder = {

})
public class RTDataSortieGroupeC {

    @XmlElement(name = "O_Shon_RT")
    protected double oShonRT;
    @XmlElement(name = "O_SHAB")
    protected double oshab;
    @XmlElement(name = "O_SURT")
    protected double osurt;
    @XmlElement(name = "O_Cep_Max")
    protected double oCepMax;
    @XmlElement(name = "O_Cef_annuel")
    protected double oCefAnnuel;
    @XmlElement(name = "O_Cep_annuel")
    protected double oCepAnnuel;
    @XmlElement(name = "O_BilanBEPOS_max_1")
    protected double oBilanBEPOSMax1;
    @XmlElement(name = "O_BilanBEPOS_max_2")
    protected double oBilanBEPOSMax2;
    @XmlElement(name = "O_BilanBEPOS_max_3")
    protected double oBilanBEPOSMax3;
    @XmlElement(name = "O_BilanBEPOS_max_4")
    protected double oBilanBEPOSMax4;
    @XmlElement(name = "O_Cef_ch_annuel")
    protected double oCefChAnnuel;
    @XmlElement(name = "O_Cef_fr_annuel")
    protected double oCefFrAnnuel;
    @XmlElement(name = "O_Cef_ecs_annuel")
    protected double oCefEcsAnnuel;
    @XmlElement(name = "O_Cef_ecl_annuel")
    protected double oCefEclAnnuel;
    @XmlElement(name = "O_Cef_aux_ventilateur_annuel")
    protected double oCefAuxVentilateurAnnuel;
    @XmlElement(name = "O_Cef_aux_distribution_annuel")
    protected double oCefAuxDistributionAnnuel;
    @XmlElement(name = "O_Cep_ch_annuel")
    protected double oCepChAnnuel;
    @XmlElement(name = "O_Cep_fr_annuel")
    protected double oCepFrAnnuel;
    @XmlElement(name = "O_Cep_ecs_annuel")
    protected double oCepEcsAnnuel;
    @XmlElement(name = "O_Cep_ecl_annuel")
    protected double oCepEclAnnuel;
    @XmlElement(name = "O_Cep_aux_ventilateur_annuel")
    protected double oCepAuxVentilateurAnnuel;
    @XmlElement(name = "O_Cep_aux_distribution_annuel")
    protected double oCepAuxDistributionAnnuel;
    @XmlElement(name = "O_Cep_ch_gaz_annuel")
    protected double oCepChGazAnnuel;
    @XmlElement(name = "O_Cep_fr_gaz_annuel")
    protected double oCepFrGazAnnuel;
    @XmlElement(name = "O_Cep_ecs_gaz_annuel")
    protected double oCepEcsGazAnnuel;
    @XmlElement(name = "O_Cep_gaz_annuel")
    protected double oCepGazAnnuel;
    @XmlElement(name = "O_Cep_ch_fioul_annuel")
    protected double oCepChFioulAnnuel;
    @XmlElement(name = "O_Cep_fr_fioul_annuel")
    protected double oCepFrFioulAnnuel;
    @XmlElement(name = "O_Cep_ecs_fioul_annuel")
    protected double oCepEcsFioulAnnuel;
    @XmlElement(name = "O_Cep_fioul_annuel")
    protected double oCepFioulAnnuel;
    @XmlElement(name = "O_Cep_ch_charbon_annuel")
    protected double oCepChCharbonAnnuel;
    @XmlElement(name = "O_Cep_fr_charbon_annuel")
    protected double oCepFrCharbonAnnuel;
    @XmlElement(name = "O_Cep_ecs_charbon_annuel")
    protected double oCepEcsCharbonAnnuel;
    @XmlElement(name = "O_Cep_charbon_annuel")
    protected double oCepCharbonAnnuel;
    @XmlElement(name = "O_Cep_ch_bois_annuel")
    protected double oCepChBoisAnnuel;
    @XmlElement(name = "O_Cep_fr_bois_annuel")
    protected double oCepFrBoisAnnuel;
    @XmlElement(name = "O_Cep_ecs_bois_annuel")
    protected double oCepEcsBoisAnnuel;
    @XmlElement(name = "O_Cep_bois_annuel")
    protected double oCepBoisAnnuel;
    @XmlElement(name = "O_Cep_ch_elec_annuel")
    protected double oCepChElecAnnuel;
    @XmlElement(name = "O_Cep_fr_elec_annuel")
    protected double oCepFrElecAnnuel;
    @XmlElement(name = "O_Cep_ecs_elec_annuel")
    protected double oCepEcsElecAnnuel;
    @XmlElement(name = "O_Cep_ecl_elec_annuel")
    protected double oCepEclElecAnnuel;
    @XmlElement(name = "O_Cep_auxv_elec_annuel")
    protected double oCepAuxvElecAnnuel;
    @XmlElement(name = "O_Cep_auxs_elec_annuel")
    protected double oCepAuxsElecAnnuel;
    @XmlElement(name = "O_Cep_elec_annuel")
    protected double oCepElecAnnuel;
    @XmlElement(name = "O_Cep_ch_reseau_chaleur_annuel")
    protected double oCepChReseauChaleurAnnuel;
    @XmlElement(name = "O_Cep_fr_reseau_chaleur_annuel")
    protected double oCepFrReseauChaleurAnnuel;
    @XmlElement(name = "O_Cep_ecs_reseau_chaleur_annuel")
    protected double oCepEcsReseauChaleurAnnuel;
    @XmlElement(name = "O_Cef_ch_gaz_annuel")
    protected Double oCefChGazAnnuel;
    @XmlElement(name = "O_Cef_fr_gaz_annuel")
    protected Double oCefFrGazAnnuel;
    @XmlElement(name = "O_Cef_ecs_gaz_annuel")
    protected Double oCefEcsGazAnnuel;
    @XmlElement(name = "O_Cef_ch_fioul_annuel")
    protected Double oCefChFioulAnnuel;
    @XmlElement(name = "O_Cef_fr_fioul_annuel")
    protected Double oCefFrFioulAnnuel;
    @XmlElement(name = "O_Cef_ecs_fioul_annuel")
    protected Double oCefEcsFioulAnnuel;
    @XmlElement(name = "O_Cef_ch_charbon_annuel")
    protected Double oCefChCharbonAnnuel;
    @XmlElement(name = "O_Cef_fr_charbon_annuel")
    protected Double oCefFrCharbonAnnuel;
    @XmlElement(name = "O_Cef_ecs_charbon_annuel")
    protected Double oCefEcsCharbonAnnuel;
    @XmlElement(name = "O_Cef_ch_bois_annuel")
    protected Double oCefChBoisAnnuel;
    @XmlElement(name = "O_Cef_fr_bois_annuel")
    protected Double oCefFrBoisAnnuel;
    @XmlElement(name = "O_Cef_ecs_bois_annuel")
    protected Double oCefEcsBoisAnnuel;
    @XmlElement(name = "O_Cef_ch_elec_annuel")
    protected Double oCefChElecAnnuel;
    @XmlElement(name = "O_Cef_fr_elec_annuel")
    protected Double oCefFrElecAnnuel;
    @XmlElement(name = "O_Cef_ecs_elec_annuel")
    protected Double oCefEcsElecAnnuel;
    @XmlElement(name = "O_Cef_ch_reseau_chaleur_annuel")
    protected Double oCefChReseauChaleurAnnuel;
    @XmlElement(name = "O_Cef_fr_reseau_chaleur_annuel")
    protected Double oCefFrReseauChaleurAnnuel;
    @XmlElement(name = "O_Cef_ecs_reseau_chaleur_annuel")
    protected Double oCefEcsReseauChaleurAnnuel;
    @XmlElement(name = "O_Cep_reseau_chaleur_annuel")
    protected double oCepReseauChaleurAnnuel;
    @XmlElement(name = "O_Cef_gaz_annuel")
    protected double oCefGazAnnuel;
    @XmlElement(name = "O_Cef_fioul_annuel")
    protected double oCefFioulAnnuel;
    @XmlElement(name = "O_Cef_charbon_annuel")
    protected double oCefCharbonAnnuel;
    @XmlElement(name = "O_Cef_bois_annuel")
    protected double oCefBoisAnnuel;
    @XmlElement(name = "O_Cef_elec_annuel")
    protected double oCefElecAnnuel;
    @XmlElement(name = "O_Cef_reseau_chaleur_annuel")
    protected double oCefReseauChaleurAnnuel;
    @XmlElement(name = "O_B_Ecs_annuel")
    protected double obEcsAnnuel;
    @XmlElement(name = "O_Cep_ch_mois")
    protected ArrayOfRTDataSortieMensuelle oCepChMois;
    @XmlElement(name = "O_Cep_fr_mois")
    protected ArrayOfRTDataSortieMensuelle oCepFrMois;
    @XmlElement(name = "O_Cep_ecs_mois")
    protected ArrayOfRTDataSortieMensuelle oCepEcsMois;
    @XmlElement(name = "O_Cep_ecl_mois")
    protected ArrayOfRTDataSortieMensuelle oCepEclMois;
    @XmlElement(name = "O_Cep_aux_ventilateur_mois")
    protected ArrayOfRTDataSortieMensuelle oCepAuxVentilateurMois;
    @XmlElement(name = "O_Cep_aux_distribution_mois")
    protected ArrayOfRTDataSortieMensuelle oCepAuxDistributionMois;
    @XmlElement(name = "O_B_Ecs_mois")
    protected ArrayOfRTDataSortieMensuelle obEcsMois;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "O_Mctype")
    protected double oMctype;
    @XmlElement(name = "O_Mcgeo")
    protected double oMcgeo;
    @XmlElement(name = "O_Mcalt")
    protected double oMcalt;
    @XmlElement(name = "O_Mcsurf")
    protected double oMcsurf;
    @XmlElement(name = "O_Mcges")
    protected double oMcges;
    @XmlElement(name = "O_McbilanBEPOS_1")
    protected double oMcbilanBEPOS1;
    @XmlElement(name = "O_McbilanBEPOS_2")
    protected double oMcbilanBEPOS2;
    @XmlElement(name = "O_McbilanBEPOS_3")
    protected double oMcbilanBEPOS3;
    @XmlElement(name = "O_ProdRef")
    protected double oProdRef;

    /**
     * Gets the value of the oShonRT property.
     * 
     */
    public double getOShonRT() {
        return oShonRT;
    }

    /**
     * Sets the value of the oShonRT property.
     * 
     */
    public void setOShonRT(double value) {
        this.oShonRT = value;
    }

    /**
     * Gets the value of the oshab property.
     * 
     */
    public double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     */
    public void setOSHAB(double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osurt property.
     * 
     */
    public double getOSURT() {
        return osurt;
    }

    /**
     * Sets the value of the osurt property.
     * 
     */
    public void setOSURT(double value) {
        this.osurt = value;
    }

    /**
     * Gets the value of the oCepMax property.
     * 
     */
    public double getOCepMax() {
        return oCepMax;
    }

    /**
     * Sets the value of the oCepMax property.
     * 
     */
    public void setOCepMax(double value) {
        this.oCepMax = value;
    }

    /**
     * Gets the value of the oCefAnnuel property.
     * 
     */
    public double getOCefAnnuel() {
        return oCefAnnuel;
    }

    /**
     * Sets the value of the oCefAnnuel property.
     * 
     */
    public void setOCefAnnuel(double value) {
        this.oCefAnnuel = value;
    }

    /**
     * Gets the value of the oCepAnnuel property.
     * 
     */
    public double getOCepAnnuel() {
        return oCepAnnuel;
    }

    /**
     * Sets the value of the oCepAnnuel property.
     * 
     */
    public void setOCepAnnuel(double value) {
        this.oCepAnnuel = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax1 property.
     * 
     */
    public double getOBilanBEPOSMax1() {
        return oBilanBEPOSMax1;
    }

    /**
     * Sets the value of the oBilanBEPOSMax1 property.
     * 
     */
    public void setOBilanBEPOSMax1(double value) {
        this.oBilanBEPOSMax1 = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax2 property.
     * 
     */
    public double getOBilanBEPOSMax2() {
        return oBilanBEPOSMax2;
    }

    /**
     * Sets the value of the oBilanBEPOSMax2 property.
     * 
     */
    public void setOBilanBEPOSMax2(double value) {
        this.oBilanBEPOSMax2 = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax3 property.
     * 
     */
    public double getOBilanBEPOSMax3() {
        return oBilanBEPOSMax3;
    }

    /**
     * Sets the value of the oBilanBEPOSMax3 property.
     * 
     */
    public void setOBilanBEPOSMax3(double value) {
        this.oBilanBEPOSMax3 = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax4 property.
     * 
     */
    public double getOBilanBEPOSMax4() {
        return oBilanBEPOSMax4;
    }

    /**
     * Sets the value of the oBilanBEPOSMax4 property.
     * 
     */
    public void setOBilanBEPOSMax4(double value) {
        this.oBilanBEPOSMax4 = value;
    }

    /**
     * Gets the value of the oCefChAnnuel property.
     * 
     */
    public double getOCefChAnnuel() {
        return oCefChAnnuel;
    }

    /**
     * Sets the value of the oCefChAnnuel property.
     * 
     */
    public void setOCefChAnnuel(double value) {
        this.oCefChAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrAnnuel property.
     * 
     */
    public double getOCefFrAnnuel() {
        return oCefFrAnnuel;
    }

    /**
     * Sets the value of the oCefFrAnnuel property.
     * 
     */
    public void setOCefFrAnnuel(double value) {
        this.oCefFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsAnnuel property.
     * 
     */
    public double getOCefEcsAnnuel() {
        return oCefEcsAnnuel;
    }

    /**
     * Sets the value of the oCefEcsAnnuel property.
     * 
     */
    public void setOCefEcsAnnuel(double value) {
        this.oCefEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefEclAnnuel property.
     * 
     */
    public double getOCefEclAnnuel() {
        return oCefEclAnnuel;
    }

    /**
     * Sets the value of the oCefEclAnnuel property.
     * 
     */
    public void setOCefEclAnnuel(double value) {
        this.oCefEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefAuxVentilateurAnnuel property.
     * 
     */
    public double getOCefAuxVentilateurAnnuel() {
        return oCefAuxVentilateurAnnuel;
    }

    /**
     * Sets the value of the oCefAuxVentilateurAnnuel property.
     * 
     */
    public void setOCefAuxVentilateurAnnuel(double value) {
        this.oCefAuxVentilateurAnnuel = value;
    }

    /**
     * Gets the value of the oCefAuxDistributionAnnuel property.
     * 
     */
    public double getOCefAuxDistributionAnnuel() {
        return oCefAuxDistributionAnnuel;
    }

    /**
     * Sets the value of the oCefAuxDistributionAnnuel property.
     * 
     */
    public void setOCefAuxDistributionAnnuel(double value) {
        this.oCefAuxDistributionAnnuel = value;
    }

    /**
     * Gets the value of the oCepChAnnuel property.
     * 
     */
    public double getOCepChAnnuel() {
        return oCepChAnnuel;
    }

    /**
     * Sets the value of the oCepChAnnuel property.
     * 
     */
    public void setOCepChAnnuel(double value) {
        this.oCepChAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrAnnuel property.
     * 
     */
    public double getOCepFrAnnuel() {
        return oCepFrAnnuel;
    }

    /**
     * Sets the value of the oCepFrAnnuel property.
     * 
     */
    public void setOCepFrAnnuel(double value) {
        this.oCepFrAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsAnnuel property.
     * 
     */
    public double getOCepEcsAnnuel() {
        return oCepEcsAnnuel;
    }

    /**
     * Sets the value of the oCepEcsAnnuel property.
     * 
     */
    public void setOCepEcsAnnuel(double value) {
        this.oCepEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCepEclAnnuel property.
     * 
     */
    public double getOCepEclAnnuel() {
        return oCepEclAnnuel;
    }

    /**
     * Sets the value of the oCepEclAnnuel property.
     * 
     */
    public void setOCepEclAnnuel(double value) {
        this.oCepEclAnnuel = value;
    }

    /**
     * Gets the value of the oCepAuxVentilateurAnnuel property.
     * 
     */
    public double getOCepAuxVentilateurAnnuel() {
        return oCepAuxVentilateurAnnuel;
    }

    /**
     * Sets the value of the oCepAuxVentilateurAnnuel property.
     * 
     */
    public void setOCepAuxVentilateurAnnuel(double value) {
        this.oCepAuxVentilateurAnnuel = value;
    }

    /**
     * Gets the value of the oCepAuxDistributionAnnuel property.
     * 
     */
    public double getOCepAuxDistributionAnnuel() {
        return oCepAuxDistributionAnnuel;
    }

    /**
     * Sets the value of the oCepAuxDistributionAnnuel property.
     * 
     */
    public void setOCepAuxDistributionAnnuel(double value) {
        this.oCepAuxDistributionAnnuel = value;
    }

    /**
     * Gets the value of the oCepChGazAnnuel property.
     * 
     */
    public double getOCepChGazAnnuel() {
        return oCepChGazAnnuel;
    }

    /**
     * Sets the value of the oCepChGazAnnuel property.
     * 
     */
    public void setOCepChGazAnnuel(double value) {
        this.oCepChGazAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrGazAnnuel property.
     * 
     */
    public double getOCepFrGazAnnuel() {
        return oCepFrGazAnnuel;
    }

    /**
     * Sets the value of the oCepFrGazAnnuel property.
     * 
     */
    public void setOCepFrGazAnnuel(double value) {
        this.oCepFrGazAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsGazAnnuel property.
     * 
     */
    public double getOCepEcsGazAnnuel() {
        return oCepEcsGazAnnuel;
    }

    /**
     * Sets the value of the oCepEcsGazAnnuel property.
     * 
     */
    public void setOCepEcsGazAnnuel(double value) {
        this.oCepEcsGazAnnuel = value;
    }

    /**
     * Gets the value of the oCepGazAnnuel property.
     * 
     */
    public double getOCepGazAnnuel() {
        return oCepGazAnnuel;
    }

    /**
     * Sets the value of the oCepGazAnnuel property.
     * 
     */
    public void setOCepGazAnnuel(double value) {
        this.oCepGazAnnuel = value;
    }

    /**
     * Gets the value of the oCepChFioulAnnuel property.
     * 
     */
    public double getOCepChFioulAnnuel() {
        return oCepChFioulAnnuel;
    }

    /**
     * Sets the value of the oCepChFioulAnnuel property.
     * 
     */
    public void setOCepChFioulAnnuel(double value) {
        this.oCepChFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrFioulAnnuel property.
     * 
     */
    public double getOCepFrFioulAnnuel() {
        return oCepFrFioulAnnuel;
    }

    /**
     * Sets the value of the oCepFrFioulAnnuel property.
     * 
     */
    public void setOCepFrFioulAnnuel(double value) {
        this.oCepFrFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsFioulAnnuel property.
     * 
     */
    public double getOCepEcsFioulAnnuel() {
        return oCepEcsFioulAnnuel;
    }

    /**
     * Sets the value of the oCepEcsFioulAnnuel property.
     * 
     */
    public void setOCepEcsFioulAnnuel(double value) {
        this.oCepEcsFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCepFioulAnnuel property.
     * 
     */
    public double getOCepFioulAnnuel() {
        return oCepFioulAnnuel;
    }

    /**
     * Sets the value of the oCepFioulAnnuel property.
     * 
     */
    public void setOCepFioulAnnuel(double value) {
        this.oCepFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCepChCharbonAnnuel property.
     * 
     */
    public double getOCepChCharbonAnnuel() {
        return oCepChCharbonAnnuel;
    }

    /**
     * Sets the value of the oCepChCharbonAnnuel property.
     * 
     */
    public void setOCepChCharbonAnnuel(double value) {
        this.oCepChCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrCharbonAnnuel property.
     * 
     */
    public double getOCepFrCharbonAnnuel() {
        return oCepFrCharbonAnnuel;
    }

    /**
     * Sets the value of the oCepFrCharbonAnnuel property.
     * 
     */
    public void setOCepFrCharbonAnnuel(double value) {
        this.oCepFrCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsCharbonAnnuel property.
     * 
     */
    public double getOCepEcsCharbonAnnuel() {
        return oCepEcsCharbonAnnuel;
    }

    /**
     * Sets the value of the oCepEcsCharbonAnnuel property.
     * 
     */
    public void setOCepEcsCharbonAnnuel(double value) {
        this.oCepEcsCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCepCharbonAnnuel property.
     * 
     */
    public double getOCepCharbonAnnuel() {
        return oCepCharbonAnnuel;
    }

    /**
     * Sets the value of the oCepCharbonAnnuel property.
     * 
     */
    public void setOCepCharbonAnnuel(double value) {
        this.oCepCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCepChBoisAnnuel property.
     * 
     */
    public double getOCepChBoisAnnuel() {
        return oCepChBoisAnnuel;
    }

    /**
     * Sets the value of the oCepChBoisAnnuel property.
     * 
     */
    public void setOCepChBoisAnnuel(double value) {
        this.oCepChBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrBoisAnnuel property.
     * 
     */
    public double getOCepFrBoisAnnuel() {
        return oCepFrBoisAnnuel;
    }

    /**
     * Sets the value of the oCepFrBoisAnnuel property.
     * 
     */
    public void setOCepFrBoisAnnuel(double value) {
        this.oCepFrBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsBoisAnnuel property.
     * 
     */
    public double getOCepEcsBoisAnnuel() {
        return oCepEcsBoisAnnuel;
    }

    /**
     * Sets the value of the oCepEcsBoisAnnuel property.
     * 
     */
    public void setOCepEcsBoisAnnuel(double value) {
        this.oCepEcsBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCepBoisAnnuel property.
     * 
     */
    public double getOCepBoisAnnuel() {
        return oCepBoisAnnuel;
    }

    /**
     * Sets the value of the oCepBoisAnnuel property.
     * 
     */
    public void setOCepBoisAnnuel(double value) {
        this.oCepBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCepChElecAnnuel property.
     * 
     */
    public double getOCepChElecAnnuel() {
        return oCepChElecAnnuel;
    }

    /**
     * Sets the value of the oCepChElecAnnuel property.
     * 
     */
    public void setOCepChElecAnnuel(double value) {
        this.oCepChElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrElecAnnuel property.
     * 
     */
    public double getOCepFrElecAnnuel() {
        return oCepFrElecAnnuel;
    }

    /**
     * Sets the value of the oCepFrElecAnnuel property.
     * 
     */
    public void setOCepFrElecAnnuel(double value) {
        this.oCepFrElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsElecAnnuel property.
     * 
     */
    public double getOCepEcsElecAnnuel() {
        return oCepEcsElecAnnuel;
    }

    /**
     * Sets the value of the oCepEcsElecAnnuel property.
     * 
     */
    public void setOCepEcsElecAnnuel(double value) {
        this.oCepEcsElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepEclElecAnnuel property.
     * 
     */
    public double getOCepEclElecAnnuel() {
        return oCepEclElecAnnuel;
    }

    /**
     * Sets the value of the oCepEclElecAnnuel property.
     * 
     */
    public void setOCepEclElecAnnuel(double value) {
        this.oCepEclElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepAuxvElecAnnuel property.
     * 
     */
    public double getOCepAuxvElecAnnuel() {
        return oCepAuxvElecAnnuel;
    }

    /**
     * Sets the value of the oCepAuxvElecAnnuel property.
     * 
     */
    public void setOCepAuxvElecAnnuel(double value) {
        this.oCepAuxvElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepAuxsElecAnnuel property.
     * 
     */
    public double getOCepAuxsElecAnnuel() {
        return oCepAuxsElecAnnuel;
    }

    /**
     * Sets the value of the oCepAuxsElecAnnuel property.
     * 
     */
    public void setOCepAuxsElecAnnuel(double value) {
        this.oCepAuxsElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepElecAnnuel property.
     * 
     */
    public double getOCepElecAnnuel() {
        return oCepElecAnnuel;
    }

    /**
     * Sets the value of the oCepElecAnnuel property.
     * 
     */
    public void setOCepElecAnnuel(double value) {
        this.oCepElecAnnuel = value;
    }

    /**
     * Gets the value of the oCepChReseauChaleurAnnuel property.
     * 
     */
    public double getOCepChReseauChaleurAnnuel() {
        return oCepChReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCepChReseauChaleurAnnuel property.
     * 
     */
    public void setOCepChReseauChaleurAnnuel(double value) {
        this.oCepChReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCepFrReseauChaleurAnnuel property.
     * 
     */
    public double getOCepFrReseauChaleurAnnuel() {
        return oCepFrReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCepFrReseauChaleurAnnuel property.
     * 
     */
    public void setOCepFrReseauChaleurAnnuel(double value) {
        this.oCepFrReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCepEcsReseauChaleurAnnuel property.
     * 
     */
    public double getOCepEcsReseauChaleurAnnuel() {
        return oCepEcsReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCepEcsReseauChaleurAnnuel property.
     * 
     */
    public void setOCepEcsReseauChaleurAnnuel(double value) {
        this.oCepEcsReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCefChGazAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefChGazAnnuel() {
        return oCefChGazAnnuel;
    }

    /**
     * Sets the value of the oCefChGazAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefChGazAnnuel(Double value) {
        this.oCefChGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrGazAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefFrGazAnnuel() {
        return oCefFrGazAnnuel;
    }

    /**
     * Sets the value of the oCefFrGazAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefFrGazAnnuel(Double value) {
        this.oCefFrGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsGazAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefEcsGazAnnuel() {
        return oCefEcsGazAnnuel;
    }

    /**
     * Sets the value of the oCefEcsGazAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefEcsGazAnnuel(Double value) {
        this.oCefEcsGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefChFioulAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefChFioulAnnuel() {
        return oCefChFioulAnnuel;
    }

    /**
     * Sets the value of the oCefChFioulAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefChFioulAnnuel(Double value) {
        this.oCefChFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrFioulAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefFrFioulAnnuel() {
        return oCefFrFioulAnnuel;
    }

    /**
     * Sets the value of the oCefFrFioulAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefFrFioulAnnuel(Double value) {
        this.oCefFrFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsFioulAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefEcsFioulAnnuel() {
        return oCefEcsFioulAnnuel;
    }

    /**
     * Sets the value of the oCefEcsFioulAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefEcsFioulAnnuel(Double value) {
        this.oCefEcsFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefChCharbonAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefChCharbonAnnuel() {
        return oCefChCharbonAnnuel;
    }

    /**
     * Sets the value of the oCefChCharbonAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefChCharbonAnnuel(Double value) {
        this.oCefChCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrCharbonAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefFrCharbonAnnuel() {
        return oCefFrCharbonAnnuel;
    }

    /**
     * Sets the value of the oCefFrCharbonAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefFrCharbonAnnuel(Double value) {
        this.oCefFrCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsCharbonAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefEcsCharbonAnnuel() {
        return oCefEcsCharbonAnnuel;
    }

    /**
     * Sets the value of the oCefEcsCharbonAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefEcsCharbonAnnuel(Double value) {
        this.oCefEcsCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCefChBoisAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefChBoisAnnuel() {
        return oCefChBoisAnnuel;
    }

    /**
     * Sets the value of the oCefChBoisAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefChBoisAnnuel(Double value) {
        this.oCefChBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrBoisAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefFrBoisAnnuel() {
        return oCefFrBoisAnnuel;
    }

    /**
     * Sets the value of the oCefFrBoisAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefFrBoisAnnuel(Double value) {
        this.oCefFrBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsBoisAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefEcsBoisAnnuel() {
        return oCefEcsBoisAnnuel;
    }

    /**
     * Sets the value of the oCefEcsBoisAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefEcsBoisAnnuel(Double value) {
        this.oCefEcsBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefChElecAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefChElecAnnuel() {
        return oCefChElecAnnuel;
    }

    /**
     * Sets the value of the oCefChElecAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefChElecAnnuel(Double value) {
        this.oCefChElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrElecAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefFrElecAnnuel() {
        return oCefFrElecAnnuel;
    }

    /**
     * Sets the value of the oCefFrElecAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefFrElecAnnuel(Double value) {
        this.oCefFrElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsElecAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefEcsElecAnnuel() {
        return oCefEcsElecAnnuel;
    }

    /**
     * Sets the value of the oCefEcsElecAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefEcsElecAnnuel(Double value) {
        this.oCefEcsElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefChReseauChaleurAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefChReseauChaleurAnnuel() {
        return oCefChReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCefChReseauChaleurAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefChReseauChaleurAnnuel(Double value) {
        this.oCefChReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrReseauChaleurAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefFrReseauChaleurAnnuel() {
        return oCefFrReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCefFrReseauChaleurAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefFrReseauChaleurAnnuel(Double value) {
        this.oCefFrReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsReseauChaleurAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefEcsReseauChaleurAnnuel() {
        return oCefEcsReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCefEcsReseauChaleurAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefEcsReseauChaleurAnnuel(Double value) {
        this.oCefEcsReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCepReseauChaleurAnnuel property.
     * 
     */
    public double getOCepReseauChaleurAnnuel() {
        return oCepReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCepReseauChaleurAnnuel property.
     * 
     */
    public void setOCepReseauChaleurAnnuel(double value) {
        this.oCepReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazAnnuel property.
     * 
     */
    public double getOCefGazAnnuel() {
        return oCefGazAnnuel;
    }

    /**
     * Sets the value of the oCefGazAnnuel property.
     * 
     */
    public void setOCefGazAnnuel(double value) {
        this.oCefGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulAnnuel property.
     * 
     */
    public double getOCefFioulAnnuel() {
        return oCefFioulAnnuel;
    }

    /**
     * Sets the value of the oCefFioulAnnuel property.
     * 
     */
    public void setOCefFioulAnnuel(double value) {
        this.oCefFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefCharbonAnnuel property.
     * 
     */
    public double getOCefCharbonAnnuel() {
        return oCefCharbonAnnuel;
    }

    /**
     * Sets the value of the oCefCharbonAnnuel property.
     * 
     */
    public void setOCefCharbonAnnuel(double value) {
        this.oCefCharbonAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisAnnuel property.
     * 
     */
    public double getOCefBoisAnnuel() {
        return oCefBoisAnnuel;
    }

    /**
     * Sets the value of the oCefBoisAnnuel property.
     * 
     */
    public void setOCefBoisAnnuel(double value) {
        this.oCefBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecAnnuel property.
     * 
     */
    public double getOCefElecAnnuel() {
        return oCefElecAnnuel;
    }

    /**
     * Sets the value of the oCefElecAnnuel property.
     * 
     */
    public void setOCefElecAnnuel(double value) {
        this.oCefElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauChaleurAnnuel property.
     * 
     */
    public double getOCefReseauChaleurAnnuel() {
        return oCefReseauChaleurAnnuel;
    }

    /**
     * Sets the value of the oCefReseauChaleurAnnuel property.
     * 
     */
    public void setOCefReseauChaleurAnnuel(double value) {
        this.oCefReseauChaleurAnnuel = value;
    }

    /**
     * Gets the value of the obEcsAnnuel property.
     * 
     */
    public double getOBEcsAnnuel() {
        return obEcsAnnuel;
    }

    /**
     * Sets the value of the obEcsAnnuel property.
     * 
     */
    public void setOBEcsAnnuel(double value) {
        this.obEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCepChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepChMois() {
        return oCepChMois;
    }

    /**
     * Sets the value of the oCepChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepChMois = value;
    }

    /**
     * Gets the value of the oCepFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepFrMois() {
        return oCepFrMois;
    }

    /**
     * Sets the value of the oCepFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepFrMois = value;
    }

    /**
     * Gets the value of the oCepEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepEcsMois() {
        return oCepEcsMois;
    }

    /**
     * Sets the value of the oCepEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepEcsMois = value;
    }

    /**
     * Gets the value of the oCepEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepEclMois() {
        return oCepEclMois;
    }

    /**
     * Sets the value of the oCepEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepEclMois = value;
    }

    /**
     * Gets the value of the oCepAuxVentilateurMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepAuxVentilateurMois() {
        return oCepAuxVentilateurMois;
    }

    /**
     * Sets the value of the oCepAuxVentilateurMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepAuxVentilateurMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepAuxVentilateurMois = value;
    }

    /**
     * Gets the value of the oCepAuxDistributionMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepAuxDistributionMois() {
        return oCepAuxDistributionMois;
    }

    /**
     * Sets the value of the oCepAuxDistributionMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepAuxDistributionMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepAuxDistributionMois = value;
    }

    /**
     * Gets the value of the obEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOBEcsMois() {
        return obEcsMois;
    }

    /**
     * Sets the value of the obEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOBEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEcsMois = value;
    }

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the oMctype property.
     * 
     */
    public double getOMctype() {
        return oMctype;
    }

    /**
     * Sets the value of the oMctype property.
     * 
     */
    public void setOMctype(double value) {
        this.oMctype = value;
    }

    /**
     * Gets the value of the oMcgeo property.
     * 
     */
    public double getOMcgeo() {
        return oMcgeo;
    }

    /**
     * Sets the value of the oMcgeo property.
     * 
     */
    public void setOMcgeo(double value) {
        this.oMcgeo = value;
    }

    /**
     * Gets the value of the oMcalt property.
     * 
     */
    public double getOMcalt() {
        return oMcalt;
    }

    /**
     * Sets the value of the oMcalt property.
     * 
     */
    public void setOMcalt(double value) {
        this.oMcalt = value;
    }

    /**
     * Gets the value of the oMcsurf property.
     * 
     */
    public double getOMcsurf() {
        return oMcsurf;
    }

    /**
     * Sets the value of the oMcsurf property.
     * 
     */
    public void setOMcsurf(double value) {
        this.oMcsurf = value;
    }

    /**
     * Gets the value of the oMcges property.
     * 
     */
    public double getOMcges() {
        return oMcges;
    }

    /**
     * Sets the value of the oMcges property.
     * 
     */
    public void setOMcges(double value) {
        this.oMcges = value;
    }

    /**
     * Gets the value of the oMcbilanBEPOS1 property.
     * 
     */
    public double getOMcbilanBEPOS1() {
        return oMcbilanBEPOS1;
    }

    /**
     * Sets the value of the oMcbilanBEPOS1 property.
     * 
     */
    public void setOMcbilanBEPOS1(double value) {
        this.oMcbilanBEPOS1 = value;
    }

    /**
     * Gets the value of the oMcbilanBEPOS2 property.
     * 
     */
    public double getOMcbilanBEPOS2() {
        return oMcbilanBEPOS2;
    }

    /**
     * Sets the value of the oMcbilanBEPOS2 property.
     * 
     */
    public void setOMcbilanBEPOS2(double value) {
        this.oMcbilanBEPOS2 = value;
    }

    /**
     * Gets the value of the oMcbilanBEPOS3 property.
     * 
     */
    public double getOMcbilanBEPOS3() {
        return oMcbilanBEPOS3;
    }

    /**
     * Sets the value of the oMcbilanBEPOS3 property.
     * 
     */
    public void setOMcbilanBEPOS3(double value) {
        this.oMcbilanBEPOS3 = value;
    }

    /**
     * Gets the value of the oProdRef property.
     * 
     */
    public double getOProdRef() {
        return oProdRef;
    }

    /**
     * Sets the value of the oProdRef property.
     * 
     */
    public void setOProdRef(double value) {
        this.oProdRef = value;
    }

}
