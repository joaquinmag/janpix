package com.jampix.rup.dto

import java.io.Serializable

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement


@XmlAccessorType(XmlAccessType.FIELD)
class PatientDTO implements Serializable {
	String patientName
	String city
}
