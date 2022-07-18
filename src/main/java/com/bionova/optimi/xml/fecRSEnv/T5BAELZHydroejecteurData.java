
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_BAELZ_hydroejecteur - 8100
 * 
 * <p>Java class for T5_BAELZ_hydroejecteur_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_BAELZ_hydroejecteur_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Lvc_Prim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lhvc_Prim" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pcirc_Prim_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Vc_Prim_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Umoyen_Hvc_Prim_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Gest_Circ_Prim_Ch" type="{}E_Gest_Circ"/>
 *         &lt;element name="Hydroejecteur" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_BAELZ_hydroejecteur_Data", propOrder = {

})
public class T5BAELZHydroejecteurData {

    @XmlElement(name = "Id_Gen")
    protected int idGen;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Lvc_Prim")
    protected double lvcPrim;
    @XmlElement(name = "Lhvc_Prim")
    protected double lhvcPrim;
    @XmlElement(name = "Pcirc_Prim_Ch")
    protected double pcircPrimCh;
    @XmlElement(name = "Umoyen_Vc_Prim_Ch")
    protected double umoyenVcPrimCh;
    @XmlElement(name = "Umoyen_Hvc_Prim_Ch")
    protected double umoyenHvcPrimCh;
    @XmlElement(name = "Gest_Circ_Prim_Ch", required = true)
    protected String gestCircPrimCh;
    @XmlElement(name = "Hydroejecteur")
    protected String hydroejecteur;

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
     * Gets the value of the pcircPrimCh property.
     * 
     */
    public double getPcircPrimCh() {
        return pcircPrimCh;
    }

    /**
     * Sets the value of the pcircPrimCh property.
     * 
     */
    public void setPcircPrimCh(double value) {
        this.pcircPrimCh = value;
    }

    /**
     * Gets the value of the umoyenVcPrimCh property.
     * 
     */
    public double getUmoyenVcPrimCh() {
        return umoyenVcPrimCh;
    }

    /**
     * Sets the value of the umoyenVcPrimCh property.
     * 
     */
    public void setUmoyenVcPrimCh(double value) {
        this.umoyenVcPrimCh = value;
    }

    /**
     * Gets the value of the umoyenHvcPrimCh property.
     * 
     */
    public double getUmoyenHvcPrimCh() {
        return umoyenHvcPrimCh;
    }

    /**
     * Sets the value of the umoyenHvcPrimCh property.
     * 
     */
    public void setUmoyenHvcPrimCh(double value) {
        this.umoyenHvcPrimCh = value;
    }

    /**
     * Gets the value of the gestCircPrimCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGestCircPrimCh() {
        return gestCircPrimCh;
    }

    /**
     * Sets the value of the gestCircPrimCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGestCircPrimCh(String value) {
        this.gestCircPrimCh = value;
    }

    /**
     * Gets the value of the hydroejecteur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHydroejecteur() {
        return hydroejecteur;
    }

    /**
     * Sets the value of the hydroejecteur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHydroejecteur(String value) {
        this.hydroejecteur = value;
    }

}
