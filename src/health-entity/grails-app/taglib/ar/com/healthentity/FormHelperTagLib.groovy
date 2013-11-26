package ar.com.healthentity

class FormHelperTagLib {
    static defaultEncodeAs = 'raw'
	
	def fieldErrors = { attrs, body ->
		out << g.hasErrors([bean:attrs.bean, field:attrs.field], {
			out << '<span class="help-block">'
			out << g.eachError([bean:attrs.bean, field:attrs.field, "var":"error"], { params ->
				out << '<span>'
				out << g.message([error:params.error])
				out << '</span><br/>'
			})
			out << '</span>'
		})
	}
}
