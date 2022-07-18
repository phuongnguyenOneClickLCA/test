
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Statut_Donnee_PC_Ch" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="GUE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_uext_Ch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_GUE_Pivot" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_GUE_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_pivot_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Paux_uext_Pivot_Ch" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_uext_Pivot_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_uint_Ch" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Paux_uint_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonct_Moteur_Ch" type="{}E_Fonctionnement_Moteur"/>
 *         &lt;element name="Statut_CP_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux0_Ch" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Paux0_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnee_PC_Fr" type="{}E_Existe_Valeur_Certifiee"/>
 *         &lt;element name="EER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pabs_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paux_uext_Fr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Statut_Val_EER_Pivot" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_EER_pivot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Val_Pabs_pivot_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Val_Paux_uext_Pivot_Fr" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Val_Paux_uext_Pivot_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux_uint_Fr" type="{}RT_Valeur_Declaree_Defaut"/>
 *         &lt;element name="Paux_uint_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Fonct_Moteur_Fr" type="{}E_Fonctionnement_Moteur"/>
 *         &lt;element name="Statut_CP_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CCP_LRcontmin_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Paux0_Fr" type="{}E_Valeur_Certifie_Justifiee_Defaut"/>
 *         &lt;element name="Paux0_Fr" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qrep" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Theta_Aval" type="{}ArrayOfDouble" minOccurs="0"/>
 *         &lt;element name="Theta_Amont" type="{}ArrayOfDouble" minOccurs="0"/>
 *         &lt;element name="N_Theta_Aval" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="N_Theta_Amont" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Theta_max_av_IGen" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Theta_min_av_IGen" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
public class T5CSTBPACMG {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Rdim")
    protected int rdim;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Statut_Donnee_PC_Ch", required = true)
    protected String statutDonneePCCh;
    @XmlElement(name = "GUE")
    protected String gue;
    @XmlElement(name = "Statut_Ch")
    protected String statutCh;
    @XmlElement(name = "Pabs_Ch")
    protected String pabsCh;
    @XmlElement(name = "Paux_uext_Ch")
    protected String pauxUextCh;
    @XmlElement(name = "Statut_Val_GUE_Pivot", required = true)
    protected String statutValGUEPivot;
    @XmlElement(name = "Val_GUE_pivot")
    protected double valGUEPivot;
    @XmlElement(name = "Val_Pabs_pivot_Ch")
    protected double valPabsPivotCh;
    @XmlElement(name = "Statut_Val_Paux_uext_Pivot_Ch", required = true)
    protected String statutValPauxUextPivotCh;
    @XmlElement(name = "Val_Paux_uext_Pivot_Ch")
    protected double valPauxUextPivotCh;
    @XmlElement(name = "Statut_Paux_uint_Ch", required = true)
    protected String statutPauxUintCh;
    @XmlElement(name = "Paux_uint_Ch")
    protected double pauxUintCh;
    @XmlElement(name = "Fonct_Moteur_Ch", required = true)
    protected String fonctMoteurCh;
    @XmlElement(name = "Statut_CP_Ch", required = true)
    protected String statutCPCh;
    @XmlElement(name = "LRcontmin_Ch")
    protected double lRcontminCh;
    @XmlElement(name = "CCP_LRcontmin_Ch")
    protected double ccplRcontminCh;
    @XmlElement(name = "Statut_Paux0_Ch", required = true)
    protected String statutPaux0Ch;
    @XmlElement(name = "Paux0_Ch")
    protected double paux0Ch;
    @XmlElement(name = "Statut_Donnee_PC_Fr", required = true)
    protected String statutDonneePCFr;
    @XmlElement(name = "EER")
    protected String eer;
    @XmlElement(name = "Statut_Fr")
    protected String statutFr;
    @XmlElement(name = "Pabs_Fr")
    protected String pabsFr;
    @XmlElement(name = "Paux_uext_Fr")
    protected String pauxUextFr;
    @XmlElement(name = "Statut_Val_EER_Pivot", required = true)
    protected String statutValEERPivot;
    @XmlElement(name = "Val_EER_pivot")
    protected double valEERPivot;
    @XmlElement(name = "Val_Pabs_pivot_Fr")
    protected double valPabsPivotFr;
    @XmlElement(name = "Statut_Val_Paux_uext_Pivot_Fr", required = true)
    protected String statutValPauxUextPivotFr;
    @XmlElement(name = "Val_Paux_uext_Pivot_Fr")
    protected double valPauxUextPivotFr;
    @XmlElement(name = "Statut_Paux_uint_Fr", required = true)
    protected String statutPauxUintFr;
    @XmlElement(name = "Paux_uint_Fr")
    protected double pauxUintFr;
    @XmlElement(name = "Fonct_Moteur_Fr", required = true)
    protected String fonctMoteurFr;
    @XmlElement(name = "Statut_CP_Fr", required = true)
    protected String statutCPFr;
    @XmlElement(name = "LRcontmin_Fr")
    protected double lRcontminFr;
    @XmlElement(name = "CCP_LRcontmin_Fr")
    protected double ccplRcontminFr;
    @XmlElement(name = "Statut_Paux0_Fr", required = true)
    protected String statutPaux0Fr;
    @XmlElement(name = "Paux0_Fr")
    protected double paux0Fr;
    @XmlElement(name = "Qrep")
    protected Double qrep;
    @XmlElement(name = "Theta_Aval")
    protected ArrayOfDouble thetaAval;
    @XmlElement(name = "Theta_Amont")
    protected ArrayOfDouble thetaAmont;
    @XmlElement(name = "N_Theta_Aval")
    protected Integer nThetaAval;
    @XmlElement(name = "N_Theta_Amont")
    protected Integer nThetaAmont;
    @XmlElement(name = "Theta_max_av_IGen")
    protected Double thetaMaxAvIGen;
    @XmlElement(name = "Theta_min_av_IGen")
    protected Double thetaMinAvIGen;

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
     * Gets the value of the statutDonneePCCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneePCCh() {
        return statutDonneePCCh;
    }

