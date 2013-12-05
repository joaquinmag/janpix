<div class="timeline">
	<% def i = 1 %>
	<g:each in="${studies}" var="study">
		<div class="timeslot ${ ((i % 2) == 0) ? "alt" : "" }">
			<div class="task">
				<span>
					<span class="type">Título</span>
					<span class="details">${study.title}</span>
					<span class="type">Tipo de estudio</span>
					<span class="details">${study.type}</span>
					<span class="details" style="text-align: right; margin-right: 5px; margin-left:5px;"><g:link controller="study" action="download" id="${study.id}" class="btn" ><i class="icon-cloud-download"></i> ${study.document.filename}</g:link></span>
				</span>
				<div class="arrow"></div>
			</div>
			<div class="icon">
				<i class="icon-calendar"></i>
			</div>
			<div class="time"><strong><g:formatDate date="${study.date}" type="date"/></strong></div>
		</div>
		<div class="clearfix"></div>
		<% i++ %>
	</g:each>
</div>