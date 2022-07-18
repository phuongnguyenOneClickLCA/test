
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Source_Ballon_Base_Thermodynamique_Elec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Source_Ballon_Base_Thermodynamique_Elec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo_Ecs" type="{}E_Systeme_Thermodynamique_Ecs"/>
 *         &lt;element name="Id_Groupe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Extrait_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Extrait_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Ambiant_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Ambiant_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_De_Nappe_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_De_Nappe_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Sol_Eau_Ecs" type="{}E_Temperatures_Amont_Sol_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Sol_Eau_Ecs" type="{}E_Temperatures_Aval_Sol_Eau_Ecs"/>
 *         &lt;element name="Performance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Cop" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Declaree_Defaut" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur" type="{}E_Emetteur"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau_Ecs" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau_Ecs" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Source_Ballon_Base_Thermodynamique_Elec", propOrder = {

})
public class RTDataSourceBallonBaseThermodynamiqueElec {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Id_Source_Amont", defaultValue = "0")
    protected int idSourceAmont;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Sys_Thermo_Ecs", required = true)
    protected String sysThermoEcs;
    @XmlElement(name = "Id_Groupe", defaultValue = "0")
    protected int idGroupe;
    @XmlElement(name = "Statut_Donnee", required = true)
    protected String statutDonnee;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ecs", required = true)
    protected String thetaAvalAirEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ecs", required = true)
    protected String thetaAmontAirEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Eau_Ecs", required = true)
    protected String thetaAvalAirExtraitEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Eau_Ecs", required = true)
    protected String thetaAmontAirExtraitEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Ambiant_Eau_Ecs", required = true)
    protected String thetaAvalAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Ambiant_Eau_Ecs", required = true)
    protected String thetaAmontAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau_Ecs", required = true)
    protected String thetaAvalEauDeNappeEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau_Ecs", required = true)
    protected String thetaAmontEauDeNappeEauEcs;
    @XmlElement(name = "Theta_Amont_Sol_Eau_Ecs", required = true)
    protected String thetaAmontSolEauEcs;
    @XmlElement(name = "Theta_Aval_Sol_Eau_Ecs", required = true)
    protected String thetaAvalSolEauEcs;
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
    @XmlElement(name = "Valeur_Declaree_Defaut", required = true)
    protected String valeurDeclareeDefaut;
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
    @XmlElement(name = "Typo_Emetteur", required = true)
    protected String typoEmetteur;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Ecs")
    protected String thetaAmontEauGlycoleeEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Ecs")
    protected String thetaAvalEauGlycoleeEauEcs;

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
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRdim(Integer value) {
        this.rdim = value;
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
     * Gets the value of the sysThermoEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoEcs() {
        return sysThermoEcs;
    }

    /**
     * Sets the value of the sysThermoEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoEcs(String value) {
        this.sysThermoEcs = value;
    }

    /**
     * Gets the value of the idGroupe property.
     * 
     */
    public int getIdGroupe() {
        return idGroupe;
    }

    /**
     * Sets the value of the idGroupe property.
     * 
     */
    public void setIdGroupe(int value) {
        this.idGroupe = value;
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
     * Gets the value of the thetaAvalAirEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauEcs() {
        return thetaAvalAirEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauEcs(String value) {
        this.thetaAvalAirEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauEcs() {
        return thetaAmontAirEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauEcs(String value) {
        this.thetaAmontAirEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirExtraitEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirExtraitEauEcs() {
        return thetaAvalAirExtraitEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirExtraitEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirExtraitEauEcs(String value) {
        this.thetaAvalAirExtraitEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirExtraitEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirExtraitEauEcs() {
        return thetaAmontAirExtraitEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirExtraitEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirExtraitEauEcs(String value) {
        this.thetaAmontAirExtraitEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirAmbiantEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirAmbiantEauEcs() {
        return thetaAvalAirAmbiantEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirAmbiantEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirAmbiantEauEcs(String value) {
        this.thetaAvalAirAmbiantEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirAmbiantEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirAmbiantEauEcs() {
        return thetaAmontAirAmbiantEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirAmbiantEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirAmbiantEauEcs(String value) {
        this.thetaAmontAirAmbiantEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeNappeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauDeNappeEauEcs() {
        return thetaAvalEauDeNappeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauDeNappeEauEcs(String value) {
        this.thetaAvalEauDeNappeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauDeNappeEauEcs() {
        return thetaAmontEauDeNappeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauDeNappeEauEcs(String value) {
        this.thetaAmontEauDeNappeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontSolEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontSolEauEcs() {
        return thetaAmontSolEauEcs;
    }

    /**
     * Sets the value of the thetaAmontSolEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontSolEauEcs(String value) {
        this.thetaAmontSolEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalSolEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalSolEauEcs() {
        return thetaAvalSolEauEcs;
    }

    /**
     * Sets the value of the thetaAvalSolEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalSolEauEcs(String value) {
        this.thetaAvalSolEauEcs = value;
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
     * Gets the value of the valeurDeclareeDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurDeclareeDefaut() {
        return valeurDeclareeDefaut;
    }

    /**
     * Sets the value of the valeurDeclareeDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurDeclareeDefaut(String value) {
        this.valeurDeclareeDefaut = value;
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

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEauEcs() {
        return thetaAmontEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEauEcs(String value) {
        this.thetaAmontEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauEcs() {
        return thetaAvalEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauEcs(String value) {
        this.thetaAvalEauGlycoleeEauEcs = value;
    }

}
