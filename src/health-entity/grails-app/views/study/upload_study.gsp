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
</body>
</html>