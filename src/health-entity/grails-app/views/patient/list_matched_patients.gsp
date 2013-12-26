
<%@ page import="ar.com.healthentity.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entity" value="${patientInstance}" />
		<title>Similitud de pacientes</title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1>Listado de pacientes con similitudes</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<g:form url="[resource:patientInstance, action:'forceRegister']">
					<g:hiddenField name="id" value="${patientInstance?.id}" />
					<g:link class="btn btn-small btn-success" action="edit" resource="${patientInstance}"><i class="icon-pencil "></i><g:message code="default.edit.label" args="['Paciente']" /></g:link>
					<g:actionSubmit class="btn btn-danger" action="forceRegister" value="${message(code: 'patient.matching.force.label', default: 'Force')}" onclick="return confirm('${message(code: 'patient.matching.force.confirm.message', default: 'Are you sure?')}');" />
				</g:form>
			</div>
		</div>
		<br>

		<g:if test="${flash.message}">
			<div class="row">
				<div class="col-sm-12">
					<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
							${flash.message}
					</div>
				</div>
			</div>
		</g:if>
		<div class="row">		
			<div class="box col-sm-12">
				<div class="box-header" data-original-title>
					<h2><i class="icon-user"></i><span class="break"></span>Posibles similitudes</h2>
					<div class="box-icon">
						<a href="#" class="btn-minimize"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
				<table class="table table-striped table-bordered bootstrap-datatable datatable">
				<thead>
					<tr>
					
						<g:sortableColumn property="firstName" title="${message(code: 'patient.firstName.label', default: 'First Name')}" />
					
						<g:sortableColumn property="lastName" title="${message(code: 'patient.lastName.label', default: 'Last Name')}" />
						
						<g:sortableColumn property="lastName" title="${message(code: 'patient.dni.label', default: 'DNI')}" />
					
						<g:sortableColumn property="birthdate" title="${message(code: 'patient.birthdate.label', default: 'Birthdate')}" />
					
						<g:sortableColumn property="sex" title="${message(code: 'patient.sex.label', default: 'Sex')}" />
					
						<th><g:message code="patient.city.label" default="City" /></th>
					
						<g:sortableColumn property="address" title="${message(code: 'patient.address.label', default: 'Address')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${patientInstanceList}" status="i" var="patientInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${patientInstance.id}">${fieldValue(bean: patientInstance, field: "firstName")}</g:link></td>
					
						<td>${fieldValue(bean: patientInstance, field: "lastName")}</td>
					
						<td>${fieldValue(bean: patientInstance, field: "dni")}</td>
					
						<td><g:formatDate date="${patientInstance.birthdate}" format="yyyy-MM-dd" /></td>
					

					
						<td>${fieldValue(bean: patientInstance, field: "sex")}</td>
					

					
						<td>${fieldValue(bean: patientInstance, field: "city")}</td>
					

					
						<td>
						${fieldValue(bean: patientInstance, field: "addressName")}
						${fieldValue(bean: patientInstance, field: "addressNumber")}
						
						</td>

					
			  		
					</tr>
				</g:each>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	</body>
</html>
