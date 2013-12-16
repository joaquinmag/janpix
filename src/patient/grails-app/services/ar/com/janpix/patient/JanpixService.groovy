package ar.com.janpix.patient

import grails.transaction.Transactional

@Transactional
class JanpixService {

	// TODO por ahora HardCode
    List<StudyCommand> queryAllStudies(String cuis) {
		List<StudyCommand> studies = []
		
		AuthorCommand author = new AuthorCommand()
		HealthEntityCommand healthEntity = new HealthEntityCommand()
		healthEntity.oid = "HE_OID_0001"
		healthEntity.name = "HE_NAME_MOCK"
		author.healthEntity = healthEntity
		
		StudyCommand study1 = new StudyCommand()
		study1.date = new Date()
		study1.name = "Study Mock"
		study1.state = "Pendiente Mock"
		study1.comments = "Es un Mock"
		study1.uniqueId = "Mock Unique Id"
		study1.author = author
		
		studies.add(study1)
		studies.add(study1)
		
		return studies;
    }
}
