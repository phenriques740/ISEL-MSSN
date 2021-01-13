package aulas.aa.behaviors;

import aulas.aa.Boid;
import processing.core.PVector;

public class Brake extends Behavior {
	private float strenght;

	public Brake(float weight, float strenght) {
		super(weight);
		setStrenght(strenght);
	}

	public float getStrenght() {
		return strenght;
	}

	public void setStrenght(float strenght) {
		this.strenght = strenght;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = new PVector();
		return PVector.sub(vd, me.getVel());
	}

}
