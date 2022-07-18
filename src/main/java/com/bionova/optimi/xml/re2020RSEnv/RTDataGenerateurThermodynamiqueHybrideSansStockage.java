
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Thermodynamique_Hybride_Sans_Stockage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Thermodynamique_Hybride_Sans_Stockage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_FouGen_Thermo" type="{}E_Id_FouGen_Mode13"/>
 *         &lt;element name="Idpriorite_Thermo_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Thermo_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo_Ch" type="{}E_Systeme_Thermodynamique_Hybride_Chauffage"/>
 *         &lt;element name="Sys_Thermo" type="{}E_Systeme_Thermodynamique_Electrique_Hybride_Reversible"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ch" type="{}E_Temperatures_Aval_Air_Eau_Ch"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ch" type="{}E_Temperatures_Amont_Air_Eau_Ch"/>
 *         &lt;element name="COP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ch" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_COP" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Fonct_Part_Ch" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur_Ch" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Emetteur"/>
 *         &lt;element name="Statut_Donnee_Fr" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Fr" type="{}E_Temperatures_Aval_Air_Eau_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Fr" type="{}E_Temperatures_Amont_Air_Eau_Fr"/>
 *         &lt;element name="EER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Fr" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_EER" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Fr" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Av_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Fonct_Part_Fr" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur_Fr" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Fr" type="{}E_Emetteur"/>
 *         &lt;element name="Id_FouGen_Comb" type="{}E_Id_FouGen_Mode15"/>
 *         &lt;element name="Type_Generateur_Combustion" type="{}E_Mode_Generateur_Combustion_Hybride"/>
 *         &lt;element name="Combustible_Gaz" type="{}E_Combustible_Gaz"/>
 *         &lt;element name="Ventilation" type="{}E_Ventilation_Generateur_Combustion_Hybride"/>
 *         &lt;element name="Idpriorite_Comb_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Comb_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Valeur_Mesuree_Defaut_Theta_Min" type="{}E_Valeur_Mesuree_Defaut"/>
 *         &lt;element name="Theta_Fonc_Min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Class_Chaud_Bois" type="{}E_Classe_Chaudiere_Bois"/>
 *         &lt;element name="Pn_gen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Defaut_R_pn" type="{}E_Valeur_Declaree_Justifiee_Certifiee_Defaut"/>
 *         &lt;element name="R_pn" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Defaut_R_Pint" type="{}E_Valeur_Declaree_Justifiee_Certifiee_Defaut"/>
 *         &lt;element name="R_Pint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Chargement_Chaudiere_Bois" type="{}E_Chargement_Chaudiere_Bois"/>
 *         &lt;element name="Accumulateur_Gaz" type="{}E_Accumulateur_Gaz"/>
 *         &lt;element name="Q_veille" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Mesuree_Defaut_Q_po_30" type="{}E_Valeur_Mesuree_Defaut"/>
 *         &lt;element name="Q_po_30" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Mesuree_Defaut_Q_aux_nom" type="{}E_Valeur_Mesuree_Defaut"/>
 *         &lt;element name="Q_aux_nom_gen_combust" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Alim_Chaudiere_Bois" type="{}E_Chaudiere_Bois"/>
 *         &lt;element name="Ventil_Emission" type="{}E_Ventil_Emission"/>
 *         &lt;element name="P_Qp_g" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Qp_g_fonc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Thermodynamique_Hybride_Sans_Stockage", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataGenerateurThermodynamiqueHybrideSansStockage {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Rdim")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int rdim;
    @XmlElement(name = "Id_FouGen_Thermo", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idFouGenThermo;
    @XmlElement(name = "Idpriorite_Thermo_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idprioriteThermoCh;
    @XmlElement(name = "Idpriorite_Thermo_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idprioriteThermoFr;
    @XmlElement(name = "Sys_Thermo_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String sysThermoCh;
    @XmlElement(name = "Sys_Thermo", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String sysThermo;
    @XmlElement(name = "Id_Source_Amont")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idSourceAmont;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDonneeCh;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirEauCh;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirEauCh;
    @XmlElement(name = "COP")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String cop;
    @XmlElement(name = "Pabs_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String pabsCh;
    @XmlElement(name = "COR_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String corCh;
    @XmlElement(name = "Statut_Val_Pivot_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutValPivotCh;
    @XmlElement(name = "Val_COP")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valCOP;
    @XmlElement(name = "Val_Pabs_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valPabsCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxAvCh;
    @XmlElement(name = "Theta_Min_Am_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinAmCh;
    @XmlElement(name = "Statut_Fonct_Part_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutFonctPartCh;
    @XmlElement(name = "Fonctionnement_Compresseur_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String fonctionnementCompresseurCh;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutFonctionnementContinuCh;
    @XmlElement(name = "LRcontmin_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ccplRcontminCh;
    @XmlElement(name = "Statut_Taux_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutTauxCh;
    @XmlElement(name = "Taux_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tauxCh;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typoEmetteurCh;
    @XmlElement(name = "Statut_Donnee_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDonneeFr;
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirEauFr;
    @XmlElement(name = "EER")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String eer;
    @XmlElement(name = "Pabs_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String pabsFr;
    @XmlElement(name = "COR_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String corFr;
    @XmlElement(name = "Statut_Val_Pivot_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutValPivotFr;
    @XmlElement(name = "Val_EER")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valEER;
    @XmlElement(name = "Val_Pabs_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valPabsFr;
    @XmlElement(name = "Lim_Theta_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String limThetaFr;
    @XmlElement(name = "Theta_Max_Av_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxAvFr;
    @XmlElement(name = "Theta_Min_Av_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinAvFr;
    @XmlElement(name = "Statut_Fonct_Part_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutFonctPartFr;
    @XmlElement(name = "Fonctionnement_Compresseur_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String fonctionnementCompresseurFr;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutFonctionnementContinuFr;
    @XmlElement(name = "LRcontmin_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double lRcontminFr;
    @XmlElement(name = "CCP_LRcontmin_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ccplRcontminFr;
    @XmlElement(name = "Statut_Taux_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutTauxFr;
    @XmlElement(name = "Taux_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double tauxFr;
    @XmlElement(name = "Typo_Emetteur_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typoEmetteurFr;
    @XmlElement(name = "Id_FouGen_Comb", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idFouGenComb;
    @XmlElement(name = "Type_Generateur_Combustion", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeGenerateurCombustion;
    @XmlElement(name = "Combustible_Gaz", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String combustibleGaz;
    @XmlElement(name = "Ventilation", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String ventilation;
    @XmlElement(name = "Idpriorite_Comb_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idprioriteCombCh;
    @XmlElement(name = "Idpriorite_Comb_Ecs")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idprioriteCombEcs;
    @XmlElement(name = "Valeur_Mesuree_Defaut_Theta_Min", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurMesureeDefautThetaMin;
    @XmlElement(name = "Theta_Fonc_Min")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaFoncMin;
    @XmlElement(name = "Class_Chaud_Bois", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String classChaudBois;
    @XmlElement(name = "Pn_gen")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pnGen;
    @XmlElement(name = "Valeur_Certifiee_Defaut_R_pn", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurCertifieeDefautRPn;
    @XmlElement(name = "R_pn")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rPn;
    @XmlElement(name = "Pint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pint;
    @XmlElement(name = "Valeur_Certifiee_Defaut_R_Pint", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurCertifieeDefautRPint;
    @XmlElement(name = "R_Pint")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rPint;
    @XmlElement(name = "Chargement_Chaudiere_Bois", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String chargementChaudiereBois;
    @XmlElement(name = "Accumulateur_Gaz", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String accumulateurGaz;
    @XmlElement(name = "Q_veille")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qVeille;
    @XmlElement(name = "Valeur_Mesuree_Defaut_Q_po_30", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurMesureeDefautQPo30;
    @XmlElement(name = "Q_po_30")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qPo30;
    @XmlElement(name = "Valeur_Mesuree_Defaut_Q_aux_nom", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurMesureeDefautQAuxNom;
    @XmlElement(name = "Q_aux_nom_gen_combust")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qAuxNomGenCombust;
    @XmlElement(name = "Alim_Chaudiere_Bois", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String alimChaudiereBois;
    @XmlElement(name = "Ventil_Emission", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String ventilEmission;
    @XmlElement(name = "P_Qp_g")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pQpG;
    @XmlElement(name = "P_Qp_g_fonc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pQpGFonc;

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
     * Gets the value of the rdim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRdim(int value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the idFouGenThermo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdFouGenThermo() {
        return idFouGenThermo;
    }

    /**
     * Sets the value of the idFouGenThermo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdFouGenThermo(String value) {
        this.idFouGenThermo = value;
    }

    /**
     * Gets the value of the idprioriteThermoCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdprioriteThermoCh() {
        return idprioriteThermoCh;
    }

    /**
     * Sets the value of the idprioriteThermoCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdprioriteThermoCh(int value) {
        this.idprioriteThermoCh = value;
    }

    /**
     * Gets the value of the idprioriteThermoFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdprioriteThermoFr() {
        return idprioriteThermoFr;
    }

    /**
     * Sets the value of the idprioriteThermoFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdprioriteThermoFr(int value) {
        this.idprioriteThermoFr = value;
    }

    /**
     * Gets the value of the sysThermoCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSysThermoCh() {
        return sysThermoCh;
    }

    /**
     * Sets the value of the sysThermoCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSysThermoCh(String value) {
        this.sysThermoCh = value;
    }

    /**
     * Gets the value of the sysThermo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getSysThermo() {
        return sysThermo;
    }

    /**
     * Sets the value of the sysThermo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSysThermo(String value) {
        this.sysThermo = value;
    }

    /**
     * Gets the value of the idSourceAmont property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdSourceAmont() {
        return idSourceAmont;
    }

    /**
     * Sets the value of the idSourceAmont property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdSourceAmont(int value) {
        this.idSourceAmont = value;
    }

    /**
     * Gets the value of the statutDonneeCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutDonneeCh() {
        return statutDonneeCh;
    }

    /**
     * Sets the value of the statutDonneeCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutDonneeCh(String value) {
        this.statutDonneeCh = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalAirEauCh() {
        return thetaAvalAirEauCh;
    }

    /**
     * Sets the value of the thetaAvalAirEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalAirEauCh(String value) {
        this.thetaAvalAirEauCh = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontAirEauCh() {
        return thetaAmontAirEauCh;
    }

    /**
     * Sets the value of the thetaAmontAirEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontAirEauCh(String value) {
        this.thetaAmontAirEauCh = value;
    }

    /**
     * Gets the value of the cop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCOP() {
        return cop;
    }

    /**
     * Sets the value of the cop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCOP(String value) {
        this.cop = value;
    }

    /**
     * Gets the value of the pabsCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPabsCh() {
        return pabsCh;
    }

    /**
     * Sets the value of the pabsCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPabsCh(String value) {
        this.pabsCh = value;
    }

    /**
     * Gets the value of the corCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCORCh() {
        return corCh;
    }

    /**
     * Sets the value of the corCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCORCh(String value) {
        this.corCh = value;
    }

    /**
     * Gets the value of the statutValPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutValPivotCh() {
        return statutValPivotCh;
    }

    /**
     * Sets the value of the statutValPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutValPivotCh(String value) {
        this.statutValPivotCh = value;
    }

    /**
     * Gets the value of the valCOP property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValCOP() {
        return valCOP;
    }

    /**
     * Sets the value of the valCOP property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValCOP(double value) {
        this.valCOP = value;
    }

    /**
     * Gets the value of the valPabsCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValPabsCh() {
        return valPabsCh;
    }

    /**
     * Sets the value of the valPabsCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValPabsCh(double value) {
        this.valPabsCh = value;
    }

    /**
     * Gets the value of the limThetaCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLimThetaCh() {
        return limThetaCh;
    }

    /**
     * Sets the value of the limThetaCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLimThetaCh(String value) {
        this.limThetaCh = value;
    }

    /**
     * Gets the value of the thetaMaxAvCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxAvCh() {
        return thetaMaxAvCh;
    }

    /**
     * Sets the value of the thetaMaxAvCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxAvCh(double value) {
        this.thetaMaxAvCh = value;
    }

    /**
     * Gets the value of the thetaMinAmCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinAmCh() {
        return thetaMinAmCh;
    }

    /**
     * Sets the value of the thetaMinAmCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinAmCh(double value) {
        this.thetaMinAmCh = value;
    }

    /**
     * Gets the value of the statutFonctPartCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutFonctPartCh() {
        return statutFonctPartCh;
    }

    /**
     * Sets the value of the statutFonctPartCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutFonctPartCh(String value) {
        this.statutFonctPartCh = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getFonctionnementCompresseurCh() {
        return fonctionnementCompresseurCh;
    }

    /**
     * Sets the value of the fonctionnementCompresseurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFonctionnementCompresseurCh(String value) {
        this.fonctionnementCompresseurCh = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutFonctionnementContinuCh() {
        return statutFonctionnementContinuCh;
    }

    /**
     * Sets the value of the statutFonctionnementContinuCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutFonctionnementContinuCh(String value) {
        this.statutFonctionnementContinuCh = value;
    }

    /**
     * Gets the value of the lRcontminCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLRcontminCh() {
        return lRcontminCh;
    }

    /**
     * Sets the value of the lRcontminCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLRcontminCh(double value) {
        this.lRcontminCh = value;
    }

    /**
     * Gets the value of the ccplRcontminCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCCPLRcontminCh() {
        return ccplRcontminCh;
    }

    /**
     * Sets the value of the ccplRcontminCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCCPLRcontminCh(double value) {
        this.ccplRcontminCh = value;
    }

    /**
     * Gets the value of the statutTauxCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutTauxCh() {
        return statutTauxCh;
    }

    /**
     * Sets the value of the statutTauxCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutTauxCh(String value) {
        this.statutTauxCh = value;
    }

    /**
     * Gets the value of the tauxCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTauxCh() {
        return tauxCh;
    }

    /**
     * Sets the value of the tauxCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTauxCh(double value) {
        this.tauxCh = value;
    }

    /**
     * Gets the value of the typoEmetteurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypoEmetteurCh() {
        return typoEmetteurCh;
    }

    /**
     * Sets the value of the typoEmetteurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypoEmetteurCh(String value) {
        this.typoEmetteurCh = value;
    }

    /**
     * Gets the value of the statutDonneeFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutDonneeFr() {
        return statutDonneeFr;
    }

    /**
     * Sets the value of the statutDonneeFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutDonneeFr(String value) {
        this.statutDonneeFr = value;
    }

    /**
     * Gets the value of the thetaAvalAirEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalAirEauFr() {
        return thetaAvalAirEauFr;
    }

    /**
     * Sets the value of the thetaAvalAirEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalAirEauFr(String value) {
        this.thetaAvalAirEauFr = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontAirEauFr() {
        return thetaAmontAirEauFr;
    }

    /**
     * Sets the value of the thetaAmontAirEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontAirEauFr(String value) {
        this.thetaAmontAirEauFr = value;
    }

    /**
     * Gets the value of the eer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getEER() {
        return eer;
    }

    /**
     * Sets the value of the eer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEER(String value) {
        this.eer = value;
    }

    /**
     * Gets the value of the pabsFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPabsFr() {
        return pabsFr;
    }

    /**
     * Sets the value of the pabsFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPabsFr(String value) {
        this.pabsFr = value;
    }

    /**
     * Gets the value of the corFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCORFr() {
        return corFr;
    }

    /**
     * Sets the value of the corFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCORFr(String value) {
        this.corFr = value;
    }

    /**
     * Gets the value of the statutValPivotFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutValPivotFr() {
        return statutValPivotFr;
    }

    /**
     * Sets the value of the statutValPivotFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutValPivotFr(String value) {
        this.statutValPivotFr = value;
    }

    /**
     * Gets the value of the valEER property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValEER() {
        return valEER;
    }

    /**
     * Sets the value of the valEER property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValEER(double value) {
        this.valEER = value;
    }

    /**
     * Gets the value of the valPabsFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValPabsFr() {
        return valPabsFr;
    }

    /**
     * Sets the value of the valPabsFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValPabsFr(double value) {
        this.valPabsFr = value;
    }

    /**
     * Gets the value of the limThetaFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLimThetaFr() {
        return limThetaFr;
    }

    /**
     * Sets the value of the limThetaFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLimThetaFr(String value) {
        this.limThetaFr = value;
    }

    /**
     * Gets the value of the thetaMaxAvFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxAvFr() {
        return thetaMaxAvFr;
    }

    /**
     * Sets the value of the thetaMaxAvFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxAvFr(double value) {
        this.thetaMaxAvFr = value;
    }

    /**
     * Gets the value of the thetaMinAvFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinAvFr() {
        return thetaMinAvFr;
    }

    /**
     * Sets the value of the thetaMinAvFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinAvFr(double value) {
        this.thetaMinAvFr = value;
    }

    /**
     * Gets the value of the statutFonctPartFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutFonctPartFr() {
        return statutFonctPartFr;
    }

    /**
     * Sets the value of the statutFonctPartFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutFonctPartFr(String value) {
        this.statutFonctPartFr = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getFonctionnementCompresseurFr() {
        return fonctionnementCompresseurFr;
    }

    /**
     * Sets the value of the fonctionnementCompresseurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFonctionnementCompresseurFr(String value) {
        this.fonctionnementCompresseurFr = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinuFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutFonctionnementContinuFr() {
        return statutFonctionnementContinuFr;
    }

    /**
     * Sets the value of the statutFonctionnementContinuFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutFonctionnementContinuFr(String value) {
        this.statutFonctionnementContinuFr = value;
    }

    /**
     * Gets the value of the lRcontminFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getLRcontminFr() {
        return lRcontminFr;
    }

    /**
     * Sets the value of the lRcontminFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLRcontminFr(double value) {
        this.lRcontminFr = value;
    }

    /**
     * Gets the value of the ccplRcontminFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCCPLRcontminFr() {
        return ccplRcontminFr;
    }

    /**
     * Sets the value of the ccplRcontminFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCCPLRcontminFr(double value) {
        this.ccplRcontminFr = value;
    }

    /**
     * Gets the value of the statutTauxFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutTauxFr() {
        return statutTauxFr;
    }

    /**
     * Sets the value of the statutTauxFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutTauxFr(String value) {
        this.statutTauxFr = value;
    }

    /**
     * Gets the value of the tauxFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTauxFr() {
        return tauxFr;
    }

    /**
     * Sets the value of the tauxFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTauxFr(double value) {
        this.tauxFr = value;
    }

    /**
     * Gets the value of the typoEmetteurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypoEmetteurFr() {
        return typoEmetteurFr;
    }

    /**
     * Sets the value of the typoEmetteurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypoEmetteurFr(String value) {
        this.typoEmetteurFr = value;
    }

    /**
     * Gets the value of the idFouGenComb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getIdFouGenComb() {
        return idFouGenComb;
    }

    /**
     * Sets the value of the idFouGenComb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdFouGenComb(String value) {
        this.idFouGenComb = value;
    }

    /**
     * Gets the value of the typeGenerateurCombustion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeGenerateurCombustion() {
        return typeGenerateurCombustion;
    }

    /**
     * Sets the value of the typeGenerateurCombustion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeGenerateurCombustion(String value) {
        this.typeGenerateurCombustion = value;
    }

    /**
     * Gets the value of the combustibleGaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCombustibleGaz() {
        return combustibleGaz;
    }

    /**
     * Sets the value of the combustibleGaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCombustibleGaz(String value) {
        this.combustibleGaz = value;
    }

    /**
     * Gets the value of the ventilation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getVentilation() {
        return ventilation;
    }

    /**
     * Sets the value of the ventilation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVentilation(String value) {
        this.ventilation = value;
    }

    /**
     * Gets the value of the idprioriteCombCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdprioriteCombCh() {
        return idprioriteCombCh;
    }

    /**
     * Sets the value of the idprioriteCombCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdprioriteCombCh(int value) {
        this.idprioriteCombCh = value;
    }

    /**
     * Gets the value of the idprioriteCombEcs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdprioriteCombEcs() {
        return idprioriteCombEcs;
    }

    /**
     * Sets the value of the idprioriteCombEcs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdprioriteCombEcs(int value) {
        this.idprioriteCombEcs = value;
    }

    /**
     * Gets the value of the valeurMesureeDefautThetaMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurMesureeDefautThetaMin() {
        return valeurMesureeDefautThetaMin;
    }

    /**
     * Sets the value of the valeurMesureeDefautThetaMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurMesureeDefautThetaMin(String value) {
        this.valeurMesureeDefautThetaMin = value;
    }

    /**
     * Gets the value of the thetaFoncMin property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaFoncMin() {
        return thetaFoncMin;
    }

    /**
     * Sets the value of the thetaFoncMin property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaFoncMin(double value) {
        this.thetaFoncMin = value;
    }

    /**
     * Gets the value of the classChaudBois property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getClassChaudBois() {
        return classChaudBois;
    }

    /**
     * Sets the value of the classChaudBois property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setClassChaudBois(String value) {
        this.classChaudBois = value;
    }

    /**
     * Gets the value of the pnGen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPnGen() {
        return pnGen;
    }

    /**
     * Sets the value of the pnGen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPnGen(double value) {
        this.pnGen = value;
    }

    /**
     * Gets the value of the valeurCertifieeDefautRPn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurCertifieeDefautRPn() {
        return valeurCertifieeDefautRPn;
    }

    /**
     * Sets the value of the valeurCertifieeDefautRPn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurCertifieeDefautRPn(String value) {
        this.valeurCertifieeDefautRPn = value;
    }

    /**
     * Gets the value of the rPn property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRPn() {
        return rPn;
    }

    /**
     * Sets the value of the rPn property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRPn(double value) {
        this.rPn = value;
    }

    /**
     * Gets the value of the pint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPint() {
        return pint;
    }

    /**
     * Sets the value of the pint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPint(double value) {
        this.pint = value;
    }

    /**
     * Gets the value of the valeurCertifieeDefautRPint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurCertifieeDefautRPint() {
        return valeurCertifieeDefautRPint;
    }

    /**
     * Sets the value of the valeurCertifieeDefautRPint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurCertifieeDefautRPint(String value) {
        this.valeurCertifieeDefautRPint = value;
    }

    /**
     * Gets the value of the rPint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRPint() {
        return rPint;
    }

    /**
     * Sets the value of the rPint property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRPint(double value) {
        this.rPint = value;
    }

    /**
     * Gets the value of the chargementChaudiereBois property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getChargementChaudiereBois() {
        return chargementChaudiereBois;
    }

    /**
     * Sets the value of the chargementChaudiereBois property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setChargementChaudiereBois(String value) {
        this.chargementChaudiereBois = value;
    }

    /**
     * Gets the value of the accumulateurGaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getAccumulateurGaz() {
        return accumulateurGaz;
    }

    /**
     * Sets the value of the accumulateurGaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAccumulateurGaz(String value) {
        this.accumulateurGaz = value;
    }

    /**
     * Gets the value of the qVeille property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQVeille() {
        return qVeille;
    }

    /**
     * Sets the value of the qVeille property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQVeille(double value) {
        this.qVeille = value;
    }

    /**
     * Gets the value of the valeurMesureeDefautQPo30 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurMesureeDefautQPo30() {
        return valeurMesureeDefautQPo30;
    }

    /**
     * Sets the value of the valeurMesureeDefautQPo30 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurMesureeDefautQPo30(String value) {
        this.valeurMesureeDefautQPo30 = value;
    }

    /**
     * Gets the value of the qPo30 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQPo30() {
        return qPo30;
    }

    /**
     * Sets the value of the qPo30 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQPo30(double value) {
        this.qPo30 = value;
    }

    /**
     * Gets the value of the valeurMesureeDefautQAuxNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurMesureeDefautQAuxNom() {
        return valeurMesureeDefautQAuxNom;
    }

    /**
     * Sets the value of the valeurMesureeDefautQAuxNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurMesureeDefautQAuxNom(String value) {
        this.valeurMesureeDefautQAuxNom = value;
    }

    /**
     * Gets the value of the qAuxNomGenCombust property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQAuxNomGenCombust() {
        return qAuxNomGenCombust;
    }

    /**
     * Sets the value of the qAuxNomGenCombust property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQAuxNomGenCombust(double value) {
        this.qAuxNomGenCombust = value;
    }

    /**
     * Gets the value of the alimChaudiereBois property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getAlimChaudiereBois() {
        return alimChaudiereBois;
    }

    /**
     * Sets the value of the alimChaudiereBois property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAlimChaudiereBois(String value) {
        this.alimChaudiereBois = value;
    }

    /**
     * Gets the value of the ventilEmission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getVentilEmission() {
        return ventilEmission;
    }

    /**
     * Sets the value of the ventilEmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVentilEmission(String value) {
        this.ventilEmission = value;
    }

    /**
     * Gets the value of the pQpG property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPQpG() {
        return pQpG;
    }

    /**
     * Sets the value of the pQpG property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPQpG(double value) {
        this.pQpG = value;
    }

    /**
     * Gets the value of the pQpGFonc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPQpGFonc() {
        return pQpGFonc;
    }

    /**
     * Sets the value of the pQpGFonc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPQpGFonc(double value) {
        this.pQpGFonc = value;
    }

}
