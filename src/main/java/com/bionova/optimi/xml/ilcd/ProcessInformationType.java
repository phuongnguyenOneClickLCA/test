
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
 * <p>Java class for ProcessInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataSetInformation" type="{http://lca.jrc.it/ILCD/Process}DataSetInformationType"/>
 *         &lt;element name="quantitativeReference" type="{http://lca.jrc.it/ILCD/Process}QuantitativeReferenceType" minOccurs="0"/>
 *         &lt;element name="time" type="{http://lca.jrc.it/ILCD/Common}TimeType" minOccurs="0"/>
 *         &lt;element name="geography" type="{http://lca.jrc.it/ILCD/Process}GeographyType" minOccurs="0"/>
 *         &lt;element name="technology" type="{http://lca.jrc.it/ILCD/Process}TechnologyType" minOccurs="0"/>
 *         &lt;element name="mathematicalRelations" type="{http://lca.jrc.it/ILCD/Process}MathematicalRelationsType" minOccurs="0"/>
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
@XmlType(name = "ProcessInformationType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "dataSetInformation",
    "quantitativeReference",
    "time",
    "geography",
    "technology",
    "mathematicalRelations",
    "other"
})
public class ProcessInformationType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process", required = true)
    protected DataSetInformationType dataSetInformation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected QuantitativeReferenceType quantitativeReference;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected TimeType time;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected GeographyType geography;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected TechnologyType technology;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected MathematicalRelationsType mathematicalRelations;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the dataSetInformation property.
     * 
     * @return
     *     possible object is
     *     {@link DataSetInformationType }
     *     
     */
    public DataSetInformationType getDataSetInformation() {
        return dataSetInformation;
    }

    /**
     * Sets the value of the dataSetInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSetInformationType }
     *     
     */
    public void setDataSetInformation(DataSetInformationType value) {
        this.dataSetInformation = value;
    }

    /**
     * Gets the value of the quantitativeReference property.
     * 
     * @return
     *     possible object is
     *     {@link QuantitativeReferenceType }
     *     
     */
    public QuantitativeReferenceType getQuantitativeReference() {
        return quantitativeReference;
    }

    /**
     * Sets the value of the quantitativeReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantitativeReferenceType }
     *     
     */
    public void setQuantitativeReference(QuantitativeReferenceType value) {
        this.quantitativeReference = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link TimeType }
     *     
     */
    public TimeType getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeType }
     *     
     */
    public void setTime(TimeType value) {
        this.time = value;
    }

    /**
     * Gets the value of the geography property.
     * 
     * @return
     *     possible object is
     *     {@link GeographyType }
     *     
     */
    public GeographyType getGeography() {
        return geography;
    }

    /**
     * Sets the value of the geography property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographyType }
     *     
     */
    public void setGeography(GeographyType value) {
        this.geography = value;
    }

    /**
     * Gets the value of the technology property.
     * 
     * @return
     *     possible object is
     *     {@link TechnologyType }
     *     
     */
    public TechnologyType getTechnology() {
        return technology;
    }

    /**
     * Sets the value of the technology property.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnologyType }
     *     
     */
    public void setTechnology(TechnologyType value) {
        this.technology = value;
    }

    /**
     * Gets the value of the mathematicalRelations property.
     * 
     * @return
     *     possible object is
     *     {@link MathematicalRelationsType }
     *     
     */
    public MathematicalRelationsType getMathematicalRelations() {
        return mathematicalRelations;
    }

    /**
     * Sets the value of the mathematicalRelations property.
     * 
     * @param value
     *     allowed object is
     *     {@link MathematicalRelationsType }
     *     
     */
    public void setMathematicalRelations(MathematicalRelationsType value) {
        this.mathematicalRelations = value;
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
