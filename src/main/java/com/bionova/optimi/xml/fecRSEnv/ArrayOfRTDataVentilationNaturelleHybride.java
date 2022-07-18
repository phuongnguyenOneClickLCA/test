
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Ventilation_Naturelle_Hybride complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Ventilation_Naturelle_Hybride">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Ventilation_Naturelle_Hybride" type="{}RT_Data_Ventilation_Naturelle_Hybride" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Ventilation_Naturelle_Hybride", propOrder = {
    "ventilationNaturelleHybride"
})
public class ArrayOfRTDataVentilationNaturelleHybride {

    @XmlElement(name = "Ventilation_Naturelle_Hybride", nillable = true)
    protected List<RTDataVentilationNaturelleHybride> ventilationNaturelleHybride;

    /**
     * Gets the value of the ventilationNaturelleHybride property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ventilationNaturelleHybride property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVentilationNaturelleHybride().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataVentilationNaturelleHybride }
     * 
     * 
     */
    public List<RTDataVentilationNaturelleHybride> getVentilationNaturelleHybride() {
        if (ventilationNaturelleHybride == null) {
            ventilationNaturelleHybride = new ArrayList<RTDataVentilationNaturelleHybride>();
        }
        return this.ventilationNaturelleHybride;
    }

}
