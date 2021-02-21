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
	private float width, height;		//altura e largura da bouding box
	private boolean flagRemove = false;

	public static final String resources = "resources/";

	public Entidade(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		this.flagRemove = false;
		pos = startingPos;
		vel = startingVel;
		anim = criarAnimador(p);
		body = criarBody(p, width, height);
	}

	public PVector getPos() {
		return pos;
	}

	public PVector getVel() {
		return vel;
	}
	
	public boolean isFlagRemove() {
		return flagRemove;
	}

	public void setFlagRemove(boolean flagRemove) {
		this.flagRemove = flagRemove;
	}

	public abstract Animador criarAnimador(PApplet p);

	public abstract Body criarBody(PApplet p, float width, float height);

	public abstract void draw(PApplet p, SubPlot plt, boolean drawBoundingBox);

	public Animador getAnimator() {
		return this.anim;
	}

	public Body getBody() {
		return this.body;
	}

}






