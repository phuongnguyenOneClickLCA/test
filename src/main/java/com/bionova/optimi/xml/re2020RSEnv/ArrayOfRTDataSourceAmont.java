
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
 * <p>Java class for ArrayOfRT_Data_Source_Amont complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Source_Amont">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Source_Amont" type="{}RT_Data_Source_Amont" minOccurs="0"/>
 *         &lt;element ref="{}T5_Nibe_Source_Amont_Melange_AirExtraitExterieur"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Source_Amont", propOrder = {
    "sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataSourceAmont {

    @XmlElements({
        @XmlElement(name = "Source_Amont", nillable = true),
        @XmlElement(name = "T5_Nibe_Source_Amont_Melange_AirExtraitExterieur", type = RTDataSourceAmontAirExtraitAirExterieurNibe.class, nillable = true)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<RTDataSourceAmont> sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur;

    /**
     * Gets the value of the sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSourceAmont }
     * {@link RTDataSourceAmontAirExtraitAirExterieurNibe }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<RTDataSourceAmont> getSourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur() {
        if (sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur == null) {
            sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur = new ArrayList<RTDataSourceAmont>();
        }
        return this.sourceAmontOrT5NibeSourceAmontMelangeAirExtraitExterieur;
    }

}
