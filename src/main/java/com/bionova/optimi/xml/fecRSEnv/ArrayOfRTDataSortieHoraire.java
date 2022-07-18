
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Horaire complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Horaire">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Sortie_Horaire" type="{}RT_Data_Sortie_Horaire" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Horaire", propOrder = {
    "sortieHoraire"
})
public class ArrayOfRTDataSortieHoraire {

    @XmlElement(name = "Sortie_Horaire", nillable = true)
    protected List<RTDataSortieHoraire> sortieHoraire;

    /**
     * Gets the value of the sortieHoraire property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieHoraire property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieHoraire().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieHoraire }
     * 
     * 
     */
    public List<RTDataSortieHoraire> getSortieHoraire() {
        if (sortieHoraire == null) {
            sortieHoraire = new ArrayList<RTDataSortieHoraire>();
        }
        return this.sortieHoraire;
    }

}
