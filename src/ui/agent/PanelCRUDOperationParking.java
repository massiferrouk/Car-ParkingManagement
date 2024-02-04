package ui.agent;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import gui.utils.GUIFieldFactory;
import gui.utils.GUIPanelFactory.JEditedComboBox;
import mdlaf.shadows.RoundedCornerBorder;
import modele.beans.OperationParking;
import modele.beans.PlaceParking;
import modele.beans.Utilisateur;
import modele.beans.Vehicule;
import modele.daos.DAOOperationParking;
import modele.daos.DAOPlaceParking;
import modele.daos.DAOUtilisateur;
import modele.daos.DAOVehicule;
import ui.LoginFrame;
import ui.utils.GUIDateTime;
import ui.utils.GUIPagination;
import ui.utils.IconsConstants;
import ui.utils.UIUtils;

/**
 *
 */


public class PanelCRUDOperationParking extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private static class ActionCommands {
		public static final String AC_ADD_ITEM 		= "AC_ADD_ITEM";
		public static final String AC_EDIT_ITEM 	= "AC_EDIT_ITEM";
		public static final String AC_DELETE_ITEM 	= "AC_DELETE_ITEM";
		public static final String AC_INFO_ITEM 	= "AC_INFO_ITEM";
		public static final String AC_EXPORT_EXCEL	= "AC_EXPORT_EXCEL";
		public static final String AC_EXPORT_PDF	= "AC_EXPORT_PDF";
		public static final String AC_SEARCH_ITEM 	= "AC_SEARCH_ITEM";
		public static final String AC_CLOSE 		= "AC_CLOSE";
	}
	
	private static final String ALL_FIELDS = "Tous";
	
	private JButton bAdd, bEdit, bDelete, bInfo;
	private JButton	bExportPDF, bExportExcel;
	private JButton bClose;
	
	private JSeparator sepTop, sepBottom;
	
	private JTextField 			tfSearch;
	private JButton				bSearch;
	
	private JComboBox<String>	cbSearchFields;
	
	private JCheckBox			chbInstantaniousSearch;
	
	private JPanel		pContent;
	
	private JScrollPane	 spTableItems;
	private MyTableModel tableModel;
	private JTable		 tableItems;
	List<OperationParking> listItems = null;
	
	private JPopupMenu	pmListItem;
	private JMenuItem	miAdd, miEdit, miDelete, miInfo;
	private JMenuItem	miExportExcel, miExportPDF;
	
	private GUIPagination	guiPagination;
	
	private JLabel	lTitleDetail;
	private boolean uiDetail = false;
	private UIDetailInterface	uiDetailInterface = null;
	private Object itemDetail = null;
	
	private FormPanel formPanel;
	
	/**
	 *
	 */
	public PanelCRUDOperationParking() {
		initUI();
		layoutUI();
		handleEvents();
	}
	
	/**
	 * Instancier les composants internes
	 */
	private void initUI() {
		Border brdButtons = BorderFactory.createLineBorder(Color.BLUE);
		
		bAdd = new JButton( IconsConstants.ADD_ICON_25_25 );
		bEdit = new JButton( IconsConstants.EDIT_ICON_25_25 );
		bDelete = new JButton( IconsConstants.DELETE_ICON_25_25 );
		bInfo = new JButton( IconsConstants.INFO_ICON_25_25 );
		bExportPDF = new JButton( IconsConstants.PDF_ICON_25_25 );
		bExportExcel = new JButton( IconsConstants.EXCEL_ICON_25_25 );
		
		bAdd.setBorder(brdButtons);
		bEdit.setBorder(brdButtons);
		bDelete.setBorder(brdButtons);
		bInfo.setBorder(brdButtons);
		bExportExcel.setBorder(brdButtons);
		bExportPDF.setBorder(brdButtons);
		
		bAdd.setToolTipText("Ajouter un nouveau ");
		bEdit.setToolTipText("Modifier les informations d'un ");
		bDelete.setToolTipText("Suppriemr / Désinscrire un ");
		bInfo.setToolTipText("Voir plus de détail ....");
		bExportPDF.setToolTipText("Exporter vers PDF");
		bExportExcel.setToolTipText("Exporter vers Excel");
		
		pmListItem = new JPopupMenu();
		miAdd = new JMenuItem("Ajouter", IconsConstants.ADD_ICON_25_25);
		miEdit = new JMenuItem("Modifier", IconsConstants.EDIT_ICON_25_25);
		miDelete = new JMenuItem("Supprimer", IconsConstants.DELETE_ICON_25_25);
		miInfo = new JMenuItem("Voir détail ....", IconsConstants.INFO_ICON_25_25);
		miExportPDF = new JMenuItem("Export PDF", IconsConstants.PDF_ICON_25_25);
		miExportExcel = new JMenuItem("Export Excel", IconsConstants.EXCEL_ICON_25_25);
		
		pmListItem.add(miAdd);
		pmListItem.add(miEdit);
		pmListItem.add(miInfo);
		pmListItem.addSeparator();
		pmListItem.add(miDelete);
		pmListItem.addSeparator();
		pmListItem.add(miExportPDF);
		pmListItem.add(miExportExcel);
		
		
		bClose = new JButton("Fermer" , IconsConstants.EXIT_ICON_25_25 );
		bClose.setMnemonic('F');
		
		sepTop = new JSeparator();
		sepBottom = new JSeparator();
		
		tfSearch = GUIFieldFactory.createSimpleTextField();
		bSearch = new JButton( IconsConstants.SEARCH_ICON_25_25 );
		bSearch.setBorder(brdButtons);
		bSearch.setToolTipText("Rechercher ....");
		
		cbSearchFields = new JComboBox<>( new String[] {ALL_FIELDS} );
		
		chbInstantaniousSearch = new JCheckBox("Recherche Instantanée");
		chbInstantaniousSearch.setSelected(true);
		
		pContent = new JPanel();
		pContent.setBorder( BorderFactory.createLineBorder(Color.BLUE) );
		
		
		tableModel = new MyTableModel("N°", "Vehicule", "Place Parking", "Entrée", "Sortie", "Observation");
		tableItems =  new JTable(tableModel) {
			private static final long serialVersionUID = 1L;
			
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
				Component returnComp = super.prepareRenderer(renderer, row, column);
				Color alternateColor = new Color(252,242,206);
				Color whiteColor = Color.WHITE;
				if (!returnComp.getBackground().equals(getSelectionBackground())){
					Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
					returnComp.setBackground(bg);
					bg = null;
				}
				return returnComp;
			}
		};
		
		tableItems.setRowHeight(25);
		
		tableItems.getColumnModel().getColumn(0).setMaxWidth(50);
		
		spTableItems = new JScrollPane(tableItems);
		spTableItems.getVerticalScrollBar().setUnitIncrement(20);
		
		tableItems.setComponentPopupMenu(pmListItem);
		
		guiPagination = new GUIPagination( new GUIPagination.IHandleUpdateState() {
			public void stateChanged() {
				updateData();
			}
		});
		
		this.setBorder( new RoundedCornerBorder() );
		
		this.lTitleDetail = new JLabel("");
		this.lTitleDetail.setVisible(false);
		
		formPanel = new FormPanel();
	}
	
	/**
	 * Gérer la dispoisiton ...
	 */
	private void layoutUI() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap(10)
						.addComponent(lTitleDetail)
						.addComponent(tfSearch, 200, 200, 200)
						.addComponent(bSearch, 25, 25, 25)
						.addComponent(cbSearchFields, 150, 150, 150)
						.addComponent(chbInstantaniousSearch)
						.addGap(100, 100, Short.MAX_VALUE)
						.addComponent(bAdd, 25, 25, 25)
						.addComponent(bEdit, 25, 25, 25)
						.addComponent(bInfo, 25, 25, 25)
						.addComponent(bDelete, 25, 25, 25)
						.addGap(25)
						.addComponent(bExportPDF, 25, 25, 25)
						.addComponent(bExportExcel, 25, 25, 25)
						.addGap(10)
				)
				.addGroup(layout.createSequentialGroup()
						.addGap(5)
						.addComponent(sepTop, 100, 100, Short.MAX_VALUE)
						.addGap(5)
				)
				.addGroup(layout.createSequentialGroup()
						.addGap(10)
						.addComponent(pContent, 100, 100, Short.MAX_VALUE)
						.addGap(10)
				)
				.addGroup(layout.createSequentialGroup()
						.addGap(5)
						.addComponent(sepBottom, 100, 100, Short.MAX_VALUE)
						.addGap(5)
				)
				.addGroup(layout.createSequentialGroup()
						.addGap(5)
						.addComponent(guiPagination, 450, 450, 450)
						.addGap(5, 5, Short.MAX_VALUE)
						.addComponent(bClose, 200, 200, 200)
						.addGap(5)
				)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(5)
				.addGroup(layout.createParallelGroup()
						.addComponent(lTitleDetail, 25, 25, 25)
						.addComponent(tfSearch, 25, 25, 25)
						.addComponent(bSearch, 25, 25, 25)
						.addComponent(cbSearchFields, 25, 25, 25)
						.addComponent(chbInstantaniousSearch, 25, 25, 25)
						.addComponent(bAdd, 25, 25, 25)
						.addComponent(bEdit, 25, 25, 25)
						.addComponent(bInfo, 25, 25, 25)
						.addComponent(bDelete, 25, 25, 25)
						.addComponent(bExportPDF, 25, 25, 25)
						.addComponent(bExportExcel, 25, 25, 25)
				)
				.addGap(3)
				.addComponent(sepTop, 2, 2, 2)
				.addGap(3)
				.addComponent(pContent, 100, 100, Short.MAX_VALUE)
				.addGap(3)
				.addComponent(sepBottom, 2, 2, 2)
				.addGap(5)
				.addGroup(layout.createParallelGroup()
						.addComponent(guiPagination, 30, 30, 30)
						.addComponent(bClose, 25, 25, 25)
				)
				.addGap(5)
		);
		
		GroupLayout layoutPContent = new GroupLayout(pContent);
		pContent.setLayout(layoutPContent);
		layoutPContent.setHorizontalGroup(layoutPContent.createSequentialGroup()
				.addGap(3)
				.addComponent(spTableItems, 100, 100, Short.MAX_VALUE)
				.addGap(3)
		);
		layoutPContent.setVerticalGroup(layoutPContent.createSequentialGroup()
				.addGap(3)
				.addComponent(spTableItems, 100, 100, Short.MAX_VALUE)
				.addGap(3)
		);
	}
	
	/**
	 * Traiter les évènements ...
	 */
	private void handleEvents() {
		bAdd.setActionCommand(ActionCommands.AC_ADD_ITEM);
		miAdd.setActionCommand(ActionCommands.AC_ADD_ITEM);
		
		bEdit.setActionCommand(ActionCommands.AC_EDIT_ITEM);
		miEdit.setActionCommand(ActionCommands.AC_EDIT_ITEM);
		
		bInfo.setActionCommand(ActionCommands.AC_INFO_ITEM);
		miInfo.setActionCommand(ActionCommands.AC_INFO_ITEM);
		
		bDelete.setActionCommand(ActionCommands.AC_DELETE_ITEM);
		miDelete.setActionCommand(ActionCommands.AC_DELETE_ITEM);
		
		bExportPDF.setActionCommand(ActionCommands.AC_EXPORT_PDF);
		miExportPDF.setActionCommand(ActionCommands.AC_EXPORT_PDF);
		
		bExportExcel.setActionCommand(ActionCommands.AC_EXPORT_EXCEL);
		miExportExcel.setActionCommand(ActionCommands.AC_EXPORT_EXCEL);
		
		bSearch.setActionCommand(ActionCommands.AC_SEARCH_ITEM);
		bClose.setActionCommand(ActionCommands.AC_CLOSE);
		
		bAdd.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bEdit.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bInfo.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bDelete.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bExportPDF.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bExportExcel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bSearch.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bClose.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleAllActions(e);
			}
		};
		
		bAdd.addActionListener( actionListener );
		bEdit.addActionListener( actionListener );
		bInfo.addActionListener( actionListener );
		bDelete.addActionListener( actionListener );
		bExportPDF.addActionListener( actionListener );
		bExportExcel.addActionListener( actionListener );
		bSearch.addActionListener( actionListener );
		bClose.addActionListener( actionListener );
		
		miAdd.addActionListener( actionListener );
		miEdit.addActionListener( actionListener );
		miInfo.addActionListener( actionListener );
		miDelete.addActionListener( actionListener );
		miExportPDF.addActionListener( actionListener );
		miExportExcel.addActionListener( actionListener );
		
		bInfo.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				infoItem();
			}
		});
		
		tfSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10 || chbInstantaniousSearch.isSelected()) {
					searchItem();
				}
			}
		});
		
		tableItems.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listItemsMouseClicked(e);
			}
		});
	}
	
	private void listItemsMouseClicked( MouseEvent e ) {
		if (e.getClickCount() >= 2) {
			editItem();
		}
	}
	
	private void handleAllActions(ActionEvent e) {
		switch ( e.getActionCommand() ) {
			case ActionCommands.AC_ADD_ITEM:{
				addItem();
				break;
			}
			case ActionCommands.AC_EDIT_ITEM:{
				editItem();
				break;
			}
			case ActionCommands.AC_INFO_ITEM:{
				//infoItem();
				break;
			}
			case ActionCommands.AC_DELETE_ITEM:{
				deleteItem();
				break;
			}
			case ActionCommands.AC_EXPORT_PDF:{
				exportPDF();
				break;
			}
			case ActionCommands.AC_EXPORT_EXCEL:{
				exportExcel();
				break;
			}
			case ActionCommands.AC_SEARCH_ITEM:{
				searchItem();
				break;
			}
			case ActionCommands.AC_CLOSE:{
				close();
				break;
			}
		}
	}
	
	private void addItem() {
		OperationParking item = new OperationParking();
		item.setDateHeureEntree( utils.DateUtils.getTimestampFromString( utils.DateUtils.getDateTimeOfThisMoment() ) );
		item.setDateHeureSortie( utils.DateUtils.getTimestampFromString( utils.DateUtils.getDateTimeOfThisMoment() ) );
		
		boolean validated = formPanel.showInDialog( SwingUtilities.getWindowAncestor(this), item );
		if (validated) {
			item = formPanel.getItem();
			
			tableModel.tableRows.add( getRow(item) );
			tableItems.updateUI();
		}
	}
	
	private void editItem() {
		if (uiDetail) {
			return;
		}
		
		int indice = tableItems.getSelectedRow();
		if (indice == -1) {
			return;
		}
		
		Vector<Object> row = tableModel.tableRows.get(indice);
		
		int num = (Integer)row.get(0);
		
		OperationParking item = (OperationParking)row.get(row.size()-1);
		
		if (item == null) {
			return;
		}
		
		boolean validated = formPanel.showInDialog(  SwingUtilities.getWindowAncestor(this), item );
		if (validated) {
			OperationParking otherItem = formPanel.getItem();
			tableModel.tableRows.set(indice, getRow(num, otherItem));
			
			tableItems.updateUI();
		}
	}
	
	private String generateInfo(OperationParking item) {
		String info = "<html>";
			
			
		info += "</html>";
		
		return info;
	}
	
	private void infoItem() {
		this.bInfo.setToolTipText("");
		//TODO : il faut ajouter du code
		
		int indiceRow = tableItems.getSelectedRow();
		if (indiceRow == -1) {
			return;
		}
		
		Vector<Object> row = tableModel.tableRows.get(indiceRow);
		
		OperationParking item = (OperationParking)row.get( row.size()-1 );
		if (item == null) {
			return;
		}
		
		this.bInfo.setToolTipText( generateInfo(item) );
		
//		MouseEvent mouseEvent = new MouseEvent(bInfo, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, 10, 10, 0, false);
//		ToolTipManager.sharedInstance().mouseMoved(mouseEvent);
	}
	
	private void deleteItem() {
		int indiceRow = tableItems.getSelectedRow();
		if (indiceRow == -1) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner un  !!!", "Sélectionner !", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int[] selectedRows = tableItems.getSelectedRows();
		
		String cetItem = selectedRows.length == 1 ? "ce " : "ces s";
		
		int reponse = JOptionPane.showConfirmDialog(this, "Voullez-vous supprimer "+cetItem+" ?", "Supprimer ?", JOptionPane.YES_NO_OPTION);
		if (reponse == JOptionPane.YES_OPTION) {
			Vector<Vector<Object>> rowsToDelete = new Vector<>();
			
			for (int indice : selectedRows) {
				Vector<Object> row = tableModel.tableRows.get(indice);
				OperationParking item = (OperationParking)row.get(row.size()-1);
				
				DAOOperationParking.delete(item);
				
				rowsToDelete.add(row);
			}
			
			tableModel.tableRows.removeAll(rowsToDelete);
			tableItems.updateUI();
			tableItems.clearSelection();
		}
	}
	
	private void exportPDF() {
		File file = UIUtils.createAndPrepareEmptyPDFFile(this, "list_operation_parkings");
		
		if (file == null) {
			return;
		}
		
//		List<OperationParking> listItems = DAOOperationParking.getList( this.getCondtion() );
//		ManualPDFCreator.PDFOperationParking.createPDFList(file, listItems);
	}
	
	private void exportExcel() {
//		File file = UIUtils.createAndPrepareEmptyExcelFile(this, "list_operation_parkings", UIUtils.PathResourceExcel.EXCEL_OPERATIONPARKINGS);
//		
//		if (file == null) {
//			return;
//		}
//		
//		try {
//			XLSXLSXCreator xlsx = new XLSXLSXCreator(file);
//			
//			List<OperationParking> listItems = DAOOperationParking.getList( this.getCondtion() );
//			
//			int baseRow = 12;
//			
//			for (int i=0; i<listItems.size(); i++) {
//				int numRow = i+baseRow;
//				
//				OperationParking item = listItems.get(i);
//				
//				xlsx.setCellValue("A"+numRow, utils.StringUtils.intToString(i+1, 2));
//				xlsx.setCellValue("B"+numRow,  item.getIdVehicule());
//				xlsx.setCellValue("C"+numRow,  item.getIdPlaceParking());
//				xlsx.setCellValue("D"+numRow,  item.getDateHeureEntree());
//				xlsx.setCellValue("E"+numRow,  item.getDateHeureSortie());
//				xlsx.setCellValue("F"+numRow,  item.getIdAgent1());
//				xlsx.setCellValue("G"+numRow,  item.getIdAgent2());
//				xlsx.setCellValue("H"+numRow,  item.getObservation());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	private void searchItem() {
		updateData();
	}
	
	private void close() {
		FenetreAccueilAgent.getSingleton().closePanelContent();
	}
	
	private String getCondtion() {
		String condition = "1";
		
		String txtFilter =  utils.StringUtils.addSQLSlashes( tfSearch.getText().trim() );
		
		String fields = (String)cbSearchFields.getSelectedItem();
		
		if (fields.equals(ALL_FIELDS)) {
			condition = " CONCAT(COALESCE(`idVehicule`,''), ' ', COALESCE(`idPlaceParking`,''), ' ', COALESCE(`date_heure_entree`,''), ' ', COALESCE(`date_heure_sortie`,''), ' ', COALESCE(`id_agent_1`,''), ' ', COALESCE(`id_agent_2`,''), ' ', COALESCE(`observation`,'')) like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `idVehicule` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `idPlaceParking` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `date_heure_entree` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `date_heure_sortie` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `id_agent_1` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `id_agent_2` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("")) {
			condition = " `observation` like '%"+txtFilter+"%' ";
		}
		
		if (uiDetail) {
			if (uiDetailInterface != null) {
				condition += " AND ( "+ uiDetailInterface.getCondition() +" )";
			}
		}
		
		return condition;
	}
	
	public String getLimits() {
		int first = guiPagination.getFirst();
		int count = guiPagination.getNbItemsInPage();
		
		return " LIMIT "+first+", "+count;
	}
	
	public void updateData(){
		tableModel.tableRows.clear();
		
		if (this.uiDetail && this.itemDetail==null) {
			return;
		}
		
		int count = (int)DAOOperationParking.getCountInTable ( this.getCondtion() );
		
		guiPagination.setNbTotalItems(count);
		
		listItems = DAOOperationParking.getList( this.getCondtion()+" "+this.getLimits() );
		
		int num=guiPagination.getFirst()+1;
		for (OperationParking item : listItems) {
			tableModel.tableRows.add( getRow(num, item) );
			
			num++;
		}
		
		tableItems.updateUI();
		tableItems.clearSelection();
	}
	
	private Vector<Object> getRow(OperationParking item){
		return getRow( tableModel.tableRows.size()+1, item );
	}
	
	private Vector<Object> getRow(int num, OperationParking item){
		Vector<Object> row = new Vector<>();
		
		row.add(num);
		row.add( DAOVehicule.getById(item.getIdVehicule()) );
		row.add( DAOPlaceParking.getById(item.getIdPlaceParking()) );
		row.add( item.getDateHeureEntree().toString() );
		row.add( item.getDateHeureSortie().toString() );
		row.add(item.getObservation());
		row.add( item );
		
		return row;
	}
	
	List<OperationParking> getListItems() {
		return listItems;
	}
	
	public JTable getTableItems() {
		return tableItems;
	}
	
	public FormPanel getFormPanel() {
		return formPanel;
	}
	
	public void setItemDetail(Object itemDetail) {
		this.itemDetail = itemDetail;
		
		this.bAdd.setEnabled( itemDetail != null );
		this.bEdit.setEnabled( itemDetail != null );
		this.bDelete.setEnabled( itemDetail != null );
		
		this.miAdd.setEnabled( itemDetail != null );
		this.miEdit.setEnabled( itemDetail != null );
		this.miDelete.setEnabled( itemDetail != null );
		
		this.tfSearch.setEnabled( itemDetail != null );
		this.bSearch.setEnabled( itemDetail != null );
		
		this.chbInstantaniousSearch.setEnabled( itemDetail != null );
	}
	
	public void setUiDetail(boolean uiDetail) {
		this.uiDetail = uiDetail;
		
		this.bExportExcel.setVisible( !this.uiDetail );
		this.bExportPDF.setVisible( !this.uiDetail );
		
		this.bEdit.setVisible( !this.uiDetail );
		this.bInfo.setVisible( !this.uiDetail );
		
		this.miExportExcel.setVisible( !this.uiDetail );
		this.miExportPDF.setVisible( !this.uiDetail );
		
		this.miEdit.setVisible( !this.uiDetail );
		this.miInfo.setVisible( !this.uiDetail );
		
		this.cbSearchFields.setVisible( !this.uiDetail );
		
		this.guiPagination.setVisible(! this.uiDetail );
		this.bClose.setVisible( !this.uiDetail );
		
		this.lTitleDetail.setVisible( this.uiDetail );
	}
	
	public void setUIDetailTitle(String uiDetailTitle){
		this.lTitleDetail.setText(uiDetailTitle);
	}
	
	public void setUiDetailInterface(UIDetailInterface uiDetailInterface) {
		this.uiDetailInterface = uiDetailInterface;
		
		if (uiDetailInterface != null) {
			uiDetailInterface.refactorUI();
		}
	}
	
	
	
	public static class FormPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		private JLabel lIdVehicule;
		private JLabel lIdPlaceParking;
		private JLabel lDateHeureEntree;
		private JLabel lDateHeureSortie;
		private JLabel lIdAgent1;
		private JLabel lIdAgent2;
		private JLabel lObservation;
		
		private JComboBox<Vehicule> cbIdVehicule;
		private JComboBox<PlaceParking> cbIdPlaceParking;
		private GUIDateTime tfDateHeureEntree;
		private GUIDateTime tfDateHeureSortie;
		private JComboBox<Utilisateur> cbIdAgent1;
		private JComboBox<Utilisateur> cbIdAgent2;
		private JTextField tfObservation;
		
		
		private JSeparator separator;
		
		private JButton	bValider;
		private JButton	bAnnuler;
		
		private JDialog dialog = null;
		
		private OperationParking item;
		private boolean formValidated = false;
		
		/**
		 *
		 */
		public FormPanel() {
			initUI();
			layoutUI();
			handleEvents();
			
			initFields();
		}
		
		@SuppressWarnings("unchecked")
		private void initUI() {
			lIdVehicule = new JLabel("Vehicule : ");
			lIdPlaceParking = new JLabel("Place de Parking : ");
			lDateHeureEntree = new JLabel("Entree : ");
			lDateHeureSortie = new JLabel("Sortie : ");
			lIdAgent1 = new JLabel(" : ");
			lIdAgent2 = new JLabel(" : ");
			lObservation = new JLabel("Observation : ");
			
			cbIdVehicule = new JEditedComboBox();
			cbIdPlaceParking = new JEditedComboBox();
			tfDateHeureEntree = new GUIDateTime();
			tfDateHeureSortie = new GUIDateTime();
			cbIdAgent1 = new JEditedComboBox();
			cbIdAgent2 = new JEditedComboBox();
			tfObservation = GUIFieldFactory.createSimpleTextField();
			
			tfDateHeureEntree.setOwner( SwingUtilities.getWindowAncestor(this) );
			tfDateHeureSortie.setOwner( SwingUtilities.getWindowAncestor(this) );
			
			separator = new JSeparator();
			
			bValider = new JButton("Valider");
			bValider.setMnemonic('V');
			
			bAnnuler = new JButton("Annuler");
			
			bAnnuler.setMnemonic('A');
		}
		
		/**
		 * Gérer la disposition ....
		 */
		private void layoutUI() {
			GroupLayout layout = new GroupLayout( this );
			this.setLayout( layout );
			
			/* Le balayage de gauche à droite */
			layout.setHorizontalGroup( layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
							.addGap(25, 25, Short.MAX_VALUE)
							.addGroup(layout.createParallelGroup()
									.addComponent(lIdVehicule)
									.addComponent(lIdPlaceParking)
									.addComponent(lDateHeureEntree)
									.addComponent(lDateHeureSortie)
//									.addComponent(lIdAgent1)
//									.addComponent(lIdAgent2)
									.addComponent(lObservation)
							)
							.addGap(5)
							.addGroup(layout.createParallelGroup()
									.addComponent(cbIdVehicule, 300, 300, 300)
									.addComponent(cbIdPlaceParking, 300, 300, 300)
									.addComponent(tfDateHeureEntree, 300, 300, 300)
									.addComponent(tfDateHeureSortie, 300, 300, 300)
//									.addComponent(cbIdAgent1, 300, 300, 300)
//									.addComponent(cbIdAgent2, 300, 300, 300)
									.addComponent(tfObservation, 300, 300, 300)
							)
							
							.addGap(25, 25, Short.MAX_VALUE)
					)
					
					.addGroup(layout.createSequentialGroup()
							.addGap(3)
							.addComponent(separator, 100, 100, Short.MAX_VALUE)
							.addGap(3)
					)
					
					.addGroup(layout.createSequentialGroup()
							.addGap(10)
							.addComponent(bValider, 120, 120, 120)
							.addGap(1, 1, Short.MAX_VALUE)
							.addComponent(bAnnuler)
							.addGap(10)
					)
			);
			
			/* Le balayage de haut en bas */
			layout.setVerticalGroup( layout.createSequentialGroup()
					.addGap(25, 25, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup()
							.addComponent(lIdVehicule, 25, 25, 25)
							.addComponent(cbIdVehicule, 25, 25, 25)
					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lIdPlaceParking, 25, 25, 25)
							.addComponent(cbIdPlaceParking, 25, 25, 25)
					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lDateHeureEntree, 25, 25, 25)
							.addComponent(tfDateHeureEntree, 25, 25, 25)
					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lDateHeureSortie, 25, 25, 25)
							.addComponent(tfDateHeureSortie, 25, 25, 25)
					)
