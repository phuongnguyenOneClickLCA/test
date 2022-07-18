
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModuleAppartementRE2020MixteData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModuleAppartementRE2020MixteData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Prim" type="{}RT_Type_Prim"/>
 *         &lt;element name="Id_PCAD" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Type_Reseau_Intergroupe_ECS" type="{}RT_Type_Reseau_ECS"/>
 *         &lt;element name="Id_Fonction" type="{}E_Fonctionnement_Reseau_Intergroupe_Mixte"/>
 *         &lt;element name="Is_Maintenir_Temp_ECS" type="{}E_Maintenir_Reseau_En_Temperature_ECS"/>
 *         &lt;element name="Is_Maintenir_Temp_CH" type="{}E_Maintenir_Reseau_En_Temperature_CH"/>
 *         &lt;element name="Id_Position" type="{}E_Position_Module"/>
 *         &lt;element name="Id_Regulation_Circ" type="{}E_Mode_Regulation_Circulateur"/>
 *         &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="c" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnees_Echangeur_ECS" type="{}E_Statut_Donnees_Echangeur_ECS"/>
 *         &lt;element name="a_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="c_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnees_Echangeur_CH" type="{}E_Statut_Donnees_Echangeur_CH"/>
 *         &lt;element name="Theta_In_Prim_Nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Maintien_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Maintien_Echangeur_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nb_Mod" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Out_Prim_Maintien_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Out_Prim_Maintien_Echangeur_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ep_Iso_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lambda_Iso_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ep_Iso_Echangeur_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lambda_Iso_Echangeur_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Mixte" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_Mixte" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Chauffage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_Chauffage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_Module" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Aux_Fct" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Aux_Arret" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Resid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_prod_ECS_CH" type="{}E_Production_ECS_Et_CH"/>
 *         &lt;element name="L_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_H_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_Vc_gaine_MTA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_H_Vc_gaine_MTA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Circ_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Moy_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Moy_H_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Moy_Vc_Gaines_Modules" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Moy_H_Vc_Gaines_Modules" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_reseau_mixte_maintien_temperature" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="TypeSortieExcel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModuleAppartementRE2020MixteData", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ModuleAppartementRE2020MixteData {

    @XmlElement(name = "Id_Gen")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idGen;
    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Type_Prim", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typePrim;
    @XmlElement(name = "Id_PCAD")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idPCAD;
    @XmlElement(name = "Type_Reseau_Intergroupe_ECS", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeReseauIntergroupeECS;
    @XmlElement(name = "Id_Fonction", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idFonction;
    @XmlElement(name = "Is_Maintenir_Temp_ECS", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isMaintenirTempECS;
    @XmlElement(name = "Is_Maintenir_Temp_CH", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isMaintenirTempCH;
    @XmlElement(name = "Id_Position", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idPosition;
    @XmlElement(name = "Id_Regulation_Circ", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idRegulationCirc;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double a;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double b;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double c;
    @XmlElement(name = "Statut_Donnees_Echangeur_ECS", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDonneesEchangeurECS;
    @XmlElement(name = "a_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ach;
    @XmlElement(name = "b_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double bch;
    @XmlElement(name = "c_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double cch;
    @XmlElement(name = "Statut_Donnees_Echangeur_CH", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDonneesEchangeurCH;
    @XmlElement(name = "Theta_In_Prim_Nom")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaInPrimNom;
    @XmlElement(name = "q_Maintien_Echangeur_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qMaintienEchangeurECS;
    @XmlElement(name = "q_Maintien_Echangeur_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qMaintienEchangeurCH;
    @XmlElement(name = "Nb_Mod")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbMod;
    @XmlElement(name = "Theta_Out_Prim_Maintien_Echangeur_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaOutPrimMaintienEchangeurECS;
    @XmlElement(name = "Theta_Out_Prim_Maintien_Echangeur_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaOutPrimMaintienEchangeurCH;
    @XmlElement(name = "Ep_Iso_Echangeur_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double epIsoEchangeurECS;
    @XmlElement(name = "Lambda_Iso_Echangeur_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lambdaIsoEchangeurECS;
    @XmlElement(name = "Ep_Iso_Echangeur_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double epIsoEchangeurCH;
    @XmlElement(name = "Lambda_Iso_Echangeur_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lambdaIsoEchangeurCH;
    @XmlElement(name = "U_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uecs;
    @XmlElement(name = "L_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lecs;
    @XmlElement(name = "U_Mixte")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uMixte;
    @XmlElement(name = "L_Mixte")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lMixte;
    @XmlElement(name = "U_Chauffage")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uChauffage;
    @XmlElement(name = "L_Chauffage")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lChauffage;
    @XmlElement(name = "R_Module")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rModule;
    @XmlElement(name = "P_Aux_Fct")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pAuxFct;
    @XmlElement(name = "P_Aux_Arret")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pAuxArret;
    @XmlElement(name = "q_Nom")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qNom;
    @XmlElement(name = "q_Resid")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qResid;
    @XmlElement(name = "Is_prod_ECS_CH", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isProdECSCH;
    @XmlElement(name = "L_Vc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lVc;
    @XmlElement(name = "L_H_Vc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lhVc;
    @XmlElement(name = "L_Vc_gaine_MTA")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lVcGaineMTA;
    @XmlElement(name = "L_H_Vc_gaine_MTA")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lhVcGaineMTA;
    @XmlElement(name = "P_Aux")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pAux;
    @XmlElement(name = "P_Circ_Vc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pCircVc;
    @XmlElement(name = "U_Moy_Vc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uMoyVc;
    @XmlElement(name = "U_Moy_H_Vc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uMoyHVc;
    @XmlElement(name = "U_Moy_Vc_Gaines_Modules")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uMoyVcGainesModules;
    @XmlElement(name = "U_Moy_H_Vc_Gaines_Modules")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uMoyHVcGainesModules;
    @XmlElement(name = "Delta_reseau_mixte_maintien_temperature")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaReseauMixteMaintienTemperature;
    @XmlElement(name = "TypeSortieExcel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int typeSortieExcel;

    /**
     * Gets the value of the idGen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdGen() {
        return idGen;
    }

    /**
     * Sets the value of the idGen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdGen(int value) {
        this.idGen = value;
    }

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
     * Gets the value of the typePrim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypePrim() {
        return typePrim;
    }

    /**
     * Sets the value of the typePrim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypePrim(String value) {
        this.typePrim = value;
    }

    /**
     * Gets the value of the idPCAD property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdPCAD() {
        return idPCAD;
    }

    /**
     * Sets the value of the idPCAD property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdPCAD(int value) {
        this.idPCAD = value;
    }

    /**
     * Gets the value of the typeReseauIntergroupeECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeReseauIntergroupeECS() {
        return typeReseauIntergroupeECS;
    }

    /**
     * Sets the value of the typeReseauIntergroupeECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeReseauIntergroupeECS(String value) {
        this.typeReseauIntergroupeECS = value;
    }

    /**
     * Gets the value of the idFonction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdFonction() {
        return idFonction;
    }

    /**
     * Sets the value of the idFonction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdFonction(String value) {
        this.idFonction = value;
    }

    /**
     * Gets the value of the isMaintenirTempECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsMaintenirTempECS() {
        return isMaintenirTempECS;
    }

    /**
     * Sets the value of the isMaintenirTempECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsMaintenirTempECS(String value) {
        this.isMaintenirTempECS = value;
    }

    /**
     * Gets the value of the isMaintenirTempCH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsMaintenirTempCH() {
        return isMaintenirTempCH;
    }

    /**
     * Sets the value of the isMaintenirTempCH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsMaintenirTempCH(String value) {
        this.isMaintenirTempCH = value;
    }

    /**
     * Gets the value of the idPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdPosition() {
        return idPosition;
    }

    /**
     * Sets the value of the idPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdPosition(String value) {
        this.idPosition = value;
    }

    /**
     * Gets the value of the idRegulationCirc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdRegulationCirc() {
        return idRegulationCirc;
    }

    /**
     * Sets the value of the idRegulationCirc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdRegulationCirc(String value) {
        this.idRegulationCirc = value;
    }

    /**
     * Gets the value of the a property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setA(double value) {
        this.a = value;
    }

    /**
     * Gets the value of the b property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setB(double value) {
        this.b = value;
    }

    /**
     * Gets the value of the c property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getC() {
        return c;
    }

    /**
     * Sets the value of the c property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setC(double value) {
        this.c = value;
    }

    /**
     * Gets the value of the statutDonneesEchangeurECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutDonneesEchangeurECS() {
        return statutDonneesEchangeurECS;
    }

    /**
     * Sets the value of the statutDonneesEchangeurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutDonneesEchangeurECS(String value) {
        this.statutDonneesEchangeurECS = value;
    }

    /**
     * Gets the value of the ach property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getACH() {
        return ach;
    }

    /**
     * Sets the value of the ach property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setACH(double value) {
        this.ach = value;
    }

    /**
     * Gets the value of the bch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getBCH() {
        return bch;
    }

    /**
     * Sets the value of the bch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBCH(double value) {
        this.bch = value;
    }

    /**
     * Gets the value of the cch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCCH() {
        return cch;
    }

    /**
     * Sets the value of the cch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCCH(double value) {
        this.cch = value;
    }

    /**
     * Gets the value of the statutDonneesEchangeurCH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutDonneesEchangeurCH() {
        return statutDonneesEchangeurCH;
    }

    /**
     * Sets the value of the statutDonneesEchangeurCH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutDonneesEchangeurCH(String value) {
        this.statutDonneesEchangeurCH = value;
    }

    /**
     * Gets the value of the thetaInPrimNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaInPrimNom() {
        return thetaInPrimNom;
    }

    /**
     * Sets the value of the thetaInPrimNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaInPrimNom(double value) {
        this.thetaInPrimNom = value;
    }

    /**
     * Gets the value of the qMaintienEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQMaintienEchangeurECS() {
        return qMaintienEchangeurECS;
    }

    /**
     * Sets the value of the qMaintienEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQMaintienEchangeurECS(double value) {
        this.qMaintienEchangeurECS = value;
    }

    /**
     * Gets the value of the qMaintienEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQMaintienEchangeurCH() {
        return qMaintienEchangeurCH;
    }

    /**
     * Sets the value of the qMaintienEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQMaintienEchangeurCH(double value) {
        this.qMaintienEchangeurCH = value;
    }

    /**
     * Gets the value of the nbMod property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbMod() {
        return nbMod;
    }

    /**
     * Sets the value of the nbMod property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbMod(double value) {
        this.nbMod = value;
    }

    /**
     * Gets the value of the thetaOutPrimMaintienEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaOutPrimMaintienEchangeurECS() {
        return thetaOutPrimMaintienEchangeurECS;
    }

    /**
     * Sets the value of the thetaOutPrimMaintienEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaOutPrimMaintienEchangeurECS(double value) {
        this.thetaOutPrimMaintienEchangeurECS = value;
    }

    /**
     * Gets the value of the thetaOutPrimMaintienEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaOutPrimMaintienEchangeurCH() {
        return thetaOutPrimMaintienEchangeurCH;
    }

    /**
     * Sets the value of the thetaOutPrimMaintienEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaOutPrimMaintienEchangeurCH(double value) {
        this.thetaOutPrimMaintienEchangeurCH = value;
    }

    /**
     * Gets the value of the epIsoEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEpIsoEchangeurECS() {
        return epIsoEchangeurECS;
    }

    /**
     * Sets the value of the epIsoEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEpIsoEchangeurECS(double value) {
        this.epIsoEchangeurECS = value;
    }

    /**
     * Gets the value of the lambdaIsoEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLambdaIsoEchangeurECS() {
        return lambdaIsoEchangeurECS;
    }

    /**
     * Sets the value of the lambdaIsoEchangeurECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLambdaIsoEchangeurECS(double value) {
        this.lambdaIsoEchangeurECS = value;
    }

    /**
     * Gets the value of the epIsoEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEpIsoEchangeurCH() {
        return epIsoEchangeurCH;
    }

    /**
     * Sets the value of the epIsoEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEpIsoEchangeurCH(double value) {
        this.epIsoEchangeurCH = value;
    }

    /**
     * Gets the value of the lambdaIsoEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLambdaIsoEchangeurCH() {
        return lambdaIsoEchangeurCH;
    }

    /**
     * Sets the value of the lambdaIsoEchangeurCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLambdaIsoEchangeurCH(double value) {
        this.lambdaIsoEchangeurCH = value;
    }

    /**
     * Gets the value of the uecs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUECS() {
        return uecs;
    }

    /**
     * Sets the value of the uecs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUECS(double value) {
        this.uecs = value;
    }

    /**
     * Gets the value of the lecs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLECS() {
        return lecs;
    }

    /**
     * Sets the value of the lecs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLECS(double value) {
        this.lecs = value;
    }

    /**
     * Gets the value of the uMixte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUMixte() {
        return uMixte;
    }

    /**
     * Sets the value of the uMixte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUMixte(double value) {
        this.uMixte = value;
    }

    /**
     * Gets the value of the lMixte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLMixte() {
        return lMixte;
    }

    /**
     * Sets the value of the lMixte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLMixte(double value) {
        this.lMixte = value;
    }

    /**
     * Gets the value of the uChauffage property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUChauffage() {
        return uChauffage;
    }

    /**
     * Sets the value of the uChauffage property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUChauffage(double value) {
        this.uChauffage = value;
    }

    /**
     * Gets the value of the lChauffage property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLChauffage() {
        return lChauffage;
    }

    /**
     * Sets the value of the lChauffage property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLChauffage(double value) {
        this.lChauffage = value;
    }

    /**
     * Gets the value of the rModule property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRModule() {
        return rModule;
    }

    /**
     * Sets the value of the rModule property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRModule(double value) {
        this.rModule = value;
    }

    /**
     * Gets the value of the pAuxFct property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPAuxFct() {
        return pAuxFct;
    }

    /**
     * Sets the value of the pAuxFct property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPAuxFct(double value) {
        this.pAuxFct = value;
    }

    /**
     * Gets the value of the pAuxArret property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPAuxArret() {
        return pAuxArret;
    }

    /**
     * Sets the value of the pAuxArret property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPAuxArret(double value) {
        this.pAuxArret = value;
    }

    /**
     * Gets the value of the qNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQNom() {
        return qNom;
    }

    /**
     * Sets the value of the qNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQNom(double value) {
        this.qNom = value;
    }

    /**
     * Gets the value of the qResid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQResid() {
        return qResid;
    }

    /**
     * Sets the value of the qResid property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQResid(double value) {
        this.qResid = value;
    }

    /**
     * Gets the value of the isProdECSCH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsProdECSCH() {
        return isProdECSCH;
    }

    /**
     * Sets the value of the isProdECSCH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsProdECSCH(String value) {
        this.isProdECSCH = value;
    }

    /**
     * Gets the value of the lVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLVc() {
        return lVc;
    }

    /**
     * Sets the value of the lVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLVc(double value) {
        this.lVc = value;
    }

    /**
     * Gets the value of the lhVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLHVc() {
        return lhVc;
    }

    /**
     * Sets the value of the lhVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLHVc(double value) {
        this.lhVc = value;
    }

    /**
     * Gets the value of the lVcGaineMTA property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLVcGaineMTA() {
        return lVcGaineMTA;
    }

    /**
     * Sets the value of the lVcGaineMTA property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLVcGaineMTA(double value) {
        this.lVcGaineMTA = value;
    }

    /**
     * Gets the value of the lhVcGaineMTA property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLHVcGaineMTA() {
        return lhVcGaineMTA;
    }

    /**
     * Sets the value of the lhVcGaineMTA property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLHVcGaineMTA(double value) {
        this.lhVcGaineMTA = value;
    }

    /**
     * Gets the value of the pAux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPAux() {
        return pAux;
    }

    /**
     * Sets the value of the pAux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPAux(double value) {
        this.pAux = value;
    }

    /**
     * Gets the value of the pCircVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPCircVc() {
        return pCircVc;
    }

    /**
     * Sets the value of the pCircVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPCircVc(double value) {
        this.pCircVc = value;
    }

    /**
     * Gets the value of the uMoyVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUMoyVc() {
        return uMoyVc;
    }

    /**
     * Sets the value of the uMoyVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUMoyVc(double value) {
        this.uMoyVc = value;
    }

    /**
     * Gets the value of the uMoyHVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUMoyHVc() {
        return uMoyHVc;
    }

    /**
     * Sets the value of the uMoyHVc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUMoyHVc(double value) {
        this.uMoyHVc = value;
    }

    /**
     * Gets the value of the uMoyVcGainesModules property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUMoyVcGainesModules() {
        return uMoyVcGainesModules;
    }

    /**
     * Sets the value of the uMoyVcGainesModules property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUMoyVcGainesModules(double value) {
        this.uMoyVcGainesModules = value;
    }

    /**
     * Gets the value of the uMoyHVcGainesModules property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUMoyHVcGainesModules() {
        return uMoyHVcGainesModules;
    }

    /**
     * Sets the value of the uMoyHVcGainesModules property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUMoyHVcGainesModules(double value) {
        this.uMoyHVcGainesModules = value;
    }

    /**
     * Gets the value of the deltaReseauMixteMaintienTemperature property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaReseauMixteMaintienTemperature() {
        return deltaReseauMixteMaintienTemperature;
    }

    /**
     * Sets the value of the deltaReseauMixteMaintienTemperature property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaReseauMixteMaintienTemperature(double value) {
        this.deltaReseauMixteMaintienTemperature = value;
    }

    /**
     * Gets the value of the typeSortieExcel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getTypeSortieExcel() {
        return typeSortieExcel;
    }

    /**
     * Sets the value of the typeSortieExcel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeSortieExcel(int value) {
        this.typeSortieExcel = value;
    }

}
