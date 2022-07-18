
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Baie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Baie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ab" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Choix_PM_GPM" type="{}E_Choix_PM_GPM"/>
 *         &lt;element name="Uap_Vert" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Usp_Vert" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Uap_Horiz" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Usp_Horiz" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw1_sp_c" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw2_sp_c" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw3_sp_c" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw1_sp_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw2_sp_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw3_sp_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tli_sp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tlid_sp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Prot_Ext" type="{}E_Position_Protection"/>
 *         &lt;element name="Sw1_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw2_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw3_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tli_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tlid_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="M_suntracking_Sw_BC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_suntracking_Sw_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_suntracking_Tl_BCE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_fixe_Sw_BC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_fixe_Sw_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_fixe_Tl_BC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_fixe_Tl_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Horl" type="{}E_Type_Horl"/>
 *         &lt;element name="M_Horl_Jour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Detec_Pres" type="{}RT_Oui_Non"/>
 *         &lt;element name="Vvent_Lim_Auto" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eclim_Auto" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Temp_Op_Limh_Auto" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Temp_Op_Limb_Auto" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="M_Rprot1_Auto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="M_Rprot1_Auto_SV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Loc_Occ_Pass" type="{}RT_Oui_Non"/>
 *         &lt;element name="PM2" type="{}RT_Oui_Non"/>
 *         &lt;element name="Baie_ouvrable" type="{}E_Ouvrable"/>
 *         &lt;element name="Exp_BR" type="{}E_Niveau_Exposition_Bruit"/>
 *         &lt;element name="Valeur_Saisie_Defaut_Rouvmax" type="{}RT2012.Entree.Bat.E_Valeur_Saisie_Defaut"/>
 *         &lt;element name="Type_Ouvrant" type="{}E_Type_Ouvrant"/>
 *         &lt;element name="Rouv_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Declaree_Defaut_Ouverture_Baie_Auto" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Mode_Gestion_Hiver" type="{}E_Type_Gestion_Ouverture_Baie"/>
 *         &lt;element name="Param_OuvBaie_Hiver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mode_Gestion_MiSaison" type="{}E_Type_Gestion_Ouverture_Baie"/>
 *         &lt;element name="Param_OuvBaie_MiSaison" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mode_Gestion_Ete" type="{}E_Type_Gestion_Ouverture_Baie"/>
 *         &lt;element name="Param_OuvBaie_Ete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mode_Gestion_E" type="{}E_Type_Gestion_Ouverture_Baie"/>
 *         &lt;element name="Param_OuvBaie_E" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Masque_Collection" type="{}ArrayOfChoice6" minOccurs="0"/>
 *         &lt;element name="Typo_Permea_PM" type="{}E_Typologie_PM"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Baie", propOrder = {

})
public class RTDataBaie {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "Ab")
    protected double ab;
    @XmlElement(name = "Choix_PM_GPM", required = true)
    protected String choixPMGPM;
    @XmlElement(name = "Uap_Vert")
    protected double uapVert;
    @XmlElement(name = "Usp_Vert")
    protected double uspVert;
    @XmlElement(name = "Uap_Horiz")
    protected double uapHoriz;
    @XmlElement(name = "Usp_Horiz")
    protected double uspHoriz;
    @XmlElement(name = "Sw1_sp_c")
    protected double sw1SpC;
    @XmlElement(name = "Sw2_sp_c")
    protected double sw2SpC;
    @XmlElement(name = "Sw3_sp_c")
    protected double sw3SpC;
    @XmlElement(name = "Sw1_sp_e")
    protected double sw1SpE;
    @XmlElement(name = "Sw2_sp_e")
    protected double sw2SpE;
    @XmlElement(name = "Sw3_sp_e")
    protected double sw3SpE;
    @XmlElement(name = "Tli_sp")
    protected double tliSp;
    @XmlElement(name = "Tlid_sp")
    protected double tlidSp;
    @XmlElement(name = "Id_Et", defaultValue = "0")
    protected int idEt;
    @XmlElement(name = "Prot_Ext", required = true)
    protected String protExt;
    @XmlElement(name = "Sw1_ap")
    protected double sw1Ap;
    @XmlElement(name = "Sw2_ap")
    protected double sw2Ap;
    @XmlElement(name = "Sw3_ap")
    protected double sw3Ap;
    @XmlElement(name = "Tli_ap")
    protected double tliAp;
    @XmlElement(name = "Tlid_ap")
    protected double tlidAp;
    @XmlElement(name = "M_suntracking_Sw_BC")
    protected String mSuntrackingSwBC;
    @XmlElement(name = "M_suntracking_Sw_E")
    protected String mSuntrackingSwE;
    @XmlElement(name = "M_suntracking_Tl_BCE")
    protected String mSuntrackingTlBCE;
    @XmlElement(name = "M_fixe_Sw_BC")
    protected String mFixeSwBC;
    @XmlElement(name = "M_fixe_Sw_E")
    protected String mFixeSwE;
    @XmlElement(name = "M_fixe_Tl_BC")
    protected String mFixeTlBC;
    @XmlElement(name = "M_fixe_Tl_E")
    protected String mFixeTlE;
    @XmlElement(name = "Type_Horl", required = true)
    protected String typeHorl;
    @XmlElement(name = "M_Horl_Jour")
    protected String mHorlJour;
    @XmlElement(name = "Detec_Pres", required = true)
    protected String detecPres;
    @XmlElement(name = "Vvent_Lim_Auto")
    protected double vventLimAuto;
    @XmlElement(name = "Eclim_Auto")
    protected double eclimAuto;
    @XmlElement(name = "Temp_Op_Limh_Auto")
    protected double tempOpLimhAuto;
    @XmlElement(name = "Temp_Op_Limb_Auto")
    protected double tempOpLimbAuto;
    @XmlElement(name = "M_Rprot1_Auto")
    protected String mRprot1Auto;
    @XmlElement(name = "M_Rprot1_Auto_SV")
    protected String mRprot1AutoSV;
    @XmlElement(name = "Loc_Occ_Pass", required = true)
    protected String locOccPass;
    @XmlElement(name = "PM2", required = true)
    protected String pm2;
    @XmlElement(name = "Baie_ouvrable", required = true)
    protected String baieOuvrable;
    @XmlElement(name = "Exp_BR", required = true)
    protected String expBR;
    @XmlElement(name = "Valeur_Saisie_Defaut_Rouvmax", required = true)
    protected String valeurSaisieDefautRouvmax;
    @XmlElement(name = "Type_Ouvrant", required = true)
    protected String typeOuvrant;
    @XmlElement(name = "Rouv_Max")
    protected double rouvMax;
    @XmlElement(name = "Valeur_Declaree_Defaut_Ouverture_Baie_Auto", required = true)
    protected String valeurDeclareeDefautOuvertureBaieAuto;
    @XmlElement(name = "Mode_Gestion_Hiver", required = true)
    protected String modeGestionHiver;
    @XmlElement(name = "Param_OuvBaie_Hiver")
    protected String paramOuvBaieHiver;
    @XmlElement(name = "Mode_Gestion_MiSaison", required = true)
    protected String modeGestionMiSaison;
    @XmlElement(name = "Param_OuvBaie_MiSaison")
    protected String paramOuvBaieMiSaison;
    @XmlElement(name = "Mode_Gestion_Ete", required = true)
    protected String modeGestionEte;
    @XmlElement(name = "Param_OuvBaie_Ete")
    protected String paramOuvBaieEte;
    @XmlElement(name = "Mode_Gestion_E", required = true)
    protected String modeGestionE;
    @XmlElement(name = "Param_OuvBaie_E")
    protected String paramOuvBaieE;
    @XmlElement(name = "Masque_Collection")
    protected ArrayOfChoice6 masqueCollection;
    @XmlElement(name = "Typo_Permea_PM", required = true)
    protected String typoPermeaPM;

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
     * Gets the value of the alpha property.
     * 
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     */
    public void setAlpha(double value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     */
    public void setBeta(double value) {
        this.beta = value;
    }

    /**
     * Gets the value of the ab property.
     * 
     */
    public double getAb() {
        return ab;
    }

    /**
     * Sets the value of the ab property.
     * 
     */
    public void setAb(double value) {
        this.ab = value;
    }

    /**
     * Gets the value of the choixPMGPM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChoixPMGPM() {
        return choixPMGPM;
    }

    /**
     * Sets the value of the choixPMGPM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChoixPMGPM(String value) {
        this.choixPMGPM = value;
    }

    /**
     * Gets the value of the uapVert property.
     * 
     */
    public double getUapVert() {
        return uapVert;
    }

    /**
     * Sets the value of the uapVert property.
     * 
     */
    public void setUapVert(double value) {
        this.uapVert = value;
    }

    /**
     * Gets the value of the uspVert property.
     * 
     */
    public double getUspVert() {
        return uspVert;
    }

    /**
     * Sets the value of the uspVert property.
     * 
     */
    public void setUspVert(double value) {
        this.uspVert = value;
    }

    /**
     * Gets the value of the uapHoriz property.
     * 
     */
    public double getUapHoriz() {
        return uapHoriz;
    }

    /**
     * Sets the value of the uapHoriz property.
     * 
     */
    public void setUapHoriz(double value) {
        this.uapHoriz = value;
    }

    /**
     * Gets the value of the uspHoriz property.
     * 
     */
    public double getUspHoriz() {
        return uspHoriz;
    }

    /**
     * Sets the value of the uspHoriz property.
     * 
     */
    public void setUspHoriz(double value) {
        this.uspHoriz = value;
    }

    /**
     * Gets the value of the sw1SpC property.
     * 
     */
    public double getSw1SpC() {
        return sw1SpC;
    }

    /**
     * Sets the value of the sw1SpC property.
     * 
     */
    public void setSw1SpC(double value) {
        this.sw1SpC = value;
    }

    /**
     * Gets the value of the sw2SpC property.
     * 
     */
    public double getSw2SpC() {
        return sw2SpC;
    }

    /**
     * Sets the value of the sw2SpC property.
     * 
     */
    public void setSw2SpC(double value) {
        this.sw2SpC = value;
    }

    /**
     * Gets the value of the sw3SpC property.
     * 
     */
    public double getSw3SpC() {
        return sw3SpC;
    }

    /**
     * Sets the value of the sw3SpC property.
     * 
     */
    public void setSw3SpC(double value) {
        this.sw3SpC = value;
    }

    /**
     * Gets the value of the sw1SpE property.
     * 
     */
    public double getSw1SpE() {
        return sw1SpE;
    }

    /**
     * Sets the value of the sw1SpE property.
     * 
     */
    public void setSw1SpE(double value) {
        this.sw1SpE = value;
    }

    /**
     * Gets the value of the sw2SpE property.
     * 
     */
    public double getSw2SpE() {
        return sw2SpE;
    }

    /**
     * Sets the value of the sw2SpE property.
     * 
     */
    public void setSw2SpE(double value) {
        this.sw2SpE = value;
    }

    /**
     * Gets the value of the sw3SpE property.
     * 
     */
    public double getSw3SpE() {
        return sw3SpE;
    }

    /**
     * Sets the value of the sw3SpE property.
     * 
     */
    public void setSw3SpE(double value) {
        this.sw3SpE = value;
    }

    /**
     * Gets the value of the tliSp property.
     * 
     */
    public double getTliSp() {
        return tliSp;
    }

    /**
     * Sets the value of the tliSp property.
     * 
     */
    public void setTliSp(double value) {
        this.tliSp = value;
    }

    /**
     * Gets the value of the tlidSp property.
     * 
     */
    public double getTlidSp() {
        return tlidSp;
    }

    /**
     * Sets the value of the tlidSp property.
     * 
     */
    public void setTlidSp(double value) {
        this.tlidSp = value;
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
     * Gets the value of the protExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtExt() {
        return protExt;
    }

    /**
     * Sets the value of the protExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtExt(String value) {
        this.protExt = value;
    }

    /**
     * Gets the value of the sw1Ap property.
     * 
     */
    public double getSw1Ap() {
        return sw1Ap;
    }

    /**
     * Sets the value of the sw1Ap property.
     * 
     */
    public void setSw1Ap(double value) {
        this.sw1Ap = value;
    }

    /**
     * Gets the value of the sw2Ap property.
     * 
     */
    public double getSw2Ap() {
        return sw2Ap;
    }

    /**
     * Sets the value of the sw2Ap property.
     * 
     */
    public void setSw2Ap(double value) {
        this.sw2Ap = value;
    }

    /**
     * Gets the value of the sw3Ap property.
     * 
     */
    public double getSw3Ap() {
        return sw3Ap;
    }

    /**
     * Sets the value of the sw3Ap property.
     * 
     */
    public void setSw3Ap(double value) {
        this.sw3Ap = value;
    }

    /**
     * Gets the value of the tliAp property.
     * 
     */
    public double getTliAp() {
        return tliAp;
    }

    /**
     * Sets the value of the tliAp property.
     * 
     */
    public void setTliAp(double value) {
        this.tliAp = value;
    }

    /**
     * Gets the value of the tlidAp property.
     * 
     */
    public double getTlidAp() {
        return tlidAp;
    }

    /**
     * Sets the value of the tlidAp property.
     * 
     */
    public void setTlidAp(double value) {
        this.tlidAp = value;
    }

    /**
     * Gets the value of the mSuntrackingSwBC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSuntrackingSwBC() {
        return mSuntrackingSwBC;
    }

    /**
     * Sets the value of the mSuntrackingSwBC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSuntrackingSwBC(String value) {
        this.mSuntrackingSwBC = value;
    }

    /**
     * Gets the value of the mSuntrackingSwE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSuntrackingSwE() {
        return mSuntrackingSwE;
    }

    /**
     * Sets the value of the mSuntrackingSwE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSuntrackingSwE(String value) {
        this.mSuntrackingSwE = value;
    }

    /**
     * Gets the value of the mSuntrackingTlBCE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSuntrackingTlBCE() {
        return mSuntrackingTlBCE;
    }

    /**
     * Sets the value of the mSuntrackingTlBCE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSuntrackingTlBCE(String value) {
        this.mSuntrackingTlBCE = value;
    }

    /**
     * Gets the value of the mFixeSwBC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMFixeSwBC() {
        return mFixeSwBC;
    }

    /**
     * Sets the value of the mFixeSwBC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMFixeSwBC(String value) {
        this.mFixeSwBC = value;
    }

    /**
     * Gets the value of the mFixeSwE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMFixeSwE() {
        return mFixeSwE;
    }

    /**
     * Sets the value of the mFixeSwE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMFixeSwE(String value) {
        this.mFixeSwE = value;
    }

    /**
     * Gets the value of the mFixeTlBC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMFixeTlBC() {
        return mFixeTlBC;
    }

    /**
     * Sets the value of the mFixeTlBC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMFixeTlBC(String value) {
        this.mFixeTlBC = value;
    }

    /**
     * Gets the value of the mFixeTlE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMFixeTlE() {
        return mFixeTlE;
    }

    /**
     * Sets the value of the mFixeTlE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMFixeTlE(String value) {
        this.mFixeTlE = value;
    }

    /**
     * Gets the value of the typeHorl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeHorl() {
        return typeHorl;
    }

    /**
     * Sets the value of the typeHorl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeHorl(String value) {
        this.typeHorl = value;
    }

    /**
     * Gets the value of the mHorlJour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMHorlJour() {
        return mHorlJour;
    }

    /**
     * Sets the value of the mHorlJour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMHorlJour(String value) {
        this.mHorlJour = value;
    }

    /**
     * Gets the value of the detecPres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetecPres() {
        return detecPres;
    }

    /**
     * Sets the value of the detecPres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetecPres(String value) {
        this.detecPres = value;
    }

    /**
     * Gets the value of the vventLimAuto property.
     * 
     */
    public double getVventLimAuto() {
        return vventLimAuto;
    }

    /**
     * Sets the value of the vventLimAuto property.
     * 
     */
    public void setVventLimAuto(double value) {
        this.vventLimAuto = value;
    }

    /**
     * Gets the value of the eclimAuto property.
     * 
     */
    public double getEclimAuto() {
        return eclimAuto;
    }

    /**
     * Sets the value of the eclimAuto property.
     * 
     */
    public void setEclimAuto(double value) {
        this.eclimAuto = value;
    }

    /**
     * Gets the value of the tempOpLimhAuto property.
     * 
     */
    public double getTempOpLimhAuto() {
        return tempOpLimhAuto;
    }

    /**
     * Sets the value of the tempOpLimhAuto property.
     * 
     */
    public void setTempOpLimhAuto(double value) {
        this.tempOpLimhAuto = value;
    }

    /**
     * Gets the value of the tempOpLimbAuto property.
     * 
     */
    public double getTempOpLimbAuto() {
        return tempOpLimbAuto;
    }

    /**
     * Sets the value of the tempOpLimbAuto property.
     * 
     */
    public void setTempOpLimbAuto(double value) {
        this.tempOpLimbAuto = value;
    }

    /**
     * Gets the value of the mRprot1Auto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMRprot1Auto() {
        return mRprot1Auto;
    }

    /**
     * Sets the value of the mRprot1Auto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMRprot1Auto(String value) {
        this.mRprot1Auto = value;
    }

    /**
     * Gets the value of the mRprot1AutoSV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMRprot1AutoSV() {
        return mRprot1AutoSV;
    }

    /**
     * Sets the value of the mRprot1AutoSV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMRprot1AutoSV(String value) {
        this.mRprot1AutoSV = value;
    }

    /**
     * Gets the value of the locOccPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocOccPass() {
        return locOccPass;
    }

    /**
     * Sets the value of the locOccPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocOccPass(String value) {
        this.locOccPass = value;
    }

    /**
     * Gets the value of the pm2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPM2() {
        return pm2;
    }

    /**
     * Sets the value of the pm2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPM2(String value) {
        this.pm2 = value;
    }

    /**
     * Gets the value of the baieOuvrable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaieOuvrable() {
        return baieOuvrable;
    }

    /**
     * Sets the value of the baieOuvrable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaieOuvrable(String value) {
        this.baieOuvrable = value;
    }

    /**
     * Gets the value of the expBR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpBR() {
        return expBR;
    }

    /**
     * Sets the value of the expBR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpBR(String value) {
        this.expBR = value;
    }

    /**
     * Gets the value of the valeurSaisieDefautRouvmax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurSaisieDefautRouvmax() {
        return valeurSaisieDefautRouvmax;
    }

    /**
     * Sets the value of the valeurSaisieDefautRouvmax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurSaisieDefautRouvmax(String value) {
        this.valeurSaisieDefautRouvmax = value;
    }

    /**
     * Gets the value of the typeOuvrant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOuvrant() {
        return typeOuvrant;
    }

    /**
     * Sets the value of the typeOuvrant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOuvrant(String value) {
        this.typeOuvrant = value;
    }

    /**
     * Gets the value of the rouvMax property.
     * 
     */
    public double getRouvMax() {
        return rouvMax;
    }

    /**
     * Sets the value of the rouvMax property.
     * 
     */
    public void setRouvMax(double value) {
        this.rouvMax = value;
    }

    /**
     * Gets the value of the valeurDeclareeDefautOuvertureBaieAuto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurDeclareeDefautOuvertureBaieAuto() {
        return valeurDeclareeDefautOuvertureBaieAuto;
    }

    /**
     * Sets the value of the valeurDeclareeDefautOuvertureBaieAuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurDeclareeDefautOuvertureBaieAuto(String value) {
        this.valeurDeclareeDefautOuvertureBaieAuto = value;
    }

    /**
     * Gets the value of the modeGestionHiver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeGestionHiver() {
        return modeGestionHiver;
    }

    /**
     * Sets the value of the modeGestionHiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeGestionHiver(String value) {
        this.modeGestionHiver = value;
    }

    /**
     * Gets the value of the paramOuvBaieHiver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOuvBaieHiver() {
        return paramOuvBaieHiver;
    }

    /**
     * Sets the value of the paramOuvBaieHiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOuvBaieHiver(String value) {
        this.paramOuvBaieHiver = value;
    }

    /**
     * Gets the value of the modeGestionMiSaison property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeGestionMiSaison() {
        return modeGestionMiSaison;
    }

    /**
     * Sets the value of the modeGestionMiSaison property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeGestionMiSaison(String value) {
        this.modeGestionMiSaison = value;
    }

    /**
     * Gets the value of the paramOuvBaieMiSaison property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOuvBaieMiSaison() {
        return paramOuvBaieMiSaison;
    }

    /**
     * Sets the value of the paramOuvBaieMiSaison property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOuvBaieMiSaison(String value) {
        this.paramOuvBaieMiSaison = value;
    }

    /**
     * Gets the value of the modeGestionEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeGestionEte() {
        return modeGestionEte;
    }

    /**
     * Sets the value of the modeGestionEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeGestionEte(String value) {
        this.modeGestionEte = value;
    }

    /**
     * Gets the value of the paramOuvBaieEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOuvBaieEte() {
        return paramOuvBaieEte;
    }

    /**
     * Sets the value of the paramOuvBaieEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOuvBaieEte(String value) {
        this.paramOuvBaieEte = value;
    }

    /**
     * Gets the value of the modeGestionE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeGestionE() {
        return modeGestionE;
    }

    /**
     * Sets the value of the modeGestionE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeGestionE(String value) {
        this.modeGestionE = value;
    }

    /**
     * Gets the value of the paramOuvBaieE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOuvBaieE() {
        return paramOuvBaieE;
    }

    /**
     * Sets the value of the paramOuvBaieE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOuvBaieE(String value) {
        this.paramOuvBaieE = value;
    }

    /**
     * Gets the value of the masqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice6 }
     *     
     */
    public ArrayOfChoice6 getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice6 }
     *     
     */
    public void setMasqueCollection(ArrayOfChoice6 value) {
        this.masqueCollection = value;
    }

    /**
     * Gets the value of the typoPermeaPM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoPermeaPM() {
        return typoPermeaPM;
    }

    /**
     * Sets the value of the typoPermeaPM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoPermeaPM(String value) {
        this.typoPermeaPM = value;
    }

}
