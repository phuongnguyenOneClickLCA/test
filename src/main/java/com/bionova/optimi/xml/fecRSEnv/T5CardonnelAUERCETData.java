
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7400 - T5 Cardonnel AUER
 * 
 * <p>Java class for T5_Cardonnel_AUER_CET_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Cardonnel_AUER_CET_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="theta_max_source" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_2nd_Ch" type="{}E_Gest_2nd_Ch"/>
 *         &lt;element name="Statut_Val_Pivot_Ecs" type="{}E_Valeur_Certifiee_Justifiee_Declaree_Defaut"/>
 *         &lt;element name="Val_Cop_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ecs" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Cardonnel_AUER_CET_Data", propOrder = {

})
public class T5CardonnelAUERCETData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "theta_max_source")
    protected double thetaMaxSource;
    @XmlElement(name = "Gest_2nd_Ch", required = true)
    protected String gest2NdCh;
    @XmlElement(name = "Statut_Val_Pivot_Ecs", required = true)
    protected String statutValPivotEcs;
    @XmlElement(name = "Val_Cop_Ecs")
    protected double valCopEcs;
    @XmlElement(name = "Val_Pabs_Ecs")
    protected double valPabsEcs;
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
    @XmlElement(name = "Lim_Theta_Ecs", required = true)
    protected String limThetaEcs;
    @XmlElement(name = "Theta_Max_Av_Ecs")
    protected double thetaMaxAvEcs;
    @XmlElement(name = "Theta_Min_Am_Ecs")
    protected double thetaMinAmEcs;

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
     * Gets the value of the thetaMaxSource property.
     * 
     */
    public double getThetaMaxSource() {
        return thetaMaxSource;
    }

    /**
     * Sets the value of the thetaMaxSource property.
     * 
     */
    public void setThetaMaxSource(double value) {
        this.thetaMaxSource = value;
    }

    /**
     * Gets the value of the gest2NdCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGest2NdCh() {
        return gest2NdCh;
    }

    /**
     * Sets the value of the gest2NdCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGest2NdCh(String value) {
        this.gest2NdCh = value;
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

}
