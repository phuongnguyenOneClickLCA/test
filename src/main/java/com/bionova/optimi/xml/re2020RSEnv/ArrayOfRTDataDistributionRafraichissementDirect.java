
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Distribution_Rafraichissement_Direct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Distribution_Rafraichissement_Direct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Geocooling_NonClimatise_Generation" type="{}Geocooling_NonClimatise_Generation_Data"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Distribution_Rafraichissement_Direct", propOrder = {
    "geocoolingNonClimatiseGeneration"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ArrayOfRTDataDistributionRafraichissementDirect {

    @XmlElementRef(name = "Geocooling_NonClimatise_Generation", type = JAXBElement.class, required = false)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected JAXBElement<GeocoolingNonClimatiseGenerationData> geocoolingNonClimatiseGeneration;

    /**
     * Gets the value of the geocoolingNonClimatiseGeneration property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link GeocoolingNonClimatiseGenerationData }{@code >}
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public JAXBElement<GeocoolingNonClimatiseGenerationData> getGeocoolingNonClimatiseGeneration() {
        return geocoolingNonClimatiseGeneration;
    }

    /**
     * Sets the value of the geocoolingNonClimatiseGeneration property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link GeocoolingNonClimatiseGenerationData }{@code >}
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setGeocoolingNonClimatiseGeneration(JAXBElement<GeocoolingNonClimatiseGenerationData> value) {
        this.geocoolingNonClimatiseGeneration = value;
    }

}
