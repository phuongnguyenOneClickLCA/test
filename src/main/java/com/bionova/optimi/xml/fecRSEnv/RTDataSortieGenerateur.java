
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Generateur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Generateur">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="IsChaudiereGaz" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Nbhcharge_0_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_0_10_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_10_20_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_20_30_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_30_40_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_40_50_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_50_60_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_60_70_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_70_80_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_80_90_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_90_100_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_HF_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_0_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_0_10_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_10_20_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_20_30_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_30_40_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_40_50_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_50_60_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_60_70_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_70_80_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_80_90_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_90_100_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_HF_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_0_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_0_10_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_10_20_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_20_30_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_30_40_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_40_50_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_50_60_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_60_70_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_70_80_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_80_90_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_90_100_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbhcharge_HF_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_fou_3_postes" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Generateur", propOrder = {
    "isChaudiereGaz",
    "nbhcharge0Ch",
    "nbhcharge010Ch",
    "nbhcharge1020Ch",
    "nbhcharge2030Ch",
    "nbhcharge3040Ch",
    "nbhcharge4050Ch",
    "nbhcharge5060Ch",
    "nbhcharge6070Ch",
    "nbhcharge7080Ch",
    "nbhcharge8090Ch",
    "nbhcharge90100Ch",
    "nbhchargeHFCh",
    "nbhcharge0Fr",
    "nbhcharge010Fr",
    "nbhcharge1020Fr",
    "nbhcharge2030Fr",
    "nbhcharge3040Fr",
    "nbhcharge4050Fr",
    "nbhcharge5060Fr",
    "nbhcharge6070Fr",
    "nbhcharge7080Fr",
    "nbhcharge8090Fr",
    "nbhcharge90100Fr",
    "nbhchargeHFFr",
    "nbhcharge0ECS",
    "nbhcharge010ECS",
    "nbhcharge1020ECS",
    "nbhcharge2030ECS",
    "nbhcharge3040ECS",
    "nbhcharge4050ECS",
    "nbhcharge5060ECS",
    "nbhcharge6070ECS",
    "nbhcharge7080ECS",
    "nbhcharge8090ECS",
    "nbhcharge90100ECS",
    "nbhchargeHFECS",
    "qFou3Postes"
})
public class RTDataSortieGenerateur
    extends RTDataSortieBase
{

    @XmlElement(name = "IsChaudiereGaz")
    protected Boolean isChaudiereGaz;
    @XmlElement(name = "Nbhcharge_0_ch")
    protected double nbhcharge0Ch;
    @XmlElement(name = "Nbhcharge_0_10_ch")
    protected double nbhcharge010Ch;
    @XmlElement(name = "Nbhcharge_10_20_ch")
    protected double nbhcharge1020Ch;
    @XmlElement(name = "Nbhcharge_20_30_ch")
    protected double nbhcharge2030Ch;
    @XmlElement(name = "Nbhcharge_30_40_ch")
    protected double nbhcharge3040Ch;
    @XmlElement(name = "Nbhcharge_40_50_ch")
    protected double nbhcharge4050Ch;
    @XmlElement(name = "Nbhcharge_50_60_ch")
    protected double nbhcharge5060Ch;
    @XmlElement(name = "Nbhcharge_60_70_ch")
    protected double nbhcharge6070Ch;
    @XmlElement(name = "Nbhcharge_70_80_ch")
    protected double nbhcharge7080Ch;
    @XmlElement(name = "Nbhcharge_80_90_ch")
    protected double nbhcharge8090Ch;
    @XmlElement(name = "Nbhcharge_90_100_ch")
    protected double nbhcharge90100Ch;
    @XmlElement(name = "Nbhcharge_HF_ch")
    protected double nbhchargeHFCh;
    @XmlElement(name = "Nbhcharge_0_fr")
    protected double nbhcharge0Fr;
    @XmlElement(name = "Nbhcharge_0_10_fr")
    protected double nbhcharge010Fr;
    @XmlElement(name = "Nbhcharge_10_20_fr")
    protected double nbhcharge1020Fr;
    @XmlElement(name = "Nbhcharge_20_30_fr")
    protected double nbhcharge2030Fr;
    @XmlElement(name = "Nbhcharge_30_40_fr")
    protected double nbhcharge3040Fr;
    @XmlElement(name = "Nbhcharge_40_50_fr")
    protected double nbhcharge4050Fr;
    @XmlElement(name = "Nbhcharge_50_60_fr")
    protected double nbhcharge5060Fr;
    @XmlElement(name = "Nbhcharge_60_70_fr")
    protected double nbhcharge6070Fr;
    @XmlElement(name = "Nbhcharge_70_80_fr")
    protected double nbhcharge7080Fr;
    @XmlElement(name = "Nbhcharge_80_90_fr")
    protected double nbhcharge8090Fr;
    @XmlElement(name = "Nbhcharge_90_100_fr")
    protected double nbhcharge90100Fr;
    @XmlElement(name = "Nbhcharge_HF_fr")
    protected double nbhchargeHFFr;
    @XmlElement(name = "Nbhcharge_0_ECS")
    protected double nbhcharge0ECS;
    @XmlElement(name = "Nbhcharge_0_10_ECS")
    protected double nbhcharge010ECS;
    @XmlElement(name = "Nbhcharge_10_20_ECS")
    protected double nbhcharge1020ECS;
    @XmlElement(name = "Nbhcharge_20_30_ECS")
    protected double nbhcharge2030ECS;
    @XmlElement(name = "Nbhcharge_30_40_ECS")
    protected double nbhcharge3040ECS;
    @XmlElement(name = "Nbhcharge_40_50_ECS")
    protected double nbhcharge4050ECS;
    @XmlElement(name = "Nbhcharge_50_60_ECS")
    protected double nbhcharge5060ECS;
    @XmlElement(name = "Nbhcharge_60_70_ECS")
    protected double nbhcharge6070ECS;
    @XmlElement(name = "Nbhcharge_70_80_ECS")
    protected double nbhcharge7080ECS;
    @XmlElement(name = "Nbhcharge_80_90_ECS")
    protected double nbhcharge8090ECS;
    @XmlElement(name = "Nbhcharge_90_100_ECS")
    protected double nbhcharge90100ECS;
    @XmlElement(name = "Nbhcharge_HF_ECS")
    protected double nbhchargeHFECS;
    @XmlElement(name = "Q_fou_3_postes")
    protected double qFou3Postes;

    /**
     * Gets the value of the isChaudiereGaz property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsChaudiereGaz() {
        return isChaudiereGaz;
    }

    /**
     * Sets the value of the isChaudiereGaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsChaudiereGaz(Boolean value) {
        this.isChaudiereGaz = value;
    }

    /**
     * Gets the value of the nbhcharge0Ch property.
     * 
     */
    public double getNbhcharge0Ch() {
        return nbhcharge0Ch;
    }

    /**
     * Sets the value of the nbhcharge0Ch property.
     * 
     */
    public void setNbhcharge0Ch(double value) {
        this.nbhcharge0Ch = value;
    }

    /**
     * Gets the value of the nbhcharge010Ch property.
     * 
     */
    public double getNbhcharge010Ch() {
        return nbhcharge010Ch;
    }

    /**
     * Sets the value of the nbhcharge010Ch property.
     * 
     */
    public void setNbhcharge010Ch(double value) {
        this.nbhcharge010Ch = value;
    }

    /**
     * Gets the value of the nbhcharge1020Ch property.
     * 
     */
    public double getNbhcharge1020Ch() {
        return nbhcharge1020Ch;
    }

    /**
     * Sets the value of the nbhcharge1020Ch property.
     * 
     */
    public void setNbhcharge1020Ch(double value) {
        this.nbhcharge1020Ch = value;
    }

    /**
     * Gets the value of the nbhcharge2030Ch property.
     * 
     */
    public double getNbhcharge2030Ch() {
        return nbhcharge2030Ch;
    }

    /**
     * Sets the value of the nbhcharge2030Ch property.
     * 
     */
    public void setNbhcharge2030Ch(double value) {
        this.nbhcharge2030Ch = value;
    }

    /**
     * Gets the value of the nbhcharge3040Ch property.
     * 
     */
    public double getNbhcharge3040Ch() {
        return nbhcharge3040Ch;
    }

    /**
     * Sets the value of the nbhcharge3040Ch property.
     * 
     */
    public void setNbhcharge3040Ch(double value) {
        this.nbhcharge3040Ch = value;
    }

    /**
     * Gets the value of the nbhcharge4050Ch property.
     * 
     */
    public double getNbhcharge4050Ch() {
        return nbhcharge4050Ch;
    }

    /**
     * Sets the value of the nbhcharge4050Ch property.
     * 
     */
    public void setNbhcharge4050Ch(double value) {
        this.nbhcharge4050Ch = value;
    }

    /**
     * Gets the value of the nbhcharge5060Ch property.
     * 
     */
    public double getNbhcharge5060Ch() {
        return nbhcharge5060Ch;
    }

    /**
     * Sets the value of the nbhcharge5060Ch property.
     * 
     */
    public void setNbhcharge5060Ch(double value) {
        this.nbhcharge5060Ch = value;
    }

    /**
     * Gets the value of the nbhcharge6070Ch property.
     * 
     */
    public double getNbhcharge6070Ch() {
        return nbhcharge6070Ch;
    }

    /**
     * Sets the value of the nbhcharge6070Ch property.
     * 
     */
    public void setNbhcharge6070Ch(double value) {
        this.nbhcharge6070Ch = value;
    }

    /**
     * Gets the value of the nbhcharge7080Ch property.
     * 
     */
    public double getNbhcharge7080Ch() {
        return nbhcharge7080Ch;
    }

    /**
     * Sets the value of the nbhcharge7080Ch property.
     * 
     */
    public void setNbhcharge7080Ch(double value) {
        this.nbhcharge7080Ch = value;
    }

    /**
     * Gets the value of the nbhcharge8090Ch property.
     * 
     */
    public double getNbhcharge8090Ch() {
        return nbhcharge8090Ch;
    }

    /**
     * Sets the value of the nbhcharge8090Ch property.
     * 
     */
    public void setNbhcharge8090Ch(double value) {
        this.nbhcharge8090Ch = value;
    }

    /**
     * Gets the value of the nbhcharge90100Ch property.
     * 
     */
    public double getNbhcharge90100Ch() {
        return nbhcharge90100Ch;
    }

    /**
     * Sets the value of the nbhcharge90100Ch property.
     * 
     */
    public void setNbhcharge90100Ch(double value) {
        this.nbhcharge90100Ch = value;
    }

    /**
     * Gets the value of the nbhchargeHFCh property.
     * 
     */
    public double getNbhchargeHFCh() {
        return nbhchargeHFCh;
    }

    /**
     * Sets the value of the nbhchargeHFCh property.
     * 
     */
    public void setNbhchargeHFCh(double value) {
        this.nbhchargeHFCh = value;
    }

    /**
     * Gets the value of the nbhcharge0Fr property.
     * 
     */
    public double getNbhcharge0Fr() {
        return nbhcharge0Fr;
    }

    /**
     * Sets the value of the nbhcharge0Fr property.
     * 
     */
    public void setNbhcharge0Fr(double value) {
        this.nbhcharge0Fr = value;
    }

    /**
     * Gets the value of the nbhcharge010Fr property.
     * 
     */
    public double getNbhcharge010Fr() {
        return nbhcharge010Fr;
    }

    /**
     * Sets the value of the nbhcharge010Fr property.
     * 
     */
    public void setNbhcharge010Fr(double value) {
        this.nbhcharge010Fr = value;
    }

    /**
     * Gets the value of the nbhcharge1020Fr property.
     * 
     */
    public double getNbhcharge1020Fr() {
        return nbhcharge1020Fr;
    }

    /**
     * Sets the value of the nbhcharge1020Fr property.
     * 
     */
    public void setNbhcharge1020Fr(double value) {
        this.nbhcharge1020Fr = value;
    }

    /**
     * Gets the value of the nbhcharge2030Fr property.
     * 
     */
    public double getNbhcharge2030Fr() {
        return nbhcharge2030Fr;
    }

    /**
     * Sets the value of the nbhcharge2030Fr property.
     * 
     */
    public void setNbhcharge2030Fr(double value) {
        this.nbhcharge2030Fr = value;
    }

    /**
     * Gets the value of the nbhcharge3040Fr property.
     * 
     */
    public double getNbhcharge3040Fr() {
        return nbhcharge3040Fr;
    }

    /**
     * Sets the value of the nbhcharge3040Fr property.
     * 
     */
    public void setNbhcharge3040Fr(double value) {
        this.nbhcharge3040Fr = value;
    }

    /**
     * Gets the value of the nbhcharge4050Fr property.
     * 
     */
    public double getNbhcharge4050Fr() {
        return nbhcharge4050Fr;
    }

    /**
     * Sets the value of the nbhcharge4050Fr property.
     * 
     */
    public void setNbhcharge4050Fr(double value) {
        this.nbhcharge4050Fr = value;
    }

    /**
     * Gets the value of the nbhcharge5060Fr property.
     * 
     */
    public double getNbhcharge5060Fr() {
        return nbhcharge5060Fr;
    }

    /**
     * Sets the value of the nbhcharge5060Fr property.
     * 
     */
    public void setNbhcharge5060Fr(double value) {
        this.nbhcharge5060Fr = value;
    }

    /**
     * Gets the value of the nbhcharge6070Fr property.
     * 
     */
    public double getNbhcharge6070Fr() {
        return nbhcharge6070Fr;
    }

    /**
     * Sets the value of the nbhcharge6070Fr property.
     * 
     */
    public void setNbhcharge6070Fr(double value) {
        this.nbhcharge6070Fr = value;
    }

    /**
     * Gets the value of the nbhcharge7080Fr property.
     * 
     */
    public double getNbhcharge7080Fr() {
        return nbhcharge7080Fr;
    }

    /**
     * Sets the value of the nbhcharge7080Fr property.
     * 
     */
    public void setNbhcharge7080Fr(double value) {
        this.nbhcharge7080Fr = value;
    }

    /**
     * Gets the value of the nbhcharge8090Fr property.
     * 
     */
    public double getNbhcharge8090Fr() {
        return nbhcharge8090Fr;
    }

    /**
     * Sets the value of the nbhcharge8090Fr property.
     * 
     */
    public void setNbhcharge8090Fr(double value) {
        this.nbhcharge8090Fr = value;
    }

    /**
     * Gets the value of the nbhcharge90100Fr property.
     * 
     */
    public double getNbhcharge90100Fr() {
        return nbhcharge90100Fr;
    }

    /**
     * Sets the value of the nbhcharge90100Fr property.
     * 
     */
    public void setNbhcharge90100Fr(double value) {
        this.nbhcharge90100Fr = value;
    }

    /**
     * Gets the value of the nbhchargeHFFr property.
     * 
     */
    public double getNbhchargeHFFr() {
        return nbhchargeHFFr;
    }

    /**
     * Sets the value of the nbhchargeHFFr property.
     * 
     */
    public void setNbhchargeHFFr(double value) {
        this.nbhchargeHFFr = value;
    }

    /**
     * Gets the value of the nbhcharge0ECS property.
     * 
     */
    public double getNbhcharge0ECS() {
        return nbhcharge0ECS;
    }

    /**
     * Sets the value of the nbhcharge0ECS property.
     * 
     */
    public void setNbhcharge0ECS(double value) {
        this.nbhcharge0ECS = value;
    }

    /**
     * Gets the value of the nbhcharge010ECS property.
     * 
     */
    public double getNbhcharge010ECS() {
        return nbhcharge010ECS;
    }

    /**
     * Sets the value of the nbhcharge010ECS property.
     * 
     */
    public void setNbhcharge010ECS(double value) {
        this.nbhcharge010ECS = value;
    }

    /**
     * Gets the value of the nbhcharge1020ECS property.
     * 
     */
    public double getNbhcharge1020ECS() {
        return nbhcharge1020ECS;
    }

    /**
     * Sets the value of the nbhcharge1020ECS property.
     * 
     */
    public void setNbhcharge1020ECS(double value) {
        this.nbhcharge1020ECS = value;
    }

    /**
     * Gets the value of the nbhcharge2030ECS property.
     * 
     */
    public double getNbhcharge2030ECS() {
        return nbhcharge2030ECS;
    }

    /**
     * Sets the value of the nbhcharge2030ECS property.
     * 
     */
    public void setNbhcharge2030ECS(double value) {
        this.nbhcharge2030ECS = value;
    }

    /**
     * Gets the value of the nbhcharge3040ECS property.
     * 
     */
    public double getNbhcharge3040ECS() {
        return nbhcharge3040ECS;
    }

    /**
     * Sets the value of the nbhcharge3040ECS property.
     * 
     */
    public void setNbhcharge3040ECS(double value) {
        this.nbhcharge3040ECS = value;
    }

    /**
     * Gets the value of the nbhcharge4050ECS property.
     * 
     */
    public double getNbhcharge4050ECS() {
        return nbhcharge4050ECS;
    }

    /**
     * Sets the value of the nbhcharge4050ECS property.
     * 
     */
    public void setNbhcharge4050ECS(double value) {
        this.nbhcharge4050ECS = value;
    }

    /**
     * Gets the value of the nbhcharge5060ECS property.
     * 
     */
    public double getNbhcharge5060ECS() {
        return nbhcharge5060ECS;
    }

    /**
     * Sets the value of the nbhcharge5060ECS property.
     * 
     */
    public void setNbhcharge5060ECS(double value) {
        this.nbhcharge5060ECS = value;
    }

    /**
     * Gets the value of the nbhcharge6070ECS property.
     * 
     */
    public double getNbhcharge6070ECS() {
        return nbhcharge6070ECS;
    }

    /**
     * Sets the value of the nbhcharge6070ECS property.
     * 
     */
    public void setNbhcharge6070ECS(double value) {
        this.nbhcharge6070ECS = value;
    }

    /**
     * Gets the value of the nbhcharge7080ECS property.
     * 
     */
    public double getNbhcharge7080ECS() {
        return nbhcharge7080ECS;
    }

    /**
     * Sets the value of the nbhcharge7080ECS property.
     * 
     */
    public void setNbhcharge7080ECS(double value) {
        this.nbhcharge7080ECS = value;
    }

    /**
     * Gets the value of the nbhcharge8090ECS property.
     * 
     */
    public double getNbhcharge8090ECS() {
        return nbhcharge8090ECS;
    }

    /**
     * Sets the value of the nbhcharge8090ECS property.
     * 
     */
    public void setNbhcharge8090ECS(double value) {
        this.nbhcharge8090ECS = value;
    }

    /**
     * Gets the value of the nbhcharge90100ECS property.
     * 
     */
    public double getNbhcharge90100ECS() {
        return nbhcharge90100ECS;
    }

    /**
     * Sets the value of the nbhcharge90100ECS property.
     * 
     */
    public void setNbhcharge90100ECS(double value) {
        this.nbhcharge90100ECS = value;
    }

    /**
     * Gets the value of the nbhchargeHFECS property.
     * 
     */
    public double getNbhchargeHFECS() {
        return nbhchargeHFECS;
    }

    /**
     * Sets the value of the nbhchargeHFECS property.
     * 
     */
    public void setNbhchargeHFECS(double value) {
        this.nbhchargeHFECS = value;
    }

    /**
     * Gets the value of the qFou3Postes property.
     * 
     */
    public double getQFou3Postes() {
        return qFou3Postes;
    }

    /**
     * Sets the value of the qFou3Postes property.
     * 
     */
    public void setQFou3Postes(double value) {
        this.qFou3Postes = value;
    }

}
