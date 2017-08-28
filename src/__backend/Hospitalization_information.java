package __backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import __backend.__entries.Prescription_node;
import __backend.__entries.Special_node;
import __database.__backend.Data_base;
import __database.__backend.File_IO;
import __database.__backend.Message_node;
import __database.__ui.Entries_check_ui;
import __tool.RegexMatch;
import __ui.Main_ui;
import __ui.Record_display_ui;

public class Hospitalization_information implements Iterable<Data_base>{
	private String id_code;
	private String patinet;
	private Vector<Data_base> pres;
	private int saveMode = 0;
	public Hospitalization_information(String a,String pat){
		id_code = a;
		patinet = pat;
		pres = new Vector<Data_base>();
	}
	public Hospitalization_information(String fileName){
		pres = new Vector<Data_base>();
		load(fileName);
	}
	public Hospitalization_information(String a,String pat,
			Vector<Vector<Map<Message_node,Integer>>> b){
		id_code = a;
		patinet = pat;
		pres = new Vector<Data_base>();
		fromCheckes(b);
	}
	public void add(Data_base db){
		pres.add(db);
	}
	public void del(String code){
		for(Data_base db :pres){
			if(db.getCode().equals(code)){
				pres.remove(db); break;
			}
		}
	}
	public void clear(){
		pres.clear();
		id_code = patinet = null;
	}
	public void load(String fileName){
		String messageStr;
		String ord = new String("%s%d");
		Data_base db = new Data_base(new Prescription_node());
		Message_node node;
		int _size;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			clear();
			id_code = reader.readLine();
			patinet = reader.readLine();
			if(patinet.equals(File_IO.NULL))patinet = "";
			saveMode = Integer.parseInt(reader.readLine());
			Special_node.countListsCode = Math.max
				(Special_node.countListsCode,Special_node.getRankOfCode(id_code));
			while((messageStr=reader.readLine())!=null){
				db.setCode(messageStr);
				Special_node.countListCode = Math.max
					(Special_node.countListCode,Special_node.getRankOfCode(messageStr));
				messageStr=reader.readLine();
				_size = Integer.parseInt(messageStr);
				for(int j=0;j<_size;j++){
					messageStr = reader.readLine();
					node = File_IO.getNode(messageStr, ord);
					db.add(db.getSign().from_Message_node(node));
				}
				pres.add(new Data_base(db));
				db.clear();
			}
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public void fromCheckes(Vector<Vector<Map<Message_node,Integer>>> c){
		if(c == null)return;
		for(Vector<Map<Message_node,Integer>> v :c){
			pres.add(fromChecked(v));
		}
	}
	static public Data_base fromChecked(Vector<Map<Message_node,Integer>> v){
		Data_base db = new Data_base(new Prescription_node());
		db.setCode(Special_node.getNexListCode());
		for(Map<Message_node,Integer> m :v){
			for (Map.Entry<Message_node,Integer> it : m.entrySet()) { 
				db.add(new Prescription_node
						(it.getKey().elementAt(0).toString(),it.getValue()));
			}
		}
		return db;
	}
	public void save(String fileName){
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(fileName,false)));
			writer.close(); //clear file
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName,true)));
			writer.write(id_code+"\r\n");
			writer.write(patinet+"\r\n");
			writer.write(saveMode+"\r\n");
			for(Data_base database : pres){
				writer.write(database.getCode()+"\r\n");
				writer.write(database.size()+"\r\n");
				database.save(writer);
			}
			writer.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public Vector<Vector<Map<Message_node,Integer>>> toCheckes(){
		Vector<Vector<Map<Message_node,Integer>>> res = 
				new Vector<Vector<Map<Message_node,Integer>>>();
		for(Data_base db :pres){
			Vector<Map<Message_node,Integer>> toAdd = 
					toChecked(db);
			res.add(toAdd);
		}
		return res;
	}
	public static Vector<Map<Message_node,Integer>> toChecked(Data_base db){
		Vector<Map<Message_node,Integer>> toAdd = 
				new Vector<Map<Message_node,Integer>>();
		for(int i=0;i<Data_base_init.ITEM_TYPE_CON;i++)toAdd.add(new TreeMap<Message_node,Integer>());
		for(Message_node m :db){
			addItem(m.elementAt(0).toString()
					,(Integer)m.elementAt(1),toAdd);
		}
		return toAdd;
	}
	public static void addItem(String c,int t
			,Vector<Map<Message_node,Integer>> toAdd){
		for(int i=0;i<Data_base_init.ITEM_TYPE_CON;i++){
			String r = Main_ui.regexs.elementAt(i).elementAt(0).second;
			if(RegexMatch.absoluteMatch(c,r)){
				toAdd.elementAt(i).put(
					Main_ui.dataBases.elementAt(i).search(c).elementAt(0),t);
				break;
			}
		}
	}
	public String getCode(){
		return id_code;
	}
	public void setCode(String c){
		id_code = c;
	}
	public String getPatinet(){
		return patinet;
	}
	public void setPatinet(String p){
		patinet = p;
	}
	public Iterator<Data_base> iterator() {
		return pres.iterator();
	}
	public int getSaveMode(){
		return saveMode;
	}
	public boolean checkFull(){
		if(this.getPatinet()==null || this.getCode().equals(""))
			return false;
		if(this.pres.size() == 0)return false;
		return true;
	}
	public void setSaveMode(int m){
		if(saveMode == 3)return;
		if(m == 1 && saveMode == 0)saveMode ++;
		else if(m == 2){
			if(!checkFull()){
				JOptionPane.showMessageDialog(null
					,"Illegal operation, please fill in the necessary information."
					, "",JOptionPane.WARNING_MESSAGE);
				if(saveMode == 0)saveMode = 1;
			}else if(saveMode < 2)saveMode = 2;
			else saveMode = 3;
		}
		boolean hadSave = false;
		for(int i=0;i<Main_ui.allOfRecords.size();i++)
			if(Entries_check_ui.hosInf.getCode()
					.equals(Main_ui.allOfRecords.elementAt(i).getCode())){
				Main_ui.allOfRecords.setElementAt(Entries_check_ui.hosInf,i);
				hadSave = true;
				break;
			}
		if(!hadSave)Main_ui.allOfRecords.add(Entries_check_ui.hosInf);
		Entries_check_ui.hosInf.setCode(Special_node.getNextListsCode());
		Record_display_ui.getSingle().getItemsFresh();
	}
}
