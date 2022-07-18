
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Thermodynamique_Gaz_Reversible complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Thermodynamique_Gaz_Reversible">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo" type="{}E_Systeme_Thermodynamique_Gaz_Reversible"/>
 *         &lt;element name="Id_Groupe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_classique" type="{}E_Temperatures_Aval_Air_Eau_classique"/>
 *         &lt;element name="Theta_Amont_Air_Eau" type="{}E_Temperatures_Amont_Air_Exterieur"/>
 *         &lt;element name="Theta_Amont_Eau_Eau" type="{}E_Temperatures_Amont_Eau_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Eau" type="{}E_Temperatures_Aval_Eau_Eau"/>
 *         &lt;element name="GUE_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_GUE_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_pc_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Bruleur_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Bruleur_Ch" type="{}E_Fonctionnement_Bruleur"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Autres_Donnees_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Paux0_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Emetteur"/>
 *         &lt;element name="Statut_Echangeur_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Echangeur_Eau_Ch_Fumee" type="{}RT_Oui_Non"/>
 *         &lt;element name="Rdt_Comp_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pertes_40deg_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Source_Amont_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_Fr" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Cycle_Fr" type="{}E_Cycle_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Fr" type="{}E_Temperatures_Amont_Air_Eau"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Fr" type="{}E_Temperatures_Aval_Refroidisseur_Eau_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_Tour_Fr" type="{}E_Temperatures_Amont_Eau_Tour"/>
 *         &lt;element name="GUE_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_GUE_Pivot_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_pc_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Fr" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Bruleur_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Bruleur_Fr" type="{}E_Fonctionnement_Bruleur"/>
 *         &lt;element name="Statut_Autres_Donnees_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux0_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Fr" type="{}E_Emetteur"/>
 *         &lt;element name="Rdt_Comp_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pertes_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_ECS" type="{}E_Temperatures_Aval_ECS" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Thermodynamique_Gaz_Reversible", propOrder = {

})
public class RTDataGenerateurThermodynamiqueGazReversible {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Sys_Thermo", required = true)
    protected String sysThermo;
    @XmlElement(name = "Id_Groupe")
    protected int idGroupe;
    @XmlElement(name = "Id_Source_Amont_Ch", defaultValue = "0")
    protected int idSourceAmontCh;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    protected String statutDonneeCh;
    @XmlElement(name = "Theta_Aval_Air_Eau_classique", required = true)
    protected String thetaAvalAirEauClassique;
    @XmlElement(name = "Theta_Amont_Air_Eau", required = true)
    protected String thetaAmontAirEau;
    @XmlElement(name = "Theta_Amont_Eau_Eau", required = true)
    protected String thetaAmontEauEau;
    @XmlElement(name = "Theta_Aval_Eau_Eau", required = true)
    protected String thetaAvalEauEau;
    @XmlElement(name = "GUE_Ch")
    protected String gueCh;
    @XmlElement(name = "Pabs_Ch")
    protected String pabsCh;
    @XmlElement(name = "COR_Ch")
    protected String corCh;
    @XmlElement(name = "Paux_pc_Ch")
    protected String pauxPcCh;
    @XmlElement(name = "Statut_GUE_Pivot_Ch", required = true)
    protected String statutGUEPivotCh;
    @XmlElement(name = "Val_GUE_Ch")
    protected double valGUECh;
    @XmlElement(name = "Statut_Paux_Pivot_Ch", required = true)
    protected String statutPauxPivotCh;
    @XmlElement(name = "Val_Paux_pc_Ch")
    protected double valPauxPcCh;
    @XmlElement(name = "Val_Pabs_Ch")
    protected double valPabsCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av")
    protected double thetaMaxAv;
    @XmlElement(name = "Theta_Min_Am")
    protected double thetaMinAm;
    @XmlElement(name = "Statut_Bruleur_Ch", required = true)
    protected String statutBruleurCh;
    @XmlElement(name = "Fonctionnement_Bruleur_Ch", required = true)
    protected String fonctionnementBruleurCh;
    @XmlElement(name = "LRcontmin_Ch")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Statut_Autres_Donnees_Ch", required = true)
    protected String statutAutresDonneesCh;
    @XmlElement(name = "Paux0_Ch")
    protected double paux0Ch;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    protected String typoEmetteurCh;
    @XmlElement(name = "Statut_Echangeur_Ch", required = true)
    protected String statutEchangeurCh;
    @XmlElement(name = "Echangeur_Eau_Ch_Fumee", required = true)
    protected String echangeurEauChFumee;
    @XmlElement(name = "Rdt_Comp_Ch")
    protected double rdtCompCh;
    @XmlElement(name = "Pertes_40deg_Ch")
    protected double pertes40DegCh;
    @XmlElement(name = "Id_Source_Amont_Fr", defaultValue = "0")
    protected int idSourceAmontFr;
    @XmlElement(name = "Statut_Donnee_Fr", required = true)
    protected String statutDonneeFr;
    @XmlElement(name = "Cycle_Fr", required = true)
    protected String cycleFr;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    protected String thetaAmontAirEauFr;
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Eau_Tour_Fr", required = true)
    protected String thetaAmontEauTourFr;
    @XmlElement(name = "GUE_Fr")
    protected String gueFr;
    @XmlElement(name = "Pabs_Fr")
    protected String pabsFr;
    @XmlElement(name = "COR_Fr")
    protected String corFr;
    @XmlElement(name = "Paux_pc_Fr")
    protected String pauxPcFr;
    @XmlElement(name = "Statut_GUE_Pivot_Fr", required = true)
    protected String statutGUEPivotFr;
    @XmlElement(name = "Val_GUE_Fr")
    protected double valGUEFr;
    @XmlElement(name = "Statut_Paux_Pivot_Fr", required = true)
    protected String statutPauxPivotFr;
    @XmlElement(name = "Val_Paux_pc_Fr")
    protected double valPauxPcFr;
    @XmlElement(name = "Val_Pabs_Fr")
    protected double valPabsFr;
    @XmlElement(name = "Lim_Theta_Fr", required = true)
    protected String limThetaFr;
    @XmlElement(name = "Theta_Max_Am")
    protected double thetaMaxAm;
    @XmlElement(name = "Theta_Min_Av")
    protected double thetaMinAv;
    @XmlElement(name = "Statut_Bruleur_Fr", required = true)
    protected String statutBruleurFr;
    @XmlElement(name = "Fonctionnement_Bruleur_Fr", required = true)
    protected String fonctionnementBruleurFr;
    @XmlElement(name = "Statut_Autres_Donnees_Fr", required = true)
    protected String statutAutresDonneesFr;
    @XmlElement(name = "LRcontmin_Fr")
    protected double lRcontminFr;
    @XmlElement(name = "CCP_LRcontmin_Fr")
    protected double ccplRcontminFr;
    @XmlElement(name = "Paux0_Fr")
    protected double paux0Fr;
    @XmlElement(name = "Typo_Emetteur_Fr", required = true)
    protected String typoEmetteurFr;
    @XmlElement(name = "Rdt_Comp_Fr")
    protected double rdtCompFr;
    @XmlElement(name = "Pertes_Fr")
    protected double pertesFr;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Theta_Aval_ECS")
    protected String thetaAvalECS;

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
     * Gets the value of the sysThermo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermo() {
        return sysThermo;
    }

