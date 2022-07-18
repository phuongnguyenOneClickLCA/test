
package com.bionova.optimi.xml.ilcd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LCIAMethodPrincipleValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LCIAMethodPrincipleValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Distance-to-target"/>
 *     &lt;enumeration value="Critical surface-time"/>
 *     &lt;enumeration value="Effective volumes"/>
 *     &lt;enumeration value="AoP-Damage model"/>
 *     &lt;enumeration value="Carrying capacity"/>
 *     &lt;enumeration value="Resource dissipation"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LCIAMethodPrincipleValues", namespace = "http://lca.jrc.it/ILCD/Common")
@XmlEnum
public enum LCIAMethodPrincipleValues {

    @XmlEnumValue("Distance-to-target")
    DISTANCE_TO_TARGET("Distance-to-target"),
    @XmlEnumValue("Critical surface-time")
    CRITICAL_SURFACE_TIME("Critical surface-time"),
    @XmlEnumValue("Effective volumes")
    EFFECTIVE_VOLUMES("Effective volumes"),
    @XmlEnumValue("AoP-Damage model")
    AO_P_DAMAGE_MODEL("AoP-Damage model"),
    @XmlEnumValue("Carrying capacity")
    CARRYING_CAPACITY("Carrying capacity"),
    @XmlEnumValue("Resource dissipation")
    RESOURCE_DISSIPATION("Resource dissipation"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    LCIAMethodPrincipleValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LCIAMethodPrincipleValues fromValue(String v) {
        for (LCIAMethodPrincipleValues c: LCIAMethodPrincipleValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
