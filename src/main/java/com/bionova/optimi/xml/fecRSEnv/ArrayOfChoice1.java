
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfChoice1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChoice1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Source_Ballon_Appoint_Effet_Joule" type="{}RT_Data_Source_Ballon_Appoint_Effet_Joule"/>
 *         &lt;element name="Source_Ballon_Appoint_Combustion" type="{}RT_Data_Source_Ballon_Appoint_Combustion"/>
 *         &lt;element name="Source_Ballon_Appoint_Reseau_Fourniture" type="{}RT_Data_Source_Ballon_Appoint_Reseau_Fourniture"/>
 *         &lt;element ref="{}T5_CSTB_Appoint_Thermodynamique_DS"/>
 *         &lt;element ref="{}T5_CSTB_Appoint_Thermodynamique_ECS"/>
 *         &lt;element ref="{}T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_DS"/>
 *         &lt;element ref="{}T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_ECS"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChoice1", propOrder = {
    "sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture"
})
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.T5FRANCEAIRMYRIADEBallonData.SourceBallonAppointCollection.class,
    com.bionova.optimi.xml.fecRSEnv.T5CardonnelIngenierieLiMithraData.SourceBallonAppointCollection.class,
    com.bionova.optimi.xml.fecRSEnv.HeliopacData.SourceBallonAppointCollection.class
})
public class ArrayOfChoice1 {

    @XmlElements({
        @XmlElement(name = "Source_Ballon_Appoint_Effet_Joule", type = RTDataSourceBallonAppointEffetJoule.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Appoint_Combustion", type = RTDataSourceBallonAppointCombustion.class, nillable = true),
        @XmlElement(name = "Source_Ballon_Appoint_Reseau_Fourniture", type = RTDataSourceBallonAppointReseauFourniture.class, nillable = true),
        @XmlElement(name = "T5_CSTB_Appoint_Thermodynamique_DS", type = T5CSTBAppointThermodynamiqueDSData.class, nillable = true),
        @XmlElement(name = "T5_CSTB_Appoint_Thermodynamique_ECS", type = T5CSTBAppointThermodynamiqueECSData.class, nillable = true),
        @XmlElement(name = "T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_DS", type = T5FRANCEAIRMYRIADEAppointThermodynamiqueGazDSData.class, nillable = true),
        @XmlElement(name = "T5_FRANCEAIR_MYRIADE_Appoint_Thermodynamique_gaz_ECS", type = T5FRANCEAIRMYRIADEAppointThermodynamiqueGazECSData.class, nillable = true)
    })
    protected List<Object> sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture;

    /**
     * Gets the value of the sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataSourceBallonAppointEffetJoule }
     * {@link RTDataSourceBallonAppointCombustion }
     * {@link RTDataSourceBallonAppointReseauFourniture }
     * {@link T5CSTBAppointThermodynamiqueDSData }
     * {@link T5CSTBAppointThermodynamiqueECSData }
     * {@link T5FRANCEAIRMYRIADEAppointThermodynamiqueGazDSData }
     * {@link T5FRANCEAIRMYRIADEAppointThermodynamiqueGazECSData }
     * 
     * 
     */
    public List<Object> getSourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture() {
        if (sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture == null) {
            sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture = new ArrayList<Object>();
        }
        return this.sourceBallonAppointEffetJouleOrSourceBallonAppointCombustionOrSourceBallonAppointReseauFourniture;
    }

}
