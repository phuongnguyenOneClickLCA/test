
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Zone_E complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Zone_E">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Theta_Ext" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *         &lt;element name="Isr_Horiz" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *         &lt;element name="Sortie_Groupe_E_Collection" type="{}ArrayOfRT_Data_Sortie_Groupe_E" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Zone_E", propOrder = {
    "thetaExt",
    "isrHoriz",
    "sortieGroupeECollection"
})
public class RTDataSortieZoneE
    extends RTDataSortieBase
{

    @XmlElement(name = "Theta_Ext")
    protected ArrayOfRTDataSortieHoraire thetaExt;
    @XmlElement(name = "Isr_Horiz")
    protected ArrayOfRTDataSortieHoraire isrHoriz;
    @XmlElement(name = "Sortie_Groupe_E_Collection")
    protected ArrayOfRTDataSortieGroupeE sortieGroupeECollection;

    /**
     * Gets the value of the thetaExt property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public ArrayOfRTDataSortieHoraire getThetaExt() {
        return thetaExt;
    }

    /**
     * Sets the value of the thetaExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public void setThetaExt(ArrayOfRTDataSortieHoraire value) {
        this.thetaExt = value;
    }

    /**
     * Gets the value of the isrHoriz property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public ArrayOfRTDataSortieHoraire getIsrHoriz() {
        return isrHoriz;
    }

    /**
     * Sets the value of the isrHoriz property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public void setIsrHoriz(ArrayOfRTDataSortieHoraire value) {
        this.isrHoriz = value;
    }

    /**
     * Gets the value of the sortieGroupeECollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGroupeE }
     *     
     */
    public ArrayOfRTDataSortieGroupeE getSortieGroupeECollection() {
        return sortieGroupeECollection;
    }

    /**
     * Sets the value of the sortieGroupeECollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGroupeE }
     *     
     */
    public void setSortieGroupeECollection(ArrayOfRTDataSortieGroupeE value) {
        this.sortieGroupeECollection = value;
    }

}
