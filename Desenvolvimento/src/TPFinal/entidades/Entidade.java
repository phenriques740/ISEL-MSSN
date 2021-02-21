package TPFinal.entidades;

import TPFinal.Animador;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Entidade {
	private PVector pos;
	private PVector vel;
	private Animador anim;
	private Body body;
	private float width, height; // altura e largura da bouding box

	public static final String resources = "resources/";

	public Entidade(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		pos = startingPos;
		vel = startingVel;
		this.width = width;
		this.height = height;
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

	public abstract void draw(PApplet p, SubPlot plt, boolean drawBoundingBox);

	public Animador getAnimator() {
		return this.anim;
	}

	public Body getBody() {
		return this.body;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
