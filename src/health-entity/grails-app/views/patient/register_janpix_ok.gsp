		<div class="row-fluid">		
			<div class="box span12">
				<h2><g:message code="patient.register.janpix.ok.label" /></h2>
			</div>
		</div>

		<!--Contenido-->
		<div class="row-fluid">	
			<div class="box span12 show-view">
			
				<div class="row-fluid">
						<div class="span3">
							<span class="view-label">
								<g:message code="patient.cuis.label" default="CUIS" />
							</span>
						</div>
						<div class="span9">
							<span class="view-text">
							
								${cuis}
							
							</span>
						</div>
					</div>
							
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
		
			
		</div>

