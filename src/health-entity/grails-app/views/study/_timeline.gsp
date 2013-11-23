<div class="timeline">
	<% def i = 1 %>
	<g:each in="${studies}" var="study">
		<div class="timeslot ${ ((i % 2) == 0) ? "alt" : "" }">
			<div class="task">
				<span>
					<span class="type">Tipo de estudio</span>
					<span class="details">Diagnóstico</span>
					<span>Documentos</span>
					<g:each in="${study.documents}" var="document">
						<span class="details">${document.name}</span>
					</g:each>
				</span>
				<div class="arrow"></div>
			</div>
			<div class="icon">
				<i class="icon-calendar"></i>
			</div>
			<div class="time">${study.date}</div>
		</div>
		<div class="clearfix"></div>
		<% i++ %>
	</g:each>
</div>