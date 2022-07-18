
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Espace_Tampon_Solarise complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Espace_Tampon_Solarise">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="A_et" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Volet" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cm_et_surf" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Httfet" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Vventc_lim" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Uet_pb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Protected" type="{}RT_Oui_Non"/>
 *         &lt;element name="Protet_ext" type="{}E_Position_Protection"/>
 *         &lt;element name="Id_surv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Def_Qv_et_base" type="{}E_Definition_Qv_et_base"/>
 *         &lt;element name="Qv_et_base" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paroi_Ext_ET_Collection" type="{}ArrayOfRT_Data_Paroi_Ext_ET" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Espace_Tampon_Solarise", propOrder = {

})
public class RTDataEspaceTamponSolarise {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "A_et")
    protected double aEt;
    @XmlElement(name = "Volet")
    protected double volet;
    @XmlElement(name = "Cm_et_surf")
    protected Double cmEtSurf;
    @XmlElement(name = "Httfet")
    protected double httfet;
    @XmlElement(name = "Vventc_lim")
    protected Double vventcLim;
    @XmlElement(name = "Uet_pb")
    protected double uetPb;
    @XmlElement(name = "Is_Protected", required = true)
    protected String isProtected;
    @XmlElement(name = "Protet_ext", required = true)
    protected String protetExt;
    @XmlElement(name = "Id_surv")
    protected String idSurv;
    @XmlElement(name = "Def_Qv_et_base", required = true)
    protected String defQvEtBase;
    @XmlElement(name = "Qv_et_base")
    protected double qvEtBase;
    @XmlElement(name = "Paroi_Ext_ET_Collection")
    protected ArrayOfRTDataParoiExtET paroiExtETCollection;

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
     * Gets the value of the aEt property.
     * 
     */
    public double getAEt() {
        return aEt;
    }

    /**
     * Sets the value of the aEt property.
     * 
     */
    public void setAEt(double value) {
        this.aEt = value;
    }

    /**
     * Gets the value of the volet property.
     * 
     */
    public double getVolet() {
        return volet;
    }

    /**
     * Sets the value of the volet property.
     * 
     */
    public void setVolet(double value) {
        this.volet = value;
    }

    /**
     * Gets the value of the cmEtSurf property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCmEtSurf() {
        return cmEtSurf;
    }

    /**
     * Sets the value of the cmEtSurf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCmEtSurf(Double value) {
        this.cmEtSurf = value;
    }

    /**
     * Gets the value of the httfet property.
     * 
     */
    public double getHttfet() {
        return httfet;
    }

    /**
     * Sets the value of the httfet property.
     * 
     */
    public void setHttfet(double value) {
        this.httfet = value;
    }

    /**
     * Gets the value of the vventcLim property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVventcLim() {
        return vventcLim;
    }

    /**
     * Sets the value of the vventcLim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVventcLim(Double value) {
        this.vventcLim = value;
    }

    /**
     * Gets the value of the uetPb property.
     * 
     */
    public double getUetPb() {
        return uetPb;
    }

    /**
     * Sets the value of the uetPb property.
     * 
     */
    public void setUetPb(double value) {
        this.uetPb = value;
    }

    /**
     * Gets the value of the isProtected property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsProtected() {
        return isProtected;
    }

    /**
     * Sets the value of the isProtected property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsProtected(String value) {
        this.isProtected = value;
    }

    /**
     * Gets the value of the protetExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtetExt() {
        return protetExt;
    }

    /**
     * Sets the value of the protetExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtetExt(String value) {
        this.protetExt = value;
    }

    /**
     * Gets the value of the idSurv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSurv() {
        return idSurv;
    }

    /**
     * Sets the value of the idSurv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSurv(String value) {
        this.idSurv = value;
    }

    /**
     * Gets the value of the defQvEtBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefQvEtBase() {
        return defQvEtBase;
    }

    /**
     * Sets the value of the defQvEtBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefQvEtBase(String value) {
        this.defQvEtBase = value;
    }

    /**
     * Gets the value of the qvEtBase property.
     * 
     */
    public double getQvEtBase() {
        return qvEtBase;
    }

    /**
     * Sets the value of the qvEtBase property.
     * 
     */
    public void setQvEtBase(double value) {
        this.qvEtBase = value;
    }

    /**
     * Gets the value of the paroiExtETCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataParoiExtET }
     *     
     */
    public ArrayOfRTDataParoiExtET getParoiExtETCollection() {
        return paroiExtETCollection;
    }

    /**
     * Sets the value of the paroiExtETCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataParoiExtET }
     *     
     */
    public void setParoiExtETCollection(ArrayOfRTDataParoiExtET value) {
        this.paroiExtETCollection = value;
    }

}
