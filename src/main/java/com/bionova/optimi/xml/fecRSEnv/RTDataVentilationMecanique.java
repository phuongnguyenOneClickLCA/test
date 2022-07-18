
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * new
 * 
 * <p>Java class for RT_Data_Ventilation_Mecanique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Ventilation_Mecanique">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Type_Ventilation_Mecanique" type="{}RT_Type_Ven_Meca"/>
 *         &lt;element name="Niveau_Pression" type="{}E_Niveau_Pression"/>
 *         &lt;element name="Type_CTA_DAC" type="{}E_Type_CTA_DAC"/>
 *         &lt;element name="Q_souf_cond_max_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q_souf_cond_max_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Ventilateur" type="{}E_Type_Ventilateur_DAV"/>
 *         &lt;element name="Type_Echangeur" type="{}RT_Type_Echangeur_Ventilation"/>
 *         &lt;element name="Type_Echangeur_Detaille" type="{}RT_Type_Echangeur_Detaille"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur" type="{}RT_Certificat_Efficacite_Echangeur"/>
 *         &lt;element name="Epsilon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_elec_ech" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Antigel" type="{}RT_Oui_Non"/>
 *         &lt;element name="Statut_Regulation_Antigel" type="{}RT_Statut_Regulation_Antigel"/>
 *         &lt;element name="T_sec_h_rep_LIM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_ByPass" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_ext_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_aux_AN" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Regulation" type="{}E_Type_Regulation"/>
 *         &lt;element name="T_ENC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ENF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_T_AN" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_extref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_ChaudHR" type="{}RT_Oui_Non"/>
 *         &lt;element name="Type_Humidification" type="{}E_Type_Humidification"/>
 *         &lt;element name="W_cons" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Prechaud" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_cons_prechaud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ex_prechaud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Prefroid" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_cons_prefroid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ex_prefroid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_dim_fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Humidification_Ete" type="{}E_Type_Humidification_Ete"/>
 *         &lt;element name="Theta_i_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_i_1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_i_2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rendement_Humidificateur_Ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_base_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_base_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Puits_Hydraulique" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Detail_Echangeur_PH" type="{}RT_Detail_Echangeur_PH"/>
 *         &lt;element name="Efficacite_PH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_echPH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Origine_Horaire" type="{}E_Origine_Horaires_Ven_Hyb"/>
 *         &lt;element name="Dugd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="H_gd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="V_vent_reg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_ext_reg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Puits_Climatique" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Distribution_CTA_Chaud_Collection" type="{}ArrayOfRT_Data_Distribution_CTA_Chaud" minOccurs="0"/>
 *         &lt;element name="Distribution_CTA_Froid" type="{}RT_Data_Distribution_CTA_Froid" minOccurs="0"/>
 *         &lt;element name="Is_Rafnoc" type="{}RT_Type_Rafnoc"/>
 *         &lt;element name="Param_Rafnoc_MiSaison" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Param_Rafnoc_Ete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_rafnoc_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rafnoc_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_in_bat_gel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="T_aux_AN_min" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Type_Regulation_AN" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Pvent_souf_CH" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_rep_CH" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_souf_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_souf_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_rep_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_rep_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_modeCH_rep" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_modeCH_souf" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_modeFR_rep" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Pvent_modeFR_souf" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Ventilation_Mecanique", propOrder = {

})
public class RTDataVentilationMecanique {

    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Type_Ventilation_Mecanique", required = true)
    protected String typeVentilationMecanique;
    @XmlElement(name = "Niveau_Pression", required = true)
    protected String niveauPression;
    @XmlElement(name = "Type_CTA_DAC", required = true)
    protected String typeCTADAC;
    @XmlElement(name = "Q_souf_cond_max_occ")
    protected double qSoufCondMaxOcc;
    @XmlElement(name = "Q_souf_cond_max_inocc")
    protected double qSoufCondMaxInocc;
    @XmlElement(name = "Type_Ventilateur", required = true)
    protected String typeVentilateur;
    @XmlElement(name = "Type_Echangeur", required = true)
    protected String typeEchangeur;
    @XmlElement(name = "Type_Echangeur_Detaille", required = true)
    protected String typeEchangeurDetaille;
    @XmlElement(name = "Certificat_Efficacite_Echangeur", required = true)
    protected String certificatEfficaciteEchangeur;
    @XmlElement(name = "Epsilon")
    protected double epsilon;
    @XmlElement(name = "P_elec_ech")
    protected double pElecEch;
    @XmlElement(name = "UA")
    protected double ua;
    @XmlElement(name = "Is_Antigel", required = true)
    protected String isAntigel;
    @XmlElement(name = "Statut_Regulation_Antigel", required = true)
    protected String statutRegulationAntigel;
    @XmlElement(name = "T_sec_h_rep_LIM")
    protected double tSecHRepLIM;
    @XmlElement(name = "Is_ByPass", required = true)
    protected String isByPass;
    @XmlElement(name = "T_ext_bp_hiver")
    protected double tExtBpHiver;
    @XmlElement(name = "T_int_bp_hiver")
    protected double tIntBpHiver;
    @XmlElement(name = "T_ext_bp_ete")
    protected double tExtBpEte;
    @XmlElement(name = "T_int_bp_ete")
    protected double tIntBpEte;
    @XmlElement(name = "T_aux_AN")
    protected double tAuxAN;
    @XmlElement(name = "Type_Regulation", required = true)
    protected String typeRegulation;
    @XmlElement(name = "T_ENC")
    protected double tenc;
    @XmlElement(name = "T_ENF")
    protected double tenf;
    @XmlElement(name = "T_ext_T_AN")
    protected double tExtTAN;
    @XmlElement(name = "T_extref")
    protected double tExtref;
    @XmlElement(name = "Is_ChaudHR", required = true)
    protected String isChaudHR;
    @XmlElement(name = "Type_Humidification", required = true)
    protected String typeHumidification;
    @XmlElement(name = "W_cons")
    protected double wCons;
    @XmlElement(name = "Is_Prechaud", required = true)
    protected String isPrechaud;
    @XmlElement(name = "T_cons_prechaud")
    protected double tConsPrechaud;
    @XmlElement(name = "T_ex_prechaud")
    protected double tExPrechaud;
    @XmlElement(name = "Is_Prefroid", required = true)
    protected String isPrefroid;
    @XmlElement(name = "T_cons_prefroid")
    protected double tConsPrefroid;
    @XmlElement(name = "T_ex_prefroid")
    protected double tExPrefroid;
    @XmlElement(name = "Theta_dim_fr")
    protected double thetaDimFr;
    @XmlElement(name = "Type_Humidification_Ete", required = true)
    protected String typeHumidificationEte;
    @XmlElement(name = "Theta_i_base")
    protected double thetaIBase;
    @XmlElement(name = "Delta_Theta_i_1")
    protected double deltaThetaI1;
    @XmlElement(name = "Delta_Theta_i_2")
    protected double deltaThetaI2;
    @XmlElement(name = "Rendement_Humidificateur_Ete")
    protected double rendementHumidificateurEte;
    @XmlElement(name = "Pvent_base_souf")
    protected double pventBaseSouf;
    @XmlElement(name = "Pvent_pointe_souf")
    protected double pventPointeSouf;
    @XmlElement(name = "Pvent_base_rep")
    protected double pventBaseRep;
    @XmlElement(name = "Pvent_pointe_rep")
    protected double pventPointeRep;
    @XmlElement(name = "Pvent_souf_occ")
    protected double pventSoufOcc;
    @XmlElement(name = "Pvent_souf_inocc")
    protected double pventSoufInocc;
    @XmlElement(name = "Pvent_rep_occ")
    protected double pventRepOcc;
    @XmlElement(name = "Pvent_rep_inocc")
    protected double pventRepInocc;
    @XmlElement(name = "Pvent")
    protected String pvent;
    @XmlElement(name = "Pvent_pointe")
    protected double pventPointe;
    @XmlElement(name = "Id_Puits_Hydraulique")
    protected int idPuitsHydraulique;
    @XmlElement(name = "Detail_Echangeur_PH", required = true)
    protected String detailEchangeurPH;
    @XmlElement(name = "Efficacite_PH")
    protected double efficacitePH;
    @XmlElement(name = "UA_echPH")
    protected double uaEchPH;
    @XmlElement(name = "Origine_Horaire", required = true)
    protected String origineHoraire;
    @XmlElement(name = "Dugd")
    protected int dugd;
    @XmlElement(name = "H_gd")
    protected String hGd;
    @XmlElement(name = "V_vent_reg")
    protected String vVentReg;
    @XmlElement(name = "Theta_ext_reg")
    protected String thetaExtReg;
    @XmlElement(name = "Id_Puits_Climatique")
    protected int idPuitsClimatique;
    @XmlElement(name = "Id_Et")
    protected int idEt;
    @XmlElement(name = "Distribution_CTA_Chaud_Collection")
    protected ArrayOfRTDataDistributionCTAChaud distributionCTAChaudCollection;
    @XmlElement(name = "Distribution_CTA_Froid")
    protected RTDataDistributionCTAFroid distributionCTAFroid;
    @XmlElement(name = "Is_Rafnoc", required = true)
    protected String isRafnoc;
    @XmlElement(name = "Param_Rafnoc_MiSaison")
    protected String paramRafnocMiSaison;
    @XmlElement(name = "Param_Rafnoc_Ete")
    protected String paramRafnocEte;
    @XmlElement(name = "Pvent_rafnoc_rep")
    protected double pventRafnocRep;
    @XmlElement(name = "Pvent_rafnoc_souf")
    protected double pventRafnocSouf;
    @XmlElement(name = "T_in_bat_gel")
    protected Double tInBatGel;
    @XmlElement(name = "T_aux_AN_min")
    protected Double tAuxANMin;
    @XmlElement(name = "Type_Regulation_AN")
    protected Integer typeRegulationAN;
    @XmlElement(name = "Pvent_souf_CH")
    protected Double pventSoufCH;
    @XmlElement(name = "Pvent_rep_CH")
    protected Double pventRepCH;
    @XmlElement(name = "Pvent_souf_ZN_occ")
    protected Double pventSoufZNOcc;
    @XmlElement(name = "Pvent_souf_ZN_inocc")
    protected Double pventSoufZNInocc;
    @XmlElement(name = "Pvent_rep_ZN_occ")
    protected Double pventRepZNOcc;
    @XmlElement(name = "Pvent_rep_ZN_inocc")
    protected Double pventRepZNInocc;
    @XmlElement(name = "Pvent_modeCH_rep")
    protected Double pventModeCHRep;
    @XmlElement(name = "Pvent_modeCH_souf")
    protected Double pventModeCHSouf;
    @XmlElement(name = "Pvent_modeFR_rep")
    protected Double pventModeFRRep;
    @XmlElement(name = "Pvent_modeFR_souf")
    protected Double pventModeFRSouf;

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
     * Gets the value of the typeVentilationMecanique property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeVentilationMecanique() {
        return typeVentilationMecanique;
    }

