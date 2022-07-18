
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
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="Usage" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="denomination_co" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="512"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ventilation_mecanique_collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="ventilation_mecanique" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="grp_SF_hygro_A" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="grp_SF_hygro_B" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="debit_base_occ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="debit_pointe_inocc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="debit_souf_base_occ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="debit_souf_pointe_inocc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="marque" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="512"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="deno_comm" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="512"/>
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
 *         &lt;element name="type_energie_gen_ch" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all minOccurs="0">
 *                   &lt;element name="elec_joule" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="elec_thermo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="gaz" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="fioul" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="bois" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="reseaux_chaleur" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="gaz_thermo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="type_energie_gen_fr" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all minOccurs="0">
 *                   &lt;element name="elec_joule" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="elec_thermo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="reseau" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="gaz" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mode_prod_ch" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;maxInclusive value="4"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="groupe_collection">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}groupe" maxOccurs="unbounded"/>
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
    "oshab",
    "osurt",
    "usage",
    "denominationCo",
    "ventilationMecaniqueCollection",
    "typeEnergieGenCh",
    "typeEnergieGenFr",
    "modeProdCh",
    "groupeCollection"
})
@XmlRootElement(name = "zone")
public class Zone {

    @XmlElement(name = "Index", required = true)
    protected BigInteger index;
    @XmlElement(name = "O_Shon_RT", required = true)
    protected BigDecimal oShonRT;
    @XmlElement(name = "O_SHAB", defaultValue = "0")
    protected BigDecimal oshab;
    @XmlElement(name = "O_SURT", defaultValue = "0")
    protected BigDecimal osurt;
    @XmlElement(name = "Usage", required = true)
    protected BigInteger usage;
    @XmlElement(name = "denomination_co")
    protected String denominationCo;
    @XmlElement(name = "ventilation_mecanique_collection")
    protected Zone.VentilationMecaniqueCollection ventilationMecaniqueCollection;
    @XmlElement(name = "type_energie_gen_ch")
    protected Zone.TypeEnergieGenCh typeEnergieGenCh;
    @XmlElement(name = "type_energie_gen_fr")
    protected Zone.TypeEnergieGenFr typeEnergieGenFr;
    @XmlElement(name = "mode_prod_ch", defaultValue = "0")
    protected Integer modeProdCh;
    @XmlElement(name = "groupe_collection", required = true)
    protected Zone.GroupeCollection groupeCollection;

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
     * Gets the value of the oshab property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOSHAB(BigDecimal value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osurt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOSURT() {
        return osurt;
    }

    /**
     * Sets the value of the osurt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOSURT(BigDecimal value) {
        this.osurt = value;
    }

    /**
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUsage(BigInteger value) {
        this.usage = value;
    }

    /**
     * Gets the value of the denominationCo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominationCo() {
        return denominationCo;
    }

    /**
     * Sets the value of the denominationCo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominationCo(String value) {
        this.denominationCo = value;
    }

    /**
     * Gets the value of the ventilationMecaniqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Zone.VentilationMecaniqueCollection }
     *     
     */
    public Zone.VentilationMecaniqueCollection getVentilationMecaniqueCollection() {
        return ventilationMecaniqueCollection;
    }

    /**
     * Sets the value of the ventilationMecaniqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zone.VentilationMecaniqueCollection }
     *     
     */
    public void setVentilationMecaniqueCollection(Zone.VentilationMecaniqueCollection value) {
        this.ventilationMecaniqueCollection = value;
    }

    /**
     * Gets the value of the typeEnergieGenCh property.
     * 
     * @return
     *     possible object is
     *     {@link Zone.TypeEnergieGenCh }
     *     
     */
    public Zone.TypeEnergieGenCh getTypeEnergieGenCh() {
        return typeEnergieGenCh;
    }

    /**
     * Sets the value of the typeEnergieGenCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zone.TypeEnergieGenCh }
     *     
     */
    public void setTypeEnergieGenCh(Zone.TypeEnergieGenCh value) {
        this.typeEnergieGenCh = value;
    }

    /**
     * Gets the value of the typeEnergieGenFr property.
     * 
     * @return
     *     possible object is
     *     {@link Zone.TypeEnergieGenFr }
     *     
     */
    public Zone.TypeEnergieGenFr getTypeEnergieGenFr() {
        return typeEnergieGenFr;
    }

    /**
     * Sets the value of the typeEnergieGenFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zone.TypeEnergieGenFr }
     *     
     */
    public void setTypeEnergieGenFr(Zone.TypeEnergieGenFr value) {
        this.typeEnergieGenFr = value;
    }

    /**
     * Gets the value of the modeProdCh property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getModeProdCh() {
        return modeProdCh;
    }

    /**
     * Sets the value of the modeProdCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setModeProdCh(Integer value) {
        this.modeProdCh = value;
    }

    /**
     * Gets the value of the groupeCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Zone.GroupeCollection }
     *     
     */
    public Zone.GroupeCollection getGroupeCollection() {
        return groupeCollection;
    }

    /**
     * Sets the value of the groupeCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zone.GroupeCollection }
     *     
     */
    public void setGroupeCollection(Zone.GroupeCollection value) {
        this.groupeCollection = value;
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
     *         &lt;element ref="{}groupe" maxOccurs="unbounded"/>
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
        "groupe"
    })
    public static class GroupeCollection {

