package ar.com.healthentity

class Province {
	String name
	String country
	String description

    static constraints = {
    }
	
	boolean equals(other){
		return (this.country == other.country && this.name == other.name)
	}
	
	String toString(){
		return "${description}"
	}
}
