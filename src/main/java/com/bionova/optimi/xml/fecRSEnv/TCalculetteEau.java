
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_calculette_eau complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_calculette_eau">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="reseau_collecte">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="reseau_assainissement">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nb_usager">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="surf_parc_arrosee">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="vol_collecte_ep_parc">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="conso_particuliere">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="conso_ep_usage_int">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="fg_equipement" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="toilettes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="taux_seches" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="taux_3l_6l" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="taux_2l_4l" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="taux_urinoirs" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="regulateur_debit" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="taux_lavabo" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="taux_evier" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="taux_douche" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;maxInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="taux_puis_sup_8m" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="1"/>
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
@XmlType(name = "t_calculette_eau", propOrder = {

})
public class TCalculetteEau {

    @XmlElement(name = "reseau_collecte")
    protected int reseauCollecte;
    @XmlElement(name = "reseau_assainissement")
    protected int reseauAssainissement;
    @XmlElement(name = "nb_usager", required = true)
    protected BigDecimal nbUsager;
    @XmlElement(name = "surf_parc_arrosee", required = true)
    protected BigDecimal surfParcArrosee;
    @XmlElement(name = "vol_collecte_ep_parc", required = true)
    protected BigDecimal volCollecteEpParc;
    @XmlElement(name = "conso_particuliere", required = true)
    protected BigDecimal consoParticuliere;
    @XmlElement(name = "conso_ep_usage_int", required = true)
    protected BigDecimal consoEpUsageInt;
    @XmlElement(name = "fg_equipement")
    protected BigDecimal fgEquipement;
    protected TCalculetteEau.Toilettes toilettes;
    @XmlElement(name = "regulateur_debit")
    protected TCalculetteEau.RegulateurDebit regulateurDebit;
    @XmlElement(name = "taux_puis_sup_8m")
    protected BigDecimal tauxPuisSup8M;

    /**
     * Gets the value of the reseauCollecte property.
     * 
     */
    public int getReseauCollecte() {
        return reseauCollecte;
    }

    /**
     * Sets the value of the reseauCollecte property.
     * 
     */
    public void setReseauCollecte(int value) {
        this.reseauCollecte = value;
    }

    /**
     * Gets the value of the reseauAssainissement property.
     * 
     */
    public int getReseauAssainissement() {
        return reseauAssainissement;
    }

    /**
     * Sets the value of the reseauAssainissement property.
     * 
     */
    public void setReseauAssainissement(int value) {
        this.reseauAssainissement = value;
    }

    /**
     * Gets the value of the nbUsager property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNbUsager() {
        return nbUsager;
    }

    /**
     * Sets the value of the nbUsager property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNbUsager(BigDecimal value) {
        this.nbUsager = value;
    }

    /**
     * Gets the value of the surfParcArrosee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSurfParcArrosee() {
        return surfParcArrosee;
    }

    /**
     * Sets the value of the surfParcArrosee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSurfParcArrosee(BigDecimal value) {
        this.surfParcArrosee = value;
    }

    /**
     * Gets the value of the volCollecteEpParc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVolCollecteEpParc() {
        return volCollecteEpParc;
    }

    /**
     * Sets the value of the volCollecteEpParc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVolCollecteEpParc(BigDecimal value) {
        this.volCollecteEpParc = value;
    }

    /**
     * Gets the value of the consoParticuliere property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getConsoParticuliere() {
        return consoParticuliere;
    }

    /**
     * Sets the value of the consoParticuliere property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setConsoParticuliere(BigDecimal value) {
        this.consoParticuliere = value;
    }

    /**
     * Gets the value of the consoEpUsageInt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getConsoEpUsageInt() {
        return consoEpUsageInt;
    }

    /**
     * Sets the value of the consoEpUsageInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setConsoEpUsageInt(BigDecimal value) {
        this.consoEpUsageInt = value;
    }

    /**
     * Gets the value of the fgEquipement property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFgEquipement() {
        return fgEquipement;
    }

    /**
     * Sets the value of the fgEquipement property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFgEquipement(BigDecimal value) {
        this.fgEquipement = value;
    }

    /**
     * Gets the value of the toilettes property.
     * 
     * @return
     *     possible object is
     *     {@link TCalculetteEau.Toilettes }
     *     
     */
    public TCalculetteEau.Toilettes getToilettes() {
        return toilettes;
    }

