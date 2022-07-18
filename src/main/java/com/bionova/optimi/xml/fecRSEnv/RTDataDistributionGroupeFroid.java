
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_Groupe_Froid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_Groupe_Froid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_2nd" type="{}E_Type_2nd"/>
 *         &lt;element name="Lvc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lhvc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q2nd_Resid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Dist_1re" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Gest_2nd_Fr" type="{}E_Gest_2nd_Fr"/>
 *         &lt;element name="Mode_Reg_Debit_Fr" type="{}E_Mode_Reg_Debit"/>
 *         &lt;element name="Theta_Dep_Dim_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Ret_Dim_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_Em_Dim_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qnom_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Vc_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Hvc_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_Circ_2nd_Fr" type="{}E_Gest_Circ_2nd"/>
 *         &lt;element name="Pcirculateur_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Distribution_Groupe_Froid", propOrder = {

})
public class RTDataDistributionGroupeFroid {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_2nd", required = true)
    protected String type2Nd;
    @XmlElement(name = "Lvc")
    protected double lvc;
    @XmlElement(name = "Lhvc")
    protected double lhvc;
    @XmlElement(name = "Q2nd_Resid")
    protected double q2NdResid;
    @XmlElement(name = "Id_Et", defaultValue = "0")
    protected int idEt;
    @XmlElement(name = "Id_Dist_1re", defaultValue = "0")
    protected int idDist1Re;
    @XmlElement(name = "Gest_2nd_Fr", required = true)
    protected String gest2NdFr;
    @XmlElement(name = "Mode_Reg_Debit_Fr", required = true)
    protected String modeRegDebitFr;
    @XmlElement(name = "Theta_Dep_Dim_Fr")
    protected double thetaDepDimFr;
    @XmlElement(name = "Theta_Ret_Dim_Fr")
    protected double thetaRetDimFr;
    @XmlElement(name = "Delta_Theta_Em_Dim_Fr")
    protected double deltaThetaEmDimFr;
    @XmlElement(name = "Qnom_Fr")
    protected double qnomFr;
    @XmlElement(name = "Umoyen_Vc_Fr")
    protected double umoyenVcFr;
    @XmlElement(name = "Umoyen_Hvc_Fr")
    protected double umoyenHvcFr;
    @XmlElement(name = "Gest_Circ_2nd_Fr", required = true)
    protected String gestCirc2NdFr;
    @XmlElement(name = "Pcirculateur_Fr")
    protected double pcirculateurFr;

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
     * Gets the value of the type2Nd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType2Nd() {
        return type2Nd;
    }

    /**
     * Sets the value of the type2Nd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType2Nd(String value) {
        this.type2Nd = value;
    }

    /**
     * Gets the value of the lvc property.
     * 
     */
    public double getLvc() {
        return lvc;
    }

    /**
     * Sets the value of the lvc property.
     * 
     */
    public void setLvc(double value) {
        this.lvc = value;
    }

    /**
     * Gets the value of the lhvc property.
     * 
     */
    public double getLhvc() {
        return lhvc;
    }

    /**
     * Sets the value of the lhvc property.
     * 
     */
    public void setLhvc(double value) {
        this.lhvc = value;
    }

    /**
     * Gets the value of the q2NdResid property.
     * 
     */
    public double getQ2NdResid() {
        return q2NdResid;
    }

    /**
     * Sets the value of the q2NdResid property.
     * 
     */
    public void setQ2NdResid(double value) {
        this.q2NdResid = value;
    }

    /**
     * Gets the value of the idEt property.
     * 
     */
    public int getIdEt() {
        return idEt;
    }

    /**
     * Sets the value of the idEt property.
     * 
     */
    public void setIdEt(int value) {
        this.idEt = value;
    }

    /**
     * Gets the value of the idDist1Re property.
     * 
     */
    public int getIdDist1Re() {
        return idDist1Re;
    }

    /**
     * Sets the value of the idDist1Re property.
     * 
     */
    public void setIdDist1Re(int value) {
        this.idDist1Re = value;
    }

    /**
     * Gets the value of the gest2NdFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGest2NdFr() {
        return gest2NdFr;
    }

    /**
     * Sets the value of the gest2NdFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGest2NdFr(String value) {
        this.gest2NdFr = value;
    }

    /**
     * Gets the value of the modeRegDebitFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeRegDebitFr() {
        return modeRegDebitFr;
    }

    /**
     * Sets the value of the modeRegDebitFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeRegDebitFr(String value) {
        this.modeRegDebitFr = value;
    }

    /**
     * Gets the value of the thetaDepDimFr property.
     * 
     */
    public double getThetaDepDimFr() {
        return thetaDepDimFr;
    }

    /**
     * Sets the value of the thetaDepDimFr property.
     * 
     */
    public void setThetaDepDimFr(double value) {
        this.thetaDepDimFr = value;
    }

    /**
     * Gets the value of the thetaRetDimFr property.
     * 
     */
    public double getThetaRetDimFr() {
        return thetaRetDimFr;
    }

    /**
     * Sets the value of the thetaRetDimFr property.
     * 
     */
    public void setThetaRetDimFr(double value) {
        this.thetaRetDimFr = value;
    }

    /**
     * Gets the value of the deltaThetaEmDimFr property.
     * 
     */
    public double getDeltaThetaEmDimFr() {
        return deltaThetaEmDimFr;
    }

    /**
     * Sets the value of the deltaThetaEmDimFr property.
     * 
     */
    public void setDeltaThetaEmDimFr(double value) {
        this.deltaThetaEmDimFr = value;
    }

    /**
     * Gets the value of the qnomFr property.
     * 
     */
    public double getQnomFr() {
        return qnomFr;
    }

    /**
     * Sets the value of the qnomFr property.
     * 
     */
    public void setQnomFr(double value) {
        this.qnomFr = value;
    }

    /**
     * Gets the value of the umoyenVcFr property.
     * 
     */
    public double getUmoyenVcFr() {
        return umoyenVcFr;
    }

    /**
     * Sets the value of the umoyenVcFr property.
     * 
     */
    public void setUmoyenVcFr(double value) {
        this.umoyenVcFr = value;
    }

    /**
     * Gets the value of the umoyenHvcFr property.
     * 
     */
    public double getUmoyenHvcFr() {
        return umoyenHvcFr;
    }

    /**
     * Sets the value of the umoyenHvcFr property.
     * 
     */
    public void setUmoyenHvcFr(double value) {
        this.umoyenHvcFr = value;
    }

    /**
     * Gets the value of the gestCirc2NdFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGestCirc2NdFr() {
        return gestCirc2NdFr;
    }

    /**
     * Sets the value of the gestCirc2NdFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGestCirc2NdFr(String value) {
        this.gestCirc2NdFr = value;
    }

    /**
     * Gets the value of the pcirculateurFr property.
     * 
     */
    public double getPcirculateurFr() {
        return pcirculateurFr;
    }

    /**
     * Sets the value of the pcirculateurFr property.
     * 
     */
    public void setPcirculateurFr(double value) {
        this.pcirculateurFr = value;
    }

}