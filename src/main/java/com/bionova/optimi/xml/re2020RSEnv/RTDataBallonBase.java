
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
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
 *         &lt;element name="Type_accumulateur_ECS" type="{}RT_Type_Accumulateur_ECS"/>
 *         &lt;element name="Theta_Cons" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Retour_Boucle_Separe" type="{}RT_Oui_Non"/>
 *         &lt;element name="Pech_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qv_prim_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Circulateur_Prep_ECS" type="{}E_Gest_Circ_ECS"/>
 *         &lt;element name="Pw_circ_prim_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pw_circ_prim_RB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gestion_Regulation_Thermostat_Ballon_Collection" type="{}ArrayOfRT_Data_Gestion_Regulation_Thermostat_Ballon" minOccurs="0"/>
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
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataBallonBase {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Id_Fou_Sto", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idFouSto;
    @XmlElement(name = "Type_prod_stockage", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeProdStockage;
    @XmlElement(name = "nb_sto_b")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int nbStoB;
    @XmlElement(name = "nb_assembl")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int nbAssembl;
    @XmlElement(name = "V_tot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double vTot;
    @XmlElement(name = "Statut_faux", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutFaux;
    @XmlElement(name = "f_aux")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double fAux;
    @XmlElement(name = "Valeur_Certifiee_Justifiee_Defaut", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurCertifieeJustifieeDefaut;
    @XmlElement(name = "Nature_Ballon", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String natureBallon;
    @XmlElement(name = "UA_S")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uas;
    @XmlElement(name = "Theta_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMax;
    @XmlElement(name = "b_sto_e")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double bStoE;
    @XmlElement(name = "V_tot_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double vTotAppoint;
    @XmlElement(name = "Valeur_Certifiee_Justifiee_Defaut_Appoint", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurCertifieeJustifieeDefautAppoint;
    @XmlElement(name = "Nature_Ballon_Appoint", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String natureBallonAppoint;
    @XmlElement(name = "UA_S_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double uasAppoint;
    @XmlElement(name = "Theta_Max_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxAppoint;
    @XmlElement(name = "type_gest_th_base", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeGestThBase;
    @XmlElement(name = "Delta_Theta_base")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaBase;
    @XmlElement(name = "hech_base")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double hechBase;
    @XmlElement(name = "z_reg_base")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int zRegBase;
    @XmlElement(name = "type_gest_th_appoint", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeGestThAppoint;
    @XmlElement(name = "Delta_Theta_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaAppoint;
    @XmlElement(name = "hech_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double hechAppoint;
    @XmlElement(name = "z_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int zAppoint;
    @XmlElement(name = "z_reg_appoint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int zRegAppoint;
    @XmlElement(name = "delta_reg_bcl_ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaRegBclCh;
    @XmlElement(name = "Statut_Delta_Theta_Base", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDeltaThetaBase;
    @XmlElement(name = "Statut_Delta_Theta_Appoint", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDeltaThetaAppoint;
    @XmlElement(name = "Type_accumulateur_ECS", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeAccumulateurECS;
    @XmlElement(name = "Theta_Cons")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaCons;
    @XmlElement(name = "Is_Retour_Boucle_Separe", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isRetourBoucleSepare;
    @XmlElement(name = "Pech_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pechECS;
    @XmlElement(name = "qv_prim_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvPrimECS;
    @XmlElement(name = "Type_Circulateur_Prep_ECS", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeCirculateurPrepECS;
    @XmlElement(name = "Pw_circ_prim_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pwCircPrimECS;
    @XmlElement(name = "Pw_circ_prim_RB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pwCircPrimRB;
    @XmlElement(name = "Gestion_Regulation_Thermostat_Ballon_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataGestionRegulationThermostatBallon gestionRegulationThermostatBallonCollection;

    /**
     * Gets the value of the index property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeProdStockage(String value) {
        this.typeProdStockage = value;
    }

    /**
     * Gets the value of the nbStoB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getNbStoB() {
        return nbStoB;
    }

    /**
     * Sets the value of the nbStoB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbStoB(int value) {
        this.nbStoB = value;
    }

    /**
     * Gets the value of the nbAssembl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getNbAssembl() {
        return nbAssembl;
    }

    /**
     * Sets the value of the nbAssembl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbAssembl(int value) {
        this.nbAssembl = value;
    }

    /**
     * Gets the value of the vTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVTot() {
        return vTot;
    }

    /**
     * Sets the value of the vTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutFaux(String value) {
        this.statutFaux = value;
    }

    /**
     * Gets the value of the fAux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getFAux() {
        return fAux;
    }

    /**
     * Sets the value of the fAux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNatureBallon(String value) {
        this.natureBallon = value;
    }

    /**
     * Gets the value of the uas property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUAS() {
        return uas;
    }

    /**
     * Sets the value of the uas property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUAS(double value) {
        this.uas = value;
    }

    /**
     * Gets the value of the thetaMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMax() {
        return thetaMax;
    }

    /**
     * Sets the value of the thetaMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMax(double value) {
        this.thetaMax = value;
    }

    /**
     * Gets the value of the bStoE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getBStoE() {
        return bStoE;
    }

    /**
     * Sets the value of the bStoE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBStoE(double value) {
        this.bStoE = value;
    }

    /**
     * Gets the value of the vTotAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVTotAppoint() {
        return vTotAppoint;
    }

    /**
     * Sets the value of the vTotAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNatureBallonAppoint(String value) {
        this.natureBallonAppoint = value;
    }

    /**
     * Gets the value of the uasAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUASAppoint() {
        return uasAppoint;
    }

    /**
     * Sets the value of the uasAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUASAppoint(double value) {
        this.uasAppoint = value;
    }

    /**
     * Gets the value of the thetaMaxAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxAppoint() {
        return thetaMaxAppoint;
    }

    /**
     * Sets the value of the thetaMaxAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeGestThBase(String value) {
        this.typeGestThBase = value;
    }

    /**
     * Gets the value of the deltaThetaBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaBase() {
        return deltaThetaBase;
    }

    /**
     * Sets the value of the deltaThetaBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaBase(double value) {
        this.deltaThetaBase = value;
    }

    /**
     * Gets the value of the hechBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getHechBase() {
        return hechBase;
    }

    /**
     * Sets the value of the hechBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setHechBase(double value) {
        this.hechBase = value;
    }

    /**
     * Gets the value of the zRegBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getZRegBase() {
        return zRegBase;
    }

    /**
     * Sets the value of the zRegBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeGestThAppoint(String value) {
        this.typeGestThAppoint = value;
    }

    /**
     * Gets the value of the deltaThetaAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaAppoint() {
        return deltaThetaAppoint;
    }

    /**
     * Sets the value of the deltaThetaAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaAppoint(double value) {
        this.deltaThetaAppoint = value;
    }

    /**
     * Gets the value of the hechAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getHechAppoint() {
        return hechAppoint;
    }

    /**
     * Sets the value of the hechAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setHechAppoint(double value) {
        this.hechAppoint = value;
    }

    /**
     * Gets the value of the zAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getZAppoint() {
        return zAppoint;
    }

    /**
     * Sets the value of the zAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setZAppoint(int value) {
        this.zAppoint = value;
    }

    /**
     * Gets the value of the zRegAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getZRegAppoint() {
        return zRegAppoint;
    }

    /**
     * Sets the value of the zRegAppoint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setZRegAppoint(int value) {
        this.zRegAppoint = value;
    }

    /**
     * Gets the value of the deltaRegBclCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaRegBclCh() {
        return deltaRegBclCh;
    }

    /**
     * Sets the value of the deltaRegBclCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutDeltaThetaAppoint(String value) {
        this.statutDeltaThetaAppoint = value;
    }

    /**
     * Gets the value of the typeAccumulateurECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeAccumulateurECS() {
        return typeAccumulateurECS;
    }

    /**
     * Sets the value of the typeAccumulateurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeAccumulateurECS(String value) {
        this.typeAccumulateurECS = value;
    }

    /**
     * Gets the value of the thetaCons property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaCons() {
        return thetaCons;
    }

    /**
     * Sets the value of the thetaCons property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaCons(double value) {
        this.thetaCons = value;
    }

    /**
     * Gets the value of the isRetourBoucleSepare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsRetourBoucleSepare() {
        return isRetourBoucleSepare;
    }

    /**
     * Sets the value of the isRetourBoucleSepare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsRetourBoucleSepare(String value) {
        this.isRetourBoucleSepare = value;
    }

    /**
     * Gets the value of the pechECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPechECS() {
        return pechECS;
    }

    /**
     * Sets the value of the pechECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPechECS(double value) {
        this.pechECS = value;
    }

    /**
     * Gets the value of the qvPrimECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvPrimECS() {
        return qvPrimECS;
    }

    /**
     * Sets the value of the qvPrimECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvPrimECS(double value) {
        this.qvPrimECS = value;
    }

    /**
     * Gets the value of the typeCirculateurPrepECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeCirculateurPrepECS() {
        return typeCirculateurPrepECS;
    }

    /**
     * Sets the value of the typeCirculateurPrepECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeCirculateurPrepECS(String value) {
        this.typeCirculateurPrepECS = value;
    }

    /**
     * Gets the value of the pwCircPrimECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPwCircPrimECS() {
        return pwCircPrimECS;
    }

    /**
     * Sets the value of the pwCircPrimECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPwCircPrimECS(double value) {
        this.pwCircPrimECS = value;
    }

    /**
     * Gets the value of the pwCircPrimRB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPwCircPrimRB() {
        return pwCircPrimRB;
    }

    /**
     * Sets the value of the pwCircPrimRB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPwCircPrimRB(double value) {
        this.pwCircPrimRB = value;
    }

    /**
     * Gets the value of the gestionRegulationThermostatBallonCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataGestionRegulationThermostatBallon }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataGestionRegulationThermostatBallon getGestionRegulationThermostatBallonCollection() {
        return gestionRegulationThermostatBallonCollection;
    }

    /**
     * Sets the value of the gestionRegulationThermostatBallonCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataGestionRegulationThermostatBallon }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGestionRegulationThermostatBallonCollection(ArrayOfRTDataGestionRegulationThermostatBallon value) {
        this.gestionRegulationThermostatBallonCollection = value;
    }

}
