
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecommendationLevelValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RecommendationLevelValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Level I"/>
 *     &lt;enumeration value="Level II"/>
 *     &lt;enumeration value="Level III"/>
 *     &lt;enumeration value="Interim"/>
 *     &lt;enumeration value="Not recommended"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecommendationLevelValues", namespace = "http://lca.jrc.it/ILCD/Common")
@XmlEnum
public enum RecommendationLevelValues {


    /**
     * Highest recommendation level. See also field "Specific meaning of the recommendation level".
     * 
     */
    @XmlEnumValue("Level I")
    LEVEL_I("Level I"),

    /**
     * Second highest recommendation level. See also field "Specific meaning of the recommendation level".
     * 
     */
    @XmlEnumValue("Level II")
    LEVEL_II("Level II"),

    /**
     * Third highest recommendation level. See also field "Specific meaning of the recommendation level".
     * 
     */
    @XmlEnumValue("Level III")
    LEVEL_III("Level III"),

    /**
     * Level between the third highest recommendation level and "not recommended". See also field "Specific meaning of the recommendation
     *             level".
     * 
     */
    @XmlEnumValue("Interim")
    INTERIM("Interim"),

    /**
     * Not recommended for use.
     * 
     */
    @XmlEnumValue("Not recommended")
    NOT_RECOMMENDED("Not recommended");
    private final String value;

    RecommendationLevelValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RecommendationLevelValues fromValue(String v) {
        for (RecommendationLevelValues c: RecommendationLevelValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
