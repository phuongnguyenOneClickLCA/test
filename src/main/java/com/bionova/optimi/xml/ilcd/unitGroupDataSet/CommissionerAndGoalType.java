
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for CommissionerAndGoalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommissionerAndGoalType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referenceToCommissioner" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="project" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="intendedApplications" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
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
@XmlType(name = "CommissionerAndGoalType", namespace = "http://lca.jrc.it/ILCD/Common", propOrder = {
    "referenceToCommissioner",
    "project",
    "intendedApplications",
    "other"
})
public class CommissionerAndGoalType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<GlobalReferenceType> referenceToCommissioner;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<StringMultiLang> project;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<FTMultiLang> intendedApplications;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the referenceToCommissioner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToCommissioner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToCommissioner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToCommissioner() {
        if (referenceToCommissioner == null) {
            referenceToCommissioner = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToCommissioner;
    }

    /**
     * Gets the value of the project property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the project property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getProject() {
        if (project == null) {
            project = new ArrayList<StringMultiLang>();
        }
        return this.project;
    }

    /**
     * Gets the value of the intendedApplications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the intendedApplications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntendedApplications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getIntendedApplications() {
        if (intendedApplications == null) {
            intendedApplications = new ArrayList<FTMultiLang>();
        }
        return this.intendedApplications;
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
