package com.janpix.servidordocumentos.dto

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class DateCreationCriteriaDTO {
	@XmlElement(required=true)
	Date searchDate
}

@XmlRootElement
class TitleCriteriaDTO {
	@XmlElement(required=true)
	String valueLookup
}