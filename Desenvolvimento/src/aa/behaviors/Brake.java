package aa.behaviors;

import aa.Boid;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Brake extends Behavior {
	private int strenght;

	public Brake(float weight, int strenght) {
		super(weight);
		setStrenght(strenght);
	}

	public int getStrenght() {
		return strenght;
	}

	public void setStrenght(int strenght) {
		this.strenght = strenght;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		me.applyForce(new PVector(-strenght, -strenght));
		return new PVector();
	}

}
