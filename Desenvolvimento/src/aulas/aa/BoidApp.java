package aulas.aa;

import java.util.ArrayList;
import java.util.List;

import aulas.aa.behaviors.Patrol;
import aulas.graph.SubPlot;
import aulas.physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.InterfaceProcessingApp;

public class BoidApp implements InterfaceProcessingApp {
	private Boid b;
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	private Body target;
	private List<Body> allTrackingBodies;
	private int index = 0;
	private ArrayList<Body> Waypoints;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		b = new Boid(new PVector(), 1, 0.5f, p.color(0), p, plt);
		Waypoints = new ArrayList<Body>();
		
		Body bodyWP1 = new Body(new PVector(5,5), new PVector(), 1f, 0.2f, p.color(255, 0, 0));
		Body bodyWP2 = new Body(new PVector(-8,5), new PVector(), 1f, 0.2f, p.color(255, 0, 0));
		Waypoints.add(bodyWP1);
		Waypoints.add(bodyWP2);
		
		b.addBehavior(new Patrol(1f, Waypoints));
		
		Eye eye = new Eye(b, Waypoints);
		b.setEye(eye);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		b.applySingleBehavior(index, dt);
		b.display(p, plt);
		
		for(Body body : Waypoints) {
			body.display(p, plt);
		}
	}

	@Override
	public void mousePressed(PApplet p) {
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

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
