
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Adresse
 * 
 * <p>Java class for t_adresse2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_adresse2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ligne" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="3"/>
 *         &lt;element name="code_postal">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="5"/>
 *               &lt;pattern value="[0-9]{5}"/>
 *               &lt;pattern value=""/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="commune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_adresse2", propOrder = {
    "ligne",
    "codePostal",
    "commune"
})
public class TAdresse2 {

    @XmlElement(required = true)
    protected List<String> ligne;
    @XmlElement(name = "code_postal", required = true)
    protected String codePostal;
    @XmlElement(required = true)
    protected String commune;

    /**
     * Gets the value of the ligne property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ligne property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLigne().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLigne() {
        if (ligne == null) {
            ligne = new ArrayList<String>();
        }
        return this.ligne;
    }

    /**
     * Gets the value of the codePostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Sets the value of the codePostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePostal(String value) {
        this.codePostal = value;
    }

    /**
     * Gets the value of the commune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommune() {
        return commune;
    }

    /**
     * Sets the value of the commune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommune(String value) {
        this.commune = value;
    }

}
