package aulas.LidenMayer;

import aulas.graph.SubPlot;
import processing.core.PApplet;
import setup.InterfaceProcessingApp;

public class LSystemApp5 implements InterfaceProcessingApp {

	private double[] window = {0,2,0,3};
	private float[] viewport = {0.1f,0.1f,0.8f,0.8f};
	private LSystem ls,ls2,ls3;
	private Turtle turtle,turtle2,turtle3,turtle4,turtle5,turtle6;
	private double[] starting = {0.5,0.4};
	private double[] starting2 = {0.7,0.4};
	private double[] starting3 = {0.9,0.4};
	private double[] starting4 = {1.1,0.4};
	private double[] starting5 = {1.3,0.4};
	private double[] starting6 = {1.5,0.4};

	private SubPlot plot;

	@Override
	public void setup(PApplet parent) {
		plot = new SubPlot(window,viewport,parent.width,parent.height);
		//Primeira �rvore
		Regra[] rule = new Regra[1];
		rule[0] = new Regra('F',"FF-[-F+F+F]+[+F-F-F]");
		ls = new LSystem("F",rule);
		turtle = new Turtle(parent, plot, 0.2f, PApplet.radians(23), false);
		//Segunda �rvore
		Regra[] rule2 = new Regra[2];
		rule2[0] = new Regra('F',"F[+F]G[-F][F]");
		rule2[1] = new Regra('G',"GG");
		ls2 = new LSystem("F",rule2);
		turtle2 = new Turtle(parent, plot, 0.2f, PApplet.radians(20), false);
		//Terceira �rvore
		Regra[] rule3 = new Regra[1];
		rule3[0] = new Regra('F',"F[+F]F[-F]F");
		ls3 = new LSystem("F",rule3);
		turtle3 = new Turtle(parent, plot, 0.2f, PApplet.radians(26), false);
		//Quarta �rvore
		turtle4 = new Turtle(parent, plot, 0.2f, PApplet.radians(26), false);
		//Quinta �rvore
		turtle5 = new Turtle(parent, plot, 0.2f, PApplet.radians(20), false);
		//Sexta �rvore
		turtle6 = new Turtle(parent, plot, 0.2f, PApplet.radians(23), false);

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

		parent.pushMatrix();
		
		turtle.setPose(starting, PApplet.radians(90));
		turtle.render(ls);
		parent.popMatrix();
		
		parent.pushMatrix();
		turtle2.setPose(starting2, PApplet.radians(90));
		turtle2.render(ls2);
		parent.popMatrix();
		
		parent.pushMatrix();
		turtle3.setPose(starting3, PApplet.radians(90));
		turtle3.render(ls3);
		parent.popMatrix();
		
		parent.pushMatrix();
		turtle4.setPose(starting4, PApplet.radians(90));
		turtle4.render(ls3);
		parent.popMatrix();
		
		parent.pushMatrix();
		turtle5.setPose(starting5, PApplet.radians(90));
		turtle5.render(ls2);
		parent.popMatrix();
		
		parent.pushMatrix();
		turtle6.setPose(starting6, PApplet.radians(90));
		turtle6.render(ls);
		parent.popMatrix();
		
	}

	@Override
	public void keyPressed(PApplet parent) {
	}

	@Override
	public void mousePressed(PApplet parent) {

		
		ls.nextGeneration1();
		ls2.nextGeneration1();
		ls3.nextGeneration1();
		turtle.scaling(0.6f);
		turtle2.scaling(0.6f);
		turtle3.scaling(0.6f);
		turtle4.scaling(0.6f);
		turtle5.scaling(0.6f);
		turtle6.scaling(0.6f);
	}



	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
