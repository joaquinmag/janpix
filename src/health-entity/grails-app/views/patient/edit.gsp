<%@ page import="ar.com.healthentity.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title>Editar paciente</title>
	</head>
	<body>
	<div class="row">
			<div class="col-sm-12">
				<g:link class="btn btn-small btn-success" action="create"><i class="icon-plus "></i><g:message code="default.new.label" args="[entityName]" /></g:link>
				<g:link class="btn btn-small" action="index"><i class="icon-reorder"></i><g:message code="default.list.label" args="[entityName]" /></g:link>
			</div>
		</div>

		<div class="row">		
			<div class="box col-sm-12">
				<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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

		<g:hasErrors bean="${patientInstance}">
		<div class="row">		
			<div class="box col-sm-12">
				<div class="alert alert-info">
				<ul class="errors" role="alert">
					<g:eachError bean="${patientInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				<div>
			</div>
		</div>
		</g:hasErrors>

		<div class="row">		
			<div class="box col-sm-12">
				<div class="box-header" data-original-title>
					<h2><i class="icon-user"></i><span class="break"></span>Editar paciente</h2>
					<div class="box-icon">
						<a href="#" class="btn-minimize"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
					<g:form url="[resource:patientInstance, action:'update']" method="PUT" >
						<g:hiddenField name="id" value="${patientInstance?.id}" />
						<g:hiddenField name="version" value="${patientInstance?.version}" />
						<fieldset class="col-sm-12">
							<g:render template="form"/>
						</fieldset>
						<div class="form-actions">
							<g:actionSubmit class="btn btn-large btn-success" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
						</div>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
