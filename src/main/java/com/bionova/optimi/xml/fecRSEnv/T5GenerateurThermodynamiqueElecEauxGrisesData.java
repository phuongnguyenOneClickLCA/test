
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_BIOFLUIDES-SOLARONICS_Eaux-grises
 * 
 * <p>Java class for T5_Generateur_Thermodynamique_Elec_Eaux_grises_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Generateur_Thermodynamique_Elec_Eaux_grises_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_val_pivot" type="{}E_Val_Declaree_Defaut"/>
 *         &lt;element name="ValECS_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ValPabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_donnees" type="{}E_Ex_Valeur_Certifiee"/>
 *         &lt;element name="ValECS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValPabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValCOR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ppompe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_min_am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_max_av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Fonct_Part" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Generateur_Thermodynamique_Elec_Eaux_grises_Data", propOrder = {

})
public class T5GenerateurThermodynamiqueElecEauxGrisesData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Statut_val_pivot", required = true)
    protected String statutValPivot;
    @XmlElement(name = "ValECS_pivot")
    protected double valECSPivot;
    @XmlElement(name = "ValPabs_pivot")
    protected double valPabsPivot;
    @XmlElement(name = "Statut_donnees", required = true)
    protected String statutDonnees;
    @XmlElement(name = "ValECS")
    protected String valECS;
    @XmlElement(name = "ValPabs")
    protected String valPabs;
    @XmlElement(name = "ValCOR")
    protected String valCOR;
    @XmlElement(name = "Ppompe")
    protected double ppompe;
    @XmlElement(name = "Lim_Theta", required = true)
    protected String limTheta;
    @XmlElement(name = "Theta_min_am")
    protected double thetaMinAm;
    @XmlElement(name = "Theta_max_av")
    protected double thetaMaxAv;
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
     * Gets the value of the valECSPivot property.
     * 
     */
    public double getValECSPivot() {
        return valECSPivot;
    }

    /**
     * Sets the value of the valECSPivot property.
     * 
     */
    public void setValECSPivot(double value) {
        this.valECSPivot = value;
    }

    /**
     * Gets the value of the valPabsPivot property.
     * 
     */
    public double getValPabsPivot() {
        return valPabsPivot;
    }

    /**
     * Sets the value of the valPabsPivot property.
     * 
     */
    public void setValPabsPivot(double value) {
        this.valPabsPivot = value;
    }

    /**
     * Gets the value of the statutDonnees property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonnees() {
        return statutDonnees;
    }

    /**
     * Sets the value of the statutDonnees property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonnees(String value) {
        this.statutDonnees = value;
    }

    /**
     * Gets the value of the valECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValECS() {
        return valECS;
    }

    /**
     * Sets the value of the valECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValECS(String value) {
        this.valECS = value;
    }

    /**
     * Gets the value of the valPabs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValPabs() {
        return valPabs;
    }

    /**
     * Sets the value of the valPabs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValPabs(String value) {
        this.valPabs = value;
    }

    /**
     * Gets the value of the valCOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValCOR() {
        return valCOR;
    }

    /**
     * Sets the value of the valCOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValCOR(String value) {
        this.valCOR = value;
    }

    /**
     * Gets the value of the ppompe property.
     * 
     */
    public double getPpompe() {
        return ppompe;
    }

    /**
     * Sets the value of the ppompe property.
     * 
     */
    public void setPpompe(double value) {
        this.ppompe = value;
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

}
