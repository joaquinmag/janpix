if(! ('ace' in window) ) window['ace'] = {}
jQuery(function() {
	//at some places we try to use 'tap' event instead of 'click' if jquery mobile plugin is available
	window['ace'].click_event = $.fn.tap ? "tap" : "click";
});

(function($ , undefined) {
	var multiplible = 'multiple' in document.createElement('INPUT');
	var hasFileList = 'FileList' in window;//file list enabled in modern browsers
	var hasFileReader = 'FileReader' in window;

	var Ace_File_Input = function(element , settings) {
		var self = this;
		this.settings = $.extend({}, $.fn.ace_file_input.defaults, settings);

		this.$element = $(element);
		this.element = element;
		this.disabled = false;
		this.can_reset = true;

		this.$element.on('change.ace_inner_call', function(e , ace_inner_call){
			if(ace_inner_call === true) return;//this change event is called from above drop event
			return handle_on_change.call(self);
		});
		
		this.$element.wrap('<div class="ace-file-input" />');
		
		this.apply_settings();
	}
	Ace_File_Input.error = {
		'FILE_LOAD_FAILED' : 1,
		'IMAGE_LOAD_FAILED' : 2,
		'THUMBNAIL_FAILED' : 3
	};


	Ace_File_Input.prototype.apply_settings = function() {
		var self = this;
		var remove_btn = !!this.settings.icon_remove;

		this.multi = this.$element.attr('multiple') && multiplible;
		this.well_style = this.settings.style == 'well';

		if(this.well_style) this.$element.parent().addClass('ace-file-multiple');
		 else this.$element.parent().removeClass('ace-file-multiple');

		this.$element.parent().find(':not(input[type=file])').remove();//remove all except our input, good for when changing settings
		this.$element.after('<label class="file-label" data-title="'+this.settings.btn_choose+'"><span class="file-name" data-title="'+this.settings.no_file+'">'+(this.settings.no_icon ? '<i class="'+this.settings.no_icon+'"></i>' : '')+'</span></label>'+(remove_btn ? '<a class="remove" href="#"><i class="'+this.settings.icon_remove+'"></i></a>' : ''));
		this.$label = this.$element.next();

		this.$label.on('click', function(){//firefox mobile doesn't allow 'tap'!
			if(!this.disabled && !self.element.disabled && !self.$element.attr('readonly')) 
				self.$element.click();
		})

		if(remove_btn) this.$label.next('a').on(ace.click_event, function(){
			if(! self.can_reset ) return false;
			
			var ret = true;
			if(self.settings.before_remove) ret = self.settings.before_remove.call(self.element);
			if(!ret) return false;
			return self.reset_input();
		});


		if(this.settings.droppable && hasFileList) {
			enable_drop_functionality.call(this);
		}
	}

	Ace_File_Input.prototype.show_file_list = function($files) {
		var files = typeof $files === "undefined" ? this.$element.data('ace_input_files') : $files;
		if(!files || files.length == 0) return;

		//////////////////////////////////////////////////////////////////

		if(this.well_style) {
			this.$label.find('.file-name').remove();
			if(!this.settings.btn_change) this.$label.addClass('hide-placeholder');
		}
		this.$label.attr('data-title', this.settings.btn_change).addClass('selected');
		
		for (var i = 0; i < files.length; i++) {
			var filename = typeof files[i] === "string" ? files[i] : $.trim( files[i].name );
			var index = filename.lastIndexOf("\\") + 1;
			if(index == 0)index = filename.lastIndexOf("/") + 1;
			filename = filename.substr(index);
			
			var fileIcon = 'icon-file';
			if((/\.(jpe?g|png|gif|svg|bmp|tiff?)$/i).test(filename)) {
				fileIcon = 'icon-picture';
			}
			else if((/\.(mpe?g|flv|mov|avi|swf|mp4|mkv|webm|wmv|3gp)$/i).test(filename)) fileIcon = 'icon-film';
			else if((/\.(mp3|ogg|wav|wma|amr|aac)$/i).test(filename)) fileIcon = 'icon-music';


			if(!this.well_style) this.$label.find('.file-name').attr({'data-title':filename}).find('[class*="icon-"]').attr('class', fileIcon);
			else {
				this.$label.append('<span class="file-name" data-title="'+filename+'"><i class="'+fileIcon+'"></i></span>');
				var type = $.trim(files[i].type);
				var can_preview = hasFileReader && this.settings.thumbnail 
						&&
						( (type.length > 0 && type.match('image')) || (type.length == 0 && fileIcon == 'icon-picture') )//the second one is for Android's default browser which gives an empty text for file.type
				if(can_preview) {
					var self = this;
					$.when(preview_image.call(this, files[i])).fail(function(result){
						//called on failure to load preview
						if(self.settings.preview_error) self.settings.preview_error.call(self, filename, result.code);
					});
				}
			}

		}

		return true;
	}

	Ace_File_Input.prototype.reset_input = function() {
	  this.$label.attr({'data-title':this.settings.btn_choose, 'class':'file-label'})
			.find('.file-name:first').attr({'data-title':this.settings.no_file , 'class':'file-name'})
			.find('[class*="icon-"]').attr('class', this.settings.no_icon)
			.prev('img').remove();
			if(!this.settings.no_icon) this.$label.find('[class*="icon-"]').remove();
		
		this.$label.find('.file-name').not(':first').remove();
		
		if(this.$element.data('ace_input_files')) {
			this.$element.removeData('ace_input_files');
			this.$element.removeData('ace_input_method');
		}

		this.reset_input_field();
		
		return false;
	}

	Ace_File_Input.prototype.reset_input_field = function() {
		//http://stackoverflow.com/questions/1043957/clearing-input-type-file-using-jquery/13351234#13351234
		this.$element.wrap('<form>').closest('form').get(0).reset();
		this.$element.unwrap();
	}
	
	Ace_File_Input.prototype.enable_reset = function(can_reset) {
		this.can_reset = can_reset;
	}

	Ace_File_Input.prototype.disable = function() {
		this.disabled = true;
		this.$element.attr('disabled', 'disabled').addClass('disabled');
	}
	Ace_File_Input.prototype.enable = function() {
		this.disabled = false;
		this.$element.removeAttr('disabled').removeClass('disabled');
	}
	
	Ace_File_Input.prototype.files = function() {
		return $(this).data('ace_input_files') || null;
	}
	Ace_File_Input.prototype.method = function() {
		return $(this).data('ace_input_method') || '';
	}
	
	Ace_File_Input.prototype.update_settings = function(new_settings) {
		this.settings = $.extend({}, this.settings, new_settings);
		this.apply_settings();
	}



	var enable_drop_functionality = function() {
		var self = this;
		var dropbox = this.element.parentNode;		
		$(dropbox).on('dragenter', function(e){
			e.preventDefault();
			e.stopPropagation();
		}).on('dragover', function(e){
			e.preventDefault();
			e.stopPropagation();
		}).on('drop', function(e){
			e.preventDefault();
			e.stopPropagation();

			var dt = e.originalEvent.dataTransfer;
			var files = dt.files;
			if(!self.multi && files.length > 1) {//single file upload, but dragged multiple files
				var tmpfiles = [];
				tmpfiles.push(files[0]);
				files = tmpfiles;//keep only first file
			}
			
			var ret = true;
			if(self.settings.before_change) ret = self.settings.before_change.call(self.element, files, true);//true means files have been dropped
			if(!ret || ret.length == 0) {
				return false;
			}
			
			//user can return a modified File Array as result
			if(ret instanceof Array || (hasFileList && ret instanceof FileList)) files = ret;
			
			
			self.$element.data('ace_input_files', files);//save files data to be used later by user
			self.$element.data('ace_input_method', 'drop');


			self.show_file_list(files);
			
			
			self.$element.triggerHandler('change' , [true]);//true means inner_call
			return true;
		});
	}
	
	
	var handle_on_change = function() {
		var ret = true;
		if(this.settings.before_change) ret = this.settings.before_change.call(this.element, this.element.files || [this.element.value]/*make it an array*/, false);//false means files have been selected, not dropped
		if(!ret || ret.length == 0) {
			if(!this.$element.data('ace_input_files')) this.reset_input_field();//if nothing selected before, reset because of the newly unacceptable (ret=false||length=0) selection
			return false;
		}
		

		//user can return a modified File Array as result
		var files = !hasFileList ? null ://for old IE, etc
					((ret instanceof Array || ret instanceof FileList) ? ret : this.element.files);
		this.$element.data('ace_input_method', 'select');


		if(files && files.length > 0) {//html5
			this.$element.data('ace_input_files', files);
		}
		else {
			var name = $.trim( this.element.value );
			if(name && name.length > 0) {
				files = []
				files.push(name);
				this.$element.data('ace_input_files', files);
			}
		}

		if(!files || files.length == 0) return false;
		this.show_file_list(files);

		return true;
	}




	var preview_image = function(file) {
		var self = this;
		var $span = self.$label.find('.file-name:last');//it should be out of onload, otherwise all onloads may target the same span because of delays
		
		var deferred = new $.Deferred
		var reader = new FileReader();
		reader.onload = function (e) {
			$span.prepend("<img class='middle' style='display:none;' />");
			var img = $span.find('img:last').get(0);

			$(img).one('load', function() {
				//if image loaded successfully
				var size = 50;
				if(self.settings.thumbnail == 'large') size = 150;
				else if(self.settings.thumbnail == 'fit') size = $span.width();
				$span.addClass(size > 50 ? 'large' : '');

				var thumb = get_thumbnail(img, size, file.type);
				if(thumb == null) {
					//if making thumbnail fails
					$(this).remove();
					deferred.reject({code:Ace_File_Input.error['THUMBNAIL_FAILED']});
					return;
				}

				var w = thumb.w, h = thumb.h;
				if(self.settings.thumbnail == 'small') {w=h=size;};
				$(img).css({'background-image':'url('+thumb.src+')' , width:w, height:h})									
						.data('thumb', thumb.src)
						.attr({src:'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg=='})
						.show()

				///////////////////
				deferred.resolve();
			}).one('error', function() {
				//for example when a file has image extenstion, but format is something else
				$span.find('img').remove();
				deferred.reject({code:Ace_File_Input.error['IMAGE_LOAD_FAILED']});
			});

			img.src = e.target.result;
		}
		reader.onerror = function (e) {
			deferred.reject({code:Ace_File_Input.error['FILE_LOAD_FAILED']});
		}
		reader.readAsDataURL(file);

		return deferred.promise();
	}

	var get_thumbnail = function(img, size, type) {
		
		var w = img.width, h = img.height;
		if(w > size || h > size) {
		  if(w > h) {
			h = parseInt(size/w * h);
			w = size;
		  } else {
			w = parseInt(size/h * w);
			h = size;
		  }
		}

		var dataURL
		try {
			var canvas = document.createElement('canvas');
			canvas.width = w; canvas.height = h;
			var context = canvas.getContext('2d');
			context.drawImage(img, 0, 0, img.width, img.height, 0, 0, w, h);
			dataURL = canvas.toDataURL(/*type == 'image/jpeg' ? type : 'image/png', 10*/)
		} catch(e) {
			dataURL = null;
		}

		//there was only one image that failed in firefox completely randomly! so let's double check it
		if(!( /^data\:image\/(png|jpe?g|gif);base64,[0-9A-Za-z\+\/\=]+$/.test(dataURL)) ) dataURL = null;
		if(! dataURL) return null;

		return {src: dataURL, w:w, h:h};
	}



	///////////////////////////////////////////
	$.fn.ace_file_input = function (option,value) {
		var retval;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ace_file_input');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('ace_file_input', (data = new Ace_File_Input(this, options)));
			if (typeof option === 'string') retval = data[option](value);
		});

		return (retval === undefined) ? $set : retval;
	};


	$.fn.ace_file_input.defaults = {
		style:false,
		no_file:'No File ...',
		no_icon:'icon-upload-alt',
		btn_choose:'Choose',
		btn_change:'Change',
		icon_remove:'icon-remove',
		droppable:false,
		thumbnail:false,//large, fit, small
		
		//callbacks
		before_change:null,
		before_remove:null,
		preview_error:null
     }


})(window.jQuery);
