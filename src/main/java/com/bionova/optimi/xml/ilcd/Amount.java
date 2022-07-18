
package com.bionova.optimi.xml.ilcd;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Amount", namespace = "http://www.iai.kit.edu/EPD/2013", propOrder = {
    "value"
})
@XmlRootElement(name = "other", namespace = "http://www.iai.kit.edu/EPD/2013")
public class Amount {

    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "module", namespace = "http://www.iai.kit.edu/EPD/2013")
    protected String module;

    /**
     * Short text with a maximum length of 1000 characters.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param module
     *     allowed object is
     *     {@link String }
     *
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * Short text with a maximum length of 1000 characters.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getModule() {
        return module;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
