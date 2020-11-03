package aula.aula2.dla;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Walker {

	public enum State {
		STOPPED, WANDER
	}

	private PVector pos;
	private State state;

	private int color;
	public static int radius = 4;

	public static int numWanders = 0;
	public static int numStopped = 0;

	/**
	 * Construtor de Walker. Inicializa em movimento
	 * 
	 * @param p
	 */
	public Walker(PApplet p) {
		// pos = new PVector(p.random(p.width), p.random(p.height));

		pos = new PVector(p.width / 2, p.height / 2);
		PVector r = PVector.random2D();
		r.mult(p.width / 2);
		pos.add(r);

		setState(p, State.WANDER);
	}

	/**
	 * Construtor de Walker. Inicializa parado numa posicao
	 * 
	 * @param p
	 * @param pos
	 */
	public Walker(PApplet p, PVector pos) {
		this.pos = pos;
		setState(p, State.STOPPED);
	}

	public State getState() {
		return state;
	}

	public void setState(PApplet p, State state) {
		if (state == State.STOPPED) {
			color = p.color(0);
			numStopped++;
			numWanders--;
		} else {
			color = p.color(255);
			numWanders++;
		}
		this.state = state;
	}

	private void wander(PApplet p) {
		PVector step = PVector.random2D();
		pos.add(step);
		pos.lerp(new PVector(p.width / 2, p.height / 2), 0.00002f);
		pos.x = PApplet.constrain(pos.x, 0, p.width);
		pos.y = PApplet.constrain(pos.y, 0, p.height);
	}

	public void display(PApplet p) {
		p.fill(color);
		p.circle(pos.x, pos.y, 2 * radius);
	}

	public void run(PApplet p) {
		switch (state) {
		case WANDER:
			wander(p);
			break;
		case STOPPED:
			break;
		default:
			break;
		}
	}

	public void updateState(PApplet p, List<Walker> walkers) {
		if (state == State.STOPPED) {
			return;
		}

		for (Walker walker : walkers) {
			if (walker.getState() == State.STOPPED) {
				float dist = PVector.dist(pos, walker.pos);
				if (dist <= 2 * radius) {
					setState(p, State.STOPPED);
					break;
				}
			}
		}
	}
}
