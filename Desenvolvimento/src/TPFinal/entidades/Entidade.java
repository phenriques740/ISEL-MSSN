package TPFinal.entidades;

import TPFinal.Animador;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Entidade {
	private PVector pos;
	private PVector vel;
	private Animador anim;
	private Body body;
	private PApplet p;

	public static final String resources = "resources/";

	public Entidade(PApplet p, PVector startingPos, PVector startingVel) {
		pos = startingPos;
		vel = startingVel;
		anim = criarAnimador(p);
		body = criarBody(p);
	}

	public PVector getPos() {
		return pos;
	}

	public PVector getVel() {
		return vel;
	}

	public abstract Animador criarAnimador(PApplet p);

	public abstract Body criarBody(PApplet p);

	public Animador getAnimator() {
		return this.anim;
	}

	public Body getBody() {
		return this.body;
	}

}
