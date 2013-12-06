<%@ page import="ar.com.healthentity.Patient" %>

<r:script>
$(document).ready( function() {
	$(".select2").select2();
});
</r:script>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'firstName', 'error')} required">
	<label class="control-label" for="firstName">
		<g:message code="patient.firstName.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="firstName" required="" value="${patientInstance?.firstName}" class="form-control"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'lastName', 'error')} required">
	<label class="control-label" for="lastName">
		<g:message code="patient.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="lastName" required="" value="${patientInstance?.lastName}" class="form-control"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'dni', 'error')} required">
	<label class="control-label" for="dni">
		<g:message code="patient.dni.label" default="Dni" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="dni" required="" value="${patientInstance?.dni}" class="form-control"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'birthdate', 'error')} required">
	<label class="control-label" for="birthdate">
		<g:message code="patient.birthdate.label" default="Birthdate" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:datePicker class="form-control" name="birthdate" precision="day"  value="${patientInstance?.birthdate}"  />

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'sex', 'error')} required">
	<label class="control-label" for="sex">
		<g:message code="patient.sex.label" default="Sex" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:select class="form-control select2" name="sex" from="${ar.com.healthentity.SexType?.values()}" keys="${ar.com.healthentity.SexType.values()*.name()}" required="" value="${patientInstance?.sex?.name()}"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'city', 'error')} required">
	<label class="control-label" for="city">
		<g:message code="patient.city.label" default="City" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:select id="city" name="city.id" from="${ar.com.healthentity.City.list()}" optionKey="id" required="" value="${patientInstance?.city?.id}" class="many-to-one form-control select2"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'addressName', 'error')} required">
	<label class="control-label" for="addressName">
		<g:message code="patient.addressName.label" default="Address Name" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="addressName" required="" value="${patientInstance?.addressName}" class="form-control"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'addressNumber', 'error')} required">
	<label class="control-label" for="addressNumber">
		<g:message code="patient.addressNumber.label" default="Address Number" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="addressNumber" required="" value="${patientInstance?.addressNumber}" class="form-control"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'email', 'error')} ">
	<label class="control-label" for="email">
		<g:message code="patient.email.label" default="Email" />
		
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="email" value="${patientInstance?.email}" class="form-control"/>

		</div>
	</div>
</div>

<div class="form-group ${hasErrors(bean: patientInstance, field: 'phone', 'error')} ">
	<label class="control-label" for="phone">
		<g:message code="patient.phone.label" default="Phone" />
		
	</label>
	<div class="controls row">
		<div class="col-sm-6">
			<g:textField name="phone" value="${patientInstance?.phone}" class="form-control"/>

		</div>
	</div>
</div>

