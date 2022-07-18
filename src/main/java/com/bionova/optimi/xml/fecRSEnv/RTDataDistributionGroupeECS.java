
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_Groupe_ECS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_Groupe_ECS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nb_dist_2nd_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="delta_lvc" type="{}E_Valeur_Defaut_Distribution_Secondaire"/>
 *         &lt;element name="l_vc_2nd_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="l_hvc_2nd_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="d_int_2nd_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_2nd_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Dist_Primaire" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Ballon" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Distribution_Groupe_ECS", propOrder = {

})
public class RTDataDistributionGroupeECS {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "nb_dist_2nd_e")
    protected double nbDist2NdE;
    @XmlElement(name = "delta_lvc", required = true)
    protected String deltaLvc;
    @XmlElement(name = "l_vc_2nd_e")
    protected double lVc2NdE;
    @XmlElement(name = "l_hvc_2nd_e")
    protected double lHvc2NdE;
    @XmlElement(name = "d_int_2nd_e")
    protected double dInt2NdE;
    @XmlElement(name = "theta_2nd_e")
    protected double theta2NdE;
    @XmlElement(name = "Id_Dist_Primaire", defaultValue = "0")
    protected int idDistPrimaire;
    @XmlElement(name = "Id_Ballon", defaultValue = "0")
    protected int idBallon;
    @XmlElement(name = "Id_Et", defaultValue = "0")
    protected int idEt;

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
     * Gets the value of the nbDist2NdE property.
     * 
     */
    public double getNbDist2NdE() {
        return nbDist2NdE;
    }

    /**
     * Sets the value of the nbDist2NdE property.
     * 
     */
    public void setNbDist2NdE(double value) {
        this.nbDist2NdE = value;
    }

    /**
     * Gets the value of the deltaLvc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeltaLvc() {
        return deltaLvc;
    }

    /**
     * Sets the value of the deltaLvc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeltaLvc(String value) {
        this.deltaLvc = value;
    }

    /**
     * Gets the value of the lVc2NdE property.
     * 
     */
    public double getLVc2NdE() {
        return lVc2NdE;
    }

    /**
     * Sets the value of the lVc2NdE property.
     * 
     */
    public void setLVc2NdE(double value) {
        this.lVc2NdE = value;
    }

    /**
     * Gets the value of the lHvc2NdE property.
     * 
     */
    public double getLHvc2NdE() {
        return lHvc2NdE;
    }

    /**
     * Sets the value of the lHvc2NdE property.
     * 
     */
    public void setLHvc2NdE(double value) {
        this.lHvc2NdE = value;
    }

    /**
     * Gets the value of the dInt2NdE property.
     * 
     */
    public double getDInt2NdE() {
        return dInt2NdE;
    }

    /**
     * Sets the value of the dInt2NdE property.
     * 
     */
    public void setDInt2NdE(double value) {
        this.dInt2NdE = value;
    }

    /**
     * Gets the value of the theta2NdE property.
     * 
     */
    public double getTheta2NdE() {
        return theta2NdE;
    }

    /**
     * Sets the value of the theta2NdE property.
     * 
     */
    public void setTheta2NdE(double value) {
        this.theta2NdE = value;
    }

    /**
     * Gets the value of the idDistPrimaire property.
     * 
     */
    public int getIdDistPrimaire() {
        return idDistPrimaire;
    }

    /**
     * Sets the value of the idDistPrimaire property.
     * 
     */
    public void setIdDistPrimaire(int value) {
        this.idDistPrimaire = value;
    }

    /**
     * Gets the value of the idBallon property.
     * 
     */
    public int getIdBallon() {
        return idBallon;
    }

    /**
     * Sets the value of the idBallon property.
     * 
     */
    public void setIdBallon(int value) {
        this.idBallon = value;
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

}
