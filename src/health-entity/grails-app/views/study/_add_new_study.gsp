<r:require module="multiplefileinput" />
<r:require module="bootstrapdatepicker" />
<r:script>
$(document).ready(function() {
	$('#studyFile').ace_file_input({
	   style: 'well',
	   btn_choose:'Suelte el archivo aquí o haga click aquí para elegir uno',
	   btn_change:null,
	   no_icon:'icon-cloud-upload',
	   droppable:true,
	   thumbnail:'large'
	});
	$('.date-picker').datepicker();

});
</r:script>
<g:render template="/common/errors" model="['model': createStudyModel]" />
<g:form mapping="createDocument" method="POST" useToken="true" class="form-horizontal" enctype="multipart/form-data">
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
			<div class="form-group col-md-12">
				<label class="control-label" for="studyTypes">Tipo de estudio</label>
		        <div class="row">
		        	<div class="col-xs-12">
		        		<g:render template="/common/study_type_form" model="['inputname':'studyType', 'studyTypeRoots':studyTypeRoots]" />
		          	</div>
		        </div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6 col-md-12">
				<label class="control-label" for="studyFile">Archivo</label>
				<input id="studyFile" name="studyFile" type="file" class="form-control" />
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="control-label" for="observations">Comentarios</label>
				<textarea id="observations" name="observations" rows="6" style="width: 100%; overflow: hidden; word-wrap: break-word; resize: horizontal; height: 126px;"></textarea>
			</div>
		</div>
		<div class="row">
			<div class="form-actions">
				<button type="submit" class="btn btn-success">Crear estudio</button>
			</div> 
		</div>
	</fieldset>
</g:form>
