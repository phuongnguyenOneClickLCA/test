
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Masque_Vert_Gauche complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Masque_Vert_Gauche">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dpg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Dvg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lp_b" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Masque_Vert_Gauche", propOrder = {

})
public class RTDataMasqueVertGauche {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Dpg")
    protected double dpg;
    @XmlElement(name = "Dvg")
    protected double dvg;
    @XmlElement(name = "lp_b")
    protected double lpB;

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
     * Gets the value of the dpg property.
     * 
     */
    public double getDpg() {
        return dpg;
    }

    /**
     * Sets the value of the dpg property.
     * 
     */
    public void setDpg(double value) {
        this.dpg = value;
    }

    /**
     * Gets the value of the dvg property.
     * 
     */
    public double getDvg() {
        return dvg;
    }

    /**
     * Sets the value of the dvg property.
     * 
     */
    public void setDvg(double value) {
        this.dvg = value;
    }

    /**
     * Gets the value of the lpB property.
     * 
     */
    public double getLpB() {
        return lpB;
    }

    /**
     * Sets the value of the lpB property.
     * 
     */
    public void setLpB(double value) {
        this.lpB = value;
    }

}