    /**
     * Sets the value of the statutDonneePCCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneePCCh(String value) {
        this.statutDonneePCCh = value;
    }

    /**
     * Gets the value of the gue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUE() {
        return gue;
    }

    /**
     * Sets the value of the gue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUE(String value) {
        this.gue = value;
    }

    /**
     * Gets the value of the statutCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutCh() {
        return statutCh;
    }

    /**
     * Sets the value of the statutCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutCh(String value) {
        this.statutCh = value;
    }

    /**
     * Gets the value of the pabsCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsCh() {
        return pabsCh;
    }

    /**
     * Sets the value of the pabsCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsCh(String value) {
        this.pabsCh = value;
    }

    /**
     * Gets the value of the pauxUextCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxUextCh() {
        return pauxUextCh;
    }

    /**
     * Sets the value of the pauxUextCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxUextCh(String value) {
        this.pauxUextCh = value;
    }

    /**
     * Gets the value of the statutValGUEPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValGUEPivot() {
        return statutValGUEPivot;
    }

    /**
     * Sets the value of the statutValGUEPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValGUEPivot(String value) {
        this.statutValGUEPivot = value;
    }

    /**
     * Gets the value of the valGUEPivot property.
     * 
     */
    public double getValGUEPivot() {
        return valGUEPivot;
    }

    /**
     * Sets the value of the valGUEPivot property.
     * 
     */
    public void setValGUEPivot(double value) {
        this.valGUEPivot = value;
    }

    /**
     * Gets the value of the valPabsPivotCh property.
     * 
     */
    public double getValPabsPivotCh() {
        return valPabsPivotCh;
    }

    /**
     * Sets the value of the valPabsPivotCh property.
     * 
     */
    public void setValPabsPivotCh(double value) {
        this.valPabsPivotCh = value;
    }

    /**
     * Gets the value of the statutValPauxUextPivotCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPauxUextPivotCh() {
        return statutValPauxUextPivotCh;
    }

    /**
     * Sets the value of the statutValPauxUextPivotCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPauxUextPivotCh(String value) {
        this.statutValPauxUextPivotCh = value;
    }

    /**
     * Gets the value of the valPauxUextPivotCh property.
     * 
     */
    public double getValPauxUextPivotCh() {
        return valPauxUextPivotCh;
    }

    /**
     * Sets the value of the valPauxUextPivotCh property.
     * 
     */
    public void setValPauxUextPivotCh(double value) {
        this.valPauxUextPivotCh = value;
    }

    /**
     * Gets the value of the statutPauxUintCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxUintCh() {
        return statutPauxUintCh;
    }

    /**
     * Sets the value of the statutPauxUintCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxUintCh(String value) {
        this.statutPauxUintCh = value;
    }

    /**
     * Gets the value of the pauxUintCh property.
     * 
     */
    public double getPauxUintCh() {
        return pauxUintCh;
    }

