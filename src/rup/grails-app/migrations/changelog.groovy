databaseChangeLog = {

	changeSet(author: "martin (generated)", id: "1378508030737-1") {
		createTable(tableName: "address") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "addressPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "city_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "department", type: "varchar(255)")

			column(name: "floor", type: "varchar(255)")

			column(name: "number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "street", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "zip_code", type: "varchar(255)")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-2") {
		createTable(tableName: "assigning_authority") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "assigning_autPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "oid", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "class", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-3") {
		createTable(tableName: "city") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cityPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "province_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-4") {
		createTable(tableName: "country") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "countryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-5") {
		createTable(tableName: "extended_date") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "extended_datePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date", type: "timestamp")

			column(name: "precission", type: "varchar(7)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-6") {
		createTable(tableName: "identifier") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "identifierPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "assigning_authority_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-7") {
		createTable(tableName: "identity_document") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "identity_docuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-8") {
		createTable(tableName: "patient_identifier") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "patient_identPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "main_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-9") {
		createTable(tableName: "person") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "administrative_sex", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "birthdate_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "birthplace_id", type: "bigint")

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "deathdate_id", type: "bigint")

			column(name: "given_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "marital_status", type: "varchar(255)")

			column(name: "multiple_birth_indicator", type: "boolean")

			column(name: "organ_donor_indicator", type: "boolean")

			column(name: "class", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "unique_id_id", type: "bigint")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-10") {
		createTable(tableName: "person_address") {
			column(name: "person_addresses_id", type: "bigint")

			column(name: "address_id", type: "bigint")

			column(name: "addresses_idx", type: "integer")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-11") {
		createTable(tableName: "person_identifier") {
			column(name: "person_identifiers_id", type: "bigint")

			column(name: "identifier_id", type: "bigint")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-12") {
		createTable(tableName: "person_name") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "person_namePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "first_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-13") {
		createTable(tableName: "person_phone_number") {
			column(name: "person_phone_numbers_id", type: "bigint")

			column(name: "phone_number_id", type: "bigint")

			column(name: "phone_numbers_idx", type: "integer")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-14") {
		createTable(tableName: "person_relationship") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "person_relatiPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "left_person_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "relation_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "right_person_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-15") {
		createTable(tableName: "phone_number") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "phone_numberPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-16") {
		createTable(tableName: "province") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "provincePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "country_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-32") {
		createIndex(indexName: "oid_uniq_1378508030361", tableName: "assigning_authority", unique: "true") {
			column(name: "oid")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-33") {
		createIndex(indexName: "main_id_uniq_1378508030387", tableName: "patient_identifier", unique: "true") {
			column(name: "main_id")
		}
	}

	changeSet(author: "martin (generated)", id: "1378508030737-17") {
		addForeignKeyConstraint(baseColumnNames: "city_id", baseTableName: "address", constraintName: "FKBB979BF482AC380D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "city", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-18") {
		addForeignKeyConstraint(baseColumnNames: "province_id", baseTableName: "city", constraintName: "FK2E996B9F6DD46D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "province", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-19") {
		addForeignKeyConstraint(baseColumnNames: "assigning_authority_id", baseTableName: "identifier", constraintName: "FK9F88ACA9A717C0A6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "assigning_authority", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-20") {
		addForeignKeyConstraint(baseColumnNames: "birthdate_id", baseTableName: "person", constraintName: "FKC4E39B55D5B02D67", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "extended_date", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-21") {
		addForeignKeyConstraint(baseColumnNames: "birthplace_id", baseTableName: "person", constraintName: "FKC4E39B557399C8B0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "city", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-22") {
		addForeignKeyConstraint(baseColumnNames: "deathdate_id", baseTableName: "person", constraintName: "FKC4E39B5558A02A12", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "extended_date", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-23") {
		addForeignKeyConstraint(baseColumnNames: "given_name_id", baseTableName: "person", constraintName: "FKC4E39B55D774A000", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-24") {
		addForeignKeyConstraint(baseColumnNames: "unique_id_id", baseTableName: "person", constraintName: "FKC4E39B554D65730C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "patient_identifier", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-25") {
		addForeignKeyConstraint(baseColumnNames: "address_id", baseTableName: "person_address", constraintName: "FK23F8B90A60A1AA27", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "address", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-26") {
		addForeignKeyConstraint(baseColumnNames: "identifier_id", baseTableName: "person_identifier", constraintName: "FK5CC06FD3A6EFD50D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "identifier", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-27") {
		addForeignKeyConstraint(baseColumnNames: "person_identifiers_id", baseTableName: "person_identifier", constraintName: "FK5CC06FD352CA382", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-28") {
		addForeignKeyConstraint(baseColumnNames: "phone_number_id", baseTableName: "person_phone_number", constraintName: "FK29D3E3E414A65504", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "phone_number", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-29") {
		addForeignKeyConstraint(baseColumnNames: "left_person_id", baseTableName: "person_relationship", constraintName: "FK3EB816E2A62FB4D5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-30") {
		addForeignKeyConstraint(baseColumnNames: "right_person_id", baseTableName: "person_relationship", constraintName: "FK3EB816E2F0B500AA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
	}

	changeSet(author: "martin (generated)", id: "1378508030737-31") {
		addForeignKeyConstraint(baseColumnNames: "country_id", baseTableName: "province", constraintName: "FKC5242B30518D6EE7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "country", referencesUniqueColumn: "false")
	}

}
