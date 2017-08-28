package __backend.__entries;

import __backend.Data_base_init;
import __backend.Hospitalization_information;
import __database.__backend.Data_base;
import __database.__backend.Entries;
import __database.__backend.Message_node;
import __database.__ui.Entries_check_ui;
import __tool.Pair;
import __ui.Main_ui;

public class Medicine extends Entries{
	private String name;
	private Double price,price_limit;
	private Integer hospital_grade,medicine_grade;
	private Boolean specialItem;
	public Medicine(String a,String b,Double c,Double d,Integer e,Integer f,Boolean g){
		super(a);
		name = b;
		price = c;
		price_limit = d;
		hospital_grade = e;
		medicine_grade = f;
		specialItem = g;
		message_ord = new String("%s%s%f%f%d%d%b");
	}
	public Medicine(){
		super(null);
		name = null;
		price = price_limit = null;
		hospital_grade = medicine_grade = null;
		specialItem = null;
		message_ord = new String("%s%s%f%f%d%d%b");
	}
	public Message_node to_Message_node(){
		Message_node res = new Message_node();
		res.add(getCode());
		res.add(name);
		res.add(price);
		res.add(price_limit);
		res.add(hospital_grade);
		res.add(medicine_grade);
		res.add(specialItem);
		return res;
	}
	public Entries from_Message_node(Message_node m){
		String a = (String)m.elementAt(0);
		String b = (String)m.elementAt(1);
		Double c = (Double)m.elementAt(2);
		Double d = (Double)m.elementAt(3);
		Integer e = (Integer)m.elementAt(4);
		Integer f = (Integer)m.elementAt(5);
		Boolean g = (Boolean)m.elementAt(6);
		return new Medicine(a,b,c,d,e,f,g);
	}
	public int get_entries_type(){
		return Entries.SERVICE;
	}
	public Pair<Double,Double> getExpenseCalculation(Entries e,int t) {
		Medicine m = (Medicine)e; boolean tjtz = false; double can;
		Pair<Double,Double> theMostLikelyAns = 
				new Pair<Double,Double>((double) 0,m.price*t);
		String zdddyljg = Main_ui.dataBases.elementAt(0)
						.search(Entries_check_ui.thePatinetNow)
						.elementAt(0).elementAt(4).toString();
		if((m.specialItem != null && m.specialItem)||
				!zdddyljg.equals(Data_base_init.theHospitalId))
				for(Hospitalization_information h :Main_ui.allOfRecords)
					for(Data_base db :h)
						for(Message_node mnode :db)
							if(mnode.elementAt(0).toString().equals(e.getCode())){
								tjtz = true;
							}
			if(!tjtz)return theMostLikelyAns;
		if(m.hospital_grade==null || 
				m.hospital_grade < Data_base_init.theHospitalGrade ||
				m.medicine_grade==null || 
				m.medicine_grade == 3)
			return theMostLikelyAns;
		if(m.medicine_grade == 1)can = t*m.price;
		else can = t*m.price/2;
		can = Math.min(can, m.price_limit);
		return new Pair<Double,Double>(can,t*m.price-can);
	}
}
