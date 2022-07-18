
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Groupe_E complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Groupe_E">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_Shon_RT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SHAB" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_SURT" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Tic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Tic_Ref" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="O_Type_Groupe" type="{}E_Type_Groupe"/>
 *         &lt;element name="O_Tic_h" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *         &lt;element name="O_Tic_ref_h" type="{}ArrayOfRT_Data_Sortie_Horaire" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Groupe_E", propOrder = {
    "oShonRT",
    "oshab",
    "osurt",
    "oTic",
    "oTicRef",
    "oTypeGroupe",
    "oTicH",
    "oTicRefH"
})
public class RTDataSortieGroupeE
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Shon_RT")
    protected double oShonRT;
    @XmlElement(name = "O_SHAB")
    protected double oshab;
    @XmlElement(name = "O_SURT")
    protected double osurt;
    @XmlElement(name = "O_Tic")
    protected double oTic;
    @XmlElement(name = "O_Tic_Ref")
    protected double oTicRef;
    @XmlElement(name = "O_Type_Groupe", required = true)
    protected String oTypeGroupe;
    @XmlElement(name = "O_Tic_h")
    protected ArrayOfRTDataSortieHoraire oTicH;
    @XmlElement(name = "O_Tic_ref_h")
    protected ArrayOfRTDataSortieHoraire oTicRefH;

    /**
     * Gets the value of the oShonRT property.
     * 
     */
    public double getOShonRT() {
        return oShonRT;
    }

    /**
     * Sets the value of the oShonRT property.
     * 
     */
    public void setOShonRT(double value) {
        this.oShonRT = value;
    }

    /**
     * Gets the value of the oshab property.
     * 
     */
    public double getOSHAB() {
        return oshab;
    }

    /**
     * Sets the value of the oshab property.
     * 
     */
    public void setOSHAB(double value) {
        this.oshab = value;
    }

    /**
     * Gets the value of the osurt property.
     * 
     */
    public double getOSURT() {
        return osurt;
    }

    /**
     * Sets the value of the osurt property.
     * 
     */
    public void setOSURT(double value) {
        this.osurt = value;
    }

    /**
     * Gets the value of the oTic property.
     * 
     */
    public double getOTic() {
        return oTic;
    }

    /**
     * Sets the value of the oTic property.
     * 
     */
    public void setOTic(double value) {
        this.oTic = value;
    }

    /**
     * Gets the value of the oTicRef property.
     * 
     */
    public double getOTicRef() {
        return oTicRef;
    }

    /**
     * Sets the value of the oTicRef property.
     * 
     */
    public void setOTicRef(double value) {
        this.oTicRef = value;
    }

    /**
     * Gets the value of the oTypeGroupe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTypeGroupe() {
        return oTypeGroupe;
    }

    /**
     * Sets the value of the oTypeGroupe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTypeGroupe(String value) {
        this.oTypeGroupe = value;
    }

    /**
     * Gets the value of the oTicH property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public ArrayOfRTDataSortieHoraire getOTicH() {
        return oTicH;
    }

    /**
     * Sets the value of the oTicH property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public void setOTicH(ArrayOfRTDataSortieHoraire value) {
        this.oTicH = value;
    }

    /**
     * Gets the value of the oTicRefH property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public ArrayOfRTDataSortieHoraire getOTicRefH() {
        return oTicRefH;
    }

    /**
     * Sets the value of the oTicRefH property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieHoraire }
     *     
     */
    public void setOTicRefH(ArrayOfRTDataSortieHoraire value) {
        this.oTicRefH = value;
    }

}
