
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Types_Fonctionnements_Generateur_T5_sonnenkraft.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RT_Types_Fonctionnements_Generateur_T5_sonnenkraft">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Chauffage"/>
 *     &lt;enumeration value="Refroidissement"/>
 *     &lt;enumeration value="ECS"/>
 *     &lt;enumeration value="Chauffage_et_ECS"/>
 *     &lt;enumeration value="Chauffage_et_Refroidissement"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RT_Types_Fonctionnements_Generateur_T5_sonnenkraft")
@XmlEnum
public enum RTTypesFonctionnementsGenerateurT5Sonnenkraft {

    @XmlEnumValue("Chauffage")
    CHAUFFAGE("Chauffage"),
    @XmlEnumValue("Refroidissement")
    REFROIDISSEMENT("Refroidissement"),
    ECS("ECS"),
    @XmlEnumValue("Chauffage_et_ECS")
    CHAUFFAGE_ET_ECS("Chauffage_et_ECS"),
    @XmlEnumValue("Chauffage_et_Refroidissement")
    CHAUFFAGE_ET_REFROIDISSEMENT("Chauffage_et_Refroidissement");
    private final String value;

    RTTypesFonctionnementsGenerateurT5Sonnenkraft(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RTTypesFonctionnementsGenerateurT5Sonnenkraft fromValue(String v) {
        for (RTTypesFonctionnementsGenerateurT5Sonnenkraft c: RTTypesFonctionnementsGenerateurT5Sonnenkraft.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