    /**
     * Sets the value of the typeVentilationMecanique property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeVentilationMecanique(String value) {
        this.typeVentilationMecanique = value;
    }

    /**
     * Gets the value of the niveauPression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNiveauPression() {
        return niveauPression;
    }

    /**
     * Sets the value of the niveauPression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNiveauPression(String value) {
        this.niveauPression = value;
    }

    /**
     * Gets the value of the typeCTADAC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeCTADAC() {
        return typeCTADAC;
    }

    /**
     * Sets the value of the typeCTADAC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeCTADAC(String value) {
        this.typeCTADAC = value;
    }

    /**
     * Gets the value of the qSoufCondMaxOcc property.
     * 
     */
    public double getQSoufCondMaxOcc() {
        return qSoufCondMaxOcc;
    }

    /**
     * Sets the value of the qSoufCondMaxOcc property.
     * 
     */
    public void setQSoufCondMaxOcc(double value) {
        this.qSoufCondMaxOcc = value;
    }

    /**
     * Gets the value of the qSoufCondMaxInocc property.
     * 
     */
    public double getQSoufCondMaxInocc() {
        return qSoufCondMaxInocc;
    }

    /**
     * Sets the value of the qSoufCondMaxInocc property.
     * 
     */
    public void setQSoufCondMaxInocc(double value) {
        this.qSoufCondMaxInocc = value;
    }

