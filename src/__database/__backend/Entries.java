package __database.__backend;

import __tool.Pair;

public abstract class Entries {
	public final static int VISITER = 1;
	public final static int SERVICE = 0;
	public final static int VECTOR = 2;
	protected String message_ord;
	protected String id_code;
	public Entries(String a){
		id_code = a;
	}
	public String getCode(){
		return id_code;
	}
	public String getOrd(){
		return message_ord;
	}
	abstract public Message_node to_Message_node();
	abstract public Entries from_Message_node(Message_node m);
	abstract public int get_entries_type();
	abstract public Pair<Double,Double> getExpenseCalculation(Entries e,int t);
}
