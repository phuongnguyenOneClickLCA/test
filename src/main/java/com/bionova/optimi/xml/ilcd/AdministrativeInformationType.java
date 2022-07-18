
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
 * <p>Java class for AdministrativeInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdministrativeInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}commissionerAndGoal" minOccurs="0"/>
 *         &lt;element name="dataGenerator" type="{http://lca.jrc.it/ILCD/Process}DataGeneratorType" minOccurs="0"/>
 *         &lt;element name="dataEntryBy" type="{http://lca.jrc.it/ILCD/Process}DataEntryByType" minOccurs="0"/>
 *         &lt;element name="publicationAndOwnership" type="{http://lca.jrc.it/ILCD/Process}PublicationAndOwnershipType" minOccurs="0"/>
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
@XmlType(name = "AdministrativeInformationType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "commissionerAndGoal",
    "dataGenerator",
    "dataEntryBy",
    "publicationAndOwnership",
    "other"
})
public class AdministrativeInformationType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected CommissionerAndGoalType commissionerAndGoal;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected DataGeneratorType dataGenerator;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected DataEntryByType dataEntryBy;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected PublicationAndOwnershipType publicationAndOwnership;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Basic information about goal and scope of the data set.
     * 
     * @return
     *     possible object is
     *     {@link CommissionerAndGoalType }
     *     
     */
    public CommissionerAndGoalType getCommissionerAndGoal() {
        return commissionerAndGoal;
    }

    /**
     * Sets the value of the commissionerAndGoal property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionerAndGoalType }
     *     
     */
    public void setCommissionerAndGoal(CommissionerAndGoalType value) {
        this.commissionerAndGoal = value;
    }

    /**
     * Gets the value of the dataGenerator property.
     * 
     * @return
     *     possible object is
     *     {@link DataGeneratorType }
     *     
     */
    public DataGeneratorType getDataGenerator() {
        return dataGenerator;
    }

    /**
     * Sets the value of the dataGenerator property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataGeneratorType }
     *     
     */
    public void setDataGenerator(DataGeneratorType value) {
        this.dataGenerator = value;
    }

    /**
     * Gets the value of the dataEntryBy property.
     * 
     * @return
     *     possible object is
     *     {@link DataEntryByType }
     *     
     */
    public DataEntryByType getDataEntryBy() {
        return dataEntryBy;
    }

    /**
     * Sets the value of the dataEntryBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataEntryByType }
     *     
     */
    public void setDataEntryBy(DataEntryByType value) {
        this.dataEntryBy = value;
    }

    /**
     * Gets the value of the publicationAndOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link PublicationAndOwnershipType }
     *     
     */
    public PublicationAndOwnershipType getPublicationAndOwnership() {
        return publicationAndOwnership;
    }

    /**
     * Sets the value of the publicationAndOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicationAndOwnershipType }
     *     
     */
    public void setPublicationAndOwnership(PublicationAndOwnershipType value) {
        this.publicationAndOwnership = value;
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