
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_UNICLIMA_PAC_CO2 - 8100
 * 
 * <p>Java class for PAC_CO2_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PAC_CO2_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Performance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval" type="{}E_Temperatures_Aval"/>
 *         &lt;element name="Theta_Amont" type="{}E_Temperatures_Amont"/>
 *         &lt;element name="Statut_Donnee" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Cop" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot" type="{}E_Valeur_Declaree_Defaut_COP"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRContmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PAC_CO2_Data", propOrder = {

})
public class PACCO2Data {

    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Performance")
    protected String performance;
    @XmlElement(name = "Pabs")
    protected String pabs;
    @XmlElement(name = "COR")
    protected String cor;
    @XmlElement(name = "Theta_Aval", required = true)
    protected String thetaAval;
    @XmlElement(name = "Theta_Amont", required = true)
    protected String thetaAmont;
    @XmlElement(name = "Statut_Donnee", required = true)
    protected String statutDonnee;
    @XmlElement(name = "Val_Cop")
    protected double valCop;
    @XmlElement(name = "Val_Pabs")
    protected double valPabs;
    @XmlElement(name = "Statut_Val_Pivot", required = true)
    protected String statutValPivot;
    @XmlElement(name = "Statut_Taux", required = true)
    protected String statutTaux;
    @XmlElement(name = "Taux")
    protected double taux;
    @XmlElement(name = "Fonctionnement_Compresseur", required = true)
    protected String fonctionnementCompresseur;
    @XmlElement(name = "Statut_Fonct_Part", required = true)
    protected String statutFonctPart;
    @XmlElement(name = "Statut_Fonctionnement_Continu", required = true)
    protected String statutFonctionnementContinu;
    @XmlElement(name = "LRContmin")
    protected double lrContmin;
    @XmlElement(name = "CCP_LRcontmin")
    protected double ccplRcontmin;

    /**
     * Gets the value of the idSourceAmont property.
     * 
     */
    public int getIdSourceAmont() {
        return idSourceAmont;
    }

    /**
     * Sets the value of the idSourceAmont property.
     * 
     */
    public void setIdSourceAmont(int value) {
        this.idSourceAmont = value;
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
     * Gets the value of the rdim property.
     * 
     */
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    public void setRdim(int value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the performance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformance() {
        return performance;
    }

    /**
     * Sets the value of the performance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformance(String value) {
        this.performance = value;
    }

    /**
     * Gets the value of the pabs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabs() {
        return pabs;
    }

    /**
     * Sets the value of the pabs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabs(String value) {
        this.pabs = value;
    }

    /**
     * Gets the value of the cor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOR() {
        return cor;
    }

    /**
     * Sets the value of the cor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOR(String value) {
        this.cor = value;
    }

    /**
     * Gets the value of the thetaAval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAval() {
        return thetaAval;
    }

    /**
     * Sets the value of the thetaAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAval(String value) {
        this.thetaAval = value;
    }

    /**
     * Gets the value of the thetaAmont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmont() {
        return thetaAmont;
    }

    /**
     * Sets the value of the thetaAmont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmont(String value) {
        this.thetaAmont = value;
    }

    /**
     * Gets the value of the statutDonnee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonnee() {
        return statutDonnee;
    }

    /**
     * Sets the value of the statutDonnee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonnee(String value) {
        this.statutDonnee = value;
    }

    /**
     * Gets the value of the valCop property.
     * 
     */
    public double getValCop() {
        return valCop;
    }

    /**
     * Sets the value of the valCop property.
     * 
     */
    public void setValCop(double value) {
        this.valCop = value;
    }

    /**
     * Gets the value of the valPabs property.
     * 
     */
    public double getValPabs() {
        return valPabs;
    }

    /**
     * Sets the value of the valPabs property.
     * 
     */
    public void setValPabs(double value) {
        this.valPabs = value;
    }

    /**
     * Gets the value of the statutValPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivot() {
        return statutValPivot;
    }

    /**
     * Sets the value of the statutValPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivot(String value) {
        this.statutValPivot = value;
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

    /**
     * Gets the value of the fonctionnementCompresseur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseur() {
        return fonctionnementCompresseur;
    }

    /**
     * Sets the value of the fonctionnementCompresseur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseur(String value) {
        this.fonctionnementCompresseur = value;
    }

    /**
     * Gets the value of the statutFonctPart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPart() {
        return statutFonctPart;
    }

    /**
     * Sets the value of the statutFonctPart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPart(String value) {
        this.statutFonctPart = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinu() {
        return statutFonctionnementContinu;
    }

    /**
     * Sets the value of the statutFonctionnementContinu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinu(String value) {
        this.statutFonctionnementContinu = value;
    }

    /**
     * Gets the value of the lrContmin property.
     * 
     */
    public double getLRContmin() {
        return lrContmin;
    }

    /**
     * Sets the value of the lrContmin property.
     * 
     */
    public void setLRContmin(double value) {
        this.lrContmin = value;
    }

    /**
     * Gets the value of the ccplRcontmin property.
     * 
     */
    public double getCCPLRcontmin() {
        return ccplRcontmin;
    }

    /**
     * Sets the value of the ccplRcontmin property.
     * 
     */
    public void setCCPLRcontmin(double value) {
        this.ccplRcontmin = value;
    }

}
