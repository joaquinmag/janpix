package com.janpix.webclient.rup;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.2
 * 2013-11-10T18:22:31.804-03:00
 * Generated source version: 2.6.2
 * 
 */
@WebService(targetNamespace = "http://pixmanager.rup.janpix.com/", name = "PIXManagerJanpixService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PIXManagerJanpixService {

    @WebResult(name = "GetIdentifiersPatientResponse", targetNamespace = "http://pixmanager.rup.janpix.com/", partName = "GetIdentifiersPatientResponse")
    @WebMethod(operationName = "GetIdentifiersPatient")
    public AckMessage getIdentifiersPatient(
        @WebParam(partName = "GetIdentifiersPatient", name = "GetIdentifiersPatient", targetNamespace = "http://pixmanager.rup.janpix.com/")
        GetIdentifiersRequestMessage getIdentifiersPatient
    );

    @WebResult(name = "AddNewPatientWithoutValidationResponse", targetNamespace = "http://pixmanager.rup.janpix.com/", partName = "AddNewPatientWithoutValidationResponse")
    @WebMethod(operationName = "AddNewPatientWithoutValidation")
    public AckMessage addNewPatientWithoutValidation(
        @WebParam(partName = "AddNewPatientWithoutValidation", name = "AddNewPatientWithoutValidation", targetNamespace = "http://pixmanager.rup.janpix.com/")
        AddPatientRequestMessage addNewPatientWithoutValidation
    );

    @WebResult(name = "AddNewPatientResponse", targetNamespace = "http://pixmanager.rup.janpix.com/", partName = "AddNewPatientResponse")
    @WebMethod(operationName = "AddNewPatient")
    public AckMessage addNewPatient(
        @WebParam(partName = "AddNewPatient", name = "AddNewPatient", targetNamespace = "http://pixmanager.rup.janpix.com/")
        AddPatientRequestMessage addNewPatient
    );

    @WebResult(name = "GetAllPossibleMatchedPatientsResponse", targetNamespace = "http://pixmanager.rup.janpix.com/", partName = "GetAllPossibleMatchedPatientsResponse")
    @WebMethod(operationName = "GetAllPossibleMatchedPatients")
    public AckQueryPatientMessage getAllPossibleMatchedPatients(
        @WebParam(partName = "GetAllPossibleMatchedPatients", name = "GetAllPossibleMatchedPatients", targetNamespace = "http://pixmanager.rup.janpix.com/")
        GetAllPossibleMatchedPatientsRequestMessage getAllPossibleMatchedPatients
    );

    @WebResult(name = "getPixManagerServiceResponse", targetNamespace = "http://pixmanager.rup.janpix.com/", partName = "getPixManagerServiceResponse")
    @WebMethod
    public java.lang.Object getPixManagerService();

    @WebMethod
    public void setPixManagerService(
        @WebParam(partName = "setPixManagerService", name = "setPixManagerService", targetNamespace = "http://pixmanager.rup.janpix.com/")
        java.lang.Object setPixManagerService
    );

    @WebResult(name = "UpdatePatientResponse", targetNamespace = "http://pixmanager.rup.janpix.com/", partName = "UpdatePatientResponse")
    @WebMethod(operationName = "UpdatePatient")
    public AckMessage updatePatient(
        @WebParam(partName = "UpdatePatient", name = "UpdatePatient", targetNamespace = "http://pixmanager.rup.janpix.com/")
        UpdatePatientRequestMessage updatePatient
    );
}
