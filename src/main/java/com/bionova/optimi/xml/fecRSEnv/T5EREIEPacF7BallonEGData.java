
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_EREIE_PacF7 - ballon
 * 
 * <p>Java class for T5_EREIE_PacF7_ballon_EG_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_EREIE_PacF7_ballon_EG_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="V_tot_GW" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_s_GW" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_vc_coll_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_coll_GW" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ECS_EG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_EREIE_PacF7_ballon_EG_Data", propOrder = {

})
public class T5EREIEPacF7BallonEGData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "V_tot_GW")
    protected double vTotGW;
    @XmlElement(name = "UA_s_GW")
    protected double uasgw;
    @XmlElement(name = "L_vc_coll_e")
    protected double lVcCollE;
    @XmlElement(name = "U_coll_GW")
    protected double uCollGW;
    @XmlElement(name = "ECS_EG")
    protected String ecseg;

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
     * Gets the value of the vTotGW property.
     * 
     */
    public double getVTotGW() {
        return vTotGW;
    }

    /**
     * Sets the value of the vTotGW property.
     * 
     */
    public void setVTotGW(double value) {
        this.vTotGW = value;
    }

    /**
     * Gets the value of the uasgw property.
     * 
     */
    public double getUASGW() {
        return uasgw;
    }

    /**
     * Sets the value of the uasgw property.
     * 
     */
    public void setUASGW(double value) {
        this.uasgw = value;
    }

    /**
     * Gets the value of the lVcCollE property.
     * 
     */
    public double getLVcCollE() {
        return lVcCollE;
    }

    /**
     * Sets the value of the lVcCollE property.
     * 
     */
    public void setLVcCollE(double value) {
        this.lVcCollE = value;
    }

    /**
     * Gets the value of the uCollGW property.
     * 
     */
    public double getUCollGW() {
        return uCollGW;
    }

    /**
     * Sets the value of the uCollGW property.
     * 
     */
    public void setUCollGW(double value) {
        this.uCollGW = value;
    }

    /**
     * Gets the value of the ecseg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getECSEG() {
        return ecseg;
    }

    /**
     * Sets the value of the ecseg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setECSEG(String value) {
        this.ecseg = value;
    }

}
