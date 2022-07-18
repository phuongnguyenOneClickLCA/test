
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Gestion_Regulation_Thermostat_Ballon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Gestion_Regulation_Thermostat_Ballon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Gestion_Appoint_Nuit" type="{}Gestion_Appoint_Nuit_Data" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Gestion_Regulation_Thermostat_Ballon", propOrder = {
    "gestionAppointNuit"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataGestionRegulationThermostatBallon {

    @XmlElementRef(name = "Gestion_Appoint_Nuit", type = JAXBElement.class, required = false)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected JAXBElement<GestionAppointNuitData> gestionAppointNuit;

    /**
     * Gets the value of the gestionAppointNuit property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link GestionAppointNuitData }{@code >}
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public JAXBElement<GestionAppointNuitData> getGestionAppointNuit() {
        return gestionAppointNuit;
    }

    /**
     * Sets the value of the gestionAppointNuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link GestionAppointNuitData }{@code >}
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGestionAppointNuit(JAXBElement<GestionAppointNuitData> value) {
        this.gestionAppointNuit = value;
    }

}
