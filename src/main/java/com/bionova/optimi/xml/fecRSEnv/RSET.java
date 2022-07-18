
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.math.BigInteger;
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
 *         &lt;element name="Datas_Comp">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="donnees_generales">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{}t_donnees_administratives">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="generation_collection" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element ref="{}generation" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="batiment_collection">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}batiment" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="distribution_intergroupe_chaud" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="classe_hvc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="distribution_intergroupe_froid" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="classe_hvc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="distribution_intergroupe_ecs" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="pcad_collection" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="pcad" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;all>
 *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="ballon_decentralise_collection" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="ballon_decentralise" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;all>
 *                                                           &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                                           &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="marque_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                           &lt;element name="deno_comm_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                         &lt;/all>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="ballon_centralise" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;all>
 *                                                 &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                                 &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                               &lt;/all>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/all>
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
 *         &lt;element name="Entree_Projet" type="{}RT_Data_Projet"/>
 *         &lt;element name="Sortie_Projet">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}RT_Data_Sortie_Projet">
 *                 &lt;attribute name="HashKey" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;maxLength value="89"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Comp" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="Sortie_Batiment_B_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_B" minOccurs="0"/>
 *                   &lt;element name="Sortie_Batiment_C_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_C" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="idfiche" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="65"/>
 *             &lt;whiteSpace value="collapse"/>
 *             &lt;maxLength value="128"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="idxsd" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="idxsl" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datasComp",
    "entreeProjet",
    "sortieProjet",
    "comp"
})
@XmlRootElement(name = "RSET")
public class RSET {

    @XmlElement(name = "Datas_Comp", required = true)
    protected RSET.DatasComp datasComp;
    @XmlElement(name = "Entree_Projet", required = true)
    protected RTDataProjet entreeProjet;
    @XmlElement(name = "Sortie_Projet", required = true)
    protected RSET.SortieProjet sortieProjet;
    @XmlElement(name = "Comp")
    protected RSET.Comp comp;
    @XmlAttribute(name = "idfiche", required = true)
    protected String idfiche;
    @XmlAttribute(name = "idxsd")
    protected BigDecimal idxsd;
    @XmlAttribute(name = "idxsl", required = true)
    protected BigDecimal idxsl;

    /**
     * Gets the value of the datasComp property.
     * 
     * @return
     *     possible object is
     *     {@link RSET.DatasComp }
     *     
     */
    public RSET.DatasComp getDatasComp() {
        return datasComp;
    }

    /**
     * Sets the value of the datasComp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSET.DatasComp }
     *     
     */
    public void setDatasComp(RSET.DatasComp value) {
        this.datasComp = value;
    }

    /**
     * Gets the value of the entreeProjet property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataProjet }
     *     
     */
    public RTDataProjet getEntreeProjet() {
        return entreeProjet;
    }

    /**
     * Sets the value of the entreeProjet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataProjet }
     *     
     */
    public void setEntreeProjet(RTDataProjet value) {
        this.entreeProjet = value;
    }

    /**
     * Gets the value of the sortieProjet property.
     * 
     * @return
     *     possible object is
     *     {@link RSET.SortieProjet }
     *     
     */
    public RSET.SortieProjet getSortieProjet() {
        return sortieProjet;
    }

    /**
     * Sets the value of the sortieProjet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSET.SortieProjet }
     *     
     */
    public void setSortieProjet(RSET.SortieProjet value) {
        this.sortieProjet = value;
    }

    /**
     * Gets the value of the comp property.
     * 
     * @return
     *     possible object is
     *     {@link RSET.Comp }
     *     
     */
    public RSET.Comp getComp() {
        return comp;
    }

    /**
     * Sets the value of the comp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSET.Comp }
     *     
     */
    public void setComp(RSET.Comp value) {
        this.comp = value;
    }

