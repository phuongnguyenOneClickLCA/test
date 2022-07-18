
package com.bionova.optimi.xml.fecRSEnv;

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
public class RTDataGenerateurThermodynamiqueElecAutre {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Cat_Gen", required = true)
    protected String catGen;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Sys_Thermo", required = true)
    protected String sysThermo;
    @XmlElement(name = "Id_Groupe")
    protected int idGroupe;
    @XmlElement(name = "Id_Source_Amont_Ch")
    protected int idSourceAmontCh;
    @XmlElement(name = "Statut_Donnee_Ch", required = true)
    protected String statutDonneeCh;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ch", required = true)
    protected String thetaAvalAirEauCh;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ch", required = true)
    protected String thetaAmontAirEauCh;
    @XmlElement(name = "Theta_Aval_Air_Exterieur_Air_Recycle", required = true)
    protected String thetaAvalAirExterieurAirRecycle;
    @XmlElement(name = "Theta_Amont_Air_Exterieur_Air_Recycle", required = true)
    protected String thetaAmontAirExterieurAirRecycle;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Air_Neuf", required = true)
    protected String thetaAvalAirExtraitAirNeuf;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Air_Neuf", required = true)
    protected String thetaAmontAirExtraitAirNeuf;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Air", required = true)
    protected String thetaAvalEauDeNappeAir;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau", required = true)
    protected String thetaAvalEauDeNappeEau;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Air", required = true)
    protected String thetaAmontEauDeNappeAir;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau", required = true)
    protected String thetaAmontEauDeNappeEau;
    @XmlElement(name = "Theta_Aval_Eau_De_Boucle_Air", required = true)
    protected String thetaAvalEauDeBoucleAir;
    @XmlElement(name = "Theta_Amont_Eau_De_Boucle_Air", required = true)
    protected String thetaAmontEauDeBoucleAir;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau", required = true)
    protected String thetaAvalEauGlycoleeEau;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau", required = true)
    protected String thetaAmontEauGlycoleeEau;
    @XmlElement(name = "COP")
    protected String cop;
    @XmlElement(name = "Pabs_Ch")
    protected String pabsCh;
    @XmlElement(name = "COR_Ch")
    protected String corCh;
    @XmlElement(name = "Statut_Val_Pivot_Ch", required = true)
    protected String statutValPivotCh;
    @XmlElement(name = "Val_Cop")
    protected double valCop;
    @XmlElement(name = "Val_Pabs_Ch")
    protected double valPabsCh;
    @XmlElement(name = "Lim_Theta_Ch", required = true)
    protected String limThetaCh;
    @XmlElement(name = "Theta_Max_Av")
    protected double thetaMaxAv;
    @XmlElement(name = "Theta_Min_Am")
    protected double thetaMinAm;
    @XmlElement(name = "Valeur_Declaree_Defaut_Ch", required = true)
    protected String valeurDeclareeDefautCh;
    @XmlElement(name = "Fonctionnement_Compresseur_Ch", required = true)
    protected String fonctionnementCompresseurCh;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Ch", required = true)
    protected String statutFonctionnementContinuCh;
    @XmlElement(name = "LRcontmin_Ch")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Statut_Taux_Ch", required = true)
    protected String statutTauxCh;
    @XmlElement(name = "Taux_Ch")
    protected double tauxCh;
    @XmlElement(name = "Typo_Emetteur_Ch", required = true)
    protected String typoEmetteurCh;
    @XmlElement(name = "Id_Source_Amont_Fr")
    protected int idSourceAmontFr;
    @XmlElement(name = "Statut_Donnee_Fr", required = true)
    protected String statutDonneeFr;
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    protected String thetaAmontAirEauFr;
    @XmlElement(name = "Theta_Aval_Air_Exterieur_Air_Recycle_Fr", required = true)
    protected String thetaAvalAirExterieurAirRecycleFr;
    @XmlElement(name = "Theta_Amont_Air_Exterieur_Air_Recycle_Fr", required = true)
    protected String thetaAmontAirExterieurAirRecycleFr;
    @XmlElement(name = "Theta_Aval_Eau_Air_Fr", required = true)
    protected String thetaAvalEauAirFr;
    @XmlElement(name = "Theta_Amont_Eau_Air_Fr", required = true)
    protected String thetaAmontEauAirFr;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Fr", required = true)
    protected String thetaAvalEauEauFr;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Fr", required = true)
    protected String thetaAmontEauEauFr;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Air_Neuf_Fr", required = true)
    protected String thetaAvalAirExtraitAirNeufFr;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Air_Neuf_Fr", required = true)
    protected String thetaAmontAirExtraitAirNeufFr;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Air_Fr", required = true)
    protected String thetaAvalEauDeNappeAirFr;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Air_Fr", required = true)
    protected String thetaAmontEauDeNappeAirFr;
    @XmlElement(name = "EER")
    protected String eer;
    @XmlElement(name = "Pabs_Fr")
    protected String pabsFr;
    @XmlElement(name = "COR_Fr")
    protected String corFr;
    @XmlElement(name = "Statut_Val_Pivot_Fr", required = true)
    protected String statutValPivotFr;
    @XmlElement(name = "Val_EER")
    protected double valEER;
    @XmlElement(name = "Val_Pabs_Fr")
    protected double valPabsFr;
    @XmlElement(name = "Lim_Theta_Fr", required = true)
    protected String limThetaFr;
    @XmlElement(name = "Theta_Max_Am")
    protected double thetaMaxAm;
    @XmlElement(name = "Theta_Min_Av")
    protected double thetaMinAv;
    @XmlElement(name = "Valeur_Declaree_Defaut_Fr", required = true)
    protected String valeurDeclareeDefautFr;
    @XmlElement(name = "Fonctionnement_Compresseur_Fr", required = true)
    protected String fonctionnementCompresseurFr;
    @XmlElement(name = "Statut_Fonctionnement_Continu_Fr", required = true)
    protected String statutFonctionnementContinuFr;
    @XmlElement(name = "LRcontmin_Fr")
    protected double lRcontminFr;
    @XmlElement(name = "CCP_LRcontmin_Fr")
    protected double ccplRcontminFr;
    @XmlElement(name = "Statut_Taux_Fr", required = true)
    protected String statutTauxFr;
    @XmlElement(name = "Taux_Fr")
    protected double tauxFr;
    @XmlElement(name = "Typo_Emetteur_Fr", required = true)
    protected String typoEmetteurFr;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the rdim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
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
    public void setCatGen(String value) {
        this.catGen = value;
    }

