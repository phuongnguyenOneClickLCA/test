
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * add 7100 - T5 module appartement Cardonnel - ECS seul
 * 
 * <p>Java class for ModuleAppartementRT2012ECSData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModuleAppartementRT2012ECSData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all minOccurs="0">
 *         &lt;element name="Id_Fonction" type="{}E_Fonctionnement_Reseau_Intergroupe_Mixte"/>
 *         &lt;element name="Is_Maintenir_Temp_Reseau" type="{}E_Maintenir_Reseau_En_Temperature"/>
 *         &lt;element name="Id_Regulation_Circ" type="{}E_Mode_Regulation_Circulateur"/>
 *         &lt;element name="Id_Position" type="{}E_Position_Module"/>
 *         &lt;element name="a" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="c" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Statut_Donnees_Echangeur_ECS" type="{}E_Statut_Donnees_Echangeur_ECS"/>
 *         &lt;element name="Theta_In_Prim_Nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Maintien_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nb_Mod" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Theta_Out_Prim_Maintien_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Ep_Iso_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Lambda_Iso_Echangeur_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_ECS" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_Module" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Aux_Fct" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Aux_Arret" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_H_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_Vc_gaine_MTA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="L_H_Vc_gaine_MTA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Aux" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_Circ_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Moy_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="U_Moy_H_Vc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="q_Resid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_Gen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_PCAD" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type_Reseau_Intergroupe_ECS" type="{}RT_Type_Reseau_ECS"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModuleAppartementRT2012ECSData", propOrder = {

})
public class ModuleAppartementRT2012ECSData {

    @XmlElement(name = "Id_Fonction")
    protected String idFonction;
    @XmlElement(name = "Is_Maintenir_Temp_Reseau")
    protected String isMaintenirTempReseau;
    @XmlElement(name = "Id_Regulation_Circ")
    protected String idRegulationCirc;
    @XmlElement(name = "Id_Position")
    protected String idPosition;
    protected Double a;
    protected Double b;
    protected Double c;
    @XmlElement(name = "Statut_Donnees_Echangeur_ECS")
    protected String statutDonneesEchangeurECS;
    @XmlElement(name = "Theta_In_Prim_Nom")
    protected Double thetaInPrimNom;
    @XmlElement(name = "q_Maintien_Echangeur_ECS")
    protected Double qMaintienEchangeurECS;
    @XmlElement(name = "Nb_Mod")
    protected Double nbMod;
    @XmlElement(name = "Theta_Out_Prim_Maintien_Echangeur_ECS")
    protected Double thetaOutPrimMaintienEchangeurECS;
    @XmlElement(name = "Ep_Iso_Echangeur_ECS")
    protected Double epIsoEchangeurECS;
    @XmlElement(name = "Lambda_Iso_Echangeur_ECS")
    protected Double lambdaIsoEchangeurECS;
    @XmlElement(name = "U_ECS")
    protected Double uecs;
    @XmlElement(name = "L_ECS")
    protected Double lecs;
    @XmlElement(name = "R_Module")
    protected Double rModule;
    @XmlElement(name = "P_Aux_Fct")
    protected Double pAuxFct;
    @XmlElement(name = "P_Aux_Arret")
    protected Double pAuxArret;
    @XmlElement(name = "q_Nom")
    protected Double qNom;
    @XmlElement(name = "L_Vc")
    protected Double lVc;
    @XmlElement(name = "L_H_Vc")
    protected Double lhVc;
    @XmlElement(name = "L_Vc_gaine_MTA")
    protected Double lVcGaineMTA;
    @XmlElement(name = "L_H_Vc_gaine_MTA")
    protected Double lhVcGaineMTA;
    @XmlElement(name = "P_Aux")
    protected Double pAux;
    @XmlElement(name = "P_Circ_Vc")
    protected Double pCircVc;
    @XmlElement(name = "U_Moy_Vc")
    protected Double uMoyVc;
    @XmlElement(name = "U_Moy_H_Vc")
    protected Double uMoyHVc;
    @XmlElement(name = "q_Resid")
    protected Double qResid;
    @XmlElement(name = "Id_Gen")
    protected Integer idGen;
    @XmlElement(name = "Id_PCAD")
    protected Integer idPCAD;
    @XmlElement(name = "Index")
    protected Integer index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Type_Reseau_Intergroupe_ECS")
    protected String typeReseauIntergroupeECS;

