
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * v8000
 * 
 * <p>Java class for RT_Data_Sortie_Ascenseur_C complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Ascenseur_C">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_Eti" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Etm" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Etot" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="m_O_Cef_asc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Emoy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Tmobh" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Ndem" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Ascenseur_C", propOrder = {
    "oEti",
    "oEtm",
    "oEtot",
    "moCefAsc",
    "oEmoy",
    "oTmobh",
    "oNdem"
})
public class RTDataSortieAscenseurC
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Eti")
    protected double oEti;
    @XmlElement(name = "O_Etm")
    protected double oEtm;
    @XmlElement(name = "O_Etot")
    protected double oEtot;
    @XmlElement(name = "m_O_Cef_asc")
    protected double moCefAsc;
    @XmlElement(name = "O_Emoy")
    protected double oEmoy;
    @XmlElement(name = "O_Tmobh")
    protected double oTmobh;
    @XmlElement(name = "O_Ndem")
    protected double oNdem;

    /**
     * Gets the value of the oEti property.
     * 
     */
    public double getOEti() {
        return oEti;
    }

    /**
     * Sets the value of the oEti property.
     * 
     */
    public void setOEti(double value) {
        this.oEti = value;
    }

    /**
     * Gets the value of the oEtm property.
     * 
     */
    public double getOEtm() {
        return oEtm;
    }

    /**
     * Sets the value of the oEtm property.
     * 
     */
    public void setOEtm(double value) {
        this.oEtm = value;
    }

    /**
     * Gets the value of the oEtot property.
     * 
     */
    public double getOEtot() {
        return oEtot;
    }

    /**
     * Sets the value of the oEtot property.
     * 
     */
    public void setOEtot(double value) {
        this.oEtot = value;
    }

    /**
     * Gets the value of the moCefAsc property.
     * 
     */
    public double getMOCefAsc() {
        return moCefAsc;
    }

    /**
     * Sets the value of the moCefAsc property.
     * 
     */
    public void setMOCefAsc(double value) {
        this.moCefAsc = value;
    }

    /**
     * Gets the value of the oEmoy property.
     * 
     */
    public double getOEmoy() {
        return oEmoy;
    }

    /**
     * Sets the value of the oEmoy property.
     * 
     */
    public void setOEmoy(double value) {
        this.oEmoy = value;
    }

    /**
     * Gets the value of the oTmobh property.
     * 
     */
    public double getOTmobh() {
        return oTmobh;
    }

    /**
     * Sets the value of the oTmobh property.
     * 
     */
    public void setOTmobh(double value) {
        this.oTmobh = value;
    }

    /**
     * Gets the value of the oNdem property.
     * 
     */
    public double getONdem() {
        return oNdem;
    }

    /**
     * Sets the value of the oNdem property.
     * 
     */
    public void setONdem(double value) {
        this.oNdem = value;
    }

}
