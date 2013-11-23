<%@ page import="com.janpix.rup.empi.Identifier" %>



<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="identifier.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="type" required="" value="${identifierInstance?.type}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'number', 'error')} required">
	<label for="number">
		<g:message code="identifier.number.label" default="Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="number" required="" value="${identifierInstance?.number}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: identifierInstance, field: 'assigningAuthority', 'error')} required">
	<label for="assigningAuthority">
		<g:message code="identifier.assigningAuthority.label" default="Assigning Authority" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="assigningAuthority" name="assigningAuthority.id" from="${com.janpix.rup.empi.AssigningAuthority.list()}" optionKey="id" required="" value="${identifierInstance?.assigningAuthority?.id}" class="many-to-one"/>
</div>

