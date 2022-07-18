
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Batiment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Batiment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Reseau" type="{}RT_TypesResau"/>
 *         &lt;element name="RatENR_rdch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="RatENR_rdfr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Espace_Tampon_Collection" type="{}ArrayOfChoice3" minOccurs="0"/>
 *         &lt;element name="PV_install_Collection" type="{}ArrayOfRT_Data_PV_install" minOccurs="0"/>
 *         &lt;element name="Zone_Collection" type="{}ArrayOfRT_Data_Zone" minOccurs="0"/>
 *         &lt;element name="Ascenseur_Collection" type="{}ArrayOfRT_Data_Ascenseur" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Batiment", propOrder = {

})
public class RTDataBatiment {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_Reseau", required = true)
    protected String typeReseau;
    @XmlElement(name = "RatENR_rdch")
    protected double ratENRRdch;
    @XmlElement(name = "RatENR_rdfr")
    protected double ratENRRdfr;
    @XmlElement(name = "Espace_Tampon_Collection")
    protected ArrayOfChoice3 espaceTamponCollection;
    @XmlElement(name = "PV_install_Collection")
    protected ArrayOfRTDataPVInstall pvInstallCollection;
    @XmlElement(name = "Zone_Collection")
    protected ArrayOfRTDataZone zoneCollection;
    @XmlElement(name = "Ascenseur_Collection")
    protected ArrayOfRTDataAscenseur ascenseurCollection;

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
     * Gets the value of the typeReseau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeReseau() {
        return typeReseau;
    }

    /**
     * Sets the value of the typeReseau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeReseau(String value) {
        this.typeReseau = value;
    }

    /**
     * Gets the value of the ratENRRdch property.
     * 
     */
    public double getRatENRRdch() {
        return ratENRRdch;
    }

    /**
     * Sets the value of the ratENRRdch property.
     * 
     */
    public void setRatENRRdch(double value) {
        this.ratENRRdch = value;
    }

    /**
     * Gets the value of the ratENRRdfr property.
     * 
     */
    public double getRatENRRdfr() {
        return ratENRRdfr;
    }

    /**
     * Sets the value of the ratENRRdfr property.
     * 
     */
    public void setRatENRRdfr(double value) {
        this.ratENRRdfr = value;
    }

    /**
     * Gets the value of the espaceTamponCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice3 }
     *     
     */
    public ArrayOfChoice3 getEspaceTamponCollection() {
        return espaceTamponCollection;
    }

    /**
     * Sets the value of the espaceTamponCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice3 }
     *     
     */
    public void setEspaceTamponCollection(ArrayOfChoice3 value) {
        this.espaceTamponCollection = value;
    }

    /**
     * Gets the value of the pvInstallCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataPVInstall }
     *     
     */
    public ArrayOfRTDataPVInstall getPVInstallCollection() {
        return pvInstallCollection;
    }

    /**
     * Sets the value of the pvInstallCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataPVInstall }
     *     
     */
    public void setPVInstallCollection(ArrayOfRTDataPVInstall value) {
        this.pvInstallCollection = value;
    }

    /**
     * Gets the value of the zoneCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataZone }
     *     
     */
    public ArrayOfRTDataZone getZoneCollection() {
        return zoneCollection;
    }

    /**
     * Sets the value of the zoneCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataZone }
     *     
     */
    public void setZoneCollection(ArrayOfRTDataZone value) {
        this.zoneCollection = value;
    }

    /**
     * Gets the value of the ascenseurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataAscenseur }
     *     
     */
    public ArrayOfRTDataAscenseur getAscenseurCollection() {
        return ascenseurCollection;
    }

    /**
     * Sets the value of the ascenseurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataAscenseur }
     *     
     */
    public void setAscenseurCollection(ArrayOfRTDataAscenseur value) {
        this.ascenseurCollection = value;
    }

}
