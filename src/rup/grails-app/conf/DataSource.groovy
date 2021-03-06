dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "janpix_rup"
			password = "janpix_rup"
			url = "jdbc:mysql://localhost:3306/janpix_rup"
			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect' //Para que genere tablas InnoDB
		}
    }
    production {
        dataSource {
            dbCreate = "update"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "janpix_rup_prod"
			password = "janpix_rup_prod"
			url = "jdbc:mysql://localhost:3306/janpix_rup_prod"
			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect' //Para que genere tablas InnoDB
        }
    }
}
