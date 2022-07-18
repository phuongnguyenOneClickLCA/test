
package com.bionova.optimi.xml.ilcd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ExchangeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExchangeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referenceToFlowDataSet" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType"/>
 *         &lt;element name="location" type="{http://lca.jrc.it/ILCD/Common}String" minOccurs="0"/>
 *         &lt;element name="functionType" type="{http://lca.jrc.it/ILCD/Common}ExchangeFunctionTypeValues" minOccurs="0"/>
 *         &lt;element name="exchangeDirection" type="{http://lca.jrc.it/ILCD/Common}ExchangeDirectionValues" minOccurs="0"/>
 *         &lt;element name="referenceToVariable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="meanAmount" type="{http://lca.jrc.it/ILCD/Common}Real"/>
 *         &lt;element name="resultingAmount" type="{http://lca.jrc.it/ILCD/Common}Real" minOccurs="0"/>
 *         &lt;element name="minimumAmount" type="{http://lca.jrc.it/ILCD/Common}Real" minOccurs="0"/>
 *         &lt;element name="maximumAmount" type="{http://lca.jrc.it/ILCD/Common}Real" minOccurs="0"/>
 *         &lt;element name="uncertaintyDistributionType" type="{http://lca.jrc.it/ILCD/Common}UncertaintyDistributionTypeValues" minOccurs="0"/>
 *         &lt;element name="relativeStandardDeviation95In" type="{http://lca.jrc.it/ILCD/Common}Perc" minOccurs="0"/>
 *         &lt;element name="allocations" type="{http://lca.jrc.it/ILCD/Process}AllocationsType" minOccurs="0"/>
 *         &lt;element name="dataSourceType" type="{http://lca.jrc.it/ILCD/Common}DataSourceTypeValues" minOccurs="0"/>
 *         &lt;element name="dataDerivationTypeStatus" type="{http://lca.jrc.it/ILCD/Common}DataDerivationTypeStatusValues" minOccurs="0"/>
 *         &lt;element name="referencesToDataSource" type="{http://lca.jrc.it/ILCD/Process}ReferencesToDataSourceType" minOccurs="0"/>
 *         &lt;element name="generalComment" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}other" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dataSetInternalID" use="required" type="{http://lca.jrc.it/ILCD/Common}Int6" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangeType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "referenceToFlowDataSet",
    "location",
    "functionType",
    "exchangeDirection",
    "referenceToVariable",
    "meanAmount",
    "resultingAmount",
    "minimumAmount",
    "maximumAmount",
    "uncertaintyDistributionType",
    "relativeStandardDeviation95In",
    "allocations",
    "dataSourceType",
    "dataDerivationTypeStatus",
    "referencesToDataSource",
    "generalComment",
    "other"
})
public class ExchangeType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process", required = true)
    protected GlobalReferenceType referenceToFlowDataSet;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected String location;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected ExchangeFunctionTypeValues functionType;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected ExchangeDirectionValues exchangeDirection;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected String referenceToVariable;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected double meanAmount;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected Double resultingAmount;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected Double minimumAmount;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected Double maximumAmount;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected UncertaintyDistributionTypeValues uncertaintyDistributionType;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected BigDecimal relativeStandardDeviation95In;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected AllocationsType allocations;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected String dataSourceType;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected DataDerivationTypeStatusValues dataDerivationTypeStatus;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected ReferencesToDataSourceType referencesToDataSource;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<StringMultiLang> generalComment;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAttribute(name = "dataSetInternalID", required = true)
    protected BigInteger dataSetInternalID;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the referenceToFlowDataSet property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalReferenceType }
     *     
     */
    public GlobalReferenceType getReferenceToFlowDataSet() {
        return referenceToFlowDataSet;
    }

    /**
     * Sets the value of the referenceToFlowDataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReferenceType }
     *     
     */
    public void setReferenceToFlowDataSet(GlobalReferenceType value) {
        this.referenceToFlowDataSet = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the functionType property.
     * 
     * @return
     *     possible object is
     *     {@link ExchangeFunctionTypeValues }
     *     
     */
    public ExchangeFunctionTypeValues getFunctionType() {
        return functionType;
    }

    /**
     * Sets the value of the functionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeFunctionTypeValues }
     *     
     */
    public void setFunctionType(ExchangeFunctionTypeValues value) {
        this.functionType = value;
    }

    /**
     * Gets the value of the exchangeDirection property.
     * 
     * @return
     *     possible object is
     *     {@link ExchangeDirectionValues }
     *     
     */
    public ExchangeDirectionValues getExchangeDirection() {
        return exchangeDirection;
    }

    /**
     * Sets the value of the exchangeDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeDirectionValues }
     *     
     */
    public void setExchangeDirection(ExchangeDirectionValues value) {
        this.exchangeDirection = value;
    }

    /**
     * Gets the value of the referenceToVariable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceToVariable() {
        return referenceToVariable;
    }

    /**
     * Sets the value of the referenceToVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceToVariable(String value) {
        this.referenceToVariable = value;
    }

    /**
     * Gets the value of the meanAmount property.
     * 
     */
    public double getMeanAmount() {
        return meanAmount;
    }

    /**
     * Sets the value of the meanAmount property.
     * 
     */
    public void setMeanAmount(double value) {
        this.meanAmount = value;
    }

    /**
     * Gets the value of the resultingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getResultingAmount() {
        return resultingAmount;
    }

    /**
     * Sets the value of the resultingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setResultingAmount(Double value) {
        this.resultingAmount = value;
    }

    /**
     * Gets the value of the minimumAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinimumAmount() {
        return minimumAmount;
    }

    /**
     * Sets the value of the minimumAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinimumAmount(Double value) {
        this.minimumAmount = value;
    }

    /**
     * Gets the value of the maximumAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * Sets the value of the maximumAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaximumAmount(Double value) {
        this.maximumAmount = value;
    }

    /**
     * Gets the value of the uncertaintyDistributionType property.
     * 
     * @return
     *     possible object is
     *     {@link UncertaintyDistributionTypeValues }
     *     
     */
    public UncertaintyDistributionTypeValues getUncertaintyDistributionType() {
        return uncertaintyDistributionType;
    }

    /**
     * Sets the value of the uncertaintyDistributionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link UncertaintyDistributionTypeValues }
     *     
     */
    public void setUncertaintyDistributionType(UncertaintyDistributionTypeValues value) {
        this.uncertaintyDistributionType = value;
    }

    /**
     * Gets the value of the relativeStandardDeviation95In property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRelativeStandardDeviation95In() {
        return relativeStandardDeviation95In;
    }

    /**
     * Sets the value of the relativeStandardDeviation95In property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRelativeStandardDeviation95In(BigDecimal value) {
        this.relativeStandardDeviation95In = value;
    }

    /**
     * Gets the value of the allocations property.
     * 
     * @return
     *     possible object is
     *     {@link AllocationsType }
     *     
     */
    public AllocationsType getAllocations() {
        return allocations;
    }

    /**
     * Sets the value of the allocations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllocationsType }
     *     
     */
    public void setAllocations(AllocationsType value) {
        this.allocations = value;
    }

    /**
     * Gets the value of the dataSourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceType() {
        return dataSourceType;
    }

    /**
     * Sets the value of the dataSourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceType(String value) {
        this.dataSourceType = value;
    }

    /**
     * Gets the value of the dataDerivationTypeStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DataDerivationTypeStatusValues }
     *     
     */
    public DataDerivationTypeStatusValues getDataDerivationTypeStatus() {
        return dataDerivationTypeStatus;
    }

    /**
     * Sets the value of the dataDerivationTypeStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDerivationTypeStatusValues }
     *     
     */
    public void setDataDerivationTypeStatus(DataDerivationTypeStatusValues value) {
        this.dataDerivationTypeStatus = value;
    }

    /**
     * Gets the value of the referencesToDataSource property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencesToDataSourceType }
     *     
     */
    public ReferencesToDataSourceType getReferencesToDataSource() {
        return referencesToDataSource;
    }

    /**
     * Sets the value of the referencesToDataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencesToDataSourceType }
     *     
     */
    public void setReferencesToDataSource(ReferencesToDataSourceType value) {
        this.referencesToDataSource = value;
    }

    /**
     * Gets the value of the generalComment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generalComment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGeneralComment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getGeneralComment() {
        if (generalComment == null) {
            generalComment = new ArrayList<StringMultiLang>();
        }
        return this.generalComment;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link Other }
     *     
     */
    public Other getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link Other }
     *     
     */
    public void setOther(Other value) {
        this.other = value;
    }

    /**
     * Gets the value of the dataSetInternalID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDataSetInternalID() {
        return dataSetInternalID;
    }

    /**
     * Sets the value of the dataSetInternalID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDataSetInternalID(BigInteger value) {
        this.dataSetInternalID = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
