
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Groupe_B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Groupe_B">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Categorie_Ce1_Ce2" type="{}E_Categorie_Ce1_Ce2"/>
 *         &lt;element name="Is_Climatise" type="{}RT_Oui_Non"/>
 *         &lt;element name="O_Bbio_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbgeo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbalt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Mbsurf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Bbio_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Bbio_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Grp_Acces_Total" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Grp_Acces_Mixte" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Grp_Sans_Acces" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Nbh_Occ_Einat_Sup" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbh_Occ_Einat_Inf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbh_Occ_Nuit" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nbh_Ecl_Non_Aut" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Taux_Occ_Einat_Sup" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Baie_B_Collection" type="{}ArrayOfRT_Data_Sortie_Baie_B" minOccurs="0"/>
 *         &lt;element name="A_baies" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_sud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_nord" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_ouest" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_est" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_horiz" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_B", propOrder = {
    "oShonRT",
    "oshab",
    "osurt",
    "categorieCe1Ce2",
    "isClimatise",
    "oBbioMax",
    "oMbgeo",
    "oMbalt",
    "oMbsurf",
    "obChMois",
    "obFrMois",
    "obEclMois",
    "obChAnnuel",
    "obFrAnnuel",
    "obEclAnnuel",
    "oBbioPtsMois",
    "oBbioPtsAnnuel",
    "grpAccesTotal",
    "grpAccesMixte",
    "grpSansAcces",
    "nbhOccEinatSup",
    "nbhOccEinatInf",
    "nbhOccNuit",
    "nbhEclNonAut",
    "tauxOccEinatSup",
    "sortieBaieBCollection",
    "aBaies",
    "partBaiesSud",
    "partBaiesNord",
    "partBaiesOuest",
    "partBaiesEst",
    "partBaiesHoriz"
})
public class RTDataSortieGroupeB
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Shon_RT")
    protected double oShonRT;
    @XmlElement(name = "O_SHAB")
    protected double oshab;
    @XmlElement(name = "O_SURT")
    protected double osurt;
    @XmlElement(name = "Categorie_Ce1_Ce2", required = true)
    protected String categorieCe1Ce2;
    @XmlElement(name = "Is_Climatise", required = true)
    protected String isClimatise;
    @XmlElement(name = "O_Bbio_Max")
    protected double oBbioMax;
    @XmlElement(name = "O_Mbgeo")
    protected double oMbgeo;
    @XmlElement(name = "O_Mbalt")
    protected double oMbalt;
    @XmlElement(name = "O_Mbsurf")
    protected double oMbsurf;
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
    @XmlElement(name = "Grp_Acces_Total")
    protected boolean grpAccesTotal;
    @XmlElement(name = "Grp_Acces_Mixte")
    protected boolean grpAccesMixte;
    @XmlElement(name = "Grp_Sans_Acces")
    protected boolean grpSansAcces;
    @XmlElement(name = "Nbh_Occ_Einat_Sup")
    protected double nbhOccEinatSup;
    @XmlElement(name = "Nbh_Occ_Einat_Inf")
    protected double nbhOccEinatInf;
    @XmlElement(name = "Nbh_Occ_Nuit")
    protected double nbhOccNuit;
    @XmlElement(name = "Nbh_Ecl_Non_Aut")
    protected double nbhEclNonAut;
    @XmlElement(name = "Taux_Occ_Einat_Sup")
    protected double tauxOccEinatSup;
    @XmlElement(name = "Sortie_Baie_B_Collection")
    protected ArrayOfRTDataSortieBaieB sortieBaieBCollection;
    @XmlElement(name = "A_baies")
    protected double aBaies;
    @XmlElement(name = "Part_baies_sud")
    protected double partBaiesSud;
    @XmlElement(name = "Part_baies_nord")
    protected double partBaiesNord;
    @XmlElement(name = "Part_baies_ouest")
    protected double partBaiesOuest;
    @XmlElement(name = "Part_baies_est")
    protected double partBaiesEst;
    @XmlElement(name = "Part_baies_horiz")
    protected double partBaiesHoriz;

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
     * Gets the value of the oshab property.
     * 
     */
    public double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     */
    public void setOSHAB(double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osurt property.
     * 
     */
    public double getOSURT() {
        return osurt;
    }

    /**
     * Sets the value of the osurt property.
     * 
     */
    public void setOSURT(double value) {
        this.osurt = value;
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
     * Gets the value of the oMbgeo property.
     * 
     */
    public double getOMbgeo() {
        return oMbgeo;
    }

    /**
     * Sets the value of the oMbgeo property.
     * 
     */
    public void setOMbgeo(double value) {
        this.oMbgeo = value;
    }

    /**
     * Gets the value of the oMbalt property.
     * 
     */
    public double getOMbalt() {
        return oMbalt;
    }

    /**
     * Sets the value of the oMbalt property.
     * 
     */
    public void setOMbalt(double value) {
        this.oMbalt = value;
    }

    /**
     * Gets the value of the oMbsurf property.
     * 
     */
    public double getOMbsurf() {
        return oMbsurf;
    }

    /**
     * Sets the value of the oMbsurf property.
     * 
     */
    public void setOMbsurf(double value) {
        this.oMbsurf = value;
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
     * Gets the value of the grpAccesTotal property.
     * 
     */
    public boolean isGrpAccesTotal() {
        return grpAccesTotal;
    }

    /**
     * Sets the value of the grpAccesTotal property.
     * 
     */
    public void setGrpAccesTotal(boolean value) {
        this.grpAccesTotal = value;
    }

    /**
     * Gets the value of the grpAccesMixte property.
     * 
     */
    public boolean isGrpAccesMixte() {
        return grpAccesMixte;
    }

    /**
     * Sets the value of the grpAccesMixte property.
     * 
     */
    public void setGrpAccesMixte(boolean value) {
        this.grpAccesMixte = value;
    }

    /**
     * Gets the value of the grpSansAcces property.
     * 
     */
    public boolean isGrpSansAcces() {
        return grpSansAcces;
    }

    /**
     * Sets the value of the grpSansAcces property.
     * 
     */
    public void setGrpSansAcces(boolean value) {
        this.grpSansAcces = value;
    }

    /**
     * Gets the value of the nbhOccEinatSup property.
     * 
     */
    public double getNbhOccEinatSup() {
        return nbhOccEinatSup;
    }

    /**
     * Sets the value of the nbhOccEinatSup property.
     * 
     */
    public void setNbhOccEinatSup(double value) {
        this.nbhOccEinatSup = value;
    }

    /**
     * Gets the value of the nbhOccEinatInf property.
     * 
     */
    public double getNbhOccEinatInf() {
        return nbhOccEinatInf;
    }

    /**
     * Sets the value of the nbhOccEinatInf property.
     * 
     */
    public void setNbhOccEinatInf(double value) {
        this.nbhOccEinatInf = value;
    }

    /**
     * Gets the value of the nbhOccNuit property.
     * 
     */
    public double getNbhOccNuit() {
        return nbhOccNuit;
    }

    /**
     * Sets the value of the nbhOccNuit property.
     * 
     */
    public void setNbhOccNuit(double value) {
        this.nbhOccNuit = value;
    }

    /**
     * Gets the value of the nbhEclNonAut property.
     * 
     */
    public double getNbhEclNonAut() {
        return nbhEclNonAut;
    }

    /**
     * Sets the value of the nbhEclNonAut property.
     * 
     */
    public void setNbhEclNonAut(double value) {
        this.nbhEclNonAut = value;
    }

    /**
     * Gets the value of the tauxOccEinatSup property.
     * 
     */
    public double getTauxOccEinatSup() {
        return tauxOccEinatSup;
    }

    /**
     * Sets the value of the tauxOccEinatSup property.
     * 
     */
    public void setTauxOccEinatSup(double value) {
        this.tauxOccEinatSup = value;
    }

    /**
     * Gets the value of the sortieBaieBCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieBaieB }
     *     
     */
    public ArrayOfRTDataSortieBaieB getSortieBaieBCollection() {
        return sortieBaieBCollection;
    }

    /**
     * Sets the value of the sortieBaieBCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieBaieB }
     *     
     */
    public void setSortieBaieBCollection(ArrayOfRTDataSortieBaieB value) {
        this.sortieBaieBCollection = value;
    }

    /**
     * Gets the value of the aBaies property.
     * 
     */
    public double getABaies() {
        return aBaies;
    }

    /**
     * Sets the value of the aBaies property.
     * 
     */
    public void setABaies(double value) {
        this.aBaies = value;
    }

    /**
     * Gets the value of the partBaiesSud property.
     * 
     */
    public double getPartBaiesSud() {
        return partBaiesSud;
    }

    /**
     * Sets the value of the partBaiesSud property.
     * 
     */
    public void setPartBaiesSud(double value) {
        this.partBaiesSud = value;
    }

    /**
     * Gets the value of the partBaiesNord property.
     * 
     */
    public double getPartBaiesNord() {
        return partBaiesNord;
    }

    /**
     * Sets the value of the partBaiesNord property.
     * 
     */
    public void setPartBaiesNord(double value) {
        this.partBaiesNord = value;
    }

    /**
     * Gets the value of the partBaiesOuest property.
     * 
     */
    public double getPartBaiesOuest() {
        return partBaiesOuest;
    }

    /**
     * Sets the value of the partBaiesOuest property.
     * 
     */
    public void setPartBaiesOuest(double value) {
        this.partBaiesOuest = value;
    }

    /**
     * Gets the value of the partBaiesEst property.
     * 
     */
    public double getPartBaiesEst() {
        return partBaiesEst;
    }

    /**
     * Sets the value of the partBaiesEst property.
     * 
     */
    public void setPartBaiesEst(double value) {
        this.partBaiesEst = value;
    }

    /**
     * Gets the value of the partBaiesHoriz property.
     * 
     */
    public double getPartBaiesHoriz() {
        return partBaiesHoriz;
    }

    /**
     * Sets the value of the partBaiesHoriz property.
     * 
     */
    public void setPartBaiesHoriz(double value) {
        this.partBaiesHoriz = value;
    }

}
