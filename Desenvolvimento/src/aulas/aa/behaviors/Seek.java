package aulas.aa.behaviors;

import aulas.aa.Boid;
import aulas.physics.Body;
import processing.core.PVector;

public class Seek extends Behavior {

	public Seek(float weight) {
		super(weight);

	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.getEye().getTarget();
		System.out.println(bodyTarget.getPos());
		return PVector.sub(bodyTarget.getPos(), me.getPos());
	}

}
