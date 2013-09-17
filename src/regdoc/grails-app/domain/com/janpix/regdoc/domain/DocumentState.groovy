package com.janpix.regdoc.domain

class DocumentState {
	def approve()
	def deprecate()
}

class ApprovedDocument extends DocumentState {
	def approve() {
	}
	def deprecate() {
	}
}

class DeprecatedDocument extends DocumentState {
	def approve() {
	}
	def deprecate() {
	}
}
