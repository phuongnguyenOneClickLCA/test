
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5_CardonnelIngenierie_Production_Stockage_Rotex
 * 
 * <p>Java class for T5_CardonnelIngenierie_Production_Stockage_Rotex_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CardonnelIngenierie_Production_Stockage_Rotex_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fl_Aval" type="{}E_Id_Fluide_Rotex"/>
 *         &lt;element name="Id_Fou_Sto" type="{}RT_Id_FouGen_Mode1"/>
 *         &lt;element name="Theta_max_av_IGen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" type="{}ArrayOfChoice1" minOccurs="0"/>
 *         &lt;element name="Source_Ballon_Base_Collection" type="{}ArrayOfRTSourceBallonBase" minOccurs="0"/>
 *         &lt;element name="Type_systeme" type="{}E_type_systeme"/>
 *         &lt;element name="statut_boucle_solaire" type="{}E_statut_boucle_solaire"/>
 *         &lt;element name="Reference_ballon" type="{}E_reference_ballon_Rotex"/>
 *         &lt;element name="Nb_ballons" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_sto_vc" type="{}E_position_ballon"/>
 *         &lt;element name="theta_depart_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="statut_donnee_UA" type="{}E_statut_donnee_UA"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_gestion_appoint" type="{}E_Type_gestion_appoint_Rotex"/>
 *         &lt;element name="delta_theta_c_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_confort_ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pp_solaire_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pp_solaire_min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_capteur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="n_0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="a1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="a2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ui" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Le_aller" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Le_retour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Li_aller" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Li_retour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_max_capteurs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_regul_solaire" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_relance_pompe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="t_mise_en_service" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_stop_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="deb_sol_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="K_theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot_app" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_b_max_app" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="statut_donnee_UA_app" type="{}E_statut_donnee_UA"/>
 *         &lt;element name="UA_s_app" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="z_app" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CardonnelIngenierie_Production_Stockage_Rotex_Data", propOrder = {

})
public class T5CardonnelIngenierieProductionStockageRotexData {

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
    @XmlElement(name = "Id_Fl_Aval")
    protected String idFlAval;
    @XmlElement(name = "Id_Fou_Sto")
    protected String idFouSto;
    @XmlElement(name = "Theta_max_av_IGen")
    protected Double thetaMaxAvIGen;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    protected ArrayOfChoice1 sourceBallonAppointCollection;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected ArrayOfRTSourceBallonBase sourceBallonBaseCollection;
    @XmlElement(name = "Type_systeme")
    protected String typeSysteme;
    @XmlElement(name = "statut_boucle_solaire")
    protected String statutBoucleSolaire;
    @XmlElement(name = "Reference_ballon")
    protected String referenceBallon;
    @XmlElement(name = "Nb_ballons")
    protected Double nbBallons;
    @XmlElement(name = "Is_sto_vc")
    protected String isStoVc;
    @XmlElement(name = "theta_depart_ch")
    protected Double thetaDepartCh;
    @XmlElement(name = "statut_donnee_UA")
    protected String statutDonneeUA;
    @XmlElement(name = "UA_s")
    protected Double uas;
    @XmlElement(name = "Type_gestion_appoint")
    protected String typeGestionAppoint;
    @XmlElement(name = "delta_theta_c_ap")
    protected Double deltaThetaCAp;
    @XmlElement(name = "T_confort_ecs")
    protected Double tConfortEcs;
    @XmlElement(name = "Pp_solaire_max")
    protected Double ppSolaireMax;
    @XmlElement(name = "Pp_solaire_min")
    protected Double ppSolaireMin;
    @XmlElement(name = "S_capteur")
    protected Double sCapteur;
    @XmlElement(name = "n_0")
    protected Double n0;
    protected Double a1;
    protected Double a2;
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
    @XmlElement(name = "theta_max_capteurs")
    protected Double thetaMaxCapteurs;
    @XmlElement(name = "theta_regul_solaire")
    protected Double thetaRegulSolaire;
    @XmlElement(name = "theta_relance_pompe")
    protected Double thetaRelancePompe;
    @XmlElement(name = "t_mise_en_service")
    protected Double tMiseEnService;
    @XmlElement(name = "theta_stop_boucle")
    protected Double thetaStopBoucle;
    @XmlElement(name = "deb_sol_nom")
    protected Double debSolNom;
    @XmlElement(name = "K_theta")
    protected Double kTheta;
    @XmlElement(name = "V_tot_app")
    protected Double vTotApp;
    @XmlElement(name = "theta_b_max_app")
    protected Double thetaBMaxApp;
    @XmlElement(name = "statut_donnee_UA_app")
    protected String statutDonneeUAApp;
    @XmlElement(name = "UA_s_app")
    protected Double uasApp;
    @XmlElement(name = "z_app")
    protected Double zApp;

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
     * Gets the value of the idFlAval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFlAval() {
        return idFlAval;
    }

