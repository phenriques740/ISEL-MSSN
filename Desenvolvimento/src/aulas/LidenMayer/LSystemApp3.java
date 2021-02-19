package aulas.LidenMayer;

import aulas.graph.SubPlot;
import processing.core.PApplet;
import setup.InterfaceProcessingApp;

public class LSystemApp3 implements InterfaceProcessingApp {

	private double[] window = {0,2,0,3};
	private float[] viewport = {0.1f,0.1f,0.8f,0.8f};
	private LSystem ls;
	private Turtle turtle;
	private double[] startingFlake = {0.4,1.0};
	private SubPlot plot;

	@Override
	public void setup(PApplet parent) {
		plot = new SubPlot(window,viewport,parent.width,parent.height);
		
		//Koch snowflake 
		Regra[] rule = new Regra[1];
		rule[0] = new Regra('F', "F+F--F+F");
		ls = new LSystem("+F--F--F",rule);
		
		//Koch curve - Dimens�o fractal desta curva � log(4)/log(3) = 0,477
		//Regra[] rule = new Regra[1];
		//rule[0] = new Regra('F',"F+F--F+F");
		//ls = new LSystem("F",rule);
		
		turtle = new Turtle(parent, plot, 0.1f, PApplet.radians(60), true);
	}



	public Regra Regra50() {
		double i =  Math.random();
		if (i >= 0.5) 
			return new Regra('F',"F+F--F+F") ;
		return new Regra('F',"F-F++F-F");
	}
	@Override
	public void draw(PApplet parent, float dt) {
		float[] bb = plot.getBoundingBox();
		parent.rect(bb[0], bb[1], bb[2], bb[3]);
		turtle.setPose(startingFlake, PApplet.radians(60));
		//turtle.setPose(startingCurve, PApplet.radians(0));
		turtle.render(ls);
	}

	@Override
	public void keyPressed(PApplet parent) {}

	@Override
	public void mousePressed(PApplet parent) {
		ls.nextGeneration1();
		turtle.scaling(0.5f);
	}



	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}
}
