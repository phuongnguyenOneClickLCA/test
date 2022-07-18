
package com.bionova.optimi.xml.ilcd;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ComplianceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplianceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://lca.jrc.it/ILCD/Common}ComplianceGroup"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}nomenclatureCompliance" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}methodologicalCompliance" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}reviewCompliance" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}documentationCompliance" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}qualityCompliance" minOccurs="0"/>
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
@XmlType(name = "ComplianceType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "referenceToComplianceSystem",
    "approvalOfOverallCompliance",
    "nomenclatureCompliance",
    "methodologicalCompliance",
    "reviewCompliance",
    "documentationCompliance",
    "qualityCompliance",
    "other"
})
public class ComplianceType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common", required = true)
    protected GlobalReferenceType referenceToComplianceSystem;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues approvalOfOverallCompliance;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues nomenclatureCompliance;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues methodologicalCompliance;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues reviewCompliance;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues documentationCompliance;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues qualityCompliance;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the referenceToComplianceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalReferenceType }
     *     
     */
    public GlobalReferenceType getReferenceToComplianceSystem() {
        return referenceToComplianceSystem;
    }

    /**
     * Sets the value of the referenceToComplianceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReferenceType }
     *     
     */
    public void setReferenceToComplianceSystem(GlobalReferenceType value) {
        this.referenceToComplianceSystem = value;
    }

    /**
     * Gets the value of the approvalOfOverallCompliance property.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceValues }
     *     
     */
    public ComplianceValues getApprovalOfOverallCompliance() {
        return approvalOfOverallCompliance;
    }

    /**
     * Sets the value of the approvalOfOverallCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceValues }
     *     
     */
    public void setApprovalOfOverallCompliance(ComplianceValues value) {
        this.approvalOfOverallCompliance = value;
    }

    /**
     * Nomenclature compliance of this data set with the respective requirements set by the "compliance system" refered to.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceValues }
     *     
     */
    public ComplianceValues getNomenclatureCompliance() {
        return nomenclatureCompliance;
    }

    /**
     * Sets the value of the nomenclatureCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceValues }
     *     
     */
    public void setNomenclatureCompliance(ComplianceValues value) {
        this.nomenclatureCompliance = value;
    }

    /**
     * Methodological compliance of this data set with the respective requirements set by the "compliance system" refered to.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceValues }
     *     
     */
    public ComplianceValues getMethodologicalCompliance() {
        return methodologicalCompliance;
    }

    /**
     * Sets the value of the methodologicalCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceValues }
     *     
     */
    public void setMethodologicalCompliance(ComplianceValues value) {
        this.methodologicalCompliance = value;
    }

    /**
     * Review/Verification compliance of this data set with the respective requirements set by the "compliance system" refered to.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceValues }
     *     
     */
    public ComplianceValues getReviewCompliance() {
        return reviewCompliance;
    }

    /**
     * Sets the value of the reviewCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceValues }
     *     
     */
    public void setReviewCompliance(ComplianceValues value) {
        this.reviewCompliance = value;
    }

    /**
     * Documentation/Reporting compliance of this data set with the respective requirements set by the "compliance system" refered to.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceValues }
     *     
     */
    public ComplianceValues getDocumentationCompliance() {
        return documentationCompliance;
    }

    /**
     * Sets the value of the documentationCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceValues }
     *     
     */
    public void setDocumentationCompliance(ComplianceValues value) {
        this.documentationCompliance = value;
    }

    /**
     * Quality compliance of this data set with the respective requirements set by the "compliance system" refered to.
     * 
     * @return
     *     possible object is
     *     {@link ComplianceValues }
     *     
     */
    public ComplianceValues getQualityCompliance() {
        return qualityCompliance;
    }

    /**
     * Sets the value of the qualityCompliance property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplianceValues }
     *     
     */
    public void setQualityCompliance(ComplianceValues value) {
        this.qualityCompliance = value;
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
