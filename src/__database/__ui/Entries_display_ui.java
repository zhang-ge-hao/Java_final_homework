package __database.__ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import __backend.__entries.Patinet;
import __database.__backend.Data_base;
import __database.__backend.Entries;
import __database.__backend.Message_node;
import __tool.Pair;
import __ui.Main_ui;

public class Entries_display_ui {
	public static int ANS_ROWS = 10;
	public static int ANS_BORDER_WEIGHT = 3;
	public static int ITEM_BORDER_WEIGHT = 1;
	public static int MAIN_WEIGHT = 800;
	public static int MAIN_HEIGHT = 300;
	public static int PAGE_ACTUAL = 6;
	public static int PAGE_NUMBER_BUTTON_WEIGHT = 15;
	public static int PAGE_BUTTON_WEIGHT = 30;
	public static int PAGE_BUTTON_HEIGHT = 20;
	public static int EANDD_BUTTON_WEIGHT = 40;
	public static int EANDD_BUTTON_HEIGHT = 12;
	public static int ITEM_MAX_CON = 6;
	public static int TOT_PAGE = -1;
	public static Color BORDER_COLOR = Color.DARK_GRAY;
	public static Color EDIT_COLOR = new Color(0,100,0);
	public static Color DELETE_COLOR = new Color(139,0,0);
	public static final int NEXT_PAGE = -1;
	public static final int PREV_PAGE = -2;
	public static final String MALE_SIGN = "Male";
	public static final String FEMALE_SIGN = "Female";
	protected JPanel ansPanel;
	private Border itemBorder,topBorder,bottomBorder,buttonBorder;
	private Vector<Integer> weights; 
	private Vector<String> titles;
	protected Vector<Message_node> displayItems;
	private int displayPage,itemCon;
	private Message_node search;
	protected int rank,ans_rows,main_weight,main_height;
	private Pair<Message_node,Message_node> subDialogRes;
	public Entries_display_ui(int rank,int w,int h,int r){
		init(rank,w,h,r);
	}
	public Entries_display_ui(int rank){
		init(rank,MAIN_WEIGHT,MAIN_HEIGHT,ANS_ROWS);
	}
	public void init(int rank,int w,int h,int r){
		ans_rows = r; main_weight = w; main_height = h;
		this.rank = rank;
		displayItems = null; search = null;
		itemBorder = BorderFactory.createMatteBorder
			(0,ANS_BORDER_WEIGHT,ITEM_BORDER_WEIGHT,ANS_BORDER_WEIGHT,BORDER_COLOR);
		topBorder = BorderFactory.createMatteBorder
			(ANS_BORDER_WEIGHT,ANS_BORDER_WEIGHT,ITEM_BORDER_WEIGHT,ANS_BORDER_WEIGHT,BORDER_COLOR);
		bottomBorder = BorderFactory.createMatteBorder
			(0,ANS_BORDER_WEIGHT,ANS_BORDER_WEIGHT,ANS_BORDER_WEIGHT,BORDER_COLOR);
		buttonBorder = BorderFactory.createLineBorder(BORDER_COLOR,1);
		ansPanel = new JPanel(new GridLayout(ans_rows+1,1));
		itemCon = Math.min(ITEM_MAX_CON,Main_ui.titleFonts.elementAt(this.rank).size());
		weights = Pair.getSecondVector(Main_ui.titleFonts.elementAt(this.rank));
		titles = Pair.getFirstVector(Main_ui.titleFonts.elementAt(this.rank));
		ansPanel.add(getRow(titles),0,0);
		setBlock(1,ans_rows); getBeautify();
		ansPanel.setPreferredSize(new Dimension(main_weight,main_height));
		ansPanel.add(getPageNumber(1));
	}
	public void reDisplay(int rank){
		this.rank = rank;
		displayItems = null; search = null;
		itemCon = Math.min(ITEM_MAX_CON,Main_ui.titleFonts.elementAt(this.rank).size());
		weights = Pair.getSecondVector(Main_ui.titleFonts.elementAt(this.rank));
		titles = Pair.getFirstVector(Main_ui.titleFonts.elementAt(this.rank));
		displayItems(1,Main_ui.dataBases.elementAt(this.rank).getSign().to_Message_node());
	}
	@SuppressWarnings("unchecked")
	public JPanel getRow(Object obj){
		Vector<Object> items;
		if(obj == null)items = null;
		else if(obj instanceof Vector)items = (Vector<Object>)obj;
		else return null;
		JPanel res = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		JLabel item;
		String itemStr;
		if(items != null)for(int i=0;i<itemCon;i++){
			if(items.elementAt(i) == null){
				itemStr = new String("");
			}else if(titles.elementAt(i).equals("Sex") && obj != titles){
				itemStr = items.elementAt(i)
						.toString().equals(""+Patinet.MALE)?MALE_SIGN:FEMALE_SIGN;
			}else itemStr = items.elementAt(i).toString();
			item = new JLabel(itemStr,JLabel.CENTER);
			item.setPreferredSize(new Dimension(weights.elementAt(i),20));
			res.add(item);
		}
		res.setPreferredSize(new Dimension(0,20));
		return res;
	}
	public JPanel getRow(Object obj,int _rank){
		JPanel res = getRow(obj);
		res.addMouseListener(getRowListener(_rank,res));
		return res;
	}
	public MouseListener getRowListener(final int _rank
			,final JPanel thePanel){
		return new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton()==MouseEvent.BUTTON1) {
					rowClickedLeftMouseRun(_rank,thePanel);
				}else if (e.getButton()==MouseEvent.BUTTON3) {
					rowClickedRightMouseRun(_rank,thePanel);
				}
			}  
			public void mouseEntered(MouseEvent e){
				if(!checkedJudge(_rank))swapColor(thePanel);
			}  
			public void mouseExited(MouseEvent e){  
				if(!checkedJudge(_rank))swapColor(thePanel);
			}  
		};
	}
	public void rowClickedRightMouseRun(int _rank,JPanel thePanel){}
	public void rowClickedLeftMouseRun(int _rank,JPanel thePanel){
		JDialog dialog = new JDialog();
		//dialog.setPreferredSize(new Dimension
			//	(Entries_ui.MAIN_WEIGHT,Entries_ui.MAIN_HEIGHT));
		Entries_ui itemUi = new Entries_ui
				(rank,displayItems.elementAt(_rank),dialog,this);
		dialog.add(itemUi.getMainPanel());
		dialog.pack();
		dialog.setModal(true);
		dialog.setResizable(false);
		this.setRes(null);
		Container oldFrame = Main_ui.MAIN_FRAME;
		Main_ui.MAIN_FRAME = dialog;
		dialog.setVisible(true);
		Main_ui.MAIN_FRAME = oldFrame;
		Pair<Message_node,Message_node> r = this.getRes();
		if(r != null && r.second != null){
			Main_ui.dataBases.elementAt(rank)
				.del(Main_ui.dataBases.elementAt(rank)
					.getSign().from_Message_node(r.second));
		}
		if(r != null && r.first != null){
			Vector<Message_node> v = Main_ui.dataBases.elementAt(rank).search
				(Main_ui.dataBases.elementAt(rank)
					.getSign().from_Message_node(r.first));
			if(v.size() != 0){
				Entries R = Main_ui.dataBases
					.elementAt(rank).getSign().from_Message_node(r.first);
				Main_ui.dataBases.elementAt(rank).updata(R,R);
			}else Main_ui.dataBases.elementAt(rank)
				.add(Main_ui.dataBases.elementAt(rank)
					.getSign().from_Message_node(r.first));
		}
		displayItems(displayPage,search);
	}
	static public void swapColor(JPanel row){
		Component c;
		Color b,f=row.getComponent(0).getForeground();
		row.setBackground(f);
		int count = row.getComponentCount();
		for (int i=0;i<count;i++) {
			c = row.getComponent(i);
			b=c.getBackground();f=c.getForeground();
			c.setBackground(f);
			c.setForeground(b);
		}
	}
	public void setBlock(int lo,int hi){
		for(int i=lo;i<hi;i++){
			ansPanel.add(getRow(null));
		}
	}
	public void getBeautify(){
		JPanel item = (JPanel)ansPanel.getComponent(0);
		JLabel topLabel;
		item.setBorder(topBorder);
		for(int i=0;i<itemCon;i++){
			topLabel = (JLabel)item.getComponent(i);
			topLabel.setForeground(BORDER_COLOR);
			topLabel.setFont(new Font("Dialog",2,15));
		}
		for(int i=1;i<ans_rows-1;i++){
			item = (JPanel)ansPanel.getComponent(i);
			item.setBorder(itemBorder);
		}
		item = (JPanel)ansPanel.getComponent(ans_rows-1);
		item.setBorder(bottomBorder);
	}
	public JPanel getMainPanel(){
		return ansPanel;
	}
	public JPanel getPageNumber(int page){
		if(displayItems == null)return new JPanel();
		JPanel res = new JPanel(new FlowLayout(FlowLayout.CENTER,2,4));
		JLabel button;
		int tot = displayItems.size()/(ans_rows-1)
				+(displayItems.size()%(ans_rows-1)==0?0:1);
		page = Math.min(page,tot);
		int exc = tot<=PAGE_ACTUAL?tot:PAGE_ACTUAL;
		int sta = (PAGE_ACTUAL/2<=page)?(page-PAGE_ACTUAL/2+1):1;
		if(sta+exc-1>tot)sta = tot-exc+1<1?1:tot-exc+1;
		button = getPageButton(1,"first",true);
		if(page == 1)button.setBorder(buttonBorder);
		res.add(button);
		res.add(getPageButton(PREV_PAGE,"prev",true));
		for(int i=0;i<exc;i++){
			button = getPageButton(i+sta,""+(i+sta),false);
			if(i+sta == page)button.setBorder(buttonBorder);
			res.add(button);
		}
		res.add(getPageButton(NEXT_PAGE,"next",true));
		button = getPageButton(tot,"last",true);
		if(page == tot)button.setBorder(buttonBorder);
		res.add(button);
		return res;
	}
	public JLabel getPageButton(int page,String word,boolean specialButton){
		JLabel res = new JLabel(word,JLabel.CENTER);
		if(specialButton)
			res.setPreferredSize(new Dimension(PAGE_BUTTON_WEIGHT,PAGE_BUTTON_HEIGHT));
		else res.setPreferredSize(new Dimension(PAGE_NUMBER_BUTTON_WEIGHT,PAGE_BUTTON_HEIGHT));
		res.addMouseListener(getPageButtonListener(page,res,this));
		return res;
	}
	public MouseListener getPageButtonListener(final int page
			,final JLabel button,final Entries_display_ui _this){
		return new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(page == NEXT_PAGE)_this.displayItems(_this.nextPage());
				else if(page == PREV_PAGE)_this.displayItems(_this.prevPage());
				else _this.displayItems(page);
			}  
			public void mouseEntered(MouseEvent e){  
				button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  
			}  
			public void mouseExited(MouseEvent e){  
				button.setCursor(Cursor.getDefaultCursor());  
			}  
		};
	}
	public boolean checkedJudge(int _rank){
		return false;
	}
	public boolean checkedJudge(Message_node exe){
		return false;
	}
	public void displayItems(int page){
		if(displayItems==null)return;
		int tot = displayItems.size()/(ans_rows-1)
				+(displayItems.size()%(ans_rows-1)==0?0:1);
		if(page == TOT_PAGE)page = tot;
		page = Math.min(page,tot);page = Math.max(1,page);
		displayPage = page;
		ansPanel.removeAll();
		ansPanel.add(getRow(titles),0,0);
		int i; JPanel arow;
		for(i=0;i<(ans_rows-1)&&((page-1)*(ans_rows-1)+i<displayItems.size());i++){
			Message_node m = displayItems.elementAt((page-1)*(ans_rows-1)+i);
			arow = getRow(m.getVector(),(page-1)*(ans_rows-1)+i);
			if(checkedJudge(m))getNumSelect(m,arow,true);
			ansPanel.add(arow,0,i+1);
			if(checkedJudge(((page-1)*(ans_rows-1)+i)))
				swapColor(arow);
		}
		setBlock(i+1,ans_rows);
		getBeautify();
		ansPanel.add(getPageNumber(page));
	}
	public void getNumSelect(Message_node exe,JPanel rowPanel,boolean hasIn){};
	public void displayItems(int page,Vector<Message_node> v){
		displayItems = v;
		displayItems(page);
	}
	public void displayItems(int page,Message_node n){
		Vector<Message_node> v = 
				Main_ui.dataBases.elementAt(this.rank)
				.search(Main_ui.dataBases.elementAt(this.rank)
						.getSign().from_Message_node(n));
		search = new Message_node(n);
		displayItems = v;
		displayItems(page);
	}
	public int nextPage(){
		return Math.min(displayPage+1,displayItems.size()/(ans_rows-1)+1);
	}
	public int prevPage(){
		return Math.max(displayPage-1,1);
	}
	public void setRes(Pair<Message_node,Message_node> r){
		subDialogRes = r;
	}
	public int getPage(){
		return displayPage;
	}
	public void setPage(int p){
		displayPage = p;
	}
	public Data_base getDataBase(){
		return Main_ui.dataBases.elementAt(this.rank);
	}
	public Pair<Message_node,Message_node> getRes(){
		return subDialogRes;
	}
	public Message_node getSearchNode(){
		return search;
	}
	public int getRank(){
		return rank;
	}
}
