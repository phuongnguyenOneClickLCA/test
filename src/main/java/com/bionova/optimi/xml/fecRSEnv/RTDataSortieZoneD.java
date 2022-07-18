
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Zone_D complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Zone_D">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="O_Cep_PH_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_PH_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cep_BA_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_BA_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_BA_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_BA_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Cef_PH_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_PH_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="Sortie_Groupe_D_Collection" type="{}ArrayOfRT_Data_Sortie_Groupe_D" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Zone_D", propOrder = {

})
public class RTDataSortieZoneD {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "O_Cep_PH_annuel")
    protected double oCepPHAnnuel;
    @XmlElement(name = "O_Cep_PH_mois")
    protected ArrayOfRTDataSortieMensuelle oCepPHMois;
    @XmlElement(name = "O_Cep_BA_annuel")
    protected double oCepBAAnnuel;
    @XmlElement(name = "O_Cep_BA_mois")
    protected ArrayOfRTDataSortieMensuelle oCepBAMois;
    @XmlElement(name = "O_Cef_BA_annuel")
    protected Double oCefBAAnnuel;
    @XmlElement(name = "O_Cef_BA_mois")
    protected ArrayOfRTDataSortieMensuelle oCefBAMois;
    @XmlElement(name = "O_Cef_PH_annuel")
    protected Double oCefPHAnnuel;
    @XmlElement(name = "O_Cef_PH_mois")
    protected ArrayOfRTDataSortieMensuelle oCefPHMois;
    @XmlElement(name = "Sortie_Groupe_D_Collection")
    protected ArrayOfRTDataSortieGroupeD sortieGroupeDCollection;

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
     * Gets the value of the oCepPHAnnuel property.
     * 
     */
    public double getOCepPHAnnuel() {
        return oCepPHAnnuel;
    }

    /**
     * Sets the value of the oCepPHAnnuel property.
     * 
     */
    public void setOCepPHAnnuel(double value) {
        this.oCepPHAnnuel = value;
    }

    /**
     * Gets the value of the oCepPHMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepPHMois() {
        return oCepPHMois;
    }

    /**
     * Sets the value of the oCepPHMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepPHMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepPHMois = value;
    }

    /**
     * Gets the value of the oCepBAAnnuel property.
     * 
     */
    public double getOCepBAAnnuel() {
        return oCepBAAnnuel;
    }

    /**
     * Sets the value of the oCepBAAnnuel property.
     * 
     */
    public void setOCepBAAnnuel(double value) {
        this.oCepBAAnnuel = value;
    }

    /**
     * Gets the value of the oCepBAMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepBAMois() {
        return oCepBAMois;
    }

    /**
     * Sets the value of the oCepBAMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepBAMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepBAMois = value;
    }

    /**
     * Gets the value of the oCefBAAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefBAAnnuel() {
        return oCefBAAnnuel;
    }

    /**
     * Sets the value of the oCefBAAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefBAAnnuel(Double value) {
        this.oCefBAAnnuel = value;
    }

    /**
     * Gets the value of the oCefBAMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCefBAMois() {
        return oCefBAMois;
    }

    /**
     * Sets the value of the oCefBAMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCefBAMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefBAMois = value;
    }

    /**
     * Gets the value of the oCefPHAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefPHAnnuel() {
        return oCefPHAnnuel;
    }

    /**
     * Sets the value of the oCefPHAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefPHAnnuel(Double value) {
        this.oCefPHAnnuel = value;
    }

    /**
     * Gets the value of the oCefPHMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCefPHMois() {
        return oCefPHMois;
    }

    /**
     * Sets the value of the oCefPHMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCefPHMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefPHMois = value;
    }

    /**
     * Gets the value of the sortieGroupeDCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGroupeD }
     *     
     */
    public ArrayOfRTDataSortieGroupeD getSortieGroupeDCollection() {
        return sortieGroupeDCollection;
    }

    /**
     * Sets the value of the sortieGroupeDCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGroupeD }
     *     
     */
    public void setSortieGroupeDCollection(ArrayOfRTDataSortieGroupeD value) {
        this.sortieGroupeDCollection = value;
    }

}
