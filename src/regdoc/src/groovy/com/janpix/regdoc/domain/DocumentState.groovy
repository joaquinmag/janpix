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
	
	static DocumentState submittedState() {
		return new DocumentState(name: DocumentStateTypes.Submitted.toString())
	}
	
	static DocumentState approvedState() {
		return new DocumentState(name: DocumentStateTypes.Approved.toString())
	}
	
	static DocumentState deprecatedState() {
		return new DocumentState(name: DocumentStateTypes.Deprecated.toString())
	}
	
	static DocumentState deletedState() {
		return new DocumentState(name: DocumentStateTypes.Deleted.toString())
	}
}
