
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
 * Liste ballons appoint
 * 
 * <p>Java class for ArrayOfChoice1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChoice1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Source_Ballon_Appoint_Effet_Joule" type="{}RT_Data_Source_Ballon_Appoint_Effet_Joule"/>
 *         &lt;element name="Source_Ballon_Appoint_Combustion" type="{}RT_Data_Source_Ballon_Appoint_Combustion"/>
 *         &lt;element name="Source_Ballon_Appoint_Reseau_Fourniture" type="{}RT_Data_Source_Ballon_Appoint_Reseau_Fourniture"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChoice1", propOrder = {
    "sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfChoice1 {

    @XmlElements({
        @XmlElement(name = "Source_Ballon_Appoint_Effet_Joule", type = RTDataSourceBallonAppointEffetJoule.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Appoint_Combustion", type = RTDataSourceBallonAppointCombustion.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Appoint_Reseau_Fourniture", type = RTDataSourceBallonAppointReseauFourniture.class, nillable = true)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Object> sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture;

    /**
     * Gets the value of the sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSourceBallonAppointEffetJoule }
     * {@link RTDataSourceBallonAppointCombustion }
     * {@link RTDataSourceBallonAppointReseauFourniture }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Object> getSourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture() {
        if (sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture == null) {
            sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture = new ArrayList<Object>();
        }
        return this.sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture;
    }

}
