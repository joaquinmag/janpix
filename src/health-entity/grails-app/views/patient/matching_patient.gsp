		<div class="row-fluid">		
			<div class="box span12">
				<h2><g:message code="patient.matching.label" /></h2>
			</div>
		</div>

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
							
				
									
			</div>
			<!-- Menu -->
			<div class="box span5 show-menu">
				<h3><g:message code="default.actions.label" default="Actions"/></h3>
				<g:form url="[resource:patientInstance, action:'forceRegister']">
					<g:hiddenField name="id" value="${patientInstance?.id}" />
					<p>
						<g:link class="btn btn-info" action="listMatching" resource="${patientInstance}"><g:message code="patient.matching.possible.list.label" default="Show Possible Matching" /></g:link>
					</p>
					<p>
						<g:actionSubmit class="btn btn-danger" action="forceRegister" value="${message(code: 'patient.matching.force.label', default: 'Force')}" onclick="return confirm('${message(code: 'patient.matching.force.confirm.message', default: 'Are you sure?')}');" />
					</p>
				</g:form>
			</div>
			
		</div>