    /**
     * Sets the value of the idFlAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFlAval(String value) {
        this.idFlAval = value;
    }

    /**
     * Gets the value of the idFouSto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouSto() {
        return idFouSto;
    }

    /**
     * Sets the value of the idFouSto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouSto(String value) {
        this.idFouSto = value;
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
     * Gets the value of the typeSysteme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeSysteme() {
        return typeSysteme;
    }

    /**
     * Sets the value of the typeSysteme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeSysteme(String value) {
        this.typeSysteme = value;
    }

    /**
     * Gets the value of the statutBoucleSolaire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutBoucleSolaire() {
        return statutBoucleSolaire;
    }

    /**
     * Sets the value of the statutBoucleSolaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutBoucleSolaire(String value) {
        this.statutBoucleSolaire = value;
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
     * Gets the value of the isStoVc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsStoVc() {
        return isStoVc;
    }

    /**
     * Sets the value of the isStoVc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsStoVc(String value) {
        this.isStoVc = value;
    }

    /**
     * Gets the value of the thetaDepartCh property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaDepartCh() {
        return thetaDepartCh;
    }

    /**
     * Sets the value of the thetaDepartCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaDepartCh(Double value) {
        this.thetaDepartCh = value;
    }

    /**
     * Gets the value of the statutDonneeUA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeUA() {
        return statutDonneeUA;
    }

    /**
     * Sets the value of the statutDonneeUA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeUA(String value) {
        this.statutDonneeUA = value;
    }

    /**
     * Gets the value of the uas property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUAS() {
        return uas;
    }

    /**
     * Sets the value of the uas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUAS(Double value) {
        this.uas = value;
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
     * Gets the value of the tConfortEcs property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTConfortEcs() {
        return tConfortEcs;
    }

    /**
     * Sets the value of the tConfortEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTConfortEcs(Double value) {
        this.tConfortEcs = value;
    }

    /**
     * Gets the value of the ppSolaireMax property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPpSolaireMax() {
        return ppSolaireMax;
    }

    /**
     * Sets the value of the ppSolaireMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPpSolaireMax(Double value) {
        this.ppSolaireMax = value;
    }

    /**
     * Gets the value of the ppSolaireMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPpSolaireMin() {
        return ppSolaireMin;
    }

    /**
     * Sets the value of the ppSolaireMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPpSolaireMin(Double value) {
        this.ppSolaireMin = value;
    }

    /**
     * Gets the value of the sCapteur property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSCapteur() {
        return sCapteur;
    }

    /**
     * Sets the value of the sCapteur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSCapteur(Double value) {
        this.sCapteur = value;
    }

    /**
     * Gets the value of the n0 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getN0() {
        return n0;
    }

    /**
     * Sets the value of the n0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setN0(Double value) {
        this.n0 = value;
    }

    /**
     * Gets the value of the a1 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getA1() {
        return a1;
    }

    /**
     * Sets the value of the a1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setA1(Double value) {
        this.a1 = value;
    }

    /**
     * Gets the value of the a2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getA2() {
        return a2;
    }

    /**
     * Sets the value of the a2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setA2(Double value) {
        this.a2 = value;
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
     * Gets the value of the thetaMaxCapteurs property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMaxCapteurs() {
        return thetaMaxCapteurs;
    }

    /**
     * Sets the value of the thetaMaxCapteurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMaxCapteurs(Double value) {
        this.thetaMaxCapteurs = value;
    }

    /**
     * Gets the value of the thetaRegulSolaire property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaRegulSolaire() {
        return thetaRegulSolaire;
    }

    /**
     * Sets the value of the thetaRegulSolaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaRegulSolaire(Double value) {
        this.thetaRegulSolaire = value;
    }

    /**
     * Gets the value of the thetaRelancePompe property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaRelancePompe() {
        return thetaRelancePompe;
    }

    /**
     * Sets the value of the thetaRelancePompe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaRelancePompe(Double value) {
        this.thetaRelancePompe = value;
    }

    /**
     * Gets the value of the tMiseEnService property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTMiseEnService() {
        return tMiseEnService;
    }

    /**
     * Sets the value of the tMiseEnService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTMiseEnService(Double value) {
        this.tMiseEnService = value;
    }

    /**
     * Gets the value of the thetaStopBoucle property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaStopBoucle() {
        return thetaStopBoucle;
    }

    /**
     * Sets the value of the thetaStopBoucle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaStopBoucle(Double value) {
        this.thetaStopBoucle = value;
    }

    /**
     * Gets the value of the debSolNom property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDebSolNom() {
        return debSolNom;
    }

    /**
     * Sets the value of the debSolNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDebSolNom(Double value) {
        this.debSolNom = value;
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
     * Gets the value of the vTotApp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVTotApp() {
        return vTotApp;
    }

    /**
     * Sets the value of the vTotApp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVTotApp(Double value) {
        this.vTotApp = value;
    }

    /**
     * Gets the value of the thetaBMaxApp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaBMaxApp() {
        return thetaBMaxApp;
    }

    /**
     * Sets the value of the thetaBMaxApp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaBMaxApp(Double value) {
        this.thetaBMaxApp = value;
    }

    /**
     * Gets the value of the statutDonneeUAApp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeUAApp() {
        return statutDonneeUAApp;
    }

    /**
     * Sets the value of the statutDonneeUAApp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeUAApp(String value) {
        this.statutDonneeUAApp = value;
    }

    /**
     * Gets the value of the uasApp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUASApp() {
        return uasApp;
    }

    /**
     * Sets the value of the uasApp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUASApp(Double value) {
        this.uasApp = value;
    }

    /**
     * Gets the value of the zApp property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getZApp() {
        return zApp;
    }

    /**
     * Sets the value of the zApp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setZApp(Double value) {
        this.zApp = value;
    }

}
