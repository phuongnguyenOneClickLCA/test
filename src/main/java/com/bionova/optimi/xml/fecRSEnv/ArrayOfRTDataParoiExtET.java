
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Paroi_Ext_ET complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Paroi_Ext_ET">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Paroi_Ext_ET" type="{}RT_Data_Paroi_Ext_ET" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Paroi_Ext_ET", propOrder = {
    "paroiExtET"
})
public class ArrayOfRTDataParoiExtET {

    @XmlElement(name = "Paroi_Ext_ET", nillable = true)
    protected List<RTDataParoiExtET> paroiExtET;

    /**
     * Gets the value of the paroiExtET property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paroiExtET property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParoiExtET().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataParoiExtET }
     * 
     * 
     */
    public List<RTDataParoiExtET> getParoiExtET() {
        if (paroiExtET == null) {
            paroiExtET = new ArrayList<RTDataParoiExtET>();
        }
        return this.paroiExtET;
    }

}
