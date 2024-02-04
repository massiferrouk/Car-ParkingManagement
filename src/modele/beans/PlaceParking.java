package modele.beans;

public class PlaceParking extends ABean{
	private int 	id = 0;
	private String 	numero = "";
	private int 	type = 0;
	private boolean occupe = false;
	private int 	idEtage = 0;
	
	public PlaceParking() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	public boolean isOccupe() {
		return occupe;
	}
	
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}
	
	public int getIdEtage() {
		return idEtage;
	}
	
	public void setIdEtage(int idEtage) {
		this.idEtage = idEtage;
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
		} catch (Exception e) {
			return "/";
		}
	}

	public String toString() {
		return this.getNumero()+" ("+(this.occupe ? "Occupe" : "Libre")+")";
	}
}
