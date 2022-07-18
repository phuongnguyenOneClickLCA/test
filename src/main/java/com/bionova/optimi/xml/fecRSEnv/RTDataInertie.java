
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Inertie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Inertie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Inertie_Quotidienne" type="{}E_Inertie_Quotidienne"/>
 *         &lt;element name="Amq_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cmq_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Inertie_Sequentielle" type="{}E_Inertie_Sequentielle"/>
 *         &lt;element name="Ams_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cms_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Type_Inertie_Annuelle" type="{}E_Inertie_Annuelle"/>
 *         &lt;element name="Ama_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cma_surf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Inertie", propOrder = {

})
public class RTDataInertie {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_Inertie_Quotidienne", required = true)
    protected String typeInertieQuotidienne;
    @XmlElement(name = "Amq_surf")
    protected double amqSurf;
    @XmlElement(name = "Cmq_surf")
    protected double cmqSurf;
    @XmlElement(name = "Type_Inertie_Sequentielle", required = true)
    protected String typeInertieSequentielle;
    @XmlElement(name = "Ams_surf")
    protected double amsSurf;
    @XmlElement(name = "Cms_surf")
    protected double cmsSurf;
    @XmlElement(name = "Type_Inertie_Annuelle", required = true)
    protected String typeInertieAnnuelle;
    @XmlElement(name = "Ama_surf")
    protected double amaSurf;
    @XmlElement(name = "Cma_surf")
    protected double cmaSurf;

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
     * Gets the value of the typeInertieQuotidienne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeInertieQuotidienne() {
        return typeInertieQuotidienne;
    }

    /**
     * Sets the value of the typeInertieQuotidienne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeInertieQuotidienne(String value) {
        this.typeInertieQuotidienne = value;
    }

    /**
     * Gets the value of the amqSurf property.
     * 
     */
    public double getAmqSurf() {
        return amqSurf;
    }

    /**
     * Sets the value of the amqSurf property.
     * 
     */
    public void setAmqSurf(double value) {
        this.amqSurf = value;
    }

    /**
     * Gets the value of the cmqSurf property.
     * 
     */
    public double getCmqSurf() {
        return cmqSurf;
    }

    /**
     * Sets the value of the cmqSurf property.
     * 
     */
    public void setCmqSurf(double value) {
        this.cmqSurf = value;
    }

    /**
     * Gets the value of the typeInertieSequentielle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeInertieSequentielle() {
        return typeInertieSequentielle;
    }

    /**
     * Sets the value of the typeInertieSequentielle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeInertieSequentielle(String value) {
        this.typeInertieSequentielle = value;
    }

    /**
     * Gets the value of the amsSurf property.
     * 
     */
    public double getAmsSurf() {
        return amsSurf;
    }

    /**
     * Sets the value of the amsSurf property.
     * 
     */
    public void setAmsSurf(double value) {
        this.amsSurf = value;
    }

    /**
     * Gets the value of the cmsSurf property.
     * 
     */
    public double getCmsSurf() {
        return cmsSurf;
    }

    /**
     * Sets the value of the cmsSurf property.
     * 
     */
    public void setCmsSurf(double value) {
        this.cmsSurf = value;
    }

    /**
     * Gets the value of the typeInertieAnnuelle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeInertieAnnuelle() {
        return typeInertieAnnuelle;
    }

    /**
     * Sets the value of the typeInertieAnnuelle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeInertieAnnuelle(String value) {
        this.typeInertieAnnuelle = value;
    }

    /**
     * Gets the value of the amaSurf property.
     * 
     */
    public double getAmaSurf() {
        return amaSurf;
    }

    /**
     * Sets the value of the amaSurf property.
     * 
     */
    public void setAmaSurf(double value) {
        this.amaSurf = value;
    }

    /**
     * Gets the value of the cmaSurf property.
     * 
     */
    public double getCmaSurf() {
        return cmaSurf;
    }

    /**
     * Sets the value of the cmaSurf property.
     * 
     */
    public void setCmaSurf(double value) {
        this.cmaSurf = value;
    }

}
