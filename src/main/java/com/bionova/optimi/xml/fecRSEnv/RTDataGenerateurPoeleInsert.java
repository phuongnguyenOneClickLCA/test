
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Poele_Insert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Poele_Insert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Pngen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Eta_H_sys_N" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pauxvent" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeCombustibleBois" type="{}RT_TypesCombustiblesBois"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Poele_Insert", propOrder = {

})
public class RTDataGenerateurPoeleInsert {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Pngen")
    protected double pngen;
    @XmlElement(name = "Eta_H_sys_N")
    protected double etaHSysN;
    @XmlElement(name = "Pauxvent")
    protected double pauxvent;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "TypeCombustibleBois")
    protected int typeCombustibleBois;

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
     * Gets the value of the rdim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRdim() {
        return rdim;
    }

    /**
     * Sets the value of the rdim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRdim(Integer value) {
        this.rdim = value;
    }

    /**
     * Gets the value of the pngen property.
     * 
     */
    public double getPngen() {
        return pngen;
    }

    /**
     * Sets the value of the pngen property.
     * 
     */
    public void setPngen(double value) {
        this.pngen = value;
    }

    /**
     * Gets the value of the etaHSysN property.
     * 
     */
    public double getEtaHSysN() {
        return etaHSysN;
    }

    /**
     * Sets the value of the etaHSysN property.
     * 
     */
    public void setEtaHSysN(double value) {
        this.etaHSysN = value;
    }

    /**
     * Gets the value of the pauxvent property.
     * 
     */
    public double getPauxvent() {
        return pauxvent;
    }

    /**
     * Sets the value of the pauxvent property.
     * 
     */
    public void setPauxvent(double value) {
        this.pauxvent = value;
    }

    /**
     * Gets the value of the idprioriteCh property.
     * 
     */
    public int getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     */
    public void setIdprioriteCh(int value) {
        this.idprioriteCh = value;
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
     * Gets the value of the typeCombustibleBois property.
     * 
     */
    public int getTypeCombustibleBois() {
        return typeCombustibleBois;
    }

    /**
     * Sets the value of the typeCombustibleBois property.
     * 
     */
    public void setTypeCombustibleBois(int value) {
        this.typeCombustibleBois = value;
    }

}
