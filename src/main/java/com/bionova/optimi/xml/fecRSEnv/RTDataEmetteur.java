
package com.bionova.optimi.xml.fecRSEnv;

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
public class RTDataEmetteur {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Is_emetteur_chaud", required = true)
    protected String isEmetteurChaud;
    @XmlElement(name = "Is_emetteur_froid", required = true)
    protected String isEmetteurFroid;
    @XmlElement(name = "Per_dos")
    protected double perDos;
    @XmlElement(name = "Carac_Haut_Plafond", required = true)
    protected String caracHautPlafond;
    @XmlElement(name = "Typologie_Emetteur_Chaud", required = true)
    protected String typologieEmetteurChaud;
    @XmlElement(name = "Pem_conv_ch")
    protected double pemConvCh;
    @XmlElement(name = "Classe_Variation_Spatiale_Chaud", required = true)
    protected String classeVariationSpatialeChaud;
    @XmlElement(name = "Nombre_niveaux_desservis", required = true)
    protected String nombreNiveauxDesservis;
    @XmlElement(name = "Delta_Temp_vs_ch")
    protected double deltaTempVsCh;
    @XmlElement(name = "Statut_Variation_Temporelle_Chaud", required = true)
    protected String statutVariationTemporelleChaud;
    @XmlElement(name = "Couple_Regulateur_Emetteur_Chaud", required = true)
    protected String coupleRegulateurEmetteurChaud;
    @XmlElement(name = "Regulation_Poele_Ou_Insert", required = true)
    protected String regulationPoeleOuInsert;
    @XmlElement(name = "Delta_Temp_vt_ch")
    protected double deltaTempVtCh;
    @XmlElement(name = "Rat_s_ch")
    protected double ratSCh;
    @XmlElement(name = "Rat_t_ch")
    protected double ratTCh;
    @XmlElement(name = "Typologie_Emetteur_Froid", required = true)
    protected String typologieEmetteurFroid;
    @XmlElement(name = "Pem_conv_fr")
    protected double pemConvFr;
    @XmlElement(name = "Classe_Variation_Spatiale_Froid", required = true)
    protected String classeVariationSpatialeFroid;
    @XmlElement(name = "Delta_Temp_vs_fr")
    protected double deltaTempVsFr;
    @XmlElement(name = "Statut_Variation_Temporelle_Froid", required = true)
    protected String statutVariationTemporelleFroid;
    @XmlElement(name = "Couple_Regulateur_Emetteur_Froid", required = true)
    protected String coupleRegulateurEmetteurFroid;
    @XmlElement(name = "Delta_Temp_vt_fr")
    protected double deltaTempVtFr;
    @XmlElement(name = "Rat_s_fr")
    protected double ratSFr;
    @XmlElement(name = "Rat_t_fr")
    protected double ratTFr;
    @XmlElement(name = "Gest_vcv", required = true)
    protected String gestVcv;
    @XmlElement(name = "I_spv", required = true)
    protected String iSpv;
    @XmlElement(name = "P_VCV_gv")
    protected double pvcvGv;
    @XmlElement(name = "P_VCV_mv")
    protected double pvcvMv;
    @XmlElement(name = "P_VCV_pv")
    protected double pvcvPv;
    @XmlElement(name = "P_VCV_spv")
    protected double pvcvSpv;
    @XmlElement(name = "Id_Regul_Batt", required = true)
    protected String idRegulBatt;
    @XmlElement(name = "Q_v_recirc_GV")
    protected double qvRecircGV;
    @XmlElement(name = "Q_v_recirc_MV")
    protected double qvRecircMV;
    @XmlElement(name = "Q_v_recirc_PV")
    protected double qvRecircPV;
    @XmlElement(name = "Distribution_Groupe_Chaud")
    protected RTDataDistributionGroupeChaud distributionGroupeChaud;
    @XmlElement(name = "Distribution_Groupe_Froid")
    protected RTDataDistributionGroupeFroid distributionGroupeFroid;

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
     * Gets the value of the isEmetteurChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setIsEmetteurFroid(String value) {
        this.isEmetteurFroid = value;
    }

    /**
     * Gets the value of the perDos property.
     * 
     */
    public double getPerDos() {
        return perDos;
    }

    /**
     * Sets the value of the perDos property.
     * 
     */
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
    public void setTypologieEmetteurChaud(String value) {
        this.typologieEmetteurChaud = value;
    }

    /**
     * Gets the value of the pemConvCh property.
     * 
     */
    public double getPemConvCh() {
        return pemConvCh;
    }

    /**
     * Sets the value of the pemConvCh property.
     * 
     */
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
    public void setNombreNiveauxDesservis(String value) {
        this.nombreNiveauxDesservis = value;
    }

    /**
     * Gets the value of the deltaTempVsCh property.
     * 
     */
    public double getDeltaTempVsCh() {
        return deltaTempVsCh;
    }

