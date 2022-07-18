
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Generateur_Thermodynamique_Elec_ECSsurEauGlycolee_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Generateur_Thermodynamique_Elec_ECSsurEauGlycolee_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau"/>
 *         &lt;element name="Performance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Cop" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Fonct_Part" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur" type="{}E_Emetteur" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Generateur_Thermodynamique_Elec_ECSsurEauGlycolee_Data", propOrder = {

})
public class GenerateurThermodynamiqueElecECSsurEauGlycoleeData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Id_Source_Amont", defaultValue = "0")
    protected int idSourceAmont;
    @XmlElement(name = "Statut_Donnee", required = true)
    protected String statutDonnee;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau", required = true)
    protected String thetaAvalEauGlycoleeEau;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau", required = true)
    protected String thetaAmontEauGlycoleeEau;
    @XmlElement(name = "Performance")
    protected String performance;
    @XmlElement(name = "Pabs")
    protected String pabs;
    @XmlElement(name = "COR")
    protected String cor;
    @XmlElement(name = "Statut_Val_Pivot", required = true)
    protected String statutValPivot;
    @XmlElement(name = "Val_Cop")
    protected double valCop;
    @XmlElement(name = "Val_Pabs")
    protected double valPabs;
    @XmlElement(name = "Lim_Theta", required = true)
    protected String limTheta;
    @XmlElement(name = "Theta_Max_Av")
    protected double thetaMaxAv;
    @XmlElement(name = "Theta_Min_Am")
    protected double thetaMinAm;
    @XmlElement(name = "Statut_Fonct_Part", required = true)
    protected String statutFonctPart;
    @XmlElement(name = "Fonctionnement_Compresseur", required = true)
    protected String fonctionnementCompresseur;
    @XmlElement(name = "Statut_Fonctionnement_Continu", required = true)
    protected String statutFonctionnementContinu;
    @XmlElement(name = "LRcontmin")
    protected double lRcontmin;
    @XmlElement(name = "CCP_LRcontmin")
    protected double ccplRcontmin;
    @XmlElement(name = "Statut_Taux", required = true)
    protected String statutTaux;
    @XmlElement(name = "Taux")
    protected double taux;
    @XmlElement(name = "Typo_Emetteur")
    protected String typoEmetteur;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the idprioriteEcs property.
     * 
     */
    public int getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     */
    public void setIdprioriteEcs(int value) {
        this.idprioriteEcs = value;
    }

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
     * Gets the value of the thetaAvalEauGlycoleeEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEau() {
        return thetaAvalEauGlycoleeEau;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEau(String value) {
        this.thetaAvalEauGlycoleeEau = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEau() {
        return thetaAmontEauGlycoleeEau;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEau(String value) {
        this.thetaAmontEauGlycoleeEau = value;
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
     * Gets the value of the limTheta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimTheta() {
        return limTheta;
    }

    /**
     * Sets the value of the limTheta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimTheta(String value) {
        this.limTheta = value;
    }

    /**
     * Gets the value of the thetaMaxAv property.
     * 
     */
    public double getThetaMaxAv() {
        return thetaMaxAv;
    }

    /**
     * Sets the value of the thetaMaxAv property.
     * 
     */
    public void setThetaMaxAv(double value) {
        this.thetaMaxAv = value;
    }

    /**
     * Gets the value of the thetaMinAm property.
     * 
     */
    public double getThetaMinAm() {
        return thetaMinAm;
    }

    /**
     * Sets the value of the thetaMinAm property.
     * 
     */
    public void setThetaMinAm(double value) {
        this.thetaMinAm = value;
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
     * Gets the value of the lRcontmin property.
     * 
     */
    public double getLRcontmin() {
        return lRcontmin;
    }

    /**
     * Sets the value of the lRcontmin property.
     * 
     */
    public void setLRcontmin(double value) {
        this.lRcontmin = value;
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
     * Gets the value of the typoEmetteur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoEmetteur() {
        return typoEmetteur;
    }

    /**
     * Sets the value of the typoEmetteur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoEmetteur(String value) {
        this.typoEmetteur = value;
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

}
