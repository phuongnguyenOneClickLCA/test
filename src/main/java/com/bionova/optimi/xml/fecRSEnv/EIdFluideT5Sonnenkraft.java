
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for E_Id_Fluide_T5_sonnenkraft.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="E_Id_Fluide_T5_sonnenkraft">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Eau"/>
 *     &lt;enumeration value="Air"/>
 *     &lt;enumeration value="Fluide"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "E_Id_Fluide_T5_sonnenkraft")
@XmlEnum
public enum EIdFluideT5Sonnenkraft {

    @XmlEnumValue("Eau")
    EAU("Eau"),
    @XmlEnumValue("Air")
    AIR("Air"),
    @XmlEnumValue("Fluide")
    FLUIDE("Fluide");
    private final String value;

    EIdFluideT5Sonnenkraft(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EIdFluideT5Sonnenkraft fromValue(String v) {
        for (EIdFluideT5Sonnenkraft c: EIdFluideT5Sonnenkraft.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
