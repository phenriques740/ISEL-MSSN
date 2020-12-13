package aa;

import java.util.ArrayList;

import aa.behaviors.Accelerate;
import aa.behaviors.Behavior;
import aa.behaviors.Brake;
import aa.behaviors.Patrol;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;

public class D implements IProcessingApp {
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	private Boid boid;
	private ArrayList<Body> patrolPoints;
	private float accWeight, brakeWeight;
	private Behavior acc, brake;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		boid = new Boid(new PVector(), 0.1f, 0.5f, p.color(255, 0, 0), p, plt);
		patrolPoints = new ArrayList<Body>();
		addPatrolPoints(p.color(0, 0, 255));
		brake = new Brake(brakeWeight, 0.5f);
		acc = new Accelerate(accWeight, 0.5f);

		boid.addBehavior(new Patrol(1f, patrolPoints));
		boid.addBehavior(acc);
		boid.addBehavior(brake);
		boid.setEye(new Eye(boid, patrolPoints));
	}

	private void addPatrolPoints(int color) {
		Body p1, p2, p3, p4;
		p1 = new Body(new PVector(-5, 0), new PVector(), 0, 0.5f, color);
		p2 = new Body(new PVector(-5, 5), new PVector(), 0, 0.5f, color);
		p3 = new Body(new PVector(5, 0), new PVector(), 0, 0.5f, color);
		p4 = new Body(new PVector(5, 5), new PVector(), 0, 0.5f, color);

		patrolPoints.add(p1);
		patrolPoints.add(p2);
		patrolPoints.add(p3);
		patrolPoints.add(p4);
	}

	@Override
	public void draw(PApplet p, float dt) {
		float[] boundingBox = plt.getBoundingBox(); // da as coordenadas do viewport
		p.rect(boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);
		p.background(50);

		for (Body point : patrolPoints) {
			point.display(p, plt);
		}

		boid.applyBehaviors(dt);
		boid.display(p, plt);
	}

	@Override
	public void mousePressed(PApplet p) {
		double[] pp = plt.getWorldCoord(p.mouseX, p.mouseY);
		PVector pos = new PVector((float) pp[0], (float) pp[1]);

		if (p.mouseButton == PConstants.LEFT) {
			patrolPoints.add(new Body(pos, new PVector(), 0, 0.5f, p.color(0, 255, 0)));
		} else if (p.mouseButton == PConstants.RIGHT) {
			for (Body body : patrolPoints) {
				if (body.isInside(pos)) {
					System.out.println("removing bodys index " + patrolPoints.indexOf(body));
					patrolPoints.remove(body);
					break;
				}
			}
		}
	}

	public void keyPressed(PApplet p) {
		DNA dna = boid.getDna();
		switch (p.keyCode) {
		case PConstants.UP:
			acc.setWeight(++accWeight);
			brake.setWeight(--brakeWeight);
			System.out.println("Max Speed UP " + dna.maxSpeed);
			break;
		case PConstants.DOWN:
			acc.setWeight(--accWeight);
			brake.setWeight(++brakeWeight);
			System.out.println("Max Speed DOWN " + dna.maxSpeed);
			break;
		default:
			System.out.println("Tecla invalida");
		}
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
