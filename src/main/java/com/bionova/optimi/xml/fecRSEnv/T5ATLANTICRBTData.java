
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5_ATLANTIC_Distribution_RBT
 * 
 * <p>Java class for T5_ATLANTIC_RBT_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_ATLANTIC_RBT_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lvc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lhvc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Uprim_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_gest_circ_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_circ_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_PCAD" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pos_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Due" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_et" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Delta_Theta_Base" type="{}E_Valeur_Declaree_Def"/>
 *         &lt;element name="delta_theta_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Delta_Theta_Appoint" type="{}E_Valeur_Declaree_Def"/>
 *         &lt;element name="delta_theta_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_faux" type="{}E_Valeur_Saisie_Def"/>
 *         &lt;element name="f_aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Justifiee_Defaut" type="{}E_Valeur_Certifiee_Justifiee_Def_Ballon"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="z_base" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="z_reg_base" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="z_ap" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="z_reg_ap" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hrel_ech_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hrel_ech_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Name_PAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index_PAC" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo_ECS" type="{}E_Systeme_Thermo_Ecs"/>
 *         &lt;element name="Pvent_gaine" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonc_compr" type="{}E_Fonctionnement_Comp"/>
 *         &lt;element name="Statut_val_pivot" type="{}E_Valeur_Declaree_Def"/>
 *         &lt;element name="ValCOP_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ValPabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_donnees" type="{}E_Ex_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Ecs_Atlantic"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Eau_Ecs_Atlantic"/>
 *         &lt;element name="Theta_Aval_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Ambiant_Eau_Ecs_Atlantic"/>
 *         &lt;element name="Theta_Amont_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Ambiant_Eau_Ecs_Atlantic"/>
 *         &lt;element name="ValCOP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValPabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValCOR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lim_Theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_min_am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_max_av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifiee_Justifiee_Def"/>
 *         &lt;element name="Ccp_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="LR_contmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifiee_Justifiee_Def"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pngen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_ATLANTIC_RBT_Data", propOrder = {

})
public class T5ATLANTICRBTData {

    @XmlElement(name = "Index")
    protected Integer index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Lvc_prim_bcl_e")
    protected Double lvcPrimBclE;
    @XmlElement(name = "Lhvc_prim_bcl_e")
    protected Double lhvcPrimBclE;
    @XmlElement(name = "Uprim_e")
    protected Double uprimE;
    protected Double b;
    @XmlElement(name = "Type_gest_circ_e")
    protected Double typeGestCircE;
    @XmlElement(name = "P_circ_e")
    protected Double pCircE;
    @XmlElement(name = "Id_Gen")
    protected Integer idGen;
    @XmlElement(name = "Id_PCAD")
    protected Integer idPCAD;
    @XmlElement(name = "Pos_Gen")
    protected Integer posGen;
    @XmlElement(name = "Due")
    protected Double due;
    @XmlElement(name = "b_et")
    protected Double bEt;
    @XmlElement(name = "Statut_Delta_Theta_Base")
    protected String statutDeltaThetaBase;
    @XmlElement(name = "delta_theta_base")
    protected Double deltaThetaBase;
    @XmlElement(name = "Statut_Delta_Theta_Appoint")
    protected String statutDeltaThetaAppoint;
    @XmlElement(name = "delta_theta_ap")
    protected Double deltaThetaAp;
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
    @XmlElement(name = "z_base")
    protected Integer zBase;
    @XmlElement(name = "z_reg_base")
    protected Integer zRegBase;
    @XmlElement(name = "z_ap")
    protected Integer zAp;
    @XmlElement(name = "z_reg_ap")
    protected Integer zRegAp;
    @XmlElement(name = "hrel_ech_base")
    protected Double hrelEchBase;
    @XmlElement(name = "hrel_ech_ap")
    protected Double hrelEchAp;
    @XmlElement(name = "Name_PAC")
    protected String namePAC;
    @XmlElement(name = "Index_PAC")
    protected Integer indexPAC;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Sys_Thermo_ECS")
    protected String sysThermoECS;
    @XmlElement(name = "Pvent_gaine")
    protected Double pventGaine;
    @XmlElement(name = "Fonc_compr")
    protected String foncCompr;
    @XmlElement(name = "Statut_val_pivot")
    protected String statutValPivot;
    @XmlElement(name = "ValCOP_pivot")
    protected Double valCOPPivot;
    @XmlElement(name = "ValPabs_pivot")
    protected Double valPabsPivot;
    @XmlElement(name = "Statut_donnees")
    protected String statutDonnees;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ecs")
    protected String thetaAvalAirEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ecs")
    protected String thetaAmontAirEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Ambiant_Eau_Ecs")
    protected String thetaAvalAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Ambiant_Eau_Ecs")
    protected String thetaAmontAirAmbiantEauEcs;
    @XmlElement(name = "ValCOP")
    protected String valCOP;
    @XmlElement(name = "ValPabs")
    protected String valPabs;
    @XmlElement(name = "ValCOR")
    protected String valCOR;
    @XmlElement(name = "Lim_Theta")
    protected Double limTheta;
    @XmlElement(name = "Theta_min_am")
    protected Double thetaMinAm;
    @XmlElement(name = "Theta_max_av")
    protected Double thetaMaxAv;
    @XmlElement(name = "Statut_Fonctionnement_Continu")
    protected String statutFonctionnementContinu;
    @XmlElement(name = "Ccp_LRcontmin")
    protected Double ccpLRcontmin;
    @XmlElement(name = "LR_contmin")
    protected Double lrContmin;
    @XmlElement(name = "Statut_Taux")
    protected String statutTaux;
    @XmlElement(name = "Taux")
    protected Double taux;
    @XmlElement(name = "Pngen")
    protected Double pngen;

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
     * Gets the value of the lvcPrimBclE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLvcPrimBclE() {
        return lvcPrimBclE;
    }

