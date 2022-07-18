
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_RIDEL_RidelX
 * 
 * <p>Java class for RidelX_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RidelX_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fonct_Systeme" type="{}E_Fonct_Systeme"/>
 *         &lt;element name="Pech_ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Vtot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_UA" type="{}E_Statut_UA"/>
 *         &lt;element name="UA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcircu_ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcond_fr_pos" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcond_fr_neg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RidelX_Data", propOrder = {

})
public class RidelXData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Fonct_Systeme", required = true)
    protected String fonctSysteme;
    @XmlElement(name = "Pech_ecs")
    protected double pechEcs;
    @XmlElement(name = "Vtot")
    protected double vtot;
    @XmlElement(name = "Statut_UA", required = true)
    protected String statutUA;
    @XmlElement(name = "UA")
    protected double ua;
    @XmlElement(name = "Pcircu_ecs")
    protected double pcircuEcs;
    @XmlElement(name = "Pcond_fr_pos")
    protected double pcondFrPos;
    @XmlElement(name = "Pcond_fr_neg")
    protected double pcondFrNeg;

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
     * Gets the value of the fonctSysteme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctSysteme() {
        return fonctSysteme;
    }

    /**
     * Sets the value of the fonctSysteme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctSysteme(String value) {
        this.fonctSysteme = value;
    }

    /**
     * Gets the value of the pechEcs property.
     * 
     */
    public double getPechEcs() {
        return pechEcs;
    }

    /**
     * Sets the value of the pechEcs property.
     * 
     */
    public void setPechEcs(double value) {
        this.pechEcs = value;
    }

    /**
     * Gets the value of the vtot property.
     * 
     */
    public double getVtot() {
        return vtot;
    }

    /**
     * Sets the value of the vtot property.
     * 
     */
    public void setVtot(double value) {
        this.vtot = value;
    }

    /**
     * Gets the value of the statutUA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutUA() {
        return statutUA;
    }

    /**
     * Sets the value of the statutUA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutUA(String value) {
        this.statutUA = value;
    }

    /**
     * Gets the value of the ua property.
     * 
     */
    public double getUA() {
        return ua;
    }

    /**
     * Sets the value of the ua property.
     * 
     */
    public void setUA(double value) {
        this.ua = value;
    }

    /**
     * Gets the value of the pcircuEcs property.
     * 
     */
    public double getPcircuEcs() {
        return pcircuEcs;
    }

    /**
     * Sets the value of the pcircuEcs property.
     * 
     */
    public void setPcircuEcs(double value) {
        this.pcircuEcs = value;
    }

    /**
     * Gets the value of the pcondFrPos property.
     * 
     */
    public double getPcondFrPos() {
        return pcondFrPos;
    }

    /**
     * Sets the value of the pcondFrPos property.
     * 
     */
    public void setPcondFrPos(double value) {
        this.pcondFrPos = value;
    }

    /**
     * Gets the value of the pcondFrNeg property.
     * 
     */
    public double getPcondFrNeg() {
        return pcondFrNeg;
    }

    /**
     * Sets the value of the pcondFrNeg property.
     * 
     */
    public void setPcondFrNeg(double value) {
        this.pcondFrNeg = value;
    }

}
