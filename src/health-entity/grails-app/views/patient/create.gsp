<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Crear paciente</title>
	</head>
	<body>
	<!-- Titulo -->
	<div class="row">		
		<div class="box col-sm-12">
			<h1>Crear paciente</h1>
		</div>
	</div>

	<!-- FlashMessage -->
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

	<!--Errores -->
	<g:hasErrors bean="${patientInstance}">
		<div class="row">		
			<div class="col-sm-12">
				<div class="alert alert-danger">
				<ul class="errors" role="alert">
					<g:eachError bean="${patientInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</div>
			</div>
		</div>
	</g:hasErrors>

	<!-- Formulario -->
	<div class="row">
		<div class="box col-sm-12">
			<div class="box-content">
				<g:form url="[resource:patientInstance, action:'save']" >
					<fieldset class="col-sm-12">
						<g:render template="form"/>
						<div class="form-actions">								
							<g:submitButton name="create" class="btn btn-large btn-success" value="${message(code: 'default.button.create.label', default: 'Create')}" />
						</div>
					</fieldset>
				</g:form>
			</div>
		</div>
	</div>



	</body>
</html>
