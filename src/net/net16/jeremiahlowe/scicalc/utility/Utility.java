package net.net16.jeremiahlowe.scicalc.utility;

import net.net16.jeremiahlowe.scicalc.utility.collections.Vector2Precise;

public class Utility {
	public static final String clampLength(String in, int len){return clampLength(in, len, false);}
	public static final String clampLength(String in, int len, boolean dots){
		if(in.length() > len) return in.substring(0, len) + (dots ? "..." : "");
		else return in;
	}
	public static final String removeWhitespace(String in){
		String out = "";
		for(int i = 0; i < in.length(); i++){
			char c = in.charAt(i);
			if(c == ' ' || c == '\n' || c == '\r' || c == '\t') continue;
			else out += c;
		}
		return out;
	}
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
	public static final boolean isNumeric(char c, boolean includeDecimal){
		return (c >= '0' && c <= '9' || (includeDecimal && c == '.'));
	}
	public static final boolean isNumeric(String s, boolean includeDecimal){
		char[] sc = s.toCharArray();
		for(char c : sc) if(!isNumeric(c, includeDecimal)) return false;
		return true;
	}
	public static final boolean isNumeric(char c){return isNumeric(c, false);}
	public static final boolean isNumeric(String s){return isNumeric(s, false);}
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
	public static Vector2Precise[] combineXYArrays(double[] x, double[] y){
		if(x.length != y.length)
			throw new RuntimeException("X and Y Arrays must be same in size");
		int len = x.length;
		Vector2Precise[] out = new Vector2Precise[len];
		for(int i = 0; i < len; i++)
			out[i] = new Vector2Precise(x[i], y[i]);
		return out;
	}
}
