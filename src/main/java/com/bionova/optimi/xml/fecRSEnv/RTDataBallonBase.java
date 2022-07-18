
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Ballon_Base complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Ballon_Base">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Fou_Sto" type="{}E_Id_FouGen_Mode1"/>
 *         &lt;element name="Type_prod_stockage" type="{}E_Type_Ballon"/>
 *         &lt;element name="nb_sto_b" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nb_assembl" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_faux" type="{}RT2012.Entree.Ven.E_Valeur_Saisie_Defaut"/>
 *         &lt;element name="f_aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Justifiee_Defaut" type="{}E_Valeur_Certifiee_Justifiee_Defaut_Ballon"/>
 *         &lt;element name="Nature_Ballon" type="{}E_Nature_Ballon"/>
 *         &lt;element name="UA_S" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_sto_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Justifiee_Defaut_Appoint" type="{}E_Valeur_Certifiee_Justifiee_Defaut_Ballon"/>
 *         &lt;element name="Nature_Ballon_Appoint" type="{}E_Nature_Ballon"/>
 *         &lt;element name="UA_S_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="type_gest_th_base" type="{}E_Gestion_Thermostat_Ballon"/>
 *         &lt;element name="Delta_Theta_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hech_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="z_reg_base" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type_gest_th_appoint" type="{}E_Gestion_Thermostat_Ballon"/>
 *         &lt;element name="Delta_Theta_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hech_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="z_appoint" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="z_reg_appoint" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="delta_reg_bcl_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Delta_Theta_Base" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Delta_Theta_Appoint" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element ref="{}Gestion_Regulation_Thermostat_Ballon_Collection" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Ballon_Base", propOrder = {

})
@XmlSeeAlso({
    RTDataBallonCentralise.class
})
public class RTDataBallonBase {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_Fou_Sto", required = true)
    protected String idFouSto;
    @XmlElement(name = "Type_prod_stockage", required = true)
    protected String typeProdStockage;
    @XmlElement(name = "nb_sto_b")
    protected int nbStoB;
    @XmlElement(name = "nb_assembl")
    protected int nbAssembl;
    @XmlElement(name = "V_tot")
    protected double vTot;
    @XmlElement(name = "Statut_faux", required = true)
    protected String statutFaux;
    @XmlElement(name = "f_aux")
    protected double fAux;
    @XmlElement(name = "Valeur_Certifiee_Justifiee_Defaut", required = true)
    protected String valeurCertifieeJustifieeDefaut;
    @XmlElement(name = "Nature_Ballon", required = true)
    protected String natureBallon;
    @XmlElement(name = "UA_S")
    protected double uas;
    @XmlElement(name = "Theta_Max")
    protected double thetaMax;
    @XmlElement(name = "b_sto_e")
    protected double bStoE;
    @XmlElement(name = "V_tot_appoint")
    protected double vTotAppoint;
    @XmlElement(name = "Valeur_Certifiee_Justifiee_Defaut_Appoint", required = true)
    protected String valeurCertifieeJustifieeDefautAppoint;
    @XmlElement(name = "Nature_Ballon_Appoint", required = true)
    protected String natureBallonAppoint;
    @XmlElement(name = "UA_S_appoint")
    protected double uasAppoint;
    @XmlElement(name = "Theta_Max_appoint")
    protected double thetaMaxAppoint;
    @XmlElement(name = "type_gest_th_base", required = true)
    protected String typeGestThBase;
    @XmlElement(name = "Delta_Theta_base")
    protected double deltaThetaBase;
    @XmlElement(name = "hech_base")
    protected double hechBase;
    @XmlElement(name = "z_reg_base")
    protected int zRegBase;
    @XmlElement(name = "type_gest_th_appoint", required = true)
    protected String typeGestThAppoint;
    @XmlElement(name = "Delta_Theta_appoint")
    protected double deltaThetaAppoint;
    @XmlElement(name = "hech_appoint")
    protected double hechAppoint;
    @XmlElement(name = "z_appoint")
    protected int zAppoint;
    @XmlElement(name = "z_reg_appoint")
    protected int zRegAppoint;
    @XmlElement(name = "delta_reg_bcl_ch")
    protected double deltaRegBclCh;
    @XmlElement(name = "Statut_Delta_Theta_Base", required = true)
    protected String statutDeltaThetaBase;
    @XmlElement(name = "Statut_Delta_Theta_Appoint", required = true)
    protected String statutDeltaThetaAppoint;
    @XmlElement(name = "Gestion_Regulation_Thermostat_Ballon_Collection")
    protected GestionRegulationThermostatBallonCollection gestionRegulationThermostatBallonCollection;

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
     * Gets the value of the idFouSto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouSto() {
        return idFouSto;
    }

