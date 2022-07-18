
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Emetteur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Emetteur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Emetteur" type="{}RT_Data_Emetteur" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Emetteur", propOrder = {
    "emetteur"
})
public class ArrayOfRTDataEmetteur {

    @XmlElement(name = "Emetteur", nillable = true)
    protected List<RTDataEmetteur> emetteur;

    /**
     * Gets the value of the emetteur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emetteur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmetteur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataEmetteur }
     * 
     * 
     */
    public List<RTDataEmetteur> getEmetteur() {
        if (emetteur == null) {
            emetteur = new ArrayList<RTDataEmetteur>();
        }
        return this.emetteur;
    }

}
