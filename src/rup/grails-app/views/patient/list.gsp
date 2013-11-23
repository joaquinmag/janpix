
<%@ page import="com.janpix.rup.empi.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-patient" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="list-patient" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<th><g:message code="patient.uniqueId.label" default="CUIS" /></th>
						
						<th><g:message code="patient.givenName.label" default="Given Name" /></th>
					
						<g:sortableColumn property="administrativeSex" title="${message(code: 'patient.administrativeSex.label', default: 'Administrative Sex')}" />
					
						<th><g:message code="patient.birthdate.label" default="Birthdate" /></th>
					
						<th><g:message code="patient.birthplace.label" default="Birthplace" /></th>
						
						<th><g:message code="default.actions.label" default="Actions" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${patientInstanceList}" status="i" var="patientInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
						<td>${fieldValue(bean: patientInstance, field: "uniqueId")}</td>
											
						<td><g:link action="show" id="${patientInstance.id}">${fieldValue(bean: patientInstance, field: "givenName")}</g:link></td>
						
					
						<td>${fieldValue(bean: patientInstance, field: "administrativeSex")}</td>
					
						<td><g:formatDate date="${patientInstance.birthdate?.date}" format="yyyy-MM-dd" /></td>
					
						<td>${fieldValue(bean: patientInstance, field: "birthplace")}</td>
						
						<td>
							<g:link controller="identifier" action="listPatientIdentifiers" id="${patientInstance?.id} ">
								Ver identificadores
							</g:link>
						</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${patientInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
