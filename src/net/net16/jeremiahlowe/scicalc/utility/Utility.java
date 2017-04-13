package net.net16.jeremiahlowe.scicalc.utility;

public class Utility {
	public static final boolean isAlphanumeric(char c){
		return (isAlphabetical(c) || isNumeric(c));
	}
	public static final boolean isAlphanumeric(String s){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isAlphanumeric(c)) return false;
		return true;
	}
	public static final boolean isAlphabetical(char c){
		boolean upper = c >= 'A' && c <= 'Z';
		boolean lower = c >= 'a' && c <= 'z';
		return (upper || lower);
	}
	public static final boolean isAlphabetical(String s){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isAlphabetical(c)) return false;
		return true;
	}
	public static final boolean isNumeric(char c){
		return (c >= '0' && c <= '9');
	}
	public static final boolean isNumeric(String s){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isNumeric(c)) return false;
		return true;
	}
	public static final int count(String text, char regex){
		char[] chars = text.toCharArray();
		int ocurrences = 0;
		for(char c : chars) if(c == regex) ocurrences++;
		return ocurrences;
	}
	@SuppressWarnings("unchecked")
	public static <T> boolean anyOf(T any, T... of){
		for(T t : of){
			if(any == t) return true;
		}
		return false;
	}
}
