		<div class="row">		
			<div class="col-sm-12">
				<h2><g:message code="patient.register.janpix.ok.label" /></h2>
			</div>
		</div>

		<!--Contenido-->
		<div class="row">	
			<div class="col-sm-12 show-view">
			
				<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.id.label" default="Id" />
							</span>
						</div>
						<div class="col-sm-9">
							<g:if test="${patientInstance?.id}">
							<span class="view-text">
								${cuis}
							</span>
							</g:if>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-3">
							<span class="view-label">
								<g:message code="patient.healthentity.label" default="Health Entity" />
							</span>
						</div>
						<div class="col-sm-9">
							<span class="view-text">
								${healthEntity}
							</span>
						</div>
					</div>
							
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
							
				
									
			</div>
		
			
		</div>

