
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Batiment_B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Batiment_B">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_SREF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Bbio_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Bbio_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Bbio_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ratio_psi" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q4Pa_SPAROI" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q4Pa_SREF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecl_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Zone_B_Collection" type="{}ArrayOfRT_Data_Sortie_Zone_B" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Batiment_B", propOrder = {
    "osref",
    "obChMois",
    "obFrMois",
    "obEclMois",
    "obChAnnuel",
    "obFrAnnuel",
    "obEclAnnuel",
    "oBbioPtsMois",
    "oBbioPtsAnnuel",
    "oBbioMax",
    "ratioPsi",
    "q4PaSPAROI",
    "q4PaSREF",
    "obChPtsMois",
    "obFrPtsMois",
    "obEclPtsMois",
    "obChPtsAnnuel",
    "obFrPtsAnnuel",
    "obEclPtsAnnuel",
    "sortieZoneBCollection"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieBatimentB
    extends RTDataSortieBase
{

    @XmlElement(name = "O_SREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osref;
    @XmlElement(name = "O_B_Ch_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obChMois;
    @XmlElement(name = "O_B_Fr_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obFrMois;
    @XmlElement(name = "O_B_Ecl_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obEclMois;
    @XmlElement(name = "O_B_Ch_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obChAnnuel;
    @XmlElement(name = "O_B_Fr_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obFrAnnuel;
    @XmlElement(name = "O_B_Ecl_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obEclAnnuel;
    @XmlElement(name = "O_Bbio_pts_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle oBbioPtsMois;
    @XmlElement(name = "O_Bbio_pts_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBbioPtsAnnuel;
    @XmlElement(name = "O_Bbio_Max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oBbioMax;
    @XmlElement(name = "Ratio_psi")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratioPsi;
    @XmlElement(name = "Q4Pa_SPAROI")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double q4PaSPAROI;
    @XmlElement(name = "Q4Pa_SREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double q4PaSREF;
    @XmlElement(name = "O_B_Ch_pts_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obChPtsMois;
    @XmlElement(name = "O_B_Fr_pts_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obFrPtsMois;
    @XmlElement(name = "O_B_Ecl_pts_mois")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieMensuelle obEclPtsMois;
    @XmlElement(name = "O_B_Ch_pts_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obChPtsAnnuel;
    @XmlElement(name = "O_B_Fr_pts_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obFrPtsAnnuel;
    @XmlElement(name = "O_B_Ecl_pts_annuel")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double obEclPtsAnnuel;
    @XmlElement(name = "Sortie_Zone_B_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieZoneB sortieZoneBCollection;

    /**
     * Gets the value of the osref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSREF() {
        return osref;
    }

    /**
     * Sets the value of the osref property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSREF(double value) {
        this.osref = value;
    }

    /**
     * Gets the value of the obChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBChMois() {
        return obChMois;
    }

    /**
     * Sets the value of the obChMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChMois(ArrayOfRTDataSortieMensuelle value) {
        this.obChMois = value;
    }

    /**
     * Gets the value of the obFrMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBFrMois() {
        return obFrMois;
    }

    /**
     * Sets the value of the obFrMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrMois(ArrayOfRTDataSortieMensuelle value) {
        this.obFrMois = value;
    }

    /**
     * Gets the value of the obEclMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBEclMois() {
        return obEclMois;
    }

    /**
     * Sets the value of the obEclMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclMois = value;
    }

    /**
     * Gets the value of the obChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBChAnnuel() {
        return obChAnnuel;
    }

    /**
     * Sets the value of the obChAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChAnnuel(double value) {
        this.obChAnnuel = value;
    }

    /**
     * Gets the value of the obFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBFrAnnuel() {
        return obFrAnnuel;
    }

    /**
     * Sets the value of the obFrAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrAnnuel(double value) {
        this.obFrAnnuel = value;
    }

    /**
     * Gets the value of the obEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBEclAnnuel() {
        return obEclAnnuel;
    }

    /**
     * Sets the value of the obEclAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclAnnuel(double value) {
        this.obEclAnnuel = value;
    }

    /**
     * Gets the value of the oBbioPtsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBbioPtsMois() {
        return oBbioPtsMois;
    }

    /**
     * Sets the value of the oBbioPtsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBbioPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oBbioPtsMois = value;
    }

    /**
     * Gets the value of the oBbioPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBbioPtsAnnuel() {
        return oBbioPtsAnnuel;
    }

    /**
     * Sets the value of the oBbioPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBbioPtsAnnuel(double value) {
        this.oBbioPtsAnnuel = value;
    }

    /**
     * Gets the value of the oBbioMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBbioMax() {
        return oBbioMax;
    }

    /**
     * Sets the value of the oBbioMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBbioMax(double value) {
        this.oBbioMax = value;
    }

    /**
     * Gets the value of the ratioPsi property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatioPsi() {
        return ratioPsi;
    }

    /**
     * Sets the value of the ratioPsi property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatioPsi(double value) {
        this.ratioPsi = value;
    }

    /**
     * Gets the value of the q4PaSPAROI property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQ4PaSPAROI() {
        return q4PaSPAROI;
    }

    /**
     * Sets the value of the q4PaSPAROI property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQ4PaSPAROI(double value) {
        this.q4PaSPAROI = value;
    }

    /**
     * Gets the value of the q4PaSREF property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQ4PaSREF() {
        return q4PaSREF;
    }

    /**
     * Sets the value of the q4PaSREF property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQ4PaSREF(double value) {
        this.q4PaSREF = value;
    }

    /**
     * Gets the value of the obChPtsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBChPtsMois() {
        return obChPtsMois;
    }

    /**
     * Sets the value of the obChPtsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.obChPtsMois = value;
    }

    /**
     * Gets the value of the obFrPtsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBFrPtsMois() {
        return obFrPtsMois;
    }

    /**
     * Sets the value of the obFrPtsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.obFrPtsMois = value;
    }

    /**
     * Gets the value of the obEclPtsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieMensuelle getOBEclPtsMois() {
        return obEclPtsMois;
    }

    /**
     * Sets the value of the obEclPtsMois property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclPtsMois = value;
    }

    /**
     * Gets the value of the obChPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBChPtsAnnuel() {
        return obChPtsAnnuel;
    }

    /**
     * Sets the value of the obChPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBChPtsAnnuel(double value) {
        this.obChPtsAnnuel = value;
    }

    /**
     * Gets the value of the obFrPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBFrPtsAnnuel() {
        return obFrPtsAnnuel;
    }

    /**
     * Sets the value of the obFrPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBFrPtsAnnuel(double value) {
        this.obFrPtsAnnuel = value;
    }

    /**
     * Gets the value of the obEclPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOBEclPtsAnnuel() {
        return obEclPtsAnnuel;
    }

    /**
     * Sets the value of the obEclPtsAnnuel property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOBEclPtsAnnuel(double value) {
        this.obEclPtsAnnuel = value;
    }

    /**
     * Gets the value of the sortieZoneBCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieZoneB }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieZoneB getSortieZoneBCollection() {
        return sortieZoneBCollection;
    }

    /**
     * Sets the value of the sortieZoneBCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieZoneB }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieZoneBCollection(ArrayOfRTDataSortieZoneB value) {
        this.sortieZoneBCollection = value;
    }

}