        @XmlElement(required = true)
        protected List<Groupe> groupe;

        /**
         * Groupes Gets the value of the groupe property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the groupe property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGroupe().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Groupe }
         * 
         * 
         */
        public List<Groupe> getGroupe() {
            if (groupe == null) {
                groupe = new ArrayList<Groupe>();
            }
            return this.groupe;
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
     *       &lt;all minOccurs="0">
     *         &lt;element name="elec_joule" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="elec_thermo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="gaz" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="fioul" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="bois" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="reseaux_chaleur" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="gaz_thermo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
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
    public static class TypeEnergieGenCh {

        @XmlElement(name = "elec_joule", defaultValue = "0")
        protected Integer elecJoule;
        @XmlElement(name = "elec_thermo", defaultValue = "0")
        protected Integer elecThermo;
        @XmlElement(defaultValue = "0")
        protected Integer gaz;
        @XmlElement(defaultValue = "0")
        protected Integer fioul;
        @XmlElement(defaultValue = "0")
        protected Integer bois;
        @XmlElement(name = "reseaux_chaleur", defaultValue = "0")
        protected Integer reseauxChaleur;
        @XmlElement(name = "gaz_thermo", defaultValue = "0")
        protected Integer gazThermo;
        protected String autre;

        /**
         * Gets the value of the elecJoule property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getElecJoule() {
            return elecJoule;
        }

        /**
         * Sets the value of the elecJoule property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setElecJoule(Integer value) {
            this.elecJoule = value;
        }

        /**
         * Gets the value of the elecThermo property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getElecThermo() {
            return elecThermo;
        }

        /**
         * Sets the value of the elecThermo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setElecThermo(Integer value) {
            this.elecThermo = value;
        }

        /**
         * Gets the value of the gaz property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getGaz() {
            return gaz;
        }

        /**
         * Sets the value of the gaz property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGaz(Integer value) {
            this.gaz = value;
        }

        /**
         * Gets the value of the fioul property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getFioul() {
            return fioul;
        }

        /**
         * Sets the value of the fioul property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setFioul(Integer value) {
            this.fioul = value;
        }

        /**
         * Gets the value of the bois property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getBois() {
            return bois;
        }

        /**
         * Sets the value of the bois property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setBois(Integer value) {
            this.bois = value;
        }

        /**
         * Gets the value of the reseauxChaleur property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getReseauxChaleur() {
            return reseauxChaleur;
        }

        /**
         * Sets the value of the reseauxChaleur property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setReseauxChaleur(Integer value) {
            this.reseauxChaleur = value;
        }

        /**
         * Gets the value of the gazThermo property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getGazThermo() {
            return gazThermo;
        }

        /**
         * Sets the value of the gazThermo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGazThermo(Integer value) {
            this.gazThermo = value;
        }

        /**
         * Gets the value of the autre property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAutre() {
            return autre;
        }

        /**
         * Sets the value of the autre property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAutre(String value) {
            this.autre = value;
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
     *       &lt;all minOccurs="0">
     *         &lt;element name="elec_joule" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="elec_thermo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="reseau" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="gaz" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="autre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
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
    public static class TypeEnergieGenFr {

        @XmlElement(name = "elec_joule", defaultValue = "0")
        protected Integer elecJoule;
        @XmlElement(name = "elec_thermo", defaultValue = "0")
        protected Integer elecThermo;
        @XmlElement(defaultValue = "0")
        protected Integer reseau;
        @XmlElement(defaultValue = "0")
        protected Integer gaz;
        protected String autre;

        /**
         * Gets the value of the elecJoule property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getElecJoule() {
            return elecJoule;
        }

        /**
         * Sets the value of the elecJoule property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setElecJoule(Integer value) {
            this.elecJoule = value;
        }

        /**
         * Gets the value of the elecThermo property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getElecThermo() {
            return elecThermo;
        }

        /**
         * Sets the value of the elecThermo property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setElecThermo(Integer value) {
            this.elecThermo = value;
        }

        /**
         * Gets the value of the reseau property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getReseau() {
            return reseau;
        }

        /**
         * Sets the value of the reseau property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setReseau(Integer value) {
            this.reseau = value;
        }

        /**
         * Gets the value of the gaz property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getGaz() {
            return gaz;
        }

        /**
         * Sets the value of the gaz property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setGaz(Integer value) {
            this.gaz = value;
        }

        /**
         * Gets the value of the autre property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAutre() {
            return autre;
        }

        /**
         * Sets the value of the autre property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAutre(String value) {
            this.autre = value;
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
     *         &lt;element name="ventilation_mecanique" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="grp_SF_hygro_A" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="grp_SF_hygro_B" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="debit_base_occ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="debit_pointe_inocc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="debit_souf_base_occ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="debit_souf_pointe_inocc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="marque" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="512"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="deno_comm" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="512"/>
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
        "ventilationMecanique"
    })
    public static class VentilationMecaniqueCollection {

        @XmlElement(name = "ventilation_mecanique")
        protected List<Zone.VentilationMecaniqueCollection.VentilationMecanique> ventilationMecanique;

        /**
         * Gets the value of the ventilationMecanique property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ventilationMecanique property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVentilationMecanique().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Zone.VentilationMecaniqueCollection.VentilationMecanique }
         * 
         * 
         */
        public List<Zone.VentilationMecaniqueCollection.VentilationMecanique> getVentilationMecanique() {
            if (ventilationMecanique == null) {
                ventilationMecanique = new ArrayList<Zone.VentilationMecaniqueCollection.VentilationMecanique>();
            }
            return this.ventilationMecanique;
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
         *         &lt;element name="grp_SF_hygro_A" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="grp_SF_hygro_B" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="debit_base_occ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="debit_pointe_inocc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="debit_souf_base_occ" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="debit_souf_pointe_inocc" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="marque" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="512"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="deno_comm" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="512"/>
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
            "grpSFHygroA",
            "grpSFHygroB",
            "debitBaseOcc",
            "debitPointeInocc",
            "debitSoufBaseOcc",
            "debitSoufPointeInocc",
            "marque",
            "denoComm"
        })
        public static class VentilationMecanique {

            @XmlElement(name = "Index", required = true)
            protected BigInteger index;
            @XmlElement(name = "grp_SF_hygro_A", defaultValue = "0")
            protected BigInteger grpSFHygroA;
            @XmlElement(name = "grp_SF_hygro_B", defaultValue = "0")
            protected BigInteger grpSFHygroB;
            @XmlElement(name = "debit_base_occ", defaultValue = "0")
            protected BigDecimal debitBaseOcc;
            @XmlElement(name = "debit_pointe_inocc", defaultValue = "0")
            protected BigDecimal debitPointeInocc;
            @XmlElement(name = "debit_souf_base_occ", defaultValue = "0")
            protected BigDecimal debitSoufBaseOcc;
            @XmlElement(name = "debit_souf_pointe_inocc", defaultValue = "0")
            protected BigDecimal debitSoufPointeInocc;
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
             * Gets the value of the grpSFHygroA property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getGrpSFHygroA() {
                return grpSFHygroA;
            }

            /**
             * Sets the value of the grpSFHygroA property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setGrpSFHygroA(BigInteger value) {
                this.grpSFHygroA = value;
            }

            /**
             * Gets the value of the grpSFHygroB property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getGrpSFHygroB() {
                return grpSFHygroB;
            }

            /**
             * Sets the value of the grpSFHygroB property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setGrpSFHygroB(BigInteger value) {
                this.grpSFHygroB = value;
            }

            /**
             * Gets the value of the debitBaseOcc property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDebitBaseOcc() {
                return debitBaseOcc;
            }

            /**
             * Sets the value of the debitBaseOcc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDebitBaseOcc(BigDecimal value) {
                this.debitBaseOcc = value;
            }

            /**
             * Gets the value of the debitPointeInocc property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDebitPointeInocc() {
                return debitPointeInocc;
            }

            /**
             * Sets the value of the debitPointeInocc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDebitPointeInocc(BigDecimal value) {
                this.debitPointeInocc = value;
            }

            /**
             * Gets the value of the debitSoufBaseOcc property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDebitSoufBaseOcc() {
                return debitSoufBaseOcc;
            }

            /**
             * Sets the value of the debitSoufBaseOcc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDebitSoufBaseOcc(BigDecimal value) {
                this.debitSoufBaseOcc = value;
            }

            /**
             * Gets the value of the debitSoufPointeInocc property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDebitSoufPointeInocc() {
                return debitSoufPointeInocc;
            }

            /**
             * Sets the value of the debitSoufPointeInocc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDebitSoufPointeInocc(BigDecimal value) {
                this.debitSoufPointeInocc = value;
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

    }

}
