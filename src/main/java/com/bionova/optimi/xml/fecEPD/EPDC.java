
package com.bionova.optimi.xml.fecEPD;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EPDCId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ConfiguratorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfiguratorCode" type="{}ConfiguratorIdEnum"/>
 *         &lt;element name="ConfiguratorVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PublicAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParentDataBase" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ParentEPDId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="TLD" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="UnitId" type="{}UnitIdEnum"/>
 *         &lt;element name="FunctionalUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Standard" type="{}StandardIdEnum" minOccurs="0"/>
 *         &lt;element name="ProductionDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Parameters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Parameter" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ParameterId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="UnitId" type="{}UnitIdEnum"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AirRating" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="IsHealthNumExist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HealthNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsContactDrinkingWater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InfosDrinkingWater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsContactNotDrinkingWater" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="InfosNotDrinkingWater" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OtherHealthInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComfortA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComfortO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComfortV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComfortH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OtherComfort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Indicators" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Indicator" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Phases">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Phase" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="PhaseCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="IndicatorCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "epdcId",
    "name",
    "configuratorName",
    "configuratorCode",
    "configuratorVersion",
    "publicAddress",
    "parentDataBase",
    "parentEPDId",
    "tld",
    "quantity",
    "unitId",
    "functionalUnit",
    "standard",
    "productionDate",
    "parameters",
    "airRating",
    "isHealthNumExist",
    "healthNum",
    "isContactDrinkingWater",
    "infosDrinkingWater",
    "isContactNotDrinkingWater",
    "infosNotDrinkingWater",
    "otherHealthInfo",
    "comfortA",
    "comfortO",
    "comfortV",
    "comfortH",
    "otherComfort",
    "indicators"
})
@XmlRootElement(name = "EPDC")
public class EPDC {

    @XmlElement(name = "EPDCId", required = true)
    protected String epdcId;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ConfiguratorName")
    protected String configuratorName;
    @XmlElement(name = "ConfiguratorCode", required = true)
    protected BigInteger configuratorCode;
    @XmlElement(name = "ConfiguratorVersion", required = true)
    protected String configuratorVersion;
    @XmlElement(name = "PublicAddress", required = true)
    protected String publicAddress;
    @XmlElement(name = "ParentDataBase", required = true)
    protected BigInteger parentDataBase;
    @XmlElement(name = "ParentEPDId", required = true)
    protected BigInteger parentEPDId;
    @XmlElement(name = "TLD", required = true)
    protected BigDecimal tld;
    @XmlElement(name = "Quantity", required = true)
    protected BigDecimal quantity;
    @XmlElement(name = "UnitId", required = true)
    protected BigInteger unitId;
    @XmlElement(name = "FunctionalUnit")
    protected String functionalUnit;
    @XmlElement(name = "Standard")
    protected BigInteger standard;
    @XmlElement(name = "ProductionDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar productionDate;
    @XmlElement(name = "Parameters", required = true)
    protected EPDC.Parameters parameters;
    @XmlElement(name = "AirRating")
    protected BigInteger airRating;
    @XmlElement(name = "IsHealthNumExist")
    protected String isHealthNumExist;
    @XmlElement(name = "HealthNum")
    protected String healthNum;
    @XmlElement(name = "IsContactDrinkingWater")
    protected String isContactDrinkingWater;
    @XmlElement(name = "InfosDrinkingWater")
    protected String infosDrinkingWater;
    @XmlElement(name = "IsContactNotDrinkingWater")
    protected Boolean isContactNotDrinkingWater;
    @XmlElement(name = "InfosNotDrinkingWater")
    protected String infosNotDrinkingWater;
    @XmlElement(name = "OtherHealthInfo")
    protected String otherHealthInfo;
    @XmlElement(name = "ComfortA")
    protected String comfortA;
    @XmlElement(name = "ComfortO")
    protected String comfortO;
    @XmlElement(name = "ComfortV")
    protected String comfortV;
    @XmlElement(name = "ComfortH")
    protected String comfortH;
    @XmlElement(name = "OtherComfort")
    protected String otherComfort;
    @XmlElement(name = "Indicators")
    protected EPDC.Indicators indicators;

    /**
     * Gets the value of the epdcId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEPDCId() {
        return epdcId;
    }

    /**
     * Sets the value of the epdcId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEPDCId(String value) {
        this.epdcId = value;
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
     * Gets the value of the configuratorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfiguratorName() {
        return configuratorName;
    }

    /**
     * Sets the value of the configuratorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfiguratorName(String value) {
        this.configuratorName = value;
    }

    /**
     * Gets the value of the configuratorCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getConfiguratorCode() {
        return configuratorCode;
    }

    /**
     * Sets the value of the configuratorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setConfiguratorCode(BigInteger value) {
        this.configuratorCode = value;
    }

    /**
     * Gets the value of the configuratorVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfiguratorVersion() {
        return configuratorVersion;
    }

    /**
     * Sets the value of the configuratorVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfiguratorVersion(String value) {
        this.configuratorVersion = value;
    }

    /**
     * Gets the value of the publicAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicAddress() {
        return publicAddress;
    }

    /**
     * Sets the value of the publicAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicAddress(String value) {
        this.publicAddress = value;
    }

    /**
     * Gets the value of the parentDataBase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParentDataBase() {
        return parentDataBase;
    }

    /**
     * Sets the value of the parentDataBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParentDataBase(BigInteger value) {
        this.parentDataBase = value;
    }

    /**
     * Gets the value of the parentEPDId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getParentEPDId() {
        return parentEPDId;
    }

    /**
     * Sets the value of the parentEPDId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setParentEPDId(BigInteger value) {
        this.parentEPDId = value;
    }

    /**
     * Gets the value of the tld property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTLD() {
        return tld;
    }

    /**
     * Sets the value of the tld property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTLD(BigDecimal value) {
        this.tld = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the unitId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUnitId() {
        return unitId;
    }

    /**
     * Sets the value of the unitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUnitId(BigInteger value) {
        this.unitId = value;
    }

    /**
     * Gets the value of the functionalUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionalUnit() {
        return functionalUnit;
    }

    /**
     * Sets the value of the functionalUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionalUnit(String value) {
        this.functionalUnit = value;
    }

    /**
     * Gets the value of the standard property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStandard() {
        return standard;
    }

    /**
     * Sets the value of the standard property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStandard(BigInteger value) {
        this.standard = value;
    }

    /**
     * Gets the value of the productionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProductionDate() {
        return productionDate;
    }

    /**
     * Sets the value of the productionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProductionDate(XMLGregorianCalendar value) {
        this.productionDate = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link EPDC.Parameters }
     *     
     */
    public EPDC.Parameters getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link EPDC.Parameters }
     *     
     */
    public void setParameters(EPDC.Parameters value) {
        this.parameters = value;
    }

