package __backend.__entries;

import __backend.Data_base_init;
import __backend.Hospitalization_information;
import __database.__backend.Data_base;
import __database.__backend.Entries;
import __database.__backend.Message_node;
import __database.__ui.Entries_check_ui;
import __tool.Pair;

public class Special_node extends Entries{
	public static int ITEM_NUM = 6;
	String[] mes = new String[ITEM_NUM];
	public static int countListCode = 0,countListsCode = 0;
	public Special_node(String a,String[] b){
		super(a);
		message_ord = "";
		for(int i=0;i<ITEM_NUM;i++){
			mes[i] = b[i];
			message_ord += "%s";
		}
	}
	public Special_node(){
		super(null);
		message_ord = "";
		for(int i=0;i<ITEM_NUM;i++){
			mes[i] = null;
			message_ord += "%s";
		}
	}
	public Message_node to_Message_node(){
		Message_node res = new Message_node();
		res.add(id_code);
		for(int i=0;i<ITEM_NUM;i++)res.add(mes[i]);
		return res;
	}
	public Entries from_Message_node(Message_node m){
		String a; String[] b = new String[ITEM_NUM];
		a = id_code;
		for(int i=0;i<ITEM_NUM;i++)b[i] = (String)m.elementAt(i);
		return new Special_node(a,b);
	}
	public int get_entries_type(){
		return Entries.SERVICE;
	}
	public static String getNexListCode(){
		countListCode ++;
		if(countListCode == 1000)countListCode = 0;
		return String.format("YF%0"+Data_base_init.BITS+"d",countListCode);
	}
	public static String getNextListsCode(){
		countListsCode ++;
		if(countListsCode == 1000)countListsCode = 0;
		return String.format("JL%0"+Data_base_init.BITS+"d",countListsCode);
	}
	public static int getRankOfCode(String code){
		code = code.substring(2);
		return Integer.parseInt(code);
	}
	static public Message_node toSpecialNode(Data_base list){
		String a = list.getCode(); int i;
		String[] b = new String[ITEM_NUM];
		for(i=0;i<ITEM_NUM;i++)b[i] = "";
		b[0] = (Entries_check_ui.hosInf==null
				||Entries_check_ui.hosInf.getPatinet()==null)
				?"":Entries_check_ui.hosInf.getPatinet();
		i = 1;
		for(Message_node m :list){
			b[i++] = m.elementAt(0).toString()+" x"+m.elementAt(1).toString();
			if(i == ITEM_NUM-1)break;
		}
		if(list.size() > ITEM_NUM-2)b[i-1] = "...";
		return new Special_node(a,b).to_Message_node();
	}
	static public Message_node toSpecialNode(Hospitalization_information list){
		String a = list.getCode(); int i;
		String[] b = new String[ITEM_NUM];
		for(i=0;i<ITEM_NUM;i++)b[i] = "";
		b[0] = list.getPatinet()!=null?list.getPatinet():"";
		i = 1;
		for(Data_base m :list){
			b[i++] = m.getCode()+"("+m.size()+")";
			if(i == ITEM_NUM)break;
		}
		return new Special_node(a,b).to_Message_node();
	}
	public Pair<Double,Double> getExpenseCalculation(Entries e,int t) {
		return null;
	}
}

