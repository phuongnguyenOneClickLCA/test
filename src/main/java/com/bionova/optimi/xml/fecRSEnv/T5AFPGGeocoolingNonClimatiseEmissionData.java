
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for T5_AFPG_Geocooling_NonClimatise_Emission_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_AFPG_Geocooling_NonClimatise_Emission_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}Data_Object_Base">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_dist" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Typologie_Emetteur_Froid" type="{}E_Typologie_Emetteur_Fr"/>
 *         &lt;element name="Pem_conv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pper_em" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Kem_100" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Kem_30" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qv_nom_em" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qv_resid_em" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_vcv_mv_em" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_vcv_veille_em" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lvc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Uvc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_AFPG_Geocooling_NonClimatise_Emission_Data", propOrder = {
    "name",
    "description",
    "index",
    "idDist",
    "typologieEmetteurFroid",
    "pemConv",
    "pperEm",
    "kem100",
    "kem30",
    "qvNomEm",
    "qvResidEm",
    "pVcvMvEm",
    "pVcvVeilleEm",
    "lvc",
    "uvc"
})
public class T5AFPGGeocoolingNonClimatiseEmissionData
    extends DataObjectBase
{

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Id_dist")
    protected int idDist;
    @XmlElement(name = "Typologie_Emetteur_Froid", required = true)
    protected String typologieEmetteurFroid;
    @XmlElement(name = "Pem_conv")
    protected double pemConv;
    @XmlElement(name = "Pper_em")
    protected double pperEm;
    @XmlElement(name = "Kem_100")
    protected double kem100;
    @XmlElement(name = "Kem_30")
    protected double kem30;
    @XmlElement(name = "qv_nom_em")
    protected double qvNomEm;
    @XmlElement(name = "qv_resid_em")
    protected double qvResidEm;
    @XmlElement(name = "P_vcv_mv_em")
    protected double pVcvMvEm;
    @XmlElement(name = "P_vcv_veille_em")
    protected double pVcvVeilleEm;
    @XmlElement(name = "Lvc")
    protected double lvc;
    @XmlElement(name = "Uvc")
    protected double uvc;

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
     * Gets the value of the idDist property.
     * 
     */
    public int getIdDist() {
        return idDist;
    }

    /**
     * Sets the value of the idDist property.
     * 
     */
    public void setIdDist(int value) {
        this.idDist = value;
    }

    /**
     * Gets the value of the typologieEmetteurFroid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypologieEmetteurFroid() {
        return typologieEmetteurFroid;
    }

    /**
     * Sets the value of the typologieEmetteurFroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypologieEmetteurFroid(String value) {
        this.typologieEmetteurFroid = value;
    }

    /**
     * Gets the value of the pemConv property.
     * 
     */
    public double getPemConv() {
        return pemConv;
    }

    /**
     * Sets the value of the pemConv property.
     * 
     */
    public void setPemConv(double value) {
        this.pemConv = value;
    }

    /**
     * Gets the value of the pperEm property.
     * 
     */
    public double getPperEm() {
        return pperEm;
    }

    /**
     * Sets the value of the pperEm property.
     * 
     */
    public void setPperEm(double value) {
        this.pperEm = value;
    }

    /**
     * Gets the value of the kem100 property.
     * 
     */
    public double getKem100() {
        return kem100;
    }

    /**
     * Sets the value of the kem100 property.
     * 
     */
    public void setKem100(double value) {
        this.kem100 = value;
    }

    /**
     * Gets the value of the kem30 property.
     * 
     */
    public double getKem30() {
        return kem30;
    }

    /**
     * Sets the value of the kem30 property.
     * 
     */
    public void setKem30(double value) {
        this.kem30 = value;
    }

    /**
     * Gets the value of the qvNomEm property.
     * 
     */
    public double getQvNomEm() {
        return qvNomEm;
    }

    /**
     * Sets the value of the qvNomEm property.
     * 
     */
    public void setQvNomEm(double value) {
        this.qvNomEm = value;
    }

    /**
     * Gets the value of the qvResidEm property.
     * 
     */
    public double getQvResidEm() {
        return qvResidEm;
    }

    /**
     * Sets the value of the qvResidEm property.
     * 
     */
    public void setQvResidEm(double value) {
        this.qvResidEm = value;
    }

    /**
     * Gets the value of the pVcvMvEm property.
     * 
     */
    public double getPVcvMvEm() {
        return pVcvMvEm;
    }

    /**
     * Sets the value of the pVcvMvEm property.
     * 
     */
    public void setPVcvMvEm(double value) {
        this.pVcvMvEm = value;
    }

    /**
     * Gets the value of the pVcvVeilleEm property.
     * 
     */
    public double getPVcvVeilleEm() {
        return pVcvVeilleEm;
    }

    /**
     * Sets the value of the pVcvVeilleEm property.
     * 
     */
    public void setPVcvVeilleEm(double value) {
        this.pVcvVeilleEm = value;
    }

    /**
     * Gets the value of the lvc property.
     * 
     */
    public double getLvc() {
        return lvc;
    }

    /**
     * Sets the value of the lvc property.
     * 
     */
    public void setLvc(double value) {
        this.lvc = value;
    }

    /**
     * Gets the value of the uvc property.
     * 
     */
    public double getUvc() {
        return uvc;
    }

    /**
     * Sets the value of the uvc property.
     * 
     */
    public void setUvc(double value) {
        this.uvc = value;
    }

}
