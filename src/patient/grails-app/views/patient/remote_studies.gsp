<%@ page import="ar.com.janpix.patient.PatientCommand" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entity" value="${patientInstance}" />
		<title>Estudios de ${entity}</title>
	</head>
	<body>
		<div class="row">		
			<div class="box col-sm-12">
				<h1>
					${patientInstance}
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
									<g:if test="${!studiesInstanceList.empty}">
										<table class="table table-striped table-bordered bootstrap-datatable datatable">
											<thead>
		 										<tr>
		 											<th>Fecha de creación</th>
		 											<th>Título</th>
		 											<th>Tipo de estudio</th>
		 											<th>Autor</th>
		 											<th>Estado</th>
		 											<th>Acciones</th>
		 										</tr>
		 									</thead> 
		 									<tbody>
		 										<g:each in="${studiesInstanceList}" var="study">
			 										<tr id="study${study.uniqueId}">
			 											<td class="center"><g:formatDate format="dd/MM/yyyy" date="${study.date}"/></td>
			 											<td class="center">${study.name}</td>
			 											<td class="center">${study.type}</td>
			 											<td class="center">${study.author}</td>
			 											<td class="center">${study.state}</td>
			 											<td class="center">Aprobar</td>
			 										</tr>
		 										</g:each>
		 									</tbody>
		 								</table>
			 						</g:if>
			 					</div>
		 					</div>
						</div><!-- /.box-content -->
					</div> <!-- /box -->
				</div><!-- /row -->
			</div>
		</div>			
	</body>
</html>
