
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * v8000
 * 
 * <p>Java class for RT_Data_Brasseur_Air complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Brasseur_Air">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Objet_Base">
 *       &lt;sequence>
 *         &lt;element name="Nb" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rat_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Us" type="{}E_Jour_Nuit"/>
 *         &lt;element name="Mode_gestion_brasseur" type="{}E_Mode_gestion_brasseur"/>
 *         &lt;element name="Delta_theta_op_auto_thermostat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Delta_theta_op_auto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_op_dec_auto" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_air_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_air_min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_elec_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_elec_min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Brasseur_Air", propOrder = {
    "nb",
    "ratSurf",
    "us",
    "modeGestionBrasseur",
    "deltaThetaOpAutoThermostat",
    "deltaThetaOpAuto",
    "thetaOpDecAuto",
    "qvAirMax",
    "qvAirMin",
    "pElecMax",
    "pElecMin"
})
public class RTDataBrasseurAir
    extends RTDataObjetBase
{

    @XmlElement(name = "Nb")
    protected int nb;
    @XmlElement(name = "Rat_surf")
    protected double ratSurf;
    @XmlElement(name = "Us", required = true)
    protected String us;
    @XmlElement(name = "Mode_gestion_brasseur", required = true)
    protected String modeGestionBrasseur;
    @XmlElement(name = "Delta_theta_op_auto_thermostat")
    protected String deltaThetaOpAutoThermostat;
    @XmlElement(name = "Delta_theta_op_auto")
    protected String deltaThetaOpAuto;
    @XmlElement(name = "Theta_op_dec_auto")
    protected double thetaOpDecAuto;
    @XmlElement(name = "Qv_air_max")
    protected double qvAirMax;
    @XmlElement(name = "Qv_air_min")
    protected double qvAirMin;
    @XmlElement(name = "P_elec_max")
    protected double pElecMax;
    @XmlElement(name = "P_elec_min")
    protected double pElecMin;

    /**
     * Gets the value of the nb property.
     * 
     */
    public int getNb() {
        return nb;
    }

    /**
     * Sets the value of the nb property.
     * 
     */
    public void setNb(int value) {
        this.nb = value;
    }

    /**
     * Gets the value of the ratSurf property.
     * 
     */
    public double getRatSurf() {
        return ratSurf;
    }

    /**
     * Sets the value of the ratSurf property.
     * 
     */
    public void setRatSurf(double value) {
        this.ratSurf = value;
    }

    /**
     * Gets the value of the us property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUs() {
        return us;
    }

    /**
     * Sets the value of the us property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUs(String value) {
        this.us = value;
    }

    /**
     * Gets the value of the modeGestionBrasseur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeGestionBrasseur() {
        return modeGestionBrasseur;
    }

    /**
     * Sets the value of the modeGestionBrasseur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeGestionBrasseur(String value) {
        this.modeGestionBrasseur = value;
    }

    /**
     * Gets the value of the deltaThetaOpAutoThermostat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeltaThetaOpAutoThermostat() {
        return deltaThetaOpAutoThermostat;
    }

    /**
     * Sets the value of the deltaThetaOpAutoThermostat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeltaThetaOpAutoThermostat(String value) {
        this.deltaThetaOpAutoThermostat = value;
    }

    /**
     * Gets the value of the deltaThetaOpAuto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeltaThetaOpAuto() {
        return deltaThetaOpAuto;
    }

    /**
     * Sets the value of the deltaThetaOpAuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeltaThetaOpAuto(String value) {
        this.deltaThetaOpAuto = value;
    }

    /**
     * Gets the value of the thetaOpDecAuto property.
     * 
     */
    public double getThetaOpDecAuto() {
        return thetaOpDecAuto;
    }

    /**
     * Sets the value of the thetaOpDecAuto property.
     * 
     */
    public void setThetaOpDecAuto(double value) {
        this.thetaOpDecAuto = value;
    }

    /**
     * Gets the value of the qvAirMax property.
     * 
     */
    public double getQvAirMax() {
        return qvAirMax;
    }

    /**
     * Sets the value of the qvAirMax property.
     * 
     */
    public void setQvAirMax(double value) {
        this.qvAirMax = value;
    }

    /**
     * Gets the value of the qvAirMin property.
     * 
     */
    public double getQvAirMin() {
        return qvAirMin;
    }

    /**
     * Sets the value of the qvAirMin property.
     * 
     */
    public void setQvAirMin(double value) {
        this.qvAirMin = value;
    }

    /**
     * Gets the value of the pElecMax property.
     * 
     */
    public double getPElecMax() {
        return pElecMax;
    }

    /**
     * Sets the value of the pElecMax property.
     * 
     */
    public void setPElecMax(double value) {
        this.pElecMax = value;
    }

    /**
     * Gets the value of the pElecMin property.
     * 
     */
    public double getPElecMin() {
        return pElecMin;
    }

    /**
     * Sets the value of the pElecMin property.
     * 
     */
    public void setPElecMin(double value) {
        this.pElecMin = value;
    }

}
