
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Paroi_Opaque complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Paroi_Opaque">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ak" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Paroi_Inclk" type="{}E_Type_Paroi_Inclk"/>
 *         &lt;element name="Uk" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sf_ck" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sf_ek" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Vegetalise" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_Simul_Deb_V" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="T_Simul_Fin_V" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sfc_Vk" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sfe_Vk" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "RT_Data_Paroi_Opaque", propOrder = {

})
public class RTDataParoiOpaque {

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
    @XmlElement(name = "Ak")
    protected double ak;
    @XmlElement(name = "Type_Paroi_Inclk", required = true)
    protected String typeParoiInclk;
    @XmlElement(name = "Uk")
    protected double uk;
    @XmlElement(name = "Sf_ck")
    protected double sfCk;
    @XmlElement(name = "Sf_ek")
    protected double sfEk;
    @XmlElement(name = "Is_Vegetalise", required = true)
    protected String isVegetalise;
    @XmlElement(name = "T_Simul_Deb_V")
    protected int tSimulDebV;
    @XmlElement(name = "T_Simul_Fin_V")
    protected int tSimulFinV;
    @XmlElement(name = "Sfc_Vk")
    protected double sfcVk;
    @XmlElement(name = "Sfe_Vk")
    protected double sfeVk;
    @XmlElement(name = "Id_Et")
    protected int idEt;
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
     * Gets the value of the ak property.
     * 
     */
    public double getAk() {
        return ak;
    }

    /**
     * Sets the value of the ak property.
     * 
     */
    public void setAk(double value) {
        this.ak = value;
    }

    /**
     * Gets the value of the typeParoiInclk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeParoiInclk() {
        return typeParoiInclk;
    }

    /**
     * Sets the value of the typeParoiInclk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeParoiInclk(String value) {
        this.typeParoiInclk = value;
    }

    /**
     * Gets the value of the uk property.
     * 
     */
    public double getUk() {
        return uk;
    }

    /**
     * Sets the value of the uk property.
     * 
     */
    public void setUk(double value) {
        this.uk = value;
    }

    /**
     * Gets the value of the sfCk property.
     * 
     */
    public double getSfCk() {
        return sfCk;
    }

    /**
     * Sets the value of the sfCk property.
     * 
     */
    public void setSfCk(double value) {
        this.sfCk = value;
    }

    /**
     * Gets the value of the sfEk property.
     * 
     */
    public double getSfEk() {
        return sfEk;
    }

    /**
     * Sets the value of the sfEk property.
     * 
     */
    public void setSfEk(double value) {
        this.sfEk = value;
    }

    /**
     * Gets the value of the isVegetalise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsVegetalise() {
        return isVegetalise;
    }

    /**
     * Sets the value of the isVegetalise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsVegetalise(String value) {
        this.isVegetalise = value;
    }

    /**
     * Gets the value of the tSimulDebV property.
     * 
     */
    public int getTSimulDebV() {
        return tSimulDebV;
    }

    /**
     * Sets the value of the tSimulDebV property.
     * 
     */
    public void setTSimulDebV(int value) {
        this.tSimulDebV = value;
    }

    /**
     * Gets the value of the tSimulFinV property.
     * 
     */
    public int getTSimulFinV() {
        return tSimulFinV;
    }

    /**
     * Sets the value of the tSimulFinV property.
     * 
     */
    public void setTSimulFinV(int value) {
        this.tSimulFinV = value;
    }

    /**
     * Gets the value of the sfcVk property.
     * 
     */
    public double getSfcVk() {
        return sfcVk;
    }

    /**
     * Sets the value of the sfcVk property.
     * 
     */
    public void setSfcVk(double value) {
        this.sfcVk = value;
    }

    /**
     * Gets the value of the sfeVk property.
     * 
     */
    public double getSfeVk() {
        return sfeVk;
    }

    /**
     * Sets the value of the sfeVk property.
     * 
     */
    public void setSfeVk(double value) {
        this.sfeVk = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    public void setIdEt(int value) {
        this.idEt = value;
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
