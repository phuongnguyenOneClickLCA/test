
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * moteur 6300 - T5
 * 
 * <p>Java class for T5_CSTB_VMCDF_2Fonctions_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CSTB_VMCDF_2Fonctions_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_fonction" type="{}E_Type_Fonct"/>
 *         &lt;element name="Id_mode_CH" type="{}E_Type_Mode_Boost"/>
 *         &lt;element name="Id_mode_FR" type="{}E_Type_Mode_Boost"/>
 *         &lt;element name="Pvent_base_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_base_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_modeCH_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_modeCH_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_modeFR_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_modeFR_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur" type="{}RT_Certificat_Efficacite_Echangeur"/>
 *         &lt;element name="Epsilon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Epsilon_CH_saisi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Epsilon_FR_saisi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_ByPass" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_ext_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Is_Raf_noc" type="{}RT_Type_Rafnoc"/>
 *         &lt;element name="Param_Rafnoc_MiSaison" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Param_Rafnoc_Ete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_rafnoc_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rafnoc_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrep_modeCH_dep_saisi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qsouf_modeCH_dep_saisi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrecycle_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrecycle_CH_ext" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrep_modeFR_dep_saisi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qsouf_modeFR_dep_saisi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrecycle_FR" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrecycle_FR_ext" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rrecyclage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_vc_recyclage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Etancheite_Recyclage" type="{}E_Classe_Etancheite"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CSTB_VMCDF_2Fonctions_Data", propOrder = {

})
public class T5CSTBVMCDF2FonctionsData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_fonction", required = true)
    protected String idFonction;
    @XmlElement(name = "Id_mode_CH", required = true)
    protected String idModeCH;
    @XmlElement(name = "Id_mode_FR", required = true)
    protected String idModeFR;
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
    @XmlElement(name = "Pvent_modeCH_rep")
    protected double pventModeCHRep;
    @XmlElement(name = "Pvent_modeCH_souf")
    protected double pventModeCHSouf;
    @XmlElement(name = "Pvent_modeFR_rep")
    protected double pventModeFRRep;
    @XmlElement(name = "Pvent_modeFR_souf")
    protected double pventModeFRSouf;
    @XmlElement(name = "Certificat_Efficacite_Echangeur", required = true)
    protected String certificatEfficaciteEchangeur;
    @XmlElement(name = "Epsilon")
    protected double epsilon;
    @XmlElement(name = "Certificat_Efficacite_Echangeur_Ch")
    protected int certificatEfficaciteEchangeurCh;
    @XmlElement(name = "Epsilon_CH_saisi")
    protected double epsilonCHSaisi;
    @XmlElement(name = "Certificat_Efficacite_Echangeur_Fr")
    protected int certificatEfficaciteEchangeurFr;
    @XmlElement(name = "Epsilon_FR_saisi")
    protected double epsilonFRSaisi;
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
    @XmlElement(name = "Id_Et")
    protected int idEt;
    @XmlElement(name = "Is_Raf_noc", required = true)
    protected String isRafNoc;
    @XmlElement(name = "Param_Rafnoc_MiSaison")
    protected String paramRafnocMiSaison;
    @XmlElement(name = "Param_Rafnoc_Ete")
    protected String paramRafnocEte;
    @XmlElement(name = "Pvent_rafnoc_rep")
    protected double pventRafnocRep;
    @XmlElement(name = "Pvent_rafnoc_souf")
    protected double pventRafnocSouf;
    @XmlElement(name = "Qrep_modeCH_dep_saisi")
    protected double qrepModeCHDepSaisi;
    @XmlElement(name = "Qsouf_modeCH_dep_saisi")
    protected double qsoufModeCHDepSaisi;
    @XmlElement(name = "Qrecycle_CH")
    protected double qrecycleCH;
    @XmlElement(name = "Qrecycle_CH_ext")
    protected double qrecycleCHExt;
    @XmlElement(name = "Qrep_modeFR_dep_saisi")
    protected double qrepModeFRDepSaisi;
    @XmlElement(name = "Qsouf_modeFR_dep_saisi")
    protected double qsoufModeFRDepSaisi;
    @XmlElement(name = "Qrecycle_FR")
    protected double qrecycleFR;
    @XmlElement(name = "Qrecycle_FR_ext")
    protected double qrecycleFRExt;
    @XmlElement(name = "Rrecyclage")
    protected double rrecyclage;
    @XmlElement(name = "Rat_vc_recyclage")
    protected double ratVcRecyclage;
    @XmlElement(name = "Classe_Etancheite_Recyclage", required = true)
    protected String classeEtancheiteRecyclage;

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
     * Gets the value of the idFonction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFonction() {
        return idFonction;
    }

    /**
     * Sets the value of the idFonction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFonction(String value) {
        this.idFonction = value;
    }

    /**
     * Gets the value of the idModeCH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdModeCH() {
        return idModeCH;
    }

    /**
     * Sets the value of the idModeCH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdModeCH(String value) {
        this.idModeCH = value;
    }

    /**
     * Gets the value of the idModeFR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdModeFR() {
        return idModeFR;
    }

    /**
     * Sets the value of the idModeFR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdModeFR(String value) {
        this.idModeFR = value;
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
     * Gets the value of the pventModeCHRep property.
     * 
     */
    public double getPventModeCHRep() {
        return pventModeCHRep;
    }

    /**
     * Sets the value of the pventModeCHRep property.
     * 
     */
    public void setPventModeCHRep(double value) {
        this.pventModeCHRep = value;
    }

    /**
     * Gets the value of the pventModeCHSouf property.
     * 
     */
    public double getPventModeCHSouf() {
        return pventModeCHSouf;
    }

    /**
     * Sets the value of the pventModeCHSouf property.
     * 
     */
    public void setPventModeCHSouf(double value) {
        this.pventModeCHSouf = value;
    }

    /**
     * Gets the value of the pventModeFRRep property.
     * 
     */
    public double getPventModeFRRep() {
        return pventModeFRRep;
    }

    /**
     * Sets the value of the pventModeFRRep property.
     * 
     */
    public void setPventModeFRRep(double value) {
        this.pventModeFRRep = value;
    }

    /**
     * Gets the value of the pventModeFRSouf property.
     * 
     */
    public double getPventModeFRSouf() {
        return pventModeFRSouf;
    }

    /**
     * Sets the value of the pventModeFRSouf property.
     * 
     */
    public void setPventModeFRSouf(double value) {
        this.pventModeFRSouf = value;
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
     * Gets the value of the certificatEfficaciteEchangeurCh property.
     * 
     */
    public int getCertificatEfficaciteEchangeurCh() {
        return certificatEfficaciteEchangeurCh;
    }

    /**
     * Sets the value of the certificatEfficaciteEchangeurCh property.
     * 
     */
    public void setCertificatEfficaciteEchangeurCh(int value) {
        this.certificatEfficaciteEchangeurCh = value;
    }

    /**
     * Gets the value of the epsilonCHSaisi property.
     * 
     */
    public double getEpsilonCHSaisi() {
        return epsilonCHSaisi;
    }

    /**
     * Sets the value of the epsilonCHSaisi property.
     * 
     */
    public void setEpsilonCHSaisi(double value) {
        this.epsilonCHSaisi = value;
    }

    /**
     * Gets the value of the certificatEfficaciteEchangeurFr property.
     * 
     */
    public int getCertificatEfficaciteEchangeurFr() {
        return certificatEfficaciteEchangeurFr;
    }

    /**
     * Sets the value of the certificatEfficaciteEchangeurFr property.
     * 
     */
    public void setCertificatEfficaciteEchangeurFr(int value) {
        this.certificatEfficaciteEchangeurFr = value;
    }

    /**
     * Gets the value of the epsilonFRSaisi property.
     * 
     */
    public double getEpsilonFRSaisi() {
        return epsilonFRSaisi;
    }

    /**
     * Sets the value of the epsilonFRSaisi property.
     * 
     */
    public void setEpsilonFRSaisi(double value) {
        this.epsilonFRSaisi = value;
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
     * Gets the value of the isRafNoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRafNoc() {
        return isRafNoc;
    }

    /**
     * Sets the value of the isRafNoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRafNoc(String value) {
        this.isRafNoc = value;
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
     * Gets the value of the qrepModeCHDepSaisi property.
     * 
     */
    public double getQrepModeCHDepSaisi() {
        return qrepModeCHDepSaisi;
    }

    /**
     * Sets the value of the qrepModeCHDepSaisi property.
     * 
     */
    public void setQrepModeCHDepSaisi(double value) {
        this.qrepModeCHDepSaisi = value;
    }

    /**
     * Gets the value of the qsoufModeCHDepSaisi property.
     * 
     */
    public double getQsoufModeCHDepSaisi() {
        return qsoufModeCHDepSaisi;
    }

    /**
     * Sets the value of the qsoufModeCHDepSaisi property.
     * 
     */
    public void setQsoufModeCHDepSaisi(double value) {
        this.qsoufModeCHDepSaisi = value;
    }

    /**
     * Gets the value of the qrecycleCH property.
     * 
     */
    public double getQrecycleCH() {
        return qrecycleCH;
    }

    /**
     * Sets the value of the qrecycleCH property.
     * 
     */
    public void setQrecycleCH(double value) {
        this.qrecycleCH = value;
    }

    /**
     * Gets the value of the qrecycleCHExt property.
     * 
     */
    public double getQrecycleCHExt() {
        return qrecycleCHExt;
    }

    /**
     * Sets the value of the qrecycleCHExt property.
     * 
     */
    public void setQrecycleCHExt(double value) {
        this.qrecycleCHExt = value;
    }

    /**
     * Gets the value of the qrepModeFRDepSaisi property.
     * 
     */
    public double getQrepModeFRDepSaisi() {
        return qrepModeFRDepSaisi;
    }

    /**
     * Sets the value of the qrepModeFRDepSaisi property.
     * 
     */
    public void setQrepModeFRDepSaisi(double value) {
        this.qrepModeFRDepSaisi = value;
    }

    /**
     * Gets the value of the qsoufModeFRDepSaisi property.
     * 
     */
    public double getQsoufModeFRDepSaisi() {
        return qsoufModeFRDepSaisi;
    }

    /**
     * Sets the value of the qsoufModeFRDepSaisi property.
     * 
     */
    public void setQsoufModeFRDepSaisi(double value) {
        this.qsoufModeFRDepSaisi = value;
    }

    /**
     * Gets the value of the qrecycleFR property.
     * 
     */
    public double getQrecycleFR() {
        return qrecycleFR;
    }

    /**
     * Sets the value of the qrecycleFR property.
     * 
     */
    public void setQrecycleFR(double value) {
        this.qrecycleFR = value;
    }

    /**
     * Gets the value of the qrecycleFRExt property.
     * 
     */
    public double getQrecycleFRExt() {
        return qrecycleFRExt;
    }

    /**
     * Sets the value of the qrecycleFRExt property.
     * 
     */
    public void setQrecycleFRExt(double value) {
        this.qrecycleFRExt = value;
    }

    /**
     * Gets the value of the rrecyclage property.
     * 
     */
    public double getRrecyclage() {
        return rrecyclage;
    }

    /**
     * Sets the value of the rrecyclage property.
     * 
     */
    public void setRrecyclage(double value) {
        this.rrecyclage = value;
    }

    /**
     * Gets the value of the ratVcRecyclage property.
     * 
     */
    public double getRatVcRecyclage() {
        return ratVcRecyclage;
    }

    /**
     * Sets the value of the ratVcRecyclage property.
     * 
     */
    public void setRatVcRecyclage(double value) {
        this.ratVcRecyclage = value;
    }

    /**
     * Gets the value of the classeEtancheiteRecyclage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClasseEtancheiteRecyclage() {
        return classeEtancheiteRecyclage;
    }

    /**
     * Sets the value of the classeEtancheiteRecyclage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClasseEtancheiteRecyclage(String value) {
        this.classeEtancheiteRecyclage = value;
    }

}
