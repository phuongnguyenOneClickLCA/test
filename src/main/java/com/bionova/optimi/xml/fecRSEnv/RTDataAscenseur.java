
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * v8000
 * 
 * <p>Java class for RT_Data_Ascenseur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Ascenseur">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Objet_Base">
 *       &lt;sequence>
 *         &lt;element name="H" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Netage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="C" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Q" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="TechMac" type="{}E_Typologie_Ascenseur"/>
 *         &lt;element name="Cp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ScVeille" type="{}RT_Oui_Non"/>
 *         &lt;element name="Pti" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dP1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dP2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Ascenseur", propOrder = {
    "h",
    "netage",
    "c",
    "q",
    "v",
    "techMac",
    "cp",
    "scVeille",
    "pti",
    "dp1",
    "t1",
    "dp2",
    "t2"
})
public class RTDataAscenseur
    extends RTDataObjetBase
{

    @XmlElement(name = "H")
    protected double h;
    @XmlElement(name = "Netage")
    protected int netage;
    @XmlElement(name = "C")
    protected String c;
    @XmlElement(name = "Q")
    protected double q;
    @XmlElement(name = "V")
    protected double v;
    @XmlElement(name = "TechMac", required = true)
    protected String techMac;
    @XmlElement(name = "Cp")
    protected double cp;
    @XmlElement(name = "ScVeille", required = true)
    protected String scVeille;
    @XmlElement(name = "Pti")
    protected double pti;
    @XmlElement(name = "dP1")
    protected double dp1;
    @XmlElement(name = "T1")
    protected double t1;
    @XmlElement(name = "dP2")
    protected double dp2;
    @XmlElement(name = "T2")
    protected double t2;

    /**
     * Gets the value of the h property.
     * 
     */
    public double getH() {
        return h;
    }

    /**
     * Sets the value of the h property.
     * 
     */
    public void setH(double value) {
        this.h = value;
    }

    /**
     * Gets the value of the netage property.
     * 
     */
    public int getNetage() {
        return netage;
    }

    /**
     * Sets the value of the netage property.
     * 
     */
    public void setNetage(int value) {
        this.netage = value;
    }

    /**
     * Gets the value of the c property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC() {
        return c;
    }

    /**
     * Sets the value of the c property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC(String value) {
        this.c = value;
    }

    /**
     * Gets the value of the q property.
     * 
     */
    public double getQ() {
        return q;
    }

    /**
     * Sets the value of the q property.
     * 
     */
    public void setQ(double value) {
        this.q = value;
    }

    /**
     * Gets the value of the v property.
     * 
     */
    public double getV() {
        return v;
    }

    /**
     * Sets the value of the v property.
     * 
     */
    public void setV(double value) {
        this.v = value;
    }

    /**
     * Gets the value of the techMac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechMac() {
        return techMac;
    }

    /**
     * Sets the value of the techMac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechMac(String value) {
        this.techMac = value;
    }

    /**
     * Gets the value of the cp property.
     * 
     */
    public double getCp() {
        return cp;
    }

    /**
     * Sets the value of the cp property.
     * 
     */
    public void setCp(double value) {
        this.cp = value;
    }

    /**
     * Gets the value of the scVeille property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScVeille() {
        return scVeille;
    }

    /**
     * Sets the value of the scVeille property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScVeille(String value) {
        this.scVeille = value;
    }

    /**
     * Gets the value of the pti property.
     * 
     */
    public double getPti() {
        return pti;
    }

    /**
     * Sets the value of the pti property.
     * 
     */
    public void setPti(double value) {
        this.pti = value;
    }

    /**
     * Gets the value of the dp1 property.
     * 
     */
    public double getDP1() {
        return dp1;
    }

    /**
     * Sets the value of the dp1 property.
     * 
     */
    public void setDP1(double value) {
        this.dp1 = value;
    }

    /**
     * Gets the value of the t1 property.
     * 
     */
    public double getT1() {
        return t1;
    }

    /**
     * Sets the value of the t1 property.
     * 
     */
    public void setT1(double value) {
        this.t1 = value;
    }

    /**
     * Gets the value of the dp2 property.
     * 
     */
    public double getDP2() {
        return dp2;
    }

    /**
     * Sets the value of the dp2 property.
     * 
     */
    public void setDP2(double value) {
        this.dp2 = value;
    }

    /**
     * Gets the value of the t2 property.
     * 
     */
    public double getT2() {
        return t2;
    }

    /**
     * Sets the value of the t2 property.
     * 
     */
    public void setT2(double value) {
        this.t2 = value;
    }

}
