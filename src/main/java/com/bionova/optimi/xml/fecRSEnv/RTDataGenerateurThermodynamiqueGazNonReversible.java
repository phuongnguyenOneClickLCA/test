
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Thermodynamique_Gaz_NonReversible complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Thermodynamique_Gaz_NonReversible">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_FouGen_Mod" type="{}E_Id_FouGen_Mode7"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo_Abso_Ch" type="{}E_Systeme_Thermodynamique_Absorption_Chauffage"/>
 *         &lt;element name="Sys_Thermo_Abso_Fr" type="{}E_Systeme_Thermodynamique_Absorption_Refroidissement"/>
 *         &lt;element name="Sys_Thermo_Abso_Ecs" type="{}E_Systeme_Thermodynamique_Absorption_Ecs"/>
 *         &lt;element name="Statut_Donnee" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Cycle_Fr" type="{}E_Cycle_Fr"/>
 *         &lt;element name="Theta_Aval_Air_Eau_classique" type="{}E_Temperatures_Aval_Air_Eau_classique"/>
 *         &lt;element name="Theta_Amont_Air_Eau" type="{}E_Temperatures_Amont_Air_Exterieur"/>
 *         &lt;element name="Theta_Aval_Air_Eau_haute_temperature" type="{}E_Temperatures_Aval_Air_Eau_haute_temperature"/>
 *         &lt;element name="Theta_Aval_Eau_glycolee_Eau_classique" type="{}E_Temperatures_Aval_Eau_glycolee_Eau_classique"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau" type="{}E_Temperatures_Amont_Eau_Capteur_Geothermiques"/>
 *         &lt;element name="Theta_Aval_Eau_glycolee_Eau_haute_temperature" type="{}E_Temperatures_Aval_Eau_glycolee_Eau_haute_temperature"/>
 *         &lt;element name="Theta_Amont_Eau_Eau" type="{}E_Temperatures_Amont_Eau_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Eau" type="{}E_Temperatures_Aval_Eau_Eau"/>
 *         &lt;element name="Theta_Aval_Air_Eau" type="{}E_Temperatures_Aval_Air_Eau" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_ECS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Fr" type="{}E_Temperatures_Amont_Air_Eau"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Fr" type="{}E_Temperatures_Aval_Refroidisseur_Eau_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_Tour" type="{}E_Temperatures_Amont_Eau_Tour"/>
 *         &lt;element name="GUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_GUE_Pivot" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_pc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Bruleur" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Bruleur" type="{}E_Fonctionnement_Bruleur"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Autres_Donnees" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Paux0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur" type="{}E_Emetteur"/>
 *         &lt;element name="Statut_Echangeur" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Echangeur_Eau_Ch_Fumee" type="{}RT_Oui_Non"/>
 *         &lt;element name="Rdt_Comp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pertes_40deg" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "RT_Data_Generateur_Thermodynamique_Gaz_NonReversible", propOrder = {

})
public class RTDataGenerateurThermodynamiqueGazNonReversible {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Id_Source_Amont", defaultValue = "0")
    protected int idSourceAmont;
    @XmlElement(name = "Id_FouGen_Mod", required = true)
    protected String idFouGenMod;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Sys_Thermo_Abso_Ch", required = true)
    protected String sysThermoAbsoCh;
    @XmlElement(name = "Sys_Thermo_Abso_Fr", required = true)
    protected String sysThermoAbsoFr;
    @XmlElement(name = "Sys_Thermo_Abso_Ecs", required = true)
    protected String sysThermoAbsoEcs;
    @XmlElement(name = "Statut_Donnee", required = true)
    protected String statutDonnee;
    @XmlElement(name = "Cycle_Fr", required = true)
    protected String cycleFr;
    @XmlElement(name = "Theta_Aval_Air_Eau_classique", required = true)
    protected String thetaAvalAirEauClassique;
    @XmlElement(name = "Theta_Amont_Air_Eau", required = true)
    protected String thetaAmontAirEau;
    @XmlElement(name = "Theta_Aval_Air_Eau_haute_temperature", required = true)
    protected String thetaAvalAirEauHauteTemperature;
    @XmlElement(name = "Theta_Aval_Eau_glycolee_Eau_classique", required = true)
    protected String thetaAvalEauGlycoleeEauClassique;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau", required = true)
    protected String thetaAmontEauGlycoleeEau;
    @XmlElement(name = "Theta_Aval_Eau_glycolee_Eau_haute_temperature", required = true)
    protected String thetaAvalEauGlycoleeEauHauteTemperature;
    @XmlElement(name = "Theta_Amont_Eau_Eau", required = true)
    protected String thetaAmontEauEau;
    @XmlElement(name = "Theta_Aval_Eau_Eau", required = true)
    protected String thetaAvalEauEau;
    @XmlElement(name = "Theta_Aval_Air_Eau")
    protected String thetaAvalAirEau;
    @XmlElement(name = "Theta_Aval_ECS")
    protected int thetaAvalECS;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    protected String thetaAmontAirEauFr;
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Eau_Tour", required = true)
    protected String thetaAmontEauTour;
    @XmlElement(name = "GUE")
    protected String gue;
    @XmlElement(name = "Pabs")
    protected String pabs;
    @XmlElement(name = "COR")
    protected String cor;
    @XmlElement(name = "Paux_pc")
    protected String pauxPc;
    @XmlElement(name = "Statut_GUE_Pivot", required = true)
    protected String statutGUEPivot;
    @XmlElement(name = "Val_GUE")
    protected double valGUE;
    @XmlElement(name = "Statut_Paux_Pivot", required = true)
    protected String statutPauxPivot;
    @XmlElement(name = "Val_Paux_pc")
    protected double valPauxPc;
    @XmlElement(name = "Val_Pabs")
    protected double valPabs;
    @XmlElement(name = "Lim_Theta", required = true)
    protected String limTheta;
    @XmlElement(name = "Theta_Max_Av")
    protected double thetaMaxAv;
    @XmlElement(name = "Theta_Min_Am")
    protected double thetaMinAm;
    @XmlElement(name = "Theta_Max_Am")
    protected double thetaMaxAm;
    @XmlElement(name = "Theta_Min_Av")
    protected double thetaMinAv;
    @XmlElement(name = "Statut_Bruleur", required = true)
    protected String statutBruleur;
    @XmlElement(name = "Fonctionnement_Bruleur", required = true)
    protected String fonctionnementBruleur;
    @XmlElement(name = "LRcontmin")
    protected double lRcontmin;
    @XmlElement(name = "CCP_LRcontmin")
    protected double ccplRcontmin;
    @XmlElement(name = "Statut_Autres_Donnees", required = true)
    protected String statutAutresDonnees;
    @XmlElement(name = "Paux0")
    protected double paux0;
    @XmlElement(name = "Typo_Emetteur", required = true)
    protected String typoEmetteur;
    @XmlElement(name = "Statut_Echangeur", required = true)
    protected String statutEchangeur;
    @XmlElement(name = "Echangeur_Eau_Ch_Fumee", required = true)
    protected String echangeurEauChFumee;
    @XmlElement(name = "Rdt_Comp")
    protected double rdtComp;
    @XmlElement(name = "Pertes_40deg")
    protected double pertes40Deg;
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
     * Gets the value of the idFouGenMod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouGenMod() {
        return idFouGenMod;
    }

