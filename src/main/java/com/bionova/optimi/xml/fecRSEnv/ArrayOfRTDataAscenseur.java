
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
 * <p>Java class for ArrayOfRT_Data_Ascenseur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Ascenseur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Ascenseur" type="{}RT_Data_Ascenseur" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Ascenseur", propOrder = {
    "ascenseur"
})
public class ArrayOfRTDataAscenseur {

    @XmlElement(name = "Ascenseur", nillable = true)
    protected List<RTDataAscenseur> ascenseur;

    /**
     * Gets the value of the ascenseur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ascenseur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAscenseur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataAscenseur }
     * 
     * 
     */
    public List<RTDataAscenseur> getAscenseur() {
        if (ascenseur == null) {
            ascenseur = new ArrayList<RTDataAscenseur>();
        }
        return this.ascenseur;
    }

}
