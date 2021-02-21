package TPFinal.entidades;

import TPFinal.Animador;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Osso extends Entidade {
	public Osso(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body criarBody(PApplet p) {
		// TODO Auto-generated method stub
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), p.color(255, 128, 0));
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox) {
		// TODO Auto-generated method stub

	}

}
