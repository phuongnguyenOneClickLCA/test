
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Emission_Rafraichissement_Direct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Emission_Rafraichissement_Direct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Geocooling_NonClimatise_Emission" type="{}Geocooling_NonClimatise_Emission_Data"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Emission_Rafraichissement_Direct", propOrder = {
    "geocoolingNonClimatiseEmission"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataEmissionRafraichissementDirect {

    @XmlElementRef(name = "Geocooling_NonClimatise_Emission", type = JAXBElement.class, required = false)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected JAXBElement<GeocoolingNonClimatiseEmissionData> geocoolingNonClimatiseEmission;

    /**
     * Gets the value of the geocoolingNonClimatiseEmission property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link GeocoolingNonClimatiseEmissionData }{@code >}
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public JAXBElement<GeocoolingNonClimatiseEmissionData> getGeocoolingNonClimatiseEmission() {
        return geocoolingNonClimatiseEmission;
    }

    /**
     * Sets the value of the geocoolingNonClimatiseEmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link GeocoolingNonClimatiseEmissionData }{@code >}
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGeocoolingNonClimatiseEmission(JAXBElement<GeocoolingNonClimatiseEmissionData> value) {
        this.geocoolingNonClimatiseEmission = value;
    }

}
