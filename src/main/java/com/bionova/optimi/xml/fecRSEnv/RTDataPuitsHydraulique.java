
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * v8000
 * 
 * <p>Java class for RT_Data_Puits_Hydraulique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Puits_Hydraulique">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Objet_Base">
 *       &lt;sequence>
 *         &lt;element name="Z" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_reg_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_arret" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_variable" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_PH_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_PH_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="d_i" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="e_p" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lamda_tube" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Sol" type="{}E_Type_Sol"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Puits_Hydraulique", propOrder = {
    "z",
    "thetaRegBase",
    "deltaThetaArret",
    "deltaThetaVariable",
    "qphMax",
    "pphMax",
    "l",
    "di",
    "ep",
    "lamdaTube",
    "typeSol"
})
public class RTDataPuitsHydraulique
    extends RTDataObjetBase
{

    @XmlElement(name = "Z")
    protected double z;
    @XmlElement(name = "Theta_reg_base")
    protected double thetaRegBase;
    @XmlElement(name = "Delta_Theta_arret")
    protected double deltaThetaArret;
    @XmlElement(name = "Delta_Theta_variable")
    protected double deltaThetaVariable;
    @XmlElement(name = "Q_PH_max")
    protected double qphMax;
    @XmlElement(name = "P_PH_max")
    protected double pphMax;
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
     * Gets the value of the thetaRegBase property.
     * 
     */
    public double getThetaRegBase() {
        return thetaRegBase;
    }

    /**
     * Sets the value of the thetaRegBase property.
     * 
     */
    public void setThetaRegBase(double value) {
        this.thetaRegBase = value;
    }

    /**
     * Gets the value of the deltaThetaArret property.
     * 
     */
    public double getDeltaThetaArret() {
        return deltaThetaArret;
    }

    /**
     * Sets the value of the deltaThetaArret property.
     * 
     */
    public void setDeltaThetaArret(double value) {
        this.deltaThetaArret = value;
    }

    /**
     * Gets the value of the deltaThetaVariable property.
     * 
     */
    public double getDeltaThetaVariable() {
        return deltaThetaVariable;
    }

    /**
     * Sets the value of the deltaThetaVariable property.
     * 
     */
    public void setDeltaThetaVariable(double value) {
        this.deltaThetaVariable = value;
    }

    /**
     * Gets the value of the qphMax property.
     * 
     */
    public double getQPHMax() {
        return qphMax;
    }

    /**
     * Sets the value of the qphMax property.
     * 
     */
    public void setQPHMax(double value) {
        this.qphMax = value;
    }

    /**
     * Gets the value of the pphMax property.
     * 
     */
    public double getPPHMax() {
        return pphMax;
    }

    /**
     * Sets the value of the pphMax property.
     * 
     */
    public void setPPHMax(double value) {
        this.pphMax = value;
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

}
