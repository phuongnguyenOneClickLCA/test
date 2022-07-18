
package com.bionova.optimi.xml.ilcd;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "shortDescription"
})
@XmlRootElement(name = "referenceToUnitGroupDataSet", namespace = "http://www.iai.kit.edu/EPD/2013")
public class ReferenceToUnitGroupDataSet {

    @XmlElement(namespace = "http://lca.jrc.it/ILCD/Common")
    protected List<STMultiLang> shortDescription;

    /**
     * Gets the value of the shortDescription property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shortDescription property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShortDescription().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link STMultiLang }
     *
     *
     */
    public List<STMultiLang> getShortDescription() {
        if (shortDescription == null) {
            shortDescription = new ArrayList<STMultiLang>();
        }
        return this.shortDescription;
    }
}
