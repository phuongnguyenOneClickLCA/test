
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Priorite" type="{}E_Type_Priorite"/>
 *         &lt;element name="Idraccord_Gnr" type="{}E_Type_Raccord_Gene"/>
 *         &lt;element name="Idraccord_Reseau_Gen" type="{}E_Type_Raccord_Reseau_Gene"/>
 *         &lt;element name="Pos_Gen" type="{}E_Position_Gen"/>
 *         &lt;element name="Id_Bat" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Type_Gestion_Chaud_Gen" type="{}E_Type_Gestion_Chaud"/>
 *         &lt;element name="Theta_Wm_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Gestion_Froid_Gen" type="{}E_Type_Gestion_Froid"/>
 *         &lt;element name="Theta_Wm_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Wm_Ecs" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Generateur_Collection" type="{}ArrayOfChoiceGenerateur" minOccurs="0"/>
 *         &lt;element name="Production_Stockage_ECS_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{}ArrayOfRT_Data_Production_Stockage">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}T5_CardonnelIngenierie_Comfort_E_PLUS_XL" minOccurs="0"/>
 *                   &lt;element ref="{}T5_CardonnelIngenierie_Production_Stockage_Rotex" minOccurs="0"/>
 *                   &lt;element ref="{}T5_CardonnelIngenierie_Giordano" minOccurs="0"/>
 *                   &lt;element ref="{}T5_ATLANTIC_HYDRA" minOccurs="0"/>
 *                   &lt;element ref="{}T5_HELIOPAC_Heliopacsystem_Geopacsystem" minOccurs="0"/>
 *                   &lt;element ref="{}T5_CardonnelIngenierie_LiMithra" minOccurs="0"/>
 *                   &lt;element ref="{}T5_SOLARONICS_PACF7_Ballon_stockage" minOccurs="0"/>
 *                   &lt;element ref="{}T5_FRANCEAIR_MYRIADE_Ballon" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Source_Amont_Collection" type="{}ArrayOfRT_Data_Source_Amont" minOccurs="0"/>
 *         &lt;element name="Distribution_Rafraichissement_Direct_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}T5_AFPG_Geocooling_NonClimatise_Generation" minOccurs="0"/>
 *                 &lt;/choice>
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
@XmlType(name = "RT_Data_Generation", propOrder = {

})
public class RTDataGeneration {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_Priorite", required = true)
    protected String typePriorite;
    @XmlElement(name = "Idraccord_Gnr", required = true)
    protected String idraccordGnr;
    @XmlElement(name = "Idraccord_Reseau_Gen", required = true)
    protected String idraccordReseauGen;
    @XmlElement(name = "Pos_Gen", required = true)
    protected String posGen;
    @XmlElement(name = "Id_Bat")
    protected int idBat;
    @XmlElement(name = "Id_Et")
    protected int idEt;
    @XmlElement(name = "Type_Gestion_Chaud_Gen", required = true)
    protected String typeGestionChaudGen;
    @XmlElement(name = "Theta_Wm_Ch")
    protected double thetaWmCh;
    @XmlElement(name = "Type_Gestion_Froid_Gen", required = true)
    protected String typeGestionFroidGen;
    @XmlElement(name = "Theta_Wm_Fr")
    protected double thetaWmFr;
    @XmlElement(name = "Theta_Wm_Ecs")
    protected double thetaWmEcs;
    @XmlElement(name = "Generateur_Collection")
    protected ArrayOfChoiceGenerateur generateurCollection;
    @XmlElement(name = "Production_Stockage_ECS_Collection")
    protected RTDataGeneration.ProductionStockageECSCollection productionStockageECSCollection;
    @XmlElement(name = "Source_Amont_Collection")
    protected ArrayOfRTDataSourceAmont sourceAmontCollection;
    @XmlElement(name = "Distribution_Rafraichissement_Direct_Collection")
    protected RTDataGeneration.DistributionRafraichissementDirectCollection distributionRafraichissementDirectCollection;

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
     * Gets the value of the typePriorite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePriorite() {
        return typePriorite;
    }

