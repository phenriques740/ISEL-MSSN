package tp;

import processing.core.PApplet;
import processing.core.PVector;

public class Jogador extends RigidBody {

	public Jogador(float mass, float hitBoxRadius, PVector pos) {
		super(mass, hitBoxRadius, pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display(PApplet p) {
		PVector pos = getPos();
		p.fill(0);
		p.circle(pos.x, pos.y, getHitBoxRadius());
	}

}
