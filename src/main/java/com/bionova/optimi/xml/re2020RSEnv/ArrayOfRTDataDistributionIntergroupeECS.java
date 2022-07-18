
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Distribution_Intergroupe_ECS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Distribution_Intergroupe_ECS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Distribution_Intergroupe_ECS" type="{}RT_Data_Distribution_Intergroupe_ECS"/>
 *         &lt;element ref="{}T5_Cardonnel_ModuleAppartement_ECS_Seul"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Distribution_Intergroupe_ECS", propOrder = {
    "distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataDistributionIntergroupeECS {

    @XmlElements({
        @XmlElement(name = "Distribution_Intergroupe_ECS", type = RTDataDistributionIntergroupeECS.class, nillable = true),
        @XmlElement(name = "T5_Cardonnel_ModuleAppartement_ECS_Seul", type = ModuleAppartementRE2020ECSData.class, nillable = true)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Object> distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul;

    /**
     * Gets the value of the distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataDistributionIntergroupeECS }
     * {@link ModuleAppartementRE2020ECSData }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Object> getDistributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul() {
        if (distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul == null) {
            distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul = new ArrayList<Object>();
        }
        return this.distributionIntergroupeECSOrT5CardonnelModuleAppartementECSSeul;
    }

}
