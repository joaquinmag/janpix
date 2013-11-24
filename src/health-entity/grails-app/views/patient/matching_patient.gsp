		<div class="row">		
			<div class="col-sm-12">
				<h2><g:message code="patient.matching.label" /></h2>
			</div>
		</div>
		<br>
		<!--Contenido-->
		<div class="row">	
			<div class="col-sm-7 show-view">
							
					<div class="row">
						<div class="col-sm-4">
							<span class="view-label">
								<g:message code="patient.firstName.label" default="First Name" />
							</span>
						</div>
						<div class="col-sm-8">
							<g:if test="${patientInstance?.firstName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="firstName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
					<div class="row">
						<div class="col-sm-4">
							<span class="view-label">
								<g:message code="patient.lastName.label" default="Last Name" />
							</span>
						</div>
						<div class="col-sm-8">
							<g:if test="${patientInstance?.lastName}">
							<span class="view-text">
							
								<g:fieldValue bean="${patientInstance}" field="lastName"/>
							
							</span>
							</g:if>		
						</div>
					</div>
							
				
									
			</div>
			<!-- Menu -->
			<div class="col-sm-5">
				<div class="row">
					<strong><g:message code="default.actions.label" default="Actions"/></strong>
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
			
		</div>

