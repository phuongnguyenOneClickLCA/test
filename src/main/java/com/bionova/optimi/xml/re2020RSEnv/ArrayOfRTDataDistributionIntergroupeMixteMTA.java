
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Distribution_Intergroupe_Mixte_MTA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Distribution_Intergroupe_Mixte_MTA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}T5_Cardonnel_ModuleAppartement_Mixte"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Distribution_Intergroupe_Mixte_MTA", propOrder = {
    "t5CardonnelModuleAppartementMixte"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataDistributionIntergroupeMixteMTA {

    @XmlElement(name = "T5_Cardonnel_ModuleAppartement_Mixte", nillable = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<ModuleAppartementRE2020MixteData> t5CardonnelModuleAppartementMixte;

    /**
     * Gets the value of the t5CardonnelModuleAppartementMixte property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the t5CardonnelModuleAppartementMixte property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getT5CardonnelModuleAppartementMixte().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModuleAppartementRE2020MixteData }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<ModuleAppartementRE2020MixteData> getT5CardonnelModuleAppartementMixte() {
        if (t5CardonnelModuleAppartementMixte == null) {
            t5CardonnelModuleAppartementMixte = new ArrayList<ModuleAppartementRE2020MixteData>();
        }
        return this.t5CardonnelModuleAppartementMixte;
    }

}
