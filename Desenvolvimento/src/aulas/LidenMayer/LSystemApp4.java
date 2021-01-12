package aulas.LidenMayer;

import aulas.graph.SubPlot;
import processing.core.PApplet;
import setup.InterfaceProcessingApp;

public class LSystemApp4 implements InterfaceProcessingApp {

	private double[] window = {0,2,0,3};
	private float[] viewport = {0.1f,0.1f,0.8f,0.8f};
	private LSystem ls;
	private Turtle turtle;

	private double[] starting = {0.9,0.4};


	private SubPlot plot;

	@Override
	public void setup(PApplet parent) {
		plot = new SubPlot(window,viewport,parent.width,parent.height);
		//Primeira �rvore
		Regra[] rule = new Regra[1];
		rule[0] = new Regra('F',"FF-[-F+F+F]+[+F-F-F]");
		ls = new LSystem("F",rule);
		turtle = new Turtle(parent, plot, 0.2f, PApplet.radians(23), false);


	}

	@Override
	public void draw(PApplet parent, float dt) {

		float[] bb = plot.getBoundingBox();
		parent.rect(bb[0], bb[1], bb[2], bb[3]);

		parent.pushMatrix();
		
		turtle.setPose(starting, PApplet.radians(90));
		turtle.render(ls);
		parent.popMatrix();
		
	}

	@Override
	public void keyPressed(PApplet parent) {
	}

	@Override
	public void mousePressed(PApplet parent) {

		ls.setRuleset(ls.Regra50(new Regra('F',"FF-[-F+F+F]+[+F-F-F]"), new Regra('F',"FF+F[F]-F")));
		ls.nextGeneration1();
		turtle.scaling(0.5f);
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