    /**
     * Sets the value of the deltaTempVsCh property.
     * 
     */
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
    public void setRegulationPoeleOuInsert(String value) {
        this.regulationPoeleOuInsert = value;
    }

    /**
     * Gets the value of the deltaTempVtCh property.
     * 
     */
    public double getDeltaTempVtCh() {
        return deltaTempVtCh;
    }

    /**
     * Sets the value of the deltaTempVtCh property.
     * 
     */
    public void setDeltaTempVtCh(double value) {
        this.deltaTempVtCh = value;
    }

    /**
     * Gets the value of the ratSCh property.
     * 
     */
    public double getRatSCh() {
        return ratSCh;
    }

    /**
     * Sets the value of the ratSCh property.
     * 
     */
    public void setRatSCh(double value) {
        this.ratSCh = value;
    }

    /**
     * Gets the value of the ratTCh property.
     * 
     */
    public double getRatTCh() {
        return ratTCh;
    }

    /**
     * Sets the value of the ratTCh property.
     * 
     */
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
    public void setTypologieEmetteurFroid(String value) {
        this.typologieEmetteurFroid = value;
    }

    /**
     * Gets the value of the pemConvFr property.
     * 
     */
    public double getPemConvFr() {
        return pemConvFr;
    }

    /**
     * Sets the value of the pemConvFr property.
     * 
     */
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
    public void setClasseVariationSpatialeFroid(String value) {
        this.classeVariationSpatialeFroid = value;
    }

    /**
     * Gets the value of the deltaTempVsFr property.
     * 
     */
    public double getDeltaTempVsFr() {
        return deltaTempVsFr;
    }

    /**
     * Sets the value of the deltaTempVsFr property.
     * 
     */
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
    public void setCoupleRegulateurEmetteurFroid(String value) {
        this.coupleRegulateurEmetteurFroid = value;
    }

    /**
     * Gets the value of the deltaTempVtFr property.
     * 
     */
    public double getDeltaTempVtFr() {
        return deltaTempVtFr;
    }

    /**
     * Sets the value of the deltaTempVtFr property.
     * 
     */
    public void setDeltaTempVtFr(double value) {
        this.deltaTempVtFr = value;
    }

    /**
     * Gets the value of the ratSFr property.
     * 
     */
    public double getRatSFr() {
        return ratSFr;
    }

    /**
     * Sets the value of the ratSFr property.
     * 
     */
    public void setRatSFr(double value) {
        this.ratSFr = value;
    }

    /**
     * Gets the value of the ratTFr property.
     * 
     */
    public double getRatTFr() {
        return ratTFr;
    }

    /**
     * Sets the value of the ratTFr property.
     * 
     */
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
    public void setISpv(String value) {
        this.iSpv = value;
    }

    /**
     * Gets the value of the pvcvGv property.
     * 
     */
    public double getPVCVGv() {
        return pvcvGv;
    }

    /**
     * Sets the value of the pvcvGv property.
     * 
     */
    public void setPVCVGv(double value) {
        this.pvcvGv = value;
    }

    /**
     * Gets the value of the pvcvMv property.
     * 
     */
    public double getPVCVMv() {
        return pvcvMv;
    }

    /**
     * Sets the value of the pvcvMv property.
     * 
     */
    public void setPVCVMv(double value) {
        this.pvcvMv = value;
    }

    /**
     * Gets the value of the pvcvPv property.
     * 
     */
    public double getPVCVPv() {
        return pvcvPv;
    }

    /**
     * Sets the value of the pvcvPv property.
     * 
     */
    public void setPVCVPv(double value) {
        this.pvcvPv = value;
    }

    /**
     * Gets the value of the pvcvSpv property.
     * 
     */
    public double getPVCVSpv() {
        return pvcvSpv;
    }

    /**
     * Sets the value of the pvcvSpv property.
     * 
     */
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
    public void setIdRegulBatt(String value) {
        this.idRegulBatt = value;
    }

    /**
     * Gets the value of the qvRecircGV property.
     * 
     */
    public double getQVRecircGV() {
        return qvRecircGV;
    }

    /**
     * Sets the value of the qvRecircGV property.
     * 
     */
    public void setQVRecircGV(double value) {
        this.qvRecircGV = value;
    }

    /**
     * Gets the value of the qvRecircMV property.
     * 
     */
    public double getQVRecircMV() {
        return qvRecircMV;
    }

    /**
     * Sets the value of the qvRecircMV property.
     * 
     */
    public void setQVRecircMV(double value) {
        this.qvRecircMV = value;
    }

    /**
     * Gets the value of the qvRecircPV property.
     * 
     */
    public double getQVRecircPV() {
        return qvRecircPV;
    }

    /**
     * Sets the value of the qvRecircPV property.
     * 
     */
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
    public void setDistributionGroupeFroid(RTDataDistributionGroupeFroid value) {
        this.distributionGroupeFroid = value;
    }

}
