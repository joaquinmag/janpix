package com.janpix.regdoc.domain

import com.janpix.regdoc.exceptions.*

enum DocumentStateTypes {
	Inserted,
	Approved,
	Deprecated
}

class DocumentState {
	String name
	
	def approve() {
		if (this.name == DocumentStateTypes.Inserted.toString())
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
	
	static DocumentState insertedState() {
		return new DocumentState(name: DocumentStateTypes.Inserted.toString())
	}
	
	static DocumentState approvedState() {
		return new DocumentState(name: DocumentStateTypes.Approved.toString())
	}
	
	static DocumentState deprecatedState() {
		return new DocumentState(name: DocumentStateTypes.Deprecated.toString())
	}
}
