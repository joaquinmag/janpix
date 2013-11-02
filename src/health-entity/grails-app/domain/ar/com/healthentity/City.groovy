package ar.com.healthentity


class City {
	String name
	Province province
	
	static belongsTo = [province:Province]
	
    static constraints = {
    }
	
	boolean equals(other){
		return (this.province == other.province && this.name == other.name)
	}
	
	String toString(){
		return "${name} - ${province}"
	}
	
}
