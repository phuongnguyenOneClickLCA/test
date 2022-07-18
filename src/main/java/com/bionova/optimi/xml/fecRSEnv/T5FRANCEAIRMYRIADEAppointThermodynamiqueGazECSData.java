
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_FRANCEAIR_MYRIADE
 * 
 * <p>Java class for T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_ECS_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_ECS_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo" type="{}E_Systeme_Thermodynamique_Double_Service"/>
 *         &lt;element name="Statut_Donnee_Ecs" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_Eau"/>
 *         &lt;element name="Performance_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_pc_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ecs" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Bruleur_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Bruleur_Ch" type="{}E_Fonctionnement_Bruleur"/>
 *         &lt;element name="Statut_Echangeur" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Echangeur_Eau_Ch_Fumee" type="{}E_Presence_Echangeur"/>
 *         &lt;element name="Statut_Autres_Donnees_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rdt_Comp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pertes_40deg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Temp_Aval" type="{}ArrayOfDouble" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_ECS_Data", propOrder = {
    "name",
    "index",
    "description",
    "rdim",
    "idprioriteEcs",
    "idSourceAmont",
    "sysThermo",
    "statutDonneeEcs",
    "thetaAvalEauGlycoleeEauEcs",
    "thetaAmontEauGlycoleeEauEcs",
    "thetaAvalEauEauEcs",
    "thetaAmontEauEauEcs",
    "performanceEcs",
    "pabsEcs",
    "corEcs",
    "pauxPcEcs",
    "statutValPivotEcs",
    "valGUEEcs",
    "valPabsEcs",
    "statutPauxPivotEcs",
    "valPauxPcEcs",
    "limThetaEcs",
    "thetaMaxAvEcs",
    "thetaMinAmEcs",
    "statutBruleurCh",
    "fonctionnementBruleurCh",
    "statutEchangeur",
    "echangeurEauChFumee",
    "statutAutresDonneesCh",
    "lRcontminCh",
    "ccplRcontminCh",
    "paux0",
    "rdtComp",
    "pertes40Deg",
    "valTempAval"
})
@XmlSeeAlso({
    T5FRANCEAIRMYRIADEAppointThermodynamiqueGazDSData.class
})
public class T5FRANCEAIRMYRIADEAppointThermodynamiqueGazECSData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Sys_Thermo", required = true)
    protected String sysThermo;
    @XmlElement(name = "Statut_Donnee_Ecs", required = true)
    protected String statutDonneeEcs;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Ecs", required = true)
    protected String thetaAvalEauGlycoleeEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Ecs", required = true)
    protected String thetaAmontEauGlycoleeEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Ecs", required = true)
    protected String thetaAvalEauEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Ecs", required = true)
    protected String thetaAmontEauEauEcs;
    @XmlElement(name = "Performance_Ecs")
    protected String performanceEcs;
    @XmlElement(name = "Pabs_Ecs")
    protected String pabsEcs;
    @XmlElement(name = "COR_Ecs")
    protected String corEcs;
    @XmlElement(name = "Paux_pc_Ecs")
    protected String pauxPcEcs;
    @XmlElement(name = "Statut_Val_Pivot_Ecs", required = true)
    protected String statutValPivotEcs;
    @XmlElement(name = "Val_GUE_Ecs")
    protected double valGUEEcs;
    @XmlElement(name = "Val_Pabs_Ecs")
    protected double valPabsEcs;
    @XmlElement(name = "Statut_Paux_Pivot_Ecs", required = true)
    protected String statutPauxPivotEcs;
    @XmlElement(name = "Val_Paux_pc_Ecs")
    protected double valPauxPcEcs;
    @XmlElement(name = "Lim_Theta_Ecs", required = true)
    protected String limThetaEcs;
    @XmlElement(name = "Theta_Max_Av_Ecs")
    protected double thetaMaxAvEcs;
    @XmlElement(name = "Theta_Min_Am_Ecs")
    protected double thetaMinAmEcs;
    @XmlElement(name = "Statut_Bruleur_Ch", required = true)
    protected String statutBruleurCh;
    @XmlElement(name = "Fonctionnement_Bruleur_Ch", required = true)
    protected String fonctionnementBruleurCh;
    @XmlElement(name = "Statut_Echangeur", required = true)
    protected String statutEchangeur;
    @XmlElement(name = "Echangeur_Eau_Ch_Fumee", required = true)
    protected String echangeurEauChFumee;
    @XmlElement(name = "Statut_Autres_Donnees_Ch", required = true)
    protected String statutAutresDonneesCh;
    @XmlElement(name = "LRcontmin_Ch")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Paux0")
    protected double paux0;
    @XmlElement(name = "Rdt_Comp")
    protected double rdtComp;
    @XmlElement(name = "Pertes_40deg")
    protected double pertes40Deg;
    @XmlElement(name = "Val_Temp_Aval")
    protected ArrayOfDouble valTempAval;

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
     * Gets the value of the rdim property.
     * 
     */
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    public void setRdim(int value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the idprioriteEcs property.
     * 
     */
    public int getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     */
    public void setIdprioriteEcs(int value) {
        this.idprioriteEcs = value;
    }

    /**
     * Gets the value of the idSourceAmont property.
     * 
     */
    public int getIdSourceAmont() {
        return idSourceAmont;
    }

    /**
     * Sets the value of the idSourceAmont property.
     * 
     */
    public void setIdSourceAmont(int value) {
        this.idSourceAmont = value;
    }

    /**
     * Gets the value of the sysThermo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermo() {
        return sysThermo;
    }

    /**
     * Sets the value of the sysThermo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermo(String value) {
        this.sysThermo = value;
    }

    /**
     * Gets the value of the statutDonneeEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeEcs() {
        return statutDonneeEcs;
    }

    /**
     * Sets the value of the statutDonneeEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeEcs(String value) {
        this.statutDonneeEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauEcs() {
        return thetaAvalEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauEcs(String value) {
        this.thetaAvalEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEauEcs() {
        return thetaAmontEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEauEcs(String value) {
        this.thetaAmontEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauEauEcs() {
        return thetaAvalEauEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauEauEcs(String value) {
        this.thetaAvalEauEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauEauEcs() {
        return thetaAmontEauEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauEauEcs(String value) {
        this.thetaAmontEauEauEcs = value;
    }

    /**
     * Gets the value of the performanceEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformanceEcs() {
        return performanceEcs;
    }

    /**
     * Sets the value of the performanceEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformanceEcs(String value) {
        this.performanceEcs = value;
    }

    /**
     * Gets the value of the pabsEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsEcs() {
        return pabsEcs;
    }

    /**
     * Sets the value of the pabsEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsEcs(String value) {
        this.pabsEcs = value;
    }

    /**
     * Gets the value of the corEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOREcs() {
        return corEcs;
    }

    /**
     * Sets the value of the corEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOREcs(String value) {
        this.corEcs = value;
    }

    /**
     * Gets the value of the pauxPcEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxPcEcs() {
        return pauxPcEcs;
    }

    /**
     * Sets the value of the pauxPcEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxPcEcs(String value) {
        this.pauxPcEcs = value;
    }

    /**
     * Gets the value of the statutValPivotEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotEcs() {
        return statutValPivotEcs;
    }

    /**
     * Sets the value of the statutValPivotEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotEcs(String value) {
        this.statutValPivotEcs = value;
    }

    /**
     * Gets the value of the valGUEEcs property.
     * 
     */
    public double getValGUEEcs() {
        return valGUEEcs;
    }

    /**
     * Sets the value of the valGUEEcs property.
     * 
     */
    public void setValGUEEcs(double value) {
        this.valGUEEcs = value;
    }

    /**
     * Gets the value of the valPabsEcs property.
     * 
     */
    public double getValPabsEcs() {
        return valPabsEcs;
    }

    /**
     * Sets the value of the valPabsEcs property.
     * 
     */
    public void setValPabsEcs(double value) {
        this.valPabsEcs = value;
    }

    /**
     * Gets the value of the statutPauxPivotEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxPivotEcs() {
        return statutPauxPivotEcs;
    }

    /**
     * Sets the value of the statutPauxPivotEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxPivotEcs(String value) {
        this.statutPauxPivotEcs = value;
    }

    /**
     * Gets the value of the valPauxPcEcs property.
     * 
     */
    public double getValPauxPcEcs() {
        return valPauxPcEcs;
    }

    /**
     * Sets the value of the valPauxPcEcs property.
     * 
     */
    public void setValPauxPcEcs(double value) {
        this.valPauxPcEcs = value;
    }

    /**
     * Gets the value of the limThetaEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimThetaEcs() {
        return limThetaEcs;
    }

    /**
     * Sets the value of the limThetaEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimThetaEcs(String value) {
        this.limThetaEcs = value;
    }

    /**
     * Gets the value of the thetaMaxAvEcs property.
     * 
     */
    public double getThetaMaxAvEcs() {
        return thetaMaxAvEcs;
    }

    /**
     * Sets the value of the thetaMaxAvEcs property.
     * 
     */
    public void setThetaMaxAvEcs(double value) {
        this.thetaMaxAvEcs = value;
    }

    /**
     * Gets the value of the thetaMinAmEcs property.
     * 
     */
    public double getThetaMinAmEcs() {
        return thetaMinAmEcs;
    }

    /**
     * Sets the value of the thetaMinAmEcs property.
     * 
     */
    public void setThetaMinAmEcs(double value) {
        this.thetaMinAmEcs = value;
    }

    /**
     * Gets the value of the statutBruleurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutBruleurCh() {
        return statutBruleurCh;
    }

    /**
     * Sets the value of the statutBruleurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutBruleurCh(String value) {
        this.statutBruleurCh = value;
    }

    /**
     * Gets the value of the fonctionnementBruleurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementBruleurCh() {
        return fonctionnementBruleurCh;
    }

    /**
     * Sets the value of the fonctionnementBruleurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementBruleurCh(String value) {
        this.fonctionnementBruleurCh = value;
    }

    /**
     * Gets the value of the statutEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutEchangeur() {
        return statutEchangeur;
    }

    /**
     * Sets the value of the statutEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutEchangeur(String value) {
        this.statutEchangeur = value;
    }

    /**
     * Gets the value of the echangeurEauChFumee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEchangeurEauChFumee() {
        return echangeurEauChFumee;
    }

    /**
     * Sets the value of the echangeurEauChFumee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEchangeurEauChFumee(String value) {
        this.echangeurEauChFumee = value;
    }

    /**
     * Gets the value of the statutAutresDonneesCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutAutresDonneesCh() {
        return statutAutresDonneesCh;
    }

    /**
     * Sets the value of the statutAutresDonneesCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutAutresDonneesCh(String value) {
        this.statutAutresDonneesCh = value;
    }

    /**
     * Gets the value of the lRcontminCh property.
     * 
     */
    public double getLRcontminCh() {
        return lRcontminCh;
    }

    /**
     * Sets the value of the lRcontminCh property.
     * 
     */
    public void setLRcontminCh(double value) {
        this.lRcontminCh = value;
    }

    /**
     * Gets the value of the ccplRcontminCh property.
     * 
     */
    public double getCCPLRcontminCh() {
        return ccplRcontminCh;
    }

    /**
     * Sets the value of the ccplRcontminCh property.
     * 
     */
    public void setCCPLRcontminCh(double value) {
        this.ccplRcontminCh = value;
    }

    /**
     * Gets the value of the paux0 property.
     * 
     */
    public double getPaux0() {
        return paux0;
    }

    /**
     * Sets the value of the paux0 property.
     * 
     */
    public void setPaux0(double value) {
        this.paux0 = value;
    }

    /**
     * Gets the value of the rdtComp property.
     * 
     */
    public double getRdtComp() {
        return rdtComp;
    }

    /**
     * Sets the value of the rdtComp property.
     * 
     */
    public void setRdtComp(double value) {
        this.rdtComp = value;
    }

    /**
     * Gets the value of the pertes40Deg property.
     * 
     */
    public double getPertes40Deg() {
        return pertes40Deg;
    }

    /**
     * Sets the value of the pertes40Deg property.
     * 
     */
    public void setPertes40Deg(double value) {
        this.pertes40Deg = value;
    }

    /**
     * Gets the value of the valTempAval property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getValTempAval() {
        return valTempAval;
    }

    /**
     * Sets the value of the valTempAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setValTempAval(ArrayOfDouble value) {
        this.valTempAval = value;
    }

}
