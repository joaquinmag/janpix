
package com.janpix.webclient.regdoc;

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
 *     &lt;enumeration value="SuccededInsertion"/>
 *     &lt;enumeration value="SuccededRegistration"/>
 *     &lt;enumeration value="SuccededQuery"/>
 *     &lt;enumeration value="SuccededRetrieve"/>
 *     &lt;enumeration value="MetadataError"/>
 *     &lt;enumeration value="ValidationError"/>
 *     &lt;enumeration value="InternalError"/>
 *     &lt;enumeration value="RegistrationError"/>
 *     &lt;enumeration value="RetrieveError"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeCode")
@XmlEnum
public enum TypeCode {

    @XmlEnumValue("SuccededInsertion")
    SUCCEDED_INSERTION("SuccededInsertion"),
    @XmlEnumValue("SuccededRegistration")
    SUCCEDED_REGISTRATION("SuccededRegistration"),
    @XmlEnumValue("SuccededQuery")
    SUCCEDED_QUERY("SuccededQuery"),
    @XmlEnumValue("SuccededRetrieve")
    SUCCEDED_RETRIEVE("SuccededRetrieve"),
    @XmlEnumValue("MetadataError")
    METADATA_ERROR("MetadataError"),
    @XmlEnumValue("ValidationError")
    VALIDATION_ERROR("ValidationError"),
    @XmlEnumValue("InternalError")
    INTERNAL_ERROR("InternalError"),
    @XmlEnumValue("RegistrationError")
    REGISTRATION_ERROR("RegistrationError"),
    @XmlEnumValue("RetrieveError")
    RETRIEVE_ERROR("RetrieveError");
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
