
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Ventilation_Naturelle_Hybride complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Ventilation_Naturelle_Hybride">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_type_vent" type="{}E_Type_Ventilation_NatHyb"/>
 *         &lt;element name="Nid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="id_type_bouche" type="{}E_Type_Bouche_Extraction"/>
 *         &lt;element name="A_sect_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="A_sect_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dP_bea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Qv_bea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="A_cond" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Per_cond" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="h_cond" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ratfuite_vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cdep" type="{}E_Ventil_Deperdition"/>
 *         &lt;element name="Cdep_Value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Classe_Etancheite" type="{}E_Classe_Etancheite"/>
 *         &lt;element name="v_cond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="C_extr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Epsilon_Extr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Systeme_Mecanique" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="qspec_rep_conv_pointe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qspec_rep_conv_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Ventilation_Naturelle_Hybride", propOrder = {

})
public class RTDataVentilationNaturelleHybride {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "id_type_vent", required = true)
    protected String idTypeVent;
    @XmlElement(name = "Nid")
    protected double nid;
    @XmlElement(name = "id_type_bouche", required = true)
    protected String idTypeBouche;
    @XmlElement(name = "A_sect_base")
    protected double aSectBase;
    @XmlElement(name = "A_sect_pointe")
    protected double aSectPointe;
    @XmlElement(name = "dP_bea")
    protected String dpBea;
    @XmlElement(name = "Qv_bea")
    protected String qvBea;
    @XmlElement(name = "A_cond")
    protected double aCond;
    @XmlElement(name = "Per_cond")
    protected double perCond;
    @XmlElement(name = "h_cond")
    protected double hCond;
    @XmlElement(name = "Ratfuite_vc")
    protected double ratfuiteVc;
    @XmlElement(name = "Cdep", required = true)
    protected String cdep;
    @XmlElement(name = "Cdep_Value")
    protected double cdepValue;
    @XmlElement(name = "Classe_Etancheite", required = true)
    protected String classeEtancheite;
    @XmlElement(name = "v_cond")
    protected String vCond;
    @XmlElement(name = "C_extr")
    protected String cExtr;
    @XmlElement(name = "Epsilon_Extr")
    protected double epsilonExtr;
    @XmlElement(name = "Id_Systeme_Mecanique", defaultValue = "0")
    protected int idSystemeMecanique;
    @XmlElement(name = "qspec_rep_conv_pointe")
    protected double qspecRepConvPointe;
    @XmlElement(name = "qspec_rep_conv_base")
    protected double qspecRepConvBase;

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
     * Gets the value of the idTypeVent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTypeVent() {
        return idTypeVent;
    }

    /**
     * Sets the value of the idTypeVent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTypeVent(String value) {
        this.idTypeVent = value;
    }

    /**
     * Gets the value of the nid property.
     * 
     */
    public double getNid() {
        return nid;
    }

    /**
     * Sets the value of the nid property.
     * 
     */
    public void setNid(double value) {
        this.nid = value;
    }

    /**
     * Gets the value of the idTypeBouche property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTypeBouche() {
        return idTypeBouche;
    }

    /**
     * Sets the value of the idTypeBouche property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTypeBouche(String value) {
        this.idTypeBouche = value;
    }

    /**
     * Gets the value of the aSectBase property.
     * 
     */
    public double getASectBase() {
        return aSectBase;
    }

    /**
     * Sets the value of the aSectBase property.
     * 
     */
    public void setASectBase(double value) {
        this.aSectBase = value;
    }

    /**
     * Gets the value of the aSectPointe property.
     * 
     */
    public double getASectPointe() {
        return aSectPointe;
    }

    /**
     * Sets the value of the aSectPointe property.
     * 
     */
    public void setASectPointe(double value) {
        this.aSectPointe = value;
    }

    /**
     * Gets the value of the dpBea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDPBea() {
        return dpBea;
    }

    /**
     * Sets the value of the dpBea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDPBea(String value) {
        this.dpBea = value;
    }

    /**
     * Gets the value of the qvBea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQvBea() {
        return qvBea;
    }

    /**
     * Sets the value of the qvBea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQvBea(String value) {
        this.qvBea = value;
    }

    /**
     * Gets the value of the aCond property.
     * 
     */
    public double getACond() {
        return aCond;
    }

    /**
     * Sets the value of the aCond property.
     * 
     */
    public void setACond(double value) {
        this.aCond = value;
    }

    /**
     * Gets the value of the perCond property.
     * 
     */
    public double getPerCond() {
        return perCond;
    }

    /**
     * Sets the value of the perCond property.
     * 
     */
    public void setPerCond(double value) {
        this.perCond = value;
    }

    /**
     * Gets the value of the hCond property.
     * 
     */
    public double getHCond() {
        return hCond;
    }

    /**
     * Sets the value of the hCond property.
     * 
     */
    public void setHCond(double value) {
        this.hCond = value;
    }

    /**
     * Gets the value of the ratfuiteVc property.
     * 
     */
    public double getRatfuiteVc() {
        return ratfuiteVc;
    }

    /**
     * Sets the value of the ratfuiteVc property.
     * 
     */
    public void setRatfuiteVc(double value) {
        this.ratfuiteVc = value;
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
     * Gets the value of the vCond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCond() {
        return vCond;
    }

    /**
     * Sets the value of the vCond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCond(String value) {
        this.vCond = value;
    }

    /**
     * Gets the value of the cExtr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCExtr() {
        return cExtr;
    }

    /**
     * Sets the value of the cExtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCExtr(String value) {
        this.cExtr = value;
    }

    /**
     * Gets the value of the epsilonExtr property.
     * 
     */
    public double getEpsilonExtr() {
        return epsilonExtr;
    }

    /**
     * Sets the value of the epsilonExtr property.
     * 
     */
    public void setEpsilonExtr(double value) {
        this.epsilonExtr = value;
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
     * Gets the value of the qspecRepConvPointe property.
     * 
     */
    public double getQspecRepConvPointe() {
        return qspecRepConvPointe;
    }

    /**
     * Sets the value of the qspecRepConvPointe property.
     * 
     */
    public void setQspecRepConvPointe(double value) {
        this.qspecRepConvPointe = value;
    }

    /**
     * Gets the value of the qspecRepConvBase property.
     * 
     */
    public double getQspecRepConvBase() {
        return qspecRepConvBase;
    }

    /**
     * Sets the value of the qspecRepConvBase property.
     * 
     */
    public void setQspecRepConvBase(double value) {
        this.qspecRepConvBase = value;
    }

}
