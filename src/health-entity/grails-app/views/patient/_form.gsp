<%@ page import="ar.com.healthentity.Patient" %>



<div class="control-group ${hasErrors(bean: patientInstance, field: 'firstName', 'error')} required">
	<label class="control-label" for="firstName">
		<g:message code="patient.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="firstName" required="" value="${patientInstance?.firstName}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'lastName', 'error')} required">
	<label class="control-label" for="lastName">
		<g:message code="patient.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="lastName" required="" value="${patientInstance?.lastName}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'birthdate', 'error')} required">
	<label class="control-label" for="birthdate">
		<g:message code="patient.birthdate.label" default="Birthdate" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:datePicker name="birthdate" precision="day"  value="${patientInstance?.birthdate}"  />
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'sex', 'error')} required">
	<label class="control-label" for="sex">
		<g:message code="patient.sex.label" default="Sex" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:select name="sex" from="${ar.com.healthentity.SexType?.values()}" keys="${ar.com.healthentity.SexType.values()*.name()}" required="" value="${patientInstance?.sex?.name()}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'city', 'error')} required">
	<label class="control-label" for="city">
		<g:message code="patient.city.label" default="City" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:select id="city" name="city.id" from="${ar.com.healthentity.City.list()}" optionKey="id" required="" value="${patientInstance?.city?.id}" class="many-to-one"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'address', 'error')} required">
	<label class="control-label" for="address">
		<g:message code="patient.address.label" default="Address" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="address" required="" value="${patientInstance?.address}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'email', 'error')} ">
	<label class="control-label" for="email">
		<g:message code="patient.email.label" default="Email" />
		
	</label>
	<div class="controls">
		<g:textField name="email" value="${patientInstance?.email}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: patientInstance, field: 'phone', 'error')} ">
	<label class="control-label" for="phone">
		<g:message code="patient.phone.label" default="Phone" />
		
	</label>
	<div class="controls">
		<g:textField name="phone" value="${patientInstance?.phone}"/>
	</div>
</div>

