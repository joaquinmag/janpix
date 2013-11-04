package ar.com.healthentity

enum SexType {
	Masculino,
	Femenino
}

class Patient {
	String firstName
	String lastName
	String address
	City city
	SexType sex
	List<Study> studies = []
	
	Date birthdate
	
	String email
	String phone
	
	Date dateCreated
	Date lastUpdated
	
	static hasMany = [studies:Study]
	
	static mapping = {
		sex enumType: "values"
	}
	
    static constraints = {
		firstName(nullable:false,blank:false)
		lastName(nullable:false,blank:false)
		birthdate(nullable:false)
		sex(nullable:false)
		city(nullable:false)
		address(nullable:false,blank:false)
		email(nullable:true)
		phone(nullable:true)
		dateCreated(display:false)
		lastUpdated(display:false)
    }
}