    /**
     * Sets the value of the pauxUintCh property.
     * 
     */
    public void setPauxUintCh(double value) {
        this.pauxUintCh = value;
    }

    /**
     * Gets the value of the fonctMoteurCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctMoteurCh() {
        return fonctMoteurCh;
    }

    /**
     * Sets the value of the fonctMoteurCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctMoteurCh(String value) {
        this.fonctMoteurCh = value;
    }

    /**
     * Gets the value of the statutCPCh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutCPCh() {
        return statutCPCh;
    }

    /**
     * Sets the value of the statutCPCh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutCPCh(String value) {
        this.statutCPCh = value;
    }

    /**
     * Gets the value of the lRcontminCh property.
     * 
     */
    public double getLRcontminCh() {
        return lRcontminCh;
    }

    /**
     * Sets the value of the lRcontminCh property.
     * 
     */
    public void setLRcontminCh(double value) {
        this.lRcontminCh = value;
    }

    /**
     * Gets the value of the ccplRcontminCh property.
     * 
     */
    public double getCCPLRcontminCh() {
        return ccplRcontminCh;
    }

    /**
     * Sets the value of the ccplRcontminCh property.
     * 
     */
    public void setCCPLRcontminCh(double value) {
        this.ccplRcontminCh = value;
    }

    /**
     * Gets the value of the statutPaux0Ch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPaux0Ch() {
        return statutPaux0Ch;
    }

    /**
     * Sets the value of the statutPaux0Ch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPaux0Ch(String value) {
        this.statutPaux0Ch = value;
    }

    /**
     * Gets the value of the paux0Ch property.
     * 
     */
    public double getPaux0Ch() {
        return paux0Ch;
    }

    /**
     * Sets the value of the paux0Ch property.
     * 
     */
    public void setPaux0Ch(double value) {
        this.paux0Ch = value;
    }

    /**
     * Gets the value of the statutDonneePCFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneePCFr() {
        return statutDonneePCFr;
    }

    /**
     * Sets the value of the statutDonneePCFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneePCFr(String value) {
        this.statutDonneePCFr = value;
    }

    /**
     * Gets the value of the eer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEER() {
        return eer;
    }

    /**
     * Sets the value of the eer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEER(String value) {
        this.eer = value;
    }

    /**
     * Gets the value of the statutFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutFr() {
        return statutFr;
    }

    /**
     * Sets the value of the statutFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutFr(String value) {
        this.statutFr = value;
    }

    /**
     * Gets the value of the pabsFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPabsFr() {
        return pabsFr;
    }

    /**
     * Sets the value of the pabsFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPabsFr(String value) {
        this.pabsFr = value;
    }

    /**
     * Gets the value of the pauxUextFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPauxUextFr() {
        return pauxUextFr;
    }

    /**
     * Sets the value of the pauxUextFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPauxUextFr(String value) {
        this.pauxUextFr = value;
    }

    /**
     * Gets the value of the statutValEERPivot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValEERPivot() {
        return statutValEERPivot;
    }

    /**
     * Sets the value of the statutValEERPivot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValEERPivot(String value) {
        this.statutValEERPivot = value;
    }

    /**
     * Gets the value of the valEERPivot property.
     * 
     */
    public double getValEERPivot() {
        return valEERPivot;
    }

    /**
     * Sets the value of the valEERPivot property.
     * 
     */
    public void setValEERPivot(double value) {
        this.valEERPivot = value;
    }

    /**
     * Gets the value of the valPabsPivotFr property.
     * 
     */
    public double getValPabsPivotFr() {
        return valPabsPivotFr;
    }

    /**
     * Sets the value of the valPabsPivotFr property.
     * 
     */
    public void setValPabsPivotFr(double value) {
        this.valPabsPivotFr = value;
    }

    /**
     * Gets the value of the statutValPauxUextPivotFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutValPauxUextPivotFr() {
        return statutValPauxUextPivotFr;
    }

    /**
     * Sets the value of the statutValPauxUextPivotFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutValPauxUextPivotFr(String value) {
        this.statutValPauxUextPivotFr = value;
    }

    /**
     * Gets the value of the valPauxUextPivotFr property.
     * 
     */
    public double getValPauxUextPivotFr() {
        return valPauxUextPivotFr;
    }

