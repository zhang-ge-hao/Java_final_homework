package __ui;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import __backend.Hospitalization_information;
import __backend.__entries.Special_node;
import __database.__backend.Message_node;
import __database.__ui.Entries_check_ui;
import __database.__ui.Entries_display_ui;

public class Record_display_ui {
	private static Special_desplay_ui single =
			new Special_desplay_ui(){
		public void getItemsFresh(){
			Message_node m;
			Vector<Message_node> v = new Vector<Message_node>();
			for(Hospitalization_information h :Main_ui.allOfRecords){
				m = Special_node.toSpecialNode(h);
				v.add(m);
			}
			displayItems(1,v);
		}
		public void rowClickedLeftMouseRun(int _rank,JPanel thePanel){
			mainPanelMouseListenerRun();
			Entries_display_ui.swapColor(thePanel);
			String code = getListCode(thePanel);
			for(Hospitalization_information h :Main_ui.allOfRecords)
				if(h!=null && h.getCode().equals(code)){
					Entries_check_ui.hosInf = h;
					break;
				}
            Entries_check_ui.thePatinetNow = Entries_check_ui.hosInf.getPatinet();
            Entries_check_ui.checkes = 
            		Entries_check_ui.hosInf.toCheckes();
            Work_ui.getExpenseCalculationRes();
            Work_ui.displayChecked();
		}
		public void mainPanelMouseListenerRun(){
			if((Entries_check_ui.checkes!=null
					&& Entries_check_ui.checkes.size()>0)
							|| Entries_check_ui.thePatinetNow!=null){
				if(Entries_check_ui.hosInf.getSaveMode()==0){
					int exi = JOptionPane.showConfirmDialog
	                		(null,"You are editting ,Save changes?", "",
	                				JOptionPane.YES_NO_OPTION,
	                				JOptionPane.QUESTION_MESSAGE);
	                if (exi == JOptionPane.YES_OPTION){
	                	Entries_check_ui.hosInf.setSaveMode(1);
	                }
				}
			}
			Entries_check_ui.hosInf = null;
            Entries_check_ui.thePatinetNow = null;
            Entries_check_ui.checkes = null;
            Entries_check_ui.checked = null;
			Work_ui.displayChecked();
			Work_ui.setPatient(null);
            Main_ui.getSingle().changeSide(false);
		}
		public void rowClickedRightMouseRun(int _rank,JPanel thePanel){
			String code = getListCode(thePanel);
			for(int i=0;i<Main_ui.allOfRecords.size();i++)
				if(code.equals(Main_ui.allOfRecords.elementAt(i).getCode())){
					Main_ui.allOfRecords.remove(i);
					break;
				}
			getItemsFresh();
		}
	};
	private Record_display_ui(){}
	public static JPanel getMainPanel(){
		return single.getMainPanel();
	}
	public static Special_desplay_ui getSingle(){
		return single;
	}
}
