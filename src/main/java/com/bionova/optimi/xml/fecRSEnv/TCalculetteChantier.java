
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_calculette_chantier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_calculette_chantier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="terres_evacuees" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="terres_excavees">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="distance_terrassement">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="surf_parc" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="grue" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="duree_ete_avec" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="duree_ete_sans" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="duree_hiv_avec" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="duree_hiv_sans" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_calculette_chantier", propOrder = {

})
public class TCalculetteChantier {

    @XmlElement(name = "terres_evacuees")
    protected BigDecimal terresEvacuees;
    @XmlElement(name = "terres_excavees", required = true)
    protected BigDecimal terresExcavees;
    @XmlElement(name = "distance_terrassement", required = true)
    protected BigDecimal distanceTerrassement;
    @XmlElement(name = "surf_parc")
    protected BigDecimal surfParc;
    protected TCalculetteChantier.Grue grue;

    /**
     * Gets the value of the terresEvacuees property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTerresEvacuees() {
        return terresEvacuees;
    }

    /**
     * Sets the value of the terresEvacuees property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTerresEvacuees(BigDecimal value) {
        this.terresEvacuees = value;
    }

    /**
     * Gets the value of the terresExcavees property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTerresExcavees() {
        return terresExcavees;
    }

    /**
     * Sets the value of the terresExcavees property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTerresExcavees(BigDecimal value) {
        this.terresExcavees = value;
    }

    /**
     * Gets the value of the distanceTerrassement property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDistanceTerrassement() {
        return distanceTerrassement;
    }

    /**
     * Sets the value of the distanceTerrassement property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDistanceTerrassement(BigDecimal value) {
        this.distanceTerrassement = value;
    }

    /**
     * Gets the value of the surfParc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSurfParc() {
        return surfParc;
    }

    /**
     * Sets the value of the surfParc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSurfParc(BigDecimal value) {
        this.surfParc = value;
    }

    /**
     * Gets the value of the grue property.
     * 
     * @return
     *     possible object is
     *     {@link TCalculetteChantier.Grue }
     *     
     */
    public TCalculetteChantier.Grue getGrue() {
        return grue;
    }

    /**
     * Sets the value of the grue property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCalculetteChantier.Grue }
     *     
     */
    public void setGrue(TCalculetteChantier.Grue value) {
        this.grue = value;
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
     *       &lt;all>
     *         &lt;element name="duree_ete_avec" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="duree_ete_sans" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="duree_hiv_avec" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="duree_hiv_sans" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
    public static class Grue {

        @XmlElement(name = "duree_ete_avec")
        protected Integer dureeEteAvec;
        @XmlElement(name = "duree_ete_sans")
        protected Integer dureeEteSans;
        @XmlElement(name = "duree_hiv_avec")
        protected Integer dureeHivAvec;
        @XmlElement(name = "duree_hiv_sans")
        protected Integer dureeHivSans;

        /**
         * Gets the value of the dureeEteAvec property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDureeEteAvec() {
            return dureeEteAvec;
        }

        /**
         * Sets the value of the dureeEteAvec property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDureeEteAvec(Integer value) {
            this.dureeEteAvec = value;
        }

        /**
         * Gets the value of the dureeEteSans property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDureeEteSans() {
            return dureeEteSans;
        }

        /**
         * Sets the value of the dureeEteSans property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDureeEteSans(Integer value) {
            this.dureeEteSans = value;
        }

        /**
         * Gets the value of the dureeHivAvec property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDureeHivAvec() {
            return dureeHivAvec;
        }

        /**
         * Sets the value of the dureeHivAvec property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDureeHivAvec(Integer value) {
            this.dureeHivAvec = value;
        }

        /**
         * Gets the value of the dureeHivSans property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getDureeHivSans() {
            return dureeHivSans;
        }

        /**
         * Sets the value of the dureeHivSans property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setDureeHivSans(Integer value) {
            this.dureeHivSans = value;
        }

    }

}
