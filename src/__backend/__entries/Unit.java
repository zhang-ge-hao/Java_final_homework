package __backend.__entries;

import __database.__backend.Entries;
import __database.__backend.Message_node;
import __tool.Pair;

public class Unit extends Entries{
	private String name;
	private String point_hospital;
	public Unit(String a,String b,String c){
		super(a);
		name = b;
		point_hospital = c;
		message_ord = new String("%s%s%s");
	}
	public Unit(){
		super(null);
		name = point_hospital = null;
		message_ord = new String("%s%s%s");
	}
	public Message_node to_Message_node(){
		Message_node res = new Message_node();
		res.add(getCode());
		res.add(name);
		res.add(point_hospital);
		return res;
	}
	public Entries from_Message_node(Message_node m){
		String a = (String)m.elementAt(0);
		String b = (String)m.elementAt(1);
		String c = (String)m.elementAt(2);
		return new Unit(a,b,c);
	}
	public int get_entries_type(){
		return Entries.VISITER;
	}
	public Pair<Double,Double> getExpenseCalculation(Entries e,int t) {
		return null;
	}
}
