
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
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
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataVentilationNaturelleHybride {

    @XmlElement(name = "Ventilation_Naturelle_Hybride", nillable = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<RTDataVentilationNaturelleHybride> getVentilationNaturelleHybride() {
        if (ventilationNaturelleHybride == null) {
            ventilationNaturelleHybride = new ArrayList<RTDataVentilationNaturelleHybride>();
        }
        return this.ventilationNaturelleHybride;
    }

}
