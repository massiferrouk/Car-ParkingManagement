package modele.beans;

public class Utilisateur  extends ABean{
	private int 	id = 0;
	private String 	nom = "";
	private String 	prenom = "";
	private String 	login = "";
	private String 	motDePasse = "";
	private int 	role = 0;
	
	public Utilisateur() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	public String getRolseAsString(){
		return ROLES[ this.role ];
	}
}
