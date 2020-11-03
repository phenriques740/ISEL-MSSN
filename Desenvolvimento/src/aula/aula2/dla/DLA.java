package aula.aula2.dla;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class DLA implements IProcessingApp {
	private List<Walker> walkers;
	private final int NUM_WALKERS = 200;

	private final int NUM_STEPS_PER_FRAME = 100;

	@Override
	public void setup(PApplet p) {
		walkers = new ArrayList<Walker>();

		Walker w = new Walker(p, new PVector(p.width / 2, p.height / 2));
		walkers.add(w);
		for (int i = 0; i < NUM_WALKERS; i++) {
			walkers.add(new Walker(p));
		}

	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(190);

		for (int i = 0; i < NUM_STEPS_PER_FRAME; i++) {
			for (Walker walker : walkers) {
				walker.run(p);
				walker.updateState(p, walkers);
			}
		}

		for (Walker walker : walkers) {
			walker.display(p);
		}

		if (Walker.numWanders < NUM_WALKERS) {
			for (int i = 0; i < NUM_WALKERS - Walker.numWanders; i++) {
				walkers.add(new Walker(p));
			}
		}
		System.out.println("Stopped = " + Walker.numStopped + ".\nWandering = " + Walker.numWanders);
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
