package aa;

import aa.behaviors.Wander;
import graph.SubPlot;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;

public class SingleBoidTestApp implements IProcessingApp {
	private Boid b;
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;

	private int currBehaviour = 0;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		b = new Boid(new PVector(), 1, 0.5f, p.color(0), p, plt);
		b.addBehavior(new Wander(1f));
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
