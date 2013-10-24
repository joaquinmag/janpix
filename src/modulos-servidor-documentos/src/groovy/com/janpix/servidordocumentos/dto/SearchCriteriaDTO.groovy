package com.janpix.servidordocumentos.dto

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlEnum
public enum LookupFields {
	@XmlEnumValue("TitleLookup") TitleLookup,
	@XmlEnumValue("DateCreationLookup") DateCreationLookup
}

@XmlRootElement
class SearchCriteriaDTO {
	@XmlElement(required=true)
	LookupFields lookupField
}

@XmlRootElement
class DateCreationCriteriaDTO extends SearchCriteriaDTO {
	@XmlElement(required=true)
	Date searchDate
	
	DateCreationCriteriaDTO() {
		lookupField = LookupFields.DateCreationLookup
	}
}

@XmlRootElement
class TitleCriteriaDTO extends SearchCriteriaDTO {
	@XmlElement(required=true)
	String valueLookup
	
	TitleCriteriaDTO() {
		lookupField = LookupFields.TitleLookup
	}
}