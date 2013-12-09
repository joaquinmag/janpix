<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<g:if test="${upload_correct}">
	<p>Archivo subido correctamente.</p>
</g:if>
<g:else>
	<p>Archivo no subido correctamente.</p>
</g:else>
<script type="text/javascript">
	function updateDocumentState() {
		var actions = $("#study${createStudyCommand.id} .actions");
		var status = $("#study${createStudyCommand.id} .labelSynchro");
		<g:if test="${upload_correct}">
		actions.show();
		status.html("<span class=\"label label-info\">Remoto</span>")
		</g:if>
		<g:else>
		actions.hide();
		status.html("<span class=\"label label-warning\">Local</span>");
		</g:else>
	}
</script>
</body>
</html>