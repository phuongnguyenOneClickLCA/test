
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Onduleur_PV complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Onduleur_PV">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_CourbeRend" type="{}E_choix_Courbe_Rend"/>
 *         &lt;element name="n_EU" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ValeurRefCourbe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Valeur_Declaree_Defaut_Pac_Onduleur" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Pac_Onduleur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Capteur_PV_Collection" type="{}ArrayOfRT_Data_Capteur_PV" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Onduleur_PV", propOrder = {

})
public class RTDataOnduleurPV {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_CourbeRend", required = true)
    protected String typeCourbeRend;
    @XmlElement(name = "n_EU")
    protected double neu;
    @XmlElement(name = "ValeurRefCourbe")
    protected String valeurRefCourbe;
    @XmlElement(name = "Valeur_Declaree_Defaut_Pac_Onduleur", required = true)
    protected String valeurDeclareeDefautPacOnduleur;
    @XmlElement(name = "Pac_Onduleur")
    protected double pacOnduleur;
    @XmlElement(name = "Capteur_PV_Collection")
    protected ArrayOfRTDataCapteurPV capteurPVCollection;

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
     * Gets the value of the typeCourbeRend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeCourbeRend() {
        return typeCourbeRend;
    }

    /**
     * Sets the value of the typeCourbeRend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeCourbeRend(String value) {
        this.typeCourbeRend = value;
    }

    /**
     * Gets the value of the neu property.
     * 
     */
    public double getNEU() {
        return neu;
    }

    /**
     * Sets the value of the neu property.
     * 
     */
    public void setNEU(double value) {
        this.neu = value;
    }

    /**
     * Gets the value of the valeurRefCourbe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurRefCourbe() {
        return valeurRefCourbe;
    }

    /**
     * Sets the value of the valeurRefCourbe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurRefCourbe(String value) {
        this.valeurRefCourbe = value;
    }

    /**
     * Gets the value of the valeurDeclareeDefautPacOnduleur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurDeclareeDefautPacOnduleur() {
        return valeurDeclareeDefautPacOnduleur;
    }

    /**
     * Sets the value of the valeurDeclareeDefautPacOnduleur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurDeclareeDefautPacOnduleur(String value) {
        this.valeurDeclareeDefautPacOnduleur = value;
    }

    /**
     * Gets the value of the pacOnduleur property.
     * 
     */
    public double getPacOnduleur() {
        return pacOnduleur;
    }

    /**
     * Sets the value of the pacOnduleur property.
     * 
     */
    public void setPacOnduleur(double value) {
        this.pacOnduleur = value;
    }

    /**
     * Gets the value of the capteurPVCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataCapteurPV }
     *     
     */
    public ArrayOfRTDataCapteurPV getCapteurPVCollection() {
        return capteurPVCollection;
    }

    /**
     * Sets the value of the capteurPVCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataCapteurPV }
     *     
     */
    public void setCapteurPVCollection(ArrayOfRTDataCapteurPV value) {
        this.capteurPVCollection = value;
    }

}
