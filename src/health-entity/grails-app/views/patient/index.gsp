
<%@ page import="ar.com.healthentity.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title>Listado de pacientes</title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1>Pacientes</h1>
			</div>
		</div>

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
					<h2><i class="icon-user"></i><span class="break"></span>Tabla de pacientes</h2>
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
					
						<th><g:message code="default.actions.label" default="Actions" /></th>
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

					<td class="center">
						<g:link class="btn btn-success" action="show" id="${patientInstance.id}"><i class="icon-zoom-in "></i></g:link>
						<g:link class="btn btn-info" action="edit" id="${patientInstance.id}"><i class="icon-edit "></i>  </g:link>
						<g:remoteLink class="btn btn-warning" action="registerOnJanpix" 
						id="${patientInstance.id}" onSuccess="show_modal()" update="[success:'modalBody',failure:'modalBody']">
							<i class="icon-refresh "></i> 
						</g:remoteLink>
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