    /**
     * Gets the value of the typeVentilateur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeVentilateur() {
        return typeVentilateur;
    }

    /**
     * Sets the value of the typeVentilateur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeVentilateur(String value) {
        this.typeVentilateur = value;
    }

    /**
     * Gets the value of the typeEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setTypeEchangeur(String value) {
        this.typeEchangeur = value;
    }

    /**
     * Gets the value of the typeEchangeurDetaille property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeEchangeurDetaille() {
        return typeEchangeurDetaille;
    }

    /**
     * Sets the value of the typeEchangeurDetaille property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeEchangeurDetaille(String value) {
        this.typeEchangeurDetaille = value;
    }

    /**
     * Gets the value of the certificatEfficaciteEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificatEfficaciteEchangeur() {
        return certificatEfficaciteEchangeur;
    }

    /**
     * Sets the value of the certificatEfficaciteEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificatEfficaciteEchangeur(String value) {
        this.certificatEfficaciteEchangeur = value;
    }

    /**
     * Gets the value of the epsilon property.
     * 
     */
    public double getEpsilon() {
        return epsilon;
    }

    /**
     * Sets the value of the epsilon property.
     * 
     */
    public void setEpsilon(double value) {
        this.epsilon = value;
    }

    /**
     * Gets the value of the pElecEch property.
     * 
     */
    public double getPElecEch() {
        return pElecEch;
    }

