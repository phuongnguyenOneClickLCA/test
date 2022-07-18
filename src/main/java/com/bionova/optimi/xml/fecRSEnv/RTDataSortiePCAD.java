
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_PCAD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_PCAD">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Generateur_Collection" type="{}ArrayOfRT_Data_Sortie_Generateur" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_PCAD", propOrder = {
    "sortieGenerateurCollection"
})
public class RTDataSortiePCAD
    extends RTDataSortieBase
{

    @XmlElement(name = "Sortie_Generateur_Collection")
    protected ArrayOfRTDataSortieGenerateur sortieGenerateurCollection;

    /**
     * Gets the value of the sortieGenerateurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGenerateur }
     *     
     */
    public ArrayOfRTDataSortieGenerateur getSortieGenerateurCollection() {
        return sortieGenerateurCollection;
    }

    /**
     * Sets the value of the sortieGenerateurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGenerateur }
     *     
     */
    public void setSortieGenerateurCollection(ArrayOfRTDataSortieGenerateur value) {
        this.sortieGenerateurCollection = value;
    }

}
