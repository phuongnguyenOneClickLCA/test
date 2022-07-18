
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
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
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_SREF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SU" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_nr_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcgeo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mccombles" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcsurf_moy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mcsurf_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mccat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_BilanBEPOS_max_4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_McbilanBEPOS_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_McbilanBEPOS_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_McbilanBEPOS_3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_ProdRef" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_aux_ventilateur_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_aux_distribution_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fr_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_reseau_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fr_reseau_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_reseau_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fr_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecs_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ecl_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_auxv_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_auxs_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_gaz_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_fioul_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_bois_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_reseau_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecs_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cef_ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle1" minOccurs="0"/>
 *         &lt;element name="O_Cef_fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle1" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle1" minOccurs="0"/>
 *         &lt;element name="O_Cef_ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle1" minOccurs="0"/>
 *         &lt;element name="O_Cef_aux_ventilateur_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle1" minOccurs="0"/>
 *         &lt;element name="O_Cef_aux_distribution_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle1" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecs_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_C", propOrder = {
    "osref",
    "oshab",
    "osu",
    "oCefAnnuel",
    "oCepAnnuel",
    "oCepMax",
    "oCepNrMax",
    "oMcgeo",
    "oMccombles",
    "oMcsurfMoy",
    "oMcsurfTot",
    "oMccat",
    "oBilanBEPOSMax1",
    "oBilanBEPOSMax2",
    "oBilanBEPOSMax3",
    "oBilanBEPOSMax4",
    "oMcbilanBEPOS1",
    "oMcbilanBEPOS2",
    "oMcbilanBEPOS3",
    "oProdRef",
    "oCefChAnnuel",
    "oCefFrAnnuel",
    "oCefEcsAnnuel",
    "oCefEclAnnuel",
    "oCefAuxVentilateurAnnuel",
    "oCefAuxDistributionAnnuel",
    "oCefChGazAnnuel",
    "oCefFrGazAnnuel",
    "oCefEcsGazAnnuel",
    "oCefChFioulAnnuel",
    "oCefEcsFioulAnnuel",
    "oCefChBoisAnnuel",
    "oCefEcsBoisAnnuel",
    "oCefChReseauAnnuel",
    "oCefFrReseauAnnuel",
    "oCefEcsReseauAnnuel",
    "oCefChElecAnnuel",
    "oCefFrElecAnnuel",
    "oCefEcsElecAnnuel",
    "oCefEclElecAnnuel",
    "oCefAuxvElecAnnuel",
    "oCefAuxsElecAnnuel",
    "oCefGazAnnuel",
    "oCefFioulAnnuel",
    "oCefBoisAnnuel",
    "oCefElecAnnuel",
    "oCefReseauAnnuel",
    "obEcsAnnuel",
    "obChAnnuel",
    "obFrAnnuel",
    "obEclAnnuel",
    "oCefChMois",
    "oCefFrMois",
    "oCefEcsMois",
    "oCefEclMois",
    "oCefAuxVentilateurMois",
    "oCefAuxDistributionMois",
    "obChMois",
    "obFrMois",
    "obEcsMois",
    "obEclMois"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieGroupeC
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
    @XmlElement(name = "O_Cef_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefAnnuel;
    @XmlElement(name = "O_Cep_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepAnnuel;
    @XmlElement(name = "O_Cep_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepMax;
    @XmlElement(name = "O_Cep_nr_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCepNrMax;
    @XmlElement(name = "O_Mcgeo")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMcgeo;
    @XmlElement(name = "O_Mccombles")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMccombles;
    @XmlElement(name = "O_Mcsurf_moy")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMcsurfMoy;
    @XmlElement(name = "O_Mcsurf_tot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMcsurfTot;
    @XmlElement(name = "O_Mccat")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMccat;
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
    @XmlElement(name = "O_McbilanBEPOS_1")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMcbilanBEPOS1;
    @XmlElement(name = "O_McbilanBEPOS_2")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMcbilanBEPOS2;
    @XmlElement(name = "O_McbilanBEPOS_3")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMcbilanBEPOS3;
    @XmlElement(name = "O_ProdRef")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oProdRef;
    @XmlElement(name = "O_Cef_ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefChAnnuel;
    @XmlElement(name = "O_Cef_fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFrAnnuel;
    @XmlElement(name = "O_Cef_ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEcsAnnuel;
    @XmlElement(name = "O_Cef_ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEclAnnuel;
    @XmlElement(name = "O_Cef_aux_ventilateur_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefAuxVentilateurAnnuel;
    @XmlElement(name = "O_Cef_aux_distribution_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefAuxDistributionAnnuel;
    @XmlElement(name = "O_Cef_ch_gaz_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefChGazAnnuel;
    @XmlElement(name = "O_Cef_fr_gaz_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFrGazAnnuel;
    @XmlElement(name = "O_Cef_ecs_gaz_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEcsGazAnnuel;
    @XmlElement(name = "O_Cef_ch_fioul_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefChFioulAnnuel;
    @XmlElement(name = "O_Cef_ecs_fioul_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEcsFioulAnnuel;
    @XmlElement(name = "O_Cef_ch_bois_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefChBoisAnnuel;
    @XmlElement(name = "O_Cef_ecs_bois_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEcsBoisAnnuel;
    @XmlElement(name = "O_Cef_ch_reseau_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefChReseauAnnuel;
    @XmlElement(name = "O_Cef_fr_reseau_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFrReseauAnnuel;
    @XmlElement(name = "O_Cef_ecs_reseau_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEcsReseauAnnuel;
    @XmlElement(name = "O_Cef_ch_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefChElecAnnuel;
    @XmlElement(name = "O_Cef_fr_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFrElecAnnuel;
    @XmlElement(name = "O_Cef_ecs_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEcsElecAnnuel;
    @XmlElement(name = "O_Cef_ecl_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefEclElecAnnuel;
    @XmlElement(name = "O_Cef_auxv_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefAuxvElecAnnuel;
    @XmlElement(name = "O_Cef_auxs_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefAuxsElecAnnuel;
    @XmlElement(name = "O_Cef_gaz_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefGazAnnuel;
    @XmlElement(name = "O_Cef_fioul_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefFioulAnnuel;
    @XmlElement(name = "O_Cef_bois_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefBoisAnnuel;
    @XmlElement(name = "O_Cef_elec_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefElecAnnuel;
    @XmlElement(name = "O_Cef_reseau_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oCefReseauAnnuel;
    @XmlElement(name = "O_B_Ecs_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obEcsAnnuel;
    @XmlElement(name = "O_B_Ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obChAnnuel;
    @XmlElement(name = "O_B_Fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obFrAnnuel;
    @XmlElement(name = "O_B_Ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obEclAnnuel;
    @XmlElement(name = "O_Cef_ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle1 oCefChMois;
    @XmlElement(name = "O_Cef_fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle1 oCefFrMois;
    @XmlElement(name = "O_Cef_ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle1 oCefEcsMois;
    @XmlElement(name = "O_Cef_ecl_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle1 oCefEclMois;
    @XmlElement(name = "O_Cef_aux_ventilateur_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle1 oCefAuxVentilateurMois;
    @XmlElement(name = "O_Cef_aux_distribution_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle1 oCefAuxDistributionMois;
    @XmlElement(name = "O_B_Ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obChMois;
    @XmlElement(name = "O_B_Fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obFrMois;
    @XmlElement(name = "O_B_Ecs_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obEcsMois;
    @XmlElement(name = "O_B_Ecl_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obEclMois;

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
     * Gets the value of the oMcgeo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMcgeo() {
        return oMcgeo;
    }

    /**
     * Sets the value of the oMcgeo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMcgeo(double value) {
        this.oMcgeo = value;
    }

    /**
     * Gets the value of the oMccombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMccombles() {
        return oMccombles;
    }

    /**
     * Sets the value of the oMccombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMccombles(double value) {
        this.oMccombles = value;
    }

    /**
     * Gets the value of the oMcsurfMoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMcsurfMoy() {
        return oMcsurfMoy;
    }

    /**
     * Sets the value of the oMcsurfMoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMcsurfMoy(double value) {
        this.oMcsurfMoy = value;
    }

    /**
     * Gets the value of the oMcsurfTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMcsurfTot() {
        return oMcsurfTot;
    }

    /**
     * Sets the value of the oMcsurfTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMcsurfTot(double value) {
        this.oMcsurfTot = value;
    }

    /**
     * Gets the value of the oMccat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMccat() {
        return oMccat;
    }

    /**
     * Sets the value of the oMccat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMccat(double value) {
        this.oMccat = value;
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
     * Gets the value of the oMcbilanBEPOS1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMcbilanBEPOS1() {
        return oMcbilanBEPOS1;
    }

    /**
     * Sets the value of the oMcbilanBEPOS1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMcbilanBEPOS1(double value) {
        this.oMcbilanBEPOS1 = value;
    }

    /**
     * Gets the value of the oMcbilanBEPOS2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMcbilanBEPOS2() {
        return oMcbilanBEPOS2;
    }

    /**
     * Sets the value of the oMcbilanBEPOS2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMcbilanBEPOS2(double value) {
        this.oMcbilanBEPOS2 = value;
    }

    /**
     * Gets the value of the oMcbilanBEPOS3 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMcbilanBEPOS3() {
        return oMcbilanBEPOS3;
    }

    /**
     * Sets the value of the oMcbilanBEPOS3 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMcbilanBEPOS3(double value) {
        this.oMcbilanBEPOS3 = value;
    }

    /**
     * Gets the value of the oProdRef property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOProdRef() {
        return oProdRef;
    }

    /**
     * Sets the value of the oProdRef property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOProdRef(double value) {
        this.oProdRef = value;
    }

    /**
     * Gets the value of the oCefChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefChAnnuel() {
        return oCefChAnnuel;
    }

    /**
     * Sets the value of the oCefChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChAnnuel(double value) {
        this.oCefChAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFrAnnuel() {
        return oCefFrAnnuel;
    }

    /**
     * Sets the value of the oCefFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFrAnnuel(double value) {
        this.oCefFrAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEcsAnnuel() {
        return oCefEcsAnnuel;
    }

    /**
     * Sets the value of the oCefEcsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsAnnuel(double value) {
        this.oCefEcsAnnuel = value;
    }

    /**
     * Gets the value of the oCefEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEclAnnuel() {
        return oCefEclAnnuel;
    }

    /**
     * Sets the value of the oCefEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEclAnnuel(double value) {
        this.oCefEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefAuxVentilateurAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefAuxVentilateurAnnuel() {
        return oCefAuxVentilateurAnnuel;
    }

    /**
     * Sets the value of the oCefAuxVentilateurAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAuxVentilateurAnnuel(double value) {
        this.oCefAuxVentilateurAnnuel = value;
    }

    /**
     * Gets the value of the oCefAuxDistributionAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefAuxDistributionAnnuel() {
        return oCefAuxDistributionAnnuel;
    }

    /**
     * Sets the value of the oCefAuxDistributionAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAuxDistributionAnnuel(double value) {
        this.oCefAuxDistributionAnnuel = value;
    }

    /**
     * Gets the value of the oCefChGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefChGazAnnuel() {
        return oCefChGazAnnuel;
    }

    /**
     * Sets the value of the oCefChGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChGazAnnuel(double value) {
        this.oCefChGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFrGazAnnuel() {
        return oCefFrGazAnnuel;
    }

    /**
     * Sets the value of the oCefFrGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFrGazAnnuel(double value) {
        this.oCefFrGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEcsGazAnnuel() {
        return oCefEcsGazAnnuel;
    }

    /**
     * Sets the value of the oCefEcsGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsGazAnnuel(double value) {
        this.oCefEcsGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefChFioulAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefChFioulAnnuel() {
        return oCefChFioulAnnuel;
    }

    /**
     * Sets the value of the oCefChFioulAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChFioulAnnuel(double value) {
        this.oCefChFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsFioulAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEcsFioulAnnuel() {
        return oCefEcsFioulAnnuel;
    }

    /**
     * Sets the value of the oCefEcsFioulAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsFioulAnnuel(double value) {
        this.oCefEcsFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefChBoisAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefChBoisAnnuel() {
        return oCefChBoisAnnuel;
    }

    /**
     * Sets the value of the oCefChBoisAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChBoisAnnuel(double value) {
        this.oCefChBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsBoisAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEcsBoisAnnuel() {
        return oCefEcsBoisAnnuel;
    }

    /**
     * Sets the value of the oCefEcsBoisAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsBoisAnnuel(double value) {
        this.oCefEcsBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefChReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefChReseauAnnuel() {
        return oCefChReseauAnnuel;
    }

    /**
     * Sets the value of the oCefChReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChReseauAnnuel(double value) {
        this.oCefChReseauAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFrReseauAnnuel() {
        return oCefFrReseauAnnuel;
    }

    /**
     * Sets the value of the oCefFrReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFrReseauAnnuel(double value) {
        this.oCefFrReseauAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEcsReseauAnnuel() {
        return oCefEcsReseauAnnuel;
    }

    /**
     * Sets the value of the oCefEcsReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsReseauAnnuel(double value) {
        this.oCefEcsReseauAnnuel = value;
    }

    /**
     * Gets the value of the oCefChElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefChElecAnnuel() {
        return oCefChElecAnnuel;
    }

    /**
     * Sets the value of the oCefChElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChElecAnnuel(double value) {
        this.oCefChElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefFrElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFrElecAnnuel() {
        return oCefFrElecAnnuel;
    }

    /**
     * Sets the value of the oCefFrElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFrElecAnnuel(double value) {
        this.oCefFrElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefEcsElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEcsElecAnnuel() {
        return oCefEcsElecAnnuel;
    }

    /**
     * Sets the value of the oCefEcsElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsElecAnnuel(double value) {
        this.oCefEcsElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefEclElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefEclElecAnnuel() {
        return oCefEclElecAnnuel;
    }

    /**
     * Sets the value of the oCefEclElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEclElecAnnuel(double value) {
        this.oCefEclElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefAuxvElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefAuxvElecAnnuel() {
        return oCefAuxvElecAnnuel;
    }

    /**
     * Sets the value of the oCefAuxvElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAuxvElecAnnuel(double value) {
        this.oCefAuxvElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefAuxsElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefAuxsElecAnnuel() {
        return oCefAuxsElecAnnuel;
    }

    /**
     * Sets the value of the oCefAuxsElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAuxsElecAnnuel(double value) {
        this.oCefAuxsElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefGazAnnuel() {
        return oCefGazAnnuel;
    }

    /**
     * Sets the value of the oCefGazAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefGazAnnuel(double value) {
        this.oCefGazAnnuel = value;
    }

    /**
     * Gets the value of the oCefFioulAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefFioulAnnuel() {
        return oCefFioulAnnuel;
    }

    /**
     * Sets the value of the oCefFioulAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFioulAnnuel(double value) {
        this.oCefFioulAnnuel = value;
    }

    /**
     * Gets the value of the oCefBoisAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefBoisAnnuel() {
        return oCefBoisAnnuel;
    }

    /**
     * Sets the value of the oCefBoisAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefBoisAnnuel(double value) {
        this.oCefBoisAnnuel = value;
    }

    /**
     * Gets the value of the oCefElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefElecAnnuel() {
        return oCefElecAnnuel;
    }

    /**
     * Sets the value of the oCefElecAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefElecAnnuel(double value) {
        this.oCefElecAnnuel = value;
    }

    /**
     * Gets the value of the oCefReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOCefReseauAnnuel() {
        return oCefReseauAnnuel;
    }

    /**
     * Sets the value of the oCefReseauAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefReseauAnnuel(double value) {
        this.oCefReseauAnnuel = value;
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
     * Gets the value of the obEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBEclAnnuel() {
        return obEclAnnuel;
    }

    /**
     * Sets the value of the obEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclAnnuel(double value) {
        this.obEclAnnuel = value;
    }

    /**
     * Gets the value of the oCefChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle1 getOCefChMois() {
        return oCefChMois;
    }

    /**
     * Sets the value of the oCefChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefChMois(ArrayOfRTDataSortieMensuelle1 value) {
        this.oCefChMois = value;
    }

    /**
     * Gets the value of the oCefFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle1 getOCefFrMois() {
        return oCefFrMois;
    }

    /**
     * Sets the value of the oCefFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefFrMois(ArrayOfRTDataSortieMensuelle1 value) {
        this.oCefFrMois = value;
    }

    /**
     * Gets the value of the oCefEcsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle1 getOCefEcsMois() {
        return oCefEcsMois;
    }

    /**
     * Sets the value of the oCefEcsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEcsMois(ArrayOfRTDataSortieMensuelle1 value) {
        this.oCefEcsMois = value;
    }

    /**
     * Gets the value of the oCefEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle1 getOCefEclMois() {
        return oCefEclMois;
    }

    /**
     * Sets the value of the oCefEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefEclMois(ArrayOfRTDataSortieMensuelle1 value) {
        this.oCefEclMois = value;
    }

    /**
     * Gets the value of the oCefAuxVentilateurMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle1 getOCefAuxVentilateurMois() {
        return oCefAuxVentilateurMois;
    }

    /**
     * Sets the value of the oCefAuxVentilateurMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAuxVentilateurMois(ArrayOfRTDataSortieMensuelle1 value) {
        this.oCefAuxVentilateurMois = value;
    }

    /**
     * Gets the value of the oCefAuxDistributionMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle1 getOCefAuxDistributionMois() {
        return oCefAuxDistributionMois;
    }

    /**
     * Sets the value of the oCefAuxDistributionMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOCefAuxDistributionMois(ArrayOfRTDataSortieMensuelle1 value) {
        this.oCefAuxDistributionMois = value;
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
     * Gets the value of the obEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclMois = value;
    }

}
