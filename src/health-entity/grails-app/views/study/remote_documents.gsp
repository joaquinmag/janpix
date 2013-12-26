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
				<th>Sincronizado</th>
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
					<td class="center"><i class="icon-<%= study.isSynchro ? "ok-sign green" : "remove-sign red" %>"></i></td>
					<td>
						<g:if test="${!study.isSynchro}">
							<g:form mapping="downloadRemoteDocument" method="POST">
								<g:hiddenField name="idPatient" value="${idPatient}" />
								<g:hiddenField name="title" value="${study.title}" />
								<input type="hidden" name="creationDate" id="creationDate" value="<g:formatDate date="${study.date}" format="yyyy-MM-dd HH:mm:ss.S" />" >
								<g:hiddenField name="idStudyType" value="${study.type.idStudyType}" />
								<g:hiddenField name="observation" value="${study.observation}" />
								<g:hiddenField name="uniqueId" value="${study.repositoryId}" />
								<g:hiddenField name="localDocId" value="${study.localDocId}" />
								<g:hiddenField name="filename" value="${study.document.filename}" />
								<button type="submit" class="btn btn-primary" >
									<i class="icon-cloud-download"></i>
									Descargar
								</button>
							</g:form>
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
