
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_Evacuation_EG complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_Evacuation_EG">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="L_evac_vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_evac_vnc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_aval_vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_aval_vnc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_relevage_nominale" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Echangeur" type="{}RT_Data_Echangeur_EG" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Distribution_Evacuation_EG", propOrder = {
    "indexOrNameOrDescription"
})
@XmlSeeAlso({
    RTDataDistributionEvacuationEGIntergroupe.class
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataDistributionEvacuationEG {

    @XmlElementRefs({
        @XmlElementRef(name = "L_evac_vc", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Description", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Name", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "L_aval_vc", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Index", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "L_evac_vnc", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "L_aval_vnc", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Echangeur", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "P_relevage_nominale", type = JAXBElement.class, required = false)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<JAXBElement<?>> indexOrNameOrDescription;

    /**
     * Gets the value of the indexOrNameOrDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indexOrNameOrDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndexOrNameOrDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Double }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Double }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link Double }{@code >}
     * {@link JAXBElement }{@code <}{@link Double }{@code >}
     * {@link JAXBElement }{@code <}{@link RTDataEchangeurEG }{@code >}
     * {@link JAXBElement }{@code <}{@link Double }{@code >}
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<JAXBElement<?>> getIndexOrNameOrDescription() {
        if (indexOrNameOrDescription == null) {
            indexOrNameOrDescription = new ArrayList<JAXBElement<?>>();
        }
        return this.indexOrNameOrDescription;
    }

}
