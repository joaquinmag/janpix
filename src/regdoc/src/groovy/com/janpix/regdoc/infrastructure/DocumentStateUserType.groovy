package com.janpix.regdoc.infrastructure

import com.janpix.regdoc.domain.*
import org.hibernate.type.*
import org.hibernate.engine.*
import org.hibernate.engine.spi.*
import org.hibernate.usertype.*
import java.sql.*

class DocumentStateUserType implements UserType {
	
	Serializable disassemble(Object a) {
		throw new UnsupportedOperationException()
	}

	Object assemble(Serializable a, Object b) {
		throw new UnsupportedOperationException()
	}
	
	Object deepCopy(Object object) {
		return object
	}
	
	boolean equals(Object x, Object y) {
		if (x == y) return true
		if (x == null || y == null) return false
		x == y
	}
	
	int hashCode(Object object) {
		object.hashCode()
	}
	
	boolean isMutable() {
		false
	}
	
	Object nullSafeGet(ResultSet rs, String[] names, Object owner) {
		assert names.length == 1

		String state = StringType.INSTANCE.get(rs, names[0])

		if (state == null) {
			return null
		}

		switch (state) {
			case toString(DocumentStateTypes.Inserted): return DocumentState.insertedState()
			case toString(DocumentStateTypes.Approved): return DocumentState.approvedState()
			case toString(DocumentStateTypes.Deprecated): return DocumentState.deprecatedState()
			default:
				throw new IllegalArgumentException("invalid state value [${state}]")
		}
	}
	
	void nullSafeSet(PreparedStatement st, Object state, int index) {
		if (value == null) {
			StringType.INSTANCE.set(st, null, index);
		} else {
			StringType.INSTANCE.set(st, toString(state.name), index);
		}
	}

	Object replace(Object a, Object b, Object d) {
		throw new UnsupportedOperationException()
	}
	
	Class returnedClass() {
		DocumentState.class;
	}

	int[] sqlTypes() {
		return [ Types.VARCHAR ]
	}

	String toString(DocumentStateTypes type) {
		type.toString().toLowerCase()
	}
}
