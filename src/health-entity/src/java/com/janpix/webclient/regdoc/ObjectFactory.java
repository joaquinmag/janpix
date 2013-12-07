
package com.janpix.webclient.regdoc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.janpix.webclient.regdoc package. 
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

    private final static QName _AuthorDTO_QNAME = new QName("jpx:ar.com.janpix", "authorDTO");
    private final static QName _QueryDocumentRequestMessage_QNAME = new QName("http://services.regdoc.janpix.com/", "queryDocumentRequestMessage");
    private final static QName _HealthEntityDTO_QNAME = new QName("jpx:ar.com.janpix", "healthEntityDTO");
    private final static QName _TitleCriteriaDTO_QNAME = new QName("jpx:ar.com.janpix", "titleCriteriaDTO");
    private final static QName _AckMessage_QNAME = new QName("jpx:ar.com.janpix", "ackMessage");
    private final static QName _RegisterDocumentRequestMessage_QNAME = new QName("http://services.regdoc.janpix.com/", "registerDocumentRequestMessage");
    private final static QName _FileAttributesDTO_QNAME = new QName("jpx:ar.com.janpix", "fileAttributesDTO");
    private final static QName _RegisterDocumentRequest_QNAME = new QName("jpx:ar.com.janpix", "registerDocumentRequest");
    private final static QName _TypeCode_QNAME = new QName("jpx:ar.com.janpix", "typeCode");
    private final static QName _QueryDocumentRequest_QNAME = new QName("jpx:ar.com.janpix", "queryDocumentRequest");
    private final static QName _ClinicalDocumentDTO_QNAME = new QName("jpx:ar.com.janpix", "clinicalDocumentDTO");
    private final static QName _QueryDocumentResponse_QNAME = new QName("http://services.regdoc.janpix.com/", "queryDocumentResponse");
    private final static QName _AckStoredQueryMessage_QNAME = new QName("jpx:ar.com.janpix", "ackStoredQueryMessage");
    private final static QName _DateCreationCriteriaDTO_QNAME = new QName("jpx:ar.com.janpix", "dateCreationCriteriaDTO");
    private final static QName _RegisterDocumentResponse_QNAME = new QName("http://services.regdoc.janpix.com/", "registerDocumentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.janpix.webclient.regdoc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegisterDocumentRequest }
     * 
     */
    public RegisterDocumentRequest createRegisterDocumentRequest() {
        return new RegisterDocumentRequest();
    }

    /**
     * Create an instance of {@link ClinicalDocumentDTO }
     * 
     */
    public ClinicalDocumentDTO createClinicalDocumentDTO() {
        return new ClinicalDocumentDTO();
    }

    /**
     * Create an instance of {@link QueryDocumentRequest }
     * 
     */
    public QueryDocumentRequest createQueryDocumentRequest() {
        return new QueryDocumentRequest();
    }

    /**
     * Create an instance of {@link HealthEntityDTO }
     * 
     */
    public HealthEntityDTO createHealthEntityDTO() {
        return new HealthEntityDTO();
    }

    /**
     * Create an instance of {@link AuthorDTO }
     * 
     */
    public AuthorDTO createAuthorDTO() {
        return new AuthorDTO();
    }

    /**
     * Create an instance of {@link TitleCriteriaDTO }
     * 
     */
    public TitleCriteriaDTO createTitleCriteriaDTO() {
        return new TitleCriteriaDTO();
    }

    /**
     * Create an instance of {@link AckStoredQueryMessage }
     * 
     */
    public AckStoredQueryMessage createAckStoredQueryMessage() {
        return new AckStoredQueryMessage();
    }

    /**
     * Create an instance of {@link AckStoredQueryMessage.ClinicalDocuments }
     * 
     */
    public AckStoredQueryMessage.ClinicalDocuments createAckStoredQueryMessageClinicalDocuments() {
        return new AckStoredQueryMessage.ClinicalDocuments();
    }

    /**
     * Create an instance of {@link FileAttributesDTO }
     * 
     */
    public FileAttributesDTO createFileAttributesDTO() {
        return new FileAttributesDTO();
    }

    /**
     * Create an instance of {@link DateCreationCriteriaDTO }
     * 
     */
    public DateCreationCriteriaDTO createDateCreationCriteriaDTO() {
        return new DateCreationCriteriaDTO();
    }

    /**
     * Create an instance of {@link AckMessage }
     * 
     */
    public AckMessage createAckMessage() {
        return new AckMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "authorDTO")
    public JAXBElement<AuthorDTO> createAuthorDTO(AuthorDTO value) {
        return new JAXBElement<AuthorDTO>(_AuthorDTO_QNAME, AuthorDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.regdoc.janpix.com/", name = "queryDocumentRequestMessage")
    public JAXBElement<QueryDocumentRequest> createQueryDocumentRequestMessage(QueryDocumentRequest value) {
        return new JAXBElement<QueryDocumentRequest>(_QueryDocumentRequestMessage_QNAME, QueryDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HealthEntityDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "healthEntityDTO")
    public JAXBElement<HealthEntityDTO> createHealthEntityDTO(HealthEntityDTO value) {
        return new JAXBElement<HealthEntityDTO>(_HealthEntityDTO_QNAME, HealthEntityDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TitleCriteriaDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "titleCriteriaDTO")
    public JAXBElement<TitleCriteriaDTO> createTitleCriteriaDTO(TitleCriteriaDTO value) {
        return new JAXBElement<TitleCriteriaDTO>(_TitleCriteriaDTO_QNAME, TitleCriteriaDTO.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.regdoc.janpix.com/", name = "registerDocumentRequestMessage")
    public JAXBElement<RegisterDocumentRequest> createRegisterDocumentRequestMessage(RegisterDocumentRequest value) {
        return new JAXBElement<RegisterDocumentRequest>(_RegisterDocumentRequestMessage_QNAME, RegisterDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileAttributesDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "fileAttributesDTO")
    public JAXBElement<FileAttributesDTO> createFileAttributesDTO(FileAttributesDTO value) {
        return new JAXBElement<FileAttributesDTO>(_FileAttributesDTO_QNAME, FileAttributesDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "registerDocumentRequest")
    public JAXBElement<RegisterDocumentRequest> createRegisterDocumentRequest(RegisterDocumentRequest value) {
        return new JAXBElement<RegisterDocumentRequest>(_RegisterDocumentRequest_QNAME, RegisterDocumentRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "queryDocumentRequest")
    public JAXBElement<QueryDocumentRequest> createQueryDocumentRequest(QueryDocumentRequest value) {
        return new JAXBElement<QueryDocumentRequest>(_QueryDocumentRequest_QNAME, QueryDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClinicalDocumentDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "clinicalDocumentDTO")
    public JAXBElement<ClinicalDocumentDTO> createClinicalDocumentDTO(ClinicalDocumentDTO value) {
        return new JAXBElement<ClinicalDocumentDTO>(_ClinicalDocumentDTO_QNAME, ClinicalDocumentDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckStoredQueryMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.regdoc.janpix.com/", name = "queryDocumentResponse")
    public JAXBElement<AckStoredQueryMessage> createQueryDocumentResponse(AckStoredQueryMessage value) {
        return new JAXBElement<AckStoredQueryMessage>(_QueryDocumentResponse_QNAME, AckStoredQueryMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckStoredQueryMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "ackStoredQueryMessage")
    public JAXBElement<AckStoredQueryMessage> createAckStoredQueryMessage(AckStoredQueryMessage value) {
        return new JAXBElement<AckStoredQueryMessage>(_AckStoredQueryMessage_QNAME, AckStoredQueryMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateCreationCriteriaDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "dateCreationCriteriaDTO")
    public JAXBElement<DateCreationCriteriaDTO> createDateCreationCriteriaDTO(DateCreationCriteriaDTO value) {
        return new JAXBElement<DateCreationCriteriaDTO>(_DateCreationCriteriaDTO_QNAME, DateCreationCriteriaDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.regdoc.janpix.com/", name = "registerDocumentResponse")
    public JAXBElement<AckMessage> createRegisterDocumentResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_RegisterDocumentResponse_QNAME, AckMessage.class, null, value);
    }

}
