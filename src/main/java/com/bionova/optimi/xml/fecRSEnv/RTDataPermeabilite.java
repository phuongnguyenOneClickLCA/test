
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Permeabilite complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Permeabilite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Valeur_Saisie_Defaut_Q4PaSurf" type="{}RT2012.Entree.Ven.E_Valeur_Saisie_Defaut"/>
 *         &lt;element name="Q4PaSurf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Permeabilite", propOrder = {

})
public class RTDataPermeabilite {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Valeur_Saisie_Defaut_Q4PaSurf", required = true)
    protected String valeurSaisieDefautQ4PaSurf;
    @XmlElement(name = "Q4PaSurf")
    protected double q4PaSurf;

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
     * Gets the value of the valeurSaisieDefautQ4PaSurf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurSaisieDefautQ4PaSurf() {
        return valeurSaisieDefautQ4PaSurf;
    }

    /**
     * Sets the value of the valeurSaisieDefautQ4PaSurf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurSaisieDefautQ4PaSurf(String value) {
        this.valeurSaisieDefautQ4PaSurf = value;
    }

    /**
     * Gets the value of the q4PaSurf property.
     * 
     */
    public double getQ4PaSurf() {
        return q4PaSurf;
    }

    /**
     * Sets the value of the q4PaSurf property.
     * 
     */
    public void setQ4PaSurf(double value) {
        this.q4PaSurf = value;
    }

}