    /**
     * Sets the value of the idFouGenMod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouGenMod(String value) {
        this.idFouGenMod = value;
    }

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
     * Gets the value of the idprioriteFr property.
     * 
     */
    public int getIdprioriteFr() {
        return idprioriteFr;
    }

    /**
     * Sets the value of the idprioriteFr property.
     * 
     */
    public void setIdprioriteFr(int value) {
        this.idprioriteFr = value;
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
     * Gets the value of the sysThermoAbsoCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoAbsoCh() {
        return sysThermoAbsoCh;
    }

    /**
     * Sets the value of the sysThermoAbsoCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoAbsoCh(String value) {
        this.sysThermoAbsoCh = value;
    }

    /**
     * Gets the value of the sysThermoAbsoFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoAbsoFr() {
        return sysThermoAbsoFr;
    }

    /**
     * Sets the value of the sysThermoAbsoFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoAbsoFr(String value) {
        this.sysThermoAbsoFr = value;
    }

    /**
     * Gets the value of the sysThermoAbsoEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoAbsoEcs() {
        return sysThermoAbsoEcs;
    }

    /**
     * Sets the value of the sysThermoAbsoEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoAbsoEcs(String value) {
        this.sysThermoAbsoEcs = value;
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
     * Gets the value of the cycleFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCycleFr() {
        return cycleFr;
    }

    /**
     * Sets the value of the cycleFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCycleFr(String value) {
        this.cycleFr = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauClassique property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauClassique() {
        return thetaAvalAirEauClassique;
    }

    /**
     * Sets the value of the thetaAvalAirEauClassique property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauClassique(String value) {
        this.thetaAvalAirEauClassique = value;
    }

    /**
     * Gets the value of the thetaAmontAirEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEau() {
        return thetaAmontAirEau;
    }

    /**
     * Sets the value of the thetaAmontAirEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEau(String value) {
        this.thetaAmontAirEau = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauHauteTemperature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauHauteTemperature() {
        return thetaAvalAirEauHauteTemperature;
    }

    /**
     * Sets the value of the thetaAvalAirEauHauteTemperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauHauteTemperature(String value) {
        this.thetaAvalAirEauHauteTemperature = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauClassique property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauClassique() {
        return thetaAvalEauGlycoleeEauClassique;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauClassique property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauClassique(String value) {
        this.thetaAvalEauGlycoleeEauClassique = value;
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
     * Gets the value of the thetaAvalEauGlycoleeEauHauteTemperature property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauHauteTemperature() {
        return thetaAvalEauGlycoleeEauHauteTemperature;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauHauteTemperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauHauteTemperature(String value) {
        this.thetaAvalEauGlycoleeEauHauteTemperature = value;
    }

    /**
     * Gets the value of the thetaAmontEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauEau() {
        return thetaAmontEauEau;
    }

    /**
     * Sets the value of the thetaAmontEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauEau(String value) {
        this.thetaAmontEauEau = value;
    }

    /**
     * Gets the value of the thetaAvalEauEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauEau() {
        return thetaAvalEauEau;
    }

    /**
     * Sets the value of the thetaAvalEauEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauEau(String value) {
        this.thetaAvalEauEau = value;
    }

    /**
     * Gets the value of the thetaAvalAirEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEau() {
        return thetaAvalAirEau;
    }

    /**
     * Sets the value of the thetaAvalAirEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEau(String value) {
        this.thetaAvalAirEau = value;
    }

    /**
     * Gets the value of the thetaAvalECS property.
     * 
     */
    public int getThetaAvalECS() {
        return thetaAvalECS;
    }

    /**
     * Sets the value of the thetaAvalECS property.
     * 
     */
    public void setThetaAvalECS(int value) {
        this.thetaAvalECS = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauFr() {
        return thetaAmontAirEauFr;
    }

    /**
     * Sets the value of the thetaAmontAirEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauFr(String value) {
        this.thetaAmontAirEauFr = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauFr() {
        return thetaAvalAirEauFr;
    }

    /**
     * Sets the value of the thetaAvalAirEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauFr(String value) {
        this.thetaAvalAirEauFr = value;
    }

    /**
     * Gets the value of the thetaAmontEauTour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauTour() {
        return thetaAmontEauTour;
    }

    /**
     * Sets the value of the thetaAmontEauTour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauTour(String value) {
        this.thetaAmontEauTour = value;
    }

    /**
     * Gets the value of the gue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUE() {
        return gue;
    }

    /**
     * Sets the value of the gue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUE(String value) {
        this.gue = value;
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
     * Gets the value of the pauxPc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxPc() {
        return pauxPc;
    }

    /**
     * Sets the value of the pauxPc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxPc(String value) {
        this.pauxPc = value;
    }

    /**
     * Gets the value of the statutGUEPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutGUEPivot() {
        return statutGUEPivot;
    }

    /**
     * Sets the value of the statutGUEPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutGUEPivot(String value) {
        this.statutGUEPivot = value;
    }

    /**
     * Gets the value of the valGUE property.
     * 
     */
    public double getValGUE() {
        return valGUE;
    }

    /**
     * Sets the value of the valGUE property.
     * 
     */
    public void setValGUE(double value) {
        this.valGUE = value;
    }

    /**
     * Gets the value of the statutPauxPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxPivot() {
        return statutPauxPivot;
    }

    /**
     * Sets the value of the statutPauxPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxPivot(String value) {
        this.statutPauxPivot = value;
    }

    /**
     * Gets the value of the valPauxPc property.
     * 
     */
    public double getValPauxPc() {
        return valPauxPc;
    }

    /**
     * Sets the value of the valPauxPc property.
     * 
     */
    public void setValPauxPc(double value) {
        this.valPauxPc = value;
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
     * Gets the value of the thetaMaxAm property.
     * 
     */
    public double getThetaMaxAm() {
        return thetaMaxAm;
    }

    /**
     * Sets the value of the thetaMaxAm property.
     * 
     */
    public void setThetaMaxAm(double value) {
        this.thetaMaxAm = value;
    }

    /**
     * Gets the value of the thetaMinAv property.
     * 
     */
    public double getThetaMinAv() {
        return thetaMinAv;
    }

    /**
     * Sets the value of the thetaMinAv property.
     * 
     */
    public void setThetaMinAv(double value) {
        this.thetaMinAv = value;
    }

    /**
     * Gets the value of the statutBruleur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutBruleur() {
        return statutBruleur;
    }

    /**
     * Sets the value of the statutBruleur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutBruleur(String value) {
        this.statutBruleur = value;
    }

    /**
     * Gets the value of the fonctionnementBruleur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementBruleur() {
        return fonctionnementBruleur;
    }

    /**
     * Sets the value of the fonctionnementBruleur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementBruleur(String value) {
        this.fonctionnementBruleur = value;
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
     * Gets the value of the statutAutresDonnees property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutAutresDonnees() {
        return statutAutresDonnees;
    }

    /**
     * Sets the value of the statutAutresDonnees property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutAutresDonnees(String value) {
        this.statutAutresDonnees = value;
    }

    /**
     * Gets the value of the paux0 property.
     * 
     */
    public double getPaux0() {
        return paux0;
    }

    /**
     * Sets the value of the paux0 property.
     * 
     */
    public void setPaux0(double value) {
        this.paux0 = value;
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
     * Gets the value of the statutEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutEchangeur() {
        return statutEchangeur;
    }

    /**
     * Sets the value of the statutEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutEchangeur(String value) {
        this.statutEchangeur = value;
    }

    /**
     * Gets the value of the echangeurEauChFumee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEchangeurEauChFumee() {
        return echangeurEauChFumee;
    }

    /**
     * Sets the value of the echangeurEauChFumee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEchangeurEauChFumee(String value) {
        this.echangeurEauChFumee = value;
    }

    /**
     * Gets the value of the rdtComp property.
     * 
     */
    public double getRdtComp() {
        return rdtComp;
    }

    /**
     * Sets the value of the rdtComp property.
     * 
     */
    public void setRdtComp(double value) {
        this.rdtComp = value;
    }

    /**
     * Gets the value of the pertes40Deg property.
     * 
     */
    public double getPertes40Deg() {
        return pertes40Deg;
    }

    /**
     * Sets the value of the pertes40Deg property.
     * 
     */
    public void setPertes40Deg(double value) {
        this.pertes40Deg = value;
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
