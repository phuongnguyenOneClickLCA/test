
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7400 - T5_Uniclima_Gestion_Appoint_Nuit
 * 
 * <p>Java class for T5_Uniclima_Gestion_Appoint_Nuit_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Uniclima_Gestion_Appoint_Nuit_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}Data_Object_Base">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_gestion_base" type="{}RT_Gestion_Thermostat_Ballon"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Uniclima_Gestion_Appoint_Nuit_Data", propOrder = {
    "index",
    "name",
    "typeGestionBase"
})
public class T5UniclimaGestionAppointNuitData
    extends DataObjectBase
{

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Type_gestion_base", required = true)
    protected String typeGestionBase;

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the typeGestionBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionBase() {
        return typeGestionBase;
    }

    /**
     * Sets the value of the typeGestionBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionBase(String value) {
        this.typeGestionBase = value;
    }

}
