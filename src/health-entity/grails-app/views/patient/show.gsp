
<%@ page import="ar.com.healthentity.Patient" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>${patientInstance.fullName}</title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1>
					${patientInstance.fullName}
					<span class="pull-right">
						<g:form url="[resource:patientInstance, action:'delete']" method="DELETE">
							<g:hiddenField name="id" value="${patientInstance?.id}" />
							<p>
								<button type="submit" onclick="return confirm('¿Estás seguro que deseas eliminar el paciente?');" class="btn btn-danger" >
									<i class="icon-remove"></i>
								</button>
							</p>
						</g:form>
					</span>
					<span class="pull-right">
						<g:link action="edit" resource="${patientInstance}" class="btn btn-info"><i class="icon-pencil"></i></g:link>
					</span>
				</h1>
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

		<!--Contenido-->
		<div class="row">	
			<div class="box col-sm-7 show-view">
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.firstName.label" default="First Name" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.firstName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="firstName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.lastName.label" default="Last Name" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.lastName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="lastName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.dni.label" default="Dni" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.dni}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="dni"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.birthdate.label" default="Birthdate" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.birthdate}">
							<span class="view-text">
							
								<g:formatDate date="${patientInstance?.birthdate}" type="date" style="LONG" />
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.sex.label" default="Sex" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.sex}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="sex"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.city.label" default="City" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.city}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="city"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.addressName.label" default="Address Name" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.addressName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="addressName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.addressNumber.label" default="Address Number" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.addressNumber}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="addressNumber"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.email.label" default="Email" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.email}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="email"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.phone.label" default="Phone" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.phone}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="phone"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.dateCreated.label" default="Date Created" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.dateCreated}">
							<span class="view-text">
							
								<g:formatDate date="${patientInstance?.dateCreated}" type="date" style="LONG" />
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.lastUpdated.label" default="Last Updated" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.lastUpdated}">
							<span class="view-text">
							
								<g:formatDate date="${patientInstance?.lastUpdated}" type="date" style="LONG" />
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.studies.label" default="Studies" />
							</span>
						</div>
						<div class="col-sm-9">
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
		</div>
	</body>
</html>
