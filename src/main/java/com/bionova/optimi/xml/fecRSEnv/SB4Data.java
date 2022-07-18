
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_STIMERGY_Chaudiere_numerique_SB4
 * 
 * <p>Java class for SB4_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SB4_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pnom_chaudiere" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux_chaudiere" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux_delestage" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "SB4_Data", propOrder = {

})
public class SB4Data {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Pnom_chaudiere")
    protected double pnomChaudiere;
    @XmlElement(name = "Paux_chaudiere")
    protected double pauxChaudiere;
    @XmlElement(name = "Paux_delestage")
    protected double pauxDelestage;
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
     * Gets the value of the pnomChaudiere property.
     * 
     */
    public double getPnomChaudiere() {
        return pnomChaudiere;
    }

    /**
     * Sets the value of the pnomChaudiere property.
     * 
     */
    public void setPnomChaudiere(double value) {
        this.pnomChaudiere = value;
    }

    /**
     * Gets the value of the pauxChaudiere property.
     * 
     */
    public double getPauxChaudiere() {
        return pauxChaudiere;
    }

    /**
     * Sets the value of the pauxChaudiere property.
     * 
     */
    public void setPauxChaudiere(double value) {
        this.pauxChaudiere = value;
    }

    /**
     * Gets the value of the pauxDelestage property.
     * 
     */
    public double getPauxDelestage() {
        return pauxDelestage;
    }

    /**
     * Sets the value of the pauxDelestage property.
     * 
     */
    public void setPauxDelestage(double value) {
        this.pauxDelestage = value;
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
