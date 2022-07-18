
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for UnitGroupDataSetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitGroupDataSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unitGroupInformation" type="{http://lca.jrc.it/ILCD/UnitGroup}UnitGroupInformationType"/>
 *         &lt;element name="modellingAndValidation" type="{http://lca.jrc.it/ILCD/UnitGroup}ModellingAndValidationType" minOccurs="0"/>
 *         &lt;element name="administrativeInformation" type="{http://lca.jrc.it/ILCD/UnitGroup}AdministrativeInformationType" minOccurs="0"/>
 *         &lt;element name="units" type="{http://lca.jrc.it/ILCD/UnitGroup}UnitsType" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}other" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://lca.jrc.it/ILCD/Common}SchemaVersion" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitGroupDataSetType", namespace = "http://lca.jrc.it/ILCD/UnitGroup", propOrder = {
    "unitGroupInformation",
    "modellingAndValidation",
    "administrativeInformation",
    "units",
    "other"
})
public class UnitGroupDataSetType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/UnitGroup", required = true)
    protected UnitGroupInformationType unitGroupInformation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/UnitGroup")
    protected ModellingAndValidationType modellingAndValidation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/UnitGroup")
    protected AdministrativeInformationType administrativeInformation;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/UnitGroup")
    protected UnitsType units;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the unitGroupInformation property.
     * 
     * @return
     *     possible object is
     *     {@link UnitGroupInformationType }
     *     
     */
    public UnitGroupInformationType getUnitGroupInformation() {
        return unitGroupInformation;
    }

    /**
     * Sets the value of the unitGroupInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitGroupInformationType }
     *     
     */
    public void setUnitGroupInformation(UnitGroupInformationType value) {
        this.unitGroupInformation = value;
    }

    /**
     * Gets the value of the modellingAndValidation property.
     * 
     * @return
     *     possible object is
     *     {@link ModellingAndValidationType }
     *     
     */
    public ModellingAndValidationType getModellingAndValidation() {
        return modellingAndValidation;
    }

    /**
     * Sets the value of the modellingAndValidation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModellingAndValidationType }
     *     
     */
    public void setModellingAndValidation(ModellingAndValidationType value) {
        this.modellingAndValidation = value;
    }

    /**
     * Gets the value of the administrativeInformation property.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeInformationType }
     *     
     */
    public AdministrativeInformationType getAdministrativeInformation() {
        return administrativeInformation;
    }

    /**
     * Sets the value of the administrativeInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeInformationType }
     *     
     */
    public void setAdministrativeInformation(AdministrativeInformationType value) {
        this.administrativeInformation = value;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsType }
     *     
     */
    public UnitsType getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsType }
     *     
     */
    public void setUnits(UnitsType value) {
        this.units = value;
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
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
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
