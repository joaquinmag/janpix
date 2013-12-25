
package com.janpix.webclient.rup;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="typeCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SuccededCreation"/>
 *     &lt;enumeration value="SuccededInsertion"/>
 *     &lt;enumeration value="SuccededUpdated"/>
 *     &lt;enumeration value="PossibleMatchingPatientsError"/>
 *     &lt;enumeration value="ShortDemographicError"/>
 *     &lt;enumeration value="IdentifierError"/>
 *     &lt;enumeration value="InternalError"/>
 *     &lt;enumeration value="DuplicatePatientError"/>
 *     &lt;enumeration value="DontExistingPatientError"/>
 *     &lt;enumeration value="SuccededQuery"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeCode")
@XmlEnum
public enum TypeCode {

    @XmlEnumValue("SuccededCreation")
    SUCCEDED_CREATION("SuccededCreation"),
    @XmlEnumValue("SuccededInsertion")
    SUCCEDED_INSERTION("SuccededInsertion"),
    @XmlEnumValue("SuccededUpdated")
    SUCCEDED_UPDATED("SuccededUpdated"),
    @XmlEnumValue("PossibleMatchingPatientsError")
    POSSIBLE_MATCHING_PATIENTS_ERROR("PossibleMatchingPatientsError"),
    @XmlEnumValue("ShortDemographicError")
    SHORT_DEMOGRAPHIC_ERROR("ShortDemographicError"),
    @XmlEnumValue("IdentifierError")
    IDENTIFIER_ERROR("IdentifierError"),
    @XmlEnumValue("InternalError")
    INTERNAL_ERROR("InternalError"),
    @XmlEnumValue("DuplicatePatientError")
    DUPLICATE_PATIENT_ERROR("DuplicatePatientError"),
    @XmlEnumValue("DontExistingPatientError")
    DONT_EXISTING_PATIENT_ERROR("DontExistingPatientError"),
    @XmlEnumValue("SuccededQuery")
    SUCCEDED_QUERY("SuccededQuery");
    private final String value;

    TypeCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeCode fromValue(String v) {
        for (TypeCode c: TypeCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
