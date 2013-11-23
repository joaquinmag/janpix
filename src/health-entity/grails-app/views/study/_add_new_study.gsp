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
<g:form mapping="study" method="POST" useToken="true" enctype="multipart/form-data" role="form">
	<div class="form-group">
		<label>Archivos</label>
		<input name="studyFiles" type="file" class="form-control" multiple />
	</div>
	<g:actionSubmit value="Crear estudio" class="btn-success"/>
</g:form>
