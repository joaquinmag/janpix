import org.springframework.web.servlet.i18n.SessionLocaleResolver

// Place your Spring DSL code here
beans = {
	localeResolver(SessionLocaleResolver) {
		def locale = new Locale("es","ES")
		defaultLocale= locale
		Locale.setDefault (locale)
	 }
}
