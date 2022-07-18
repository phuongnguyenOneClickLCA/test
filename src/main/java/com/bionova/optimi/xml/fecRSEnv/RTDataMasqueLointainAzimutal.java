
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Masque_Lointain_Azimutal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Masque_Lointain_Azimutal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Gamma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Vegetation" type="{}RT_Oui_Non"/>
 *         &lt;element name="Debut_Vegetation" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Fin_Vegetation" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Masque_Lointain_Azimutal", propOrder = {

})
public class RTDataMasqueLointainAzimutal {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Gamma")
    protected String gamma;
    @XmlElement(name = "Vegetation", required = true)
    protected String vegetation;
    @XmlElement(name = "Debut_Vegetation")
    protected int debutVegetation;
    @XmlElement(name = "Fin_Vegetation")
    protected int finVegetation;

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
     * Gets the value of the gamma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGamma() {
        return gamma;
    }

    /**
     * Sets the value of the gamma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGamma(String value) {
        this.gamma = value;
    }

    /**
     * Gets the value of the vegetation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVegetation() {
        return vegetation;
    }

    /**
     * Sets the value of the vegetation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVegetation(String value) {
        this.vegetation = value;
    }

    /**
     * Gets the value of the debutVegetation property.
     * 
     */
    public int getDebutVegetation() {
        return debutVegetation;
    }

    /**
     * Sets the value of the debutVegetation property.
     * 
     */
    public void setDebutVegetation(int value) {
        this.debutVegetation = value;
    }

    /**
     * Gets the value of the finVegetation property.
     * 
     */
    public int getFinVegetation() {
        return finVegetation;
    }

    /**
     * Sets the value of the finVegetation property.
     * 
     */
    public void setFinVegetation(int value) {
        this.finVegetation = value;
    }

}
