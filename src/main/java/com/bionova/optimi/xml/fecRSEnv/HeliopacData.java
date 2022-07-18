
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7502 - 6/10/2017
 * 
 * <p>Java class for Heliopac_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Heliopac_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ArrayOfChoice1">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Source_Ballon_Base_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ArrayOfRTSourceBallonBase">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Vtot_strat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Vtot_stock" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_UA_stock" type="{}E_Statut_UA_stock"/>
 *         &lt;element name="UA_stock" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_UA_strat" type="{}E_Statut_UA_strat"/>
 *         &lt;element name="UA_strat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_capteur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_capt_masq" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="K_theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eta_0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bu" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcirc_prim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Systeme" type="{}E_Type_systeme"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Performances" type="{}E_Statut_Performances"/>
 *         &lt;element name="COP_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="COP_10_65" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_10_65" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcirc_second" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Heliopac_Data", propOrder = {

})
public class HeliopacData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    protected HeliopacData.SourceBallonAppointCollection sourceBallonAppointCollection;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected HeliopacData.SourceBallonBaseCollection sourceBallonBaseCollection;
    @XmlElement(name = "Vtot_strat")
    protected double vtotStrat;
    @XmlElement(name = "Vtot_stock")
    protected double vtotStock;
    @XmlElement(name = "Statut_UA_stock", required = true)
    protected String statutUAStock;
    @XmlElement(name = "UA_stock")
    protected double uaStock;
    @XmlElement(name = "Statut_UA_strat", required = true)
    protected String statutUAStrat;
    @XmlElement(name = "UA_strat")
    protected double uaStrat;
    @XmlElement(name = "S_capteur")
    protected double sCapteur;
    @XmlElement(name = "Rat_capt_masq")
    protected double ratCaptMasq;
    @XmlElement(name = "Ue")
    protected double ue;
    @XmlElement(name = "K_theta")
    protected double kTheta;
    @XmlElement(name = "Eta_0")
    protected double eta0;
    protected double bu;
    protected double b1;
    protected double b2;
    @XmlElement(name = "Pcirc_prim")
    protected double pcircPrim;
    @XmlElement(name = "Type_Systeme", required = true)
    protected String typeSysteme;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Statut_Performances", required = true)
    protected String statutPerformances;
    @XmlElement(name = "COP_pivot")
    protected double copPivot;
    @XmlElement(name = "Pabs_pivot")
    protected double pabsPivot;
    @XmlElement(name = "COP_10_65")
    protected double cop1065;
    @XmlElement(name = "Pabs_10_65")
    protected double pabs1065;
    @XmlElement(name = "Taux")
    protected double taux;
    @XmlElement(name = "Pcirc_second")
    protected double pcircSecond;

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
     * Gets the value of the sourceBallonAppointCollection property.
     * 
     * @return
     *     possible object is
     *     {@link HeliopacData.SourceBallonAppointCollection }
     *     
     */
    public HeliopacData.SourceBallonAppointCollection getSourceBallonAppointCollection() {
        return sourceBallonAppointCollection;
    }

    /**
     * Sets the value of the sourceBallonAppointCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeliopacData.SourceBallonAppointCollection }
     *     
     */
    public void setSourceBallonAppointCollection(HeliopacData.SourceBallonAppointCollection value) {
        this.sourceBallonAppointCollection = value;
    }

    /**
     * Gets the value of the sourceBallonBaseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link HeliopacData.SourceBallonBaseCollection }
     *     
     */
    public HeliopacData.SourceBallonBaseCollection getSourceBallonBaseCollection() {
        return sourceBallonBaseCollection;
    }

    /**
     * Sets the value of the sourceBallonBaseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeliopacData.SourceBallonBaseCollection }
     *     
     */
    public void setSourceBallonBaseCollection(HeliopacData.SourceBallonBaseCollection value) {
        this.sourceBallonBaseCollection = value;
    }

    /**
     * Gets the value of the vtotStrat property.
     * 
     */
    public double getVtotStrat() {
        return vtotStrat;
    }

    /**
     * Sets the value of the vtotStrat property.
     * 
     */
    public void setVtotStrat(double value) {
        this.vtotStrat = value;
    }

    /**
     * Gets the value of the vtotStock property.
     * 
     */
    public double getVtotStock() {
        return vtotStock;
    }

    /**
     * Sets the value of the vtotStock property.
     * 
     */
    public void setVtotStock(double value) {
        this.vtotStock = value;
    }

    /**
     * Gets the value of the statutUAStock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutUAStock() {
        return statutUAStock;
    }

    /**
     * Sets the value of the statutUAStock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutUAStock(String value) {
        this.statutUAStock = value;
    }

    /**
     * Gets the value of the uaStock property.
     * 
     */
    public double getUAStock() {
        return uaStock;
    }

    /**
     * Sets the value of the uaStock property.
     * 
     */
    public void setUAStock(double value) {
        this.uaStock = value;
    }

    /**
     * Gets the value of the statutUAStrat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutUAStrat() {
        return statutUAStrat;
    }

    /**
     * Sets the value of the statutUAStrat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutUAStrat(String value) {
        this.statutUAStrat = value;
    }

    /**
     * Gets the value of the uaStrat property.
     * 
     */
    public double getUAStrat() {
        return uaStrat;
    }

    /**
     * Sets the value of the uaStrat property.
     * 
     */
    public void setUAStrat(double value) {
        this.uaStrat = value;
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
     * Gets the value of the ratCaptMasq property.
     * 
     */
    public double getRatCaptMasq() {
        return ratCaptMasq;
    }

    /**
     * Sets the value of the ratCaptMasq property.
     * 
     */
    public void setRatCaptMasq(double value) {
        this.ratCaptMasq = value;
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
     * Gets the value of the kTheta property.
     * 
     */
    public double getKTheta() {
        return kTheta;
    }

    /**
     * Sets the value of the kTheta property.
     * 
     */
    public void setKTheta(double value) {
        this.kTheta = value;
    }

    /**
     * Gets the value of the eta0 property.
     * 
     */
    public double getEta0() {
        return eta0;
    }

    /**
     * Sets the value of the eta0 property.
     * 
     */
    public void setEta0(double value) {
        this.eta0 = value;
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
     * Gets the value of the pcircPrim property.
     * 
     */
    public double getPcircPrim() {
        return pcircPrim;
    }

    /**
     * Sets the value of the pcircPrim property.
     * 
     */
    public void setPcircPrim(double value) {
        this.pcircPrim = value;
    }

    /**
     * Gets the value of the typeSysteme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeSysteme() {
        return typeSysteme;
    }

    /**
     * Sets the value of the typeSysteme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeSysteme(String value) {
        this.typeSysteme = value;
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
     * Gets the value of the statutPerformances property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPerformances() {
        return statutPerformances;
    }

    /**
     * Sets the value of the statutPerformances property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPerformances(String value) {
        this.statutPerformances = value;
    }

    /**
     * Gets the value of the copPivot property.
     * 
     */
    public double getCOPPivot() {
        return copPivot;
    }

    /**
     * Sets the value of the copPivot property.
     * 
     */
    public void setCOPPivot(double value) {
        this.copPivot = value;
    }

    /**
     * Gets the value of the pabsPivot property.
     * 
     */
    public double getPabsPivot() {
        return pabsPivot;
    }

    /**
     * Sets the value of the pabsPivot property.
     * 
     */
    public void setPabsPivot(double value) {
        this.pabsPivot = value;
    }

    /**
     * Gets the value of the cop1065 property.
     * 
     */
    public double getCOP1065() {
        return cop1065;
    }

    /**
     * Sets the value of the cop1065 property.
     * 
     */
    public void setCOP1065(double value) {
        this.cop1065 = value;
    }

    /**
     * Gets the value of the pabs1065 property.
     * 
     */
    public double getPabs1065() {
        return pabs1065;
    }

    /**
     * Sets the value of the pabs1065 property.
     * 
     */
    public void setPabs1065(double value) {
        this.pabs1065 = value;
    }

    /**
     * Gets the value of the taux property.
     * 
     */
    public double getTaux() {
        return taux;
    }

    /**
     * Sets the value of the taux property.
     * 
     */
    public void setTaux(double value) {
        this.taux = value;
    }

    /**
     * Gets the value of the pcircSecond property.
     * 
     */
    public double getPcircSecond() {
        return pcircSecond;
    }

    /**
     * Sets the value of the pcircSecond property.
     * 
     */
    public void setPcircSecond(double value) {
        this.pcircSecond = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}ArrayOfChoice1">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SourceBallonAppointCollection
        extends ArrayOfChoice1
    {


    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}ArrayOfRTSourceBallonBase">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SourceBallonBaseCollection
        extends ArrayOfRTSourceBallonBase
    {


    }

}
