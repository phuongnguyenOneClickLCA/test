
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * T5_CSTB_UAT - moteur 6300
 * 
 * <p>Java class for Data_Object_Base complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Data_Object_Base">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Data_Object_Base")
@XmlSeeAlso({
    T5AFPGGeocoolingNonClimatiseEmissionData.class,
    T5UniclimaGestionAppointNuitData.class,
    T5TerrealGestionPACJourData.class,
    T5AFPGGenEchangeurGeocoolingData.class,
    T5UNICLIMAPACTSData.class,
    T5NibePACAirExtraitEauData.class,
    T5AERMECPACNRPData.class,
    T5PanasonicPACTripleServiceData.class,
    T5CSTBUATData.class,
    T5CSTBVMCDoubleFluxEchIndivData.class
})
public abstract class DataObjectBase {


}
