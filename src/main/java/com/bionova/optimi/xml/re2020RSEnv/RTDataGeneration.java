
package com.bionova.optimi.xml.re2020RSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{}T5_HELIOPAC_Heliopacsystem_Geopacsystem"/>
 *                 &lt;/choice>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Source_Amont_Collection" type="{}ArrayOfRT_Data_Source_Amont" minOccurs="0"/>
 *         &lt;element name="Distribution_Rafraichissement_Direct_Collection" type="{}ArrayOfRT_Data_Distribution_Rafraichissement_Direct" minOccurs="0"/>
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
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataGeneration {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Type_Priorite", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typePriorite;
    @XmlElement(name = "Idraccord_Gnr", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idraccordGnr;
    @XmlElement(name = "Idraccord_Reseau_Gen", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String idraccordReseauGen;
    @XmlElement(name = "Pos_Gen", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String posGen;
    @XmlElement(name = "Id_Bat")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idBat;
    @XmlElement(name = "Id_Et")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idEt;
    @XmlElement(name = "Type_Gestion_Chaud_Gen", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeGestionChaudGen;
    @XmlElement(name = "Theta_Wm_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaWmCh;
    @XmlElement(name = "Type_Gestion_Froid_Gen", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeGestionFroidGen;
    @XmlElement(name = "Theta_Wm_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaWmFr;
    @XmlElement(name = "Theta_Wm_Ecs")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double thetaWmEcs;
    @XmlElement(name = "Generateur_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfChoiceGenerateur generateurCollection;
    @XmlElement(name = "Production_Stockage_ECS_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataGeneration.ProductionStockageECSCollection productionStockageECSCollection;
    @XmlElement(name = "Source_Amont_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSourceAmont sourceAmontCollection;
    @XmlElement(name = "Distribution_Rafraichissement_Direct_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionRafraichissementDirect distributionRafraichissementDirectCollection;

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
     * Gets the value of the typePriorite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPosGen(String value) {
        this.posGen = value;
    }

    /**
     * Gets the value of the idBat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdBat() {
        return idBat;
    }

    /**
     * Sets the value of the idBat property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdBat(int value) {
        this.idBat = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeGestionChaudGen(String value) {
        this.typeGestionChaudGen = value;
    }

    /**
     * Gets the value of the thetaWmCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaWmCh() {
        return thetaWmCh;
    }

    /**
     * Sets the value of the thetaWmCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeGestionFroidGen(String value) {
        this.typeGestionFroidGen = value;
    }

    /**
     * Gets the value of the thetaWmFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaWmFr() {
        return thetaWmFr;
    }

    /**
     * Sets the value of the thetaWmFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaWmFr(double value) {
        this.thetaWmFr = value;
    }

    /**
     * Gets the value of the thetaWmEcs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getThetaWmEcs() {
        return thetaWmEcs;
    }

    /**
     * Sets the value of the thetaWmEcs property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSourceAmontCollection(ArrayOfRTDataSourceAmont value) {
        this.sourceAmontCollection = value;
    }

    /**
     * Gets the value of the distributionRafraichissementDirectCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionRafraichissementDirect }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataDistributionRafraichissementDirect getDistributionRafraichissementDirectCollection() {
        return distributionRafraichissementDirectCollection;
    }

    /**
     * Sets the value of the distributionRafraichissementDirectCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionRafraichissementDirect }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionRafraichissementDirectCollection(ArrayOfRTDataDistributionRafraichissementDirect value) {
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
     *     &lt;extension base="{}ArrayOfRT_Data_Production_Stockage">
     *       &lt;choice maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{}T5_HELIOPAC_Heliopacsystem_Geopacsystem"/>
     *       &lt;/choice>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "t5HELIOPACHeliopacsystemGeopacsystem"
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public static class ProductionStockageECSCollection
        extends ArrayOfRTDataProductionStockage
    {

        @XmlElement(name = "T5_HELIOPAC_Heliopacsystem_Geopacsystem", nillable = true)
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        protected List<HeliopacData> t5HELIOPACHeliopacsystemGeopacsystem;

        /**
         * Gets the value of the t5HELIOPACHeliopacsystemGeopacsystem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the t5HELIOPACHeliopacsystemGeopacsystem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getT5HELIOPACHeliopacsystemGeopacsystem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HeliopacData }
         * 
         * 
         */
        @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
        public List<HeliopacData> getT5HELIOPACHeliopacsystemGeopacsystem() {
            if (t5HELIOPACHeliopacsystemGeopacsystem == null) {
                t5HELIOPACHeliopacsystemGeopacsystem = new ArrayList<HeliopacData>();
            }
            return this.t5HELIOPACHeliopacsystemGeopacsystem;
        }

    }

}
