
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_CTA_Chaud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_CTA_Chaud">
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
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Is_Prechaud" type="{}RT_Oui_Non"/>
 *         &lt;element name="Is_Antigel" type="{}RT_Oui_Non"/>
 *         &lt;element name="Is_ChaudHR" type="{}RT_Oui_Non"/>
 *         &lt;element name="Gest_2nd_Ch" type="{}E_Gest_2nd_Ch"/>
 *         &lt;element name="Mode_Reg_Debit_Ch" type="{}E_Mode_Reg_Debit"/>
 *         &lt;element name="Theta_Dep_Dim_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Ret_Dim_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_Em_Dim_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qnom_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Vc_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Hvc_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_Circ_2nd_Ch" type="{}E_Gest_Circ_2nd"/>
 *         &lt;element name="Pcirculateur_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Distribution_CTA_Chaud", propOrder = {

})
public class RTDataDistributionCTAChaud {

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
    @XmlElement(name = "Id_Gen")
    protected int idGen;
    @XmlElement(name = "Id_Et")
    protected int idEt;
    @XmlElement(name = "Is_Prechaud", required = true)
    protected String isPrechaud;
    @XmlElement(name = "Is_Antigel", required = true)
    protected String isAntigel;
    @XmlElement(name = "Is_ChaudHR", required = true)
    protected String isChaudHR;
    @XmlElement(name = "Gest_2nd_Ch", required = true)
    protected String gest2NdCh;
    @XmlElement(name = "Mode_Reg_Debit_Ch", required = true)
    protected String modeRegDebitCh;
    @XmlElement(name = "Theta_Dep_Dim_Ch")
    protected double thetaDepDimCh;
    @XmlElement(name = "Theta_Ret_Dim_Ch")
    protected double thetaRetDimCh;
    @XmlElement(name = "Delta_Theta_Em_Dim_Ch")
    protected double deltaThetaEmDimCh;
    @XmlElement(name = "Qnom_Ch")
    protected double qnomCh;
    @XmlElement(name = "Umoyen_Vc_Ch")
    protected double umoyenVcCh;
    @XmlElement(name = "Umoyen_Hvc_Ch")
    protected double umoyenHvcCh;
    @XmlElement(name = "Gest_Circ_2nd_Ch", required = true)
    protected String gestCirc2NdCh;
    @XmlElement(name = "Pcirculateur_Ch")
    protected double pcirculateurCh;

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
     * Gets the value of the idGen property.
     * 
     */
    public int getIdGen() {
        return idGen;
    }

    /**
     * Sets the value of the idGen property.
     * 
     */
    public void setIdGen(int value) {
        this.idGen = value;
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
     * Gets the value of the isPrechaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPrechaud() {
        return isPrechaud;
    }

    /**
     * Sets the value of the isPrechaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPrechaud(String value) {
        this.isPrechaud = value;
    }

    /**
     * Gets the value of the isAntigel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAntigel() {
        return isAntigel;
    }

    /**
     * Sets the value of the isAntigel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAntigel(String value) {
        this.isAntigel = value;
    }

    /**
     * Gets the value of the isChaudHR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsChaudHR() {
        return isChaudHR;
    }

    /**
     * Sets the value of the isChaudHR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsChaudHR(String value) {
        this.isChaudHR = value;
    }

    /**
     * Gets the value of the gest2NdCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGest2NdCh() {
        return gest2NdCh;
    }

    /**
     * Sets the value of the gest2NdCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGest2NdCh(String value) {
        this.gest2NdCh = value;
    }

    /**
     * Gets the value of the modeRegDebitCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeRegDebitCh() {
        return modeRegDebitCh;
    }

    /**
     * Sets the value of the modeRegDebitCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeRegDebitCh(String value) {
        this.modeRegDebitCh = value;
    }

    /**
     * Gets the value of the thetaDepDimCh property.
     * 
     */
    public double getThetaDepDimCh() {
        return thetaDepDimCh;
    }

    /**
     * Sets the value of the thetaDepDimCh property.
     * 
     */
    public void setThetaDepDimCh(double value) {
        this.thetaDepDimCh = value;
    }

    /**
     * Gets the value of the thetaRetDimCh property.
     * 
     */
    public double getThetaRetDimCh() {
        return thetaRetDimCh;
    }

    /**
     * Sets the value of the thetaRetDimCh property.
     * 
     */
    public void setThetaRetDimCh(double value) {
        this.thetaRetDimCh = value;
    }

    /**
     * Gets the value of the deltaThetaEmDimCh property.
     * 
     */
    public double getDeltaThetaEmDimCh() {
        return deltaThetaEmDimCh;
    }

    /**
     * Sets the value of the deltaThetaEmDimCh property.
     * 
     */
    public void setDeltaThetaEmDimCh(double value) {
        this.deltaThetaEmDimCh = value;
    }

    /**
     * Gets the value of the qnomCh property.
     * 
     */
    public double getQnomCh() {
        return qnomCh;
    }

    /**
     * Sets the value of the qnomCh property.
     * 
     */
    public void setQnomCh(double value) {
        this.qnomCh = value;
    }

    /**
     * Gets the value of the umoyenVcCh property.
     * 
     */
    public double getUmoyenVcCh() {
        return umoyenVcCh;
    }

    /**
     * Sets the value of the umoyenVcCh property.
     * 
     */
    public void setUmoyenVcCh(double value) {
        this.umoyenVcCh = value;
    }

    /**
     * Gets the value of the umoyenHvcCh property.
     * 
     */
    public double getUmoyenHvcCh() {
        return umoyenHvcCh;
    }

    /**
     * Sets the value of the umoyenHvcCh property.
     * 
     */
    public void setUmoyenHvcCh(double value) {
        this.umoyenHvcCh = value;
    }

    /**
     * Gets the value of the gestCirc2NdCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGestCirc2NdCh() {
        return gestCirc2NdCh;
    }

    /**
     * Sets the value of the gestCirc2NdCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGestCirc2NdCh(String value) {
        this.gestCirc2NdCh = value;
    }

    /**
     * Gets the value of the pcirculateurCh property.
     * 
     */
    public double getPcirculateurCh() {
        return pcirculateurCh;
    }

    /**
     * Sets the value of the pcirculateurCh property.
     * 
     */
    public void setPcirculateurCh(double value) {
        this.pcirculateurCh = value;
    }

}