    /**
     * Gets the value of the airRating property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAirRating() {
        return airRating;
    }

    /**
     * Sets the value of the airRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAirRating(BigInteger value) {
        this.airRating = value;
    }

    /**
     * Gets the value of the isHealthNumExist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHealthNumExist() {
        return isHealthNumExist;
    }

    /**
     * Sets the value of the isHealthNumExist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHealthNumExist(String value) {
        this.isHealthNumExist = value;
    }

    /**
     * Gets the value of the healthNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealthNum() {
        return healthNum;
    }

    /**
     * Sets the value of the healthNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthNum(String value) {
        this.healthNum = value;
    }

    /**
     * Gets the value of the isContactDrinkingWater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsContactDrinkingWater() {
        return isContactDrinkingWater;
    }

    /**
     * Sets the value of the isContactDrinkingWater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsContactDrinkingWater(String value) {
        this.isContactDrinkingWater = value;
    }

    /**
     * Gets the value of the infosDrinkingWater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfosDrinkingWater() {
        return infosDrinkingWater;
    }

    /**
     * Sets the value of the infosDrinkingWater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfosDrinkingWater(String value) {
        this.infosDrinkingWater = value;
    }

    /**
     * Gets the value of the isContactNotDrinkingWater property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsContactNotDrinkingWater() {
        return isContactNotDrinkingWater;
    }

    /**
     * Sets the value of the isContactNotDrinkingWater property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsContactNotDrinkingWater(Boolean value) {
        this.isContactNotDrinkingWater = value;
    }

    /**
     * Gets the value of the infosNotDrinkingWater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfosNotDrinkingWater() {
        return infosNotDrinkingWater;
    }

    /**
     * Sets the value of the infosNotDrinkingWater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfosNotDrinkingWater(String value) {
        this.infosNotDrinkingWater = value;
    }

    /**
     * Gets the value of the otherHealthInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherHealthInfo() {
        return otherHealthInfo;
    }

    /**
     * Sets the value of the otherHealthInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherHealthInfo(String value) {
        this.otherHealthInfo = value;
    }

    /**
     * Gets the value of the comfortA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComfortA() {
        return comfortA;
    }

    /**
     * Sets the value of the comfortA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComfortA(String value) {
        this.comfortA = value;
    }

    /**
     * Gets the value of the comfortO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComfortO() {
        return comfortO;
    }

    /**
     * Sets the value of the comfortO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComfortO(String value) {
        this.comfortO = value;
    }

    /**
     * Gets the value of the comfortV property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComfortV() {
        return comfortV;
    }

    /**
     * Sets the value of the comfortV property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComfortV(String value) {
        this.comfortV = value;
    }

    /**
     * Gets the value of the comfortH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComfortH() {
        return comfortH;
    }

    /**
     * Sets the value of the comfortH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComfortH(String value) {
        this.comfortH = value;
    }

    /**
     * Gets the value of the otherComfort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherComfort() {
        return otherComfort;
    }

    /**
     * Sets the value of the otherComfort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherComfort(String value) {
        this.otherComfort = value;
    }

    /**
     * Gets the value of the indicators property.
     * 
     * @return
     *     possible object is
     *     {@link EPDC.Indicators }
     *     
     */
    public EPDC.Indicators getIndicators() {
        return indicators;
    }

