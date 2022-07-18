
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * v8000
 * 
 * <p>Java class for RT_Data_Parking complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Parking">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Net" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Npl" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Type_Parking" type="{}E_Type_Parking"/>
 *         &lt;element name="IsParamEclJoDefaut" type="{}RT_Oui_Non"/>
 *         &lt;element name="NbjO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsParamEclHoDefaut" type="{}RT_Oui_Non"/>
 *         &lt;element name="Type_PlagOse" type="{}E_Type_Plage_Horaire"/>
 *         &lt;element name="PlagOse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_PlagOwe" type="{}E_Type_Plage_Horaire"/>
 *         &lt;element name="PlagOwe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsParamEclHdDefaut" type="{}RT_Oui_Non"/>
 *         &lt;element name="Type_PlagDse" type="{}E_Type_Plage_Horaire"/>
 *         &lt;element name="PlagDse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_PlagDwe" type="{}E_Type_Plage_Horaire"/>
 *         &lt;element name="PlagDwe" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ex" type="{}RT_Oui_Non"/>
 *         &lt;element name="IsParamEclPuisDefaut" type="{}RT_Oui_Non"/>
 *         &lt;element name="Pec_ins" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Vent" type="{}RT_Oui_Non"/>
 *         &lt;element name="Type_Usage" type="{}E_Typologie_Parking"/>
 *         &lt;element name="IsParamVentilationDefaut" type="{}RT_Oui_Non"/>
 *         &lt;element name="Dvent1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Dvent2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="IsParamVentilationHabDefaut" type="{}RT_Oui_Non"/>
 *         &lt;element name="Pvent600" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reg" type="{}RT_Oui_Non"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Parking", propOrder = {

})
public class RTDataParking {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Net")
    protected int net;
    @XmlElement(name = "Npl")
    protected int npl;
    @XmlElement(name = "Type_Parking", required = true)
    protected String typeParking;
    @XmlElement(name = "IsParamEclJoDefaut", required = true)
    protected String isParamEclJoDefaut;
    @XmlElement(name = "NbjO")
    protected String nbjO;
    @XmlElement(name = "IsParamEclHoDefaut", required = true)
    protected String isParamEclHoDefaut;
    @XmlElement(name = "Type_PlagOse", required = true)
    protected String typePlagOse;
    @XmlElement(name = "PlagOse")
    protected String plagOse;
    @XmlElement(name = "Type_PlagOwe", required = true)
    protected String typePlagOwe;
    @XmlElement(name = "PlagOwe")
    protected String plagOwe;
    @XmlElement(name = "IsParamEclHdDefaut", required = true)
    protected String isParamEclHdDefaut;
    @XmlElement(name = "Type_PlagDse", required = true)
    protected String typePlagDse;
    @XmlElement(name = "PlagDse")
    protected String plagDse;
    @XmlElement(name = "Type_PlagDwe", required = true)
    protected String typePlagDwe;
    @XmlElement(name = "PlagDwe")
    protected String plagDwe;
    @XmlElement(name = "Ex", required = true)
    protected String ex;
    @XmlElement(name = "IsParamEclPuisDefaut", required = true)
    protected String isParamEclPuisDefaut;
    @XmlElement(name = "Pec_ins")
    protected double pecIns;
    @XmlElement(name = "Vent", required = true)
    protected String vent;
    @XmlElement(name = "Type_Usage", required = true)
    protected String typeUsage;
    @XmlElement(name = "IsParamVentilationDefaut", required = true)
    protected String isParamVentilationDefaut;
    @XmlElement(name = "Dvent1")
    protected double dvent1;
    @XmlElement(name = "Dvent2")
    protected double dvent2;
    @XmlElement(name = "Pvent1")
    protected double pvent1;
    @XmlElement(name = "Pvent2")
    protected double pvent2;
    @XmlElement(name = "IsParamVentilationHabDefaut", required = true)
    protected String isParamVentilationHabDefaut;
    @XmlElement(name = "Pvent600")
    protected double pvent600;
    @XmlElement(name = "Reg", required = true)
    protected String reg;

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
     * Gets the value of the net property.
     * 
     */
    public int getNet() {
        return net;
    }

    /**
     * Sets the value of the net property.
     * 
     */
    public void setNet(int value) {
        this.net = value;
    }

    /**
     * Gets the value of the npl property.
     * 
     */
    public int getNpl() {
        return npl;
    }

    /**
     * Sets the value of the npl property.
     * 
     */
    public void setNpl(int value) {
        this.npl = value;
    }

