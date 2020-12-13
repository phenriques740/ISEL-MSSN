package aa.behaviors;

import aa.Boid;
import processing.core.PVector;

public class Accelerate extends Behavior {
	private float strenght;

	public Accelerate(float weight, float strenght) {
		super(weight);
		setStrenght(strenght);

	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector dir = me.getVel().normalize();
		return me.getVel().add(dir.mult(strenght));
	}

	public float getStrenght() {
		return strenght;
	}

	public void setStrenght(float strenght) {
		this.strenght = strenght;
	}

}
