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
	//User author
	String observation

	// synchro information
	boolean isSynchro = false
	String repositoryId
	String localDocId

	static mapping = {
		sort "date"
	}
	
    static constraints = {
		title nullable: false, blank: false
		patient nullable: false
		document nullable: false
		date nullable: false
		type nullable: false
		observation nullable: true
		repositoryId nullable: true
		localDocId nullable: true
		//author nullable: false
    }
	
	String toString(){
		return "${title}-${type}"
	}
}
