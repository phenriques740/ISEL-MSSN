package LidenMayer;


public class LSystem 
{
	private String sequence;
	private Rule[] ruleset;  
	private Regra[] regraSet;  
	private int generation;

	public LSystem(String axiom, Rule[] ruleset) 
	{
		sequence = axiom;
		this.ruleset = ruleset;
		generation = 0;
	}


	public LSystem(String axiom, Regra[] rule) {
		// TODO Auto-generated constructor stub
		this.sequence = axiom;
		this.regraSet = rule;
		this.generation = 0;
	}


	public String getSequence()
	{
		return sequence;
	}

	public int getGeneration()
	{
		return generation;
	}

	public void nextGeneration()
	{
		generation++;

		String nextgen = "";
		for(int i=0;i<sequence.length();i++) {
			char c = sequence.charAt(i);
			String replace = "" + c;
			for(int j=0;j<ruleset.length;j++){
				if (c == ruleset[j].getSymbol()) {
					replace = ruleset[j].getString();
					break;
				}
			}
			nextgen += replace;
		}
		this.sequence = nextgen;
	}
	
	//para os LSystem 3 e 4. Da outro tipo de fractal mas ainda é baseado em LindenMayer e dai estarem agrupados
	public void nextGeneration1() {
		String nextGen = "";
		generation ++ ;
		for(int i = 0; i < sequence.length();i++) {
			char c = sequence.charAt(i); //percorrer o caminho da geracao seguinte
			String replace = "" + c;
			for(int j = 0; j < regraSet.length;j++) {
				if(c == regraSet[j].getSymbol()) {//verifica se encontrou a regra
					replace = regraSet[j].getString();
					break;
				}
			}
			nextGen += replace;

		}
		sequence = nextGen;
	}
	
	public Regra[] Regra50(Regra a, Regra b) {
		double i =  Math.random();
		
		if (i >= 0.5) 
			return new Regra[] {a} ;
		
		return new Regra[] {b};
	}
	
	public void setRuleset(Regra[] ruleset) {
		this.regraSet = ruleset;
	}
	
}




