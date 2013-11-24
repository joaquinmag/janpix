
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
			<div class="col-md-6">
				<div class="row">
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-asterisk"></i>
								Información del paciente
							</h2>
						</div>
						<div class="box-content">
							<div class="row">
								<div class="col-md-4">
									<img class="img-thumbnail" src="<g:resource dir="uploads" file="default-avatar-male.png"/>" alt="Avatar de ${patientInstance.fullName }">
								</div>
								<div class="col-md-8">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr>
												<td>DNI</td>
												<td>
													<g:if test="${patientInstance?.dni}">
														<span class="view-text">
															<g:fieldValue bean="${patientInstance}" field="dni"/>
														</span>
													</g:if>											
												</td>
											</tr>
											<tr>
												<td>Fecha de nacimiento</td>
												<td>
													<g:if test="${patientInstance?.birthdate}">
													<span class="view-text">
													
														<g:formatDate date="${patientInstance?.birthdate}" type="date" style="LONG" />
													
													</span>
													</g:if>	
												</td>
											</tr>
											<tr>
												<td>Género</td>
												<td>
													<g:if test="${patientInstance?.sex}">
													<span class="view-text">
													
														<g:fieldValue bean="${patientInstance}" field="sex"/>
													
													</span>
													</g:if>	
												</td>
											</tr>
											<tr>
												<td>Dirección</td>
												<td>
													${ patientInstance.fullAddress }	
												</td>
											</tr>
											<tr>
												<td>Email</td>
												<td>
													${patientInstance.email}
												</td>
											</tr>
											<tr>
												<td>Teléfono</td>
												<td>
													${patientInstance.phone}
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div> <!-- /box -->
				</div><!-- /row -->
				<div class="row">
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-stethoscope"></i>
								Agregar nuevo estudio
							</h2>
						</div>
						<div class="box-content">
							<g:render template="/study/add_new_study" model="['patientId': patientInstance.id, 'studyTypeRoots': studyTypeRoots]" />
						</div>
					</div><!-- /box -->
				</div><!-- /row -->
			</div>
			<div class="box col-md-6">
				<div class="box-header">
					<h2>
						<i class="icon-book"></i>
						Historial
					</h2>
				</div>
				<div class="box-content">
					<div class="row">
						<div class="col-md-12">
							<div class="graph">
								<g:render template="/study/timeline" model="['studies':patientInstance.studies]" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			
	</body>
</html>
