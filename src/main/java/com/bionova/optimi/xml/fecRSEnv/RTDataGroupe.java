
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Groupe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Groupe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="V" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Def_Httf" type="{}E_Def_Httf"/>
 *         &lt;element name="Httf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_trav_surv" type="{}E_Caractere_Traversant"/>
 *         &lt;element name="Is_Hall" type="{}RT_Oui_Non"/>
 *         &lt;element name="Categorie_Ce1_Ce2" type="{}E_Categorie_Ce1_Ce2"/>
 *         &lt;element name="Is_Climatise" type="{}RT_Oui_Non"/>
 *         &lt;element name="Type_Pgrm_Ch" type="{}E_Type_Pgrm_Ch"/>
 *         &lt;element name="Type_Pgrm_Fr" type="{}RT_Type_Pgrm_Fr"/>
 *         &lt;element name="Qv_occ_BBIO" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_inocc_BBIO" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Bois_Energie" type="{}RT_Oui_Non"/>
 *         &lt;element name="Is_Reseau_Ch" type="{}E_Is_Reseau_Ch"/>
 *         &lt;element name="Contenu_CO2_Reseau_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Reseau_Fr" type="{}E_Is_Reseau_Fr"/>
 *         &lt;element name="Contenu_CO2_Reseau_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paroi_opaque_Collection" type="{}ArrayOfRT_Data_Paroi_opaque" minOccurs="0"/>
 *         &lt;element name="Lineaire_Collection" type="{}ArrayOfRT_Data_Lineaire" minOccurs="0"/>
 *         &lt;element name="Emetteur_Collection" type="{}ArrayOfRT_Data_Emetteur" minOccurs="0"/>
 *         &lt;element name="Emetteur_ECS_Collection" type="{}ArrayOfRT_Data_Emetteur_ECS" minOccurs="0"/>
 *         &lt;element name="Entree_Air_Collection" type="{}ArrayOfRT_Data_Entree_Air" minOccurs="0"/>
 *         &lt;element name="Eclairage_Collection" type="{}ArrayOfRT_Data_Eclairage" minOccurs="0"/>
 *         &lt;element name="Inertie" type="{}RT_Data_Inertie" minOccurs="0"/>
 *         &lt;element name="Permeabilite" type="{}RT_Data_Permeabilite" minOccurs="0"/>
 *         &lt;element name="Baie_Collection" type="{}ArrayOfRT_Data_Baie" minOccurs="0"/>
 *         &lt;element name="Bouche_Conduit_Collection" type="{}ArrayOfRT_Data_Bouche_Conduit" minOccurs="0"/>
 *         &lt;element name="Aeration_Collection" type="{}ArrayOfRT_Data_Aeration" minOccurs="0"/>
 *         &lt;element name="Ventilation_Naturelle_Hybride_Collection" type="{}ArrayOfRT_Data_Ventilation_Naturelle_Hybride" minOccurs="0"/>
 *         &lt;element name="Emission_Rafraichissement_Direct_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}T5_AFPG_Geocooling_NonClimatise_Emission" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Brasseur_Air_Collection" type="{}ArrayOfRT_Data_Brasseur_Air" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Groupe", propOrder = {

})
public class RTDataGroupe {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "SURT")
    protected double surt;
    @XmlElement(name = "SHAB")
    protected double shab;
    @XmlElement(name = "V")
    protected double v;
    @XmlElement(name = "Def_Httf", required = true)
    protected String defHttf;
    @XmlElement(name = "Httf")
    protected double httf;
    @XmlElement(name = "Delta_trav_surv", required = true)
    protected String deltaTravSurv;
    @XmlElement(name = "Is_Hall", required = true)
    protected String isHall;
    @XmlElement(name = "Categorie_Ce1_Ce2", required = true)
    protected String categorieCe1Ce2;
    @XmlElement(name = "Is_Climatise", required = true)
    protected String isClimatise;
    @XmlElement(name = "Type_Pgrm_Ch", required = true)
    protected String typePgrmCh;
    @XmlElement(name = "Type_Pgrm_Fr", required = true)
    protected String typePgrmFr;
    @XmlElement(name = "Qv_occ_BBIO")
    protected double qvOccBBIO;
    @XmlElement(name = "Qv_inocc_BBIO")
    protected double qvInoccBBIO;
    @XmlElement(name = "Bois_Energie", required = true)
    protected String boisEnergie;
    @XmlElement(name = "Is_Reseau_Ch", required = true)
    protected String isReseauCh;
    @XmlElement(name = "Contenu_CO2_Reseau_Ch")
    protected double contenuCO2ReseauCh;
    @XmlElement(name = "Is_Reseau_Fr", required = true)
    protected String isReseauFr;
    @XmlElement(name = "Contenu_CO2_Reseau_Fr")
    protected double contenuCO2ReseauFr;
    @XmlElement(name = "Paroi_opaque_Collection")
    protected ArrayOfRTDataParoiOpaque paroiOpaqueCollection;
    @XmlElement(name = "Lineaire_Collection")
    protected ArrayOfRTDataLineaire lineaireCollection;
    @XmlElement(name = "Emetteur_Collection")
    protected ArrayOfRTDataEmetteur emetteurCollection;
    @XmlElement(name = "Emetteur_ECS_Collection")
    protected ArrayOfRTDataEmetteurECS emetteurECSCollection;
    @XmlElement(name = "Entree_Air_Collection")
    protected ArrayOfRTDataEntreeAir entreeAirCollection;
    @XmlElement(name = "Eclairage_Collection")
    protected ArrayOfRTDataEclairage eclairageCollection;
    @XmlElement(name = "Inertie")
    protected RTDataInertie inertie;
    @XmlElement(name = "Permeabilite")
    protected RTDataPermeabilite permeabilite;
    @XmlElement(name = "Baie_Collection")
    protected ArrayOfRTDataBaie baieCollection;
    @XmlElement(name = "Bouche_Conduit_Collection")
    protected ArrayOfRTDataBoucheConduit boucheConduitCollection;
    @XmlElement(name = "Aeration_Collection")
    protected ArrayOfRTDataAeration aerationCollection;
    @XmlElement(name = "Ventilation_Naturelle_Hybride_Collection")
    protected ArrayOfRTDataVentilationNaturelleHybride ventilationNaturelleHybrideCollection;
    @XmlElement(name = "Emission_Rafraichissement_Direct_Collection")
    protected RTDataGroupe.EmissionRafraichissementDirectCollection emissionRafraichissementDirectCollection;
    @XmlElement(name = "Brasseur_Air_Collection")
    protected ArrayOfRTDataBrasseurAir brasseurAirCollection;

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
     * Gets the value of the surt property.
     * 
     */
    public double getSURT() {
        return surt;
    }

    /**
     * Sets the value of the surt property.
     * 
     */
    public void setSURT(double value) {
        this.surt = value;
    }

    /**
     * Gets the value of the shab property.
     * 
     */
    public double getSHAB() {
        return shab;
    }

    /**
     * Sets the value of the shab property.
     * 
     */
    public void setSHAB(double value) {
        this.shab = value;
    }

    /**
     * Gets the value of the v property.
     * 
     */
    public double getV() {
        return v;
    }

    /**
     * Sets the value of the v property.
     * 
     */
    public void setV(double value) {
        this.v = value;
    }

    /**
     * Gets the value of the defHttf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefHttf() {
        return defHttf;
    }

    /**
     * Sets the value of the defHttf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefHttf(String value) {
        this.defHttf = value;
    }

    /**
     * Gets the value of the httf property.
     * 
     */
    public double getHttf() {
        return httf;
    }

    /**
     * Sets the value of the httf property.
     * 
     */
    public void setHttf(double value) {
        this.httf = value;
    }

    /**
     * Gets the value of the deltaTravSurv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeltaTravSurv() {
        return deltaTravSurv;
    }

    /**
     * Sets the value of the deltaTravSurv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeltaTravSurv(String value) {
        this.deltaTravSurv = value;
    }

    /**
     * Gets the value of the isHall property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHall() {
        return isHall;
    }

    /**
     * Sets the value of the isHall property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHall(String value) {
        this.isHall = value;
    }

    /**
     * Gets the value of the categorieCe1Ce2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategorieCe1Ce2() {
        return categorieCe1Ce2;
    }

    /**
     * Sets the value of the categorieCe1Ce2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategorieCe1Ce2(String value) {
        this.categorieCe1Ce2 = value;
    }

    /**
     * Gets the value of the isClimatise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsClimatise() {
        return isClimatise;
    }

    /**
     * Sets the value of the isClimatise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsClimatise(String value) {
        this.isClimatise = value;
    }

    /**
     * Gets the value of the typePgrmCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePgrmCh() {
        return typePgrmCh;
    }

    /**
     * Sets the value of the typePgrmCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePgrmCh(String value) {
        this.typePgrmCh = value;
    }

    /**
     * Gets the value of the typePgrmFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePgrmFr() {
        return typePgrmFr;
    }

    /**
     * Sets the value of the typePgrmFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePgrmFr(String value) {
        this.typePgrmFr = value;
    }

    /**
     * Gets the value of the qvOccBBIO property.
     * 
     */
    public double getQvOccBBIO() {
        return qvOccBBIO;
    }

    /**
     * Sets the value of the qvOccBBIO property.
     * 
     */
    public void setQvOccBBIO(double value) {
        this.qvOccBBIO = value;
    }

    /**
     * Gets the value of the qvInoccBBIO property.
     * 
     */
    public double getQvInoccBBIO() {
        return qvInoccBBIO;
    }

    /**
     * Sets the value of the qvInoccBBIO property.
     * 
     */
    public void setQvInoccBBIO(double value) {
        this.qvInoccBBIO = value;
    }

    /**
     * Gets the value of the boisEnergie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoisEnergie() {
        return boisEnergie;
    }

    /**
     * Sets the value of the boisEnergie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoisEnergie(String value) {
        this.boisEnergie = value;
    }

    /**
     * Gets the value of the isReseauCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsReseauCh() {
        return isReseauCh;
    }

    /**
     * Sets the value of the isReseauCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsReseauCh(String value) {
        this.isReseauCh = value;
    }

    /**
     * Gets the value of the contenuCO2ReseauCh property.
     * 
     */
    public double getContenuCO2ReseauCh() {
        return contenuCO2ReseauCh;
    }

    /**
     * Sets the value of the contenuCO2ReseauCh property.
     * 
     */
    public void setContenuCO2ReseauCh(double value) {
        this.contenuCO2ReseauCh = value;
    }

    /**
     * Gets the value of the isReseauFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsReseauFr() {
        return isReseauFr;
    }

    /**
     * Sets the value of the isReseauFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsReseauFr(String value) {
        this.isReseauFr = value;
    }

    /**
     * Gets the value of the contenuCO2ReseauFr property.
     * 
     */
    public double getContenuCO2ReseauFr() {
        return contenuCO2ReseauFr;
    }

    /**
     * Sets the value of the contenuCO2ReseauFr property.
     * 
     */
    public void setContenuCO2ReseauFr(double value) {
        this.contenuCO2ReseauFr = value;
    }

    /**
     * Gets the value of the paroiOpaqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataParoiOpaque }
     *     
     */
    public ArrayOfRTDataParoiOpaque getParoiOpaqueCollection() {
        return paroiOpaqueCollection;
    }

    /**
     * Sets the value of the paroiOpaqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataParoiOpaque }
     *     
     */
    public void setParoiOpaqueCollection(ArrayOfRTDataParoiOpaque value) {
        this.paroiOpaqueCollection = value;
    }

    /**
     * Gets the value of the lineaireCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataLineaire }
     *     
     */
    public ArrayOfRTDataLineaire getLineaireCollection() {
        return lineaireCollection;
    }

    /**
     * Sets the value of the lineaireCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataLineaire }
     *     
     */
    public void setLineaireCollection(ArrayOfRTDataLineaire value) {
        this.lineaireCollection = value;
    }

    /**
     * Gets the value of the emetteurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEmetteur }
     *     
     */
    public ArrayOfRTDataEmetteur getEmetteurCollection() {
        return emetteurCollection;
    }

    /**
     * Sets the value of the emetteurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEmetteur }
     *     
     */
    public void setEmetteurCollection(ArrayOfRTDataEmetteur value) {
        this.emetteurCollection = value;
    }

    /**
     * Gets the value of the emetteurECSCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEmetteurECS }
     *     
     */
    public ArrayOfRTDataEmetteurECS getEmetteurECSCollection() {
        return emetteurECSCollection;
    }

    /**
     * Sets the value of the emetteurECSCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEmetteurECS }
     *     
     */
    public void setEmetteurECSCollection(ArrayOfRTDataEmetteurECS value) {
        this.emetteurECSCollection = value;
    }

    /**
     * Gets the value of the entreeAirCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEntreeAir }
     *     
     */
    public ArrayOfRTDataEntreeAir getEntreeAirCollection() {
        return entreeAirCollection;
    }

    /**
     * Sets the value of the entreeAirCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEntreeAir }
     *     
     */
    public void setEntreeAirCollection(ArrayOfRTDataEntreeAir value) {
        this.entreeAirCollection = value;
    }

    /**
     * Gets the value of the eclairageCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEclairage }
     *     
     */
    public ArrayOfRTDataEclairage getEclairageCollection() {
        return eclairageCollection;
    }

    /**
     * Sets the value of the eclairageCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEclairage }
     *     
     */
    public void setEclairageCollection(ArrayOfRTDataEclairage value) {
        this.eclairageCollection = value;
    }

    /**
     * Gets the value of the inertie property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataInertie }
     *     
     */
    public RTDataInertie getInertie() {
        return inertie;
    }

    /**
     * Sets the value of the inertie property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataInertie }
     *     
     */
    public void setInertie(RTDataInertie value) {
        this.inertie = value;
    }

    /**
     * Gets the value of the permeabilite property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataPermeabilite }
     *     
     */
    public RTDataPermeabilite getPermeabilite() {
        return permeabilite;
    }

    /**
     * Sets the value of the permeabilite property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataPermeabilite }
     *     
     */
    public void setPermeabilite(RTDataPermeabilite value) {
        this.permeabilite = value;
    }

    /**
     * Gets the value of the baieCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBaie }
     *     
     */
    public ArrayOfRTDataBaie getBaieCollection() {
        return baieCollection;
    }

    /**
     * Sets the value of the baieCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBaie }
     *     
     */
    public void setBaieCollection(ArrayOfRTDataBaie value) {
        this.baieCollection = value;
    }

    /**
     * Gets the value of the boucheConduitCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBoucheConduit }
     *     
     */
    public ArrayOfRTDataBoucheConduit getBoucheConduitCollection() {
        return boucheConduitCollection;
    }

    /**
     * Sets the value of the boucheConduitCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBoucheConduit }
     *     
     */
    public void setBoucheConduitCollection(ArrayOfRTDataBoucheConduit value) {
        this.boucheConduitCollection = value;
    }

    /**
     * Gets the value of the aerationCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataAeration }
     *     
     */
    public ArrayOfRTDataAeration getAerationCollection() {
        return aerationCollection;
    }

    /**
     * Sets the value of the aerationCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataAeration }
     *     
     */
    public void setAerationCollection(ArrayOfRTDataAeration value) {
        this.aerationCollection = value;
    }

    /**
     * Gets the value of the ventilationNaturelleHybrideCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataVentilationNaturelleHybride }
     *     
     */
    public ArrayOfRTDataVentilationNaturelleHybride getVentilationNaturelleHybrideCollection() {
        return ventilationNaturelleHybrideCollection;
    }

    /**
     * Sets the value of the ventilationNaturelleHybrideCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataVentilationNaturelleHybride }
     *     
     */
    public void setVentilationNaturelleHybrideCollection(ArrayOfRTDataVentilationNaturelleHybride value) {
        this.ventilationNaturelleHybrideCollection = value;
    }

    /**
     * Gets the value of the emissionRafraichissementDirectCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataGroupe.EmissionRafraichissementDirectCollection }
     *     
     */
    public RTDataGroupe.EmissionRafraichissementDirectCollection getEmissionRafraichissementDirectCollection() {
        return emissionRafraichissementDirectCollection;
    }

    /**
     * Sets the value of the emissionRafraichissementDirectCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataGroupe.EmissionRafraichissementDirectCollection }
     *     
     */
    public void setEmissionRafraichissementDirectCollection(RTDataGroupe.EmissionRafraichissementDirectCollection value) {
        this.emissionRafraichissementDirectCollection = value;
    }

    /**
     * Gets the value of the brasseurAirCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBrasseurAir }
     *     
     */
    public ArrayOfRTDataBrasseurAir getBrasseurAirCollection() {
        return brasseurAirCollection;
    }

    /**
     * Sets the value of the brasseurAirCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBrasseurAir }
     *     
     */
    public void setBrasseurAirCollection(ArrayOfRTDataBrasseurAir value) {
        this.brasseurAirCollection = value;
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
     *         &lt;element ref="{}T5_AFPG_Geocooling_NonClimatise_Emission" minOccurs="0"/>
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
        "t5AFPGGeocoolingNonClimatiseEmission"
    })
    public static class EmissionRafraichissementDirectCollection {

        @XmlElement(name = "T5_AFPG_Geocooling_NonClimatise_Emission", nillable = true)
        protected List<T5AFPGGeocoolingNonClimatiseEmissionData> t5AFPGGeocoolingNonClimatiseEmission;

        /**
         * Gets the value of the t5AFPGGeocoolingNonClimatiseEmission property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the t5AFPGGeocoolingNonClimatiseEmission property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getT5AFPGGeocoolingNonClimatiseEmission().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link T5AFPGGeocoolingNonClimatiseEmissionData }
         * 
         * 
         */
        public List<T5AFPGGeocoolingNonClimatiseEmissionData> getT5AFPGGeocoolingNonClimatiseEmission() {
            if (t5AFPGGeocoolingNonClimatiseEmission == null) {
                t5AFPGGeocoolingNonClimatiseEmission = new ArrayList<T5AFPGGeocoolingNonClimatiseEmissionData>();
            }
            return this.t5AFPGGeocoolingNonClimatiseEmission;
        }

    }

}
