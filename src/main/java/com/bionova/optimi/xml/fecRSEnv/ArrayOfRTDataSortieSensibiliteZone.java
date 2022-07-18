
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Sensibilite_Zone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Sensibilite_Zone">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Sensibilite_Zone" type="{}RT_Data_Sortie_Sensibilite_Zone" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Sensibilite_Zone", propOrder = {
    "sortieSensibiliteZone"
})
public class ArrayOfRTDataSortieSensibiliteZone {

    @XmlElement(name = "Sortie_Sensibilite_Zone", nillable = true)
    protected List<RTDataSortieSensibiliteZone> sortieSensibiliteZone;

    /**
     * Gets the value of the sortieSensibiliteZone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieSensibiliteZone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieSensibiliteZone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSortieSensibiliteZone }
     * 
     * 
     */
    public List<RTDataSortieSensibiliteZone> getSortieSensibiliteZone() {
        if (sortieSensibiliteZone == null) {
            sortieSensibiliteZone = new ArrayList<RTDataSortieSensibiliteZone>();
        }
        return this.sortieSensibiliteZone;
    }

}
