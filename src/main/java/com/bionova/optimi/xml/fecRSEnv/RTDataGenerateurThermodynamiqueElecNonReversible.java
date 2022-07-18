
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * new version v8000
 * 
 * <p>Java class for RT_Data_Generateur_Thermodynamique_Elec_NonReversible complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Thermodynamique_Elec_NonReversible">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_FouGen_Mod" type="{}E_Id_FouGen_Mode7"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sys_Thermo_Ch" type="{}E_Systeme_Thermodynamique_Chauffage"/>
 *         &lt;element name="Sys_Thermo_Fr" type="{}E_Systeme_Thermodynamique_Refroidissement"/>
 *         &lt;element name="Sys_Thermo_Ecs" type="{}E_Systeme_Thermodynamique_Ecs"/>
 *         &lt;element name="Id_Groupe" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ch" type="{}E_Temperatures_Aval_Air_Eau_Ch"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ch" type="{}E_Temperatures_Amont_Air_Eau_Ch"/>
 *         &lt;element name="Theta_Aval_Air_Exterieur_Air_Recycle" type="{}E_Temperatures_Aval_Air_Extrait_Air_Recycle"/>
 *         &lt;element name="Theta_Amont_Air_Exterieur_Air_Recycle" type="{}E_Temperatures_Amont_Air_Extrait_Air_Recycle"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Air_Neuf" type="{}E_Temperatures_Aval_Air_Extrait_Air_Neuf"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Air_Neuf" type="{}E_Temperatures_Amont_Air_Extrait_Air_Neuf"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Eau" type="{}E_Temperatures_Aval_Eau_De_Nappe_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Eau" type="{}E_Temperatures_Amont_Eau_De_Nappe_Eau"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau" type="{}E_Temperatures_Aval_Eau_Glycolee_Eau"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau" type="{}E_Temperatures_Amont_Eau_Glycolee_Eau"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Air" type="{}E_Temperatures_Aval_Eau_De_Nappe_Air"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Air" type="{}E_Temperatures_Amont_Eau_De_Nappe_Air"/>
 *         &lt;element name="Theta_Aval_Eau_De_Boucle_Air" type="{}E_Temperatures_Aval_Eau_De_Boucle_Air"/>
 *         &lt;element name="Theta_Amont_Eau_De_Boucle_Air" type="{}E_Temperatures_Amont_Eau_De_Boucle_Air"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Extrait_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Extrait_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Aval_Air_Ambiant_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Air_Ambiant_Eau_Ecs" type="{}E_Temperatures_Amont_Air_Ambiant_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Eau_Ecs" type="{}E_Temperatures_Aval_Eau_De_Nappe_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Eau_Ecs" type="{}E_Temperatures_Amont_Eau_De_Nappe_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Air_Eau_Fr" type="{}E_Temperatures_Aval_Air_Eau_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Eau_Fr" type="{}E_Temperatures_Amont_Air_Eau_Fr"/>
 *         &lt;element name="Theta_Aval_Air_Exterieur_Air_Recycle_Fr" type="{}E_Temperatures_Aval_Air_Exterieur_Air_Recycle_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Exterieur_Air_Recycle_Fr" type="{}E_Temperatures_Amont_Air_Exterieur_Air_Recycle_Fr"/>
 *         &lt;element name="Theta_Aval_Eau_De_Nappe_Air_Fr" type="{}E_Temperatures_Aval_Eau_De_Nappe_Air_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_De_Nappe_Air_Fr" type="{}E_Temperatures_Amont_Eau_De_Nappe_Air_Fr"/>
 *         &lt;element name="Theta_Aval_Air_Extrait_Air_Neuf_Fr" type="{}E_Temperatures_Aval_Air_Extrait_Air_Neuf_Fr"/>
 *         &lt;element name="Theta_Amont_Air_Extrait_Air_Neuf_Fr" type="{}E_Temperatures_Amont_Air_Extrait_Air_Neuf_Fr"/>
 *         &lt;element name="Theta_Aval_Eau_Eau_Fr" type="{}E_Temperatures_Aval_Eau_Eau_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_Eau_Fr" type="{}E_Temperatures_Amont_Eau_Eau_Fr"/>
 *         &lt;element name="Theta_Aval_Eau_Air_Fr" type="{}E_Temperatures_Aval_Eau_Air_Fr"/>
 *         &lt;element name="Theta_Amont_Eau_Air_Fr" type="{}E_Temperatures_Amont_Eau_Air_Fr"/>
 *         &lt;element name="Theta_Aval_Sol_Eau_Ch" type="{}E_Temperatures_Aval_Sol_Eau_Ch"/>
 *         &lt;element name="Theta_Amont_Sol_Eau_Ch" type="{}E_Temperatures_Amont_Sol_Eau_Ch"/>
 *         &lt;element name="Theta_Aval_Sol_Sol_Ch" type="{}E_Temperatures_Aval_Sol_Sol_Ch"/>
 *         &lt;element name="Theta_Amont_Sol_Sol_Ch" type="{}E_Temperatures_Amont_Sol_Sol_Ch"/>
 *         &lt;element name="Theta_Aval_Sol_Eau_Ecs" type="{}E_Temperatures_Aval_Sol_Eau_Ecs"/>
 *         &lt;element name="Theta_Amont_Sol_Eau_Ecs" type="{}E_Temperatures_Amont_Sol_Eau_Ecs"/>
 *         &lt;element name="Theta_Aval_Eau_Glycolee_Eau_Ecs" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Theta_Amont_Eau_Glycolee_Eau_Ecs" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Performance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="COR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_Pivot" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Cop" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lim_Theta" type="{}E_Lim_T"/>
 *         &lt;element name="Theta_Max_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Max_Am" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Min_Av" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Declaree_Defaut" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Fonctionnement_Compresseur" type="{}E_Fonctionnement_Compresseur"/>
 *         &lt;element name="Statut_Fonctionnement_Continu" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Taux" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Taux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Typo_Emetteur" type="{}E_Emetteur"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Thermodynamique_Elec_NonReversible", propOrder = {

})
public class RTDataGenerateurThermodynamiqueElecNonReversible {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Id_FouGen_Mod", required = true)
    protected String idFouGenMod;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Sys_Thermo_Ch", required = true)
    protected String sysThermoCh;
    @XmlElement(name = "Sys_Thermo_Fr", required = true)
    protected String sysThermoFr;
    @XmlElement(name = "Sys_Thermo_Ecs", required = true)
    protected String sysThermoEcs;
    @XmlElement(name = "Id_Groupe")
    protected int idGroupe;
    @XmlElement(name = "Statut_Donnee", required = true)
    protected String statutDonnee;
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
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau", required = true)
    protected String thetaAvalEauDeNappeEau;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau", required = true)
    protected String thetaAmontEauDeNappeEau;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau", required = true)
    protected String thetaAvalEauGlycoleeEau;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau", required = true)
    protected String thetaAmontEauGlycoleeEau;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Air", required = true)
    protected String thetaAvalEauDeNappeAir;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Air", required = true)
    protected String thetaAmontEauDeNappeAir;
    @XmlElement(name = "Theta_Aval_Eau_De_Boucle_Air", required = true)
    protected String thetaAvalEauDeBoucleAir;
    @XmlElement(name = "Theta_Amont_Eau_De_Boucle_Air", required = true)
    protected String thetaAmontEauDeBoucleAir;
    @XmlElement(name = "Theta_Aval_Air_Eau_Ecs", required = true)
    protected String thetaAvalAirEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Eau_Ecs", required = true)
    protected String thetaAmontAirEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Eau_Ecs", required = true)
    protected String thetaAvalAirExtraitEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Eau_Ecs", required = true)
    protected String thetaAmontAirExtraitEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Ambiant_Eau_Ecs", required = true)
    protected String thetaAvalAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Amont_Air_Ambiant_Eau_Ecs", required = true)
    protected String thetaAmontAirAmbiantEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Eau_Ecs", required = true)
    protected String thetaAvalEauDeNappeEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Eau_Ecs", required = true)
    protected String thetaAmontEauDeNappeEauEcs;
    @XmlElement(name = "Theta_Aval_Air_Eau_Fr", required = true)
    protected String thetaAvalAirEauFr;
    @XmlElement(name = "Theta_Amont_Air_Eau_Fr", required = true)
    protected String thetaAmontAirEauFr;
    @XmlElement(name = "Theta_Aval_Air_Exterieur_Air_Recycle_Fr", required = true)
    protected String thetaAvalAirExterieurAirRecycleFr;
    @XmlElement(name = "Theta_Amont_Air_Exterieur_Air_Recycle_Fr", required = true)
    protected String thetaAmontAirExterieurAirRecycleFr;
    @XmlElement(name = "Theta_Aval_Eau_De_Nappe_Air_Fr", required = true)
    protected String thetaAvalEauDeNappeAirFr;
    @XmlElement(name = "Theta_Amont_Eau_De_Nappe_Air_Fr", required = true)
    protected String thetaAmontEauDeNappeAirFr;
    @XmlElement(name = "Theta_Aval_Air_Extrait_Air_Neuf_Fr", required = true)
    protected String thetaAvalAirExtraitAirNeufFr;
    @XmlElement(name = "Theta_Amont_Air_Extrait_Air_Neuf_Fr", required = true)
    protected String thetaAmontAirExtraitAirNeufFr;
    @XmlElement(name = "Theta_Aval_Eau_Eau_Fr", required = true)
    protected String thetaAvalEauEauFr;
    @XmlElement(name = "Theta_Amont_Eau_Eau_Fr", required = true)
    protected String thetaAmontEauEauFr;
    @XmlElement(name = "Theta_Aval_Eau_Air_Fr", required = true)
    protected String thetaAvalEauAirFr;
    @XmlElement(name = "Theta_Amont_Eau_Air_Fr", required = true)
    protected String thetaAmontEauAirFr;
    @XmlElement(name = "Theta_Aval_Sol_Eau_Ch", required = true)
    protected String thetaAvalSolEauCh;
    @XmlElement(name = "Theta_Amont_Sol_Eau_Ch", required = true)
    protected String thetaAmontSolEauCh;
    @XmlElement(name = "Theta_Aval_Sol_Sol_Ch", required = true)
    protected String thetaAvalSolSolCh;
    @XmlElement(name = "Theta_Amont_Sol_Sol_Ch", required = true)
    protected String thetaAmontSolSolCh;
    @XmlElement(name = "Theta_Aval_Sol_Eau_Ecs", required = true)
    protected String thetaAvalSolEauEcs;
    @XmlElement(name = "Theta_Amont_Sol_Eau_Ecs", required = true)
    protected String thetaAmontSolEauEcs;
    @XmlElement(name = "Theta_Aval_Eau_Glycolee_Eau_Ecs")
    protected Integer thetaAvalEauGlycoleeEauEcs;
    @XmlElement(name = "Theta_Amont_Eau_Glycolee_Eau_Ecs")
    protected Integer thetaAmontEauGlycoleeEauEcs;
    @XmlElement(name = "Performance")
    protected String performance;
    @XmlElement(name = "Pabs")
    protected String pabs;
    @XmlElement(name = "COR")
    protected String cor;
    @XmlElement(name = "Statut_Val_Pivot", required = true)
    protected String statutValPivot;
    @XmlElement(name = "Val_Cop")
    protected double valCop;
    @XmlElement(name = "Val_Pabs")
    protected double valPabs;
    @XmlElement(name = "Lim_Theta", required = true)
    protected String limTheta;
    @XmlElement(name = "Theta_Max_Av")
    protected double thetaMaxAv;
    @XmlElement(name = "Theta_Min_Am")
    protected double thetaMinAm;
    @XmlElement(name = "Theta_Max_Am")
    protected double thetaMaxAm;
    @XmlElement(name = "Theta_Min_Av")
    protected double thetaMinAv;
    @XmlElement(name = "Valeur_Declaree_Defaut", required = true)
    protected String valeurDeclareeDefaut;
    @XmlElement(name = "Fonctionnement_Compresseur", required = true)
    protected String fonctionnementCompresseur;
    @XmlElement(name = "Statut_Fonctionnement_Continu", required = true)
    protected String statutFonctionnementContinu;
    @XmlElement(name = "LRcontmin")
    protected double lRcontmin;
    @XmlElement(name = "CCP_LRcontmin")
    protected double ccplRcontmin;
    @XmlElement(name = "Statut_Taux", required = true)
    protected String statutTaux;
    @XmlElement(name = "Taux")
    protected double taux;
    @XmlElement(name = "Typo_Emetteur", required = true)
    protected String typoEmetteur;

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
     * Gets the value of the rdim property.
     * 
     */
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    public void setRdim(int value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the idSourceAmont property.
     * 
     */
    public int getIdSourceAmont() {
        return idSourceAmont;
    }

    /**
     * Sets the value of the idSourceAmont property.
     * 
     */
    public void setIdSourceAmont(int value) {
        this.idSourceAmont = value;
    }

    /**
     * Gets the value of the idFouGenMod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouGenMod() {
        return idFouGenMod;
    }

    /**
     * Sets the value of the idFouGenMod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouGenMod(String value) {
        this.idFouGenMod = value;
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
     * Gets the value of the idprioriteEcs property.
     * 
     */
    public int getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     */
    public void setIdprioriteEcs(int value) {
        this.idprioriteEcs = value;
    }

    /**
     * Gets the value of the sysThermoCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setSysThermoCh(String value) {
        this.sysThermoCh = value;
    }

    /**
     * Gets the value of the sysThermoFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoFr() {
        return sysThermoFr;
    }

    /**
     * Sets the value of the sysThermoFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoFr(String value) {
        this.sysThermoFr = value;
    }

    /**
     * Gets the value of the sysThermoEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysThermoEcs() {
        return sysThermoEcs;
    }

    /**
     * Sets the value of the sysThermoEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysThermoEcs(String value) {
        this.sysThermoEcs = value;
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
     * Gets the value of the statutDonnee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonnee() {
        return statutDonnee;
    }

    /**
     * Sets the value of the statutDonnee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonnee(String value) {
        this.statutDonnee = value;
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
     * Gets the value of the thetaAvalAirEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirEauEcs() {
        return thetaAvalAirEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirEauEcs(String value) {
        this.thetaAvalAirEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirEauEcs() {
        return thetaAmontAirEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirEauEcs(String value) {
        this.thetaAmontAirEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirExtraitEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirExtraitEauEcs() {
        return thetaAvalAirExtraitEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirExtraitEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirExtraitEauEcs(String value) {
        this.thetaAvalAirExtraitEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirExtraitEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirExtraitEauEcs() {
        return thetaAmontAirExtraitEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirExtraitEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirExtraitEauEcs(String value) {
        this.thetaAmontAirExtraitEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalAirAmbiantEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalAirAmbiantEauEcs() {
        return thetaAvalAirAmbiantEauEcs;
    }

    /**
     * Sets the value of the thetaAvalAirAmbiantEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalAirAmbiantEauEcs(String value) {
        this.thetaAvalAirAmbiantEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontAirAmbiantEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontAirAmbiantEauEcs() {
        return thetaAmontAirAmbiantEauEcs;
    }

    /**
     * Sets the value of the thetaAmontAirAmbiantEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontAirAmbiantEauEcs(String value) {
        this.thetaAmontAirAmbiantEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauDeNappeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalEauDeNappeEauEcs() {
        return thetaAvalEauDeNappeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauDeNappeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalEauDeNappeEauEcs(String value) {
        this.thetaAvalEauDeNappeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauDeNappeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontEauDeNappeEauEcs() {
        return thetaAmontEauDeNappeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauDeNappeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontEauDeNappeEauEcs(String value) {
        this.thetaAmontEauDeNappeEauEcs = value;
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
     * Gets the value of the thetaAvalSolEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalSolEauCh() {
        return thetaAvalSolEauCh;
    }

    /**
     * Sets the value of the thetaAvalSolEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalSolEauCh(String value) {
        this.thetaAvalSolEauCh = value;
    }

    /**
     * Gets the value of the thetaAmontSolEauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontSolEauCh() {
        return thetaAmontSolEauCh;
    }

    /**
     * Sets the value of the thetaAmontSolEauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontSolEauCh(String value) {
        this.thetaAmontSolEauCh = value;
    }

    /**
     * Gets the value of the thetaAvalSolSolCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalSolSolCh() {
        return thetaAvalSolSolCh;
    }

    /**
     * Sets the value of the thetaAvalSolSolCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalSolSolCh(String value) {
        this.thetaAvalSolSolCh = value;
    }

    /**
     * Gets the value of the thetaAmontSolSolCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontSolSolCh() {
        return thetaAmontSolSolCh;
    }

    /**
     * Sets the value of the thetaAmontSolSolCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontSolSolCh(String value) {
        this.thetaAmontSolSolCh = value;
    }

    /**
     * Gets the value of the thetaAvalSolEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAvalSolEauEcs() {
        return thetaAvalSolEauEcs;
    }

    /**
     * Sets the value of the thetaAvalSolEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAvalSolEauEcs(String value) {
        this.thetaAvalSolEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontSolEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThetaAmontSolEauEcs() {
        return thetaAmontSolEauEcs;
    }

    /**
     * Sets the value of the thetaAmontSolEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThetaAmontSolEauEcs(String value) {
        this.thetaAmontSolEauEcs = value;
    }

    /**
     * Gets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getThetaAvalEauGlycoleeEauEcs() {
        return thetaAvalEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAvalEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setThetaAvalEauGlycoleeEauEcs(Integer value) {
        this.thetaAvalEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getThetaAmontEauGlycoleeEauEcs() {
        return thetaAmontEauGlycoleeEauEcs;
    }

    /**
     * Sets the value of the thetaAmontEauGlycoleeEauEcs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setThetaAmontEauGlycoleeEauEcs(Integer value) {
        this.thetaAmontEauGlycoleeEauEcs = value;
    }

    /**
     * Gets the value of the performance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformance() {
        return performance;
    }

    /**
     * Sets the value of the performance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformance(String value) {
        this.performance = value;
    }

    /**
     * Gets the value of the pabs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabs() {
        return pabs;
    }

    /**
     * Sets the value of the pabs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabs(String value) {
        this.pabs = value;
    }

    /**
     * Gets the value of the cor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOR() {
        return cor;
    }

    /**
     * Sets the value of the cor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOR(String value) {
        this.cor = value;
    }

    /**
     * Gets the value of the statutValPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPivot() {
        return statutValPivot;
    }

    /**
     * Sets the value of the statutValPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPivot(String value) {
        this.statutValPivot = value;
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
     * Gets the value of the valPabs property.
     * 
     */
    public double getValPabs() {
        return valPabs;
    }

    /**
     * Sets the value of the valPabs property.
     * 
     */
    public void setValPabs(double value) {
        this.valPabs = value;
    }

    /**
     * Gets the value of the limTheta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimTheta() {
        return limTheta;
    }

    /**
     * Sets the value of the limTheta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimTheta(String value) {
        this.limTheta = value;
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
     * Gets the value of the valeurDeclareeDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurDeclareeDefaut() {
        return valeurDeclareeDefaut;
    }

    /**
     * Sets the value of the valeurDeclareeDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurDeclareeDefaut(String value) {
        this.valeurDeclareeDefaut = value;
    }

    /**
     * Gets the value of the fonctionnementCompresseur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctionnementCompresseur() {
        return fonctionnementCompresseur;
    }

    /**
     * Sets the value of the fonctionnementCompresseur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctionnementCompresseur(String value) {
        this.fonctionnementCompresseur = value;
    }

    /**
     * Gets the value of the statutFonctionnementContinu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFonctionnementContinu() {
        return statutFonctionnementContinu;
    }

    /**
     * Sets the value of the statutFonctionnementContinu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFonctionnementContinu(String value) {
        this.statutFonctionnementContinu = value;
    }

    /**
     * Gets the value of the lRcontmin property.
     * 
     */
    public double getLRcontmin() {
        return lRcontmin;
    }

    /**
     * Sets the value of the lRcontmin property.
     * 
     */
    public void setLRcontmin(double value) {
        this.lRcontmin = value;
    }

    /**
     * Gets the value of the ccplRcontmin property.
     * 
     */
    public double getCCPLRcontmin() {
        return ccplRcontmin;
    }

    /**
     * Sets the value of the ccplRcontmin property.
     * 
     */
    public void setCCPLRcontmin(double value) {
        this.ccplRcontmin = value;
    }

    /**
     * Gets the value of the statutTaux property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutTaux() {
        return statutTaux;
    }

    /**
     * Sets the value of the statutTaux property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutTaux(String value) {
        this.statutTaux = value;
    }

    /**
     * Gets the value of the taux property.
     * 
     */
    public double getTaux() {
        return taux;
    }

    /**
     * Sets the value of the taux property.
     * 
     */
    public void setTaux(double value) {
        this.taux = value;
    }

    /**
     * Gets the value of the typoEmetteur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypoEmetteur() {
        return typoEmetteur;
    }

    /**
     * Sets the value of the typoEmetteur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypoEmetteur(String value) {
        this.typoEmetteur = value;
    }

}
