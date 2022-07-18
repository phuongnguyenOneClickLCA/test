
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Batiment_E complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Batiment_E">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Zone_E_Collection" type="{}ArrayOfRT_Data_Sortie_Zone_E" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Batiment_E", propOrder = {
    "sortieZoneECollection"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieBatimentE
    extends RTDataSortieBase
{

    @XmlElement(name = "Sortie_Zone_E_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieZoneE sortieZoneECollection;

    /**
     * Gets the value of the sortieZoneECollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieZoneE }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieZoneE getSortieZoneECollection() {
        return sortieZoneECollection;
    }

    /**
     * Sets the value of the sortieZoneECollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieZoneE }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieZoneECollection(ArrayOfRTDataSortieZoneE value) {
        this.sortieZoneECollection = value;
    }

}
