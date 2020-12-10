package aa.behaviors;

import aa.Boid;
import processing.core.PVector;

public class Wander extends Behavior {

	public Wander(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector center = me.getPos().copy();
		center.add(me.getVel().copy().mult(me.getDna().deltaTWander));
		PVector target = new PVector(me.getDna().radiusWander * (float) Math.cos(me.getPhiWander()),
				me.getDna().radiusWander * (float) Math.sin(me.getPhiWander()));
		target.add(center);
		me.setPhiWander((float) (me.getPhiWander() + 2 * (Math.random() - 0.5) * me.getDna().deltaPhiWander));
		return PVector.sub(target, me.getPos());
	}

}
