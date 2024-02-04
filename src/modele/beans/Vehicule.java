package modele.beans;

public class Vehicule extends ABean{
	private int 	id = 0;
	private String 	matricule = "";
	private int 	type = 0;
	
	public Vehicule() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getTypeAsString(){
		try {
			return TYPE_VEHICULE [ this.type ];
		}
		catch(Exception e) {
			return "/";
		}
	}
	
	
	public String toString() {
		return this.getMatricule()+ " ["+this.getTypeAsString()+"]";
	}
}
