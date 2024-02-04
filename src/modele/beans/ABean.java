/**
 * 
 */
package modele.beans;

public abstract class ABean {
//	*********************************************************
//	Type enum role ....
//	*********************************************************
	public static final int ROLE_ADMINISTRATEUR = 0;
	public static final int ROLE_AGENT 			= 1;
	
	public static final String[] ROLES =  {
		"Administrateur",
		"Agent"
	};

	public static int getRoleCode(String str) {
		int role = -1;
		
		for (int i=0; i<ROLES.length; i++) {
			if (ROLES[i].equals(str)) {
				role = i;
				break;
			}
		}
		
		return role;
	}
	
	public static boolean isAdministrateur(String str){
		return isAdministrateur( getRoleCode(str) );
	}
	
	public static boolean isAdministrateur(int code){
		return code == ROLE_ADMINISTRATEUR;
	}
	
	public static boolean isAgent(String str){
		return isAdministrateur( getRoleCode(str) );
	}
	
	public static boolean isAgent(int code){
		return code == ROLE_AGENT;
	}
	
	
//	*********************************************************
//	Type enum Type de vehicule
//	*********************************************************
	public static final int TYPE_VEHICULE_LEGER = 0;
	public static final int TYPE_VEHICULE_MOYEN = 1;
	public static final int TYPE_VEHICULE_LOURD = 2;
	
	public static final String[] TYPE_VEHICULE =  {
		"leger", 
		"moyen", 
		"lourd"
	};
	
	public static int getTypeCode(String str) {
		int code = -1;
		
		for (int i=0; i<TYPE_VEHICULE.length; i++) {
			if (TYPE_VEHICULE[i].equals(str)) {
				code = i;
				break;
			}
		}
		
		return code;
	}
	
	public static boolean isLeger(String str){
		return isLeger( getRoleCode(str) );
	}
	
	public static boolean isMoyen(String str){
		return isMoyen( getRoleCode(str) );
	}
	
	public static boolean isLourd(String str){
		return isLourd( getRoleCode(str) );
	}
	
	public static boolean isLeger(int code){
		return code == TYPE_VEHICULE_LEGER;
	}
	
	public static boolean isMoyen(int code){
		return code == TYPE_VEHICULE_MOYEN;
	}
	
	public static boolean isLourd(int code){
		return code == TYPE_VEHICULE_LOURD;
	}
	
//	*********************************************************
//	Get attributs fields ....
//	*********************************************************
	
}
