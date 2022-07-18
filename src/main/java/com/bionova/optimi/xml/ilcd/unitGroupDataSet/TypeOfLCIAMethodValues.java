
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeOfLCIAMethodValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeOfLCIAMethodValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Inventory indicator"/>
 *     &lt;enumeration value="Mid-point indicator"/>
 *     &lt;enumeration value="Damage indicator"/>
 *     &lt;enumeration value="Area of Protection damage indicator"/>
 *     &lt;enumeration value="Combined single-point indicator"/>
 *     &lt;enumeration value="LCIA methodology documentation"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeOfLCIAMethodValues", namespace = "http://lca.jrc.it/ILCD/Common")
@XmlEnum
public enum TypeOfLCIAMethodValues {


    /**
     * Method for an inventory indicator, i.e. without impact assessment method.
     * 
     */
    @XmlEnumValue("Inventory indicator")
    INVENTORY_INDICATOR("Inventory indicator"),

    /**
     * Method for a mid-point indicator at a point of the impact chain between the inventory and the damage.
     * 
     */
    @XmlEnumValue("Mid-point indicator")
    MID_POINT_INDICATOR("Mid-point indicator"),

    /**
     * Method for a damage indicator, representing the damage to a part of a Area of Protection or by specific mechanisms, only.
     * 
     */
    @XmlEnumValue("Damage indicator")
    DAMAGE_INDICATOR("Damage indicator"),

    /**
     * Method for a damage indicator, representing the damage to one complete Area of Protection.
     * 
     */
    @XmlEnumValue("Area of Protection damage indicator")
    AREA_OF_PROTECTION_DAMAGE_INDICATOR("Area of Protection damage indicator"),

    /**
     * Method for a damage indicator, representing the damage to one complete Area of Protection.
     * 
     */
    @XmlEnumValue("Combined single-point indicator")
    COMBINED_SINGLE_POINT_INDICATOR("Combined single-point indicator"),

    /**
     * Dataset that refers to actual LCIA method datasets, providing common metainformation.
     * 
     */
    @XmlEnumValue("LCIA methodology documentation")
    LCIA_METHODOLOGY_DOCUMENTATION("LCIA methodology documentation");
    private final String value;

    TypeOfLCIAMethodValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeOfLCIAMethodValues fromValue(String v) {
        for (TypeOfLCIAMethodValues c: TypeOfLCIAMethodValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
