package __database.__ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import __backend.Data_base_init;
import __backend.Hospitalization_information;
import __backend.__entries.Patinet;
import __database.__backend.Entries;
import __database.__backend.Message_node;
import __ui.Main_ui;
import __ui.Work_ui;

public class Entries_check_ui extends Entries_display_ui{
	static public int CHECK_ONE_PATINET = 1;
	static public int CHECK_SEVERAL_COMMODITY = 2;
	static public Vector<Map<Message_node,Integer>> checked = null;
	static public Vector<Vector<Map<Message_node,Integer>>> checkes = null;
	static public Hospitalization_information hosInf = null;
	static public String thePatinetNow = null;
	static private int mode = -1;
	public Entries_check_ui(int rank,int _mode){
		super(rank);
		mode = _mode;
	}
	static public void checkedInit(){
		checked = new Vector<Map<Message_node,Integer>>();
		for(int i=0;i<Data_base_init.ITEM_TYPE_CON;i++)
			checked.add(new TreeMap<Message_node,Integer>());
	}
	static public void hosInfInit(){
		if(Entries_check_ui.hosInf == null)
			Entries_check_ui.hosInf = 
				new Hospitalization_information
				("",thePatinetNow,Entries_check_ui.checkes);
	}
	static public int checkedSize(){
		int res = 0;
		if(checked == null){
			checkedInit(); return 0;
		}else for(int i=0;i<Data_base_init.ITEM_TYPE_CON;i++)
			res += checked.elementAt(i).size();
		return res;
	}
	public Entries_check_ui(Vector<Map<Message_node,Integer>> c){
		super(0);
		checked = c;
		mode = CHECK_SEVERAL_COMMODITY;
	}
	public boolean checkIllegal(){
		if(mode==CHECK_ONE_PATINET && (rank!=0||checked.elementAt(0).size()>=1)){
			JOptionPane.showMessageDialog(this.getMainPanel()
				,"Choosing one patient, please do not choose more or some other entries."
				, "",JOptionPane.WARNING_MESSAGE);
			return true;
		}else if(mode == CHECK_SEVERAL_COMMODITY 
			&& Main_ui.dataBases.elementAt(rank).getSign().get_entries_type()!=Entries.SERVICE){
			JOptionPane.showMessageDialog(this.getMainPanel()
				,"Choosing several commodities, please do not choose some other entries."
				, "",JOptionPane.WARNING_MESSAGE);
			return true;
		}
		return false;
	}
	public void rowClickedRightMouseRun(int _rank,JPanel thePanel){
		Message_node exeNode = displayItems.elementAt(_rank);
		if(checkedJudge(_rank)){
			if(mode == CHECK_SEVERAL_COMMODITY){
				thePanel.remove(thePanel.getComponentCount()-1);
				thePanel.remove(thePanel.getComponentCount()-1);
				Main_ui.reFresh();
			}
			checked.elementAt(rank).remove(exeNode);
		}else{
			if(checkIllegal())return;
			checked.elementAt(rank).put(exeNode,1);
			if(mode == CHECK_SEVERAL_COMMODITY){
				getNumSelect(exeNode,thePanel,false);
			}
		}
	}
	public void getNumSelect(final Message_node exe,JPanel rowPanel,boolean hasIn){
		String text = hasIn?(""+checked.elementAt(rank).get(exe)):"1";
		final JLabel lab = new JLabel(text,JLabel.CENTER);
		lab.setPreferredSize(new Dimension(15,15));
		lab.setForeground(Color.WHITE);
		lab.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
		lab.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				lab.setText(""+(Integer.parseInt(lab.getText())+1));
				lab.setPreferredSize(new Dimension(10*lab.getText().length()+5,15));
				Integer t = checked.elementAt(rank).get(exe);
				checked.elementAt(rank).remove(exe);
				checked.elementAt(rank).put(exe,t+1);
			}
			public void mouseEntered(MouseEvent e){
				
			}
			public void mouseExited(MouseEvent e){
				
			}
		});
		JLabel block = new JLabel("");
		block.setPreferredSize(new Dimension(100-lab.getWidth(),15));
		rowPanel.add(block); rowPanel.add(lab);
		Main_ui.reFresh();
	}
	public boolean checkedJudge(int _rank){
		if(checked == null){
			checkedInit(); return false;
		}
		Message_node exeNode = displayItems.elementAt(_rank);
		return checked.elementAt(rank).containsKey(exeNode);
	}
	public boolean checkedJudge(Message_node exe){
		if(checked == null){
			checkedInit(); return false;
		}else if(mode != CHECK_SEVERAL_COMMODITY)return false;
		return checked.elementAt(rank).containsKey(exe);
	}
	static public void checksRefresh(String patinetCode){
		if(patinetCode == null || patinetCode.equals("")){
			checkedInit(); return;
		}
		checkedInit();
		checked.elementAt(0).put(new Patinet(
				patinetCode,null,null,null,null,null,null
				).to_Message_node(),1);
		Work_ui.displayChecked();
	}
	static public void checksRefresh(){
	}
}
