
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Source_Ballon_Appoint_Combustion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Source_Ballon_Appoint_Combustion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rdim" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Generateur" type="{}E_Mode_Generateur_Combustion"/>
 *         &lt;element name="Ventilation" type="{}E_Ventilation_Generateur_Combustion"/>
 *         &lt;element name="Evac_Fumee" type="{}E_Evac_Fumee"/>
 *         &lt;element name="Combustible_Gaz" type="{}E_Combustible_Gaz"/>
 *         &lt;element name="Id_Fou_Gen_1" type="{}E_Id_FouGen_Mode1"/>
 *         &lt;element name="Id_Fou_Gen_4" type="{}E_Id_FouGen_Mode4"/>
 *         &lt;element name="Id_Fou_Gen_5" type="{}E_Id_FouGen_Mode5"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Ecs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Valeur_Mesuree_Defaut_Theta_Min" type="{}E_Valeur_Mesuree_Defaut"/>
 *         &lt;element name="Theta_Fonc_Min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Class_Chaud_Bois" type="{}E_Classe_Chaudiere_Bois"/>
 *         &lt;element name="Pn_gen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Defaut_R_pn" type="{}E_Valeur_Declaree_Justifiee_Certifiee_Defaut"/>
 *         &lt;element name="R_pn" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Certifiee_Defaut_R_Pint" type="{}E_Valeur_Declaree_Justifiee_Certifiee_Defaut"/>
 *         &lt;element name="R_Pint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Mesuree_Defaut_Q_po_30" type="{}E_Valeur_Mesuree_Defaut"/>
 *         &lt;element name="Q_po_30" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Chargement_Chaudiere_Bois" type="{}E_Chargement_Chaudiere_Bois"/>
 *         &lt;element name="Accumulateur_Gaz" type="{}E_Accumulateur_Gaz"/>
 *         &lt;element name="Q_veille" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeur_Mesuree_Defaut_Q_aux_nom" type="{}E_Valeur_Mesuree_Defaut"/>
 *         &lt;element name="Q_aux_nom" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Alim_Chaudiere_Bois" type="{}E_Chaudiere_Bois"/>
 *         &lt;element name="Ventil_Emission" type="{}E_Ventil_Emission"/>
 *         &lt;element name="Is_Cogeneration" type="{}RT_Oui_Non"/>
 *         &lt;element name="Id_app_inte" type="{}E_Cogeneration_Appoint_Separe_Integre"/>
 *         &lt;element name="Pn_th_coge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Pn_Prelec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_Prelec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="R_activ_Prelec" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeCombustibleBois" type="{}RT_TypesCombustiblesBois"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Source_Ballon_Appoint_Combustion", propOrder = {

})
public class RTDataSourceBallonAppointCombustion {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Rdim")
    protected Integer rdim;
    @XmlElement(name = "Generateur", required = true)
    protected String generateur;
    @XmlElement(name = "Ventilation", required = true)
    protected String ventilation;
    @XmlElement(name = "Evac_Fumee", required = true)
    protected String evacFumee;
    @XmlElement(name = "Combustible_Gaz", required = true)
    protected String combustibleGaz;
    @XmlElement(name = "Id_Fou_Gen_1", required = true)
    protected String idFouGen1;
    @XmlElement(name = "Id_Fou_Gen_4", required = true)
    protected String idFouGen4;
    @XmlElement(name = "Id_Fou_Gen_5", required = true)
    protected String idFouGen5;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Ecs")
    protected int idprioriteEcs;
    @XmlElement(name = "Valeur_Mesuree_Defaut_Theta_Min", required = true)
    protected String valeurMesureeDefautThetaMin;
    @XmlElement(name = "Theta_Fonc_Min")
    protected double thetaFoncMin;
    @XmlElement(name = "Class_Chaud_Bois", required = true)
    protected String classChaudBois;
    @XmlElement(name = "Pn_gen")
    protected double pnGen;
    @XmlElement(name = "Valeur_Certifiee_Defaut_R_pn", required = true)
    protected String valeurCertifieeDefautRPn;
    @XmlElement(name = "R_pn")
    protected double rPn;
    @XmlElement(name = "Pint")
    protected double pint;
    @XmlElement(name = "Valeur_Certifiee_Defaut_R_Pint", required = true)
    protected String valeurCertifieeDefautRPint;
    @XmlElement(name = "R_Pint")
    protected double rPint;
    @XmlElement(name = "Valeur_Mesuree_Defaut_Q_po_30", required = true)
    protected String valeurMesureeDefautQPo30;
    @XmlElement(name = "Q_po_30")
    protected double qPo30;
    @XmlElement(name = "Chargement_Chaudiere_Bois", required = true)
    protected String chargementChaudiereBois;
    @XmlElement(name = "Accumulateur_Gaz", required = true)
    protected String accumulateurGaz;
    @XmlElement(name = "Q_veille")
    protected double qVeille;
    @XmlElement(name = "Valeur_Mesuree_Defaut_Q_aux_nom", required = true)
    protected String valeurMesureeDefautQAuxNom;
    @XmlElement(name = "Q_aux_nom")
    protected double qAuxNom;
    @XmlElement(name = "Alim_Chaudiere_Bois", required = true)
    protected String alimChaudiereBois;
    @XmlElement(name = "Ventil_Emission", required = true)
    protected String ventilEmission;
    @XmlElement(name = "Is_Cogeneration", required = true)
    protected String isCogeneration;
    @XmlElement(name = "Id_app_inte", required = true)
    protected String idAppInte;
    @XmlElement(name = "Pn_th_coge")
    protected double pnThCoge;
    @XmlElement(name = "Pn_Prelec")
    protected double pnPrelec;
    @XmlElement(name = "R_Prelec")
    protected double rPrelec;
    @XmlElement(name = "R_activ_Prelec")
    protected double rActivPrelec;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "TypeCombustibleBois")
    protected int typeCombustibleBois;

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
     * Gets the value of the generateur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenerateur() {
        return generateur;
    }

    /**
     * Sets the value of the generateur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenerateur(String value) {
        this.generateur = value;
    }

    /**
     * Gets the value of the ventilation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVentilation() {
        return ventilation;
    }

    /**
     * Sets the value of the ventilation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVentilation(String value) {
        this.ventilation = value;
    }

    /**
     * Gets the value of the evacFumee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvacFumee() {
        return evacFumee;
    }

    /**
     * Sets the value of the evacFumee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvacFumee(String value) {
        this.evacFumee = value;
    }

    /**
     * Gets the value of the combustibleGaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombustibleGaz() {
        return combustibleGaz;
    }

    /**
     * Sets the value of the combustibleGaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombustibleGaz(String value) {
        this.combustibleGaz = value;
    }

    /**
     * Gets the value of the idFouGen1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouGen1() {
        return idFouGen1;
    }

    /**
     * Sets the value of the idFouGen1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouGen1(String value) {
        this.idFouGen1 = value;
    }

    /**
     * Gets the value of the idFouGen4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouGen4() {
        return idFouGen4;
    }

    /**
     * Sets the value of the idFouGen4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouGen4(String value) {
        this.idFouGen4 = value;
    }

    /**
     * Gets the value of the idFouGen5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFouGen5() {
        return idFouGen5;
    }

    /**
     * Sets the value of the idFouGen5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFouGen5(String value) {
        this.idFouGen5 = value;
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
     * Gets the value of the valeurMesureeDefautThetaMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurMesureeDefautThetaMin() {
        return valeurMesureeDefautThetaMin;
    }

    /**
     * Sets the value of the valeurMesureeDefautThetaMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurMesureeDefautThetaMin(String value) {
        this.valeurMesureeDefautThetaMin = value;
    }

    /**
     * Gets the value of the thetaFoncMin property.
     * 
     */
    public double getThetaFoncMin() {
        return thetaFoncMin;
    }

    /**
     * Sets the value of the thetaFoncMin property.
     * 
     */
    public void setThetaFoncMin(double value) {
        this.thetaFoncMin = value;
    }

    /**
     * Gets the value of the classChaudBois property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassChaudBois() {
        return classChaudBois;
    }

    /**
     * Sets the value of the classChaudBois property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassChaudBois(String value) {
        this.classChaudBois = value;
    }

    /**
     * Gets the value of the pnGen property.
     * 
     */
    public double getPnGen() {
        return pnGen;
    }

    /**
     * Sets the value of the pnGen property.
     * 
     */
    public void setPnGen(double value) {
        this.pnGen = value;
    }

    /**
     * Gets the value of the valeurCertifieeDefautRPn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurCertifieeDefautRPn() {
        return valeurCertifieeDefautRPn;
    }

    /**
     * Sets the value of the valeurCertifieeDefautRPn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurCertifieeDefautRPn(String value) {
        this.valeurCertifieeDefautRPn = value;
    }

    /**
     * Gets the value of the rPn property.
     * 
     */
    public double getRPn() {
        return rPn;
    }

    /**
     * Sets the value of the rPn property.
     * 
     */
    public void setRPn(double value) {
        this.rPn = value;
    }

    /**
     * Gets the value of the pint property.
     * 
     */
    public double getPint() {
        return pint;
    }

    /**
     * Sets the value of the pint property.
     * 
     */
    public void setPint(double value) {
        this.pint = value;
    }

    /**
     * Gets the value of the valeurCertifieeDefautRPint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurCertifieeDefautRPint() {
        return valeurCertifieeDefautRPint;
    }

    /**
     * Sets the value of the valeurCertifieeDefautRPint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurCertifieeDefautRPint(String value) {
        this.valeurCertifieeDefautRPint = value;
    }

    /**
     * Gets the value of the rPint property.
     * 
     */
    public double getRPint() {
        return rPint;
    }

    /**
     * Sets the value of the rPint property.
     * 
     */
    public void setRPint(double value) {
        this.rPint = value;
    }

    /**
     * Gets the value of the valeurMesureeDefautQPo30 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurMesureeDefautQPo30() {
        return valeurMesureeDefautQPo30;
    }

    /**
     * Sets the value of the valeurMesureeDefautQPo30 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurMesureeDefautQPo30(String value) {
        this.valeurMesureeDefautQPo30 = value;
    }

    /**
     * Gets the value of the qPo30 property.
     * 
     */
    public double getQPo30() {
        return qPo30;
    }

    /**
     * Sets the value of the qPo30 property.
     * 
     */
    public void setQPo30(double value) {
        this.qPo30 = value;
    }

    /**
     * Gets the value of the chargementChaudiereBois property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargementChaudiereBois() {
        return chargementChaudiereBois;
    }

    /**
     * Sets the value of the chargementChaudiereBois property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargementChaudiereBois(String value) {
        this.chargementChaudiereBois = value;
    }

    /**
     * Gets the value of the accumulateurGaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccumulateurGaz() {
        return accumulateurGaz;
    }

    /**
     * Sets the value of the accumulateurGaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccumulateurGaz(String value) {
        this.accumulateurGaz = value;
    }

    /**
     * Gets the value of the qVeille property.
     * 
     */
    public double getQVeille() {
        return qVeille;
    }

    /**
     * Sets the value of the qVeille property.
     * 
     */
    public void setQVeille(double value) {
        this.qVeille = value;
    }

    /**
     * Gets the value of the valeurMesureeDefautQAuxNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValeurMesureeDefautQAuxNom() {
        return valeurMesureeDefautQAuxNom;
    }

    /**
     * Sets the value of the valeurMesureeDefautQAuxNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValeurMesureeDefautQAuxNom(String value) {
        this.valeurMesureeDefautQAuxNom = value;
    }

    /**
     * Gets the value of the qAuxNom property.
     * 
     */
    public double getQAuxNom() {
        return qAuxNom;
    }

    /**
     * Sets the value of the qAuxNom property.
     * 
     */
    public void setQAuxNom(double value) {
        this.qAuxNom = value;
    }

    /**
     * Gets the value of the alimChaudiereBois property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlimChaudiereBois() {
        return alimChaudiereBois;
    }

    /**
     * Sets the value of the alimChaudiereBois property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlimChaudiereBois(String value) {
        this.alimChaudiereBois = value;
    }

    /**
     * Gets the value of the ventilEmission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVentilEmission() {
        return ventilEmission;
    }

    /**
     * Sets the value of the ventilEmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVentilEmission(String value) {
        this.ventilEmission = value;
    }

    /**
     * Gets the value of the isCogeneration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCogeneration() {
        return isCogeneration;
    }

    /**
     * Sets the value of the isCogeneration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCogeneration(String value) {
        this.isCogeneration = value;
    }

    /**
     * Gets the value of the idAppInte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAppInte() {
        return idAppInte;
    }

    /**
     * Sets the value of the idAppInte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAppInte(String value) {
        this.idAppInte = value;
    }

    /**
     * Gets the value of the pnThCoge property.
     * 
     */
    public double getPnThCoge() {
        return pnThCoge;
    }

    /**
     * Sets the value of the pnThCoge property.
     * 
     */
    public void setPnThCoge(double value) {
        this.pnThCoge = value;
    }

    /**
     * Gets the value of the pnPrelec property.
     * 
     */
    public double getPnPrelec() {
        return pnPrelec;
    }

    /**
     * Sets the value of the pnPrelec property.
     * 
     */
    public void setPnPrelec(double value) {
        this.pnPrelec = value;
    }

    /**
     * Gets the value of the rPrelec property.
     * 
     */
    public double getRPrelec() {
        return rPrelec;
    }

    /**
     * Sets the value of the rPrelec property.
     * 
     */
    public void setRPrelec(double value) {
        this.rPrelec = value;
    }

    /**
     * Gets the value of the rActivPrelec property.
     * 
     */
    public double getRActivPrelec() {
        return rActivPrelec;
    }

    /**
     * Sets the value of the rActivPrelec property.
     * 
     */
    public void setRActivPrelec(double value) {
        this.rActivPrelec = value;
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
     * Gets the value of the typeCombustibleBois property.
     * 
     */
    public int getTypeCombustibleBois() {
        return typeCombustibleBois;
    }

    /**
     * Sets the value of the typeCombustibleBois property.
     * 
     */
    public void setTypeCombustibleBois(int value) {
        this.typeCombustibleBois = value;
    }

}
