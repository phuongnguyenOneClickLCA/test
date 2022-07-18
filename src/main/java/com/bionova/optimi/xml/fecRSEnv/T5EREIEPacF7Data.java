
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_EREIE_PacF7
 * 
 * <p>Java class for T5_EREIE_PacF7_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_EREIE_PacF7_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_val_pivot" type="{}E_Val_Declaree_Defaut"/>
 *         &lt;element name="ValECS_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ValPabs_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_donnees" type="{}E_Ex_Valeur_Certifiee"/>
 *         &lt;element name="ValECS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValPabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValCOR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ppompe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_min_am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_max_av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ECS_EG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_UA_s" type="{}E_Val_Certifiee_Justifiee_Defaut_Ballon"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "T5_EREIE_PacF7_Data", propOrder = {

})
public class T5EREIEPacF7Data {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Statut_val_pivot", required = true)
    protected String statutValPivot;
    @XmlElement(name = "ValECS_pivot")
    protected double valECSPivot;
    @XmlElement(name = "ValPabs_pivot")
    protected double valPabsPivot;
    @XmlElement(name = "Statut_donnees", required = true)
    protected String statutDonnees;
    @XmlElement(name = "ValECS")
    protected String valECS;
    @XmlElement(name = "ValPabs")
    protected String valPabs;
    @XmlElement(name = "ValCOR")
    protected String valCOR;
    @XmlElement(name = "Ppompe")
    protected double ppompe;
    @XmlElement(name = "Lim_Theta")
    protected double limTheta;
    @XmlElement(name = "Theta_min_am")
    protected double thetaMinAm;
    @XmlElement(name = "Theta_max_av")
    protected double thetaMaxAv;
    @XmlElement(name = "ECS_EG")
    protected String ecseg;
    @XmlElement(name = "Statut_UA_s", required = true)
    protected String statutUAS;
    @XmlElement(name = "V_tot")
    protected double vTot;
    @XmlElement(name = "UA_s")
    protected double uas;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the statutValPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivot() {
        return statutValPivot;
    }

    /**
     * Sets the value of the statutValPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivot(String value) {
        this.statutValPivot = value;
    }

    /**
     * Gets the value of the valECSPivot property.
     * 
     */
    public double getValECSPivot() {
        return valECSPivot;
    }

    /**
     * Sets the value of the valECSPivot property.
     * 
     */
    public void setValECSPivot(double value) {
        this.valECSPivot = value;
    }

    /**
     * Gets the value of the valPabsPivot property.
     * 
     */
    public double getValPabsPivot() {
        return valPabsPivot;
    }

    /**
     * Sets the value of the valPabsPivot property.
     * 
     */
    public void setValPabsPivot(double value) {
        this.valPabsPivot = value;
    }

    /**
     * Gets the value of the statutDonnees property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonnees() {
        return statutDonnees;
    }

    /**
     * Sets the value of the statutDonnees property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonnees(String value) {
        this.statutDonnees = value;
    }

    /**
     * Gets the value of the valECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValECS() {
        return valECS;
    }

    /**
     * Sets the value of the valECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValECS(String value) {
        this.valECS = value;
    }

    /**
     * Gets the value of the valPabs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValPabs() {
        return valPabs;
    }

    /**
     * Sets the value of the valPabs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValPabs(String value) {
        this.valPabs = value;
    }

    /**
     * Gets the value of the valCOR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValCOR() {
        return valCOR;
    }

    /**
     * Sets the value of the valCOR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValCOR(String value) {
        this.valCOR = value;
    }

    /**
     * Gets the value of the ppompe property.
     * 
     */
    public double getPpompe() {
        return ppompe;
    }

    /**
     * Sets the value of the ppompe property.
     * 
     */
    public void setPpompe(double value) {
        this.ppompe = value;
    }

    /**
     * Gets the value of the limTheta property.
     * 
     */
    public double getLimTheta() {
        return limTheta;
    }

    /**
     * Sets the value of the limTheta property.
     * 
     */
    public void setLimTheta(double value) {
        this.limTheta = value;
    }

    /**
     * Gets the value of the thetaMinAm property.
     * 
     */
    public double getThetaMinAm() {
        return thetaMinAm;
    }

    /**
     * Sets the value of the thetaMinAm property.
     * 
     */
    public void setThetaMinAm(double value) {
        this.thetaMinAm = value;
    }

    /**
     * Gets the value of the thetaMaxAv property.
     * 
     */
    public double getThetaMaxAv() {
        return thetaMaxAv;
    }

    /**
     * Sets the value of the thetaMaxAv property.
     * 
     */
    public void setThetaMaxAv(double value) {
        this.thetaMaxAv = value;
    }

    /**
     * Gets the value of the ecseg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getECSEG() {
        return ecseg;
    }

    /**
     * Sets the value of the ecseg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setECSEG(String value) {
        this.ecseg = value;
    }

    /**
     * Gets the value of the statutUAS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutUAS() {
        return statutUAS;
    }

    /**
     * Sets the value of the statutUAS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutUAS(String value) {
        this.statutUAS = value;
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
