package ui.agent;

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

public class PanelLeftAgent extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*
	 * **********************************************************************
	 * Definir les composants internes : bouttons, zones de text ...
	 * **********************************************************************
	 */
	
	private JButton bGererVehicule;
	private JButton bGererOperationParking;
	
	private JButton bParametres;
	private JButton	bSeDeconnecter;
	private JButton bQuitter;
	
	
	public PanelLeftAgent() {
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
		bGererVehicule = new JButton("Vehicule");
		bGererVehicule.setIcon( IconsConstants.CAR_ICON_40);
		
		bGererOperationParking = new JButton("Operation Parking");
		bGererOperationParking.setIcon( IconsConstants.OPERATION_PARKING_ICON_40_40 );
			
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
						.addComponent(bGererVehicule, 100, 100, Short.MAX_VALUE)
						.addComponent(bGererOperationParking, 100, 100, Short.MAX_VALUE)
						.addComponent(bParametres, 100, 100, Short.MAX_VALUE)
						.addComponent(bSeDeconnecter, 100, 100, Short.MAX_VALUE)
						.addComponent(bQuitter, 100, 100, Short.MAX_VALUE)
				)
				.addGap(3)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addComponent(bGererVehicule, 60, 60, 60)
				.addGap(5)
				.addComponent(bGererOperationParking, 60, 60, 60)
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
		this.bGererVehicule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bGererVehiculeActionPerformed();
			}
		});
		
		this.bGererOperationParking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bGererOperationParkingActionPerformed();
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
	
	private void bGererVehiculeActionPerformed() {
		FenetreAccueilAgent.getSingleton().showPanelCRUDVehicule();
	}
	
	private void bGererOperationParkingActionPerformed() {
		FenetreAccueilAgent.getSingleton().showPanelCRUDOperationParking();
	}
	
	private void seDeconnecter(){
		int reponse = JOptionPane.showConfirmDialog( SwingUtilities.getWindowAncestor(this) , "Voullez-vous fermer la session ?", "Se déconnecter ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (reponse == JOptionPane.YES_OPTION) {
			FenetreAccueilAgent.getSingleton().setVisible(false);
			
			LoginFrame.setUser(null);
			LoginFrame.getSingleton().setVisible(true);
		}
	}
	
	private void quitter() {
		FenetreAccueilAgent.getSingleton().fenetreAccueilClosing();
	}
}
