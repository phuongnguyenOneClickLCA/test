
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
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
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieAscenseurC
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Eti")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEti;
    @XmlElement(name = "O_Etm")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEtm;
    @XmlElement(name = "O_Etot")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEtot;
    @XmlElement(name = "m_O_Cef_asc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double moCefAsc;
    @XmlElement(name = "O_Emoy")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oEmoy;
    @XmlElement(name = "O_Tmobh")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oTmobh;
    @XmlElement(name = "O_Ndem")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double oNdem;

    /**
     * Gets the value of the oEti property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEti() {
        return oEti;
    }

    /**
     * Sets the value of the oEti property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEti(double value) {
        this.oEti = value;
    }

    /**
     * Gets the value of the oEtm property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEtm() {
        return oEtm;
    }

    /**
     * Sets the value of the oEtm property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEtm(double value) {
        this.oEtm = value;
    }

    /**
     * Gets the value of the oEtot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEtot() {
        return oEtot;
    }

    /**
     * Sets the value of the oEtot property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEtot(double value) {
        this.oEtot = value;
    }

    /**
     * Gets the value of the moCefAsc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getMOCefAsc() {
        return moCefAsc;
    }

    /**
     * Sets the value of the moCefAsc property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMOCefAsc(double value) {
        this.moCefAsc = value;
    }

    /**
     * Gets the value of the oEmoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOEmoy() {
        return oEmoy;
    }

    /**
     * Sets the value of the oEmoy property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOEmoy(double value) {
        this.oEmoy = value;
    }

    /**
     * Gets the value of the oTmobh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getOTmobh() {
        return oTmobh;
    }

    /**
     * Sets the value of the oTmobh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOTmobh(double value) {
        this.oTmobh = value;
    }

    /**
     * Gets the value of the oNdem property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getONdem() {
        return oNdem;
    }

    /**
     * Sets the value of the oNdem property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setONdem(double value) {
        this.oNdem = value;
    }

}
