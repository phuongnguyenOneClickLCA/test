
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Element terminal
 * 
 * <p>Java class for t_element complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_element">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id_base">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="id_fiche" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_fiche_configurateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version_configurateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_produit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unite_uf">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="quantite" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dve" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="type_donnees">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *               &lt;enumeration value="7"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="commentaire" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="donnees_configurateur" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parametre" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="unite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numero" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
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
@XmlType(name = "t_element", propOrder = {

})
public class TElement {

    @XmlElement(name = "id_base", defaultValue = "0")
    protected int idBase;
    @XmlElement(name = "id_fiche")
    protected String idFiche;
    @XmlElement(name = "id_fiche_configurateur")
    protected String idFicheConfigurateur;
    @XmlElement(name = "version_configurateur")
    protected String versionConfigurateur;
    @XmlElement(name = "id_produit")
    protected String idProduit;
    @XmlElement(required = true)
    protected String nom;
    @XmlElement(name = "unite_uf")
    protected int uniteUf;
    @XmlElement(required = true)
    protected BigDecimal quantite;
    @XmlElement(required = true)
    protected BigDecimal dve;
    @XmlElement(name = "type_donnees")
    protected int typeDonnees;
    protected String commentaire;
    @XmlElement(name = "donnees_configurateur")
    protected TElement.DonneesConfigurateur donneesConfigurateur;

    /**
     * Gets the value of the idBase property.
     * 
     */
    public int getIdBase() {
        return idBase;
    }

    /**
     * Sets the value of the idBase property.
     * 
     */
    public void setIdBase(int value) {
        this.idBase = value;
    }

    /**
     * Gets the value of the idFiche property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFiche() {
        return idFiche;
    }

    /**
     * Sets the value of the idFiche property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFiche(String value) {
        this.idFiche = value;
    }

    /**
     * Gets the value of the idFicheConfigurateur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFicheConfigurateur() {
        return idFicheConfigurateur;
    }

    /**
     * Sets the value of the idFicheConfigurateur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFicheConfigurateur(String value) {
        this.idFicheConfigurateur = value;
    }

    /**
     * Gets the value of the versionConfigurateur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionConfigurateur() {
        return versionConfigurateur;
    }

    /**
     * Sets the value of the versionConfigurateur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionConfigurateur(String value) {
        this.versionConfigurateur = value;
    }

    /**
     * Gets the value of the idProduit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProduit() {
        return idProduit;
    }

    /**
     * Sets the value of the idProduit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProduit(String value) {
        this.idProduit = value;
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
     * Gets the value of the uniteUf property.
     * 
     */
    public int getUniteUf() {
        return uniteUf;
    }

    /**
     * Sets the value of the uniteUf property.
     * 
     */
    public void setUniteUf(int value) {
        this.uniteUf = value;
    }

    /**
     * Gets the value of the quantite property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantite() {
        return quantite;
    }

    /**
     * Sets the value of the quantite property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantite(BigDecimal value) {
        this.quantite = value;
    }

    /**
     * Gets the value of the dve property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDve() {
        return dve;
    }

    /**
     * Sets the value of the dve property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDve(BigDecimal value) {
        this.dve = value;
    }

    /**
     * Gets the value of the typeDonnees property.
     * 
     */
    public int getTypeDonnees() {
        return typeDonnees;
    }

    /**
     * Sets the value of the typeDonnees property.
     * 
     */
    public void setTypeDonnees(int value) {
        this.typeDonnees = value;
    }

    /**
     * Gets the value of the commentaire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Sets the value of the commentaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommentaire(String value) {
        this.commentaire = value;
    }

    /**
     * Gets the value of the donneesConfigurateur property.
     * 
     * @return
     *     possible object is
     *     {@link TElement.DonneesConfigurateur }
     *     
     */
    public TElement.DonneesConfigurateur getDonneesConfigurateur() {
        return donneesConfigurateur;
    }

    /**
     * Sets the value of the donneesConfigurateur property.
     * 
     * @param value
     *     allowed object is
     *     {@link TElement.DonneesConfigurateur }
     *     
     */
    public void setDonneesConfigurateur(TElement.DonneesConfigurateur value) {
        this.donneesConfigurateur = value;
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
     *         &lt;element name="parametre" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="unite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numero" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
        "parametre"
    })
    public static class DonneesConfigurateur {

        @XmlElement(required = true)
        protected List<TElement.DonneesConfigurateur.Parametre> parametre;

        /**
         * Gets the value of the parametre property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the parametre property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParametre().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TElement.DonneesConfigurateur.Parametre }
         * 
         * 
         */
        public List<TElement.DonneesConfigurateur.Parametre> getParametre() {
            if (parametre == null) {
                parametre = new ArrayList<TElement.DonneesConfigurateur.Parametre>();
            }
            return this.parametre;
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
         *         &lt;element name="valeur" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="unite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="numero" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
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
            "valeur",
            "unite"
        })
        public static class Parametre {

            @XmlElement(required = true)
            protected String nom;
            @XmlElement(required = true)
            protected String valeur;
            protected String unite;
            @XmlAttribute(name = "numero", required = true)
            protected int numero;

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
             * Gets the value of the valeur property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValeur() {
                return valeur;
            }

            /**
             * Sets the value of the valeur property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValeur(String value) {
                this.valeur = value;
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
             * Gets the value of the numero property.
             * 
             */
            public int getNumero() {
                return numero;
            }

            /**
             * Sets the value of the numero property.
             * 
             */
            public void setNumero(int value) {
                this.numero = value;
            }

        }

    }

}
