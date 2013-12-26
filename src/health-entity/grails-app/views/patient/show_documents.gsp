<%@ page import="ar.com.healthentity.Patient" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entity" value="${patientInstance}" />
		<title>Estudios de ${entity.fullName}</title>
		<r:script>
			function uploadFinished(idMsg) {
				$("#"+idMsg).show();
				updateDocumentState();
			}
			<g:if test="${(flash.success || flash.warning)}">
			$(document).ready( function() {
				$("#refresh-docs #${patientInstance.id}").click();
			});
			</g:if>
		</r:script>
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
							<ul class="nav tab-menu nav-tabs" id="myTab">
								<li class="active"><a href="#Local">Local</a></li>
								<li><a href="#Remoto">Remoto</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="Local">
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
				 											<th>Sincronizado en Janpix</th>
				 											<th>Acciones</th>
				 										</tr>
				 									</thead> 
				 									<tbody>
				 										<g:each in="${entity.studies}" var="study">
					 										<tr id="study${study.id}">
					 											<td class="center"><g:formatDate format="dd/MM/yyyy" date="${study.date}"/></td>
					 											<td class="center">${study.title}</td>
					 											<td class="center">${study.type.name}</td>
					 											<td class="center"><g:link controller="study" action="download" id="${study.id}">${study.document.filename}</g:link></td>
					 											<td class="center labelSynchro">
					 												<g:if test="${!study.isSynchro}">
					 													<span class="label label-danger">
						 													No sincronizado
						 												</span>
																	</g:if>
																	<g:else>
																		<span class="label label-success">
						 													Sincronizado
						 												</span>
																	</g:else>
					 											</td>
																<td class="center">
																	<div class="actions">
																		<g:if test="${!study.isSynchro}">
																			<g:remoteLink mapping="uploadDocument" id="${study.id}" class="btn btn-primary" update="[success: 'success-msg-txt', failure: 'warning-msg-txt']"  onSuccess="uploadFinished('success-msg')" onFailure="uploadFinished('warning-msg')">
																				<i class="icon-cloud-upload"></i>
																				Subir archivo
																			</g:remoteLink>
																		</g:if>
																	</div>
																</td>
					 										</tr>
				 										</g:each>
				 									</tbody>
				 								</table>
			 								</g:if>
			 							</div>
		 							</div>
	 							</div>
	 							<div class="tab-pane" id="Remoto">
									<div class="row">
										<div id="refresh-docs" class="col-sm-12" style="padding-top:10px">
											<g:remoteLink mapping="refreshRemoteDocuments" id="${patientInstance.id}" class="btn btn-primary pull-right" update="[success: 'remoteDocuments', failure: 'remoteDocuments']">
												<i class="icon-refresh"></i>
												Recargar listado de archivos
											</g:remoteLink>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12" id="remoteDocuments">
										</div>
									</div>
	 							</div>
							</div><!-- /.tab-content -->
						</div><!-- /.box-content -->
					</div> <!-- /box -->
				</div><!-- /row -->
			</div>
		</div>			
	</body>
</html>
