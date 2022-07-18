
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Ventilation_Mecanique complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Ventilation_Mecanique">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Ventilation_Mecanique" type="{}RT_Data_Ventilation_Mecanique" minOccurs="0"/>
 *         &lt;element name="T5_CSTB_VMCDF_2Fonctions" type="{}T5_CSTB_VMCDF_2Fonctions_Data" minOccurs="0"/>
 *         &lt;element name="T5_CSTB_UAT" type="{}T5_CSTB_UAT_Data" minOccurs="0"/>
 *         &lt;element name="T5_CSTB_VMC_Double_Flux_Ech_Indiv" type="{}T5_CSTB_VMC_Double_Flux_Ech_Indiv_Data" minOccurs="0"/>
 *         &lt;element ref="{}T5_Engie_UAT_RA" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Ventilation_Mecanique", propOrder = {
    "ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT"
})
public class ArrayOfRTDataVentilationMecanique {

    @XmlElements({
        @XmlElement(name = "Ventilation_Mecanique", type = RTDataVentilationMecanique.class, nillable = true),
        @XmlElement(name = "T5_CSTB_VMCDF_2Fonctions", type = T5CSTBVMCDF2FonctionsData.class, nillable = true),
        @XmlElement(name = "T5_CSTB_UAT", type = T5CSTBUATData.class, nillable = true),
        @XmlElement(name = "T5_CSTB_VMC_Double_Flux_Ech_Indiv", type = T5CSTBVMCDoubleFluxEchIndivData.class, nillable = true),
        @XmlElement(name = "T5_Engie_UAT_RA", type = T5EngieUATRAData.class, nillable = true)
    })
    protected List<Object> ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT;

    /**
     * Gets the value of the ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVentilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataVentilationMecanique }
     * {@link T5CSTBVMCDF2FonctionsData }
     * {@link T5CSTBUATData }
     * {@link T5CSTBVMCDoubleFluxEchIndivData }
     * {@link T5EngieUATRAData }
     * 
     * 
     */
    public List<Object> getVentilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT() {
        if (ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT == null) {
            ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT = new ArrayList<Object>();
        }
        return this.ventilationMecaniqueOrT5CSTBVMCDF2FonctionsOrT5CSTBUAT;
    }

}
