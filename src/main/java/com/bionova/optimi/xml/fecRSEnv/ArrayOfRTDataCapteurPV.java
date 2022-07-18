
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Capteur_PV complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Capteur_PV">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Capteur_PV" type="{}RT_Data_Capteur_PV" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Capteur_PV", propOrder = {
    "capteurPV"
})
public class ArrayOfRTDataCapteurPV {

    @XmlElement(name = "Capteur_PV", nillable = true)
    protected List<RTDataCapteurPV> capteurPV;

    /**
     * Gets the value of the capteurPV property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capteurPV property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapteurPV().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataCapteurPV }
     * 
     * 
     */
    public List<RTDataCapteurPV> getCapteurPV() {
        if (capteurPV == null) {
            capteurPV = new ArrayList<RTDataCapteurPV>();
        }
        return this.capteurPV;
    }

}