    /**
     * Sets the value of the sysThermo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermo(String value) {
        this.sysThermo = value;
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
     * Gets the value of the idSourceAmontCh property.
     * 
     */
    public int getIdSourceAmontCh() {
        return idSourceAmontCh;
    }

    /**
     * Sets the value of the idSourceAmontCh property.
     * 
     */
    public void setIdSourceAmontCh(int value) {
        this.idSourceAmontCh = value;
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
     * Gets the value of the gueCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUECh() {
        return gueCh;
    }

    /**
     * Sets the value of the gueCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUECh(String value) {
        this.gueCh = value;
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
     * Gets the value of the statutGUEPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutGUEPivotCh() {
        return statutGUEPivotCh;
    }

    /**
     * Sets the value of the statutGUEPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutGUEPivotCh(String value) {
        this.statutGUEPivotCh = value;
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
     * Gets the value of the statutBruleurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutBruleurCh() {
        return statutBruleurCh;
    }

    /**
     * Sets the value of the statutBruleurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutBruleurCh(String value) {
        this.statutBruleurCh = value;
    }

    /**
     * Gets the value of the fonctionnementBruleurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementBruleurCh() {
        return fonctionnementBruleurCh;
    }

    /**
     * Sets the value of the fonctionnementBruleurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementBruleurCh(String value) {
        this.fonctionnementBruleurCh = value;
    }

    /**
     * Gets the value of the lRcontminCh property.
     * 
     */
    public double getLRcontminCh() {
        return lRcontminCh;
    }

    /**
     * Sets the value of the lRcontminCh property.
     * 
     */
    public void setLRcontminCh(double value) {
        this.lRcontminCh = value;
    }

    /**
     * Gets the value of the ccplRcontminCh property.
     * 
     */
    public double getCCPLRcontminCh() {
        return ccplRcontminCh;
    }

    /**
     * Sets the value of the ccplRcontminCh property.
     * 
     */
    public void setCCPLRcontminCh(double value) {
        this.ccplRcontminCh = value;
    }

    /**
     * Gets the value of the statutAutresDonneesCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutAutresDonneesCh() {
        return statutAutresDonneesCh;
    }

    /**
     * Sets the value of the statutAutresDonneesCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutAutresDonneesCh(String value) {
        this.statutAutresDonneesCh = value;
    }

    /**
     * Gets the value of the paux0Ch property.
     * 
     */
    public double getPaux0Ch() {
        return paux0Ch;
    }

    /**
     * Sets the value of the paux0Ch property.
     * 
     */
    public void setPaux0Ch(double value) {
        this.paux0Ch = value;
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

    /**
     * Gets the value of the statutEchangeurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutEchangeurCh() {
        return statutEchangeurCh;
    }

    /**
     * Sets the value of the statutEchangeurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutEchangeurCh(String value) {
        this.statutEchangeurCh = value;
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
     * Gets the value of the rdtCompCh property.
     * 
     */
    public double getRdtCompCh() {
        return rdtCompCh;
    }

    /**
     * Sets the value of the rdtCompCh property.
     * 
     */
    public void setRdtCompCh(double value) {
        this.rdtCompCh = value;
    }

    /**
     * Gets the value of the pertes40DegCh property.
     * 
     */
    public double getPertes40DegCh() {
        return pertes40DegCh;
    }

    /**
     * Sets the value of the pertes40DegCh property.
     * 
     */
    public void setPertes40DegCh(double value) {
        this.pertes40DegCh = value;
    }

    /**
     * Gets the value of the idSourceAmontFr property.
     * 
     */
    public int getIdSourceAmontFr() {
        return idSourceAmontFr;
    }

    /**
     * Sets the value of the idSourceAmontFr property.
     * 
     */
    public void setIdSourceAmontFr(int value) {
        this.idSourceAmontFr = value;
    }

    /**
     * Gets the value of the statutDonneeFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeFr() {
        return statutDonneeFr;
    }

    /**
     * Sets the value of the statutDonneeFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeFr(String value) {
        this.statutDonneeFr = value;
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
     * Gets the value of the thetaAmontEauTourFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauTourFr() {
        return thetaAmontEauTourFr;
    }

    /**
     * Sets the value of the thetaAmontEauTourFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauTourFr(String value) {
        this.thetaAmontEauTourFr = value;
    }

    /**
     * Gets the value of the gueFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUEFr() {
        return gueFr;
    }

    /**
     * Sets the value of the gueFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUEFr(String value) {
        this.gueFr = value;
    }

    /**
     * Gets the value of the pabsFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsFr() {
        return pabsFr;
    }

    /**
     * Sets the value of the pabsFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsFr(String value) {
        this.pabsFr = value;
    }

    /**
     * Gets the value of the corFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCORFr() {
        return corFr;
    }

    /**
     * Sets the value of the corFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCORFr(String value) {
        this.corFr = value;
    }

    /**
     * Gets the value of the pauxPcFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxPcFr() {
        return pauxPcFr;
    }

    /**
     * Sets the value of the pauxPcFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxPcFr(String value) {
        this.pauxPcFr = value;
    }

    /**
     * Gets the value of the statutGUEPivotFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutGUEPivotFr() {
        return statutGUEPivotFr;
    }

    /**
     * Sets the value of the statutGUEPivotFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutGUEPivotFr(String value) {
        this.statutGUEPivotFr = value;
    }

    /**
     * Gets the value of the valGUEFr property.
     * 
     */
    public double getValGUEFr() {
        return valGUEFr;
    }

    /**
     * Sets the value of the valGUEFr property.
     * 
     */
    public void setValGUEFr(double value) {
        this.valGUEFr = value;
    }

    /**
     * Gets the value of the statutPauxPivotFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxPivotFr() {
        return statutPauxPivotFr;
    }

    /**
     * Sets the value of the statutPauxPivotFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxPivotFr(String value) {
        this.statutPauxPivotFr = value;
    }

    /**
     * Gets the value of the valPauxPcFr property.
     * 
     */
    public double getValPauxPcFr() {
        return valPauxPcFr;
    }

    /**
     * Sets the value of the valPauxPcFr property.
     * 
     */
    public void setValPauxPcFr(double value) {
        this.valPauxPcFr = value;
    }

    /**
     * Gets the value of the valPabsFr property.
     * 
     */
    public double getValPabsFr() {
        return valPabsFr;
    }

    /**
     * Sets the value of the valPabsFr property.
     * 
     */
    public void setValPabsFr(double value) {
        this.valPabsFr = value;
    }

    /**
     * Gets the value of the limThetaFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimThetaFr() {
        return limThetaFr;
    }

    /**
     * Sets the value of the limThetaFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimThetaFr(String value) {
        this.limThetaFr = value;
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
     * Gets the value of the statutBruleurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutBruleurFr() {
        return statutBruleurFr;
    }

    /**
     * Sets the value of the statutBruleurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutBruleurFr(String value) {
        this.statutBruleurFr = value;
    }

    /**
     * Gets the value of the fonctionnementBruleurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementBruleurFr() {
        return fonctionnementBruleurFr;
    }

    /**
     * Sets the value of the fonctionnementBruleurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementBruleurFr(String value) {
        this.fonctionnementBruleurFr = value;
    }

    /**
     * Gets the value of the statutAutresDonneesFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutAutresDonneesFr() {
        return statutAutresDonneesFr;
    }

    /**
     * Sets the value of the statutAutresDonneesFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutAutresDonneesFr(String value) {
        this.statutAutresDonneesFr = value;
    }

    /**
     * Gets the value of the lRcontminFr property.
     * 
     */
    public double getLRcontminFr() {
        return lRcontminFr;
    }

    /**
     * Sets the value of the lRcontminFr property.
     * 
     */
    public void setLRcontminFr(double value) {
        this.lRcontminFr = value;
    }

    /**
     * Gets the value of the ccplRcontminFr property.
     * 
     */
    public double getCCPLRcontminFr() {
        return ccplRcontminFr;
    }

    /**
     * Sets the value of the ccplRcontminFr property.
     * 
     */
    public void setCCPLRcontminFr(double value) {
        this.ccplRcontminFr = value;
    }

    /**
     * Gets the value of the paux0Fr property.
     * 
     */
    public double getPaux0Fr() {
        return paux0Fr;
    }

    /**
     * Sets the value of the paux0Fr property.
     * 
     */
    public void setPaux0Fr(double value) {
        this.paux0Fr = value;
    }

    /**
     * Gets the value of the typoEmetteurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoEmetteurFr() {
        return typoEmetteurFr;
    }

    /**
     * Sets the value of the typoEmetteurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoEmetteurFr(String value) {
        this.typoEmetteurFr = value;
    }

    /**
     * Gets the value of the rdtCompFr property.
     * 
     */
    public double getRdtCompFr() {
        return rdtCompFr;
    }

    /**
     * Sets the value of the rdtCompFr property.
     * 
     */
    public void setRdtCompFr(double value) {
        this.rdtCompFr = value;
    }

    /**
     * Gets the value of the pertesFr property.
     * 
     */
    public double getPertesFr() {
        return pertesFr;
    }

    /**
     * Sets the value of the pertesFr property.
     * 
     */
    public void setPertesFr(double value) {
        this.pertesFr = value;
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
     * Gets the value of the thetaAvalECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalECS() {
        return thetaAvalECS;
    }

    /**
     * Sets the value of the thetaAvalECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalECS(String value) {
        this.thetaAvalECS = value;
    }

}
