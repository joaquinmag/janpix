
package com.janpix.webclient.rup;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.janpix.webclient.rup package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AckMessage_QNAME = new QName("jpx:ar.com.janpix", "ackMessage");
    private final static QName _AckQueryPatientMessage_QNAME = new QName("jpx:ar.com.janpix", "ackQueryPatientMessage");
    private final static QName _AddPatientRequestMessage_QNAME = new QName("jpx:ar.com.janpix", "addPatientRequestMessage");
    private final static QName _SetPixManagerService_QNAME = new QName("http://pixmanager.rup.janpix.com/", "setPixManagerService");
    private final static QName _GetAllPossibleMatchedPatients_QNAME = new QName("http://pixmanager.rup.janpix.com/", "GetAllPossibleMatchedPatients");
    private final static QName _GetIdentifiersPatientResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "GetIdentifiersPatientResponse");
    private final static QName _CityDTO_QNAME = new QName("jpx:ar.com.janpix", "cityDTO");
    private final static QName _AddNewPatientWithoutValidation_QNAME = new QName("http://pixmanager.rup.janpix.com/", "AddNewPatientWithoutValidation");
    private final static QName _AddNewPatient_QNAME = new QName("http://pixmanager.rup.janpix.com/", "AddNewPatient");
    private final static QName _AddNewPatientResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "AddNewPatientResponse");
    private final static QName _PhoneNumberDTO_QNAME = new QName("jpx:ar.com.janpix", "phoneNumberDTO");
    private final static QName _AssigningAuthorityDTO_QNAME = new QName("jpx:ar.com.janpix", "assigningAuthorityDTO");
    private final static QName _PersonNameDTO_QNAME = new QName("jpx:ar.com.janpix", "personNameDTO");
    private final static QName _GetAllPossibleMatchedPatientsResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "GetAllPossibleMatchedPatientsResponse");
    private final static QName _IdentifierDTO_QNAME = new QName("jpx:ar.com.janpix", "identifierDTO");
    private final static QName _UpdatePatientRequestMessage_QNAME = new QName("jpx:ar.com.janpix", "updatePatientRequestMessage");
    private final static QName _ValidatePatientRequestMessage_QNAME = new QName("jpx:ar.com.janpix", "validatePatientRequestMessage");
    private final static QName _GetIdentifiersRequestMessage_QNAME = new QName("jpx:ar.com.janpix", "getIdentifiersRequestMessage");
    private final static QName _GetIdentifiersPatient_QNAME = new QName("http://pixmanager.rup.janpix.com/", "GetIdentifiersPatient");
    private final static QName _UpdatePatientResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "UpdatePatientResponse");
    private final static QName _ExtendedDateDTO_QNAME = new QName("jpx:ar.com.janpix", "extendedDateDTO");
    private final static QName _GetPixManagerServiceResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "getPixManagerServiceResponse");
    private final static QName _PersonDTO_QNAME = new QName("jpx:ar.com.janpix", "personDTO");
    private final static QName _ValidatePatientResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "ValidatePatientResponse");
    private final static QName _SetSecurityService_QNAME = new QName("http://pixmanager.rup.janpix.com/", "setSecurityService");
    private final static QName _TypeCode_QNAME = new QName("jpx:ar.com.janpix", "typeCode");
    private final static QName _UpdatePatient_QNAME = new QName("http://pixmanager.rup.janpix.com/", "UpdatePatient");
    private final static QName _AddNewPatientWithoutValidationResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "AddNewPatientWithoutValidationResponse");
    private final static QName _ValidatePatient_QNAME = new QName("http://pixmanager.rup.janpix.com/", "ValidatePatient");
    private final static QName _GetAllPossibleMatchedPatientsRequestMessage_QNAME = new QName("jpx:ar.com.janpix", "getAllPossibleMatchedPatientsRequestMessage");
    private final static QName _GetSecurityServiceResponse_QNAME = new QName("http://pixmanager.rup.janpix.com/", "getSecurityServiceResponse");
    private final static QName _AddressDTO_QNAME = new QName("jpx:ar.com.janpix", "addressDTO");
    private final static QName _PatientDTO_QNAME = new QName("jpx:ar.com.janpix", "patientDTO");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.janpix.webclient.rup
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PersonNameDTO }
     * 
     */
    public PersonNameDTO createPersonNameDTO() {
        return new PersonNameDTO();
    }

    /**
     * Create an instance of {@link PatientDTO }
     * 
     */
    public PatientDTO createPatientDTO() {
        return new PatientDTO();
    }

    /**
     * Create an instance of {@link GetIdentifiersRequestMessage.OthersDomain }
     * 
     */
    public GetIdentifiersRequestMessage.OthersDomain createGetIdentifiersRequestMessageOthersDomain() {
        return new GetIdentifiersRequestMessage.OthersDomain();
    }

    /**
     * Create an instance of {@link AckQueryPatientMessage }
     * 
     */
    public AckQueryPatientMessage createAckQueryPatientMessage() {
        return new AckQueryPatientMessage();
    }

    /**
     * Create an instance of {@link AssigningAuthorityDTO }
     * 
     */
    public AssigningAuthorityDTO createAssigningAuthorityDTO() {
        return new AssigningAuthorityDTO();
    }

    /**
     * Create an instance of {@link AddressDTO }
     * 
     */
    public AddressDTO createAddressDTO() {
        return new AddressDTO();
    }

    /**
     * Create an instance of {@link ExtendedDateDTO }
     * 
     */
    public ExtendedDateDTO createExtendedDateDTO() {
        return new ExtendedDateDTO();
    }

    /**
     * Create an instance of {@link GetIdentifiersRequestMessage }
     * 
     */
    public GetIdentifiersRequestMessage createGetIdentifiersRequestMessage() {
        return new GetIdentifiersRequestMessage();
    }

    /**
     * Create an instance of {@link AckMessage }
     * 
     */
    public AckMessage createAckMessage() {
        return new AckMessage();
    }

    /**
     * Create an instance of {@link PersonDTO.Identifiers }
     * 
     */
    public PersonDTO.Identifiers createPersonDTOIdentifiers() {
        return new PersonDTO.Identifiers();
    }

    /**
     * Create an instance of {@link PersonDTO.PhoneNumbers }
     * 
     */
    public PersonDTO.PhoneNumbers createPersonDTOPhoneNumbers() {
        return new PersonDTO.PhoneNumbers();
    }

    /**
     * Create an instance of {@link UpdatePatientRequestMessage }
     * 
     */
    public UpdatePatientRequestMessage createUpdatePatientRequestMessage() {
        return new UpdatePatientRequestMessage();
    }

    /**
     * Create an instance of {@link IdentifierDTO }
     * 
     */
    public IdentifierDTO createIdentifierDTO() {
        return new IdentifierDTO();
    }

    /**
     * Create an instance of {@link ValidatePatientRequestMessage }
     * 
     */
    public ValidatePatientRequestMessage createValidatePatientRequestMessage() {
        return new ValidatePatientRequestMessage();
    }

    /**
     * Create an instance of {@link PersonDTO.Addresses }
     * 
     */
    public PersonDTO.Addresses createPersonDTOAddresses() {
        return new PersonDTO.Addresses();
    }

    /**
     * Create an instance of {@link AddPatientRequestMessage }
     * 
     */
    public AddPatientRequestMessage createAddPatientRequestMessage() {
        return new AddPatientRequestMessage();
    }

    /**
     * Create an instance of {@link CityDTO }
     * 
     */
    public CityDTO createCityDTO() {
        return new CityDTO();
    }

    /**
     * Create an instance of {@link PersonDTO }
     * 
     */
    public PersonDTO createPersonDTO() {
        return new PersonDTO();
    }

    /**
     * Create an instance of {@link GetAllPossibleMatchedPatientsRequestMessage }
     * 
     */
    public GetAllPossibleMatchedPatientsRequestMessage createGetAllPossibleMatchedPatientsRequestMessage() {
        return new GetAllPossibleMatchedPatientsRequestMessage();
    }

    /**
     * Create an instance of {@link AckQueryPatientMessage.Patients }
     * 
     */
    public AckQueryPatientMessage.Patients createAckQueryPatientMessagePatients() {
        return new AckQueryPatientMessage.Patients();
    }

    /**
     * Create an instance of {@link PhoneNumberDTO }
     * 
     */
    public PhoneNumberDTO createPhoneNumberDTO() {
        return new PhoneNumberDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "ackMessage")
    public JAXBElement<AckMessage> createAckMessage(AckMessage value) {
        return new JAXBElement<AckMessage>(_AckMessage_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckQueryPatientMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "ackQueryPatientMessage")
    public JAXBElement<AckQueryPatientMessage> createAckQueryPatientMessage(AckQueryPatientMessage value) {
        return new JAXBElement<AckQueryPatientMessage>(_AckQueryPatientMessage_QNAME, AckQueryPatientMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "addPatientRequestMessage")
    public JAXBElement<AddPatientRequestMessage> createAddPatientRequestMessage(AddPatientRequestMessage value) {
        return new JAXBElement<AddPatientRequestMessage>(_AddPatientRequestMessage_QNAME, AddPatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "setPixManagerService")
    public JAXBElement<Object> createSetPixManagerService(Object value) {
        return new JAXBElement<Object>(_SetPixManagerService_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPossibleMatchedPatientsRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "GetAllPossibleMatchedPatients")
    public JAXBElement<GetAllPossibleMatchedPatientsRequestMessage> createGetAllPossibleMatchedPatients(GetAllPossibleMatchedPatientsRequestMessage value) {
        return new JAXBElement<GetAllPossibleMatchedPatientsRequestMessage>(_GetAllPossibleMatchedPatients_QNAME, GetAllPossibleMatchedPatientsRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "GetIdentifiersPatientResponse")
    public JAXBElement<AckMessage> createGetIdentifiersPatientResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_GetIdentifiersPatientResponse_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CityDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "cityDTO")
    public JAXBElement<CityDTO> createCityDTO(CityDTO value) {
        return new JAXBElement<CityDTO>(_CityDTO_QNAME, CityDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "AddNewPatientWithoutValidation")
    public JAXBElement<AddPatientRequestMessage> createAddNewPatientWithoutValidation(AddPatientRequestMessage value) {
        return new JAXBElement<AddPatientRequestMessage>(_AddNewPatientWithoutValidation_QNAME, AddPatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "AddNewPatient")
    public JAXBElement<AddPatientRequestMessage> createAddNewPatient(AddPatientRequestMessage value) {
        return new JAXBElement<AddPatientRequestMessage>(_AddNewPatient_QNAME, AddPatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "AddNewPatientResponse")
    public JAXBElement<AckMessage> createAddNewPatientResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_AddNewPatientResponse_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneNumberDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "phoneNumberDTO")
    public JAXBElement<PhoneNumberDTO> createPhoneNumberDTO(PhoneNumberDTO value) {
        return new JAXBElement<PhoneNumberDTO>(_PhoneNumberDTO_QNAME, PhoneNumberDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssigningAuthorityDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "assigningAuthorityDTO")
    public JAXBElement<AssigningAuthorityDTO> createAssigningAuthorityDTO(AssigningAuthorityDTO value) {
        return new JAXBElement<AssigningAuthorityDTO>(_AssigningAuthorityDTO_QNAME, AssigningAuthorityDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonNameDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "personNameDTO")
    public JAXBElement<PersonNameDTO> createPersonNameDTO(PersonNameDTO value) {
        return new JAXBElement<PersonNameDTO>(_PersonNameDTO_QNAME, PersonNameDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckQueryPatientMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "GetAllPossibleMatchedPatientsResponse")
    public JAXBElement<AckQueryPatientMessage> createGetAllPossibleMatchedPatientsResponse(AckQueryPatientMessage value) {
        return new JAXBElement<AckQueryPatientMessage>(_GetAllPossibleMatchedPatientsResponse_QNAME, AckQueryPatientMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "identifierDTO")
    public JAXBElement<IdentifierDTO> createIdentifierDTO(IdentifierDTO value) {
        return new JAXBElement<IdentifierDTO>(_IdentifierDTO_QNAME, IdentifierDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "updatePatientRequestMessage")
    public JAXBElement<UpdatePatientRequestMessage> createUpdatePatientRequestMessage(UpdatePatientRequestMessage value) {
        return new JAXBElement<UpdatePatientRequestMessage>(_UpdatePatientRequestMessage_QNAME, UpdatePatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "validatePatientRequestMessage")
    public JAXBElement<ValidatePatientRequestMessage> createValidatePatientRequestMessage(ValidatePatientRequestMessage value) {
        return new JAXBElement<ValidatePatientRequestMessage>(_ValidatePatientRequestMessage_QNAME, ValidatePatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIdentifiersRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "getIdentifiersRequestMessage")
    public JAXBElement<GetIdentifiersRequestMessage> createGetIdentifiersRequestMessage(GetIdentifiersRequestMessage value) {
        return new JAXBElement<GetIdentifiersRequestMessage>(_GetIdentifiersRequestMessage_QNAME, GetIdentifiersRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIdentifiersRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "GetIdentifiersPatient")
    public JAXBElement<GetIdentifiersRequestMessage> createGetIdentifiersPatient(GetIdentifiersRequestMessage value) {
        return new JAXBElement<GetIdentifiersRequestMessage>(_GetIdentifiersPatient_QNAME, GetIdentifiersRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "UpdatePatientResponse")
    public JAXBElement<AckMessage> createUpdatePatientResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_UpdatePatientResponse_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtendedDateDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "extendedDateDTO")
    public JAXBElement<ExtendedDateDTO> createExtendedDateDTO(ExtendedDateDTO value) {
        return new JAXBElement<ExtendedDateDTO>(_ExtendedDateDTO_QNAME, ExtendedDateDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "getPixManagerServiceResponse")
    public JAXBElement<Object> createGetPixManagerServiceResponse(Object value) {
        return new JAXBElement<Object>(_GetPixManagerServiceResponse_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "personDTO")
    public JAXBElement<PersonDTO> createPersonDTO(PersonDTO value) {
        return new JAXBElement<PersonDTO>(_PersonDTO_QNAME, PersonDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "ValidatePatientResponse")
    public JAXBElement<AckMessage> createValidatePatientResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_ValidatePatientResponse_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "setSecurityService")
    public JAXBElement<Object> createSetSecurityService(Object value) {
        return new JAXBElement<Object>(_SetSecurityService_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TypeCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "typeCode")
    public JAXBElement<TypeCode> createTypeCode(TypeCode value) {
        return new JAXBElement<TypeCode>(_TypeCode_QNAME, TypeCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "UpdatePatient")
    public JAXBElement<UpdatePatientRequestMessage> createUpdatePatient(UpdatePatientRequestMessage value) {
        return new JAXBElement<UpdatePatientRequestMessage>(_UpdatePatient_QNAME, UpdatePatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "AddNewPatientWithoutValidationResponse")
    public JAXBElement<AckMessage> createAddNewPatientWithoutValidationResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_AddNewPatientWithoutValidationResponse_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePatientRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "ValidatePatient")
    public JAXBElement<ValidatePatientRequestMessage> createValidatePatient(ValidatePatientRequestMessage value) {
        return new JAXBElement<ValidatePatientRequestMessage>(_ValidatePatient_QNAME, ValidatePatientRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPossibleMatchedPatientsRequestMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "getAllPossibleMatchedPatientsRequestMessage")
    public JAXBElement<GetAllPossibleMatchedPatientsRequestMessage> createGetAllPossibleMatchedPatientsRequestMessage(GetAllPossibleMatchedPatientsRequestMessage value) {
        return new JAXBElement<GetAllPossibleMatchedPatientsRequestMessage>(_GetAllPossibleMatchedPatientsRequestMessage_QNAME, GetAllPossibleMatchedPatientsRequestMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pixmanager.rup.janpix.com/", name = "getSecurityServiceResponse")
    public JAXBElement<Object> createGetSecurityServiceResponse(Object value) {
        return new JAXBElement<Object>(_GetSecurityServiceResponse_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "addressDTO")
    public JAXBElement<AddressDTO> createAddressDTO(AddressDTO value) {
        return new JAXBElement<AddressDTO>(_AddressDTO_QNAME, AddressDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "patientDTO")
    public JAXBElement<PatientDTO> createPatientDTO(PatientDTO value) {
        return new JAXBElement<PatientDTO>(_PatientDTO_QNAME, PatientDTO.class, null, value);
    }

}
