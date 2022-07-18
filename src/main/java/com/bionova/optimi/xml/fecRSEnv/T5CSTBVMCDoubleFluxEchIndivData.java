
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_CSTB_VMC_Double_Flux_Ech_Indiv
 * 
 * <p>Java class for T5_CSTB_VMC_Double_Flux_Ech_Indiv_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_CSTB_VMC_Double_Flux_Ech_Indiv_Data">
 *   &lt;complexContent>
 *     &lt;extension base="{}Data_Object_Base">
 *       &lt;all>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_base_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_base_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_pointe_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Certificat_Efficacite_Echangeur" type="{}RT_Certificat_Efficacite_Echangeur"/>
 *         &lt;element name="Epsilon_eq" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_ByPass" type="{}RT_Oui_Non"/>
 *         &lt;element name="Is_Antigel" type="{}RT_Oui_Non"/>
 *         &lt;element name="T_ext_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_hiver" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_ext_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_int_bp_ete" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_rep_int" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_souf_int" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Phi_conduit_coll_vert" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="T_sec_h_rep_LIM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Et" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Param_Rafnoc_MiSaison" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Param_Rafnoc_Ete" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pvent_rafnoc_rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pvent_rafnoc_souf" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_CSTB_VMC_Double_Flux_Ech_Indiv_Data", propOrder = {
    "name",
    "index",
    "description",
    "pventBaseSouf",
    "pventPointeSouf",
    "pventBaseRep",
    "pventPointeRep",
    "certificatEfficaciteEchangeur",
    "epsilonEq",
    "isByPass",
    "isAntigel",
    "tExtBpHiver",
    "tIntBpHiver",
    "tExtBpEte",
    "tIntBpEte",
    "b",
    "rRepInt",
    "rSoufInt",
    "phiConduitCollVert",
    "tSecHRepLIM",
    "idEt",
    "paramRafnocMiSaison",
    "paramRafnocEte",
    "pventRafnocRep",
    "pventRafnocSouf"
})
public class T5CSTBVMCDoubleFluxEchIndivData
    extends DataObjectBase
{

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Pvent_base_souf")
    protected double pventBaseSouf;
    @XmlElement(name = "Pvent_pointe_souf")
    protected double pventPointeSouf;
    @XmlElement(name = "Pvent_base_rep")
    protected double pventBaseRep;
    @XmlElement(name = "Pvent_pointe_rep")
    protected double pventPointeRep;
    @XmlElement(name = "Certificat_Efficacite_Echangeur", required = true)
    protected String certificatEfficaciteEchangeur;
    @XmlElement(name = "Epsilon_eq")
    protected double epsilonEq;
    @XmlElement(name = "Is_ByPass", required = true)
    protected String isByPass;
    @XmlElement(name = "Is_Antigel", required = true)
    protected String isAntigel;
    @XmlElement(name = "T_ext_bp_hiver")
    protected double tExtBpHiver;
    @XmlElement(name = "T_int_bp_hiver")
    protected double tIntBpHiver;
    @XmlElement(name = "T_ext_bp_ete")
    protected double tExtBpEte;
    @XmlElement(name = "T_int_bp_ete")
    protected double tIntBpEte;
    protected double b;
    @XmlElement(name = "R_rep_int")
    protected double rRepInt;
    @XmlElement(name = "R_souf_int")
    protected double rSoufInt;
    @XmlElement(name = "Phi_conduit_coll_vert")
    protected double phiConduitCollVert;
    @XmlElement(name = "T_sec_h_rep_LIM")
    protected double tSecHRepLIM;
    @XmlElement(name = "Id_Et")
    protected int idEt;
    @XmlElement(name = "Param_Rafnoc_MiSaison")
    protected String paramRafnocMiSaison;
    @XmlElement(name = "Param_Rafnoc_Ete")
    protected String paramRafnocEte;
    @XmlElement(name = "Pvent_rafnoc_rep")
    protected double pventRafnocRep;
    @XmlElement(name = "Pvent_rafnoc_souf")
    protected double pventRafnocSouf;

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
     * Gets the value of the pventBaseSouf property.
     * 
     */
    public double getPventBaseSouf() {
        return pventBaseSouf;
    }

    /**
     * Sets the value of the pventBaseSouf property.
     * 
     */
    public void setPventBaseSouf(double value) {
        this.pventBaseSouf = value;
    }

    /**
     * Gets the value of the pventPointeSouf property.
     * 
     */
    public double getPventPointeSouf() {
        return pventPointeSouf;
    }

    /**
     * Sets the value of the pventPointeSouf property.
     * 
     */
    public void setPventPointeSouf(double value) {
        this.pventPointeSouf = value;
    }

    /**
     * Gets the value of the pventBaseRep property.
     * 
     */
    public double getPventBaseRep() {
        return pventBaseRep;
    }

    /**
     * Sets the value of the pventBaseRep property.
     * 
     */
    public void setPventBaseRep(double value) {
        this.pventBaseRep = value;
    }

    /**
     * Gets the value of the pventPointeRep property.
     * 
     */
    public double getPventPointeRep() {
        return pventPointeRep;
    }

    /**
     * Sets the value of the pventPointeRep property.
     * 
     */
    public void setPventPointeRep(double value) {
        this.pventPointeRep = value;
    }

    /**
     * Gets the value of the certificatEfficaciteEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificatEfficaciteEchangeur() {
        return certificatEfficaciteEchangeur;
    }

    /**
     * Sets the value of the certificatEfficaciteEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificatEfficaciteEchangeur(String value) {
        this.certificatEfficaciteEchangeur = value;
    }

    /**
     * Gets the value of the epsilonEq property.
     * 
     */
    public double getEpsilonEq() {
        return epsilonEq;
    }

    /**
     * Sets the value of the epsilonEq property.
     * 
     */
    public void setEpsilonEq(double value) {
        this.epsilonEq = value;
    }

    /**
     * Gets the value of the isByPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsByPass() {
        return isByPass;
    }

    /**
     * Sets the value of the isByPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsByPass(String value) {
        this.isByPass = value;
    }

    /**
     * Gets the value of the isAntigel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAntigel() {
        return isAntigel;
    }

    /**
     * Sets the value of the isAntigel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAntigel(String value) {
        this.isAntigel = value;
    }

    /**
     * Gets the value of the tExtBpHiver property.
     * 
     */
    public double getTExtBpHiver() {
        return tExtBpHiver;
    }

    /**
     * Sets the value of the tExtBpHiver property.
     * 
     */
    public void setTExtBpHiver(double value) {
        this.tExtBpHiver = value;
    }

    /**
     * Gets the value of the tIntBpHiver property.
     * 
     */
    public double getTIntBpHiver() {
        return tIntBpHiver;
    }

    /**
     * Sets the value of the tIntBpHiver property.
     * 
     */
    public void setTIntBpHiver(double value) {
        this.tIntBpHiver = value;
    }

    /**
     * Gets the value of the tExtBpEte property.
     * 
     */
    public double getTExtBpEte() {
        return tExtBpEte;
    }

    /**
     * Sets the value of the tExtBpEte property.
     * 
     */
    public void setTExtBpEte(double value) {
        this.tExtBpEte = value;
    }

    /**
     * Gets the value of the tIntBpEte property.
     * 
     */
    public double getTIntBpEte() {
        return tIntBpEte;
    }

    /**
     * Sets the value of the tIntBpEte property.
     * 
     */
    public void setTIntBpEte(double value) {
        this.tIntBpEte = value;
    }

    /**
     * Gets the value of the b property.
     * 
     */
    public double getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     */
    public void setB(double value) {
        this.b = value;
    }

    /**
     * Gets the value of the rRepInt property.
     * 
     */
    public double getRRepInt() {
        return rRepInt;
    }

    /**
     * Sets the value of the rRepInt property.
     * 
     */
    public void setRRepInt(double value) {
        this.rRepInt = value;
    }

    /**
     * Gets the value of the rSoufInt property.
     * 
     */
    public double getRSoufInt() {
        return rSoufInt;
    }

    /**
     * Sets the value of the rSoufInt property.
     * 
     */
    public void setRSoufInt(double value) {
        this.rSoufInt = value;
    }

    /**
     * Gets the value of the phiConduitCollVert property.
     * 
     */
    public double getPhiConduitCollVert() {
        return phiConduitCollVert;
    }

    /**
     * Sets the value of the phiConduitCollVert property.
     * 
     */
    public void setPhiConduitCollVert(double value) {
        this.phiConduitCollVert = value;
    }

    /**
     * Gets the value of the tSecHRepLIM property.
     * 
     */
    public double getTSecHRepLIM() {
        return tSecHRepLIM;
    }

    /**
     * Sets the value of the tSecHRepLIM property.
     * 
     */
    public void setTSecHRepLIM(double value) {
        this.tSecHRepLIM = value;
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

    /**
     * Gets the value of the paramRafnocMiSaison property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamRafnocMiSaison() {
        return paramRafnocMiSaison;
    }

    /**
     * Sets the value of the paramRafnocMiSaison property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamRafnocMiSaison(String value) {
        this.paramRafnocMiSaison = value;
    }

    /**
     * Gets the value of the paramRafnocEte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamRafnocEte() {
        return paramRafnocEte;
    }

    /**
     * Sets the value of the paramRafnocEte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamRafnocEte(String value) {
        this.paramRafnocEte = value;
    }

    /**
     * Gets the value of the pventRafnocRep property.
     * 
     */
    public double getPventRafnocRep() {
        return pventRafnocRep;
    }

    /**
     * Sets the value of the pventRafnocRep property.
     * 
     */
    public void setPventRafnocRep(double value) {
        this.pventRafnocRep = value;
    }

    /**
     * Gets the value of the pventRafnocSouf property.
     * 
     */
    public double getPventRafnocSouf() {
        return pventRafnocSouf;
    }

    /**
     * Sets the value of the pventRafnocSouf property.
     * 
     */
    public void setPventRafnocSouf(double value) {
        this.pventRafnocSouf = value;
    }

}
