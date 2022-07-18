
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Eclairage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Eclairage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Eclairage" type="{}RT_Data_Eclairage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Eclairage", propOrder = {
    "eclairage"
})
public class ArrayOfRTDataEclairage {

    @XmlElement(name = "Eclairage", nillable = true)
    protected List<RTDataEclairage> eclairage;

    /**
     * Gets the value of the eclairage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eclairage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEclairage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataEclairage }
     * 
     * 
     */
    public List<RTDataEclairage> getEclairage() {
        if (eclairage == null) {
            eclairage = new ArrayList<RTDataEclairage>();
        }
        return this.eclairage;
    }

}