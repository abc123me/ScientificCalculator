package net.net16.jeremiahlowe.scicalc.functions.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.Enums.CPEReason;
import net.net16.jeremiahlowe.shared.TextUtility;

public interface Expressions{
	public class Variable{
		private String name = "x";
		public double value = 0;
		public Variable(String name, double value) {
			this.name = name;
			this.value = value;
		}
		public Variable(String name) {this(name, 0);}
		public String substitute(){return "(" + value + ")";}
		public String toString(){return name + "=" + value;}
		public void setName(String to) throws CannotParseException{
			if(to.length() <= 0 || !TextUtility.isAlphabetical(to.charAt(0)))
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
	public class NamedVariableSorter implements Comparator<Variable>{
		@Override
		public int compare(Variable a, Variable b) {
			if(a.name.length() > b.name.length()) return -1;
			else if(a.name.length() < b.name.length()) return 1;
			else return 0;
		}
		public static Variable[] sortVariablesByName(Variable[] vars){
			List<Variable> sortedVars = new ArrayList<Variable>();
			for(Variable v : vars) sortedVars.add(v);
			sortedVars.sort(new NamedVariableSorter());
			sortedVars.toArray(vars);
			return vars;
		}
	}
}

