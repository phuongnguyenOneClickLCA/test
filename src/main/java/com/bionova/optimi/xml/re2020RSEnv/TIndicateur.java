
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
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
 *         &lt;element name="indicateur" maxOccurs="24" minOccurs="24">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="valeur_phase_acv" maxOccurs="6" minOccurs="4">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>float">
 *                           &lt;attribute name="ref" use="required">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="A1-A3"/>
 *                                 &lt;enumeration value="A4-A5"/>
 *                                 &lt;enumeration value="B"/>
 *                                 &lt;enumeration value="C"/>
 *                                 &lt;enumeration value="D"/>
 *                                 &lt;enumeration value="Bexp"/>
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
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class TIndicateur {

    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
     *         &lt;element name="valeur_phase_acv" maxOccurs="6" minOccurs="4">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>float">
     *                 &lt;attribute name="ref" use="required">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="A1-A3"/>
     *                       &lt;enumeration value="A4-A5"/>
     *                       &lt;enumeration value="B"/>
     *                       &lt;enumeration value="C"/>
     *                       &lt;enumeration value="D"/>
     *                       &lt;enumeration value="Bexp"/>
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
        "valeurPhaseAcv"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class Indicateur {

        @XmlElement(name = "valeur_phase_acv", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected List<TIndicateur.Indicateur.ValeurPhaseAcv> valeurPhaseAcv;
        @XmlAttribute(name = "ref", required = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected int ref;

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
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public int getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         * 
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>float">
         *       &lt;attribute name="ref" use="required">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="A1-A3"/>
         *             &lt;enumeration value="A4-A5"/>
         *             &lt;enumeration value="B"/>
         *             &lt;enumeration value="C"/>
         *             &lt;enumeration value="D"/>
         *             &lt;enumeration value="Bexp"/>
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
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public static class ValeurPhaseAcv {

            @XmlValue
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected float value;
            @XmlAttribute(name = "ref", required = true)
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            protected String ref;

            /**
             * Gets the value of the value property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public float getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setValue(float value) {
                this.value = value;
            }

            /**
             * Gets the value of the ref property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public String getRef() {
                return ref;
            }

            /**
             * Sets the value of the ref property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
            public void setRef(String value) {
                this.ref = value;
            }

        }

    }

}
