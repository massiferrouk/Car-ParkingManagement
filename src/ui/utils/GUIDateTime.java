/**
 * 
 */
package ui.utils;

import java.awt.Window;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.utils.GUIDate;
import gui.utils.GUITime;

public class GUIDateTime extends JPanel {
	private static final long serialVersionUID = 1L;

	private GUIDate 		tfDate;
	private GUITime 		tfTime;
	
	private int widthTfDate = 170;
	private int widthTfTime = 90;
	
	/**
	 * 
	 */
	public GUIDateTime() {
		initComponents();
		layoutUI();
	}
	
	private void initComponents() {
		tfDate = new GUIDate();
		
		tfDate.setOwner( SwingUtilities.getWindowAncestor(this) );
		tfDate.setTypeComponent( GUIDate.TYPE_COMPONENT_TEXTFIELD );
		
		tfTime = new GUITime();
		tfTime.setComboBoxMode(false);
	}
	
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		this.tfDate.setEnabled(enabled);
		this.tfTime.setEnabled(enabled);
	}
	
	private void layoutUI() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		updateLayout();
	}
	
	private void updateLayout() {
		this.removeAll();
		GroupLayout layout = (GroupLayout)this.getLayout();
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGap(5)
				.addComponent(tfDate, widthTfDate, widthTfDate, widthTfDate)
				.addGap(5)
				.addComponent(tfTime, widthTfTime, widthTfTime, widthTfTime)
				.addGap(5)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(0, 0, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup()
						.addComponent(tfDate, 23, 23, 23)
						.addComponent(tfTime, 23, 23, 23)
				)
				.addGap(0, 0, Short.MAX_VALUE)
		);
	}
	
	/**
	 * @param widthTfDate the widthTfDate to set
	 */
	public void setWidthTfDate(int widthTfDate) {
		this.widthTfDate = widthTfDate;
		updateLayout();
	}
	
	/**
	 * @param widthTfTime the widthTfTime to set
	 */
	public void setWidthTfTime(int widthTfTime) {
		this.widthTfTime = widthTfTime;
		updateLayout();
	}
	
	public void setOwner(Window owner) {
		this.tfDate.setOwner(owner);
	}
	
	//yyyy-mm-dd hh:mm:ss
	public String getDateTime() {
		String datetime = tfDate.getMySQLDate()+" "+tfTime.getTime();
		
		return datetime;
	}
	
	public void setDateTime(String datetime) {
		String parts[] = datetime.split(" ");
		tfDate.setMySQLDate(parts[0]);
		tfTime.setTimeAsString(parts[1]);
	}
	
	public java.sql.Timestamp getTimeStamp(){
		return utils.DateUtils.getTimestampFromString( this.getDateTime() );
	}
	
	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.setDateTime( timestamp.toString() );
	}
	
	public void clear() {
		this.tfDate.clear();
		this.tfTime.clear();
	}
}
