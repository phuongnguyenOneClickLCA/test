
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_YACK_YACKBionic
 * 
 * <p>Java class for YACKBionic_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="YACKBionic_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Vtot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_UA" type="{}E_Statut_UA"/>
 *         &lt;element name="UA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rdim_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Performance_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_PAC_CO2" type="{}E_Temperatures_Aval_PAC_CO2"/>
 *         &lt;element name="Theta_Amont_PAC_CO2" type="{}E_Temperatures_Amont_PAC_CO2"/>
 *         &lt;element name="Statut_Donnee_PAC_CO2" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Cop_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_PAC_CO2" type="{}E_Valeur_Declaree_Defaut_COP"/>
 *         &lt;element name="Statut_Taux_PAC_CO2" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur_PAC_CO2" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_PAC_CO2" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_PAC_CO2" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRContmin_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_PAC_CO2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rdim_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Performance_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_PAC_Eau_Eau" type="{}E_Temperatures_Aval_PAC_Eau_Eau"/>
 *         &lt;element name="Theta_Amont_PAC_Eau_Eau" type="{}E_Temperatures_Amont_PAC_Eau_Eau"/>
 *         &lt;element name="Statut_Donnee_PAC_Eau_Eau" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Cop_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_PAC_Eau_Eau" type="{}E_Valeur_Declaree_Defaut_COP"/>
 *         &lt;element name="Theta_max_av_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux_PAC_Eau_Eau" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur_PAC_Eau_Eau" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_PAC_Eau_Eau" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_PAC_Eau_Eau" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRContmin_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcircu_PAC_Eau_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "YACKBionic_Data", propOrder = {

})
public class YACKBionicData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Vtot")
    protected double vtot;
    @XmlElement(name = "Statut_UA", required = true)
    protected String statutUA;
    @XmlElement(name = "UA")
    protected double ua;
    @XmlElement(name = "Rdim_PAC_CO2")
    protected int rdimPACCO2;
    @XmlElement(name = "Performance_PAC_CO2")
    protected String performancePACCO2;
    @XmlElement(name = "Pabs_PAC_CO2")
    protected String pabsPACCO2;
    @XmlElement(name = "COR_PAC_CO2")
    protected String corpacco2;
    @XmlElement(name = "Theta_Aval_PAC_CO2", required = true)
    protected String thetaAvalPACCO2;
    @XmlElement(name = "Theta_Amont_PAC_CO2", required = true)
    protected String thetaAmontPACCO2;
    @XmlElement(name = "Statut_Donnee_PAC_CO2", required = true)
    protected String statutDonneePACCO2;
    @XmlElement(name = "Val_Cop_PAC_CO2")
    protected double valCopPACCO2;
    @XmlElement(name = "Val_Pabs_PAC_CO2")
    protected double valPabsPACCO2;
    @XmlElement(name = "Statut_Val_Pivot_PAC_CO2", required = true)
    protected String statutValPivotPACCO2;
    @XmlElement(name = "Statut_Taux_PAC_CO2", required = true)
    protected String statutTauxPACCO2;
    @XmlElement(name = "Taux_PAC_CO2")
    protected double tauxPACCO2;
    @XmlElement(name = "Fonctionnement_Compresseur_PAC_CO2", required = true)
    protected String fonctionnementCompresseurPACCO2;
    @XmlElement(name = "Statut_Fonct_Part_PAC_CO2", required = true)
    protected String statutFonctPartPACCO2;
    @XmlElement(name = "Statut_Fonctionnement_Continu_PAC_CO2", required = true)
    protected String statutFonctionnementContinuPACCO2;
    @XmlElement(name = "LRContmin_PAC_CO2")
    protected double lrContminPACCO2;
    @XmlElement(name = "CCP_LRcontmin_PAC_CO2")
    protected double ccplRcontminPACCO2;
    @XmlElement(name = "Rdim_PAC_Eau_Eau")
    protected int rdimPACEauEau;
    @XmlElement(name = "Performance_PAC_Eau_Eau")
    protected String performancePACEauEau;
    @XmlElement(name = "Pabs_PAC_Eau_Eau")
    protected String pabsPACEauEau;
    @XmlElement(name = "COR_PAC_Eau_Eau")
    protected String corpacEauEau;
    @XmlElement(name = "Theta_Aval_PAC_Eau_Eau", required = true)
    protected String thetaAvalPACEauEau;
    @XmlElement(name = "Theta_Amont_PAC_Eau_Eau", required = true)
    protected String thetaAmontPACEauEau;
    @XmlElement(name = "Statut_Donnee_PAC_Eau_Eau", required = true)
    protected String statutDonneePACEauEau;
    @XmlElement(name = "Val_Cop_PAC_Eau_Eau")
    protected double valCopPACEauEau;
    @XmlElement(name = "Val_Pabs_PAC_Eau_Eau")
    protected double valPabsPACEauEau;
    @XmlElement(name = "Statut_Val_Pivot_PAC_Eau_Eau", required = true)
    protected String statutValPivotPACEauEau;
    @XmlElement(name = "Theta_max_av_PAC_Eau_Eau")
    protected double thetaMaxAvPACEauEau;
    @XmlElement(name = "Statut_Taux_PAC_Eau_Eau", required = true)
    protected String statutTauxPACEauEau;
    @XmlElement(name = "Taux_PAC_Eau_Eau")
    protected double tauxPACEauEau;
    @XmlElement(name = "Fonctionnement_Compresseur_PAC_Eau_Eau", required = true)
    protected String fonctionnementCompresseurPACEauEau;
    @XmlElement(name = "Statut_Fonct_Part_PAC_Eau_Eau", required = true)
    protected String statutFonctPartPACEauEau;
    @XmlElement(name = "Statut_Fonctionnement_Continu_PAC_Eau_Eau", required = true)
    protected String statutFonctionnementContinuPACEauEau;
    @XmlElement(name = "LRContmin_PAC_Eau_Eau")
    protected double lrContminPACEauEau;
    @XmlElement(name = "CCP_LRcontmin_PAC_Eau_Eau")
    protected double ccplRcontminPACEauEau;
    @XmlElement(name = "Pcircu_PAC_Eau_Eau")
    protected double pcircuPACEauEau;

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
     * Gets the value of the rdimPACCO2 property.
     * 
     */
    public int getRdimPACCO2() {
        return rdimPACCO2;
    }

    /**
     * Sets the value of the rdimPACCO2 property.
     * 
     */
    public void setRdimPACCO2(int value) {
        this.rdimPACCO2 = value;
    }

    /**
     * Gets the value of the performancePACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformancePACCO2() {
        return performancePACCO2;
    }

    /**
     * Sets the value of the performancePACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformancePACCO2(String value) {
        this.performancePACCO2 = value;
    }

    /**
     * Gets the value of the pabsPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsPACCO2() {
        return pabsPACCO2;
    }

    /**
     * Sets the value of the pabsPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsPACCO2(String value) {
        this.pabsPACCO2 = value;
    }

    /**
     * Gets the value of the corpacco2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCORPACCO2() {
        return corpacco2;
    }

    /**
     * Sets the value of the corpacco2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCORPACCO2(String value) {
        this.corpacco2 = value;
    }

    /**
     * Gets the value of the thetaAvalPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalPACCO2() {
        return thetaAvalPACCO2;
    }

    /**
     * Sets the value of the thetaAvalPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalPACCO2(String value) {
        this.thetaAvalPACCO2 = value;
    }

    /**
     * Gets the value of the thetaAmontPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontPACCO2() {
        return thetaAmontPACCO2;
    }

    /**
     * Sets the value of the thetaAmontPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontPACCO2(String value) {
        this.thetaAmontPACCO2 = value;
    }

    /**
     * Gets the value of the statutDonneePACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneePACCO2() {
        return statutDonneePACCO2;
    }

    /**
     * Sets the value of the statutDonneePACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneePACCO2(String value) {
        this.statutDonneePACCO2 = value;
    }

    /**
     * Gets the value of the valCopPACCO2 property.
     * 
     */
    public double getValCopPACCO2() {
        return valCopPACCO2;
    }

    /**
     * Sets the value of the valCopPACCO2 property.
     * 
     */
    public void setValCopPACCO2(double value) {
        this.valCopPACCO2 = value;
    }

    /**
     * Gets the value of the valPabsPACCO2 property.
     * 
     */
    public double getValPabsPACCO2() {
        return valPabsPACCO2;
    }

    /**
     * Sets the value of the valPabsPACCO2 property.
     * 
     */
    public void setValPabsPACCO2(double value) {
        this.valPabsPACCO2 = value;
    }

    /**
     * Gets the value of the statutValPivotPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotPACCO2() {
        return statutValPivotPACCO2;
    }

    /**
     * Sets the value of the statutValPivotPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotPACCO2(String value) {
        this.statutValPivotPACCO2 = value;
    }

    /**
     * Gets the value of the statutTauxPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutTauxPACCO2() {
        return statutTauxPACCO2;
    }

    /**
     * Sets the value of the statutTauxPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutTauxPACCO2(String value) {
        this.statutTauxPACCO2 = value;
    }

    /**
     * Gets the value of the tauxPACCO2 property.
     * 
     */
    public double getTauxPACCO2() {
        return tauxPACCO2;
    }

    /**
     * Sets the value of the tauxPACCO2 property.
     * 
     */
    public void setTauxPACCO2(double value) {
        this.tauxPACCO2 = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseurPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseurPACCO2() {
        return fonctionnementCompresseurPACCO2;
    }

    /**
     * Sets the value of the fonctionnementCompresseurPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseurPACCO2(String value) {
        this.fonctionnementCompresseurPACCO2 = value;
    }

    /**
     * Gets the value of the statutFonctPartPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPartPACCO2() {
        return statutFonctPartPACCO2;
    }

    /**
     * Sets the value of the statutFonctPartPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPartPACCO2(String value) {
        this.statutFonctPartPACCO2 = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuPACCO2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinuPACCO2() {
        return statutFonctionnementContinuPACCO2;
    }

    /**
     * Sets the value of the statutFonctionnementContinuPACCO2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinuPACCO2(String value) {
        this.statutFonctionnementContinuPACCO2 = value;
    }

    /**
     * Gets the value of the lrContminPACCO2 property.
     * 
     */
    public double getLRContminPACCO2() {
        return lrContminPACCO2;
    }

    /**
     * Sets the value of the lrContminPACCO2 property.
     * 
     */
    public void setLRContminPACCO2(double value) {
        this.lrContminPACCO2 = value;
    }

    /**
     * Gets the value of the ccplRcontminPACCO2 property.
     * 
     */
    public double getCCPLRcontminPACCO2() {
        return ccplRcontminPACCO2;
    }

    /**
     * Sets the value of the ccplRcontminPACCO2 property.
     * 
     */
    public void setCCPLRcontminPACCO2(double value) {
        this.ccplRcontminPACCO2 = value;
    }

    /**
     * Gets the value of the rdimPACEauEau property.
     * 
     */
    public int getRdimPACEauEau() {
        return rdimPACEauEau;
    }

    /**
     * Sets the value of the rdimPACEauEau property.
     * 
     */
    public void setRdimPACEauEau(int value) {
        this.rdimPACEauEau = value;
    }

    /**
     * Gets the value of the performancePACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformancePACEauEau() {
        return performancePACEauEau;
    }

    /**
     * Sets the value of the performancePACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformancePACEauEau(String value) {
        this.performancePACEauEau = value;
    }

    /**
     * Gets the value of the pabsPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsPACEauEau() {
        return pabsPACEauEau;
    }

    /**
     * Sets the value of the pabsPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsPACEauEau(String value) {
        this.pabsPACEauEau = value;
    }

    /**
     * Gets the value of the corpacEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCORPACEauEau() {
        return corpacEauEau;
    }

    /**
     * Sets the value of the corpacEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCORPACEauEau(String value) {
        this.corpacEauEau = value;
    }

    /**
     * Gets the value of the thetaAvalPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalPACEauEau() {
        return thetaAvalPACEauEau;
    }

    /**
     * Sets the value of the thetaAvalPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalPACEauEau(String value) {
        this.thetaAvalPACEauEau = value;
    }

    /**
     * Gets the value of the thetaAmontPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontPACEauEau() {
        return thetaAmontPACEauEau;
    }

    /**
     * Sets the value of the thetaAmontPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontPACEauEau(String value) {
        this.thetaAmontPACEauEau = value;
    }

    /**
     * Gets the value of the statutDonneePACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneePACEauEau() {
        return statutDonneePACEauEau;
    }

    /**
     * Sets the value of the statutDonneePACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneePACEauEau(String value) {
        this.statutDonneePACEauEau = value;
    }

    /**
     * Gets the value of the valCopPACEauEau property.
     * 
     */
    public double getValCopPACEauEau() {
        return valCopPACEauEau;
    }

    /**
     * Sets the value of the valCopPACEauEau property.
     * 
     */
    public void setValCopPACEauEau(double value) {
        this.valCopPACEauEau = value;
    }

    /**
     * Gets the value of the valPabsPACEauEau property.
     * 
     */
    public double getValPabsPACEauEau() {
        return valPabsPACEauEau;
    }

    /**
     * Sets the value of the valPabsPACEauEau property.
     * 
     */
    public void setValPabsPACEauEau(double value) {
        this.valPabsPACEauEau = value;
    }

    /**
     * Gets the value of the statutValPivotPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotPACEauEau() {
        return statutValPivotPACEauEau;
    }

    /**
     * Sets the value of the statutValPivotPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotPACEauEau(String value) {
        this.statutValPivotPACEauEau = value;
    }

    /**
     * Gets the value of the thetaMaxAvPACEauEau property.
     * 
     */
    public double getThetaMaxAvPACEauEau() {
        return thetaMaxAvPACEauEau;
    }

    /**
     * Sets the value of the thetaMaxAvPACEauEau property.
     * 
     */
    public void setThetaMaxAvPACEauEau(double value) {
        this.thetaMaxAvPACEauEau = value;
    }

    /**
     * Gets the value of the statutTauxPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutTauxPACEauEau() {
        return statutTauxPACEauEau;
    }

    /**
     * Sets the value of the statutTauxPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutTauxPACEauEau(String value) {
        this.statutTauxPACEauEau = value;
    }

    /**
     * Gets the value of the tauxPACEauEau property.
     * 
     */
    public double getTauxPACEauEau() {
        return tauxPACEauEau;
    }

    /**
     * Sets the value of the tauxPACEauEau property.
     * 
     */
    public void setTauxPACEauEau(double value) {
        this.tauxPACEauEau = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseurPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseurPACEauEau() {
        return fonctionnementCompresseurPACEauEau;
    }

    /**
     * Sets the value of the fonctionnementCompresseurPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseurPACEauEau(String value) {
        this.fonctionnementCompresseurPACEauEau = value;
    }

    /**
     * Gets the value of the statutFonctPartPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPartPACEauEau() {
        return statutFonctPartPACEauEau;
    }

    /**
     * Sets the value of the statutFonctPartPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPartPACEauEau(String value) {
        this.statutFonctPartPACEauEau = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuPACEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinuPACEauEau() {
        return statutFonctionnementContinuPACEauEau;
    }

    /**
     * Sets the value of the statutFonctionnementContinuPACEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinuPACEauEau(String value) {
        this.statutFonctionnementContinuPACEauEau = value;
    }

    /**
     * Gets the value of the lrContminPACEauEau property.
     * 
     */
    public double getLRContminPACEauEau() {
        return lrContminPACEauEau;
    }

    /**
     * Sets the value of the lrContminPACEauEau property.
     * 
     */
    public void setLRContminPACEauEau(double value) {
        this.lrContminPACEauEau = value;
    }

    /**
     * Gets the value of the ccplRcontminPACEauEau property.
     * 
     */
    public double getCCPLRcontminPACEauEau() {
        return ccplRcontminPACEauEau;
    }

    /**
     * Sets the value of the ccplRcontminPACEauEau property.
     * 
     */
    public void setCCPLRcontminPACEauEau(double value) {
        this.ccplRcontminPACEauEau = value;
    }

    /**
     * Gets the value of the pcircuPACEauEau property.
     * 
     */
    public double getPcircuPACEauEau() {
        return pcircuPACEauEau;
    }

    /**
     * Sets the value of the pcircuPACEauEau property.
     * 
     */
    public void setPcircuPACEauEau(double value) {
        this.pcircuPACEauEau = value;
    }

}
