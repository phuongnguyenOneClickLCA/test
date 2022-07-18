
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Ballon_Decentralise complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Ballon_Decentralise">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Ballon_Decentralise" type="{}RT_Data_Ballon_Decentralise" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Ballon_Decentralise", propOrder = {
    "ballonDecentralise"
})
public class ArrayOfRTDataBallonDecentralise {

    @XmlElement(name = "Ballon_Decentralise", nillable = true)
    protected List<RTDataBallonDecentralise> ballonDecentralise;

    /**
     * Gets the value of the ballonDecentralise property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ballonDecentralise property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBallonDecentralise().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataBallonDecentralise }
     * 
     * 
     */
    public List<RTDataBallonDecentralise> getBallonDecentralise() {
        if (ballonDecentralise == null) {
            ballonDecentralise = new ArrayList<RTDataBallonDecentralise>();
        }
        return this.ballonDecentralise;
    }

}
