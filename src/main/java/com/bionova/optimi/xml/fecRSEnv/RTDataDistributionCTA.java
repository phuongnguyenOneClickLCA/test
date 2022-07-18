
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_CTA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_CTA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_2nd" type="{}E_Type_2nd"/>
 *         &lt;element name="Lvc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lhvc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Q2nd_Resid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "RT_Data_Distribution_CTA", propOrder = {

})
public abstract class RTDataDistributionCTA {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
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

}
