
package com.bionova.optimi.xml.ilcd;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ModellingAndValidationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModellingAndValidationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LCIMethodAndAllocation" type="{http://lca.jrc.it/ILCD/Process}LCIMethodAndAllocationType" minOccurs="0"/>
 *         &lt;element name="dataSourcesTreatmentAndRepresentativeness" type="{http://lca.jrc.it/ILCD/Process}DataSourcesTreatmentAndRepresentativenessType" minOccurs="0"/>
 *         &lt;element name="completeness" type="{http://lca.jrc.it/ILCD/Process}CompletenessType" minOccurs="0"/>
 *         &lt;element name="validation" type="{http://lca.jrc.it/ILCD/Process}ValidationType" minOccurs="0"/>
 *         &lt;element name="complianceDeclarations" type="{http://lca.jrc.it/ILCD/Process}ComplianceDeclarationsType" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}other" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModellingAndValidationType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "lciMethodAndAllocation",
    "dataSourcesTreatmentAndRepresentativeness",
    "completeness",
    "validation",
    "complianceDeclarations",
    "other"
})
public class ModellingAndValidationType {

    @XmlElement(name = "LCIMethodAndAllocation", namespace = "http://lca.jrc.it/ILCD/Process")
    protected LCIMethodAndAllocationType lciMethodAndAllocation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected DataSourcesTreatmentAndRepresentativenessType dataSourcesTreatmentAndRepresentativeness;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected CompletenessType completeness;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected ValidationType validation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected ComplianceDeclarationsType complianceDeclarations;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the lciMethodAndAllocation property.
     * 
     * @return
     *     possible object is
     *     {@link LCIMethodAndAllocationType }
     *     
     */
    public LCIMethodAndAllocationType getLCIMethodAndAllocation() {
        return lciMethodAndAllocation;
    }

    /**
     * Sets the value of the lciMethodAndAllocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link LCIMethodAndAllocationType }
     *     
     */
    public void setLCIMethodAndAllocation(LCIMethodAndAllocationType value) {
        this.lciMethodAndAllocation = value;
    }

    /**
     * Gets the value of the dataSourcesTreatmentAndRepresentativeness property.
     * 
     * @return
     *     possible object is
     *     {@link DataSourcesTreatmentAndRepresentativenessType }
     *     
     */
    public DataSourcesTreatmentAndRepresentativenessType getDataSourcesTreatmentAndRepresentativeness() {
        return dataSourcesTreatmentAndRepresentativeness;
    }

    /**
     * Sets the value of the dataSourcesTreatmentAndRepresentativeness property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourcesTreatmentAndRepresentativenessType }
     *     
     */
    public void setDataSourcesTreatmentAndRepresentativeness(DataSourcesTreatmentAndRepresentativenessType value) {
        this.dataSourcesTreatmentAndRepresentativeness = value;
    }

    /**
     * Gets the value of the completeness property.
     * 
     * @return
     *     possible object is
     *     {@link CompletenessType }
     *     
     */
    public CompletenessType getCompleteness() {
        return completeness;
    }

    /**
     * Sets the value of the completeness property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompletenessType }
     *     
     */
    public void setCompleteness(CompletenessType value) {
        this.completeness = value;
    }

    /**
     * Gets the value of the validation property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationType }
     *     
     */
    public ValidationType getValidation() {
        return validation;
    }

    /**
     * Sets the value of the validation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationType }
     *     
     */
    public void setValidation(ValidationType value) {
        this.validation = value;
    }

    /**
     * Gets the value of the complianceDeclarations property.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceDeclarationsType }
     *     
     */
    public ComplianceDeclarationsType getComplianceDeclarations() {
        return complianceDeclarations;
    }

    /**
     * Sets the value of the complianceDeclarations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceDeclarationsType }
     *     
     */
    public void setComplianceDeclarations(ComplianceDeclarationsType value) {
        this.complianceDeclarations = value;
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
