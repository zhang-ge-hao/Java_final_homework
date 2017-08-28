package __backend;

import java.util.Vector;

import __database.__backend.Data_base;
import __database.__backend.Entries;
import __database.__backend.Message_node;
import __database.__ui.Entries_check_ui;
import __tool.Pair;
import __tool.RegexMatch;
import __ui.Main_ui;

public class ExpenseCalculation {
	static public Vector<Pair<String,Vector<Pair<String,Pair<Double,Double>>>>> resList;
	static public void getExpenseCalculation(){
		resList = new Vector<Pair<String,Vector<Pair<String,Pair<Double,Double>>>>>();
		Vector<Pair<String,Pair<Double,Double>>> aDb;
		Pair<Double,Double> aPair;
		for(Data_base db :Entries_check_ui.hosInf){
			aDb = new Vector<Pair<String,Pair<Double,Double>>>();
			for(Message_node m :db){
				aPair = getAnItem(m.elementAt(0).toString(),(Integer)m.elementAt(1));
				aDb.add(new Pair<String,Pair<Double,Double>>
					(m.elementAt(0).toString(),aPair));
			}
			resList.add(new Pair<String,Vector<Pair<String,Pair
					<Double,Double>>>>(db.getCode(),aDb));
		}
	}
	public static Pair<Double,Double> getAnItem(String code,int t){
		for(int i=0;i<Data_base_init.ITEM_TYPE_CON;i++)
			if(RegexMatch.absoluteMatch(code
					,Main_ui.regexs.elementAt(i).elementAt(0).second)){
				Entries esign = Main_ui.dataBases.elementAt(i).getSign();
				Message_node m = Main_ui.dataBases.elementAt(i).search(code).elementAt(0);
				return esign.getExpenseCalculation(esign.from_Message_node(m),t);
			}
		return null;
	}
}
