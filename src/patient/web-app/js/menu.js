/**
 * Script que marca como active el 'main-menu' en base a la pagina actual
 */
jQuery(document).ready(function(e){
	e("ul.main-menu li a").each(function(){
		e(e(this))[0].href==String(window.location)&&e(this).parent().addClass("active")
	});
	e("ul.main-menu li ul li a").each(function(){
		if(e(e(this))[0].href==String(window.location)){
			e(this).parent().addClass("active");
			e(this).parent().parent().show()
		}	
	});
	e(".dropmenu").click(function(t){
		t.preventDefault();
		e(this).parent().find("ul").slideToggle()
	})
})

