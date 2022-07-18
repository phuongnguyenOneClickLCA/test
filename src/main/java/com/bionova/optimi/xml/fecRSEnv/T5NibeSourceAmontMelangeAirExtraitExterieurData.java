
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7501 - T5 NIBE PAC
 * 
 * <p>Java class for T5_Nibe_Source_Amont_Melange_AirExtraitExterieur_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Nibe_Source_Amont_Melange_AirExtraitExterieur_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_SF_Extraction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pvent_Gaine" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Nibe_Source_Amont_Melange_AirExtraitExterieur_Data", propOrder = {

})
public class T5NibeSourceAmontMelangeAirExtraitExterieurData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Id_SF_Extraction")
    protected int idSFExtraction;
    @XmlElement(name = "Pvent_Gaine")
    protected int pventGaine;

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
     * Gets the value of the idSFExtraction property.
     * 
     */
    public int getIdSFExtraction() {
        return idSFExtraction;
    }

    /**
     * Sets the value of the idSFExtraction property.
     * 
     */
    public void setIdSFExtraction(int value) {
        this.idSFExtraction = value;
    }

    /**
     * Gets the value of the pventGaine property.
     * 
     */
    public int getPventGaine() {
        return pventGaine;
    }

    /**
     * Sets the value of the pventGaine property.
     * 
     */
    public void setPventGaine(int value) {
        this.pventGaine = value;
    }

}