//					.addGap(3)
//					.addGroup(layout.createParallelGroup()
//							.addComponent(lIdAgent1, 25, 25, 25)
//							.addComponent(cbIdAgent1, 25, 25, 25)
//					)
//					.addGap(3)
//					.addGroup(layout.createParallelGroup()
//							.addComponent(lIdAgent2, 25, 25, 25)
//							.addComponent(cbIdAgent2, 25, 25, 25)
//					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lObservation, 25, 25, 25)
							.addComponent(tfObservation, 25, 25, 25)
					)
					.addGap(25, 25, Short.MAX_VALUE)
					.addComponent(separator, 2, 2, 2)
					.addGap(5)
					.addGroup(layout.createParallelGroup()
							.addComponent(bValider)
							.addComponent(bAnnuler)
					)
					.addGap(5)
			);
			
			
			this.setSize(500, 400);
		}
		
		/**
		 * Traiter les évènements .....
		 */
		private void handleEvents() {
			bValider.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					bValiderActionPerformed();
				}
			});
			
			bAnnuler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					formValidated = false;
					close();
				}
			});
		}
		
		private void configureRootPane(JRootPane rootPane) {
			InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escPressed");
			rootPane.getActionMap().put("escPressed",
				new AbstractAction("escPressed") {
					private static final long serialVersionUID = 1L;
					
					public void actionPerformed(ActionEvent actionEvent) {
						escapePressed();
					}
				}
			);
		}
		
		private void escapePressed() {
			this.close();
		}
		
		private void bValiderActionPerformed() {
			java.lang.Integer idVehicule = 0;
			java.lang.Integer idPlaceParking = 0;
			java.sql.Timestamp dateHeureEntree = tfDateHeureEntree.getTimeStamp();
			java.sql.Timestamp dateHeureSortie = tfDateHeureSortie.getTimeStamp();
			java.lang.String observation = tfObservation.getText().trim();
			
			Vehicule itemVehicule = cbIdVehicule.getSelectedItem() == null ? null : (Vehicule)(cbIdVehicule.getSelectedItem()) ;
			idVehicule = itemVehicule == null ? 0 : itemVehicule.getId();
			
			PlaceParking itemPlaceParking = cbIdPlaceParking.getSelectedItem() == null ? null : (PlaceParking)(cbIdPlaceParking.getSelectedItem()) ;
			idPlaceParking = itemPlaceParking == null ? 0 : itemPlaceParking.getId();
			
			if (idVehicule == 0) {
				JOptionPane.showMessageDialog(this, "Veuillez selectionner le vehicule !!!", "Champs vide !", JOptionPane.ERROR_MESSAGE);
				cbIdVehicule.requestFocus();
				return;
			}
			
			if (idPlaceParking == 0) {
				JOptionPane.showMessageDialog(this, "Veuillez selectionner le l'emplacmenent de parking !!!", "Champs vide !", JOptionPane.ERROR_MESSAGE);
				cbIdPlaceParking.requestFocus();
				return;
			}
			
			if (dateHeureEntree.equals("")) {
				JOptionPane.showMessageDialog(this, "Veuillez rensiegner le champs 'Date et Heure d'entree' !!!", "Champs vide !", JOptionPane.ERROR_MESSAGE);
				tfDateHeureEntree.requestFocus();
				return;
			}
			
			if (dateHeureSortie.equals("")) {
				JOptionPane.showMessageDialog(this, "Veuillez rensiegner le champs 'Date et Heure de sortie' !!!", "Champs vide !", JOptionPane.ERROR_MESSAGE);
				tfDateHeureSortie.requestFocus();
				return;
			}
			
			if ((itemPlaceParking.isOccupe() && item.getId()==0) || (itemPlaceParking.isOccupe() && item.getIdPlaceParking() != idPlaceParking)) {
				JOptionPane.showMessageDialog(this, "Emplacement de parking occupe !!!\nVeuillez selectionner un autre emplacement !!!", "Emplacement occupe !", JOptionPane.ERROR_MESSAGE);
				cbIdPlaceParking.requestFocus();
				return;
			}
			
//			if (observation.equals("")) {
//				JOptionPane.showMessageDialog(this, "Veuillez rensiegner le champs '' !!!", "Champs vide !", JOptionPane.ERROR_MESSAGE);
//				tfObservation.requestFocus();
//				return;
//			}
			
			item.setIdVehicule(idVehicule);
			item.setIdPlaceParking(idPlaceParking);
			item.setDateHeureEntree(dateHeureEntree);
			item.setDateHeureSortie(dateHeureSortie);
			item.setObservation(observation);
			
			if (item.getId() > 0) {
				item.setIdAgent2( LoginFrame.getUser().getId() );
				DAOOperationParking.update(item);
			}
			else {
				item.setIdAgent1( LoginFrame.getUser().getId() );
				item.setIdAgent2( 0 );
				DAOOperationParking.add(item);	
			}
			
			formValidated = true;
			
			this.close();
		}
		
		private void selectCbVehicule(int idItem) {
			int selectedIndex = -1;
			
			for (int i=0; i<cbIdVehicule.getItemCount(); i++) {
				if (cbIdVehicule.getItemAt(i).getId() == idItem) {
					selectedIndex = i;
					break;
				}
			}
			
			cbIdVehicule.setSelectedIndex(selectedIndex);
		}
		
		private void selectCbPlaceParking(int idItem) {
			int selectedIndex = -1;
			
			for (int i=0; i<cbIdPlaceParking.getItemCount(); i++) {
				if (cbIdPlaceParking.getItemAt(i).getId() == idItem) {
					selectedIndex = i;
					break;
				}
			}
			
			cbIdPlaceParking.setSelectedIndex(selectedIndex);
		}
		
		private void selectCbAgent1(int idItem) {
			int selectedIndex = -1;
			
			for (int i=0; i<cbIdAgent1.getItemCount(); i++) {
				if (cbIdAgent1.getItemAt(i).getId() == idItem) {
					selectedIndex = i;
					break;
				}
			}
			
			cbIdAgent1.setSelectedIndex(selectedIndex);
		}
		
		private void selectCbAgent2(int idItem) {
			int selectedIndex = -1;
			
			for (int i=0; i<cbIdAgent2.getItemCount(); i++) {
				if (cbIdAgent2.getItemAt(i).getId() == idItem) {
					selectedIndex = i;
					break;
				}
			}
			
			cbIdAgent2.setSelectedIndex(selectedIndex);
		}
		
		private void close() {
			if (this.dialog != null) {
				this.dialog.setVisible(false);
			}
		}
		
		/**
		 * @param item the item to set
		 */
		public void setItem(OperationParking item) {
			this.item = item;
			fillFields();
		}
		
		/**
		 * @return the item
		 */
		public OperationParking getItem() {
			return item;
		}
		
		private void emptyFields() {
			cbIdVehicule.setSelectedItem(null);
			cbIdPlaceParking.setSelectedItem(null);
			tfDateHeureEntree.clear();
			tfDateHeureSortie.clear();
			cbIdAgent1.setSelectedItem(null);
			cbIdAgent2.setSelectedItem(null);
			tfObservation.setText("");
		}
		
		private void fillFields() {
			if (item == null) {
				emptyFields();
				return;
			}
			
			selectCbVehicule( item.getIdVehicule() );
			selectCbPlaceParking( item.getIdPlaceParking() );
			tfDateHeureEntree.setTimestamp ( item.getDateHeureEntree() );
			tfDateHeureSortie.setTimestamp ( item.getDateHeureSortie() );
			selectCbAgent1( item.getIdAgent1() );
			selectCbAgent2( item.getIdAgent2() );
			tfObservation.setText( item.getObservation() );
		}
		
		public void initFields() {
			cbIdVehicule.removeAllItems();
			List<Vehicule> listVehicule = DAOVehicule.getList();
			for (Vehicule item : listVehicule) {
				cbIdVehicule.addItem(item);
			}
			
			cbIdPlaceParking.removeAllItems();
			List<PlaceParking> listPlaceParking = DAOPlaceParking.getList();;
			for (PlaceParking item : listPlaceParking) {
				cbIdPlaceParking.addItem(item);
			}
			
			cbIdAgent1.removeAllItems();
			List<Utilisateur> listUtilisateur = DAOUtilisateur.getList();;
			for (Utilisateur item : listUtilisateur) {
				cbIdAgent1.addItem(item);
			}
			
			cbIdAgent2.removeAllItems();
			listUtilisateur = DAOUtilisateur.getList();
			for (Utilisateur item : listUtilisateur) {
				cbIdAgent2.addItem(item);
			}
			
			formValidated = false;
			emptyFields();
			
			if (item == null) {
				item = new OperationParking();
				item.setDateHeureEntree( utils.DateUtils.getTimestampFromString( utils.DateUtils.getDateTimeOfThisMoment() ) );
				item.setDateHeureSortie( utils.DateUtils.getTimestampFromString( utils.DateUtils.getDateTimeOfThisMoment() ) );
				
				return;
			}
			
			fillFields();
		}
		
		public JDialog getDialog( Window window ) {
			if (dialog == null){
				dialog = new JDialog(window);
				dialog.setModal(true);
				dialog.setResizable(false);
				
				dialog.setContentPane( this );
				dialog.setSize(500, 300);
				
				dialog.setLocationRelativeTo(null);
				
				dialog.setUndecorated(true);
				
				this.configureRootPane(dialog.getRootPane());
			}
			
			return dialog;
		}
		
		public boolean showInDialog(Window window, OperationParking item){
			this.setItem( item );
			getDialog( window ).setVisible(true);
			
			return formValidated;
		}
		
		//Getters and Setters of ui components....
		
		public JLabel getlIdVehicule(){
			return lIdVehicule;
		}
		
		public JComboBox<Vehicule> getcbIdVehicule(){
			return cbIdVehicule;
		}
		
		public JLabel getlIdPlaceParking(){
			return lIdPlaceParking;
		}
		
		public JComboBox<PlaceParking> getcbIdPlaceParking(){
			return cbIdPlaceParking;
		}
		
		public JLabel getlDateHeureEntree(){
			return lDateHeureEntree;
		}
		
		public GUIDateTime gettfDateHeureEntree(){
			return tfDateHeureEntree;
		}
		
		public JLabel getlDateHeureSortie(){
			return lDateHeureSortie;
		}
		
		public GUIDateTime gettfDateHeureSortie(){
			return tfDateHeureSortie;
		}
		
		public JLabel getlIdAgent1(){
			return lIdAgent1;
		}
		
		public JComboBox<Utilisateur> getcbIdAgent1(){
			return cbIdAgent1;
		}
		
		public JLabel getlIdAgent2(){
			return lIdAgent2;
		}
		
		public JComboBox<Utilisateur> getcbIdAgent2(){
			return cbIdAgent2;
		}
		
		public JLabel getlObservation(){
			return lObservation;
		}
		
		public JTextField gettfObservation(){
			return tfObservation;
		}
		
	}
	
	
	public static class MyTableModel extends DefaultTableModel{
		private static final long serialVersionUID = 1L;
		
		private Vector<String> 			columnNames;;
		private Vector<Vector<Object>>	tableRows;
		
		/**
		 *
		 */
		public MyTableModel() {
			columnNames = new Vector<>();
			tableRows = new Vector<>();
		}
		
		public MyTableModel(String...columnNames) {
			this();
			
			for (String cn : columnNames ) {
				this.columnNames.add(cn);
			}
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return columnNames.size();
		}
		
		/**
		 * @return the columnNames
		 */
		public Vector<String> getColumnNames() {
			return columnNames;
		}
		
		/**
		 * @param columnNames the columnNames to set
		 */
		public void setColumnNames(Vector<String> columnNames) {
			this.columnNames.addAll(columnNames);
		}
		
		/**
		  * @return the tableRows
		 */
		public Vector<Vector<Object>> getTableRows() {
			return tableRows;
		}
		
		/**
		 * @param tableRows the tableRows to set
		 */
		public void setTableRows(Vector<Vector<Object>> tableRows) {
			this.tableRows.addAll(tableRows);
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getDataVector()
		 */
		@Override
		public Vector<?> getDataVector() {
			return tableRows;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return this.columnNames.get(column);
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			int rowCount = 0;
			
			if (this.tableRows != null) {
				rowCount = this.tableRows.size();
			}
			
			return rowCount;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int column) {
			Object value = null;
			
			if (this.tableRows != null) {
				value = this.tableRows.get(row).get(column);
			}
			
			return value;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
	
	
//	for the CRUD UIDetail ....
	public static interface UIDetailInterface {
		public void refactorUI();
		public String getCondition();
		
		
		
	}
}
// Elhamdou li Ellahi Rabbi El3alamine

