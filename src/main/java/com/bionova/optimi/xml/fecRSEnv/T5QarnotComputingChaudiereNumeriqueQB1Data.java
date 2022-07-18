
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_Qarnot_Computing_Chaudiere_Numerique_QB1
 * 
 * <p>Java class for T5_Qarnot_Computing_Chaudiere_Numerique_QB1_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Qarnot_Computing_Chaudiere_Numerique_QB1_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pnom_chaudiere" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux_chaudiere" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "T5_Qarnot_Computing_Chaudiere_Numerique_QB1_Data", propOrder = {

})
public class T5QarnotComputingChaudiereNumeriqueQB1Data {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Pnom_chaudiere")
    protected double pnomChaudiere;
    @XmlElement(name = "Paux_chaudiere")
    protected double pauxChaudiere;
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
     * Gets the value of the idprioriteEcs property.
     * 
     */
    public int getIdprioriteEcs() {
        return idprioriteEcs;
    }

    /**
     * Sets the value of the idprioriteEcs property.
     * 
     */
    public void setIdprioriteEcs(int value) {
        this.idprioriteEcs = value;
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
