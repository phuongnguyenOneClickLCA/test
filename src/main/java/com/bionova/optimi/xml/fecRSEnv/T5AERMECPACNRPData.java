
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_AERMEC_PAC_NRP - 4/3 add balise description
 * 
 * <p>Java class for T5_AERMEC_PAC_NRP_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_AERMEC_PAC_NRP_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}Data_Object_Base">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fl_Aval" type="{}E_Id_Fluide"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Performance_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ecs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ecs" type="{}E_Temperatures_Aval_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ecs" type="{}E_Temperatures_Amont_Ecs"/>
 *         &lt;element name="Lim_Theta_Ecs" type="{}E_Lim_Theta"/>
 *         &lt;element name="Theta_Max_Av_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnee_Ecs" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Cop_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_Ecs" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Performance_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ch" type="{}E_Temperatures_Aval_Ch"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ch" type="{}E_Temperatures_Amont_Ch"/>
 *         &lt;element name="Statut_Taux_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_Theta"/>
 *         &lt;element name="Theta_Max_Av_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur_Ch" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRContmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Type_Emetteur"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Cop_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Performance_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Fr" type="{}E_Temperatures_Aval_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Fr" type="{}E_Temperatures_Amont_Fr"/>
 *         &lt;element name="Lim_Theta_Fr" type="{}E_Lim_Theta"/>
 *         &lt;element name="Theta_Min_Av_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Am_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur_Fr" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonct_Part_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRContmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Fr" type="{}E_Type_Emetteur"/>
 *         &lt;element name="Statut_Donnee_Fr" type="{}E_Existe_Valeur_Certifiee_Mesuree"/>
 *         &lt;element name="Val_Eer" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Pivot_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Type_installation" type="{}E_Type_installation"/>
 *         &lt;element name="Statut_Donnee_Recup" type="{}E_Statut_Donnee_Recup"/>
 *         &lt;element name="Pth_recup" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pabs_recup" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_AERMEC_PAC_NRP_Data", propOrder = {
    "index",
    "name",
    "description",
    "idSourceAmont",
    "rdim",
    "idFlAval",
    "idprioriteEcs",
    "idprioriteCh",
    "idprioriteFr",
    "performanceEcs",
    "pabsEcs",
    "corEcs",
    "thetaAvalAirEauEcs",
    "thetaAmontAirEauEcs",
    "limThetaEcs",
    "thetaMaxAvEcs",
    "thetaMinAmEcs",
    "statutDonneeEcs",
    "valCopEcs",
    "valPabsEcs",
    "statutValPivotEcs",
    "performanceCh",
    "pabsCh",
    "corCh",
    "thetaAvalAirEauCh",
    "thetaAmontAirEauCh",
    "statutTauxCh",
    "tauxCh",
    "limThetaCh",
    "thetaMaxAvCh",
    "thetaMinAmCh",
    "fonctionnementCompresseurCh",
    "statutFonctPartCh",
    "statutFonctionnementContinuCh",
    "lrContminCh",
    "ccplRcontminCh",
    "typoEmetteurCh",
    "statutDonneeCh",
    "valCopCh",
    "valPabsCh",
    "statutValPivotCh",
    "performanceFr",
    "pabsFr",
    "corFr",
    "thetaAvalAirEauFr",
    "thetaAmontAirEauFr",
    "limThetaFr",
    "thetaMinAvFr",
    "thetaMaxAmFr",
    "fonctionnementCompresseurFr",
    "statutFonctPartFr",
    "statutFonctionnementContinuFr",
    "lrContminFr",
    "ccplRcontminFr",
    "typoEmetteurFr",
    "statutDonneeFr",
    "valEer",
    "valPabsFr",
    "statutValPivotFr",
    "typeInstallation",
    "statutDonneeRecup",
    "pthRecup",
    "pabsRecup"
})
public class T5AERMECPACNRPData
    extends DataObjectBase
{

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Id_Fl_Aval", required = true)
    protected String idFlAval;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Performance_Ecs")
    protected String performanceEcs;
    @XmlElement(name = "Pabs_Ecs")
    protected String pabsEcs;
    @XmlElement(name = "COR_Ecs")
    protected String corEcs;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ecs", required = true)
    protected String thetaAvalAirEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ecs", required = true)
    protected String thetaAmontAirEauEcs;
    @XmlElement(name = "Lim_Theta_Ecs", required = true)
    protected String limThetaEcs;
    @XmlElement(name = "Theta_Max_Av_Ecs")
    protected double thetaMaxAvEcs;
    @XmlElement(name = "Theta_Min_Am_Ecs")
    protected double thetaMinAmEcs;
    @XmlElement(name = "Statut_Donnee_Ecs", required = true)
    protected String statutDonneeEcs;
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
    @XmlElement(name = "Theta_Aval_Air_Eau_Ch", required = true)
    protected String thetaAvalAirEauCh;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ch", required = true)
    protected String thetaAmontAirEauCh;
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
    @XmlElement(name = "LRContmin_Ch")
    protected double lrContminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    protected String typoEmetteurCh;
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
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    protected String thetaAmontAirEauFr;
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
    @XmlElement(name = "LRContmin_Fr")
    protected double lrContminFr;
    @XmlElement(name = "CCP_LRcontmin_Fr")
    protected double ccplRcontminFr;
    @XmlElement(name = "Typo_Emetteur_Fr", required = true)
    protected String typoEmetteurFr;
    @XmlElement(name = "Statut_Donnee_Fr", required = true)
    protected String statutDonneeFr;
    @XmlElement(name = "Val_Eer")
    protected double valEer;
    @XmlElement(name = "Val_Pabs_Fr")
    protected double valPabsFr;
    @XmlElement(name = "Statut_Val_Pivot_Fr", required = true)
    protected String statutValPivotFr;
    @XmlElement(name = "Type_installation", required = true)
    protected String typeInstallation;
    @XmlElement(name = "Statut_Donnee_Recup", required = true)
    protected String statutDonneeRecup;
    @XmlElement(name = "Pth_recup")
    protected double pthRecup;
    @XmlElement(name = "Pabs_recup")
    protected double pabsRecup;

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
     * Gets the value of the idprioriteFr property.
     * 
     */
    public int getIdprioriteFr() {
        return idprioriteFr;
    }

    /**
     * Sets the value of the idprioriteFr property.
     * 
     */
    public void setIdprioriteFr(int value) {
        this.idprioriteFr = value;
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
     * Gets the value of the thetaAvalAirEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauCh() {
        return thetaAvalAirEauCh;
    }

    /**
     * Sets the value of the thetaAvalAirEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauCh(String value) {
        this.thetaAvalAirEauCh = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauCh() {
        return thetaAmontAirEauCh;
    }

    /**
     * Sets the value of the thetaAmontAirEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauCh(String value) {
        this.thetaAmontAirEauCh = value;
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
     * Gets the value of the lrContminCh property.
     * 
     */
    public double getLRContminCh() {
        return lrContminCh;
    }

    /**
     * Sets the value of the lrContminCh property.
     * 
     */
    public void setLRContminCh(double value) {
        this.lrContminCh = value;
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
     * Gets the value of the thetaAvalAirEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauFr() {
        return thetaAvalAirEauFr;
    }

    /**
     * Sets the value of the thetaAvalAirEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauFr(String value) {
        this.thetaAvalAirEauFr = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauFr() {
        return thetaAmontAirEauFr;
    }

    /**
     * Sets the value of the thetaAmontAirEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauFr(String value) {
        this.thetaAmontAirEauFr = value;
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
     * Gets the value of the lrContminFr property.
     * 
     */
    public double getLRContminFr() {
        return lrContminFr;
    }

    /**
     * Sets the value of the lrContminFr property.
     * 
     */
    public void setLRContminFr(double value) {
        this.lrContminFr = value;
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
     * Gets the value of the typoEmetteurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoEmetteurFr() {
        return typoEmetteurFr;
    }

    /**
     * Sets the value of the typoEmetteurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoEmetteurFr(String value) {
        this.typoEmetteurFr = value;
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

    /**
     * Gets the value of the typeInstallation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeInstallation() {
        return typeInstallation;
    }

    /**
     * Sets the value of the typeInstallation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeInstallation(String value) {
        this.typeInstallation = value;
    }

    /**
     * Gets the value of the statutDonneeRecup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneeRecup() {
        return statutDonneeRecup;
    }

    /**
     * Sets the value of the statutDonneeRecup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneeRecup(String value) {
        this.statutDonneeRecup = value;
    }

    /**
     * Gets the value of the pthRecup property.
     * 
     */
    public double getPthRecup() {
        return pthRecup;
    }

    /**
     * Sets the value of the pthRecup property.
     * 
     */
    public void setPthRecup(double value) {
        this.pthRecup = value;
    }

    /**
     * Gets the value of the pabsRecup property.
     * 
     */
    public double getPabsRecup() {
        return pabsRecup;
    }

    /**
     * Sets the value of the pabsRecup property.
     * 
     */
    public void setPabsRecup(double value) {
        this.pabsRecup = value;
    }

}
