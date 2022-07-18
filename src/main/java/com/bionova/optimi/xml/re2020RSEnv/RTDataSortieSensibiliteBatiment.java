
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
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
    "ssApportsSolBbioCh",
    "ssApportsSolBbioFr",
    "ssApportsSolBbioEcl",
    "ssApportsSolLumBbioCh",
    "ssApportsSolLumBbioFr",
    "ssApportsSolLumBbioEcl",
    "sortieSensibiliteZoneCollection"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieSensibiliteBatiment
    extends RTDataSortieBase
{

    @XmlElement(name = "Permea_MI_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double permeaMIBbio;
    @XmlElement(name = "Permea_MI_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double permeaMICep;
    @XmlElement(name = "Permea_LC_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double permeaLCBbio;
    @XmlElement(name = "Permea_LC_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double permeaLCCep;
    @XmlElement(name = "Permea_Tertiaire_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double permeaTertiaireBbio;
    @XmlElement(name = "Permea_Tertiaire_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double permeaTertiaireCep;
    @XmlElement(name = "Apport_Sw_Sp_Aug_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwSpAugBbio;
    @XmlElement(name = "Apport_Sw_Sp_Aug_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwSpAugCep;
    @XmlElement(name = "Apport_Sw_Sp_Dim_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwSpDimBbio;
    @XmlElement(name = "Apport_Sw_Sp_Dim_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwSpDimCep;
    @XmlElement(name = "Apport_Sw_Ap_Aug_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwApAugBbio;
    @XmlElement(name = "Apport_Sw_Ap_Aug_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwApAugCep;
    @XmlElement(name = "Apport_Sw_Ap_Dim_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwApDimBbio;
    @XmlElement(name = "Apport_Sw_Ap_Dim_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double apportSwApDimCep;
    @XmlElement(name = "Facteur_Tt_Lum_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double facteurTtLumBbio;
    @XmlElement(name = "Facteur_Tt_Lum_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double facteurTtLumCep;
    @XmlElement(name = "Valeurs_U_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeursUBbio;
    @XmlElement(name = "Valeurs_U_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeursUCep;
    @XmlElement(name = "Valeur_Usp_Uap_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeurUspUapBbio;
    @XmlElement(name = "Valeur_Usp_Uap_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeurUspUapCep;
    @XmlElement(name = "Valeurs_Ponts_Th_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeursPontsThBbio;
    @XmlElement(name = "Valeurs_Ponts_Th_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeursPontsThCep;
    @XmlElement(name = "Inertie_Tres_Legere_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double inertieTresLegereBbio;
    @XmlElement(name = "Inertie_Tres_Legere_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double inertieTresLegereCep;
    @XmlElement(name = "Inertie_Tres_Lourde_Bbio")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double inertieTresLourdeBbio;
    @XmlElement(name = "Inertie_Tres_Lourde_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double inertieTresLourdeCep;
    @XmlElement(name = "Ventilation_Classe_B_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ventilationClasseBCep;
    @XmlElement(name = "Ventilation_Amelio_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ventilationAmelioCep;
    @XmlElement(name = "Ventilation_Pena_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ventilationPenaCep;
    @XmlElement(name = "Classe_Varia_Spa_Chaud_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double classeVariaSpaChaudCep;
    @XmlElement(name = "Reseau_Distrib_Chauff_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double reseauDistribChauffCep;
    @XmlElement(name = "Classe_Varia_Spa_Froid_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double classeVariaSpaFroidCep;
    @XmlElement(name = "Reseau_Distrib_Froid_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double reseauDistribFroidCep;
    @XmlElement(name = "Eclairage_Locaux_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double eclairageLocauxCep;
    @XmlElement(name = "ECS_Perte_UAs_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ecsPerteUAsCep;
    @XmlElement(name = "Reseau_ECS_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double reseauECSCep;
    @XmlElement(name = "Surf_Capteur_Sol_Aug_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double surfCapteurSolAugCep;
    @XmlElement(name = "Surf_Capteur_Sol_Dim_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double surfCapteurSolDimCep;
    @XmlElement(name = "Scenario_Pessimiste_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCep;
    @XmlElement(name = "Scenario_Pessimiste_Cep_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCepCh;
    @XmlElement(name = "Scenario_Pessimiste_Cep_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCepFr;
    @XmlElement(name = "Scenario_Pessimiste_Cep_ECS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCepECS;
    @XmlElement(name = "Scenario_Pessimiste_Cep_Ecl")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCepEcl;
    @XmlElement(name = "Scenario_Pessimiste_Cep_AuxS")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCepAuxS;
    @XmlElement(name = "Scenario_Pessimiste_Cep_AuxV")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioPessimisteCepAuxV;
    @XmlElement(name = "Scenario_Optimiste_Cep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double scenarioOptimisteCep;
    @XmlElement(name = "Ss_Apports_Sol_Bbio_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ssApportsSolBbioCh;
    @XmlElement(name = "Ss_Apports_Sol_Bbio_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ssApportsSolBbioFr;
    @XmlElement(name = "Ss_Apports_Sol_Bbio_Ecl")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ssApportsSolBbioEcl;
    @XmlElement(name = "Ss_Apports_Sol_Lum_Bbio_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ssApportsSolLumBbioCh;
    @XmlElement(name = "Ss_Apports_Sol_Lum_Bbio_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ssApportsSolLumBbioFr;
    @XmlElement(name = "Ss_Apports_Sol_Lum_Bbio_Ecl")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ssApportsSolLumBbioEcl;
    @XmlElement(name = "Sortie_Sensibilite_Zone_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieSensibiliteZone sortieSensibiliteZoneCollection;

    /**
     * Gets the value of the permeaMIBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPermeaMIBbio() {
        return permeaMIBbio;
    }

    /**
     * Sets the value of the permeaMIBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeaMIBbio(double value) {
        this.permeaMIBbio = value;
    }

    /**
     * Gets the value of the permeaMICep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPermeaMICep() {
        return permeaMICep;
    }

    /**
     * Sets the value of the permeaMICep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeaMICep(double value) {
        this.permeaMICep = value;
    }

    /**
     * Gets the value of the permeaLCBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPermeaLCBbio() {
        return permeaLCBbio;
    }

    /**
     * Sets the value of the permeaLCBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeaLCBbio(double value) {
        this.permeaLCBbio = value;
    }

    /**
     * Gets the value of the permeaLCCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPermeaLCCep() {
        return permeaLCCep;
    }

    /**
     * Sets the value of the permeaLCCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeaLCCep(double value) {
        this.permeaLCCep = value;
    }

    /**
     * Gets the value of the permeaTertiaireBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPermeaTertiaireBbio() {
        return permeaTertiaireBbio;
    }

    /**
     * Sets the value of the permeaTertiaireBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeaTertiaireBbio(double value) {
        this.permeaTertiaireBbio = value;
    }

    /**
     * Gets the value of the permeaTertiaireCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPermeaTertiaireCep() {
        return permeaTertiaireCep;
    }

    /**
     * Sets the value of the permeaTertiaireCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPermeaTertiaireCep(double value) {
        this.permeaTertiaireCep = value;
    }

    /**
     * Gets the value of the apportSwSpAugBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwSpAugBbio() {
        return apportSwSpAugBbio;
    }

    /**
     * Sets the value of the apportSwSpAugBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwSpAugBbio(double value) {
        this.apportSwSpAugBbio = value;
    }

    /**
     * Gets the value of the apportSwSpAugCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwSpAugCep() {
        return apportSwSpAugCep;
    }

    /**
     * Sets the value of the apportSwSpAugCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwSpAugCep(double value) {
        this.apportSwSpAugCep = value;
    }

    /**
     * Gets the value of the apportSwSpDimBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwSpDimBbio() {
        return apportSwSpDimBbio;
    }

    /**
     * Sets the value of the apportSwSpDimBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwSpDimBbio(double value) {
        this.apportSwSpDimBbio = value;
    }

    /**
     * Gets the value of the apportSwSpDimCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwSpDimCep() {
        return apportSwSpDimCep;
    }

    /**
     * Sets the value of the apportSwSpDimCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwSpDimCep(double value) {
        this.apportSwSpDimCep = value;
    }

    /**
     * Gets the value of the apportSwApAugBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwApAugBbio() {
        return apportSwApAugBbio;
    }

    /**
     * Sets the value of the apportSwApAugBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwApAugBbio(double value) {
        this.apportSwApAugBbio = value;
    }

    /**
     * Gets the value of the apportSwApAugCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwApAugCep() {
        return apportSwApAugCep;
    }

    /**
     * Sets the value of the apportSwApAugCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwApAugCep(double value) {
        this.apportSwApAugCep = value;
    }

    /**
     * Gets the value of the apportSwApDimBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwApDimBbio() {
        return apportSwApDimBbio;
    }

    /**
     * Sets the value of the apportSwApDimBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwApDimBbio(double value) {
        this.apportSwApDimBbio = value;
    }

    /**
     * Gets the value of the apportSwApDimCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getApportSwApDimCep() {
        return apportSwApDimCep;
    }

    /**
     * Sets the value of the apportSwApDimCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setApportSwApDimCep(double value) {
        this.apportSwApDimCep = value;
    }

    /**
     * Gets the value of the facteurTtLumBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getFacteurTtLumBbio() {
        return facteurTtLumBbio;
    }

    /**
     * Sets the value of the facteurTtLumBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFacteurTtLumBbio(double value) {
        this.facteurTtLumBbio = value;
    }

    /**
     * Gets the value of the facteurTtLumCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getFacteurTtLumCep() {
        return facteurTtLumCep;
    }

    /**
     * Sets the value of the facteurTtLumCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFacteurTtLumCep(double value) {
        this.facteurTtLumCep = value;
    }

    /**
     * Gets the value of the valeursUBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeursUBbio() {
        return valeursUBbio;
    }

    /**
     * Sets the value of the valeursUBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeursUBbio(double value) {
        this.valeursUBbio = value;
    }

    /**
     * Gets the value of the valeursUCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeursUCep() {
        return valeursUCep;
    }

    /**
     * Sets the value of the valeursUCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeursUCep(double value) {
        this.valeursUCep = value;
    }

    /**
     * Gets the value of the valeurUspUapBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeurUspUapBbio() {
        return valeurUspUapBbio;
    }

    /**
     * Sets the value of the valeurUspUapBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurUspUapBbio(double value) {
        this.valeurUspUapBbio = value;
    }

    /**
     * Gets the value of the valeurUspUapCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeurUspUapCep() {
        return valeurUspUapCep;
    }

    /**
     * Sets the value of the valeurUspUapCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurUspUapCep(double value) {
        this.valeurUspUapCep = value;
    }

    /**
     * Gets the value of the valeursPontsThBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeursPontsThBbio() {
        return valeursPontsThBbio;
    }

    /**
     * Sets the value of the valeursPontsThBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeursPontsThBbio(double value) {
        this.valeursPontsThBbio = value;
    }

    /**
     * Gets the value of the valeursPontsThCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeursPontsThCep() {
        return valeursPontsThCep;
    }

    /**
     * Sets the value of the valeursPontsThCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeursPontsThCep(double value) {
        this.valeursPontsThCep = value;
    }

    /**
     * Gets the value of the inertieTresLegereBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getInertieTresLegereBbio() {
        return inertieTresLegereBbio;
    }

    /**
     * Sets the value of the inertieTresLegereBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertieTresLegereBbio(double value) {
        this.inertieTresLegereBbio = value;
    }

    /**
     * Gets the value of the inertieTresLegereCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getInertieTresLegereCep() {
        return inertieTresLegereCep;
    }

    /**
     * Sets the value of the inertieTresLegereCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertieTresLegereCep(double value) {
        this.inertieTresLegereCep = value;
    }

    /**
     * Gets the value of the inertieTresLourdeBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getInertieTresLourdeBbio() {
        return inertieTresLourdeBbio;
    }

    /**
     * Sets the value of the inertieTresLourdeBbio property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertieTresLourdeBbio(double value) {
        this.inertieTresLourdeBbio = value;
    }

    /**
     * Gets the value of the inertieTresLourdeCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getInertieTresLourdeCep() {
        return inertieTresLourdeCep;
    }

    /**
     * Sets the value of the inertieTresLourdeCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertieTresLourdeCep(double value) {
        this.inertieTresLourdeCep = value;
    }

    /**
     * Gets the value of the ventilationClasseBCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVentilationClasseBCep() {
        return ventilationClasseBCep;
    }

    /**
     * Sets the value of the ventilationClasseBCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVentilationClasseBCep(double value) {
        this.ventilationClasseBCep = value;
    }

    /**
     * Gets the value of the ventilationAmelioCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVentilationAmelioCep() {
        return ventilationAmelioCep;
    }

    /**
     * Sets the value of the ventilationAmelioCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVentilationAmelioCep(double value) {
        this.ventilationAmelioCep = value;
    }

    /**
     * Gets the value of the ventilationPenaCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getVentilationPenaCep() {
        return ventilationPenaCep;
    }

    /**
     * Sets the value of the ventilationPenaCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVentilationPenaCep(double value) {
        this.ventilationPenaCep = value;
    }

    /**
     * Gets the value of the classeVariaSpaChaudCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getClasseVariaSpaChaudCep() {
        return classeVariaSpaChaudCep;
    }

    /**
     * Sets the value of the classeVariaSpaChaudCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setClasseVariaSpaChaudCep(double value) {
        this.classeVariaSpaChaudCep = value;
    }

    /**
     * Gets the value of the reseauDistribChauffCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getReseauDistribChauffCep() {
        return reseauDistribChauffCep;
    }

    /**
     * Sets the value of the reseauDistribChauffCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setReseauDistribChauffCep(double value) {
        this.reseauDistribChauffCep = value;
    }

    /**
     * Gets the value of the classeVariaSpaFroidCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getClasseVariaSpaFroidCep() {
        return classeVariaSpaFroidCep;
    }

    /**
     * Sets the value of the classeVariaSpaFroidCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setClasseVariaSpaFroidCep(double value) {
        this.classeVariaSpaFroidCep = value;
    }

    /**
     * Gets the value of the reseauDistribFroidCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getReseauDistribFroidCep() {
        return reseauDistribFroidCep;
    }

    /**
     * Sets the value of the reseauDistribFroidCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setReseauDistribFroidCep(double value) {
        this.reseauDistribFroidCep = value;
    }

    /**
     * Gets the value of the eclairageLocauxCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getEclairageLocauxCep() {
        return eclairageLocauxCep;
    }

    /**
     * Sets the value of the eclairageLocauxCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEclairageLocauxCep(double value) {
        this.eclairageLocauxCep = value;
    }

    /**
     * Gets the value of the ecsPerteUAsCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getECSPerteUAsCep() {
        return ecsPerteUAsCep;
    }

    /**
     * Sets the value of the ecsPerteUAsCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setECSPerteUAsCep(double value) {
        this.ecsPerteUAsCep = value;
    }

    /**
     * Gets the value of the reseauECSCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getReseauECSCep() {
        return reseauECSCep;
    }

    /**
     * Sets the value of the reseauECSCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setReseauECSCep(double value) {
        this.reseauECSCep = value;
    }

    /**
     * Gets the value of the surfCapteurSolAugCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSurfCapteurSolAugCep() {
        return surfCapteurSolAugCep;
    }

    /**
     * Sets the value of the surfCapteurSolAugCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSurfCapteurSolAugCep(double value) {
        this.surfCapteurSolAugCep = value;
    }

    /**
     * Gets the value of the surfCapteurSolDimCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSurfCapteurSolDimCep() {
        return surfCapteurSolDimCep;
    }

    /**
     * Sets the value of the surfCapteurSolDimCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSurfCapteurSolDimCep(double value) {
        this.surfCapteurSolDimCep = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCep() {
        return scenarioPessimisteCep;
    }

    /**
     * Sets the value of the scenarioPessimisteCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCep(double value) {
        this.scenarioPessimisteCep = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCepCh() {
        return scenarioPessimisteCepCh;
    }

    /**
     * Sets the value of the scenarioPessimisteCepCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCepCh(double value) {
        this.scenarioPessimisteCepCh = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCepFr() {
        return scenarioPessimisteCepFr;
    }

    /**
     * Sets the value of the scenarioPessimisteCepFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCepFr(double value) {
        this.scenarioPessimisteCepFr = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCepECS() {
        return scenarioPessimisteCepECS;
    }

    /**
     * Sets the value of the scenarioPessimisteCepECS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCepECS(double value) {
        this.scenarioPessimisteCepECS = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepEcl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCepEcl() {
        return scenarioPessimisteCepEcl;
    }

    /**
     * Sets the value of the scenarioPessimisteCepEcl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCepEcl(double value) {
        this.scenarioPessimisteCepEcl = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepAuxS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCepAuxS() {
        return scenarioPessimisteCepAuxS;
    }

    /**
     * Sets the value of the scenarioPessimisteCepAuxS property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCepAuxS(double value) {
        this.scenarioPessimisteCepAuxS = value;
    }

    /**
     * Gets the value of the scenarioPessimisteCepAuxV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioPessimisteCepAuxV() {
        return scenarioPessimisteCepAuxV;
    }

    /**
     * Sets the value of the scenarioPessimisteCepAuxV property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioPessimisteCepAuxV(double value) {
        this.scenarioPessimisteCepAuxV = value;
    }

    /**
     * Gets the value of the scenarioOptimisteCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getScenarioOptimisteCep() {
        return scenarioOptimisteCep;
    }

    /**
     * Sets the value of the scenarioOptimisteCep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setScenarioOptimisteCep(double value) {
        this.scenarioOptimisteCep = value;
    }

    /**
     * Gets the value of the ssApportsSolBbioCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSsApportsSolBbioCh() {
        return ssApportsSolBbioCh;
    }

    /**
     * Sets the value of the ssApportsSolBbioCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSsApportsSolBbioCh(double value) {
        this.ssApportsSolBbioCh = value;
    }

    /**
     * Gets the value of the ssApportsSolBbioFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSsApportsSolBbioFr() {
        return ssApportsSolBbioFr;
    }

    /**
     * Sets the value of the ssApportsSolBbioFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSsApportsSolBbioFr(double value) {
        this.ssApportsSolBbioFr = value;
    }

    /**
     * Gets the value of the ssApportsSolBbioEcl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSsApportsSolBbioEcl() {
        return ssApportsSolBbioEcl;
    }

    /**
     * Sets the value of the ssApportsSolBbioEcl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSsApportsSolBbioEcl(double value) {
        this.ssApportsSolBbioEcl = value;
    }

    /**
     * Gets the value of the ssApportsSolLumBbioCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSsApportsSolLumBbioCh() {
        return ssApportsSolLumBbioCh;
    }

    /**
     * Sets the value of the ssApportsSolLumBbioCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSsApportsSolLumBbioCh(double value) {
        this.ssApportsSolLumBbioCh = value;
    }

    /**
     * Gets the value of the ssApportsSolLumBbioFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSsApportsSolLumBbioFr() {
        return ssApportsSolLumBbioFr;
    }

    /**
     * Sets the value of the ssApportsSolLumBbioFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSsApportsSolLumBbioFr(double value) {
        this.ssApportsSolLumBbioFr = value;
    }

    /**
     * Gets the value of the ssApportsSolLumBbioEcl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSsApportsSolLumBbioEcl() {
        return ssApportsSolLumBbioEcl;
    }

    /**
     * Sets the value of the ssApportsSolLumBbioEcl property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieSensibiliteZoneCollection(ArrayOfRTDataSortieSensibiliteZone value) {
        this.sortieSensibiliteZoneCollection = value;
    }

}
