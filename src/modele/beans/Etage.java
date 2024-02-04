package modele.beans;

public class Etage extends ABean{
	private int 	id = 0;
	private int 	numero = 0;
	private String 	description = "";
	
	public Etage() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "N° : "+this.numero+" ("+this.getDescription()+")";
	}
}
