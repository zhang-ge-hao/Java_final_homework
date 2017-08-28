package __tool;

import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

@SuppressWarnings("rawtypes")
public class Pair<type1,type2> implements Comparable<Pair>{
	public type1 first;
	public type2 second;
	public Pair(type1 a,type2 b){
		first = a;
		second = b;
	}
	public static<T1,T2>Vector<T1>getFirstVector(Vector<Pair<T1,T2>> v){
		Vector<T1> res = new Vector<T1>();
		for(Pair<T1,T2> p : v)res.add(p.first);
		return res;
	}
	public static<T1,T2>Set<T1>getFirstVector(Set<Pair<T1,T2>> v){
		Set<T1> res = new TreeSet<T1>();
		for(Pair<T1,T2> p : v)res.add(p.first);
		return res;
	}
	public static<T1,T2>Vector<T2>getSecondVector(Vector<Pair<T1,T2>> v){
		Vector<T2> res = new Vector<T2>();
		for(Pair<T1,T2> p : v)res.add(p.second);
		return res;
	}
	public static<T1,T2>Set<T2>getSecondVector(Set<Pair<T1,T2>> v){
		Set<T2> res = new TreeSet<T2>();
		for(Pair<T1,T2> p : v)res.add(p.second);
		return res;
	}
	@SuppressWarnings({ "unchecked" })
	public int compareTo(Pair that) {
		Comparable<type1> f1=(Comparable<type1>) this.first;
		type1 f2=(type1)that.first;
		Comparable<type2> s1=(Comparable<type2>) this.second;
		type2 s2=(type2)that.second;
		return f1.compareTo(f2)!=0?f1.compareTo(f2):s1.compareTo(s2);
	}
}
