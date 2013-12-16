<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<g:if test="${!remoteStudies.empty}">
	<script type="text/javascript">
	function downloadFinished() {
		show_modal();
		refreshDownloadStatus();
	}
	</script>
	<table class="table table-striped table-bordered bootstrap-datatable datatable">
		<thead>
			<tr>
				<th>Fecha de creación</th>
				<th>Título</th>
				<th>Tipo de estudio</th>
				<th>Comentarios</th>
				<th>Descargado</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${remoteStudies}" var="study" status="pos">
				<tr id="remote${pos}">
					<td><g:formatDate date="${study.date}" format="dd-MM-yyyy" /></td>
					<td>${study.title}</td>
					<td>${study.type}</td>
					<td>${study.observation}</td>
					<td class="center labelSynchro"><i class="icon-<%= study.isSynchro ? "ok-sign green" : "remove-sign red" %>"></i></td>
					<td>
						<g:if test="${!study.isSynchro}">
							<div class="actions">
								<g:formRemote name="remoteForm" url="[controller:'study', action: 'downloadRemote']" 
											action="${createLink(controller:'study', action: 'downloadRemote')}" 
											method="POST" update="[success: 'modalBody', failure: 'modalBody']"  
											onSuccess="downloadFinished()">
									<g:hiddenField name="idRemote" value="${pos}" />
									<g:hiddenField name="idPatient" value="${idPatient}" />
									<g:hiddenField name="title" value="${study.title}" />
									<input type="hidden" name="creationDate" id="creationDate" value="<g:formatDate date="${study.date}" format="yyyy-MM-dd HH:mm:ss.S" />" >
									<g:hiddenField name="idStudyType" value="${study.type.idStudyType}" />
									<g:hiddenField name="observation" value="${study.observation}" />
									<g:hiddenField name="uniqueId" value="${study.repositoryId}" />
									<g:hiddenField name="localDocId" value="${study.localDocId}" />
									<g:hiddenField name="filename" value="${study.document.filename}" />
									<button type="submit" class="btn btn-primary">
										<i class="icon-cloud-download"></i>
										Descargar
									</button>
								</g:formRemote>
							</div>
						</g:if>
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
</g:if>
<g:else>
	<div class="alert alert-info">
		No hay documentos remotos para este paciente.
	</div>
</g:else>
</body>
</html>
