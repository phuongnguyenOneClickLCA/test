
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * espaces tampons
 * 
 * <p>Java class for ArrayOfChoice3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChoice3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Espace_Tampon_Solarise" type="{}RT_Data_Espace_Tampon_Solarise"/>
 *         &lt;element name="Espace_Tampon_Non_Solarise" type="{}RT_Data_Espace_Tampon_Non_Solarise"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChoice3", propOrder = {
    "espaceTamponSolariseOrEspaceTamponNonSolarise"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfChoice3 {

    @XmlElements({
        @XmlElement(name = "Espace_Tampon_Solarise", type = RTDataEspaceTamponSolarise.class, nillable = true),
        @XmlElement(name = "Espace_Tampon_Non_Solarise", type = RTDataEspaceTamponNonSolarise.class, nillable = true)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Object> espaceTamponSolariseOrEspaceTamponNonSolarise;

    /**
     * Gets the value of the espaceTamponSolariseOrEspaceTamponNonSolarise property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the espaceTamponSolariseOrEspaceTamponNonSolarise property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspaceTamponSolariseOrEspaceTamponNonSolarise().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataEspaceTamponSolarise }
     * {@link RTDataEspaceTamponNonSolarise }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Object> getEspaceTamponSolariseOrEspaceTamponNonSolarise() {
        if (espaceTamponSolariseOrEspaceTamponNonSolarise == null) {
            espaceTamponSolariseOrEspaceTamponNonSolarise = new ArrayList<Object>();
        }
        return this.espaceTamponSolariseOrEspaceTamponNonSolarise;
    }

}
