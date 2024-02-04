package ui.admin;


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
import modele.beans.ABean;
import modele.beans.Etage;
import modele.beans.PlaceParking;
import modele.daos.DAOEtage;
import modele.daos.DAOPlaceParking;
import ui.utils.GUIPagination;
import ui.utils.IconsConstants;
import ui.utils.UIUtils;

/**
 *
 */


public class PanelCRUDPlaceParking extends JPanel{
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
	List<PlaceParking> listItems = null;
	
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
	public PanelCRUDPlaceParking() {
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
		
		cbSearchFields = new JComboBox<>( new String[] {ALL_FIELDS, "Numero", "Type", "Ouccupe", "Etage"} );
		
		chbInstantaniousSearch = new JCheckBox("Recherche Instantanée");
		chbInstantaniousSearch.setSelected(true);
		
		pContent = new JPanel();
		pContent.setBorder( BorderFactory.createLineBorder(Color.BLUE) );
		
		
		tableModel = new MyTableModel("N°", "Numero", "Type", "Occucpe?", "Etage");
		tableItems =  new JTable(tableModel) {
			private static final long serialVersionUID = 1L;
			
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
				Component returnComp = super.prepareRenderer(renderer, row, column);
				Color alternateColor = new Color(252,242,206);
				Color whiteColor = Color.WHITE;
				if (!returnComp.getBackground().equals(getSelectionBackground())){
					Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
					returnComp .setBackground(bg);
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
		PlaceParking item = new PlaceParking();
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
		
		PlaceParking item = (PlaceParking)row.get(row.size()-1);
		
		if (item == null) {
			return;
		}
		
		boolean validated = formPanel.showInDialog(  SwingUtilities.getWindowAncestor(this), item );
		if (validated) {
			PlaceParking otherItem = formPanel.getItem();
			tableModel.tableRows.set(indice, getRow(num, otherItem));
			
			tableItems.updateUI();
		}
	}
	
	private String generateInfo(PlaceParking item) {
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
		
		PlaceParking item = (PlaceParking)row.get( row.size()-1 );
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
				PlaceParking item = (PlaceParking)row.get(row.size()-1);
				
				DAOPlaceParking.delete(item);
				
				rowsToDelete.add(row);
			}
			
			tableModel.tableRows.removeAll(rowsToDelete);
			tableItems.updateUI();
			tableItems.clearSelection();
		}
	}
	
	private void exportPDF() {
		File file = UIUtils.createAndPrepareEmptyPDFFile(this, "list_place_parkings");
		
		if (file == null) {
			return;
		}
		
//		List<PlaceParking> listItems = DAOPlaceParking.getList( this.getCondtion() );
//		ManualPDFCreator.PDFPlaceParking.createPDFList(file, listItems);
	}
	
	private void exportExcel() {
//		File file = UIUtils.createAndPrepareEmptyExcelFile(this, "list_place_parkings", UIUtils.PathResourceExcel.EXCEL_PLACEPARKINGS);
//		
//		if (file == null) {
//			return;
//		}
//		
//		try {
//			XLSXLSXCreator xlsx = new XLSXLSXCreator(file);
//			
//			List<PlaceParking> listItems = DAOPlaceParking.getList( this.getCondtion() );
//			
//			int baseRow = 12;
//			
//			for (int i=0; i<listItems.size(); i++) {
//				int numRow = i+baseRow;
//				
//				PlaceParking item = listItems.get(i);
//				
//				xlsx.setCellValue("A"+numRow, utils.StringUtils.intToString(i+1, 2));
//				xlsx.setCellValue("B"+numRow,  item.getNumero());
//				xlsx.setCellValue("C"+numRow,  item.getType());
//				xlsx.setCellValue("D"+numRow,  item.getIdEtage());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	private void searchItem() {
		updateData();
	}
	
	private void close() {
		FenetreAccueilAdmin.getSingleton().closePanelContent();
	}
	
