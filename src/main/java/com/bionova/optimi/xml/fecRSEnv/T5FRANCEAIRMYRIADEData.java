
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_FRANCEAIR_MYRIADE
 * 
 * <p>Java class for T5_FRANCEAIR_MYRIADE_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_FRANCEAIR_MYRIADE_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Delta_Theta_Evap_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_eau_glycolee" type="{}RT_Oui_Non"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_recup_eau_de_ville" type="{}RT_Oui_Non"/>
 *         &lt;element name="UA_ech_eaudeville" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_recup_ventilation" type="{}RT_Oui_Non"/>
 *         &lt;element name="Id_DF_SF_Extraction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="P_pompe_C4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Eff" type="{}E_Valeur_Certifie_Justifiee_Declaree"/>
 *         &lt;element name="Eff" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_CTA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_boucle_ventil" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_recup_EG" type="{}E_type_recup_EG"/>
 *         &lt;element name="P_pompe_EG" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_eau_EG" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_EG" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_cuve_EG" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_cuve_EG" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ul" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lcanal_hor" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eff_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="C_trans" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ECS_EG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_FRANCEAIR_MYRIADE_Data", propOrder = {

})
public class T5FRANCEAIRMYRIADEData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Delta_Theta_Evap_Ch")
    protected double deltaThetaEvapCh;
    @XmlElement(name = "Is_eau_glycolee", required = true)
    protected String isEauGlycolee;
    @XmlElement(name = "V_tot")
    protected double vTot;
    @XmlElement(name = "UA_s")
    protected double uas;
    @XmlElement(name = "Theta_Max")
    protected double thetaMax;
    @XmlElement(name = "Is_recup_eau_de_ville", required = true)
    protected String isRecupEauDeVille;
    @XmlElement(name = "UA_ech_eaudeville")
    protected double uaEchEaudeville;
    @XmlElement(name = "Is_recup_ventilation", required = true)
    protected String isRecupVentilation;
    @XmlElement(name = "Id_DF_SF_Extraction")
    protected int idDFSFExtraction;
    @XmlElement(name = "P_pompe_C4")
    protected double pPompeC4;
    @XmlElement(name = "Q_eau")
    protected double qEau;
    @XmlElement(name = "Statut_Eff", required = true)
    protected String statutEff;
    @XmlElement(name = "Eff")
    protected double eff;
    @XmlElement(name = "L_CTA")
    protected double lcta;
    @XmlElement(name = "U_boucle_ventil")
    protected double uBoucleVentil;
    @XmlElement(name = "Type_recup_EG", required = true)
    protected String typeRecupEG;
    @XmlElement(name = "P_pompe_EG")
    protected double pPompeEG;
    @XmlElement(name = "Q_eau_EG")
    protected double qEauEG;
    @XmlElement(name = "UA_EG")
    protected double uaeg;
    @XmlElement(name = "UA_cuve_EG")
    protected double uaCuveEG;
    @XmlElement(name = "V_cuve_EG")
    protected double vCuveEG;
    @XmlElement(name = "Ul")
    protected double ul;
    @XmlElement(name = "Lcanal_hor")
    protected double lcanalHor;
    @XmlElement(name = "Eff_nom")
    protected double effNom;
    @XmlElement(name = "C_trans")
    protected double cTrans;
    @XmlElement(name = "ECS_EG")
    protected String ecseg;

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
     * Gets the value of the deltaThetaEvapCh property.
     * 
     */
    public double getDeltaThetaEvapCh() {
        return deltaThetaEvapCh;
    }

    /**
     * Sets the value of the deltaThetaEvapCh property.
     * 
     */
    public void setDeltaThetaEvapCh(double value) {
        this.deltaThetaEvapCh = value;
    }

    /**
     * Gets the value of the isEauGlycolee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsEauGlycolee() {
        return isEauGlycolee;
    }

    /**
     * Sets the value of the isEauGlycolee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsEauGlycolee(String value) {
        this.isEauGlycolee = value;
    }

    /**
     * Gets the value of the vTot property.
     * 
     */
    public double getVTot() {
        return vTot;
    }

    /**
     * Sets the value of the vTot property.
     * 
     */
    public void setVTot(double value) {
        this.vTot = value;
    }

    /**
     * Gets the value of the uas property.
     * 
     */
    public double getUAS() {
        return uas;
    }

    /**
     * Sets the value of the uas property.
     * 
     */
    public void setUAS(double value) {
        this.uas = value;
    }

    /**
     * Gets the value of the thetaMax property.
     * 
     */
    public double getThetaMax() {
        return thetaMax;
    }

    /**
     * Sets the value of the thetaMax property.
     * 
     */
    public void setThetaMax(double value) {
        this.thetaMax = value;
    }

    /**
     * Gets the value of the isRecupEauDeVille property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRecupEauDeVille() {
        return isRecupEauDeVille;
    }

    /**
     * Sets the value of the isRecupEauDeVille property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRecupEauDeVille(String value) {
        this.isRecupEauDeVille = value;
    }

    /**
     * Gets the value of the uaEchEaudeville property.
     * 
     */
    public double getUAEchEaudeville() {
        return uaEchEaudeville;
    }

    /**
     * Sets the value of the uaEchEaudeville property.
     * 
     */
    public void setUAEchEaudeville(double value) {
        this.uaEchEaudeville = value;
    }

    /**
     * Gets the value of the isRecupVentilation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRecupVentilation() {
        return isRecupVentilation;
    }

    /**
     * Sets the value of the isRecupVentilation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRecupVentilation(String value) {
        this.isRecupVentilation = value;
    }

    /**
     * Gets the value of the idDFSFExtraction property.
     * 
     */
    public int getIdDFSFExtraction() {
        return idDFSFExtraction;
    }

    /**
     * Sets the value of the idDFSFExtraction property.
     * 
     */
    public void setIdDFSFExtraction(int value) {
        this.idDFSFExtraction = value;
    }

    /**
     * Gets the value of the pPompeC4 property.
     * 
     */
    public double getPPompeC4() {
        return pPompeC4;
    }

    /**
     * Sets the value of the pPompeC4 property.
     * 
     */
    public void setPPompeC4(double value) {
        this.pPompeC4 = value;
    }

    /**
     * Gets the value of the qEau property.
     * 
     */
    public double getQEau() {
        return qEau;
    }

    /**
     * Sets the value of the qEau property.
     * 
     */
    public void setQEau(double value) {
        this.qEau = value;
    }

    /**
     * Gets the value of the statutEff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutEff() {
        return statutEff;
    }

    /**
     * Sets the value of the statutEff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutEff(String value) {
        this.statutEff = value;
    }

    /**
     * Gets the value of the eff property.
     * 
     */
    public double getEff() {
        return eff;
    }

    /**
     * Sets the value of the eff property.
     * 
     */
    public void setEff(double value) {
        this.eff = value;
    }

    /**
     * Gets the value of the lcta property.
     * 
     */
    public double getLCTA() {
        return lcta;
    }

    /**
     * Sets the value of the lcta property.
     * 
     */
    public void setLCTA(double value) {
        this.lcta = value;
    }

    /**
     * Gets the value of the uBoucleVentil property.
     * 
     */
    public double getUBoucleVentil() {
        return uBoucleVentil;
    }

    /**
     * Sets the value of the uBoucleVentil property.
     * 
     */
    public void setUBoucleVentil(double value) {
        this.uBoucleVentil = value;
    }

    /**
     * Gets the value of the typeRecupEG property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeRecupEG() {
        return typeRecupEG;
    }

    /**
     * Sets the value of the typeRecupEG property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeRecupEG(String value) {
        this.typeRecupEG = value;
    }

    /**
     * Gets the value of the pPompeEG property.
     * 
     */
    public double getPPompeEG() {
        return pPompeEG;
    }

    /**
     * Sets the value of the pPompeEG property.
     * 
     */
    public void setPPompeEG(double value) {
        this.pPompeEG = value;
    }

    /**
     * Gets the value of the qEauEG property.
     * 
     */
    public double getQEauEG() {
        return qEauEG;
    }

    /**
     * Sets the value of the qEauEG property.
     * 
     */
    public void setQEauEG(double value) {
        this.qEauEG = value;
    }

    /**
     * Gets the value of the uaeg property.
     * 
     */
    public double getUAEG() {
        return uaeg;
    }

    /**
     * Sets the value of the uaeg property.
     * 
     */
    public void setUAEG(double value) {
        this.uaeg = value;
    }

    /**
     * Gets the value of the uaCuveEG property.
     * 
     */
    public double getUACuveEG() {
        return uaCuveEG;
    }

    /**
     * Sets the value of the uaCuveEG property.
     * 
     */
    public void setUACuveEG(double value) {
        this.uaCuveEG = value;
    }

    /**
     * Gets the value of the vCuveEG property.
     * 
     */
    public double getVCuveEG() {
        return vCuveEG;
    }

    /**
     * Sets the value of the vCuveEG property.
     * 
     */
    public void setVCuveEG(double value) {
        this.vCuveEG = value;
    }

    /**
     * Gets the value of the ul property.
     * 
     */
    public double getUl() {
        return ul;
    }

    /**
     * Sets the value of the ul property.
     * 
     */
    public void setUl(double value) {
        this.ul = value;
    }

    /**
     * Gets the value of the lcanalHor property.
     * 
     */
    public double getLcanalHor() {
        return lcanalHor;
    }

    /**
     * Sets the value of the lcanalHor property.
     * 
     */
    public void setLcanalHor(double value) {
        this.lcanalHor = value;
    }

    /**
     * Gets the value of the effNom property.
     * 
     */
    public double getEffNom() {
        return effNom;
    }

    /**
     * Sets the value of the effNom property.
     * 
     */
    public void setEffNom(double value) {
        this.effNom = value;
    }

    /**
     * Gets the value of the cTrans property.
     * 
     */
    public double getCTrans() {
        return cTrans;
    }

    /**
     * Sets the value of the cTrans property.
     * 
     */
    public void setCTrans(double value) {
        this.cTrans = value;
    }

    /**
     * Gets the value of the ecseg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getECSEG() {
        return ecseg;
    }

    /**
     * Sets the value of the ecseg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setECSEG(String value) {
        this.ecseg = value;
    }

}
