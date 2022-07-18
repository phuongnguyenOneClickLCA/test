
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
 * <p>Java class for ArrayOfRT_Data_Brasseur_Air complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Brasseur_Air">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Brasseur_Air" type="{}RT_Data_Brasseur_Air" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Brasseur_Air", propOrder = {
    "brasseurAir"
})
public class ArrayOfRTDataBrasseurAir {

    @XmlElement(name = "Brasseur_Air", nillable = true)
    protected List<RTDataBrasseurAir> brasseurAir;

    /**
     * Gets the value of the brasseurAir property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the brasseurAir property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBrasseurAir().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataBrasseurAir }
     * 
     * 
     */
    public List<RTDataBrasseurAir> getBrasseurAir() {
        if (brasseurAir == null) {
            brasseurAir = new ArrayList<RTDataBrasseurAir>();
        }
        return this.brasseurAir;
    }

}
