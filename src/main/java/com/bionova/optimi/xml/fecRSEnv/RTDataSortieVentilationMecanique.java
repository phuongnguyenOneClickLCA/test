
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Ventilation_Mecanique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Ventilation_Mecanique">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Taux_AN" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Ventilation_Mecanique", propOrder = {
    "tauxAN"
})
public class RTDataSortieVentilationMecanique
    extends RTDataSortieBase
{

    @XmlElement(name = "Taux_AN")
    protected double tauxAN;

    /**
     * Gets the value of the tauxAN property.
     * 
     */
    public double getTauxAN() {
        return tauxAN;
    }

    /**
     * Sets the value of the tauxAN property.
     * 
     */
    public void setTauxAN(double value) {
        this.tauxAN = value;
    }

}
