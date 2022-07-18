
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Ventilation_Mecanique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Ventilation_Mecanique">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Ventilation_Mecanique" type="{}RT_Data_Sortie_Ventilation_Mecanique" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Ventilation_Mecanique", propOrder = {
    "sortieVentilationMecanique"
})
public class ArrayOfRTDataSortieVentilationMecanique {

    @XmlElement(name = "Sortie_Ventilation_Mecanique", nillable = true)
    protected List<RTDataSortieVentilationMecanique> sortieVentilationMecanique;

    /**
     * Gets the value of the sortieVentilationMecanique property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieVentilationMecanique property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieVentilationMecanique().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieVentilationMecanique }
     * 
     * 
     */
    public List<RTDataSortieVentilationMecanique> getSortieVentilationMecanique() {
        if (sortieVentilationMecanique == null) {
            sortieVentilationMecanique = new ArrayList<RTDataSortieVentilationMecanique>();
        }
        return this.sortieVentilationMecanique;
    }

}
