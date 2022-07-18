
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
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * <p>Java class for PublicationAndOwnershipType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublicationAndOwnershipType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}dateOfLastRevision" minOccurs="0"/>
 *         &lt;group ref="{http://lca.jrc.it/ILCD/Common}PublicationAndOwnershipGroup1"/>
 *         &lt;group ref="{http://lca.jrc.it/ILCD/Common}PublicationAndOwnershipGroup2"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}referenceToRegistrationAuthority" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}registrationNumber" minOccurs="0"/>
 *         &lt;element ref="{http://lca.jrc.it/ILCD/Common}referenceToOwnershipOfDataSet" minOccurs="0"/>
 *         &lt;group ref="{http://lca.jrc.it/ILCD/Common}PublicationAndOwnershipGroup3"/>
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
@XmlType(name = "PublicationAndOwnershipType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "dateOfLastRevision",
    "dataSetVersion",
    "referenceToPrecedingDataSetVersion",
    "permanentDataSetURI",
    "workflowAndPublicationStatus",
    "referenceToUnchangedRepublication",
    "referenceToRegistrationAuthority",
    "registrationNumber",
    "referenceToOwnershipOfDataSet",
    "copyright",
    "referenceToEntitiesWithExclusiveAccess",
    "licenseType",
    "accessRestrictions",
    "other"
})
public class PublicationAndOwnershipType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfLastRevision;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common", required = true)
    protected String dataSetVersion;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<GlobalReferenceType> referenceToPrecedingDataSetVersion;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "anyURI")
    protected String permanentDataSetURI;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected WorkflowAndPublicationStatusValues workflowAndPublicationStatus;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected GlobalReferenceType referenceToUnchangedRepublication;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected GlobalReferenceType referenceToRegistrationAuthority;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected String registrationNumber;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected GlobalReferenceType referenceToOwnershipOfDataSet;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Boolean copyright;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<GlobalReferenceType> referenceToEntitiesWithExclusiveAccess;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    @XmlSchemaType(name = "string")
    protected LicenseTypeValues licenseType;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<FTMultiLang> accessRestrictions;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Date when the data set was revised for the last time, typically manually set.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfLastRevision() {
        return dateOfLastRevision;
    }

    /**
     * Sets the value of the dateOfLastRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfLastRevision(XMLGregorianCalendar value) {
        this.dateOfLastRevision = value;
    }

    /**
     * Gets the value of the dataSetVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSetVersion() {
        return dataSetVersion;
    }

    /**
     * Sets the value of the dataSetVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSetVersion(String value) {
        this.dataSetVersion = value;
    }

    /**
     * Gets the value of the referenceToPrecedingDataSetVersion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToPrecedingDataSetVersion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToPrecedingDataSetVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToPrecedingDataSetVersion() {
        if (referenceToPrecedingDataSetVersion == null) {
            referenceToPrecedingDataSetVersion = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToPrecedingDataSetVersion;
    }

    /**
     * Gets the value of the permanentDataSetURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermanentDataSetURI() {
        return permanentDataSetURI;
    }

    /**
     * Sets the value of the permanentDataSetURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermanentDataSetURI(String value) {
        this.permanentDataSetURI = value;
    }

    /**
     * Gets the value of the workflowAndPublicationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowAndPublicationStatusValues }
     *     
     */
    public WorkflowAndPublicationStatusValues getWorkflowAndPublicationStatus() {
        return workflowAndPublicationStatus;
    }

    /**
     * Sets the value of the workflowAndPublicationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowAndPublicationStatusValues }
     *     
     */
    public void setWorkflowAndPublicationStatus(WorkflowAndPublicationStatusValues value) {
        this.workflowAndPublicationStatus = value;
    }

    /**
     * Gets the value of the referenceToUnchangedRepublication property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalReferenceType }
     *     
     */
    public GlobalReferenceType getReferenceToUnchangedRepublication() {
        return referenceToUnchangedRepublication;
    }

    /**
     * Sets the value of the referenceToUnchangedRepublication property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReferenceType }
     *     
     */
    public void setReferenceToUnchangedRepublication(GlobalReferenceType value) {
        this.referenceToUnchangedRepublication = value;
    }

    /**
     * "Contact data set" of the authority that has registered this data set.
     * 
     * @return
     *     possible object is
     *     {@link GlobalReferenceType }
     *     
     */
    public GlobalReferenceType getReferenceToRegistrationAuthority() {
        return referenceToRegistrationAuthority;
    }

    /**
     * Sets the value of the referenceToRegistrationAuthority property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReferenceType }
     *     
     */
    public void setReferenceToRegistrationAuthority(GlobalReferenceType value) {
        this.referenceToRegistrationAuthority = value;
    }

    /**
     * A unique identifying number for this data set issued by the registration authority.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the value of the registrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationNumber(String value) {
        this.registrationNumber = value;
    }

    /**
     * "Contact data set" of the person or entity who owns this data set. (Note: this is not necessarily the publisher of the data set.)
     * 
     * @return
     *     possible object is
     *     {@link GlobalReferenceType }
     *     
     */
    public GlobalReferenceType getReferenceToOwnershipOfDataSet() {
        return referenceToOwnershipOfDataSet;
    }

    /**
     * Sets the value of the referenceToOwnershipOfDataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalReferenceType }
     *     
     */
    public void setReferenceToOwnershipOfDataSet(GlobalReferenceType value) {
        this.referenceToOwnershipOfDataSet = value;
    }

    /**
     * Indicates whether or not a copyright on the data set exists. Decided upon by the "Owner of data set". [Note: See also field "Access and use
     *       restrictions".]
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyright() {
        return copyright;
    }

    /**
     * Sets the value of the copyright property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyright(Boolean value) {
        this.copyright = value;
    }

    /**
     * Gets the value of the referenceToEntitiesWithExclusiveAccess property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToEntitiesWithExclusiveAccess property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToEntitiesWithExclusiveAccess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToEntitiesWithExclusiveAccess() {
        if (referenceToEntitiesWithExclusiveAccess == null) {
            referenceToEntitiesWithExclusiveAccess = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToEntitiesWithExclusiveAccess;
    }

    /**
     * Gets the value of the licenseType property.
     * 
     * @return
     *     possible object is
     *     {@link LicenseTypeValues }
     *     
     */
    public LicenseTypeValues getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the value of the licenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link LicenseTypeValues }
     *     
     */
    public void setLicenseType(LicenseTypeValues value) {
        this.licenseType = value;
    }

    /**
     * Access restrictions / use conditions for this data set as free text or referring to e.g. license conditions. In case of no restrictions "None" is
     *       entered.Gets the value of the accessRestrictions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accessRestrictions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccessRestrictions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getAccessRestrictions() {
        if (accessRestrictions == null) {
            accessRestrictions = new ArrayList<FTMultiLang>();
        }
        return this.accessRestrictions;
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
