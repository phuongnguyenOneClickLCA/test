
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_Cardonnel_France_Energie_Gen_BE
 * 
 * <p>Java class for T5_Cardonnel_France_Energie_Gen_BE_Data complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="T5_Cardonnel_France_Energie_Gen_BE_Data">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Idpriorite_Ch" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Idpriorite_Fr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_Source_Amont" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id_recup_ae" type="{}E_Presence"/>
 *         &lt;element name="Id_recup_eu" type="{}E_Presence"/>
 *         &lt;element name="Id_recup_sol" type="{}E_Presence"/>
 *         &lt;element name="debit_eau_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Paux_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_min_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_max_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_mois_mini_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="volume_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="chute_temp_boucle" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="P_ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tee_ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tes_ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tae_ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Tas_ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Qair_ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Nb_cuve" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="V_cuve" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_cuve" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UA_ech" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="N_puits" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="H_puits" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dsep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="rb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="rp_i" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="rp_e" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="kp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="kg" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_min_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_max_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_mois_mini_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cv_sol" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Id_appoint_froid_boucle" type="{}E_type_appoint_froid_boucle"/>
 *         &lt;element name="Type_tour" type="{}E_type_tour"/>
 *         &lt;element name="Pvent_tour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="delta_theta_tour" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="theta_es_tour_consigne" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ECS_EU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "T5_Cardonnel_France_Energie_Gen_BE_Data", propOrder = {

})
@XmlSeeAlso({
    T5CardonnelFranceEnergieGenBE.class
})
public class T5CardonnelFranceEnergieGenBEData {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Idpriorite_Ch")
    protected int idprioriteCh;
    @XmlElement(name = "Idpriorite_Fr")
    protected int idprioriteFr;
    @XmlElement(name = "Id_Source_Amont")
    protected int idSourceAmont;
    @XmlElement(name = "Id_recup_ae", required = true)
    protected String idRecupAe;
    @XmlElement(name = "Id_recup_eu", required = true)
    protected String idRecupEu;
    @XmlElement(name = "Id_recup_sol", required = true)
    protected String idRecupSol;
    @XmlElement(name = "debit_eau_boucle")
    protected double debitEauBoucle;
    @XmlElement(name = "Paux_boucle")
    protected double pauxBoucle;
    @XmlElement(name = "theta_min_boucle")
    protected double thetaMinBoucle;
    @XmlElement(name = "theta_max_boucle")
    protected double thetaMaxBoucle;
    @XmlElement(name = "Id_mois_mini_boucle")
    protected double idMoisMiniBoucle;
    @XmlElement(name = "volume_boucle")
    protected double volumeBoucle;
    @XmlElement(name = "chute_temp_boucle")
    protected double chuteTempBoucle;
    @XmlElement(name = "P_ref")
    protected double pRef;
    @XmlElement(name = "Tee_ref")
    protected double teeRef;
    @XmlElement(name = "Tes_ref")
    protected double tesRef;
    @XmlElement(name = "Tae_ref")
    protected double taeRef;
    @XmlElement(name = "Tas_ref")
    protected double tasRef;
    @XmlElement(name = "Qair_ref")
    protected double qairRef;
    @XmlElement(name = "Nb_cuve")
    protected int nbCuve;
    @XmlElement(name = "V_cuve")
    protected double vCuve;
    @XmlElement(name = "UA_cuve")
    protected double uaCuve;
    @XmlElement(name = "UA_ech")
    protected double uaEch;
    @XmlElement(name = "N_puits")
    protected int nPuits;
    @XmlElement(name = "H_puits")
    protected double hPuits;
    protected double dsep;
    protected double rb;
    @XmlElement(name = "rp_i")
    protected double rpI;
    @XmlElement(name = "rp_e")
    protected double rpE;
    protected double kp;
    protected double kg;
    @XmlElement(name = "theta_min_sol")
    protected double thetaMinSol;
    @XmlElement(name = "theta_max_sol")
    protected double thetaMaxSol;
    @XmlElement(name = "Id_mois_mini_sol")
    protected double idMoisMiniSol;
    @XmlElement(name = "Cv_sol")
    protected double cvSol;
    @XmlElement(name = "Id_appoint_froid_boucle", required = true)
    protected String idAppointFroidBoucle;
    @XmlElement(name = "Type_tour", required = true)
    protected String typeTour;
    @XmlElement(name = "Pvent_tour")
    protected double pventTour;
    @XmlElement(name = "delta_theta_tour")
    protected double deltaThetaTour;
    @XmlElement(name = "theta_es_tour_consigne")
    protected double thetaEsTourConsigne;
    @XmlElement(name = "ECS_EU")
    protected String ecseu;

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
     * Gets the value of the idRecupAe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRecupAe() {
        return idRecupAe;
    }