    /**
     * Gets the value of the idprioriteCh property.
     * 
     */
    public int getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     */
    public void setIdprioriteCh(int value) {
        this.idprioriteCh = value;
    }

    /**
     * Gets the value of the idprioriteFr property.
     * 
     */
    public int getIdprioriteFr() {
        return idprioriteFr;
    }

    /**
     * Sets the value of the idprioriteFr property.
     * 
     */
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
    public void setSysThermo(String value) {
        this.sysThermo = value;
    }

    /**
     * Gets the value of the idGroupe property.
     * 
     */
    public int getIdGroupe() {
        return idGroupe;
    }

    /**
     * Sets the value of the idGroupe property.
     * 
     */
    public void setIdGroupe(int value) {
        this.idGroupe = value;
    }

    /**
     * Gets the value of the idSourceAmontCh property.
     * 
     */
    public int getIdSourceAmontCh() {
        return idSourceAmontCh;
    }

    /**
     * Sets the value of the idSourceAmontCh property.
     * 
     */
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
    public void setStatutValPivotCh(String value) {
        this.statutValPivotCh = value;
    }

    /**
     * Gets the value of the valCop property.
     * 
     */
    public double getValCop() {
        return valCop;
    }

    /**
     * Sets the value of the valCop property.
     * 
     */
    public void setValCop(double value) {
        this.valCop = value;
    }

    /**
     * Gets the value of the valPabsCh property.
     * 
     */
    public double getValPabsCh() {
        return valPabsCh;
    }

    /**
     * Sets the value of the valPabsCh property.
     * 
     */
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
    public void setLimThetaCh(String value) {
        this.limThetaCh = value;
    }

