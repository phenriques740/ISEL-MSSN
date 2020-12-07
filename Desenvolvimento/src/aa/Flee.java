package aa;

import physics.Body;
import processing.core.PVector;
//igual ao Seek mas com uma velocidade sim�trica
public class Flee extends Behavior {

	public Flee(float weight) {
		super(weight);

	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.eye.target;
		PVector vd = PVector.sub(bodyTarget.getPos(), me.getPos());
		return vd.mult(-1);
	}

}
