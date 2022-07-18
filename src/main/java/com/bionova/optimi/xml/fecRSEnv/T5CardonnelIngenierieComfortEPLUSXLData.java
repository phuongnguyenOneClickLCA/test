
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5_CardonnelIngenierie_Comfort_E_PLUS_XL
 * 
 * <p>Java class for T5_CardonnelIngenierie_Comfort_E_PLUS_XL_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CardonnelIngenierie_Comfort_E_PLUS_XL_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Theta_max_av_IGen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" type="{}ArrayOfChoice1" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Base_Collection" type="{}ArrayOfRTSourceBallonBase" minOccurs="0"/>
 *         &lt;element name="Type_systeme_choisi" type="{}E_Type_systeme_choisi"/>
 *         &lt;element name="Configuration_choisie" type="{}E_configuration_choisie"/>
 *         &lt;element name="Id_pos_gen" type="{}E_position_generation"/>
 *         &lt;element name="Type_module_FWM" type="{}E_Type_module_FWM"/>
 *         &lt;element name="U_ballon_FWM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_ballon_FWM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_FWM_ballon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nb_modFWM_boucl" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reference_capteurs" type="{}E_reference_capteurs"/>
 *         &lt;element name="Nb_capteurs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ui" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Le_aller" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Le_retour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Li_aller" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Li_retour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="K_theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_module_SLM" type="{}E_Type_module_SLM"/>
 *         &lt;element name="U_ballon_SLM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_ballon_SLM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_SLM_ballon" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reference_ballon" type="{}E_reference_ballon"/>
 *         &lt;element name="Nb_ballons" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Zone_retour_gen" type="{}E_Zone_retour_gen"/>
 *         &lt;element name="theta_c_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="delta_theta_c_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_gestion_appoint" type="{}E_Type_gestion_appoint"/>
 *         &lt;element name="delta_Tchauf_on" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="delta_Tchauf_off" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tretour_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="type_res_inter_gr_ecs" type="{}E_type_res_inter_gr_ecs"/>
 *         &lt;element name="L_vc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_hvc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_prim_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_therm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CardonnelIngenierie_Comfort_E_PLUS_XL_Data", propOrder = {

})
public class T5CardonnelIngenierieComfortEPLUSXLData {

