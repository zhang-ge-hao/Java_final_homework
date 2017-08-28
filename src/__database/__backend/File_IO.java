package __database.__backend;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Vector;
public class File_IO {
	public static String split = "<>"; 
	public static String NULL = "null"; 
	public static Vector<Message_node> read(String ord,String fileName){
		Vector<Message_node> res = new Vector<Message_node>();
		String messageStr = new String();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while((messageStr=reader.readLine())!=null){
				//System.out.println(messageStr);
				res.add(getNode(messageStr,ord));
			}
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return res;
	}
	public static void write(Vector<Message_node> messageLine,String fileName,boolean mode){
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(fileName, mode)));
			for(Message_node message:messageLine){
				for(int i=0;i<message.size();i++){
					writer.write(message.elementAt(i)+(i+1==message.size()?"\r\n":split));
				}
			}
			writer.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static void write(Vector<Message_node> messageLine,BufferedWriter writer){
		String m;
		try{
			for(Message_node message:messageLine){
				for(int i=0;i<message.size();i++){
					if(message.elementAt(i)==null)m = NULL;
					else m = message.elementAt(i).toString();
					writer.write(m+(i+1==message.size()?"\r\n":split));
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static Message_node getNode(String messageStr,String ord){
		Message_node res = new Message_node();
		String[] messageLine = messageStr.split(split);
		for(int i=0;i<messageLine.length;i++){
			//System.out.println(messageLine[i]+"\n"+ord.charAt(i*2+1));
			if(messageLine[i].equals(NULL)){
				res.add(null);
			}else if(ord.charAt(i*2+1)=='d'){
				res.add(Integer.parseInt(messageLine[i]));
			}else if(ord.charAt(i*2+1)=='f'){
				res.add(Double.parseDouble(messageLine[i]));
			}else if(ord.charAt(i*2+1)=='s'){
				res.add(messageLine[i]);
			}else if(ord.charAt(i*2+1)=='b'){
				res.add(messageLine[i].equals("true")?true:false);
			}
		}
		return res;
	}
}