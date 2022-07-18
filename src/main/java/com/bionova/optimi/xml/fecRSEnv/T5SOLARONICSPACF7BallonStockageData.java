
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_BIOFLUIDES-SOLARONICS_Eaux-grises
 * 
 * <p>Java class for T5_SOLARONICS_PACF7_Ballon_stockage_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_SOLARONICS_PACF7_Ballon_stockage_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_UA_s" type="{}E_Val_Certifiee_Declaree_Defaut_Ballon"/>
 *         &lt;element name="UA_s" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V_tot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Source_Ballon_Base_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ArrayOfRTSourceBallonBase">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_SOLARONICS_PACF7_Ballon_stockage_Data", propOrder = {

})
public class T5SOLARONICSPACF7BallonStockageData {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Statut_UA_s", required = true)
    protected String statutUAS;
    @XmlElement(name = "UA_s")
    protected double uas;
    @XmlElement(name = "V_tot")
    protected double vTot;
    @XmlElement(name = "Source_Ballon_Base_Collection")
    protected T5SOLARONICSPACF7BallonStockageData.SourceBallonBaseCollection sourceBallonBaseCollection;

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
     * Gets the value of the sourceBallonBaseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link T5SOLARONICSPACF7BallonStockageData.SourceBallonBaseCollection }
     *     
     */
    public T5SOLARONICSPACF7BallonStockageData.SourceBallonBaseCollection getSourceBallonBaseCollection() {
        return sourceBallonBaseCollection;
    }

    /**
     * Sets the value of the sourceBallonBaseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link T5SOLARONICSPACF7BallonStockageData.SourceBallonBaseCollection }
     *     
     */
    public void setSourceBallonBaseCollection(T5SOLARONICSPACF7BallonStockageData.SourceBallonBaseCollection value) {
        this.sourceBallonBaseCollection = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}ArrayOfRTSourceBallonBase">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SourceBallonBaseCollection
        extends ArrayOfRTSourceBallonBase
    {


    }

}