    /**
     * Sets the value of the pElecEch property.
     * 
     */
    public void setPElecEch(double value) {
        this.pElecEch = value;
    }

    /**
     * Gets the value of the ua property.
     * 
     */
    public double getUA() {
        return ua;
    }

    /**
     * Sets the value of the ua property.
     * 
     */
    public void setUA(double value) {
        this.ua = value;
    }

    /**
     * Gets the value of the isAntigel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAntigel() {
        return isAntigel;
    }

    /**
     * Sets the value of the isAntigel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAntigel(String value) {
        this.isAntigel = value;
    }

    /**
     * Gets the value of the statutRegulationAntigel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutRegulationAntigel() {
        return statutRegulationAntigel;
    }

    /**
     * Sets the value of the statutRegulationAntigel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutRegulationAntigel(String value) {
        this.statutRegulationAntigel = value;
    }

    /**
     * Gets the value of the tSecHRepLIM property.
     * 
     */
    public double getTSecHRepLIM() {
        return tSecHRepLIM;
    }

    /**
     * Sets the value of the tSecHRepLIM property.
     * 
     */
    public void setTSecHRepLIM(double value) {
        this.tSecHRepLIM = value;
    }

    /**
     * Gets the value of the isByPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsByPass() {
        return isByPass;
    }

    /**
     * Sets the value of the isByPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsByPass(String value) {
        this.isByPass = value;
    }

    /**
     * Gets the value of the tExtBpHiver property.
     * 
     */
    public double getTExtBpHiver() {
        return tExtBpHiver;
    }

    /**
     * Sets the value of the tExtBpHiver property.
     * 
     */
    public void setTExtBpHiver(double value) {
        this.tExtBpHiver = value;
    }

    /**
     * Gets the value of the tIntBpHiver property.
     * 
     */
    public double getTIntBpHiver() {
        return tIntBpHiver;
    }

    /**
     * Sets the value of the tIntBpHiver property.
     * 
     */
    public void setTIntBpHiver(double value) {
        this.tIntBpHiver = value;
    }

    /**
     * Gets the value of the tExtBpEte property.
     * 
     */
    public double getTExtBpEte() {
        return tExtBpEte;
    }

    /**
     * Sets the value of the tExtBpEte property.
     * 
     */
    public void setTExtBpEte(double value) {
        this.tExtBpEte = value;
    }

    /**
     * Gets the value of the tIntBpEte property.
     * 
     */
    public double getTIntBpEte() {
        return tIntBpEte;
    }

    /**
     * Sets the value of the tIntBpEte property.
     * 
     */
    public void setTIntBpEte(double value) {
        this.tIntBpEte = value;
    }

    /**
     * Gets the value of the tAuxAN property.
     * 
     */
    public double getTAuxAN() {
        return tAuxAN;
    }

    /**
     * Sets the value of the tAuxAN property.
     * 
     */
    public void setTAuxAN(double value) {
        this.tAuxAN = value;
    }

