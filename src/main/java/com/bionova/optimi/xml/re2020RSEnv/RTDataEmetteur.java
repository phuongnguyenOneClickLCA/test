
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Emetteur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Emetteur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Is_emetteur_chaud" type="{}RT_Oui_Non"/>
 *         &lt;element name="Is_emetteur_froid" type="{}RT_Oui_Non"/>
 *         &lt;element name="Per_dos" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Carac_Haut_Plafond" type="{}E_Carac_Haut_Plafond"/>
 *         &lt;element name="Typologie_Emetteur_Chaud" type="{}E_Typologie_Emetteur_Ch"/>
 *         &lt;element name="Pem_conv_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Variation_Spatiale_Chaud" type="{}E_Classe_Spatiale_Ch"/>
 *         &lt;element name="Nombre_niveaux_desservis" type="{}E_Nombre_Niveaux_Desservis"/>
 *         &lt;element name="Delta_Temp_vs_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Variation_Temporelle_Chaud" type="{}E_Statut_Variation_Temporelle_Chaud"/>
 *         &lt;element name="Couple_Regulateur_Emetteur_Chaud" type="{}E_Couple_Regulateur_Emetteur"/>
 *         &lt;element name="Regulation_Poele_Ou_Insert" type="{}E_Regulation_Poele_Insert"/>
 *         &lt;element name="Delta_Temp_vt_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="detection_presence" type="{}RT_Bool_Presence"/>
 *         &lt;element name="Delta_temp_presence_Ch" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Rat_s_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_t_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typologie_Emetteur_Froid" type="{}E_Typologie_Emetteur_Fr"/>
 *         &lt;element name="Pem_conv_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Variation_Spatiale_Froid" type="{}E_Classe_Spatiale_Fr"/>
 *         &lt;element name="Delta_Temp_vs_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Variation_Temporelle_Froid" type="{}E_Statut_Variation_Temporelle_Froid"/>
 *         &lt;element name="Couple_Regulateur_Emetteur_Froid" type="{}E_Couple_Regulateur_Emetteur"/>
 *         &lt;element name="Delta_Temp_vt_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_s_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_t_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_vcv" type="{}E_Gestion_Ventilation"/>
 *         &lt;element name="I_spv" type="{}RT_Oui_Non"/>
 *         &lt;element name="P_VCV_gv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_VCV_mv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_VCV_pv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_VCV_spv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Regul_Batt" type="{}E_Regul_Batt_Refroidissement"/>
 *         &lt;element name="Q_v_recirc_GV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_v_recirc_MV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_v_recirc_PV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Distribution_Groupe_Chaud" type="{}RT_Data_Distribution_Groupe_Chaud" minOccurs="0"/>
 *         &lt;element name="Distribution_Groupe_Froid" type="{}RT_Data_Distribution_Groupe_Froid" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Emetteur", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataEmetteur {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Is_emetteur_chaud", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isEmetteurChaud;
    @XmlElement(name = "Is_emetteur_froid", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String isEmetteurFroid;
    @XmlElement(name = "Per_dos")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double perDos;
    @XmlElement(name = "Carac_Haut_Plafond", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String caracHautPlafond;
    @XmlElement(name = "Typologie_Emetteur_Chaud", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typologieEmetteurChaud;
    @XmlElement(name = "Pem_conv_ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pemConvCh;
    @XmlElement(name = "Classe_Variation_Spatiale_Chaud", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String classeVariationSpatialeChaud;
    @XmlElement(name = "Nombre_niveaux_desservis", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String nombreNiveauxDesservis;
    @XmlElement(name = "Delta_Temp_vs_ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaTempVsCh;
    @XmlElement(name = "Statut_Variation_Temporelle_Chaud", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutVariationTemporelleChaud;
    @XmlElement(name = "Couple_Regulateur_Emetteur_Chaud", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String coupleRegulateurEmetteurChaud;
    @XmlElement(name = "Regulation_Poele_Ou_Insert", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String regulationPoeleOuInsert;
    @XmlElement(name = "Delta_Temp_vt_ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaTempVtCh;
    @XmlElement(name = "detection_presence", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String detectionPresence;
    @XmlElement(name = "Delta_temp_presence_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Double deltaTempPresenceCh;
    @XmlElement(name = "Rat_s_ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratSCh;
    @XmlElement(name = "Rat_t_ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratTCh;
    @XmlElement(name = "Typologie_Emetteur_Froid", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typologieEmetteurFroid;
    @XmlElement(name = "Pem_conv_fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pemConvFr;
    @XmlElement(name = "Classe_Variation_Spatiale_Froid", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String classeVariationSpatialeFroid;
    @XmlElement(name = "Delta_Temp_vs_fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaTempVsFr;
    @XmlElement(name = "Statut_Variation_Temporelle_Froid", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutVariationTemporelleFroid;
    @XmlElement(name = "Couple_Regulateur_Emetteur_Froid", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String coupleRegulateurEmetteurFroid;
    @XmlElement(name = "Delta_Temp_vt_fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double deltaTempVtFr;
    @XmlElement(name = "Rat_s_fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratSFr;
    @XmlElement(name = "Rat_t_fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratTFr;
    @XmlElement(name = "Gest_vcv", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String gestVcv;
    @XmlElement(name = "I_spv", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String iSpv;
    @XmlElement(name = "P_VCV_gv")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pvcvGv;
    @XmlElement(name = "P_VCV_mv")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pvcvMv;
    @XmlElement(name = "P_VCV_pv")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pvcvPv;
    @XmlElement(name = "P_VCV_spv")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pvcvSpv;
    @XmlElement(name = "Id_Regul_Batt", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idRegulBatt;
    @XmlElement(name = "Q_v_recirc_GV")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRecircGV;
    @XmlElement(name = "Q_v_recirc_MV")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRecircMV;
    @XmlElement(name = "Q_v_recirc_PV")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRecircPV;
    @XmlElement(name = "Distribution_Groupe_Chaud")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataDistributionGroupeChaud distributionGroupeChaud;
    @XmlElement(name = "Distribution_Groupe_Froid")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataDistributionGroupeFroid distributionGroupeFroid;

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
     * Gets the value of the isEmetteurChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsEmetteurChaud() {
        return isEmetteurChaud;
    }

    /**
     * Sets the value of the isEmetteurChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsEmetteurChaud(String value) {
        this.isEmetteurChaud = value;
    }

    /**
     * Gets the value of the isEmetteurFroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIsEmetteurFroid() {
        return isEmetteurFroid;
    }

    /**
     * Sets the value of the isEmetteurFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIsEmetteurFroid(String value) {
        this.isEmetteurFroid = value;
    }

    /**
     * Gets the value of the perDos property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPerDos() {
        return perDos;
    }

    /**
     * Sets the value of the perDos property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPerDos(double value) {
        this.perDos = value;
    }

    /**
     * Gets the value of the caracHautPlafond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCaracHautPlafond() {
        return caracHautPlafond;
    }

    /**
     * Sets the value of the caracHautPlafond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCaracHautPlafond(String value) {
        this.caracHautPlafond = value;
    }

    /**
     * Gets the value of the typologieEmetteurChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypologieEmetteurChaud() {
        return typologieEmetteurChaud;
    }

    /**
     * Sets the value of the typologieEmetteurChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypologieEmetteurChaud(String value) {
        this.typologieEmetteurChaud = value;
    }

    /**
     * Gets the value of the pemConvCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPemConvCh() {
        return pemConvCh;
    }

    /**
     * Sets the value of the pemConvCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPemConvCh(double value) {
        this.pemConvCh = value;
    }

    /**
     * Gets the value of the classeVariationSpatialeChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getClasseVariationSpatialeChaud() {
        return classeVariationSpatialeChaud;
    }

    /**
     * Sets the value of the classeVariationSpatialeChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setClasseVariationSpatialeChaud(String value) {
        this.classeVariationSpatialeChaud = value;
    }

    /**
     * Gets the value of the nombreNiveauxDesservis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNombreNiveauxDesservis() {
        return nombreNiveauxDesservis;
    }

    /**
     * Sets the value of the nombreNiveauxDesservis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNombreNiveauxDesservis(String value) {
        this.nombreNiveauxDesservis = value;
    }

    /**
     * Gets the value of the deltaTempVsCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaTempVsCh() {
        return deltaTempVsCh;
    }

    /**
     * Sets the value of the deltaTempVsCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaTempVsCh(double value) {
        this.deltaTempVsCh = value;
    }

    /**
     * Gets the value of the statutVariationTemporelleChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutVariationTemporelleChaud() {
        return statutVariationTemporelleChaud;
    }

    /**
     * Sets the value of the statutVariationTemporelleChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutVariationTemporelleChaud(String value) {
        this.statutVariationTemporelleChaud = value;
    }

    /**
     * Gets the value of the coupleRegulateurEmetteurChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCoupleRegulateurEmetteurChaud() {
        return coupleRegulateurEmetteurChaud;
    }

    /**
     * Sets the value of the coupleRegulateurEmetteurChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCoupleRegulateurEmetteurChaud(String value) {
        this.coupleRegulateurEmetteurChaud = value;
    }

    /**
     * Gets the value of the regulationPoeleOuInsert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getRegulationPoeleOuInsert() {
        return regulationPoeleOuInsert;
    }

    /**
     * Sets the value of the regulationPoeleOuInsert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRegulationPoeleOuInsert(String value) {
        this.regulationPoeleOuInsert = value;
    }

    /**
     * Gets the value of the deltaTempVtCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaTempVtCh() {
        return deltaTempVtCh;
    }

    /**
     * Sets the value of the deltaTempVtCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaTempVtCh(double value) {
        this.deltaTempVtCh = value;
    }

    /**
     * Gets the value of the detectionPresence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getDetectionPresence() {
        return detectionPresence;
    }

    /**
     * Sets the value of the detectionPresence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDetectionPresence(String value) {
        this.detectionPresence = value;
    }

    /**
     * Gets the value of the deltaTempPresenceCh property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Double getDeltaTempPresenceCh() {
        return deltaTempPresenceCh;
    }

    /**
     * Sets the value of the deltaTempPresenceCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaTempPresenceCh(Double value) {
        this.deltaTempPresenceCh = value;
    }

    /**
     * Gets the value of the ratSCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatSCh() {
        return ratSCh;
    }

    /**
     * Sets the value of the ratSCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatSCh(double value) {
        this.ratSCh = value;
    }

    /**
     * Gets the value of the ratTCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatTCh() {
        return ratTCh;
    }

    /**
     * Sets the value of the ratTCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatTCh(double value) {
        this.ratTCh = value;
    }

    /**
     * Gets the value of the typologieEmetteurFroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypologieEmetteurFroid() {
        return typologieEmetteurFroid;
    }

    /**
     * Sets the value of the typologieEmetteurFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypologieEmetteurFroid(String value) {
        this.typologieEmetteurFroid = value;
    }

    /**
     * Gets the value of the pemConvFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPemConvFr() {
        return pemConvFr;
    }

    /**
     * Sets the value of the pemConvFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPemConvFr(double value) {
        this.pemConvFr = value;
    }

    /**
     * Gets the value of the classeVariationSpatialeFroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getClasseVariationSpatialeFroid() {
        return classeVariationSpatialeFroid;
    }

    /**
     * Sets the value of the classeVariationSpatialeFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setClasseVariationSpatialeFroid(String value) {
        this.classeVariationSpatialeFroid = value;
    }

    /**
     * Gets the value of the deltaTempVsFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaTempVsFr() {
        return deltaTempVsFr;
    }

    /**
     * Sets the value of the deltaTempVsFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaTempVsFr(double value) {
        this.deltaTempVsFr = value;
    }

    /**
     * Gets the value of the statutVariationTemporelleFroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutVariationTemporelleFroid() {
        return statutVariationTemporelleFroid;
    }

    /**
     * Sets the value of the statutVariationTemporelleFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutVariationTemporelleFroid(String value) {
        this.statutVariationTemporelleFroid = value;
    }

    /**
     * Gets the value of the coupleRegulateurEmetteurFroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCoupleRegulateurEmetteurFroid() {
        return coupleRegulateurEmetteurFroid;
    }

    /**
     * Sets the value of the coupleRegulateurEmetteurFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCoupleRegulateurEmetteurFroid(String value) {
        this.coupleRegulateurEmetteurFroid = value;
    }

    /**
     * Gets the value of the deltaTempVtFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getDeltaTempVtFr() {
        return deltaTempVtFr;
    }

    /**
     * Sets the value of the deltaTempVtFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDeltaTempVtFr(double value) {
        this.deltaTempVtFr = value;
    }

    /**
     * Gets the value of the ratSFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatSFr() {
        return ratSFr;
    }

    /**
     * Sets the value of the ratSFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatSFr(double value) {
        this.ratSFr = value;
    }

    /**
     * Gets the value of the ratTFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatTFr() {
        return ratTFr;
    }

    /**
     * Sets the value of the ratTFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatTFr(double value) {
        this.ratTFr = value;
    }

    /**
     * Gets the value of the gestVcv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getGestVcv() {
        return gestVcv;
    }

    /**
     * Sets the value of the gestVcv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGestVcv(String value) {
        this.gestVcv = value;
    }

    /**
     * Gets the value of the iSpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getISpv() {
        return iSpv;
    }

    /**
     * Sets the value of the iSpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setISpv(String value) {
        this.iSpv = value;
    }

    /**
     * Gets the value of the pvcvGv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPVCVGv() {
        return pvcvGv;
    }

    /**
     * Sets the value of the pvcvGv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPVCVGv(double value) {
        this.pvcvGv = value;
    }

    /**
     * Gets the value of the pvcvMv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPVCVMv() {
        return pvcvMv;
    }

    /**
     * Sets the value of the pvcvMv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPVCVMv(double value) {
        this.pvcvMv = value;
    }

    /**
     * Gets the value of the pvcvPv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPVCVPv() {
        return pvcvPv;
    }

    /**
     * Sets the value of the pvcvPv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPVCVPv(double value) {
        this.pvcvPv = value;
    }

    /**
     * Gets the value of the pvcvSpv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPVCVSpv() {
        return pvcvSpv;
    }

    /**
     * Sets the value of the pvcvSpv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPVCVSpv(double value) {
        this.pvcvSpv = value;
    }

    /**
     * Gets the value of the idRegulBatt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdRegulBatt() {
        return idRegulBatt;
    }

    /**
     * Sets the value of the idRegulBatt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdRegulBatt(String value) {
        this.idRegulBatt = value;
    }

    /**
     * Gets the value of the qvRecircGV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQVRecircGV() {
        return qvRecircGV;
    }

    /**
     * Sets the value of the qvRecircGV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQVRecircGV(double value) {
        this.qvRecircGV = value;
    }

    /**
     * Gets the value of the qvRecircMV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQVRecircMV() {
        return qvRecircMV;
    }

    /**
     * Sets the value of the qvRecircMV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQVRecircMV(double value) {
        this.qvRecircMV = value;
    }

    /**
     * Gets the value of the qvRecircPV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQVRecircPV() {
        return qvRecircPV;
    }

    /**
     * Sets the value of the qvRecircPV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQVRecircPV(double value) {
        this.qvRecircPV = value;
    }

    /**
     * Gets the value of the distributionGroupeChaud property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataDistributionGroupeChaud }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataDistributionGroupeChaud getDistributionGroupeChaud() {
        return distributionGroupeChaud;
    }

    /**
     * Sets the value of the distributionGroupeChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataDistributionGroupeChaud }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionGroupeChaud(RTDataDistributionGroupeChaud value) {
        this.distributionGroupeChaud = value;
    }

    /**
     * Gets the value of the distributionGroupeFroid property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataDistributionGroupeFroid }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataDistributionGroupeFroid getDistributionGroupeFroid() {
        return distributionGroupeFroid;
    }

    /**
     * Sets the value of the distributionGroupeFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataDistributionGroupeFroid }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionGroupeFroid(RTDataDistributionGroupeFroid value) {
        this.distributionGroupeFroid = value;
    }

}
