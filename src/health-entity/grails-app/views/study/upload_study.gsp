<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<g:if test="${upload_correct}">
	<p>Estudio sincronizado correctamente.</p>
</g:if>
<g:else>
	<p>El estudio no se pudo sincronizar correctamente correctamente.</p>
</g:else>
<script type="text/javascript">
	function updateDocumentState() {
		var actions = $("#study${idStudy} .actions");
		var status = $("#study${idStudy} .labelSynchro");
		<g:if test="${upload_correct}">
		actions.hide();
		status.html("<span class=\"label label-success\">Sincronizado</span>")
		</g:if>
		<g:else>
		actions.show();
		status.html("<span class=\"label label-warning\">No sincronizado</span>");
		</g:else>
	}
</script>
</body>
</html>
