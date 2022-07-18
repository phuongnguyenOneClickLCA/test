
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_BIOFLUIDES-SOLARONICS_Eaux-grises - utilisé aussi par le T 6300 patch1 oct 2014
 * 
 * <p>Java class for T5_BIOFLUIDES_ERS_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_BIOFLUIDES_ERS_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="ECS_EG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="S_ech" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="V_dec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_ech" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_ballon_amont" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Theta_amont_min" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Theta_EG_min" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Nb_element" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_BIOFLUIDES_ERS_Data", propOrder = {

})
public class T5BIOFLUIDESERSData {

    @XmlElement(name = "ECS_EG")
    protected String ecseg;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "S_ech")
    protected Double sEch;
    @XmlElement(name = "V_dec")
    protected double vDec;
    @XmlElement(name = "V_ech")
    protected double vEch;
    @XmlElement(name = "V_ballon_amont")
    protected Double vBallonAmont;
    @XmlElement(name = "Theta_amont_min")
    protected Double thetaAmontMin;
    @XmlElement(name = "Theta_EG_min")
    protected Double thetaEGMin;
    @XmlElement(name = "Nb_element")
    protected Double nbElement;

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
     * Gets the value of the sEch property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSEch() {
        return sEch;
    }

    /**
     * Sets the value of the sEch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSEch(Double value) {
        this.sEch = value;
    }

    /**
     * Gets the value of the vDec property.
     * 
     */
    public double getVDec() {
        return vDec;
    }

    /**
     * Sets the value of the vDec property.
     * 
     */
    public void setVDec(double value) {
        this.vDec = value;
    }

    /**
     * Gets the value of the vEch property.
     * 
     */
    public double getVEch() {
        return vEch;
    }

    /**
     * Sets the value of the vEch property.
     * 
     */
    public void setVEch(double value) {
        this.vEch = value;
    }

    /**
     * Gets the value of the vBallonAmont property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVBallonAmont() {
        return vBallonAmont;
    }

    /**
     * Sets the value of the vBallonAmont property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVBallonAmont(Double value) {
        this.vBallonAmont = value;
    }

    /**
     * Gets the value of the thetaAmontMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaAmontMin() {
        return thetaAmontMin;
    }

    /**
     * Sets the value of the thetaAmontMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaAmontMin(Double value) {
        this.thetaAmontMin = value;
    }

    /**
     * Gets the value of the thetaEGMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaEGMin() {
        return thetaEGMin;
    }

    /**
     * Sets the value of the thetaEGMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaEGMin(Double value) {
        this.thetaEGMin = value;
    }

    /**
     * Gets the value of the nbElement property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNbElement() {
        return nbElement;
    }

    /**
     * Sets the value of the nbElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNbElement(Double value) {
        this.nbElement = value;
    }

}
