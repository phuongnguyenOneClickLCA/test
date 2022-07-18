
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Sensibilite_Groupe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Sensibilite_Groupe">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="Sw_Baies_Aug_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Sw_Baies_Dim_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Transm_Lum_Global_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeurs_U_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Valeurs_Usp_Uap_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Inertie_Tres_Legere_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Inertie_Tres_Lourde_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Puiss_Eclair_Locaux_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Sensibilite_Groupe", propOrder = {
    "swBaiesAugTic",
    "swBaiesDimTic",
    "transmLumGlobalTic",
    "valeursUTic",
    "valeursUspUapTic",
    "inertieTresLegereTic",
    "inertieTresLourdeTic",
    "puissEclairLocauxTic"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieSensibiliteGroupe
    extends RTDataSortieBase
{

    @XmlElement(name = "Sw_Baies_Aug_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double swBaiesAugTic;
    @XmlElement(name = "Sw_Baies_Dim_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double swBaiesDimTic;
    @XmlElement(name = "Transm_Lum_Global_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double transmLumGlobalTic;
    @XmlElement(name = "Valeurs_U_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeursUTic;
    @XmlElement(name = "Valeurs_Usp_Uap_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double valeursUspUapTic;
    @XmlElement(name = "Inertie_Tres_Legere_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double inertieTresLegereTic;
    @XmlElement(name = "Inertie_Tres_Lourde_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double inertieTresLourdeTic;
    @XmlElement(name = "Puiss_Eclair_Locaux_Tic")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected double puissEclairLocauxTic;

    /**
     * Gets the value of the swBaiesAugTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSwBaiesAugTic() {
        return swBaiesAugTic;
    }

    /**
     * Sets the value of the swBaiesAugTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSwBaiesAugTic(double value) {
        this.swBaiesAugTic = value;
    }

    /**
     * Gets the value of the swBaiesDimTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getSwBaiesDimTic() {
        return swBaiesDimTic;
    }

    /**
     * Sets the value of the swBaiesDimTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSwBaiesDimTic(double value) {
        this.swBaiesDimTic = value;
    }

    /**
     * Gets the value of the transmLumGlobalTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getTransmLumGlobalTic() {
        return transmLumGlobalTic;
    }

    /**
     * Sets the value of the transmLumGlobalTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTransmLumGlobalTic(double value) {
        this.transmLumGlobalTic = value;
    }

    /**
     * Gets the value of the valeursUTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeursUTic() {
        return valeursUTic;
    }

    /**
     * Sets the value of the valeursUTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeursUTic(double value) {
        this.valeursUTic = value;
    }

    /**
     * Gets the value of the valeursUspUapTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getValeursUspUapTic() {
        return valeursUspUapTic;
    }

    /**
     * Sets the value of the valeursUspUapTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValeursUspUapTic(double value) {
        this.valeursUspUapTic = value;
    }

    /**
     * Gets the value of the inertieTresLegereTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getInertieTresLegereTic() {
        return inertieTresLegereTic;
    }

    /**
     * Sets the value of the inertieTresLegereTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertieTresLegereTic(double value) {
        this.inertieTresLegereTic = value;
    }

    /**
     * Gets the value of the inertieTresLourdeTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getInertieTresLourdeTic() {
        return inertieTresLourdeTic;
    }

    /**
     * Sets the value of the inertieTresLourdeTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInertieTresLourdeTic(double value) {
        this.inertieTresLourdeTic = value;
    }

    /**
     * Gets the value of the puissEclairLocauxTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public double getPuissEclairLocauxTic() {
        return puissEclairLocauxTic;
    }

    /**
     * Sets the value of the puissEclairLocauxTic property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPuissEclairLocauxTic(double value) {
        this.puissEclairLocauxTic = value;
    }

}
