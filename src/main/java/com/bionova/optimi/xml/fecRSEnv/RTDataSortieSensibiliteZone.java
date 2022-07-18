
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Sensibilite_Zone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Sensibilite_Zone">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Sensibilite_Groupe_Collection" type="{}ArrayOfRT_Data_Sortie_Sensibilite_Groupe" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Sensibilite_Zone", propOrder = {
    "sortieSensibiliteGroupeCollection"
})
public class RTDataSortieSensibiliteZone
    extends RTDataSortieBase
{

    @XmlElement(name = "Sortie_Sensibilite_Groupe_Collection")
    protected ArrayOfRTDataSortieSensibiliteGroupe sortieSensibiliteGroupeCollection;

    /**
     * Gets the value of the sortieSensibiliteGroupeCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieSensibiliteGroupe }
     *     
     */
    public ArrayOfRTDataSortieSensibiliteGroupe getSortieSensibiliteGroupeCollection() {
        return sortieSensibiliteGroupeCollection;
    }

    /**
     * Sets the value of the sortieSensibiliteGroupeCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieSensibiliteGroupe }
     *     
     */
    public void setSortieSensibiliteGroupeCollection(ArrayOfRTDataSortieSensibiliteGroupe value) {
        this.sortieSensibiliteGroupeCollection = value;
    }

}
