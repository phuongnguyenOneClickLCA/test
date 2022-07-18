
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Zone_B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Zone_B">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Usage" type="{}E_Usage"/>
 *         &lt;element name="O_Bbio_Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ch_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Fr_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ecl_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_B_Ch_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Fr_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_B_Ecl_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Bbio_pts_mois" type="{}ArrayOfRT_Data_Sortie_Mensuelle" minOccurs="0"/>
 *         &lt;element name="O_Bbio_pts_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_CE1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_CE2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="S_Clim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q4PaSurf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q4Pa_SHONRT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_opv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_ophh" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_ophb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_baies" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_sud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_nord" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_ouest" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_est" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Part_baies_horiz" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_T" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_T_perm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_PT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_opv_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_ophh_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_ophb_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_baies_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_T_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_T_perm_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_PT_Surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Hg_es_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_Th_op" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_Th_opv" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_Th_ophh" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_Th_ophb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_Th_PT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_Vent_Hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="H_V_Def_Hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sortie_Groupe_B_Collection" type="{}ArrayOfRT_Data_Sortie_Groupe_B" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Zone_B", propOrder = {
    "oShonRT",
    "oshab",
    "osurt",
    "usage",
    "oBbioMax",
    "obChMois",
    "obFrMois",
    "obEclMois",
    "obChAnnuel",
    "obFrAnnuel",
    "obEclAnnuel",
    "oBbioPtsMois",
    "oBbioPtsAnnuel",
    "sce1",
    "sce2",
    "sClim",
    "q4PaSurf",
    "q4PaSHONRT",
    "aOpv",
    "aOphh",
    "aOphb",
    "aBaies",
    "partBaiesSud",
    "partBaiesNord",
    "partBaiesOuest",
    "partBaiesEst",
    "partBaiesHoriz",
    "at",
    "atPerm",
    "lpt",
    "aOpvSurf",
    "aOphhSurf",
    "aOphbSurf",
    "aBaiesSurf",
    "atSurf",
    "atPermSurf",
    "lptSurf",
    "hgEsHiver",
    "hThOp",
    "hThOpv",
    "hThOphh",
    "hThOphb",
    "hThPT",
    "hVentHiver",
    "hvDefHiver",
    "sortieGroupeBCollection"
})
public class RTDataSortieZoneB
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Shon_RT")
    protected double oShonRT;
    @XmlElement(name = "O_SHAB")
    protected double oshab;
    @XmlElement(name = "O_SURT")
    protected double osurt;
    @XmlElement(name = "Usage", required = true)
    protected String usage;
    @XmlElement(name = "O_Bbio_Max")
    protected double oBbioMax;
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
    @XmlElement(name = "S_CE1")
    protected double sce1;
    @XmlElement(name = "S_CE2")
    protected double sce2;
    @XmlElement(name = "S_Clim")
    protected double sClim;
    @XmlElement(name = "Q4PaSurf")
    protected double q4PaSurf;
    @XmlElement(name = "Q4Pa_SHONRT")
    protected double q4PaSHONRT;
    @XmlElement(name = "A_opv")
    protected double aOpv;
    @XmlElement(name = "A_ophh")
    protected double aOphh;
    @XmlElement(name = "A_ophb")
    protected double aOphb;
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
    @XmlElement(name = "A_T")
    protected double at;
    @XmlElement(name = "A_T_perm")
    protected double atPerm;
    @XmlElement(name = "L_PT")
    protected double lpt;
    @XmlElement(name = "A_opv_Surf")
    protected double aOpvSurf;
    @XmlElement(name = "A_ophh_Surf")
    protected double aOphhSurf;
    @XmlElement(name = "A_ophb_Surf")
    protected double aOphbSurf;
    @XmlElement(name = "A_baies_Surf")
    protected double aBaiesSurf;
    @XmlElement(name = "A_T_Surf")
    protected double atSurf;
    @XmlElement(name = "A_T_perm_Surf")
    protected double atPermSurf;
    @XmlElement(name = "L_PT_Surf")
    protected double lptSurf;
    @XmlElement(name = "Hg_es_hiver")
    protected double hgEsHiver;
    @XmlElement(name = "H_Th_op")
    protected double hThOp;
    @XmlElement(name = "H_Th_opv")
    protected double hThOpv;
    @XmlElement(name = "H_Th_ophh")
    protected double hThOphh;
    @XmlElement(name = "H_Th_ophb")
    protected double hThOphb;
    @XmlElement(name = "H_Th_PT")
    protected double hThPT;
    @XmlElement(name = "H_Vent_Hiver")
    protected double hVentHiver;
    @XmlElement(name = "H_V_Def_Hiver")
    protected double hvDefHiver;
    @XmlElement(name = "Sortie_Groupe_B_Collection")
    protected ArrayOfRTDataSortieGroupeB sortieGroupeBCollection;

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
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsage(String value) {
        this.usage = value;
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
     * Gets the value of the sce1 property.
     * 
     */
    public double getSCE1() {
        return sce1;
    }

    /**
     * Sets the value of the sce1 property.
     * 
     */
    public void setSCE1(double value) {
        this.sce1 = value;
    }

    /**
     * Gets the value of the sce2 property.
     * 
     */
    public double getSCE2() {
        return sce2;
    }

    /**
     * Sets the value of the sce2 property.
     * 
     */
    public void setSCE2(double value) {
        this.sce2 = value;
    }

    /**
     * Gets the value of the sClim property.
     * 
     */
    public double getSClim() {
        return sClim;
    }

    /**
     * Sets the value of the sClim property.
     * 
     */
    public void setSClim(double value) {
        this.sClim = value;
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
     * Gets the value of the q4PaSHONRT property.
     * 
     */
    public double getQ4PaSHONRT() {
        return q4PaSHONRT;
    }

    /**
     * Sets the value of the q4PaSHONRT property.
     * 
     */
    public void setQ4PaSHONRT(double value) {
        this.q4PaSHONRT = value;
    }

    /**
     * Gets the value of the aOpv property.
     * 
     */
    public double getAOpv() {
        return aOpv;
    }

    /**
     * Sets the value of the aOpv property.
     * 
     */
    public void setAOpv(double value) {
        this.aOpv = value;
    }

    /**
     * Gets the value of the aOphh property.
     * 
     */
    public double getAOphh() {
        return aOphh;
    }

    /**
     * Sets the value of the aOphh property.
     * 
     */
    public void setAOphh(double value) {
        this.aOphh = value;
    }

    /**
     * Gets the value of the aOphb property.
     * 
     */
    public double getAOphb() {
        return aOphb;
    }

    /**
     * Sets the value of the aOphb property.
     * 
     */
    public void setAOphb(double value) {
        this.aOphb = value;
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

    /**
     * Gets the value of the at property.
     * 
     */
    public double getAT() {
        return at;
    }

    /**
     * Sets the value of the at property.
     * 
     */
    public void setAT(double value) {
        this.at = value;
    }

    /**
     * Gets the value of the atPerm property.
     * 
     */
    public double getATPerm() {
        return atPerm;
    }

    /**
     * Sets the value of the atPerm property.
     * 
     */
    public void setATPerm(double value) {
        this.atPerm = value;
    }

    /**
     * Gets the value of the lpt property.
     * 
     */
    public double getLPT() {
        return lpt;
    }

    /**
     * Sets the value of the lpt property.
     * 
     */
    public void setLPT(double value) {
        this.lpt = value;
    }

    /**
     * Gets the value of the aOpvSurf property.
     * 
     */
    public double getAOpvSurf() {
        return aOpvSurf;
    }

    /**
     * Sets the value of the aOpvSurf property.
     * 
     */
    public void setAOpvSurf(double value) {
        this.aOpvSurf = value;
    }

    /**
     * Gets the value of the aOphhSurf property.
     * 
     */
    public double getAOphhSurf() {
        return aOphhSurf;
    }

    /**
     * Sets the value of the aOphhSurf property.
     * 
     */
    public void setAOphhSurf(double value) {
        this.aOphhSurf = value;
    }

    /**
     * Gets the value of the aOphbSurf property.
     * 
     */
    public double getAOphbSurf() {
        return aOphbSurf;
    }

    /**
     * Sets the value of the aOphbSurf property.
     * 
     */
    public void setAOphbSurf(double value) {
        this.aOphbSurf = value;
    }

    /**
     * Gets the value of the aBaiesSurf property.
     * 
     */
    public double getABaiesSurf() {
        return aBaiesSurf;
    }

    /**
     * Sets the value of the aBaiesSurf property.
     * 
     */
    public void setABaiesSurf(double value) {
        this.aBaiesSurf = value;
    }

    /**
     * Gets the value of the atSurf property.
     * 
     */
    public double getATSurf() {
        return atSurf;
    }

    /**
     * Sets the value of the atSurf property.
     * 
     */
    public void setATSurf(double value) {
        this.atSurf = value;
    }

    /**
     * Gets the value of the atPermSurf property.
     * 
     */
    public double getATPermSurf() {
        return atPermSurf;
    }

    /**
     * Sets the value of the atPermSurf property.
     * 
     */
    public void setATPermSurf(double value) {
        this.atPermSurf = value;
    }

    /**
     * Gets the value of the lptSurf property.
     * 
     */
    public double getLPTSurf() {
        return lptSurf;
    }

    /**
     * Sets the value of the lptSurf property.
     * 
     */
    public void setLPTSurf(double value) {
        this.lptSurf = value;
    }

    /**
     * Gets the value of the hgEsHiver property.
     * 
     */
    public double getHgEsHiver() {
        return hgEsHiver;
    }

    /**
     * Sets the value of the hgEsHiver property.
     * 
     */
    public void setHgEsHiver(double value) {
        this.hgEsHiver = value;
    }

    /**
     * Gets the value of the hThOp property.
     * 
     */
    public double getHThOp() {
        return hThOp;
    }

    /**
     * Sets the value of the hThOp property.
     * 
     */
    public void setHThOp(double value) {
        this.hThOp = value;
    }

    /**
     * Gets the value of the hThOpv property.
     * 
     */
    public double getHThOpv() {
        return hThOpv;
    }

    /**
     * Sets the value of the hThOpv property.
     * 
     */
    public void setHThOpv(double value) {
        this.hThOpv = value;
    }

    /**
     * Gets the value of the hThOphh property.
     * 
     */
    public double getHThOphh() {
        return hThOphh;
    }

    /**
     * Sets the value of the hThOphh property.
     * 
     */
    public void setHThOphh(double value) {
        this.hThOphh = value;
    }

    /**
     * Gets the value of the hThOphb property.
     * 
     */
    public double getHThOphb() {
        return hThOphb;
    }

    /**
     * Sets the value of the hThOphb property.
     * 
     */
    public void setHThOphb(double value) {
        this.hThOphb = value;
    }

    /**
     * Gets the value of the hThPT property.
     * 
     */
    public double getHThPT() {
        return hThPT;
    }

    /**
     * Sets the value of the hThPT property.
     * 
     */
    public void setHThPT(double value) {
        this.hThPT = value;
    }

    /**
     * Gets the value of the hVentHiver property.
     * 
     */
    public double getHVentHiver() {
        return hVentHiver;
    }

    /**
     * Sets the value of the hVentHiver property.
     * 
     */
    public void setHVentHiver(double value) {
        this.hVentHiver = value;
    }

    /**
     * Gets the value of the hvDefHiver property.
     * 
     */
    public double getHVDefHiver() {
        return hvDefHiver;
    }

    /**
     * Sets the value of the hvDefHiver property.
     * 
     */
    public void setHVDefHiver(double value) {
        this.hvDefHiver = value;
    }

    /**
     * Gets the value of the sortieGroupeBCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGroupeB }
     *     
     */
    public ArrayOfRTDataSortieGroupeB getSortieGroupeBCollection() {
        return sortieGroupeBCollection;
    }

    /**
     * Sets the value of the sortieGroupeBCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGroupeB }
     *     
     */
    public void setSortieGroupeBCollection(ArrayOfRTDataSortieGroupeB value) {
        this.sortieGroupeBCollection = value;
    }

}
