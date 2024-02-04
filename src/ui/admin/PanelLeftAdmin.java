package ui.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ui.LoginFrame;
import ui.utils.IconsConstants;

public class PanelLeftAdmin extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*
	 * **********************************************************************
	 * Definir les composants internes : bouttons, zones de text ...
	 * **********************************************************************
	 */
	
	private JButton bGererUtilisateur;
	private JButton bGererEtage;
	private JButton bGererPlaceParking;
	private JButton bStatistiques;
	private JButton bParametres;
	private JButton	bSeDeconnecter;
	private JButton bQuitter;
	
	
	public PanelLeftAdmin() {
		initUI();
		layoutUI();
		handleEvents();
	}
	
	/*
	 * **********************************************************************
	 * Instancier les composants internes 
	 * **********************************************************************
	 */
	private void initUI(){
		bGererUtilisateur = new JButton("Utilisateurs");
		bGererUtilisateur.setIcon( IconsConstants.USER_MANAGEMENT_ICON_40_40 );

		bGererEtage = new JButton("Étages");
		bGererEtage.setIcon( IconsConstants.ETAGES_ICON_40_40 );
		
		bGererPlaceParking = new JButton("Places-Parking");
		bGererPlaceParking.setIcon( IconsConstants.EMPLACEMENT_PARKING_ICON_40_40 );
		
		bStatistiques = new JButton("Statistiques");
		bStatistiques.setIcon( IconsConstants.STATISTIQUES_ICON_40_40 );
		
		bParametres = new JButton("Parametres");
		bParametres.setIcon( IconsConstants.PARAMETRES_ICON_40_40 );
		
		bSeDeconnecter = new JButton("Se Déconnecter");
		bSeDeconnecter.setIcon(IconsConstants.LOGOUT_ICON_40_40);
		
		bQuitter = new JButton("Quitter");
		bQuitter.setIcon(IconsConstants.EXIT_ICON_40_40);
		
		this.setBorder(BorderFactory.createEtchedBorder());
	}
	
	/*
	 * **********************************************************************
	 * G�rer la dispoistion des composants internes ....
	 * **********************************************************************
	 */
	private void layoutUI(){
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addGroup( layout.createParallelGroup()
						.addComponent(bStatistiques, 100, 100, Short.MAX_VALUE)
						.addComponent(bGererEtage, 100, 100, Short.MAX_VALUE)
						.addComponent(bGererPlaceParking, 100, 100, Short.MAX_VALUE)
						.addComponent(bGererUtilisateur, 100, 100, Short.MAX_VALUE)
						.addComponent(bParametres, 100, 100, Short.MAX_VALUE)
						.addComponent(bSeDeconnecter, 100, 100, Short.MAX_VALUE)
						.addComponent(bQuitter, 100, 100, Short.MAX_VALUE)
				)
				.addGap(3)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addComponent(bStatistiques, 60, 60, 60)
				.addGap(5)
				.addComponent(bGererUtilisateur, 60, 60, 60)
				.addGap(5)
				.addComponent(bGererEtage, 60, 60, 60)
				.addGap(5)
				.addComponent(bGererPlaceParking, 60, 60, 60)
				.addGap(5)
				.addComponent(bParametres, 60, 60, 60)
				.addGap(5, 5, Short.MAX_VALUE)
				.addComponent(bSeDeconnecter, 60, 60, 60)
				.addGap(5)
				.addComponent(bQuitter, 60, 60, 60)
				.addGap(3)
		);
	}
	
	/*
	 * **********************************************************************
	 * Traiter les evenements des composants internes ....
	 * **********************************************************************
	 */
	private void handleEvents() {
		this.bGererUtilisateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bGererUtilisateurActionPerformed();
			}
		});

		this.bGererEtage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bGererEtageActionPerformed();
			}
		});

		this.bGererPlaceParking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bGererPlaceParkingActionPerformed();
			}
		});
		
		this.bSeDeconnecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seDeconnecter();
			}
		});
		
		this.bQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitter();
			}
		});
	}
	
	private void bGererUtilisateurActionPerformed() {
		FenetreAccueilAdmin.getSingleton().showPanelCRUDUtlisateur();
	}
	
	private void bGererEtageActionPerformed() {
		FenetreAccueilAdmin.getSingleton().showPanelCRUDEtage();
	}

	private void bGererPlaceParkingActionPerformed() {
		FenetreAccueilAdmin.getSingleton().showPanelCRUDPlaceParking();
	}
	
	private void seDeconnecter(){
		int reponse = JOptionPane.showConfirmDialog( SwingUtilities.getWindowAncestor(this) , "Voullez-vous fermer la session ?", "Se déconnecter ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (reponse == JOptionPane.YES_OPTION) {
			FenetreAccueilAdmin.getSingleton().setVisible(false);
			
			LoginFrame.setUser(null);
			LoginFrame.getSingleton().setVisible(true);
		}
	}
	
	private void quitter() {
		FenetreAccueilAdmin.getSingleton().fenetreAccueilClosing();
	}
}
