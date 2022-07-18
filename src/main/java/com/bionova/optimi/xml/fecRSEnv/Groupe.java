
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="emetteur_collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="emetteur" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="type_emet_chaud" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type_emet_froid" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="distribution_groupe_chaud" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                       &lt;element name="classe_iso_vc" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="classe_iso_hvc" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="distribution_groupe_froid" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                       &lt;element name="classe_iso_vc" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                       &lt;element name="classe_iso_hvc" minOccurs="0">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                             &lt;minInclusive value="0"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="emetteur_ecs_collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="emetteur_ecs" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="A_hab_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
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
    "index",
    "oShonRT",
    "emetteurCollection",
    "emetteurEcsCollection"
})
@XmlRootElement(name = "groupe")
public class Groupe {

    @XmlElement(name = "Index", required = true)
    protected BigInteger index;
    @XmlElement(name = "O_Shon_RT")
    protected BigDecimal oShonRT;
    @XmlElement(name = "emetteur_collection")
    protected Groupe.EmetteurCollection emetteurCollection;
    @XmlElement(name = "emetteur_ecs_collection")
    protected Groupe.EmetteurEcsCollection emetteurEcsCollection;

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
     * Gets the value of the oShonRT property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOShonRT() {
        return oShonRT;
    }

    /**
     * Sets the value of the oShonRT property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOShonRT(BigDecimal value) {
        this.oShonRT = value;
    }

    /**
     * Gets the value of the emetteurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Groupe.EmetteurCollection }
     *     
     */
    public Groupe.EmetteurCollection getEmetteurCollection() {
        return emetteurCollection;
    }

    /**
     * Sets the value of the emetteurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Groupe.EmetteurCollection }
     *     
     */
    public void setEmetteurCollection(Groupe.EmetteurCollection value) {
        this.emetteurCollection = value;
    }

    /**
     * Gets the value of the emetteurEcsCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Groupe.EmetteurEcsCollection }
     *     
     */
    public Groupe.EmetteurEcsCollection getEmetteurEcsCollection() {
        return emetteurEcsCollection;
    }

