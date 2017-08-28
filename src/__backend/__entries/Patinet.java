package __backend.__entries;
import __database.__backend.Entries;
import __database.__backend.Message_node;
import __tool.Pair;


public class Patinet extends Entries{
	private String name,unit_code,birth,citizenship_number;
	private Integer sex;
	private Boolean approve;
	public static final int MALE = 1;
	public static final int FEMALE = 0;
	public Patinet(String a,String b,String c,Integer d,String e,String f,Boolean g){
		super(a);
		name = b;
		citizenship_number = c;
		sex = d;
		unit_code = e;
		birth = f;
		approve = g;
		message_ord = new String("%s%s%s%d%s%s%b");
	}
	public Patinet(){
		super(null);
		name = citizenship_number = 
			unit_code = birth = null;
		sex = null; approve = null;
		message_ord = new String("%s%s%s%d%s%s%b");
	}
	public Message_node to_Message_node(){
		Message_node res = new Message_node();
		res.add(id_code);
		res.add(name);
		res.add(citizenship_number);
		res.add(sex);
		res.add(unit_code);
		res.add(birth);
		res.add(approve);
		return res;
	}
	public Patinet from_Message_node(Message_node m){
		String a = (String)m.elementAt(0);
		String b = (String)m.elementAt(1);
		String c = (String)m.elementAt(2);
		Integer d = (Integer)m.elementAt(3);
		String e = (String)m.elementAt(4);
		String f = (String)m.elementAt(5);
		Boolean g = (Boolean)m.elementAt(6);
		return new Patinet(a,b,c,d,e,f,g);
	}
	public int get_entries_type(){
		return Entries.VISITER;
	}
	public Pair<Double,Double> getExpenseCalculation(Entries e,int t) {
		return null;
	}
}
