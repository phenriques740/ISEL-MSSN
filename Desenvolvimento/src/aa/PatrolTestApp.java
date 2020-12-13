package aa;

import java.util.ArrayList;

import aa.behaviors.Patrol;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class PatrolTestApp implements IProcessingApp {
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	private Boid boid;
	private ArrayList<Body> patrolPoints;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		boid = new Boid(new PVector(), 0.1f, 0.5f, p.color(255, 0, 0), p, plt);

		patrolPoints = new ArrayList<Body>();
		patrolPoints.add(new Body(new PVector(), new PVector(), 0, 5, p.color(0, 0, 255)));
		boid.addBehavior(new Patrol(1f, patrolPoints));
		boid.setEye(new Eye(boid, patrolPoints));
	}

	@Override
	public void draw(PApplet p, float dt) {
		float[] boundingBox = plt.getBoundingBox(); // da as coordenadas do viewport
		p.rect(boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);
		p.background(50);
		boid.applyBehaviors(dt);
		boid.display(p, plt);
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
