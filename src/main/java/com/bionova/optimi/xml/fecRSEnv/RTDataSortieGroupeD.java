
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Groupe_D complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Groupe_D">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Type_Groupe" type="{}E_Type_Groupe"/>
 *         &lt;element name="O_Dies" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_NbDegresHeures" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_PPDMoyen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Dies_ThDB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_NbDegresHeures_ThDB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_PPDMoyen_ThDB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Dies_ThDC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_NbDegresHeures_ThDC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_PPDMoyen_ThDC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_DebutConfAdapt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_FinConfAdapt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Cep_BA_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Cep_BA_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Tic_h" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *         &lt;element name="O_Tic_ref_h" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *         &lt;element name="O_Cef_BA_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Cef_BA_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Q_Cef_Puits_Hydrauliques_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Q_Cep_Puits_Hydrauliques_annuel" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="O_Q_Cef_PH_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Q_Cep_Puits_Hydrauliques_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_D", propOrder = {

})
public class RTDataSortieGroupeD {

    @XmlElement(name = "Index")
    protected Integer index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "O_Shon_RT")
    protected Double oShonRT;
    @XmlElement(name = "O_SHAB")
    protected Double oshab;
    @XmlElement(name = "O_SURT")
    protected Double osurt;
    @XmlElement(name = "O_Type_Groupe")
    protected String oTypeGroupe;
    @XmlElement(name = "O_Dies")
    protected Double oDies;
    @XmlElement(name = "O_NbDegresHeures")
    protected Double oNbDegresHeures;
    @XmlElement(name = "O_PPDMoyen")
    protected Double oppdMoyen;
    @XmlElement(name = "O_Dies_ThDB")
    protected Double oDiesThDB;
    @XmlElement(name = "O_NbDegresHeures_ThDB")
    protected Double oNbDegresHeuresThDB;
    @XmlElement(name = "O_PPDMoyen_ThDB")
    protected Double oppdMoyenThDB;
    @XmlElement(name = "O_Dies_ThDC")
    protected Double oDiesThDC;
    @XmlElement(name = "O_NbDegresHeures_ThDC")
    protected Double oNbDegresHeuresThDC;
    @XmlElement(name = "O_PPDMoyen_ThDC")
    protected Double oppdMoyenThDC;
    @XmlElement(name = "O_DebutConfAdapt")
    protected Integer oDebutConfAdapt;
    @XmlElement(name = "O_FinConfAdapt")
    protected Integer oFinConfAdapt;
    @XmlElement(name = "O_Nb_h_inconf")
    protected Integer oNbHInconf;
    @XmlElement(name = "O_Nb_h_inconf_1")
    protected Integer oNbHInconf1;
    @XmlElement(name = "O_Nb_h_inconf_2")
    protected Integer oNbHInconf2;
    @XmlElement(name = "O_Cep_BA_annuel")
    protected Double oCepBAAnnuel;
    @XmlElement(name = "O_Cep_BA_mois")
    protected ArrayOfRTDataSortieMensuelle oCepBAMois;
    @XmlElement(name = "O_Tic_h")
    protected ArrayOfRTDataSortieHoraire oTicH;
    @XmlElement(name = "O_Tic_ref_h")
    protected ArrayOfRTDataSortieHoraire oTicRefH;
    @XmlElement(name = "O_Cef_BA_annuel")
    protected Double oCefBAAnnuel;
    @XmlElement(name = "O_Cef_BA_mois")
    protected ArrayOfRTDataSortieMensuelle oCefBAMois;
    @XmlElement(name = "O_Q_Cef_Puits_Hydrauliques_annuel")
    protected Double oqCefPuitsHydrauliquesAnnuel;
    @XmlElement(name = "O_Q_Cep_Puits_Hydrauliques_annuel")
    protected Double oqCepPuitsHydrauliquesAnnuel;
    @XmlElement(name = "O_Q_Cef_PH_mois")
    protected ArrayOfRTDataSortieMensuelle oqCefPHMois;
    @XmlElement(name = "O_Q_Cep_Puits_Hydrauliques_mois")
    protected ArrayOfRTDataSortieMensuelle oqCepPuitsHydrauliquesMois;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
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
     * Gets the value of the oShonRT property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOShonRT() {
        return oShonRT;
    }

    /**
     * Sets the value of the oShonRT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOShonRT(Double value) {
        this.oShonRT = value;
    }

    /**
     * Gets the value of the oshab property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOSHAB(Double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osurt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOSURT() {
        return osurt;
    }

    /**
     * Sets the value of the osurt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOSURT(Double value) {
        this.osurt = value;
    }

    /**
     * Gets the value of the oTypeGroupe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTypeGroupe() {
        return oTypeGroupe;
    }

    /**
     * Sets the value of the oTypeGroupe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTypeGroupe(String value) {
        this.oTypeGroupe = value;
    }

    /**
     * Gets the value of the oDies property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getODies() {
        return oDies;
    }

    /**
     * Sets the value of the oDies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setODies(Double value) {
        this.oDies = value;
    }

    /**
     * Gets the value of the oNbDegresHeures property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getONbDegresHeures() {
        return oNbDegresHeures;
    }

    /**
     * Sets the value of the oNbDegresHeures property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setONbDegresHeures(Double value) {
        this.oNbDegresHeures = value;
    }

    /**
     * Gets the value of the oppdMoyen property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOPPDMoyen() {
        return oppdMoyen;
    }

    /**
     * Sets the value of the oppdMoyen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOPPDMoyen(Double value) {
        this.oppdMoyen = value;
    }

    /**
     * Gets the value of the oDiesThDB property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getODiesThDB() {
        return oDiesThDB;
    }

    /**
     * Sets the value of the oDiesThDB property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setODiesThDB(Double value) {
        this.oDiesThDB = value;
    }

    /**
     * Gets the value of the oNbDegresHeuresThDB property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getONbDegresHeuresThDB() {
        return oNbDegresHeuresThDB;
    }

    /**
     * Sets the value of the oNbDegresHeuresThDB property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setONbDegresHeuresThDB(Double value) {
        this.oNbDegresHeuresThDB = value;
    }

    /**
     * Gets the value of the oppdMoyenThDB property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOPPDMoyenThDB() {
        return oppdMoyenThDB;
    }

    /**
     * Sets the value of the oppdMoyenThDB property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOPPDMoyenThDB(Double value) {
        this.oppdMoyenThDB = value;
    }

    /**
     * Gets the value of the oDiesThDC property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getODiesThDC() {
        return oDiesThDC;
    }

    /**
     * Sets the value of the oDiesThDC property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setODiesThDC(Double value) {
        this.oDiesThDC = value;
    }

    /**
     * Gets the value of the oNbDegresHeuresThDC property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getONbDegresHeuresThDC() {
        return oNbDegresHeuresThDC;
    }

    /**
     * Sets the value of the oNbDegresHeuresThDC property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setONbDegresHeuresThDC(Double value) {
        this.oNbDegresHeuresThDC = value;
    }

    /**
     * Gets the value of the oppdMoyenThDC property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOPPDMoyenThDC() {
        return oppdMoyenThDC;
    }

    /**
     * Sets the value of the oppdMoyenThDC property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOPPDMoyenThDC(Double value) {
        this.oppdMoyenThDC = value;
    }

    /**
     * Gets the value of the oDebutConfAdapt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getODebutConfAdapt() {
        return oDebutConfAdapt;
    }

    /**
     * Sets the value of the oDebutConfAdapt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setODebutConfAdapt(Integer value) {
        this.oDebutConfAdapt = value;
    }

    /**
     * Gets the value of the oFinConfAdapt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOFinConfAdapt() {
        return oFinConfAdapt;
    }

    /**
     * Sets the value of the oFinConfAdapt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOFinConfAdapt(Integer value) {
        this.oFinConfAdapt = value;
    }

    /**
     * Gets the value of the oNbHInconf property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getONbHInconf() {
        return oNbHInconf;
    }

    /**
     * Sets the value of the oNbHInconf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setONbHInconf(Integer value) {
        this.oNbHInconf = value;
    }

    /**
     * Gets the value of the oNbHInconf1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getONbHInconf1() {
        return oNbHInconf1;
    }

    /**
     * Sets the value of the oNbHInconf1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setONbHInconf1(Integer value) {
        this.oNbHInconf1 = value;
    }

    /**
     * Gets the value of the oNbHInconf2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getONbHInconf2() {
        return oNbHInconf2;
    }

    /**
     * Sets the value of the oNbHInconf2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setONbHInconf2(Integer value) {
        this.oNbHInconf2 = value;
    }

    /**
     * Gets the value of the oCepBAAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCepBAAnnuel() {
        return oCepBAAnnuel;
    }

    /**
     * Sets the value of the oCepBAAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCepBAAnnuel(Double value) {
        this.oCepBAAnnuel = value;
    }

    /**
     * Gets the value of the oCepBAMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCepBAMois() {
        return oCepBAMois;
    }

    /**
     * Sets the value of the oCepBAMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCepBAMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCepBAMois = value;
    }

    /**
     * Gets the value of the oTicH property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public ArrayOfRTDataSortieHoraire getOTicH() {
        return oTicH;
    }

    /**
     * Sets the value of the oTicH property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public void setOTicH(ArrayOfRTDataSortieHoraire value) {
        this.oTicH = value;
    }

    /**
     * Gets the value of the oTicRefH property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public ArrayOfRTDataSortieHoraire getOTicRefH() {
        return oTicRefH;
    }

    /**
     * Sets the value of the oTicRefH property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public void setOTicRefH(ArrayOfRTDataSortieHoraire value) {
        this.oTicRefH = value;
    }

    /**
     * Gets the value of the oCefBAAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOCefBAAnnuel() {
        return oCefBAAnnuel;
    }

    /**
     * Sets the value of the oCefBAAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOCefBAAnnuel(Double value) {
        this.oCefBAAnnuel = value;
    }

    /**
     * Gets the value of the oCefBAMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOCefBAMois() {
        return oCefBAMois;
    }

    /**
     * Sets the value of the oCefBAMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOCefBAMois(ArrayOfRTDataSortieMensuelle value) {
        this.oCefBAMois = value;
    }

    /**
     * Gets the value of the oqCefPuitsHydrauliquesAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOQCefPuitsHydrauliquesAnnuel() {
        return oqCefPuitsHydrauliquesAnnuel;
    }

    /**
     * Sets the value of the oqCefPuitsHydrauliquesAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOQCefPuitsHydrauliquesAnnuel(Double value) {
        this.oqCefPuitsHydrauliquesAnnuel = value;
    }

    /**
     * Gets the value of the oqCepPuitsHydrauliquesAnnuel property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOQCepPuitsHydrauliquesAnnuel() {
        return oqCepPuitsHydrauliquesAnnuel;
    }

    /**
     * Sets the value of the oqCepPuitsHydrauliquesAnnuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOQCepPuitsHydrauliquesAnnuel(Double value) {
        this.oqCepPuitsHydrauliquesAnnuel = value;
    }

    /**
     * Gets the value of the oqCefPHMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOQCefPHMois() {
        return oqCefPHMois;
    }

    /**
     * Sets the value of the oqCefPHMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOQCefPHMois(ArrayOfRTDataSortieMensuelle value) {
        this.oqCefPHMois = value;
    }

    /**
     * Gets the value of the oqCepPuitsHydrauliquesMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public ArrayOfRTDataSortieMensuelle getOQCepPuitsHydrauliquesMois() {
        return oqCepPuitsHydrauliquesMois;
    }

    /**
     * Sets the value of the oqCepPuitsHydrauliquesMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    public void setOQCepPuitsHydrauliquesMois(ArrayOfRTDataSortieMensuelle value) {
        this.oqCepPuitsHydrauliquesMois = value;
    }

}
