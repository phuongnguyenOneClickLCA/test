
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7000 - extension T5_TERREAL_LAHEROOF
 * 
 * <p>Java class for T5_TERREAL_LAHEROOF_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_TERREAL_LAHEROOF_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paroi_Ext_ET_Collection" type="{}ArrayOfRT_Data_Paroi_Ext_ET" minOccurs="0"/>
 *         &lt;element name="id_comble" type="{}E_type_comble"/>
 *         &lt;element name="id_alpha_tuile" type="{}E_alpha_tuile"/>
 *         &lt;element name="alpha_tuile_mesure" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b_therm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_TERREAL_LAHEROOF_Data", propOrder = {

})
public class T5TERREALLAHEROOFData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Paroi_Ext_ET_Collection")
    protected ArrayOfRTDataParoiExtET paroiExtETCollection;
    @XmlElement(name = "id_comble", required = true)
    protected String idComble;
    @XmlElement(name = "id_alpha_tuile", required = true)
    protected String idAlphaTuile;
    @XmlElement(name = "alpha_tuile_mesure")
    protected double alphaTuileMesure;
    @XmlElement(name = "b_therm")
    protected double bTherm;

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
     * Gets the value of the paroiExtETCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataParoiExtET }
     *     
     */
    public ArrayOfRTDataParoiExtET getParoiExtETCollection() {
        return paroiExtETCollection;
    }

    /**
     * Sets the value of the paroiExtETCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataParoiExtET }
     *     
     */
    public void setParoiExtETCollection(ArrayOfRTDataParoiExtET value) {
        this.paroiExtETCollection = value;
    }

    /**
     * Gets the value of the idComble property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComble() {
        return idComble;
    }

    /**
     * Sets the value of the idComble property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComble(String value) {
        this.idComble = value;
    }

    /**
     * Gets the value of the idAlphaTuile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAlphaTuile() {
        return idAlphaTuile;
    }

    /**
     * Sets the value of the idAlphaTuile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAlphaTuile(String value) {
        this.idAlphaTuile = value;
    }

    /**
     * Gets the value of the alphaTuileMesure property.
     * 
     */
    public double getAlphaTuileMesure() {
        return alphaTuileMesure;
    }

    /**
     * Sets the value of the alphaTuileMesure property.
     * 
     */
    public void setAlphaTuileMesure(double value) {
        this.alphaTuileMesure = value;
    }

    /**
     * Gets the value of the bTherm property.
     * 
     */
    public double getBTherm() {
        return bTherm;
    }

    /**
     * Sets the value of the bTherm property.
     * 
     */
    public void setBTherm(double value) {
        this.bTherm = value;
    }

}
