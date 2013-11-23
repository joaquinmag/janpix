<%@ page import="com.janpix.rup.empi.Patient" %>



<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'givenName', 'error')} required">
	<label for="givenName">
		<g:message code="patient.givenName.label" default="Given Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="givenName" name="givenName.id" from="${com.janpix.rup.empi.PersonName.list()}" optionKey="id" required="" value="${patientInstance?.givenName?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'administrativeSex', 'error')} ">
	<label for="administrativeSex">
		<g:message code="patient.administrativeSex.label" default="Administrative Sex" />
		
	</label>
	<g:textField name="administrativeSex" value="${patientInstance?.administrativeSex}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'birthdate', 'error')} required">
	<label for="birthdate">
		<g:message code="patient.birthdate.label" default="Birthdate" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="birthdate" name="birthdate.id" from="${com.janpix.rup.empi.ExtendedDate.list()}" optionKey="id" required="" value="${patientInstance?.birthdate?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'addresses', 'error')} ">
	<label for="addresses">
		<g:message code="patient.addresses.label" default="Addresses" />
		
	</label>
	<g:select name="addresses" from="${com.janpix.rup.empi.Address.list()}" multiple="multiple" optionKey="id" size="5" value="${patientInstance?.addresses*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'maritalStatus', 'error')} ">
	<label for="maritalStatus">
		<g:message code="patient.maritalStatus.label" default="Marital Status" />
		
	</label>
	<g:textField name="maritalStatus" value="${patientInstance?.maritalStatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'deathdate', 'error')} ">
	<label for="deathdate">
		<g:message code="patient.deathdate.label" default="Deathdate" />
		
	</label>
	<g:select id="deathdate" name="deathdate.id" from="${com.janpix.rup.empi.ExtendedDate.list()}" optionKey="id" value="${patientInstance?.deathdate?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'birthplace', 'error')} ">
	<label for="birthplace">
		<g:message code="patient.birthplace.label" default="Birthplace" />
		
	</label>
	<g:select id="birthplace" name="birthplace.id" from="${com.janpix.rup.empi.City.list()}" optionKey="id" value="${patientInstance?.birthplace?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'multipleBirthIndicator', 'error')} ">
	<label for="multipleBirthIndicator">
		<g:message code="patient.multipleBirthIndicator.label" default="Multiple Birth Indicator" />
		
	</label>
	<g:checkBox name="multipleBirthIndicator" value="${patientInstance?.multipleBirthIndicator}" />
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'organDonorIndicator', 'error')} ">
	<label for="organDonorIndicator">
		<g:message code="patient.organDonorIndicator.label" default="Organ Donor Indicator" />
		
	</label>
	<g:checkBox name="organDonorIndicator" value="${patientInstance?.organDonorIndicator}" />
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'identifiers', 'error')} ">
	<label for="identifiers">
		<g:message code="patient.identifiers.label" default="Identifiers" />
		
	</label>
	<g:select name="identifiers" from="${com.janpix.rup.empi.Identifier.list()}" multiple="multiple" optionKey="id" size="5" value="${patientInstance?.identifiers*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'phoneNumbers', 'error')} ">
	<label for="phoneNumbers">
		<g:message code="patient.phoneNumbers.label" default="Phone Numbers" />
		
	</label>
	<g:select name="phoneNumbers" from="${com.janpix.rup.empi.PhoneNumber.list()}" multiple="multiple" optionKey="id" size="5" value="${patientInstance?.phoneNumbers*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patientInstance, field: 'uniqueId', 'error')} required">
	<label for="uniqueId">
		<g:message code="patient.uniqueId.label" default="Unique Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="uniqueId" name="uniqueId.id" from="${com.janpix.rup.empi.PatientIdentifier.list()}" optionKey="id" required="" value="${patientInstance?.uniqueId?.id}" class="many-to-one"/>
</div>

