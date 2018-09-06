package net.net16.jeremiahlowe.scicalc.functions.parser;

import net.net16.jeremiahlowe.scicalc.functions.parser.Nodes.Node;

public class Parser {
	public static final String ILLEGAL_CHARACTERS = "~`@$#;:\'\"<>\\_";
	public static final String cleanStr(String func) {
		String clean = "";
		outer: for(int i = 0; i < func.length(); i++) {
			char c = func.charAt(i);
			if(c < '!') continue;
			if(c > '~') continue;
			for(char d : ILLEGAL_CHARACTERS.toCharArray())
				if(c == d)
					continue outer;
			clean += c;
		}
		return clean;
	}
	// Basically takes 1 + 4(7) and turns it into nodes, each of them can be added together to get the result
	// So 1 becomes its own ValueNode while 4(7) becomes a group of nodes
	// Then 4(7) becomes a CoefficientNode
	public static final Node[] parseIntoNodes(String func) {
		System.out.println("Noderizing string: " + func);
		//Remove WS and other BS
		String f = cleanStr(func);
		System.out.println("Cleaned result: " + f);
		for(int i = 0; i < f.length(); i++) {
			char c = f.charAt(i);
			if(c >= '0' || c <= '9') {
				
			}
		}
	}
	
}
