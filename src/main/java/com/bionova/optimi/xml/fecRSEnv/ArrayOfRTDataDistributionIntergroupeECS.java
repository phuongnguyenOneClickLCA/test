
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
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
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Distribution_Intergroupe_ECS" type="{}RT_Data_Distribution_Intergroupe_ECS" minOccurs="0"/>
 *         &lt;element name="T5_Cardonnel_ModuleAppartement_ECS_Seul" type="{}ModuleAppartementRT2012ECSData" minOccurs="0"/>
 *         &lt;element ref="{}T5_ATLANTIC_Distribution_RBT" minOccurs="0"/>
 *         &lt;element ref="{}T5_Qarnot_Computing_Chaudiere_Numerique_Distribution" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Distribution_Intergroupe_ECS", propOrder = {
    "distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT"
})
public class ArrayOfRTDataDistributionIntergroupeECS {

    @XmlElements({
        @XmlElement(name = "Distribution_Intergroupe_ECS", type = RTDataDistributionIntergroupeECS.class, nillable = true),
        @XmlElement(name = "T5_Cardonnel_ModuleAppartement_ECS_Seul", type = ModuleAppartementRT2012ECSData.class),
        @XmlElement(name = "T5_ATLANTIC_Distribution_RBT", type = T5ATLANTICRBTData.class, nillable = true),
        @XmlElement(name = "T5_Qarnot_Computing_Chaudiere_Numerique_Distribution", type = T5QarnotComputingChaudiereNumeriqueDistributionData.class, nillable = true)
    })
    protected List<Object> distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT;

    /**
     * Gets the value of the distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDistributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataDistributionIntergroupeECS }
     * {@link ModuleAppartementRT2012ECSData }
     * {@link T5ATLANTICRBTData }
     * {@link T5QarnotComputingChaudiereNumeriqueDistributionData }
     * 
     * 
     */
    public List<Object> getDistributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT() {
        if (distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT == null) {
            distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT = new ArrayList<Object>();
        }
        return this.distributionIntergroupeECSAndT5CardonnelModuleAppartementECSSeulAndT5ATLANTICDistributionRBT;
    }

}
