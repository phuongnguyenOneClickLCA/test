
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
 * <p>Java class for LCIMethodAndAllocationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LCIMethodAndAllocationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeOfDataSet" type="{http://lca.jrc.it/ILCD/Common}TypeOfProcessValues" minOccurs="0"/>
 *         &lt;element name="LCIMethodPrinciple" type="{http://lca.jrc.it/ILCD/Common}LCIMethodPrincipleValues" minOccurs="0"/>
 *         &lt;element name="deviationsFromLCIMethodPrinciple" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="LCIMethodApproaches" type="{http://lca.jrc.it/ILCD/Common}LCIMethodApproachesValues" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deviationsFromLCIMethodApproaches" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="modellingConstants" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="deviationsFromModellingConstants" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="referenceToLCAMethodDetails" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "LCIMethodAndAllocationType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "typeOfDataSet",
    "lciMethodPrinciple",
    "deviationsFromLCIMethodPrinciple",
    "lciMethodApproaches",
    "deviationsFromLCIMethodApproaches",
    "modellingConstants",
    "deviationsFromModellingConstants",
    "referenceToLCAMethodDetails",
    "other"
})
public class LCIMethodAndAllocationType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected TypeOfProcessValues typeOfDataSet;
    @XmlElement(name = "LCIMethodPrinciple", namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected LCIMethodPrincipleValues lciMethodPrinciple;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> deviationsFromLCIMethodPrinciple;
    @XmlElement(name = "LCIMethodApproaches", namespace = "http://lca.jrc.it/ILCD/Process")
    @XmlSchemaType(name = "string")
    protected List<LCIMethodApproachesValues> lciMethodApproaches;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> deviationsFromLCIMethodApproaches;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> modellingConstants;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> deviationsFromModellingConstants;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<GlobalReferenceType> referenceToLCAMethodDetails;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the typeOfDataSet property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfProcessValues }
     *     
     */
    public TypeOfProcessValues getTypeOfDataSet() {
        return typeOfDataSet;
    }

    /**
     * Sets the value of the typeOfDataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfProcessValues }
     *     
     */
    public void setTypeOfDataSet(TypeOfProcessValues value) {
        this.typeOfDataSet = value;
    }

    /**
     * Gets the value of the lciMethodPrinciple property.
     * 
     * @return
     *     possible object is
     *     {@link LCIMethodPrincipleValues }
     *     
     */
    public LCIMethodPrincipleValues getLCIMethodPrinciple() {
        return lciMethodPrinciple;
    }

    /**
     * Sets the value of the lciMethodPrinciple property.
     * 
     * @param value
     *     allowed object is
     *     {@link LCIMethodPrincipleValues }
     *     
     */
    public void setLCIMethodPrinciple(LCIMethodPrincipleValues value) {
        this.lciMethodPrinciple = value;
    }

    /**
     * Gets the value of the deviationsFromLCIMethodPrinciple property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviationsFromLCIMethodPrinciple property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviationsFromLCIMethodPrinciple().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDeviationsFromLCIMethodPrinciple() {
        if (deviationsFromLCIMethodPrinciple == null) {
            deviationsFromLCIMethodPrinciple = new ArrayList<FTMultiLang>();
        }
        return this.deviationsFromLCIMethodPrinciple;
    }

    /**
     * Gets the value of the lciMethodApproaches property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lciMethodApproaches property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLCIMethodApproaches().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LCIMethodApproachesValues }
     * 
     * 
     */
    public List<LCIMethodApproachesValues> getLCIMethodApproaches() {
        if (lciMethodApproaches == null) {
            lciMethodApproaches = new ArrayList<LCIMethodApproachesValues>();
        }
        return this.lciMethodApproaches;
    }

    /**
     * Gets the value of the deviationsFromLCIMethodApproaches property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviationsFromLCIMethodApproaches property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviationsFromLCIMethodApproaches().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDeviationsFromLCIMethodApproaches() {
        if (deviationsFromLCIMethodApproaches == null) {
            deviationsFromLCIMethodApproaches = new ArrayList<FTMultiLang>();
        }
        return this.deviationsFromLCIMethodApproaches;
    }

    /**
     * Gets the value of the modellingConstants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modellingConstants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModellingConstants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getModellingConstants() {
        if (modellingConstants == null) {
            modellingConstants = new ArrayList<FTMultiLang>();
        }
        return this.modellingConstants;
    }

    /**
     * Gets the value of the deviationsFromModellingConstants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviationsFromModellingConstants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviationsFromModellingConstants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDeviationsFromModellingConstants() {
        if (deviationsFromModellingConstants == null) {
            deviationsFromModellingConstants = new ArrayList<FTMultiLang>();
        }
        return this.deviationsFromModellingConstants;
    }

    /**
     * Gets the value of the referenceToLCAMethodDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToLCAMethodDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToLCAMethodDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToLCAMethodDetails() {
        if (referenceToLCAMethodDetails == null) {
            referenceToLCAMethodDetails = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToLCAMethodDetails;
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
