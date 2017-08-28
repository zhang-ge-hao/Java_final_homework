package __backend;

import java.io.File;
import java.util.Vector;

import __backend.__entries.Medicine;
import __backend.__entries.Patinet;
import __backend.__entries.Service_facilities;
import __backend.__entries.Special_node;
import __backend.__entries.Treatment_project;
import __backend.__entries.Unit;
import __database.__backend.Data_base;
import __tool.Pair;
import __tool.RegexMatch;
import __ui.Main_ui;

public class Data_base_init {
	static public int BITS = 3;
	static public int ITEM_TYPE_CON = 6;
	static public int theHospitalGrade = 2;
	static public String theHospitalId = new String("YY001");
	static public void init(){
		Main_ui.dataBases = new Vector<Data_base>();
		Main_ui.titleFonts = new Vector<Vector<Pair<String,Integer>>>();
		Main_ui.smallTitles = new Vector<String>();
		Main_ui.regexs = new Vector<Vector<Pair<Boolean,String>>>();
		Main_ui.allOfRecords = new Vector<Hospitalization_information>();
		
		patinetInit();
		medicineInit();
		serviceFacilitiesInit();
		treatmentProjectInit();
		UnitsInit();
		OthersInit();
		SpecialInit();
		
		HospitalizationInformationInit();
	}
	static public void patinetInit(){
		Data_base db = new Data_base(new Patinet(),"dbs\\Patinets.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Name",70));
		p.add(new Pair<String,Integer>("Citizenship Number",150));
		p.add(new Pair<String,Integer>("Sex",50));
		p.add(new Pair<String,Integer>("Unit Code",70));
		p.add(new Pair<String,Integer>("Birth",100));
		p.add(new Pair<String,Integer>("Approve",70));
		
