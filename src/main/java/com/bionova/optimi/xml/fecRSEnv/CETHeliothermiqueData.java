
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_IMERYS_CET_Heliothermique
 * 
 * <p>Java class for CET_Heliothermique_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CET_Heliothermique_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="COP_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Performances" type="{}E_Statut_Performances"/>
 *         &lt;element name="Statut_Taux" type="{}E_Statut_Taux"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CET_Heliothermique_Data", propOrder = {

})
public class CETHeliothermiqueData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Description")
    protected Integer description;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "COP_pivot")
    protected double copPivot;
    @XmlElement(name = "Pabs_pivot")
    protected double pabsPivot;
    @XmlElement(name = "Statut_Performances", required = true)
    protected String statutPerformances;
    @XmlElement(name = "Statut_Taux", required = true)
    protected String statutTaux;
    @XmlElement(name = "Taux")
    protected double taux;

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDescription(Integer value) {
        this.description = value;
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

    /**
     * Gets the value of the copPivot property.
     * 
     */
    public double getCOPPivot() {
        return copPivot;
    }

    /**
     * Sets the value of the copPivot property.
     * 
     */
    public void setCOPPivot(double value) {
        this.copPivot = value;
    }

    /**
     * Gets the value of the pabsPivot property.
     * 
     */
    public double getPabsPivot() {
        return pabsPivot;
    }

    /**
     * Sets the value of the pabsPivot property.
     * 
     */
    public void setPabsPivot(double value) {
        this.pabsPivot = value;
    }

    /**
     * Gets the value of the statutPerformances property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPerformances() {
        return statutPerformances;
    }

    /**
     * Sets the value of the statutPerformances property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPerformances(String value) {
        this.statutPerformances = value;
    }

    /**
     * Gets the value of the statutTaux property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutTaux() {
        return statutTaux;
    }

    /**
     * Sets the value of the statutTaux property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutTaux(String value) {
        this.statutTaux = value;
    }

    /**
     * Gets the value of the taux property.
     * 
     */
    public double getTaux() {
        return taux;
    }

    /**
     * Sets the value of the taux property.
     * 
     */
    public void setTaux(double value) {
        this.taux = value;
    }

}