    /**
     * Sets the value of the toilettes property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCalculetteEau.Toilettes }
     *     
     */
    public void setToilettes(TCalculetteEau.Toilettes value) {
        this.toilettes = value;
    }

    /**
     * Gets the value of the regulateurDebit property.
     * 
     * @return
     *     possible object is
     *     {@link TCalculetteEau.RegulateurDebit }
     *     
     */
    public TCalculetteEau.RegulateurDebit getRegulateurDebit() {
        return regulateurDebit;
    }

    /**
     * Sets the value of the regulateurDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCalculetteEau.RegulateurDebit }
     *     
     */
    public void setRegulateurDebit(TCalculetteEau.RegulateurDebit value) {
        this.regulateurDebit = value;
    }

    /**
     * Gets the value of the tauxPuisSup8M property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTauxPuisSup8M() {
        return tauxPuisSup8M;
    }

    /**
     * Sets the value of the tauxPuisSup8M property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTauxPuisSup8M(BigDecimal value) {
        this.tauxPuisSup8M = value;
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
     *         &lt;element name="taux_lavabo" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="taux_evier" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="taux_douche" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
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
    public static class RegulateurDebit {

        @XmlElement(name = "taux_lavabo")
        protected BigDecimal tauxLavabo;
        @XmlElement(name = "taux_evier")
        protected BigDecimal tauxEvier;
        @XmlElement(name = "taux_douche")
        protected BigDecimal tauxDouche;

        /**
         * Gets the value of the tauxLavabo property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTauxLavabo() {
            return tauxLavabo;
        }

        /**
         * Sets the value of the tauxLavabo property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTauxLavabo(BigDecimal value) {
            this.tauxLavabo = value;
        }

        /**
         * Gets the value of the tauxEvier property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTauxEvier() {
            return tauxEvier;
        }

        /**
         * Sets the value of the tauxEvier property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTauxEvier(BigDecimal value) {
            this.tauxEvier = value;
        }

        /**
         * Gets the value of the tauxDouche property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTauxDouche() {
            return tauxDouche;
        }

        /**
         * Sets the value of the tauxDouche property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTauxDouche(BigDecimal value) {
            this.tauxDouche = value;
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
     *       &lt;all>
     *         &lt;element name="taux_seches" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="taux_3l_6l" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="taux_2l_4l" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="taux_urinoirs" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;maxInclusive value="1"/>
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
    public static class Toilettes {

        @XmlElement(name = "taux_seches")
        protected BigDecimal tauxSeches;
        @XmlElement(name = "taux_3l_6l")
        protected BigDecimal taux3L6L;
        @XmlElement(name = "taux_2l_4l")
        protected BigDecimal taux2L4L;
        @XmlElement(name = "taux_urinoirs")
        protected BigDecimal tauxUrinoirs;

        /**
         * Gets the value of the tauxSeches property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTauxSeches() {
            return tauxSeches;
        }

        /**
         * Sets the value of the tauxSeches property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTauxSeches(BigDecimal value) {
            this.tauxSeches = value;
        }

        /**
         * Gets the value of the taux3L6L property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTaux3L6L() {
            return taux3L6L;
        }

        /**
         * Sets the value of the taux3L6L property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTaux3L6L(BigDecimal value) {
            this.taux3L6L = value;
        }

        /**
         * Gets the value of the taux2L4L property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTaux2L4L() {
            return taux2L4L;
        }

        /**
         * Sets the value of the taux2L4L property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTaux2L4L(BigDecimal value) {
            this.taux2L4L = value;
        }

        /**
         * Gets the value of the tauxUrinoirs property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTauxUrinoirs() {
            return tauxUrinoirs;
        }

        /**
         * Sets the value of the tauxUrinoirs property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTauxUrinoirs(BigDecimal value) {
            this.tauxUrinoirs = value;
        }

    }

}
