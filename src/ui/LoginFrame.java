
package ui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import modele.beans.ABean;
import modele.beans.Utilisateur;
import modele.daos.DAOUtilisateur;

/**
 *
 * @author MASSI
 */
public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static LoginFrame singleton = null;
	
	private static Utilisateur user = null;
	
	private JButton bLogin;
    private JLabel lFermerX;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPasswordField pwfMotDePasse;
    private JTextField tfLogin;
	
    
	public static LoginFrame getSingleton() {
		if (singleton == null) {
			singleton = new LoginFrame();
		}
		
		return singleton;
	}
	
    private LoginFrame() {
        initAndLayoutComponents();
        handleEvents();
    }

    //**********************************************************
    //Creer les composants internes et gerer la disponsition
    //**********************************************************
    private void initAndLayoutComponents() {
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        jLabel4 = new JLabel();
        jPanel1 = new JPanel();
        lFermerX = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        tfLogin = new JTextField();
        jLabel8 = new JLabel();
        pwfMotDePasse = new JPasswordField();
        jLabel11 = new JLabel();
        jLabel12 = new JLabel();
        bLogin = new JButton();

        jLabel9.setIcon(new ImageIcon(getClass().getResource("/resources/photos/username.png"))); // NOI18N
        jLabel9.setText("jLabel9");

        jLabel10.setText("jLabel10");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(102, 102, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Parking Automobile");
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 280, 30));

        jLabel4.setIcon(new ImageIcon(getClass().getResource("/resources/photos/parking4.jpg"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -4, 430, 470));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 460));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lFermerX.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lFermerX.setForeground(new java.awt.Color(255, 255, 255));
        lFermerX.setText("X");
        lFermerX.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        
        jPanel1.add(lFermerX, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 20, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Password :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 100, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Welcome !");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 45, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Login To Your Account");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 210, 30));

        tfLogin.setBackground(new java.awt.Color(102, 102, 255));
        tfLogin.setToolTipText("");
        tfLogin.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jPanel1.add(tfLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 190, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Username :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 100, 30));

        pwfMotDePasse.setBackground(new java.awt.Color(102, 102, 255));
        pwfMotDePasse.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jPanel1.add(pwfMotDePasse, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 190, 30));

        jLabel11.setIcon(new ImageIcon(getClass().getResource("/resources/photos/icons8_Secure_50px.png"))); // NOI18N
        jLabel11.setText("jLabel11");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 50, -1));

        jLabel12.setIcon(new ImageIcon(getClass().getResource("/resources/photos/icons8_Account_50px.png"))); // NOI18N
        jLabel12.setText("jLabel11");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 50, -1));

        bLogin.setText("Login");
        jPanel1.add(bLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 80, 40));
        bLogin.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );

        this.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 350, 460));

        this.getRootPane().setDefaultButton(bLogin);
        
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    
    //**********************************************************
    //Gerer les �v�nements : click ordinaire, souris , clavier ...
    //**********************************************************
    private void handleEvents(){
    	lFermerX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lFermerXMouseClicked(evt);
            }
        });
    	
    	
		bLogin.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bLoginActionPerformed(e);
			}
		} );
    }

    //**********************************************************
    //Traitement des �v�nements ....
    //**********************************************************
    
    //Traitement de l'�v�nemet click sur le bouton bLogin
    @SuppressWarnings("deprecation")
    private void bLoginActionPerformed(ActionEvent e) {
    	if (tfLogin.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(this, "Veuillez remplir le champs 'Login' ....", "Erreur Champs vide", JOptionPane.ERROR_MESSAGE);
    		tfLogin.requestFocus();
    		return; //quitter la fonction ...
    	}
    	
    	if (pwfMotDePasse.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(this, "Veuillez remplir le champs 'Mot de passe' ....", "Erreur Champs vide", JOptionPane.ERROR_MESSAGE);
    		pwfMotDePasse.requestFocus();
    		return;//quitter la fonction ...
    	}
    	
    	//Une fois les champs ne sont pas vides ...
    	
    	String login = tfLogin.getText();
		String motDepasse = pwfMotDePasse.getText();
    	
    	user = DAOUtilisateur.authentifier(login, motDepasse);
    	
    	if (user == null) {
    		System.out.println("Erreur d'authentification ...");
    		JOptionPane.showMessageDialog(this, "Erreur d'authentification (votre login et/ou mot de passe est incorrect", "Erreur d'Acces....", JOptionPane.ERROR_MESSAGE);
    	}
    	else {//Autentification ....
    		System.out.println("Authentification Reussi...");
    		this.pwfMotDePasse.setText("");
    		this.tfLogin.setText("");
    		
    		this.tfLogin.requestFocus();
    		
    		//JOptionPane.showMessageDialog(this, "Authentification reussie", "Acces avec succes", JOptionPane.INFORMATION_MESSAGE);
    		if (ABean.isAdministrateur( user.getRole() )) {
    			ui.admin.FenetreAccueilAdmin.showFrame();
    			this.setVisible(false);
    		}
    		else if (ABean.isAgent( user.getRole() ) ) {
    			ui.agent.FenetreAccueilAgent.showFrame();
    			this.setVisible(false);
    		}
    		else {
    			JOptionPane.showMessageDialog(this, "Erreur, vous n'avez pas de role dans l'application ....", "Pas de droit d'acces ...", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }
    
    private void lFermerXMouseClicked(java.awt.event.MouseEvent evt) {
    	quitter();
    }
    
    private void quitter() {
    	int reponse = JOptionPane.showConfirmDialog(this, "Voullez-vous quitter l'application ?", "Quitter ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (reponse == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }
    
	public static Utilisateur getUser() {
		return user;
	}

	public static void setUser(Utilisateur user) {
		LoginFrame.user = user;
	}
}
