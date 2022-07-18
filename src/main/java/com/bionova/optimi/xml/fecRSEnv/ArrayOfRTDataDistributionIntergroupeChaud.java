
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Distribution_Intergroupe_Chaud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Distribution_Intergroupe_Chaud">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Distribution_Intergroupe_Chaud" type="{}RT_Data_Distribution_Intergroupe_Chaud" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}T5_BAELZ_hydroejecteur" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Distribution_Intergroupe_Chaud", propOrder = {
    "distributionIntergroupeChaudAndT5BAELZHydroejecteur"
})
public class ArrayOfRTDataDistributionIntergroupeChaud {

    @XmlElements({
        @XmlElement(name = "Distribution_Intergroupe_Chaud", type = RTDataDistributionIntergroupeChaud.class, nillable = true),
        @XmlElement(name = "T5_BAELZ_hydroejecteur", type = T5BAELZHydroejecteurData.class, nillable = true)
    })
    protected List<Object> distributionIntergroupeChaudAndT5BAELZHydroejecteur;

    /**
     * Gets the value of the distributionIntergroupeChaudAndT5BAELZHydroejecteur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distributionIntergroupeChaudAndT5BAELZHydroejecteur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistributionIntergroupeChaudAndT5BAELZHydroejecteur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataDistributionIntergroupeChaud }
     * {@link T5BAELZHydroejecteurData }
     * 
     * 
     */
    public List<Object> getDistributionIntergroupeChaudAndT5BAELZHydroejecteur() {
        if (distributionIntergroupeChaudAndT5BAELZHydroejecteur == null) {
            distributionIntergroupeChaudAndT5BAELZHydroejecteur = new ArrayList<Object>();
        }
        return this.distributionIntergroupeChaudAndT5BAELZHydroejecteur;
    }

}
