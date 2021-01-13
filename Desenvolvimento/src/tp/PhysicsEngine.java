package tp;

import tp.RigidBody;
import processing.core.PVector;

public class PhysicsEngine {
	public static void applyForce(PVector vector, RigidBody body, float dt) {
		PVector pos = body.getPos();
		PVector vel = body.getVel();
		PVector acc = body.getAcc();
		pos.add(PVector.mult(vel, dt));
		vel.add(PVector.mult(acc, dt));
	}
}
