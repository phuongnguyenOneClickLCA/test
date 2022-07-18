
package com.bionova.optimi.xml.fecRSEnv;

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
 *         &lt;element name="Rat_em_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nb_lgt_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nu_gr_em_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="id_corr_em_e" type="{}E_Type_Calcul"/>
 *         &lt;element name="part_em_e_melangeurs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="part_em_e_mitigeur_thermo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="part_em_e_temporisateur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="app_ecs" type="{}E_Type_Appareil_Sanitaire"/>
 *         &lt;element name="corr_util" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nb_maison_gr_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
public class RTDataEmetteurECS {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rat_em_e")
    protected double ratEmE;
    @XmlElement(name = "nb_lgt_gr_em_e")
    protected double nbLgtGrEmE;
    @XmlElement(name = "nu_gr_em_e")
    protected double nuGrEmE;
    @XmlElement(name = "id_corr_em_e", required = true)
    protected String idCorrEmE;
    @XmlElement(name = "part_em_e_melangeurs")
    protected double partEmEMelangeurs;
    @XmlElement(name = "part_em_e_mitigeur_thermo")
    protected double partEmEMitigeurThermo;
    @XmlElement(name = "part_em_e_temporisateur")
    protected double partEmETemporisateur;
    @XmlElement(name = "app_ecs", required = true)
    protected String appEcs;
    @XmlElement(name = "corr_util")
    protected double corrUtil;
    @XmlElement(name = "nb_maison_gr_e")
    protected double nbMaisonGrE;
    @XmlElement(name = "Distribution_Groupe_ECS")
    protected RTDataDistributionGroupeECS distributionGroupeECS;

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the ratEmE property.
     * 
     */
    public double getRatEmE() {
        return ratEmE;
    }

    /**
     * Sets the value of the ratEmE property.
     * 
     */
    public void setRatEmE(double value) {
        this.ratEmE = value;
    }

    /**
     * Gets the value of the nbLgtGrEmE property.
     * 
     */
    public double getNbLgtGrEmE() {
        return nbLgtGrEmE;
    }

    /**
     * Sets the value of the nbLgtGrEmE property.
     * 
     */
    public void setNbLgtGrEmE(double value) {
        this.nbLgtGrEmE = value;
    }

    /**
     * Gets the value of the nuGrEmE property.
     * 
     */
    public double getNuGrEmE() {
        return nuGrEmE;
    }

    /**
     * Sets the value of the nuGrEmE property.
     * 
     */
    public void setNuGrEmE(double value) {
        this.nuGrEmE = value;
    }

    /**
     * Gets the value of the idCorrEmE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCorrEmE() {
        return idCorrEmE;
    }

    /**
     * Sets the value of the idCorrEmE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCorrEmE(String value) {
        this.idCorrEmE = value;
    }

    /**
     * Gets the value of the partEmEMelangeurs property.
     * 
     */
    public double getPartEmEMelangeurs() {
        return partEmEMelangeurs;
    }

    /**
     * Sets the value of the partEmEMelangeurs property.
     * 
     */
    public void setPartEmEMelangeurs(double value) {
        this.partEmEMelangeurs = value;
    }

    /**
     * Gets the value of the partEmEMitigeurThermo property.
     * 
     */
    public double getPartEmEMitigeurThermo() {
        return partEmEMitigeurThermo;
    }

    /**
     * Sets the value of the partEmEMitigeurThermo property.
     * 
     */
    public void setPartEmEMitigeurThermo(double value) {
        this.partEmEMitigeurThermo = value;
    }

    /**
     * Gets the value of the partEmETemporisateur property.
     * 
     */
    public double getPartEmETemporisateur() {
        return partEmETemporisateur;
    }

    /**
     * Sets the value of the partEmETemporisateur property.
     * 
     */
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
    public void setAppEcs(String value) {
        this.appEcs = value;
    }

    /**
     * Gets the value of the corrUtil property.
     * 
     */
    public double getCorrUtil() {
        return corrUtil;
    }

    /**
     * Sets the value of the corrUtil property.
     * 
     */
    public void setCorrUtil(double value) {
        this.corrUtil = value;
    }

    /**
     * Gets the value of the nbMaisonGrE property.
     * 
     */
    public double getNbMaisonGrE() {
        return nbMaisonGrE;
    }

    /**
     * Sets the value of the nbMaisonGrE property.
     * 
     */
    public void setNbMaisonGrE(double value) {
        this.nbMaisonGrE = value;
    }

    /**
     * Gets the value of the distributionGroupeECS property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataDistributionGroupeECS }
     *     
     */
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
    public void setDistributionGroupeECS(RTDataDistributionGroupeECS value) {
        this.distributionGroupeECS = value;
    }

}
