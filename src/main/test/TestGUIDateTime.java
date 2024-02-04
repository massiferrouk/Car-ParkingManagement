/**
 * 
 */
package main.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import ui.utils.GUIDateTime;

public class TestGUIDateTime {
	public static void main(String[] args) {
		GUIDateTime guiDateTime = new GUIDateTime();
		JButton bSetTime, bGetTime;
		
		JFrame frame = new JFrame();
		
		bSetTime = new JButton("Heure actuelle ...");
		bGetTime = new JButton("Afficher l'heure ...");
		
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(guiDateTime);
		frame.getContentPane().add(bSetTime);
		frame.getContentPane().add(bGetTime);
		
		guiDateTime.setPreferredSize(new Dimension(500, 25));
		bSetTime.setPreferredSize(new Dimension(150, 25));
		bGetTime.setPreferredSize(new Dimension(150, 25));
		
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		bGetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println( guiDateTime.getDateTime() );
			}
		});

		bSetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dateTime = utils.DateUtils.getDateTimeOfThisMoment();
				guiDateTime.setDateTime(dateTime);
				
				System.out.println(dateTime);
			}
		});
	}
}
