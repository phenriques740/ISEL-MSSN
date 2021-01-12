package aulas.LidenMayer;

import aulas.graph.SubPlot;
import aulas.setup.InterfaceProcessingApp;
import processing.core.PApplet;

public class LSystemApp1 implements InterfaceProcessingApp 
{
	private LSystem lsys;
	private Turtle turtle;
	private double[] startingPos = {0, 0};

	private double[] window = {-1, 1, 0, 15};
	private float[] viewport = {0.1f, 0.1f, 0.8f, 0.8f};
	private SubPlot plot;
	private boolean firstTree;

	@Override
	public void setup(PApplet p) 
	{
			//GRUPO 1 ALINEA 1.1.1 EXERCICIO 1
			plot = new SubPlot(window, viewport, p.width, p.height);
			Rule[] ruleset = new Rule[1];
			//ruleset[0] = new Rule('F',"F[+F]F[-F][F]");
			ruleset[0] = new Rule('F',"FF-[-F+F+F]+[+F-F-F]");
			lsys = new LSystem("F", ruleset);
			//turtle = new Turtle(p,plot,5f,PApplet.radians(20),false);
			turtle = new Turtle(p,plot,4f,PApplet.radians(22.5f),false);
		
	
			//GRUPO 1 ALINEA 1.1.1 EXERCICIO 2
	//		plot = new SubPlot(window,viewport,p.width,p.height);
	//		Rule[] ruleset = new Rule[2];
	//		ruleset[0] = new Rule('F',"G[+F]-F");
	//		ruleset[1] = new Rule('G',"GG");
	//		lsys = new LSystem("F",ruleset);
	//		turtle = new Turtle(p,plot,7f,PApplet.radians(20f),false);

	}

	@Override
	public void draw(PApplet p, float dt) 
	{
		float[] r = plot.getBoundingBox();
		p.rect(r[0], r[1], r[2], r[3]);
		turtle.setPose(startingPos, (float)Math.PI/2f);
		turtle.render(lsys);	
	}

	@Override
	public void keyPressed(PApplet p) 
	{
	}

	@Override
	public void mousePressed(PApplet p) 
	{
		System.out.println(lsys.getSequence());
		lsys.nextGeneration();
		turtle.scaling(0.5f);
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}
}