    /**
     * Gets the value of the typeRegulation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeRegulation() {
        return typeRegulation;
    }

    /**
     * Sets the value of the typeRegulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeRegulation(String value) {
        this.typeRegulation = value;
    }

    /**
     * Gets the value of the tenc property.
     * 
     */
    public double getTENC() {
        return tenc;
    }

    /**
     * Sets the value of the tenc property.
     * 
     */
    public void setTENC(double value) {
        this.tenc = value;
    }

    /**
     * Gets the value of the tenf property.
     * 
     */
    public double getTENF() {
        return tenf;
    }

    /**
     * Sets the value of the tenf property.
     * 
     */
    public void setTENF(double value) {
        this.tenf = value;
    }

    /**
     * Gets the value of the tExtTAN property.
     * 
     */
    public double getTExtTAN() {
        return tExtTAN;
    }

    /**
     * Sets the value of the tExtTAN property.
     * 
     */
    public void setTExtTAN(double value) {
        this.tExtTAN = value;
    }

    /**
     * Gets the value of the tExtref property.
     * 
     */
    public double getTExtref() {
        return tExtref;
    }

    /**
     * Sets the value of the tExtref property.
     * 
     */
    public void setTExtref(double value) {
        this.tExtref = value;
    }

    /**
     * Gets the value of the isChaudHR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsChaudHR() {
        return isChaudHR;
    }

    /**
     * Sets the value of the isChaudHR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsChaudHR(String value) {
        this.isChaudHR = value;
    }

    /**
     * Gets the value of the typeHumidification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeHumidification() {
        return typeHumidification;
    }

    /**
     * Sets the value of the typeHumidification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeHumidification(String value) {
        this.typeHumidification = value;
    }

    /**
     * Gets the value of the wCons property.
     * 
     */
    public double getWCons() {
        return wCons;
    }

    /**
     * Sets the value of the wCons property.
     * 
     */
    public void setWCons(double value) {
        this.wCons = value;
    }

    /**
     * Gets the value of the isPrechaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPrechaud() {
        return isPrechaud;
    }

    /**
     * Sets the value of the isPrechaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPrechaud(String value) {
        this.isPrechaud = value;
    }

    /**
     * Gets the value of the tConsPrechaud property.
     * 
     */
    public double getTConsPrechaud() {
        return tConsPrechaud;
    }

    /**
     * Sets the value of the tConsPrechaud property.
     * 
     */
    public void setTConsPrechaud(double value) {
        this.tConsPrechaud = value;
    }

    /**
     * Gets the value of the tExPrechaud property.
     * 
     */
    public double getTExPrechaud() {
        return tExPrechaud;
    }

    /**
     * Sets the value of the tExPrechaud property.
     * 
     */
    public void setTExPrechaud(double value) {
        this.tExPrechaud = value;
    }

    /**
     * Gets the value of the isPrefroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPrefroid() {
        return isPrefroid;
    }

    /**
     * Sets the value of the isPrefroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPrefroid(String value) {
        this.isPrefroid = value;
    }

    /**
     * Gets the value of the tConsPrefroid property.
     * 
     */
    public double getTConsPrefroid() {
        return tConsPrefroid;
    }

    /**
     * Sets the value of the tConsPrefroid property.
     * 
     */
    public void setTConsPrefroid(double value) {
        this.tConsPrefroid = value;
    }

    /**
     * Gets the value of the tExPrefroid property.
     * 
     */
    public double getTExPrefroid() {
        return tExPrefroid;
    }

    /**
     * Sets the value of the tExPrefroid property.
     * 
     */
    public void setTExPrefroid(double value) {
        this.tExPrefroid = value;
    }

    /**
     * Gets the value of the thetaDimFr property.
     * 
     */
    public double getThetaDimFr() {
        return thetaDimFr;
    }

    /**
     * Sets the value of the thetaDimFr property.
     * 
     */
    public void setThetaDimFr(double value) {
        this.thetaDimFr = value;
    }

    /**
     * Gets the value of the typeHumidificationEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeHumidificationEte() {
        return typeHumidificationEte;
    }

    /**
     * Sets the value of the typeHumidificationEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeHumidificationEte(String value) {
        this.typeHumidificationEte = value;
    }

    /**
     * Gets the value of the thetaIBase property.
     * 
     */
    public double getThetaIBase() {
        return thetaIBase;
    }

