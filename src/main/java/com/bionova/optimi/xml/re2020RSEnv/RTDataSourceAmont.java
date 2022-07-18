
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Source_Amont complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Source_Amont">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Fl_Amont" type="{}RT_Type_Fluide"/>
 *         &lt;element name="Source_Amont_Eau" type="{}RT_Source_Amont_Eau"/>
 *         &lt;element name="Source_Amont_Air" type="{}RT_Types_Source_Amont_Air"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_SF_Extraction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Tair_Lim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Idgestion_Captage" type="{}RT_Gestion_Captage"/>
 *         &lt;element name="Idgestion_Pompe_Captage" type="{}RT_Gestion_Pompe_Captage"/>
 *         &lt;element name="Type_Echangeur" type="{}RT_Echangeur"/>
 *         &lt;element name="Type_Generateur_Fr" type="{}RT_Type_Generateur_Fr"/>
 *         &lt;element name="Pvent_Gaine" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_Evap_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_Cond_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ppompes_Tour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_Tour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_Tour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Es_Tour_Consigne" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ppompes_Cap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ppompes_Nappe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ppompes_Inter" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ppompes_Boucle_Eau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Idmois_Mini" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Theta_Min_Source" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Source" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Rech_BE" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Fr_BE" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_nappe_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_inter_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_nom_tour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rho_inter" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cpe_inter" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Idmois_Mini_sol" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Theta_min_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_max_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "RT_Data_Source_Amont", propOrder = {

})
@XmlSeeAlso({
    RTDataSourceAmontAirExtraitAirExterieurNibe.class
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSourceAmont {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Id_Fl_Amont", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idFlAmont;
    @XmlElement(name = "Source_Amont_Eau", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String sourceAmontEau;
    @XmlElement(name = "Source_Amont_Air", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String sourceAmontAir;
    @XmlElement(name = "Id_Et", defaultValue = "0")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idEt;
    @XmlElement(name = "Id_SF_Extraction", defaultValue = "0")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idSFExtraction;
    @XmlElement(name = "Tair_Lim")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tairLim;
    @XmlElement(name = "Idgestion_Captage", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idgestionCaptage;
    @XmlElement(name = "Idgestion_Pompe_Captage", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idgestionPompeCaptage;
    @XmlElement(name = "Type_Echangeur", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeEchangeur;
    @XmlElement(name = "Type_Generateur_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeGenerateurFr;
    @XmlElement(name = "Pvent_Gaine")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventGaine;
    @XmlElement(name = "Delta_Theta_Evap_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaEvapCh;
    @XmlElement(name = "Delta_Theta_Cond_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaCondFr;
    @XmlElement(name = "Ppompes_Tour")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ppompesTour;
    @XmlElement(name = "Pvent_Tour")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pventTour;
    @XmlElement(name = "Delta_Theta_Tour")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaThetaTour;
    @XmlElement(name = "Theta_Es_Tour_Consigne")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaEsTourConsigne;
    @XmlElement(name = "Ppompes_Cap")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ppompesCap;
    @XmlElement(name = "Ppompes_Nappe")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ppompesNappe;
    @XmlElement(name = "Ppompes_Inter")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ppompesInter;
    @XmlElement(name = "Ppompes_Boucle_Eau")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ppompesBoucleEau;
    @XmlElement(name = "Idmois_Mini")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idmoisMini;
    @XmlElement(name = "Theta_Min_Source")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinSource;
    @XmlElement(name = "Theta_Max_Source")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxSource;
    @XmlElement(name = "Theta_Max_Rech_BE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxRechBE;
    @XmlElement(name = "Theta_Min_Fr_BE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinFrBE;
    @XmlElement(name = "Theta_Min_Boucle")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinBoucle;
    @XmlElement(name = "Theta_Max_Boucle")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxBoucle;
    @XmlElement(name = "Rb")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rb;
    @XmlElement(name = "L")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double l;
    @XmlElement(name = "Qv_nappe_nom")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvNappeNom;
    @XmlElement(name = "Qv_inter_nom")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvInterNom;
    @XmlElement(name = "Qv_nom_tour")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvNomTour;
    @XmlElement(name = "Rho_inter")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rhoInter;
    @XmlElement(name = "UA")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ua;
    @XmlElement(name = "Cpe_inter")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double cpeInter;
    @XmlElement(name = "Idmois_Mini_sol")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idmoisMiniSol;
    @XmlElement(name = "Theta_min_sol")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinSol;
    @XmlElement(name = "Theta_max_sol")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxSol;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;

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
     * Gets the value of the idFlAmont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdFlAmont() {
        return idFlAmont;
    }

    /**
     * Sets the value of the idFlAmont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdFlAmont(String value) {
        this.idFlAmont = value;
    }

    /**
     * Gets the value of the sourceAmontEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSourceAmontEau() {
        return sourceAmontEau;
    }

    /**
     * Sets the value of the sourceAmontEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSourceAmontEau(String value) {
        this.sourceAmontEau = value;
    }

    /**
     * Gets the value of the sourceAmontAir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSourceAmontAir() {
        return sourceAmontAir;
    }

    /**
     * Sets the value of the sourceAmontAir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSourceAmontAir(String value) {
        this.sourceAmontAir = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdEt(int value) {
        this.idEt = value;
    }

    /**
     * Gets the value of the idSFExtraction property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdSFExtraction() {
        return idSFExtraction;
    }

    /**
     * Sets the value of the idSFExtraction property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdSFExtraction(int value) {
        this.idSFExtraction = value;
    }

    /**
     * Gets the value of the tairLim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTairLim() {
        return tairLim;
    }

    /**
     * Sets the value of the tairLim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTairLim(double value) {
        this.tairLim = value;
    }

    /**
     * Gets the value of the idgestionCaptage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdgestionCaptage() {
        return idgestionCaptage;
    }

    /**
     * Sets the value of the idgestionCaptage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdgestionCaptage(String value) {
        this.idgestionCaptage = value;
    }

    /**
     * Gets the value of the idgestionPompeCaptage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdgestionPompeCaptage() {
        return idgestionPompeCaptage;
    }

    /**
     * Sets the value of the idgestionPompeCaptage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdgestionPompeCaptage(String value) {
        this.idgestionPompeCaptage = value;
    }

    /**
     * Gets the value of the typeEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeEchangeur() {
        return typeEchangeur;
    }

    /**
     * Sets the value of the typeEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeEchangeur(String value) {
        this.typeEchangeur = value;
    }

    /**
     * Gets the value of the typeGenerateurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeGenerateurFr() {
        return typeGenerateurFr;
    }

    /**
     * Sets the value of the typeGenerateurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeGenerateurFr(String value) {
        this.typeGenerateurFr = value;
    }

    /**
     * Gets the value of the pventGaine property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventGaine() {
        return pventGaine;
    }

    /**
     * Sets the value of the pventGaine property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventGaine(double value) {
        this.pventGaine = value;
    }

    /**
     * Gets the value of the deltaThetaEvapCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaEvapCh() {
        return deltaThetaEvapCh;
    }

    /**
     * Sets the value of the deltaThetaEvapCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaEvapCh(double value) {
        this.deltaThetaEvapCh = value;
    }

    /**
     * Gets the value of the deltaThetaCondFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaCondFr() {
        return deltaThetaCondFr;
    }

    /**
     * Sets the value of the deltaThetaCondFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaCondFr(double value) {
        this.deltaThetaCondFr = value;
    }

    /**
     * Gets the value of the ppompesTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPpompesTour() {
        return ppompesTour;
    }

    /**
     * Sets the value of the ppompesTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPpompesTour(double value) {
        this.ppompesTour = value;
    }

    /**
     * Gets the value of the pventTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPventTour() {
        return pventTour;
    }

    /**
     * Sets the value of the pventTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPventTour(double value) {
        this.pventTour = value;
    }

    /**
     * Gets the value of the deltaThetaTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaThetaTour() {
        return deltaThetaTour;
    }

    /**
     * Sets the value of the deltaThetaTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaThetaTour(double value) {
        this.deltaThetaTour = value;
    }

    /**
     * Gets the value of the thetaEsTourConsigne property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaEsTourConsigne() {
        return thetaEsTourConsigne;
    }

    /**
     * Sets the value of the thetaEsTourConsigne property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaEsTourConsigne(double value) {
        this.thetaEsTourConsigne = value;
    }

    /**
     * Gets the value of the ppompesCap property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPpompesCap() {
        return ppompesCap;
    }

    /**
     * Sets the value of the ppompesCap property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPpompesCap(double value) {
        this.ppompesCap = value;
    }

    /**
     * Gets the value of the ppompesNappe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPpompesNappe() {
        return ppompesNappe;
    }

    /**
     * Sets the value of the ppompesNappe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPpompesNappe(double value) {
        this.ppompesNappe = value;
    }

    /**
     * Gets the value of the ppompesInter property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPpompesInter() {
        return ppompesInter;
    }

    /**
     * Sets the value of the ppompesInter property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPpompesInter(double value) {
        this.ppompesInter = value;
    }

    /**
     * Gets the value of the ppompesBoucleEau property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPpompesBoucleEau() {
        return ppompesBoucleEau;
    }

    /**
     * Sets the value of the ppompesBoucleEau property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPpompesBoucleEau(double value) {
        this.ppompesBoucleEau = value;
    }

    /**
     * Gets the value of the idmoisMini property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdmoisMini() {
        return idmoisMini;
    }

    /**
     * Sets the value of the idmoisMini property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdmoisMini(int value) {
        this.idmoisMini = value;
    }

    /**
     * Gets the value of the thetaMinSource property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinSource() {
        return thetaMinSource;
    }

    /**
     * Sets the value of the thetaMinSource property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinSource(double value) {
        this.thetaMinSource = value;
    }

    /**
     * Gets the value of the thetaMaxSource property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxSource() {
        return thetaMaxSource;
    }

    /**
     * Sets the value of the thetaMaxSource property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxSource(double value) {
        this.thetaMaxSource = value;
    }

    /**
     * Gets the value of the thetaMaxRechBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxRechBE() {
        return thetaMaxRechBE;
    }

    /**
     * Sets the value of the thetaMaxRechBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxRechBE(double value) {
        this.thetaMaxRechBE = value;
    }

    /**
     * Gets the value of the thetaMinFrBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinFrBE() {
        return thetaMinFrBE;
    }

    /**
     * Sets the value of the thetaMinFrBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinFrBE(double value) {
        this.thetaMinFrBE = value;
    }

    /**
     * Gets the value of the thetaMinBoucle property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinBoucle() {
        return thetaMinBoucle;
    }

    /**
     * Sets the value of the thetaMinBoucle property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinBoucle(double value) {
        this.thetaMinBoucle = value;
    }

    /**
     * Gets the value of the thetaMaxBoucle property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxBoucle() {
        return thetaMaxBoucle;
    }

    /**
     * Sets the value of the thetaMaxBoucle property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxBoucle(double value) {
        this.thetaMaxBoucle = value;
    }

    /**
     * Gets the value of the rb property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRb() {
        return rb;
    }

    /**
     * Sets the value of the rb property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRb(double value) {
        this.rb = value;
    }

    /**
     * Gets the value of the l property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getL() {
        return l;
    }

    /**
     * Sets the value of the l property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setL(double value) {
        this.l = value;
    }

    /**
     * Gets the value of the qvNappeNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvNappeNom() {
        return qvNappeNom;
    }

    /**
     * Sets the value of the qvNappeNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvNappeNom(double value) {
        this.qvNappeNom = value;
    }

    /**
     * Gets the value of the qvInterNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvInterNom() {
        return qvInterNom;
    }

    /**
     * Sets the value of the qvInterNom property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvInterNom(double value) {
        this.qvInterNom = value;
    }

    /**
     * Gets the value of the qvNomTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvNomTour() {
        return qvNomTour;
    }

    /**
     * Sets the value of the qvNomTour property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvNomTour(double value) {
        this.qvNomTour = value;
    }

    /**
     * Gets the value of the rhoInter property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRhoInter() {
        return rhoInter;
    }

    /**
     * Sets the value of the rhoInter property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRhoInter(double value) {
        this.rhoInter = value;
    }

    /**
     * Gets the value of the ua property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getUA() {
        return ua;
    }

    /**
     * Sets the value of the ua property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setUA(double value) {
        this.ua = value;
    }

    /**
     * Gets the value of the cpeInter property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCpeInter() {
        return cpeInter;
    }

    /**
     * Sets the value of the cpeInter property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCpeInter(double value) {
        this.cpeInter = value;
    }

    /**
     * Gets the value of the idmoisMiniSol property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdmoisMiniSol() {
        return idmoisMiniSol;
    }

    /**
     * Sets the value of the idmoisMiniSol property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdmoisMiniSol(int value) {
        this.idmoisMiniSol = value;
    }

    /**
     * Gets the value of the thetaMinSol property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinSol() {
        return thetaMinSol;
    }

    /**
     * Sets the value of the thetaMinSol property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinSol(double value) {
        this.thetaMinSol = value;
    }

    /**
     * Gets the value of the thetaMaxSol property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxSol() {
        return thetaMaxSol;
    }

    /**
     * Sets the value of the thetaMaxSol property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxSol(double value) {
        this.thetaMaxSol = value;
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

}