	private String getCondtion() {
		String condition = "1";
		
		String txtFilter =  utils.StringUtils.addSQLSlashes( tfSearch.getText().trim() );
		
		String fields = (String)cbSearchFields.getSelectedItem();
		
		if (fields.equals(ALL_FIELDS)) {
			condition = " CONCAT(COALESCE(`numero`,''), ' ', COALESCE(`type`,''), ' ', COALESCE(`idEtage`,'')) like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("Numero")) {
			condition = " `numero` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("Type")) {
			condition = " `type` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("Occupe")) {
			condition = " `occupe` like '%"+txtFilter+"%' ";
		}
		else if (fields.equals("Etage")) {
			condition = " `idEtage` like '%"+txtFilter+"%' ";
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
		
		int count = (int)DAOPlaceParking.getCountInTable ( this.getCondtion() );
		
		guiPagination.setNbTotalItems(count);
		
		listItems = DAOPlaceParking.getList( this.getCondtion()+" "+this.getLimits() );
		
		int num=guiPagination.getFirst()+1;
		for (PlaceParking item : listItems) {
			tableModel.tableRows.add( getRow(num, item) );
			
			num++;
		}
		
		tableItems.updateUI();
		tableItems.clearSelection();
	}
	
	private Vector<Object> getRow(PlaceParking item){
		return getRow( tableModel.tableRows.size()+1, item );
	}
	
	private Vector<Object> getRow(int num, PlaceParking item){
		Vector<Object> row = new Vector<>();
		
		row.add(num);
		row.add(item.getNumero());
		row.add(item.getType());
		row.add(item.getIdEtage());
		row.add( item );
		
		return row;
	}
	
	List<PlaceParking> getListItems() {
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
		
		private JLabel lNumero;
		private JLabel lType;
		private JLabel lOccupe;
		private JLabel lIdEtage;
		
		private JTextField tfNumero;
		private JComboBox<String> cbType;
		private JCheckBox chbOccupe;
		private JComboBox<Etage> cbIdEtage;
		
		
		private JSeparator separator;
		
		private JButton	bValider;
		private JButton	bAnnuler;
		
		private JDialog dialog = null;
		
		private PlaceParking item;
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
			lNumero = new JLabel("Numero : ");
			lType = new JLabel("Type : ");
			lOccupe = new JLabel("Occupé : ");
			lIdEtage = new JLabel("Etage : ");
			
			tfNumero = GUIFieldFactory.createSimpleTextField();
			chbOccupe = new JCheckBox(); 
			cbType = new JEditedComboBox();
			cbIdEtage = new JEditedComboBox();
			
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
									.addComponent(lNumero)
									.addComponent(lType)
									.addComponent(lOccupe)
									.addComponent(lIdEtage)
							)
							.addGap(5)
							.addGroup(layout.createParallelGroup()
									.addComponent(tfNumero, 300, 300, 300)
									.addComponent(cbType, 300, 300, 300)
									.addComponent(chbOccupe, 300, 300, 300)
									.addComponent(cbIdEtage, 300, 300, 300)
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
							.addComponent(lNumero, 25, 25, 25)
							.addComponent(tfNumero, 25, 25, 25)
					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lType, 25, 25, 25)
							.addComponent(cbType, 25, 25, 25)
					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lOccupe, 25, 25, 25)
							.addComponent(chbOccupe, 25, 25, 25)
					)
					.addGap(3)
					.addGroup(layout.createParallelGroup()
							.addComponent(lIdEtage, 25, 25, 25)
							.addComponent(cbIdEtage, 25, 25, 25)
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
			java.lang.String numero = tfNumero.getText().trim();
			java.lang.String type = cbType.getSelectedItem().toString();
			java.lang.Boolean occupe = chbOccupe.isSelected();
			java.lang.Integer idEtage = 0;
			
			Etage itemEtage = cbIdEtage.getSelectedItem() == null ? null : (Etage)(cbIdEtage.getSelectedItem()) ;
			idEtage = itemEtage == null ? 0 : itemEtage.getId();
			
			if (numero.equals("")) {
				JOptionPane.showMessageDialog(this, "Veuillez rensiegner le champs '' !!!", "Champs vide !", JOptionPane.ERROR_MESSAGE);
				tfNumero.requestFocus();
				return;
			}
			
			item.setNumero(numero);
			item.setType( ABean.getTypeCode(type) );
			item.setOccupe(occupe);
			item.setIdEtage(idEtage);
			
			if (item.getId()>0) {
				DAOPlaceParking.update(item);
			}
			else {
				DAOPlaceParking.add(item);
			}
			
			formValidated = true;
			
			this.close();
		}
		
		private void close() {
			if (this.dialog != null) {
				this.dialog.setVisible(false);
			}
		}
		
		/**
		 * @param item the item to set
		 */
		public void setItem(PlaceParking item) {
			this.item = item;
			fillFields();
		}
		
		/**
		 * @return the item
		 */
		public PlaceParking getItem() {
			return item;
		}
		
		private void emptyFields() {
			tfNumero.setText("");
			cbType.setSelectedItem(null);
			chbOccupe.setSelected(false);
			cbIdEtage.setSelectedItem(null);
			
			chbOccupe.setEnabled(false);
		}
		
		private void fillFields() {
			if (item == null) {
				emptyFields();
				return;
			}
			
			tfNumero.setText( item.getNumero() );
			cbType.setSelectedItem( item.getTypeAsString() );
			chbOccupe.setSelected( item.isOccupe() );
			selectCbEtage( item.getIdEtage() );
		}
		
		private void selectCbEtage(int idEtage) {
			int selectedIndex = -1;
			
			for (int i=0; i<cbIdEtage.getItemCount(); i++) {
				if (cbIdEtage.getItemAt(i).getId() == idEtage) {
					selectedIndex = i;
					break;
				}
			}
			
			cbIdEtage.setSelectedIndex(selectedIndex);
		}
		
		public void initFields() {
			cbType.removeAllItems();
			cbType.addItem("leger");
			cbType.addItem("moyen");
			cbType.addItem("lourd");
			
			cbIdEtage.removeAllItems();
			List<Etage> listEtage = DAOEtage.getList();;
			for (Etage item : listEtage) {
				cbIdEtage.addItem(item);
			}
			
			formValidated = false;
			emptyFields();
			
			if (item == null) {
				item = new PlaceParking();
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
		
		public boolean showInDialog(Window window, PlaceParking item){
			this.setItem( item );
			getDialog( window ).setVisible(true);
			
			return formValidated;
		}
		
		//Getters and Setters of ui components....
		
		public JLabel getlNumero(){
			return lNumero;
		}
		
		public JTextField gettfNumero(){
			return tfNumero;
		}
		
		public JLabel getlType(){
			return lType;
		}
		
		public JComboBox<String> getcbType(){
			return cbType;
		}
		
		public JLabel getlIdEtage(){
			return lIdEtage;
		}
		
		public JComboBox<Etage> getcbIdEtage(){
			return cbIdEtage;
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

