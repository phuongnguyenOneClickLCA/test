
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Distribution_Intergroupe_Froid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Distribution_Intergroupe_Froid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Prim" type="{}E_Type_Prim"/>
 *         &lt;element name="Lvc_Prim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lhvc_Prim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Umoyen_Vc_Prim_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Hvc_Prim_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_Circ_Prim_Fr" type="{}E_Gest_Circ_2nd"/>
 *         &lt;element name="Pcirc_Prim_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "RT_Data_Distribution_Intergroupe_Froid", propOrder = {

})
public class RTDataDistributionIntergroupeFroid {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type_Prim", required = true)
    protected String typePrim;
    @XmlElement(name = "Lvc_Prim")
    protected double lvcPrim;
    @XmlElement(name = "Lhvc_Prim")
    protected double lhvcPrim;
    @XmlElement(name = "Id_Gen")
    protected int idGen;
    @XmlElement(name = "Umoyen_Vc_Prim_Fr")
    protected double umoyenVcPrimFr;
    @XmlElement(name = "Umoyen_Hvc_Prim_Fr")
    protected double umoyenHvcPrimFr;
    @XmlElement(name = "Gest_Circ_Prim_Fr", required = true)
    protected String gestCircPrimFr;
    @XmlElement(name = "Pcirc_Prim_Fr")
    protected double pcircPrimFr;
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
     * Gets the value of the typePrim property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePrim() {
        return typePrim;
    }

    /**
     * Sets the value of the typePrim property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePrim(String value) {
        this.typePrim = value;
    }

    /**
     * Gets the value of the lvcPrim property.
     * 
     */
    public double getLvcPrim() {
        return lvcPrim;
    }

    /**
     * Sets the value of the lvcPrim property.
     * 
     */
    public void setLvcPrim(double value) {
        this.lvcPrim = value;
    }

    /**
     * Gets the value of the lhvcPrim property.
     * 
     */
    public double getLhvcPrim() {
        return lhvcPrim;
    }

    /**
     * Sets the value of the lhvcPrim property.
     * 
     */
    public void setLhvcPrim(double value) {
        this.lhvcPrim = value;
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
     * Gets the value of the umoyenVcPrimFr property.
     * 
     */
    public double getUmoyenVcPrimFr() {
        return umoyenVcPrimFr;
    }

    /**
     * Sets the value of the umoyenVcPrimFr property.
     * 
     */
    public void setUmoyenVcPrimFr(double value) {
        this.umoyenVcPrimFr = value;
    }

    /**
     * Gets the value of the umoyenHvcPrimFr property.
     * 
     */
    public double getUmoyenHvcPrimFr() {
        return umoyenHvcPrimFr;
    }

    /**
     * Sets the value of the umoyenHvcPrimFr property.
     * 
     */
    public void setUmoyenHvcPrimFr(double value) {
        this.umoyenHvcPrimFr = value;
    }

    /**
     * Gets the value of the gestCircPrimFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGestCircPrimFr() {
        return gestCircPrimFr;
    }

    /**
     * Sets the value of the gestCircPrimFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGestCircPrimFr(String value) {
        this.gestCircPrimFr = value;
    }

    /**
     * Gets the value of the pcircPrimFr property.
     * 
     */
    public double getPcircPrimFr() {
        return pcircPrimFr;
    }

    /**
     * Sets the value of the pcircPrimFr property.
     * 
     */
    public void setPcircPrimFr(double value) {
        this.pcircPrimFr = value;
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
