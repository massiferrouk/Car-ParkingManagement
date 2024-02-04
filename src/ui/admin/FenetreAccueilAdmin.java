package ui.admin;

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

public class FenetreAccueilAdmin extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final String ROOT_TITLE = "Parking Auto : Espace Administrateur....";
	
	/*
	 * **********************************************************************
	 * Definir les composants internes : bouttons, zones de text ...
	 * **********************************************************************
	 */
	private PanelLeftAdmin panelLeft;
	private JPanel	  panelContent;
	
	private PanelCRUDUtilisateur 	panelCRUDUtilisateur = null;
	private PanelCRUDEtage			panelCRUDEtage = null;
	private PanelCRUDPlaceParking   panelCRUDPlaceParking;
	
	//Singleton : une seul instance ....
	private static FenetreAccueilAdmin singleton = null;
	
	public static FenetreAccueilAdmin getSingleton() {
		if (singleton == null){
			singleton = new FenetreAccueilAdmin();
		}
		
		return singleton;
	}
	
//	Constructeur private : pas d'instanciation de l'exterieur : il faut passer par getSingleton
	private FenetreAccueilAdmin() {
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
		this.panelLeft = new PanelLeftAdmin();
		
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
	
	public PanelCRUDUtilisateur getPanelCRUDUtilisateur() {
		if (panelCRUDUtilisateur == null) {
			panelCRUDUtilisateur = new PanelCRUDUtilisateur();
			panelCRUDUtilisateur.updateData();
		}
		else {
			panelCRUDUtilisateur.getFormPanel().initFields();
		}
		
		return panelCRUDUtilisateur;
	}
	
	public PanelCRUDEtage getPanelCRUDEtage() {
		if (panelCRUDEtage == null) {
			panelCRUDEtage = new PanelCRUDEtage();
			panelCRUDEtage.updateData();
		}
		else {
			panelCRUDEtage.getFormPanel().initFields();
		}
		
		return panelCRUDEtage;
	}
	
	public PanelCRUDPlaceParking getPanelCRUDPlaceParking() {
		if (panelCRUDPlaceParking == null) {
			panelCRUDPlaceParking = new PanelCRUDPlaceParking();
			panelCRUDPlaceParking.updateData();
		}
		else {
			panelCRUDPlaceParking.getFormPanel().initFields();
		}
		
		return panelCRUDPlaceParking;
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
	
	public void showPanelCRUDUtlisateur() {
		showPanel( getPanelCRUDUtilisateur(), ROOT_TITLE+"  [ Gestion des Utilisateurs ]" );
	}

	public void showPanelCRUDEtage() {
		showPanel( getPanelCRUDEtage(), ROOT_TITLE+"  [ Gestion des Etages ]"  );
	}
	
	public void showPanelCRUDPlaceParking() {
		showPanel( getPanelCRUDPlaceParking(), ROOT_TITLE+"  [ Gestion des Places parking ]" );
	}
	
	public static void showFrame() {
		getSingleton().setVisible(true);
	}
}