    /**
     * Gets the value of the thetaMaxAv property.
     * 
     */
    public double getThetaMaxAv() {
        return thetaMaxAv;
    }

    /**
     * Sets the value of the thetaMaxAv property.
     * 
     */
    public void setThetaMaxAv(double value) {
        this.thetaMaxAv = value;
    }

    /**
     * Gets the value of the thetaMinAm property.
     * 
     */
    public double getThetaMinAm() {
        return thetaMinAm;
    }

    /**
     * Sets the value of the thetaMinAm property.
     * 
     */
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
    public void setStatutFonctionnementContinuCh(String value) {
        this.statutFonctionnementContinuCh = value;
    }

    /**
     * Gets the value of the lRcontminCh property.
     * 
     */
    public double getLRcontminCh() {
        return lRcontminCh;
    }

    /**
     * Sets the value of the lRcontminCh property.
     * 
     */
    public void setLRcontminCh(double value) {
        this.lRcontminCh = value;
    }

    /**
     * Gets the value of the ccplRcontminCh property.
     * 
     */
    public double getCCPLRcontminCh() {
        return ccplRcontminCh;
    }

    /**
     * Sets the value of the ccplRcontminCh property.
     * 
     */
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
    public void setStatutTauxCh(String value) {
        this.statutTauxCh = value;
    }

    /**
     * Gets the value of the tauxCh property.
     * 
     */
    public double getTauxCh() {
        return tauxCh;
    }

    /**
     * Sets the value of the tauxCh property.
     * 
     */
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
    public void setTypoEmetteurCh(String value) {
        this.typoEmetteurCh = value;
    }

    /**
     * Gets the value of the idSourceAmontFr property.
     * 
     */
    public int getIdSourceAmontFr() {
        return idSourceAmontFr;
    }

    /**
     * Sets the value of the idSourceAmontFr property.
     * 
     */
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
    public void setStatutValPivotFr(String value) {
        this.statutValPivotFr = value;
    }

    /**
     * Gets the value of the valEER property.
     * 
     */
    public double getValEER() {
        return valEER;
    }

    /**
     * Sets the value of the valEER property.
     * 
     */
    public void setValEER(double value) {
        this.valEER = value;
    }

    /**
     * Gets the value of the valPabsFr property.
     * 
     */
    public double getValPabsFr() {
        return valPabsFr;
    }

    /**
     * Sets the value of the valPabsFr property.
     * 
     */
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
    public void setLimThetaFr(String value) {
        this.limThetaFr = value;
    }

    /**
     * Gets the value of the thetaMaxAm property.
     * 
     */
    public double getThetaMaxAm() {
        return thetaMaxAm;
    }

    /**
     * Sets the value of the thetaMaxAm property.
     * 
     */
    public void setThetaMaxAm(double value) {
        this.thetaMaxAm = value;
    }

    /**
     * Gets the value of the thetaMinAv property.
     * 
     */
    public double getThetaMinAv() {
        return thetaMinAv;
    }

    /**
     * Sets the value of the thetaMinAv property.
     * 
     */
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
    public void setStatutFonctionnementContinuFr(String value) {
        this.statutFonctionnementContinuFr = value;
    }

    /**
     * Gets the value of the lRcontminFr property.
     * 
     */
    public double getLRcontminFr() {
        return lRcontminFr;
    }

    /**
     * Sets the value of the lRcontminFr property.
     * 
     */
    public void setLRcontminFr(double value) {
        this.lRcontminFr = value;
    }

    /**
     * Gets the value of the ccplRcontminFr property.
     * 
     */
    public double getCCPLRcontminFr() {
        return ccplRcontminFr;
    }

    /**
     * Sets the value of the ccplRcontminFr property.
     * 
     */
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
    public void setStatutTauxFr(String value) {
        this.statutTauxFr = value;
    }

    /**
     * Gets the value of the tauxFr property.
     * 
     */
    public double getTauxFr() {
        return tauxFr;
    }

    /**
     * Sets the value of the tauxFr property.
     * 
     */
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

}
