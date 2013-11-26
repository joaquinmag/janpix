package ar.com.healthentity

/**
 * Representa un estudio realizado a un paciente por un profesional
 * que puede contener varios documentos
 * TODO ver si hacemos herencias con diferentes estilos de estudio
 */
class Study {
	
	String title
	Date date
	Patient patient	
	ClinicalDocument document
	StudyType type
	User author
	String observation
	
    static constraints = {
		title nullable: false, blank: false
		patient nullable: false
		document nullable: false
		date nullable: false
		type nullable: false
		author nullable: false
    }
}
