package __ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import __backend.Data_base_init;
import __backend.Hospitalization_information;
import __backend.__entries.Special_node;
import __database.__backend.Data_base;
import __database.__backend.Message_node;
import __database.__ui.Data_base_ui;
import __database.__ui.Entries_display_ui;
import __tool.Pair;

public class Main_ui {
	public static void main(String[] args) {
		Main_ui.getSingle().show();
	}
	private static Main_ui single = null;
	public static int BUTTON_BLOCK_WEIGHT = 20;
	public static int BUTTON_HEIGHT = 20;
	public static int MAIN_WEIGHT = 600;
	public static int MAIN_HEIGHT = Entries_display_ui.MAIN_HEIGHT*2+BUTTON_HEIGHT+35;
	public static int COMBO_WEIGHT = 150;
	public static int COMBO_HEIGHT = 200;
	public static Color BUTTON_BACKGROUND = Color.DARK_GRAY;
	public static Color BUTTON_FOREROUND = Color.WHITE;
	private JFrame mainFrame;
	private JPanel topButtonPanel;
	private JPanel centerPanel;
	private JTextField searchHosInf;
	private int side;
	public static Vector<Data_base> dataBases; 
	public static Vector<Vector<Pair<String,Integer>>> titleFonts;
	public static Vector<String> smallTitles;
	public static Vector<Vector<Boolean>> haveToFills;
	public static Vector<Vector<Pair<Boolean,String>>> regexs;
	public static Vector<Hospitalization_information> allOfRecords;
	private Main_ui(){
		Data_base_init.init();
		initMainandCenterFrame();
		initTopButtonPanel();
		initSearchHosInf();
		mainFrame.add(topButtonPanel,BorderLayout.NORTH);
		mainFrame.add(centerPanel,BorderLayout.CENTER);
		mainFrame.pack();
		side = 2;
	}
	public void show(){
		MAIN_FRAME = mainFrame;
		mainFrame.setVisible(true);
	}
	public void exit(){
		mainFrame.dispose();
	}
	public void initMainandCenterFrame(){
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension
				(MAIN_WEIGHT,Entries_display_ui.MAIN_HEIGHT*2));
		centerPanel.add(Welcome_ui.getMainPanel());
		mainFrame = new JFrame();
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setPreferredSize(new Dimension(MAIN_WEIGHT,MAIN_HEIGHT));
		mainFrame.setResizable(false);
		mainFrame.addWindowListener(getMainWindowListener());
	}
	public void initTopButtonPanel(){
		JButton button;
		topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		button = new JButton("Submit an Expense Account");
		setTopButtonColor(button);
		button.setPreferredSize(new Dimension
				(button.getText().length()*7+BUTTON_BLOCK_WEIGHT,BUTTON_HEIGHT));
		button.addMouseListener(getTopButtonListener(0,button,this));
		topButtonPanel.add(button);
		button = new JButton("Data Base");
		setTopButtonColor(button);
		button.setPreferredSize(new Dimension
				(button.getText().length()*6+BUTTON_BLOCK_WEIGHT,BUTTON_HEIGHT));
		button.addMouseListener(getTopButtonListener(1,button,this));
		topButtonPanel.add(button);
		topButtonPanel.setPreferredSize(new Dimension
				(MAIN_WEIGHT,BUTTON_HEIGHT));
	}
	public MouseListener getTopButtonListener(final int r
			,final JButton button,final Main_ui tm){
		return new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(r == 0){
					changeSide(e.getClickCount()==2?true:false);
				}else if(r == 1){
					new Data_base_ui(0).show();
				}
			}
			public void mouseEntered(MouseEvent e){
				button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}  
			public void mouseExited(MouseEvent e){
				button.setCursor(Cursor.getDefaultCursor());
			}  
		};
	}
	public WindowAdapter getMainWindowListener(){
		return new WindowAdapter (){
            public void windowClosing ( WindowEvent e ){
                int exi = JOptionPane.showConfirmDialog
                		(null, "Save changes?", "",
                				JOptionPane.YES_NO_OPTION,
                				JOptionPane.QUESTION_MESSAGE);
                if (exi == JOptionPane.YES_OPTION)
                	Data_base_init.saveAll();
                System.exit(0);
            }
        };
	}
	public void setTopButtonColor(JButton b){
		b.setBackground(BUTTON_BACKGROUND);
		b.setForeground(BUTTON_FOREROUND);
		b.setBorder(BorderFactory.createLineBorder(BUTTON_FOREROUND,1));
	}
	public static Container MAIN_FRAME;
	public void changeSide(boolean toWelcome){
		Main_ui m = Main_ui.getSingle();
		m.side = toWelcome?2:(m.side+1)%2;
		m.centerPanel.removeAll();
		topButtonPanel.remove(searchHosInf);
		if(m.side == 0){
			m.centerPanel.add(Work_ui.getMainPanel());
		}else if(m.side == 1){
			searchHosInf.setText("");
			topButtonPanel.add(searchHosInf);
			m.centerPanel.add(Record_display_ui.getMainPanel());
		}else{
			m.centerPanel.add(Welcome_ui.getMainPanel());
		}
		reFresh();
	}
	public void initSearchHosInf(){
		searchHosInf = new JTextField();
		searchHosInf.setPreferredSize(new Dimension(322,BUTTON_HEIGHT));
		//searchHosInf.setBorder(BorderFactory.createLineBorder(Color.WHITT,1));
		searchHosInf.setBackground(Color.DARK_GRAY);
		searchHosInf.setForeground(Color.WHITE);
		searchHosInf.setFont(new Font("simsun",1,14));
		searchHosInf.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e) {
				searchHosInfs(searchHosInf.getText());
			}public void insertUpdate(DocumentEvent e) {
				searchHosInfs(searchHosInf.getText());
			}public void removeUpdate(DocumentEvent e) {
				searchHosInfs(searchHosInf.getText());
			}
		});
	}
	public void searchHosInfs(String code){
		Vector<Message_node> v = new Vector<Message_node>();
		for(Hospitalization_information h :allOfRecords){
			if(h.getCode().indexOf(code) != -1){
				v.add(Special_node.toSpecialNode(h));
			}
		}
		Record_display_ui.getSingle().displayItems(1, v);
	}
	public static void reFresh(){
		MAIN_FRAME.invalidate();
		MAIN_FRAME.repaint();
		MAIN_FRAME.setVisible(true);
	}
	public static Main_ui getSingle(){
		if(single == null)single = new Main_ui();
		return single;
	}
}
