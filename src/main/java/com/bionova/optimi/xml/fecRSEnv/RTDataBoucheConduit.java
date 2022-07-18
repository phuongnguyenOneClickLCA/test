
package com.bionova.optimi.xml.fecRSEnv;

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
 *         &lt;element name="Qv_souf_CH_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_CH_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_ZN_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_souf_ZN_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_CH_occ" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qv_rep_CH_inocc" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
public class RTDataBoucheConduit {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_Bouche_Conduit", required = true)
    protected String typeBoucheConduit;
    @XmlElement(name = "Cdep", required = true)
    protected String cdep;
    @XmlElement(name = "Cdep_Value")
    protected double cdepValue;
    @XmlElement(name = "Classe_Etancheite", required = true)
    protected String classeEtancheite;
    @XmlElement(name = "Ratfuitevc")
    protected double ratfuitevc;
    @XmlElement(name = "Type_Regul_Non_Res", required = true)
    protected String typeRegulNonRes;
    @XmlElement(name = "Cndbnr_Value")
    protected double cndbnrValue;
    @XmlElement(name = "Type_Regul_Res", required = true)
    protected String typeRegulRes;
    @XmlElement(name = "R_rep")
    protected double rRep;
    @XmlElement(name = "R_souf")
    protected double rSouf;
    @XmlElement(name = "Qv_rep_occ")
    protected double qvRepOcc;
    @XmlElement(name = "Qv_rep_inocc")
    protected double qvRepInocc;
    @XmlElement(name = "Qv_souf_occ")
    protected double qvSoufOcc;
    @XmlElement(name = "Qv_souf_inocc")
    protected double qvSoufInocc;
    @XmlElement(name = "Qv_rep_pointe")
    protected double qvRepPointe;
    @XmlElement(name = "Qv_rep_base")
    protected double qvRepBase;
    @XmlElement(name = "Qv_souf_pointe")
    protected double qvSoufPointe;
    @XmlElement(name = "Qv_souf_base")
    protected double qvSoufBase;
    @XmlElement(name = "Qv_souf_CH_occ")
    protected double qvSoufCHOcc;
    @XmlElement(name = "Qv_souf_CH_inocc")
    protected double qvSoufCHInocc;
    @XmlElement(name = "Qv_souf_ZN_occ")
    protected double qvSoufZNOcc;
    @XmlElement(name = "Qv_souf_ZN_inocc")
    protected double qvSoufZNInocc;
    @XmlElement(name = "Qv_rep_CH_occ")
    protected double qvRepCHOcc;
    @XmlElement(name = "Qv_rep_CH_inocc")
    protected double qvRepCHInocc;
    @XmlElement(name = "Qv_rep_ZN_occ")
    protected double qvRepZNOcc;
    @XmlElement(name = "Qv_rep_ZN_inocc")
    protected double qvRepZNInocc;
    @XmlElement(name = "Id_Systeme_Mecanique", defaultValue = "0")
    protected int idSystemeMecanique;
    @XmlElement(name = "Emetteur_Collection")
    protected ArrayOfRTDataEmetteur emetteurCollection;
    @XmlElement(name = "Qspec_rep_rafnoc")
    protected double qspecRepRafnoc;
    @XmlElement(name = "Qspec_souf_rafnoc")
    protected double qspecSoufRafnoc;

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
     * Gets the value of the typeBoucheConduit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setCdep(String value) {
        this.cdep = value;
    }

    /**
     * Gets the value of the cdepValue property.
     * 
     */
    public double getCdepValue() {
        return cdepValue;
    }

    /**
     * Sets the value of the cdepValue property.
     * 
     */
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
    public void setClasseEtancheite(String value) {
        this.classeEtancheite = value;
    }

    /**
     * Gets the value of the ratfuitevc property.
     * 
     */
    public double getRatfuitevc() {
        return ratfuitevc;
    }

    /**
     * Sets the value of the ratfuitevc property.
     * 
     */
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
    public void setTypeRegulNonRes(String value) {
        this.typeRegulNonRes = value;
    }

    /**
     * Gets the value of the cndbnrValue property.
     * 
     */
    public double getCndbnrValue() {
        return cndbnrValue;
    }

    /**
     * Sets the value of the cndbnrValue property.
     * 
     */
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
    public void setTypeRegulRes(String value) {
        this.typeRegulRes = value;
    }

    /**
     * Gets the value of the rRep property.
     * 
     */
    public double getRRep() {
        return rRep;
    }

    /**
     * Sets the value of the rRep property.
     * 
     */
    public void setRRep(double value) {
        this.rRep = value;
    }

    /**
     * Gets the value of the rSouf property.
     * 
     */
    public double getRSouf() {
        return rSouf;
    }

    /**
     * Sets the value of the rSouf property.
     * 
     */
    public void setRSouf(double value) {
        this.rSouf = value;
    }

    /**
     * Gets the value of the qvRepOcc property.
     * 
     */
    public double getQvRepOcc() {
        return qvRepOcc;
    }

    /**
     * Sets the value of the qvRepOcc property.
     * 
     */
    public void setQvRepOcc(double value) {
        this.qvRepOcc = value;
    }

    /**
     * Gets the value of the qvRepInocc property.
     * 
     */
    public double getQvRepInocc() {
        return qvRepInocc;
    }

    /**
     * Sets the value of the qvRepInocc property.
     * 
     */
    public void setQvRepInocc(double value) {
        this.qvRepInocc = value;
    }

    /**
     * Gets the value of the qvSoufOcc property.
     * 
     */
    public double getQvSoufOcc() {
        return qvSoufOcc;
    }

