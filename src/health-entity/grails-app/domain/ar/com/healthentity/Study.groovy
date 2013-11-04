package ar.com.healthentity

/**
 * Representa un estudio realizado a un paciente por un profesional
 * que puede contener varios documentos
 * TODO ver si hacemos herencias con diferentes estilos de estudio
 */
class Study {
	
	Date date
	
	//Patient patient
	List<ClinicalDocument> documents = []
	//StudyType type
	
	//HealthWorker author
	
	String observation
	
	static belongsTo = [patient:Patient]
	
	static hasMany = [documents:ClinicalDocument]
	
    static constraints = {
		
    }
}
