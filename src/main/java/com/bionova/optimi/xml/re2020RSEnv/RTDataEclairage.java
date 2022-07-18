
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Eclairage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Eclairage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Locaux_Bureau" type="{}E_Locaux_Bureau"/>
 *         &lt;element name="Locaux_Creche_Garderie_Pouponieres" type="{}E_Locaux_Creche_Garderie_Pouponieres"/>
 *         &lt;element name="Locaux_Enseignement_Primaire" type="{}E_Locaux_Enseignement_Primaire"/>
 *         &lt;element name="Locaux_Mediatheque" type="{}E_Locaux_Mediatheque"/>
 *         &lt;element name="Locaux_Residence_Medicalisee" type="{}E_Locaux_Residence_Medicalisee"/>
 *         &lt;element name="Locaux_Universite" type="{}E_Locaux_Universite"/>
 *         &lt;element name="Locaux_Secondaire_Jour" type="{}E_Locaux_Secondaire_Jour"/>
 *         &lt;element name="Locaux_Hotel_1etoile_Nuit" type="{}E_Locaux_Hotel_1etoile_Nuit"/>
 *         &lt;element name="Locaux_Hotel_2etoile_Nuit" type="{}E_Locaux_Hotel_2etoile_Nuit"/>
 *         &lt;element name="Locaux_Hotel_3etoile_Nuit" type="{}E_Locaux_Hotel_3etoile_Nuit"/>
 *         &lt;element name="Locaux_Hotel_4etoile_Nuit" type="{}E_Locaux_Hotel_4etoile_Nuit"/>
 *         &lt;element name="Locaux_Hotel_1_ou_2etoiles_Jour" type="{}E_Locaux_Hotel_1_ou_2etoiles_Jour"/>
 *         &lt;element name="Locaux_Hotel_3_ou_4etoiles_Jour" type="{}E_Locaux_Hotel_3_ou_4etoiles_Jour"/>
 *         &lt;element name="Locaux_Restaurant_1_Repas_5_7" type="{}E_Locaux_Restaurant_1_Repas_5_7"/>
 *         &lt;element name="Locaux_Restaurant_2_Repas_jour_6_7" type="{}E_Locaux_Restaurant_2_Repas_jour_6_7"/>
 *         &lt;element name="Locaux_Restaurant_2_Repas_jour_7_7" type="{}E_Locaux_Restaurant_2_Repas_jour_7_7"/>
 *         &lt;element name="Locaux_Restaurant_3_Repas_jour_5_7" type="{}E_Locaux_Restaurant_3_Repas_jour_5_7"/>
 *         &lt;element name="Locaux_Restauration_Continue" type="{}E_Locaux_Restauration_Continue"/>
 *         &lt;element name="Locaux_Commerce_Magazin_ZI" type="{}E_Locaux_Commerce_Magazin_ZI"/>
 *         &lt;element name="Locaux_Vestiaires" type="{}E_Locaux_Vestiaires"/>
 *         &lt;element name="Locaux_Gymnase" type="{}E_Locaux_Gymnase"/>
 *         &lt;element name="Residence_Medicalisee" type="{}E_Locaux_Residence_Medicalisee"/>
 *         &lt;element name="Locaux_Hopital_partie_nuit" type="{}E_Locaux_Hopital_partie_nuit"/>
 *         &lt;element name="Locaux_Hopital_partie_jour" type="{}E_Locaux_Hopital_partie_jour"/>
 *         &lt;element name="Locaux_Transport_Aerogare" type="{}E_Locaux_Transport_Aerogare"/>
 *         &lt;element name="Locaux_Industrie_3_8" type="{}E_Locaux_Industrie_3_8"/>
 *         &lt;element name="Locaux_Industrie_8h_18" type="{}E_Locaux_Industrie_8h_18"/>
 *         &lt;element name="Eiproj" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_local" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ratio_ecl_nat" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fr_Grad_Ecl" type="{}E_Fr_Grad_ecl"/>
 *         &lt;element name="Eff_immo_projet" type="{}E_Lampes"/>
 *         &lt;element name="Eff_ecl_immo_projet" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pecl_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pecl_aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_Ecl" type="{}E_Gest_Ecl"/>
 *         &lt;element name="Grad_Ecl" type="{}E_Grad_Ecl"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Eclairage", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataEclairage {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Locaux_Bureau", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxBureau;
    @XmlElement(name = "Locaux_Creche_Garderie_Pouponieres", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxCrecheGarderiePouponieres;
    @XmlElement(name = "Locaux_Enseignement_Primaire", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxEnseignementPrimaire;
    @XmlElement(name = "Locaux_Mediatheque", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxMediatheque;
    @XmlElement(name = "Locaux_Residence_Medicalisee", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxResidenceMedicalisee;
    @XmlElement(name = "Locaux_Universite", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxUniversite;
    @XmlElement(name = "Locaux_Secondaire_Jour", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxSecondaireJour;
    @XmlElement(name = "Locaux_Hotel_1etoile_Nuit", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHotel1EtoileNuit;
    @XmlElement(name = "Locaux_Hotel_2etoile_Nuit", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHotel2EtoileNuit;
    @XmlElement(name = "Locaux_Hotel_3etoile_Nuit", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHotel3EtoileNuit;
    @XmlElement(name = "Locaux_Hotel_4etoile_Nuit", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHotel4EtoileNuit;
    @XmlElement(name = "Locaux_Hotel_1_ou_2etoiles_Jour", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHotel1Ou2EtoilesJour;
    @XmlElement(name = "Locaux_Hotel_3_ou_4etoiles_Jour", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHotel3Ou4EtoilesJour;
    @XmlElement(name = "Locaux_Restaurant_1_Repas_5_7", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxRestaurant1Repas57;
    @XmlElement(name = "Locaux_Restaurant_2_Repas_jour_6_7", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxRestaurant2RepasJour67;
    @XmlElement(name = "Locaux_Restaurant_2_Repas_jour_7_7", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxRestaurant2RepasJour77;
    @XmlElement(name = "Locaux_Restaurant_3_Repas_jour_5_7", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxRestaurant3RepasJour57;
    @XmlElement(name = "Locaux_Restauration_Continue", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxRestaurationContinue;
    @XmlElement(name = "Locaux_Commerce_Magazin_ZI", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxCommerceMagazinZI;
    @XmlElement(name = "Locaux_Vestiaires", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxVestiaires;
    @XmlElement(name = "Locaux_Gymnase", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxGymnase;
    @XmlElement(name = "Residence_Medicalisee", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String residenceMedicalisee;
    @XmlElement(name = "Locaux_Hopital_partie_nuit", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHopitalPartieNuit;
    @XmlElement(name = "Locaux_Hopital_partie_jour", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxHopitalPartieJour;
    @XmlElement(name = "Locaux_Transport_Aerogare", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxTransportAerogare;
    @XmlElement(name = "Locaux_Industrie_3_8", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxIndustrie38;
    @XmlElement(name = "Locaux_Industrie_8h_18", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locauxIndustrie8H18;
    @XmlElement(name = "Eiproj")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double eiproj;
    @XmlElement(name = "Rat_local")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratLocal;
    @XmlElement(name = "Ratio_ecl_nat")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratioEclNat;
    @XmlElement(name = "Fr_Grad_Ecl", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String frGradEcl;
    @XmlElement(name = "Eff_immo_projet", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String effImmoProjet;
    @XmlElement(name = "Eff_ecl_immo_projet")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double effEclImmoProjet;
    @XmlElement(name = "Pecl_tot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double peclTot;
    @XmlElement(name = "Pecl_aux")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double peclAux;
    @XmlElement(name = "Gest_Ecl", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String gestEcl;
    @XmlElement(name = "Grad_Ecl", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String gradEcl;

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
     * Gets the value of the locauxBureau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxBureau() {
        return locauxBureau;
    }

    /**
     * Sets the value of the locauxBureau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxBureau(String value) {
        this.locauxBureau = value;
    }

    /**
     * Gets the value of the locauxCrecheGarderiePouponieres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxCrecheGarderiePouponieres() {
        return locauxCrecheGarderiePouponieres;
    }

    /**
     * Sets the value of the locauxCrecheGarderiePouponieres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxCrecheGarderiePouponieres(String value) {
        this.locauxCrecheGarderiePouponieres = value;
    }

    /**
     * Gets the value of the locauxEnseignementPrimaire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxEnseignementPrimaire() {
        return locauxEnseignementPrimaire;
    }

    /**
     * Sets the value of the locauxEnseignementPrimaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxEnseignementPrimaire(String value) {
        this.locauxEnseignementPrimaire = value;
    }

    /**
     * Gets the value of the locauxMediatheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxMediatheque() {
        return locauxMediatheque;
    }

    /**
     * Sets the value of the locauxMediatheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxMediatheque(String value) {
        this.locauxMediatheque = value;
    }

    /**
     * Gets the value of the locauxResidenceMedicalisee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxResidenceMedicalisee() {
        return locauxResidenceMedicalisee;
    }

    /**
     * Sets the value of the locauxResidenceMedicalisee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxResidenceMedicalisee(String value) {
        this.locauxResidenceMedicalisee = value;
    }

    /**
     * Gets the value of the locauxUniversite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxUniversite() {
        return locauxUniversite;
    }

    /**
     * Sets the value of the locauxUniversite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxUniversite(String value) {
        this.locauxUniversite = value;
    }

    /**
     * Gets the value of the locauxSecondaireJour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxSecondaireJour() {
        return locauxSecondaireJour;
    }

    /**
     * Sets the value of the locauxSecondaireJour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxSecondaireJour(String value) {
        this.locauxSecondaireJour = value;
    }

    /**
     * Gets the value of the locauxHotel1EtoileNuit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHotel1EtoileNuit() {
        return locauxHotel1EtoileNuit;
    }

    /**
     * Sets the value of the locauxHotel1EtoileNuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHotel1EtoileNuit(String value) {
        this.locauxHotel1EtoileNuit = value;
    }

    /**
     * Gets the value of the locauxHotel2EtoileNuit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHotel2EtoileNuit() {
        return locauxHotel2EtoileNuit;
    }

    /**
     * Sets the value of the locauxHotel2EtoileNuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHotel2EtoileNuit(String value) {
        this.locauxHotel2EtoileNuit = value;
    }

    /**
     * Gets the value of the locauxHotel3EtoileNuit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHotel3EtoileNuit() {
        return locauxHotel3EtoileNuit;
    }

    /**
     * Sets the value of the locauxHotel3EtoileNuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHotel3EtoileNuit(String value) {
        this.locauxHotel3EtoileNuit = value;
    }

    /**
     * Gets the value of the locauxHotel4EtoileNuit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHotel4EtoileNuit() {
        return locauxHotel4EtoileNuit;
    }

    /**
     * Sets the value of the locauxHotel4EtoileNuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHotel4EtoileNuit(String value) {
        this.locauxHotel4EtoileNuit = value;
    }

    /**
     * Gets the value of the locauxHotel1Ou2EtoilesJour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHotel1Ou2EtoilesJour() {
        return locauxHotel1Ou2EtoilesJour;
    }

    /**
     * Sets the value of the locauxHotel1Ou2EtoilesJour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHotel1Ou2EtoilesJour(String value) {
        this.locauxHotel1Ou2EtoilesJour = value;
    }

    /**
     * Gets the value of the locauxHotel3Ou4EtoilesJour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHotel3Ou4EtoilesJour() {
        return locauxHotel3Ou4EtoilesJour;
    }

    /**
     * Sets the value of the locauxHotel3Ou4EtoilesJour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHotel3Ou4EtoilesJour(String value) {
        this.locauxHotel3Ou4EtoilesJour = value;
    }

    /**
     * Gets the value of the locauxRestaurant1Repas57 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxRestaurant1Repas57() {
        return locauxRestaurant1Repas57;
    }

    /**
     * Sets the value of the locauxRestaurant1Repas57 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxRestaurant1Repas57(String value) {
        this.locauxRestaurant1Repas57 = value;
    }

    /**
     * Gets the value of the locauxRestaurant2RepasJour67 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxRestaurant2RepasJour67() {
        return locauxRestaurant2RepasJour67;
    }

    /**
     * Sets the value of the locauxRestaurant2RepasJour67 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxRestaurant2RepasJour67(String value) {
        this.locauxRestaurant2RepasJour67 = value;
    }

    /**
     * Gets the value of the locauxRestaurant2RepasJour77 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxRestaurant2RepasJour77() {
        return locauxRestaurant2RepasJour77;
    }

    /**
     * Sets the value of the locauxRestaurant2RepasJour77 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxRestaurant2RepasJour77(String value) {
        this.locauxRestaurant2RepasJour77 = value;
    }

    /**
     * Gets the value of the locauxRestaurant3RepasJour57 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxRestaurant3RepasJour57() {
        return locauxRestaurant3RepasJour57;
    }

    /**
     * Sets the value of the locauxRestaurant3RepasJour57 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxRestaurant3RepasJour57(String value) {
        this.locauxRestaurant3RepasJour57 = value;
    }

    /**
     * Gets the value of the locauxRestaurationContinue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxRestaurationContinue() {
        return locauxRestaurationContinue;
    }

    /**
     * Sets the value of the locauxRestaurationContinue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxRestaurationContinue(String value) {
        this.locauxRestaurationContinue = value;
    }

    /**
     * Gets the value of the locauxCommerceMagazinZI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxCommerceMagazinZI() {
        return locauxCommerceMagazinZI;
    }

    /**
     * Sets the value of the locauxCommerceMagazinZI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxCommerceMagazinZI(String value) {
        this.locauxCommerceMagazinZI = value;
    }

    /**
     * Gets the value of the locauxVestiaires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxVestiaires() {
        return locauxVestiaires;
    }

    /**
     * Sets the value of the locauxVestiaires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxVestiaires(String value) {
        this.locauxVestiaires = value;
    }

    /**
     * Gets the value of the locauxGymnase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxGymnase() {
        return locauxGymnase;
    }

    /**
     * Sets the value of the locauxGymnase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxGymnase(String value) {
        this.locauxGymnase = value;
    }

    /**
     * Gets the value of the residenceMedicalisee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getResidenceMedicalisee() {
        return residenceMedicalisee;
    }

    /**
     * Sets the value of the residenceMedicalisee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setResidenceMedicalisee(String value) {
        this.residenceMedicalisee = value;
    }

    /**
     * Gets the value of the locauxHopitalPartieNuit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHopitalPartieNuit() {
        return locauxHopitalPartieNuit;
    }

    /**
     * Sets the value of the locauxHopitalPartieNuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHopitalPartieNuit(String value) {
        this.locauxHopitalPartieNuit = value;
    }

    /**
     * Gets the value of the locauxHopitalPartieJour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxHopitalPartieJour() {
        return locauxHopitalPartieJour;
    }

    /**
     * Sets the value of the locauxHopitalPartieJour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxHopitalPartieJour(String value) {
        this.locauxHopitalPartieJour = value;
    }

    /**
     * Gets the value of the locauxTransportAerogare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxTransportAerogare() {
        return locauxTransportAerogare;
    }

    /**
     * Sets the value of the locauxTransportAerogare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxTransportAerogare(String value) {
        this.locauxTransportAerogare = value;
    }

    /**
     * Gets the value of the locauxIndustrie38 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxIndustrie38() {
        return locauxIndustrie38;
    }

    /**
     * Sets the value of the locauxIndustrie38 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxIndustrie38(String value) {
        this.locauxIndustrie38 = value;
    }

    /**
     * Gets the value of the locauxIndustrie8H18 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocauxIndustrie8H18() {
        return locauxIndustrie8H18;
    }

    /**
     * Sets the value of the locauxIndustrie8H18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocauxIndustrie8H18(String value) {
        this.locauxIndustrie8H18 = value;
    }

    /**
     * Gets the value of the eiproj property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEiproj() {
        return eiproj;
    }

    /**
     * Sets the value of the eiproj property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEiproj(double value) {
        this.eiproj = value;
    }

    /**
     * Gets the value of the ratLocal property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatLocal() {
        return ratLocal;
    }

    /**
     * Sets the value of the ratLocal property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatLocal(double value) {
        this.ratLocal = value;
    }

    /**
     * Gets the value of the ratioEclNat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatioEclNat() {
        return ratioEclNat;
    }

    /**
     * Sets the value of the ratioEclNat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatioEclNat(double value) {
        this.ratioEclNat = value;
    }

    /**
     * Gets the value of the frGradEcl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getFrGradEcl() {
        return frGradEcl;
    }

    /**
     * Sets the value of the frGradEcl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFrGradEcl(String value) {
        this.frGradEcl = value;
    }

    /**
     * Gets the value of the effImmoProjet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getEffImmoProjet() {
        return effImmoProjet;
    }

    /**
     * Sets the value of the effImmoProjet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEffImmoProjet(String value) {
        this.effImmoProjet = value;
    }

    /**
     * Gets the value of the effEclImmoProjet property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEffEclImmoProjet() {
        return effEclImmoProjet;
    }

    /**
     * Sets the value of the effEclImmoProjet property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEffEclImmoProjet(double value) {
        this.effEclImmoProjet = value;
    }

    /**
     * Gets the value of the peclTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPeclTot() {
        return peclTot;
    }

    /**
     * Sets the value of the peclTot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPeclTot(double value) {
        this.peclTot = value;
    }

    /**
     * Gets the value of the peclAux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPeclAux() {
        return peclAux;
    }

    /**
     * Sets the value of the peclAux property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPeclAux(double value) {
        this.peclAux = value;
    }

    /**
     * Gets the value of the gestEcl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getGestEcl() {
        return gestEcl;
    }

    /**
     * Sets the value of the gestEcl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGestEcl(String value) {
        this.gestEcl = value;
    }

    /**
     * Gets the value of the gradEcl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getGradEcl() {
        return gradEcl;
    }

    /**
     * Sets the value of the gradEcl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGradEcl(String value) {
        this.gradEcl = value;
    }

}
