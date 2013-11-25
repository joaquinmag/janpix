package ar.com.healthentity

class StudyType {

	String name
	StudyType father
	Long idStudyType
	
	static hasMany = [ children: StudyType ]
	static mappedBy = [ children: 'father' ]
	
	static constraints = {
		name (nullable: false, blank: false)
		father (nullable: true)
		idStudyType (nullable: false, blank: false, unique: true)
	}
	
	static transients = [ 'rootType' ]
	
	StudyType(String name, StudyType documentType, Long idStudyType) {
		this.name = name
		this.father = documentType
		this.idStudyType = idStudyType
	}
	
	StudyType getRootType() {
		if (father)
			return this
		else
			return father.getRootType()
	}

}
