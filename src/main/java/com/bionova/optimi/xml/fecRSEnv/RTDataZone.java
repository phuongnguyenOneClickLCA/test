
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Zone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Zone">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Usage" type="{}E_Usage"/>
 *         &lt;element name="SHON_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="is_Rat_l" type="{}E_Is_Rat_l"/>
 *         &lt;element name="Rat_l" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NB_logement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Faisa_Scenario_Personnalise" type="{}RT_Oui_Non" minOccurs="0"/>
 *         &lt;element name="Faisa_Fichier_Scenario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FullPathScenario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hauteur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Hauteur_Zone" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Traversant" type="{}RT_Oui_Non"/>
 *         &lt;element name="Groupe_Collection" type="{}ArrayOfRT_Data_Groupe" minOccurs="0"/>
 *         &lt;element name="Ventilation_Mecanique_Collection" type="{}ArrayOfRT_Data_Ventilation_Mecanique" minOccurs="0"/>
 *         &lt;element name="Puits_Climatique_Collection" type="{}ArrayOfRT_Data_Puits_Climatique" minOccurs="0"/>
 *         &lt;element name="Puits_Hydraulique_Collection" type="{}ArrayOfRT_Data_Puits_Hydraulique" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Zone", propOrder = {

})
public class RTDataZone {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Usage", required = true)
    protected String usage;
    @XmlElement(name = "SHON_RT")
    protected double shonrt;
    @XmlElement(name = "is_Rat_l", required = true)
    protected String isRatL;
    @XmlElement(name = "Rat_l")
    protected String ratL;
    @XmlElement(name = "NB_logement")
    protected int nbLogement;
    @XmlElement(name = "Faisa_Scenario_Personnalise")
    protected String faisaScenarioPersonnalise;
    @XmlElement(name = "Faisa_Fichier_Scenario")
    protected String faisaFichierScenario;
    @XmlElement(name = "FullPathScenario")
    protected String fullPathScenario;
    @XmlElement(name = "Hauteur")
    protected double hauteur;
    @XmlElement(name = "Hauteur_Zone")
    protected double hauteurZone;
    @XmlElement(name = "Is_Traversant", required = true)
    protected String isTraversant;
    @XmlElement(name = "Groupe_Collection")
    protected ArrayOfRTDataGroupe groupeCollection;
    @XmlElement(name = "Ventilation_Mecanique_Collection")
    protected ArrayOfRTDataVentilationMecanique ventilationMecaniqueCollection;
    @XmlElement(name = "Puits_Climatique_Collection")
    protected ArrayOfRTDataPuitsClimatique puitsClimatiqueCollection;
    @XmlElement(name = "Puits_Hydraulique_Collection")
    protected ArrayOfRTDataPuitsHydraulique puitsHydrauliqueCollection;

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
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsage(String value) {
        this.usage = value;
    }

    /**
     * Gets the value of the shonrt property.
     * 
     */
    public double getSHONRT() {
        return shonrt;
    }

    /**
     * Sets the value of the shonrt property.
     * 
     */
    public void setSHONRT(double value) {
        this.shonrt = value;
    }

    /**
     * Gets the value of the isRatL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRatL() {
        return isRatL;
    }

    /**
     * Sets the value of the isRatL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRatL(String value) {
        this.isRatL = value;
    }

    /**
     * Gets the value of the ratL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatL() {
        return ratL;
    }

    /**
     * Sets the value of the ratL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatL(String value) {
        this.ratL = value;
    }

    /**
     * Gets the value of the nbLogement property.
     * 
     */
    public int getNBLogement() {
        return nbLogement;
    }

    /**
     * Sets the value of the nbLogement property.
     * 
     */
    public void setNBLogement(int value) {
        this.nbLogement = value;
    }

    /**
     * Gets the value of the faisaScenarioPersonnalise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaisaScenarioPersonnalise() {
        return faisaScenarioPersonnalise;
    }

    /**
     * Sets the value of the faisaScenarioPersonnalise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaisaScenarioPersonnalise(String value) {
        this.faisaScenarioPersonnalise = value;
    }

    /**
     * Gets the value of the faisaFichierScenario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaisaFichierScenario() {
        return faisaFichierScenario;
    }

    /**
     * Sets the value of the faisaFichierScenario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaisaFichierScenario(String value) {
        this.faisaFichierScenario = value;
    }

    /**
     * Gets the value of the fullPathScenario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullPathScenario() {
        return fullPathScenario;
    }

    /**
     * Sets the value of the fullPathScenario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullPathScenario(String value) {
        this.fullPathScenario = value;
    }

    /**
     * Gets the value of the hauteur property.
     * 
     */
    public double getHauteur() {
        return hauteur;
    }

    /**
     * Sets the value of the hauteur property.
     * 
     */
    public void setHauteur(double value) {
        this.hauteur = value;
    }

    /**
     * Gets the value of the hauteurZone property.
     * 
     */
    public double getHauteurZone() {
        return hauteurZone;
    }

    /**
     * Sets the value of the hauteurZone property.
     * 
     */
    public void setHauteurZone(double value) {
        this.hauteurZone = value;
    }

    /**
     * Gets the value of the isTraversant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsTraversant() {
        return isTraversant;
    }

    /**
     * Sets the value of the isTraversant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsTraversant(String value) {
        this.isTraversant = value;
    }

    /**
     * Gets the value of the groupeCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataGroupe }
     *     
     */
    public ArrayOfRTDataGroupe getGroupeCollection() {
        return groupeCollection;
    }

    /**
     * Sets the value of the groupeCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataGroupe }
     *     
     */
    public void setGroupeCollection(ArrayOfRTDataGroupe value) {
        this.groupeCollection = value;
    }

    /**
     * Gets the value of the ventilationMecaniqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataVentilationMecanique }
     *     
     */
    public ArrayOfRTDataVentilationMecanique getVentilationMecaniqueCollection() {
        return ventilationMecaniqueCollection;
    }

    /**
     * Sets the value of the ventilationMecaniqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataVentilationMecanique }
     *     
     */
    public void setVentilationMecaniqueCollection(ArrayOfRTDataVentilationMecanique value) {
        this.ventilationMecaniqueCollection = value;
    }

    /**
     * Gets the value of the puitsClimatiqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataPuitsClimatique }
     *     
     */
    public ArrayOfRTDataPuitsClimatique getPuitsClimatiqueCollection() {
        return puitsClimatiqueCollection;
    }

    /**
     * Sets the value of the puitsClimatiqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataPuitsClimatique }
     *     
     */
    public void setPuitsClimatiqueCollection(ArrayOfRTDataPuitsClimatique value) {
        this.puitsClimatiqueCollection = value;
    }

    /**
     * Gets the value of the puitsHydrauliqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataPuitsHydraulique }
     *     
     */
    public ArrayOfRTDataPuitsHydraulique getPuitsHydrauliqueCollection() {
        return puitsHydrauliqueCollection;
    }

    /**
     * Sets the value of the puitsHydrauliqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataPuitsHydraulique }
     *     
     */
    public void setPuitsHydrauliqueCollection(ArrayOfRTDataPuitsHydraulique value) {
        this.puitsHydrauliqueCollection = value;
    }

}
