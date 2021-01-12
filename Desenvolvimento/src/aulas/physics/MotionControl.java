package aulas.physics;

import aulas.physics.RigidBody.ControlType;
import processing.core.PApplet;
import processing.core.PVector;

public class MotionControl {
	private RigidBody rb;
	private ControlType ct;
	private PVector vector;

	public MotionControl(ControlType ct, RigidBody rb) {
		this.rb = rb;
		this.ct = ct;
		vector = new PVector();
	}

	public void setVector(PVector vector) {
		this.vector = vector;
		switch (ct) {
		case POSITION:
			rb.setPos(vector);
			break;
		case VELOCITY:
			rb.setVel(vector);
			break;
		case FORCE:
			rb.applyForce(vector);
		default:
			break;
		}
	}

	public void display(PApplet p) {
		p.strokeWeight(3);
		p.line(-p.width / 2, 0, p.width / 2, 0);
		p.line(0, -p.height / 2, 0, p.height / 2);
		p.textSize(24);
		p.fill(0);
		p.text("Control by " + ct.toString(), -140, -270);

		p.line(0, 0, vector.x, vector.y);
	}

}
