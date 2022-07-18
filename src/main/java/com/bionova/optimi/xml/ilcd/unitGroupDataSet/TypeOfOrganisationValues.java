
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeOfOrganisationValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeOfOrganisationValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Private company"/>
 *     &lt;enumeration value="Governmental"/>
 *     &lt;enumeration value="Non-governmental org."/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeOfOrganisationValues", namespace = "http://lca.jrc.it/ILCD/Common")
@XmlEnum
public enum TypeOfOrganisationValues {


    /**
     * Private company
     * 
     */
    @XmlEnumValue("Private company")
    PRIVATE_COMPANY("Private company"),

    /**
     * Governmental organisation
     * 
     */
    @XmlEnumValue("Governmental")
    GOVERNMENTAL("Governmental"),

    /**
     * Non-governmental organisation
     * 
     */
    @XmlEnumValue("Non-governmental org.")
    NON_GOVERNMENTAL_ORG("Non-governmental org."),

    /**
     * Other, e.g. a project consortium or network
     * 
     */
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    TypeOfOrganisationValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeOfOrganisationValues fromValue(String v) {
        for (TypeOfOrganisationValues c: TypeOfOrganisationValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