		e.add(new Pair<Boolean,String>(true,"^BR[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(true,RegexMatch.PERSON_NAME));
		e.add(new Pair<Boolean,String>(true,"^[0-9]{17}[0-9x]$"));
		e.add(new Pair<Boolean,String>(false,"^Male$|^Female$"));
		e.add(new Pair<Boolean,String>(false,"^DW[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(false,"^[0-9]{4}-[0-9]{2}-[0-9]{2}$"));
		e.add(new Pair<Boolean,String>(false,RegexMatch.BOOLEAN));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("Patinet");
		Main_ui.regexs.add(e);
	}
	static public void medicineInit(){
		Data_base db = new Data_base(new Medicine(),"dbs\\Medicines.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Name",100));
		p.add(new Pair<String,Integer>("Price",50));
		p.add(new Pair<String,Integer>("Price Limit",80));
		p.add(new Pair<String,Integer>("Hospital Grade",100));
		p.add(new Pair<String,Integer>("Medicine Grade",120));
		p.add(new Pair<String,Integer>("Special Item",70));
		
		e.add(new Pair<Boolean,String>(true,"^YP[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(true,RegexMatch.WORDS));
		e.add(new Pair<Boolean,String>(true,RegexMatch.FLOAT));
		e.add(new Pair<Boolean,String>(false,RegexMatch.FLOAT));
		e.add(new Pair<Boolean,String>(false,"^[1-4]$"));
		e.add(new Pair<Boolean,String>(false,"^[1-3]$"));
		e.add(new Pair<Boolean,String>(false,RegexMatch.BOOLEAN));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("Medicine");
		Main_ui.regexs.add(e);
	}
	static public void serviceFacilitiesInit(){
		Data_base db = new Data_base(new Service_facilities(),"dbs\\ServiceFacilities.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Name",50));
		p.add(new Pair<String,Integer>("Price",50));
		
		e.add(new Pair<Boolean,String>(true,"^FW[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(true,RegexMatch.WORDS));
		e.add(new Pair<Boolean,String>(true,RegexMatch.FLOAT));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("Service Facilitiy");
		Main_ui.regexs.add(e);
	}
	static public void treatmentProjectInit(){
		Data_base db = new Data_base(new Treatment_project(),"dbs\\TreatmentProjects.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Name",100));
		p.add(new Pair<String,Integer>("Price",50));
		p.add(new Pair<String,Integer>("Price Limit",80));
		p.add(new Pair<String,Integer>("Hospital Grade",100));
		p.add(new Pair<String,Integer>("Special Item",70));
		
		e.add(new Pair<Boolean,String>(true,"^YP[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(true,RegexMatch.WORDS));
		e.add(new Pair<Boolean,String>(true,RegexMatch.FLOAT));
		e.add(new Pair<Boolean,String>(false,RegexMatch.FLOAT));
		e.add(new Pair<Boolean,String>(false,"^[1-4]$"));
		e.add(new Pair<Boolean,String>(false,RegexMatch.BOOLEAN));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("Medicine");
		Main_ui.regexs.add(e);
	}
	static public void UnitsInit(){
		Data_base db = new Data_base(new Unit(),"dbs\\Units.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Name",50));
		p.add(new Pair<String,Integer>("Point Hospital",120));
		
		e.add(new Pair<Boolean,String>(true,"^DW[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(true,RegexMatch.WORDS));
		e.add(new Pair<Boolean,String>(false,"^YY[0-9]{"+BITS+"}$"));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("Unit");
		Main_ui.regexs.add(e);
	}
	static public void OthersInit(){
		Data_base db = new Data_base(new Treatment_project(),"dbs\\Others.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Name",100));
		p.add(new Pair<String,Integer>("Price",50));
		
		e.add(new Pair<Boolean,String>(true,"^OT[0-9]{"+BITS+"}$"));
		e.add(new Pair<Boolean,String>(true,RegexMatch.WORDS));
		e.add(new Pair<Boolean,String>(true,RegexMatch.FLOAT));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("Other");
		Main_ui.regexs.add(e);
	}
	static public void SpecialInit(){
		Data_base db = new Data_base(new Special_node(),"dbs\\Special.txt");
		Vector<Pair<String,Integer>> p = new Vector<Pair<String,Integer>>();
		Vector<Pair<Boolean,String>> e = new Vector<Pair<Boolean,String>>();
		p.add(new Pair<String,Integer>("ID Code",70));
		p.add(new Pair<String,Integer>("Patinet",70));
		p.add(new Pair<String,Integer>("Items",70));
		for(int i=0;i<Special_node.ITEM_NUM-3;i++)
			p.add(new Pair<String,Integer>("",70));
		
		e.add(new Pair<Boolean,String>(true,"^TS[0-9]{"+BITS+"}$"));
		for(int i=0;i<Special_node.ITEM_NUM-1;i++)
			e.add(new Pair<Boolean,String>(false,"^.*$"));
		
		Main_ui.dataBases.add(db);
		Main_ui.titleFonts.add(p);
		Main_ui.smallTitles.add("NULL");
		Main_ui.regexs.add(e);
	}
	static public void HospitalizationInformationInit(){
		File path = new File("rcs");
		File flist[] = path.listFiles();
		if (flist == null || flist.length == 0)return;
		for(File f : flist){
			Main_ui.allOfRecords.add
				(new Hospitalization_information(f.getAbsolutePath()));
		}
	}
	static public void saveAll(){
		Main_ui.dataBases.elementAt(0).save("dbs\\Patinets.txt",false);
		Main_ui.dataBases.elementAt(1).save("dbs\\Medicines.txt",false);
		Main_ui.dataBases.elementAt(2).save("dbs\\ServiceFacilities.txt",false);
		Main_ui.dataBases.elementAt(3).save("dbs\\TreatmentProjects.txt",false);
		Main_ui.dataBases.elementAt(4).save("dbs\\Units.txt",false);
		Main_ui.dataBases.elementAt(5).save("dbs\\Others.txt",false);
		File path = new File("rcs");
		File flist[] = path.listFiles();
		if (flist == null || flist.length == 0)return;
		for(File f : flist)f.delete();
		for(Hospitalization_information h :Main_ui.allOfRecords){
			h.save("rcs\\"+h.getCode()+".txt");
		}
	}
}
