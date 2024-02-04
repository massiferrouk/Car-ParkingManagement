/**
 * 
 */
package ui.utils;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIPagination extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int nbPages = 0;
	private int currentPage = 0;
	private int nbItemsInPage = 30;
	private int nbTotalItems = 0;
	
	private JButton 			bNext, bPrev, bFirst, bLast;
	private JTextField			tfCurrentPage, tfNbPages;
	private JComboBox<Integer>  cbNbItemsInPage;
	private JLabel				lSlash;
	private JLabel				lRanges;
	
	private IHandleUpdateState iHandleUpdateState = null;
	
	public GUIPagination(){
		this(null);
	}
	
	public GUIPagination(IHandleUpdateState iHandleUpdateState){
		initUI();
		layoutUI();
		handleEvents();
		
		this.iHandleUpdateState = iHandleUpdateState;
	}
	
	private void initUI() {
		bNext = new JButton( IconsConstants.PAGE_NEXT_ICON_25_25 );
		bNext.setBorder(BorderFactory.createTitledBorder(""));
		
		bPrev = new JButton( IconsConstants.PAGE_PREV_ICON_25_25 );
		bPrev.setBorder(BorderFactory.createTitledBorder(""));
		
		bFirst = new JButton( IconsConstants.PAGE_FIRST_ICON_25_25 );
		bFirst.setBorder(BorderFactory.createTitledBorder(""));
		
		bLast = new JButton( IconsConstants.PAGE_LAST_ICON_25_25 );
		bLast.setBorder(BorderFactory.createTitledBorder(""));
		
		tfCurrentPage = new JTextField();
		tfNbPages = new JTextField();
		
		tfNbPages.setEditable(false);
		
		cbNbItemsInPage = new JComboBox<>(  new Integer[]{25, 30, 50, 75, 100, 150, 200, 500} );
		cbNbItemsInPage.setSelectedItem( (Integer)30 );
		
		lSlash = new JLabel(" / ");
		
		lRanges = new JLabel();
		
		bNext.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bFirst.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bLast.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		bPrev.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
		
		bNext.setToolTipText("Page suivante");
		bLast.setToolTipText("Dernière page");
		bFirst.setToolTipText("Première page");
		bPrev.setToolTipText("Page précédente");
		
		tfCurrentPage.setToolTipText("Page en cours");
		tfNbPages.setToolTipText("Nombre maximale de pages");
		cbNbItemsInPage.setToolTipText("Afficher "+cbNbItemsInPage.getSelectedItem()+" éléments dans une page");
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.updateState();
		this.setNbTotalItems(752);
		this.setCurrentPage(1);
	}
	
	private void layoutUI() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(2)
			.addComponent(bFirst, 25, 25, 25)
			.addComponent(bPrev, 25, 25, 25)
			.addGap(3)
			.addComponent(tfCurrentPage, 50, 50, 50)
			.addComponent(lSlash)
			.addComponent(tfNbPages, 50, 50, 50)
			.addGap(3)
			.addComponent(cbNbItemsInPage, 70, 70, 70)
			.addGap(3)
			.addComponent(bNext, 25, 25, 25)
			.addComponent(bLast, 25, 25, 25)
			.addGap(2, 2, Short.MAX_VALUE)
			.addComponent(lRanges)
			.addGap(2)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(0, 0, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup()
						.addComponent(bFirst, 25, 25, 25)
						.addComponent(bPrev, 25, 25, 25)
						.addComponent(tfCurrentPage, 25, 25, 25)
						.addComponent(lSlash, 25, 25, 25)
						.addComponent(tfNbPages, 25, 25, 25)
						.addComponent(cbNbItemsInPage, 25, 25, 25)
						.addComponent(bNext, 25, 25, 25)
						.addComponent(bLast, 25, 25, 25)
						.addComponent(lRanges, 25, 25, 25)
				)
				.addGap(0, 0, Short.MAX_VALUE)
		);
	}
	
	private void handleEvents() {
		cbNbItemsInPage.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				cbNbItemsInPageMouseMoved();
			}
		});
		
		bFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bFirstClicked();
			}
		});
		
		bPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bPrevClicked();
			}
		});
		
		bNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bNextClicked();
			}
		});
		
		bLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bLastClicked();
			}
		});
		
		this.cbNbItemsInPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbNbItemsInPageClicked();
			}
		});
		
		this.tfCurrentPage.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				tfCurrentPageEnterReleased(e);
			}
			
			public void keyTyped(KeyEvent e) {
				tfCurrentPageEnterReleased(e);
			}
		});
	}
	
	private void tfCurrentPageEnterReleased(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			int currentPage = -1;
			try {
				currentPage = Integer.parseInt(tfCurrentPage.getText().trim());
			}
			catch(Exception ex) {
			}
			
			if (currentPage != -1 && currentPage != this.currentPage) {
				this.setCurrentPage(currentPage);
			}
			else {
				this.tfCurrentPage.setText( this.currentPage+"" );
			}
		}
		else {
			char keyChar = e.getKeyChar();
			if (keyChar<'0' || keyChar>'9') {
				e.setKeyChar((char)0);
				e.consume();
			}
		}
	}
	
	private void cbNbItemsInPageClicked() {
		Integer nbItemsInPage = (Integer)cbNbItemsInPage.getSelectedItem();
		if (nbItemsInPage == null) {
			return;
		}
		
		this.setNbItemsInPage(nbItemsInPage);
	}
	
	private void bFirstClicked() {
		if (this.currentPage <= 1) {
			return;
		}
		
		this.setCurrentPage( 1 );
	}
	
	private void bPrevClicked() {
		if (this.currentPage <= 1) {
			return;
		}
		
		this.setCurrentPage( this.currentPage-1 );
	}

	private void bNextClicked() {
		if (this.currentPage >= nbPages) {
			return;
		}
		
		this.setCurrentPage( this.currentPage+1 );
	}

	private void bLastClicked() {
		if (this.currentPage >= nbPages) {
			return;
		}
		
		this.setCurrentPage( this.nbPages );
	}
	private void cbNbItemsInPageMouseMoved() {
		cbNbItemsInPage.setToolTipText("Afficher "+cbNbItemsInPage.getSelectedItem()+" éléments dans une page");
	}
	
	private void updateState() {
		this.bFirst.setEnabled( this.currentPage > 1 );
		this.bPrev.setEnabled( this.currentPage > 1 );
		
		this.bNext.setEnabled( this.currentPage < nbPages );
		this.bLast.setEnabled( this.currentPage < nbPages );
		
		this.updateRanges();
		
		if (this.iHandleUpdateState != null) {
			this.iHandleUpdateState.stateChanged();
		}
	}
	
	public void updateRanges() {
		int first = this.getFirst()+1;
		int last = first + this.getNbItemsInPage();
		last = Math.min(last, this.getNbTotalItems());
		
		this.lRanges.setText("[ "+first+" - "+last+" de "+this.getNbTotalItems()+" ]");
	}
	
