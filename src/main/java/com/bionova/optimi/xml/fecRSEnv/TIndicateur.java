
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Indicateur
 * 
 * <p>Java class for t_indicateur complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_indicateur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicateur" maxOccurs="unbounded" minOccurs="7">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="nom" type="{}p_string500" minOccurs="0"/>
 *                   &lt;element name="unite" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;whiteSpace value="collapse"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="valeur_phase_acv" maxOccurs="7" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
 *                           &lt;attribute name="ref" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                                 &lt;enumeration value="1"/>
 *                                 &lt;enumeration value="2"/>
 *                                 &lt;enumeration value="3"/>
 *                                 &lt;enumeration value="4"/>
 *                                 &lt;enumeration value="5"/>
 *                                 &lt;enumeration value="6"/>
 *                                 &lt;enumeration value="7"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
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
 *                       &lt;enumeration value="7"/>
 *                       &lt;enumeration value="8"/>
 *                       &lt;enumeration value="9"/>
 *                       &lt;enumeration value="10"/>
 *                       &lt;enumeration value="11"/>
 *                       &lt;enumeration value="12"/>
 *                       &lt;enumeration value="13"/>
 *                       &lt;enumeration value="14"/>
 *                       &lt;enumeration value="15"/>
 *                       &lt;enumeration value="16"/>
 *                       &lt;enumeration value="17"/>
 *                       &lt;enumeration value="18"/>
 *                       &lt;enumeration value="19"/>
 *                       &lt;enumeration value="20"/>
 *                       &lt;enumeration value="21"/>
 *                       &lt;enumeration value="22"/>
 *                       &lt;enumeration value="23"/>
 *                       &lt;enumeration value="24"/>
 *                       &lt;enumeration value="25"/>
 *                       &lt;enumeration value="26"/>
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
@XmlType(name = "t_indicateur", propOrder = {
    "indicateur"
})
public class TIndicateur {

    @XmlElement(required = true)
    protected List<TIndicateur.Indicateur> indicateur;

    /**
     * Gets the value of the indicateur property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indicateur property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndicateur().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TIndicateur.Indicateur }
     * 
     * 
     */
    public List<TIndicateur.Indicateur> getIndicateur() {
        if (indicateur == null) {
            indicateur = new ArrayList<TIndicateur.Indicateur>();
        }
        return this.indicateur;
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
     *               &lt;minLength value="1"/>
     *               &lt;whiteSpace value="collapse"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="valeur_phase_acv" maxOccurs="7" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
     *                 &lt;attribute name="ref" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *                       &lt;enumeration value="1"/>
     *                       &lt;enumeration value="2"/>
     *                       &lt;enumeration value="3"/>
     *                       &lt;enumeration value="4"/>
     *                       &lt;enumeration value="5"/>
     *                       &lt;enumeration value="6"/>
     *                       &lt;enumeration value="7"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
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
     *             &lt;enumeration value="7"/>
     *             &lt;enumeration value="8"/>
     *             &lt;enumeration value="9"/>
     *             &lt;enumeration value="10"/>
     *             &lt;enumeration value="11"/>
     *             &lt;enumeration value="12"/>
     *             &lt;enumeration value="13"/>
     *             &lt;enumeration value="14"/>
     *             &lt;enumeration value="15"/>
     *             &lt;enumeration value="16"/>
     *             &lt;enumeration value="17"/>
     *             &lt;enumeration value="18"/>
     *             &lt;enumeration value="19"/>
     *             &lt;enumeration value="20"/>
     *             &lt;enumeration value="21"/>
     *             &lt;enumeration value="22"/>
     *             &lt;enumeration value="23"/>
     *             &lt;enumeration value="24"/>
     *             &lt;enumeration value="25"/>
     *             &lt;enumeration value="26"/>
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
        "unite",
        "valeurPhaseAcv"
    })
    public static class Indicateur {

        @XmlElement(required = true)
        protected BigDecimal valeur;
        protected String nom;
        protected String unite;
        @XmlElement(name = "valeur_phase_acv")
        protected List<TIndicateur.Indicateur.ValeurPhaseAcv> valeurPhaseAcv;
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
         * Gets the value of the valeurPhaseAcv property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the valeurPhaseAcv property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValeurPhaseAcv().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TIndicateur.Indicateur.ValeurPhaseAcv }
         * 
         * 
         */
        public List<TIndicateur.Indicateur.ValeurPhaseAcv> getValeurPhaseAcv() {
            if (valeurPhaseAcv == null) {
                valeurPhaseAcv = new ArrayList<TIndicateur.Indicateur.ValeurPhaseAcv>();
            }
            return this.valeurPhaseAcv;
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


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
         *       &lt;attribute name="ref" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
         *             &lt;enumeration value="1"/>
         *             &lt;enumeration value="2"/>
         *             &lt;enumeration value="3"/>
         *             &lt;enumeration value="4"/>
         *             &lt;enumeration value="5"/>
         *             &lt;enumeration value="6"/>
         *             &lt;enumeration value="7"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ValeurPhaseAcv {

            @XmlValue
            protected BigDecimal value;
            @XmlAttribute(name = "ref", required = true)
            protected int ref;

            /**
             * Gets the value of the value property.
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
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValue(BigDecimal value) {
                this.value = value;
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

}
