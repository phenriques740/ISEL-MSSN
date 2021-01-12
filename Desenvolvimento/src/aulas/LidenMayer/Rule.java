package aulas.LidenMayer;

public class Rule 
{
	private char symbol;
	private String string;
	private String axi;

	public Rule(char symbol, String string) 
	{
		this.symbol = symbol;
		this.string = string; 
	}
	
	public Rule(String axioma, String string) {
		this.axi = axioma;
		this.string = string;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public String getString() {
		return string;
	}
	
	public String getAxi() {
		return axi;
	}
}



