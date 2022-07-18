
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Groupe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Groupe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SU" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Def_Httf" type="{}E_Def_Httf"/>
 *         &lt;element name="Httf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_trav_surv" type="{}E_Caractere_Traversant"/>
 *         &lt;element name="Is_Hall" type="{}RT_Oui_Non"/>
 *         &lt;element name="Categorie_CE" type="{}E_Categorie_CE"/>
 *         &lt;element name="Is_Climatise" type="{}RT_Oui_Non"/>
 *         &lt;element name="Exp_BR_Groupe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="S_combles" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Pgrm_Ch" type="{}E_Type_Pgrm_Ch"/>
 *         &lt;element name="Type_Pgrm_Fr" type="{}RT_Type_Pgrm_Fr"/>
 *         &lt;element name="Qv_occ_BBIO" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_inocc_BBIO" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Bois_Energie" type="{}RT_Oui_Non"/>
 *         &lt;element name="Is_Reseau_Ch" type="{}E_Is_Reseau_Ch"/>
 *         &lt;element name="Contenu_CO2_Reseau_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Reseau_Fr" type="{}E_Is_Reseau_Fr"/>
 *         &lt;element name="Contenu_CO2_Reseau_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paroi_opaque_Collection" type="{}ArrayOfRT_Data_Paroi_opaque" minOccurs="0"/>
 *         &lt;element name="Lineaire_Collection" type="{}ArrayOfRT_Data_Lineaire" minOccurs="0"/>
 *         &lt;element name="Emetteur_Collection" type="{}ArrayOfRT_Data_Emetteur" minOccurs="0"/>
 *         &lt;element name="Emetteur_ECS_Collection" type="{}ArrayOfRT_Data_Emetteur_ECS" minOccurs="0"/>
 *         &lt;element name="Entree_Air_Collection" type="{}ArrayOfRT_Data_Entree_Air" minOccurs="0"/>
 *         &lt;element name="Eclairage_Collection" type="{}ArrayOfRT_Data_Eclairage" minOccurs="0"/>
 *         &lt;element name="Inertie" type="{}RT_Data_Inertie" minOccurs="0"/>
 *         &lt;element name="Permeabilite" type="{}RT_Data_Permeabilite" minOccurs="0"/>
 *         &lt;element name="Baie_Collection" type="{}ArrayOfRT_Data_Baie" minOccurs="0"/>
 *         &lt;element name="Bouche_Conduit_Collection" type="{}ArrayOfRT_Data_Bouche_Conduit" minOccurs="0"/>
 *         &lt;element name="Aeration_Collection" type="{}ArrayOfRT_Data_Aeration" minOccurs="0"/>
 *         &lt;element name="Ventilation_Naturelle_Hybride_Collection" type="{}ArrayOfRT_Data_Ventilation_Naturelle_Hybride" minOccurs="0"/>
 *         &lt;element name="Brasseur_Air_Collection" type="{}ArrayOfRT_Data_Brasseur_Air" minOccurs="0"/>
 *         &lt;element name="Emission_Rafraichissement_Direct_Collection" type="{}ArrayOfRT_Data_Emission_Rafraichissement_Direct" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Groupe", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataGroupe {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "SU")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double su;
    @XmlElement(name = "SHAB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double shab;
    @XmlElement(name = "V")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double v;
    @XmlElement(name = "Def_Httf", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String defHttf;
    @XmlElement(name = "Httf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double httf;
    @XmlElement(name = "Delta_trav_surv", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String deltaTravSurv;
    @XmlElement(name = "Is_Hall", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isHall;
    @XmlElement(name = "Categorie_CE", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String categorieCE;
    @XmlElement(name = "Is_Climatise", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isClimatise;
    @XmlElement(name = "Exp_BR_Groupe")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int expBRGroupe;
    @XmlElement(name = "S_combles")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double sCombles;
    @XmlElement(name = "Type_Pgrm_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typePgrmCh;
    @XmlElement(name = "Type_Pgrm_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typePgrmFr;
    @XmlElement(name = "Qv_occ_BBIO")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvOccBBIO;
    @XmlElement(name = "Qv_inocc_BBIO")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvInoccBBIO;
    @XmlElement(name = "Bois_Energie", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String boisEnergie;
    @XmlElement(name = "Is_Reseau_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isReseauCh;
    @XmlElement(name = "Contenu_CO2_Reseau_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double contenuCO2ReseauCh;
    @XmlElement(name = "Is_Reseau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isReseauFr;
    @XmlElement(name = "Contenu_CO2_Reseau_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double contenuCO2ReseauFr;
    @XmlElement(name = "Paroi_opaque_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataParoiOpaque paroiOpaqueCollection;
    @XmlElement(name = "Lineaire_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataLineaire lineaireCollection;
    @XmlElement(name = "Emetteur_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataEmetteur emetteurCollection;
    @XmlElement(name = "Emetteur_ECS_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataEmetteurECS emetteurECSCollection;
    @XmlElement(name = "Entree_Air_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataEntreeAir entreeAirCollection;
    @XmlElement(name = "Eclairage_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataEclairage eclairageCollection;
    @XmlElement(name = "Inertie")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataInertie inertie;
    @XmlElement(name = "Permeabilite")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataPermeabilite permeabilite;
    @XmlElement(name = "Baie_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataBaie baieCollection;
    @XmlElement(name = "Bouche_Conduit_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataBoucheConduit boucheConduitCollection;
    @XmlElement(name = "Aeration_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataAeration aerationCollection;
    @XmlElement(name = "Ventilation_Naturelle_Hybride_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataVentilationNaturelleHybride ventilationNaturelleHybrideCollection;
    @XmlElement(name = "Brasseur_Air_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataBrasseurAir brasseurAirCollection;
    @XmlElement(name = "Emission_Rafraichissement_Direct_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataEmissionRafraichissementDirect emissionRafraichissementDirectCollection;

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
     * Gets the value of the su property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSU() {
        return su;
    }

    /**
     * Sets the value of the su property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSU(double value) {
        this.su = value;
    }

    /**
     * Gets the value of the shab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSHAB() {
        return shab;
    }

    /**
     * Sets the value of the shab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSHAB(double value) {
        this.shab = value;
    }

    /**
     * Gets the value of the v property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getV() {
        return v;
    }

    /**
     * Sets the value of the v property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setV(double value) {
        this.v = value;
    }

    /**
     * Gets the value of the defHttf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getDefHttf() {
        return defHttf;
    }

    /**
     * Sets the value of the defHttf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDefHttf(String value) {
        this.defHttf = value;
    }

    /**
     * Gets the value of the httf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getHttf() {
        return httf;
    }

    /**
     * Sets the value of the httf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setHttf(double value) {
        this.httf = value;
    }

    /**
     * Gets the value of the deltaTravSurv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getDeltaTravSurv() {
        return deltaTravSurv;
    }

    /**
     * Sets the value of the deltaTravSurv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaTravSurv(String value) {
        this.deltaTravSurv = value;
    }

    /**
     * Gets the value of the isHall property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsHall() {
        return isHall;
    }

    /**
     * Sets the value of the isHall property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsHall(String value) {
        this.isHall = value;
    }

    /**
     * Gets the value of the categorieCE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCategorieCE() {
        return categorieCE;
    }

    /**
     * Sets the value of the categorieCE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCategorieCE(String value) {
        this.categorieCE = value;
    }

    /**
     * Gets the value of the isClimatise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsClimatise() {
        return isClimatise;
    }

    /**
     * Sets the value of the isClimatise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsClimatise(String value) {
        this.isClimatise = value;
    }

    /**
     * Gets the value of the expBRGroupe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getExpBRGroupe() {
        return expBRGroupe;
    }

    /**
     * Sets the value of the expBRGroupe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setExpBRGroupe(int value) {
        this.expBRGroupe = value;
    }

    /**
     * Gets the value of the sCombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSCombles() {
        return sCombles;
    }

    /**
     * Sets the value of the sCombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSCombles(double value) {
        this.sCombles = value;
    }

    /**
     * Gets the value of the typePgrmCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypePgrmCh() {
        return typePgrmCh;
    }

    /**
     * Sets the value of the typePgrmCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypePgrmCh(String value) {
        this.typePgrmCh = value;
    }

    /**
     * Gets the value of the typePgrmFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypePgrmFr() {
        return typePgrmFr;
    }

    /**
     * Sets the value of the typePgrmFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypePgrmFr(String value) {
        this.typePgrmFr = value;
    }

    /**
     * Gets the value of the qvOccBBIO property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvOccBBIO() {
        return qvOccBBIO;
    }

    /**
     * Sets the value of the qvOccBBIO property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvOccBBIO(double value) {
        this.qvOccBBIO = value;
    }

    /**
     * Gets the value of the qvInoccBBIO property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvInoccBBIO() {
        return qvInoccBBIO;
    }

    /**
     * Sets the value of the qvInoccBBIO property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvInoccBBIO(double value) {
        this.qvInoccBBIO = value;
    }

    /**
     * Gets the value of the boisEnergie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getBoisEnergie() {
        return boisEnergie;
    }

    /**
     * Sets the value of the boisEnergie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBoisEnergie(String value) {
        this.boisEnergie = value;
    }

    /**
     * Gets the value of the isReseauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsReseauCh() {
        return isReseauCh;
    }

    /**
     * Sets the value of the isReseauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsReseauCh(String value) {
        this.isReseauCh = value;
    }

    /**
     * Gets the value of the contenuCO2ReseauCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getContenuCO2ReseauCh() {
        return contenuCO2ReseauCh;
    }

    /**
     * Sets the value of the contenuCO2ReseauCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setContenuCO2ReseauCh(double value) {
        this.contenuCO2ReseauCh = value;
    }

    /**
     * Gets the value of the isReseauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsReseauFr() {
        return isReseauFr;
    }

    /**
     * Sets the value of the isReseauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsReseauFr(String value) {
        this.isReseauFr = value;
    }

    /**
     * Gets the value of the contenuCO2ReseauFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getContenuCO2ReseauFr() {
        return contenuCO2ReseauFr;
    }

    /**
     * Sets the value of the contenuCO2ReseauFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setContenuCO2ReseauFr(double value) {
        this.contenuCO2ReseauFr = value;
    }

    /**
     * Gets the value of the paroiOpaqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataParoiOpaque }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataParoiOpaque getParoiOpaqueCollection() {
        return paroiOpaqueCollection;
    }

    /**
     * Sets the value of the paroiOpaqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataParoiOpaque }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setParoiOpaqueCollection(ArrayOfRTDataParoiOpaque value) {
        this.paroiOpaqueCollection = value;
    }

    /**
     * Gets the value of the lineaireCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataLineaire }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataLineaire getLineaireCollection() {
        return lineaireCollection;
    }

    /**
     * Sets the value of the lineaireCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataLineaire }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLineaireCollection(ArrayOfRTDataLineaire value) {
        this.lineaireCollection = value;
    }

    /**
     * Gets the value of the emetteurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEmetteur }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataEmetteur getEmetteurCollection() {
        return emetteurCollection;
    }

    /**
     * Sets the value of the emetteurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEmetteur }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEmetteurCollection(ArrayOfRTDataEmetteur value) {
        this.emetteurCollection = value;
    }

    /**
     * Gets the value of the emetteurECSCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEmetteurECS }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataEmetteurECS getEmetteurECSCollection() {
        return emetteurECSCollection;
    }

    /**
     * Sets the value of the emetteurECSCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEmetteurECS }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEmetteurECSCollection(ArrayOfRTDataEmetteurECS value) {
        this.emetteurECSCollection = value;
    }

    /**
     * Gets the value of the entreeAirCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEntreeAir }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataEntreeAir getEntreeAirCollection() {
        return entreeAirCollection;
    }

    /**
     * Sets the value of the entreeAirCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEntreeAir }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEntreeAirCollection(ArrayOfRTDataEntreeAir value) {
        this.entreeAirCollection = value;
    }

    /**
     * Gets the value of the eclairageCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEclairage }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataEclairage getEclairageCollection() {
        return eclairageCollection;
    }

    /**
     * Sets the value of the eclairageCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEclairage }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEclairageCollection(ArrayOfRTDataEclairage value) {
        this.eclairageCollection = value;
    }

    /**
     * Gets the value of the inertie property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataInertie }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataInertie getInertie() {
        return inertie;
    }

    /**
     * Sets the value of the inertie property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataInertie }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertie(RTDataInertie value) {
        this.inertie = value;
    }

    /**
     * Gets the value of the permeabilite property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataPermeabilite }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataPermeabilite getPermeabilite() {
        return permeabilite;
    }

    /**
     * Sets the value of the permeabilite property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataPermeabilite }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeabilite(RTDataPermeabilite value) {
        this.permeabilite = value;
    }

    /**
     * Gets the value of the baieCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBaie }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataBaie getBaieCollection() {
        return baieCollection;
    }

    /**
     * Sets the value of the baieCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBaie }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBaieCollection(ArrayOfRTDataBaie value) {
        this.baieCollection = value;
    }

    /**
     * Gets the value of the boucheConduitCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBoucheConduit }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataBoucheConduit getBoucheConduitCollection() {
        return boucheConduitCollection;
    }

    /**
     * Sets the value of the boucheConduitCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBoucheConduit }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBoucheConduitCollection(ArrayOfRTDataBoucheConduit value) {
        this.boucheConduitCollection = value;
    }

    /**
     * Gets the value of the aerationCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataAeration }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataAeration getAerationCollection() {
        return aerationCollection;
    }

    /**
     * Sets the value of the aerationCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataAeration }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAerationCollection(ArrayOfRTDataAeration value) {
        this.aerationCollection = value;
    }

    /**
     * Gets the value of the ventilationNaturelleHybrideCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataVentilationNaturelleHybride }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataVentilationNaturelleHybride getVentilationNaturelleHybrideCollection() {
        return ventilationNaturelleHybrideCollection;
    }

    /**
     * Sets the value of the ventilationNaturelleHybrideCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataVentilationNaturelleHybride }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVentilationNaturelleHybrideCollection(ArrayOfRTDataVentilationNaturelleHybride value) {
        this.ventilationNaturelleHybrideCollection = value;
    }

    /**
     * Gets the value of the brasseurAirCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBrasseurAir }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataBrasseurAir getBrasseurAirCollection() {
        return brasseurAirCollection;
    }

    /**
     * Sets the value of the brasseurAirCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBrasseurAir }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setBrasseurAirCollection(ArrayOfRTDataBrasseurAir value) {
        this.brasseurAirCollection = value;
    }

    /**
     * Gets the value of the emissionRafraichissementDirectCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEmissionRafraichissementDirect }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataEmissionRafraichissementDirect getEmissionRafraichissementDirectCollection() {
        return emissionRafraichissementDirectCollection;
    }

    /**
     * Sets the value of the emissionRafraichissementDirectCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEmissionRafraichissementDirect }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEmissionRafraichissementDirectCollection(ArrayOfRTDataEmissionRafraichissementDirect value) {
        this.emissionRafraichissementDirectCollection = value;
    }

}
