
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for UnitGroupInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitGroupInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataSetInformation" type="{http://lca.jrc.it/ILCD/UnitGroup}DataSetInformationType"/>
 *         &lt;element name="quantitativeReference" type="{http://lca.jrc.it/ILCD/UnitGroup}QuantitativeReferenceType" minOccurs="0"/>
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
@XmlType(name = "UnitGroupInformationType", namespace = "http://lca.jrc.it/ILCD/UnitGroup", propOrder = {
    "dataSetInformation",
    "quantitativeReference",
    "other"
})
public class UnitGroupInformationType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/UnitGroup", required = true)
    protected DataSetInformationType dataSetInformation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/UnitGroup")
    protected QuantitativeReferenceType quantitativeReference;
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
