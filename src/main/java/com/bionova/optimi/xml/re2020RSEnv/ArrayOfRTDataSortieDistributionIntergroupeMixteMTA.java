
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Distribution_Intergroupe_Mixte_MTA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Distribution_Intergroupe_Mixte_MTA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Distribution_Intergroupe_Mixte_MTA" type="{}RT_Data_Sortie_Distribution_Intergroupe_Mixte_MTA" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Distribution_Intergroupe_Mixte_MTA", propOrder = {
    "sortieDistributionIntergroupeMixteMTA"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataSortieDistributionIntergroupeMixteMTA {

    @XmlElement(name = "Sortie_Distribution_Intergroupe_Mixte_MTA", nillable = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<RTDataSortieDistributionIntergroupeMixteMTA> sortieDistributionIntergroupeMixteMTA;

    /**
     * Gets the value of the sortieDistributionIntergroupeMixteMTA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieDistributionIntergroupeMixteMTA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieDistributionIntergroupeMixteMTA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieDistributionIntergroupeMixteMTA }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<RTDataSortieDistributionIntergroupeMixteMTA> getSortieDistributionIntergroupeMixteMTA() {
        if (sortieDistributionIntergroupeMixteMTA == null) {
            sortieDistributionIntergroupeMixteMTA = new ArrayList<RTDataSortieDistributionIntergroupeMixteMTA>();
        }
        return this.sortieDistributionIntergroupeMixteMTA;
    }

}