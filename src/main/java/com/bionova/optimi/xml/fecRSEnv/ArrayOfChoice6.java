
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfChoice6 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChoice6">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Masque_Vert_Gauche" type="{}RT_Data_Masque_Vert_Gauche"/>
 *         &lt;element name="Masque_Vert_Droite" type="{}RT_Data_Masque_Vert_Droite"/>
 *         &lt;element name="Masque_Lointain_Vertical" type="{}RT_Data_Masque_Lointain_Vertical"/>
 *         &lt;element name="Masque_Lointain_Azimutal" type="{}RT_Data_Masque_Lointain_Azimutal"/>
 *         &lt;element name="Masque_Horizontal" type="{}RT_Data_Masque_Horizontal"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChoice6", propOrder = {
    "masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical"
})
public class ArrayOfChoice6 {

    @XmlElements({
        @XmlElement(name = "Masque_Vert_Gauche", type = RTDataMasqueVertGauche.class, nillable = true),
        @XmlElement(name = "Masque_Vert_Droite", type = RTDataMasqueVertDroite.class, nillable = true),
        @XmlElement(name = "Masque_Lointain_Vertical", type = RTDataMasqueLointainVertical.class, nillable = true),
        @XmlElement(name = "Masque_Lointain_Azimutal", type = RTDataMasqueLointainAzimutal.class, nillable = true),
        @XmlElement(name = "Masque_Horizontal", type = RTDataMasqueHorizontal.class, nillable = true)
    })
    protected List<Object> masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical;

    /**
     * Gets the value of the masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMasqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RTDataMasqueVertGauche }
     * {@link RTDataMasqueVertDroite }
     * {@link RTDataMasqueLointainVertical }
     * {@link RTDataMasqueLointainAzimutal }
     * {@link RTDataMasqueHorizontal }
     * 
     * 
     */
    public List<Object> getMasqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical() {
        if (masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical == null) {
            masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical = new ArrayList<Object>();
        }
        return this.masqueVertGaucheOrMasqueVertDroiteOrMasqueLointainVertical;
    }

}
