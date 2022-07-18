
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_Cardonnel_Boostherm
 * 
 * <p>Java class for T5_Cardonnel_Boostherm_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Cardonnel_Boostherm_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="P_condensation_constructeur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_recuperation_Boostherm" type="{}EType_BOOSTHERM"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Cardonnel_Boostherm_Data", propOrder = {

})
public class T5CardonnelBoosthermData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "P_condensation_constructeur")
    protected double pCondensationConstructeur;
    @XmlElement(name = "Type_recuperation_Boostherm", required = true)
    protected String typeRecuperationBoostherm;

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
     * Gets the value of the idprioriteEcs property.
     * 
     */
    public int getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     */
    public void setIdprioriteEcs(int value) {
        this.idprioriteEcs = value;
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
     * Gets the value of the pCondensationConstructeur property.
     * 
     */
    public double getPCondensationConstructeur() {
        return pCondensationConstructeur;
    }

    /**
     * Sets the value of the pCondensationConstructeur property.
     * 
     */
    public void setPCondensationConstructeur(double value) {
        this.pCondensationConstructeur = value;
    }

    /**
     * Gets the value of the typeRecuperationBoostherm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeRecuperationBoostherm() {
        return typeRecuperationBoostherm;
    }

    /**
     * Sets the value of the typeRecuperationBoostherm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeRecuperationBoostherm(String value) {
        this.typeRecuperationBoostherm = value;
    }

}