    /**
     * Gets the value of the idfiche property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdfiche() {
        return idfiche;
    }

    /**
     * Sets the value of the idfiche property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdfiche(String value) {
        this.idfiche = value;
    }

    /**
     * Gets the value of the idxsd property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdxsd() {
        return idxsd;
    }

    /**
     * Sets the value of the idxsd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdxsd(BigDecimal value) {
        this.idxsd = value;
    }

    /**
     * Gets the value of the idxsl property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdxsl() {
        return idxsl;
    }

    /**
     * Sets the value of the idxsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdxsl(BigDecimal value) {
        this.idxsl = value;
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
     *         &lt;element name="Sortie_Batiment_B_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_B" minOccurs="0"/>
     *         &lt;element name="Sortie_Batiment_C_Collection" type="{}ArrayOfRT_Data_Sortie_Batiment_C" minOccurs="0"/>
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
        "sortieBatimentBCollection",
        "sortieBatimentCCollection"
    })
    public static class Comp {

        @XmlElement(name = "Sortie_Batiment_B_Collection")
        protected ArrayOfRTDataSortieBatimentB sortieBatimentBCollection;
        @XmlElement(name = "Sortie_Batiment_C_Collection")
        protected ArrayOfRTDataSortieBatimentC sortieBatimentCCollection;

        /**
         * Gets the value of the sortieBatimentBCollection property.
         * 
         * @return
         *     possible object is
         *     {@link ArrayOfRTDataSortieBatimentB }
         *     
         */
        public ArrayOfRTDataSortieBatimentB getSortieBatimentBCollection() {
            return sortieBatimentBCollection;
        }

        /**
         * Sets the value of the sortieBatimentBCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link ArrayOfRTDataSortieBatimentB }
         *     
         */
        public void setSortieBatimentBCollection(ArrayOfRTDataSortieBatimentB value) {
            this.sortieBatimentBCollection = value;
        }

        /**
         * Gets the value of the sortieBatimentCCollection property.
         * 
         * @return
         *     possible object is
         *     {@link ArrayOfRTDataSortieBatimentC }
         *     
         */
        public ArrayOfRTDataSortieBatimentC getSortieBatimentCCollection() {
            return sortieBatimentCCollection;
        }