    /**
     * Gets the value of the idFonction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFonction() {
        return idFonction;
    }

    /**
     * Sets the value of the idFonction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFonction(String value) {
        this.idFonction = value;
    }

    /**
     * Gets the value of the isMaintenirTempReseau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsMaintenirTempReseau() {
        return isMaintenirTempReseau;
    }

    /**
     * Sets the value of the isMaintenirTempReseau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsMaintenirTempReseau(String value) {
        this.isMaintenirTempReseau = value;
    }

    /**
     * Gets the value of the idRegulationCirc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRegulationCirc() {
        return idRegulationCirc;
    }

    /**
     * Sets the value of the idRegulationCirc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRegulationCirc(String value) {
        this.idRegulationCirc = value;
    }

    /**
     * Gets the value of the idPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPosition() {
        return idPosition;
    }

    /**
     * Sets the value of the idPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPosition(String value) {
        this.idPosition = value;
    }

    /**
     * Gets the value of the a property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setA(Double value) {
        this.a = value;
    }

    /**
     * Gets the value of the b property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getB() {
        return b;
    }

    /**
     * Sets the value of the b property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setB(Double value) {
        this.b = value;
    }

    /**
     * Gets the value of the c property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC() {
        return c;
    }

    /**
     * Sets the value of the c property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC(Double value) {
        this.c = value;
    }

    /**
     * Gets the value of the statutDonneesEchangeurECS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutDonneesEchangeurECS() {
        return statutDonneesEchangeurECS;
    }

    /**
     * Sets the value of the statutDonneesEchangeurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutDonneesEchangeurECS(String value) {
        this.statutDonneesEchangeurECS = value;
    }

    /**
     * Gets the value of the thetaInPrimNom property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaInPrimNom() {
        return thetaInPrimNom;
    }

    /**
     * Sets the value of the thetaInPrimNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaInPrimNom(Double value) {
        this.thetaInPrimNom = value;
    }

    /**
     * Gets the value of the qMaintienEchangeurECS property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQMaintienEchangeurECS() {
        return qMaintienEchangeurECS;
    }

    /**
     * Sets the value of the qMaintienEchangeurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQMaintienEchangeurECS(Double value) {
        this.qMaintienEchangeurECS = value;
    }

    /**
     * Gets the value of the nbMod property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNbMod() {
        return nbMod;
    }

    /**
     * Sets the value of the nbMod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNbMod(Double value) {
        this.nbMod = value;
    }

    /**
     * Gets the value of the thetaOutPrimMaintienEchangeurECS property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getThetaOutPrimMaintienEchangeurECS() {
        return thetaOutPrimMaintienEchangeurECS;
    }

    /**
     * Sets the value of the thetaOutPrimMaintienEchangeurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setThetaOutPrimMaintienEchangeurECS(Double value) {
        this.thetaOutPrimMaintienEchangeurECS = value;
    }

    /**
     * Gets the value of the epIsoEchangeurECS property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEpIsoEchangeurECS() {
        return epIsoEchangeurECS;
    }

    /**
     * Sets the value of the epIsoEchangeurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEpIsoEchangeurECS(Double value) {
        this.epIsoEchangeurECS = value;
    }

    /**
     * Gets the value of the lambdaIsoEchangeurECS property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLambdaIsoEchangeurECS() {
        return lambdaIsoEchangeurECS;
    }

    /**
     * Sets the value of the lambdaIsoEchangeurECS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLambdaIsoEchangeurECS(Double value) {
        this.lambdaIsoEchangeurECS = value;
    }

    /**
     * Gets the value of the uecs property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUECS() {
        return uecs;
    }

    /**
     * Sets the value of the uecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUECS(Double value) {
        this.uecs = value;
    }

    /**
     * Gets the value of the lecs property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLECS() {
        return lecs;
    }

    /**
     * Sets the value of the lecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLECS(Double value) {
        this.lecs = value;
    }

    /**
     * Gets the value of the rModule property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRModule() {
        return rModule;
    }

    /**
     * Sets the value of the rModule property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRModule(Double value) {
        this.rModule = value;
    }

    /**
     * Gets the value of the pAuxFct property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPAuxFct() {
        return pAuxFct;
    }

    /**
     * Sets the value of the pAuxFct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPAuxFct(Double value) {
        this.pAuxFct = value;
    }

    /**
     * Gets the value of the pAuxArret property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPAuxArret() {
        return pAuxArret;
    }

    /**
     * Sets the value of the pAuxArret property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPAuxArret(Double value) {
        this.pAuxArret = value;
    }

    /**
     * Gets the value of the qNom property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQNom() {
        return qNom;
    }

    /**
     * Sets the value of the qNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQNom(Double value) {
        this.qNom = value;
    }

    /**
     * Gets the value of the lVc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLVc() {
        return lVc;
    }

    /**
     * Sets the value of the lVc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLVc(Double value) {
        this.lVc = value;
    }

    /**
     * Gets the value of the lhVc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLHVc() {
        return lhVc;
    }

    /**
     * Sets the value of the lhVc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLHVc(Double value) {
        this.lhVc = value;
    }

    /**
     * Gets the value of the lVcGaineMTA property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLVcGaineMTA() {
        return lVcGaineMTA;
    }

    /**
     * Sets the value of the lVcGaineMTA property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLVcGaineMTA(Double value) {
        this.lVcGaineMTA = value;
    }

    /**
     * Gets the value of the lhVcGaineMTA property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLHVcGaineMTA() {
        return lhVcGaineMTA;
    }

    /**
     * Sets the value of the lhVcGaineMTA property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLHVcGaineMTA(Double value) {
        this.lhVcGaineMTA = value;
    }

    /**
     * Gets the value of the pAux property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPAux() {
        return pAux;
    }

    /**
     * Sets the value of the pAux property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPAux(Double value) {
        this.pAux = value;
    }

    /**
     * Gets the value of the pCircVc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPCircVc() {
        return pCircVc;
    }

    /**
     * Sets the value of the pCircVc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPCircVc(Double value) {
        this.pCircVc = value;
    }

    /**
     * Gets the value of the uMoyVc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUMoyVc() {
        return uMoyVc;
    }

    /**
     * Sets the value of the uMoyVc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUMoyVc(Double value) {
        this.uMoyVc = value;
    }

    /**
     * Gets the value of the uMoyHVc property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getUMoyHVc() {
        return uMoyHVc;
    }

    /**
     * Sets the value of the uMoyHVc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setUMoyHVc(Double value) {
        this.uMoyHVc = value;
    }

    /**
     * Gets the value of the qResid property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getQResid() {
        return qResid;
    }

    /**
     * Sets the value of the qResid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setQResid(Double value) {
        this.qResid = value;
    }

    /**
     * Gets the value of the idGen property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdGen() {
        return idGen;
    }

    /**
     * Sets the value of the idGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdGen(Integer value) {
        this.idGen = value;
    }

    /**
     * Gets the value of the idPCAD property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPCAD() {
        return idPCAD;
    }

    /**
     * Sets the value of the idPCAD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPCAD(Integer value) {
        this.idPCAD = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(Integer value) {
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

}