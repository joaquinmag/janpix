<%@ page import="com.janpix.rup.empi.Identifier" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Identificadores del paciente ${patientName }</title>
	</head>
	<body>
		<a href="#list-identifier" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list" controller="patient">Listar Pacientes</g:link></li>
			</ul>
		</div>
		<div id="list-identifier" class="content scaffold-list" role="main">
			<h1>Identificadores del paciente ${patientName }</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="number" title="${message(code: 'identifier.number.label', default: 'Number')}" />
					
						<th><g:message code="identifier.assigningAuthority.label" default="Assigning Authority" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${identifierInstanceList}" status="i" var="identifierInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: identifierInstance, field: "number")}</td>
					
						<td>${fieldValue(bean: identifierInstance, field: "assigningAuthority")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${identifierInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