    /**
     * Sets the value of the valPauxUextPivotFr property.
     * 
     */
    public void setValPauxUextPivotFr(double value) {
        this.valPauxUextPivotFr = value;
    }

    /**
     * Gets the value of the statutPauxUintFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPauxUintFr() {
        return statutPauxUintFr;
    }

    /**
     * Sets the value of the statutPauxUintFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPauxUintFr(String value) {
        this.statutPauxUintFr = value;
    }

    /**
     * Gets the value of the pauxUintFr property.
     * 
     */
    public double getPauxUintFr() {
        return pauxUintFr;
    }

    /**
     * Sets the value of the pauxUintFr property.
     * 
     */
    public void setPauxUintFr(double value) {
        this.pauxUintFr = value;
    }

    /**
     * Gets the value of the fonctMoteurFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonctMoteurFr() {
        return fonctMoteurFr;
    }

    /**
     * Sets the value of the fonctMoteurFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonctMoteurFr(String value) {
        this.fonctMoteurFr = value;
    }

    /**
     * Gets the value of the statutCPFr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutCPFr() {
        return statutCPFr;
    }

    /**
     * Sets the value of the statutCPFr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutCPFr(String value) {
        this.statutCPFr = value;
    }

    /**
     * Gets the value of the lRcontminFr property.
     * 
     */
    public double getLRcontminFr() {
        return lRcontminFr;
    }

    /**
     * Sets the value of the lRcontminFr property.
     * 
     */
    public void setLRcontminFr(double value) {
        this.lRcontminFr = value;
    }

    /**
     * Gets the value of the ccplRcontminFr property.
     * 
     */
    public double getCCPLRcontminFr() {
        return ccplRcontminFr;
    }

    /**
     * Sets the value of the ccplRcontminFr property.
     * 
     */
    public void setCCPLRcontminFr(double value) {
        this.ccplRcontminFr = value;
    }

    /**
     * Gets the value of the statutPaux0Fr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutPaux0Fr() {
        return statutPaux0Fr;
    }

    /**
     * Sets the value of the statutPaux0Fr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutPaux0Fr(String value) {
        this.statutPaux0Fr = value;
    }

    /**
     * Gets the value of the paux0Fr property.
     * 
     */
    public double getPaux0Fr() {
        return paux0Fr;
    }

    /**
     * Sets the value of the paux0Fr property.
     * 
     */
    public void setPaux0Fr(double value) {
        this.paux0Fr = value;
    }

    /**
     * Gets the value of the qrep property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQrep() {
        return qrep;
    }

    /**
     * Sets the value of the qrep property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQrep(Double value) {
        this.qrep = value;
    }

    /**
     * Gets the value of the thetaAval property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getThetaAval() {
        return thetaAval;
    }

    /**
     * Sets the value of the thetaAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setThetaAval(ArrayOfDouble value) {
        this.thetaAval = value;
    }

    /**
     * Gets the value of the thetaAmont property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDouble }
     *     
     */
    public ArrayOfDouble getThetaAmont() {
        return thetaAmont;
    }

    /**
     * Sets the value of the thetaAmont property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDouble }
     *     
     */
    public void setThetaAmont(ArrayOfDouble value) {
        this.thetaAmont = value;
    }

    /**
     * Gets the value of the nThetaAval property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNThetaAval() {
        return nThetaAval;
    }

    /**
     * Sets the value of the nThetaAval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNThetaAval(Integer value) {
        this.nThetaAval = value;
    }

    /**
     * Gets the value of the nThetaAmont property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNThetaAmont() {
        return nThetaAmont;
    }

    /**
     * Sets the value of the nThetaAmont property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNThetaAmont(Integer value) {
        this.nThetaAmont = value;
    }

    /**
     * Gets the value of the thetaMaxAvIGen property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMaxAvIGen() {
        return thetaMaxAvIGen;
    }

    /**
     * Sets the value of the thetaMaxAvIGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMaxAvIGen(Double value) {
        this.thetaMaxAvIGen = value;
    }

    /**
     * Gets the value of the thetaMinAvIGen property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaMinAvIGen() {
        return thetaMinAvIGen;
    }

    /**
     * Sets the value of the thetaMinAvIGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaMinAvIGen(Double value) {
        this.thetaMinAvIGen = value;
    }

}
