
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Coefficients modulateurs icenergie
 * 
 * <p>Java class for t_coef_mod_icenergie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_coef_mod_icenergie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="mcgeo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mccombles" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mcsurf_moy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mcsurf_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mccat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ic_energie_maxmoyen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_coef_mod_icenergie", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class TCoefModIcenergie {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double mcgeo;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double mccombles;
    @XmlElement(name = "mcsurf_moy")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double mcsurfMoy;
    @XmlElement(name = "mcsurf_tot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double mcsurfTot;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double mccat;
    @XmlElement(name = "ic_energie_maxmoyen")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int icEnergieMaxmoyen;

    /**
     * Gets the value of the mcgeo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getMcgeo() {
        return mcgeo;
    }

    /**
     * Sets the value of the mcgeo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMcgeo(double value) {
        this.mcgeo = value;
    }

    /**
     * Gets the value of the mccombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getMccombles() {
        return mccombles;
    }

    /**
     * Sets the value of the mccombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMccombles(double value) {
        this.mccombles = value;
    }

    /**
     * Gets the value of the mcsurfMoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getMcsurfMoy() {
        return mcsurfMoy;
    }

    /**
     * Sets the value of the mcsurfMoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMcsurfMoy(double value) {
        this.mcsurfMoy = value;
    }

    /**
     * Gets the value of the mcsurfTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getMcsurfTot() {
        return mcsurfTot;
    }

    /**
     * Sets the value of the mcsurfTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMcsurfTot(double value) {
        this.mcsurfTot = value;
    }

    /**
     * Gets the value of the mccat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getMccat() {
        return mccat;
    }

    /**
     * Sets the value of the mccat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMccat(double value) {
        this.mccat = value;
    }

    /**
     * Gets the value of the icEnergieMaxmoyen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIcEnergieMaxmoyen() {
        return icEnergieMaxmoyen;
    }

    /**
     * Sets the value of the icEnergieMaxmoyen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIcEnergieMaxmoyen(int value) {
        this.icEnergieMaxmoyen = value;
    }

}
