
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Heliopac_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Heliopac_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" type="{}ArrayOfChoice1" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Base_Collection" type="{}ArrayOfRTSourceBallonBase" minOccurs="0"/>
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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Heliopac_Data", propOrder = {
    "index",
    "name",
    "sourceBallonAppointCollection",
    "sourceBallonBaseCollection",
    "vtotStrat",
    "vtotStock",
    "statutUAStock",
    "uaStock",
    "statutUAStrat",
    "uaStrat",
    "sCapteur",
    "ratCaptMasq",
    "ue",
    "kTheta",
    "eta0",
    "bu",
    "b1",
    "b2",
    "pcircPrim",
    "typeSysteme",
    "rdim",
    "statutPerformances",
    "copPivot",
    "pabsPivot",
    "cop1065",
    "pabs1065",
    "taux",
    "pcircSecond"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class HeliopacData {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfChoice1 sourceBallonAppointCollection;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTSourceBallonBase sourceBallonBaseCollection;
    @XmlElement(name = "Vtot_strat")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double vtotStrat;
    @XmlElement(name = "Vtot_stock")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double vtotStock;
    @XmlElement(name = "Statut_UA_stock", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutUAStock;
    @XmlElement(name = "UA_stock")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uaStock;
    @XmlElement(name = "Statut_UA_strat", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutUAStrat;
    @XmlElement(name = "UA_strat")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uaStrat;
    @XmlElement(name = "S_capteur")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double sCapteur;
    @XmlElement(name = "Rat_capt_masq")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratCaptMasq;
    @XmlElement(name = "Ue")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ue;
    @XmlElement(name = "K_theta")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double kTheta;
    @XmlElement(name = "Eta_0")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double eta0;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double bu;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double b1;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double b2;
    @XmlElement(name = "Pcirc_prim")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pcircPrim;
    @XmlElement(name = "Type_Systeme", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeSysteme;
    @XmlElement(name = "Rdim")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int rdim;
    @XmlElement(name = "Statut_Performances", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutPerformances;
    @XmlElement(name = "COP_pivot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double copPivot;
    @XmlElement(name = "Pabs_pivot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pabsPivot;
    @XmlElement(name = "COP_10_65")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double cop1065;
    @XmlElement(name = "Pabs_10_65")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pabs1065;
    @XmlElement(name = "Taux")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double taux;
    @XmlElement(name = "Pcirc_second")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pcircSecond;

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
     * Gets the value of the sourceBallonAppointCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSourceBallonBaseCollection(ArrayOfRTSourceBallonBase value) {
        this.sourceBallonBaseCollection = value;
    }

    /**
     * Gets the value of the vtotStrat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVtotStrat() {
        return vtotStrat;
    }

    /**
     * Sets the value of the vtotStrat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVtotStrat(double value) {
        this.vtotStrat = value;
    }

    /**
     * Gets the value of the vtotStock property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVtotStock() {
        return vtotStock;
    }

    /**
     * Sets the value of the vtotStock property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutUAStock(String value) {
        this.statutUAStock = value;
    }

    /**
     * Gets the value of the uaStock property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUAStock() {
        return uaStock;
    }

    /**
     * Sets the value of the uaStock property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutUAStrat(String value) {
        this.statutUAStrat = value;
    }

    /**
     * Gets the value of the uaStrat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUAStrat() {
        return uaStrat;
    }

    /**
     * Sets the value of the uaStrat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUAStrat(double value) {
        this.uaStrat = value;
    }

    /**
     * Gets the value of the sCapteur property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSCapteur() {
        return sCapteur;
    }

    /**
     * Sets the value of the sCapteur property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSCapteur(double value) {
        this.sCapteur = value;
    }

    /**
     * Gets the value of the ratCaptMasq property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatCaptMasq() {
        return ratCaptMasq;
    }

    /**
     * Sets the value of the ratCaptMasq property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatCaptMasq(double value) {
        this.ratCaptMasq = value;
    }

    /**
     * Gets the value of the ue property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUe() {
        return ue;
    }

    /**
     * Sets the value of the ue property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUe(double value) {
        this.ue = value;
    }

    /**
     * Gets the value of the kTheta property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getKTheta() {
        return kTheta;
    }

    /**
     * Sets the value of the kTheta property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setKTheta(double value) {
        this.kTheta = value;
    }

    /**
     * Gets the value of the eta0 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEta0() {
        return eta0;
    }

    /**
     * Sets the value of the eta0 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEta0(double value) {
        this.eta0 = value;
    }

    /**
     * Gets the value of the bu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getBu() {
        return bu;
    }

    /**
     * Sets the value of the bu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBu(double value) {
        this.bu = value;
    }

    /**
     * Gets the value of the b1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getB1() {
        return b1;
    }

    /**
     * Sets the value of the b1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setB1(double value) {
        this.b1 = value;
    }

    /**
     * Gets the value of the b2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getB2() {
        return b2;
    }

    /**
     * Sets the value of the b2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setB2(double value) {
        this.b2 = value;
    }

    /**
     * Gets the value of the pcircPrim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPcircPrim() {
        return pcircPrim;
    }

    /**
     * Sets the value of the pcircPrim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeSysteme(String value) {
        this.typeSysteme = value;
    }

    /**
     * Gets the value of the rdim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutPerformances(String value) {
        this.statutPerformances = value;
    }

    /**
     * Gets the value of the copPivot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCOPPivot() {
        return copPivot;
    }

    /**
     * Sets the value of the copPivot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCOPPivot(double value) {
        this.copPivot = value;
    }

    /**
     * Gets the value of the pabsPivot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPabsPivot() {
        return pabsPivot;
    }

    /**
     * Sets the value of the pabsPivot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPabsPivot(double value) {
        this.pabsPivot = value;
    }

    /**
     * Gets the value of the cop1065 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCOP1065() {
        return cop1065;
    }

    /**
     * Sets the value of the cop1065 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCOP1065(double value) {
        this.cop1065 = value;
    }

    /**
     * Gets the value of the pabs1065 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPabs1065() {
        return pabs1065;
    }

    /**
     * Sets the value of the pabs1065 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPabs1065(double value) {
        this.pabs1065 = value;
    }

    /**
     * Gets the value of the taux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTaux() {
        return taux;
    }

    /**
     * Sets the value of the taux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTaux(double value) {
        this.taux = value;
    }

    /**
     * Gets the value of the pcircSecond property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPcircSecond() {
        return pcircSecond;
    }

    /**
     * Sets the value of the pcircSecond property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPcircSecond(double value) {
        this.pcircSecond = value;
    }

}
