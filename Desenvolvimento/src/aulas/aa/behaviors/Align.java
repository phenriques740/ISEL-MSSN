package aulas.aa.behaviors;

import aulas.aa.Boid;
import aulas.physics.Body;
import processing.core.PVector;

public class Align extends Behavior {

	public Align(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		PVector vd = me.getVel().copy();
		for (Body b : me.getEye().getFarSight()) {
			vd.add(b.getVel());	
		}
		return vd.div(me.getEye().getFarSight().size()+1);//+1 porque o meu pr�prio boid n�o esta incluido nessas lista!												
	}

}
