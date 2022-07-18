
package com.bionova.optimi.xml.fecRSEnv;

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
public class RTDataSortieBatimentE
    extends RTDataSortieBase
{

    @XmlElement(name = "Sortie_Zone_E_Collection")
    protected ArrayOfRTDataSortieZoneE sortieZoneECollection;

    /**
     * Gets the value of the sortieZoneECollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieZoneE }
     *     
     */
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
    public void setSortieZoneECollection(ArrayOfRTDataSortieZoneE value) {
        this.sortieZoneECollection = value;
    }

}
