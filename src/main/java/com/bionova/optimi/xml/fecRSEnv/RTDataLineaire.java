
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Lineaire complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Lineaire">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Is_Detail_Calcul" type="{}E_Simplifiee_Detaillee"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ll" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Psil" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sf_cl" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sf_el" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Masque_Collection" type="{}ArrayOfChoice5" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Lineaire", propOrder = {

})
public class RTDataLineaire {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Is_Detail_Calcul", required = true)
    protected String isDetailCalcul;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "Ll")
    protected double ll;
    @XmlElement(name = "Psil")
    protected double psil;
    @XmlElement(name = "Sf_cl")
    protected double sfCl;
    @XmlElement(name = "Sf_el")
    protected double sfEl;
    @XmlElement(name = "Id_Et", defaultValue = "0")
    protected int idEt;
    @XmlElement(name = "Masque_Collection")
    protected ArrayOfChoice5 masqueCollection;

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
     * Gets the value of the isDetailCalcul property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDetailCalcul() {
        return isDetailCalcul;
    }

    /**
     * Sets the value of the isDetailCalcul property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDetailCalcul(String value) {
        this.isDetailCalcul = value;
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
     * Gets the value of the ll property.
     * 
     */
    public double getLl() {
        return ll;
    }

    /**
     * Sets the value of the ll property.
     * 
     */
    public void setLl(double value) {
        this.ll = value;
    }

    /**
     * Gets the value of the psil property.
     * 
     */
    public double getPsil() {
        return psil;
    }

    /**
     * Sets the value of the psil property.
     * 
     */
    public void setPsil(double value) {
        this.psil = value;
    }

    /**
     * Gets the value of the sfCl property.
     * 
     */
    public double getSfCl() {
        return sfCl;
    }

    /**
     * Sets the value of the sfCl property.
     * 
     */
    public void setSfCl(double value) {
        this.sfCl = value;
    }

    /**
     * Gets the value of the sfEl property.
     * 
     */
    public double getSfEl() {
        return sfEl;
    }

    /**
     * Sets the value of the sfEl property.
     * 
     */
    public void setSfEl(double value) {
        this.sfEl = value;
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
     *     {@link ArrayOfChoice5 }
     *     
     */
    public ArrayOfChoice5 getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice5 }
     *     
     */
    public void setMasqueCollection(ArrayOfChoice5 value) {
        this.masqueCollection = value;
    }

}
