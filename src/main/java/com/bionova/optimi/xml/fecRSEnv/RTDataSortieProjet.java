
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Projet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Projet">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Departement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Zone_Climatique_Ete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Zone_Climatique_Hiver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Altitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Batiment_B_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_B" minOccurs="0"/>
 *         &lt;element name="Sortie_Batiment_C_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_C" minOccurs="0"/>
 *         &lt;element name="Sortie_Batiment_D_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_D" minOccurs="0"/>
 *         &lt;element name="Sortie_Batiment_E_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_E" minOccurs="0"/>
 *         &lt;element name="Sortie_Generation_Collection" type="{}ArrayOfRT_Data_Sortie_Generation" minOccurs="0"/>
 *         &lt;element name="Sortie_PCAD_Collection" type="{}ArrayOfRT_Data_Sortie_PCAD" minOccurs="0"/>
 *         &lt;element name="Sortie_Parking_Collection" type="{}ArrayOfRT_Data_Sortie_Parking_C" minOccurs="0"/>
 *         &lt;element name="Sortie_Sensibilite_Batiment_Collection" type="{}ArrayOfRT_Data_Sortie_Sensibilite_Batiment" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Projet", propOrder = {
    "version",
    "departement",
    "zoneClimatiqueEte",
    "zoneClimatiqueHiver",
    "altitude",
    "sortieBatimentBCollection",
    "sortieBatimentCCollection",
    "sortieBatimentDCollection",
    "sortieBatimentECollection",
    "sortieGenerationCollection",
    "sortiePCADCollection",
    "sortieParkingCollection",
    "sortieSensibiliteBatimentCollection"
})
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.RSET.SortieProjet.class
})
public class RTDataSortieProjet
    extends RTDataSortieBase
{

    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "Departement")
    protected int departement;
    @XmlElement(name = "Zone_Climatique_Ete")
    protected String zoneClimatiqueEte;
    @XmlElement(name = "Zone_Climatique_Hiver")
    protected String zoneClimatiqueHiver;
    @XmlElement(name = "Altitude")
    protected double altitude;
    @XmlElement(name = "Sortie_Batiment_B_Collection")
    protected ArrayOfRTDataSortieBatimentB sortieBatimentBCollection;
    @XmlElement(name = "Sortie_Batiment_C_Collection")
    protected ArrayOfRTDataSortieBatimentC sortieBatimentCCollection;
    @XmlElement(name = "Sortie_Batiment_D_Collection")
    protected ArrayOfRTDataSortieBatimentD sortieBatimentDCollection;
    @XmlElement(name = "Sortie_Batiment_E_Collection")
    protected ArrayOfRTDataSortieBatimentE sortieBatimentECollection;
    @XmlElement(name = "Sortie_Generation_Collection")
    protected ArrayOfRTDataSortieGeneration sortieGenerationCollection;
    @XmlElement(name = "Sortie_PCAD_Collection")
    protected ArrayOfRTDataSortiePCAD sortiePCADCollection;
    @XmlElement(name = "Sortie_Parking_Collection")
    protected ArrayOfRTDataSortieParkingC sortieParkingCollection;
    @XmlElement(name = "Sortie_Sensibilite_Batiment_Collection")
    protected ArrayOfRTDataSortieSensibiliteBatiment sortieSensibiliteBatimentCollection;

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the departement property.
     * 
     */
    public int getDepartement() {
        return departement;
    }

    /**
     * Sets the value of the departement property.
     * 
     */
    public void setDepartement(int value) {
        this.departement = value;
    }

    /**
     * Gets the value of the zoneClimatiqueEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneClimatiqueEte() {
        return zoneClimatiqueEte;
    }

    /**
     * Sets the value of the zoneClimatiqueEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneClimatiqueEte(String value) {
        this.zoneClimatiqueEte = value;
    }

    /**
     * Gets the value of the zoneClimatiqueHiver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneClimatiqueHiver() {
        return zoneClimatiqueHiver;
    }

    /**
     * Sets the value of the zoneClimatiqueHiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneClimatiqueHiver(String value) {
        this.zoneClimatiqueHiver = value;
    }

    /**
     * Gets the value of the altitude property.
     * 
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Sets the value of the altitude property.
     * 
     */
    public void setAltitude(double value) {
        this.altitude = value;
    }

    /**
     * Gets the value of the sortieBatimentBCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieBatimentB }
     *     
     */
    public ArrayOfRTDataSortieBatimentB getSortieBatimentBCollection() {
        return sortieBatimentBCollection;
    }

    /**
     * Sets the value of the sortieBatimentBCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieBatimentB }
     *     
     */
    public void setSortieBatimentBCollection(ArrayOfRTDataSortieBatimentB value) {
        this.sortieBatimentBCollection = value;
    }

    /**
     * Gets the value of the sortieBatimentCCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieBatimentC }
     *     
     */
    public ArrayOfRTDataSortieBatimentC getSortieBatimentCCollection() {
        return sortieBatimentCCollection;
    }

    /**
     * Sets the value of the sortieBatimentCCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieBatimentC }
     *     
     */
    public void setSortieBatimentCCollection(ArrayOfRTDataSortieBatimentC value) {
        this.sortieBatimentCCollection = value;
    }

    /**
     * Gets the value of the sortieBatimentDCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieBatimentD }
     *     
     */
    public ArrayOfRTDataSortieBatimentD getSortieBatimentDCollection() {
        return sortieBatimentDCollection;
    }

    /**
     * Sets the value of the sortieBatimentDCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieBatimentD }
     *     
     */
    public void setSortieBatimentDCollection(ArrayOfRTDataSortieBatimentD value) {
        this.sortieBatimentDCollection = value;
    }

    /**
     * Gets the value of the sortieBatimentECollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieBatimentE }
     *     
     */
    public ArrayOfRTDataSortieBatimentE getSortieBatimentECollection() {
        return sortieBatimentECollection;
    }

    /**
     * Sets the value of the sortieBatimentECollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieBatimentE }
     *     
     */
    public void setSortieBatimentECollection(ArrayOfRTDataSortieBatimentE value) {
        this.sortieBatimentECollection = value;
    }

    /**
     * Gets the value of the sortieGenerationCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGeneration }
     *     
     */
    public ArrayOfRTDataSortieGeneration getSortieGenerationCollection() {
        return sortieGenerationCollection;
    }

    /**
     * Sets the value of the sortieGenerationCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGeneration }
     *     
     */
    public void setSortieGenerationCollection(ArrayOfRTDataSortieGeneration value) {
        this.sortieGenerationCollection = value;
    }

    /**
     * Gets the value of the sortiePCADCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortiePCAD }
     *     
     */
    public ArrayOfRTDataSortiePCAD getSortiePCADCollection() {
        return sortiePCADCollection;
    }

    /**
     * Sets the value of the sortiePCADCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortiePCAD }
     *     
     */
    public void setSortiePCADCollection(ArrayOfRTDataSortiePCAD value) {
        this.sortiePCADCollection = value;
    }

    /**
     * Gets the value of the sortieParkingCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieParkingC }
     *     
     */
    public ArrayOfRTDataSortieParkingC getSortieParkingCollection() {
        return sortieParkingCollection;
    }

    /**
     * Sets the value of the sortieParkingCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieParkingC }
     *     
     */
    public void setSortieParkingCollection(ArrayOfRTDataSortieParkingC value) {
        this.sortieParkingCollection = value;
    }

    /**
     * Gets the value of the sortieSensibiliteBatimentCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieSensibiliteBatiment }
     *     
     */
    public ArrayOfRTDataSortieSensibiliteBatiment getSortieSensibiliteBatimentCollection() {
        return sortieSensibiliteBatimentCollection;
    }

    /**
     * Sets the value of the sortieSensibiliteBatimentCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieSensibiliteBatiment }
     *     
     */
    public void setSortieSensibiliteBatimentCollection(ArrayOfRTDataSortieSensibiliteBatiment value) {
        this.sortieSensibiliteBatimentCollection = value;
    }

}
