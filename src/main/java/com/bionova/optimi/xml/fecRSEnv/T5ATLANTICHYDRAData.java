
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5_ATLANTIC_HYDRA
 * 
 * <p>Java class for T5_ATLANTIC_HYDRA_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_ATLANTIC_HYDRA_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Source_Ballon_Base_Collection" type="{}ArrayOfRTSourceBallonBase" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" type="{}ArrayOfChoice1" minOccurs="0"/>
 *         &lt;element name="Type_prod_stockage" type="{}E_type_prod"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_faux" type="{}E_Valeur_Saisie_Def"/>
 *         &lt;element name="f_aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Justifiee_Defaut" type="{}E_Valeur_Certifiee_Justifiee_Def_Ballon"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_sto_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="type_gest_th_base" type="{}E_Gestion_ThermostatBallon"/>
 *         &lt;element name="Statut_Delta_Theta_Base" type="{}E_Valeur_Declaree_Def"/>
 *         &lt;element name="Delta_Theta_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hech_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="z_base" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="z_reg_base" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type_gest_th_appoint" type="{}E_Gestion_ThermostatBallon"/>
 *         &lt;element name="Statut_Delta_Theta_Appoint" type="{}E_Valeur_Declaree_Def"/>
 *         &lt;element name="Delta_Theta_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hech_appoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="z_appoint" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="z_reg_appoint" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="V_tot_appoint" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Valeur_Certifiee_Justifiee_Defaut_appoint" type="{}E_Valeur_Certifiee_Justifiee_Def_Ballon" minOccurs="0"/>
 *         &lt;element name="UA_s_appoint" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Theta_Max_appoint" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="b_sto_e_appoint" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_ATLANTIC_HYDRA_Data", propOrder = {

})
public class T5ATLANTICHYDRAData {

    @XmlElement(name = "Index")
    protected Integer index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Idpriorite_Ecs")
    protected Integer idprioriteEcs;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected ArrayOfRTSourceBallonBase sourceBallonBaseCollection;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    protected ArrayOfChoice1 sourceBallonAppointCollection;
    @XmlElement(name = "Type_prod_stockage")
    protected String typeProdStockage;
    @XmlElement(name = "V_tot")
    protected Double vTot;
    @XmlElement(name = "Statut_faux")
    protected String statutFaux;
    @XmlElement(name = "f_aux")
    protected Double fAux;
    @XmlElement(name = "Valeur_Certifiee_Justifiee_Defaut")
    protected String valeurCertifieeJustifieeDefaut;
    @XmlElement(name = "UA_s")
    protected Double uas;
    @XmlElement(name = "Theta_Max")
    protected Double thetaMax;
    @XmlElement(name = "b_sto_e")
    protected Double bStoE;
    @XmlElement(name = "type_gest_th_base")
    protected String typeGestThBase;
    @XmlElement(name = "Statut_Delta_Theta_Base")
    protected String statutDeltaThetaBase;
    @XmlElement(name = "Delta_Theta_base")
    protected Double deltaThetaBase;
    @XmlElement(name = "hech_base")
    protected Double hechBase;
    @XmlElement(name = "z_base")
    protected Integer zBase;
    @XmlElement(name = "z_reg_base")
    protected Integer zRegBase;
    @XmlElement(name = "type_gest_th_appoint")
    protected String typeGestThAppoint;
    @XmlElement(name = "Statut_Delta_Theta_Appoint")
    protected String statutDeltaThetaAppoint;
    @XmlElement(name = "Delta_Theta_appoint")
    protected Double deltaThetaAppoint;
    @XmlElement(name = "hech_appoint")
    protected Double hechAppoint;
    @XmlElement(name = "z_appoint")
    protected Integer zAppoint;
    @XmlElement(name = "z_reg_appoint")
    protected Integer zRegAppoint;
    @XmlElement(name = "V_tot_appoint")
    protected Double vTotAppoint;
    @XmlElement(name = "Valeur_Certifiee_Justifiee_Defaut_appoint")
    protected String valeurCertifieeJustifieeDefautAppoint;
    @XmlElement(name = "UA_s_appoint")
    protected Double uasAppoint;
    @XmlElement(name = "Theta_Max_appoint")
    protected Double thetaMaxAppoint;
    @XmlElement(name = "b_sto_e_appoint")
    protected Double bStoEAppoint;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
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
     * Gets the value of the idprioriteEcs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdprioriteEcs(Integer value) {
        this.idprioriteEcs = value;
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
     * Gets the value of the sourceBallonBaseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTSourceBallonBase }
     *     
     */
    public ArrayOfRTSourceBallonBase getSourceBallonBaseCollection() {
        return sourceBallonBaseCollection;
    }

