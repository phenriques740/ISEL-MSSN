package aulas.aa.behaviors;

import aulas.aa.Boid;
import aulas.physics.Body;
import processing.core.PVector;

public class Pursuit extends Behavior {

	public Pursuit(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.getEye().getTarget();
		PVector d = bodyTarget.getVel().mult(me.getDna().deltaTPursuit); // d e o vetor de deslocamento
		PVector target = PVector.add(bodyTarget.getPos(), d);
		return PVector.sub(target, me.getPos());

	}

}