    @XmlElement(name = "Index")
    protected Integer index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Idpriorite_Ch")
    protected Integer idprioriteCh;
    @XmlElement(name = "Idpriorite_Ecs")
    protected Integer idprioriteEcs;
    @XmlElement(name = "Theta_max_av_IGen")
    protected Double thetaMaxAvIGen;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    protected ArrayOfChoice1 sourceBallonAppointCollection;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected ArrayOfRTSourceBallonBase sourceBallonBaseCollection;
    @XmlElement(name = "Type_systeme_choisi")
    protected String typeSystemeChoisi;
    @XmlElement(name = "Configuration_choisie")
    protected String configurationChoisie;
    @XmlElement(name = "Id_pos_gen")
    protected String idPosGen;
    @XmlElement(name = "Type_module_FWM")
    protected String typeModuleFWM;
    @XmlElement(name = "U_ballon_FWM")
    protected Double uBallonFWM;
    @XmlElement(name = "L_ballon_FWM")
    protected Double lBallonFWM;
    @XmlElement(name = "L_FWM_ballon")
    protected Double lfwmBallon;
    @XmlElement(name = "Nb_modFWM_boucl")
    protected Double nbModFWMBoucl;
    @XmlElement(name = "Reference_capteurs")
    protected String referenceCapteurs;
    @XmlElement(name = "Nb_capteurs")
    protected Double nbCapteurs;
    @XmlElement(name = "Ue")
    protected Double ue;
    @XmlElement(name = "Ui")
    protected Double ui;
    @XmlElement(name = "Le_aller")
    protected Double leAller;
    @XmlElement(name = "Le_retour")
    protected Double leRetour;
    @XmlElement(name = "Li_aller")
    protected Double liAller;
    @XmlElement(name = "Li_retour")
    protected Double liRetour;
    @XmlElement(name = "K_theta")
    protected Double kTheta;
    @XmlElement(name = "Type_module_SLM")
    protected String typeModuleSLM;
    @XmlElement(name = "U_ballon_SLM")
    protected Double uBallonSLM;
    @XmlElement(name = "L_ballon_SLM")
    protected Double lBallonSLM;
    @XmlElement(name = "L_SLM_ballon")
    protected Double lslmBallon;
    @XmlElement(name = "Reference_ballon")
    protected String referenceBallon;
    @XmlElement(name = "Nb_ballons")
    protected Double nbBallons;
    @XmlElement(name = "Zone_retour_gen")
    protected String zoneRetourGen;
    @XmlElement(name = "theta_c_ap")
    protected Double thetaCAp;
    @XmlElement(name = "delta_theta_c_ap")
    protected Double deltaThetaCAp;
    @XmlElement(name = "Type_gestion_appoint")
    protected String typeGestionAppoint;
    @XmlElement(name = "delta_Tchauf_on")
    protected Double deltaTchaufOn;
    @XmlElement(name = "delta_Tchauf_off")
    protected Double deltaTchaufOff;
    @XmlElement(name = "Tretour_ch")
    protected Double tretourCh;
    @XmlElement(name = "type_res_inter_gr_ecs")
    protected String typeResInterGrEcs;
    @XmlElement(name = "L_vc_prim_bcl_e")
    protected Double lVcPrimBclE;
    @XmlElement(name = "L_hvc_prim_bcl_e")
    protected Double lHvcPrimBclE;
    @XmlElement(name = "U_prim_e")
    protected Double uPrimE;
    @XmlElement(name = "b_therm")
    protected Double bTherm;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
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
     * Gets the value of the rdim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRdim(Integer value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the idprioriteCh property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdprioriteCh(Integer value) {
        this.idprioriteCh = value;
    }

    /**
     * Gets the value of the idprioriteEcs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdprioriteEcs(Integer value) {
        this.idprioriteEcs = value;
    }

    /**
     * Gets the value of the thetaMaxAvIGen property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMaxAvIGen() {
        return thetaMaxAvIGen;
    }

    /**
     * Sets the value of the thetaMaxAvIGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMaxAvIGen(Double value) {
        this.thetaMaxAvIGen = value;
    }

    /**
     * Gets the value of the sourceBallonAppointCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    public ArrayOfChoice1 getSourceBallonAppointCollection() {
        return sourceBallonAppointCollection;
    }

    /**
     * Sets the value of the sourceBallonAppointCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice1 }
     *     
     */
    public void setSourceBallonAppointCollection(ArrayOfChoice1 value) {
        this.sourceBallonAppointCollection = value;
    }

    /**
     * Gets the value of the sourceBallonBaseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTSourceBallonBase }
     *     
     */
    public ArrayOfRTSourceBallonBase getSourceBallonBaseCollection() {
        return sourceBallonBaseCollection;
    }

    /**
     * Sets the value of the sourceBallonBaseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTSourceBallonBase }
     *     
     */
    public void setSourceBallonBaseCollection(ArrayOfRTSourceBallonBase value) {
        this.sourceBallonBaseCollection = value;
    }

    /**
     * Gets the value of the typeSystemeChoisi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeSystemeChoisi() {
        return typeSystemeChoisi;
    }

    /**
     * Sets the value of the typeSystemeChoisi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeSystemeChoisi(String value) {
        this.typeSystemeChoisi = value;
    }

    /**
     * Gets the value of the configurationChoisie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationChoisie() {
        return configurationChoisie;
    }

    /**
     * Sets the value of the configurationChoisie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationChoisie(String value) {
        this.configurationChoisie = value;
    }

    /**
     * Gets the value of the idPosGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPosGen() {
        return idPosGen;
    }

    /**
     * Sets the value of the idPosGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPosGen(String value) {
        this.idPosGen = value;
    }

    /**
     * Gets the value of the typeModuleFWM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeModuleFWM() {
        return typeModuleFWM;
    }

    /**
     * Sets the value of the typeModuleFWM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeModuleFWM(String value) {
        this.typeModuleFWM = value;
    }

    /**
     * Gets the value of the uBallonFWM property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUBallonFWM() {
        return uBallonFWM;
    }

    /**
     * Sets the value of the uBallonFWM property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUBallonFWM(Double value) {
        this.uBallonFWM = value;
    }

    /**
     * Gets the value of the lBallonFWM property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLBallonFWM() {
        return lBallonFWM;
    }

    /**
     * Sets the value of the lBallonFWM property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLBallonFWM(Double value) {
        this.lBallonFWM = value;
    }

    /**
     * Gets the value of the lfwmBallon property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLFWMBallon() {
        return lfwmBallon;
    }

    /**
     * Sets the value of the lfwmBallon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLFWMBallon(Double value) {
        this.lfwmBallon = value;
    }

    /**
     * Gets the value of the nbModFWMBoucl property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNbModFWMBoucl() {
        return nbModFWMBoucl;
    }

    /**
     * Sets the value of the nbModFWMBoucl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNbModFWMBoucl(Double value) {
        this.nbModFWMBoucl = value;
    }

    /**
     * Gets the value of the referenceCapteurs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceCapteurs() {
        return referenceCapteurs;
    }

    /**
     * Sets the value of the referenceCapteurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceCapteurs(String value) {
        this.referenceCapteurs = value;
    }

    /**
     * Gets the value of the nbCapteurs property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNbCapteurs() {
        return nbCapteurs;
    }

    /**
     * Sets the value of the nbCapteurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNbCapteurs(Double value) {
        this.nbCapteurs = value;
    }

    /**
     * Gets the value of the ue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUe() {
        return ue;
    }

    /**
     * Sets the value of the ue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUe(Double value) {
        this.ue = value;
    }

    /**
     * Gets the value of the ui property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUi() {
        return ui;
    }

    /**
     * Sets the value of the ui property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUi(Double value) {
        this.ui = value;
    }

    /**
     * Gets the value of the leAller property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLeAller() {
        return leAller;
    }

    /**
     * Sets the value of the leAller property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLeAller(Double value) {
        this.leAller = value;
    }

    /**
     * Gets the value of the leRetour property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLeRetour() {
        return leRetour;
    }

    /**
     * Sets the value of the leRetour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLeRetour(Double value) {
        this.leRetour = value;
    }

    /**
     * Gets the value of the liAller property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLiAller() {
        return liAller;
    }

    /**
     * Sets the value of the liAller property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLiAller(Double value) {
        this.liAller = value;
    }

    /**
     * Gets the value of the liRetour property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLiRetour() {
        return liRetour;
    }

    /**
     * Sets the value of the liRetour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLiRetour(Double value) {
        this.liRetour = value;
    }

    /**
     * Gets the value of the kTheta property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getKTheta() {
        return kTheta;
    }

    /**
     * Sets the value of the kTheta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setKTheta(Double value) {
        this.kTheta = value;
    }

    /**
     * Gets the value of the typeModuleSLM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeModuleSLM() {
        return typeModuleSLM;
    }

    /**
     * Sets the value of the typeModuleSLM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeModuleSLM(String value) {
        this.typeModuleSLM = value;
    }

    /**
     * Gets the value of the uBallonSLM property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUBallonSLM() {
        return uBallonSLM;
    }

    /**
     * Sets the value of the uBallonSLM property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUBallonSLM(Double value) {
        this.uBallonSLM = value;
    }

    /**
     * Gets the value of the lBallonSLM property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLBallonSLM() {
        return lBallonSLM;
    }

    /**
     * Sets the value of the lBallonSLM property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLBallonSLM(Double value) {
        this.lBallonSLM = value;
    }

    /**
     * Gets the value of the lslmBallon property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLSLMBallon() {
        return lslmBallon;
    }

    /**
     * Sets the value of the lslmBallon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLSLMBallon(Double value) {
        this.lslmBallon = value;
    }

    /**
     * Gets the value of the referenceBallon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceBallon() {
        return referenceBallon;
    }

    /**
     * Sets the value of the referenceBallon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceBallon(String value) {
        this.referenceBallon = value;
    }

    /**
     * Gets the value of the nbBallons property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNbBallons() {
        return nbBallons;
    }

    /**
     * Sets the value of the nbBallons property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNbBallons(Double value) {
        this.nbBallons = value;
    }

    /**
     * Gets the value of the zoneRetourGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneRetourGen() {
        return zoneRetourGen;
    }

    /**
     * Sets the value of the zoneRetourGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneRetourGen(String value) {
        this.zoneRetourGen = value;
    }

    /**
     * Gets the value of the thetaCAp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaCAp() {
        return thetaCAp;
    }

    /**
     * Sets the value of the thetaCAp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaCAp(Double value) {
        this.thetaCAp = value;
    }

    /**
     * Gets the value of the deltaThetaCAp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeltaThetaCAp() {
        return deltaThetaCAp;
    }

    /**
     * Sets the value of the deltaThetaCAp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeltaThetaCAp(Double value) {
        this.deltaThetaCAp = value;
    }

    /**
     * Gets the value of the typeGestionAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionAppoint() {
        return typeGestionAppoint;
    }

    /**
     * Sets the value of the typeGestionAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionAppoint(String value) {
        this.typeGestionAppoint = value;
    }

    /**
     * Gets the value of the deltaTchaufOn property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeltaTchaufOn() {
        return deltaTchaufOn;
    }

    /**
     * Sets the value of the deltaTchaufOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeltaTchaufOn(Double value) {
        this.deltaTchaufOn = value;
    }

    /**
     * Gets the value of the deltaTchaufOff property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDeltaTchaufOff() {
        return deltaTchaufOff;
    }

    /**
     * Sets the value of the deltaTchaufOff property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDeltaTchaufOff(Double value) {
        this.deltaTchaufOff = value;
    }

    /**
     * Gets the value of the tretourCh property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTretourCh() {
        return tretourCh;
    }

    /**
     * Sets the value of the tretourCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTretourCh(Double value) {
        this.tretourCh = value;
    }

    /**
     * Gets the value of the typeResInterGrEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeResInterGrEcs() {
        return typeResInterGrEcs;
    }

    /**
     * Sets the value of the typeResInterGrEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeResInterGrEcs(String value) {
        this.typeResInterGrEcs = value;
    }

    /**
     * Gets the value of the lVcPrimBclE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLVcPrimBclE() {
        return lVcPrimBclE;
    }

    /**
     * Sets the value of the lVcPrimBclE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLVcPrimBclE(Double value) {
        this.lVcPrimBclE = value;
    }

    /**
     * Gets the value of the lHvcPrimBclE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLHvcPrimBclE() {
        return lHvcPrimBclE;
    }

    /**
     * Sets the value of the lHvcPrimBclE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLHvcPrimBclE(Double value) {
        this.lHvcPrimBclE = value;
    }

    /**
     * Gets the value of the uPrimE property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUPrimE() {
        return uPrimE;
    }

    /**
     * Sets the value of the uPrimE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUPrimE(Double value) {
        this.uPrimE = value;
    }

    /**
     * Gets the value of the bTherm property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBTherm() {
        return bTherm;
    }

    /**
     * Sets the value of the bTherm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBTherm(Double value) {
        this.bTherm = value;
    }

}
