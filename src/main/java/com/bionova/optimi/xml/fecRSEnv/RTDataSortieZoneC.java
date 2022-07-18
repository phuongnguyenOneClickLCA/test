
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Zone_C complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Zone_C">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Usage" type="{}RT_Usage"/>
 *         &lt;element name="O_Cef_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_spe_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Aepenr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaq_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqpoel_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqpoel_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqpoel_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchpoel_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchpoel_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchpoel_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranpoel_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranpoel_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranpoel_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_auxv_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_auxs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_AUE_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_chaleur_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_chaleur_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_chaleur_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_aux_ventilateur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_aux_distribution_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_us_mob_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_us_immob_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_AUE_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_aux_ventilateur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_aux_distribution_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_us_mob_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_us_immob_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
 *         &lt;element name="O_Cep_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_charbon_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_chaleur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_charbon_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_charbon_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_charbon_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgran_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgran_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgran_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuch_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuch_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuch_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaq_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaq_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_E_Sol_zone" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_E_ef_aux_zone" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_aux_ventilateur_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_aux_distribution_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_E_ef_PV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_E_ep_PV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Groupe_C_Collection" type="{}ArrayOfRT_Data_Sortie_Groupe_C" minOccurs="0"/>
 *         &lt;element name="Sortie_Ventilation_Mecanique_Collection" type="{}ArrayOfRT_Data_Sortie_Ventilation_Mecanique" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Zone_C", propOrder = {

})
public class RTDataSortieZoneC {

