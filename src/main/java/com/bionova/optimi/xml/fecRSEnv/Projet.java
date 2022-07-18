
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}RSEnv"/>
 *         &lt;element ref="{}RSET"/>
 *       &lt;/all>
 *       &lt;attribute name="num_PEBN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="referentiel_ec">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="2"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="version" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "projet")
public class Projet {

    @XmlElement(name = "RSEnv", required = true)
    protected RSEnv rsEnv;
    @XmlElement(name = "RSET", required = true)
    protected RSET rset;
    @XmlAttribute(name = "num_PEBN")
    protected String numPEBN;
    @XmlAttribute(name = "referentiel_ec")
    protected String referentielEc;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the rsEnv property.
     * 
     * @return
     *     possible object is
     *     {@link RSEnv }
     *     
     */
    public RSEnv getRSEnv() {
        return rsEnv;
    }

    /**
     * Sets the value of the rsEnv property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSEnv }
     *     
     */
    public void setRSEnv(RSEnv value) {
        this.rsEnv = value;
    }

    /**
     * Gets the value of the rset property.
     * 
     * @return
     *     possible object is
     *     {@link RSET }
     *     
     */
    public RSET getRSET() {
        return rset;
    }

    /**
     * Sets the value of the rset property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSET }
     *     
     */
    public void setRSET(RSET value) {
        this.rset = value;
    }

    /**
     * Gets the value of the numPEBN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumPEBN() {
        return numPEBN;
    }

    /**
     * Sets the value of the numPEBN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumPEBN(String value) {
        this.numPEBN = value;
    }

    /**
     * Gets the value of the referentielEc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferentielEc() {
        return referentielEc;
    }

    /**
     * Sets the value of the referentielEc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferentielEc(String value) {
        this.referentielEc = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