    /**
     * Sets the value of the lvcPrimBclE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLvcPrimBclE(Double value) {
        this.lvcPrimBclE = value;
    }

    /**
     * Gets the value of the lhvcPrimBclE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLhvcPrimBclE() {
        return lhvcPrimBclE;
    }

    /**
     * Sets the value of the lhvcPrimBclE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLhvcPrimBclE(Double value) {
        this.lhvcPrimBclE = value;
    }

    /**
     * Gets the value of the uprimE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUprimE() {
        return uprimE;
    }

    /**
     * Sets the value of the uprimE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUprimE(Double value) {
        this.uprimE = value;
    }

    /**
     * Gets the value of the b property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setB(Double value) {
        this.b = value;
    }

    /**
     * Gets the value of the typeGestCircE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTypeGestCircE() {
        return typeGestCircE;
    }

    /**
     * Sets the value of the typeGestCircE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTypeGestCircE(Double value) {
        this.typeGestCircE = value;
    }

    /**
     * Gets the value of the pCircE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPCircE() {
        return pCircE;
    }

    /**
     * Sets the value of the pCircE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPCircE(Double value) {
        this.pCircE = value;
    }

    /**
     * Gets the value of the idGen property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdGen() {
        return idGen;
    }

    /**
     * Sets the value of the idGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdGen(Integer value) {
        this.idGen = value;
    }

    /**
     * Gets the value of the idPCAD property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPCAD() {
        return idPCAD;
    }

    /**
     * Sets the value of the idPCAD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPCAD(Integer value) {
        this.idPCAD = value;
    }

    /**
     * Gets the value of the posGen property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPosGen() {
        return posGen;
    }

    /**
     * Sets the value of the posGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPosGen(Integer value) {
        this.posGen = value;
    }

    /**
     * Gets the value of the due property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDue() {
        return due;
    }

    /**
     * Sets the value of the due property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDue(Double value) {
        this.due = value;
    }

    /**
     * Gets the value of the bEt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBEt() {
        return bEt;
    }

    /**
     * Sets the value of the bEt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBEt(Double value) {
        this.bEt = value;
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
     * Gets the value of the deltaThetaAp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeltaThetaAp() {
        return deltaThetaAp;
    }

    /**
     * Sets the value of the deltaThetaAp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeltaThetaAp(Double value) {
        this.deltaThetaAp = value;
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
     * Gets the value of the zAp property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZAp() {
        return zAp;
    }

    /**
     * Sets the value of the zAp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZAp(Integer value) {
        this.zAp = value;
    }

    /**
     * Gets the value of the zRegAp property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZRegAp() {
        return zRegAp;
    }

    /**
     * Sets the value of the zRegAp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZRegAp(Integer value) {
        this.zRegAp = value;
    }

    /**
     * Gets the value of the hrelEchBase property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHrelEchBase() {
        return hrelEchBase;
    }

    /**
     * Sets the value of the hrelEchBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHrelEchBase(Double value) {
        this.hrelEchBase = value;
    }

    /**
     * Gets the value of the hrelEchAp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHrelEchAp() {
        return hrelEchAp;
    }

    /**
     * Sets the value of the hrelEchAp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHrelEchAp(Double value) {
        this.hrelEchAp = value;
    }

    /**
     * Gets the value of the namePAC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamePAC() {
        return namePAC;
    }

    /**
     * Sets the value of the namePAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamePAC(String value) {
        this.namePAC = value;
    }

    /**
     * Gets the value of the indexPAC property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndexPAC() {
        return indexPAC;
    }

    /**
     * Sets the value of the indexPAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndexPAC(Integer value) {
        this.indexPAC = value;
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
     * Gets the value of the sysThermoECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoECS() {
        return sysThermoECS;
    }

    /**
     * Sets the value of the sysThermoECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoECS(String value) {
        this.sysThermoECS = value;
    }

    /**
     * Gets the value of the pventGaine property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventGaine() {
        return pventGaine;
    }

    /**
     * Sets the value of the pventGaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventGaine(Double value) {
        this.pventGaine = value;
    }

    /**
     * Gets the value of the foncCompr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoncCompr() {
        return foncCompr;
    }

    /**
     * Sets the value of the foncCompr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoncCompr(String value) {
        this.foncCompr = value;
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
     * Gets the value of the valCOPPivot property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValCOPPivot() {
        return valCOPPivot;
    }

    /**
     * Sets the value of the valCOPPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValCOPPivot(Double value) {
        this.valCOPPivot = value;
    }

    /**
     * Gets the value of the valPabsPivot property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValPabsPivot() {
        return valPabsPivot;
    }

    /**
     * Sets the value of the valPabsPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValPabsPivot(Double value) {
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
     * Gets the value of the valCOP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValCOP() {
        return valCOP;
    }

    /**
     * Sets the value of the valCOP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValCOP(String value) {
        this.valCOP = value;
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
     * Gets the value of the limTheta property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLimTheta() {
        return limTheta;
    }

    /**
     * Sets the value of the limTheta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLimTheta(Double value) {
        this.limTheta = value;
    }

    /**
     * Gets the value of the thetaMinAm property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMinAm() {
        return thetaMinAm;
    }

    /**
     * Sets the value of the thetaMinAm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMinAm(Double value) {
        this.thetaMinAm = value;
    }

    /**
     * Gets the value of the thetaMaxAv property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMaxAv() {
        return thetaMaxAv;
    }

    /**
     * Sets the value of the thetaMaxAv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMaxAv(Double value) {
        this.thetaMaxAv = value;
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
     * Gets the value of the ccpLRcontmin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCcpLRcontmin() {
        return ccpLRcontmin;
    }

    /**
     * Sets the value of the ccpLRcontmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCcpLRcontmin(Double value) {
        this.ccpLRcontmin = value;
    }

    /**
     * Gets the value of the lrContmin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLRContmin() {
        return lrContmin;
    }

    /**
     * Sets the value of the lrContmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLRContmin(Double value) {
        this.lrContmin = value;
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
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTaux() {
        return taux;
    }

    /**
     * Sets the value of the taux property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTaux(Double value) {
        this.taux = value;
    }

    /**
     * Gets the value of the pngen property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPngen() {
        return pngen;
    }

    /**
     * Sets the value of the pngen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPngen(Double value) {
        this.pngen = value;
    }

}
