
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Capteur_PV complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Capteur_PV">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nb_capteurs_PV" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Type_Techno_Capteur" type="{}E_Type_Techno_Capteur"/>
 *         &lt;element name="Valeur_Declaree_Defaut" type="{}E_Valeur_Declaree_Justifiee_Certifiee_Defaut_Pv"/>
 *         &lt;element name="Pc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Mu" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="NOCT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Confinement" type="{}E_Coef_Confinement"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Masque_Collection" type="{}ArrayOfChoice4" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Capteur_PV", propOrder = {

})
public class RTDataCapteurPV {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Nb_capteurs_PV")
    protected int nbCapteursPV;
    @XmlElement(name = "Type_Techno_Capteur", required = true)
    protected String typeTechnoCapteur;
    @XmlElement(name = "Valeur_Declaree_Defaut", required = true)
    protected String valeurDeclareeDefaut;
    @XmlElement(name = "Pc")
    protected double pc;
    @XmlElement(name = "Mu")
    protected double mu;
    @XmlElement(name = "NOCT")
    protected double noct;
    @XmlElement(name = "Type_Confinement", required = true)
    protected String typeConfinement;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "S")
    protected double s;
    @XmlElement(name = "Masque_Collection")
    protected ArrayOfChoice4 masqueCollection;

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
     * Gets the value of the nbCapteursPV property.
     * 
     */
    public int getNbCapteursPV() {
        return nbCapteursPV;
    }

    /**
     * Sets the value of the nbCapteursPV property.
     * 
     */
    public void setNbCapteursPV(int value) {
        this.nbCapteursPV = value;
    }

    /**
     * Gets the value of the typeTechnoCapteur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeTechnoCapteur() {
        return typeTechnoCapteur;
    }

    /**
     * Sets the value of the typeTechnoCapteur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeTechnoCapteur(String value) {
        this.typeTechnoCapteur = value;
    }

    /**
     * Gets the value of the valeurDeclareeDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurDeclareeDefaut() {
        return valeurDeclareeDefaut;
    }

    /**
     * Sets the value of the valeurDeclareeDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurDeclareeDefaut(String value) {
        this.valeurDeclareeDefaut = value;
    }

    /**
     * Gets the value of the pc property.
     * 
     */
    public double getPc() {
        return pc;
    }

    /**
     * Sets the value of the pc property.
     * 
     */
    public void setPc(double value) {
        this.pc = value;
    }

    /**
     * Gets the value of the mu property.
     * 
     */
    public double getMu() {
        return mu;
    }

    /**
     * Sets the value of the mu property.
     * 
     */
    public void setMu(double value) {
        this.mu = value;
    }

    /**
     * Gets the value of the noct property.
     * 
     */
    public double getNOCT() {
        return noct;
    }

    /**
     * Sets the value of the noct property.
     * 
     */
    public void setNOCT(double value) {
        this.noct = value;
    }

    /**
     * Gets the value of the typeConfinement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeConfinement() {
        return typeConfinement;
    }

    /**
     * Sets the value of the typeConfinement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeConfinement(String value) {
        this.typeConfinement = value;
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
     * Gets the value of the s property.
     * 
     */
    public double getS() {
        return s;
    }

    /**
     * Sets the value of the s property.
     * 
     */
    public void setS(double value) {
        this.s = value;
    }

    /**
     * Gets the value of the masqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice4 }
     *     
     */
    public ArrayOfChoice4 getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice4 }
     *     
     */
    public void setMasqueCollection(ArrayOfChoice4 value) {
        this.masqueCollection = value;
    }

}
