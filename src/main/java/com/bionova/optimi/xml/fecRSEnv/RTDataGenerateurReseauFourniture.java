
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Reseau_Fourniture complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Reseau_Fourniture">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Reseau" type="{}E_Reseau_Urbain"/>
 *         &lt;element name="Id_Fou_Gen" type="{}E_Id_FouGen_Mod3"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="P_Ess" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Reseau_Chaleur" type="{}E_Reseau_Chaleur"/>
 *         &lt;element name="Isolation_Du_Reseau" type="{}E_Isolation_Reseau_Urbain"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Reseau_Fourniture", propOrder = {

})
public class RTDataGenerateurReseauFourniture {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Reseau", required = true)
    protected String reseau;
    @XmlElement(name = "Id_Fou_Gen", required = true)
    protected String idFouGen;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "P_Ess")
    protected double pEss;
    @XmlElement(name = "Reseau_Chaleur", required = true)
    protected String reseauChaleur;
    @XmlElement(name = "Isolation_Du_Reseau", required = true)
    protected String isolationDuReseau;
    @XmlElement(name = "Description")
    protected String description;

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
     * Gets the value of the reseau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReseau() {
        return reseau;
    }

    /**
     * Sets the value of the reseau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReseau(String value) {
        this.reseau = value;
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
     * Gets the value of the idprioriteCh property.
     * 
     */
    public int getIdprioriteCh() {
        return idprioriteCh;
    }

    /**
     * Sets the value of the idprioriteCh property.
     * 
     */
    public void setIdprioriteCh(int value) {
        this.idprioriteCh = value;
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
     * Gets the value of the pEss property.
     * 
     */
    public double getPEss() {
        return pEss;
    }

    /**
     * Sets the value of the pEss property.
     * 
     */
    public void setPEss(double value) {
        this.pEss = value;
    }

    /**
     * Gets the value of the reseauChaleur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReseauChaleur() {
        return reseauChaleur;
    }

    /**
     * Sets the value of the reseauChaleur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReseauChaleur(String value) {
        this.reseauChaleur = value;
    }

    /**
     * Gets the value of the isolationDuReseau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsolationDuReseau() {
        return isolationDuReseau;
    }

    /**
     * Sets the value of the isolationDuReseau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsolationDuReseau(String value) {
        this.isolationDuReseau = value;
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

}
