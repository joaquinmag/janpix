<%@ page import="ar.com.healthentity.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entity" value="${patientInstance}" />
		<title>Estudios de ${entity.fullName}</title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1>
					<g:link mapping="showPatient" params="[id: entity.id]" title="Volver"><i class="icon-circle-arrow-left blue top-link"></i></g:link>
					${patientInstance.fullName}
					<span class="pull-right">
					</span>
				</h1>
			</div>
		</div>

		<div class="row">	
			<div class="col-sm-12">
				<div class="row">
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-asterisk"></i>
								Estudios del paciente
							</h2>
						</div>
						<div class="box-content">
							<div class="row">
								<div class="col-sm-12">
									<g:if test="${!entity.studies.empty}">
										<table class="table table-striped table-bordered bootstrap-datatable datatable">
											<thead>
		 										<tr>
		 											<th>Fecha de creación</th>
		 											<th>Título</th>
		 											<th>Tipo de estudio</th>
		 											<th>Documento</th>
		 											<th>Sincronizado</th>
		 											<th>Acciones</th>
		 										</tr>
		 									</thead> 
		 									<tbody>
		 										<g:each in="${entity.studies}" var="study">
			 										<tr>
			 											<td class="center"><g:formatDate format="dd/MM/yyyy" date="${study.date}"/></td>
			 											<td class="center">${study.title}</td>
			 											<td class="center">${study.type.name}</td>
			 											<td class="center">${study.document.filename}</td>
			 											<td class="center">
			 												<g:if test="${!study.isSynchro}">
			 													<span class="label label-warning">
				 													Local
				 												</span>
															</g:if>
															<g:else>
																<span class="label label-success">
				 													Subido
				 												</span>
															</g:else>
			 											</td>
														<td class="center">
															<a class="btn btn-info" href="table.html#">
																<i class="icon-refresh"></i>
																Sincronizar
															</a>
														</td>
			 										</tr>
		 										</g:each>
		 									</tbody>
		 								</table>
	 								</g:if>
	 							</div>
 							</div>
						</div>
					</div> <!-- /box -->
				</div><!-- /row -->
			</div>
		</div>			
	</body>
</html>