        /**
         * Sets the value of the sortieBatimentCCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link ArrayOfRTDataSortieBatimentC }
         *     
         */
        public void setSortieBatimentCCollection(ArrayOfRTDataSortieBatimentC value) {
            this.sortieBatimentCCollection = value;
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
     *         &lt;element name="donnees_generales">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{}t_donnees_administratives">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="generation_collection" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;element ref="{}generation" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="batiment_collection">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}batiment" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="distribution_intergroupe_chaud" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="classe_hvc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="distribution_intergroupe_froid" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="classe_hvc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="distribution_intergroupe_ecs" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence minOccurs="0">
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="pcad_collection" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="pcad" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;all>
     *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="ballon_decentralise_collection" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="ballon_decentralise" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;all>
     *                                                 &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                                                 &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="marque_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                                 &lt;element name="deno_comm_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                               &lt;/all>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="ballon_centralise" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;all>
     *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                                       &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                     &lt;/all>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/all>
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
        "donneesGenerales",
        "generationCollection",
        "batimentCollection",
        "distributionIntergroupeChaud",
        "distributionIntergroupeFroid",
        "distributionIntergroupeEcs",
        "pcadCollection"
    })
    public static class DatasComp {

        @XmlElement(name = "donnees_generales", required = true)
        protected RSET.DatasComp.DonneesGenerales donneesGenerales;
        @XmlElement(name = "generation_collection")
        protected RSET.DatasComp.GenerationCollection generationCollection;
        @XmlElement(name = "batiment_collection", required = true)
        protected RSET.DatasComp.BatimentCollection batimentCollection;
        @XmlElement(name = "distribution_intergroupe_chaud")
        protected List<RSET.DatasComp.DistributionIntergroupeChaud> distributionIntergroupeChaud;
        @XmlElement(name = "distribution_intergroupe_froid")
        protected List<RSET.DatasComp.DistributionIntergroupeFroid> distributionIntergroupeFroid;
        @XmlElement(name = "distribution_intergroupe_ecs")
        protected List<RSET.DatasComp.DistributionIntergroupeEcs> distributionIntergroupeEcs;
        @XmlElement(name = "pcad_collection")
        protected RSET.DatasComp.PcadCollection pcadCollection;

        /**
         * Gets the value of the donneesGenerales property.
         * 
         * @return
         *     possible object is
         *     {@link RSET.DatasComp.DonneesGenerales }
         *     
         */
        public RSET.DatasComp.DonneesGenerales getDonneesGenerales() {
            return donneesGenerales;
        }

        /**
         * Sets the value of the donneesGenerales property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSET.DatasComp.DonneesGenerales }
         *     
         */
        public void setDonneesGenerales(RSET.DatasComp.DonneesGenerales value) {
            this.donneesGenerales = value;
        }

        /**
         * Gets the value of the generationCollection property.
         * 
         * @return
         *     possible object is
         *     {@link RSET.DatasComp.GenerationCollection }
         *     
         */
        public RSET.DatasComp.GenerationCollection getGenerationCollection() {
            return generationCollection;
        }

        /**
         * Sets the value of the generationCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSET.DatasComp.GenerationCollection }
         *     
         */
        public void setGenerationCollection(RSET.DatasComp.GenerationCollection value) {
            this.generationCollection = value;
        }

        /**
         * Gets the value of the batimentCollection property.
         * 
         * @return
         *     possible object is
         *     {@link RSET.DatasComp.BatimentCollection }
         *     
         */
        public RSET.DatasComp.BatimentCollection getBatimentCollection() {
            return batimentCollection;
        }

        /**
         * Sets the value of the batimentCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSET.DatasComp.BatimentCollection }
         *     
         */
        public void setBatimentCollection(RSET.DatasComp.BatimentCollection value) {
            this.batimentCollection = value;
        }

        /**
         * Gets the value of the distributionIntergroupeChaud property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the distributionIntergroupeChaud property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDistributionIntergroupeChaud().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RSET.DatasComp.DistributionIntergroupeChaud }
         * 
         * 
         */
        public List<RSET.DatasComp.DistributionIntergroupeChaud> getDistributionIntergroupeChaud() {
            if (distributionIntergroupeChaud == null) {
                distributionIntergroupeChaud = new ArrayList<RSET.DatasComp.DistributionIntergroupeChaud>();
            }
            return this.distributionIntergroupeChaud;
        }

        /**
         * Gets the value of the distributionIntergroupeFroid property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the distributionIntergroupeFroid property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDistributionIntergroupeFroid().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RSET.DatasComp.DistributionIntergroupeFroid }
         * 
         * 
         */
        public List<RSET.DatasComp.DistributionIntergroupeFroid> getDistributionIntergroupeFroid() {
            if (distributionIntergroupeFroid == null) {
                distributionIntergroupeFroid = new ArrayList<RSET.DatasComp.DistributionIntergroupeFroid>();
            }
            return this.distributionIntergroupeFroid;
        }

        /**
         * Gets the value of the distributionIntergroupeEcs property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the distributionIntergroupeEcs property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDistributionIntergroupeEcs().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RSET.DatasComp.DistributionIntergroupeEcs }
         * 
         * 
         */
        public List<RSET.DatasComp.DistributionIntergroupeEcs> getDistributionIntergroupeEcs() {
            if (distributionIntergroupeEcs == null) {
                distributionIntergroupeEcs = new ArrayList<RSET.DatasComp.DistributionIntergroupeEcs>();
            }
            return this.distributionIntergroupeEcs;
        }

        /**
         * Gets the value of the pcadCollection property.
         * 
         * @return
         *     possible object is
         *     {@link RSET.DatasComp.PcadCollection }
         *     
         */
        public RSET.DatasComp.PcadCollection getPcadCollection() {
            return pcadCollection;
        }

        /**
         * Sets the value of the pcadCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link RSET.DatasComp.PcadCollection }
         *     
         */
        public void setPcadCollection(RSET.DatasComp.PcadCollection value) {
            this.pcadCollection = value;
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
         *         &lt;element ref="{}batiment" maxOccurs="unbounded"/>
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
            "batiment"
        })
        public static class BatimentCollection {

            @XmlElement(required = true)
            protected List<Batiment> batiment;

            /**
             * batiments, données complémentaires, données d'entrées Gets the value of the batiment property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the batiment property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBatiment().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Batiment }
             * 
             * 
             */
            public List<Batiment> getBatiment() {
                if (batiment == null) {
                    batiment = new ArrayList<Batiment>();
                }
                return this.batiment;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="classe_hvc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
            "classeVc",
            "classeHvc"
        })
        public static class DistributionIntergroupeChaud {

            @XmlElement(name = "Index")
            protected BigInteger index;
            @XmlElement(name = "classe_vc", defaultValue = "0")
            protected BigInteger classeVc;
            @XmlElement(name = "classe_hvc", defaultValue = "0")
            protected BigInteger classeHvc;

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
             * Gets the value of the classeVc property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getClasseVc() {
                return classeVc;
            }

            /**
             * Sets the value of the classeVc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setClasseVc(BigInteger value) {
                this.classeVc = value;
            }

            /**
             * Gets the value of the classeHvc property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getClasseHvc() {
                return classeHvc;
            }

            /**
             * Sets the value of the classeHvc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setClasseHvc(BigInteger value) {
                this.classeHvc = value;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
            "classeVc"
        })
        public static class DistributionIntergroupeEcs {

            @XmlElement(name = "Index")
            protected BigInteger index;
            @XmlElement(name = "classe_vc", defaultValue = "0")
            protected BigInteger classeVc;

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
             * Gets the value of the classeVc property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getClasseVc() {
                return classeVc;
            }

            /**
             * Sets the value of the classeVc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setClasseVc(BigInteger value) {
                this.classeVc = value;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="classe_vc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="classe_hvc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
            "classeVc",
            "classeHvc"
        })
        public static class DistributionIntergroupeFroid {

            @XmlElement(name = "Index")
            protected BigInteger index;
            @XmlElement(name = "classe_vc", defaultValue = "0")
            protected BigInteger classeVc;
            @XmlElement(name = "classe_hvc", defaultValue = "0")
            protected BigInteger classeHvc;

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
             * Gets the value of the classeVc property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getClasseVc() {
                return classeVc;
            }

            /**
             * Sets the value of the classeVc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setClasseVc(BigInteger value) {
                this.classeVc = value;
            }

            /**
             * Gets the value of the classeHvc property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getClasseHvc() {
                return classeHvc;
            }

            /**
             * Sets the value of the classeHvc property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setClasseHvc(BigInteger value) {
                this.classeHvc = value;
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
         *     &lt;extension base="{}t_donnees_administratives">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class DonneesGenerales
            extends TDonneesAdministratives
        {


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
         *         &lt;element ref="{}generation" maxOccurs="unbounded"/>
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
            "generation"
        })
        public static class GenerationCollection {

            protected List<Generation> generation;

            /**
             * Gets the value of the generation property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the generation property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGeneration().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Generation }
             * 
             * 
             */
            public List<Generation> getGeneration() {
                if (generation == null) {
                    generation = new ArrayList<Generation>();
                }
                return this.generation;
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
         *         &lt;element name="pcad" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;all>
         *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="ballon_decentralise_collection" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="ballon_decentralise" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;all>
         *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                                       &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="marque_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                       &lt;element name="deno_comm_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                                     &lt;/all>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="ballon_centralise" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;all>
         *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                             &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                           &lt;/all>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/all>
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
            "pcad"
        })
        public static class PcadCollection {

            protected List<RSET.DatasComp.PcadCollection.Pcad> pcad;

            /**
             * Gets the value of the pcad property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the pcad property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPcad().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link RSET.DatasComp.PcadCollection.Pcad }
             * 
             * 
             */
            public List<RSET.DatasComp.PcadCollection.Pcad> getPcad() {
                if (pcad == null) {
                    pcad = new ArrayList<RSET.DatasComp.PcadCollection.Pcad>();
                }
                return this.pcad;
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
             *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="ballon_decentralise_collection" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="ballon_decentralise" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;all>
             *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *                             &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="marque_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                             &lt;element name="deno_comm_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                           &lt;/all>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="ballon_centralise" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;all>
             *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *                   &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            @XmlType(name = "", propOrder = {

            })
            public static class Pcad {

                @XmlElement(name = "Index", required = true)
                protected Object index;
                @XmlElement(name = "ballon_decentralise_collection")
                protected RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection ballonDecentraliseCollection;
                @XmlElement(name = "ballon_centralise")
                protected RSET.DatasComp.PcadCollection.Pcad.BallonCentralise ballonCentralise;

                /**
                 * Gets the value of the index property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getIndex() {
                    return index;
                }

                /**
                 * Sets the value of the index property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setIndex(Object value) {
                    this.index = value;
                }

                /**
                 * Gets the value of the ballonDecentraliseCollection property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection }
                 *     
                 */
                public RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection getBallonDecentraliseCollection() {
                    return ballonDecentraliseCollection;
                }

                /**
                 * Sets the value of the ballonDecentraliseCollection property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection }
                 *     
                 */
                public void setBallonDecentraliseCollection(RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection value) {
                    this.ballonDecentraliseCollection = value;
                }

                /**
                 * Gets the value of the ballonCentralise property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RSET.DatasComp.PcadCollection.Pcad.BallonCentralise }
                 *     
                 */
                public RSET.DatasComp.PcadCollection.Pcad.BallonCentralise getBallonCentralise() {
                    return ballonCentralise;
                }

                /**
                 * Sets the value of the ballonCentralise property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RSET.DatasComp.PcadCollection.Pcad.BallonCentralise }
                 *     
                 */
                public void setBallonCentralise(RSET.DatasComp.PcadCollection.Pcad.BallonCentralise value) {
                    this.ballonCentralise = value;
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
                 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
                 *         &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                public static class BallonCentralise {

                    @XmlElement(name = "Index")
                    protected int index;
                    @XmlElement(name = "marque_ballon")
                    protected String marqueBallon;
                    @XmlElement(name = "deno_comm_ballon")
                    protected String denoCommBallon;

                    /**
                     * Gets the value of the index property.
                     * 
                     */
                    public int getIndex() {
                        return index;
                    }

                    /**
                     * Sets the value of the index property.
                     * 
                     */
                    public void setIndex(int value) {
                        this.index = value;
                    }

                    /**
                     * Gets the value of the marqueBallon property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMarqueBallon() {
                        return marqueBallon;
                    }

                    /**
                     * Sets the value of the marqueBallon property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMarqueBallon(String value) {
                        this.marqueBallon = value;
                    }

                    /**
                     * Gets the value of the denoCommBallon property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDenoCommBallon() {
                        return denoCommBallon;
                    }

                    /**
                     * Sets the value of the denoCommBallon property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDenoCommBallon(String value) {
                        this.denoCommBallon = value;
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
                 *         &lt;element name="ballon_decentralise" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;all>
                 *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
                 *                   &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="marque_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                   &lt;element name="deno_comm_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *                 &lt;/all>
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
                    "ballonDecentralise"
                })
                public static class BallonDecentraliseCollection {

                    @XmlElement(name = "ballon_decentralise")
                    protected List<RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection.BallonDecentralise> ballonDecentralise;

                    /**
                     * Gets the value of the ballonDecentralise property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the ballonDecentralise property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getBallonDecentralise().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection.BallonDecentralise }
                     * 
                     * 
                     */
                    public List<RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection.BallonDecentralise> getBallonDecentralise() {
                        if (ballonDecentralise == null) {
                            ballonDecentralise = new ArrayList<RSET.DatasComp.PcadCollection.Pcad.BallonDecentraliseCollection.BallonDecentralise>();
                        }
                        return this.ballonDecentralise;
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
                     *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
                     *         &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="marque_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                     *         &lt;element name="deno_comm_ballon_app_com" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                    public static class BallonDecentralise {

                        @XmlElement(name = "Index")
                        protected int index;
                        @XmlElement(name = "marque_ballon")
                        protected String marqueBallon;
                        @XmlElement(name = "deno_comm_ballon")
                        protected String denoCommBallon;
                        @XmlElement(name = "marque_ballon_app_com")
                        protected String marqueBallonAppCom;
                        @XmlElement(name = "deno_comm_ballon_app_com")
                        protected String denoCommBallonAppCom;

                        /**
                         * Gets the value of the index property.
                         * 
                         */
                        public int getIndex() {
                            return index;
                        }

                        /**
                         * Sets the value of the index property.
                         * 
                         */
                        public void setIndex(int value) {
                            this.index = value;
                        }

                        /**
                         * Gets the value of the marqueBallon property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getMarqueBallon() {
                            return marqueBallon;
                        }

                        /**
                         * Sets the value of the marqueBallon property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setMarqueBallon(String value) {
                            this.marqueBallon = value;
                        }

                        /**
                         * Gets the value of the denoCommBallon property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getDenoCommBallon() {
                            return denoCommBallon;
                        }

                        /**
                         * Sets the value of the denoCommBallon property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setDenoCommBallon(String value) {
                            this.denoCommBallon = value;
                        }

                        /**
                         * Gets the value of the marqueBallonAppCom property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getMarqueBallonAppCom() {
                            return marqueBallonAppCom;
                        }

                        /**
                         * Sets the value of the marqueBallonAppCom property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setMarqueBallonAppCom(String value) {
                            this.marqueBallonAppCom = value;
                        }

                        /**
                         * Gets the value of the denoCommBallonAppCom property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getDenoCommBallonAppCom() {
                            return denoCommBallonAppCom;
                        }

                        /**
                         * Sets the value of the denoCommBallonAppCom property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setDenoCommBallonAppCom(String value) {
                            this.denoCommBallonAppCom = value;
                        }

                    }

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
     *     &lt;extension base="{}RT_Data_Sortie_Projet">
     *       &lt;attribute name="HashKey" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;maxLength value="89"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SortieProjet
        extends RTDataSortieProjet
    {

        @XmlAttribute(name = "HashKey", required = true)
        protected String hashKey;

        /**
         * Gets the value of the hashKey property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHashKey() {
            return hashKey;
        }

        /**
         * Sets the value of the hashKey property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHashKey(String value) {
            this.hashKey = value;
        }

    }

}
