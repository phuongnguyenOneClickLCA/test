
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Production_Stockage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Production_Stockage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="Production_Stockage" type="{}RT_Data_Production_Stockage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Production_Stockage", propOrder = {
    "productionStockage"
})
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.RTDataGeneration.ProductionStockageECSCollection.class
})
public class ArrayOfRTDataProductionStockage {

    @XmlElement(name = "Production_Stockage", nillable = true)
    protected List<RTDataProductionStockage> productionStockage;

    /**
     * Gets the value of the productionStockage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productionStockage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductionStockage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataProductionStockage }
     * 
     * 
     */
    public List<RTDataProductionStockage> getProductionStockage() {
        if (productionStockage == null) {
            productionStockage = new ArrayList<RTDataProductionStockage>();
        }
        return this.productionStockage;
    }

}
