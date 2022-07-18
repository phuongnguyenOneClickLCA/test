
package com.bionova.optimi.xml.fecRSEnv;

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
    "sortieGenerateurCollection",
    "sortieProductionStockageCollection"
})
public class RTDataSortieGeneration
    extends RTDataSortieBase
{

    @XmlElement(name = "O_Idsousdim_Court_Ch")
    protected boolean oIdsousdimCourtCh;
    @XmlElement(name = "O_Idsousdim_Long_Ch")
    protected boolean oIdsousdimLongCh;
    @XmlElement(name = "O_Idsousdim_Court_Fr")
    protected boolean oIdsousdimCourtFr;
    @XmlElement(name = "O_Idsousdim_Long_Fr")
    protected boolean oIdsousdimLongFr;
    @XmlElement(name = "Sortie_Generateur_Collection")
    protected ArrayOfRTDataSortieGenerateur sortieGenerateurCollection;
    @XmlElement(name = "Sortie_Production_Stockage_Collection")
    protected ArrayOfRTDataSortieProductionStockage sortieProductionStockageCollection;

    /**
     * Gets the value of the oIdsousdimCourtCh property.
     * 
     */
    public boolean isOIdsousdimCourtCh() {
        return oIdsousdimCourtCh;
    }

    /**
     * Sets the value of the oIdsousdimCourtCh property.
     * 
     */
    public void setOIdsousdimCourtCh(boolean value) {
        this.oIdsousdimCourtCh = value;
    }

    /**
     * Gets the value of the oIdsousdimLongCh property.
     * 
     */
    public boolean isOIdsousdimLongCh() {
        return oIdsousdimLongCh;
    }

    /**
     * Sets the value of the oIdsousdimLongCh property.
     * 
     */
    public void setOIdsousdimLongCh(boolean value) {
        this.oIdsousdimLongCh = value;
    }

    /**
     * Gets the value of the oIdsousdimCourtFr property.
     * 
     */
    public boolean isOIdsousdimCourtFr() {
        return oIdsousdimCourtFr;
    }

    /**
     * Sets the value of the oIdsousdimCourtFr property.
     * 
     */
    public void setOIdsousdimCourtFr(boolean value) {
        this.oIdsousdimCourtFr = value;
    }

    /**
     * Gets the value of the oIdsousdimLongFr property.
     * 
     */
    public boolean isOIdsousdimLongFr() {
        return oIdsousdimLongFr;
    }

    /**
     * Sets the value of the oIdsousdimLongFr property.
     * 
     */
    public void setOIdsousdimLongFr(boolean value) {
        this.oIdsousdimLongFr = value;
    }

    /**
     * Gets the value of the sortieGenerateurCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataSortieGenerateur }
     *     
     */
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
    public void setSortieProductionStockageCollection(ArrayOfRTDataSortieProductionStockage value) {
        this.sortieProductionStockageCollection = value;
    }

}
