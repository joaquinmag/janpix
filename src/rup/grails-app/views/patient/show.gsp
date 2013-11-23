
<%@ page import="com.janpix.rup.empi.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-patient" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-patient" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list patient">
			
				<g:if test="${patientInstance?.givenName}">
				<li class="fieldcontain">
					<span id="givenName-label" class="property-label"><g:message code="patient.givenName.label" default="Given Name" /></span>
					
						<span class="property-value" aria-labelledby="givenName-label"><g:link controller="personName" action="show" id="${patientInstance?.givenName?.id}">${patientInstance?.givenName?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.administrativeSex}">
				<li class="fieldcontain">
					<span id="administrativeSex-label" class="property-label"><g:message code="patient.administrativeSex.label" default="Administrative Sex" /></span>
					
						<span class="property-value" aria-labelledby="administrativeSex-label"><g:fieldValue bean="${patientInstance}" field="administrativeSex"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.birthdate}">
				<li class="fieldcontain">
					<span id="birthdate-label" class="property-label"><g:message code="patient.birthdate.label" default="Birthdate" /></span>
					
						<span class="property-value" aria-labelledby="birthdate-label"><g:link controller="extendedDate" action="show" id="${patientInstance?.birthdate?.id}">${patientInstance?.birthdate?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.addresses}">
				<li class="fieldcontain">
					<span id="addresses-label" class="property-label"><g:message code="patient.addresses.label" default="Addresses" /></span>
					
						<g:each in="${patientInstance.addresses}" var="a">
						<span class="property-value" aria-labelledby="addresses-label"><g:link controller="address" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.maritalStatus}">
				<li class="fieldcontain">
					<span id="maritalStatus-label" class="property-label"><g:message code="patient.maritalStatus.label" default="Marital Status" /></span>
					
						<span class="property-value" aria-labelledby="maritalStatus-label"><g:fieldValue bean="${patientInstance}" field="maritalStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.deathdate}">
				<li class="fieldcontain">
					<span id="deathdate-label" class="property-label"><g:message code="patient.deathdate.label" default="Deathdate" /></span>
					
						<span class="property-value" aria-labelledby="deathdate-label"><g:link controller="extendedDate" action="show" id="${patientInstance?.deathdate?.id}">${patientInstance?.deathdate?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.birthplace}">
				<li class="fieldcontain">
					<span id="birthplace-label" class="property-label"><g:message code="patient.birthplace.label" default="Birthplace" /></span>
					
						<span class="property-value" aria-labelledby="birthplace-label"><g:link controller="city" action="show" id="${patientInstance?.birthplace?.id}">${patientInstance?.birthplace?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.multipleBirthIndicator}">
				<li class="fieldcontain">
					<span id="multipleBirthIndicator-label" class="property-label"><g:message code="patient.multipleBirthIndicator.label" default="Multiple Birth Indicator" /></span>
					
						<span class="property-value" aria-labelledby="multipleBirthIndicator-label"><g:formatBoolean boolean="${patientInstance?.multipleBirthIndicator}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.organDonorIndicator}">
				<li class="fieldcontain">
					<span id="organDonorIndicator-label" class="property-label"><g:message code="patient.organDonorIndicator.label" default="Organ Donor Indicator" /></span>
					
						<span class="property-value" aria-labelledby="organDonorIndicator-label"><g:formatBoolean boolean="${patientInstance?.organDonorIndicator}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.identifiers}">
				<li class="fieldcontain">
					<span id="identifiers-label" class="property-label"><g:message code="patient.identifiers.label" default="Identifiers" /></span>
					
						<g:each in="${patientInstance.identifiers}" var="i">
						<span class="property-value" aria-labelledby="identifiers-label"><g:link controller="identifier" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.phoneNumbers}">
				<li class="fieldcontain">
					<span id="phoneNumbers-label" class="property-label"><g:message code="patient.phoneNumbers.label" default="Phone Numbers" /></span>
					
						<g:each in="${patientInstance.phoneNumbers}" var="p">
						<span class="property-value" aria-labelledby="phoneNumbers-label"><g:link controller="phoneNumber" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.uniqueId}">
				<li class="fieldcontain">
					<span id="uniqueId-label" class="property-label"><g:message code="patient.uniqueId.label" default="Unique Id" /></span>
					
						<span class="property-value" aria-labelledby="uniqueId-label"><g:link controller="patientIdentifier" action="show" id="${patientInstance?.uniqueId?.id}">${patientInstance?.uniqueId?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="patient.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${patientInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${patientInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="patient.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${patientInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${patientInstance?.id}" />
					<g:link class="edit" action="edit" id="${patientInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
