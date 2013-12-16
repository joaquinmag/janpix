<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<g:if test="${upload_correct}">
	<h1>Archivo descargado correctamente.</h1>
</g:if>
<g:else>
	<h1>El archivo no se pudo descargar.</h1>
</g:else>
<script type="text/javascript">
	function refreshDownloadStatus() {
		var actions = $("#remote${idRemote} .actions");
		var status = $("#remote${idRemote} .labelSynchro");
		<g:if test="${upload_correct}">
		actions.hide();
		status.html("<i class=\"icon-ok-sign green\"></i>")
		</g:if>
		<g:else>
		actions.show();
		status.html("<i class=\"icon-remove-sign red\"></i>");
		</g:else>
	}
</script>
</body>
</html>