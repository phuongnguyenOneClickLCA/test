
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_Qarnot_Computing_Chaudiere_Numerique_Distribution
 * 
 * <p>Java class for T5_Qarnot_Computing_Chaudiere_Numerique_Distribution_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Qarnot_Computing_Chaudiere_Numerique_Distribution_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="L_vc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_hvc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_prim_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_gest_circ_e" type="{}E_Type_Gestion_Circulateurs"/>
 *         &lt;element name="P_circ_prim_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Paux_chaudiere" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pnom_chaudiere" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_max_Pmax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Alpha_Pfou" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta_Pfou" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Qarnot_Computing_Chaudiere_Numerique_Distribution_Data", propOrder = {

})
public class T5QarnotComputingChaudiereNumeriqueDistributionData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Id_Gen")
    protected int idGen;
    @XmlElement(name = "L_vc_prim_bcl_e")
    protected double lVcPrimBclE;
    @XmlElement(name = "L_hvc_prim_bcl_e")
    protected double lHvcPrimBclE;
    @XmlElement(name = "U_prim_e")
    protected double uPrimE;
    @XmlElement(name = "Type_gest_circ_e", required = true)
    protected String typeGestCircE;
    @XmlElement(name = "P_circ_prim_e")
    protected double pCircPrimE;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Paux_chaudiere")
    protected double pauxChaudiere;
    @XmlElement(name = "Pnom_chaudiere")
    protected double pnomChaudiere;
    @XmlElement(name = "Theta_max_Pmax")
    protected double thetaMaxPmax;
    @XmlElement(name = "Theta_max")
    protected double thetaMax;
    @XmlElement(name = "Alpha_Pfou")
    protected double alphaPfou;
    @XmlElement(name = "Beta_Pfou")
    protected double betaPfou;

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
     * Gets the value of the lVcPrimBclE property.
     * 
     */
    public double getLVcPrimBclE() {
        return lVcPrimBclE;
    }

    /**
     * Sets the value of the lVcPrimBclE property.
     * 
     */
    public void setLVcPrimBclE(double value) {
        this.lVcPrimBclE = value;
    }

    /**
     * Gets the value of the lHvcPrimBclE property.
     * 
     */
    public double getLHvcPrimBclE() {
        return lHvcPrimBclE;
    }

    /**
     * Sets the value of the lHvcPrimBclE property.
     * 
     */
    public void setLHvcPrimBclE(double value) {
        this.lHvcPrimBclE = value;
    }

    /**
     * Gets the value of the uPrimE property.
     * 
     */
    public double getUPrimE() {
        return uPrimE;
    }

    /**
     * Sets the value of the uPrimE property.
     * 
     */
    public void setUPrimE(double value) {
        this.uPrimE = value;
    }

    /**
     * Gets the value of the typeGestCircE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestCircE() {
        return typeGestCircE;
    }

    /**
     * Sets the value of the typeGestCircE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestCircE(String value) {
        this.typeGestCircE = value;
    }

    /**
     * Gets the value of the pCircPrimE property.
     * 
     */
    public double getPCircPrimE() {
        return pCircPrimE;
    }

    /**
     * Sets the value of the pCircPrimE property.
     * 
     */
    public void setPCircPrimE(double value) {
        this.pCircPrimE = value;
    }

    /**
     * Gets the value of the rdim property.
     * 
     */
    public int getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     */
    public void setRdim(int value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the pauxChaudiere property.
     * 
     */
    public double getPauxChaudiere() {
        return pauxChaudiere;
    }

    /**
     * Sets the value of the pauxChaudiere property.
     * 
     */
    public void setPauxChaudiere(double value) {
        this.pauxChaudiere = value;
    }

    /**
     * Gets the value of the pnomChaudiere property.
     * 
     */
    public double getPnomChaudiere() {
        return pnomChaudiere;
    }

    /**
     * Sets the value of the pnomChaudiere property.
     * 
     */
    public void setPnomChaudiere(double value) {
        this.pnomChaudiere = value;
    }

    /**
     * Gets the value of the thetaMaxPmax property.
     * 
     */
    public double getThetaMaxPmax() {
        return thetaMaxPmax;
    }

    /**
     * Sets the value of the thetaMaxPmax property.
     * 
     */
    public void setThetaMaxPmax(double value) {
        this.thetaMaxPmax = value;
    }

    /**
     * Gets the value of the thetaMax property.
     * 
     */
    public double getThetaMax() {
        return thetaMax;
    }

    /**
     * Sets the value of the thetaMax property.
     * 
     */
    public void setThetaMax(double value) {
        this.thetaMax = value;
    }

    /**
     * Gets the value of the alphaPfou property.
     * 
     */
    public double getAlphaPfou() {
        return alphaPfou;
    }

    /**
     * Sets the value of the alphaPfou property.
     * 
     */
    public void setAlphaPfou(double value) {
        this.alphaPfou = value;
    }

    /**
     * Gets the value of the betaPfou property.
     * 
     */
    public double getBetaPfou() {
        return betaPfou;
    }

    /**
     * Sets the value of the betaPfou property.
     * 
     */
    public void setBetaPfou(double value) {
        this.betaPfou = value;
    }

}
