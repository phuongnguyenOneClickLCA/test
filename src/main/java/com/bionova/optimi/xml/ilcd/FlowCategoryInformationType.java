
package com.bionova.optimi.xml.ilcd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlowCategoryInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlowCategoryInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="elementaryFlowCategorization" type="{http://lca.jrc.it/ILCD/Common}FlowCategorizationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="classification" type="{http://lca.jrc.it/ILCD/Common}ClassificationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlowCategoryInformationType", namespace = "http://lca.jrc.it/ILCD/Common", propOrder = {
    "elementaryFlowCategorization",
    "classification"
})
public class FlowCategoryInformationType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<FlowCategorizationType> elementaryFlowCategorization;
    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<ClassificationType> classification;

    /**
     * Gets the value of the elementaryFlowCategorization property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elementaryFlowCategorization property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElementaryFlowCategorization().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowCategorizationType }
     * 
     * 
     */
    public List<FlowCategorizationType> getElementaryFlowCategorization() {
        if (elementaryFlowCategorization == null) {
            elementaryFlowCategorization = new ArrayList<FlowCategorizationType>();
        }
        return this.elementaryFlowCategorization;
    }

    /**
     * Gets the value of the classification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClassificationType }
     * 
     * 
     */
    public List<ClassificationType> getClassification() {
        if (classification == null) {
            classification = new ArrayList<ClassificationType>();
        }
        return this.classification;
    }

}
