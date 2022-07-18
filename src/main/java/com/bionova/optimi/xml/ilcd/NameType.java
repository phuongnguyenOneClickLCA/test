
package com.bionova.optimi.xml.ilcd;

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
 * <p>Java class for NameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NameType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseName" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100"/>
 *         &lt;element name="treatmentStandardsRoutes" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="mixAndLocationTypes" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="functionalUnitFlowProperties" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
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
@XmlType(name = "NameType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "baseName",
    "treatmentStandardsRoutes",
    "mixAndLocationTypes",
    "functionalUnitFlowProperties",
    "other"
})
public class NameType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process", required = true)
    protected List<StringMultiLang> baseName;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<StringMultiLang> treatmentStandardsRoutes;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<StringMultiLang> mixAndLocationTypes;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<StringMultiLang> functionalUnitFlowProperties;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the baseName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getBaseName() {
        if (baseName == null) {
            baseName = new ArrayList<StringMultiLang>();
        }
        return this.baseName;
    }

    /**
     * Gets the value of the treatmentStandardsRoutes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treatmentStandardsRoutes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreatmentStandardsRoutes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getTreatmentStandardsRoutes() {
        if (treatmentStandardsRoutes == null) {
            treatmentStandardsRoutes = new ArrayList<StringMultiLang>();
        }
        return this.treatmentStandardsRoutes;
    }

    /**
     * Gets the value of the mixAndLocationTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mixAndLocationTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMixAndLocationTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getMixAndLocationTypes() {
        if (mixAndLocationTypes == null) {
            mixAndLocationTypes = new ArrayList<StringMultiLang>();
        }
        return this.mixAndLocationTypes;
    }

    /**
     * Gets the value of the functionalUnitFlowProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the functionalUnitFlowProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionalUnitFlowProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getFunctionalUnitFlowProperties() {
        if (functionalUnitFlowProperties == null) {
            functionalUnitFlowProperties = new ArrayList<StringMultiLang>();
        }
        return this.functionalUnitFlowProperties;
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
