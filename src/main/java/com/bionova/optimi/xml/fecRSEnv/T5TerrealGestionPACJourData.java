
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7000
 * 
 * <p>Java class for T5_Terreal_Gestion_PAC_Jour_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Terreal_Gestion_PAC_Jour_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}Data_Object_Base">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="Type_gestion_th_base" type="{}RT_Gestion_Thermostat_Ballon_Extension" minOccurs="0"/>
 *         &lt;element name="Type_gestion_th_appoint" type="{}RT_Gestion_Thermostat_Ballon_Extension"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Terreal_Gestion_PAC_Jour_Data", propOrder = {
    "index",
    "name",
    "typeGestionThBase",
    "typeGestionThAppoint"
})
public class T5TerrealGestionPACJourData
    extends DataObjectBase
{

    @XmlElement(name = "Index", required = true)
    protected Object index;
    @XmlElement(name = "Name")
    protected Object name;
    @XmlElement(name = "Type_gestion_th_base")
    protected String typeGestionThBase;
    @XmlElement(name = "Type_gestion_th_appoint", required = true)
    protected String typeGestionThAppoint;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setIndex(Object value) {
        this.index = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setName(Object value) {
        this.name = value;
    }

    /**
     * Gets the value of the typeGestionThBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionThBase() {
        return typeGestionThBase;
    }

    /**
     * Sets the value of the typeGestionThBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionThBase(String value) {
        this.typeGestionThBase = value;
    }

    /**
     * Gets the value of the typeGestionThAppoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionThAppoint() {
        return typeGestionThAppoint;
    }

    /**
     * Sets the value of the typeGestionThAppoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionThAppoint(String value) {
        this.typeGestionThAppoint = value;
    }

}
