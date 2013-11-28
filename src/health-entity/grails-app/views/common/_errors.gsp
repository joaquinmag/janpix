<g:hasErrors bean="${model}">
	<div class="alert alert-danger">
		<strong>Han ocurrido los siguientes errores:</strong>
		<ul>
			<g:eachError bean="${model}" var="error">
				<li><g:message error="${error}" /></li>
			</g:eachError>
		</ul>
	</div>
</g:hasErrors>