
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * v8000
 * 
 * <p>Java class for ArrayOfRT_Data_Puits_Hydraulique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Puits_Hydraulique">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Puits_Hydraulique" type="{}RT_Data_Puits_Hydraulique" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Puits_Hydraulique", propOrder = {
    "puitsHydraulique"
})
public class ArrayOfRTDataPuitsHydraulique {

    @XmlElement(name = "Puits_Hydraulique", nillable = true)
    protected List<RTDataPuitsHydraulique> puitsHydraulique;

    /**
     * Gets the value of the puitsHydraulique property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the puitsHydraulique property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPuitsHydraulique().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataPuitsHydraulique }
     * 
     * 
     */
    public List<RTDataPuitsHydraulique> getPuitsHydraulique() {
        if (puitsHydraulique == null) {
            puitsHydraulique = new ArrayList<RTDataPuitsHydraulique>();
        }
        return this.puitsHydraulique;
    }

}
