package __tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatch {
	static public String PERSON_NAME = "^[A-Z][a-z]* [A-Z][a-z]*$";
	static public String FLOAT = "^([1-9][0-9]*.?|0.)?[0-9]+$";
	static public String WORDS = "^([A-Z][a-z]* )*([A-Z][a-z]*)$";
	static public String BOOLEAN = "^true$|^false$";
	static public boolean absoluteMatch(String T,String P){
		Pattern p = Pattern.compile(P);
		Matcher m = p.matcher(T);
		return m.find()?m.group(0).equals(T):false;
	}
}
