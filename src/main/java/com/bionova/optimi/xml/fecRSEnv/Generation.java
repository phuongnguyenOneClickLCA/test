
package com.bionova.optimi.xml.fecRSEnv;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
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
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gene_commune">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="id_bat_commun">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="generateur_collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="generateur" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="marque_generateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="deno_comm_gene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="categorie" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="poste_conso" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="origine" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="type_prod_chauffage" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type_prod_ECS" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="matrice" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="certification_matrice" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                             &lt;element name="source_data_P" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="2"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="P_pleine_charge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="P_auxi_amont" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
 *         &lt;element name="ECS_types" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="id" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;maxInclusive value="7"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="type_solaire_thermique" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;maxInclusive value="9"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="production_stockage_collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="production_stockage" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                             &lt;element name="marque_generateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="deno_comm_gene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="type_energie_base" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="8"/>
 *                                   &lt;minInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="type_energie_appoint" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="3"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="certification" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="marque_ballon_appoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="deno_comm_ballon_appoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="certification_appoint" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                                   &lt;maxInclusive value="4"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Pmax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                             &lt;element name="source_ballon_base_collection" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence minOccurs="0">
 *                                       &lt;element name="source_ballon_base" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                 &lt;element name="marque_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="deno_comm_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="source_ballon_appoint_collection" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                                       &lt;element name="source_ballon_appoint_effet_joule" type="{}t_deno_ballons" minOccurs="0"/>
 *                                       &lt;element name="source_ballon_appoint_combustion" type="{}t_deno_ballons" minOccurs="0"/>
 *                                       &lt;element name="source_ballon_appoint_reseau_fourniture" type="{}t_deno_ballons" minOccurs="0"/>
 *                                     &lt;/sequence>
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
    "index",
    "name",
    "geneCommune",
    "idBatCommun",
    "generateurCollection",
    "ecsTypes",
    "typeSolaireThermique",
    "productionStockageCollection"
})
@XmlRootElement(name = "generation")
public class Generation {

