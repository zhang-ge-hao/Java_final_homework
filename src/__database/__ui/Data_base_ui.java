package __database.__ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import __backend.Data_base_init;
import __ui.Entries_scan_ui;
import __ui.Main_ui;

public class Data_base_ui {
	static public int TOP_PANEL_HEIGHT = 25;
	static public int TEXTSEARCH_WEIGHT = 100;
	static public int TOP_COMBO_WEIGHT = 130;
	static public int MAIN_HEIGHT = TOP_PANEL_HEIGHT+Entries_display_ui.MAIN_HEIGHT;
	static public int MAIN_WEIGHT = Entries_display_ui.MAIN_WEIGHT;
	private JDialog mainFrame;
	private Entries_display_ui dis;
	private JPanel topPanel;
	public Data_base_ui(int rank,Entries_display_ui dis){
		init(rank,dis);
	}
	public Data_base_ui(int rank){
		init(rank,new Entries_display_ui(rank));
	}
	public void init(int rank,Entries_display_ui dis){
		this.dis = dis;
		mainFrame = new JDialog();
		mainFrame.setPreferredSize(new Dimension(MAIN_WEIGHT,MAIN_HEIGHT));
		mainFrame.setLayout(new BorderLayout());
		initTopPanel();
		mainFrame.add(topPanel,BorderLayout.NORTH);
		mainFrame.add(dis.getMainPanel(),BorderLayout.CENTER);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setModal(true);
		dis.displayItems(1,Main_ui.dataBases
				.elementAt(rank).getSign().to_Message_node());
	}
	public void initTopPanel(){
		final JLabel addButton = getNewLabel("Add Item",65);
		addButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				runAddOrSearch(true,true);
			}
			public void mouseEntered(MouseEvent e){
				addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}  
			public void mouseExited(MouseEvent e){  
				addButton.setCursor(Cursor.getDefaultCursor());
			}  
		});
		final JLabel searchButton = getNewLabel("Search by Message",120);
		searchButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				runAddOrSearch(false,false);
			}
			public void mouseEntered(MouseEvent e){
				searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}  
			public void mouseExited(MouseEvent e){  
				searchButton.setCursor(Cursor.getDefaultCursor());
			}  
		});
		topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		topPanel.setPreferredSize(new Dimension(MAIN_WEIGHT,TOP_PANEL_HEIGHT-5));
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.add(getNewLabel("Search at",65));
		topPanel.add(getTopCombo());
		topPanel.add(getNewLabel("",30));
		topPanel.add(getNewLabel("Search by Code",95));
		topPanel.add(getSearchByCode());
		topPanel.add(getNewLabel("",30));
		topPanel.add(addButton);
		topPanel.add(getNewLabel("",30));
		topPanel.add(searchButton);
	}
	public JTextField getSearchByCode(){
		final JTextField searchByCode = new JTextField();
		searchByCode.setBorder(BorderFactory
				.createLineBorder(Color.BLACK,1));
		searchByCode.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e) {
				displayRefresh(searchByCode.getText());
			}
			public void insertUpdate(DocumentEvent e) {
				displayRefresh(searchByCode.getText());
			}
			public void removeUpdate(DocumentEvent e) {
				displayRefresh(searchByCode.getText());
			}
		});
		searchByCode.setBackground(Color.WHITE);
		searchByCode.setForeground(Color.DARK_GRAY);
		searchByCode.setPreferredSize(new Dimension
				(TEXTSEARCH_WEIGHT,TOP_PANEL_HEIGHT-5));
		return searchByCode;
	}
	public JLabel getNewLabel(String tit,int w){
		JLabel res = new JLabel(tit,JLabel.CENTER);
		res.setBackground(Color.DARK_GRAY);
		res.setForeground(Color.WHITE);
		res.setPreferredSize(new Dimension(w,TOP_PANEL_HEIGHT-5));
		return res;
	}
	public JComboBox<String> getTopCombo(){
		String[] items = new String[Data_base_init.ITEM_TYPE_CON];
		for(int i=0;i<Data_base_init.ITEM_TYPE_CON;i++)
			items[i] = new String(Main_ui.smallTitles.elementAt(i));
		final JComboBox<String> res = new JComboBox<String>(items);
		res.setPreferredSize(new Dimension(TOP_COMBO_WEIGHT,TOP_PANEL_HEIGHT-7));
		res.setSelectedIndex(dis.getRank());
		res.setBackground(Color.DARK_GRAY);
		res.setForeground(Color.WHITE);
		res.setUI((ComboBoxUI)MyComboBoxUI.createUI(res));
		res.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					int rank = res.getSelectedIndex();
					dis.reDisplay(rank);
				}
			}
		});
		return res;
	}
	public void runAddOrSearch(boolean isAdd,boolean haveToFull){
		JDialog dia = new JDialog();
		Entries_scan_ui s = new Entries_scan_ui(dis.getRank()
				,Main_ui.dataBases.elementAt(dis.getRank()).getSign().to_Message_node()
				,dia,dis,isAdd); 
		dia.add(s.getMainPanel());
		dia.pack();
		dia.setModal(true);
		dia.setResizable(false);
		dis.setRes(null);
		dia.setVisible(true);
		if(dis.getRes() != null && dis.getRes().first != null){
			if(isAdd){
				Main_ui.dataBases.elementAt(dis.getRank())
				.add(Main_ui.dataBases.elementAt(dis.getRank()).getSign()
					.from_Message_node(dis.getRes().first));
				dis.displayItems(dis.getPage(),dis.getSearchNode());
			}else dis.displayItems(1,dis.getRes().first);
		}
	}
	static class MyComboBoxUI extends BasicComboBoxUI {
		public static ComponentUI createUI(JComponent c) {
			return new MyComboBoxUI();
		}
		protected JButton createArrowButton() {
			JButton button = new BasicArrowButton(BasicArrowButton.SOUTH);
			button.setBackground(Color.BLACK);
			return button;
		}
	}
	public void displayRefresh(String search){
		dis.setPage(1);
		dis.displayItems(dis.getPage(),dis.getDataBase().fuzzy_search(search));
	}
	public void show(){
		Container oldFrame = Main_ui.MAIN_FRAME;
		Main_ui.MAIN_FRAME = mainFrame;
		mainFrame.setVisible(true);
		Main_ui.MAIN_FRAME = oldFrame;
	}
	public void exit(){
		mainFrame.dispose();
	}
}
