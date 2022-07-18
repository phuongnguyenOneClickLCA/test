
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_CardonnelIngenierie_LiMithra
 * 
 * <p>Java class for T5_CardonnelIngenierie_LiMithra_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CardonnelIngenierie_LiMithra_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fou_Sto" type="{}RT_Id_FouGen_Mode1"/>
 *         &lt;element name="Source_Ballon_Appoint_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ArrayOfChoice1">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Source_Ballon_Base_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ArrayOfRTSourceBallonBase">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="theta_b_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_b_ch_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_consigne_ballon_ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="statut_donnee_UA" type="{}E_statut_donnee_UA"/>
 *         &lt;element name="statut_donnee_UA_ch" type="{}E_statut_donnee_UA_chauffage"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_chauffage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="delta_theta_c_ap" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_gestion_appoint" type="{}E_Type_gestion_appoint"/>
 *         &lt;element name="P_c" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_capteur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="n_0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bu" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ui" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="deb_sol_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pp_solaire_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="f_aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ECS_et_chauffage" type="{}E_ECS_et_chauffage"/>
 *         &lt;element name="type_appoint" type="{}E_type_appoint"/>
 *         &lt;element name="Statut_Performances" type="{}E_Statut_Performances"/>
 *         &lt;element name="COP_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CardonnelIngenierie_LiMithra_Data", propOrder = {

})
public class T5CardonnelIngenierieLiMithraData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Id_Fou_Sto", required = true)
    protected String idFouSto;
    @XmlElement(name = "Source_Ballon_Appoint_Collection")
    protected T5CardonnelIngenierieLiMithraData.SourceBallonAppointCollection sourceBallonAppointCollection;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected T5CardonnelIngenierieLiMithraData.SourceBallonBaseCollection sourceBallonBaseCollection;
    @XmlElement(name = "theta_b_max")
    protected double thetaBMax;
    @XmlElement(name = "theta_b_ch_max")
    protected double thetaBChMax;
    @XmlElement(name = "V_tot")
    protected double vTot;
    @XmlElement(name = "V_tot_ch")
    protected double vTotCh;
    @XmlElement(name = "T_consigne_ballon_ch")
    protected double tConsigneBallonCh;
    @XmlElement(name = "statut_donnee_UA", required = true)
    protected String statutDonneeUA;
    @XmlElement(name = "statut_donnee_UA_ch", required = true)
    protected String statutDonneeUACh;
    @XmlElement(name = "UA_s")
    protected double uas;
    @XmlElement(name = "UA_chauffage")
    protected double uaChauffage;
    @XmlElement(name = "delta_theta_c_ap")
    protected double deltaThetaCAp;
    @XmlElement(name = "Type_gestion_appoint", required = true)
    protected String typeGestionAppoint;
    @XmlElement(name = "P_c")
    protected double pc;
    @XmlElement(name = "S_capteur")
    protected double sCapteur;
    @XmlElement(name = "n_0")
    protected double n0;
    protected double b1;
    protected double bu;
    protected double b2;
    @XmlElement(name = "Ue")
    protected double ue;
    @XmlElement(name = "Ui")
    protected double ui;
    @XmlElement(name = "deb_sol_nom")
    protected double debSolNom;
    @XmlElement(name = "Pp_solaire_nom")
    protected double ppSolaireNom;
    @XmlElement(name = "f_aux")
    protected double fAux;
    @XmlElement(name = "ECS_et_chauffage", required = true)
    protected String ecsEtChauffage;
    @XmlElement(name = "type_appoint", required = true)
    protected String typeAppoint;
    @XmlElement(name = "Statut_Performances", required = true)
    protected String statutPerformances;
    @XmlElement(name = "COP_pivot")
    protected double copPivot;
    @XmlElement(name = "Pabs_pivot")
    protected double pabsPivot;

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
     * Gets the value of the rdim property.
     * 
     */
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    public void setRdim(int value) {
        this.rdim = value;
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
     * Gets the value of the sourceBallonAppointCollection property.
     * 
     * @return
     *     possible object is
     *     {@link T5CardonnelIngenierieLiMithraData.SourceBallonAppointCollection }
     *     
     */
    public T5CardonnelIngenierieLiMithraData.SourceBallonAppointCollection getSourceBallonAppointCollection() {
        return sourceBallonAppointCollection;
    }

    /**
     * Sets the value of the sourceBallonAppointCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link T5CardonnelIngenierieLiMithraData.SourceBallonAppointCollection }
     *     
     */
    public void setSourceBallonAppointCollection(T5CardonnelIngenierieLiMithraData.SourceBallonAppointCollection value) {
        this.sourceBallonAppointCollection = value;
    }

    /**
     * Gets the value of the sourceBallonBaseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link T5CardonnelIngenierieLiMithraData.SourceBallonBaseCollection }
     *     
     */
    public T5CardonnelIngenierieLiMithraData.SourceBallonBaseCollection getSourceBallonBaseCollection() {
        return sourceBallonBaseCollection;
    }

    /**
     * Sets the value of the sourceBallonBaseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link T5CardonnelIngenierieLiMithraData.SourceBallonBaseCollection }
     *     
     */
    public void setSourceBallonBaseCollection(T5CardonnelIngenierieLiMithraData.SourceBallonBaseCollection value) {
        this.sourceBallonBaseCollection = value;
    }

    /**
     * Gets the value of the thetaBMax property.
     * 
     */
    public double getThetaBMax() {
        return thetaBMax;
    }

    /**
     * Sets the value of the thetaBMax property.
     * 
     */
    public void setThetaBMax(double value) {
        this.thetaBMax = value;
    }

    /**
     * Gets the value of the thetaBChMax property.
     * 
     */
    public double getThetaBChMax() {
        return thetaBChMax;
    }

    /**
     * Sets the value of the thetaBChMax property.
     * 
     */
    public void setThetaBChMax(double value) {
        this.thetaBChMax = value;
    }

    /**
     * Gets the value of the vTot property.
     * 
     */
    public double getVTot() {
        return vTot;
    }

    /**
     * Sets the value of the vTot property.
     * 
     */
    public void setVTot(double value) {
        this.vTot = value;
    }

    /**
     * Gets the value of the vTotCh property.
     * 
     */
    public double getVTotCh() {
        return vTotCh;
    }

    /**
     * Sets the value of the vTotCh property.
     * 
     */
    public void setVTotCh(double value) {
        this.vTotCh = value;
    }

    /**
     * Gets the value of the tConsigneBallonCh property.
     * 
     */
    public double getTConsigneBallonCh() {
        return tConsigneBallonCh;
    }

    /**
     * Sets the value of the tConsigneBallonCh property.
     * 
     */
    public void setTConsigneBallonCh(double value) {
        this.tConsigneBallonCh = value;
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
     * Gets the value of the statutDonneeUACh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeUACh() {
        return statutDonneeUACh;
    }

    /**
     * Sets the value of the statutDonneeUACh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeUACh(String value) {
        this.statutDonneeUACh = value;
    }

    /**
     * Gets the value of the uas property.
     * 
     */
    public double getUAS() {
        return uas;
    }

    /**
     * Sets the value of the uas property.
     * 
     */
    public void setUAS(double value) {
        this.uas = value;
    }

    /**
     * Gets the value of the uaChauffage property.
     * 
     */
    public double getUAChauffage() {
        return uaChauffage;
    }

    /**
     * Sets the value of the uaChauffage property.
     * 
     */
    public void setUAChauffage(double value) {
        this.uaChauffage = value;
    }

    /**
     * Gets the value of the deltaThetaCAp property.
     * 
     */
    public double getDeltaThetaCAp() {
        return deltaThetaCAp;
    }

    /**
     * Sets the value of the deltaThetaCAp property.
     * 
     */
    public void setDeltaThetaCAp(double value) {
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
     * Gets the value of the pc property.
     * 
     */
    public double getPC() {
        return pc;
    }

    /**
     * Sets the value of the pc property.
     * 
     */
    public void setPC(double value) {
        this.pc = value;
    }

    /**
     * Gets the value of the sCapteur property.
     * 
     */
    public double getSCapteur() {
        return sCapteur;
    }

    /**
     * Sets the value of the sCapteur property.
     * 
     */
    public void setSCapteur(double value) {
        this.sCapteur = value;
    }

    /**
     * Gets the value of the n0 property.
     * 
     */
    public double getN0() {
        return n0;
    }

    /**
     * Sets the value of the n0 property.
     * 
     */
    public void setN0(double value) {
        this.n0 = value;
    }

    /**
     * Gets the value of the b1 property.
     * 
     */
    public double getB1() {
        return b1;
    }

    /**
     * Sets the value of the b1 property.
     * 
     */
    public void setB1(double value) {
        this.b1 = value;
    }

    /**
     * Gets the value of the bu property.
     * 
     */
    public double getBu() {
        return bu;
    }

    /**
     * Sets the value of the bu property.
     * 
     */
    public void setBu(double value) {
        this.bu = value;
    }

    /**
     * Gets the value of the b2 property.
     * 
     */
    public double getB2() {
        return b2;
    }

    /**
     * Sets the value of the b2 property.
     * 
     */
    public void setB2(double value) {
        this.b2 = value;
    }

    /**
     * Gets the value of the ue property.
     * 
     */
    public double getUe() {
        return ue;
    }

    /**
     * Sets the value of the ue property.
     * 
     */
    public void setUe(double value) {
        this.ue = value;
    }

    /**
     * Gets the value of the ui property.
     * 
     */
    public double getUi() {
        return ui;
    }

    /**
     * Sets the value of the ui property.
     * 
     */
    public void setUi(double value) {
        this.ui = value;
    }

    /**
     * Gets the value of the debSolNom property.
     * 
     */
    public double getDebSolNom() {
        return debSolNom;
    }

    /**
     * Sets the value of the debSolNom property.
     * 
     */
    public void setDebSolNom(double value) {
        this.debSolNom = value;
    }

    /**
     * Gets the value of the ppSolaireNom property.
     * 
     */
    public double getPpSolaireNom() {
        return ppSolaireNom;
    }

    /**
     * Sets the value of the ppSolaireNom property.
     * 
     */
    public void setPpSolaireNom(double value) {
        this.ppSolaireNom = value;
    }

    /**
     * Gets the value of the fAux property.
     * 
     */
    public double getFAux() {
        return fAux;
    }

    /**
     * Sets the value of the fAux property.
     * 
     */
    public void setFAux(double value) {
        this.fAux = value;
    }

    /**
     * Gets the value of the ecsEtChauffage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getECSEtChauffage() {
        return ecsEtChauffage;
    }

    /**
     * Sets the value of the ecsEtChauffage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setECSEtChauffage(String value) {
        this.ecsEtChauffage = value;
    }

    /**
     * Gets the value of the typeAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeAppoint() {
        return typeAppoint;
    }

    /**
     * Sets the value of the typeAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeAppoint(String value) {
        this.typeAppoint = value;
    }

    /**
     * Gets the value of the statutPerformances property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPerformances() {
        return statutPerformances;
    }

    /**
     * Sets the value of the statutPerformances property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPerformances(String value) {
        this.statutPerformances = value;
    }

    /**
     * Gets the value of the copPivot property.
     * 
     */
    public double getCOPPivot() {
        return copPivot;
    }

    /**
     * Sets the value of the copPivot property.
     * 
     */
    public void setCOPPivot(double value) {
        this.copPivot = value;
    }

    /**
     * Gets the value of the pabsPivot property.
     * 
     */
    public double getPabsPivot() {
        return pabsPivot;
    }

    /**
     * Sets the value of the pabsPivot property.
     * 
     */
    public void setPabsPivot(double value) {
        this.pabsPivot = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}ArrayOfChoice1">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SourceBallonAppointCollection
        extends ArrayOfChoice1
    {


    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}ArrayOfRTSourceBallonBase">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SourceBallonBaseCollection
        extends ArrayOfRTSourceBallonBase
    {


    }

}
