package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.LoginFrame;
import ui.admin.FenetreAccueilAdmin;
import utils.DBConnector;

public class ProjetParking {
	public static void main(String[] args) {
		DBConnector.setDebug(true);
		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "");
//		DBConnector.getSingleton("localhost", 3306, "parking_db", "root", "root");
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				try {
//					UIManager.setLookAndFeel(com.alee.laf.WebLookAndFeel.class.getName());
					
		            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		                if ("Nimbus".equals(info.getName())) {
		                    UIManager.setLookAndFeel(info.getClassName());
		                    break;
		                }
		            }
				}
				catch(Exception e){
				}
				
//		        } catch (ClassNotFoundException ex) {
//		            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        } catch (InstantiationException ex) {
//		            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        } catch (IllegalAccessException ex) {
//		            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        } catch (UnsupportedLookAndFeelException ex) {
//		            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		        }
				
				//LoginFrame.getSingleton().setVisible(true);
				FenetreAccueilAdmin.showFrame();
			}
		});
	}
}
