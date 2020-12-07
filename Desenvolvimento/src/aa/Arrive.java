package aa;

import processing.core.PVector;

public class Arrive extends Behavior {

	public Arrive(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = PVector.sub(me.eye.target.getPos(), me.getPos());
		float distance = vd.mag();
		float R = me.dna.radiusArrive;
		if(distance < R) {
			vd.mult(distance/R);
		}
		return vd;
	}

}




















