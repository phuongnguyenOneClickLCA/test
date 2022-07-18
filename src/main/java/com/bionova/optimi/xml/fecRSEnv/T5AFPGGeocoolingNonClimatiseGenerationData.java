
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for T5_AFPG_Geocooling_NonClimatise_Generation_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_AFPG_Geocooling_NonClimatise_Generation_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Lvc_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Uvc_moy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_dist" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcirc_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="id_circ" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Theta_d_geo_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_hyst_geo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_r_source_lim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_amont" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_geo_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qv_nom_geo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_AFPG_Geocooling_NonClimatise_Generation_Data", propOrder = {

})
public class T5AFPGGeocoolingNonClimatiseGenerationData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Lvc_tot")
    protected double lvcTot;
    @XmlElement(name = "Uvc_moy")
    protected double uvcMoy;
    @XmlElement(name = "b_dist")
    protected double bDist;
    @XmlElement(name = "Pcirc_nom")
    protected double pcircNom;
    @XmlElement(name = "id_circ")
    protected int idCirc;
    @XmlElement(name = "Theta_d_geo_nom")
    protected double thetaDGeoNom;
    @XmlElement(name = "Delta_Theta_hyst_geo")
    protected double deltaThetaHystGeo;
    @XmlElement(name = "Theta_r_source_lim")
    protected double thetaRSourceLim;
    @XmlElement(name = "Delta_Theta_amont")
    protected double deltaThetaAmont;
    @XmlElement(name = "UA_geo_nom")
    protected double uaGeoNom;
    @XmlElement(name = "qv_nom_geo")
    protected double qvNomGeo;

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
     * Gets the value of the lvcTot property.
     * 
     */
    public double getLvcTot() {
        return lvcTot;
    }

    /**
     * Sets the value of the lvcTot property.
     * 
     */
    public void setLvcTot(double value) {
        this.lvcTot = value;
    }

    /**
     * Gets the value of the uvcMoy property.
     * 
     */
    public double getUvcMoy() {
        return uvcMoy;
    }

    /**
     * Sets the value of the uvcMoy property.
     * 
     */
    public void setUvcMoy(double value) {
        this.uvcMoy = value;
    }

    /**
     * Gets the value of the bDist property.
     * 
     */
    public double getBDist() {
        return bDist;
    }

    /**
     * Sets the value of the bDist property.
     * 
     */
    public void setBDist(double value) {
        this.bDist = value;
    }

    /**
     * Gets the value of the pcircNom property.
     * 
     */
    public double getPcircNom() {
        return pcircNom;
    }

    /**
     * Sets the value of the pcircNom property.
     * 
     */
    public void setPcircNom(double value) {
        this.pcircNom = value;
    }

    /**
     * Gets the value of the idCirc property.
     * 
     */
    public int getIdCirc() {
        return idCirc;
    }

    /**
     * Sets the value of the idCirc property.
     * 
     */
    public void setIdCirc(int value) {
        this.idCirc = value;
    }

    /**
     * Gets the value of the thetaDGeoNom property.
     * 
     */
    public double getThetaDGeoNom() {
        return thetaDGeoNom;
    }

    /**
     * Sets the value of the thetaDGeoNom property.
     * 
     */
    public void setThetaDGeoNom(double value) {
        this.thetaDGeoNom = value;
    }

    /**
     * Gets the value of the deltaThetaHystGeo property.
     * 
     */
    public double getDeltaThetaHystGeo() {
        return deltaThetaHystGeo;
    }

    /**
     * Sets the value of the deltaThetaHystGeo property.
     * 
     */
    public void setDeltaThetaHystGeo(double value) {
        this.deltaThetaHystGeo = value;
    }

    /**
     * Gets the value of the thetaRSourceLim property.
     * 
     */
    public double getThetaRSourceLim() {
        return thetaRSourceLim;
    }

    /**
     * Sets the value of the thetaRSourceLim property.
     * 
     */
    public void setThetaRSourceLim(double value) {
        this.thetaRSourceLim = value;
    }

    /**
     * Gets the value of the deltaThetaAmont property.
     * 
     */
    public double getDeltaThetaAmont() {
        return deltaThetaAmont;
    }

    /**
     * Sets the value of the deltaThetaAmont property.
     * 
     */
    public void setDeltaThetaAmont(double value) {
        this.deltaThetaAmont = value;
    }

    /**
     * Gets the value of the uaGeoNom property.
     * 
     */
    public double getUAGeoNom() {
        return uaGeoNom;
    }

    /**
     * Sets the value of the uaGeoNom property.
     * 
     */
    public void setUAGeoNom(double value) {
        this.uaGeoNom = value;
    }

    /**
     * Gets the value of the qvNomGeo property.
     * 
     */
    public double getQvNomGeo() {
        return qvNomGeo;
    }

    /**
     * Sets the value of the qvNomGeo property.
     * 
     */
    public void setQvNomGeo(double value) {
        this.qvNomGeo = value;
    }

}
