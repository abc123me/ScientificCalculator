package net.net16.jeremiahlowe.scicalc.functions.parser;

import net.net16.jeremiahlowe.scicalc.Enums.CPEReason;
import net.net16.jeremiahlowe.scicalc.utility.Utility;

public interface Expressions{
	public static double evaluate(String text, Variable[] vars){
		//3x+4+x where x=17
		//First format to: 3(17)+4+17
		
		for(Variable v : vars){
			text.replaceAll(v.name, v.substitute());
		}
		return 0;
	}
	public class Variable{
		private String name = "x";
		public double value = 0;
		public String substitute(){
			return "(" + value + ")";
		}
		public String toString(){
			return name + "=" + value;
		}
		public void setName(String to) throws CannotParseException{
			if(to.length() <= 0 || !Utility.isAlphabetical(to.charAt(0)))
				throw new CannotParseException("Variable name must start with a letter (a-z or A-Z)", CPEReason.InvalidVariableName); 
		}
		public String getName(){return name;}
		public boolean matches(Variable v){
			if(v.value == value && v.name.matches(name)) return true;
			return false;
		}
		public boolean nameMatches(Variable v){
			if(v.name.matches(name)) return true;
			return false;
		}
	}
}

