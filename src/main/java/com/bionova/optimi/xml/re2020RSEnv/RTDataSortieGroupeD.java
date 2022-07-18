
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
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
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_SREF" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SU" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_PPDMoyen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_NbDegresHeures" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_NbDegresHeures_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_DebutConfAdapt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_FinConfAdapt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_NbDegresHeures_ThDB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_PPDMoyen_ThDB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Nb_h_inconf_ThDB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_1_ThDB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_2_ThDB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_NbDegresHeures_ThDC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_PPDMoyen_ThDC" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Nb_h_inconf_ThDC" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_1_ThDC" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Nb_h_inconf_2_ThDC" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="O_Type_Groupe" type="{}E_Type_Groupe"/>
 *         &lt;element name="O_Tic_h" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *         &lt;element name="O_Tic_ref_h" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_D", propOrder = {
    "osref",
    "oshab",
    "osu",
    "oppdMoyen",
    "oNbDegresHeures",
    "oNbDegresHeuresMax",
    "oDebutConfAdapt",
    "oFinConfAdapt",
    "oNbHInconf",
    "oNbHInconf1",
    "oNbHInconf2",
    "oNbDegresHeuresThDB",
    "oppdMoyenThDB",
    "oNbHInconfThDB",
    "oNbHInconf1ThDB",
    "oNbHInconf2ThDB",
    "oNbDegresHeuresThDC",
    "oppdMoyenThDC",
    "oNbHInconfThDC",
    "oNbHInconf1ThDC",
    "oNbHInconf2ThDC",
    "oTypeGroupe",
    "oTicH",
    "oTicRefH"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieGroupeD
    extends RTDataSortieBase
{

    @XmlElement(name = "O_SREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osref;
    @XmlElement(name = "O_SHAB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oshab;
    @XmlElement(name = "O_SU")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double osu;
    @XmlElement(name = "O_PPDMoyen")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oppdMoyen;
    @XmlElement(name = "O_NbDegresHeures")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oNbDegresHeures;
    @XmlElement(name = "O_NbDegresHeures_max")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oNbDegresHeuresMax;
    @XmlElement(name = "O_DebutConfAdapt")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oDebutConfAdapt;
    @XmlElement(name = "O_FinConfAdapt")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oFinConfAdapt;
    @XmlElement(name = "O_Nb_h_inconf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf;
    @XmlElement(name = "O_Nb_h_inconf_1")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf1;
    @XmlElement(name = "O_Nb_h_inconf_2")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf2;
    @XmlElement(name = "O_NbDegresHeures_ThDB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oNbDegresHeuresThDB;
    @XmlElement(name = "O_PPDMoyen_ThDB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oppdMoyenThDB;
    @XmlElement(name = "O_Nb_h_inconf_ThDB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconfThDB;
    @XmlElement(name = "O_Nb_h_inconf_1_ThDB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf1ThDB;
    @XmlElement(name = "O_Nb_h_inconf_2_ThDB")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf2ThDB;
    @XmlElement(name = "O_NbDegresHeures_ThDC")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oNbDegresHeuresThDC;
    @XmlElement(name = "O_PPDMoyen_ThDC")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oppdMoyenThDC;
    @XmlElement(name = "O_Nb_h_inconf_ThDC")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconfThDC;
    @XmlElement(name = "O_Nb_h_inconf_1_ThDC")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf1ThDC;
    @XmlElement(name = "O_Nb_h_inconf_2_ThDC")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int oNbHInconf2ThDC;
    @XmlElement(name = "O_Type_Groupe", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String oTypeGroupe;
    @XmlElement(name = "O_Tic_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieHoraire oTicH;
    @XmlElement(name = "O_Tic_ref_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieHoraire oTicRefH;

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
     * Gets the value of the oshab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSHAB(double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOSU() {
        return osu;
    }

    /**
     * Sets the value of the osu property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOSU(double value) {
        this.osu = value;
    }

    /**
     * Gets the value of the oppdMoyen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOPPDMoyen() {
        return oppdMoyen;
    }

    /**
     * Sets the value of the oppdMoyen property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOPPDMoyen(double value) {
        this.oppdMoyen = value;
    }

    /**
     * Gets the value of the oNbDegresHeures property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getONbDegresHeures() {
        return oNbDegresHeures;
    }

    /**
     * Sets the value of the oNbDegresHeures property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbDegresHeures(double value) {
        this.oNbDegresHeures = value;
    }

    /**
     * Gets the value of the oNbDegresHeuresMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getONbDegresHeuresMax() {
        return oNbDegresHeuresMax;
    }

    /**
     * Sets the value of the oNbDegresHeuresMax property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbDegresHeuresMax(double value) {
        this.oNbDegresHeuresMax = value;
    }

    /**
     * Gets the value of the oDebutConfAdapt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getODebutConfAdapt() {
        return oDebutConfAdapt;
    }

    /**
     * Sets the value of the oDebutConfAdapt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setODebutConfAdapt(int value) {
        this.oDebutConfAdapt = value;
    }

    /**
     * Gets the value of the oFinConfAdapt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getOFinConfAdapt() {
        return oFinConfAdapt;
    }

    /**
     * Sets the value of the oFinConfAdapt property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOFinConfAdapt(int value) {
        this.oFinConfAdapt = value;
    }

    /**
     * Gets the value of the oNbHInconf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf() {
        return oNbHInconf;
    }

    /**
     * Sets the value of the oNbHInconf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf(int value) {
        this.oNbHInconf = value;
    }

    /**
     * Gets the value of the oNbHInconf1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf1() {
        return oNbHInconf1;
    }

    /**
     * Sets the value of the oNbHInconf1 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf1(int value) {
        this.oNbHInconf1 = value;
    }

    /**
     * Gets the value of the oNbHInconf2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf2() {
        return oNbHInconf2;
    }

    /**
     * Sets the value of the oNbHInconf2 property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf2(int value) {
        this.oNbHInconf2 = value;
    }

    /**
     * Gets the value of the oNbDegresHeuresThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getONbDegresHeuresThDB() {
        return oNbDegresHeuresThDB;
    }

    /**
     * Sets the value of the oNbDegresHeuresThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbDegresHeuresThDB(double value) {
        this.oNbDegresHeuresThDB = value;
    }

    /**
     * Gets the value of the oppdMoyenThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOPPDMoyenThDB() {
        return oppdMoyenThDB;
    }

    /**
     * Sets the value of the oppdMoyenThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOPPDMoyenThDB(double value) {
        this.oppdMoyenThDB = value;
    }

    /**
     * Gets the value of the oNbHInconfThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconfThDB() {
        return oNbHInconfThDB;
    }

    /**
     * Sets the value of the oNbHInconfThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconfThDB(int value) {
        this.oNbHInconfThDB = value;
    }

    /**
     * Gets the value of the oNbHInconf1ThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf1ThDB() {
        return oNbHInconf1ThDB;
    }

    /**
     * Sets the value of the oNbHInconf1ThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf1ThDB(int value) {
        this.oNbHInconf1ThDB = value;
    }

    /**
     * Gets the value of the oNbHInconf2ThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf2ThDB() {
        return oNbHInconf2ThDB;
    }

    /**
     * Sets the value of the oNbHInconf2ThDB property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf2ThDB(int value) {
        this.oNbHInconf2ThDB = value;
    }

    /**
     * Gets the value of the oNbDegresHeuresThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getONbDegresHeuresThDC() {
        return oNbDegresHeuresThDC;
    }

    /**
     * Sets the value of the oNbDegresHeuresThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbDegresHeuresThDC(double value) {
        this.oNbDegresHeuresThDC = value;
    }

    /**
     * Gets the value of the oppdMoyenThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOPPDMoyenThDC() {
        return oppdMoyenThDC;
    }

    /**
     * Sets the value of the oppdMoyenThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOPPDMoyenThDC(double value) {
        this.oppdMoyenThDC = value;
    }

    /**
     * Gets the value of the oNbHInconfThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconfThDC() {
        return oNbHInconfThDC;
    }

    /**
     * Sets the value of the oNbHInconfThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconfThDC(int value) {
        this.oNbHInconfThDC = value;
    }

    /**
     * Gets the value of the oNbHInconf1ThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf1ThDC() {
        return oNbHInconf1ThDC;
    }

    /**
     * Sets the value of the oNbHInconf1ThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf1ThDC(int value) {
        this.oNbHInconf1ThDC = value;
    }

    /**
     * Gets the value of the oNbHInconf2ThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getONbHInconf2ThDC() {
        return oNbHInconf2ThDC;
    }

    /**
     * Sets the value of the oNbHInconf2ThDC property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONbHInconf2ThDC(int value) {
        this.oNbHInconf2ThDC = value;
    }

    /**
     * Gets the value of the oTypeGroupe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOTypeGroupe(String value) {
        this.oTypeGroupe = value;
    }

    /**
     * Gets the value of the oTicH property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
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
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOTicRefH(ArrayOfRTDataSortieHoraire value) {
        this.oTicRefH = value;
    }

}
