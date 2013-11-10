
<%@ page import="ar.com.healthentity.Patient" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'patient.label', default: 'Patient')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
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
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			</div>
		</div>

		<g:if test="${flash.message}">
			<div class="row-fluid">
				<div class="span12">
					<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
							${flash.message}
					</div>
				</div>
			</div>
		</g:if>

		<!--Contenido-->
		<div class="row-fluid">	
			<div class="box span7 show-view">
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.firstName.label" default="First Name" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.firstName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="firstName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.lastName.label" default="Last Name" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.lastName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="lastName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.dni.label" default="Dni" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.dni}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="dni"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.birthdate.label" default="Birthdate" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.birthdate}">
							<span class="view-text">
							
								<g:formatDate date="${patientInstance?.birthdate}" type="date" style="LONG" />
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.sex.label" default="Sex" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.sex}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="sex"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.city.label" default="City" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.city}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="city"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.addressName.label" default="Address Name" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.addressName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="addressName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.addressNumber.label" default="Address Number" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.addressNumber}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="addressNumber"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.email.label" default="Email" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.email}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="email"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.phone.label" default="Phone" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.phone}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="phone"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.dateCreated.label" default="Date Created" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.dateCreated}">
							<span class="view-text">
							
								<g:formatDate date="${patientInstance?.dateCreated}" type="date" style="LONG" />
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.lastUpdated.label" default="Last Updated" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.lastUpdated}">
							<span class="view-text">
							
								<g:formatDate date="${patientInstance?.lastUpdated}" type="date" style="LONG" />
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.studies.label" default="Studies" />
							</span>
						</div>
						<div class="span9">
							<g:if test="${patientInstance?.studies}">
							<span class="view-text">
							
								<g:link controller="study" action="index" params="[patientId:patientInstance?.id]"  class="btn btn-info">
								<g:message code="patient.studies.show.label" default="View One-to-Many" />
								</g:link>

							
							</span>
							</g:if>		
						</div>
					</div>
			
			</div>
			<!-- Menu -->
			<div class="box span5 show-menu">
				<h3><g:message code="default.actions.label" default="Actions"/></h3>
				<g:form url="[resource:patientInstance, action:'delete']" method="DELETE">
					<g:hiddenField name="id" value="${patientInstance?.id}" />
					<p>
						<g:link class="btn btn-info" action="edit" resource="${patientInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					</p>
					<p>
						<g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</p>
				</g:form>
			</div>
		</div>
	</body>
</html>
