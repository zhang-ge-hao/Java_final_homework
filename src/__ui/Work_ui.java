package __ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import __backend.Data_base_init;
import __backend.ExpenseCalculation;
import __backend.__entries.Patinet;
import __backend.__entries.Special_node;
import __database.__backend.Data_base;
import __database.__backend.Message_node;
import __database.__ui.Data_base_ui;
import __database.__ui.Entries_check_ui;
import __database.__ui.Entries_display_ui;
import __tool.Pair;

public class Work_ui{
	static public int PATINET_PANEL_HEIGHT = 60;
	static public int MESSAGE_PANEL_HEIGHT = 
			Entries_display_ui.MAIN_HEIGHT-PATINET_PANEL_HEIGHT/2+10;
	static public int ROW_COUNT;
	static private Work_ui single = new Work_ui();
	private JPanel workPanel,patinetPanel;
	private static JPanel itemPanel;
	private static JPanel resMessagePanel;
	private static JPanel subPanel1;
	private static Border subPanelBoard;
	private static JTextArea resultArea;
	private static JLabel nameLabel;
	private static JLabel codeLabel;
	private static JLabel numLabel;
	private Work_ui(){
		subPanelBoard = BorderFactory.createMatteBorder
				(1,1,2,2,Color.DARK_GRAY);
		workPanel = new JPanel(new BorderLayout(20,0));
		workPanel.setPreferredSize(new Dimension
				(Main_ui.MAIN_WEIGHT,Entries_display_ui.MAIN_HEIGHT*2));
		patinetPanelInit();
		workPanel.add(patinetPanel,BorderLayout.NORTH);
		itemPanelInit();
		workPanel.add(itemPanel,BorderLayout.CENTER);
		resMessagePanelInit();
		workPanel.add(resMessagePanel,BorderLayout.SOUTH);
		displayChecked();
	}
	public void patinetPanelInit(){
		nameLabel = new JLabel("",JLabel.CENTER);
		nameLabel.setFont(new Font("Dialog",2,24));
		nameLabel.setPreferredSize
			(new Dimension(200,25));
		codeLabel = new JLabel("",JLabel.LEFT);
		codeLabel.setFont(new Font("Dialog",1,12));
		numLabel = new JLabel("",JLabel.LEFT);
		numLabel.setFont(new Font("Dialog",2,12));
		subPanel1 = new JPanel(new GridLayout(2,1,0,4));
		subPanel1.setPreferredSize
			(new Dimension(200,25));
		subPanel1.add(codeLabel,0,0); subPanel1.add(numLabel,0,1);
		patinetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Border titleBorder = BorderFactory.createTitledBorder("Patinet");
		patinetPanel.setBorder(BorderFactory
				.createCompoundBorder(titleBorder,subPanelBoard));
		patinetPanel.setPreferredSize
			(new Dimension(Main_ui.MAIN_WEIGHT,PATINET_PANEL_HEIGHT));
		patinetPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(Entries_check_ui.hosInf!=null
						&& Entries_check_ui.hosInf.getSaveMode()>1)
					return;
				Entries_check_ui.checksRefresh(codeLabel.getText());
				Entries_check_ui cui = new Entries_check_ui
						(0,Entries_check_ui.CHECK_ONE_PATINET);
				new Data_base_ui(0,cui).show();
				getExpenseCalculationRes();
				Map<Message_node, Integer> s = Entries_check_ui.checked.elementAt(0);
				if(s.size() == 0)setPatient(null);
				else for(Message_node m:s.keySet()){
					m = Main_ui.dataBases.elementAt(0)
						.search(m.elementAt(0).toString()).elementAt(0);
					setPatient(m);
					if(m != null)Entries_check_ui
						.thePatinetNow = m.elementAt(0).toString();
					break;
				}
				displayChecked();
			}
		});
		patinetPanel.add(nameLabel);
		patinetPanel.add(subPanel1);
	}
	public static void setPatient(String a,String b,String c,String d){
		Entries_check_ui.thePatinetNow = a;
		codeLabel.setText(b);
		nameLabel.setText(c);
		numLabel.setText(d);
	}
	public static void setPatient(Message_node m){
		if(m != null){
			setPatient(new Patinet().from_Message_node(m).getCode()
					,m.elementAt(0).toString()
					,m.elementAt(1).toString()
					,m.elementAt(2).toString());
		}else setPatient(null,"","","");
	}
	public void itemPanelInit() {
		itemPanel = new JPanel();
		Border titleBorder = BorderFactory.createTitledBorder("Prescriptions");
		itemPanel.setBorder(BorderFactory
				.createCompoundBorder(titleBorder,subPanelBoard));
		itemPanel.setPreferredSize
			(new Dimension(Main_ui.MAIN_WEIGHT,MESSAGE_PANEL_HEIGHT));
	}
	static public void displayChecked(){
		if(Entries_check_ui.thePatinetNow != null){
			Message_node p = Main_ui.dataBases.elementAt(0)
					.search(Entries_check_ui.thePatinetNow).elementAt(0);
			setPatient(p);
		}
		if(Entries_check_ui.hosInf == null)
			Entries_check_ui.hosInfInit();
		Entries_check_ui.hosInf
			.setPatinet(Entries_check_ui.thePatinetNow);
		Vector<Message_node> dis = new Vector<Message_node>();
		for(Data_base db :Entries_check_ui.hosInf){
			dis.add(Special_node.toSpecialNode(db));
		}
		Special_desplay_ui disui = new Special_desplay_ui
				(Data_base_init.ITEM_TYPE_CON,575,220,7);
		disui.displayItems(Entries_display_ui.TOT_PAGE,dis);
		itemPanel.removeAll();
		itemPanel.add(disui.getMainPanel());
		checkSave();
		Main_ui.reFresh();
	}
	public static void getExpenseCalculationRes(){
		resultArea.setText(getExpenseCalculationText());
	}
	public static String getExpenseCalculationText(){
		String line1 = "\t\t____________________________________\n";
		String line2 = "\t\t．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．\n";
		String line3 = "____________________________________________\n";
		StringBuffer res = new StringBuffer();
		DecimalFormat df = new DecimalFormat("0.00");
		if(Entries_check_ui.thePatinetNow==null || 
				Entries_check_ui.thePatinetNow.equals("") ||
				Entries_check_ui.hosInf == null)
			return res.toString();
		int i = 0;double sum1,sum2,sum3,sum4;
		for(Data_base h :Entries_check_ui.hosInf)
			i += h.size();
		if(i == 0)return res.toString();
		ExpenseCalculation.getExpenseCalculation();
		int r = 0; sum1 = sum2 = 0;
		for(Pair<String,Vector<Pair<String,Pair<Double,Double>>>> p1 
				:ExpenseCalculation.resList){
			res.append("  Prescription "+(++r)+" :\n");
			sum3 = sum4 = 0;
			for(Pair<String,Pair<Double,Double>> p2 :p1.second){
				res.append("  \tthe Item's Code : "+p2.first+
					"\tCan : "+df.format(p2.second.first)+
					"\tCan not : "+df.format(p2.second.second)+"\n");
				sum3 += p2.second.first; sum4 += p2.second.second;
			}
			res.append(line1+"\t\t\tTotal : Can "
					+df.format(sum3)+"and Can not"+df.format(sum4)+"\n"+line2);
			sum1 += sum3; sum2 += sum4;
		}
		res.append("\n\n\n"+line3+"  This record list has Total : Can "
				+df.format(sum1)+" Can not "+df.format(sum2)+"\n");
		String[] con = res.toString().split("\n");
		ROW_COUNT = (con!=null)?con.length-1:0;
		return res.toString();
	}
	public static void checkSave(){
		Border titleBorder = BorderFactory.createTitledBorder("Settlement");
		Border subB = BorderFactory.createMatteBorder
				(1,1,2,2,Color.DARK_GRAY);
		if(Entries_check_ui.hosInf==null){
			subB = BorderFactory.createMatteBorder(1,1,2,2,Color.DARK_GRAY);
		}else if(Entries_check_ui.hosInf.getSaveMode() == 2){
			subB = BorderFactory.createMatteBorder(2,2,4,4,Color.ORANGE);
		}else if(Entries_check_ui.hosInf.getSaveMode() == 3){
			subB = BorderFactory.createMatteBorder(2,2,4,4,Color.GREEN);
		}
		resMessagePanel.setBorder(BorderFactory
				.createCompoundBorder(titleBorder,subB));
		Main_ui.reFresh();
	}
	public void resMessagePanelInit() {
		resultArea = new JTextArea();
		JScrollPane scro = new JScrollPane(resultArea);
		resMessagePanel = new JPanel();
		resMessagePanel.add(scro);
		checkSave();
		resMessagePanel.setPreferredSize
			(new Dimension(Main_ui.MAIN_WEIGHT,MESSAGE_PANEL_HEIGHT));
		scro.setPreferredSize
			(new Dimension(Main_ui.MAIN_WEIGHT-25,MESSAGE_PANEL_HEIGHT-37));
		scro.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		resultArea.setPreferredSize
		(new Dimension(Main_ui.MAIN_WEIGHT-25,1000));		
		scro.setBorder(BorderFactory.createEmptyBorder());
		resultArea.setBorder(BorderFactory.createEmptyBorder()); 
		resultArea.setBackground(new Color(235,235,235));
		resultArea.setFont(new Font("Meiryo",1,12));  
		resultArea.setWrapStyleWord(true);  
		resultArea.setLineWrap(true);
		resultArea.setEditable(false);
		resultArea.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				Entries_check_ui.hosInf
					.setSaveMode(e.getClickCount()==2?2:1);
				checkSave();
			}
		});
	}
	static public JPanel getMainPanel(){
		return single.workPanel;
	}
	static public Work_ui getSingle(){
		return single;
	}
}
