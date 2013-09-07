databaseChangeLog = {

	changeSet(author: "martin (generated)", id: "1378580698655-1") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "number", tableName: "address")
	}
}
