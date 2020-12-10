package aa.behaviors;

import aa.Boid;
import physics.Body;
import processing.core.PVector;

public class Seek extends Behavior {

	public Seek(float weight) {
		super(weight);

	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.getEye().getTarget();
		return PVector.sub(bodyTarget.getPos(), me.getPos());
	}

}
