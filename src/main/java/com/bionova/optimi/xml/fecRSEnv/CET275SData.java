
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5 ECOScience_CET275S - Pouget
 * 
 * <p>Java class for CET275S_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CET275S_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id_Fl_Amont" type="{}RT_Id_Fl_Amont" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Fl_Aval" type="{}E_Id_Fluide" minOccurs="0"/>
 *         &lt;element name="Id_Fou_Gen" type="{}RT_Types_Fonctionnements_Generateur" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ind_horloge" type="{}E_Ind_horloge"/>
 *         &lt;element name="Certif_COP_Pivot" type="{}E_Certif_COP_Pivot"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CET275S_Data", propOrder = {

})
public class CET275SData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Id_Fl_Amont")
    protected String idFlAmont;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Id_Fl_Aval")
    protected String idFlAval;
    @XmlElement(name = "Id_Fou_Gen")
    protected String idFouGen;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "Ind_horloge", required = true)
    protected String indHorloge;
    @XmlElement(name = "Certif_COP_Pivot", required = true)
    protected String certifCOPPivot;

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
     * Gets the value of the idFlAmont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFlAmont() {
        return idFlAmont;
    }

    /**
     * Sets the value of the idFlAmont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFlAmont(String value) {
        this.idFlAmont = value;
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
     * Gets the value of the indHorloge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndHorloge() {
        return indHorloge;
    }

    /**
     * Sets the value of the indHorloge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndHorloge(String value) {
        this.indHorloge = value;
    }

    /**
     * Gets the value of the certifCOPPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertifCOPPivot() {
        return certifCOPPivot;
    }

    /**
     * Sets the value of the certifCOPPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertifCOPPivot(String value) {
        this.certifCOPPivot = value;
    }

}
