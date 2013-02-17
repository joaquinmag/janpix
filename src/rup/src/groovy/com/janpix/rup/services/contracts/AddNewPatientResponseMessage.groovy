package com.janpix.rup.services.contracts

import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import org.hl7.v3.MCCIIN000002UV01

@XmlAccessorType(XmlAccessType.FIELD)
class AddNewPatientResponseMessage implements Serializable {
	MCCIIN000002UV01 ackMessage
}
