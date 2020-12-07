package aa;

import physics.Body;
import processing.core.PVector;

public class Separate extends Behavior {

	public Separate(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = new PVector();
		for(Body b : me.eye.getNearSight()) {
			PVector r = PVector.sub(me.getPos(), b.getPos());//ao contrario do habitual pois quero fugir; tambem podia multiplicar por -1 se fizesse como nos outrs comportamentos
			float d = r.mag();
			r.div(d*d);
			vd.add(r);
		}
		return vd;
	}

}













