
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Groupe_B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Groupe_B">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Groupe_B" type="{}RT_Data_Sortie_Groupe_B" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Groupe_B", propOrder = {
    "sortieGroupeB"
})
public class ArrayOfRTDataSortieGroupeB {

    @XmlElement(name = "Sortie_Groupe_B", nillable = true)
    protected List<RTDataSortieGroupeB> sortieGroupeB;

    /**
     * Gets the value of the sortieGroupeB property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieGroupeB property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieGroupeB().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieGroupeB }
     * 
     * 
     */
    public List<RTDataSortieGroupeB> getSortieGroupeB() {
        if (sortieGroupeB == null) {
            sortieGroupeB = new ArrayList<RTDataSortieGroupeB>();
        }
        return this.sortieGroupeB;
    }

}
