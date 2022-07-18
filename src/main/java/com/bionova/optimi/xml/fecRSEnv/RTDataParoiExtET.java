
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Paroi_Ext_ET complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Paroi_Ext_ET">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Aue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Uue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Swe_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Swe_sp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tle_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tle_sp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rouv_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Masque_Collection" type="{}ArrayOfChoice2" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Paroi_Ext_ET", propOrder = {

})
public class RTDataParoiExtET {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "Aue")
    protected double aue;
    @XmlElement(name = "Uue")
    protected double uue;
    @XmlElement(name = "Swe_ap")
    protected double sweAp;
    @XmlElement(name = "Swe_sp")
    protected double sweSp;
    @XmlElement(name = "Tle_ap")
    protected double tleAp;
    @XmlElement(name = "Tle_sp")
    protected double tleSp;
    @XmlElement(name = "Rouv_max")
    protected double rouvMax;
    @XmlElement(name = "Masque_Collection")
    protected ArrayOfChoice2 masqueCollection;

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
     * Gets the value of the alpha property.
     * 
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     */
    public void setAlpha(double value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     */
    public void setBeta(double value) {
        this.beta = value;
    }

    /**
     * Gets the value of the aue property.
     * 
     */
    public double getAue() {
        return aue;
    }

    /**
     * Sets the value of the aue property.
     * 
     */
    public void setAue(double value) {
        this.aue = value;
    }

    /**
     * Gets the value of the uue property.
     * 
     */
    public double getUue() {
        return uue;
    }

    /**
     * Sets the value of the uue property.
     * 
     */
    public void setUue(double value) {
        this.uue = value;
    }

    /**
     * Gets the value of the sweAp property.
     * 
     */
    public double getSweAp() {
        return sweAp;
    }

    /**
     * Sets the value of the sweAp property.
     * 
     */
    public void setSweAp(double value) {
        this.sweAp = value;
    }

    /**
     * Gets the value of the sweSp property.
     * 
     */
    public double getSweSp() {
        return sweSp;
    }

    /**
     * Sets the value of the sweSp property.
     * 
     */
    public void setSweSp(double value) {
        this.sweSp = value;
    }

    /**
     * Gets the value of the tleAp property.
     * 
     */
    public double getTleAp() {
        return tleAp;
    }

    /**
     * Sets the value of the tleAp property.
     * 
     */
    public void setTleAp(double value) {
        this.tleAp = value;
    }

    /**
     * Gets the value of the tleSp property.
     * 
     */
    public double getTleSp() {
        return tleSp;
    }

    /**
     * Sets the value of the tleSp property.
     * 
     */
    public void setTleSp(double value) {
        this.tleSp = value;
    }

    /**
     * Gets the value of the rouvMax property.
     * 
     */
    public double getRouvMax() {
        return rouvMax;
    }

    /**
     * Sets the value of the rouvMax property.
     * 
     */
    public void setRouvMax(double value) {
        this.rouvMax = value;
    }

    /**
     * Gets the value of the masqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice2 }
     *     
     */
    public ArrayOfChoice2 getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice2 }
     *     
     */
    public void setMasqueCollection(ArrayOfChoice2 value) {
        this.masqueCollection = value;
    }

}
