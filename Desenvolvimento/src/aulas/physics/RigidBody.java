package aulas.physics;

import processing.core.PApplet;
import processing.core.PVector;

public class RigidBody {
	private PVector pos;
	private PVector vel;
	private PVector acc;
	private float mass;

	public enum ControlType {
		POSITION, VELOCITY, FORCE
	}

	public RigidBody(float mass) {
		pos = new PVector();
		vel = new PVector();
		acc = new PVector();

		this.mass = mass;
	}

	public void move(float dt, ControlType ct) {
		switch (ct) {
		case POSITION:
			break;
		case VELOCITY:
			pos.add(PVector.mult(vel, dt));
			break;
		case FORCE:
			pos.add(PVector.mult(vel, dt));
			vel.add(PVector.mult(acc, dt));
			break;
		default:
			break;
		}
	}

	public void display(PApplet p, ControlType ct) {
		p.circle(pos.x, pos.y, 30);
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getPos() {
		return pos;
	}

	public PVector getVel() {
		return vel;
	}

	public void setVel(PVector vector) {
		this.vel = vector;
	}

	public void applyForce(PVector force) {
		System.out.println("CCCCC");

		this.acc = PVector.div(force, mass);
	}

}
