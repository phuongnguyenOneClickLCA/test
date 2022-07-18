
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Sensibilite_Batiment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Sensibilite_Batiment">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Permea_MI_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Permea_MI_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Permea_LC_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Permea_LC_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Permea_Tertiaire_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Permea_Tertiaire_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Sp_Aug_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Sp_Aug_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Sp_Dim_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Sp_Dim_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Ap_Aug_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Ap_Aug_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Ap_Dim_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Apport_Sw_Ap_Dim_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Facteur_Tt_Lum_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Facteur_Tt_Lum_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeurs_U_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeurs_U_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Usp_Uap_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Usp_Uap_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeurs_Ponts_Th_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeurs_Ponts_Th_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Inertie_Tres_Legere_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Inertie_Tres_Legere_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Inertie_Tres_Lourde_Bbio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Inertie_Tres_Lourde_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ventilation_Classe_B_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ventilation_Amelio_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ventilation_Pena_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Varia_Spa_Chaud_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reseau_Distrib_Chauff_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Varia_Spa_Froid_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reseau_Distrib_Froid_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eclairage_Locaux_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ECS_Perte_UAs_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reseau_ECS_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Surf_Capteur_Sol_Aug_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Surf_Capteur_Sol_Dim_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep_Ecl" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep_AuxS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Pessimiste_Cep_AuxV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep_Ecl" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep_AuxS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Scenario_Optimiste_Cep_AuxV" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ss_Apports_Sol_Bbio_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ss_Apports_Sol_Bbio_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ss_Apports_Sol_Bbio_Ecl" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ss_Apports_Sol_Lum_Bbio_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ss_Apports_Sol_Lum_Bbio_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ss_Apports_Sol_Lum_Bbio_Ecl" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Sensibilite_Zone_Collection" type="{}ArrayOfRT_Data_Sortie_Sensibilite_Zone" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Sensibilite_Batiment", propOrder = {
    "permeaMIBbio",
    "permeaMICep",
    "permeaLCBbio",
    "permeaLCCep",
    "permeaTertiaireBbio",
    "permeaTertiaireCep",
    "apportSwSpAugBbio",
    "apportSwSpAugCep",
    "apportSwSpDimBbio",
    "apportSwSpDimCep",
    "apportSwApAugBbio",
    "apportSwApAugCep",
    "apportSwApDimBbio",
    "apportSwApDimCep",
    "facteurTtLumBbio",
    "facteurTtLumCep",
    "valeursUBbio",
    "valeursUCep",
    "valeurUspUapBbio",
    "valeurUspUapCep",
    "valeursPontsThBbio",
    "valeursPontsThCep",
    "inertieTresLegereBbio",
    "inertieTresLegereCep",
    "inertieTresLourdeBbio",
    "inertieTresLourdeCep",
    "ventilationClasseBCep",
    "ventilationAmelioCep",
    "ventilationPenaCep",
    "classeVariaSpaChaudCep",
    "reseauDistribChauffCep",
    "classeVariaSpaFroidCep",
    "reseauDistribFroidCep",
    "eclairageLocauxCep",
    "ecsPerteUAsCep",
    "reseauECSCep",
    "surfCapteurSolAugCep",
    "surfCapteurSolDimCep",
    "scenarioPessimisteCep",
    "scenarioPessimisteCepCh",
    "scenarioPessimisteCepFr",
    "scenarioPessimisteCepECS",
    "scenarioPessimisteCepEcl",
    "scenarioPessimisteCepAuxS",
    "scenarioPessimisteCepAuxV",
    "scenarioOptimisteCep",
    "scenarioOptimisteCepCh",
    "scenarioOptimisteCepFr",
    "scenarioOptimisteCepECS",
    "scenarioOptimisteCepEcl",
    "scenarioOptimisteCepAuxS",
    "scenarioOptimisteCepAuxV",
    "ssApportsSolBbioCh",
    "ssApportsSolBbioFr",
    "ssApportsSolBbioEcl",
    "ssApportsSolLumBbioCh",
    "ssApportsSolLumBbioFr",
    "ssApportsSolLumBbioEcl",
    "sortieSensibiliteZoneCollection"
})
public class RTDataSortieSensibiliteBatiment
    extends RTDataSortieBase
{

    @XmlElement(name = "Permea_MI_Bbio")
    protected double permeaMIBbio;
    @XmlElement(name = "Permea_MI_Cep")
    protected double permeaMICep;
    @XmlElement(name = "Permea_LC_Bbio")
    protected double permeaLCBbio;
    @XmlElement(name = "Permea_LC_Cep")
    protected double permeaLCCep;
    @XmlElement(name = "Permea_Tertiaire_Bbio")
    protected double permeaTertiaireBbio;
    @XmlElement(name = "Permea_Tertiaire_Cep")
    protected double permeaTertiaireCep;
    @XmlElement(name = "Apport_Sw_Sp_Aug_Bbio")
    protected double apportSwSpAugBbio;
    @XmlElement(name = "Apport_Sw_Sp_Aug_Cep")
    protected double apportSwSpAugCep;
    @XmlElement(name = "Apport_Sw_Sp_Dim_Bbio")
    protected double apportSwSpDimBbio;
    @XmlElement(name = "Apport_Sw_Sp_Dim_Cep")
    protected double apportSwSpDimCep;
    @XmlElement(name = "Apport_Sw_Ap_Aug_Bbio")
    protected double apportSwApAugBbio;
    @XmlElement(name = "Apport_Sw_Ap_Aug_Cep")
    protected double apportSwApAugCep;
    @XmlElement(name = "Apport_Sw_Ap_Dim_Bbio")
    protected double apportSwApDimBbio;
    @XmlElement(name = "Apport_Sw_Ap_Dim_Cep")
    protected double apportSwApDimCep;
    @XmlElement(name = "Facteur_Tt_Lum_Bbio")
    protected double facteurTtLumBbio;
    @XmlElement(name = "Facteur_Tt_Lum_Cep")
    protected double facteurTtLumCep;
    @XmlElement(name = "Valeurs_U_Bbio")
    protected double valeursUBbio;
    @XmlElement(name = "Valeurs_U_Cep")
    protected double valeursUCep;
    @XmlElement(name = "Valeur_Usp_Uap_Bbio")
    protected double valeurUspUapBbio;
    @XmlElement(name = "Valeur_Usp_Uap_Cep")
    protected double valeurUspUapCep;
    @XmlElement(name = "Valeurs_Ponts_Th_Bbio")
    protected double valeursPontsThBbio;
    @XmlElement(name = "Valeurs_Ponts_Th_Cep")
    protected double valeursPontsThCep;
    @XmlElement(name = "Inertie_Tres_Legere_Bbio")
    protected double inertieTresLegereBbio;
    @XmlElement(name = "Inertie_Tres_Legere_Cep")
    protected double inertieTresLegereCep;
    @XmlElement(name = "Inertie_Tres_Lourde_Bbio")
    protected double inertieTresLourdeBbio;
    @XmlElement(name = "Inertie_Tres_Lourde_Cep")
    protected double inertieTresLourdeCep;
    @XmlElement(name = "Ventilation_Classe_B_Cep")
    protected double ventilationClasseBCep;
    @XmlElement(name = "Ventilation_Amelio_Cep")
    protected double ventilationAmelioCep;
    @XmlElement(name = "Ventilation_Pena_Cep")
    protected double ventilationPenaCep;
    @XmlElement(name = "Classe_Varia_Spa_Chaud_Cep")
    protected double classeVariaSpaChaudCep;
    @XmlElement(name = "Reseau_Distrib_Chauff_Cep")
    protected double reseauDistribChauffCep;
    @XmlElement(name = "Classe_Varia_Spa_Froid_Cep")
    protected double classeVariaSpaFroidCep;
    @XmlElement(name = "Reseau_Distrib_Froid_Cep")
    protected double reseauDistribFroidCep;
    @XmlElement(name = "Eclairage_Locaux_Cep")
    protected double eclairageLocauxCep;
    @XmlElement(name = "ECS_Perte_UAs_Cep")
    protected double ecsPerteUAsCep;
    @XmlElement(name = "Reseau_ECS_Cep")
    protected double reseauECSCep;
    @XmlElement(name = "Surf_Capteur_Sol_Aug_Cep")
    protected double surfCapteurSolAugCep;
    @XmlElement(name = "Surf_Capteur_Sol_Dim_Cep")
    protected double surfCapteurSolDimCep;
    @XmlElement(name = "Scenario_Pessimiste_Cep")
    protected double scenarioPessimisteCep;
    @XmlElement(name = "Scenario_Pessimiste_Cep_Ch")
    protected double scenarioPessimisteCepCh;
    @XmlElement(name = "Scenario_Pessimiste_Cep_Fr")
    protected double scenarioPessimisteCepFr;
    @XmlElement(name = "Scenario_Pessimiste_Cep_ECS")
    protected double scenarioPessimisteCepECS;
    @XmlElement(name = "Scenario_Pessimiste_Cep_Ecl")
    protected double scenarioPessimisteCepEcl;
    @XmlElement(name = "Scenario_Pessimiste_Cep_AuxS")
    protected double scenarioPessimisteCepAuxS;
    @XmlElement(name = "Scenario_Pessimiste_Cep_AuxV")
    protected double scenarioPessimisteCepAuxV;
    @XmlElement(name = "Scenario_Optimiste_Cep")
    protected double scenarioOptimisteCep;
    @XmlElement(name = "Scenario_Optimiste_Cep_Ch")
    protected double scenarioOptimisteCepCh;
    @XmlElement(name = "Scenario_Optimiste_Cep_Fr")
    protected double scenarioOptimisteCepFr;
    @XmlElement(name = "Scenario_Optimiste_Cep_ECS")
    protected double scenarioOptimisteCepECS;
    @XmlElement(name = "Scenario_Optimiste_Cep_Ecl")
    protected double scenarioOptimisteCepEcl;
    @XmlElement(name = "Scenario_Optimiste_Cep_AuxS")
    protected double scenarioOptimisteCepAuxS;
    @XmlElement(name = "Scenario_Optimiste_Cep_AuxV")
    protected double scenarioOptimisteCepAuxV;
    @XmlElement(name = "Ss_Apports_Sol_Bbio_Ch")
    protected double ssApportsSolBbioCh;
    @XmlElement(name = "Ss_Apports_Sol_Bbio_Fr")
    protected double ssApportsSolBbioFr;
    @XmlElement(name = "Ss_Apports_Sol_Bbio_Ecl")
    protected double ssApportsSolBbioEcl;
    @XmlElement(name = "Ss_Apports_Sol_Lum_Bbio_Ch")
    protected double ssApportsSolLumBbioCh;
    @XmlElement(name = "Ss_Apports_Sol_Lum_Bbio_Fr")
    protected double ssApportsSolLumBbioFr;
    @XmlElement(name = "Ss_Apports_Sol_Lum_Bbio_Ecl")
    protected double ssApportsSolLumBbioEcl;
    @XmlElement(name = "Sortie_Sensibilite_Zone_Collection")
    protected ArrayOfRTDataSortieSensibiliteZone sortieSensibiliteZoneCollection;

    /**
     * Gets the value of the permeaMIBbio property.
     * 
     */
    public double getPermeaMIBbio() {
        return permeaMIBbio;
    }

    /**
     * Sets the value of the permeaMIBbio property.
     * 
     */
    public void setPermeaMIBbio(double value) {
        this.permeaMIBbio = value;
    }

    /**
     * Gets the value of the permeaMICep property.
     * 
     */
    public double getPermeaMICep() {
        return permeaMICep;
    }

    /**
     * Sets the value of the permeaMICep property.
     * 
     */
    public void setPermeaMICep(double value) {
        this.permeaMICep = value;
    }

    /**
     * Gets the value of the permeaLCBbio property.
     * 
     */
    public double getPermeaLCBbio() {
        return permeaLCBbio;
    }

    /**
     * Sets the value of the permeaLCBbio property.
     * 
     */
    public void setPermeaLCBbio(double value) {
        this.permeaLCBbio = value;
    }

    /**
     * Gets the value of the permeaLCCep property.
     * 
     */
    public double getPermeaLCCep() {
        return permeaLCCep;
    }

    /**
     * Sets the value of the permeaLCCep property.
     * 
     */
    public void setPermeaLCCep(double value) {
        this.permeaLCCep = value;
    }

    /**
     * Gets the value of the permeaTertiaireBbio property.
     * 
     */
    public double getPermeaTertiaireBbio() {
        return permeaTertiaireBbio;
    }

    /**
     * Sets the value of the permeaTertiaireBbio property.
     * 
     */
    public void setPermeaTertiaireBbio(double value) {
        this.permeaTertiaireBbio = value;
    }

    /**
     * Gets the value of the permeaTertiaireCep property.
     * 
     */
    public double getPermeaTertiaireCep() {
        return permeaTertiaireCep;
    }

    /**
     * Sets the value of the permeaTertiaireCep property.
     * 
     */
    public void setPermeaTertiaireCep(double value) {
        this.permeaTertiaireCep = value;
    }

    /**
     * Gets the value of the apportSwSpAugBbio property.
     * 
     */
    public double getApportSwSpAugBbio() {
        return apportSwSpAugBbio;
    }

    /**
     * Sets the value of the apportSwSpAugBbio property.
     * 
     */
    public void setApportSwSpAugBbio(double value) {
        this.apportSwSpAugBbio = value;
    }

    /**
     * Gets the value of the apportSwSpAugCep property.
     * 
     */
    public double getApportSwSpAugCep() {
        return apportSwSpAugCep;
    }

    /**
     * Sets the value of the apportSwSpAugCep property.
     * 
     */
    public void setApportSwSpAugCep(double value) {
        this.apportSwSpAugCep = value;
    }

    /**
     * Gets the value of the apportSwSpDimBbio property.
     * 
     */
    public double getApportSwSpDimBbio() {
        return apportSwSpDimBbio;
    }

    /**
     * Sets the value of the apportSwSpDimBbio property.
     * 
     */
    public void setApportSwSpDimBbio(double value) {
        this.apportSwSpDimBbio = value;
    }

    /**
     * Gets the value of the apportSwSpDimCep property.
     * 
     */
    public double getApportSwSpDimCep() {
        return apportSwSpDimCep;
    }

    /**
     * Sets the value of the apportSwSpDimCep property.
     * 
     */
    public void setApportSwSpDimCep(double value) {
        this.apportSwSpDimCep = value;
    }

    /**
     * Gets the value of the apportSwApAugBbio property.
     * 
     */
    public double getApportSwApAugBbio() {
        return apportSwApAugBbio;
    }

    /**
     * Sets the value of the apportSwApAugBbio property.
     * 
     */
    public void setApportSwApAugBbio(double value) {
        this.apportSwApAugBbio = value;
    }

    /**
     * Gets the value of the apportSwApAugCep property.
     * 
     */
    public double getApportSwApAugCep() {
        return apportSwApAugCep;
    }

    /**
     * Sets the value of the apportSwApAugCep property.
     * 
     */
    public void setApportSwApAugCep(double value) {
        this.apportSwApAugCep = value;
    }

    /**
     * Gets the value of the apportSwApDimBbio property.
     * 
     */
    public double getApportSwApDimBbio() {
        return apportSwApDimBbio;
    }

    /**
     * Sets the value of the apportSwApDimBbio property.
     * 
     */
    public void setApportSwApDimBbio(double value) {
        this.apportSwApDimBbio = value;
    }

    /**
     * Gets the value of the apportSwApDimCep property.
     * 
     */
    public double getApportSwApDimCep() {
        return apportSwApDimCep;
    }

    /**
     * Sets the value of the apportSwApDimCep property.
     * 
     */
    public void setApportSwApDimCep(double value) {
        this.apportSwApDimCep = value;
    }

    /**
     * Gets the value of the facteurTtLumBbio property.
     * 
     */
    public double getFacteurTtLumBbio() {
        return facteurTtLumBbio;
    }

    /**
     * Sets the value of the facteurTtLumBbio property.
     * 
     */
    public void setFacteurTtLumBbio(double value) {
        this.facteurTtLumBbio = value;
    }

    /**
     * Gets the value of the facteurTtLumCep property.
     * 
     */
    public double getFacteurTtLumCep() {
        return facteurTtLumCep;
    }

    /**
     * Sets the value of the facteurTtLumCep property.
     * 
     */
    public void setFacteurTtLumCep(double value) {
        this.facteurTtLumCep = value;
    }

    /**
     * Gets the value of the valeursUBbio property.
     * 
     */
    public double getValeursUBbio() {
        return valeursUBbio;
    }

    /**
     * Sets the value of the valeursUBbio property.
     * 
     */
    public void setValeursUBbio(double value) {
        this.valeursUBbio = value;
    }

    /**
     * Gets the value of the valeursUCep property.
     * 
     */
    public double getValeursUCep() {
        return valeursUCep;
    }

    /**
     * Sets the value of the valeursUCep property.
     * 
     */
    public void setValeursUCep(double value) {
        this.valeursUCep = value;
    }

    /**
     * Gets the value of the valeurUspUapBbio property.
     * 
     */
    public double getValeurUspUapBbio() {
        return valeurUspUapBbio;
    }

    /**
     * Sets the value of the valeurUspUapBbio property.
     * 
     */
    public void setValeurUspUapBbio(double value) {
        this.valeurUspUapBbio = value;
    }

    /**
     * Gets the value of the valeurUspUapCep property.
     * 
     */
    public double getValeurUspUapCep() {
        return valeurUspUapCep;
    }

    /**
     * Sets the value of the valeurUspUapCep property.
     * 
     */
    public void setValeurUspUapCep(double value) {
        this.valeurUspUapCep = value;
    }

    /**
     * Gets the value of the valeursPontsThBbio property.
     * 
     */
    public double getValeursPontsThBbio() {
        return valeursPontsThBbio;
    }

    /**
     * Sets the value of the valeursPontsThBbio property.
     * 
     */
    public void setValeursPontsThBbio(double value) {
        this.valeursPontsThBbio = value;
    }

    /**
     * Gets the value of the valeursPontsThCep property.
     * 
     */
    public double getValeursPontsThCep() {
        return valeursPontsThCep;
    }

    /**
     * Sets the value of the valeursPontsThCep property.
     * 
     */
    public void setValeursPontsThCep(double value) {
        this.valeursPontsThCep = value;
    }

    /**
     * Gets the value of the inertieTresLegereBbio property.
     * 
     */
    public double getInertieTresLegereBbio() {
        return inertieTresLegereBbio;
    }

    /**
     * Sets the value of the inertieTresLegereBbio property.
     * 
     */
    public void setInertieTresLegereBbio(double value) {
        this.inertieTresLegereBbio = value;
    }

    /**
     * Gets the value of the inertieTresLegereCep property.
     * 
     */
    public double getInertieTresLegereCep() {
        return inertieTresLegereCep;
    }

    /**
     * Sets the value of the inertieTresLegereCep property.
     * 
     */
    public void setInertieTresLegereCep(double value) {
        this.inertieTresLegereCep = value;
    }

    /**
     * Gets the value of the inertieTresLourdeBbio property.
     * 
     */
    public double getInertieTresLourdeBbio() {
        return inertieTresLourdeBbio;
    }

    /**
     * Sets the value of the inertieTresLourdeBbio property.
     * 
     */
    public void setInertieTresLourdeBbio(double value) {
        this.inertieTresLourdeBbio = value;
    }

    /**
     * Gets the value of the inertieTresLourdeCep property.
     * 
     */
    public double getInertieTresLourdeCep() {
        return inertieTresLourdeCep;
    }

    /**
     * Sets the value of the inertieTresLourdeCep property.
     * 
     */
    public void setInertieTresLourdeCep(double value) {
        this.inertieTresLourdeCep = value;
    }

    /**
     * Gets the value of the ventilationClasseBCep property.
     * 
     */
    public double getVentilationClasseBCep() {
        return ventilationClasseBCep;
    }

    /**
     * Sets the value of the ventilationClasseBCep property.
     * 
     */
    public void setVentilationClasseBCep(double value) {
        this.ventilationClasseBCep = value;
    }

    /**
     * Gets the value of the ventilationAmelioCep property.
     * 
     */
    public double getVentilationAmelioCep() {
        return ventilationAmelioCep;
    }

    /**
     * Sets the value of the ventilationAmelioCep property.
     * 
     */
    public void setVentilationAmelioCep(double value) {
        this.ventilationAmelioCep = value;
    }

    /**
     * Gets the value of the ventilationPenaCep property.
     * 
     */
    public double getVentilationPenaCep() {
        return ventilationPenaCep;
    }

    /**
     * Sets the value of the ventilationPenaCep property.
     * 
     */
    public void setVentilationPenaCep(double value) {
        this.ventilationPenaCep = value;
    }

    /**
     * Gets the value of the classeVariaSpaChaudCep property.
     * 
     */
    public double getClasseVariaSpaChaudCep() {
        return classeVariaSpaChaudCep;
    }

    /**
     * Sets the value of the classeVariaSpaChaudCep property.
     * 
     */
    public void setClasseVariaSpaChaudCep(double value) {
        this.classeVariaSpaChaudCep = value;
    }

    /**
     * Gets the value of the reseauDistribChauffCep property.
     * 
     */
    public double getReseauDistribChauffCep() {
        return reseauDistribChauffCep;
    }

    /**
     * Sets the value of the reseauDistribChauffCep property.
     * 
     */
    public void setReseauDistribChauffCep(double value) {
        this.reseauDistribChauffCep = value;
    }

    /**
     * Gets the value of the classeVariaSpaFroidCep property.
     * 
     */
    public double getClasseVariaSpaFroidCep() {
        return classeVariaSpaFroidCep;
    }

    /**
     * Sets the value of the classeVariaSpaFroidCep property.
     * 
     */
    public void setClasseVariaSpaFroidCep(double value) {
        this.classeVariaSpaFroidCep = value;
    }

    /**
     * Gets the value of the reseauDistribFroidCep property.
     * 
     */
    public double getReseauDistribFroidCep() {
        return reseauDistribFroidCep;
    }

    /**
     * Sets the value of the reseauDistribFroidCep property.
     * 
     */
    public void setReseauDistribFroidCep(double value) {
        this.reseauDistribFroidCep = value;
    }

    /**
     * Gets the value of the eclairageLocauxCep property.
     * 
     */
    public double getEclairageLocauxCep() {
        return eclairageLocauxCep;
    }

    /**
     * Sets the value of the eclairageLocauxCep property.
     * 
     */
    public void setEclairageLocauxCep(double value) {
        this.eclairageLocauxCep = value;
    }

    /**
     * Gets the value of the ecsPerteUAsCep property.
     * 
     */
    public double getECSPerteUAsCep() {
        return ecsPerteUAsCep;
    }

    /**
     * Sets the value of the ecsPerteUAsCep property.
     * 
     */
    public void setECSPerteUAsCep(double value) {
        this.ecsPerteUAsCep = value;
    }

    /**
     * Gets the value of the reseauECSCep property.
     * 
     */
    public double getReseauECSCep() {
        return reseauECSCep;
    }

    /**
     * Sets the value of the reseauECSCep property.
     * 
     */
    public void setReseauECSCep(double value) {
        this.reseauECSCep = value;
    }

    /**
     * Gets the value of the surfCapteurSolAugCep property.
     * 
     */
    public double getSurfCapteurSolAugCep() {
        return surfCapteurSolAugCep;
    }

    /**
     * Sets the value of the surfCapteurSolAugCep property.
     * 
     */
    public void setSurfCapteurSolAugCep(double value) {
        this.surfCapteurSolAugCep = value;
    }

    /**
     * Gets the value of the surfCapteurSolDimCep property.
     * 
     */
    public double getSurfCapteurSolDimCep() {
        return surfCapteurSolDimCep;
    }

    /**
     * Sets the value of the surfCapteurSolDimCep property.
     * 
     */
    public void setSurfCapteurSolDimCep(double value) {
        this.surfCapteurSolDimCep = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCep property.
     * 
     */
    public double getScenarioPessimisteCep() {
        return scenarioPessimisteCep;
    }

    /**
     * Sets the value of the scenarioPessimisteCep property.
     * 
     */
    public void setScenarioPessimisteCep(double value) {
        this.scenarioPessimisteCep = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepCh property.
     * 
     */
    public double getScenarioPessimisteCepCh() {
        return scenarioPessimisteCepCh;
    }

    /**
     * Sets the value of the scenarioPessimisteCepCh property.
     * 
     */
    public void setScenarioPessimisteCepCh(double value) {
        this.scenarioPessimisteCepCh = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepFr property.
     * 
     */
    public double getScenarioPessimisteCepFr() {
        return scenarioPessimisteCepFr;
    }

    /**
     * Sets the value of the scenarioPessimisteCepFr property.
     * 
     */
    public void setScenarioPessimisteCepFr(double value) {
        this.scenarioPessimisteCepFr = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepECS property.
     * 
     */
    public double getScenarioPessimisteCepECS() {
        return scenarioPessimisteCepECS;
    }

    /**
     * Sets the value of the scenarioPessimisteCepECS property.
     * 
     */
    public void setScenarioPessimisteCepECS(double value) {
        this.scenarioPessimisteCepECS = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepEcl property.
     * 
     */
    public double getScenarioPessimisteCepEcl() {
        return scenarioPessimisteCepEcl;
    }

    /**
     * Sets the value of the scenarioPessimisteCepEcl property.
     * 
     */
    public void setScenarioPessimisteCepEcl(double value) {
        this.scenarioPessimisteCepEcl = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepAuxS property.
     * 
     */
    public double getScenarioPessimisteCepAuxS() {
        return scenarioPessimisteCepAuxS;
    }

    /**
     * Sets the value of the scenarioPessimisteCepAuxS property.
     * 
     */
    public void setScenarioPessimisteCepAuxS(double value) {
        this.scenarioPessimisteCepAuxS = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepAuxV property.
     * 
     */
    public double getScenarioPessimisteCepAuxV() {
        return scenarioPessimisteCepAuxV;
    }

    /**
     * Sets the value of the scenarioPessimisteCepAuxV property.
     * 
     */
    public void setScenarioPessimisteCepAuxV(double value) {
        this.scenarioPessimisteCepAuxV = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCep property.
     * 
     */
    public double getScenarioOptimisteCep() {
        return scenarioOptimisteCep;
    }

    /**
     * Sets the value of the scenarioOptimisteCep property.
     * 
     */
    public void setScenarioOptimisteCep(double value) {
        this.scenarioOptimisteCep = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCepCh property.
     * 
     */
    public double getScenarioOptimisteCepCh() {
        return scenarioOptimisteCepCh;
    }

    /**
     * Sets the value of the scenarioOptimisteCepCh property.
     * 
     */
    public void setScenarioOptimisteCepCh(double value) {
        this.scenarioOptimisteCepCh = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCepFr property.
     * 
     */
    public double getScenarioOptimisteCepFr() {
        return scenarioOptimisteCepFr;
    }

    /**
     * Sets the value of the scenarioOptimisteCepFr property.
     * 
     */
    public void setScenarioOptimisteCepFr(double value) {
        this.scenarioOptimisteCepFr = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCepECS property.
     * 
     */
    public double getScenarioOptimisteCepECS() {
        return scenarioOptimisteCepECS;
    }

    /**
     * Sets the value of the scenarioOptimisteCepECS property.
     * 
     */
    public void setScenarioOptimisteCepECS(double value) {
        this.scenarioOptimisteCepECS = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCepEcl property.
     * 
     */
    public double getScenarioOptimisteCepEcl() {
        return scenarioOptimisteCepEcl;
    }

    /**
     * Sets the value of the scenarioOptimisteCepEcl property.
     * 
     */
    public void setScenarioOptimisteCepEcl(double value) {
        this.scenarioOptimisteCepEcl = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCepAuxS property.
     * 
     */
    public double getScenarioOptimisteCepAuxS() {
        return scenarioOptimisteCepAuxS;
    }

    /**
     * Sets the value of the scenarioOptimisteCepAuxS property.
     * 
     */
    public void setScenarioOptimisteCepAuxS(double value) {
        this.scenarioOptimisteCepAuxS = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCepAuxV property.
     * 
     */
    public double getScenarioOptimisteCepAuxV() {
        return scenarioOptimisteCepAuxV;
    }

    /**
     * Sets the value of the scenarioOptimisteCepAuxV property.
     * 
     */
    public void setScenarioOptimisteCepAuxV(double value) {
        this.scenarioOptimisteCepAuxV = value;
    }

    /**
     * Gets the value of the ssApportsSolBbioCh property.
     * 
     */
    public double getSsApportsSolBbioCh() {
        return ssApportsSolBbioCh;
    }

    /**
     * Sets the value of the ssApportsSolBbioCh property.
     * 
     */
    public void setSsApportsSolBbioCh(double value) {
        this.ssApportsSolBbioCh = value;
    }

    /**
     * Gets the value of the ssApportsSolBbioFr property.
     * 
     */
    public double getSsApportsSolBbioFr() {
        return ssApportsSolBbioFr;
    }

    /**
     * Sets the value of the ssApportsSolBbioFr property.
     * 
     */
    public void setSsApportsSolBbioFr(double value) {
        this.ssApportsSolBbioFr = value;
    }

    /**
     * Gets the value of the ssApportsSolBbioEcl property.
     * 
     */
    public double getSsApportsSolBbioEcl() {
        return ssApportsSolBbioEcl;
    }

    /**
     * Sets the value of the ssApportsSolBbioEcl property.
     * 
     */
    public void setSsApportsSolBbioEcl(double value) {
        this.ssApportsSolBbioEcl = value;
    }

    /**
     * Gets the value of the ssApportsSolLumBbioCh property.
     * 
     */
    public double getSsApportsSolLumBbioCh() {
        return ssApportsSolLumBbioCh;
    }

    /**
     * Sets the value of the ssApportsSolLumBbioCh property.
     * 
     */
    public void setSsApportsSolLumBbioCh(double value) {
        this.ssApportsSolLumBbioCh = value;
    }

    /**
     * Gets the value of the ssApportsSolLumBbioFr property.
     * 
     */
    public double getSsApportsSolLumBbioFr() {
        return ssApportsSolLumBbioFr;
    }

    /**
     * Sets the value of the ssApportsSolLumBbioFr property.
     * 
     */
    public void setSsApportsSolLumBbioFr(double value) {
        this.ssApportsSolLumBbioFr = value;
    }

    /**
     * Gets the value of the ssApportsSolLumBbioEcl property.
     * 
     */
    public double getSsApportsSolLumBbioEcl() {
        return ssApportsSolLumBbioEcl;
    }

    /**
     * Sets the value of the ssApportsSolLumBbioEcl property.
     * 
     */
    public void setSsApportsSolLumBbioEcl(double value) {
        this.ssApportsSolLumBbioEcl = value;
    }

    /**
     * Gets the value of the sortieSensibiliteZoneCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieSensibiliteZone }
     *     
     */
    public ArrayOfRTDataSortieSensibiliteZone getSortieSensibiliteZoneCollection() {
        return sortieSensibiliteZoneCollection;
    }

    /**
     * Sets the value of the sortieSensibiliteZoneCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieSensibiliteZone }
     *     
     */
    public void setSortieSensibiliteZoneCollection(ArrayOfRTDataSortieSensibiliteZone value) {
        this.sortieSensibiliteZoneCollection = value;
    }

}
