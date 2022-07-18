
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Generateur_Boucle_Solaire complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Generateur_Boucle_Solaire">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="A" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Alpha" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Beta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Mode_Regul_BS" type="{}E_Mode_Regulation_Boucle_Solaire"/>
 *         &lt;element name="Valeur_Certifiee_Defaut_Boucle_Solaire" type="{}E_Valeur_Certifiee_Defaut_Boucle_Solaire"/>
 *         &lt;element name="Type_Capteur_Boucle_Solaire" type="{}E_Type_Capteur_Boucle_Solaire"/>
 *         &lt;element name="Eta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="a1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="a2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_te" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_ti" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Is_avec_echangeur" type="{}E_Presence_Echangeur"/>
 *         &lt;element name="K_theta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_np" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Masque_Collection" type="{}ArrayOfChoice7" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Generateur_Boucle_Solaire", propOrder = {

})
@XmlSeeAlso({
    RTDataBoucleSolaireProdCAD.class
})
public class RTDataGenerateurBoucleSolaire {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "A")
    protected double a;
    @XmlElement(name = "Alpha")
    protected double alpha;
    @XmlElement(name = "Beta")
    protected double beta;
    @XmlElement(name = "Mode_Regul_BS", required = true)
    protected String modeRegulBS;
    @XmlElement(name = "Valeur_Certifiee_Defaut_Boucle_Solaire", required = true)
    protected String valeurCertifieeDefautBoucleSolaire;
    @XmlElement(name = "Type_Capteur_Boucle_Solaire", required = true)
    protected String typeCapteurBoucleSolaire;
    @XmlElement(name = "Eta")
    protected double eta;
    protected double a1;
    protected double a2;
    @XmlElement(name = "UA_te")
    protected double uaTe;
    @XmlElement(name = "UA_ti")
    protected double uaTi;
    @XmlElement(name = "Is_avec_echangeur", required = true)
    protected String isAvecEchangeur;
    @XmlElement(name = "K_theta")
    protected double kTheta;
    @XmlElement(name = "P_np")
    protected double pNp;
    @XmlElement(name = "Masque_Collection")
    protected ArrayOfChoice7 masqueCollection;

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
     * Gets the value of the a property.
     * 
     */
    public double getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     */
    public void setA(double value) {
        this.a = value;
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
     * Gets the value of the modeRegulBS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeRegulBS() {
        return modeRegulBS;
    }

    /**
     * Sets the value of the modeRegulBS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeRegulBS(String value) {
        this.modeRegulBS = value;
    }

    /**
     * Gets the value of the valeurCertifieeDefautBoucleSolaire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurCertifieeDefautBoucleSolaire() {
        return valeurCertifieeDefautBoucleSolaire;
    }

    /**
     * Sets the value of the valeurCertifieeDefautBoucleSolaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurCertifieeDefautBoucleSolaire(String value) {
        this.valeurCertifieeDefautBoucleSolaire = value;
    }

    /**
     * Gets the value of the typeCapteurBoucleSolaire property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeCapteurBoucleSolaire() {
        return typeCapteurBoucleSolaire;
    }

    /**
     * Sets the value of the typeCapteurBoucleSolaire property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeCapteurBoucleSolaire(String value) {
        this.typeCapteurBoucleSolaire = value;
    }

    /**
     * Gets the value of the eta property.
     * 
     */
    public double getEta() {
        return eta;
    }

    /**
     * Sets the value of the eta property.
     * 
     */
    public void setEta(double value) {
        this.eta = value;
    }

    /**
     * Gets the value of the a1 property.
     * 
     */
    public double getA1() {
        return a1;
    }

    /**
     * Sets the value of the a1 property.
     * 
     */
    public void setA1(double value) {
        this.a1 = value;
    }

    /**
     * Gets the value of the a2 property.
     * 
     */
    public double getA2() {
        return a2;
    }

    /**
     * Sets the value of the a2 property.
     * 
     */
    public void setA2(double value) {
        this.a2 = value;
    }

    /**
     * Gets the value of the uaTe property.
     * 
     */
    public double getUATe() {
        return uaTe;
    }

    /**
     * Sets the value of the uaTe property.
     * 
     */
    public void setUATe(double value) {
        this.uaTe = value;
    }

    /**
     * Gets the value of the uaTi property.
     * 
     */
    public double getUATi() {
        return uaTi;
    }

    /**
     * Sets the value of the uaTi property.
     * 
     */
    public void setUATi(double value) {
        this.uaTi = value;
    }

    /**
     * Gets the value of the isAvecEchangeur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAvecEchangeur() {
        return isAvecEchangeur;
    }

    /**
     * Sets the value of the isAvecEchangeur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAvecEchangeur(String value) {
        this.isAvecEchangeur = value;
    }

    /**
     * Gets the value of the kTheta property.
     * 
     */
    public double getKTheta() {
        return kTheta;
    }

    /**
     * Sets the value of the kTheta property.
     * 
     */
    public void setKTheta(double value) {
        this.kTheta = value;
    }

    /**
     * Gets the value of the pNp property.
     * 
     */
    public double getPNp() {
        return pNp;
    }

    /**
     * Sets the value of the pNp property.
     * 
     */
    public void setPNp(double value) {
        this.pNp = value;
    }

    /**
     * Gets the value of the masqueCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChoice7 }
     *     
     */
    public ArrayOfChoice7 getMasqueCollection() {
        return masqueCollection;
    }

    /**
     * Sets the value of the masqueCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChoice7 }
     *     
     */
    public void setMasqueCollection(ArrayOfChoice7 value) {
        this.masqueCollection = value;
    }

}
