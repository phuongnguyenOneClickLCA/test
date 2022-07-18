
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * DC:Feuillet des données administratives
 * 
 * <p>Java class for t_donnees_administratives complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_donnees_administratives">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maitre_ouvrage" type="{}t_acteur_obli"/>
 *         &lt;element name="maitre_oeuvre" type="{}t_acteur" minOccurs="0"/>
 *         &lt;element name="BET">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="adresse" type="{}t_adresse"/>
 *                   &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="courriel" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="date_etude">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;length value="10"/>
 *                         &lt;pattern value="20[0-2][0-9]-[0-1][0-9]-[0-3][0-9]"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="editeur_logiciel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="nom_logiciel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="version_logiciel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="bureau_controle" type="{}t_acteur"/>
 *         &lt;element name="operation" type="{}t_operation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_donnees_administratives", propOrder = {
    "maitreOuvrage",
    "maitreOeuvre",
    "bet",
    "bureauControle",
    "operation"
})
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.RSET.DatasComp.DonneesGenerales.class
})
public class TDonneesAdministratives {

    @XmlElement(name = "maitre_ouvrage", required = true)
    protected TActeurObli maitreOuvrage;
    @XmlElement(name = "maitre_oeuvre")
    protected TActeur maitreOeuvre;
    @XmlElement(name = "BET", required = true)
    protected TDonneesAdministratives.BET bet;
    @XmlElement(name = "bureau_controle", required = true)
    protected TActeur bureauControle;
    @XmlElement(required = true)
    protected TOperation operation;

    /**
     * Gets the value of the maitreOuvrage property.
     * 
     * @return
     *     possible object is
     *     {@link TActeurObli }
     *     
     */
    public TActeurObli getMaitreOuvrage() {
        return maitreOuvrage;
    }

    /**
     * Sets the value of the maitreOuvrage property.
     * 
     * @param value
     *     allowed object is
     *     {@link TActeurObli }
     *     
     */
    public void setMaitreOuvrage(TActeurObli value) {
        this.maitreOuvrage = value;
    }

    /**
     * Gets the value of the maitreOeuvre property.
     * 
     * @return
     *     possible object is
     *     {@link TActeur }
     *     
     */
    public TActeur getMaitreOeuvre() {
        return maitreOeuvre;
    }

    /**
     * Sets the value of the maitreOeuvre property.
     * 
     * @param value
     *     allowed object is
     *     {@link TActeur }
     *     
     */
    public void setMaitreOeuvre(TActeur value) {
        this.maitreOeuvre = value;
    }

    /**
     * Gets the value of the bet property.
     * 
     * @return
     *     possible object is
     *     {@link TDonneesAdministratives.BET }
     *     
     */
    public TDonneesAdministratives.BET getBET() {
        return bet;
    }

    /**
     * Sets the value of the bet property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDonneesAdministratives.BET }
     *     
     */
    public void setBET(TDonneesAdministratives.BET value) {
        this.bet = value;
    }

    /**
     * Gets the value of the bureauControle property.
     * 
     * @return
     *     possible object is
     *     {@link TActeur }
     *     
     */
    public TActeur getBureauControle() {
        return bureauControle;
    }

    /**
     * Sets the value of the bureauControle property.
     * 
     * @param value
     *     allowed object is
     *     {@link TActeur }
     *     
     */
    public void setBureauControle(TActeur value) {
        this.bureauControle = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link TOperation }
     *     
     */
    public TOperation getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOperation }
     *     
     */
    public void setOperation(TOperation value) {
        this.operation = value;
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
     *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="adresse" type="{}t_adresse"/>
     *         &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="courriel" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="date_etude">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;length value="10"/>
     *               &lt;pattern value="20[0-2][0-9]-[0-1][0-9]-[0-3][0-9]"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="editeur_logiciel" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="nom_logiciel" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="version_logiciel" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "nom",
        "adresse",
        "tel",
        "courriel",
        "dateEtude",
        "editeurLogiciel",
        "nomLogiciel",
        "versionLogiciel"
    })
    public static class BET {

        @XmlElement(required = true)
        protected String nom;
        @XmlElement(required = true)
        protected TAdresse adresse;
        protected String tel;
        protected String courriel;
        @XmlElement(name = "date_etude", required = true)
        protected String dateEtude;
        @XmlElement(name = "editeur_logiciel", required = true)
        protected String editeurLogiciel;
        @XmlElement(name = "nom_logiciel", required = true)
        protected String nomLogiciel;
        @XmlElement(name = "version_logiciel", required = true)
        protected String versionLogiciel;

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
         * Gets the value of the adresse property.
         * 
         * @return
         *     possible object is
         *     {@link TAdresse }
         *     
         */
        public TAdresse getAdresse() {
            return adresse;
        }

        /**
         * Sets the value of the adresse property.
         * 
         * @param value
         *     allowed object is
         *     {@link TAdresse }
         *     
         */
        public void setAdresse(TAdresse value) {
            this.adresse = value;
        }

        /**
         * Gets the value of the tel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTel() {
            return tel;
        }

        /**
         * Sets the value of the tel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTel(String value) {
            this.tel = value;
        }

        /**
         * Gets the value of the courriel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCourriel() {
            return courriel;
        }

        /**
         * Sets the value of the courriel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCourriel(String value) {
            this.courriel = value;
        }

        /**
         * Gets the value of the dateEtude property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateEtude() {
            return dateEtude;
        }

        /**
         * Sets the value of the dateEtude property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateEtude(String value) {
            this.dateEtude = value;
        }

        /**
         * Gets the value of the editeurLogiciel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEditeurLogiciel() {
            return editeurLogiciel;
        }

        /**
         * Sets the value of the editeurLogiciel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEditeurLogiciel(String value) {
            this.editeurLogiciel = value;
        }

        /**
         * Gets the value of the nomLogiciel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNomLogiciel() {
            return nomLogiciel;
        }

        /**
         * Sets the value of the nomLogiciel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNomLogiciel(String value) {
            this.nomLogiciel = value;
        }

        /**
         * Gets the value of the versionLogiciel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVersionLogiciel() {
            return versionLogiciel;
        }

        /**
         * Sets the value of the versionLogiciel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVersionLogiciel(String value) {
            this.versionLogiciel = value;
        }

    }

}
