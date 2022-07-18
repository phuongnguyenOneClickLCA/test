
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for T5_CSTB_Appoint_Thermodynamique_ECS_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CSTB_Appoint_Thermodynamique_ECS_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Is_Resistance_Elec" type="{}E_Presence_Resistance_Electrique"/>
 *         &lt;element name="P_nom_Resistance_Elec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rat_faux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sys_Thermo" type="{}E_Systeme_Thermodynamique_Double_Service"/>
 *         &lt;element name="Statut_Donnee_Ecs" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Extrait_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Ambiant_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_De_Nappe_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_De_Nappe_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Sol_Eau_Ecs" type="{}E_Temperatures_Aval_Sol_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Sol_Eau_Ecs" type="{}E_Temperatures_Amont_Sol_Eau_Ecs"/>
 *         &lt;element name="Performance_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Cop_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ecs" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Fonct_Part" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Temp_Aval" type="{}ArrayOfDouble" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CSTB_Appoint_Thermodynamique_ECS_Data", propOrder = {

})
public class T5CSTBAppointThermodynamiqueECSData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Is_Resistance_Elec", required = true)
    protected String isResistanceElec;
    @XmlElement(name = "P_nom_Resistance_Elec")
    protected double pNomResistanceElec;
    @XmlElement(name = "Rat_faux")
    protected double ratFaux;
    @XmlElement(name = "Sys_Thermo", required = true)
    protected String sysThermo;
    @XmlElement(name = "Statut_Donnee_Ecs", required = true)
    protected String statutDonneeEcs;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ecs", required = true)
    protected String thetaAvalAirEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ecs", required = true)
    protected String thetaAmontAirEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Eau_Ecs", required = true)
    protected String thetaAvalAirExtraitEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Eau_Ecs", required = true)
    protected String thetaAmontAirExtraitEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Ambiant_Eau_Ecs", required = true)
    protected String thetaAvalAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Ambiant_Eau_Ecs", required = true)
    protected String thetaAmontAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau_Ecs", required = true)
    protected String thetaAvalEauDeNappeEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau_Ecs", required = true)
    protected String thetaAmontEauDeNappeEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Ecs", required = true)
    protected String thetaAvalEauGlycoleeEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Ecs", required = true)
    protected String thetaAmontEauGlycoleeEauEcs;
    @XmlElement(name = "Theta_Aval_Sol_Eau_Ecs", required = true)
    protected String thetaAvalSolEauEcs;
    @XmlElement(name = "Theta_Amont_Sol_Eau_Ecs", required = true)
    protected String thetaAmontSolEauEcs;
    @XmlElement(name = "Performance_Ecs")
    protected String performanceEcs;
    @XmlElement(name = "Pabs_Ecs")
    protected String pabsEcs;
    @XmlElement(name = "COR_Ecs")
    protected String corEcs;
    @XmlElement(name = "Statut_Val_Pivot_Ecs", required = true)
    protected String statutValPivotEcs;
    @XmlElement(name = "Val_Cop_Ecs")
    protected double valCopEcs;
    @XmlElement(name = "Val_Pabs_Ecs")
    protected double valPabsEcs;
    @XmlElement(name = "Lim_Theta_Ecs", required = true)
    protected String limThetaEcs;
    @XmlElement(name = "Theta_Max_Av_Ecs")
    protected double thetaMaxAvEcs;
    @XmlElement(name = "Theta_Min_Am_Ecs")
    protected double thetaMinAmEcs;
    @XmlElement(name = "Statut_Fonct_Part", required = true)
    protected String statutFonctPart;
    @XmlElement(name = "Fonctionnement_Compresseur", required = true)
    protected String fonctionnementCompresseur;
    @XmlElement(name = "Statut_Fonctionnement_Continu", required = true)
    protected String statutFonctionnementContinu;
    @XmlElement(name = "LRcontmin")
    protected double lRcontmin;
    @XmlElement(name = "CCP_LRcontmin")
    protected double ccplRcontmin;
    @XmlElement(name = "Statut_Taux", required = true)
    protected String statutTaux;
    @XmlElement(name = "Taux")
    protected double taux;
    @XmlElement(name = "Val_Temp_Aval")
    protected ArrayOfDouble valTempAval;

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
     * Gets the value of the isResistanceElec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsResistanceElec() {
        return isResistanceElec;
    }

    /**
     * Sets the value of the isResistanceElec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsResistanceElec(String value) {
        this.isResistanceElec = value;
    }

    /**
     * Gets the value of the pNomResistanceElec property.
     * 
     */
    public double getPNomResistanceElec() {
        return pNomResistanceElec;
    }

    /**
     * Sets the value of the pNomResistanceElec property.
     * 
     */
    public void setPNomResistanceElec(double value) {
        this.pNomResistanceElec = value;
    }

    /**
     * Gets the value of the ratFaux property.
     * 
     */
    public double getRatFaux() {
        return ratFaux;
    }

    /**
     * Sets the value of the ratFaux property.
     * 
     */
    public void setRatFaux(double value) {
        this.ratFaux = value;
    }

    /**
     * Gets the value of the sysThermo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermo() {
        return sysThermo;
    }

    /**
     * Sets the value of the sysThermo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermo(String value) {
        this.sysThermo = value;
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
     * Gets the value of the thetaAvalAirEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauEcs() {
        return thetaAvalAirEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauEcs(String value) {
        this.thetaAvalAirEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauEcs() {
        return thetaAmontAirEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauEcs(String value) {
        this.thetaAmontAirEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirExtraitEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirExtraitEauEcs() {
        return thetaAvalAirExtraitEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirExtraitEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirExtraitEauEcs(String value) {
        this.thetaAvalAirExtraitEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirExtraitEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirExtraitEauEcs() {
        return thetaAmontAirExtraitEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirExtraitEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirExtraitEauEcs(String value) {
        this.thetaAmontAirExtraitEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirAmbiantEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirAmbiantEauEcs() {
        return thetaAvalAirAmbiantEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirAmbiantEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirAmbiantEauEcs(String value) {
        this.thetaAvalAirAmbiantEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirAmbiantEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirAmbiantEauEcs() {
        return thetaAmontAirAmbiantEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirAmbiantEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirAmbiantEauEcs(String value) {
        this.thetaAmontAirAmbiantEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeNappeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauDeNappeEauEcs() {
        return thetaAvalEauDeNappeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauDeNappeEauEcs(String value) {
        this.thetaAvalEauDeNappeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauDeNappeEauEcs() {
        return thetaAmontEauDeNappeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauDeNappeEauEcs(String value) {
        this.thetaAmontEauDeNappeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauGlycoleeEauEcs() {
        return thetaAvalEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauGlycoleeEauEcs(String value) {
        this.thetaAvalEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauGlycoleeEauEcs() {
        return thetaAmontEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauGlycoleeEauEcs(String value) {
        this.thetaAmontEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalSolEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalSolEauEcs() {
        return thetaAvalSolEauEcs;
    }

    /**
     * Sets the value of the thetaAvalSolEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalSolEauEcs(String value) {
        this.thetaAvalSolEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontSolEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontSolEauEcs() {
        return thetaAmontSolEauEcs;
    }

    /**
     * Sets the value of the thetaAmontSolEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontSolEauEcs(String value) {
        this.thetaAmontSolEauEcs = value;
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
     * Gets the value of the valCopEcs property.
     * 
     */
    public double getValCopEcs() {
        return valCopEcs;
    }

    /**
     * Sets the value of the valCopEcs property.
     * 
     */
    public void setValCopEcs(double value) {
        this.valCopEcs = value;
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
     * Gets the value of the statutFonctPart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPart() {
        return statutFonctPart;
    }

    /**
     * Sets the value of the statutFonctPart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPart(String value) {
        this.statutFonctPart = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseur() {
        return fonctionnementCompresseur;
    }

    /**
     * Sets the value of the fonctionnementCompresseur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseur(String value) {
        this.fonctionnementCompresseur = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinu() {
        return statutFonctionnementContinu;
    }

    /**
     * Sets the value of the statutFonctionnementContinu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinu(String value) {
        this.statutFonctionnementContinu = value;
    }

    /**
     * Gets the value of the lRcontmin property.
     * 
     */
    public double getLRcontmin() {
        return lRcontmin;
    }

    /**
     * Sets the value of the lRcontmin property.
     * 
     */
    public void setLRcontmin(double value) {
        this.lRcontmin = value;
    }

    /**
     * Gets the value of the ccplRcontmin property.
     * 
     */
    public double getCCPLRcontmin() {
        return ccplRcontmin;
    }

    /**
     * Sets the value of the ccplRcontmin property.
     * 
     */
    public void setCCPLRcontmin(double value) {
        this.ccplRcontmin = value;
    }

    /**
     * Gets the value of the statutTaux property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutTaux() {
        return statutTaux;
    }

    /**
     * Sets the value of the statutTaux property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutTaux(String value) {
        this.statutTaux = value;
    }

    /**
     * Gets the value of the taux property.
     * 
     */
    public double getTaux() {
        return taux;
    }

    /**
     * Sets the value of the taux property.
     * 
     */
    public void setTaux(double value) {
        this.taux = value;
    }

    /**
     * Gets the value of the valTempAval property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getValTempAval() {
        return valTempAval;
    }

    /**
     * Sets the value of the valTempAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setValTempAval(ArrayOfDouble value) {
        this.valTempAval = value;
    }

}
