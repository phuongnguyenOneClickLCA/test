
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Thermodynamique_Elec_Autre complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Thermodynamique_Elec_Autre">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Cat_Gen" type="{}E_Cat_Gen_Thermo_Elec_Rev"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo" type="{}E_Systeme_Thermodynamique_Electrique_Reversible"/>
 *         &lt;element name="Id_Groupe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_Ch" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ch" type="{}E_Temperatures_Aval_Air_Eau_Ch"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ch" type="{}E_Temperatures_Amont_Air_Eau_Ch"/>
 *         &lt;element name="Theta_Aval_Air_Exterieur_Air_Recycle" type="{}E_Temperatures_Aval_Air_Extrait_Air_Recycle"/>
 *         &lt;element name="Theta_Amont_Air_Exterieur_Air_Recycle" type="{}E_Temperatures_Amont_Air_Extrait_Air_Recycle"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Air_Neuf" type="{}E_Temperatures_Aval_Air_Extrait_Air_Neuf"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Air_Neuf" type="{}E_Temperatures_Amont_Air_Extrait_Air_Neuf"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Air" type="{}E_Temperatures_Aval_Eau_De_Nappe_Air"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Eau" type="{}E_Temperatures_Aval_Eau_De_Nappe_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Air" type="{}E_Temperatures_Amont_Eau_De_Nappe_Air"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Eau" type="{}E_Temperatures_Amont_Eau_De_Nappe_Eau"/>
 *         &lt;element name="Theta_Aval_Eau_De_Boucle_Air" type="{}E_Temperatures_Aval_Eau_De_Boucle_Air"/>
 *         &lt;element name="Theta_Amont_Eau_De_Boucle_Air" type="{}E_Temperatures_Amont_Eau_De_Boucle_Air"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau"/>
 *         &lt;element name="COP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Cop" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Ch" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Declaree_Defaut_Ch" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur_Ch" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Ch" type="{}E_Emetteur"/>
 *         &lt;element name="Id_Source_Amont_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_Fr" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Fr" type="{}E_Temperatures_Aval_Air_Eau_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Fr" type="{}E_Temperatures_Amont_Air_Eau_Fr"/>
 *         &lt;element name="Theta_Aval_Air_Exterieur_Air_Recycle_Fr" type="{}E_Temperatures_Aval_Air_Exterieur_Air_Recycle_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Exterieur_Air_Recycle_Fr" type="{}E_Temperatures_Amont_Air_Exterieur_Air_Recycle_Fr"/>
 *         &lt;element name="Theta_Aval_Eau_Air_Fr" type="{}E_Temperatures_Aval_Eau_Air_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_Air_Fr" type="{}E_Temperatures_Amont_Eau_Air_Fr"/>
 *         &lt;element name="Theta_Aval_Eau_Eau_Fr" type="{}E_Temperatures_Aval_Eau_Eau_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_Eau_Fr" type="{}E_Temperatures_Amont_Eau_Eau_Fr"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Air_Neuf_Fr" type="{}E_Temperatures_Aval_Air_Extrait_Air_Neuf_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Air_Neuf_Fr" type="{}E_Temperatures_Amont_Air_Extrait_Air_Neuf_Fr"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Air_Fr" type="{}E_Temperatures_Aval_Eau_De_Nappe_Air_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Air_Fr" type="{}E_Temperatures_Amont_Eau_De_Nappe_Air_Fr"/>
 *         &lt;element name="EER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_EER" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta_Fr" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Declaree_Defaut_Fr" type="{}E_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur_Fr" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur_Fr" type="{}E_Emetteur"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Eau_Fr" type="{}E_Temperatures_Aval_Eau_De_Nappe_Eau_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Eau_Fr" type="{}E_Temperatures_Amont_Eau_De_Nappe_Eau_Fr"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Thermodynamique_Elec_Autre", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataGenerateurThermodynamiqueElecAutre {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Rdim")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Integer rdim;
    @XmlElement(name = "Cat_Gen", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String catGen;
    @XmlElement(name = "Idpriorite_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idprioriteFr;
    @XmlElement(name = "Sys_Thermo", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String sysThermo;
    @XmlElement(name = "Id_Groupe")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idGroupe;
    @XmlElement(name = "Id_Source_Amont_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idSourceAmontCh;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDonneeCh;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirEauCh;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirEauCh;
    @XmlElement(name = "Theta_Aval_Air_Exterieur_Air_Recycle", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirExterieurAirRecycle;
    @XmlElement(name = "Theta_Amont_Air_Exterieur_Air_Recycle", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirExterieurAirRecycle;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Air_Neuf", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirExtraitAirNeuf;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Air_Neuf", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirExtraitAirNeuf;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Air", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauDeNappeAir;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauDeNappeEau;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Air", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauDeNappeAir;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauDeNappeEau;
    @XmlElement(name = "Theta_Aval_Eau_De_Boucle_Air", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauDeBoucleAir;
    @XmlElement(name = "Theta_Amont_Eau_De_Boucle_Air", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauDeBoucleAir;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauGlycoleeEau;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauGlycoleeEau;
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
    @XmlElement(name = "Val_Cop")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valCop;
    @XmlElement(name = "Val_Pabs_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valPabsCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxAv;
    @XmlElement(name = "Theta_Min_Am")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinAm;
    @XmlElement(name = "Valeur_Declaree_Defaut_Ch", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurDeclareeDefautCh;
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
    @XmlElement(name = "Id_Source_Amont_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idSourceAmontFr;
    @XmlElement(name = "Statut_Donnee_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutDonneeFr;
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirEauFr;
    @XmlElement(name = "Theta_Aval_Air_Exterieur_Air_Recycle_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirExterieurAirRecycleFr;
    @XmlElement(name = "Theta_Amont_Air_Exterieur_Air_Recycle_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirExterieurAirRecycleFr;
    @XmlElement(name = "Theta_Aval_Eau_Air_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauAirFr;
    @XmlElement(name = "Theta_Amont_Eau_Air_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauAirFr;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauEauFr;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauEauFr;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Air_Neuf_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalAirExtraitAirNeufFr;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Air_Neuf_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontAirExtraitAirNeufFr;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Air_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauDeNappeAirFr;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Air_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauDeNappeAirFr;
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
    @XmlElement(name = "Theta_Max_Am")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMaxAm;
    @XmlElement(name = "Theta_Min_Av")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaMinAv;
    @XmlElement(name = "Valeur_Declaree_Defaut_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurDeclareeDefautFr;
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
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAvalEauDeNappeEauFr;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau_Fr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String thetaAmontEauDeNappeEauFr;

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
     * Gets the value of the rdim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Integer getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRdim(Integer value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the catGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCatGen() {
        return catGen;
    }

    /**
     * Sets the value of the catGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCatGen(String value) {
        this.catGen = value;
    }

    /**
     * Gets the value of the idprioriteCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdprioriteCh(int value) {
        this.idprioriteCh = value;
    }

    /**
     * Gets the value of the idprioriteFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdprioriteFr() {
        return idprioriteFr;
    }

    /**
     * Sets the value of the idprioriteFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdprioriteFr(int value) {
        this.idprioriteFr = value;
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
     * Gets the value of the idGroupe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdGroupe() {
        return idGroupe;
    }

    /**
     * Sets the value of the idGroupe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdGroupe(int value) {
        this.idGroupe = value;
    }

    /**
     * Gets the value of the idSourceAmontCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdSourceAmontCh() {
        return idSourceAmontCh;
    }

    /**
     * Sets the value of the idSourceAmontCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdSourceAmontCh(int value) {
        this.idSourceAmontCh = value;
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
     * Gets the value of the thetaAvalAirExterieurAirRecycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalAirExterieurAirRecycle() {
        return thetaAvalAirExterieurAirRecycle;
    }

    /**
     * Sets the value of the thetaAvalAirExterieurAirRecycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalAirExterieurAirRecycle(String value) {
        this.thetaAvalAirExterieurAirRecycle = value;
    }

    /**
     * Gets the value of the thetaAmontAirExterieurAirRecycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontAirExterieurAirRecycle() {
        return thetaAmontAirExterieurAirRecycle;
    }

    /**
     * Sets the value of the thetaAmontAirExterieurAirRecycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontAirExterieurAirRecycle(String value) {
        this.thetaAmontAirExterieurAirRecycle = value;
    }

    /**
     * Gets the value of the thetaAvalAirExtraitAirNeuf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalAirExtraitAirNeuf() {
        return thetaAvalAirExtraitAirNeuf;
    }

    /**
     * Sets the value of the thetaAvalAirExtraitAirNeuf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalAirExtraitAirNeuf(String value) {
        this.thetaAvalAirExtraitAirNeuf = value;
    }

    /**
     * Gets the value of the thetaAmontAirExtraitAirNeuf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontAirExtraitAirNeuf() {
        return thetaAmontAirExtraitAirNeuf;
    }

    /**
     * Sets the value of the thetaAmontAirExtraitAirNeuf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontAirExtraitAirNeuf(String value) {
        this.thetaAmontAirExtraitAirNeuf = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeNappeAir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauDeNappeAir() {
        return thetaAvalEauDeNappeAir;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeAir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauDeNappeAir(String value) {
        this.thetaAvalEauDeNappeAir = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeNappeEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauDeNappeEau() {
        return thetaAvalEauDeNappeEau;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauDeNappeEau(String value) {
        this.thetaAvalEauDeNappeEau = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeAir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauDeNappeAir() {
        return thetaAmontEauDeNappeAir;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeAir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauDeNappeAir(String value) {
        this.thetaAmontEauDeNappeAir = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauDeNappeEau() {
        return thetaAmontEauDeNappeEau;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauDeNappeEau(String value) {
        this.thetaAmontEauDeNappeEau = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeBoucleAir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauDeBoucleAir() {
        return thetaAvalEauDeBoucleAir;
    }

    /**
     * Sets the value of the thetaAvalEauDeBoucleAir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauDeBoucleAir(String value) {
        this.thetaAvalEauDeBoucleAir = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeBoucleAir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauDeBoucleAir() {
        return thetaAmontEauDeBoucleAir;
    }

    /**
     * Sets the value of the thetaAmontEauDeBoucleAir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauDeBoucleAir(String value) {
        this.thetaAmontEauDeBoucleAir = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauGlycoleeEau() {
        return thetaAvalEauGlycoleeEau;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauGlycoleeEau(String value) {
        this.thetaAvalEauGlycoleeEau = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauGlycoleeEau() {
        return thetaAmontEauGlycoleeEau;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauGlycoleeEau(String value) {
        this.thetaAmontEauGlycoleeEau = value;
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
     * Gets the value of the valCop property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValCop() {
        return valCop;
    }

    /**
     * Sets the value of the valCop property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValCop(double value) {
        this.valCop = value;
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
     * Gets the value of the thetaMaxAv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxAv() {
        return thetaMaxAv;
    }

    /**
     * Sets the value of the thetaMaxAv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxAv(double value) {
        this.thetaMaxAv = value;
    }

    /**
     * Gets the value of the thetaMinAm property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinAm() {
        return thetaMinAm;
    }

    /**
     * Sets the value of the thetaMinAm property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinAm(double value) {
        this.thetaMinAm = value;
    }

    /**
     * Gets the value of the valeurDeclareeDefautCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurDeclareeDefautCh() {
        return valeurDeclareeDefautCh;
    }

    /**
     * Sets the value of the valeurDeclareeDefautCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurDeclareeDefautCh(String value) {
        this.valeurDeclareeDefautCh = value;
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
     * Gets the value of the idSourceAmontFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdSourceAmontFr() {
        return idSourceAmontFr;
    }

    /**
     * Sets the value of the idSourceAmontFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdSourceAmontFr(int value) {
        this.idSourceAmontFr = value;
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
     * Gets the value of the thetaAvalAirExterieurAirRecycleFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalAirExterieurAirRecycleFr() {
        return thetaAvalAirExterieurAirRecycleFr;
    }

    /**
     * Sets the value of the thetaAvalAirExterieurAirRecycleFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalAirExterieurAirRecycleFr(String value) {
        this.thetaAvalAirExterieurAirRecycleFr = value;
    }

    /**
     * Gets the value of the thetaAmontAirExterieurAirRecycleFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontAirExterieurAirRecycleFr() {
        return thetaAmontAirExterieurAirRecycleFr;
    }

    /**
     * Sets the value of the thetaAmontAirExterieurAirRecycleFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontAirExterieurAirRecycleFr(String value) {
        this.thetaAmontAirExterieurAirRecycleFr = value;
    }

    /**
     * Gets the value of the thetaAvalEauAirFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauAirFr() {
        return thetaAvalEauAirFr;
    }

    /**
     * Sets the value of the thetaAvalEauAirFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauAirFr(String value) {
        this.thetaAvalEauAirFr = value;
    }

    /**
     * Gets the value of the thetaAmontEauAirFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauAirFr() {
        return thetaAmontEauAirFr;
    }

    /**
     * Sets the value of the thetaAmontEauAirFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauAirFr(String value) {
        this.thetaAmontEauAirFr = value;
    }

    /**
     * Gets the value of the thetaAvalEauEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauEauFr() {
        return thetaAvalEauEauFr;
    }

    /**
     * Sets the value of the thetaAvalEauEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauEauFr(String value) {
        this.thetaAvalEauEauFr = value;
    }

    /**
     * Gets the value of the thetaAmontEauEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauEauFr() {
        return thetaAmontEauEauFr;
    }

    /**
     * Sets the value of the thetaAmontEauEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauEauFr(String value) {
        this.thetaAmontEauEauFr = value;
    }

    /**
     * Gets the value of the thetaAvalAirExtraitAirNeufFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalAirExtraitAirNeufFr() {
        return thetaAvalAirExtraitAirNeufFr;
    }

    /**
     * Sets the value of the thetaAvalAirExtraitAirNeufFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalAirExtraitAirNeufFr(String value) {
        this.thetaAvalAirExtraitAirNeufFr = value;
    }

    /**
     * Gets the value of the thetaAmontAirExtraitAirNeufFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontAirExtraitAirNeufFr() {
        return thetaAmontAirExtraitAirNeufFr;
    }

    /**
     * Sets the value of the thetaAmontAirExtraitAirNeufFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontAirExtraitAirNeufFr(String value) {
        this.thetaAmontAirExtraitAirNeufFr = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeNappeAirFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauDeNappeAirFr() {
        return thetaAvalEauDeNappeAirFr;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeAirFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauDeNappeAirFr(String value) {
        this.thetaAvalEauDeNappeAirFr = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeAirFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauDeNappeAirFr() {
        return thetaAmontEauDeNappeAirFr;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeAirFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauDeNappeAirFr(String value) {
        this.thetaAmontEauDeNappeAirFr = value;
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
     * Gets the value of the thetaMaxAm property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMaxAm() {
        return thetaMaxAm;
    }

    /**
     * Sets the value of the thetaMaxAm property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMaxAm(double value) {
        this.thetaMaxAm = value;
    }

    /**
     * Gets the value of the thetaMinAv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaMinAv() {
        return thetaMinAv;
    }

    /**
     * Sets the value of the thetaMinAv property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaMinAv(double value) {
        this.thetaMinAv = value;
    }

    /**
     * Gets the value of the valeurDeclareeDefautFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurDeclareeDefautFr() {
        return valeurDeclareeDefautFr;
    }

    /**
     * Sets the value of the valeurDeclareeDefautFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurDeclareeDefautFr(String value) {
        this.valeurDeclareeDefautFr = value;
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
     * Gets the value of the thetaAvalEauDeNappeEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAvalEauDeNappeEauFr() {
        return thetaAvalEauDeNappeEauFr;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAvalEauDeNappeEauFr(String value) {
        this.thetaAvalEauDeNappeEauFr = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeEauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getThetaAmontEauDeNappeEauFr() {
        return thetaAmontEauDeNappeEauFr;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeEauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaAmontEauDeNappeEauFr(String value) {
        this.thetaAmontEauDeNappeEauFr = value;
    }

}
