package com.janpix.regdoc.domain

import com.janpix.regdoc.exceptions.*

enum DocumentStateTypes {
	Submitted,
	Approved,
	Deprecated,
	Deleted
}

class DocumentState {
	String name
	
	private DocumentState(String name) {
		this.name = name
	}
	
	def approve() {
		if (this.name == DocumentStateTypes.Submitted.toString())
			this.name = DocumentStateTypes.Approved.toString()
		else
			throw new CantChangeDocumentState()
	}
	
	def deprecate() {
		if (this.name == DocumentStateTypes.Approved.toString())
			this.name = DocumentStateTypes.Deprecated.toString()
		else
			throw new CantChangeDocumentState()
	}
	
	def erase() {
		if (this.name == DocumentStateTypes.Submitted.toString() || 
			this.name == DocumentStateTypes.Approved.toString() || 
			this.name == DocumentStateTypes.Deprecated.toString())
			this.name = DocumentStateTypes.Deleted.toString()
		else
			throw new CantChangeDocumentState()
	}
	
	def submit() {
		if (this.name == DocumentStateTypes.Approved.toString())
			this.name = DocumentStateTypes.Submitted.toString()
		else
			throw new CantChangeDocumentState()
	}
	
	def isDeleted() {
		return this.name == DocumentStateTypes.Deleted.toString()
	}
	
	def isDeprecated() {
		return this.name == DocumentStateTypes.Deprecated.toString()
	}
	
	def isApproved() {
		return this.name == DocumentStateTypes.Approved.toString()
	}
	
	def isSubmitted() {
		return this.name == DocumentStateTypes.Submitted.toString()
	}
	
	static DocumentState submittedState() {
		return new DocumentState(DocumentStateTypes.Submitted.toString())
	}
	
	static DocumentState approvedState() {
		return new DocumentState(DocumentStateTypes.Approved.toString())
	}
	
	static DocumentState deprecatedState() {
		return new DocumentState(DocumentStateTypes.Deprecated.toString())
	}
	
	static DocumentState deletedState() {
		return new DocumentState(DocumentStateTypes.Deleted.toString())
	}
	
	boolean equals(other){
		return this.name == other.name
	}
}
