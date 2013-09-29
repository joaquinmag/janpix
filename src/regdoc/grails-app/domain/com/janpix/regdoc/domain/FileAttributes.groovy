package com.janpix.regdoc.domain

class FileAttributes {
	String uuid
	String repositoryId
	String mimeType
	Date creationTime
	String size
	
	static constraints = {
		uuid(unique: 'repositoryId', nullable: false, blank: false)
		repositoryId(nullable: false, blank: false)
		mimeType(nullable: false, blank: false)
		creationTime(nullable: false)
		size(nullable: false, blank: false)
	}
}
