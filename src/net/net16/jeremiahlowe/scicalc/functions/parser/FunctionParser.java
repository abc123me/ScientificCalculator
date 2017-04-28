package net.net16.jeremiahlowe.scicalc.functions.parser;

import org.nfunk.jep.JEP;

import net.net16.jeremiahlowe.scicalc.Enums.CPEReason;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.Utility;

public class FunctionParser {
	public JEP jep;
	public FunctionParser(){
		jep = new JEP();
		jep.addStandardConstants();
		jep.addStandardFunctions();
		jep.setImplicitMul(true);
	}
	public UnaryFunction parseUnaryFunction(String text) throws CannotParseException{
		//Start splitting
		String[] sides = text.split("=");
		if(sides.length != 2) throw new CannotParseException("Multiple \"=\" signs", CPEReason.NotAFunction);
		//Parse left side name
		String leftSide = sides[0];
		int openP = Utility.count(leftSide, '('), closeP = Utility.count(leftSide, ')');
		if(openP != 1 || closeP != 1) throw new CannotParseException("Function name isn't properly formatted", CPEReason.InvalidName);
		int startInnerLeft = leftSide.indexOf('(') + 1, stopInnerLeft = leftSide.indexOf(')');
		String innerLeft = leftSide.substring(startInnerLeft, stopInnerLeft);
		//Separate the variables
		String[] vars = innerLeft.split(",");
		for(int i = 0; i < vars.length; i++){
			vars[i] = vars[i].trim();
			char front = vars[i].charAt(0);
			if(!Utility.isAlphabetical(front)) throw new CannotParseException("Variable name must start with a letter (a-z or A-Z)", CPEReason.InvalidVariableName);
		}
		if(vars.length != 1) throw new CannotParseException("Can only have one variable", CPEReason.Syntax);
		String var = vars[0];
		jep.addVariable(var, 0);
		//Do right side
		String rightSide = sides[1];
		UnaryFunction f = new UnaryFunction(){
			@Override
			public double f(double x) {
				jep.setVarValue(var, x);
				jep.parseExpression(rightSide);
				return jep.getValue();
			}
		};
		return f;
	}
}
