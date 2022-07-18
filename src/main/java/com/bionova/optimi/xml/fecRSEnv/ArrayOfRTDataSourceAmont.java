
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Source_Amont complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Source_Amont">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Source_Amont" type="{}RT_Data_Source_Amont" minOccurs="0"/>
 *         &lt;element name="T5_EREIE_PACF7_ballon_EG" type="{}T5_EREIE_PacF7_ballon_EG_Data" minOccurs="0"/>
 *         &lt;element ref="{}T5_BIOFLUIDES_ERS" minOccurs="0"/>
 *         &lt;element ref="{}T5_BIOFLUIDES_ERS_Source_Amont" minOccurs="0"/>
 *         &lt;element name="T5_Nibe_Source_Amont_Melange_AirExtraitExterieur" type="{}T5_Nibe_Source_Amont_Melange_AirExtraitExterieur_Data" minOccurs="0"/>
 *         &lt;element ref="{}T5_Cardonnel_France_Energie_Source_am" minOccurs="0"/>
 *         &lt;element ref="{}T5_FRANCEAIR_MYRIADE" minOccurs="0"/>
 *         &lt;element ref="{}T5_SOLARONICS_PACF7_Source_Amont" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Source_Amont", propOrder = {
    "sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS"
})
public class ArrayOfRTDataSourceAmont {

    @XmlElementRefs({
        @XmlElementRef(name = "T5_BIOFLUIDES_ERS_Source_Amont", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "T5_SOLARONICS_PACF7_Source_Amont", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "T5_EREIE_PACF7_ballon_EG", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "T5_BIOFLUIDES_ERS", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "T5_FRANCEAIR_MYRIADE", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "T5_Cardonnel_France_Energie_Source_am", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Source_Amont", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "T5_Nibe_Source_Amont_Melange_AirExtraitExterieur", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS;

    /**
     * Gets the value of the sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link T5BIOFLUIDESERSData }{@code >}
     * {@link JAXBElement }{@code <}{@link T5SOLARONICSPACF7SourceAmontData }{@code >}
     * {@link JAXBElement }{@code <}{@link T5EREIEPacF7BallonEGData }{@code >}
     * {@link JAXBElement }{@code <}{@link T5BIOFLUIDESERSData }{@code >}
     * {@link JAXBElement }{@code <}{@link T5FRANCEAIRMYRIADEData }{@code >}
     * {@link JAXBElement }{@code <}{@link T5CardonnelFranceEnergieSourceAmData }{@code >}
     * {@link JAXBElement }{@code <}{@link RTDataSourceAmont }{@code >}
     * {@link JAXBElement }{@code <}{@link T5NibeSourceAmontMelangeAirExtraitExterieurData }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getSourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS() {
        if (sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS == null) {
            sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS = new ArrayList<JAXBElement<?>>();
        }
        return this.sourceAmontOrT5EREIEPACF7BallonEGOrT5BIOFLUIDESERS;
    }

}
