
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Simu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Simu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mode" type="{}E_Mode_de_calcul"/>
 *         &lt;element name="Option_Sensibilite" type="{}E_Option_Sensibilite"/>
 *         &lt;element name="Departement" type="{}E_Departement"/>
 *         &lt;element name="Zone_Ete_Int_Lit" type="{}E_Zone_Ete"/>
 *         &lt;element name="Altitude" type="{}E_Classe_Altitude"/>
 *         &lt;element name="Coef_B" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Coef_C" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minExclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlType(name = "RT_Data_Simu", propOrder = {

})
public class RTDataSimu {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Mode", required = true)
    protected String mode;
    @XmlElement(name = "Option_Sensibilite", required = true)
    protected String optionSensibilite;
    @XmlElement(name = "Departement", required = true)
    protected String departement;
    @XmlElement(name = "Zone_Ete_Int_Lit", required = true)
    protected String zoneEteIntLit;
    @XmlElement(name = "Altitude", required = true)
    protected String altitude;
    @XmlElement(name = "Coef_B")
    protected BigDecimal coefB;
    @XmlElement(name = "Coef_C")
    protected BigDecimal coefC;

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
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the optionSensibilite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionSensibilite() {
        return optionSensibilite;
    }

    /**
     * Sets the value of the optionSensibilite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionSensibilite(String value) {
        this.optionSensibilite = value;
    }

    /**
     * Gets the value of the departement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * Sets the value of the departement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartement(String value) {
        this.departement = value;
    }

    /**
     * Gets the value of the zoneEteIntLit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneEteIntLit() {
        return zoneEteIntLit;
    }

    /**
     * Sets the value of the zoneEteIntLit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneEteIntLit(String value) {
        this.zoneEteIntLit = value;
    }

    /**
     * Gets the value of the altitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     * Sets the value of the altitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltitude(String value) {
        this.altitude = value;
    }

    /**
     * Gets the value of the coefB property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCoefB() {
        return coefB;
    }

    /**
     * Sets the value of the coefB property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCoefB(BigDecimal value) {
        this.coefB = value;
    }

    /**
     * Gets the value of the coefC property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCoefC() {
        return coefC;
    }

    /**
     * Sets the value of the coefC property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCoefC(BigDecimal value) {
        this.coefC = value;
    }

}
