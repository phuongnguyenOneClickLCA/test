
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_GECO_PKOM4_CLASSIC
 * 
 * <p>Java class for T5_GECO_PKOM4_CLASSIC_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_GECO_PKOM4_CLASSIC_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Qv_Air_evap_tot_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Performance_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_Ecs" type="{}E_Temperatures_Aval_Ecs"/>
 *         &lt;element name="Theta_Amont_Ecs" type="{}E_Temperatures_Amont_Ecs"/>
 *         &lt;element name="Lim_Theta_Ecs" type="{}E_Lim_Theta"/>
 *         &lt;element name="Theta_Max_Av_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnee_Ecs" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Fonctionnement_Compresseur_Ecs" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Ecs" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Cop_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Performance_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Taux_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_Theta"/>
 *         &lt;element name="Theta_Max_Av_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur_Ch" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Cop_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Performance_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lim_Theta_Fr" type="{}E_Lim_Theta"/>
 *         &lt;element name="Theta_Min_Av_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Am_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur_Fr" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnee_Fr" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Eer" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_GECO_PKOM4_CLASSIC_Data", propOrder = {

})
public class T5GECOPKOM4CLASSICData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Qv_Air_evap_tot_Ecs")
    protected double qvAirEvapTotEcs;
    @XmlElement(name = "Performance_Ecs")
    protected String performanceEcs;
    @XmlElement(name = "Pabs_Ecs")
    protected String pabsEcs;
    @XmlElement(name = "COR_Ecs")
    protected String corEcs;
    @XmlElement(name = "Theta_Aval_Ecs", required = true)
    protected String thetaAvalEcs;
    @XmlElement(name = "Theta_Amont_Ecs", required = true)
    protected String thetaAmontEcs;
    @XmlElement(name = "Lim_Theta_Ecs", required = true)
    protected String limThetaEcs;
    @XmlElement(name = "Theta_Max_Av_Ecs")
    protected double thetaMaxAvEcs;
    @XmlElement(name = "Theta_Min_Am_Ecs")
    protected double thetaMinAmEcs;
    @XmlElement(name = "Statut_Donnee_Ecs", required = true)
    protected String statutDonneeEcs;
    @XmlElement(name = "Fonctionnement_Compresseur_Ecs", required = true)
    protected String fonctionnementCompresseurEcs;
    @XmlElement(name = "Statut_Fonct_Part_Ecs", required = true)
    protected String statutFonctPartEcs;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Ecs", required = true)
    protected String statutFonctionnementContinuEcs;
    @XmlElement(name = "LRcontmin_Ecs")
    protected double lRcontminEcs;
    @XmlElement(name = "CCP_LRcontmin_Ecs")
    protected double ccplRcontminEcs;
    @XmlElement(name = "Val_Cop_Ecs")
    protected double valCopEcs;
    @XmlElement(name = "Val_Pabs_Ecs")
    protected double valPabsEcs;
    @XmlElement(name = "Statut_Val_Pivot_Ecs", required = true)
    protected String statutValPivotEcs;
    @XmlElement(name = "Performance_Ch")
    protected String performanceCh;
    @XmlElement(name = "Pabs_Ch")
    protected String pabsCh;
    @XmlElement(name = "COR_Ch")
    protected String corCh;
    @XmlElement(name = "Statut_Taux_Ch", required = true)
    protected String statutTauxCh;
    @XmlElement(name = "Taux_Ch")
    protected double tauxCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av_Ch")
    protected double thetaMaxAvCh;
    @XmlElement(name = "Theta_Min_Am_Ch")
    protected double thetaMinAmCh;
    @XmlElement(name = "Fonctionnement_Compresseur_Ch", required = true)
    protected String fonctionnementCompresseurCh;
    @XmlElement(name = "Statut_Fonct_Part_Ch", required = true)
    protected String statutFonctPartCh;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Ch", required = true)
    protected String statutFonctionnementContinuCh;
    @XmlElement(name = "LRcontmin_Ch")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    protected String statutDonneeCh;
    @XmlElement(name = "Val_Cop_Ch")
    protected double valCopCh;
    @XmlElement(name = "Val_Pabs_Ch")
    protected double valPabsCh;
    @XmlElement(name = "Statut_Val_Pivot_Ch", required = true)
    protected String statutValPivotCh;
    @XmlElement(name = "Performance_Fr")
    protected String performanceFr;
    @XmlElement(name = "Pabs_Fr")
    protected String pabsFr;
    @XmlElement(name = "COR_Fr")
    protected String corFr;
    @XmlElement(name = "Lim_Theta_Fr", required = true)
    protected String limThetaFr;
    @XmlElement(name = "Theta_Min_Av_Fr")
    protected double thetaMinAvFr;
    @XmlElement(name = "Theta_Max_Am_Fr")
    protected double thetaMaxAmFr;
    @XmlElement(name = "Fonctionnement_Compresseur_Fr", required = true)
    protected String fonctionnementCompresseurFr;
    @XmlElement(name = "Statut_Fonct_Part_Fr", required = true)
    protected String statutFonctPartFr;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Fr", required = true)
    protected String statutFonctionnementContinuFr;
    @XmlElement(name = "LRcontmin_Fr")
    protected double lRcontminFr;
    @XmlElement(name = "CCP_LRcontmin_Fr")
    protected double ccplRcontminFr;
    @XmlElement(name = "Statut_Donnee_Fr", required = true)
    protected String statutDonneeFr;
    @XmlElement(name = "Val_Eer")
    protected double valEer;
    @XmlElement(name = "Val_Pabs_Fr")
    protected double valPabsFr;
    @XmlElement(name = "Statut_Val_Pivot_Fr", required = true)
    protected String statutValPivotFr;

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
     * Gets the value of the qvAirEvapTotEcs property.
     * 
     */
    public double getQvAirEvapTotEcs() {
        return qvAirEvapTotEcs;
    }

    /**
     * Sets the value of the qvAirEvapTotEcs property.
     * 
     */
    public void setQvAirEvapTotEcs(double value) {
        this.qvAirEvapTotEcs = value;
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
     * Gets the value of the thetaAvalEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEcs() {
        return thetaAvalEcs;
    }

    /**
     * Sets the value of the thetaAvalEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEcs(String value) {
        this.thetaAvalEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEcs() {
        return thetaAmontEcs;
    }

    /**
     * Sets the value of the thetaAmontEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEcs(String value) {
        this.thetaAmontEcs = value;
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
     * Gets the value of the fonctionnementCompresseurEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseurEcs() {
        return fonctionnementCompresseurEcs;
    }

    /**
     * Sets the value of the fonctionnementCompresseurEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseurEcs(String value) {
        this.fonctionnementCompresseurEcs = value;
    }

    /**
     * Gets the value of the statutFonctPartEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPartEcs() {
        return statutFonctPartEcs;
    }

    /**
     * Sets the value of the statutFonctPartEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPartEcs(String value) {
        this.statutFonctPartEcs = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinuEcs() {
        return statutFonctionnementContinuEcs;
    }

    /**
     * Sets the value of the statutFonctionnementContinuEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinuEcs(String value) {
        this.statutFonctionnementContinuEcs = value;
    }

    /**
     * Gets the value of the lRcontminEcs property.
     * 
     */
    public double getLRcontminEcs() {
        return lRcontminEcs;
    }

    /**
     * Sets the value of the lRcontminEcs property.
     * 
     */
    public void setLRcontminEcs(double value) {
        this.lRcontminEcs = value;
    }

    /**
     * Gets the value of the ccplRcontminEcs property.
     * 
     */
    public double getCCPLRcontminEcs() {
        return ccplRcontminEcs;
    }

    /**
     * Sets the value of the ccplRcontminEcs property.
     * 
     */
    public void setCCPLRcontminEcs(double value) {
        this.ccplRcontminEcs = value;
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
     * Gets the value of the statutTauxCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutTauxCh() {
        return statutTauxCh;
    }

    /**
     * Sets the value of the statutTauxCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutTauxCh(String value) {
        this.statutTauxCh = value;
    }

    /**
     * Gets the value of the tauxCh property.
     * 
     */
    public double getTauxCh() {
        return tauxCh;
    }

    /**
     * Sets the value of the tauxCh property.
     * 
     */
    public void setTauxCh(double value) {
        this.tauxCh = value;
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
     * Gets the value of the fonctionnementCompresseurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseurCh() {
        return fonctionnementCompresseurCh;
    }

    /**
     * Sets the value of the fonctionnementCompresseurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseurCh(String value) {
        this.fonctionnementCompresseurCh = value;
    }

    /**
     * Gets the value of the statutFonctPartCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPartCh() {
        return statutFonctPartCh;
    }

    /**
     * Sets the value of the statutFonctPartCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPartCh(String value) {
        this.statutFonctPartCh = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinuCh() {
        return statutFonctionnementContinuCh;
    }

    /**
     * Sets the value of the statutFonctionnementContinuCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinuCh(String value) {
        this.statutFonctionnementContinuCh = value;
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
     * Gets the value of the valCopCh property.
     * 
     */
    public double getValCopCh() {
        return valCopCh;
    }

    /**
     * Sets the value of the valCopCh property.
     * 
     */
    public void setValCopCh(double value) {
        this.valCopCh = value;
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
     * Gets the value of the performanceFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformanceFr() {
        return performanceFr;
    }

    /**
     * Sets the value of the performanceFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformanceFr(String value) {
        this.performanceFr = value;
    }

    /**
     * Gets the value of the pabsFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsFr() {
        return pabsFr;
    }

    /**
     * Sets the value of the pabsFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsFr(String value) {
        this.pabsFr = value;
    }

    /**
     * Gets the value of the corFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCORFr() {
        return corFr;
    }

    /**
     * Sets the value of the corFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCORFr(String value) {
        this.corFr = value;
    }

    /**
     * Gets the value of the limThetaFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimThetaFr() {
        return limThetaFr;
    }

    /**
     * Sets the value of the limThetaFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimThetaFr(String value) {
        this.limThetaFr = value;
    }

    /**
     * Gets the value of the thetaMinAvFr property.
     * 
     */
    public double getThetaMinAvFr() {
        return thetaMinAvFr;
    }

    /**
     * Sets the value of the thetaMinAvFr property.
     * 
     */
    public void setThetaMinAvFr(double value) {
        this.thetaMinAvFr = value;
    }

    /**
     * Gets the value of the thetaMaxAmFr property.
     * 
     */
    public double getThetaMaxAmFr() {
        return thetaMaxAmFr;
    }

    /**
     * Sets the value of the thetaMaxAmFr property.
     * 
     */
    public void setThetaMaxAmFr(double value) {
        this.thetaMaxAmFr = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseurFr() {
        return fonctionnementCompresseurFr;
    }

    /**
     * Sets the value of the fonctionnementCompresseurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseurFr(String value) {
        this.fonctionnementCompresseurFr = value;
    }

    /**
     * Gets the value of the statutFonctPartFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctPartFr() {
        return statutFonctPartFr;
    }

    /**
     * Sets the value of the statutFonctPartFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctPartFr(String value) {
        this.statutFonctPartFr = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinuFr() {
        return statutFonctionnementContinuFr;
    }

    /**
     * Sets the value of the statutFonctionnementContinuFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinuFr(String value) {
        this.statutFonctionnementContinuFr = value;
    }

    /**
     * Gets the value of the lRcontminFr property.
     * 
     */
    public double getLRcontminFr() {
        return lRcontminFr;
    }

    /**
     * Sets the value of the lRcontminFr property.
     * 
     */
    public void setLRcontminFr(double value) {
        this.lRcontminFr = value;
    }

    /**
     * Gets the value of the ccplRcontminFr property.
     * 
     */
    public double getCCPLRcontminFr() {
        return ccplRcontminFr;
    }

    /**
     * Sets the value of the ccplRcontminFr property.
     * 
     */
    public void setCCPLRcontminFr(double value) {
        this.ccplRcontminFr = value;
    }

    /**
     * Gets the value of the statutDonneeFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeFr() {
        return statutDonneeFr;
    }

    /**
     * Sets the value of the statutDonneeFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeFr(String value) {
        this.statutDonneeFr = value;
    }

    /**
     * Gets the value of the valEer property.
     * 
     */
    public double getValEer() {
        return valEer;
    }

    /**
     * Sets the value of the valEer property.
     * 
     */
    public void setValEer(double value) {
        this.valEer = value;
    }

    /**
     * Gets the value of the valPabsFr property.
     * 
     */
    public double getValPabsFr() {
        return valPabsFr;
    }

    /**
     * Sets the value of the valPabsFr property.
     * 
     */
    public void setValPabsFr(double value) {
        this.valPabsFr = value;
    }

    /**
     * Gets the value of the statutValPivotFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivotFr() {
        return statutValPivotFr;
    }

    /**
     * Sets the value of the statutValPivotFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivotFr(String value) {
        this.statutValPivotFr = value;
    }

}
