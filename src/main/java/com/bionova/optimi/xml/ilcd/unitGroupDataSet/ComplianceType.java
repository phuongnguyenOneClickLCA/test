
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

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
@XmlType(name = "ComplianceType", namespace = "http://lca.jrc.it/ILCD/UnitGroup", propOrder = {
    "referenceToComplianceSystem",
    "approvalOfOverallCompliance",
    "other"
})
public class ComplianceType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common", required = true)
    protected GlobalReferenceType referenceToComplianceSystem;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected ComplianceValues approvalOfOverallCompliance;
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
