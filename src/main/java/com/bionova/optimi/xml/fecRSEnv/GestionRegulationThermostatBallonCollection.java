
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}T5_Uniclima_Gestion_Appoint_Nuit" minOccurs="0"/>
 *         &lt;element ref="{}T5_Terreal_Gestion_PAC_Jour" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour"
})
@XmlRootElement(name = "Gestion_Regulation_Thermostat_Ballon_Collection")
public class GestionRegulationThermostatBallonCollection {

    @XmlElements({
        @XmlElement(name = "T5_Uniclima_Gestion_Appoint_Nuit", type = T5UniclimaGestionAppointNuitData.class, nillable = true),
        @XmlElement(name = "T5_Terreal_Gestion_PAC_Jour", type = T5TerrealGestionPACJourData.class)
    })
    protected List<DataObjectBase> t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour;

    /**
     * Gets the value of the t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getT5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link T5UniclimaGestionAppointNuitData }
     * {@link T5TerrealGestionPACJourData }
     * 
     * 
     */
    public List<DataObjectBase> getT5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour() {
        if (t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour == null) {
            t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour = new ArrayList<DataObjectBase>();
        }
        return this.t5UniclimaGestionAppointNuitOrT5TerrealGestionPACJour;
    }

}