    /**
     * Sets the value of the emetteurEcsCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Groupe.EmetteurEcsCollection }
     *     
     */
    public void setEmetteurEcsCollection(Groupe.EmetteurEcsCollection value) {
        this.emetteurEcsCollection = value;
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
     *       &lt;sequence minOccurs="0">
     *         &lt;element name="emetteur" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="type_emet_chaud" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type_emet_froid" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="distribution_groupe_chaud" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                             &lt;element name="classe_iso_vc" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="classe_iso_hvc" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="distribution_groupe_froid" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                             &lt;element name="classe_iso_vc" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="classe_iso_hvc" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                                   &lt;minInclusive value="0"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
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
        "emetteur"
    })
    public static class EmetteurCollection {

        protected List<Groupe.EmetteurCollection.Emetteur> emetteur;

        /**
         * Gets the value of the emetteur property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the emetteur property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmetteur().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Groupe.EmetteurCollection.Emetteur }
         * 
         * 
         */
        public List<Groupe.EmetteurCollection.Emetteur> getEmetteur() {
            if (emetteur == null) {
                emetteur = new ArrayList<Groupe.EmetteurCollection.Emetteur>();
            }
            return this.emetteur;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="type_emet_chaud" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type_emet_froid" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="distribution_groupe_chaud" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                   &lt;element name="classe_iso_vc" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="classe_iso_hvc" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="distribution_groupe_froid" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                   &lt;element name="classe_iso_vc" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="classe_iso_hvc" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *                         &lt;minInclusive value="0"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
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
            "index",
            "typeEmetChaud",
            "typeEmetFroid",
            "distributionGroupeChaud",
            "distributionGroupeFroid"
        })
        public static class Emetteur {

            @XmlElement(name = "Index", required = true)
            protected BigInteger index;
            @XmlElement(name = "type_emet_chaud", defaultValue = "0")
            protected BigInteger typeEmetChaud;
            @XmlElement(name = "type_emet_froid", defaultValue = "0")
            protected BigInteger typeEmetFroid;
            @XmlElement(name = "distribution_groupe_chaud")
            protected List<Groupe.EmetteurCollection.Emetteur.DistributionGroupeChaud> distributionGroupeChaud;
            @XmlElement(name = "distribution_groupe_froid")
            protected Groupe.EmetteurCollection.Emetteur.DistributionGroupeFroid distributionGroupeFroid;

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
             * Gets the value of the typeEmetChaud property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTypeEmetChaud() {
                return typeEmetChaud;
            }

            /**
             * Sets the value of the typeEmetChaud property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTypeEmetChaud(BigInteger value) {
                this.typeEmetChaud = value;
            }

            /**
             * Gets the value of the typeEmetFroid property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTypeEmetFroid() {
                return typeEmetFroid;
            }

            /**
             * Sets the value of the typeEmetFroid property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTypeEmetFroid(BigInteger value) {
                this.typeEmetFroid = value;
            }

            /**
             * Gets the value of the distributionGroupeChaud property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the distributionGroupeChaud property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDistributionGroupeChaud().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Groupe.EmetteurCollection.Emetteur.DistributionGroupeChaud }
             * 
             * 
             */
            public List<Groupe.EmetteurCollection.Emetteur.DistributionGroupeChaud> getDistributionGroupeChaud() {
                if (distributionGroupeChaud == null) {
                    distributionGroupeChaud = new ArrayList<Groupe.EmetteurCollection.Emetteur.DistributionGroupeChaud>();
                }
                return this.distributionGroupeChaud;
            }

            /**
             * Gets the value of the distributionGroupeFroid property.
             * 
             * @return
             *     possible object is
             *     {@link Groupe.EmetteurCollection.Emetteur.DistributionGroupeFroid }
             *     
             */
            public Groupe.EmetteurCollection.Emetteur.DistributionGroupeFroid getDistributionGroupeFroid() {
                return distributionGroupeFroid;
            }

            /**
             * Sets the value of the distributionGroupeFroid property.
             * 
             * @param value
             *     allowed object is
             *     {@link Groupe.EmetteurCollection.Emetteur.DistributionGroupeFroid }
             *     
             */
            public void setDistributionGroupeFroid(Groupe.EmetteurCollection.Emetteur.DistributionGroupeFroid value) {
                this.distributionGroupeFroid = value;
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
             *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *         &lt;element name="classe_iso_vc" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="classe_iso_hvc" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
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
                "index",
                "classeIsoVc",
                "classeIsoHvc"
            })
            public static class DistributionGroupeChaud {

                @XmlElement(name = "Index", required = true)
                protected BigInteger index;
                @XmlElement(name = "classe_iso_vc", defaultValue = "0")
                protected BigInteger classeIsoVc;
                @XmlElement(name = "classe_iso_hvc", defaultValue = "0")
                protected BigInteger classeIsoHvc;

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
                 * Gets the value of the classeIsoVc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getClasseIsoVc() {
                    return classeIsoVc;
                }

                /**
                 * Sets the value of the classeIsoVc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setClasseIsoVc(BigInteger value) {
                    this.classeIsoVc = value;
                }

                /**
                 * Gets the value of the classeIsoHvc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getClasseIsoHvc() {
                    return classeIsoHvc;
                }

                /**
                 * Sets the value of the classeIsoHvc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setClasseIsoHvc(BigInteger value) {
                    this.classeIsoHvc = value;
                }

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
             *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *         &lt;element name="classe_iso_vc" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="classe_iso_hvc" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *               &lt;minInclusive value="0"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
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
                "index",
                "classeIsoVc",
                "classeIsoHvc"
            })
            public static class DistributionGroupeFroid {

                @XmlElement(name = "Index", required = true)
                protected BigInteger index;
                @XmlElement(name = "classe_iso_vc", defaultValue = "0")
                protected BigInteger classeIsoVc;
                @XmlElement(name = "classe_iso_hvc", defaultValue = "0")
                protected BigInteger classeIsoHvc;

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
                 * Gets the value of the classeIsoVc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getClasseIsoVc() {
                    return classeIsoVc;
                }

                /**
                 * Sets the value of the classeIsoVc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setClasseIsoVc(BigInteger value) {
                    this.classeIsoVc = value;
                }

                /**
                 * Gets the value of the classeIsoHvc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getClasseIsoHvc() {
                    return classeIsoHvc;
                }

                /**
                 * Sets the value of the classeIsoHvc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setClasseIsoHvc(BigInteger value) {
                    this.classeIsoHvc = value;
                }

            }

        }

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
     *       &lt;sequence minOccurs="0">
     *         &lt;element name="emetteur_ecs" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="A_hab_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                 &lt;/sequence>
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
        "emetteurEcs"
    })
    public static class EmetteurEcsCollection {

        @XmlElement(name = "emetteur_ecs")
        protected List<Groupe.EmetteurEcsCollection.EmetteurEcs> emetteurEcs;

        /**
         * Gets the value of the emetteurEcs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the emetteurEcs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmetteurEcs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Groupe.EmetteurEcsCollection.EmetteurEcs }
         * 
         * 
         */
        public List<Groupe.EmetteurEcsCollection.EmetteurEcs> getEmetteurEcs() {
            if (emetteurEcs == null) {
                emetteurEcs = new ArrayList<Groupe.EmetteurEcsCollection.EmetteurEcs>();
            }
            return this.emetteurEcs;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="A_hab_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
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
            "index",
            "aHabGrEmE"
        })
        public static class EmetteurEcs {

            @XmlElement(name = "Index", required = true, defaultValue = "0")
            protected BigInteger index;
            @XmlElement(name = "A_hab_gr_em_e", defaultValue = "0")
            protected BigDecimal aHabGrEmE;

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
             * Gets the value of the aHabGrEmE property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAHabGrEmE() {
                return aHabGrEmE;
            }

            /**
             * Sets the value of the aHabGrEmE property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAHabGrEmE(BigDecimal value) {
                this.aHabGrEmE = value;
            }

        }

    }

}
