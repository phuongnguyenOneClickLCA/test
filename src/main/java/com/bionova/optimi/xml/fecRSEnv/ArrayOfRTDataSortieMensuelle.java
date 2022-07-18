
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Mensuelle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Mensuelle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Mensuelle" type="{}RT_Data_Sortie_Mensuelle" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Mensuelle", propOrder = {
    "sortieMensuelle"
})
public class ArrayOfRTDataSortieMensuelle {

    @XmlElement(name = "Sortie_Mensuelle", nillable = true)
    protected List<RTDataSortieMensuelle> sortieMensuelle;

    /**
     * Gets the value of the sortieMensuelle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieMensuelle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieMensuelle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieMensuelle }
     * 
     * 
     */
    public List<RTDataSortieMensuelle> getSortieMensuelle() {
        if (sortieMensuelle == null) {
            sortieMensuelle = new ArrayList<RTDataSortieMensuelle>();
        }
        return this.sortieMensuelle;
    }

}
