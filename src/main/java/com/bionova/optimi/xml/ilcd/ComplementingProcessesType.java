
package com.bionova.optimi.xml.ilcd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplementingProcessesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComplementingProcessesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referenceToComplementingProcess" type="{http://lca.jrc.it/ILCD/Common}GlobalReferenceType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplementingProcessesType", namespace = "http://lca.jrc.it/ILCD/Process", propOrder = {
    "referenceToComplementingProcess"
})
public class ComplementingProcessesType {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Process", required = true)
    protected List<GlobalReferenceType> referenceToComplementingProcess;

    /**
     * Gets the value of the referenceToComplementingProcess property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceToComplementingProcess property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceToComplementingProcess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GlobalReferenceType }
     * 
     * 
     */
    public List<GlobalReferenceType> getReferenceToComplementingProcess() {
        if (referenceToComplementingProcess == null) {
            referenceToComplementingProcess = new ArrayList<GlobalReferenceType>();
        }
        return this.referenceToComplementingProcess;
    }

}
