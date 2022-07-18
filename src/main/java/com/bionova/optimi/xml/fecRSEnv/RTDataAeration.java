
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Aeration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Aeration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Qv_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Aeration", propOrder = {

})
public class RTDataAeration {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Qv_occ")
    protected double qvOcc;
    @XmlElement(name = "Qv_inocc")
    protected double qvInocc;
    @XmlElement(name = "Qv_pointe")
    protected double qvPointe;
    @XmlElement(name = "Qv_base")
    protected double qvBase;
    @XmlElement(name = "Id_Et")
    protected int idEt;

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
     * Gets the value of the qvOcc property.
     * 
     */
    public double getQvOcc() {
        return qvOcc;
    }

    /**
     * Sets the value of the qvOcc property.
     * 
     */
    public void setQvOcc(double value) {
        this.qvOcc = value;
    }

    /**
     * Gets the value of the qvInocc property.
     * 
     */
    public double getQvInocc() {
        return qvInocc;
    }

    /**
     * Sets the value of the qvInocc property.
     * 
     */
    public void setQvInocc(double value) {
        this.qvInocc = value;
    }

    /**
     * Gets the value of the qvPointe property.
     * 
     */
    public double getQvPointe() {
        return qvPointe;
    }

    /**
     * Sets the value of the qvPointe property.
     * 
     */
    public void setQvPointe(double value) {
        this.qvPointe = value;
    }

    /**
     * Gets the value of the qvBase property.
     * 
     */
    public double getQvBase() {
        return qvBase;
    }

    /**
     * Sets the value of the qvBase property.
     * 
     */
    public void setQvBase(double value) {
        this.qvBase = value;
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

}
