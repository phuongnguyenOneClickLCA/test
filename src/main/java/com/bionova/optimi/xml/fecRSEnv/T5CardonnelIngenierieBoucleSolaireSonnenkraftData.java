
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
 * add 7100 - T5_CardonnelIngenierie_BoucleSolaire_Sonnenkraft
 * 
 * <p>Java class for T5_CardonnelIngenierie_BoucleSolaire_Sonnenkraft_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CardonnelIngenierie_BoucleSolaire_Sonnenkraft_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Masque_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;any processContents='skip' maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Ori" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Is_regulateur_temperature" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fl_Aval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fou_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CardonnelIngenierie_BoucleSolaire_Sonnenkraft_Data", propOrder = {

})
public class T5CardonnelIngenierieBoucleSolaireSonnenkraftData {

    @XmlElement(name = "Masque_Collection")
    protected T5CardonnelIngenierieBoucleSolaireSonnenkraftData.MasqueCollection masqueCollection;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "Id_Ori")
    protected int idOri;
    @XmlElement(name = "Is_regulateur_temperature")
    protected boolean isRegulateurTemperature;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Id_Fl_Aval")
    protected int idFlAval;
    @XmlElement(name = "Id_Fou_Gen")
    protected int idFouGen;

    /**
     * Gets the value of the masqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link T5CardonnelIngenierieBoucleSolaireSonnenkraftData.MasqueCollection }
     *     
     */
    public T5CardonnelIngenierieBoucleSolaireSonnenkraftData.MasqueCollection getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link T5CardonnelIngenierieBoucleSolaireSonnenkraftData.MasqueCollection }
     *     
     */
    public void setMasqueCollection(T5CardonnelIngenierieBoucleSolaireSonnenkraftData.MasqueCollection value) {
        this.masqueCollection = value;
    }

    /**
     * Gets the value of the alpha property.
     * 
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets the value of the alpha property.
     * 
     */
    public void setAlpha(double value) {
        this.alpha = value;
    }

    /**
     * Gets the value of the beta property.
     * 
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Sets the value of the beta property.
     * 
     */
    public void setBeta(double value) {
        this.beta = value;
    }

    /**
     * Gets the value of the idOri property.
     * 
     */
    public int getIdOri() {
        return idOri;
    }

    /**
     * Sets the value of the idOri property.
     * 
     */
    public void setIdOri(int value) {
        this.idOri = value;
    }

    /**
     * Gets the value of the isRegulateurTemperature property.
     * 
     */
    public boolean isIsRegulateurTemperature() {
        return isRegulateurTemperature;
    }

    /**
     * Sets the value of the isRegulateurTemperature property.
     * 
     */
    public void setIsRegulateurTemperature(boolean value) {
        this.isRegulateurTemperature = value;
    }

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
     * Gets the value of the idFlAval property.
     * 
     */
    public int getIdFlAval() {
        return idFlAval;
    }

    /**
     * Sets the value of the idFlAval property.
     * 
     */
    public void setIdFlAval(int value) {
        this.idFlAval = value;
    }

    /**
     * Gets the value of the idFouGen property.
     * 
     */
    public int getIdFouGen() {
        return idFouGen;
    }

    /**
     * Sets the value of the idFouGen property.
     * 
     */
    public void setIdFouGen(int value) {
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
     *         &lt;any processContents='skip' maxOccurs="unbounded" minOccurs="0"/>
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
