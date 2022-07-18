
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Distribution_Intergroupe_ECS_MTA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Distribution_Intergroupe_ECS_MTA">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Mod_pertes_h" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Theta_in_prim_h" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Theta_out_prim_h" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Q_totale_h" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Mod_circ_h" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Distribution_Intergroupe_ECS_MTA", propOrder = {
    "modPertesH",
    "thetaInPrimH",
    "thetaOutPrimH",
    "qTotaleH",
    "modCircH"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieDistributionIntergroupeECSMTA
    extends RTDataSortieBase
{

    @XmlElement(name = "Mod_pertes_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected float modPertesH;
    @XmlElement(name = "Theta_in_prim_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected float thetaInPrimH;
    @XmlElement(name = "Theta_out_prim_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected float thetaOutPrimH;
    @XmlElement(name = "Q_totale_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected float qTotaleH;
    @XmlElement(name = "Mod_circ_h")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected float modCircH;

    /**
     * Gets the value of the modPertesH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public float getModPertesH() {
        return modPertesH;
    }

    /**
     * Sets the value of the modPertesH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setModPertesH(float value) {
        this.modPertesH = value;
    }

    /**
     * Gets the value of the thetaInPrimH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public float getThetaInPrimH() {
        return thetaInPrimH;
    }

    /**
     * Sets the value of the thetaInPrimH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaInPrimH(float value) {
        this.thetaInPrimH = value;
    }

    /**
     * Gets the value of the thetaOutPrimH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public float getThetaOutPrimH() {
        return thetaOutPrimH;
    }

    /**
     * Sets the value of the thetaOutPrimH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setThetaOutPrimH(float value) {
        this.thetaOutPrimH = value;
    }

    /**
     * Gets the value of the qTotaleH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public float getQTotaleH() {
        return qTotaleH;
    }

    /**
     * Sets the value of the qTotaleH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setQTotaleH(float value) {
        this.qTotaleH = value;
    }

    /**
     * Gets the value of the modCircH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public float getModCircH() {
        return modCircH;
    }

    /**
     * Sets the value of the modCircH property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setModCircH(float value) {
        this.modCircH = value;
    }

}
