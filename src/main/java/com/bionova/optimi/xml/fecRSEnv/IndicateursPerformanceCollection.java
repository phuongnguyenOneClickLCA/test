
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
 *       &lt;sequence>
 *         &lt;element name="indicateurs_performance" maxOccurs="6" minOccurs="6">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="nom" type="{}p_string500" minOccurs="0"/>
 *                   &lt;element name="unite" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;whiteSpace value="collapse"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="ref" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                       &lt;enumeration value="1"/>
 *                       &lt;enumeration value="2"/>
 *                       &lt;enumeration value="3"/>
 *                       &lt;enumeration value="4"/>
 *                       &lt;enumeration value="5"/>
 *                       &lt;enumeration value="6"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "indicateursPerformance"
})
@XmlRootElement(name = "indicateurs_performance_collection")
public class IndicateursPerformanceCollection {

    @XmlElement(name = "indicateurs_performance", required = true)
    protected List<IndicateursPerformanceCollection.IndicateursPerformance> indicateursPerformance;

    /**
     * Gets the value of the indicateursPerformance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicateursPerformance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicateursPerformance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IndicateursPerformanceCollection.IndicateursPerformance }
     * 
     * 
     */
    public List<IndicateursPerformanceCollection.IndicateursPerformance> getIndicateursPerformance() {
        if (indicateursPerformance == null) {
            indicateursPerformance = new ArrayList<IndicateursPerformanceCollection.IndicateursPerformance>();
        }
        return this.indicateursPerformance;
    }


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
     *         &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="nom" type="{}p_string500" minOccurs="0"/>
     *         &lt;element name="unite" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;whiteSpace value="collapse"/>
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="ref" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *             &lt;enumeration value="1"/>
     *             &lt;enumeration value="2"/>
     *             &lt;enumeration value="3"/>
     *             &lt;enumeration value="4"/>
     *             &lt;enumeration value="5"/>
     *             &lt;enumeration value="6"/>
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
        "valeur",
        "nom",
        "unite"
    })
    public static class IndicateursPerformance {

        @XmlElement(required = true)
        protected BigDecimal valeur;
        protected String nom;
        protected String unite;
        @XmlAttribute(name = "ref", required = true)
        protected int ref;

        /**
         * Gets the value of the valeur property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValeur() {
            return valeur;
        }

        /**
         * Sets the value of the valeur property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValeur(BigDecimal value) {
            this.valeur = value;
        }

        /**
         * Gets the value of the nom property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNom() {
            return nom;
        }

        /**
         * Sets the value of the nom property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNom(String value) {
            this.nom = value;
        }

        /**
         * Gets the value of the unite property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnite() {
            return unite;
        }

        /**
         * Sets the value of the unite property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnite(String value) {
            this.unite = value;
        }

        /**
         * Gets the value of the ref property.
         * 
         */
        public int getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         * 
         */
        public void setRef(int value) {
            this.ref = value;
        }

    }

}