    /**
     * Sets the value of the thetaIBase property.
     * 
     */
    public void setThetaIBase(double value) {
        this.thetaIBase = value;
    }

    /**
     * Gets the value of the deltaThetaI1 property.
     * 
     */
    public double getDeltaThetaI1() {
        return deltaThetaI1;
    }

    /**
     * Sets the value of the deltaThetaI1 property.
     * 
     */
    public void setDeltaThetaI1(double value) {
        this.deltaThetaI1 = value;
    }

    /**
     * Gets the value of the deltaThetaI2 property.
     * 
     */
    public double getDeltaThetaI2() {
        return deltaThetaI2;
    }

    /**
     * Sets the value of the deltaThetaI2 property.
     * 
     */
    public void setDeltaThetaI2(double value) {
        this.deltaThetaI2 = value;
    }

    /**
     * Gets the value of the rendementHumidificateurEte property.
     * 
     */
    public double getRendementHumidificateurEte() {
        return rendementHumidificateurEte;
    }

    /**
     * Sets the value of the rendementHumidificateurEte property.
     * 
     */
    public void setRendementHumidificateurEte(double value) {
        this.rendementHumidificateurEte = value;
    }

    /**
     * Gets the value of the pventBaseSouf property.
     * 
     */
    public double getPventBaseSouf() {
        return pventBaseSouf;
    }

    /**
     * Sets the value of the pventBaseSouf property.
     * 
     */
    public void setPventBaseSouf(double value) {
        this.pventBaseSouf = value;
    }

    /**
     * Gets the value of the pventPointeSouf property.
     * 
     */
    public double getPventPointeSouf() {
        return pventPointeSouf;
    }

    /**
     * Sets the value of the pventPointeSouf property.
     * 
     */
    public void setPventPointeSouf(double value) {
        this.pventPointeSouf = value;
    }

    /**
     * Gets the value of the pventBaseRep property.
     * 
     */
    public double getPventBaseRep() {
        return pventBaseRep;
    }

    /**
     * Sets the value of the pventBaseRep property.
     * 
     */
    public void setPventBaseRep(double value) {
        this.pventBaseRep = value;
    }

    /**
     * Gets the value of the pventPointeRep property.
     * 
     */
    public double getPventPointeRep() {
        return pventPointeRep;
    }

    /**
     * Sets the value of the pventPointeRep property.
     * 
     */
    public void setPventPointeRep(double value) {
        this.pventPointeRep = value;
    }

    /**
     * Gets the value of the pventSoufOcc property.
     * 
     */
    public double getPventSoufOcc() {
        return pventSoufOcc;
    }

    /**
     * Sets the value of the pventSoufOcc property.
     * 
     */
    public void setPventSoufOcc(double value) {
        this.pventSoufOcc = value;
    }

    /**
     * Gets the value of the pventSoufInocc property.
     * 
     */
    public double getPventSoufInocc() {
        return pventSoufInocc;
    }

    /**
     * Sets the value of the pventSoufInocc property.
     * 
     */
    public void setPventSoufInocc(double value) {
        this.pventSoufInocc = value;
    }

    /**
     * Gets the value of the pventRepOcc property.
     * 
     */
    public double getPventRepOcc() {
        return pventRepOcc;
    }

    /**
     * Sets the value of the pventRepOcc property.
     * 
     */
    public void setPventRepOcc(double value) {
        this.pventRepOcc = value;
    }

    /**
     * Gets the value of the pventRepInocc property.
     * 
     */
    public double getPventRepInocc() {
        return pventRepInocc;
    }

    /**
     * Sets the value of the pventRepInocc property.
     * 
     */
    public void setPventRepInocc(double value) {
        this.pventRepInocc = value;
    }

    /**
     * Gets the value of the pvent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPvent() {
        return pvent;
    }

    /**
     * Sets the value of the pvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPvent(String value) {
        this.pvent = value;
    }

    /**
     * Gets the value of the pventPointe property.
     * 
     */
    public double getPventPointe() {
        return pventPointe;
    }

    /**
     * Sets the value of the pventPointe property.
     * 
     */
    public void setPventPointe(double value) {
        this.pventPointe = value;
    }