    /**
     * Sets the value of the sourceBallonBaseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTSourceBallonBase }
     *     
     */
    public void setSourceBallonBaseCollection(ArrayOfRTSourceBallonBase value) {
        this.sourceBallonBaseCollection = value;
    }

    /**
     * Gets the value of the sourceBallonAppointCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    public ArrayOfChoice1 getSourceBallonAppointCollection() {
        return sourceBallonAppointCollection;
    }

    /**
     * Sets the value of the sourceBallonAppointCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    public void setSourceBallonAppointCollection(ArrayOfChoice1 value) {
        this.sourceBallonAppointCollection = value;
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
     * Gets the value of the vTot property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVTot() {
        return vTot;
    }

    /**
     * Sets the value of the vTot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVTot(Double value) {
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
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFAux() {
        return fAux;
    }

    /**
     * Sets the value of the fAux property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFAux(Double value) {
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
     * Gets the value of the uas property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUAS() {
        return uas;
    }

    /**
     * Sets the value of the uas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUAS(Double value) {
        this.uas = value;
    }

    /**
     * Gets the value of the thetaMax property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMax() {
        return thetaMax;
    }

    /**
     * Sets the value of the thetaMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMax(Double value) {
        this.thetaMax = value;
    }

    /**
     * Gets the value of the bStoE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBStoE() {
        return bStoE;
    }

    /**
     * Sets the value of the bStoE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBStoE(Double value) {
        this.bStoE = value;
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
     * Gets the value of the deltaThetaBase property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeltaThetaBase() {
        return deltaThetaBase;
    }

    /**
     * Sets the value of the deltaThetaBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeltaThetaBase(Double value) {
        this.deltaThetaBase = value;
    }

    /**
     * Gets the value of the hechBase property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHechBase() {
        return hechBase;
    }

    /**
     * Sets the value of the hechBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHechBase(Double value) {
        this.hechBase = value;
    }

    /**
     * Gets the value of the zBase property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZBase() {
        return zBase;
    }

    /**
     * Sets the value of the zBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZBase(Integer value) {
        this.zBase = value;
    }

    /**
     * Gets the value of the zRegBase property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZRegBase() {
        return zRegBase;
    }

    /**
     * Sets the value of the zRegBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZRegBase(Integer value) {
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
     * Gets the value of the deltaThetaAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeltaThetaAppoint() {
        return deltaThetaAppoint;
    }

    /**
     * Sets the value of the deltaThetaAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeltaThetaAppoint(Double value) {
        this.deltaThetaAppoint = value;
    }

    /**
     * Gets the value of the hechAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHechAppoint() {
        return hechAppoint;
    }

    /**
     * Sets the value of the hechAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHechAppoint(Double value) {
        this.hechAppoint = value;
    }

    /**
     * Gets the value of the zAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZAppoint() {
        return zAppoint;
    }

    /**
     * Sets the value of the zAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZAppoint(Integer value) {
        this.zAppoint = value;
    }

    /**
     * Gets the value of the zRegAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZRegAppoint() {
        return zRegAppoint;
    }

    /**
     * Sets the value of the zRegAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZRegAppoint(Integer value) {
        this.zRegAppoint = value;
    }

    /**
     * Gets the value of the vTotAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVTotAppoint() {
        return vTotAppoint;
    }

    /**
     * Sets the value of the vTotAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVTotAppoint(Double value) {
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
     * Gets the value of the uasAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUASAppoint() {
        return uasAppoint;
    }

    /**
     * Sets the value of the uasAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUASAppoint(Double value) {
        this.uasAppoint = value;
    }

    /**
     * Gets the value of the thetaMaxAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMaxAppoint() {
        return thetaMaxAppoint;
    }

    /**
     * Sets the value of the thetaMaxAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMaxAppoint(Double value) {
        this.thetaMaxAppoint = value;
    }

    /**
     * Gets the value of the bStoEAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBStoEAppoint() {
        return bStoEAppoint;
    }

    /**
     * Sets the value of the bStoEAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBStoEAppoint(Double value) {
        this.bStoEAppoint = value;
    }

}
