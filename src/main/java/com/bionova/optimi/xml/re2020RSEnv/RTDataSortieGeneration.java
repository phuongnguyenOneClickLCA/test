
package com.bionova.optimi.xml.re2020RSEnv;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Generation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Generation">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Sortie_Base">
 *       &lt;sequence>
 *         &lt;element name="O_Idsousdim_Court_Ch" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Long_Ch" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Court_Fr" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Long_Fr" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Court_Ch_BE" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Long_Ch_BE" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Court_Fr_BE" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="O_Idsousdim_Long_Fr_BE" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Sortie_Generateur_Collection" type="{}ArrayOfRT_Data_Sortie_Generateur" minOccurs="0"/>
 *         &lt;element name="Sortie_Production_Stockage_Collection" type="{}ArrayOfRT_Data_Sortie_Production_Stockage" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Generation", propOrder = {
    "oIdsousdimCourtCh",
    "oIdsousdimLongCh",
    "oIdsousdimCourtFr",
    "oIdsousdimLongFr",
    "oIdsousdimCourtChBE",
    "oIdsousdimLongChBE",
    "oIdsousdimCourtFrBE",
    "oIdsousdimLongFrBE",
    "sortieGenerateurCollection",
    "sortieProductionStockageCollection"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class RTDataSortieGeneration
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Idsousdim_Court_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimCourtCh;
    @XmlElement(name = "O_Idsousdim_Long_Ch")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimLongCh;
    @XmlElement(name = "O_Idsousdim_Court_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimCourtFr;
    @XmlElement(name = "O_Idsousdim_Long_Fr")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimLongFr;
    @XmlElement(name = "O_Idsousdim_Court_Ch_BE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimCourtChBE;
    @XmlElement(name = "O_Idsousdim_Long_Ch_BE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimLongChBE;
    @XmlElement(name = "O_Idsousdim_Court_Fr_BE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimCourtFrBE;
    @XmlElement(name = "O_Idsousdim_Long_Fr_BE")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected boolean oIdsousdimLongFrBE;
    @XmlElement(name = "Sortie_Generateur_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieGenerateur sortieGenerateurCollection;
    @XmlElement(name = "Sortie_Production_Stockage_Collection")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ArrayOfRTDataSortieProductionStockage sortieProductionStockageCollection;

    /**
     * Gets the value of the oIdsousdimCourtCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimCourtCh() {
        return oIdsousdimCourtCh;
    }

    /**
     * Sets the value of the oIdsousdimCourtCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimCourtCh(boolean value) {
        this.oIdsousdimCourtCh = value;
    }

    /**
     * Gets the value of the oIdsousdimLongCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimLongCh() {
        return oIdsousdimLongCh;
    }

    /**
     * Sets the value of the oIdsousdimLongCh property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimLongCh(boolean value) {
        this.oIdsousdimLongCh = value;
    }

    /**
     * Gets the value of the oIdsousdimCourtFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimCourtFr() {
        return oIdsousdimCourtFr;
    }

    /**
     * Sets the value of the oIdsousdimCourtFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimCourtFr(boolean value) {
        this.oIdsousdimCourtFr = value;
    }

    /**
     * Gets the value of the oIdsousdimLongFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimLongFr() {
        return oIdsousdimLongFr;
    }

    /**
     * Sets the value of the oIdsousdimLongFr property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimLongFr(boolean value) {
        this.oIdsousdimLongFr = value;
    }

    /**
     * Gets the value of the oIdsousdimCourtChBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimCourtChBE() {
        return oIdsousdimCourtChBE;
    }

    /**
     * Sets the value of the oIdsousdimCourtChBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimCourtChBE(boolean value) {
        this.oIdsousdimCourtChBE = value;
    }

    /**
     * Gets the value of the oIdsousdimLongChBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimLongChBE() {
        return oIdsousdimLongChBE;
    }

    /**
     * Sets the value of the oIdsousdimLongChBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimLongChBE(boolean value) {
        this.oIdsousdimLongChBE = value;
    }

    /**
     * Gets the value of the oIdsousdimCourtFrBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimCourtFrBE() {
        return oIdsousdimCourtFrBE;
    }

    /**
     * Sets the value of the oIdsousdimCourtFrBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimCourtFrBE(boolean value) {
        this.oIdsousdimCourtFrBE = value;
    }

    /**
     * Gets the value of the oIdsousdimLongFrBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public boolean isOIdsousdimLongFrBE() {
        return oIdsousdimLongFrBE;
    }

    /**
     * Sets the value of the oIdsousdimLongFrBE property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOIdsousdimLongFrBE(boolean value) {
        this.oIdsousdimLongFrBE = value;
    }

    /**
     * Gets the value of the sortieGenerateurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGenerateur }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieGenerateur getSortieGenerateurCollection() {
        return sortieGenerateurCollection;
    }

    /**
     * Sets the value of the sortieGenerateurCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieGenerateur }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieGenerateurCollection(ArrayOfRTDataSortieGenerateur value) {
        this.sortieGenerateurCollection = value;
    }

    /**
     * Gets the value of the sortieProductionStockageCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieProductionStockage }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ArrayOfRTDataSortieProductionStockage getSortieProductionStockageCollection() {
        return sortieProductionStockageCollection;
    }

    /**
     * Sets the value of the sortieProductionStockageCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataSortieProductionStockage }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2022-05-12T03:35:35+03:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSortieProductionStockageCollection(ArrayOfRTDataSortieProductionStockage value) {
        this.sortieProductionStockageCollection = value;
    }

}