    /**
     * Gets the value of the idPuitsHydraulique property.
     * 
     */
    public int getIdPuitsHydraulique() {
        return idPuitsHydraulique;
    }

    /**
     * Sets the value of the idPuitsHydraulique property.
     * 
     */
    public void setIdPuitsHydraulique(int value) {
        this.idPuitsHydraulique = value;
    }

    /**
     * Gets the value of the detailEchangeurPH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailEchangeurPH() {
        return detailEchangeurPH;
    }

    /**
     * Sets the value of the detailEchangeurPH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailEchangeurPH(String value) {
        this.detailEchangeurPH = value;
    }

    /**
     * Gets the value of the efficacitePH property.
     * 
     */
    public double getEfficacitePH() {
        return efficacitePH;
    }

    /**
     * Sets the value of the efficacitePH property.
     * 
     */
    public void setEfficacitePH(double value) {
        this.efficacitePH = value;
    }

    /**
     * Gets the value of the uaEchPH property.
     * 
     */
    public double getUAEchPH() {
        return uaEchPH;
    }

    /**
     * Sets the value of the uaEchPH property.
     * 
     */
    public void setUAEchPH(double value) {
        this.uaEchPH = value;
    }

    /**
     * Gets the value of the origineHoraire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigineHoraire() {
        return origineHoraire;
    }

    /**
     * Sets the value of the origineHoraire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigineHoraire(String value) {
        this.origineHoraire = value;
    }

    /**
     * Gets the value of the dugd property.
     * 
     */
    public int getDugd() {
        return dugd;
    }

    /**
     * Sets the value of the dugd property.
     * 
     */
    public void setDugd(int value) {
        this.dugd = value;
    }

    /**
     * Gets the value of the hGd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHGd() {
        return hGd;
    }

    /**
     * Sets the value of the hGd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHGd(String value) {
        this.hGd = value;
    }

    /**
     * Gets the value of the vVentReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVVentReg() {
        return vVentReg;
    }

    /**
     * Sets the value of the vVentReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVVentReg(String value) {
        this.vVentReg = value;
    }

    /**
     * Gets the value of the thetaExtReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaExtReg() {
        return thetaExtReg;
    }

    /**
     * Sets the value of the thetaExtReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaExtReg(String value) {
        this.thetaExtReg = value;
    }

    /**
     * Gets the value of the idPuitsClimatique property.
     * 
     */
    public int getIdPuitsClimatique() {
        return idPuitsClimatique;
    }

