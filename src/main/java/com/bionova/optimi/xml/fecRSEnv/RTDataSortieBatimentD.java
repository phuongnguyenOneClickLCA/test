
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Batiment_D complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Batiment_D">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Zone_D_Collection" type="{}ArrayOfRT_Data_Sortie_Zone_D" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Batiment_D", propOrder = {
    "sortieZoneDCollection"
})
public class RTDataSortieBatimentD
    extends RTDataSortieBase
{

    @XmlElement(name = "Sortie_Zone_D_Collection")
    protected ArrayOfRTDataSortieZoneD sortieZoneDCollection;

    /**
     * Gets the value of the sortieZoneDCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieZoneD }
     *     
     */
    public ArrayOfRTDataSortieZoneD getSortieZoneDCollection() {
        return sortieZoneDCollection;
    }

    /**
     * Sets the value of the sortieZoneDCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieZoneD }
     *     
     */
    public void setSortieZoneDCollection(ArrayOfRTDataSortieZoneD value) {
        this.sortieZoneDCollection = value;
    }

}
