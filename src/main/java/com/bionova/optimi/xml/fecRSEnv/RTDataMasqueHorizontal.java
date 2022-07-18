
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Masque_Horizontal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Masque_Horizontal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dhm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Dhp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hp_b" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Masque_Horizontal", propOrder = {

})
public class RTDataMasqueHorizontal {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Dhm")
    protected double dhm;
    @XmlElement(name = "Dhp")
    protected double dhp;
    @XmlElement(name = "hp_b")
    protected double hpB;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the dhm property.
     * 
     */
    public double getDhm() {
        return dhm;
    }

    /**
     * Sets the value of the dhm property.
     * 
     */
    public void setDhm(double value) {
        this.dhm = value;
    }

    /**
     * Gets the value of the dhp property.
     * 
     */
    public double getDhp() {
        return dhp;
    }

    /**
     * Sets the value of the dhp property.
     * 
     */
    public void setDhp(double value) {
        this.dhp = value;
    }

    /**
     * Gets the value of the hpB property.
     * 
     */
    public double getHpB() {
        return hpB;
    }

    /**
     * Sets the value of the hpB property.
     * 
     */
    public void setHpB(double value) {
        this.hpB = value;
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

}
