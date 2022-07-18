
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
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
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_SREF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SU" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Usage" type="{}RT_Usage"/>
 *         &lt;element name="Nocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mccat" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_nr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_nr_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_auxvent_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_auxdist_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_deplacement_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_imp_mobilier_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Eef_Prod_PV_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Eef_Prod_PV_AC_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Eef_Prod_Coge_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Eef_Prod_Coge_AC_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_TAC_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_TAC_elec_PV_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_TAC_elec_Coge_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Eef_Elec_Exportee_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_imp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_bois_imp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_imp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_bois_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_bois_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranchaud_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranchaud_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchchaud_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchchaud_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqchaud_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqchaud_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqpoel_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisplaqpoel_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchpoel_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisbuchpoel_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranpoel_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_boisgranpoel_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_auxvent_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_auxdist_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_deplacement_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_cons_mobilier_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_auxvent_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_auxdist_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_deplacement_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_imp_mobilier_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_imp_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_gaz_imp_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_gaz_imp_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_fioul_imp_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_fioul_imp_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_bois_imp_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_bois_imp_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_reseau_imp_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_reseau_imp_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_reseau_imp_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_auxvent_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_auxdist_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_deplacement_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_imp_mobilier_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_auxvent_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_auxdist_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_deplacement_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_elec_cons_mobilier_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Eef_Prod_PV_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Eef_Prod_PV_AC_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Eef_Prod_Coge_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Eef_Prod_Coge_AC_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Eef_Elec_Exportee_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_E_Sol_zone" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_E_ef_aux_zone" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Groupe_C_Collection" type="{}ArrayOfRT_Data_Sortie_Groupe_C" minOccurs="0"/>
 *         &lt;element name="Sortie_Ventilation_Mecanique_Collection" type="{}ArrayOfRT_Data_Sortie_Ventilation_Mecanique" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Zone_C", propOrder = {
    "osref",
    "oshab",
    "osu",
    "usage",
    "nocc",
    "oMccat",
    "oCefAnnuel",
    "oCepAnnuel",
    "oCepNrAnnuel",
    "oCepMax",
    "oCepNrMax",
    "oBilanBEPOSMax1",
    "oBilanBEPOSMax2",
    "oBilanBEPOSMax3",
    "oBilanBEPOSMax4",
    "oCefImpChAnnuel",
    "oCefImpFrAnnuel",
    "oCefImpEcsAnnuel",
    "oCefImpEclAnnuel",
    "oCefImpAuxventAnnuel",
    "oCefImpAuxdistAnnuel",
    "oCefImpDeplacementAnnuel",
    "oCefImpMobilierAnnuel",
    "oEefProdPVAnnuel",
    "oEefProdPVACAnnuel",
    "oEefProdCogeAnnuel",
    "oEefProdCogeACAnnuel",
    "otacElecAnnuel",
    "otacElecPVAnnuel",
    "otacElecCogeAnnuel",
    "oEefElecExporteeAnnuel",
    "oCefGazImpAnnuel",
    "oCefFioulImpAnnuel",
    "oCefBoisImpAnnuel",
    "oCefElecImpAnnuel",
    "oCefReseauImpAnnuel",
    "oCefGazImpChAnnuel",
    "oCefGazImpFrAnnuel",
    "oCefGazImpEcsAnnuel",
    "oCefFioulImpChAnnuel",
    "oCefFioulImpEcsAnnuel",
    "oCefBoisImpChAnnuel",
    "oCefBoisImpEcsAnnuel",
    "oCefBoisgranchaudImpChAnnuel",
    "oCefBoisgranchaudImpEcsAnnuel",
    "oCefBoisbuchchaudImpChAnnuel",
    "oCefBoisbuchchaudImpEcsAnnuel",
    "oCefBoisplaqchaudImpChAnnuel",
    "oCefBoisplaqchaudImpEcsAnnuel",
    "oCefBoisplaqpoelImpChAnnuel",
    "oCefBoisplaqpoelImpEcsAnnuel",
    "oCefBoisbuchpoelImpChAnnuel",
    "oCefBoisbuchpoelImpEcsAnnuel",
    "oCefBoisgranpoelImpChAnnuel",
    "oCefBoisgranpoelImpEcsAnnuel",
    "oCefReseauImpChAnnuel",
    "oCefReseauImpFrAnnuel",
    "oCefReseauImpEcsAnnuel",
    "oCefElecConsChAnnuel",
    "oCefElecConsFrAnnuel",
    "oCefElecConsEcsAnnuel",
    "oCefElecConsEclAnnuel",
    "oCefElecConsAuxventAnnuel",
    "oCefElecConsAuxdistAnnuel",
    "oCefElecConsDeplacementAnnuel",
    "oCefElecConsMobilierAnnuel",
    "oCefElecImpChAnnuel",
    "oCefElecImpFrAnnuel",
    "oCefElecImpEcsAnnuel",
    "oCefElecImpEclAnnuel",
    "oCefElecImpAuxventAnnuel",
    "oCefElecImpAuxdistAnnuel",
    "oCefElecImpDeplacementAnnuel",
    "oCefElecImpMobilierAnnuel",
    "oCefGazImpChMois",
    "oCefGazImpFrMois",
    "oCefGazImpEcsMois",
    "oCefFioulImpChMois",
    "oCefFioulImpEcsMois",
    "oCefBoisImpChMois",
    "oCefBoisImpEcsMois",
    "oCefReseauImpChMois",
    "oCefReseauImpFrMois",
    "oCefReseauImpEcsMois",
    "oCefElecImpChMois",
    "oCefElecImpFrMois",
    "oCefElecImpEcsMois",
    "oCefElecImpEclMois",
    "oCefElecImpAuxventMois",
    "oCefElecImpAuxdistMois",
    "oCefElecImpDeplacementMois",
    "oCefElecImpMobilierMois",
    "oCefElecConsChMois",
    "oCefElecConsFrMois",
    "oCefElecConsEcsMois",
    "oCefElecConsEclMois",
    "oCefElecConsAuxventMois",
    "oCefElecConsAuxdistMois",
    "oCefElecConsDeplacementMois",
    "oCefElecConsMobilierMois",
    "oEefProdPVMois",
    "oEefProdPVACMois",
    "oEefProdCogeMois",
    "oEefProdCogeACMois",
    "oEefElecExporteeMois",
    "obChAnnuel",
    "obFrAnnuel",
    "obEcsAnnuel",
    "obEcsMois",
    "obChMois",
    "obFrMois",
    "oeSolZone",
    "oeEfAuxZone",
    "sortieGroupeCCollection",
    "sortieVentilationMecaniqueCollection"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieZoneC
    extends RTDataSortieBase
{

    @XmlElement(name = "O_SREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osref;
    @XmlElement(name = "O_SHAB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oshab;
    @XmlElement(name = "O_SU")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osu;
    @XmlElement(name = "Usage", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String usage;
    @XmlElement(name = "Nocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nocc;
    @XmlElement(name = "O_Mccat")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Double oMccat;
    @XmlElement(name = "O_Cef_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefAnnuel;
    @XmlElement(name = "O_Cep_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepAnnuel;
    @XmlElement(name = "O_Cep_nr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepNrAnnuel;
    @XmlElement(name = "O_Cep_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepMax;
    @XmlElement(name = "O_Cep_nr_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepNrMax;
    @XmlElement(name = "O_BilanBEPOS_max_1")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBilanBEPOSMax1;
    @XmlElement(name = "O_BilanBEPOS_max_2")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBilanBEPOSMax2;
    @XmlElement(name = "O_BilanBEPOS_max_3")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBilanBEPOSMax3;
    @XmlElement(name = "O_BilanBEPOS_max_4")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBilanBEPOSMax4;
    @XmlElement(name = "O_Cef_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpChAnnuel;
    @XmlElement(name = "O_Cef_imp_fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpFrAnnuel;
    @XmlElement(name = "O_Cef_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpEcsAnnuel;
    @XmlElement(name = "O_Cef_imp_ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpEclAnnuel;
    @XmlElement(name = "O_Cef_imp_auxvent_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpAuxventAnnuel;
    @XmlElement(name = "O_Cef_imp_auxdist_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpAuxdistAnnuel;
    @XmlElement(name = "O_Cef_imp_deplacement_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpDeplacementAnnuel;
    @XmlElement(name = "O_Cef_imp_mobilier_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefImpMobilierAnnuel;
    @XmlElement(name = "O_Eef_Prod_PV_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEefProdPVAnnuel;
    @XmlElement(name = "O_Eef_Prod_PV_AC_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEefProdPVACAnnuel;
    @XmlElement(name = "O_Eef_Prod_Coge_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEefProdCogeAnnuel;
    @XmlElement(name = "O_Eef_Prod_Coge_AC_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEefProdCogeACAnnuel;
    @XmlElement(name = "O_TAC_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double otacElecAnnuel;
    @XmlElement(name = "O_TAC_elec_PV_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double otacElecPVAnnuel;
    @XmlElement(name = "O_TAC_elec_Coge_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double otacElecCogeAnnuel;
    @XmlElement(name = "O_Eef_Elec_Exportee_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEefElecExporteeAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefGazImpAnnuel;
    @XmlElement(name = "O_Cef_fioul_imp_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFioulImpAnnuel;
    @XmlElement(name = "O_Cef_bois_imp_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisImpAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpAnnuel;
    @XmlElement(name = "O_Cef_reseau_imp_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefReseauImpAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefGazImpChAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefGazImpFrAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefGazImpEcsAnnuel;
    @XmlElement(name = "O_Cef_fioul_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFioulImpChAnnuel;
    @XmlElement(name = "O_Cef_fioul_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFioulImpEcsAnnuel;
    @XmlElement(name = "O_Cef_bois_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisImpChAnnuel;
    @XmlElement(name = "O_Cef_bois_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisgranchaud_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisgranchaudImpChAnnuel;
    @XmlElement(name = "O_Cef_boisgranchaud_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisgranchaudImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisbuchchaud_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisbuchchaudImpChAnnuel;
    @XmlElement(name = "O_Cef_boisbuchchaud_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisbuchchaudImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisplaqchaud_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisplaqchaudImpChAnnuel;
    @XmlElement(name = "O_Cef_boisplaqchaud_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisplaqchaudImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisplaqpoel_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisplaqpoelImpChAnnuel;
    @XmlElement(name = "O_Cef_boisplaqpoel_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisplaqpoelImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisbuchpoel_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisbuchpoelImpChAnnuel;
    @XmlElement(name = "O_Cef_boisbuchpoel_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisbuchpoelImpEcsAnnuel;
    @XmlElement(name = "O_Cef_boisgranpoel_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisgranpoelImpChAnnuel;
    @XmlElement(name = "O_Cef_boisgranpoel_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisgranpoelImpEcsAnnuel;
    @XmlElement(name = "O_Cef_reseau_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefReseauImpChAnnuel;
    @XmlElement(name = "O_Cef_reseau_imp_fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefReseauImpFrAnnuel;
    @XmlElement(name = "O_Cef_reseau_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefReseauImpEcsAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsChAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsFrAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsEcsAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsEclAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_auxvent_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsAuxventAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_auxdist_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsAuxdistAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_deplacement_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsDeplacementAnnuel;
    @XmlElement(name = "O_Cef_elec_cons_mobilier_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecConsMobilierAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpChAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpFrAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpEcsAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpEclAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_auxvent_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpAuxventAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_auxdist_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpAuxdistAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_deplacement_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpDeplacementAnnuel;
    @XmlElement(name = "O_Cef_elec_imp_mobilier_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecImpMobilierAnnuel;
    @XmlElement(name = "O_Cef_gaz_imp_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefGazImpChMois;
    @XmlElement(name = "O_Cef_gaz_imp_fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefGazImpFrMois;
    @XmlElement(name = "O_Cef_gaz_imp_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefGazImpEcsMois;
    @XmlElement(name = "O_Cef_fioul_imp_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefFioulImpChMois;
    @XmlElement(name = "O_Cef_fioul_imp_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefFioulImpEcsMois;
    @XmlElement(name = "O_Cef_bois_imp_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefBoisImpChMois;
    @XmlElement(name = "O_Cef_bois_imp_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefBoisImpEcsMois;
    @XmlElement(name = "O_Cef_reseau_imp_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefReseauImpChMois;
    @XmlElement(name = "O_Cef_reseau_imp_fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefReseauImpFrMois;
    @XmlElement(name = "O_Cef_reseau_imp_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefReseauImpEcsMois;
    @XmlElement(name = "O_Cef_elec_imp_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpChMois;
    @XmlElement(name = "O_Cef_elec_imp_fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpFrMois;
    @XmlElement(name = "O_Cef_elec_imp_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpEcsMois;
    @XmlElement(name = "O_Cef_elec_imp_ecl_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpEclMois;
    @XmlElement(name = "O_Cef_elec_imp_auxvent_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpAuxventMois;
    @XmlElement(name = "O_Cef_elec_imp_auxdist_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpAuxdistMois;
    @XmlElement(name = "O_Cef_elec_imp_deplacement_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpDeplacementMois;
    @XmlElement(name = "O_Cef_elec_imp_mobilier_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecImpMobilierMois;
    @XmlElement(name = "O_Cef_elec_cons_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsChMois;
    @XmlElement(name = "O_Cef_elec_cons_fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsFrMois;
    @XmlElement(name = "O_Cef_elec_cons_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsEcsMois;
    @XmlElement(name = "O_Cef_elec_cons_ecl_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsEclMois;
    @XmlElement(name = "O_Cef_elec_cons_auxvent_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsAuxventMois;
    @XmlElement(name = "O_Cef_elec_cons_auxdist_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsAuxdistMois;
    @XmlElement(name = "O_Cef_elec_cons_deplacement_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsDeplacementMois;
    @XmlElement(name = "O_Cef_elec_cons_mobilier_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oCefElecConsMobilierMois;
    @XmlElement(name = "O_Eef_Prod_PV_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oEefProdPVMois;
    @XmlElement(name = "O_Eef_Prod_PV_AC_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oEefProdPVACMois;
    @XmlElement(name = "O_Eef_Prod_Coge_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oEefProdCogeMois;
    @XmlElement(name = "O_Eef_Prod_Coge_AC_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oEefProdCogeACMois;
    @XmlElement(name = "O_Eef_Elec_Exportee_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oEefElecExporteeMois;
    @XmlElement(name = "O_B_Ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obChAnnuel;
    @XmlElement(name = "O_B_Fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obFrAnnuel;
    @XmlElement(name = "O_B_Ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obEcsAnnuel;
    @XmlElement(name = "O_B_Ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obEcsMois;
    @XmlElement(name = "O_B_Ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obChMois;
    @XmlElement(name = "O_B_Fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obFrMois;
    @XmlElement(name = "O_E_Sol_zone")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oeSolZone;
    @XmlElement(name = "O_E_ef_aux_zone")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oeEfAuxZone;
    @XmlElement(name = "Sortie_Groupe_C_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieGroupeC sortieGroupeCCollection;
    @XmlElement(name = "Sortie_Ventilation_Mecanique_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieVentilationMecanique sortieVentilationMecaniqueCollection;

    /**
     * Gets the value of the osref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSREF() {
        return osref;
    }

    /**
     * Sets the value of the osref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSREF(double value) {
        this.osref = value;
    }

    /**
     * Gets the value of the oshab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSHAB(double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSU() {
        return osu;
    }

    /**
     * Sets the value of the osu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSU(double value) {
        this.osu = value;
    }

    /**
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUsage(String value) {
        this.usage = value;
    }

    /**
     * Gets the value of the nocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNocc() {
        return nocc;
    }

    /**
     * Sets the value of the nocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNocc(double value) {
        this.nocc = value;
    }

    /**
     * Gets the value of the oMccat property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Double getOMccat() {
        return oMccat;
    }

    /**
     * Sets the value of the oMccat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMccat(Double value) {
        this.oMccat = value;
    }

    /**
     * Gets the value of the oCefAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefAnnuel() {
        return oCefAnnuel;
    }

    /**
     * Sets the value of the oCefAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAnnuel(double value) {
        this.oCefAnnuel = value;
    }

    /**
     * Gets the value of the oCepAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCepAnnuel() {
        return oCepAnnuel;
    }

    /**
     * Sets the value of the oCepAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCepAnnuel(double value) {
        this.oCepAnnuel = value;
    }

    /**
     * Gets the value of the oCepNrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCepNrAnnuel() {
        return oCepNrAnnuel;
    }

    /**
     * Sets the value of the oCepNrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCepNrAnnuel(double value) {
        this.oCepNrAnnuel = value;
    }

    /**
     * Gets the value of the oCepMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCepMax() {
        return oCepMax;
    }

    /**
     * Sets the value of the oCepMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCepMax(double value) {
        this.oCepMax = value;
    }

    /**
     * Gets the value of the oCepNrMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCepNrMax() {
        return oCepNrMax;
    }

    /**
     * Sets the value of the oCepNrMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCepNrMax(double value) {
        this.oCepNrMax = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBilanBEPOSMax1() {
        return oBilanBEPOSMax1;
    }

    /**
     * Sets the value of the oBilanBEPOSMax1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBilanBEPOSMax1(double value) {
        this.oBilanBEPOSMax1 = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBilanBEPOSMax2() {
        return oBilanBEPOSMax2;
    }

    /**
     * Sets the value of the oBilanBEPOSMax2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBilanBEPOSMax2(double value) {
        this.oBilanBEPOSMax2 = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax3 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBilanBEPOSMax3() {
        return oBilanBEPOSMax3;
    }

    /**
     * Sets the value of the oBilanBEPOSMax3 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBilanBEPOSMax3(double value) {
        this.oBilanBEPOSMax3 = value;
    }

    /**
     * Gets the value of the oBilanBEPOSMax4 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBilanBEPOSMax4() {
        return oBilanBEPOSMax4;
    }

    /**
     * Sets the value of the oBilanBEPOSMax4 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBilanBEPOSMax4(double value) {
        this.oBilanBEPOSMax4 = value;
    }

    /**
     * Gets the value of the oCefImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpChAnnuel() {
        return oCefImpChAnnuel;
    }

    /**
     * Sets the value of the oCefImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpChAnnuel(double value) {
        this.oCefImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpFrAnnuel() {
        return oCefImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpFrAnnuel(double value) {
        this.oCefImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpEcsAnnuel() {
        return oCefImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpEcsAnnuel(double value) {
        this.oCefImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpEclAnnuel() {
        return oCefImpEclAnnuel;
    }

    /**
     * Sets the value of the oCefImpEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpEclAnnuel(double value) {
        this.oCefImpEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpAuxventAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpAuxventAnnuel() {
        return oCefImpAuxventAnnuel;
    }

    /**
     * Sets the value of the oCefImpAuxventAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpAuxventAnnuel(double value) {
        this.oCefImpAuxventAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpAuxdistAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpAuxdistAnnuel() {
        return oCefImpAuxdistAnnuel;
    }

    /**
     * Sets the value of the oCefImpAuxdistAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpAuxdistAnnuel(double value) {
        this.oCefImpAuxdistAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpDeplacementAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpDeplacementAnnuel() {
        return oCefImpDeplacementAnnuel;
    }

    /**
     * Sets the value of the oCefImpDeplacementAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpDeplacementAnnuel(double value) {
        this.oCefImpDeplacementAnnuel = value;
    }

    /**
     * Gets the value of the oCefImpMobilierAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefImpMobilierAnnuel() {
        return oCefImpMobilierAnnuel;
    }

    /**
     * Sets the value of the oCefImpMobilierAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefImpMobilierAnnuel(double value) {
        this.oCefImpMobilierAnnuel = value;
    }

    /**
     * Gets the value of the oEefProdPVAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEefProdPVAnnuel() {
        return oEefProdPVAnnuel;
    }

    /**
     * Sets the value of the oEefProdPVAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdPVAnnuel(double value) {
        this.oEefProdPVAnnuel = value;
    }

    /**
     * Gets the value of the oEefProdPVACAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEefProdPVACAnnuel() {
        return oEefProdPVACAnnuel;
    }

    /**
     * Sets the value of the oEefProdPVACAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdPVACAnnuel(double value) {
        this.oEefProdPVACAnnuel = value;
    }

    /**
     * Gets the value of the oEefProdCogeAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEefProdCogeAnnuel() {
        return oEefProdCogeAnnuel;
    }

    /**
     * Sets the value of the oEefProdCogeAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdCogeAnnuel(double value) {
        this.oEefProdCogeAnnuel = value;
    }

    /**
     * Gets the value of the oEefProdCogeACAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEefProdCogeACAnnuel() {
        return oEefProdCogeACAnnuel;
    }

    /**
     * Sets the value of the oEefProdCogeACAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdCogeACAnnuel(double value) {
        this.oEefProdCogeACAnnuel = value;
    }

    /**
     * Gets the value of the otacElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOTACElecAnnuel() {
        return otacElecAnnuel;
    }

    /**
     * Sets the value of the otacElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOTACElecAnnuel(double value) {
        this.otacElecAnnuel = value;
    }

    /**
     * Gets the value of the otacElecPVAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOTACElecPVAnnuel() {
        return otacElecPVAnnuel;
    }

    /**
     * Sets the value of the otacElecPVAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOTACElecPVAnnuel(double value) {
        this.otacElecPVAnnuel = value;
    }

    /**
     * Gets the value of the otacElecCogeAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOTACElecCogeAnnuel() {
        return otacElecCogeAnnuel;
    }

    /**
     * Sets the value of the otacElecCogeAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOTACElecCogeAnnuel(double value) {
        this.otacElecCogeAnnuel = value;
    }

    /**
     * Gets the value of the oEefElecExporteeAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEefElecExporteeAnnuel() {
        return oEefElecExporteeAnnuel;
    }

    /**
     * Sets the value of the oEefElecExporteeAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefElecExporteeAnnuel(double value) {
        this.oEefElecExporteeAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefGazImpAnnuel() {
        return oCefGazImpAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpAnnuel(double value) {
        this.oCefGazImpAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFioulImpAnnuel() {
        return oCefFioulImpAnnuel;
    }

    /**
     * Sets the value of the oCefFioulImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFioulImpAnnuel(double value) {
        this.oCefFioulImpAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisImpAnnuel() {
        return oCefBoisImpAnnuel;
    }

    /**
     * Sets the value of the oCefBoisImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisImpAnnuel(double value) {
        this.oCefBoisImpAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpAnnuel() {
        return oCefElecImpAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpAnnuel(double value) {
        this.oCefElecImpAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefReseauImpAnnuel() {
        return oCefReseauImpAnnuel;
    }

    /**
     * Sets the value of the oCefReseauImpAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpAnnuel(double value) {
        this.oCefReseauImpAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefGazImpChAnnuel() {
        return oCefGazImpChAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpChAnnuel(double value) {
        this.oCefGazImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefGazImpFrAnnuel() {
        return oCefGazImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpFrAnnuel(double value) {
        this.oCefGazImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefGazImpEcsAnnuel() {
        return oCefGazImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefGazImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpEcsAnnuel(double value) {
        this.oCefGazImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFioulImpChAnnuel() {
        return oCefFioulImpChAnnuel;
    }

    /**
     * Sets the value of the oCefFioulImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFioulImpChAnnuel(double value) {
        this.oCefFioulImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFioulImpEcsAnnuel() {
        return oCefFioulImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefFioulImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFioulImpEcsAnnuel(double value) {
        this.oCefFioulImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisImpChAnnuel() {
        return oCefBoisImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisImpChAnnuel(double value) {
        this.oCefBoisImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisImpEcsAnnuel() {
        return oCefBoisImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisImpEcsAnnuel(double value) {
        this.oCefBoisImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranchaudImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisgranchaudImpChAnnuel() {
        return oCefBoisgranchaudImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranchaudImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisgranchaudImpChAnnuel(double value) {
        this.oCefBoisgranchaudImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranchaudImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisgranchaudImpEcsAnnuel() {
        return oCefBoisgranchaudImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranchaudImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisgranchaudImpEcsAnnuel(double value) {
        this.oCefBoisgranchaudImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchchaudImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisbuchchaudImpChAnnuel() {
        return oCefBoisbuchchaudImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchchaudImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisbuchchaudImpChAnnuel(double value) {
        this.oCefBoisbuchchaudImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchchaudImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisbuchchaudImpEcsAnnuel() {
        return oCefBoisbuchchaudImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchchaudImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisbuchchaudImpEcsAnnuel(double value) {
        this.oCefBoisbuchchaudImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqchaudImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisplaqchaudImpChAnnuel() {
        return oCefBoisplaqchaudImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqchaudImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisplaqchaudImpChAnnuel(double value) {
        this.oCefBoisplaqchaudImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqchaudImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisplaqchaudImpEcsAnnuel() {
        return oCefBoisplaqchaudImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqchaudImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisplaqchaudImpEcsAnnuel(double value) {
        this.oCefBoisplaqchaudImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqpoelImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisplaqpoelImpChAnnuel() {
        return oCefBoisplaqpoelImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqpoelImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisplaqpoelImpChAnnuel(double value) {
        this.oCefBoisplaqpoelImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisplaqpoelImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisplaqpoelImpEcsAnnuel() {
        return oCefBoisplaqpoelImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisplaqpoelImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisplaqpoelImpEcsAnnuel(double value) {
        this.oCefBoisplaqpoelImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchpoelImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisbuchpoelImpChAnnuel() {
        return oCefBoisbuchpoelImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchpoelImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisbuchpoelImpChAnnuel(double value) {
        this.oCefBoisbuchpoelImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisbuchpoelImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisbuchpoelImpEcsAnnuel() {
        return oCefBoisbuchpoelImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisbuchpoelImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisbuchpoelImpEcsAnnuel(double value) {
        this.oCefBoisbuchpoelImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranpoelImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisgranpoelImpChAnnuel() {
        return oCefBoisgranpoelImpChAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranpoelImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisgranpoelImpChAnnuel(double value) {
        this.oCefBoisgranpoelImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisgranpoelImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisgranpoelImpEcsAnnuel() {
        return oCefBoisgranpoelImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefBoisgranpoelImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisgranpoelImpEcsAnnuel(double value) {
        this.oCefBoisgranpoelImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefReseauImpChAnnuel() {
        return oCefReseauImpChAnnuel;
    }

    /**
     * Sets the value of the oCefReseauImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpChAnnuel(double value) {
        this.oCefReseauImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefReseauImpFrAnnuel() {
        return oCefReseauImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefReseauImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpFrAnnuel(double value) {
        this.oCefReseauImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefReseauImpEcsAnnuel() {
        return oCefReseauImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefReseauImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpEcsAnnuel(double value) {
        this.oCefReseauImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsChAnnuel() {
        return oCefElecConsChAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsChAnnuel(double value) {
        this.oCefElecConsChAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsFrAnnuel() {
        return oCefElecConsFrAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsFrAnnuel(double value) {
        this.oCefElecConsFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsEcsAnnuel() {
        return oCefElecConsEcsAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsEcsAnnuel(double value) {
        this.oCefElecConsEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsEclAnnuel() {
        return oCefElecConsEclAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsEclAnnuel(double value) {
        this.oCefElecConsEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsAuxventAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsAuxventAnnuel() {
        return oCefElecConsAuxventAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsAuxventAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsAuxventAnnuel(double value) {
        this.oCefElecConsAuxventAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsAuxdistAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsAuxdistAnnuel() {
        return oCefElecConsAuxdistAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsAuxdistAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsAuxdistAnnuel(double value) {
        this.oCefElecConsAuxdistAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsDeplacementAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsDeplacementAnnuel() {
        return oCefElecConsDeplacementAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsDeplacementAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsDeplacementAnnuel(double value) {
        this.oCefElecConsDeplacementAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecConsMobilierAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecConsMobilierAnnuel() {
        return oCefElecConsMobilierAnnuel;
    }

    /**
     * Sets the value of the oCefElecConsMobilierAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsMobilierAnnuel(double value) {
        this.oCefElecConsMobilierAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpChAnnuel() {
        return oCefElecImpChAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpChAnnuel(double value) {
        this.oCefElecImpChAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpFrAnnuel() {
        return oCefElecImpFrAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpFrAnnuel(double value) {
        this.oCefElecImpFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpEcsAnnuel() {
        return oCefElecImpEcsAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpEcsAnnuel(double value) {
        this.oCefElecImpEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpEclAnnuel() {
        return oCefElecImpEclAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpEclAnnuel(double value) {
        this.oCefElecImpEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpAuxventAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpAuxventAnnuel() {
        return oCefElecImpAuxventAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpAuxventAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpAuxventAnnuel(double value) {
        this.oCefElecImpAuxventAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpAuxdistAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpAuxdistAnnuel() {
        return oCefElecImpAuxdistAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpAuxdistAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpAuxdistAnnuel(double value) {
        this.oCefElecImpAuxdistAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpDeplacementAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpDeplacementAnnuel() {
        return oCefElecImpDeplacementAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpDeplacementAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpDeplacementAnnuel(double value) {
        this.oCefElecImpDeplacementAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecImpMobilierAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecImpMobilierAnnuel() {
        return oCefElecImpMobilierAnnuel;
    }

    /**
     * Sets the value of the oCefElecImpMobilierAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpMobilierAnnuel(double value) {
        this.oCefElecImpMobilierAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazImpChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefGazImpChMois() {
        return oCefGazImpChMois;
    }

    /**
     * Sets the value of the oCefGazImpChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefGazImpChMois = value;
    }

    /**
     * Gets the value of the oCefGazImpFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefGazImpFrMois() {
        return oCefGazImpFrMois;
    }

    /**
     * Sets the value of the oCefGazImpFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefGazImpFrMois = value;
    }

    /**
     * Gets the value of the oCefGazImpEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefGazImpEcsMois() {
        return oCefGazImpEcsMois;
    }

    /**
     * Sets the value of the oCefGazImpEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazImpEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefGazImpEcsMois = value;
    }

    /**
     * Gets the value of the oCefFioulImpChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefFioulImpChMois() {
        return oCefFioulImpChMois;
    }

    /**
     * Sets the value of the oCefFioulImpChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFioulImpChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefFioulImpChMois = value;
    }

    /**
     * Gets the value of the oCefFioulImpEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefFioulImpEcsMois() {
        return oCefFioulImpEcsMois;
    }

    /**
     * Sets the value of the oCefFioulImpEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFioulImpEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefFioulImpEcsMois = value;
    }

    /**
     * Gets the value of the oCefBoisImpChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefBoisImpChMois() {
        return oCefBoisImpChMois;
    }

    /**
     * Sets the value of the oCefBoisImpChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisImpChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefBoisImpChMois = value;
    }

    /**
     * Gets the value of the oCefBoisImpEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefBoisImpEcsMois() {
        return oCefBoisImpEcsMois;
    }

    /**
     * Sets the value of the oCefBoisImpEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisImpEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefBoisImpEcsMois = value;
    }

    /**
     * Gets the value of the oCefReseauImpChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefReseauImpChMois() {
        return oCefReseauImpChMois;
    }

    /**
     * Sets the value of the oCefReseauImpChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefReseauImpChMois = value;
    }

    /**
     * Gets the value of the oCefReseauImpFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefReseauImpFrMois() {
        return oCefReseauImpFrMois;
    }

    /**
     * Sets the value of the oCefReseauImpFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefReseauImpFrMois = value;
    }

    /**
     * Gets the value of the oCefReseauImpEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefReseauImpEcsMois() {
        return oCefReseauImpEcsMois;
    }

    /**
     * Sets the value of the oCefReseauImpEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauImpEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefReseauImpEcsMois = value;
    }

    /**
     * Gets the value of the oCefElecImpChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpChMois() {
        return oCefElecImpChMois;
    }

    /**
     * Sets the value of the oCefElecImpChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpChMois = value;
    }

    /**
     * Gets the value of the oCefElecImpFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpFrMois() {
        return oCefElecImpFrMois;
    }

    /**
     * Sets the value of the oCefElecImpFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpFrMois = value;
    }

    /**
     * Gets the value of the oCefElecImpEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpEcsMois() {
        return oCefElecImpEcsMois;
    }

    /**
     * Sets the value of the oCefElecImpEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpEcsMois = value;
    }

    /**
     * Gets the value of the oCefElecImpEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpEclMois() {
        return oCefElecImpEclMois;
    }

    /**
     * Sets the value of the oCefElecImpEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpEclMois = value;
    }

    /**
     * Gets the value of the oCefElecImpAuxventMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpAuxventMois() {
        return oCefElecImpAuxventMois;
    }

    /**
     * Sets the value of the oCefElecImpAuxventMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpAuxventMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpAuxventMois = value;
    }

    /**
     * Gets the value of the oCefElecImpAuxdistMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpAuxdistMois() {
        return oCefElecImpAuxdistMois;
    }

    /**
     * Sets the value of the oCefElecImpAuxdistMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpAuxdistMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpAuxdistMois = value;
    }

    /**
     * Gets the value of the oCefElecImpDeplacementMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpDeplacementMois() {
        return oCefElecImpDeplacementMois;
    }

    /**
     * Sets the value of the oCefElecImpDeplacementMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpDeplacementMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpDeplacementMois = value;
    }

    /**
     * Gets the value of the oCefElecImpMobilierMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecImpMobilierMois() {
        return oCefElecImpMobilierMois;
    }

    /**
     * Sets the value of the oCefElecImpMobilierMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecImpMobilierMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecImpMobilierMois = value;
    }

    /**
     * Gets the value of the oCefElecConsChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsChMois() {
        return oCefElecConsChMois;
    }

    /**
     * Sets the value of the oCefElecConsChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsChMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsChMois = value;
    }

    /**
     * Gets the value of the oCefElecConsFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsFrMois() {
        return oCefElecConsFrMois;
    }

    /**
     * Sets the value of the oCefElecConsFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsFrMois = value;
    }

    /**
     * Gets the value of the oCefElecConsEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsEcsMois() {
        return oCefElecConsEcsMois;
    }

    /**
     * Sets the value of the oCefElecConsEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsEcsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsEcsMois = value;
    }

    /**
     * Gets the value of the oCefElecConsEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsEclMois() {
        return oCefElecConsEclMois;
    }

    /**
     * Sets the value of the oCefElecConsEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsEclMois = value;
    }

    /**
     * Gets the value of the oCefElecConsAuxventMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsAuxventMois() {
        return oCefElecConsAuxventMois;
    }

    /**
     * Sets the value of the oCefElecConsAuxventMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsAuxventMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsAuxventMois = value;
    }

    /**
     * Gets the value of the oCefElecConsAuxdistMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsAuxdistMois() {
        return oCefElecConsAuxdistMois;
    }

    /**
     * Sets the value of the oCefElecConsAuxdistMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsAuxdistMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsAuxdistMois = value;
    }

    /**
     * Gets the value of the oCefElecConsDeplacementMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsDeplacementMois() {
        return oCefElecConsDeplacementMois;
    }

    /**
     * Sets the value of the oCefElecConsDeplacementMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsDeplacementMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsDeplacementMois = value;
    }

    /**
     * Gets the value of the oCefElecConsMobilierMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOCefElecConsMobilierMois() {
        return oCefElecConsMobilierMois;
    }

    /**
     * Sets the value of the oCefElecConsMobilierMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecConsMobilierMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefElecConsMobilierMois = value;
    }

    /**
     * Gets the value of the oEefProdPVMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOEefProdPVMois() {
        return oEefProdPVMois;
    }

    /**
     * Sets the value of the oEefProdPVMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdPVMois(ArrayOfRTDataSortieMensuelle value) {
        this.oEefProdPVMois = value;
    }

    /**
     * Gets the value of the oEefProdPVACMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOEefProdPVACMois() {
        return oEefProdPVACMois;
    }

    /**
     * Sets the value of the oEefProdPVACMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdPVACMois(ArrayOfRTDataSortieMensuelle value) {
        this.oEefProdPVACMois = value;
    }

    /**
     * Gets the value of the oEefProdCogeMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOEefProdCogeMois() {
        return oEefProdCogeMois;
    }

    /**
     * Sets the value of the oEefProdCogeMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdCogeMois(ArrayOfRTDataSortieMensuelle value) {
        this.oEefProdCogeMois = value;
    }

    /**
     * Gets the value of the oEefProdCogeACMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOEefProdCogeACMois() {
        return oEefProdCogeACMois;
    }

    /**
     * Sets the value of the oEefProdCogeACMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefProdCogeACMois(ArrayOfRTDataSortieMensuelle value) {
        this.oEefProdCogeACMois = value;
    }

    /**
     * Gets the value of the oEefElecExporteeMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOEefElecExporteeMois() {
        return oEefElecExporteeMois;
    }

    /**
     * Sets the value of the oEefElecExporteeMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEefElecExporteeMois(ArrayOfRTDataSortieMensuelle value) {
        this.oEefElecExporteeMois = value;
    }

    /**
     * Gets the value of the obChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBChAnnuel() {
        return obChAnnuel;
    }

    /**
     * Sets the value of the obChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChAnnuel(double value) {
        this.obChAnnuel = value;
    }

    /**
     * Gets the value of the obFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBFrAnnuel() {
        return obFrAnnuel;
    }

    /**
     * Sets the value of the obFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrAnnuel(double value) {
        this.obFrAnnuel = value;
    }

    /**
     * Gets the value of the obEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBEcsAnnuel() {
        return obEcsAnnuel;
    }

    /**
     * Sets the value of the obEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEcsAnnuel(double value) {
        this.obEcsAnnuel = value;
    }

    /**
     * Gets the value of the obEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.obFrMois = value;
    }

    /**
     * Gets the value of the oeSolZone property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOESolZone() {
        return oeSolZone;
    }

    /**
     * Sets the value of the oeSolZone property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOESolZone(double value) {
        this.oeSolZone = value;
    }

    /**
     * Gets the value of the oeEfAuxZone property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEEfAuxZone() {
        return oeEfAuxZone;
    }

    /**
     * Sets the value of the oeEfAuxZone property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEEfAuxZone(double value) {
        this.oeEfAuxZone = value;
    }

    /**
     * Gets the value of the sortieGroupeCCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGroupeC }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieVentilationMecaniqueCollection(ArrayOfRTDataSortieVentilationMecanique value) {
        this.sortieVentilationMecaniqueCollection = value;
    }

}
