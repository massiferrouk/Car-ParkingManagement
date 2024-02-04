package ui.agent;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.utils.IconsConstants;

public class FenetreAccueilAgent extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final String ROOT_TITLE = "Parking Auto : Espace Agent de Réservation ....";
	
	/*
	 * **********************************************************************
	 * Definir les composants internes : bouttons, zones de text ...
	 * **********************************************************************
	 */
	private PanelLeftAgent panelLeft;
	private JPanel	  panelContent;
	
	private PanelCRUDVehicule			panelCRUDVehicule = null;
	private PanelCRUDOperationParking 	panelCRUDOperationParking = null;
	
	//Singleton : une seul instance ....
	private static FenetreAccueilAgent singleton = null;
	
	public static FenetreAccueilAgent getSingleton() {
		if (singleton == null){
			singleton = new FenetreAccueilAgent();
		}
		
		return singleton;
	}
	
//	Constructeur private : pas d'instanciation de l'exterieur : il faut passer par getSingleton
	private FenetreAccueilAgent() {
		initUI();
		layoutUI();
		handleEvents();
		
		this.closePanelContent();
	}
	
	/*
	 * **********************************************************************
	 * Instancier les composants internes 
	 * **********************************************************************
	 */
	private void initUI(){
		this.panelLeft = new PanelLeftAgent();
		
		this.panelContent = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				int x = Math.max( (this.getWidth() - IconsConstants.BACKGROUND_IMG_ICON.getIconWidth()) / 2, 0);
				int y = Math.max( (this.getHeight() - IconsConstants.BACKGROUND_IMG_ICON.getIconHeight()) / 2, 0);
				
				g.drawImage(IconsConstants.BACKGROUND_IMG_ICON.getImage(), x, y, null);
			}
		};
		
		this.panelContent.setBorder(BorderFactory.createEtchedBorder());
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	/*
	 * **********************************************************************
	 * G�rer la dispoistion des composants internes ....
	 * **********************************************************************
	 */
	private void layoutUI(){
		Container zi = this.getContentPane(); // zi : zone interne de la fenetre
		GroupLayout layout = new GroupLayout(zi);
		zi.setLayout(layout);
		
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(panelLeft, 250, 250, 250)
				.addComponent(panelContent, 300, 300, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(5)
				.addGroup(layout.createParallelGroup()
						.addComponent(panelLeft, 300, 300, Short.MAX_VALUE)
						.addComponent(panelContent, 300, 300, Short.MAX_VALUE)
				)
				.addGap(5)
		);
		
		this.pack();
		this.setMinimumSize( this.getSize() );
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		GroupLayout layoutPContent = new GroupLayout(this.panelContent);
		this.panelContent.setLayout(layoutPContent);
	}
	
	/*
	 * **********************************************************************
	 * Traiter les evenements des composants internes ....
	 * **********************************************************************
	 */
	private void handleEvents(){
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				fenetreAccueilClosing();
			}
		});
		
	}

	public void fenetreAccueilClosing() {
		int reponse = JOptionPane.showConfirmDialog(this, "Voullez-vous quitter l'application ?", "Quitter ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (reponse == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public PanelCRUDVehicule getPanelCRUDVehicule() {
		if (panelCRUDVehicule == null) {
			panelCRUDVehicule = new PanelCRUDVehicule();
			panelCRUDVehicule.updateData();
		}
		else {
			panelCRUDVehicule.getFormPanel().initFields();
		}
		
		return panelCRUDVehicule;
	}
	
	public PanelCRUDOperationParking getPanelCRUDOperationParking() {
		if (panelCRUDOperationParking == null) {
			panelCRUDOperationParking = new PanelCRUDOperationParking();
			panelCRUDOperationParking.updateData();
		}
		else {
			panelCRUDOperationParking.getFormPanel().initFields();
		}
		
		return panelCRUDOperationParking;
	}
	
	public void closePanelContent() {	
		this.showPanel(null, ROOT_TITLE + " [Accueil]");
	}
	
	public void showPanel(JPanel panel, String title) {
		this.panelContent.removeAll();
		this.panelContent.updateUI();
		
		this.setTitle(title);
		
		if (panel == null) {
			return;
		}
		
		GroupLayout layout = (GroupLayout)panelContent.getLayout();
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addComponent(panel, 300, 300, Short.MAX_VALUE)
				.addGap(3)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addComponent(panel, 300, 300, Short.MAX_VALUE)
				.addGap(3)
		);
	}
	
	public void showPanelCRUDVehicule() {
		showPanel( getPanelCRUDVehicule(), ROOT_TITLE+"  [ Gestion des Vehicules ]"  );
	}
	
	public void showPanelCRUDOperationParking() {
		showPanel( getPanelCRUDOperationParking(), ROOT_TITLE+"  [ Reserver des emplacements de Parking ... ]" );
	}
	
	public static void showFrame() {
		getSingleton().setVisible(true);
	}
}