    /**
     * Sets the value of the idRecupAe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRecupAe(String value) {
        this.idRecupAe = value;
    }

    /**
     * Gets the value of the idRecupEu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRecupEu() {
        return idRecupEu;
    }

    /**
     * Sets the value of the idRecupEu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRecupEu(String value) {
        this.idRecupEu = value;
    }

    /**
     * Gets the value of the idRecupSol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRecupSol() {
        return idRecupSol;
    }

    /**
     * Sets the value of the idRecupSol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRecupSol(String value) {
        this.idRecupSol = value;
    }

    /**
     * Gets the value of the debitEauBoucle property.
     * 
     */
    public double getDebitEauBoucle() {
        return debitEauBoucle;
    }

    /**
     * Sets the value of the debitEauBoucle property.
     * 
     */
    public void setDebitEauBoucle(double value) {
        this.debitEauBoucle = value;
    }

    /**
     * Gets the value of the pauxBoucle property.
     * 
     */
    public double getPauxBoucle() {
        return pauxBoucle;
    }

    /**
     * Sets the value of the pauxBoucle property.
     * 
     */
    public void setPauxBoucle(double value) {
        this.pauxBoucle = value;
    }

    /**
     * Gets the value of the thetaMinBoucle property.
     * 
     */
    public double getThetaMinBoucle() {
        return thetaMinBoucle;
    }

    /**
     * Sets the value of the thetaMinBoucle property.
     * 
     */
    public void setThetaMinBoucle(double value) {
        this.thetaMinBoucle = value;
    }

    /**
     * Gets the value of the thetaMaxBoucle property.
     * 
     */
    public double getThetaMaxBoucle() {
        return thetaMaxBoucle;
    }

    /**
     * Sets the value of the thetaMaxBoucle property.
     * 
     */
    public void setThetaMaxBoucle(double value) {
        this.thetaMaxBoucle = value;
    }

    /**
     * Gets the value of the idMoisMiniBoucle property.
     * 
     */
    public double getIdMoisMiniBoucle() {
        return idMoisMiniBoucle;
    }

    /**
     * Sets the value of the idMoisMiniBoucle property.
     * 
     */
    public void setIdMoisMiniBoucle(double value) {
        this.idMoisMiniBoucle = value;
    }

    /**
     * Gets the value of the volumeBoucle property.
     * 
     */
    public double getVolumeBoucle() {
        return volumeBoucle;
    }

    /**
     * Sets the value of the volumeBoucle property.
     * 
     */
    public void setVolumeBoucle(double value) {
        this.volumeBoucle = value;
    }

    /**
     * Gets the value of the chuteTempBoucle property.
     * 
     */
    public double getChuteTempBoucle() {
        return chuteTempBoucle;
    }

    /**
     * Sets the value of the chuteTempBoucle property.
     * 
     */
    public void setChuteTempBoucle(double value) {
        this.chuteTempBoucle = value;
    }

    /**
     * Gets the value of the pRef property.
     * 
     */
    public double getPRef() {
        return pRef;
    }

    /**
     * Sets the value of the pRef property.
     * 
     */
    public void setPRef(double value) {
        this.pRef = value;
    }

    /**
     * Gets the value of the teeRef property.
     * 
     */
    public double getTeeRef() {
        return teeRef;
    }

    /**
     * Sets the value of the teeRef property.
     * 
     */
    public void setTeeRef(double value) {
        this.teeRef = value;
    }

    /**
     * Gets the value of the tesRef property.
     * 
     */
    public double getTesRef() {
        return tesRef;
    }

    /**
     * Sets the value of the tesRef property.
     * 
     */
    public void setTesRef(double value) {
        this.tesRef = value;
    }

    /**
     * Gets the value of the taeRef property.
     * 
     */
    public double getTaeRef() {
        return taeRef;
    }

    /**
     * Sets the value of the taeRef property.
     * 
     */
    public void setTaeRef(double value) {
        this.taeRef = value;
    }

    /**
     * Gets the value of the tasRef property.
     * 
     */
    public double getTasRef() {
        return tasRef;
    }

    /**
     * Sets the value of the tasRef property.
     * 
     */
    public void setTasRef(double value) {
        this.tasRef = value;
    }

    /**
     * Gets the value of the qairRef property.
     * 
     */
    public double getQairRef() {
        return qairRef;
    }

    /**
     * Sets the value of the qairRef property.
     * 
     */
    public void setQairRef(double value) {
        this.qairRef = value;
    }

    /**
     * Gets the value of the nbCuve property.
     * 
     */
    public int getNbCuve() {
        return nbCuve;
    }

    /**
     * Sets the value of the nbCuve property.
     * 
     */
    public void setNbCuve(int value) {
        this.nbCuve = value;
    }

    /**
     * Gets the value of the vCuve property.
     * 
     */
    public double getVCuve() {
        return vCuve;
    }

    /**
     * Sets the value of the vCuve property.
     * 
     */
    public void setVCuve(double value) {
        this.vCuve = value;
    }

    /**
     * Gets the value of the uaCuve property.
     * 
     */
    public double getUACuve() {
        return uaCuve;
    }

    /**
     * Sets the value of the uaCuve property.
     * 
     */
    public void setUACuve(double value) {
        this.uaCuve = value;
    }