    /**
     * Sets the value of the idPuitsClimatique property.
     * 
     */
    public void setIdPuitsClimatique(int value) {
        this.idPuitsClimatique = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    public void setIdEt(int value) {
        this.idEt = value;
    }

    /**
     * Gets the value of the distributionCTAChaudCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionCTAChaud }
     *     
     */
    public ArrayOfRTDataDistributionCTAChaud getDistributionCTAChaudCollection() {
        return distributionCTAChaudCollection;
    }

    /**
     * Sets the value of the distributionCTAChaudCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionCTAChaud }
     *     
     */
    public void setDistributionCTAChaudCollection(ArrayOfRTDataDistributionCTAChaud value) {
        this.distributionCTAChaudCollection = value;
    }

    /**
     * Gets the value of the distributionCTAFroid property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataDistributionCTAFroid }
     *     
     */
    public RTDataDistributionCTAFroid getDistributionCTAFroid() {
        return distributionCTAFroid;
    }

    /**
     * Sets the value of the distributionCTAFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataDistributionCTAFroid }
     *     
     */
    public void setDistributionCTAFroid(RTDataDistributionCTAFroid value) {
        this.distributionCTAFroid = value;
    }

    /**
     * Gets the value of the isRafnoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRafnoc() {
        return isRafnoc;
    }

    /**
     * Sets the value of the isRafnoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRafnoc(String value) {
        this.isRafnoc = value;
    }

    /**
     * Gets the value of the paramRafnocMiSaison property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamRafnocMiSaison() {
        return paramRafnocMiSaison;
    }

    /**
     * Sets the value of the paramRafnocMiSaison property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamRafnocMiSaison(String value) {
        this.paramRafnocMiSaison = value;
    }

    /**
     * Gets the value of the paramRafnocEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamRafnocEte() {
        return paramRafnocEte;
    }

    /**
     * Sets the value of the paramRafnocEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamRafnocEte(String value) {
        this.paramRafnocEte = value;
    }

    /**
     * Gets the value of the pventRafnocRep property.
     * 
     */
    public double getPventRafnocRep() {
        return pventRafnocRep;
    }

    /**
     * Sets the value of the pventRafnocRep property.
     * 
     */
    public void setPventRafnocRep(double value) {
        this.pventRafnocRep = value;
    }

    /**
     * Gets the value of the pventRafnocSouf property.
     * 
     */
    public double getPventRafnocSouf() {
        return pventRafnocSouf;
    }

    /**
     * Sets the value of the pventRafnocSouf property.
     * 
     */
    public void setPventRafnocSouf(double value) {
        this.pventRafnocSouf = value;
    }

    /**
     * Gets the value of the tInBatGel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTInBatGel() {
        return tInBatGel;
    }

    /**
     * Sets the value of the tInBatGel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTInBatGel(Double value) {
        this.tInBatGel = value;
    }

    /**
     * Gets the value of the tAuxANMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTAuxANMin() {
        return tAuxANMin;
    }

    /**
     * Sets the value of the tAuxANMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTAuxANMin(Double value) {
        this.tAuxANMin = value;
    }

    /**
     * Gets the value of the typeRegulationAN property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTypeRegulationAN() {
        return typeRegulationAN;
    }

    /**
     * Sets the value of the typeRegulationAN property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTypeRegulationAN(Integer value) {
        this.typeRegulationAN = value;
    }

    /**
     * Gets the value of the pventSoufCH property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventSoufCH() {
        return pventSoufCH;
    }

    /**
     * Sets the value of the pventSoufCH property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventSoufCH(Double value) {
        this.pventSoufCH = value;
    }

    /**
     * Gets the value of the pventRepCH property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventRepCH() {
        return pventRepCH;
    }

    /**
     * Sets the value of the pventRepCH property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventRepCH(Double value) {
        this.pventRepCH = value;
    }

    /**
     * Gets the value of the pventSoufZNOcc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventSoufZNOcc() {
        return pventSoufZNOcc;
    }

    /**
     * Sets the value of the pventSoufZNOcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventSoufZNOcc(Double value) {
        this.pventSoufZNOcc = value;
    }

    /**
     * Gets the value of the pventSoufZNInocc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventSoufZNInocc() {
        return pventSoufZNInocc;
    }

    /**
     * Sets the value of the pventSoufZNInocc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventSoufZNInocc(Double value) {
        this.pventSoufZNInocc = value;
    }

    /**
     * Gets the value of the pventRepZNOcc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventRepZNOcc() {
        return pventRepZNOcc;
    }

    /**
     * Sets the value of the pventRepZNOcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventRepZNOcc(Double value) {
        this.pventRepZNOcc = value;
    }

    /**
     * Gets the value of the pventRepZNInocc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventRepZNInocc() {
        return pventRepZNInocc;
    }

    /**
     * Sets the value of the pventRepZNInocc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventRepZNInocc(Double value) {
        this.pventRepZNInocc = value;
    }

    /**
     * Gets the value of the pventModeCHRep property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventModeCHRep() {
        return pventModeCHRep;
    }

    /**
     * Sets the value of the pventModeCHRep property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventModeCHRep(Double value) {
        this.pventModeCHRep = value;
    }

    /**
     * Gets the value of the pventModeCHSouf property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventModeCHSouf() {
        return pventModeCHSouf;
    }

    /**
     * Sets the value of the pventModeCHSouf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventModeCHSouf(Double value) {
        this.pventModeCHSouf = value;
    }

    /**
     * Gets the value of the pventModeFRRep property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventModeFRRep() {
        return pventModeFRRep;
    }

    /**
     * Sets the value of the pventModeFRRep property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventModeFRRep(Double value) {
        this.pventModeFRRep = value;
    }

    /**
     * Gets the value of the pventModeFRSouf property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPventModeFRSouf() {
        return pventModeFRSouf;
    }

    /**
     * Sets the value of the pventModeFRSouf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPventModeFRSouf(Double value) {
        this.pventModeFRSouf = value;
    }

}
