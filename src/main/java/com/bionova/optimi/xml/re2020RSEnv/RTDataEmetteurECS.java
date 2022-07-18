
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Emetteur_ECS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Emetteur_ECS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NB_rat_eviers_lavabos" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Nb_douches_bains_relies" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Nb_douches_bains_non_relies" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Nb_total_douches_bains" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Recuperateur_eg" type="{}RT_Data_Recuperateur_EG" minOccurs="0"/>
 *         &lt;element name="Rat_em_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nb_lgt_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nu_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="part_em_e_melangeurs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="part_em_e_mitigeur_thermo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="part_em_e_temporisateur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="app_ecs" type="{}E_Type_Appareil_Sanitaire"/>
 *         &lt;element name="corr_util" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="nb_maison_gr_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="i_ECS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Distribution_Groupe_ECS" type="{}RT_Data_Distribution_Groupe_ECS" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Emetteur_ECS", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataEmetteurECS {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "NB_rat_eviers_lavabos")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Integer nbRatEviersLavabos;
    @XmlElement(name = "Nb_douches_bains_relies")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Integer nbDouchesBainsRelies;
    @XmlElement(name = "Nb_douches_bains_non_relies")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Integer nbDouchesBainsNonRelies;
    @XmlElement(name = "Nb_total_douches_bains")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Integer nbTotalDouchesBains;
    @XmlElement(name = "Recuperateur_eg")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataRecuperateurEG recuperateurEg;
    @XmlElement(name = "Rat_em_e")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratEmE;
    @XmlElement(name = "nb_lgt_gr_em_e")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbLgtGrEmE;
    @XmlElement(name = "nu_gr_em_e")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nuGrEmE;
    @XmlElement(name = "part_em_e_melangeurs")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partEmEMelangeurs;
    @XmlElement(name = "part_em_e_mitigeur_thermo")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partEmEMitigeurThermo;
    @XmlElement(name = "part_em_e_temporisateur")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double partEmETemporisateur;
    @XmlElement(name = "app_ecs", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String appEcs;
    @XmlElement(name = "corr_util")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Double corrUtil;
    @XmlElement(name = "nb_maison_gr_e")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double nbMaisonGrE;
    @XmlElement(name = "i_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String iecs;
    @XmlElement(name = "Distribution_Groupe_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataDistributionGroupeECS distributionGroupeECS;

    /**
     * Gets the value of the index property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIndex(int value) {
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the nbRatEviersLavabos property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Integer getNBRatEviersLavabos() {
        return nbRatEviersLavabos;
    }

    /**
     * Sets the value of the nbRatEviersLavabos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNBRatEviersLavabos(Integer value) {
        this.nbRatEviersLavabos = value;
    }

    /**
     * Gets the value of the nbDouchesBainsRelies property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Integer getNbDouchesBainsRelies() {
        return nbDouchesBainsRelies;
    }

    /**
     * Sets the value of the nbDouchesBainsRelies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbDouchesBainsRelies(Integer value) {
        this.nbDouchesBainsRelies = value;
    }

    /**
     * Gets the value of the nbDouchesBainsNonRelies property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Integer getNbDouchesBainsNonRelies() {
        return nbDouchesBainsNonRelies;
    }

    /**
     * Sets the value of the nbDouchesBainsNonRelies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbDouchesBainsNonRelies(Integer value) {
        this.nbDouchesBainsNonRelies = value;
    }

    /**
     * Gets the value of the nbTotalDouchesBains property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Integer getNbTotalDouchesBains() {
        return nbTotalDouchesBains;
    }

    /**
     * Sets the value of the nbTotalDouchesBains property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbTotalDouchesBains(Integer value) {
        this.nbTotalDouchesBains = value;
    }

    /**
     * Gets the value of the recuperateurEg property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataRecuperateurEG }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataRecuperateurEG getRecuperateurEg() {
        return recuperateurEg;
    }

    /**
     * Sets the value of the recuperateurEg property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataRecuperateurEG }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRecuperateurEg(RTDataRecuperateurEG value) {
        this.recuperateurEg = value;
    }

    /**
     * Gets the value of the ratEmE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatEmE() {
        return ratEmE;
    }

    /**
     * Sets the value of the ratEmE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatEmE(double value) {
        this.ratEmE = value;
    }

    /**
     * Gets the value of the nbLgtGrEmE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbLgtGrEmE() {
        return nbLgtGrEmE;
    }

    /**
     * Sets the value of the nbLgtGrEmE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbLgtGrEmE(double value) {
        this.nbLgtGrEmE = value;
    }

    /**
     * Gets the value of the nuGrEmE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNuGrEmE() {
        return nuGrEmE;
    }

    /**
     * Sets the value of the nuGrEmE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNuGrEmE(double value) {
        this.nuGrEmE = value;
    }

    /**
     * Gets the value of the partEmEMelangeurs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartEmEMelangeurs() {
        return partEmEMelangeurs;
    }

    /**
     * Sets the value of the partEmEMelangeurs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartEmEMelangeurs(double value) {
        this.partEmEMelangeurs = value;
    }

    /**
     * Gets the value of the partEmEMitigeurThermo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartEmEMitigeurThermo() {
        return partEmEMitigeurThermo;
    }

    /**
     * Sets the value of the partEmEMitigeurThermo property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartEmEMitigeurThermo(double value) {
        this.partEmEMitigeurThermo = value;
    }

    /**
     * Gets the value of the partEmETemporisateur property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPartEmETemporisateur() {
        return partEmETemporisateur;
    }

    /**
     * Sets the value of the partEmETemporisateur property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPartEmETemporisateur(double value) {
        this.partEmETemporisateur = value;
    }

    /**
     * Gets the value of the appEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getAppEcs() {
        return appEcs;
    }

    /**
     * Sets the value of the appEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAppEcs(String value) {
        this.appEcs = value;
    }

    /**
     * Gets the value of the corrUtil property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Double getCorrUtil() {
        return corrUtil;
    }

    /**
     * Sets the value of the corrUtil property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCorrUtil(Double value) {
        this.corrUtil = value;
    }

    /**
     * Gets the value of the nbMaisonGrE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getNbMaisonGrE() {
        return nbMaisonGrE;
    }

    /**
     * Sets the value of the nbMaisonGrE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNbMaisonGrE(double value) {
        this.nbMaisonGrE = value;
    }

    /**
     * Gets the value of the iecs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIECS() {
        return iecs;
    }

    /**
     * Sets the value of the iecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIECS(String value) {
        this.iecs = value;
    }

    /**
     * Gets the value of the distributionGroupeECS property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataDistributionGroupeECS }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public RTDataDistributionGroupeECS getDistributionGroupeECS() {
        return distributionGroupeECS;
    }

    /**
     * Sets the value of the distributionGroupeECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataDistributionGroupeECS }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionGroupeECS(RTDataDistributionGroupeECS value) {
        this.distributionGroupeECS = value;
    }

}
