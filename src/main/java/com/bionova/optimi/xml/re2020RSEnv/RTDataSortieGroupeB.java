
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Groupe_B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Groupe_B">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="O_SREF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SU" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Categorie_CE" type="{}E_Categorie_CE"/>
 *         &lt;element name="Is_Climatise" type="{}RT_Oui_Non"/>
 *         &lt;element name="O_Bbio_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbgeo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbcombles" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbsurf_moy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbsurf_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbbruit" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_ECS_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Bbio_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_ECS_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Bbio_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Grp_Acces_Total" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Grp_Acces_Mixte" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Grp_Sans_Acces" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Nbh_Occ_Einat_Sup" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbh_Occ_Einat_Inf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbh_Occ_Nuit" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbh_Ecl_Non_Aut" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Taux_Occ_Einat_Sup" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Baie_B_Collection" type="{}ArrayOfRT_Data_Sortie_Baie_B" minOccurs="0"/>
 *         &lt;element name="A_baies" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_sud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_nord" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_ouest" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_est" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_horiz" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_B", propOrder = {
    "index",
    "name",
    "description",
    "osref",
    "oshab",
    "osu",
    "categorieCE",
    "isClimatise",
    "oBbioMax",
    "oMbgeo",
    "oMbcombles",
    "oMbsurfMoy",
    "oMbsurfTot",
    "oMbbruit",
    "obChMois",
    "obFrMois",
    "obEclMois",
    "obecsMois",
    "obChAnnuel",
    "obFrAnnuel",
    "obEclAnnuel",
    "oBbioPtsMois",
    "obecsAnnuel",
    "oBbioPtsAnnuel",
    "grpAccesTotal",
    "grpAccesMixte",
    "grpSansAcces",
    "nbhOccEinatSup",
    "nbhOccEinatInf",
    "nbhOccNuit",
    "nbhEclNonAut",
    "tauxOccEinatSup",
    "sortieBaieBCollection",
    "aBaies",
    "partBaiesSud",
    "partBaiesNord",
    "partBaiesOuest",
    "partBaiesEst",
    "partBaiesHoriz"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieGroupeB {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "O_SREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osref;
    @XmlElement(name = "O_SHAB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oshab;
    @XmlElement(name = "O_SU")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osu;
    @XmlElement(name = "Categorie_CE", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String categorieCE;
    @XmlElement(name = "Is_Climatise", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isClimatise;
    @XmlElement(name = "O_Bbio_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBbioMax;
    @XmlElement(name = "O_Mbgeo")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMbgeo;
    @XmlElement(name = "O_Mbcombles")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMbcombles;
    @XmlElement(name = "O_Mbsurf_moy")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMbsurfMoy;
    @XmlElement(name = "O_Mbsurf_tot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMbsurfTot;
    @XmlElement(name = "O_Mbbruit")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oMbbruit;
    @XmlElement(name = "O_B_Ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obChMois;
    @XmlElement(name = "O_B_Fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obFrMois;
    @XmlElement(name = "O_B_Ecl_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obEclMois;
    @XmlElement(name = "O_B_ECS_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obecsMois;
    @XmlElement(name = "O_B_Ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obChAnnuel;
    @XmlElement(name = "O_B_Fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obFrAnnuel;
    @XmlElement(name = "O_B_Ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obEclAnnuel;
    @XmlElement(name = "O_Bbio_pts_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oBbioPtsMois;
    @XmlElement(name = "O_B_ECS_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Double obecsAnnuel;
    @XmlElement(name = "O_Bbio_pts_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBbioPtsAnnuel;
    @XmlElement(name = "Grp_Acces_Total")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean grpAccesTotal;
    @XmlElement(name = "Grp_Acces_Mixte")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean grpAccesMixte;
    @XmlElement(name = "Grp_Sans_Acces")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean grpSansAcces;
    @XmlElement(name = "Nbh_Occ_Einat_Sup")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbhOccEinatSup;
    @XmlElement(name = "Nbh_Occ_Einat_Inf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbhOccEinatInf;
    @XmlElement(name = "Nbh_Occ_Nuit")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbhOccNuit;
    @XmlElement(name = "Nbh_Ecl_Non_Aut")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbhEclNonAut;
    @XmlElement(name = "Taux_Occ_Einat_Sup")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tauxOccEinatSup;
    @XmlElement(name = "Sortie_Baie_B_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieBaieB sortieBaieBCollection;
    @XmlElement(name = "A_baies")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double aBaies;
    @XmlElement(name = "Part_baies_sud")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partBaiesSud;
    @XmlElement(name = "Part_baies_nord")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partBaiesNord;
    @XmlElement(name = "Part_baies_ouest")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partBaiesOuest;
    @XmlElement(name = "Part_baies_est")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partBaiesEst;
    @XmlElement(name = "Part_baies_horiz")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partBaiesHoriz;

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
     * Gets the value of the osref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSREF() {
        return osref;
    }

    /**
     * Sets the value of the osref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSREF(double value) {
        this.osref = value;
    }

    /**
     * Gets the value of the oshab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSHAB(double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSU() {
        return osu;
    }

    /**
     * Sets the value of the osu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSU(double value) {
        this.osu = value;
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
     * Gets the value of the oBbioMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBbioMax() {
        return oBbioMax;
    }

    /**
     * Sets the value of the oBbioMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBbioMax(double value) {
        this.oBbioMax = value;
    }

    /**
     * Gets the value of the oMbgeo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMbgeo() {
        return oMbgeo;
    }

    /**
     * Sets the value of the oMbgeo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMbgeo(double value) {
        this.oMbgeo = value;
    }

    /**
     * Gets the value of the oMbcombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMbcombles() {
        return oMbcombles;
    }

    /**
     * Sets the value of the oMbcombles property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMbcombles(double value) {
        this.oMbcombles = value;
    }

    /**
     * Gets the value of the oMbsurfMoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMbsurfMoy() {
        return oMbsurfMoy;
    }

    /**
     * Sets the value of the oMbsurfMoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMbsurfMoy(double value) {
        this.oMbsurfMoy = value;
    }

    /**
     * Gets the value of the oMbsurfTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMbsurfTot() {
        return oMbsurfTot;
    }

    /**
     * Sets the value of the oMbsurfTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMbsurfTot(double value) {
        this.oMbsurfTot = value;
    }

    /**
     * Gets the value of the oMbbruit property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOMbbruit() {
        return oMbbruit;
    }

    /**
     * Sets the value of the oMbbruit property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOMbbruit(double value) {
        this.oMbbruit = value;
    }

    /**
     * Gets the value of the obChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBChMois() {
        return obChMois;
    }

    /**
     * Sets the value of the obChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChMois(ArrayOfRTDataSortieMensuelle value) {
        this.obChMois = value;
    }

    /**
     * Gets the value of the obFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBFrMois() {
        return obFrMois;
    }

    /**
     * Sets the value of the obFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.obFrMois = value;
    }

    /**
     * Gets the value of the obEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBEclMois() {
        return obEclMois;
    }

    /**
     * Sets the value of the obEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclMois = value;
    }

    /**
     * Gets the value of the obecsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBECSMois() {
        return obecsMois;
    }

    /**
     * Sets the value of the obecsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBECSMois(ArrayOfRTDataSortieMensuelle value) {
        this.obecsMois = value;
    }

    /**
     * Gets the value of the obChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBChAnnuel() {
        return obChAnnuel;
    }

    /**
     * Sets the value of the obChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChAnnuel(double value) {
        this.obChAnnuel = value;
    }

    /**
     * Gets the value of the obFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBFrAnnuel() {
        return obFrAnnuel;
    }

    /**
     * Sets the value of the obFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrAnnuel(double value) {
        this.obFrAnnuel = value;
    }

    /**
     * Gets the value of the obEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBEclAnnuel() {
        return obEclAnnuel;
    }

    /**
     * Sets the value of the obEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclAnnuel(double value) {
        this.obEclAnnuel = value;
    }

    /**
     * Gets the value of the oBbioPtsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBbioPtsMois() {
        return oBbioPtsMois;
    }

    /**
     * Sets the value of the oBbioPtsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBbioPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oBbioPtsMois = value;
    }

    /**
     * Gets the value of the obecsAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Double getOBECSAnnuel() {
        return obecsAnnuel;
    }

    /**
     * Sets the value of the obecsAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBECSAnnuel(Double value) {
        this.obecsAnnuel = value;
    }

    /**
     * Gets the value of the oBbioPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBbioPtsAnnuel() {
        return oBbioPtsAnnuel;
    }

    /**
     * Sets the value of the oBbioPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBbioPtsAnnuel(double value) {
        this.oBbioPtsAnnuel = value;
    }

    /**
     * Gets the value of the grpAccesTotal property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isGrpAccesTotal() {
        return grpAccesTotal;
    }

    /**
     * Sets the value of the grpAccesTotal property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGrpAccesTotal(boolean value) {
        this.grpAccesTotal = value;
    }

    /**
     * Gets the value of the grpAccesMixte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isGrpAccesMixte() {
        return grpAccesMixte;
    }

    /**
     * Sets the value of the grpAccesMixte property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGrpAccesMixte(boolean value) {
        this.grpAccesMixte = value;
    }

    /**
     * Gets the value of the grpSansAcces property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isGrpSansAcces() {
        return grpSansAcces;
    }

    /**
     * Sets the value of the grpSansAcces property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGrpSansAcces(boolean value) {
        this.grpSansAcces = value;
    }

    /**
     * Gets the value of the nbhOccEinatSup property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbhOccEinatSup() {
        return nbhOccEinatSup;
    }

    /**
     * Sets the value of the nbhOccEinatSup property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbhOccEinatSup(double value) {
        this.nbhOccEinatSup = value;
    }

    /**
     * Gets the value of the nbhOccEinatInf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbhOccEinatInf() {
        return nbhOccEinatInf;
    }

    /**
     * Sets the value of the nbhOccEinatInf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbhOccEinatInf(double value) {
        this.nbhOccEinatInf = value;
    }

    /**
     * Gets the value of the nbhOccNuit property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbhOccNuit() {
        return nbhOccNuit;
    }

    /**
     * Sets the value of the nbhOccNuit property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbhOccNuit(double value) {
        this.nbhOccNuit = value;
    }

    /**
     * Gets the value of the nbhEclNonAut property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbhEclNonAut() {
        return nbhEclNonAut;
    }

    /**
     * Sets the value of the nbhEclNonAut property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbhEclNonAut(double value) {
        this.nbhEclNonAut = value;
    }

    /**
     * Gets the value of the tauxOccEinatSup property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTauxOccEinatSup() {
        return tauxOccEinatSup;
    }

    /**
     * Sets the value of the tauxOccEinatSup property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTauxOccEinatSup(double value) {
        this.tauxOccEinatSup = value;
    }

    /**
     * Gets the value of the sortieBaieBCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieBaieB }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieBaieB getSortieBaieBCollection() {
        return sortieBaieBCollection;
    }

    /**
     * Sets the value of the sortieBaieBCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieBaieB }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieBaieBCollection(ArrayOfRTDataSortieBaieB value) {
        this.sortieBaieBCollection = value;
    }

    /**
     * Gets the value of the aBaies property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getABaies() {
        return aBaies;
    }

    /**
     * Sets the value of the aBaies property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setABaies(double value) {
        this.aBaies = value;
    }

    /**
     * Gets the value of the partBaiesSud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartBaiesSud() {
        return partBaiesSud;
    }

    /**
     * Sets the value of the partBaiesSud property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartBaiesSud(double value) {
        this.partBaiesSud = value;
    }

    /**
     * Gets the value of the partBaiesNord property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartBaiesNord() {
        return partBaiesNord;
    }

    /**
     * Sets the value of the partBaiesNord property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartBaiesNord(double value) {
        this.partBaiesNord = value;
    }

    /**
     * Gets the value of the partBaiesOuest property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartBaiesOuest() {
        return partBaiesOuest;
    }

    /**
     * Sets the value of the partBaiesOuest property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartBaiesOuest(double value) {
        this.partBaiesOuest = value;
    }

    /**
     * Gets the value of the partBaiesEst property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartBaiesEst() {
        return partBaiesEst;
    }

    /**
     * Sets the value of the partBaiesEst property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartBaiesEst(double value) {
        this.partBaiesEst = value;
    }

    /**
     * Gets the value of the partBaiesHoriz property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartBaiesHoriz() {
        return partBaiesHoriz;
    }

    /**
     * Sets the value of the partBaiesHoriz property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartBaiesHoriz(double value) {
        this.partBaiesHoriz = value;
    }

}
