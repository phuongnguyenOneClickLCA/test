
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
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
 *         &lt;element name="Statut_PEF_Projet" type="{}E_Statut_PEF_Projet"/>
 *         &lt;element name="PEF_bois" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="PEF_rdch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Simu" type="{}RT_Data_Simu" minOccurs="0"/>
 *         &lt;element name="PV_install_Collection" type="{}ArrayOfRT_Data_PV_install" minOccurs="0"/>
 *         &lt;element name="Generation_Collection" type="{}ArrayOfRT_Data_Generation" minOccurs="0"/>
 *         &lt;element name="Batiment_Collection" type="{}ArrayOfRT_Data_Batiment" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Chaud_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_Chaud" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Froid_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_Froid" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_ECS_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_ECS" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Evacuation_EG_Collection" type="{}ArrayOfRT_Data_Distribution_Evacuation_EG_Intergroupe" minOccurs="0"/>
 *         &lt;element name="Distribution_Intergroupe_Mixte_Collection" type="{}ArrayOfRT_Data_Distribution_Intergroupe_Mixte_MTA" minOccurs="0"/>
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
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataProjet {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Comment")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String comment;
    @XmlElement(name = "Date")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String date;
    @XmlElement(name = "Version")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String version;
    @XmlElement(name = "Statut_PEF_Projet", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String statutPEFProjet;
    @XmlElement(name = "PEF_bois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pefBois;
    @XmlElement(name = "PEF_rdch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double pefRdch;
    @XmlElement(name = "Simu")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected RTDataSimu simu;
    @XmlElement(name = "PV_install_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataPVInstall pvInstallCollection;
    @XmlElement(name = "Generation_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataGeneration generationCollection;
    @XmlElement(name = "Batiment_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataBatiment batimentCollection;
    @XmlElement(name = "Distribution_Intergroupe_Chaud_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionIntergroupeChaud distributionIntergroupeChaudCollection;
    @XmlElement(name = "Distribution_Intergroupe_Froid_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionIntergroupeFroid distributionIntergroupeFroidCollection;
    @XmlElement(name = "Distribution_Intergroupe_ECS_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionIntergroupeECS distributionIntergroupeECSCollection;
    @XmlElement(name = "Distribution_Intergroupe_Evacuation_EG_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionEvacuationEGIntergroupe distributionIntergroupeEvacuationEGCollection;
    @XmlElement(name = "Distribution_Intergroupe_Mixte_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataDistributionIntergroupeMixteMTA distributionIntergroupeMixteCollection;
    @XmlElement(name = "PCAD_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataPCAD pcadCollection;
    @XmlElement(name = "Parking_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataParking parkingCollection;

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
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the statutPEFProjet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getStatutPEFProjet() {
        return statutPEFProjet;
    }

    /**
     * Sets the value of the statutPEFProjet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setStatutPEFProjet(String value) {
        this.statutPEFProjet = value;
    }

    /**
     * Gets the value of the pefBois property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPEFBois() {
        return pefBois;
    }

    /**
     * Sets the value of the pefBois property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPEFBois(double value) {
        this.pefBois = value;
    }

    /**
     * Gets the value of the pefRdch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPEFRdch() {
        return pefRdch;
    }

    /**
     * Sets the value of the pefRdch property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPEFRdch(double value) {
        this.pefRdch = value;
    }

    /**
     * Gets the value of the simu property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataSimu }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionIntergroupeECSCollection(ArrayOfRTDataDistributionIntergroupeECS value) {
        this.distributionIntergroupeECSCollection = value;
    }

    /**
     * Gets the value of the distributionIntergroupeEvacuationEGCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionEvacuationEGIntergroupe }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataDistributionEvacuationEGIntergroupe getDistributionIntergroupeEvacuationEGCollection() {
        return distributionIntergroupeEvacuationEGCollection;
    }

    /**
     * Sets the value of the distributionIntergroupeEvacuationEGCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionEvacuationEGIntergroupe }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionIntergroupeEvacuationEGCollection(ArrayOfRTDataDistributionEvacuationEGIntergroupe value) {
        this.distributionIntergroupeEvacuationEGCollection = value;
    }

    /**
     * Gets the value of the distributionIntergroupeMixteCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataDistributionIntergroupeMixteMTA }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataDistributionIntergroupeMixteMTA getDistributionIntergroupeMixteCollection() {
        return distributionIntergroupeMixteCollection;
    }

    /**
     * Sets the value of the distributionIntergroupeMixteCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataDistributionIntergroupeMixteMTA }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDistributionIntergroupeMixteCollection(ArrayOfRTDataDistributionIntergroupeMixteMTA value) {
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setParkingCollection(ArrayOfRTDataParking value) {
        this.parkingCollection = value;
    }

}
