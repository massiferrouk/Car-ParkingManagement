package main.test.ui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.admin.FenetreAccueilAdmin;

public class TestFenetreAccueil {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(com.alee.laf.WebLookAndFeel.class.getName());
					
//					for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//						System.out.println(info.getName()+"  ==>  "+info.getClassName());
						
						//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//						UIManager.setLookAndFeel(com.alee.laf.WebLookAndFeel.class.getName());
						
//		                if ("Nimbus".equals(info.getName())) {
//		                    UIManager.setLookAndFeel(info.getClassName());
//		                    break;
//		                }
//		            }
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				FenetreAccueilAdmin.showFrame();
			}
		});
	}
}


/*
 La liste des look and feels
		javax.swing.UIManager.getCrossPlatformLookAndFeelClassName()
		javax.swing.UIManager.getSystemLookAndFeelClassName()
		"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		"com.sun.java.swing.plaf.motif.MotifLookAndFeel"
		com.easynth.lookandfeel.EaSynthLookAndFeel.class.getName()
		net.infonode.gui.laf.InfoNodeLookAndFeel.class.getName()
		com.nilo.plaf.nimrod.NimRODLookAndFeel.class.getName()
		smoothmetal.SmoothLookAndFeel.class.getName()
		com.digitprop.tonic.TonicLookAndFeel.class.getName()
		com.alee.laf.WebLookAndFeel.class.getName()
		
		
		
		SubstanceAutumnLookAndFeel.class.getName()
		SubstanceBusinessLookAndFeel.class.getName()
		SubstanceBusinessBlackSteelLookAndFeel.class.getName()
		SubstanceBusinessBlueSteelLookAndFeel.class.getName()
		SubstanceChallengerDeepLookAndFeel.class.getName()
		SubstanceCremeCoffeeLookAndFeel.class.getName()
		SubstanceCremeLookAndFeel.class.getName()
		SubstanceDustLookAndFeel.class.getName()
		SubstanceDustCoffeeLookAndFeel.class.getName()
		SubstanceDustCoffeeLookAndFeel.class.getName()
		SubstanceEmeraldDuskLookAndFeel.class.getName()
		SubstanceLegacyDefaultLookAndFeel.class.getName()
		SubstanceLookAndFeel.class.getName()
		SubstanceMagmaLookAndFeel.class.getName()
		SubstanceMistAquaLookAndFeel.class.getName()
		SubstanceMistSilverLookAndFeel.class.getName()
		SubstanceNebulaLookAndFeel.class.getName()
		SubstanceNebulaBrickWallLookAndFeel.class.getName()
		SubstanceModerateLookAndFeel.class.getName()
		SubstanceOfficeBlue2007LookAndFeel.class.getName()
		SubstanceOfficeSilver2007LookAndFeel.class.getName()
		SubstanceRavenLookAndFeel.class.getName()
		SubstanceRavenGraphiteLookAndFeel.class.getName()
		SubstanceRavenGraphiteGlassLookAndFeel.class.getName()
		SubstanceSaharaLookAndFeel.class.getName()
		SubstanceTwilightLookAndFeel.class.getName()
*/