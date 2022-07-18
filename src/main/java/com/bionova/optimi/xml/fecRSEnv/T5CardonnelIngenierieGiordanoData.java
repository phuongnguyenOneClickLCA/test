
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5_Giordano_Production_Stockage
 * 
 * <p>Java class for T5_CardonnelIngenierie_Giordano_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CardonnelIngenierie_Giordano_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" type="{}ArrayOfChoice1" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Base_Collection" type="{}ArrayOfRTSourceBallonBase" minOccurs="0"/>
 *         &lt;element name="theta_b_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="statut_donnee_UA" type="{}E_statut_donnee_UA_Giordano"/>
 *         &lt;element name="statut_donnee_UA_solaire" type="{}E_statut_donnee_UA_solaire"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_solaire" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="delta_theta_c_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_capteur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="n_0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bu" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ui" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="f_aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="type_appoint" type="{}E_type_appoint"/>
 *         &lt;element name="Type_PAC" type="{}EType_PAC"/>
 *         &lt;element name="Solaire_direct" type="{}ESolaire_direct"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CardonnelIngenierie_Giordano_Data", propOrder = {

})
public class T5CardonnelIngenierieGiordanoData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    protected ArrayOfChoice1 sourceBallonAppointCollection;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected ArrayOfRTSourceBallonBase sourceBallonBaseCollection;
    @XmlElement(name = "theta_b_max")
    protected double thetaBMax;
    @XmlElement(name = "V_tot")
    protected double vTot;
    @XmlElement(name = "V_tot_sol")
    protected double vTotSol;
    @XmlElement(name = "statut_donnee_UA", required = true)
    protected String statutDonneeUA;
    @XmlElement(name = "statut_donnee_UA_solaire", required = true)
    protected String statutDonneeUASolaire;
    @XmlElement(name = "UA_s")
    protected double uas;
    @XmlElement(name = "UA_solaire")
    protected double uaSolaire;
    @XmlElement(name = "delta_theta_c_ap")
    protected double deltaThetaCAp;
    @XmlElement(name = "S_capteur")
    protected double sCapteur;
    @XmlElement(name = "n_0")
    protected double n0;
    protected double b1;
    protected double bu;
    protected double b2;
    @XmlElement(name = "Ue")
    protected double ue;
    @XmlElement(name = "Ui")
    protected double ui;
    @XmlElement(name = "f_aux")
    protected double fAux;
    @XmlElement(name = "type_appoint", required = true)
    protected String typeAppoint;
    @XmlElement(name = "Type_PAC", required = true)
    protected String typePAC;
    @XmlElement(name = "Solaire_direct", required = true)
    protected String solaireDirect;

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
     * Gets the value of the sourceBallonAppointCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    public ArrayOfChoice1 getSourceBallonAppointCollection() {
        return sourceBallonAppointCollection;
    }

    /**
     * Sets the value of the sourceBallonAppointCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    public void setSourceBallonAppointCollection(ArrayOfChoice1 value) {
        this.sourceBallonAppointCollection = value;
    }

    /**
     * Gets the value of the sourceBallonBaseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTSourceBallonBase }
     *     
     */
    public ArrayOfRTSourceBallonBase getSourceBallonBaseCollection() {
        return sourceBallonBaseCollection;
    }

    /**
     * Sets the value of the sourceBallonBaseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTSourceBallonBase }
     *     
     */
    public void setSourceBallonBaseCollection(ArrayOfRTSourceBallonBase value) {
        this.sourceBallonBaseCollection = value;
    }

    /**
     * Gets the value of the thetaBMax property.
     * 
     */
    public double getThetaBMax() {
        return thetaBMax;
    }

    /**
     * Sets the value of the thetaBMax property.
     * 
     */
    public void setThetaBMax(double value) {
        this.thetaBMax = value;
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
     * Gets the value of the vTotSol property.
     * 
     */
    public double getVTotSol() {
        return vTotSol;
    }

    /**
     * Sets the value of the vTotSol property.
     * 
     */
    public void setVTotSol(double value) {
        this.vTotSol = value;
    }

    /**
     * Gets the value of the statutDonneeUA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeUA() {
        return statutDonneeUA;
    }

    /**
     * Sets the value of the statutDonneeUA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeUA(String value) {
        this.statutDonneeUA = value;
    }

    /**
     * Gets the value of the statutDonneeUASolaire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeUASolaire() {
        return statutDonneeUASolaire;
    }

    /**
     * Sets the value of the statutDonneeUASolaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeUASolaire(String value) {
        this.statutDonneeUASolaire = value;
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
     * Gets the value of the uaSolaire property.
     * 
     */
    public double getUASolaire() {
        return uaSolaire;
    }

    /**
     * Sets the value of the uaSolaire property.
     * 
     */
    public void setUASolaire(double value) {
        this.uaSolaire = value;
    }

    /**
     * Gets the value of the deltaThetaCAp property.
     * 
     */
    public double getDeltaThetaCAp() {
        return deltaThetaCAp;
    }

    /**
     * Sets the value of the deltaThetaCAp property.
     * 
     */
    public void setDeltaThetaCAp(double value) {
        this.deltaThetaCAp = value;
    }

    /**
     * Gets the value of the sCapteur property.
     * 
     */
    public double getSCapteur() {
        return sCapteur;
    }

    /**
     * Sets the value of the sCapteur property.
     * 
     */
    public void setSCapteur(double value) {
        this.sCapteur = value;
    }

    /**
     * Gets the value of the n0 property.
     * 
     */
    public double getN0() {
        return n0;
    }

    /**
     * Sets the value of the n0 property.
     * 
     */
    public void setN0(double value) {
        this.n0 = value;
    }

    /**
     * Gets the value of the b1 property.
     * 
     */
    public double getB1() {
        return b1;
    }

    /**
     * Sets the value of the b1 property.
     * 
     */
    public void setB1(double value) {
        this.b1 = value;
    }

    /**
     * Gets the value of the bu property.
     * 
     */
    public double getBu() {
        return bu;
    }

    /**
     * Sets the value of the bu property.
     * 
     */
    public void setBu(double value) {
        this.bu = value;
    }

    /**
     * Gets the value of the b2 property.
     * 
     */
    public double getB2() {
        return b2;
    }

    /**
     * Sets the value of the b2 property.
     * 
     */
    public void setB2(double value) {
        this.b2 = value;
    }

    /**
     * Gets the value of the ue property.
     * 
     */
    public double getUe() {
        return ue;
    }

    /**
     * Sets the value of the ue property.
     * 
     */
    public void setUe(double value) {
        this.ue = value;
    }

    /**
     * Gets the value of the ui property.
     * 
     */
    public double getUi() {
        return ui;
    }

    /**
     * Sets the value of the ui property.
     * 
     */
    public void setUi(double value) {
        this.ui = value;
    }

    /**
     * Gets the value of the fAux property.
     * 
     */
    public double getFAux() {
        return fAux;
    }

    /**
     * Sets the value of the fAux property.
     * 
     */
    public void setFAux(double value) {
        this.fAux = value;
    }

    /**
     * Gets the value of the typeAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeAppoint() {
        return typeAppoint;
    }

    /**
     * Sets the value of the typeAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeAppoint(String value) {
        this.typeAppoint = value;
    }

    /**
     * Gets the value of the typePAC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePAC() {
        return typePAC;
    }

    /**
     * Sets the value of the typePAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePAC(String value) {
        this.typePAC = value;
    }

    /**
     * Gets the value of the solaireDirect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSolaireDirect() {
        return solaireDirect;
    }

    /**
     * Sets the value of the solaireDirect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSolaireDirect(String value) {
        this.solaireDirect = value;
    }

}
