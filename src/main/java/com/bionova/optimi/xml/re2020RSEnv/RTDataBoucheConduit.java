
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Bouche_Conduit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Bouche_Conduit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Bouche_Conduit" type="{}E_Type_Bouche"/>
 *         &lt;element name="Cdep" type="{}E_Ventil_Deperdition"/>
 *         &lt;element name="valeur_surface_conduit_rep" type="{}E_type_valeur_surface_conduit" minOccurs="0"/>
 *         &lt;element name="valeur_surface_conduit_souf" type="{}E_type_valeur_surface_conduit" minOccurs="0"/>
 *         &lt;element name="A_cond_rep" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="A_cond_souf" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Cdep_Value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Etancheite" type="{}E_Classe_Etancheite"/>
 *         &lt;element name="Ratfuitevc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Regul_Non_Res" type="{}E_Type_Regulation_Ventilation_Non_Residentiel"/>
 *         &lt;element name="Cndbnr_Value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Regul_Res" type="{}E_Type_Regulation_Ventilation_Residentiel"/>
 *         &lt;element name="R_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_CH" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Systeme_Mecanique" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Emetteur_Collection" type="{}ArrayOfRT_Data_Emetteur" minOccurs="0"/>
 *         &lt;element name="Qspec_rep_rafnoc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qspec_souf_rafnoc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Bouche_Conduit", propOrder = {

})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataBoucheConduit {

    @XmlElement(name = "Index")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int index;
    @XmlElement(name = "Name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlElement(name = "Description")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String description;
    @XmlElement(name = "Type_Bouche_Conduit", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeBoucheConduit;
    @XmlElement(name = "Cdep", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String cdep;
    @XmlElement(name = "valeur_surface_conduit_rep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurSurfaceConduitRep;
    @XmlElement(name = "valeur_surface_conduit_souf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String valeurSurfaceConduitSouf;
    @XmlElement(name = "A_cond_rep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Double aCondRep;
    @XmlElement(name = "A_cond_souf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Double aCondSouf;
    @XmlElement(name = "Cdep_Value")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double cdepValue;
    @XmlElement(name = "Classe_Etancheite", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String classeEtancheite;
    @XmlElement(name = "Ratfuitevc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double ratfuitevc;
    @XmlElement(name = "Type_Regul_Non_Res", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeRegulNonRes;
    @XmlElement(name = "Cndbnr_Value")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double cndbnrValue;
    @XmlElement(name = "Type_Regul_Res", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String typeRegulRes;
    @XmlElement(name = "R_rep")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rRep;
    @XmlElement(name = "R_souf")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double rSouf;
    @XmlElement(name = "Qv_rep_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepOcc;
    @XmlElement(name = "Qv_rep_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepInocc;
    @XmlElement(name = "Qv_souf_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufOcc;
    @XmlElement(name = "Qv_souf_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufInocc;
    @XmlElement(name = "Qv_rep_pointe")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepPointe;
    @XmlElement(name = "Qv_rep_base")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepBase;
    @XmlElement(name = "Qv_souf_pointe")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufPointe;
    @XmlElement(name = "Qv_souf_base")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufBase;
    @XmlElement(name = "Qv_souf_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufCH;
    @XmlElement(name = "Qv_souf_ZN_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufZNOcc;
    @XmlElement(name = "Qv_souf_ZN_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvSoufZNInocc;
    @XmlElement(name = "Qv_rep_CH")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepCH;
    @XmlElement(name = "Qv_rep_ZN_occ")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepZNOcc;
    @XmlElement(name = "Qv_rep_ZN_inocc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qvRepZNInocc;
    @XmlElement(name = "Id_Systeme_Mecanique")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected int idSystemeMecanique;
    @XmlElement(name = "Emetteur_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataEmetteur emetteurCollection;
    @XmlElement(name = "Qspec_rep_rafnoc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qspecRepRafnoc;
    @XmlElement(name = "Qspec_souf_rafnoc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double qspecSoufRafnoc;

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
     * Gets the value of the typeBoucheConduit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeBoucheConduit() {
        return typeBoucheConduit;
    }

    /**
     * Sets the value of the typeBoucheConduit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeBoucheConduit(String value) {
        this.typeBoucheConduit = value;
    }

    /**
     * Gets the value of the cdep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCdep() {
        return cdep;
    }

    /**
     * Sets the value of the cdep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCdep(String value) {
        this.cdep = value;
    }

    /**
     * Gets the value of the valeurSurfaceConduitRep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurSurfaceConduitRep() {
        return valeurSurfaceConduitRep;
    }

    /**
     * Sets the value of the valeurSurfaceConduitRep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurSurfaceConduitRep(String value) {
        this.valeurSurfaceConduitRep = value;
    }

    /**
     * Gets the value of the valeurSurfaceConduitSouf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValeurSurfaceConduitSouf() {
        return valeurSurfaceConduitSouf;
    }

    /**
     * Sets the value of the valeurSurfaceConduitSouf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeurSurfaceConduitSouf(String value) {
        this.valeurSurfaceConduitSouf = value;
    }

    /**
     * Gets the value of the aCondRep property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Double getACondRep() {
        return aCondRep;
    }

    /**
     * Sets the value of the aCondRep property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setACondRep(Double value) {
        this.aCondRep = value;
    }

    /**
     * Gets the value of the aCondSouf property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Double getACondSouf() {
        return aCondSouf;
    }

    /**
     * Sets the value of the aCondSouf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setACondSouf(Double value) {
        this.aCondSouf = value;
    }

    /**
     * Gets the value of the cdepValue property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCdepValue() {
        return cdepValue;
    }

    /**
     * Sets the value of the cdepValue property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCdepValue(double value) {
        this.cdepValue = value;
    }

    /**
     * Gets the value of the classeEtancheite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getClasseEtancheite() {
        return classeEtancheite;
    }

    /**
     * Sets the value of the classeEtancheite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setClasseEtancheite(String value) {
        this.classeEtancheite = value;
    }

    /**
     * Gets the value of the ratfuitevc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRatfuitevc() {
        return ratfuitevc;
    }

    /**
     * Sets the value of the ratfuitevc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRatfuitevc(double value) {
        this.ratfuitevc = value;
    }

    /**
     * Gets the value of the typeRegulNonRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeRegulNonRes() {
        return typeRegulNonRes;
    }

    /**
     * Sets the value of the typeRegulNonRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeRegulNonRes(String value) {
        this.typeRegulNonRes = value;
    }

    /**
     * Gets the value of the cndbnrValue property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getCndbnrValue() {
        return cndbnrValue;
    }

    /**
     * Sets the value of the cndbnrValue property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCndbnrValue(double value) {
        this.cndbnrValue = value;
    }

    /**
     * Gets the value of the typeRegulRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTypeRegulRes() {
        return typeRegulRes;
    }

    /**
     * Sets the value of the typeRegulRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTypeRegulRes(String value) {
        this.typeRegulRes = value;
    }

    /**
     * Gets the value of the rRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRRep() {
        return rRep;
    }

    /**
     * Sets the value of the rRep property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRRep(double value) {
        this.rRep = value;
    }

    /**
     * Gets the value of the rSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getRSouf() {
        return rSouf;
    }

    /**
     * Sets the value of the rSouf property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setRSouf(double value) {
        this.rSouf = value;
    }

    /**
     * Gets the value of the qvRepOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepOcc() {
        return qvRepOcc;
    }

    /**
     * Sets the value of the qvRepOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepOcc(double value) {
        this.qvRepOcc = value;
    }

    /**
     * Gets the value of the qvRepInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepInocc() {
        return qvRepInocc;
    }

    /**
     * Sets the value of the qvRepInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepInocc(double value) {
        this.qvRepInocc = value;
    }

    /**
     * Gets the value of the qvSoufOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufOcc() {
        return qvSoufOcc;
    }

    /**
     * Sets the value of the qvSoufOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufOcc(double value) {
        this.qvSoufOcc = value;
    }

    /**
     * Gets the value of the qvSoufInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufInocc() {
        return qvSoufInocc;
    }

    /**
     * Sets the value of the qvSoufInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufInocc(double value) {
        this.qvSoufInocc = value;
    }

    /**
     * Gets the value of the qvRepPointe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepPointe() {
        return qvRepPointe;
    }

    /**
     * Sets the value of the qvRepPointe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepPointe(double value) {
        this.qvRepPointe = value;
    }

    /**
     * Gets the value of the qvRepBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepBase() {
        return qvRepBase;
    }

    /**
     * Sets the value of the qvRepBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepBase(double value) {
        this.qvRepBase = value;
    }

    /**
     * Gets the value of the qvSoufPointe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufPointe() {
        return qvSoufPointe;
    }

    /**
     * Sets the value of the qvSoufPointe property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufPointe(double value) {
        this.qvSoufPointe = value;
    }

    /**
     * Gets the value of the qvSoufBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufBase() {
        return qvSoufBase;
    }

    /**
     * Sets the value of the qvSoufBase property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufBase(double value) {
        this.qvSoufBase = value;
    }

    /**
     * Gets the value of the qvSoufCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufCH() {
        return qvSoufCH;
    }

    /**
     * Sets the value of the qvSoufCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufCH(double value) {
        this.qvSoufCH = value;
    }

    /**
     * Gets the value of the qvSoufZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufZNOcc() {
        return qvSoufZNOcc;
    }

    /**
     * Sets the value of the qvSoufZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufZNOcc(double value) {
        this.qvSoufZNOcc = value;
    }

    /**
     * Gets the value of the qvSoufZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvSoufZNInocc() {
        return qvSoufZNInocc;
    }

    /**
     * Sets the value of the qvSoufZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvSoufZNInocc(double value) {
        this.qvSoufZNInocc = value;
    }

    /**
     * Gets the value of the qvRepCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepCH() {
        return qvRepCH;
    }

    /**
     * Sets the value of the qvRepCH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepCH(double value) {
        this.qvRepCH = value;
    }

    /**
     * Gets the value of the qvRepZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepZNOcc() {
        return qvRepZNOcc;
    }

    /**
     * Sets the value of the qvRepZNOcc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepZNOcc(double value) {
        this.qvRepZNOcc = value;
    }

    /**
     * Gets the value of the qvRepZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQvRepZNInocc() {
        return qvRepZNInocc;
    }

    /**
     * Sets the value of the qvRepZNInocc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQvRepZNInocc(double value) {
        this.qvRepZNInocc = value;
    }

    /**
     * Gets the value of the idSystemeMecanique property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public int getIdSystemeMecanique() {
        return idSystemeMecanique;
    }

    /**
     * Sets the value of the idSystemeMecanique property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIdSystemeMecanique(int value) {
        this.idSystemeMecanique = value;
    }

    /**
     * Gets the value of the emetteurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataEmetteur }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataEmetteur getEmetteurCollection() {
        return emetteurCollection;
    }

    /**
     * Sets the value of the emetteurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataEmetteur }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setEmetteurCollection(ArrayOfRTDataEmetteur value) {
        this.emetteurCollection = value;
    }

    /**
     * Gets the value of the qspecRepRafnoc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQspecRepRafnoc() {
        return qspecRepRafnoc;
    }

    /**
     * Sets the value of the qspecRepRafnoc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQspecRepRafnoc(double value) {
        this.qspecRepRafnoc = value;
    }

    /**
     * Gets the value of the qspecSoufRafnoc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getQspecSoufRafnoc() {
        return qspecSoufRafnoc;
    }

    /**
     * Sets the value of the qspecSoufRafnoc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQspecSoufRafnoc(double value) {
        this.qspecSoufRafnoc = value;
    }

}
