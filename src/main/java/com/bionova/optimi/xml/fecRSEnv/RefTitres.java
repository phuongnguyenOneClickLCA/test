
package com.bionova.optimi.xml.fecRSEnv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * référence des T5 et T4 post traitement
 * 
 * <p>Java class for ref_titres complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ref_titres">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="Reference_TitreV_Systeme" maxOccurs="unbounded">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Reference_TitreV_Operation" maxOccurs="unbounded">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="O-20[1-2][0-9]-[0-9][0-9]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Reference_TitreIV" maxOccurs="unbounded">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ref_titres", propOrder = {
    "referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV"
})
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.Batiment.RefTitres.class
})
public class RefTitres {

    @XmlElementRefs({
        @XmlElementRef(name = "Reference_TitreV_Systeme", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Reference_TitreV_Operation", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Reference_TitreIV", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<? extends Serializable>> referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV;

    /**
     * Gets the value of the referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends Serializable>> getReferenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV() {
        if (referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV == null) {
            referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV = new ArrayList<JAXBElement<? extends Serializable>>();
        }
        return this.referenceTitreVSystemeOrReferenceTitreVOperationOrReferenceTitreIV;
    }

}
