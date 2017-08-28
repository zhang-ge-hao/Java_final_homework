package __backend.__entries;

import __database.__backend.Entries;
import __database.__backend.Message_node;
import __tool.Pair;

public class Others extends Entries{
	private String name;
	private Double price;
	public Others(String a,String b,Double c){
		super(a);
		name = b;
		price = c;
		message_ord = new String("%s%s%f");
	}
	public Others(){
		super(null);
		name = null;
		price = null;
		message_ord = new String("%s%s%f");
	}
	public Message_node to_Message_node(){
		Message_node res = new Message_node();
		res.add(getCode());
		res.add(name);
		res.add(price);
		return res;
	}
	public Entries from_Message_node(Message_node m){
		String a = (String)m.elementAt(0);
		String b = (String)m.elementAt(1);
		Double c = (Double)m.elementAt(2);
		return new Others(a,b,c);
	}
	public int get_entries_type(){
		return Entries.SERVICE;
	}
	public Pair<Double,Double> getExpenseCalculation(Entries e,int t) {
		return new Pair<Double,Double>((double) 0,price*t);
	}
}
