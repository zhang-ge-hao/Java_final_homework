package __database.__backend;
import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class Data_base implements Iterable<Message_node>{
	private Set<Message_node> data_base;
	private Map<String,Message_node> engine;
	private Entries type_sign;
	private String specialCode = null;
	public Data_base(Entries sign){
		type_sign = sign;
		data_base = new TreeSet<Message_node>();
		engine = new TreeMap<String,Message_node>();
	}
	public Data_base(Data_base that){
		this.specialCode = that.specialCode;
		this.data_base = new TreeSet<Message_node>(that.data_base);
		this.engine = new TreeMap<String,Message_node>(that.engine);
		this.type_sign = that.type_sign;
	}
	public Data_base(Entries sign,String fileName){
		data_base = null; engine = null;
		type_sign = sign;
		load(fileName);
	}
	public int size(){
		return data_base.size();
	}
	public Vector<Message_node> search(Entries e){
		Vector<Message_node> res = new Vector<Message_node>();
		if(e.getCode()==null){
			for(Message_node m:data_base){
				if(e.to_Message_node().equals(m))
					res.add(m);
			}
		}else res.add(engine.get(e.getCode()));
		return res;
	}
	public Vector<Message_node> search(String c){
		Vector<Message_node> res = new Vector<Message_node>();
		res.add(engine.get(c));
		return res;
	}
	public Vector<Message_node> fuzzy_search(String s2){
		Vector<Message_node> res = new Vector<Message_node>();
		String s1;
		for(Message_node m:data_base){
			s1 = (String)m.elementAt(0);
			if(s1.indexOf(s2) != -1)
				res.add(m);
		}
		return res;
	}
	public boolean add(Entries e){
		if(e.getCode()!=null||!data_base.contains(e.to_Message_node())){
			engine.put(e.getCode(),e.to_Message_node());
			data_base.add(e.to_Message_node());
			return true;
		}else return false;
	}
	public boolean del(Entries e){
		if(e.getCode()!=null||data_base.contains(e.to_Message_node())){
			engine.remove(e.getCode());
			data_base.remove(e.to_Message_node());
			return true;
		}else return false;
	}
	public boolean updata(Entries h,Entries t){
		if(data_base.contains(h.to_Message_node())){
			del(h); add(t);
			return true;
		}else return false;
	}
	public void clear(){
		if(data_base == null)
			this.data_base = new TreeSet<Message_node>();
		if(engine == null)
			this.engine = new TreeMap<String,Message_node>();
		data_base.clear();
		engine.clear();
	}
	public void load(String fileName){
		clear();
		Vector<Message_node> r = File_IO.read(type_sign.getOrd(), fileName);
		for(Message_node m : r){
			add(type_sign.from_Message_node(m));
		}
	}
	public void save(String fileName,boolean mode){
		Vector<Message_node> w = new Vector<Message_node>();
		for(Message_node m : data_base){
			w.add(m);
		}
		File_IO.write(w, fileName, mode);
	}
	public void save(BufferedWriter writer){
		Vector<Message_node> w = new Vector<Message_node>();
		for(Message_node m : data_base){
			w.add(m);
		}
		File_IO.write(w,writer);
	}
	public String toString(){
		StringBuffer res = new StringBuffer();
		for(Message_node m:data_base){
			res.append(m.toString()+"\r\n");
		}
		return res.toString();
	}
	public Entries getSign(){
		return type_sign;
	}
	public Iterator<Message_node> iterator() {
		return data_base.iterator();
	}
	public String getCode(){
		return specialCode;
	}
	public void setCode(String s){
		specialCode = s;
	}
}
