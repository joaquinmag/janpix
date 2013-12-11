<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<g:if test="${!remoteStudies.empty}">
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
			<g:each in="${remoteStudies}" var="study">
				<tr>
					<td><g:formatDate date="${study.date}" format="dd-MM-yyyy" /></td>
					<td>${study.title}</td>
					<td>${study.type}</td>
					<td>${study.observation}</td>
					<td><i class="icon-<%= study.isSynchro ? "ok-sign green" : "remove-sign red" %>"></i></td>
					<td>
						<g:form mapping="downloadRemoteDocument" method="POST">
							<g:hiddenField name="idPatient" value="${idPatient}" />
							<g:hiddenField name="uniqueId" value="${study.repositoryId}" />
							<g:hiddenField name="filename" value="${study.document.filename}" />
							<button type="submit" class="btn btn-primary" >
								<i class="icon-cloud-download"></i>
								Descargar
							</button>
						</g:form>
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