    /**
     * Sets the value of the typePriorite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePriorite(String value) {
        this.typePriorite = value;
    }

    /**
     * Gets the value of the idraccordGnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdraccordGnr() {
        return idraccordGnr;
    }

    /**
     * Sets the value of the idraccordGnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdraccordGnr(String value) {
        this.idraccordGnr = value;
    }

    /**
     * Gets the value of the idraccordReseauGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdraccordReseauGen() {
        return idraccordReseauGen;
    }

    /**
     * Sets the value of the idraccordReseauGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdraccordReseauGen(String value) {
        this.idraccordReseauGen = value;
    }

    /**
     * Gets the value of the posGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosGen() {
        return posGen;
    }

    /**
     * Sets the value of the posGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosGen(String value) {
        this.posGen = value;
    }

    /**
     * Gets the value of the idBat property.
     * 
     */
    public int getIdBat() {
        return idBat;
    }

    /**
     * Sets the value of the idBat property.
     * 
     */
    public void setIdBat(int value) {
        this.idBat = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    public void setIdEt(int value) {
        this.idEt = value;
    }

    /**
     * Gets the value of the typeGestionChaudGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionChaudGen() {
        return typeGestionChaudGen;
    }

    /**
     * Sets the value of the typeGestionChaudGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionChaudGen(String value) {
        this.typeGestionChaudGen = value;
    }

    /**
     * Gets the value of the thetaWmCh property.
     * 
     */
    public double getThetaWmCh() {
        return thetaWmCh;
    }

    /**
     * Sets the value of the thetaWmCh property.
     * 
     */
    public void setThetaWmCh(double value) {
        this.thetaWmCh = value;
    }

    /**
     * Gets the value of the typeGestionFroidGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionFroidGen() {
        return typeGestionFroidGen;
    }

    /**
     * Sets the value of the typeGestionFroidGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionFroidGen(String value) {
        this.typeGestionFroidGen = value;
    }

    /**
     * Gets the value of the thetaWmFr property.
     * 
     */
    public double getThetaWmFr() {
        return thetaWmFr;
    }

    /**
     * Sets the value of the thetaWmFr property.
     * 
     */
    public void setThetaWmFr(double value) {
        this.thetaWmFr = value;
    }

    /**
     * Gets the value of the thetaWmEcs property.
     * 
     */
    public double getThetaWmEcs() {
        return thetaWmEcs;
    }

    /**
     * Sets the value of the thetaWmEcs property.
     * 
     */
    public void setThetaWmEcs(double value) {
        this.thetaWmEcs = value;
    }

    /**
     * Gets the value of the generateurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoiceGenerateur }
     *     
     */
    public ArrayOfChoiceGenerateur getGenerateurCollection() {
        return generateurCollection;
    }

    /**
     * Sets the value of the generateurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoiceGenerateur }
     *     
     */
    public void setGenerateurCollection(ArrayOfChoiceGenerateur value) {
        this.generateurCollection = value;
    }

    /**
     * Gets the value of the productionStockageECSCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataGeneration.ProductionStockageECSCollection }
     *     
     */
    public RTDataGeneration.ProductionStockageECSCollection getProductionStockageECSCollection() {
        return productionStockageECSCollection;
    }

    /**
     * Sets the value of the productionStockageECSCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataGeneration.ProductionStockageECSCollection }
     *     
     */
    public void setProductionStockageECSCollection(RTDataGeneration.ProductionStockageECSCollection value) {
        this.productionStockageECSCollection = value;
    }

    /**
     * Gets the value of the sourceAmontCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSourceAmont }
     *     
     */
    public ArrayOfRTDataSourceAmont getSourceAmontCollection() {
        return sourceAmontCollection;
    }

    /**
     * Sets the value of the sourceAmontCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSourceAmont }
     *     
     */
    public void setSourceAmontCollection(ArrayOfRTDataSourceAmont value) {
        this.sourceAmontCollection = value;
    }

    /**
     * Gets the value of the distributionRafraichissementDirectCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataGeneration.DistributionRafraichissementDirectCollection }
     *     
     */
    public RTDataGeneration.DistributionRafraichissementDirectCollection getDistributionRafraichissementDirectCollection() {
        return distributionRafraichissementDirectCollection;
    }

    /**
     * Sets the value of the distributionRafraichissementDirectCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataGeneration.DistributionRafraichissementDirectCollection }
     *     
     */
    public void setDistributionRafraichissementDirectCollection(RTDataGeneration.DistributionRafraichissementDirectCollection value) {
        this.distributionRafraichissementDirectCollection = value;
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
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}T5_AFPG_Geocooling_NonClimatise_Generation" minOccurs="0"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "t5AFPGGeocoolingNonClimatiseGeneration"
    })
    public static class DistributionRafraichissementDirectCollection {

        @XmlElement(name = "T5_AFPG_Geocooling_NonClimatise_Generation")
        protected List<T5AFPGGeocoolingNonClimatiseGenerationData> t5AFPGGeocoolingNonClimatiseGeneration;

        /**
         * Gets the value of the t5AFPGGeocoolingNonClimatiseGeneration property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the t5AFPGGeocoolingNonClimatiseGeneration property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getT5AFPGGeocoolingNonClimatiseGeneration().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link T5AFPGGeocoolingNonClimatiseGenerationData }
         * 
         * 
         */
        public List<T5AFPGGeocoolingNonClimatiseGenerationData> getT5AFPGGeocoolingNonClimatiseGeneration() {
            if (t5AFPGGeocoolingNonClimatiseGeneration == null) {
                t5AFPGGeocoolingNonClimatiseGeneration = new ArrayList<T5AFPGGeocoolingNonClimatiseGenerationData>();
            }
            return this.t5AFPGGeocoolingNonClimatiseGeneration;
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
     *     &lt;extension base="{}ArrayOfRT_Data_Production_Stockage">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}T5_CardonnelIngenierie_Comfort_E_PLUS_XL" minOccurs="0"/>
     *         &lt;element ref="{}T5_CardonnelIngenierie_Production_Stockage_Rotex" minOccurs="0"/>
     *         &lt;element ref="{}T5_CardonnelIngenierie_Giordano" minOccurs="0"/>
     *         &lt;element ref="{}T5_ATLANTIC_HYDRA" minOccurs="0"/>
     *         &lt;element ref="{}T5_HELIOPAC_Heliopacsystem_Geopacsystem" minOccurs="0"/>
     *         &lt;element ref="{}T5_CardonnelIngenierie_LiMithra" minOccurs="0"/>
     *         &lt;element ref="{}T5_SOLARONICS_PACF7_Ballon_stockage" minOccurs="0"/>
     *         &lt;element ref="{}T5_FRANCEAIR_MYRIADE_Ballon" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano"
    })
    public static class ProductionStockageECSCollection
        extends ArrayOfRTDataProductionStockage
    {

        @XmlElements({
            @XmlElement(name = "T5_CardonnelIngenierie_Comfort_E_PLUS_XL", type = T5CardonnelIngenierieComfortEPLUSXLData.class, nillable = true),
            @XmlElement(name = "T5_CardonnelIngenierie_Production_Stockage_Rotex", type = T5CardonnelIngenierieProductionStockageRotexData.class, nillable = true),
            @XmlElement(name = "T5_CardonnelIngenierie_Giordano", type = T5CardonnelIngenierieGiordanoData.class, nillable = true),
            @XmlElement(name = "T5_ATLANTIC_HYDRA", type = T5ATLANTICHYDRAData.class, nillable = true),
            @XmlElement(name = "T5_HELIOPAC_Heliopacsystem_Geopacsystem", type = HeliopacData.class, nillable = true),
            @XmlElement(name = "T5_CardonnelIngenierie_LiMithra", type = T5CardonnelIngenierieLiMithraData.class, nillable = true),
            @XmlElement(name = "T5_SOLARONICS_PACF7_Ballon_stockage", type = T5SOLARONICSPACF7BallonStockageData.class, nillable = true),
            @XmlElement(name = "T5_FRANCEAIR_MYRIADE_Ballon", type = T5FRANCEAIRMYRIADEBallonData.class, nillable = true)
        })
        protected List<Object> t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano;

        /**
         * Gets the value of the t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getT5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link T5CardonnelIngenierieComfortEPLUSXLData }
         * {@link T5CardonnelIngenierieProductionStockageRotexData }
         * {@link T5CardonnelIngenierieGiordanoData }
         * {@link T5ATLANTICHYDRAData }
         * {@link HeliopacData }
         * {@link T5CardonnelIngenierieLiMithraData }
         * {@link T5SOLARONICSPACF7BallonStockageData }
         * {@link T5FRANCEAIRMYRIADEBallonData }
         * 
         * 
         */
        public List<Object> getT5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano() {
            if (t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano == null) {
                t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano = new ArrayList<Object>();
            }
            return this.t5CardonnelIngenierieComfortEPLUSXLAndT5CardonnelIngenierieProductionStockageRotexAndT5CardonnelIngenierieGiordano;
        }

    }

}
