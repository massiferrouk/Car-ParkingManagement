package modele.beans;

import java.sql.Timestamp;

public class OperationParking extends ABean{
	private int 		id = 0;
	private int 		idVehicule 		= 0;
	private int 		idPlaceParking 	= 0;
	private Timestamp	dateHeureEntree  = null;
	private Timestamp	dateHeureSortie  	= null;
	private String 	observation = "";
	private int 		idAgent1;
	private int 		idAgent2;
	
	public OperationParking() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdVehicule() {
		return idVehicule;
	}
	
	public void setIdVehicule(int idVehicule) {
		this.idVehicule = idVehicule;
	}
	
	public int getIdPlaceParking() {
		return idPlaceParking;
	}
	
	public void setIdPlaceParking(int idPlaceParking) {
		this.idPlaceParking = idPlaceParking;
	}
	public Timestamp getDateHeureEntree() {
		return dateHeureEntree;
	}
	
	public void setDateHeureEntree(Timestamp dateHeureDebut) {
		this.dateHeureEntree = dateHeureDebut;
	}
	
	public Timestamp getDateHeureSortie() {
		return dateHeureSortie;
	}
	
	public void setDateHeureSortie(Timestamp dateHeureFin) {
		this.dateHeureSortie = dateHeureFin;
	}
	
	public int getIdAgent1() {
		return idAgent1;
	}
	
	public void setIdAgent1(int idAgent1) {
		this.idAgent1 = idAgent1;
	}
	
	public int getIdAgent2() {
		return idAgent2;
	}
	
	public void setIdAgent2(int idAgent2) {
		this.idAgent2 = idAgent2;
	}
	
	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
}
