package __backend.__entries;

import __database.__backend.Entries;
import __database.__backend.Message_node;
import __tool.Pair;

public class Prescription_node extends Entries{
	private Integer times;
	public Prescription_node(String a,int t){
		super(a);
		times = t;
		message_ord = new String("%s%d");
	}
	public Prescription_node(){
		super(null);
		times = null;
		message_ord = new String("%s%d");
	}
	public Message_node to_Message_node(){
		Message_node res = new Message_node();
		res.add(getCode());
		res.add(times);
		return res;
	}
	public Entries from_Message_node(Message_node m){
		String a = (String)m.elementAt(0);
		int b = (Integer)m.elementAt(1);
		Prescription_node res = new Prescription_node(a,b);
		return res;
	}
	public String toString(){
		return getCode()+" "+times;
	}
	public int get_entries_type(){
		return Entries.SERVICE;
	}
	public Pair<Double,Double> getExpenseCalculation(Entries e,int t) {
		return null;
	}
}
