
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_Engie_UAT_RA
 * 
 * <p>Java class for T5_Engie_UAT_RA_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Engie_UAT_RA_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Type_UT_DF" type="{}E_Type_UT_DF"/>
 *         &lt;element name="Id_Regulation_Debit" type="{}E_Type_Regulation_Debit"/>
 *         &lt;element name="T_sou_nom_chaud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_sou_nom_froid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_regul_AN" type="{}E_Type_Regulation"/>
 *         &lt;element name="T_ENC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ENF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_souf_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rep_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_adiab" type="{}E_Type_Rafraichissement_Adiab"/>
 *         &lt;element name="N_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_i_Base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_i1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_i2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Echangeur" type="{}RT_Type_Echangeur_Ventilation"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur" type="{}RT_Certificat_Efficacite_Echangeur"/>
 *         &lt;element name="Epsilon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Echangeur_Detaille" type="{}RT_Type_Echangeur_Detaille"/>
 *         &lt;element name="Is_ByPass" type="{}RT_Oui_Non"/>
 *         &lt;element name="Pelec_ech" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Antigel" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_s_ech_rep_LIM" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Engie_UAT_RA_Data", propOrder = {

})
public class T5EngieUATRAData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_Type_UT_DF", required = true)
    protected String idTypeUTDF;
    @XmlElement(name = "Id_Regulation_Debit", required = true)
    protected String idRegulationDebit;
    @XmlElement(name = "T_sou_nom_chaud")
    protected double tSouNomChaud;
    @XmlElement(name = "T_sou_nom_froid")
    protected double tSouNomFroid;
    @XmlElement(name = "Type_regul_AN", required = true)
    protected String typeRegulAN;
    @XmlElement(name = "T_ENC")
    protected double tenc;
    @XmlElement(name = "T_ENF")
    protected double tenf;
    @XmlElement(name = "Pvent_souf_CH")
    protected double pventSoufCH;
    @XmlElement(name = "Pvent_rep_CH")
    protected double pventRepCH;
    @XmlElement(name = "Pvent_souf_ZN_occ")
    protected double pventSoufZNOcc;
    @XmlElement(name = "Pvent_souf_ZN_inocc")
    protected double pventSoufZNInocc;
    @XmlElement(name = "Pvent_rep_ZN_occ")
    protected double pventRepZNOcc;
    @XmlElement(name = "Pvent_rep_ZN_inocc")
    protected double pventRepZNInocc;
    @XmlElement(name = "Type_adiab", required = true)
    protected String typeAdiab;
    @XmlElement(name = "N_nom")
    protected double nNom;
    @XmlElement(name = "Theta_i_Base")
    protected double thetaIBase;
    @XmlElement(name = "Delta_Theta_i1")
    protected double deltaThetaI1;
    @XmlElement(name = "Delta_Theta_i2")
    protected double deltaThetaI2;
    @XmlElement(name = "Type_Echangeur", required = true)
    protected String typeEchangeur;
    @XmlElement(name = "Certificat_Efficacite_Echangeur", required = true)
    protected String certificatEfficaciteEchangeur;
    @XmlElement(name = "Epsilon")
    protected double epsilon;
    @XmlElement(name = "UA")
    protected double ua;
    @XmlElement(name = "Type_Echangeur_Detaille", required = true)
    protected String typeEchangeurDetaille;
    @XmlElement(name = "Is_ByPass", required = true)
    protected String isByPass;
    @XmlElement(name = "Pelec_ech")
    protected double pelecEch;
    @XmlElement(name = "Is_Antigel", required = true)
    protected String isAntigel;
    @XmlElement(name = "T_s_ech_rep_LIM")
    protected double tsEchRepLIM;
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
     * Gets the value of the idTypeUTDF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTypeUTDF() {
        return idTypeUTDF;
    }

    /**
     * Sets the value of the idTypeUTDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTypeUTDF(String value) {
        this.idTypeUTDF = value;
    }

    /**
     * Gets the value of the idRegulationDebit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRegulationDebit() {
        return idRegulationDebit;
    }

    /**
     * Sets the value of the idRegulationDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRegulationDebit(String value) {
        this.idRegulationDebit = value;
    }

    /**
     * Gets the value of the tSouNomChaud property.
     * 
     */
    public double getTSouNomChaud() {
        return tSouNomChaud;
    }

    /**
     * Sets the value of the tSouNomChaud property.
     * 
     */
    public void setTSouNomChaud(double value) {
        this.tSouNomChaud = value;
    }

    /**
     * Gets the value of the tSouNomFroid property.
     * 
     */
    public double getTSouNomFroid() {
        return tSouNomFroid;
    }

    /**
     * Sets the value of the tSouNomFroid property.
     * 
     */
    public void setTSouNomFroid(double value) {
        this.tSouNomFroid = value;
    }

    /**
     * Gets the value of the typeRegulAN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeRegulAN() {
        return typeRegulAN;
    }

    /**
     * Sets the value of the typeRegulAN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeRegulAN(String value) {
        this.typeRegulAN = value;
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
     * Gets the value of the pventSoufCH property.
     * 
     */
    public double getPventSoufCH() {
        return pventSoufCH;
    }

    /**
     * Sets the value of the pventSoufCH property.
     * 
     */
    public void setPventSoufCH(double value) {
        this.pventSoufCH = value;
    }

    /**
     * Gets the value of the pventRepCH property.
     * 
     */
    public double getPventRepCH() {
        return pventRepCH;
    }

    /**
     * Sets the value of the pventRepCH property.
     * 
     */
    public void setPventRepCH(double value) {
        this.pventRepCH = value;
    }

    /**
     * Gets the value of the pventSoufZNOcc property.
     * 
     */
    public double getPventSoufZNOcc() {
        return pventSoufZNOcc;
    }

    /**
     * Sets the value of the pventSoufZNOcc property.
     * 
     */
    public void setPventSoufZNOcc(double value) {
        this.pventSoufZNOcc = value;
    }

    /**
     * Gets the value of the pventSoufZNInocc property.
     * 
     */
    public double getPventSoufZNInocc() {
        return pventSoufZNInocc;
    }

    /**
     * Sets the value of the pventSoufZNInocc property.
     * 
     */
    public void setPventSoufZNInocc(double value) {
        this.pventSoufZNInocc = value;
    }

    /**
     * Gets the value of the pventRepZNOcc property.
     * 
     */
    public double getPventRepZNOcc() {
        return pventRepZNOcc;
    }

    /**
     * Sets the value of the pventRepZNOcc property.
     * 
     */
    public void setPventRepZNOcc(double value) {
        this.pventRepZNOcc = value;
    }

    /**
     * Gets the value of the pventRepZNInocc property.
     * 
     */
    public double getPventRepZNInocc() {
        return pventRepZNInocc;
    }

    /**
     * Sets the value of the pventRepZNInocc property.
     * 
     */
    public void setPventRepZNInocc(double value) {
        this.pventRepZNInocc = value;
    }

    /**
     * Gets the value of the typeAdiab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeAdiab() {
        return typeAdiab;
    }

    /**
     * Sets the value of the typeAdiab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeAdiab(String value) {
        this.typeAdiab = value;
    }

    /**
     * Gets the value of the nNom property.
     * 
     */
    public double getNNom() {
        return nNom;
    }

    /**
     * Sets the value of the nNom property.
     * 
     */
    public void setNNom(double value) {
        this.nNom = value;
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
     * Gets the value of the pelecEch property.
     * 
     */
    public double getPelecEch() {
        return pelecEch;
    }

    /**
     * Sets the value of the pelecEch property.
     * 
     */
    public void setPelecEch(double value) {
        this.pelecEch = value;
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
     * Gets the value of the tsEchRepLIM property.
     * 
     */
    public double getTSEchRepLIM() {
        return tsEchRepLIM;
    }

    /**
     * Sets the value of the tsEchRepLIM property.
     * 
     */
    public void setTSEchRepLIM(double value) {
        this.tsEchRepLIM = value;
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

}
