<%=packageName%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
	<div class="row-fluid">
			<div class="span12">
				<g:link class="btn btn-small btn-success" action="create"><i class="icon-plus "></i><g:message code="default.new.label" args="[entityName]" /></g:link>
				<g:link class="btn btn-small" action="index"><i class="icon-reorder"></i><g:message code="default.list.label" args="[entityName]" /></g:link>
			</div>
		</div>

		<div class="row-fluid">		
			<div class="box span12">
				<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			</div>
		</div>

		<g:if test="\${flash.message}">
			<div class="row-fluid">
				<div class="span12">
					<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
							\${flash.message}
					</div>
				</div>
			</div>
		</g:if>

		<g:hasErrors bean="\${${propertyName}}">
		<div class="row-fluid">		
			<div class="box span12">
				<div class="alert alert-info">
				<ul class="errors" role="alert">
					<g:eachError bean="\${${propertyName}}" var="error">
					<li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message error="\${error}"/></li>
					</g:eachError>
				</ul>
				<div>
			</div>
		</div>
		</g:hasErrors>

		<div class="row-fluid">		
			<div class="box span12">
				<div class="box-header" data-original-title>
					<h2><i class="icon-user"></i><span class="break"></span><g:message code="default.boxheader.label" args="[entityName]" /></h2>
					<div class="box-icon">
						<a href="#" class="btn-minimize"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="box-content">
					<g:form url="[resource:${propertyName}, action:'update']" method="PUT" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
						<g:hiddenField name="id" value="\${${propertyName}?.id}" />
						<g:hiddenField name="version" value="\${${propertyName}?.version}" />
						<fieldset class="form-horizontal">
							<g:render template="form"/>
						</fieldset>
						<div class="form-actions">
							<g:actionSubmit class="btn btn-large btn-success" action="update" value="\${message(code: 'default.button.update.label', default: 'Update')}" />
						</div>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
