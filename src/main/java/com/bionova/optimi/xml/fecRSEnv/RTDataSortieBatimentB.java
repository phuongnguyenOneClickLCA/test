
package com.bionova.optimi.xml.fecRSEnv;

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
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
 *         &lt;element name="Q4PaSurf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q4Pa_SHON" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "oShonRT",
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
    "q4PaSurf",
    "q4PaSHON",
    "obChPtsMois",
    "obFrPtsMois",
    "obEclPtsMois",
    "obChPtsAnnuel",
    "obFrPtsAnnuel",
    "obEclPtsAnnuel",
    "sortieZoneBCollection"
})
public class RTDataSortieBatimentB
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Shon_RT")
    protected double oShonRT;
    @XmlElement(name = "O_B_Ch_mois")
    protected ArrayOfRTDataSortieMensuelle obChMois;
    @XmlElement(name = "O_B_Fr_mois")
    protected ArrayOfRTDataSortieMensuelle obFrMois;
    @XmlElement(name = "O_B_Ecl_mois")
    protected ArrayOfRTDataSortieMensuelle obEclMois;
    @XmlElement(name = "O_B_Ch_annuel")
    protected double obChAnnuel;
    @XmlElement(name = "O_B_Fr_annuel")
    protected double obFrAnnuel;
    @XmlElement(name = "O_B_Ecl_annuel")
    protected double obEclAnnuel;
    @XmlElement(name = "O_Bbio_pts_mois")
    protected ArrayOfRTDataSortieMensuelle oBbioPtsMois;
    @XmlElement(name = "O_Bbio_pts_annuel")
    protected double oBbioPtsAnnuel;
    @XmlElement(name = "O_Bbio_Max")
    protected double oBbioMax;
    @XmlElement(name = "Ratio_psi")
    protected double ratioPsi;
    @XmlElement(name = "Q4PaSurf")
    protected double q4PaSurf;
    @XmlElement(name = "Q4Pa_SHON")
    protected double q4PaSHON;
    @XmlElement(name = "O_B_Ch_pts_mois")
    protected ArrayOfRTDataSortieMensuelle obChPtsMois;
    @XmlElement(name = "O_B_Fr_pts_mois")
    protected ArrayOfRTDataSortieMensuelle obFrPtsMois;
    @XmlElement(name = "O_B_Ecl_pts_mois")
    protected ArrayOfRTDataSortieMensuelle obEclPtsMois;
    @XmlElement(name = "O_B_Ch_pts_annuel")
    protected double obChPtsAnnuel;
    @XmlElement(name = "O_B_Fr_pts_annuel")
    protected double obFrPtsAnnuel;
    @XmlElement(name = "O_B_Ecl_pts_annuel")
    protected double obEclPtsAnnuel;
    @XmlElement(name = "Sortie_Zone_B_Collection")
    protected ArrayOfRTDataSortieZoneB sortieZoneBCollection;

    /**
     * Gets the value of the oShonRT property.
     * 
     */
    public double getOShonRT() {
        return oShonRT;
    }

    /**
     * Sets the value of the oShonRT property.
     * 
     */
    public void setOShonRT(double value) {
        this.oShonRT = value;
    }

    /**
     * Gets the value of the obChMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
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
    public void setOBEclMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclMois = value;
    }

    /**
     * Gets the value of the obChAnnuel property.
     * 
     */
    public double getOBChAnnuel() {
        return obChAnnuel;
    }

    /**
     * Sets the value of the obChAnnuel property.
     * 
     */
    public void setOBChAnnuel(double value) {
        this.obChAnnuel = value;
    }

    /**
     * Gets the value of the obFrAnnuel property.
     * 
     */
    public double getOBFrAnnuel() {
        return obFrAnnuel;
    }

    /**
     * Sets the value of the obFrAnnuel property.
     * 
     */
    public void setOBFrAnnuel(double value) {
        this.obFrAnnuel = value;
    }

    /**
     * Gets the value of the obEclAnnuel property.
     * 
     */
    public double getOBEclAnnuel() {
        return obEclAnnuel;
    }

    /**
     * Sets the value of the obEclAnnuel property.
     * 
     */
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
    public void setOBbioPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.oBbioPtsMois = value;
    }

    /**
     * Gets the value of the oBbioPtsAnnuel property.
     * 
     */
    public double getOBbioPtsAnnuel() {
        return oBbioPtsAnnuel;
    }

    /**
     * Sets the value of the oBbioPtsAnnuel property.
     * 
     */
    public void setOBbioPtsAnnuel(double value) {
        this.oBbioPtsAnnuel = value;
    }

    /**
     * Gets the value of the oBbioMax property.
     * 
     */
    public double getOBbioMax() {
        return oBbioMax;
    }

    /**
     * Sets the value of the oBbioMax property.
     * 
     */
    public void setOBbioMax(double value) {
        this.oBbioMax = value;
    }

    /**
     * Gets the value of the ratioPsi property.
     * 
     */
    public double getRatioPsi() {
        return ratioPsi;
    }

    /**
     * Sets the value of the ratioPsi property.
     * 
     */
    public void setRatioPsi(double value) {
        this.ratioPsi = value;
    }

    /**
     * Gets the value of the q4PaSurf property.
     * 
     */
    public double getQ4PaSurf() {
        return q4PaSurf;
    }

    /**
     * Sets the value of the q4PaSurf property.
     * 
     */
    public void setQ4PaSurf(double value) {
        this.q4PaSurf = value;
    }

    /**
     * Gets the value of the q4PaSHON property.
     * 
     */
    public double getQ4PaSHON() {
        return q4PaSHON;
    }

    /**
     * Sets the value of the q4PaSHON property.
     * 
     */
    public void setQ4PaSHON(double value) {
        this.q4PaSHON = value;
    }

    /**
     * Gets the value of the obChPtsMois property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieMensuelle }
     *     
     */
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
    public void setOBEclPtsMois(ArrayOfRTDataSortieMensuelle value) {
        this.obEclPtsMois = value;
    }

    /**
     * Gets the value of the obChPtsAnnuel property.
     * 
     */
    public double getOBChPtsAnnuel() {
        return obChPtsAnnuel;
    }

    /**
     * Sets the value of the obChPtsAnnuel property.
     * 
     */
    public void setOBChPtsAnnuel(double value) {
        this.obChPtsAnnuel = value;
    }

    /**
     * Gets the value of the obFrPtsAnnuel property.
     * 
     */
    public double getOBFrPtsAnnuel() {
        return obFrPtsAnnuel;
    }

    /**
     * Sets the value of the obFrPtsAnnuel property.
     * 
     */
    public void setOBFrPtsAnnuel(double value) {
        this.obFrPtsAnnuel = value;
    }

    /**
     * Gets the value of the obEclPtsAnnuel property.
     * 
     */
    public double getOBEclPtsAnnuel() {
        return obEclPtsAnnuel;
    }

    /**
     * Sets the value of the obEclPtsAnnuel property.
     * 
     */
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
    public void setSortieZoneBCollection(ArrayOfRTDataSortieZoneB value) {
        this.sortieZoneBCollection = value;
    }

}