    /**
     * Gets the value of the typeParking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeParking() {
        return typeParking;
    }

    /**
     * Sets the value of the typeParking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeParking(String value) {
        this.typeParking = value;
    }

    /**
     * Gets the value of the isParamEclJoDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsParamEclJoDefaut() {
        return isParamEclJoDefaut;
    }

    /**
     * Sets the value of the isParamEclJoDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsParamEclJoDefaut(String value) {
        this.isParamEclJoDefaut = value;
    }

    /**
     * Gets the value of the nbjO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbjO() {
        return nbjO;
    }

    /**
     * Sets the value of the nbjO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbjO(String value) {
        this.nbjO = value;
    }

    /**
     * Gets the value of the isParamEclHoDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsParamEclHoDefaut() {
        return isParamEclHoDefaut;
    }

    /**
     * Sets the value of the isParamEclHoDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsParamEclHoDefaut(String value) {
        this.isParamEclHoDefaut = value;
    }

    /**
     * Gets the value of the typePlagOse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePlagOse() {
        return typePlagOse;
    }

    /**
     * Sets the value of the typePlagOse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePlagOse(String value) {
        this.typePlagOse = value;
    }

    /**
     * Gets the value of the plagOse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlagOse() {
        return plagOse;
    }

    /**
     * Sets the value of the plagOse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlagOse(String value) {
        this.plagOse = value;
    }

    /**
     * Gets the value of the typePlagOwe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePlagOwe() {
        return typePlagOwe;
    }

    /**
     * Sets the value of the typePlagOwe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePlagOwe(String value) {
        this.typePlagOwe = value;
    }

    /**
     * Gets the value of the plagOwe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlagOwe() {
        return plagOwe;
    }

    /**
     * Sets the value of the plagOwe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlagOwe(String value) {
        this.plagOwe = value;
    }

    /**
     * Gets the value of the isParamEclHdDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsParamEclHdDefaut() {
        return isParamEclHdDefaut;
    }

    /**
     * Sets the value of the isParamEclHdDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsParamEclHdDefaut(String value) {
        this.isParamEclHdDefaut = value;
    }

    /**
     * Gets the value of the typePlagDse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePlagDse() {
        return typePlagDse;
    }

    /**
     * Sets the value of the typePlagDse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePlagDse(String value) {
        this.typePlagDse = value;
    }

    /**
     * Gets the value of the plagDse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlagDse() {
        return plagDse;
    }

    /**
     * Sets the value of the plagDse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlagDse(String value) {
        this.plagDse = value;
    }

    /**
     * Gets the value of the typePlagDwe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePlagDwe() {
        return typePlagDwe;
    }

    /**
     * Sets the value of the typePlagDwe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePlagDwe(String value) {
        this.typePlagDwe = value;
    }

    /**
     * Gets the value of the plagDwe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlagDwe() {
        return plagDwe;
    }

    /**
     * Sets the value of the plagDwe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlagDwe(String value) {
        this.plagDwe = value;
    }

    /**
     * Gets the value of the ex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEx() {
        return ex;
    }

    /**
     * Sets the value of the ex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEx(String value) {
        this.ex = value;
    }

    /**
     * Gets the value of the isParamEclPuisDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsParamEclPuisDefaut() {
        return isParamEclPuisDefaut;
    }

    /**
     * Sets the value of the isParamEclPuisDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsParamEclPuisDefaut(String value) {
        this.isParamEclPuisDefaut = value;
    }

    /**
     * Gets the value of the pecIns property.
     * 
     */
    public double getPecIns() {
        return pecIns;
    }

    /**
     * Sets the value of the pecIns property.
     * 
     */
    public void setPecIns(double value) {
        this.pecIns = value;
    }

    /**
     * Gets the value of the vent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVent() {
        return vent;
    }

    /**
     * Sets the value of the vent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVent(String value) {
        this.vent = value;
    }

    /**
     * Gets the value of the typeUsage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeUsage() {
        return typeUsage;
    }

    /**
     * Sets the value of the typeUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeUsage(String value) {
        this.typeUsage = value;
    }

    /**
     * Gets the value of the isParamVentilationDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsParamVentilationDefaut() {
        return isParamVentilationDefaut;
    }

    /**
     * Sets the value of the isParamVentilationDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsParamVentilationDefaut(String value) {
        this.isParamVentilationDefaut = value;
    }

    /**
     * Gets the value of the dvent1 property.
     * 
     */
    public double getDvent1() {
        return dvent1;
    }

    /**
     * Sets the value of the dvent1 property.
     * 
     */
    public void setDvent1(double value) {
        this.dvent1 = value;
    }

    /**
     * Gets the value of the dvent2 property.
     * 
     */
    public double getDvent2() {
        return dvent2;
    }

    /**
     * Sets the value of the dvent2 property.
     * 
     */
    public void setDvent2(double value) {
        this.dvent2 = value;
    }

    /**
     * Gets the value of the pvent1 property.
     * 
     */
    public double getPvent1() {
        return pvent1;
    }

    /**
     * Sets the value of the pvent1 property.
     * 
     */
    public void setPvent1(double value) {
        this.pvent1 = value;
    }

    /**
     * Gets the value of the pvent2 property.
     * 
     */
    public double getPvent2() {
        return pvent2;
    }

    /**
     * Sets the value of the pvent2 property.
     * 
     */
    public void setPvent2(double value) {
        this.pvent2 = value;
    }

    /**
     * Gets the value of the isParamVentilationHabDefaut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsParamVentilationHabDefaut() {
        return isParamVentilationHabDefaut;
    }

    /**
     * Sets the value of the isParamVentilationHabDefaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsParamVentilationHabDefaut(String value) {
        this.isParamVentilationHabDefaut = value;
    }

    /**
     * Gets the value of the pvent600 property.
     * 
     */
    public double getPvent600() {
        return pvent600;
    }

    /**
     * Sets the value of the pvent600 property.
     * 
     */
    public void setPvent600(double value) {
        this.pvent600 = value;
    }

    /**
     * Gets the value of the reg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReg() {
        return reg;
    }

    /**
     * Sets the value of the reg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReg(String value) {
        this.reg = value;
    }

}
