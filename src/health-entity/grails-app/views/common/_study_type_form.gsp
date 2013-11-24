<r:require module="jquerynestable" />
<r:script>
	$(document).ready(function() {
		$('#study-nestable-${inputname}').nestable();
	});
</r:script>
<input type="hidden" name="${inputname}" />
<div id="study-nestable-${inputname}" class="dd">
	<ol class="dd-list">
		<g:each in="${studyTypeRoots}" var="study">
			<g:render template="/common/study_type_nest" model="['study':study]" />
		</g:each>
	</ol>
</div>