    /**
     * Sets the value of the idFouSto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouSto(String value) {
        this.idFouSto = value;
    }

    /**
     * Gets the value of the typeProdStockage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeProdStockage() {
        return typeProdStockage;
    }

    /**
     * Sets the value of the typeProdStockage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeProdStockage(String value) {
        this.typeProdStockage = value;
    }

    /**
     * Gets the value of the nbStoB property.
     * 
     */
    public int getNbStoB() {
        return nbStoB;
    }

    /**
     * Sets the value of the nbStoB property.
     * 
     */
    public void setNbStoB(int value) {
        this.nbStoB = value;
    }

    /**
     * Gets the value of the nbAssembl property.
     * 
     */
    public int getNbAssembl() {
        return nbAssembl;
    }

    /**
     * Sets the value of the nbAssembl property.
     * 
     */
    public void setNbAssembl(int value) {
        this.nbAssembl = value;
    }

    /**
     * Gets the value of the vTot property.
     * 
     */
    public double getVTot() {
        return vTot;
    }

    /**
     * Sets the value of the vTot property.
     * 
     */
    public void setVTot(double value) {
        this.vTot = value;
    }

    /**
     * Gets the value of the statutFaux property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFaux() {
        return statutFaux;
    }

    /**
     * Sets the value of the statutFaux property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFaux(String value) {
        this.statutFaux = value;
    }

    /**
     * Gets the value of the fAux property.
     * 
     */
    public double getFAux() {
        return fAux;
    }

    /**
     * Sets the value of the fAux property.
     * 
     */
    public void setFAux(double value) {
        this.fAux = value;
    }

    /**
     * Gets the value of the valeurCertifieeJustifieeDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurCertifieeJustifieeDefaut() {
        return valeurCertifieeJustifieeDefaut;
    }

    /**
     * Sets the value of the valeurCertifieeJustifieeDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurCertifieeJustifieeDefaut(String value) {
        this.valeurCertifieeJustifieeDefaut = value;
    }

    /**
     * Gets the value of the natureBallon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNatureBallon() {
        return natureBallon;
    }

    /**
     * Sets the value of the natureBallon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNatureBallon(String value) {
        this.natureBallon = value;
    }

    /**
     * Gets the value of the uas property.
     * 
     */
    public double getUAS() {
        return uas;
    }

    /**
     * Sets the value of the uas property.
     * 
     */
    public void setUAS(double value) {
        this.uas = value;
    }

    /**
     * Gets the value of the thetaMax property.
     * 
     */
    public double getThetaMax() {
        return thetaMax;
    }

    /**
     * Sets the value of the thetaMax property.
     * 
     */
    public void setThetaMax(double value) {
        this.thetaMax = value;
    }

    /**
     * Gets the value of the bStoE property.
     * 
     */
    public double getBStoE() {
        return bStoE;
    }

    /**
     * Sets the value of the bStoE property.
     * 
     */
    public void setBStoE(double value) {
        this.bStoE = value;
    }

    /**
     * Gets the value of the vTotAppoint property.
     * 
     */
    public double getVTotAppoint() {
        return vTotAppoint;
    }

    /**
     * Sets the value of the vTotAppoint property.
     * 
     */
    public void setVTotAppoint(double value) {
        this.vTotAppoint = value;
    }

    /**
     * Gets the value of the valeurCertifieeJustifieeDefautAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurCertifieeJustifieeDefautAppoint() {
        return valeurCertifieeJustifieeDefautAppoint;
    }

    /**
     * Sets the value of the valeurCertifieeJustifieeDefautAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurCertifieeJustifieeDefautAppoint(String value) {
        this.valeurCertifieeJustifieeDefautAppoint = value;
    }

    /**
     * Gets the value of the natureBallonAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNatureBallonAppoint() {
        return natureBallonAppoint;
    }

    /**
     * Sets the value of the natureBallonAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNatureBallonAppoint(String value) {
        this.natureBallonAppoint = value;
    }

    /**
     * Gets the value of the uasAppoint property.
     * 
     */
    public double getUASAppoint() {
        return uasAppoint;
    }

    /**
     * Sets the value of the uasAppoint property.
     * 
     */
    public void setUASAppoint(double value) {
        this.uasAppoint = value;
    }

