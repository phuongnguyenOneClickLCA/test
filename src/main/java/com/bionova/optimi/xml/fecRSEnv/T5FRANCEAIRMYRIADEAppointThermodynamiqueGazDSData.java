
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_FRANCEAIR_MYRIADE
 * 
 * <p>Java class for T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_DS_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_DS_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_ECS_Data">
 *       &lt;sequence>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Ch" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ch"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Ch" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Eau_Ch" type="{}E_Temperatures_Aval_Eau_Eau_Ch"/>
 *         &lt;element name="Theta_Amont_Eau_Eau_Ch" type="{}E_Temperatures_Amont_Eau_Eau"/>
 *         &lt;element name="Performance_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot_Ch" type="{}E_Valeur_Declaree_Def"/>
 *         &lt;element name="Val_Paux_pc_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Emetteur"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_DS_Data", propOrder = {
    "idprioriteCh",
    "statutDonneeCh",
    "thetaAvalEauGlycoleeEauCh",
    "thetaAmontEauGlycoleeEauCh",
    "thetaAvalEauEauCh",
    "thetaAmontEauEauCh",
    "performanceCh",
    "pabsCh",
    "corCh",
    "pauxPcCh",
    "statutValPivotCh",
    "valGUECh",
    "valPabsCh",
    "statutPauxPivotCh",
    "valPauxPcCh",
    "limThetaCh",
    "thetaMaxAvCh",
    "thetaMinAmCh",
    "typoEmetteurCh"
})
public class T5FRANCEAIRMYRIADEAppointThermodynamiqueGazDSData
    extends T5FRANCEAIRMYRIADEAppointThermodynamiqueGazECSData
{

    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    protected String statutDonneeCh;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Ch", required = true)
    protected String thetaAvalEauGlycoleeEauCh;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Ch", required = true)
    protected String thetaAmontEauGlycoleeEauCh;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Ch", required = true)
    protected String thetaAvalEauEauCh;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Ch", required = true)
    protected String thetaAmontEauEauCh;
    @XmlElement(name = "Performance_Ch")
    protected String performanceCh;
    @XmlElement(name = "Pabs_Ch")
    protected String pabsCh;
    @XmlElement(name = "COR_Ch")
    protected String corCh;
    @XmlElement(name = "Paux_pc_Ch")
    protected String pauxPcCh;
    @XmlElement(name = "Statut_Val_Pivot_Ch", required = true)
    protected String statutValPivotCh;
    @XmlElement(name = "Val_GUE_Ch")
    protected double valGUECh;
    @XmlElement(name = "Val_Pabs_Ch")
    protected double valPabsCh;
    @XmlElement(name = "Statut_Paux_Pivot_Ch", required = true)
    protected String statutPauxPivotCh;
    @XmlElement(name = "Val_Paux_pc_Ch")
    protected double valPauxPcCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av_Ch")
    protected double thetaMaxAvCh;
    @XmlElement(name = "Theta_Min_Am_Ch")
    protected double thetaMinAmCh;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    protected String typoEmetteurCh;

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
     * Gets the value of the statutDonneeCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeCh() {
        return statutDonneeCh;
    }

    /**
     * Sets the value of the statutDonneeCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeCh(String value) {
        this.statutDonneeCh = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauCh() {
        return thetaAvalEauGlycoleeEauCh;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauCh(String value) {
        this.thetaAvalEauGlycoleeEauCh = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEauCh() {
        return thetaAmontEauGlycoleeEauCh;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEauCh(String value) {
        this.thetaAmontEauGlycoleeEauCh = value;
    }

    /**
     * Gets the value of the thetaAvalEauEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauEauCh() {
        return thetaAvalEauEauCh;
    }

    /**
     * Sets the value of the thetaAvalEauEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauEauCh(String value) {
        this.thetaAvalEauEauCh = value;
    }

    /**
     * Gets the value of the thetaAmontEauEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauEauCh() {
        return thetaAmontEauEauCh;
    }

    /**
     * Sets the value of the thetaAmontEauEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauEauCh(String value) {
        this.thetaAmontEauEauCh = value;
    }

    /**
     * Gets the value of the performanceCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformanceCh() {
        return performanceCh;
    }

    /**
     * Sets the value of the performanceCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformanceCh(String value) {
        this.performanceCh = value;
    }

    /**
     * Gets the value of the pabsCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsCh() {
        return pabsCh;
    }

    /**
     * Sets the value of the pabsCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsCh(String value) {
        this.pabsCh = value;
    }

    /**
     * Gets the value of the corCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCORCh() {
        return corCh;
    }

    /**
     * Sets the value of the corCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCORCh(String value) {
        this.corCh = value;
    }

    /**
     * Gets the value of the pauxPcCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxPcCh() {
        return pauxPcCh;
    }

    /**
     * Sets the value of the pauxPcCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxPcCh(String value) {
        this.pauxPcCh = value;
    }

    /**
     * Gets the value of the statutValPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotCh() {
        return statutValPivotCh;
    }

    /**
     * Sets the value of the statutValPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotCh(String value) {
        this.statutValPivotCh = value;
    }

    /**
     * Gets the value of the valGUECh property.
     * 
     */
    public double getValGUECh() {
        return valGUECh;
    }

    /**
     * Sets the value of the valGUECh property.
     * 
     */
    public void setValGUECh(double value) {
        this.valGUECh = value;
    }

    /**
     * Gets the value of the valPabsCh property.
     * 
     */
    public double getValPabsCh() {
        return valPabsCh;
    }

    /**
     * Sets the value of the valPabsCh property.
     * 
     */
    public void setValPabsCh(double value) {
        this.valPabsCh = value;
    }

    /**
     * Gets the value of the statutPauxPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxPivotCh() {
        return statutPauxPivotCh;
    }

    /**
     * Sets the value of the statutPauxPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxPivotCh(String value) {
        this.statutPauxPivotCh = value;
    }

    /**
     * Gets the value of the valPauxPcCh property.
     * 
     */
    public double getValPauxPcCh() {
        return valPauxPcCh;
    }

    /**
     * Sets the value of the valPauxPcCh property.
     * 
     */
    public void setValPauxPcCh(double value) {
        this.valPauxPcCh = value;
    }

    /**
     * Gets the value of the limThetaCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimThetaCh() {
        return limThetaCh;
    }

    /**
     * Sets the value of the limThetaCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimThetaCh(String value) {
        this.limThetaCh = value;
    }

    /**
     * Gets the value of the thetaMaxAvCh property.
     * 
     */
    public double getThetaMaxAvCh() {
        return thetaMaxAvCh;
    }

    /**
     * Sets the value of the thetaMaxAvCh property.
     * 
     */
    public void setThetaMaxAvCh(double value) {
        this.thetaMaxAvCh = value;
    }

    /**
     * Gets the value of the thetaMinAmCh property.
     * 
     */
    public double getThetaMinAmCh() {
        return thetaMinAmCh;
    }

    /**
     * Sets the value of the thetaMinAmCh property.
     * 
     */
    public void setThetaMinAmCh(double value) {
        this.thetaMinAmCh = value;
    }

    /**
     * Gets the value of the typoEmetteurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoEmetteurCh() {
        return typoEmetteurCh;
    }

    /**
     * Sets the value of the typoEmetteurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoEmetteurCh(String value) {
        this.typoEmetteurCh = value;
    }

}
