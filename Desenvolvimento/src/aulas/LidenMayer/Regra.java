package aulas.LidenMayer;

public class Regra {
	
	private char symbol;
	private String string;
	
	public Regra(char simbolo, String s) {
		this.symbol = simbolo;
		this.string = s;
	}

	public char getSymbol() {
		return symbol;
	}

	public String getString() {
		return string;
	}
}
