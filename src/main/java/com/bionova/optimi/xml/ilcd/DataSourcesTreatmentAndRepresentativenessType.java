
package com.bionova.optimi.xml.ilcd;

import java.math.BigDecimal;
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
 * <p>Java class for DataSourcesTreatmentAndRepresentativenessType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataSourcesTreatmentAndRepresentativenessType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataCutOffAndCompletenessPrinciples" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="deviationsFromCutOffAndCompletenessPrinciples" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="dataSelectionAndCombinationPrinciples" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="deviationsFromSelectionAndCombinationPrinciples" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="dataTreatmentAndExtrapolationsPrinciples" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="deviationsFromTreatmentAndExtrapolationPrinciples" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="referenceToDataHandlingPrinciples" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="referenceToDataSource" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="percentageSupplyOrProductionCovered" type="{http://lca.jrc.it/ILCD/Common}Perc" minOccurs="0"/>
 *         &lt;element name="annualSupplyOrProductionVolume" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="samplingProcedure" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="dataCollectionPeriod" type="{http://lca.jrc.it/ILCD/Common}StringMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="uncertaintyAdjustments" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
 *         &lt;element name="useAdviceForDataSet" type="{http://lca.jrc.it/ILCD/Common}FTMultiLang" maxOccurs="100" minOccurs="0"/>
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
@XmlType(name = "DataSourcesTreatmentAndRepresentativenessType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "dataCutOffAndCompletenessPrinciples",
    "deviationsFromCutOffAndCompletenessPrinciples",
    "dataSelectionAndCombinationPrinciples",
    "deviationsFromSelectionAndCombinationPrinciples",
    "dataTreatmentAndExtrapolationsPrinciples",
    "deviationsFromTreatmentAndExtrapolationPrinciples",
    "referenceToDataHandlingPrinciples",
    "referenceToDataSource",
    "percentageSupplyOrProductionCovered",
    "annualSupplyOrProductionVolume",
    "samplingProcedure",
    "dataCollectionPeriod",
    "uncertaintyAdjustments",
    "useAdviceForDataSet",
    "other"
})
public class DataSourcesTreatmentAndRepresentativenessType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> dataCutOffAndCompletenessPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> deviationsFromCutOffAndCompletenessPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> dataSelectionAndCombinationPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> deviationsFromSelectionAndCombinationPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> dataTreatmentAndExtrapolationsPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> deviationsFromTreatmentAndExtrapolationPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<GlobalReferenceType> referenceToDataHandlingPrinciples;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<GlobalReferenceType> referenceToDataSource;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected BigDecimal percentageSupplyOrProductionCovered;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<StringMultiLang> annualSupplyOrProductionVolume;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> samplingProcedure;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<StringMultiLang> dataCollectionPeriod;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> uncertaintyAdjustments;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process")
    protected List<FTMultiLang> useAdviceForDataSet;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected Other other;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the dataCutOffAndCompletenessPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataCutOffAndCompletenessPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataCutOffAndCompletenessPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDataCutOffAndCompletenessPrinciples() {
        if (dataCutOffAndCompletenessPrinciples == null) {
            dataCutOffAndCompletenessPrinciples = new ArrayList<FTMultiLang>();
        }
        return this.dataCutOffAndCompletenessPrinciples;
    }

    /**
     * Gets the value of the deviationsFromCutOffAndCompletenessPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviationsFromCutOffAndCompletenessPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviationsFromCutOffAndCompletenessPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDeviationsFromCutOffAndCompletenessPrinciples() {
        if (deviationsFromCutOffAndCompletenessPrinciples == null) {
            deviationsFromCutOffAndCompletenessPrinciples = new ArrayList<FTMultiLang>();
        }
        return this.deviationsFromCutOffAndCompletenessPrinciples;
    }

    /**
     * Gets the value of the dataSelectionAndCombinationPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataSelectionAndCombinationPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataSelectionAndCombinationPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDataSelectionAndCombinationPrinciples() {
        if (dataSelectionAndCombinationPrinciples == null) {
            dataSelectionAndCombinationPrinciples = new ArrayList<FTMultiLang>();
        }
        return this.dataSelectionAndCombinationPrinciples;
    }

    /**
     * Gets the value of the deviationsFromSelectionAndCombinationPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviationsFromSelectionAndCombinationPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviationsFromSelectionAndCombinationPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDeviationsFromSelectionAndCombinationPrinciples() {
        if (deviationsFromSelectionAndCombinationPrinciples == null) {
            deviationsFromSelectionAndCombinationPrinciples = new ArrayList<FTMultiLang>();
        }
        return this.deviationsFromSelectionAndCombinationPrinciples;
    }

    /**
     * Gets the value of the dataTreatmentAndExtrapolationsPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataTreatmentAndExtrapolationsPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataTreatmentAndExtrapolationsPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDataTreatmentAndExtrapolationsPrinciples() {
        if (dataTreatmentAndExtrapolationsPrinciples == null) {
            dataTreatmentAndExtrapolationsPrinciples = new ArrayList<FTMultiLang>();
        }
        return this.dataTreatmentAndExtrapolationsPrinciples;
    }

    /**
     * Gets the value of the deviationsFromTreatmentAndExtrapolationPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deviationsFromTreatmentAndExtrapolationPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeviationsFromTreatmentAndExtrapolationPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getDeviationsFromTreatmentAndExtrapolationPrinciples() {
        if (deviationsFromTreatmentAndExtrapolationPrinciples == null) {
            deviationsFromTreatmentAndExtrapolationPrinciples = new ArrayList<FTMultiLang>();
        }
        return this.deviationsFromTreatmentAndExtrapolationPrinciples;
    }

    /**
     * Gets the value of the referenceToDataHandlingPrinciples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToDataHandlingPrinciples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToDataHandlingPrinciples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToDataHandlingPrinciples() {
        if (referenceToDataHandlingPrinciples == null) {
            referenceToDataHandlingPrinciples = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToDataHandlingPrinciples;
    }

    /**
     * Gets the value of the referenceToDataSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToDataSource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToDataSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToDataSource() {
        if (referenceToDataSource == null) {
            referenceToDataSource = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToDataSource;
    }

    /**
     * Gets the value of the percentageSupplyOrProductionCovered property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentageSupplyOrProductionCovered() {
        return percentageSupplyOrProductionCovered;
    }

    /**
     * Sets the value of the percentageSupplyOrProductionCovered property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentageSupplyOrProductionCovered(BigDecimal value) {
        this.percentageSupplyOrProductionCovered = value;
    }

    /**
     * Gets the value of the annualSupplyOrProductionVolume property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the annualSupplyOrProductionVolume property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnnualSupplyOrProductionVolume().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getAnnualSupplyOrProductionVolume() {
        if (annualSupplyOrProductionVolume == null) {
            annualSupplyOrProductionVolume = new ArrayList<StringMultiLang>();
        }
        return this.annualSupplyOrProductionVolume;
    }

    /**
     * Gets the value of the samplingProcedure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the samplingProcedure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSamplingProcedure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getSamplingProcedure() {
        if (samplingProcedure == null) {
            samplingProcedure = new ArrayList<FTMultiLang>();
        }
        return this.samplingProcedure;
    }

    /**
     * Gets the value of the dataCollectionPeriod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataCollectionPeriod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataCollectionPeriod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMultiLang }
     * 
     * 
     */
    public List<StringMultiLang> getDataCollectionPeriod() {
        if (dataCollectionPeriod == null) {
            dataCollectionPeriod = new ArrayList<StringMultiLang>();
        }
        return this.dataCollectionPeriod;
    }

    /**
     * Gets the value of the uncertaintyAdjustments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uncertaintyAdjustments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUncertaintyAdjustments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getUncertaintyAdjustments() {
        if (uncertaintyAdjustments == null) {
            uncertaintyAdjustments = new ArrayList<FTMultiLang>();
        }
        return this.uncertaintyAdjustments;
    }

    /**
     * Gets the value of the useAdviceForDataSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the useAdviceForDataSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUseAdviceForDataSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FTMultiLang }
     * 
     * 
     */
    public List<FTMultiLang> getUseAdviceForDataSet() {
        if (useAdviceForDataSet == null) {
            useAdviceForDataSet = new ArrayList<FTMultiLang>();
        }
        return this.useAdviceForDataSet;
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
