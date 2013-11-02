package ar.com.healthentity

class Province {
	String name
	String country

    static constraints = {
    }
	
	boolean equals(other){
		return (this.country == other.country && this.name == other.name)
	}
	
	String toString(){
		return "${name}"
	}
}
