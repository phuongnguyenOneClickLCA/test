
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Entree_Air complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Entree_Air">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_entree_air" type="{}E_Type_entree_air"/>
 *         &lt;element name="Module" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_f" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_P1_AR" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_P2_AR" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Module_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Module_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Entree_Air", propOrder = {

})
public class RTDataEntreeAir {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_entree_air", required = true)
    protected String typeEntreeAir;
    @XmlElement(name = "Module")
    protected double module;
    @XmlElement(name = "R_f")
    protected double rf;
    @XmlElement(name = "Delta_P1_AR")
    protected double deltaP1AR;
    @XmlElement(name = "Delta_P2_AR")
    protected double deltaP2AR;
    @XmlElement(name = "Module_1")
    protected double module1;
    @XmlElement(name = "Module_2")
    protected double module2;

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
     * Gets the value of the typeEntreeAir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeEntreeAir() {
        return typeEntreeAir;
    }

    /**
     * Sets the value of the typeEntreeAir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeEntreeAir(String value) {
        this.typeEntreeAir = value;
    }

    /**
     * Gets the value of the module property.
     * 
     */
    public double getModule() {
        return module;
    }

    /**
     * Sets the value of the module property.
     * 
     */
    public void setModule(double value) {
        this.module = value;
    }

    /**
     * Gets the value of the rf property.
     * 
     */
    public double getRF() {
        return rf;
    }

    /**
     * Sets the value of the rf property.
     * 
     */
    public void setRF(double value) {
        this.rf = value;
    }

    /**
     * Gets the value of the deltaP1AR property.
     * 
     */
    public double getDeltaP1AR() {
        return deltaP1AR;
    }

    /**
     * Sets the value of the deltaP1AR property.
     * 
     */
    public void setDeltaP1AR(double value) {
        this.deltaP1AR = value;
    }

    /**
     * Gets the value of the deltaP2AR property.
     * 
     */
    public double getDeltaP2AR() {
        return deltaP2AR;
    }

    /**
     * Sets the value of the deltaP2AR property.
     * 
     */
    public void setDeltaP2AR(double value) {
        this.deltaP2AR = value;
    }

    /**
     * Gets the value of the module1 property.
     * 
     */
    public double getModule1() {
        return module1;
    }

    /**
     * Sets the value of the module1 property.
     * 
     */
    public void setModule1(double value) {
        this.module1 = value;
    }

    /**
     * Gets the value of the module2 property.
     * 
     */
    public double getModule2() {
        return module2;
    }

    /**
     * Sets the value of the module2 property.
     * 
     */
    public void setModule2(double value) {
        this.module2 = value;
    }

}