//	*************************  Getters & Setters *****************************************
	/**
	 * @return the nbPages
	 */
	public int getNbPages() {
		return nbPages;
	}

	/**
	 * @param nbPages the nbPages to set
	 */
	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		if (currentPage > this.nbPages || currentPage < 1) {
			this.tfCurrentPage.setText(this.currentPage+"");
			return;
		}
		
		this.currentPage = currentPage;
		this.tfCurrentPage.setText( this.currentPage+"" );
		
		this.updateState();
	}

	/**
	 * @return the nbItemsInPage
	 */
	public int getNbItemsInPage() {
		return nbItemsInPage;
	}

	/**
	 * @param nbItemsInPage the nbItemsInPage to set
	 */
	public void setNbItemsInPage(int nbItemsInPage) {
		this.nbItemsInPage = nbItemsInPage;
		this.updateNbPages();
	}

	/**
	 * @return the nbTotalItems
	 */
	public int getNbTotalItems() {
		return nbTotalItems;
	}

	/**
	 * @param nbTotalItems the nbTotalItems to set
	 */
	public void setNbTotalItems(int nbTotalItems) {
		this.nbTotalItems = nbTotalItems;
		
		updateNbPages();
	}
	
	private void updateNbPages() {
		this.nbPages = this.nbTotalItems / this.nbItemsInPage;
		this.nbPages = this.nbPages + ( this.nbTotalItems % this.nbItemsInPage>0 ? 1 : 0 );
		
		this.tfNbPages.setText( this.nbPages+"" );
		
		this.updateRanges();
		
		if (this.currentPage > this.nbPages) {
			this.setCurrentPage(1);
		}
	}
	
	public int getFirst() {
		int first = 0;
		
		if (this.currentPage>0) {
			first = (this.currentPage-1)*this.nbItemsInPage;
		}
		
		return first;
	}
	
	public static interface IHandleUpdateState {
		public void stateChanged();
	}
}
