
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Projet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Projet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Simu" type="{}RT_Data_Simu" minOccurs="0"/>
 *         &lt;element name="PV_install_Collection" type="{}ArrayOfRT_Data_PV_install" minOccurs="0"/>
 *         &lt;element name="Generation_Collection" type="{}ArrayOfRT_Data_Generation" minOccurs="0"/>
 *         &lt;element name="Batiment_Collection" type="{}ArrayOfRT_Data_Batiment" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Chaud_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_Chaud" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Froid_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_Froid" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_ECS_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_ECS" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Mixte_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded">
 *                   &lt;element name="T5_Cardonnel_ModuleAppartement_Mixte" type="{}ModuleAppartementRT2012MixteData" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PCAD_Collection" type="{}ArrayOfRT_Data_PCAD" minOccurs="0"/>
 *         &lt;element name="Parking_Collection" type="{}ArrayOfRT_Data_Parking" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Projet", propOrder = {

})
public class RTDataProjet {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "Date")
    protected String date;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "Simu")
    protected RTDataSimu simu;
    @XmlElement(name = "PV_install_Collection")
    protected ArrayOfRTDataPVInstall pvInstallCollection;
    @XmlElement(name = "Generation_Collection")
    protected ArrayOfRTDataGeneration generationCollection;
    @XmlElement(name = "Batiment_Collection")
    protected ArrayOfRTDataBatiment batimentCollection;
    @XmlElement(name = "Distribution_Intergroupe_Chaud_Collection")
    protected ArrayOfRTDataDistributionIntergroupeChaud distributionIntergroupeChaudCollection;
    @XmlElement(name = "Distribution_Intergroupe_Froid_Collection")
    protected ArrayOfRTDataDistributionIntergroupeFroid distributionIntergroupeFroidCollection;
    @XmlElement(name = "Distribution_Intergroupe_ECS_Collection")
    protected ArrayOfRTDataDistributionIntergroupeECS distributionIntergroupeECSCollection;
    @XmlElement(name = "Distribution_Intergroupe_Mixte_Collection")
    protected RTDataProjet.DistributionIntergroupeMixteCollection distributionIntergroupeMixteCollection;
    @XmlElement(name = "PCAD_Collection")
    protected ArrayOfRTDataPCAD pcadCollection;
    @XmlElement(name = "Parking_Collection")
    protected ArrayOfRTDataParking parkingCollection;

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
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the simu property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataSimu }
     *     
     */
    public RTDataSimu getSimu() {
        return simu;
    }

    /**
     * Sets the value of the simu property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataSimu }
     *     
     */
    public void setSimu(RTDataSimu value) {
        this.simu = value;
    }

    /**
     * Gets the value of the pvInstallCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataPVInstall }
     *     
     */
    public ArrayOfRTDataPVInstall getPVInstallCollection() {
        return pvInstallCollection;
    }

    /**
     * Sets the value of the pvInstallCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataPVInstall }
     *     
     */
    public void setPVInstallCollection(ArrayOfRTDataPVInstall value) {
        this.pvInstallCollection = value;
    }

    /**
     * Gets the value of the generationCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataGeneration }
     *     
     */
    public ArrayOfRTDataGeneration getGenerationCollection() {
        return generationCollection;
    }

    /**
     * Sets the value of the generationCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataGeneration }
     *     
     */
    public void setGenerationCollection(ArrayOfRTDataGeneration value) {
        this.generationCollection = value;
    }

    /**
     * Gets the value of the batimentCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBatiment }
     *     
     */
    public ArrayOfRTDataBatiment getBatimentCollection() {
        return batimentCollection;
    }

    /**
     * Sets the value of the batimentCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBatiment }
     *     
     */
    public void setBatimentCollection(ArrayOfRTDataBatiment value) {
        this.batimentCollection = value;
    }

    /**
     * Gets the value of the distributionIntergroupeChaudCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionIntergroupeChaud }
     *     
     */
    public ArrayOfRTDataDistributionIntergroupeChaud getDistributionIntergroupeChaudCollection() {
        return distributionIntergroupeChaudCollection;
    }

    /**
     * Sets the value of the distributionIntergroupeChaudCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionIntergroupeChaud }
     *     
     */
    public void setDistributionIntergroupeChaudCollection(ArrayOfRTDataDistributionIntergroupeChaud value) {
        this.distributionIntergroupeChaudCollection = value;
    }

    /**
     * Gets the value of the distributionIntergroupeFroidCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionIntergroupeFroid }
     *     
     */
    public ArrayOfRTDataDistributionIntergroupeFroid getDistributionIntergroupeFroidCollection() {
        return distributionIntergroupeFroidCollection;
    }

    /**
     * Sets the value of the distributionIntergroupeFroidCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionIntergroupeFroid }
     *     
     */
    public void setDistributionIntergroupeFroidCollection(ArrayOfRTDataDistributionIntergroupeFroid value) {
        this.distributionIntergroupeFroidCollection = value;
    }

    /**
     * Gets the value of the distributionIntergroupeECSCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionIntergroupeECS }
     *     
     */
    public ArrayOfRTDataDistributionIntergroupeECS getDistributionIntergroupeECSCollection() {
        return distributionIntergroupeECSCollection;
    }

    /**
     * Sets the value of the distributionIntergroupeECSCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionIntergroupeECS }
     *     
     */
    public void setDistributionIntergroupeECSCollection(ArrayOfRTDataDistributionIntergroupeECS value) {
        this.distributionIntergroupeECSCollection = value;
    }

    /**
     * Gets the value of the distributionIntergroupeMixteCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataProjet.DistributionIntergroupeMixteCollection }
     *     
     */
    public RTDataProjet.DistributionIntergroupeMixteCollection getDistributionIntergroupeMixteCollection() {
        return distributionIntergroupeMixteCollection;
    }

    /**
     * Sets the value of the distributionIntergroupeMixteCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataProjet.DistributionIntergroupeMixteCollection }
     *     
     */
    public void setDistributionIntergroupeMixteCollection(RTDataProjet.DistributionIntergroupeMixteCollection value) {
        this.distributionIntergroupeMixteCollection = value;
    }

    /**
     * Gets the value of the pcadCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataPCAD }
     *     
     */
    public ArrayOfRTDataPCAD getPCADCollection() {
        return pcadCollection;
    }

    /**
     * Sets the value of the pcadCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataPCAD }
     *     
     */
    public void setPCADCollection(ArrayOfRTDataPCAD value) {
        this.pcadCollection = value;
    }

    /**
     * Gets the value of the parkingCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataParking }
     *     
     */
    public ArrayOfRTDataParking getParkingCollection() {
        return parkingCollection;
    }

    /**
     * Sets the value of the parkingCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataParking }
     *     
     */
    public void setParkingCollection(ArrayOfRTDataParking value) {
        this.parkingCollection = value;
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
     *       &lt;sequence maxOccurs="unbounded">
     *         &lt;element name="T5_Cardonnel_ModuleAppartement_Mixte" type="{}ModuleAppartementRT2012MixteData" minOccurs="0"/>
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
        "t5CardonnelModuleAppartementMixte"
    })
    public static class DistributionIntergroupeMixteCollection {

        @XmlElement(name = "T5_Cardonnel_ModuleAppartement_Mixte")
        protected List<ModuleAppartementRT2012MixteData> t5CardonnelModuleAppartementMixte;

        /**
         * Gets the value of the t5CardonnelModuleAppartementMixte property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the t5CardonnelModuleAppartementMixte property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getT5CardonnelModuleAppartementMixte().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ModuleAppartementRT2012MixteData }
         * 
         * 
         */
        public List<ModuleAppartementRT2012MixteData> getT5CardonnelModuleAppartementMixte() {
            if (t5CardonnelModuleAppartementMixte == null) {
                t5CardonnelModuleAppartementMixte = new ArrayList<ModuleAppartementRT2012MixteData>();
            }
            return this.t5CardonnelModuleAppartementMixte;
        }

    }

}
