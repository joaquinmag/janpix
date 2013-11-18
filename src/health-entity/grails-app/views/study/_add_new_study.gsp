<r:script>
	$(document).ready(function(){
	    Dropzone.autoDiscover = false;
	    $("#fileuploader").dropzone({
	        dictDefaultMessage: "Arrastre aquí sus archivos",
	        url: "<g:link mapping="study"/>
	        clickable: true,
	        enqueueForUpload: true,
	        maxFilesize: 5,
	        uploadMultiple: true,
	        addRemoveLinks: true
	    });
	
	});
</r:script>
<r:require module="dropzone" />
<g:form mapping="study" method="POST" useToken="true" class="form-horizontal dropzone" enctype="multipart/form-data">
	<div class="form-group">
		<label class="control-label">Archivos</label>
		<div class="controls">
		  	<div id="dropzone">
					<div id="fileuploader" class="fallback">
						<input name="file" type="file" multiple="" />
					</div>
			</div>
		</div>
	</div>
	<g:actionSubmit value="Crear estudio" class="btn-success"/>
</g:form>
