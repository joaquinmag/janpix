package com.janpix.regdoc.domain

class FileAttributes {
	String uuid // Llega desde entidad sanitaria.
	String filename
	String repositoryId
	String mimeType
	Date creationTime
	Long size
	String fileHash
	
	static constraints = {
		uuid(unique: true, nullable: false, blank: false)
		filename(nullable: false)
		repositoryId(nullable: false, blank: false)
		mimeType(nullable: false, blank: false)
		creationTime(nullable: false)
		fileHash(nullable: false, blank: false)
	}
}
