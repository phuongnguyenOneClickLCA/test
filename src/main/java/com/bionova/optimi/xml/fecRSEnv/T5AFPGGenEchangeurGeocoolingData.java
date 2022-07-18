
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for T5_AFPG_Gen_Echangeur_Geocooling_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_AFPG_Gen_Echangeur_Geocooling_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}Data_Object_Base">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Type_Association" type="{}E_Type_Association"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Delta_Theta_aval" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qv_nom_aval" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_r_source_lim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Delta_Theta_amont" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_geo_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="qv_nom_amont" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcprim_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Temp_Aval" type="{}ArrayOfDouble" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_AFPG_Gen_Echangeur_Geocooling_Data", propOrder = {
    "index",
    "name",
    "description",
    "rdim",
    "typeAssociation",
    "idprioriteFr",
    "idSourceAmont",
    "deltaThetaAval",
    "qvNomAval",
    "thetaRSourceLim",
    "deltaThetaAmont",
    "uaGeoNom",
    "qvNomAmont",
    "pcprimNom",
    "valTempAval"
})
public class T5AFPGGenEchangeurGeocoolingData
    extends DataObjectBase
{

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Type_Association", required = true)
    protected String typeAssociation;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Delta_Theta_aval")
    protected double deltaThetaAval;
    @XmlElement(name = "qv_nom_aval")
    protected double qvNomAval;
    @XmlElement(name = "Theta_r_source_lim")
    protected double thetaRSourceLim;
    @XmlElement(name = "Delta_Theta_amont")
    protected double deltaThetaAmont;
    @XmlElement(name = "UA_geo_nom")
    protected double uaGeoNom;
    @XmlElement(name = "qv_nom_amont")
    protected double qvNomAmont;
    @XmlElement(name = "Pcprim_nom")
    protected double pcprimNom;
    @XmlElement(name = "Val_Temp_Aval")
    protected ArrayOfDouble valTempAval;

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
     * Gets the value of the typeAssociation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeAssociation() {
        return typeAssociation;
    }

    /**
     * Sets the value of the typeAssociation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeAssociation(String value) {
        this.typeAssociation = value;
    }

    /**
     * Gets the value of the idprioriteFr property.
     * 
     */
    public int getIdprioriteFr() {
        return idprioriteFr;
    }

    /**
     * Sets the value of the idprioriteFr property.
     * 
     */
    public void setIdprioriteFr(int value) {
        this.idprioriteFr = value;
    }

    /**
     * Gets the value of the idSourceAmont property.
     * 
     */
    public int getIdSourceAmont() {
        return idSourceAmont;
    }

    /**
     * Sets the value of the idSourceAmont property.
     * 
     */
    public void setIdSourceAmont(int value) {
        this.idSourceAmont = value;
    }

    /**
     * Gets the value of the deltaThetaAval property.
     * 
     */
    public double getDeltaThetaAval() {
        return deltaThetaAval;
    }

    /**
     * Sets the value of the deltaThetaAval property.
     * 
     */
    public void setDeltaThetaAval(double value) {
        this.deltaThetaAval = value;
    }

    /**
     * Gets the value of the qvNomAval property.
     * 
     */
    public double getQvNomAval() {
        return qvNomAval;
    }

    /**
     * Sets the value of the qvNomAval property.
     * 
     */
    public void setQvNomAval(double value) {
        this.qvNomAval = value;
    }

    /**
     * Gets the value of the thetaRSourceLim property.
     * 
     */
    public double getThetaRSourceLim() {
        return thetaRSourceLim;
    }

    /**
     * Sets the value of the thetaRSourceLim property.
     * 
     */
    public void setThetaRSourceLim(double value) {
        this.thetaRSourceLim = value;
    }

    /**
     * Gets the value of the deltaThetaAmont property.
     * 
     */
    public double getDeltaThetaAmont() {
        return deltaThetaAmont;
    }

    /**
     * Sets the value of the deltaThetaAmont property.
     * 
     */
    public void setDeltaThetaAmont(double value) {
        this.deltaThetaAmont = value;
    }

    /**
     * Gets the value of the uaGeoNom property.
     * 
     */
    public double getUAGeoNom() {
        return uaGeoNom;
    }

    /**
     * Sets the value of the uaGeoNom property.
     * 
     */
    public void setUAGeoNom(double value) {
        this.uaGeoNom = value;
    }

    /**
     * Gets the value of the qvNomAmont property.
     * 
     */
    public double getQvNomAmont() {
        return qvNomAmont;
    }

    /**
     * Sets the value of the qvNomAmont property.
     * 
     */
    public void setQvNomAmont(double value) {
        this.qvNomAmont = value;
    }

    /**
     * Gets the value of the pcprimNom property.
     * 
     */
    public double getPcprimNom() {
        return pcprimNom;
    }

    /**
     * Sets the value of the pcprimNom property.
     * 
     */
    public void setPcprimNom(double value) {
        this.pcprimNom = value;
    }

    /**
     * Gets the value of the valTempAval property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getValTempAval() {
        return valTempAval;
    }

    /**
     * Sets the value of the valTempAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setValTempAval(ArrayOfDouble value) {
        this.valTempAval = value;
    }

}
