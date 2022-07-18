
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_CSTB_GenerateurThermodynamiqueGazDoubleService_Data
 * 
 * <p>Java class for T5_CSTB_GenerateurThermodynamiqueGazDoubleService_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CSTB_GenerateurThermodynamiqueGazDoubleService_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo_ds" type="{}E_Systeme_Thermodynamique_Gaz_Double_Service"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Haute_Temperature_Ch" type="{}E_Temperatures_Aval_Air_Eau_Haute_Temperature_Ch"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Haute_Temperature_Ch" type="{}E_Temperatures_Amont_Air_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ch" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ch"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Haute_Temperature_Ch" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Eau_Ch" type="{}E_Temperatures_Aval_Eau_Eau_Ch"/>
 *         &lt;element name="Theta_Amont_Eau_Eau_Ch" type="{}E_Temperatures_Amont_Eau_Eau"/>
 *         &lt;element name="Performance_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_pc_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnee_Ecs" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Haute_Temperature_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Haute_Temperature_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Haute_Temperature_Ecs" type="{}E_Temperatures_Amont_Air_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ecs" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Haute_Temperature_Ecs" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau_Haute_Temperature"/>
 *         &lt;element name="Theta_Aval_Eau_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_Eau"/>
 *         &lt;element name="Performance_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_pc_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_pc_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ecs" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Bruleur_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Bruleur_Ch" type="{}E_Fonctionnement_Bruleur"/>
 *         &lt;element name="Statut_Echangeur" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Echangeur_Eau_Ch_Fumee" type="{}E_Oui_Non"/>
 *         &lt;element name="Statut_Autres_Donnees_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Emetteur"/>
 *         &lt;element name="Rdt_Comp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pertes_40deg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux0" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "T5_CSTB_GenerateurThermodynamiqueGazDoubleService_Data", propOrder = {

})
public class T5CSTBGenerateurThermodynamiqueGazDoubleServiceData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Sys_Thermo_ds", required = true)
    protected String sysThermoDs;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    protected String statutDonneeCh;
    @XmlElement(name = "Theta_Aval_Air_Eau_Haute_Temperature_Ch", required = true)
    protected String thetaAvalAirEauHauteTemperatureCh;
    @XmlElement(name = "Theta_Amont_Air_Eau_Haute_Temperature_Ch", required = true)
    protected String thetaAmontAirEauHauteTemperatureCh;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ch", required = true)
    protected String thetaAvalEauGlycoleeEauHauteTemperatureCh;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Haute_Temperature_Ch", required = true)
    protected String thetaAmontEauGlycoleeEauHauteTemperatureCh;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Ch", required = true)
    protected String thetaAvalEauEauCh;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Ch", required = true)
    protected String thetaAmontEauEauCh;
    @XmlElement(name = "Performance_Ch")
    protected String performanceCh;
    @XmlElement(name = "Pabs_Ch")
    protected String pabsCh;
    @XmlElement(name = "COR_Ch")
    protected String corCh;
    @XmlElement(name = "Paux_pc_Ch")
    protected String pauxPcCh;
    @XmlElement(name = "Statut_Val_Pivot_Ch", required = true)
    protected String statutValPivotCh;
    @XmlElement(name = "Val_GUE_Ch")
    protected double valGUECh;
    @XmlElement(name = "Val_Pabs_Ch")
    protected double valPabsCh;
    @XmlElement(name = "Statut_Paux_Pivot_Ch", required = true)
    protected String statutPauxPivotCh;
    @XmlElement(name = "Val_Paux_pc_Ch")
    protected double valPauxPcCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av_Ch")
    protected double thetaMaxAvCh;
    @XmlElement(name = "Theta_Min_Am_Ch")
    protected double thetaMinAmCh;
    @XmlElement(name = "Statut_Donnee_Ecs", required = true)
    protected String statutDonneeEcs;
    @XmlElement(name = "Theta_Aval_Air_Eau_Haute_Temperature_Ecs", required = true)
    protected String thetaAvalAirEauHauteTemperatureEcs;
    @XmlElement(name = "Theta_Amont_Air_Eau_Haute_Temperature_Ecs", required = true)
    protected String thetaAmontAirEauHauteTemperatureEcs;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Haute_Temperature_Ecs", required = true)
    protected String thetaAvalEauGlycoleeEauHauteTemperatureEcs;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Haute_Temperature_Ecs", required = true)
    protected String thetaAmontEauGlycoleeEauHauteTemperatureEcs;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Ecs", required = true)
    protected String thetaAvalEauEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Ecs", required = true)
    protected String thetaAmontEauEauEcs;
    @XmlElement(name = "Performance_Ecs")
    protected String performanceEcs;
    @XmlElement(name = "Pabs_Ecs")
    protected String pabsEcs;
    @XmlElement(name = "COR_Ecs")
    protected String corEcs;
    @XmlElement(name = "Paux_pc_Ecs")
    protected String pauxPcEcs;
    @XmlElement(name = "Statut_Val_Pivot_Ecs", required = true)
    protected String statutValPivotEcs;
    @XmlElement(name = "Val_GUE_Ecs")
    protected double valGUEEcs;
    @XmlElement(name = "Val_Pabs_Ecs")
    protected double valPabsEcs;
    @XmlElement(name = "Statut_Paux_Pivot_Ecs", required = true)
    protected String statutPauxPivotEcs;
    @XmlElement(name = "Val_Paux_pc_Ecs")
    protected double valPauxPcEcs;
    @XmlElement(name = "Lim_Theta_Ecs", required = true)
    protected String limThetaEcs;
    @XmlElement(name = "Theta_Max_Av_Ecs")
    protected double thetaMaxAvEcs;
    @XmlElement(name = "Theta_Min_Am_Ecs")
    protected double thetaMinAmEcs;
    @XmlElement(name = "Statut_Bruleur_Ch", required = true)
    protected String statutBruleurCh;
    @XmlElement(name = "Fonctionnement_Bruleur_Ch", required = true)
    protected String fonctionnementBruleurCh;
    @XmlElement(name = "Statut_Echangeur", required = true)
    protected String statutEchangeur;
    @XmlElement(name = "Echangeur_Eau_Ch_Fumee", required = true)
    protected String echangeurEauChFumee;
    @XmlElement(name = "Statut_Autres_Donnees_Ch", required = true)
    protected String statutAutresDonneesCh;
    @XmlElement(name = "LRcontmin_Ch")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    protected String typoEmetteurCh;
    @XmlElement(name = "Rdt_Comp")
    protected double rdtComp;
    @XmlElement(name = "Pertes_40deg")
    protected double pertes40Deg;
    @XmlElement(name = "Paux0")
    protected double paux0;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the idprioriteCh property.
     * 
     */
    public int getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     */
    public void setIdprioriteCh(int value) {
        this.idprioriteCh = value;
    }

    /**
     * Gets the value of the idprioriteEcs property.
     * 
     */
    public int getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     */
    public void setIdprioriteEcs(int value) {
        this.idprioriteEcs = value;
    }

    /**
     * Gets the value of the idSourceAmont property.
     * 
     */
    public int getIdSourceAmont() {
        return idSourceAmont;
    }

    /**
     * Sets the value of the idSourceAmont property.
     * 
     */
    public void setIdSourceAmont(int value) {
        this.idSourceAmont = value;
    }

    /**
     * Gets the value of the sysThermoDs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoDs() {
        return sysThermoDs;
    }

    /**
     * Sets the value of the sysThermoDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoDs(String value) {
        this.sysThermoDs = value;
    }

    /**
     * Gets the value of the statutDonneeCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeCh() {
        return statutDonneeCh;
    }

    /**
     * Sets the value of the statutDonneeCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeCh(String value) {
        this.statutDonneeCh = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauHauteTemperatureCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauHauteTemperatureCh() {
        return thetaAvalAirEauHauteTemperatureCh;
    }

    /**
     * Sets the value of the thetaAvalAirEauHauteTemperatureCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauHauteTemperatureCh(String value) {
        this.thetaAvalAirEauHauteTemperatureCh = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauHauteTemperatureCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauHauteTemperatureCh() {
        return thetaAmontAirEauHauteTemperatureCh;
    }

    /**
     * Sets the value of the thetaAmontAirEauHauteTemperatureCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauHauteTemperatureCh(String value) {
        this.thetaAmontAirEauHauteTemperatureCh = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauHauteTemperatureCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauHauteTemperatureCh() {
        return thetaAvalEauGlycoleeEauHauteTemperatureCh;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauHauteTemperatureCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauHauteTemperatureCh(String value) {
        this.thetaAvalEauGlycoleeEauHauteTemperatureCh = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauHauteTemperatureCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEauHauteTemperatureCh() {
        return thetaAmontEauGlycoleeEauHauteTemperatureCh;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauHauteTemperatureCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEauHauteTemperatureCh(String value) {
        this.thetaAmontEauGlycoleeEauHauteTemperatureCh = value;
    }

    /**
     * Gets the value of the thetaAvalEauEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauEauCh() {
        return thetaAvalEauEauCh;
    }

    /**
     * Sets the value of the thetaAvalEauEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauEauCh(String value) {
        this.thetaAvalEauEauCh = value;
    }

    /**
     * Gets the value of the thetaAmontEauEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauEauCh() {
        return thetaAmontEauEauCh;
    }

    /**
     * Sets the value of the thetaAmontEauEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauEauCh(String value) {
        this.thetaAmontEauEauCh = value;
    }

    /**
     * Gets the value of the performanceCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformanceCh() {
        return performanceCh;
    }

    /**
     * Sets the value of the performanceCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformanceCh(String value) {
        this.performanceCh = value;
    }

    /**
     * Gets the value of the pabsCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsCh() {
        return pabsCh;
    }

    /**
     * Sets the value of the pabsCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsCh(String value) {
        this.pabsCh = value;
    }

    /**
     * Gets the value of the corCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCORCh() {
        return corCh;
    }

    /**
     * Sets the value of the corCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCORCh(String value) {
        this.corCh = value;
    }

    /**
     * Gets the value of the pauxPcCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxPcCh() {
        return pauxPcCh;
    }

    /**
     * Sets the value of the pauxPcCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxPcCh(String value) {
        this.pauxPcCh = value;
    }

    /**
     * Gets the value of the statutValPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotCh() {
        return statutValPivotCh;
    }

    /**
     * Sets the value of the statutValPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotCh(String value) {
        this.statutValPivotCh = value;
    }

    /**
     * Gets the value of the valGUECh property.
     * 
     */
    public double getValGUECh() {
        return valGUECh;
    }

    /**
     * Sets the value of the valGUECh property.
     * 
     */
    public void setValGUECh(double value) {
        this.valGUECh = value;
    }

    /**
     * Gets the value of the valPabsCh property.
     * 
     */
    public double getValPabsCh() {
        return valPabsCh;
    }

    /**
     * Sets the value of the valPabsCh property.
     * 
     */
    public void setValPabsCh(double value) {
        this.valPabsCh = value;
    }

    /**
     * Gets the value of the statutPauxPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxPivotCh() {
        return statutPauxPivotCh;
    }

    /**
     * Sets the value of the statutPauxPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxPivotCh(String value) {
        this.statutPauxPivotCh = value;
    }

    /**
     * Gets the value of the valPauxPcCh property.
     * 
     */
    public double getValPauxPcCh() {
        return valPauxPcCh;
    }

    /**
     * Sets the value of the valPauxPcCh property.
     * 
     */
    public void setValPauxPcCh(double value) {
        this.valPauxPcCh = value;
    }

    /**
     * Gets the value of the limThetaCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimThetaCh() {
        return limThetaCh;
    }

    /**
     * Sets the value of the limThetaCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimThetaCh(String value) {
        this.limThetaCh = value;
    }

    /**
     * Gets the value of the thetaMaxAvCh property.
     * 
     */
    public double getThetaMaxAvCh() {
        return thetaMaxAvCh;
    }

    /**
     * Sets the value of the thetaMaxAvCh property.
     * 
     */
    public void setThetaMaxAvCh(double value) {
        this.thetaMaxAvCh = value;
    }

    /**
     * Gets the value of the thetaMinAmCh property.
     * 
     */
    public double getThetaMinAmCh() {
        return thetaMinAmCh;
    }

    /**
     * Sets the value of the thetaMinAmCh property.
     * 
     */
    public void setThetaMinAmCh(double value) {
        this.thetaMinAmCh = value;
    }

    /**
     * Gets the value of the statutDonneeEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeEcs() {
        return statutDonneeEcs;
    }

    /**
     * Sets the value of the statutDonneeEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeEcs(String value) {
        this.statutDonneeEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauHauteTemperatureEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauHauteTemperatureEcs() {
        return thetaAvalAirEauHauteTemperatureEcs;
    }

    /**
     * Sets the value of the thetaAvalAirEauHauteTemperatureEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauHauteTemperatureEcs(String value) {
        this.thetaAvalAirEauHauteTemperatureEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauHauteTemperatureEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauHauteTemperatureEcs() {
        return thetaAmontAirEauHauteTemperatureEcs;
    }

    /**
     * Sets the value of the thetaAmontAirEauHauteTemperatureEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauHauteTemperatureEcs(String value) {
        this.thetaAmontAirEauHauteTemperatureEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauHauteTemperatureEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauHauteTemperatureEcs() {
        return thetaAvalEauGlycoleeEauHauteTemperatureEcs;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauHauteTemperatureEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauHauteTemperatureEcs(String value) {
        this.thetaAvalEauGlycoleeEauHauteTemperatureEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauHauteTemperatureEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEauHauteTemperatureEcs() {
        return thetaAmontEauGlycoleeEauHauteTemperatureEcs;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauHauteTemperatureEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEauHauteTemperatureEcs(String value) {
        this.thetaAmontEauGlycoleeEauHauteTemperatureEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauEauEcs() {
        return thetaAvalEauEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauEauEcs(String value) {
        this.thetaAvalEauEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauEauEcs() {
        return thetaAmontEauEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauEauEcs(String value) {
        this.thetaAmontEauEauEcs = value;
    }

    /**
     * Gets the value of the performanceEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformanceEcs() {
        return performanceEcs;
    }

    /**
     * Sets the value of the performanceEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformanceEcs(String value) {
        this.performanceEcs = value;
    }

    /**
     * Gets the value of the pabsEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsEcs() {
        return pabsEcs;
    }

    /**
     * Sets the value of the pabsEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsEcs(String value) {
        this.pabsEcs = value;
    }

    /**
     * Gets the value of the corEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOREcs() {
        return corEcs;
    }

    /**
     * Sets the value of the corEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOREcs(String value) {
        this.corEcs = value;
    }

    /**
     * Gets the value of the pauxPcEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxPcEcs() {
        return pauxPcEcs;
    }

    /**
     * Sets the value of the pauxPcEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxPcEcs(String value) {
        this.pauxPcEcs = value;
    }

    /**
     * Gets the value of the statutValPivotEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotEcs() {
        return statutValPivotEcs;
    }

    /**
     * Sets the value of the statutValPivotEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotEcs(String value) {
        this.statutValPivotEcs = value;
    }

    /**
     * Gets the value of the valGUEEcs property.
     * 
     */
    public double getValGUEEcs() {
        return valGUEEcs;
    }

    /**
     * Sets the value of the valGUEEcs property.
     * 
     */
    public void setValGUEEcs(double value) {
        this.valGUEEcs = value;
    }

    /**
     * Gets the value of the valPabsEcs property.
     * 
     */
    public double getValPabsEcs() {
        return valPabsEcs;
    }

    /**
     * Sets the value of the valPabsEcs property.
     * 
     */
    public void setValPabsEcs(double value) {
        this.valPabsEcs = value;
    }

    /**
     * Gets the value of the statutPauxPivotEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxPivotEcs() {
        return statutPauxPivotEcs;
    }

    /**
     * Sets the value of the statutPauxPivotEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxPivotEcs(String value) {
        this.statutPauxPivotEcs = value;
    }

    /**
     * Gets the value of the valPauxPcEcs property.
     * 
     */
    public double getValPauxPcEcs() {
        return valPauxPcEcs;
    }

    /**
     * Sets the value of the valPauxPcEcs property.
     * 
     */
    public void setValPauxPcEcs(double value) {
        this.valPauxPcEcs = value;
    }

    /**
     * Gets the value of the limThetaEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimThetaEcs() {
        return limThetaEcs;
    }

    /**
     * Sets the value of the limThetaEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimThetaEcs(String value) {
        this.limThetaEcs = value;
    }

    /**
     * Gets the value of the thetaMaxAvEcs property.
     * 
     */
    public double getThetaMaxAvEcs() {
        return thetaMaxAvEcs;
    }

    /**
     * Sets the value of the thetaMaxAvEcs property.
     * 
     */
    public void setThetaMaxAvEcs(double value) {
        this.thetaMaxAvEcs = value;
    }

    /**
     * Gets the value of the thetaMinAmEcs property.
     * 
     */
    public double getThetaMinAmEcs() {
        return thetaMinAmEcs;
    }

    /**
     * Sets the value of the thetaMinAmEcs property.
     * 
     */
    public void setThetaMinAmEcs(double value) {
        this.thetaMinAmEcs = value;
    }

    /**
     * Gets the value of the statutBruleurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutBruleurCh() {
        return statutBruleurCh;
    }

    /**
     * Sets the value of the statutBruleurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutBruleurCh(String value) {
        this.statutBruleurCh = value;
    }

    /**
     * Gets the value of the fonctionnementBruleurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementBruleurCh() {
        return fonctionnementBruleurCh;
    }

    /**
     * Sets the value of the fonctionnementBruleurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementBruleurCh(String value) {
        this.fonctionnementBruleurCh = value;
    }

    /**
     * Gets the value of the statutEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutEchangeur() {
        return statutEchangeur;
    }

    /**
     * Sets the value of the statutEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutEchangeur(String value) {
        this.statutEchangeur = value;
    }

    /**
     * Gets the value of the echangeurEauChFumee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEchangeurEauChFumee() {
        return echangeurEauChFumee;
    }

    /**
     * Sets the value of the echangeurEauChFumee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEchangeurEauChFumee(String value) {
        this.echangeurEauChFumee = value;
    }

    /**
     * Gets the value of the statutAutresDonneesCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutAutresDonneesCh() {
        return statutAutresDonneesCh;
    }

    /**
     * Sets the value of the statutAutresDonneesCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutAutresDonneesCh(String value) {
        this.statutAutresDonneesCh = value;
    }

    /**
     * Gets the value of the lRcontminCh property.
     * 
     */
    public double getLRcontminCh() {
        return lRcontminCh;
    }

    /**
     * Sets the value of the lRcontminCh property.
     * 
     */
    public void setLRcontminCh(double value) {
        this.lRcontminCh = value;
    }

    /**
     * Gets the value of the ccplRcontminCh property.
     * 
     */
    public double getCCPLRcontminCh() {
        return ccplRcontminCh;
    }

    /**
     * Sets the value of the ccplRcontminCh property.
     * 
     */
    public void setCCPLRcontminCh(double value) {
        this.ccplRcontminCh = value;
    }

    /**
     * Gets the value of the typoEmetteurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoEmetteurCh() {
        return typoEmetteurCh;
    }

    /**
     * Sets the value of the typoEmetteurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoEmetteurCh(String value) {
        this.typoEmetteurCh = value;
    }

    /**
     * Gets the value of the rdtComp property.
     * 
     */
    public double getRdtComp() {
        return rdtComp;
    }

    /**
     * Sets the value of the rdtComp property.
     * 
     */
    public void setRdtComp(double value) {
        this.rdtComp = value;
    }

    /**
     * Gets the value of the pertes40Deg property.
     * 
     */
    public double getPertes40Deg() {
        return pertes40Deg;
    }

    /**
     * Sets the value of the pertes40Deg property.
     * 
     */
    public void setPertes40Deg(double value) {
        this.pertes40Deg = value;
    }

    /**
     * Gets the value of the paux0 property.
     * 
     */
    public double getPaux0() {
        return paux0;
    }

    /**
     * Sets the value of the paux0 property.
     * 
     */
    public void setPaux0(double value) {
        this.paux0 = value;
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

}
