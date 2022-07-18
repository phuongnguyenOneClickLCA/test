
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
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
 *         &lt;element name="T5_TERREAL_LAHEROOF" type="{}T5_TERREAL_LAHEROOF_Data" minOccurs="0"/>
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
    "espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF"
})
public class ArrayOfChoice3 {

    @XmlElements({
        @XmlElement(name = "Espace_Tampon_Solarise", type = RTDataEspaceTamponSolarise.class, nillable = true),
        @XmlElement(name = "Espace_Tampon_Non_Solarise", type = RTDataEspaceTamponNonSolarise.class, nillable = true),
        @XmlElement(name = "T5_TERREAL_LAHEROOF", type = T5TERREALLAHEROOFData.class)
    })
    protected List<Object> espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF;

    /**
     * Gets the value of the espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEspaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataEspaceTamponSolarise }
     * {@link RTDataEspaceTamponNonSolarise }
     * {@link T5TERREALLAHEROOFData }
     * 
     * 
     */
    public List<Object> getEspaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF() {
        if (espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF == null) {
            espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF = new ArrayList<Object>();
        }
        return this.espaceTamponSolariseOrEspaceTamponNonSolariseOrT5TERREALLAHEROOF;
    }

}