    /**
     * Gets the value of the uaEch property.
     * 
     */
    public double getUAEch() {
        return uaEch;
    }

    /**
     * Sets the value of the uaEch property.
     * 
     */
    public void setUAEch(double value) {
        this.uaEch = value;
    }

    /**
     * Gets the value of the nPuits property.
     * 
     */
    public int getNPuits() {
        return nPuits;
    }

    /**
     * Sets the value of the nPuits property.
     * 
     */
    public void setNPuits(int value) {
        this.nPuits = value;
    }

    /**
     * Gets the value of the hPuits property.
     * 
     */
    public double getHPuits() {
        return hPuits;
    }

    /**
     * Sets the value of the hPuits property.
     * 
     */
    public void setHPuits(double value) {
        this.hPuits = value;
    }

    /**
     * Gets the value of the dsep property.
     * 
     */
    public double getDsep() {
        return dsep;
    }

    /**
     * Sets the value of the dsep property.
     * 
     */
    public void setDsep(double value) {
        this.dsep = value;
    }

    /**
     * Gets the value of the rb property.
     * 
     */
    public double getRb() {
        return rb;
    }

    /**
     * Sets the value of the rb property.
     * 
     */
    public void setRb(double value) {
        this.rb = value;
    }

    /**
     * Gets the value of the rpI property.
     * 
     */
    public double getRpI() {
        return rpI;
    }

    /**
     * Sets the value of the rpI property.
     * 
     */
    public void setRpI(double value) {
        this.rpI = value;
    }

    /**
     * Gets the value of the rpE property.
     * 
     */
    public double getRpE() {
        return rpE;
    }

    /**
     * Sets the value of the rpE property.
     * 
     */
    public void setRpE(double value) {
        this.rpE = value;
    }

    /**
     * Gets the value of the kp property.
     * 
     */
    public double getKp() {
        return kp;
    }

    /**
     * Sets the value of the kp property.
     * 
     */
    public void setKp(double value) {
        this.kp = value;
    }

    /**
     * Gets the value of the kg property.
     * 
     */
    public double getKg() {
        return kg;
    }

    /**
     * Sets the value of the kg property.
     * 
     */
    public void setKg(double value) {
        this.kg = value;
    }

    /**
     * Gets the value of the thetaMinSol property.
     * 
     */
    public double getThetaMinSol() {
        return thetaMinSol;
    }

    /**
     * Sets the value of the thetaMinSol property.
     * 
     */
    public void setThetaMinSol(double value) {
        this.thetaMinSol = value;
    }

    /**
     * Gets the value of the thetaMaxSol property.
     * 
     */
    public double getThetaMaxSol() {
        return thetaMaxSol;
    }

    /**
     * Sets the value of the thetaMaxSol property.
     * 
     */
    public void setThetaMaxSol(double value) {
        this.thetaMaxSol = value;
    }

    /**
     * Gets the value of the idMoisMiniSol property.
     * 
     */
    public double getIdMoisMiniSol() {
        return idMoisMiniSol;
    }

    /**
     * Sets the value of the idMoisMiniSol property.
     * 
     */
    public void setIdMoisMiniSol(double value) {
        this.idMoisMiniSol = value;
    }

    /**
     * Gets the value of the cvSol property.
     * 
     */
    public double getCvSol() {
        return cvSol;
    }

    /**
     * Sets the value of the cvSol property.
     * 
     */
    public void setCvSol(double value) {
        this.cvSol = value;
    }

    /**
     * Gets the value of the idAppointFroidBoucle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAppointFroidBoucle() {
        return idAppointFroidBoucle;
    }

    /**
     * Sets the value of the idAppointFroidBoucle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAppointFroidBoucle(String value) {
        this.idAppointFroidBoucle = value;
    }

    /**
     * Gets the value of the typeTour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeTour() {
        return typeTour;
    }

    /**
     * Sets the value of the typeTour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeTour(String value) {
        this.typeTour = value;
    }

    /**
     * Gets the value of the pventTour property.
     * 
     */
    public double getPventTour() {
        return pventTour;
    }

    /**
     * Sets the value of the pventTour property.
     * 
     */
    public void setPventTour(double value) {
        this.pventTour = value;
    }

    /**
     * Gets the value of the deltaThetaTour property.
     * 
     */
    public double getDeltaThetaTour() {
        return deltaThetaTour;
    }

    /**
     * Sets the value of the deltaThetaTour property.
     * 
     */
    public void setDeltaThetaTour(double value) {
        this.deltaThetaTour = value;
    }

    /**
     * Gets the value of the thetaEsTourConsigne property.
     * 
     */
    public double getThetaEsTourConsigne() {
        return thetaEsTourConsigne;
    }

    /**
     * Sets the value of the thetaEsTourConsigne property.
     * 
     */
    public void setThetaEsTourConsigne(double value) {
        this.thetaEsTourConsigne = value;
    }

    /**
     * Gets the value of the ecseu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getECSEU() {
        return ecseu;
    }

    /**
     * Sets the value of the ecseu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setECSEU(String value) {
        this.ecseu = value;
    }

}
