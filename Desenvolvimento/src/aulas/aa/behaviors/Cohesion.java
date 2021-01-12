package aa.behaviors;

import aa.Boid;
import physics.Body;
import processing.core.PVector;

public class Cohesion extends Behavior {

	public Cohesion(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector target = me.getPos().copy();
		for (Body b : me.getEye().getFarSight()) {
			target.add(b.getPos());
		}
		target.div(me.getEye().getFarSight().size() + 1);
		return PVector.sub(target, me.getPos());
	}

}