    @XmlElement(name = "Index", required = true)
    protected BigInteger index;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "gene_commune")
    protected int geneCommune;
    @XmlElement(name = "id_bat_commun", required = true)
    protected Generation.IdBatCommun idBatCommun;
    @XmlElement(name = "generateur_collection")
    protected Generation.GenerateurCollection generateurCollection;
    @XmlElement(name = "ECS_types")
    protected Generation.ECSTypes ecsTypes;
    @XmlElement(name = "type_solaire_thermique", defaultValue = "0")
    protected BigInteger typeSolaireThermique;
    @XmlElement(name = "production_stockage_collection")
    protected Generation.ProductionStockageCollection productionStockageCollection;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the geneCommune property.
     * 
     */
    public int getGeneCommune() {
        return geneCommune;
    }

    /**
     * Sets the value of the geneCommune property.
     * 
     */
    public void setGeneCommune(int value) {
        this.geneCommune = value;
    }

    /**
     * Gets the value of the idBatCommun property.
     * 
     * @return
     *     possible object is
     *     {@link Generation.IdBatCommun }
     *     
     */
    public Generation.IdBatCommun getIdBatCommun() {
        return idBatCommun;
    }

    /**
     * Sets the value of the idBatCommun property.
     * 
     * @param value
     *     allowed object is
     *     {@link Generation.IdBatCommun }
     *     
     */
    public void setIdBatCommun(Generation.IdBatCommun value) {
        this.idBatCommun = value;
    }

    /**
     * Gets the value of the generateurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Generation.GenerateurCollection }
     *     
     */
    public Generation.GenerateurCollection getGenerateurCollection() {
        return generateurCollection;
    }

    /**
     * Sets the value of the generateurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Generation.GenerateurCollection }
     *     
     */
    public void setGenerateurCollection(Generation.GenerateurCollection value) {
        this.generateurCollection = value;
    }

    /**
     * Gets the value of the ecsTypes property.
     * 
     * @return
     *     possible object is
     *     {@link Generation.ECSTypes }
     *     
     */
    public Generation.ECSTypes getECSTypes() {
        return ecsTypes;
    }

    /**
     * Sets the value of the ecsTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Generation.ECSTypes }
     *     
     */
    public void setECSTypes(Generation.ECSTypes value) {
        this.ecsTypes = value;
    }

    /**
     * Gets the value of the typeSolaireThermique property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTypeSolaireThermique() {
        return typeSolaireThermique;
    }

    /**
     * Sets the value of the typeSolaireThermique property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTypeSolaireThermique(BigInteger value) {
        this.typeSolaireThermique = value;
    }

    /**
     * Gets the value of the productionStockageCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Generation.ProductionStockageCollection }
     *     
     */
    public Generation.ProductionStockageCollection getProductionStockageCollection() {
        return productionStockageCollection;
    }

    /**
     * Sets the value of the productionStockageCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Generation.ProductionStockageCollection }
     *     
     */
    public void setProductionStockageCollection(Generation.ProductionStockageCollection value) {
        this.productionStockageCollection = value;
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
     *         &lt;element name="id" maxOccurs="unbounded" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;maxInclusive value="7"/>
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
        "id"
    })
    public static class ECSTypes {

        @XmlElement(defaultValue = "0")
        protected List<BigInteger> id;

        /**
         * Gets the value of the id property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the id property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BigInteger }
         * 
         * 
         */
        public List<BigInteger> getId() {
            if (id == null) {
                id = new ArrayList<BigInteger>();
            }
            return this.id;
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
     *         &lt;element name="generateur" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="marque_generateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="deno_comm_gene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="categorie" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="poste_conso" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="origine" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="type_prod_chauffage" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type_prod_ECS" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="matrice" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="certification_matrice" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="source_data_P" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="2"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="P_pleine_charge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="P_auxi_amont" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
        "generateur"
    })
    public static class GenerateurCollection {

        protected List<Generation.GenerateurCollection.Generateur> generateur;

        /**
         * Gets the value of the generateur property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the generateur property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGenerateur().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Generation.GenerateurCollection.Generateur }
         * 
         * 
         */
        public List<Generation.GenerateurCollection.Generateur> getGenerateur() {
            if (generateur == null) {
                generateur = new ArrayList<Generation.GenerateurCollection.Generateur>();
            }
            return this.generateur;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="marque_generateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="deno_comm_gene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="categorie" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="poste_conso" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="origine" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="type_prod_chauffage" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type_prod_ECS" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="matrice" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="certification_matrice" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="source_data_P" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="2"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="P_pleine_charge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="P_auxi_amont" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
        public static class Generateur {

            @XmlElement(name = "Index", required = true)
            protected BigInteger index;
            @XmlElement(name = "marque_generateur")
            protected String marqueGenerateur;
            @XmlElement(name = "deno_comm_gene")
            protected String denoCommGene;
            @XmlElement(defaultValue = "0")
            protected BigInteger categorie;
            @XmlElement(name = "poste_conso", defaultValue = "0")
            protected BigInteger posteConso;
            @XmlElement(defaultValue = "0")
            protected BigInteger origine;
            @XmlElement(name = "type_prod_chauffage", defaultValue = "0")
            protected Integer typeProdChauffage;
            @XmlElement(name = "type_prod_ECS", defaultValue = "0")
            protected Integer typeProdECS;
            @XmlElement(defaultValue = "0")
            protected BigInteger matrice;
            @XmlElement(name = "certification_matrice", defaultValue = "0")
            protected BigInteger certificationMatrice;
            @XmlElement(name = "source_data_P", defaultValue = "0")
            protected BigInteger sourceDataP;
            @XmlElement(name = "P_pleine_charge", defaultValue = "0")
            protected BigDecimal pPleineCharge;
            @XmlElement(name = "P_auxi_amont", defaultValue = "0")
            protected BigDecimal pAuxiAmont;
            @XmlElement(defaultValue = "0")
            protected BigInteger certification;

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
             * Gets the value of the marqueGenerateur property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMarqueGenerateur() {
                return marqueGenerateur;
            }

            /**
             * Sets the value of the marqueGenerateur property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMarqueGenerateur(String value) {
                this.marqueGenerateur = value;
            }

            /**
             * Gets the value of the denoCommGene property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDenoCommGene() {
                return denoCommGene;
            }

            /**
             * Sets the value of the denoCommGene property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDenoCommGene(String value) {
                this.denoCommGene = value;
            }

            /**
             * Gets the value of the categorie property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCategorie() {
                return categorie;
            }

            /**
             * Sets the value of the categorie property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCategorie(BigInteger value) {
                this.categorie = value;
            }

            /**
             * Gets the value of the posteConso property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getPosteConso() {
                return posteConso;
            }

            /**
             * Sets the value of the posteConso property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setPosteConso(BigInteger value) {
                this.posteConso = value;
            }

            /**
             * Gets the value of the origine property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getOrigine() {
                return origine;
            }

            /**
             * Sets the value of the origine property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setOrigine(BigInteger value) {
                this.origine = value;
            }

            /**
             * Gets the value of the typeProdChauffage property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getTypeProdChauffage() {
                return typeProdChauffage;
            }

            /**
             * Sets the value of the typeProdChauffage property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setTypeProdChauffage(Integer value) {
                this.typeProdChauffage = value;
            }

            /**
             * Gets the value of the typeProdECS property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getTypeProdECS() {
                return typeProdECS;
            }

            /**
             * Sets the value of the typeProdECS property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setTypeProdECS(Integer value) {
                this.typeProdECS = value;
            }

            /**
             * Gets the value of the matrice property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMatrice() {
                return matrice;
            }

            /**
             * Sets the value of the matrice property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMatrice(BigInteger value) {
                this.matrice = value;
            }

            /**
             * Gets the value of the certificationMatrice property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCertificationMatrice() {
                return certificationMatrice;
            }

            /**
             * Sets the value of the certificationMatrice property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCertificationMatrice(BigInteger value) {
                this.certificationMatrice = value;
            }

            /**
             * Gets the value of the sourceDataP property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getSourceDataP() {
                return sourceDataP;
            }

            /**
             * Sets the value of the sourceDataP property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setSourceDataP(BigInteger value) {
                this.sourceDataP = value;
            }

            /**
             * Gets the value of the pPleineCharge property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPPleineCharge() {
                return pPleineCharge;
            }

            /**
             * Sets the value of the pPleineCharge property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPPleineCharge(BigDecimal value) {
                this.pPleineCharge = value;
            }

            /**
             * Gets the value of the pAuxiAmont property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPAuxiAmont() {
                return pAuxiAmont;
            }

            /**
             * Sets the value of the pAuxiAmont property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPAuxiAmont(BigDecimal value) {
                this.pAuxiAmont = value;
            }

            /**
             * Gets the value of the certification property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCertification() {
                return certification;
            }

            /**
             * Sets the value of the certification property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCertification(BigInteger value) {
                this.certification = value;
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
     *       &lt;sequence>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded"/>
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
        "id"
    })
    public static class IdBatCommun {

        @XmlElement(required = true)
        protected List<BigInteger> id;

        /**
         * Gets the value of the id property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the id property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getId().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BigInteger }
         * 
         * 
         */
        public List<BigInteger> getId() {
            if (id == null) {
                id = new ArrayList<BigInteger>();
            }
            return this.id;
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
     *         &lt;element name="production_stockage" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                   &lt;element name="marque_generateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="deno_comm_gene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="type_energie_base" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="8"/>
     *                         &lt;minInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="type_energie_appoint" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="3"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="certification" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="marque_ballon_appoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="deno_comm_ballon_appoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="certification_appoint" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *                         &lt;maxInclusive value="4"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Pmax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *                   &lt;element name="source_ballon_base_collection" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence minOccurs="0">
     *                             &lt;element name="source_ballon_base" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                       &lt;element name="marque_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="deno_comm_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
     *                   &lt;element name="source_ballon_appoint_collection" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *                             &lt;element name="source_ballon_appoint_effet_joule" type="{}t_deno_ballons" minOccurs="0"/>
     *                             &lt;element name="source_ballon_appoint_combustion" type="{}t_deno_ballons" minOccurs="0"/>
     *                             &lt;element name="source_ballon_appoint_reseau_fourniture" type="{}t_deno_ballons" minOccurs="0"/>
     *                           &lt;/sequence>
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
        "productionStockage"
    })
    public static class ProductionStockageCollection {

        @XmlElement(name = "production_stockage")
        protected List<Generation.ProductionStockageCollection.ProductionStockage> productionStockage;

        /**
         * Gets the value of the productionStockage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the productionStockage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProductionStockage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Generation.ProductionStockageCollection.ProductionStockage }
         * 
         * 
         */
        public List<Generation.ProductionStockageCollection.ProductionStockage> getProductionStockage() {
            if (productionStockage == null) {
                productionStockage = new ArrayList<Generation.ProductionStockageCollection.ProductionStockage>();
            }
            return this.productionStockage;
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
         *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *         &lt;element name="marque_generateur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="deno_comm_gene" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="marque_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="deno_comm_ballon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="type_energie_base" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="8"/>
         *               &lt;minInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="type_energie_appoint" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="3"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="certification" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="marque_ballon_appoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="deno_comm_ballon_appoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="certification_appoint" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
         *               &lt;maxInclusive value="4"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Pmax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
         *         &lt;element name="source_ballon_base_collection" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence minOccurs="0">
         *                   &lt;element name="source_ballon_base" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                             &lt;element name="marque_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="deno_comm_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
         *         &lt;element name="source_ballon_appoint_collection" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
         *                   &lt;element name="source_ballon_appoint_effet_joule" type="{}t_deno_ballons" minOccurs="0"/>
         *                   &lt;element name="source_ballon_appoint_combustion" type="{}t_deno_ballons" minOccurs="0"/>
         *                   &lt;element name="source_ballon_appoint_reseau_fourniture" type="{}t_deno_ballons" minOccurs="0"/>
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
        @XmlType(name = "", propOrder = {

        })
        public static class ProductionStockage {

            @XmlElement(name = "Index", required = true)
            protected BigInteger index;
            @XmlElement(name = "marque_generateur")
            protected String marqueGenerateur;
            @XmlElement(name = "deno_comm_gene")
            protected String denoCommGene;
            @XmlElement(name = "marque_ballon")
            protected String marqueBallon;
            @XmlElement(name = "deno_comm_ballon")
            protected String denoCommBallon;
            @XmlElement(name = "type_energie_base")
            protected Integer typeEnergieBase;
            @XmlElement(name = "type_energie_appoint", defaultValue = "0")
            protected BigInteger typeEnergieAppoint;
            @XmlElement(defaultValue = "0")
            protected BigInteger certification;
            @XmlElement(name = "marque_ballon_appoint")
            protected String marqueBallonAppoint;
            @XmlElement(name = "deno_comm_ballon_appoint")
            protected String denoCommBallonAppoint;
            @XmlElement(name = "certification_appoint", defaultValue = "0")
            protected BigInteger certificationAppoint;
            @XmlElement(name = "Pmax", defaultValue = "0")
            protected BigDecimal pmax;
            @XmlElement(name = "source_ballon_base_collection")
            protected Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection sourceBallonBaseCollection;
            @XmlElement(name = "source_ballon_appoint_collection")
            protected Generation.ProductionStockageCollection.ProductionStockage.SourceBallonAppointCollection sourceBallonAppointCollection;

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
             * Gets the value of the marqueGenerateur property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMarqueGenerateur() {
                return marqueGenerateur;
            }

            /**
             * Sets the value of the marqueGenerateur property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMarqueGenerateur(String value) {
                this.marqueGenerateur = value;
            }

            /**
             * Gets the value of the denoCommGene property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDenoCommGene() {
                return denoCommGene;
            }

            /**
             * Sets the value of the denoCommGene property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDenoCommGene(String value) {
                this.denoCommGene = value;
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
             * Gets the value of the typeEnergieBase property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getTypeEnergieBase() {
                return typeEnergieBase;
            }

            /**
             * Sets the value of the typeEnergieBase property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setTypeEnergieBase(Integer value) {
                this.typeEnergieBase = value;
            }

            /**
             * Gets the value of the typeEnergieAppoint property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTypeEnergieAppoint() {
                return typeEnergieAppoint;
            }

            /**
             * Sets the value of the typeEnergieAppoint property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTypeEnergieAppoint(BigInteger value) {
                this.typeEnergieAppoint = value;
            }

            /**
             * Gets the value of the certification property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCertification() {
                return certification;
            }

            /**
             * Sets the value of the certification property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCertification(BigInteger value) {
                this.certification = value;
            }

            /**
             * Gets the value of the marqueBallonAppoint property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMarqueBallonAppoint() {
                return marqueBallonAppoint;
            }

            /**
             * Sets the value of the marqueBallonAppoint property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMarqueBallonAppoint(String value) {
                this.marqueBallonAppoint = value;
            }

            /**
             * Gets the value of the denoCommBallonAppoint property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDenoCommBallonAppoint() {
                return denoCommBallonAppoint;
            }

            /**
             * Sets the value of the denoCommBallonAppoint property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDenoCommBallonAppoint(String value) {
                this.denoCommBallonAppoint = value;
            }

            /**
             * Gets the value of the certificationAppoint property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCertificationAppoint() {
                return certificationAppoint;
            }

            /**
             * Sets the value of the certificationAppoint property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCertificationAppoint(BigInteger value) {
                this.certificationAppoint = value;
            }

            /**
             * Gets the value of the pmax property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPmax() {
                return pmax;
            }

            /**
             * Sets the value of the pmax property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPmax(BigDecimal value) {
                this.pmax = value;
            }

            /**
             * Gets the value of the sourceBallonBaseCollection property.
             * 
             * @return
             *     possible object is
             *     {@link Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection }
             *     
             */
            public Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection getSourceBallonBaseCollection() {
                return sourceBallonBaseCollection;
            }

            /**
             * Sets the value of the sourceBallonBaseCollection property.
             * 
             * @param value
             *     allowed object is
             *     {@link Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection }
             *     
             */
            public void setSourceBallonBaseCollection(Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection value) {
                this.sourceBallonBaseCollection = value;
            }

            /**
             * Gets the value of the sourceBallonAppointCollection property.
             * 
             * @return
             *     possible object is
             *     {@link Generation.ProductionStockageCollection.ProductionStockage.SourceBallonAppointCollection }
             *     
             */
            public Generation.ProductionStockageCollection.ProductionStockage.SourceBallonAppointCollection getSourceBallonAppointCollection() {
                return sourceBallonAppointCollection;
            }

            /**
             * Sets the value of the sourceBallonAppointCollection property.
             * 
             * @param value
             *     allowed object is
             *     {@link Generation.ProductionStockageCollection.ProductionStockage.SourceBallonAppointCollection }
             *     
             */
            public void setSourceBallonAppointCollection(Generation.ProductionStockageCollection.ProductionStockage.SourceBallonAppointCollection value) {
                this.sourceBallonAppointCollection = value;
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
             *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
             *         &lt;element name="source_ballon_appoint_effet_joule" type="{}t_deno_ballons" minOccurs="0"/>
             *         &lt;element name="source_ballon_appoint_combustion" type="{}t_deno_ballons" minOccurs="0"/>
             *         &lt;element name="source_ballon_appoint_reseau_fourniture" type="{}t_deno_ballons" minOccurs="0"/>
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
                "sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture"
            })
            public static class SourceBallonAppointCollection {

                @XmlElementRefs({
                    @XmlElementRef(name = "source_ballon_appoint_combustion", type = JAXBElement.class, required = false),
                    @XmlElementRef(name = "source_ballon_appoint_effet_joule", type = JAXBElement.class, required = false),
                    @XmlElementRef(name = "source_ballon_appoint_reseau_fourniture", type = JAXBElement.class, required = false)
                })
                protected List<JAXBElement<TDenoBallons>> sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture;

                /**
                 * Gets the value of the sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link JAXBElement }{@code <}{@link TDenoBallons }{@code >}
                 * {@link JAXBElement }{@code <}{@link TDenoBallons }{@code >}
                 * {@link JAXBElement }{@code <}{@link TDenoBallons }{@code >}
                 * 
                 * 
                 */
                public List<JAXBElement<TDenoBallons>> getSourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture() {
                    if (sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture == null) {
                        sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture = new ArrayList<JAXBElement<TDenoBallons>>();
                    }
                    return this.sourceBallonAppointEffetJouleAndSourceBallonAppointCombustionAndSourceBallonAppointReseauFourniture;
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
             *         &lt;element name="source_ballon_base" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                   &lt;element name="marque_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="deno_comm_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
                "sourceBallonBase"
            })
            public static class SourceBallonBaseCollection {

                @XmlElement(name = "source_ballon_base")
                protected List<Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection.SourceBallonBase> sourceBallonBase;

                /**
                 * Gets the value of the sourceBallonBase property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the sourceBallonBase property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSourceBallonBase().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection.SourceBallonBase }
                 * 
                 * 
                 */
                public List<Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection.SourceBallonBase> getSourceBallonBase() {
                    if (sourceBallonBase == null) {
                        sourceBallonBase = new ArrayList<Generation.ProductionStockageCollection.ProductionStockage.SourceBallonBaseCollection.SourceBallonBase>();
                    }
                    return this.sourceBallonBase;
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
                 *         &lt;element name="marque_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="deno_comm_capteurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="certification" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
                    "marqueCapteurs",
                    "denoCommCapteurs",
                    "certification"
                })
                public static class SourceBallonBase {

                    @XmlElement(name = "Index", required = true)
                    protected BigInteger index;
                    @XmlElement(name = "marque_capteurs")
                    protected String marqueCapteurs;
                    @XmlElement(name = "deno_comm_capteurs")
                    protected String denoCommCapteurs;
                    @XmlElement(defaultValue = "0")
                    protected BigInteger certification;

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
                     * Gets the value of the marqueCapteurs property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMarqueCapteurs() {
                        return marqueCapteurs;
                    }

                    /**
                     * Sets the value of the marqueCapteurs property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMarqueCapteurs(String value) {
                        this.marqueCapteurs = value;
                    }

                    /**
                     * Gets the value of the denoCommCapteurs property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDenoCommCapteurs() {
                        return denoCommCapteurs;
                    }

                    /**
                     * Sets the value of the denoCommCapteurs property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDenoCommCapteurs(String value) {
                        this.denoCommCapteurs = value;
                    }

                    /**
                     * Gets the value of the certification property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigInteger }
                     *     
                     */
                    public BigInteger getCertification() {
                        return certification;
                    }

                    /**
                     * Sets the value of the certification property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigInteger }
                     *     
                     */
                    public void setCertification(BigInteger value) {
                        this.certification = value;
                    }

                }

            }

        }

    }

}
