
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Puits_Climatique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Puits_Climatique">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="n_d" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Z" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="d_i" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="e_p" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lamda_tube" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Sol" type="{}E_Type_Sol"/>
 *         &lt;element name="Type_gest_PC" type="{}E_Type_ByPass"/>
 *         &lt;element name="T_ext_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Puits_Climatique", propOrder = {

})
public class RTDataPuitsClimatique {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "n_d")
    protected double nd;
    @XmlElement(name = "Z")
    protected double z;
    @XmlElement(name = "L")
    protected double l;
    @XmlElement(name = "d_i")
    protected double di;
    @XmlElement(name = "e_p")
    protected double ep;
    @XmlElement(name = "Lamda_tube")
    protected double lamdaTube;
    @XmlElement(name = "Type_Sol", required = true)
    protected String typeSol;
    @XmlElement(name = "Type_gest_PC", required = true)
    protected String typeGestPC;
    @XmlElement(name = "T_ext_bp_hiver")
    protected double tExtBpHiver;
    @XmlElement(name = "T_int_bp_hiver")
    protected double tIntBpHiver;
    @XmlElement(name = "T_ext_bp_ete")
    protected double tExtBpEte;
    @XmlElement(name = "T_int_bp_ete")
    protected double tIntBpEte;

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
     * Gets the value of the nd property.
     * 
     */
    public double getND() {
        return nd;
    }

    /**
     * Sets the value of the nd property.
     * 
     */
    public void setND(double value) {
        this.nd = value;
    }

    /**
     * Gets the value of the z property.
     * 
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the value of the z property.
     * 
     */
    public void setZ(double value) {
        this.z = value;
    }

    /**
     * Gets the value of the l property.
     * 
     */
    public double getL() {
        return l;
    }

    /**
     * Sets the value of the l property.
     * 
     */
    public void setL(double value) {
        this.l = value;
    }

    /**
     * Gets the value of the di property.
     * 
     */
    public double getDI() {
        return di;
    }

    /**
     * Sets the value of the di property.
     * 
     */
    public void setDI(double value) {
        this.di = value;
    }

    /**
     * Gets the value of the ep property.
     * 
     */
    public double getEP() {
        return ep;
    }

    /**
     * Sets the value of the ep property.
     * 
     */
    public void setEP(double value) {
        this.ep = value;
    }

    /**
     * Gets the value of the lamdaTube property.
     * 
     */
    public double getLamdaTube() {
        return lamdaTube;
    }

    /**
     * Sets the value of the lamdaTube property.
     * 
     */
    public void setLamdaTube(double value) {
        this.lamdaTube = value;
    }

    /**
     * Gets the value of the typeSol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeSol() {
        return typeSol;
    }

    /**
     * Sets the value of the typeSol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeSol(String value) {
        this.typeSol = value;
    }

    /**
     * Gets the value of the typeGestPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestPC() {
        return typeGestPC;
    }

    /**
     * Sets the value of the typeGestPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestPC(String value) {
        this.typeGestPC = value;
    }

    /**
     * Gets the value of the tExtBpHiver property.
     * 
     */
    public double getTExtBpHiver() {
        return tExtBpHiver;
    }

    /**
     * Sets the value of the tExtBpHiver property.
     * 
     */
    public void setTExtBpHiver(double value) {
        this.tExtBpHiver = value;
    }

    /**
     * Gets the value of the tIntBpHiver property.
     * 
     */
    public double getTIntBpHiver() {
        return tIntBpHiver;
    }

    /**
     * Sets the value of the tIntBpHiver property.
     * 
     */
    public void setTIntBpHiver(double value) {
        this.tIntBpHiver = value;
    }

    /**
     * Gets the value of the tExtBpEte property.
     * 
     */
    public double getTExtBpEte() {
        return tExtBpEte;
    }

    /**
     * Sets the value of the tExtBpEte property.
     * 
     */
    public void setTExtBpEte(double value) {
        this.tExtBpEte = value;
    }

    /**
     * Gets the value of the tIntBpEte property.
     * 
     */
    public double getTIntBpEte() {
        return tIntBpEte;
    }

    /**
     * Sets the value of the tIntBpEte property.
     * 
     */
    public void setTIntBpEte(double value) {
        this.tIntBpEte = value;
    }

}
