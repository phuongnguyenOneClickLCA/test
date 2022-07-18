
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Emetteur_ECS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Emetteur_ECS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Emetteur_ECS" type="{}RT_Data_Emetteur_ECS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Emetteur_ECS", propOrder = {
    "emetteurECS"
})
public class ArrayOfRTDataEmetteurECS {

    @XmlElement(name = "Emetteur_ECS", nillable = true)
    protected List<RTDataEmetteurECS> emetteurECS;

    /**
     * Gets the value of the emetteurECS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emetteurECS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmetteurECS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataEmetteurECS }
     * 
     * 
     */
    public List<RTDataEmetteurECS> getEmetteurECS() {
        if (emetteurECS == null) {
            emetteurECS = new ArrayList<RTDataEmetteurECS>();
        }
        return this.emetteurECS;
    }

}
