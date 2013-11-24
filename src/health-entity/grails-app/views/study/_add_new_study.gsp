<r:require module="multiplefileinput" />
<r:require module="bootstrapdatepicker" />
<r:script>
$(document).ready(function() {
	$('#studyFile').ace_file_input({
	   style: 'well',
	   btn_choose:'Suelte archivos aquí o haga click para elegir',
	   btn_change:null,
	   no_icon:'icon-cloud-upload',
	   droppable:true,
	   thumbnail:'large'
	});
	$('.date-picker').datepicker();
});
</r:script>
<g:form mapping="study" method="POST" useToken="true" enctype="multipart/form-data" role="form" class="form-horizontal">
	<fieldset class="col-md-12">
		<input type="hidden" name="patientId" value="${patientId}" />
		<div class="row">
			<div class="form-group col-sm-6 col-md-12">
				<label>Título</label>
				<input name="studyTitle" type="text" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label" for="date-creation">Fecha de realización</label>
			<div class="controls row">
				<div class="input-group date col-sm-4 col-md-8">
					<span class="input-group-addon"><i class="icon-calendar"></i></span>
					<input type="text" class="form-control date-picker" id="date-creation" data-date-format="mm/dd/yyyy">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6 col-md-12">
				<label class="control-label" for="studyFile">Archivos</label>
				<input id="studyFile" name="studyFile" type="file" class="form-control" />
			</div>
		</div>
		<div class="row">
			<div class="form-actions">
				<g:actionSubmit value="Crear estudio" class="btn btn-success"/>
				<button type="reset" class="btn">Cancel</button>
			</div>
		</div>
	</fieldset>
</g:form>
