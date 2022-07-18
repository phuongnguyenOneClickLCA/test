
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * DC:Descriptif d'une opération
 * 
 * <p>Java class for t_operation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_operation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="num_permis">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="PC[A-B0-9][A-B0-9][A-B0-9][0-9][0-9][0-9][0-2][0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]|en cours|EN COURS"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="date_depot_PC" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="10"/>
 *               &lt;pattern value="20[0-2][0-9]-[0-1][0-9]-[0-3][0-9]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="date_obtention_PC" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="10"/>
 *               &lt;pattern value="20[0-2][0-9]-[0-1][0-9]-[0-3][0-9]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="avancement">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;maxInclusive value="3"/>
 *               &lt;minInclusive value="1"/>
 *               &lt;pattern value="[1-3]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="res_PEBN" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="adresse" type="{}t_adresse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_operation", propOrder = {
    "numPermis",
    "dateDepotPC",
    "dateObtentionPC",
    "avancement",
    "resPEBN",
    "name",
    "adresse"
})
public class TOperation {

    @XmlElement(name = "num_permis", required = true)
    protected String numPermis;
    @XmlElement(name = "date_depot_PC")
    protected String dateDepotPC;
    @XmlElement(name = "date_obtention_PC")
    protected String dateObtentionPC;
    @XmlElement(required = true)
    protected BigDecimal avancement;
    @XmlElement(name = "res_PEBN", defaultValue = "1")
    protected Integer resPEBN;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected TAdresse adresse;

    /**
     * Gets the value of the numPermis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumPermis() {
        return numPermis;
    }

    /**
     * Sets the value of the numPermis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumPermis(String value) {
        this.numPermis = value;
    }

    /**
     * Gets the value of the dateDepotPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateDepotPC() {
        return dateDepotPC;
    }

    /**
     * Sets the value of the dateDepotPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateDepotPC(String value) {
        this.dateDepotPC = value;
    }

    /**
     * Gets the value of the dateObtentionPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateObtentionPC() {
        return dateObtentionPC;
    }

    /**
     * Sets the value of the dateObtentionPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateObtentionPC(String value) {
        this.dateObtentionPC = value;
    }

    /**
     * Gets the value of the avancement property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvancement() {
        return avancement;
    }

    /**
     * Sets the value of the avancement property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvancement(BigDecimal value) {
        this.avancement = value;
    }

    /**
     * Gets the value of the resPEBN property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getResPEBN() {
        return resPEBN;
    }

    /**
     * Sets the value of the resPEBN property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setResPEBN(Integer value) {
        this.resPEBN = value;
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
     * Gets the value of the adresse property.
     * 
     * @return
     *     possible object is
     *     {@link TAdresse }
     *     
     */
    public TAdresse getAdresse() {
        return adresse;
    }

    /**
     * Sets the value of the adresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAdresse }
     *     
     */
    public void setAdresse(TAdresse value) {
        this.adresse = value;
    }

}
