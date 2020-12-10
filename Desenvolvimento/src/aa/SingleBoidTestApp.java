package aa;

import aa.behaviors.Brake;
import aa.behaviors.Seek;
import aa.behaviors.Wander;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;

public class SingleBoidTestApp implements IProcessingApp {
	private Boid b;
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	private Body target;
	private int currBehaviour = 0;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		b = new Boid(new PVector(), 1, 0.5f, p.color(0), p, plt);
		target = new Body(new PVector(0, 0), new PVector(), 1f, 1.0f, p.color(255, 0, 0));
		b.addBehavior(new Seek(1f));
		b.addBehavior(new Brake(100f, 1));
		b.getEye().setTarget(target);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		b.applySingleBehavior(currBehaviour, dt);
		b.display(p, plt);
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		DNA dna = b.getDna();
//		if (p.key == ' ') {
//			brake = !brake;
//		}

		switch (p.keyCode) {
		case PConstants.UP:
			dna.maxSpeed += 1;
			System.out.println("Max Speed UP " + dna.maxSpeed);
			break;
		case PConstants.DOWN:
			dna.maxSpeed -= 1;
			dna.maxSpeed = PApplet.max(dna.maxSpeed, 0);
			System.out.println("Max Speed DOWN " + dna.maxSpeed);
			break;
		case PConstants.RIGHT:
			dna.maxForce += 1;
			System.out.println("Max Force RIGHT " + dna.maxForce);
			break;
		case PConstants.LEFT:
			dna.maxForce -= 1;
			dna.maxForce = PApplet.max(dna.maxForce, 0);
			System.out.println("Max Force LEFT " + dna.maxForce);
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
