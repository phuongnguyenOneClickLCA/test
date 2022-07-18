
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_QARNOT_Radiateur_Numerique
 * 
 * <p>Java class for T5_QARNOT_Radiateur_Numerique_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_QARNOT_Radiateur_Numerique_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pw_abs_Qrad_pc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pw_abs_CM_pc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pw_abs_veille" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eta_alimentation" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_QARNOT_Radiateur_Numerique_Data", propOrder = {

})
public class T5QARNOTRadiateurNumeriqueData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Pw_abs_Qrad_pc")
    protected double pwAbsQradPc;
    @XmlElement(name = "Pw_abs_CM_pc")
    protected double pwAbsCMPc;
    @XmlElement(name = "Pw_abs_veille")
    protected double pwAbsVeille;
    @XmlElement(name = "Eta_alimentation")
    protected double etaAlimentation;

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
     * Gets the value of the idprioriteCh property.
     * 
     */
    public int getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     */
    public void setIdprioriteCh(int value) {
        this.idprioriteCh = value;
    }

    /**
     * Gets the value of the pwAbsQradPc property.
     * 
     */
    public double getPwAbsQradPc() {
        return pwAbsQradPc;
    }

    /**
     * Sets the value of the pwAbsQradPc property.
     * 
     */
    public void setPwAbsQradPc(double value) {
        this.pwAbsQradPc = value;
    }

    /**
     * Gets the value of the pwAbsCMPc property.
     * 
     */
    public double getPwAbsCMPc() {
        return pwAbsCMPc;
    }

    /**
     * Sets the value of the pwAbsCMPc property.
     * 
     */
    public void setPwAbsCMPc(double value) {
        this.pwAbsCMPc = value;
    }

    /**
     * Gets the value of the pwAbsVeille property.
     * 
     */
    public double getPwAbsVeille() {
        return pwAbsVeille;
    }

    /**
     * Sets the value of the pwAbsVeille property.
     * 
     */
    public void setPwAbsVeille(double value) {
        this.pwAbsVeille = value;
    }

    /**
     * Gets the value of the etaAlimentation property.
     * 
     */
    public double getEtaAlimentation() {
        return etaAlimentation;
    }

    /**
     * Sets the value of the etaAlimentation property.
     * 
     */
    public void setEtaAlimentation(double value) {
        this.etaAlimentation = value;
    }

}
