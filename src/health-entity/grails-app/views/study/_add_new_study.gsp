<r:require module="multiplefileinput" />
<r:script>
$(document).ready(function() {
	$('input[type=file][multiple]').ace_file_input({
	   style:'well',
	   btn_choose:'Drop images here or click to choose',
	   btn_change:null,
	   no_icon:'icon-cloud-upload',
	   droppable:true,
	   thumbnail:'large'
	});
});
</r:script>
<g:form mapping="study" method="POST" class="form-horizontal">
	<div class="form-group">
		<label class="control-label">Archivos</label>
		<div class="controls">
			<input type="file" multiple />
		</div>
	</div>
	<g:actionSubmit value="Crear estudio" class="btn-success"/>
</g:form>