    /**
     * Gets the value of the thetaMaxAppoint property.
     * 
     */
    public double getThetaMaxAppoint() {
        return thetaMaxAppoint;
    }

    /**
     * Sets the value of the thetaMaxAppoint property.
     * 
     */
    public void setThetaMaxAppoint(double value) {
        this.thetaMaxAppoint = value;
    }

    /**
     * Gets the value of the typeGestThBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestThBase() {
        return typeGestThBase;
    }

    /**
     * Sets the value of the typeGestThBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestThBase(String value) {
        this.typeGestThBase = value;
    }

    /**
     * Gets the value of the deltaThetaBase property.
     * 
     */
    public double getDeltaThetaBase() {
        return deltaThetaBase;
    }

    /**
     * Sets the value of the deltaThetaBase property.
     * 
     */
    public void setDeltaThetaBase(double value) {
        this.deltaThetaBase = value;
    }

    /**
     * Gets the value of the hechBase property.
     * 
     */
    public double getHechBase() {
        return hechBase;
    }

    /**
     * Sets the value of the hechBase property.
     * 
     */
    public void setHechBase(double value) {
        this.hechBase = value;
    }

    /**
     * Gets the value of the zRegBase property.
     * 
     */
    public int getZRegBase() {
        return zRegBase;
    }

    /**
     * Sets the value of the zRegBase property.
     * 
     */
    public void setZRegBase(int value) {
        this.zRegBase = value;
    }

    /**
     * Gets the value of the typeGestThAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestThAppoint() {
        return typeGestThAppoint;
    }

    /**
     * Sets the value of the typeGestThAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestThAppoint(String value) {
        this.typeGestThAppoint = value;
    }

    /**
     * Gets the value of the deltaThetaAppoint property.
     * 
     */
    public double getDeltaThetaAppoint() {
        return deltaThetaAppoint;
    }

    /**
     * Sets the value of the deltaThetaAppoint property.
     * 
     */
    public void setDeltaThetaAppoint(double value) {
        this.deltaThetaAppoint = value;
    }

    /**
     * Gets the value of the hechAppoint property.
     * 
     */
    public double getHechAppoint() {
        return hechAppoint;
    }

    /**
     * Sets the value of the hechAppoint property.
     * 
     */
    public void setHechAppoint(double value) {
        this.hechAppoint = value;
    }

    /**
     * Gets the value of the zAppoint property.
     * 
     */
    public int getZAppoint() {
        return zAppoint;
    }

    /**
     * Sets the value of the zAppoint property.
     * 
     */
    public void setZAppoint(int value) {
        this.zAppoint = value;
    }

    /**
     * Gets the value of the zRegAppoint property.
     * 
     */
    public int getZRegAppoint() {
        return zRegAppoint;
    }

    /**
     * Sets the value of the zRegAppoint property.
     * 
     */
    public void setZRegAppoint(int value) {
        this.zRegAppoint = value;
    }

    /**
     * Gets the value of the deltaRegBclCh property.
     * 
     */
    public double getDeltaRegBclCh() {
        return deltaRegBclCh;
    }

    /**
     * Sets the value of the deltaRegBclCh property.
     * 
     */
    public void setDeltaRegBclCh(double value) {
        this.deltaRegBclCh = value;
    }

    /**
     * Gets the value of the statutDeltaThetaBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDeltaThetaBase() {
        return statutDeltaThetaBase;
    }

    /**
     * Sets the value of the statutDeltaThetaBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDeltaThetaBase(String value) {
        this.statutDeltaThetaBase = value;
    }

    /**
     * Gets the value of the statutDeltaThetaAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDeltaThetaAppoint() {
        return statutDeltaThetaAppoint;
    }

    /**
     * Sets the value of the statutDeltaThetaAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDeltaThetaAppoint(String value) {
        this.statutDeltaThetaAppoint = value;
    }

    /**
     * Gets the value of the gestionRegulationThermostatBallonCollection property.
     * 
     * @return
     *     possible object is
     *     {@link GestionRegulationThermostatBallonCollection }
     *     
     */
    public GestionRegulationThermostatBallonCollection getGestionRegulationThermostatBallonCollection() {
        return gestionRegulationThermostatBallonCollection;
    }

    /**
     * Sets the value of the gestionRegulationThermostatBallonCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link GestionRegulationThermostatBallonCollection }
     *     
     */
    public void setGestionRegulationThermostatBallonCollection(GestionRegulationThermostatBallonCollection value) {
        this.gestionRegulationThermostatBallonCollection = value;
    }

}
