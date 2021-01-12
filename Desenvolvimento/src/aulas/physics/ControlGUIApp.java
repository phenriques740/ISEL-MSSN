package aulas.physics;

import aulas.physics.RigidBody.ControlType;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ControlGUIApp implements IProcessingApp {
	private RigidBody rb;
	private MotionControl mc;
	private float mass = 1f;
	private ControlType ct = ControlType.POSITION;

	public ControlGUIApp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup(PApplet p) {
		rb = new RigidBody(mass);
		mc = new MotionControl(ct, rb);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		p.translate(p.width / 2, p.height / 2);
		rb.move(dt, ct);
		rb.display(p, ct);
		mc.display(p);
	}

	@Override
	public void mousePressed(PApplet p) {
		float x = p.mouseX - p.width / 2;
		float y = p.mouseY - p.height / 2;
		mc.setVector(new PVector(x, y));
	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == 'p') {
			ct = ControlType.POSITION;
		}
		if (p.key == 'v') {
			ct = ControlType.VELOCITY;
		}
		if (p.key == 'f') {
			ct = ControlType.FORCE;
		}

		rb = new RigidBody(mass);
		mc = new MotionControl(ct, rb);
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
