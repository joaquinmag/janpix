<li class="dd-item" data-id="${study.idStudyType}">
	<div class="dd3-content">${study.name}</div>
	<g:if test="${!study.children.empty}">
		<ol class="dd-list">
			<g:each in="${study.children}" var="child">
				<g:render template="/common/study_type_nest" model="['study':child]" /> 
			</g:each>
		</ol>
	</g:if>
</li>