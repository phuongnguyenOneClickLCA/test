
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_Intergroupe_ECS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_Intergroupe_ECS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Reseau_Intergroupe_ECS" type="{}E_Type_Reseau_ECS"/>
 *         &lt;element name="l_vc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="l_hvc_prim_bcl_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="l_vc_prim_trac_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="l_hvc_prim_trac_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="u_prim_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_Rechauf_Bcl_e" type="{}RT_Oui_Non"/>
 *         &lt;element name="type_gest_circ_e" type="{}E_Type_Gestion_Circulateurs"/>
 *         &lt;element name="p_circ_prim_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_PCAD" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "RT_Data_Distribution_Intergroupe_ECS", propOrder = {

})
public class RTDataDistributionIntergroupeECS {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_Reseau_Intergroupe_ECS", required = true)
    protected String typeReseauIntergroupeECS;
    @XmlElement(name = "l_vc_prim_bcl_e")
    protected double lVcPrimBclE;
    @XmlElement(name = "l_hvc_prim_bcl_e")
    protected double lHvcPrimBclE;
    @XmlElement(name = "l_vc_prim_trac_e")
    protected double lVcPrimTracE;
    @XmlElement(name = "l_hvc_prim_trac_e")
    protected double lHvcPrimTracE;
    @XmlElement(name = "u_prim_e")
    protected double uPrimE;
    @XmlElement(name = "Is_Rechauf_Bcl_e", required = true)
    protected String isRechaufBclE;
    @XmlElement(name = "type_gest_circ_e", required = true)
    protected String typeGestCircE;
    @XmlElement(name = "p_circ_prim_e")
    protected double pCircPrimE;
    @XmlElement(name = "Id_Gen")
    protected int idGen;
    @XmlElement(name = "Id_PCAD")
    protected int idPCAD;
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
     * Gets the value of the typeReseauIntergroupeECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeReseauIntergroupeECS() {
        return typeReseauIntergroupeECS;
    }

    /**
     * Sets the value of the typeReseauIntergroupeECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeReseauIntergroupeECS(String value) {
        this.typeReseauIntergroupeECS = value;
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
     * Gets the value of the lVcPrimTracE property.
     * 
     */
    public double getLVcPrimTracE() {
        return lVcPrimTracE;
    }

    /**
     * Sets the value of the lVcPrimTracE property.
     * 
     */
    public void setLVcPrimTracE(double value) {
        this.lVcPrimTracE = value;
    }

    /**
     * Gets the value of the lHvcPrimTracE property.
     * 
     */
    public double getLHvcPrimTracE() {
        return lHvcPrimTracE;
    }

    /**
     * Sets the value of the lHvcPrimTracE property.
     * 
     */
    public void setLHvcPrimTracE(double value) {
        this.lHvcPrimTracE = value;
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
     * Gets the value of the isRechaufBclE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRechaufBclE() {
        return isRechaufBclE;
    }

    /**
     * Sets the value of the isRechaufBclE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRechaufBclE(String value) {
        this.isRechaufBclE = value;
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
     * Gets the value of the idPCAD property.
     * 
     */
    public int getIdPCAD() {
        return idPCAD;
    }

    /**
     * Sets the value of the idPCAD property.
     * 
     */
    public void setIdPCAD(int value) {
        this.idPCAD = value;
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