    @XmlElement(name = "O_Shon_RT")
    protected double oShonRT;
    @XmlElement(name = "O_SHAB")
    protected double oshab;
    @XmlElement(name = "O_SURT")
    protected double osurt;
    @XmlElement(name = "Usage", required = true)
    protected String usage;
    @XmlElement(name = "O_Cef_annuel")
    protected double oCefAnnuel;
    @XmlElement(name = "O_Cep_Max")
    protected double oCepMax;
    @XmlElement(name = "O_BilanBEPOS_max_1")
    protected double oBilanBEPOSMax1;
    @XmlElement(name = "O_BilanBEPOS_max_2")
    protected double oBilanBEPOSMax2;
    @XmlElement(name = "O_BilanBEPOS_max_3")
    protected double oBilanBEPOSMax3;
    @XmlElement(name = "O_BilanBEPOS_max_4")
    protected double oBilanBEPOSMax4;
    @XmlElement(name = "O_Cep_annuel")
    protected double oCepAnnuel;
    @XmlElement(name = "O_Cep_spe_annuel")
    protected double oCepSpeAnnuel;
    @XmlElement(name = "O_Aepenr")
    protected double oAepenr;
    @XmlElement(name = "O_Cef_boisplaq_imp_ecs_annuel")
    protected double oCefBoisplaqImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisplaqpoel_imp_ch_annuel")
    protected double oCefBoisplaqpoelImpChAnnuel;
    @XmlElement(name = "O_Cef_boisplaqpoel_imp_fr_annuel")
    protected double oCefBoisplaqpoelImpFrAnnuel;
    @XmlElement(name = "O_Cef_boisplaqpoel_imp_ecs_annuel")
    protected double oCefBoisplaqpoelImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisbuchpoel_imp_ch_annuel")
    protected double oCefBoisbuchpoelImpChAnnuel;
    @XmlElement(name = "O_Cef_boisbuchpoel_imp_fr_annuel")
    protected double oCefBoisbuchpoelImpFrAnnuel;
    @XmlElement(name = "O_Cef_boisbuchpoel_imp_ecs_annuel")
    protected double oCefBoisbuchpoelImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisgranpoel_imp_ch_annuel")
    protected double oCefBoisgranpoelImpChAnnuel;
    @XmlElement(name = "O_Cef_boisgranpoel_imp_fr_annuel")
    protected double oCefBoisgranpoelImpFrAnnuel;
    @XmlElement(name = "O_Cef_boisgranpoel_imp_ecs_annuel")
    protected double oCefBoisgranpoelImpEcsAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_ch_annuel")
    protected double oCefElecImpChAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_fr_annuel")
    protected double oCefElecImpFrAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_ecs_annuel")
    protected double oCefElecImpEcsAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_ecl_annuel")
    protected double oCefElecImpEclAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_auxv_annuel")
    protected double oCefElecImpAuxvAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_auxs_annuel")
    protected double oCefElecImpAuxsAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_AUE_annuel")
    protected double oCefElecImpAUEAnnuel;
    @XmlElement(name = "O_Cef_reseau_chaleur_imp_ch_annuel")
    protected double oCefReseauChaleurImpChAnnuel;
    @XmlElement(name = "O_Cef_reseau_chaleur_imp_fr_annuel")
    protected double oCefReseauChaleurImpFrAnnuel;
    @XmlElement(name = "O_Cef_reseau_chaleur_imp_ecs_annuel")
    protected double oCefReseauChaleurImpEcsAnnuel;
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
    @XmlElement(name = "O_Cef_us_mob_annuel")
    protected double oCefUsMobAnnuel;
    @XmlElement(name = "O_Cef_us_immob_annuel")
    protected double oCefUsImmobAnnuel;
    @XmlElement(name = "O_Cef_AUE_annuel")
    protected double oCefAUEAnnuel;
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
    @XmlElement(name = "O_Cep_us_mob_annuel")
    protected double oCepUsMobAnnuel;
    @XmlElement(name = "O_Cep_us_immob_annuel")
    protected double oCepUsImmobAnnuel;
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
    @XmlElement(name = "O_Cef_gaz_imp_ch_annuel")
    protected double oCefGazImpChAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_fr_annuel")
    protected double oCefGazImpFrAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_ecs_annuel")
    protected double oCefGazImpEcsAnnuel;
    @XmlElement(name = "O_Cef_fioul_imp_ch_annuel")
    protected double oCefFioulImpChAnnuel;
    @XmlElement(name = "O_Cef_fioul_imp_fr_annuel")
    protected double oCefFioulImpFrAnnuel;
    @XmlElement(name = "O_Cef_fioul_imp_ecs_annuel")
    protected double oCefFioulImpEcsAnnuel;
    @XmlElement(name = "O_Cef_charbon_imp_ch_annuel")
    protected double oCefCharbonImpChAnnuel;
    @XmlElement(name = "O_Cef_charbon_imp_fr_annuel")
    protected double oCefCharbonImpFrAnnuel;
    @XmlElement(name = "O_Cef_charbon_imp_ecs_annuel")
    protected double oCefCharbonImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisgran_imp_ch_annuel")
    protected double oCefBoisgranImpChAnnuel;
    @XmlElement(name = "O_Cef_boisgran_imp_fr_annuel")
    protected double oCefBoisgranImpFrAnnuel;
    @XmlElement(name = "O_Cef_boisgran_imp_ecs_annuel")
    protected double oCefBoisgranImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisbuch_imp_ch_annuel")
    protected double oCefBoisbuchImpChAnnuel;
    @XmlElement(name = "O_Cef_boisbuch_imp_fr_annuel")
    protected double oCefBoisbuchImpFrAnnuel;
    @XmlElement(name = "O_Cef_boisbuch_imp_ecs_annuel")
    protected double oCefBoisbuchImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisplaq_imp_ch_annuel")
    protected double oCefBoisplaqImpChAnnuel;
    @XmlElement(name = "O_Cef_boisplaq_imp_fr_annuel")
    protected double oCefBoisplaqImpFrAnnuel;
    @XmlElement(name = "O_B_Ecs_annuel")
    protected double obEcsAnnuel;
    @XmlElement(name = "O_B_Ch_annuel")
    protected Double obChAnnuel;
    @XmlElement(name = "O_B_Fr_annuel")
    protected Double obFrAnnuel;
    @XmlElement(name = "O_B_Ecl_annuel")
    protected Double obEclAnnuel;
    @XmlElement(name = "O_E_Sol_zone")
    protected double oeSolZone;
    @XmlElement(name = "O_E_ef_aux_zone")
    protected double oeEfAuxZone;
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
    @XmlElement(name = "O_B_Ch_mois")
    protected ArrayOfRTDataSortieMensuelle obChMois;
    @XmlElement(name = "O_B_Fr_mois")
    protected ArrayOfRTDataSortieMensuelle obFrMois;
    @XmlElement(name = "O_B_Ecl_mois")
    protected ArrayOfRTDataSortieMensuelle obEclMois;
    @XmlElement(name = "O_E_ef_PV")
    protected double oeEfPV;
    @XmlElement(name = "O_E_ep_PV")
    protected double oeEpPV;
    @XmlElement(name = "Sortie_Groupe_C_Collection")
    protected ArrayOfRTDataSortieGroupeC sortieGroupeCCollection;
    @XmlElement(name = "Sortie_Ventilation_Mecanique_Collection")
    protected ArrayOfRTDataSortieVentilationMecanique sortieVentilationMecaniqueCollection;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsage(String value) {
        this.usage = value;
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
     * Gets the value of the oCepSpeAnnuel property.
     * 
     */
    public double getOCepSpeAnnuel() {
        return oCepSpeAnnuel;
    }

    /**
     * Sets the value of the oCepSpeAnnuel property.
     * 
     */
    public void setOCepSpeAnnuel(double value) {
        this.oCepSpeAnnuel = value;
    }

    /**
     * Gets the value of the oAepenr property.
     * 
     */
    public double getOAepenr() {
        return oAepenr;
    }

    /**
     * Sets the value of the oAepenr property.
     * 
     */
    public void setOAepenr(double value) {
        this.oAepenr = value;
    }

    /**
     * Gets the value of the oCefBoisplaqImpEcsAnnuel property.
     * 
     */
    public double getOCefBoisplaqImpEcsAnnuel() {
        return oCefBoisplaqImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqImpEcsAnnuel property.
     * 
     */
    public void setOCefBoisplaqImpEcsAnnuel(double value) {
        this.oCefBoisplaqImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqpoelImpChAnnuel property.
     * 
     */
    public double getOCefBoisplaqpoelImpChAnnuel() {
        return oCefBoisplaqpoelImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqpoelImpChAnnuel property.
     * 
     */
    public void setOCefBoisplaqpoelImpChAnnuel(double value) {
        this.oCefBoisplaqpoelImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqpoelImpFrAnnuel property.
     * 
     */
    public double getOCefBoisplaqpoelImpFrAnnuel() {
        return oCefBoisplaqpoelImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqpoelImpFrAnnuel property.
     * 
     */
    public void setOCefBoisplaqpoelImpFrAnnuel(double value) {
        this.oCefBoisplaqpoelImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqpoelImpEcsAnnuel property.
     * 
     */
    public double getOCefBoisplaqpoelImpEcsAnnuel() {
        return oCefBoisplaqpoelImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqpoelImpEcsAnnuel property.
     * 
     */
    public void setOCefBoisplaqpoelImpEcsAnnuel(double value) {
        this.oCefBoisplaqpoelImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchpoelImpChAnnuel property.
     * 
     */
    public double getOCefBoisbuchpoelImpChAnnuel() {
        return oCefBoisbuchpoelImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchpoelImpChAnnuel property.
     * 
     */
    public void setOCefBoisbuchpoelImpChAnnuel(double value) {
        this.oCefBoisbuchpoelImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchpoelImpFrAnnuel property.
     * 
     */
    public double getOCefBoisbuchpoelImpFrAnnuel() {
        return oCefBoisbuchpoelImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchpoelImpFrAnnuel property.
     * 
     */
    public void setOCefBoisbuchpoelImpFrAnnuel(double value) {
        this.oCefBoisbuchpoelImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchpoelImpEcsAnnuel property.
     * 
     */
    public double getOCefBoisbuchpoelImpEcsAnnuel() {
        return oCefBoisbuchpoelImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchpoelImpEcsAnnuel property.
     * 
     */
    public void setOCefBoisbuchpoelImpEcsAnnuel(double value) {
        this.oCefBoisbuchpoelImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranpoelImpChAnnuel property.
     * 
     */
    public double getOCefBoisgranpoelImpChAnnuel() {
        return oCefBoisgranpoelImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranpoelImpChAnnuel property.
     * 
     */
    public void setOCefBoisgranpoelImpChAnnuel(double value) {
        this.oCefBoisgranpoelImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranpoelImpFrAnnuel property.
     * 
     */
    public double getOCefBoisgranpoelImpFrAnnuel() {
        return oCefBoisgranpoelImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranpoelImpFrAnnuel property.
     * 
     */
    public void setOCefBoisgranpoelImpFrAnnuel(double value) {
        this.oCefBoisgranpoelImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranpoelImpEcsAnnuel property.
     * 
     */
    public double getOCefBoisgranpoelImpEcsAnnuel() {
        return oCefBoisgranpoelImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranpoelImpEcsAnnuel property.
     * 
     */
    public void setOCefBoisgranpoelImpEcsAnnuel(double value) {
        this.oCefBoisgranpoelImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpChAnnuel property.
     * 
     */
    public double getOCefElecImpChAnnuel() {
        return oCefElecImpChAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpChAnnuel property.
     * 
     */
    public void setOCefElecImpChAnnuel(double value) {
        this.oCefElecImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpFrAnnuel property.
     * 
     */
    public double getOCefElecImpFrAnnuel() {
        return oCefElecImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpFrAnnuel property.
     * 
     */
    public void setOCefElecImpFrAnnuel(double value) {
        this.oCefElecImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpEcsAnnuel property.
     * 
     */
    public double getOCefElecImpEcsAnnuel() {
        return oCefElecImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpEcsAnnuel property.
     * 
     */
    public void setOCefElecImpEcsAnnuel(double value) {
        this.oCefElecImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpEclAnnuel property.
     * 
     */
    public double getOCefElecImpEclAnnuel() {
        return oCefElecImpEclAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpEclAnnuel property.
     * 
     */
    public void setOCefElecImpEclAnnuel(double value) {
        this.oCefElecImpEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpAuxvAnnuel property.
     * 
     */
    public double getOCefElecImpAuxvAnnuel() {
        return oCefElecImpAuxvAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpAuxvAnnuel property.
     * 
     */
    public void setOCefElecImpAuxvAnnuel(double value) {
        this.oCefElecImpAuxvAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpAuxsAnnuel property.
     * 
     */
    public double getOCefElecImpAuxsAnnuel() {
        return oCefElecImpAuxsAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpAuxsAnnuel property.
     * 
     */
    public void setOCefElecImpAuxsAnnuel(double value) {
        this.oCefElecImpAuxsAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpAUEAnnuel property.
     * 
     */
    public double getOCefElecImpAUEAnnuel() {
        return oCefElecImpAUEAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpAUEAnnuel property.
     * 
     */
    public void setOCefElecImpAUEAnnuel(double value) {
        this.oCefElecImpAUEAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauChaleurImpChAnnuel property.
     * 
     */
    public double getOCefReseauChaleurImpChAnnuel() {
        return oCefReseauChaleurImpChAnnuel;
    }

    /**
     * Sets the value of the oCefReseauChaleurImpChAnnuel property.
     * 
     */
    public void setOCefReseauChaleurImpChAnnuel(double value) {
        this.oCefReseauChaleurImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauChaleurImpFrAnnuel property.
     * 
     */
    public double getOCefReseauChaleurImpFrAnnuel() {
        return oCefReseauChaleurImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefReseauChaleurImpFrAnnuel property.
     * 
     */
    public void setOCefReseauChaleurImpFrAnnuel(double value) {
        this.oCefReseauChaleurImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauChaleurImpEcsAnnuel property.
     * 
     */
    public double getOCefReseauChaleurImpEcsAnnuel() {
        return oCefReseauChaleurImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefReseauChaleurImpEcsAnnuel property.
     * 
     */
    public void setOCefReseauChaleurImpEcsAnnuel(double value) {
        this.oCefReseauChaleurImpEcsAnnuel = value;
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
     * Gets the value of the oCefUsMobAnnuel property.
     * 
     */
    public double getOCefUsMobAnnuel() {
        return oCefUsMobAnnuel;
    }

    /**
     * Sets the value of the oCefUsMobAnnuel property.
     * 
     */
    public void setOCefUsMobAnnuel(double value) {
        this.oCefUsMobAnnuel = value;
    }

    /**
     * Gets the value of the oCefUsImmobAnnuel property.
     * 
     */
    public double getOCefUsImmobAnnuel() {
        return oCefUsImmobAnnuel;
    }

    /**
     * Sets the value of the oCefUsImmobAnnuel property.
     * 
     */
    public void setOCefUsImmobAnnuel(double value) {
        this.oCefUsImmobAnnuel = value;
    }

    /**
     * Gets the value of the oCefAUEAnnuel property.
     * 
     */
    public double getOCefAUEAnnuel() {
        return oCefAUEAnnuel;
    }

    /**
     * Sets the value of the oCefAUEAnnuel property.
     * 
     */
    public void setOCefAUEAnnuel(double value) {
        this.oCefAUEAnnuel = value;
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
     * Gets the value of the oCepUsMobAnnuel property.
     * 
     */
    public double getOCepUsMobAnnuel() {
        return oCepUsMobAnnuel;
    }

    /**
     * Sets the value of the oCepUsMobAnnuel property.
     * 
     */
    public void setOCepUsMobAnnuel(double value) {
        this.oCepUsMobAnnuel = value;
    }

    /**
     * Gets the value of the oCepUsImmobAnnuel property.
     * 
     */
    public double getOCepUsImmobAnnuel() {
        return oCepUsImmobAnnuel;
    }

    /**
     * Sets the value of the oCepUsImmobAnnuel property.
     * 
     */
    public void setOCepUsImmobAnnuel(double value) {
        this.oCepUsImmobAnnuel = value;
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
     * Gets the value of the oCefGazImpChAnnuel property.
     * 
     */
    public double getOCefGazImpChAnnuel() {
        return oCefGazImpChAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpChAnnuel property.
     * 
     */
    public void setOCefGazImpChAnnuel(double value) {
        this.oCefGazImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpFrAnnuel property.
     * 
     */
    public double getOCefGazImpFrAnnuel() {
        return oCefGazImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpFrAnnuel property.
     * 
     */
    public void setOCefGazImpFrAnnuel(double value) {
        this.oCefGazImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpEcsAnnuel property.
     * 
     */
    public double getOCefGazImpEcsAnnuel() {
        return oCefGazImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpEcsAnnuel property.
     * 
     */
    public void setOCefGazImpEcsAnnuel(double value) {
        this.oCefGazImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulImpChAnnuel property.
     * 
     */
    public double getOCefFioulImpChAnnuel() {
        return oCefFioulImpChAnnuel;
    }

    /**
     * Sets the value of the oCefFioulImpChAnnuel property.
     * 
     */
    public void setOCefFioulImpChAnnuel(double value) {
        this.oCefFioulImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulImpFrAnnuel property.
     * 
     */
    public double getOCefFioulImpFrAnnuel() {
        return oCefFioulImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefFioulImpFrAnnuel property.
     * 
     */
    public void setOCefFioulImpFrAnnuel(double value) {
        this.oCefFioulImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulImpEcsAnnuel property.
     * 
     */
    public double getOCefFioulImpEcsAnnuel() {
        return oCefFioulImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefFioulImpEcsAnnuel property.
     * 
     */
    public void setOCefFioulImpEcsAnnuel(double value) {
        this.oCefFioulImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefCharbonImpChAnnuel property.
     * 
     */
    public double getOCefCharbonImpChAnnuel() {
        return oCefCharbonImpChAnnuel;
    }

    /**
     * Sets the value of the oCefCharbonImpChAnnuel property.
     * 
     */
    public void setOCefCharbonImpChAnnuel(double value) {
        this.oCefCharbonImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefCharbonImpFrAnnuel property.
     * 
     */
    public double getOCefCharbonImpFrAnnuel() {
        return oCefCharbonImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefCharbonImpFrAnnuel property.
     * 
     */
    public void setOCefCharbonImpFrAnnuel(double value) {
        this.oCefCharbonImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefCharbonImpEcsAnnuel property.
     * 
     */
    public double getOCefCharbonImpEcsAnnuel() {
        return oCefCharbonImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefCharbonImpEcsAnnuel property.
     * 
     */
    public void setOCefCharbonImpEcsAnnuel(double value) {
        this.oCefCharbonImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranImpChAnnuel property.
     * 
     */
    public double getOCefBoisgranImpChAnnuel() {
        return oCefBoisgranImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranImpChAnnuel property.
     * 
     */
    public void setOCefBoisgranImpChAnnuel(double value) {
        this.oCefBoisgranImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranImpFrAnnuel property.
     * 
     */
    public double getOCefBoisgranImpFrAnnuel() {
        return oCefBoisgranImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranImpFrAnnuel property.
     * 
     */
    public void setOCefBoisgranImpFrAnnuel(double value) {
        this.oCefBoisgranImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranImpEcsAnnuel property.
     * 
     */
    public double getOCefBoisgranImpEcsAnnuel() {
        return oCefBoisgranImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranImpEcsAnnuel property.
     * 
     */
    public void setOCefBoisgranImpEcsAnnuel(double value) {
        this.oCefBoisgranImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchImpChAnnuel property.
     * 
     */
    public double getOCefBoisbuchImpChAnnuel() {
        return oCefBoisbuchImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchImpChAnnuel property.
     * 
     */
    public void setOCefBoisbuchImpChAnnuel(double value) {
        this.oCefBoisbuchImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchImpFrAnnuel property.
     * 
     */
    public double getOCefBoisbuchImpFrAnnuel() {
        return oCefBoisbuchImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchImpFrAnnuel property.
     * 
     */
    public void setOCefBoisbuchImpFrAnnuel(double value) {
        this.oCefBoisbuchImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchImpEcsAnnuel property.
     * 
     */
    public double getOCefBoisbuchImpEcsAnnuel() {
        return oCefBoisbuchImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchImpEcsAnnuel property.
     * 
     */
    public void setOCefBoisbuchImpEcsAnnuel(double value) {
        this.oCefBoisbuchImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqImpChAnnuel property.
     * 
     */
    public double getOCefBoisplaqImpChAnnuel() {
        return oCefBoisplaqImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqImpChAnnuel property.
     * 
     */
    public void setOCefBoisplaqImpChAnnuel(double value) {
        this.oCefBoisplaqImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqImpFrAnnuel property.
     * 
     */
    public double getOCefBoisplaqImpFrAnnuel() {
        return oCefBoisplaqImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqImpFrAnnuel property.
     * 
     */
    public void setOCefBoisplaqImpFrAnnuel(double value) {
        this.oCefBoisplaqImpFrAnnuel = value;
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
     * Gets the value of the obChAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOBChAnnuel() {
        return obChAnnuel;
    }

    /**
     * Sets the value of the obChAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOBChAnnuel(Double value) {
        this.obChAnnuel = value;
    }

    /**
     * Gets the value of the obFrAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOBFrAnnuel() {
        return obFrAnnuel;
    }

    /**
     * Sets the value of the obFrAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOBFrAnnuel(Double value) {
        this.obFrAnnuel = value;
    }

    /**
     * Gets the value of the obEclAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOBEclAnnuel() {
        return obEclAnnuel;
    }

    /**
     * Sets the value of the obEclAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOBEclAnnuel(Double value) {
        this.obEclAnnuel = value;
    }

    /**
     * Gets the value of the oeSolZone property.
     * 
     */
    public double getOESolZone() {
        return oeSolZone;
    }

    /**
     * Sets the value of the oeSolZone property.
     * 
     */
    public void setOESolZone(double value) {
        this.oeSolZone = value;
    }

    /**
     * Gets the value of the oeEfAuxZone property.
     * 
     */
    public double getOEEfAuxZone() {
        return oeEfAuxZone;
    }

    /**
     * Sets the value of the oeEfAuxZone property.
     * 
     */
    public void setOEEfAuxZone(double value) {
        this.oeEfAuxZone = value;
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
     * Gets the value of the obChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOBChMois() {
        return obChMois;
    }

    /**
     * Sets the value of the obChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOBChMois(ArrayOfRTDataSortieMensuelle value) {
        this.obChMois = value;
    }

    /**
     * Gets the value of the obFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOBFrMois() {
        return obFrMois;
    }

    /**
     * Sets the value of the obFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOBFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.obFrMois = value;
    }

    /**
     * Gets the value of the obEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOBEclMois() {
        return obEclMois;
    }

    /**
     * Sets the value of the obEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOBEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclMois = value;
    }

    /**
     * Gets the value of the oeEfPV property.
     * 
     */
    public double getOEEfPV() {
        return oeEfPV;
    }

    /**
     * Sets the value of the oeEfPV property.
     * 
     */
    public void setOEEfPV(double value) {
        this.oeEfPV = value;
    }

    /**
     * Gets the value of the oeEpPV property.
     * 
     */
    public double getOEEpPV() {
        return oeEpPV;
    }

    /**
     * Sets the value of the oeEpPV property.
     * 
     */
    public void setOEEpPV(double value) {
        this.oeEpPV = value;
    }

    /**
     * Gets the value of the sortieGroupeCCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGroupeC }
     *     
     */
    public ArrayOfRTDataSortieGroupeC getSortieGroupeCCollection() {
        return sortieGroupeCCollection;
    }

    /**
     * Sets the value of the sortieGroupeCCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGroupeC }
     *     
     */
    public void setSortieGroupeCCollection(ArrayOfRTDataSortieGroupeC value) {
        this.sortieGroupeCCollection = value;
    }

    /**
     * Gets the value of the sortieVentilationMecaniqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieVentilationMecanique }
     *     
     */
    public ArrayOfRTDataSortieVentilationMecanique getSortieVentilationMecaniqueCollection() {
        return sortieVentilationMecaniqueCollection;
    }

    /**
     * Sets the value of the sortieVentilationMecaniqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieVentilationMecanique }
     *     
     */
    public void setSortieVentilationMecaniqueCollection(ArrayOfRTDataSortieVentilationMecanique value) {
        this.sortieVentilationMecaniqueCollection = value;
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

}
