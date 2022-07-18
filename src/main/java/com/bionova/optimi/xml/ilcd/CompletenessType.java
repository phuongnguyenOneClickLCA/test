
package com.bionova.optimi.xml.ilcd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for CompletenessType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompletenessType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="completenessProductModel" type="{http://lca.jrc.it/ILCD/Common}CompletenessValues" minOccurs="0"/>
 *         &lt;element name="referenceToSupportedImpactAssessmentMethods" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="completenessElementaryFlows" type="{http://lca.jrc.it/ILCD/Process}CompletenessElementaryFlowsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="completenessOtherProblemField" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
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
@XmlType(name = "CompletenessType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "completenessProductModel",
    "referenceToSupportedImpactAssessmentMethods",
    "completenessElementaryFlows",
    "completenessOtherProblemField",
    "other"
})
public class CompletenessType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected CompletenessValues completenessProductModel;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<GlobalReferenceType> referenceToSupportedImpactAssessmentMethods;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<CompletenessElementaryFlowsType> completenessElementaryFlows;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> completenessOtherProblemField;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the completenessProductModel property.
     * 
     * @return
     *     possible object is
     *     {@link CompletenessValues }
     *     
     */
    public CompletenessValues getCompletenessProductModel() {
        return completenessProductModel;
    }

    /**
     * Sets the value of the completenessProductModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompletenessValues }
     *     
     */
    public void setCompletenessProductModel(CompletenessValues value) {
        this.completenessProductModel = value;
    }

    /**
     * Gets the value of the referenceToSupportedImpactAssessmentMethods property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToSupportedImpactAssessmentMethods property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToSupportedImpactAssessmentMethods().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToSupportedImpactAssessmentMethods() {
        if (referenceToSupportedImpactAssessmentMethods == null) {
            referenceToSupportedImpactAssessmentMethods = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToSupportedImpactAssessmentMethods;
    }

    /**
     * Gets the value of the completenessElementaryFlows property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the completenessElementaryFlows property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompletenessElementaryFlows().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompletenessElementaryFlowsType }
     * 
     * 
     */
    public List<CompletenessElementaryFlowsType> getCompletenessElementaryFlows() {
        if (completenessElementaryFlows == null) {
            completenessElementaryFlows = new ArrayList<CompletenessElementaryFlowsType>();
        }
        return this.completenessElementaryFlows;
    }

    /**
     * Gets the value of the completenessOtherProblemField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the completenessOtherProblemField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompletenessOtherProblemField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getCompletenessOtherProblemField() {
        if (completenessOtherProblemField == null) {
            completenessOtherProblemField = new ArrayList<FTMultiLang>();
        }
        return this.completenessOtherProblemField;
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
