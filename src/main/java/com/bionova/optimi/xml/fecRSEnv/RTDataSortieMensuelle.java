
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Mensuelle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Mensuelle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Mois" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Valeur" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Mensuelle", propOrder = {
    "index",
    "mois",
    "valeur"
})
public class RTDataSortieMensuelle {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Mois")
    protected int mois;
    @XmlElement(name = "Valeur")
    protected double valeur;

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
     * Gets the value of the mois property.
     * 
     */
    public int getMois() {
        return mois;
    }

    /**
     * Sets the value of the mois property.
     * 
     */
    public void setMois(int value) {
        this.mois = value;
    }

    /**
     * Gets the value of the valeur property.
     * 
     */
    public double getValeur() {
        return valeur;
    }

    /**
     * Sets the value of the valeur property.
     * 
     */
    public void setValeur(double value) {
        this.valeur = value;
    }

}
