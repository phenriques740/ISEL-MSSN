package aulas.aa.behaviors;

import aulas.aa.Boid;
import aulas.physics.Body;
import processing.core.PVector;

//igual a pursuit mas com velocidade sim�trica! *-1
public class Evade extends Behavior {

	public Evade(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.getEye().getTarget();
		PVector d = bodyTarget.getVel().mult(me.getDna().deltaTPursuit); // d e o vetor de deslocamento
		PVector target = PVector.add(bodyTarget.getPos(), d);
		PVector vd = PVector.sub(target, me.getPos());
		return vd.mult(-1);

	}

}
