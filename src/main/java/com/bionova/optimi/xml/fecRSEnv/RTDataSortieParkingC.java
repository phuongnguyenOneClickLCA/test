
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Parking_C complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Parking_C">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_Eec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Event" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Parking_C", propOrder = {
    "oEec",
    "oEvent"
})
public class RTDataSortieParkingC
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Eec")
    protected double oEec;
    @XmlElement(name = "O_Event")
    protected double oEvent;

    /**
     * Gets the value of the oEec property.
     * 
     */
    public double getOEec() {
        return oEec;
    }

    /**
     * Sets the value of the oEec property.
     * 
     */
    public void setOEec(double value) {
        this.oEec = value;
    }

    /**
     * Gets the value of the oEvent property.
     * 
     */
    public double getOEvent() {
        return oEvent;
    }

    /**
     * Sets the value of the oEvent property.
     * 
     */
    public void setOEvent(double value) {
        this.oEvent = value;
    }

}
