
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * marque et denomination commerciale pour les ballons - 04-2015
 * 
 * <p>Java class for t_deno_ballons complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_deno_ballons">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="marque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deno_comm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_deno_ballons", propOrder = {
    "index",
    "marque",
    "denoComm"
})
public class TDenoBallons {

    @XmlElement(name = "Index")
    protected BigInteger index;
    protected String marque;
    @XmlElement(name = "deno_comm")
    protected String denoComm;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIndex(BigInteger value) {
        this.index = value;
    }

    /**
     * Gets the value of the marque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarque() {
        return marque;
    }

    /**
     * Sets the value of the marque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarque(String value) {
        this.marque = value;
    }

    /**
     * Gets the value of the denoComm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenoComm() {
        return denoComm;
    }

    /**
     * Sets the value of the denoComm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenoComm(String value) {
        this.denoComm = value;
    }

}