    /**
     * Sets the value of the qvSoufOcc property.
     * 
     */
    public void setQvSoufOcc(double value) {
        this.qvSoufOcc = value;
    }

    /**
     * Gets the value of the qvSoufInocc property.
     * 
     */
    public double getQvSoufInocc() {
        return qvSoufInocc;
    }

    /**
     * Sets the value of the qvSoufInocc property.
     * 
     */
    public void setQvSoufInocc(double value) {
        this.qvSoufInocc = value;
    }

    /**
     * Gets the value of the qvRepPointe property.
     * 
     */
    public double getQvRepPointe() {
        return qvRepPointe;
    }

    /**
     * Sets the value of the qvRepPointe property.
     * 
     */
    public void setQvRepPointe(double value) {
        this.qvRepPointe = value;
    }

    /**
     * Gets the value of the qvRepBase property.
     * 
     */
    public double getQvRepBase() {
        return qvRepBase;
    }

    /**
     * Sets the value of the qvRepBase property.
     * 
     */
    public void setQvRepBase(double value) {
        this.qvRepBase = value;
    }

    /**
     * Gets the value of the qvSoufPointe property.
     * 
     */
    public double getQvSoufPointe() {
        return qvSoufPointe;
    }

    /**
     * Sets the value of the qvSoufPointe property.
     * 
     */
    public void setQvSoufPointe(double value) {
        this.qvSoufPointe = value;
    }

    /**
     * Gets the value of the qvSoufBase property.
     * 
     */
    public double getQvSoufBase() {
        return qvSoufBase;
    }

    /**
     * Sets the value of the qvSoufBase property.
     * 
     */
    public void setQvSoufBase(double value) {
        this.qvSoufBase = value;
    }

    /**
     * Gets the value of the qvSoufCHOcc property.
     * 
     */
    public double getQvSoufCHOcc() {
        return qvSoufCHOcc;
    }

    /**
     * Sets the value of the qvSoufCHOcc property.
     * 
     */
    public void setQvSoufCHOcc(double value) {
        this.qvSoufCHOcc = value;
    }

    /**
     * Gets the value of the qvSoufCHInocc property.
     * 
     */
    public double getQvSoufCHInocc() {
        return qvSoufCHInocc;
    }

    /**
     * Sets the value of the qvSoufCHInocc property.
     * 
     */
    public void setQvSoufCHInocc(double value) {
        this.qvSoufCHInocc = value;
    }

    /**
     * Gets the value of the qvSoufZNOcc property.
     * 
     */
    public double getQvSoufZNOcc() {
        return qvSoufZNOcc;
    }

    /**
     * Sets the value of the qvSoufZNOcc property.
     * 
     */
    public void setQvSoufZNOcc(double value) {
        this.qvSoufZNOcc = value;
    }

    /**
     * Gets the value of the qvSoufZNInocc property.
     * 
     */
    public double getQvSoufZNInocc() {
        return qvSoufZNInocc;
    }

    /**
     * Sets the value of the qvSoufZNInocc property.
     * 
     */
    public void setQvSoufZNInocc(double value) {
        this.qvSoufZNInocc = value;
    }

    /**
     * Gets the value of the qvRepCHOcc property.
     * 
     */
    public double getQvRepCHOcc() {
        return qvRepCHOcc;
    }

    /**
     * Sets the value of the qvRepCHOcc property.
     * 
     */
    public void setQvRepCHOcc(double value) {
        this.qvRepCHOcc = value;
    }

    /**
     * Gets the value of the qvRepCHInocc property.
     * 
     */
    public double getQvRepCHInocc() {
        return qvRepCHInocc;
    }

    /**
     * Sets the value of the qvRepCHInocc property.
     * 
     */
    public void setQvRepCHInocc(double value) {
        this.qvRepCHInocc = value;
    }

    /**
     * Gets the value of the qvRepZNOcc property.
     * 
     */
    public double getQvRepZNOcc() {
        return qvRepZNOcc;
    }

    /**
     * Sets the value of the qvRepZNOcc property.
     * 
     */
    public void setQvRepZNOcc(double value) {
        this.qvRepZNOcc = value;
    }

    /**
     * Gets the value of the qvRepZNInocc property.
     * 
     */
    public double getQvRepZNInocc() {
        return qvRepZNInocc;
    }

    /**
     * Sets the value of the qvRepZNInocc property.
     * 
     */
    public void setQvRepZNInocc(double value) {
        this.qvRepZNInocc = value;
    }

    /**
     * Gets the value of the idSystemeMecanique property.
     * 
     */
    public int getIdSystemeMecanique() {
        return idSystemeMecanique;
    }

    /**
     * Sets the value of the idSystemeMecanique property.
     * 
     */
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
    public void setEmetteurCollection(ArrayOfRTDataEmetteur value) {
        this.emetteurCollection = value;
    }

    /**
     * Gets the value of the qspecRepRafnoc property.
     * 
     */
    public double getQspecRepRafnoc() {
        return qspecRepRafnoc;
    }

    /**
     * Sets the value of the qspecRepRafnoc property.
     * 
     */
    public void setQspecRepRafnoc(double value) {
        this.qspecRepRafnoc = value;
    }

    /**
     * Gets the value of the qspecSoufRafnoc property.
     * 
     */
    public double getQspecSoufRafnoc() {
        return qspecSoufRafnoc;
    }

    /**
     * Sets the value of the qspecSoufRafnoc property.
     * 
     */
    public void setQspecSoufRafnoc(double value) {
        this.qspecSoufRafnoc = value;
    }

}
