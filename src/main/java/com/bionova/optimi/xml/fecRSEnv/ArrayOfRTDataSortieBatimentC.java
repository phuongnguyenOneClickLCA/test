
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Batiment_C complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Batiment_C">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Batiment_C" type="{}RT_Data_Sortie_Batiment_C" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Batiment_C", propOrder = {
    "sortieBatimentC"
})
public class ArrayOfRTDataSortieBatimentC {

    @XmlElement(name = "Sortie_Batiment_C", nillable = true)
    protected List<RTDataSortieBatimentC> sortieBatimentC;

    /**
     * Gets the value of the sortieBatimentC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieBatimentC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieBatimentC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieBatimentC }
     * 
     * 
     */
    public List<RTDataSortieBatimentC> getSortieBatimentC() {
        if (sortieBatimentC == null) {
            sortieBatimentC = new ArrayList<RTDataSortieBatimentC>();
        }
        return this.sortieBatimentC;
    }

}
