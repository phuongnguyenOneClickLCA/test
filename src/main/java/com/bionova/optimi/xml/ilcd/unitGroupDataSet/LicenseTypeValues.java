
package com.bionova.optimi.xml.ilcd.unitGroupDataSet;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LicenseTypeValues.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LicenseTypeValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Free of charge for all users and uses"/>
 *     &lt;enumeration value="Free of charge for some user types or use types"/>
 *     &lt;enumeration value="Free of charge for members only"/>
 *     &lt;enumeration value="License fee"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LicenseTypeValues", namespace = "http://lca.jrc.it/ILCD/Common")
@XmlEnum
public enum LicenseTypeValues {


    /**
     * This data set can be freely accessed and used by all user types and for all uses, including for commercial purposes
     * 
     */
    @XmlEnumValue("Free of charge for all users and uses")
    FREE_OF_CHARGE_FOR_ALL_USERS_AND_USES("Free of charge for all users and uses"),

    /**
     * This data set can be accessed free of charge for certain user types, such as academic institutions, students, public
     *             administration/government, etc., or for certain types of uses, e.g. not-for-profit. Details and license conditions are to be obtained from the "Data set
     *             owner" or electronically via the "Permanent URI", if implemented by data owner. Also see "Access and use restrictions".
     * 
     */
    @XmlEnumValue("Free of charge for some user types or use types")
    FREE_OF_CHARGE_FOR_SOME_USER_TYPES_OR_USE_TYPES("Free of charge for some user types or use types"),

    /**
     * Data set is accessible free of charge only for members. Membership itself must be for free, while not all user types may be able to become
     *             member. Membership conditions are to be obtained from the "Data set owner" or electronically via the "Permanent URI", if implemented by data owner. Also see
     *             "Access and use restrictions".
     * 
     */
    @XmlEnumValue("Free of charge for members only")
    FREE_OF_CHARGE_FOR_MEMBERS_ONLY("Free of charge for members only"),

    /**
     * Data set is accessible for a license fee. This can be a fee per data set, for a group of data sets, a whole database, or for obtaining a
     *             membership to get access to the data. Details and license conditions are to be obtained from the "Data set owner" or electronically via the "Permanent URI",
     *             if implemented by data owner. Also see "Access and use restrictions".
     * 
     */
    @XmlEnumValue("License fee")
    LICENSE_FEE("License fee"),

    /**
     * Another license type applies. Details are given in "Access and use restrictions".
     * 
     */
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    LicenseTypeValues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LicenseTypeValues fromValue(String v) {
        for (LicenseTypeValues c: LicenseTypeValues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
