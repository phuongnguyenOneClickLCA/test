
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * DC:Exposition au bruit
 * 
 * <p>Java class for t_locaux complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_locaux">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exposes_br1" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="exposes_br2_br3" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="passage" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="autres_br1" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="autres_br2_br3" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_locaux", propOrder = {
    "exposesBr1",
    "exposesBr2Br3",
    "passage",
    "autresBr1",
    "autresBr2Br3"
})
@XmlSeeAlso({
    TSynthThermEte.class
})
public class TLocaux {

    @XmlElement(name = "exposes_br1", defaultValue = "0")
    protected BigDecimal exposesBr1;
    @XmlElement(name = "exposes_br2_br3", defaultValue = "0")
    protected BigDecimal exposesBr2Br3;
    @XmlElement(defaultValue = "0")
    protected BigDecimal passage;
    @XmlElement(name = "autres_br1", defaultValue = "0")
    protected BigDecimal autresBr1;
    @XmlElement(name = "autres_br2_br3", defaultValue = "0")
    protected BigDecimal autresBr2Br3;

    /**
     * Gets the value of the exposesBr1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExposesBr1() {
        return exposesBr1;
    }

    /**
     * Sets the value of the exposesBr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExposesBr1(BigDecimal value) {
        this.exposesBr1 = value;
    }

    /**
     * Gets the value of the exposesBr2Br3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExposesBr2Br3() {
        return exposesBr2Br3;
    }

    /**
     * Sets the value of the exposesBr2Br3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExposesBr2Br3(BigDecimal value) {
        this.exposesBr2Br3 = value;
    }

    /**
     * Gets the value of the passage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPassage() {
        return passage;
    }

    /**
     * Sets the value of the passage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPassage(BigDecimal value) {
        this.passage = value;
    }

    /**
     * Gets the value of the autresBr1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAutresBr1() {
        return autresBr1;
    }

    /**
     * Sets the value of the autresBr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAutresBr1(BigDecimal value) {
        this.autresBr1 = value;
    }

    /**
     * Gets the value of the autresBr2Br3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAutresBr2Br3() {
        return autresBr2Br3;
    }

    /**
     * Sets the value of the autresBr2Br3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAutresBr2Br3(BigDecimal value) {
        this.autresBr2Br3 = value;
    }

}
