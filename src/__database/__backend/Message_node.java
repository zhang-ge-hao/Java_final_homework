package __database.__backend;
import java.util.Iterator;
import java.util.Vector;
public class Message_node implements Iterable<Object>,Comparable<Object>{
	private Vector<Object> messageLine;
	public Message_node(){
		messageLine = new Vector<Object>();
	}
	public Message_node(Message_node c){
		messageLine = new Vector<Object>(c.getVector());
	}
	public void add(Object obj){
		messageLine.add(obj);
	}
	public int size(){
		return messageLine.size();
	}
	public Iterator<Object> iterator(){
		return messageLine.iterator();
	}
	public Object elementAt(int i){
		return messageLine.elementAt(i);
	}
	public String toString(){
		StringBuffer res = new StringBuffer();
		for(int i=0;i<size();i++){
			res.append(this.elementAt(i)!=null
					?this.elementAt(i).toString():File_IO.NULL);
			if(i+1 != size())res.append(" ");
		}
		return res.toString();
	}
	public boolean equals(Object obj){
		if(!(obj instanceof Message_node))
			return false;
		Message_node that = (Message_node)obj;
		if(this.elementAt(0)!=null && that.elementAt(0)!=null){
			return this.elementAt(0).equals(that.elementAt(0));
		}
		if(this.size() != that.size())return false;
		for(int i=0;i<size();i++){
			if(this.elementAt(i)!=null && that.elementAt(i)!=null){
				if(!this.elementAt(i).equals(that.elementAt(i)))
					return false;
			}
		}
		return true;
	}
	public int compareTo(Object obj){
		if(!(obj instanceof Message_node))
	        throw new RuntimeException("Not same type.");
		Message_node that = (Message_node)obj;
		String s1=(String)this.elementAt(0);
		String s2=(String)that.elementAt(0);
		if(s1==null || s2==null)
			throw new RuntimeException("Id code error.");
		return s1.compareTo(s2);
	}
	public Vector<Object> getVector(){
		return messageLine;
	}
}