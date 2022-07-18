
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Heliopacsystem_DS_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Heliopacsystem_DS_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_capteur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_capt_masq" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="K_theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eta_0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bu" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcirc_prim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonct_Systeme" type="{}E_Fonct_Systeme"/>
 *         &lt;element name="Tcons_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tcons_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Performances" type="{}E_Statut_Performances"/>
 *         &lt;element name="COP_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="COP_10_65" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_10_65" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcirc_second" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Type_Emetteur"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Heliopacsystem_DS_Data", propOrder = {
    "name",
    "rdim",
    "index",
    "alpha",
    "beta",
    "sCapteur",
    "ratCaptMasq",
    "ue",
    "kTheta",
    "eta0",
    "bu",
    "b1",
    "b2",
    "pcircPrim",
    "fonctSysteme",
    "tconsHiver",
    "tconsEte",
    "statutPerformances",
    "copPivot",
    "pabsPivot",
    "cop1065",
    "pabs1065",
    "statutTaux",
    "taux",
    "pcircSecond",
    "typoEmetteurCh"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class HeliopacsystemDSData {

    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Rdim")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int rdim;
    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Alpha")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double alpha;
    @XmlElement(name = "Beta")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double beta;
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
    @XmlElement(name = "Fonct_Systeme", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String fonctSysteme;
    @XmlElement(name = "Tcons_hiver")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tconsHiver;
    @XmlElement(name = "Tcons_ete")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tconsEte;
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
    @XmlElement(name = "Statut_Taux", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutTaux;
    @XmlElement(name = "Taux")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double taux;
    @XmlElement(name = "Pcirc_second")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pcircSecond;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typoEmetteurCh;

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
     * Gets the value of the alpha property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAlpha(double value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBeta(double value) {
        this.beta = value;
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
     * Gets the value of the fonctSysteme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getFonctSysteme() {
        return fonctSysteme;
    }

    /**
     * Sets the value of the fonctSysteme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFonctSysteme(String value) {
        this.fonctSysteme = value;
    }

    /**
     * Gets the value of the tconsHiver property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTconsHiver() {
        return tconsHiver;
    }

    /**
     * Sets the value of the tconsHiver property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTconsHiver(double value) {
        this.tconsHiver = value;
    }

    /**
     * Gets the value of the tconsEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTconsEte() {
        return tconsEte;
    }

    /**
     * Sets the value of the tconsEte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTconsEte(double value) {
        this.tconsEte = value;
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
     * Gets the value of the statutTaux property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutTaux() {
        return statutTaux;
    }

    /**
     * Sets the value of the statutTaux property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutTaux(String value) {
        this.statutTaux = value;
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

    /**
     * Gets the value of the typoEmetteurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypoEmetteurCh() {
        return typoEmetteurCh;
    }

    /**
     * Sets the value of the typoEmetteurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypoEmetteurCh(String value) {
        this.typoEmetteurCh = value;
    }

}
