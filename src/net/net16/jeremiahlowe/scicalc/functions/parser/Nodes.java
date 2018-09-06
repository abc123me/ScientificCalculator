package net.net16.jeremiahlowe.scicalc.functions.parser;

public interface Nodes{
	public abstract class Node {
		public abstract float getResult();
	}
	//Value node - Consists of ONLY a value e.g 114
	public class ValueNode extends Node{
		public float value = 0;
		
		public ValueNode(float value) {
			this.value = value;
		}
		public float getResult() {
			return value;
		}
	}
	//Coefficient node - Consists of a node and a coefficient e.g. 4(77) or 5f(45)
	public class CoefficientNode extends Node{
		public float coefficient = 1;
		public Node inner;
		public CoefficientNode(Node inner, float coefficient) {
			this.inner = inner;
			this.coefficient = coefficient;
		}
		@Override
		public float getResult() {
			return coefficient * inner.getResult();
		}
	}
}