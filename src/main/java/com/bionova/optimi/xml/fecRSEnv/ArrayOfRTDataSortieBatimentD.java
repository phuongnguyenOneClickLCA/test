
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Batiment_D complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Batiment_D">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Batiment_D" type="{}RT_Data_Sortie_Batiment_D" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Batiment_D", propOrder = {
    "sortieBatimentD"
})
public class ArrayOfRTDataSortieBatimentD {

    @XmlElement(name = "Sortie_Batiment_D", nillable = true)
    protected List<RTDataSortieBatimentD> sortieBatimentD;

    /**
     * Gets the value of the sortieBatimentD property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieBatimentD property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieBatimentD().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieBatimentD }
     * 
     * 
     */
    public List<RTDataSortieBatimentD> getSortieBatimentD() {
        if (sortieBatimentD == null) {
            sortieBatimentD = new ArrayList<RTDataSortieBatimentD>();
        }
        return this.sortieBatimentD;
    }

}