    /**
     * Sets the value of the indicators property.
     * 
     * @param value
     *     allowed object is
     *     {@link EPDC.Indicators }
     *     
     */
    public void setIndicators(EPDC.Indicators value) {
        this.indicators = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Indicator" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Phases">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Phase" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="PhaseCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="IndicatorCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "indicator"
    })
    public static class Indicators {

        @XmlElement(name = "Indicator", required = true)
        protected List<EPDC.Indicators.Indicator> indicator;

        /**
         * Gets the value of the indicator property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the indicator property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIndicator().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EPDC.Indicators.Indicator }
         * 
         * 
         */
        public List<EPDC.Indicators.Indicator> getIndicator() {
            if (indicator == null) {
                indicator = new ArrayList<EPDC.Indicators.Indicator>();
            }
            return this.indicator;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Phases">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Phase" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="PhaseCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="IndicatorCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "phases"
        })
        public static class Indicator {

            @XmlElement(name = "Phases", required = true)
            protected EPDC.Indicators.Indicator.Phases phases;
            @XmlAttribute(name = "IndicatorCode", required = true)
            protected String indicatorCode;

            /**
             * Gets the value of the phases property.
             * 
             * @return
             *     possible object is
             *     {@link EPDC.Indicators.Indicator.Phases }
             *     
             */
            public EPDC.Indicators.Indicator.Phases getPhases() {
                return phases;
            }

            /**
             * Sets the value of the phases property.
             * 
             * @param value
             *     allowed object is
             *     {@link EPDC.Indicators.Indicator.Phases }
             *     
             */
            public void setPhases(EPDC.Indicators.Indicator.Phases value) {
                this.phases = value;
            }

            /**
             * Gets the value of the indicatorCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIndicatorCode() {
                return indicatorCode;
            }

            /**
             * Sets the value of the indicatorCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIndicatorCode(String value) {
                this.indicatorCode = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Phase" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="PhaseCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "phase"
            })
            public static class Phases {

                @XmlElement(name = "Phase", required = true)
                protected List<EPDC.Indicators.Indicator.Phases.Phase> phase;

                /**
                 * Gets the value of the phase property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the phase property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getPhase().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link EPDC.Indicators.Indicator.Phases.Phase }
                 * 
                 * 
                 */
                public List<EPDC.Indicators.Indicator.Phases.Phase> getPhase() {
                    if (phase == null) {
                        phase = new ArrayList<EPDC.Indicators.Indicator.Phases.Phase>();
                    }
                    return this.phase;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="PhaseCode" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class Phase {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "PhaseCode", required = true)
                    protected String phaseCode;

                    /**
                     * Gets the value of the value property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Gets the value of the phaseCode property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPhaseCode() {
                        return phaseCode;
                    }

                    /**
                     * Sets the value of the phaseCode property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPhaseCode(String value) {
                        this.phaseCode = value;
                    }

                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Parameter" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ParameterId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="UnitId" type="{}UnitIdEnum"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "parameter"
    })
    public static class Parameters {

        @XmlElement(name = "Parameter", required = true)
        protected List<EPDC.Parameters.Parameter> parameter;

        /**
         * Gets the value of the parameter property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the parameter property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParameter().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EPDC.Parameters.Parameter }
         * 
         * 
         */
        public List<EPDC.Parameters.Parameter> getParameter() {
            if (parameter == null) {
                parameter = new ArrayList<EPDC.Parameters.Parameter>();
            }
            return this.parameter;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ParameterId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="UnitId" type="{}UnitIdEnum"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "parameterId",
            "description",
            "value",
            "unitId"
        })
        public static class Parameter {

            @XmlElement(name = "ParameterId", required = true)
            protected String parameterId;
            @XmlElement(name = "Description")
            protected String description;
            @XmlElement(name = "Value", required = true)
            protected BigDecimal value;
            @XmlElement(name = "UnitId", required = true)
            protected BigInteger unitId;

            /**
             * Gets the value of the parameterId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParameterId() {
                return parameterId;
            }

            /**
             * Sets the value of the parameterId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParameterId(String value) {
                this.parameterId = value;
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
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValue(BigDecimal value) {
                this.value = value;
            }

            /**
             * Gets the value of the unitId property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getUnitId() {
                return unitId;
            }

            /**
             * Sets the value of the unitId property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setUnitId(BigInteger value) {
                this.unitId = value;
            }

        }

    }

}
