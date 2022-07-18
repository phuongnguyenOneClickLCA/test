
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7502 - 6/10/2017
 * 
 * <p>Java class for Heliopac_Boucle_Solaire_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Heliopac_Boucle_Solaire_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Heliopac_Boucle_Solaire_Data", propOrder = {

})
public class HeliopacBoucleSolaireData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;

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
     * Gets the value of the rdim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRdim(Integer value) {
        this.rdim = value;
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

}
