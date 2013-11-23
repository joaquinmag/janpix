
<%@ page import="com.janpix.rup.empi.Identifier" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'identifier.label', default: 'Identifier')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-identifier" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-identifier" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list identifier">
			
				<g:if test="${identifierInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="identifier.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${identifierInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${identifierInstance?.number}">
				<li class="fieldcontain">
					<span id="number-label" class="property-label"><g:message code="identifier.number.label" default="Number" /></span>
					
						<span class="property-value" aria-labelledby="number-label"><g:fieldValue bean="${identifierInstance}" field="number"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${identifierInstance?.assigningAuthority}">
				<li class="fieldcontain">
					<span id="assigningAuthority-label" class="property-label"><g:message code="identifier.assigningAuthority.label" default="Assigning Authority" /></span>
					
						<span class="property-value" aria-labelledby="assigningAuthority-label"><g:link controller="assigningAuthority" action="show" id="${identifierInstance?.assigningAuthority?.id}">${identifierInstance?.assigningAuthority?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${identifierInstance?.id}" />
					<g:link class="edit" action="edit" id="${identifierInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
