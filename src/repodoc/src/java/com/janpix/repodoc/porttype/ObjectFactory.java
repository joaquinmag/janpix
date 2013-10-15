
package com.janpix.repodoc.porttype;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.janpix.repodoc.porttype package. 
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

    private final static QName _ProvideAndRegisterDocumentMessage_QNAME = new QName("http://repodoc.janpix.com/", "provideAndRegisterDocumentMessage");
    private final static QName _AuthorDTO_QNAME = new QName("jpx:ar.com.janpix", "authorDTO");
    private final static QName _ClinicalDocumentDTO_QNAME = new QName("jpx:ar.com.janpix", "clinicalDocumentDTO");
    private final static QName _TypeCode_QNAME = new QName("jpx:ar.com.janpix", "typeCode");
    private final static QName _HealthEntityDTO_QNAME = new QName("jpx:ar.com.janpix", "healthEntityDTO");
    private final static QName _AckMessage_QNAME = new QName("jpx:ar.com.janpix", "ackMessage");
    private final static QName _RetrieveDocumentRequest_QNAME = new QName("jpx:ar.com.janpix", "retrieveDocumentRequest");
    private final static QName _RetrieveDocumentMessage_QNAME = new QName("http://repodoc.janpix.com/", "retrieveDocumentMessage");
    private final static QName _RetrieveDocumentResponse_QNAME = new QName("http://repodoc.janpix.com/", "retrieveDocumentResponse");
    private final static QName _ProvideAndRegisterDocumentRequest_QNAME = new QName("jpx:ar.com.janpix", "provideAndRegisterDocumentRequest");
    private final static QName _FileAttributesDTO_QNAME = new QName("jpx:ar.com.janpix", "fileAttributesDTO");
    private final static QName _ProvideAndRegisterDocumentResponse_QNAME = new QName("http://repodoc.janpix.com/", "provideAndRegisterDocumentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.janpix.repodoc.porttype
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProvideAndRegisterDocumentRequest }
     * 
     */
    public ProvideAndRegisterDocumentRequest createProvideAndRegisterDocumentRequest() {
        return new ProvideAndRegisterDocumentRequest();
    }

    /**
     * Create an instance of {@link FileAttributesDTO }
     * 
     */
    public FileAttributesDTO createFileAttributesDTO() {
        return new FileAttributesDTO();
    }

    /**
     * Create an instance of {@link ClinicalDocumentDTO }
     * 
     */
    public ClinicalDocumentDTO createClinicalDocumentDTO() {
        return new ClinicalDocumentDTO();
    }

    /**
     * Create an instance of {@link AuthorDTO }
     * 
     */
    public AuthorDTO createAuthorDTO() {
        return new AuthorDTO();
    }

    /**
     * Create an instance of {@link HealthEntityDTO }
     * 
     */
    public HealthEntityDTO createHealthEntityDTO() {
        return new HealthEntityDTO();
    }

    /**
     * Create an instance of {@link RetrieveDocumentRequest }
     * 
     */
    public RetrieveDocumentRequest createRetrieveDocumentRequest() {
        return new RetrieveDocumentRequest();
    }

    /**
     * Create an instance of {@link AckMessage }
     * 
     */
    public AckMessage createAckMessage() {
        return new AckMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProvideAndRegisterDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://repodoc.janpix.com/", name = "provideAndRegisterDocumentMessage")
    public JAXBElement<ProvideAndRegisterDocumentRequest> createProvideAndRegisterDocumentMessage(ProvideAndRegisterDocumentRequest value) {
        return new JAXBElement<ProvideAndRegisterDocumentRequest>(_ProvideAndRegisterDocumentMessage_QNAME, ProvideAndRegisterDocumentRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ClinicalDocumentDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "clinicalDocumentDTO")
    public JAXBElement<ClinicalDocumentDTO> createClinicalDocumentDTO(ClinicalDocumentDTO value) {
        return new JAXBElement<ClinicalDocumentDTO>(_ClinicalDocumentDTO_QNAME, ClinicalDocumentDTO.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link HealthEntityDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "healthEntityDTO")
    public JAXBElement<HealthEntityDTO> createHealthEntityDTO(HealthEntityDTO value) {
        return new JAXBElement<HealthEntityDTO>(_HealthEntityDTO_QNAME, HealthEntityDTO.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "retrieveDocumentRequest")
    public JAXBElement<RetrieveDocumentRequest> createRetrieveDocumentRequest(RetrieveDocumentRequest value) {
        return new JAXBElement<RetrieveDocumentRequest>(_RetrieveDocumentRequest_QNAME, RetrieveDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://repodoc.janpix.com/", name = "retrieveDocumentMessage")
    public JAXBElement<RetrieveDocumentRequest> createRetrieveDocumentMessage(RetrieveDocumentRequest value) {
        return new JAXBElement<RetrieveDocumentRequest>(_RetrieveDocumentMessage_QNAME, RetrieveDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://repodoc.janpix.com/", name = "retrieveDocumentResponse")
    public JAXBElement<AckMessage> createRetrieveDocumentResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_RetrieveDocumentResponse_QNAME, AckMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProvideAndRegisterDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jpx:ar.com.janpix", name = "provideAndRegisterDocumentRequest")
    public JAXBElement<ProvideAndRegisterDocumentRequest> createProvideAndRegisterDocumentRequest(ProvideAndRegisterDocumentRequest value) {
        return new JAXBElement<ProvideAndRegisterDocumentRequest>(_ProvideAndRegisterDocumentRequest_QNAME, ProvideAndRegisterDocumentRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AckMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://repodoc.janpix.com/", name = "provideAndRegisterDocumentResponse")
    public JAXBElement<AckMessage> createProvideAndRegisterDocumentResponse(AckMessage value) {
        return new JAXBElement<AckMessage>(_ProvideAndRegisterDocumentResponse_QNAME, AckMessage.class, null, value);
    }

}
