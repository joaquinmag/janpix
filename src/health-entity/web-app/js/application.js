if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}


/**
 * Funcion que muestra la ventana modal
 */
function show_modal(){
	$('#myModal').modal('show');
}

