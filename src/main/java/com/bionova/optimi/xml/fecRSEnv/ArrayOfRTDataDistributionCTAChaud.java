
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Distribution_CTA_Chaud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Distribution_CTA_Chaud">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Distribution_CTA_Chaud" type="{}RT_Data_Distribution_CTA_Chaud" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Distribution_CTA_Chaud", propOrder = {
    "distributionCTAChaud"
})
public class ArrayOfRTDataDistributionCTAChaud {

    @XmlElement(name = "Distribution_CTA_Chaud", nillable = true)
    protected List<RTDataDistributionCTAChaud> distributionCTAChaud;

    /**
     * Gets the value of the distributionCTAChaud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distributionCTAChaud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistributionCTAChaud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataDistributionCTAChaud }
     * 
     * 
     */
    public List<RTDataDistributionCTAChaud> getDistributionCTAChaud() {
        if (distributionCTAChaud == null) {
            distributionCTAChaud = new ArrayList<RTDataDistributionCTAChaud>();
        }
        return this.distributionCTAChaud;
    }

}
