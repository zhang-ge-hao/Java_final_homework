package __ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import __backend.Data_base_init;
import __backend.Hospitalization_information;
import __backend.__entries.Special_node;
import __database.__backend.Message_node;
import __database.__ui.Data_base_ui;
import __database.__ui.Entries_check_ui;
import __database.__ui.Entries_display_ui;

public class Special_desplay_ui extends Entries_display_ui{
	public Special_desplay_ui(){
		super(Data_base_init.ITEM_TYPE_CON
				,590,Entries_display_ui.MAIN_HEIGHT*2-30,19);
		ansPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				mainPanelMouseListenerRun();
			}
		});
		getItemsFresh();
	}
	public void getItemsFresh(){}
	public Special_desplay_ui(int rank,int w,int h,int r){
		super(rank,w,h,r);
		displayItems(1,new Special_node().to_Message_node());
		ansPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				mainPanelMouseListenerRun();
			}
		});
	}
	public void mainPanelMouseListenerRun(){
		if(Entries_check_ui.hosInf!=null
				&& Entries_check_ui.hosInf.getSaveMode()>1)
			return;
		Entries_check_ui.checkedInit();
		Entries_check_ui cui = new Entries_check_ui
				(0,Entries_check_ui.CHECK_SEVERAL_COMMODITY);
		new Data_base_ui(0,cui).show();
		if(Entries_check_ui.checkes==null)
			Entries_check_ui.checkes = 
				new Vector<Vector<Map<Message_node,Integer>>>();
		if(Entries_check_ui.checkedSize() > 0){
			Entries_check_ui.checkes.add(Entries_check_ui.checked);
			Entries_check_ui.hosInf.add
				(Hospitalization_information.fromChecked(Entries_check_ui.checked));
		}
		Work_ui.getExpenseCalculationRes();
		Entries_check_ui.checked = null;
		Work_ui.displayChecked();
	}
	public void rowClickedLeftMouseRun(int _rank,JPanel thePanel){
		if(Entries_check_ui.hosInf!=null
				&& Entries_check_ui.hosInf.getSaveMode()>1){
			return;
		}		
		Entries_check_ui.checked = 
				Entries_check_ui.checkes.elementAt(_rank);
		Entries_check_ui.checksRefresh();
		Entries_check_ui dis = new Entries_check_ui
				(1,Entries_check_ui.CHECK_SEVERAL_COMMODITY);
		new Data_base_ui(1,dis).show();
		Entries_check_ui.hosInf.del(getListCode(thePanel));
		Entries_check_ui.checkes = Entries_check_ui.hosInf.toCheckes();
		if(Entries_check_ui.checkes==null)
			Entries_check_ui.checkes = 
				new Vector<Vector<Map<Message_node,Integer>>>();
		if(Entries_check_ui.checkedSize() > 0){
			Entries_check_ui.checkes.add(Entries_check_ui.checked);
			Entries_check_ui.hosInf.add
				(Hospitalization_information.fromChecked(Entries_check_ui.checked));
		}
		Work_ui.getExpenseCalculationRes();
		Entries_check_ui.checked = null;
		Work_ui.displayChecked();
	}
	public void rowClickedRightMouseRun(int _rank,JPanel thePanel){
		if(Entries_check_ui.hosInf!=null 
				&& Entries_check_ui.hosInf.getSaveMode()>1){
			return;
		}
		Entries_check_ui.hosInf.del(getListCode(thePanel));
		Entries_check_ui.checkes = Entries_check_ui.hosInf.toCheckes();
		Work_ui.getExpenseCalculationRes();
		Work_ui.displayChecked();
	}
	public String getListCode(JPanel thePanel){
		JLabel label = (JLabel)thePanel.getComponent(0);
		return label.getText();
	}
}
