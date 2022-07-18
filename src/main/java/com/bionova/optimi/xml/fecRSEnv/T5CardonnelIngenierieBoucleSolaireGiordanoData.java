
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * add 7100 - T5_Giordano_Production_Stockage - boucle solaire
 * 
 * <p>Java class for T5_CardonnelIngenierie_Boucle_Solaire_Giordano_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CardonnelIngenierie_Boucle_Solaire_Giordano_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Masque_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;any processContents='skip' minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Ori" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Is_regulateur_temperature" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fl_Aval" type="{}E_Id_Fluide" minOccurs="0"/>
 *         &lt;element name="Id_Fou_Gen" type="{}RT_Types_Fonctionnements_Generateur" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CardonnelIngenierie_Boucle_Solaire_Giordano_Data", propOrder = {

})
public class T5CardonnelIngenierieBoucleSolaireGiordanoData {

    @XmlElement(name = "Masque_Collection")
    protected T5CardonnelIngenierieBoucleSolaireGiordanoData.MasqueCollection masqueCollection;
    @XmlElement(name = "Alpha")
    protected Double alpha;
    @XmlElement(name = "Beta")
    protected Double beta;
    @XmlElement(name = "Id_Ori")
    protected Integer idOri;
    @XmlElement(name = "Is_regulateur_temperature")
    protected Boolean isRegulateurTemperature;
    @XmlElement(name = "Index")
    protected Integer index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Id_Fl_Aval")
    protected String idFlAval;
    @XmlElement(name = "Id_Fou_Gen")
    protected String idFouGen;

    /**
     * Gets the value of the masqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link T5CardonnelIngenierieBoucleSolaireGiordanoData.MasqueCollection }
     *     
     */
    public T5CardonnelIngenierieBoucleSolaireGiordanoData.MasqueCollection getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link T5CardonnelIngenierieBoucleSolaireGiordanoData.MasqueCollection }
     *     
     */
    public void setMasqueCollection(T5CardonnelIngenierieBoucleSolaireGiordanoData.MasqueCollection value) {
        this.masqueCollection = value;
    }

    /**
     * Gets the value of the alpha property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAlpha(Double value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBeta(Double value) {
        this.beta = value;
    }

    /**
     * Gets the value of the idOri property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdOri() {
        return idOri;
    }

    /**
     * Sets the value of the idOri property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdOri(Integer value) {
        this.idOri = value;
    }

    /**
     * Gets the value of the isRegulateurTemperature property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRegulateurTemperature() {
        return isRegulateurTemperature;
    }

    /**
     * Sets the value of the isRegulateurTemperature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRegulateurTemperature(Boolean value) {
        this.isRegulateurTemperature = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
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
     * Gets the value of the idFlAval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFlAval() {
        return idFlAval;
    }

    /**
     * Sets the value of the idFlAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFlAval(String value) {
        this.idFlAval = value;
    }

    /**
     * Gets the value of the idFouGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouGen() {
        return idFouGen;
    }

    /**
     * Sets the value of the idFouGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouGen(String value) {
        this.idFouGen = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;any processContents='skip' minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "any"
    })
    public static class MasqueCollection {

        @XmlAnyElement
        protected List<Element> any;

        /**
         * Gets the value of the any property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the any property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAny().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Element }
         * 
         * 
         */
        public List<Element> getAny() {
            if (any == null) {
                any = new ArrayList<Element>();
            }
            return this.any;
        }

    }

}
