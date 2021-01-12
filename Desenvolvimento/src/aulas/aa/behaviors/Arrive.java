package aulas.aa.behaviors;

import aulas.aa.Boid;
import processing.core.PVector;

public class Arrive extends Behavior {

	public Arrive(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = PVector.sub(me.getEye().getTarget().getPos(), me.getPos());
		float distance = vd.mag();
		float R = me.getDna().radiusArrive;
		if(distance < R) {
			vd.mult(distance/R);
		}
		return vd;
	}
}




















