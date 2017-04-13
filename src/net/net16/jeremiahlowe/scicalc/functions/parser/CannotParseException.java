package net.net16.jeremiahlowe.scicalc.functions.parser;

import net.net16.jeremiahlowe.scicalc.Enums.CPEReason;

public class CannotParseException extends Exception{
	private static final long serialVersionUID = 1L;
	private CPEReason reason = CPEReason.Unknown;
	private String message = null;
	public CannotParseException(String message, CPEReason reason){
		this.message = message;
		this.reason = reason;
	}
	public CPEReason getReason(){return reason;}
	public String getLocation(){return message;}
	public String toString(){
		String out = "Can't parse, " + reason.toString();
		if(message != null) out += ": " + message;
		return out;
	}
}