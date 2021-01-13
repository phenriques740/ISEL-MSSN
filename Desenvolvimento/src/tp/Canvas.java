package tp;

import tp.RigidBody;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;

public class Canvas implements IProcessingApp {
	private RigidBody rb;
	private PhysicsEngine mc;

	@Override
	public void setup(PApplet p) {
		rb = new Jogador(1f, 30, new PVector(p.width / 2, p.height / 2));
		mc = new PhysicsEngine();

	}

	@Override
	public void draw(PApplet p, float dt) {
		drawBackground(p);
		rb.display(p);
	}

	/**
	 * Desenha as coisas estáticas que não se mexem.
	 */
	private void drawBackground(PApplet p) {
		p.background(125);
		p.line(0, p.height - 30, p.width, p.height - 30);
	}

	@Override
	public void mousePressed(PApplet p) {
		rb.setPos(new PVector(p.mouseX, p.mouseY));
	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.keyCode == PConstants.LEFT) {
			rb.applyForce(new PVector(-100, 0));
			System.out.println("left");
		}
		if (p.keyCode == PConstants.RIGHT) {
			rb.applyForce(new PVector(100, 0));
			System.out.println("right");
		}
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
