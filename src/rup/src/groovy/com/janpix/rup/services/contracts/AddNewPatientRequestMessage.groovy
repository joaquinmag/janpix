package com.janpix.rup.services.contracts

import java.io.Serializable

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.hl7.v3.PRPAIN201301UV02

@XmlAccessorType(XmlAccessType.FIELD)
class AddNewPatientRequestMessage implements Serializable {
	PRPAIN201301UV02 patientRegisterMessage
}
