
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Ventilation_Mecanique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Ventilation_Mecanique">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Ventilation_Mecanique" type="{}RT_Type_Ven_Meca"/>
 *         &lt;element name="Niveau_Pression" type="{}E_Niveau_Pression"/>
 *         &lt;element name="Type_CTA_DAC" type="{}E_Type_CTA_DAC"/>
 *         &lt;element name="Q_souf_cond_max_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_souf_cond_max_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Ventilateur" type="{}E_Type_Ventilateur_DAV"/>
 *         &lt;element name="Type_Echangeur" type="{}RT_Type_Echangeur_Ventilation"/>
 *         &lt;element name="Type_Echangeur_Detaille" type="{}RT_Type_Echangeur_Detaille"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur" type="{}RT_Certificat_Efficacite_Echangeur"/>
 *         &lt;element name="Epsilon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_elec_ech" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Antigel" type="{}RT_Oui_Non"/>
 *         &lt;element name="Statut_Regulation_Antigel" type="{}RT_Statut_Regulation_Antigel"/>
 *         &lt;element name="T_sec_h_rep_LIM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_ByPass" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_ext_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_aux_AN" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Regulation" type="{}E_Type_Regulation"/>
 *         &lt;element name="T_ENC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ENF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_T_AN" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_extref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_ChaudHR" type="{}RT_Oui_Non"/>
 *         &lt;element name="Type_Humidification" type="{}E_Type_Humidification"/>
 *         &lt;element name="W_cons" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Prechaud" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_cons_prechaud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ex_prechaud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Prefroid" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_cons_prefroid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ex_prefroid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_dim_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_souf_nom_chaud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_souf_nom_froid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Humidification_Ete" type="{}E_Type_Humidification_Ete"/>
 *         &lt;element name="Theta_i_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_i_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_i_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rendement_Humidificateur_Ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_base_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_base_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Puits_Climatique" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Puits_Hydraulique" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Synchro_Surventil_Puits" type="{}RT_Type_Surventilation_Puits"/>
 *         &lt;element name="Type_eff_echangeur" type="{}RT_Type_Efficacite_Echangeur"/>
 *         &lt;element name="Pch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pfr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Origine_Horaire" type="{}E_Origine_Horaires_Ven_Hyb"/>
 *         &lt;element name="Dugd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="H_gd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="V_vent_reg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_ext_reg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Distribution_CTA_Chaud_Collection" type="{}ArrayOfRT_Data_Distribution_CTA_Chaud" minOccurs="0"/>
 *         &lt;element name="Distribution_CTA_Froid" type="{}RT_Data_Distribution_CTA_Froid" minOccurs="0"/>
 *         &lt;element name="Is_Rafnoc" type="{}RT_Type_Rafnoc"/>
 *         &lt;element name="Param_Rafnoc_MiSaison" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Param_Rafnoc_Ete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_rafnoc_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rafnoc_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Ventilation_Mecanique", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataVentilationMecanique {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Type_Ventilation_Mecanique", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeVentilationMecanique;
    @XmlElement(name = "Niveau_Pression", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String niveauPression;
    @XmlElement(name = "Type_CTA_DAC", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeCTADAC;
    @XmlElement(name = "Q_souf_cond_max_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qSoufCondMaxOcc;
    @XmlElement(name = "Q_souf_cond_max_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qSoufCondMaxInocc;
    @XmlElement(name = "Type_Ventilateur", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeVentilateur;
    @XmlElement(name = "Type_Echangeur", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeEchangeur;
    @XmlElement(name = "Type_Echangeur_Detaille", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeEchangeurDetaille;
    @XmlElement(name = "Certificat_Efficacite_Echangeur", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String certificatEfficaciteEchangeur;
    @XmlElement(name = "Epsilon")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double epsilon;
    @XmlElement(name = "P_elec_ech")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pElecEch;
    @XmlElement(name = "UA")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ua;
    @XmlElement(name = "Is_Antigel", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isAntigel;
    @XmlElement(name = "Statut_Regulation_Antigel", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutRegulationAntigel;
    @XmlElement(name = "T_sec_h_rep_LIM")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tSecHRepLIM;
    @XmlElement(name = "Is_ByPass", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isByPass;
    @XmlElement(name = "T_ext_bp_hiver")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tExtBpHiver;
    @XmlElement(name = "T_int_bp_hiver")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tIntBpHiver;
    @XmlElement(name = "T_ext_bp_ete")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tExtBpEte;
    @XmlElement(name = "T_int_bp_ete")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tIntBpEte;
    @XmlElement(name = "T_aux_AN")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tAuxAN;
    @XmlElement(name = "Type_Regulation", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeRegulation;
    @XmlElement(name = "T_ENC")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tenc;
    @XmlElement(name = "T_ENF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tenf;
    @XmlElement(name = "T_ext_T_AN")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tExtTAN;
    @XmlElement(name = "T_extref")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tExtref;
    @XmlElement(name = "Is_ChaudHR", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isChaudHR;
    @XmlElement(name = "Type_Humidification", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeHumidification;
    @XmlElement(name = "W_cons")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double wCons;
    @XmlElement(name = "Is_Prechaud", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isPrechaud;
    @XmlElement(name = "T_cons_prechaud")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tConsPrechaud;
    @XmlElement(name = "T_ex_prechaud")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tExPrechaud;
    @XmlElement(name = "Is_Prefroid", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isPrefroid;
    @XmlElement(name = "T_cons_prefroid")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tConsPrefroid;
    @XmlElement(name = "T_ex_prefroid")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tExPrefroid;
    @XmlElement(name = "Theta_dim_fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaDimFr;
    @XmlElement(name = "Theta_souf_nom_chaud")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaSoufNomChaud;
    @XmlElement(name = "Theta_souf_nom_froid")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaSoufNomFroid;
    @XmlElement(name = "Type_Humidification_Ete", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeHumidificationEte;
    @XmlElement(name = "Theta_i_base")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaIBase;
    @XmlElement(name = "Delta_Theta_i_1")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaI1;
    @XmlElement(name = "Delta_Theta_i_2")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaI2;
    @XmlElement(name = "Rendement_Humidificateur_Ete")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rendementHumidificateurEte;
    @XmlElement(name = "Pvent_base_souf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventBaseSouf;
    @XmlElement(name = "Pvent_pointe_souf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventPointeSouf;
    @XmlElement(name = "Pvent_base_rep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventBaseRep;
    @XmlElement(name = "Pvent_pointe_rep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventPointeRep;
    @XmlElement(name = "Pvent_souf_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventSoufOcc;
    @XmlElement(name = "Pvent_souf_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventSoufInocc;
    @XmlElement(name = "Pvent_souf_ZN_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventSoufZNOcc;
    @XmlElement(name = "Pvent_souf_ZN_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventSoufZNInocc;
    @XmlElement(name = "Pvent_souf_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventSoufCH;
    @XmlElement(name = "Pvent_rep_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRepOcc;
    @XmlElement(name = "Pvent_rep_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRepInocc;
    @XmlElement(name = "Pvent_rep_ZN_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRepZNOcc;
    @XmlElement(name = "Pvent_rep_ZN_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRepZNInocc;
    @XmlElement(name = "Pvent_rep_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRepCH;
    @XmlElement(name = "Pvent")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String pvent;
    @XmlElement(name = "Pvent_pointe")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventPointe;
    @XmlElement(name = "Id_Puits_Climatique")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idPuitsClimatique;
    @XmlElement(name = "Id_Puits_Hydraulique")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idPuitsHydraulique;
    @XmlElement(name = "Synchro_Surventil_Puits", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String synchroSurventilPuits;
    @XmlElement(name = "Type_eff_echangeur", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeEffEchangeur;
    @XmlElement(name = "Pch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pch;
    @XmlElement(name = "Pfr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pfr;
    @XmlElement(name = "Origine_Horaire", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String origineHoraire;
    @XmlElement(name = "Dugd")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int dugd;
    @XmlElement(name = "H_gd")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String hGd;
    @XmlElement(name = "V_vent_reg")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String vVentReg;
    @XmlElement(name = "Theta_ext_reg")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaExtReg;
    @XmlElement(name = "Id_Et")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idEt;
    @XmlElement(name = "Distribution_CTA_Chaud_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionCTAChaud distributionCTAChaudCollection;
    @XmlElement(name = "Distribution_CTA_Froid")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataDistributionCTAFroid distributionCTAFroid;
    @XmlElement(name = "Is_Rafnoc", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isRafnoc;
    @XmlElement(name = "Param_Rafnoc_MiSaison")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String paramRafnocMiSaison;
    @XmlElement(name = "Param_Rafnoc_Ete")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String paramRafnocEte;
    @XmlElement(name = "Pvent_rafnoc_rep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRafnocRep;
    @XmlElement(name = "Pvent_rafnoc_souf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventRafnocSouf;

    /**
     * Gets the value of the index property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the typeVentilationMecanique property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeVentilationMecanique() {
        return typeVentilationMecanique;
    }

    /**
     * Sets the value of the typeVentilationMecanique property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeVentilationMecanique(String value) {
        this.typeVentilationMecanique = value;
    }

    /**
     * Gets the value of the niveauPression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNiveauPression() {
        return niveauPression;
    }

    /**
     * Sets the value of the niveauPression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNiveauPression(String value) {
        this.niveauPression = value;
    }

    /**
     * Gets the value of the typeCTADAC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeCTADAC() {
        return typeCTADAC;
    }

    /**
     * Sets the value of the typeCTADAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeCTADAC(String value) {
        this.typeCTADAC = value;
    }

    /**
     * Gets the value of the qSoufCondMaxOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQSoufCondMaxOcc() {
        return qSoufCondMaxOcc;
    }

    /**
     * Sets the value of the qSoufCondMaxOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQSoufCondMaxOcc(double value) {
        this.qSoufCondMaxOcc = value;
    }

    /**
     * Gets the value of the qSoufCondMaxInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQSoufCondMaxInocc() {
        return qSoufCondMaxInocc;
    }

    /**
     * Sets the value of the qSoufCondMaxInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQSoufCondMaxInocc(double value) {
        this.qSoufCondMaxInocc = value;
    }

    /**
     * Gets the value of the typeVentilateur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeVentilateur() {
        return typeVentilateur;
    }

    /**
     * Sets the value of the typeVentilateur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeVentilateur(String value) {
        this.typeVentilateur = value;
    }

    /**
     * Gets the value of the typeEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeEchangeur() {
        return typeEchangeur;
    }

    /**
     * Sets the value of the typeEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeEchangeur(String value) {
        this.typeEchangeur = value;
    }

    /**
     * Gets the value of the typeEchangeurDetaille property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeEchangeurDetaille() {
        return typeEchangeurDetaille;
    }

    /**
     * Sets the value of the typeEchangeurDetaille property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeEchangeurDetaille(String value) {
        this.typeEchangeurDetaille = value;
    }

    /**
     * Gets the value of the certificatEfficaciteEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCertificatEfficaciteEchangeur() {
        return certificatEfficaciteEchangeur;
    }

    /**
     * Sets the value of the certificatEfficaciteEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCertificatEfficaciteEchangeur(String value) {
        this.certificatEfficaciteEchangeur = value;
    }

    /**
     * Gets the value of the epsilon property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEpsilon() {
        return epsilon;
    }

    /**
     * Sets the value of the epsilon property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEpsilon(double value) {
        this.epsilon = value;
    }

    /**
     * Gets the value of the pElecEch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPElecEch() {
        return pElecEch;
    }

    /**
     * Sets the value of the pElecEch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPElecEch(double value) {
        this.pElecEch = value;
    }

    /**
     * Gets the value of the ua property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUA() {
        return ua;
    }

    /**
     * Sets the value of the ua property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUA(double value) {
        this.ua = value;
    }

    /**
     * Gets the value of the isAntigel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsAntigel() {
        return isAntigel;
    }

    /**
     * Sets the value of the isAntigel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsAntigel(String value) {
        this.isAntigel = value;
    }

    /**
     * Gets the value of the statutRegulationAntigel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutRegulationAntigel() {
        return statutRegulationAntigel;
    }

    /**
     * Sets the value of the statutRegulationAntigel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutRegulationAntigel(String value) {
        this.statutRegulationAntigel = value;
    }

    /**
     * Gets the value of the tSecHRepLIM property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTSecHRepLIM() {
        return tSecHRepLIM;
    }

    /**
     * Sets the value of the tSecHRepLIM property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTSecHRepLIM(double value) {
        this.tSecHRepLIM = value;
    }

    /**
     * Gets the value of the isByPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsByPass() {
        return isByPass;
    }

    /**
     * Sets the value of the isByPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsByPass(String value) {
        this.isByPass = value;
    }

    /**
     * Gets the value of the tExtBpHiver property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTExtBpHiver() {
        return tExtBpHiver;
    }

    /**
     * Sets the value of the tExtBpHiver property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTExtBpHiver(double value) {
        this.tExtBpHiver = value;
    }

    /**
     * Gets the value of the tIntBpHiver property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTIntBpHiver() {
        return tIntBpHiver;
    }

    /**
     * Sets the value of the tIntBpHiver property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTIntBpHiver(double value) {
        this.tIntBpHiver = value;
    }

    /**
     * Gets the value of the tExtBpEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTExtBpEte() {
        return tExtBpEte;
    }

    /**
     * Sets the value of the tExtBpEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTExtBpEte(double value) {
        this.tExtBpEte = value;
    }

    /**
     * Gets the value of the tIntBpEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTIntBpEte() {
        return tIntBpEte;
    }

    /**
     * Sets the value of the tIntBpEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTIntBpEte(double value) {
        this.tIntBpEte = value;
    }

    /**
     * Gets the value of the tAuxAN property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTAuxAN() {
        return tAuxAN;
    }

    /**
     * Sets the value of the tAuxAN property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTAuxAN(double value) {
        this.tAuxAN = value;
    }

    /**
     * Gets the value of the typeRegulation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeRegulation() {
        return typeRegulation;
    }

    /**
     * Sets the value of the typeRegulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeRegulation(String value) {
        this.typeRegulation = value;
    }

    /**
     * Gets the value of the tenc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTENC() {
        return tenc;
    }

    /**
     * Sets the value of the tenc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTENC(double value) {
        this.tenc = value;
    }

    /**
     * Gets the value of the tenf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTENF() {
        return tenf;
    }

    /**
     * Sets the value of the tenf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTENF(double value) {
        this.tenf = value;
    }

    /**
     * Gets the value of the tExtTAN property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTExtTAN() {
        return tExtTAN;
    }

    /**
     * Sets the value of the tExtTAN property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTExtTAN(double value) {
        this.tExtTAN = value;
    }

    /**
     * Gets the value of the tExtref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTExtref() {
        return tExtref;
    }

    /**
     * Sets the value of the tExtref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTExtref(double value) {
        this.tExtref = value;
    }

    /**
     * Gets the value of the isChaudHR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsChaudHR() {
        return isChaudHR;
    }

    /**
     * Sets the value of the isChaudHR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsChaudHR(String value) {
        this.isChaudHR = value;
    }

    /**
     * Gets the value of the typeHumidification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeHumidification() {
        return typeHumidification;
    }

    /**
     * Sets the value of the typeHumidification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeHumidification(String value) {
        this.typeHumidification = value;
    }

    /**
     * Gets the value of the wCons property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getWCons() {
        return wCons;
    }

    /**
     * Sets the value of the wCons property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setWCons(double value) {
        this.wCons = value;
    }

    /**
     * Gets the value of the isPrechaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsPrechaud() {
        return isPrechaud;
    }

    /**
     * Sets the value of the isPrechaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsPrechaud(String value) {
        this.isPrechaud = value;
    }

    /**
     * Gets the value of the tConsPrechaud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTConsPrechaud() {
        return tConsPrechaud;
    }

    /**
     * Sets the value of the tConsPrechaud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTConsPrechaud(double value) {
        this.tConsPrechaud = value;
    }

    /**
     * Gets the value of the tExPrechaud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTExPrechaud() {
        return tExPrechaud;
    }

    /**
     * Sets the value of the tExPrechaud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTExPrechaud(double value) {
        this.tExPrechaud = value;
    }

    /**
     * Gets the value of the isPrefroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsPrefroid() {
        return isPrefroid;
    }

    /**
     * Sets the value of the isPrefroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsPrefroid(String value) {
        this.isPrefroid = value;
    }

    /**
     * Gets the value of the tConsPrefroid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTConsPrefroid() {
        return tConsPrefroid;
    }

    /**
     * Sets the value of the tConsPrefroid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTConsPrefroid(double value) {
        this.tConsPrefroid = value;
    }

    /**
     * Gets the value of the tExPrefroid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTExPrefroid() {
        return tExPrefroid;
    }

    /**
     * Sets the value of the tExPrefroid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTExPrefroid(double value) {
        this.tExPrefroid = value;
    }

    /**
     * Gets the value of the thetaDimFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaDimFr() {
        return thetaDimFr;
    }

    /**
     * Sets the value of the thetaDimFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaDimFr(double value) {
        this.thetaDimFr = value;
    }

    /**
     * Gets the value of the thetaSoufNomChaud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaSoufNomChaud() {
        return thetaSoufNomChaud;
    }

    /**
     * Sets the value of the thetaSoufNomChaud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaSoufNomChaud(double value) {
        this.thetaSoufNomChaud = value;
    }

    /**
     * Gets the value of the thetaSoufNomFroid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaSoufNomFroid() {
        return thetaSoufNomFroid;
    }

    /**
     * Sets the value of the thetaSoufNomFroid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaSoufNomFroid(double value) {
        this.thetaSoufNomFroid = value;
    }

    /**
     * Gets the value of the typeHumidificationEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeHumidificationEte() {
        return typeHumidificationEte;
    }

    /**
     * Sets the value of the typeHumidificationEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeHumidificationEte(String value) {
        this.typeHumidificationEte = value;
    }

    /**
     * Gets the value of the thetaIBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaIBase() {
        return thetaIBase;
    }

    /**
     * Sets the value of the thetaIBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaIBase(double value) {
        this.thetaIBase = value;
    }

    /**
     * Gets the value of the deltaThetaI1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaI1() {
        return deltaThetaI1;
    }

    /**
     * Sets the value of the deltaThetaI1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaI1(double value) {
        this.deltaThetaI1 = value;
    }

    /**
     * Gets the value of the deltaThetaI2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaI2() {
        return deltaThetaI2;
    }

    /**
     * Sets the value of the deltaThetaI2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaI2(double value) {
        this.deltaThetaI2 = value;
    }

    /**
     * Gets the value of the rendementHumidificateurEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRendementHumidificateurEte() {
        return rendementHumidificateurEte;
    }

    /**
     * Sets the value of the rendementHumidificateurEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRendementHumidificateurEte(double value) {
        this.rendementHumidificateurEte = value;
    }

    /**
     * Gets the value of the pventBaseSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventBaseSouf() {
        return pventBaseSouf;
    }

    /**
     * Sets the value of the pventBaseSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventBaseSouf(double value) {
        this.pventBaseSouf = value;
    }

    /**
     * Gets the value of the pventPointeSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventPointeSouf() {
        return pventPointeSouf;
    }

    /**
     * Sets the value of the pventPointeSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventPointeSouf(double value) {
        this.pventPointeSouf = value;
    }

    /**
     * Gets the value of the pventBaseRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventBaseRep() {
        return pventBaseRep;
    }

    /**
     * Sets the value of the pventBaseRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventBaseRep(double value) {
        this.pventBaseRep = value;
    }

    /**
     * Gets the value of the pventPointeRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventPointeRep() {
        return pventPointeRep;
    }

    /**
     * Sets the value of the pventPointeRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventPointeRep(double value) {
        this.pventPointeRep = value;
    }

    /**
     * Gets the value of the pventSoufOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventSoufOcc() {
        return pventSoufOcc;
    }

    /**
     * Sets the value of the pventSoufOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventSoufOcc(double value) {
        this.pventSoufOcc = value;
    }

    /**
     * Gets the value of the pventSoufInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventSoufInocc() {
        return pventSoufInocc;
    }

    /**
     * Sets the value of the pventSoufInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventSoufInocc(double value) {
        this.pventSoufInocc = value;
    }

    /**
     * Gets the value of the pventSoufZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventSoufZNOcc() {
        return pventSoufZNOcc;
    }

    /**
     * Sets the value of the pventSoufZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventSoufZNOcc(double value) {
        this.pventSoufZNOcc = value;
    }

    /**
     * Gets the value of the pventSoufZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventSoufZNInocc() {
        return pventSoufZNInocc;
    }

    /**
     * Sets the value of the pventSoufZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventSoufZNInocc(double value) {
        this.pventSoufZNInocc = value;
    }

    /**
     * Gets the value of the pventSoufCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventSoufCH() {
        return pventSoufCH;
    }

    /**
     * Sets the value of the pventSoufCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventSoufCH(double value) {
        this.pventSoufCH = value;
    }

    /**
     * Gets the value of the pventRepOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRepOcc() {
        return pventRepOcc;
    }

    /**
     * Sets the value of the pventRepOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRepOcc(double value) {
        this.pventRepOcc = value;
    }

    /**
     * Gets the value of the pventRepInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRepInocc() {
        return pventRepInocc;
    }

    /**
     * Sets the value of the pventRepInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRepInocc(double value) {
        this.pventRepInocc = value;
    }

    /**
     * Gets the value of the pventRepZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRepZNOcc() {
        return pventRepZNOcc;
    }

    /**
     * Sets the value of the pventRepZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRepZNOcc(double value) {
        this.pventRepZNOcc = value;
    }

    /**
     * Gets the value of the pventRepZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRepZNInocc() {
        return pventRepZNInocc;
    }

    /**
     * Sets the value of the pventRepZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRepZNInocc(double value) {
        this.pventRepZNInocc = value;
    }

    /**
     * Gets the value of the pventRepCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRepCH() {
        return pventRepCH;
    }

    /**
     * Sets the value of the pventRepCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRepCH(double value) {
        this.pventRepCH = value;
    }

    /**
     * Gets the value of the pvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPvent() {
        return pvent;
    }

    /**
     * Sets the value of the pvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPvent(String value) {
        this.pvent = value;
    }

    /**
     * Gets the value of the pventPointe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventPointe() {
        return pventPointe;
    }

    /**
     * Sets the value of the pventPointe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventPointe(double value) {
        this.pventPointe = value;
    }

    /**
     * Gets the value of the idPuitsClimatique property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdPuitsClimatique() {
        return idPuitsClimatique;
    }

    /**
     * Sets the value of the idPuitsClimatique property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdPuitsClimatique(int value) {
        this.idPuitsClimatique = value;
    }

    /**
     * Gets the value of the idPuitsHydraulique property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdPuitsHydraulique() {
        return idPuitsHydraulique;
    }

    /**
     * Sets the value of the idPuitsHydraulique property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdPuitsHydraulique(int value) {
        this.idPuitsHydraulique = value;
    }

    /**
     * Gets the value of the synchroSurventilPuits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSynchroSurventilPuits() {
        return synchroSurventilPuits;
    }

    /**
     * Sets the value of the synchroSurventilPuits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSynchroSurventilPuits(String value) {
        this.synchroSurventilPuits = value;
    }

    /**
     * Gets the value of the typeEffEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeEffEchangeur() {
        return typeEffEchangeur;
    }

    /**
     * Sets the value of the typeEffEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeEffEchangeur(String value) {
        this.typeEffEchangeur = value;
    }

    /**
     * Gets the value of the pch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPch() {
        return pch;
    }

    /**
     * Sets the value of the pch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPch(double value) {
        this.pch = value;
    }

    /**
     * Gets the value of the pfr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPfr() {
        return pfr;
    }

    /**
     * Sets the value of the pfr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPfr(double value) {
        this.pfr = value;
    }

    /**
     * Gets the value of the origineHoraire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getOrigineHoraire() {
        return origineHoraire;
    }

    /**
     * Sets the value of the origineHoraire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOrigineHoraire(String value) {
        this.origineHoraire = value;
    }

    /**
     * Gets the value of the dugd property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getDugd() {
        return dugd;
    }

    /**
     * Sets the value of the dugd property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDugd(int value) {
        this.dugd = value;
    }

    /**
     * Gets the value of the hGd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getHGd() {
        return hGd;
    }

    /**
     * Sets the value of the hGd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setHGd(String value) {
        this.hGd = value;
    }

    /**
     * Gets the value of the vVentReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getVVentReg() {
        return vVentReg;
    }

    /**
     * Sets the value of the vVentReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVVentReg(String value) {
        this.vVentReg = value;
    }

    /**
     * Gets the value of the thetaExtReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaExtReg() {
        return thetaExtReg;
    }

    /**
     * Sets the value of the thetaExtReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaExtReg(String value) {
        this.thetaExtReg = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdEt(int value) {
        this.idEt = value;
    }

    /**
     * Gets the value of the distributionCTAChaudCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionCTAChaud }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataDistributionCTAChaud getDistributionCTAChaudCollection() {
        return distributionCTAChaudCollection;
    }

    /**
     * Sets the value of the distributionCTAChaudCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionCTAChaud }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionCTAChaudCollection(ArrayOfRTDataDistributionCTAChaud value) {
        this.distributionCTAChaudCollection = value;
    }

    /**
     * Gets the value of the distributionCTAFroid property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataDistributionCTAFroid }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataDistributionCTAFroid getDistributionCTAFroid() {
        return distributionCTAFroid;
    }

    /**
     * Sets the value of the distributionCTAFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataDistributionCTAFroid }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionCTAFroid(RTDataDistributionCTAFroid value) {
        this.distributionCTAFroid = value;
    }

    /**
     * Gets the value of the isRafnoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsRafnoc() {
        return isRafnoc;
    }

    /**
     * Sets the value of the isRafnoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsRafnoc(String value) {
        this.isRafnoc = value;
    }

    /**
     * Gets the value of the paramRafnocMiSaison property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getParamRafnocMiSaison() {
        return paramRafnocMiSaison;
    }

    /**
     * Sets the value of the paramRafnocMiSaison property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setParamRafnocMiSaison(String value) {
        this.paramRafnocMiSaison = value;
    }

    /**
     * Gets the value of the paramRafnocEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getParamRafnocEte() {
        return paramRafnocEte;
    }

    /**
     * Sets the value of the paramRafnocEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setParamRafnocEte(String value) {
        this.paramRafnocEte = value;
    }

    /**
     * Gets the value of the pventRafnocRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRafnocRep() {
        return pventRafnocRep;
    }

    /**
     * Sets the value of the pventRafnocRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRafnocRep(double value) {
        this.pventRafnocRep = value;
    }

    /**
     * Gets the value of the pventRafnocSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventRafnocSouf() {
        return pventRafnocSouf;
    }

    /**
     * Sets the value of the pventRafnocSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventRafnocSouf(double value) {
        this.pventRafnocSouf = value;
    }

}
