package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Jogador extends Entidade {
	private String resources = "resources/";
	private float MCStartingVel = 0f;
	private SpriteDef mcRight;
	private SpriteDef mcLeft;
	private float width, height;	//altura e largura das bouding boxes

	public Jogador(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height);
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		Animador mcAanimRight = new Animador(p, resources + "skeletonRun.json", resources + "skeleton.png",
				super.getPos(), super.getVel());
		Animador mcAnimLeft = new Animador(p, resources + "skeletonLRun.json", resources + "skeleton.png",
				super.getPos(), super.getVel());

		mcRight = mcAanimRight.getSpriteDef();
		mcLeft = mcAnimLeft.getSpriteDef();
		return mcAnimLeft;
	}

	@Override
	public Body criarBody(PApplet p, float width, float height) {
		return new Body(super.getPos(), super.getVel(), 1f, width, height, p.color(255, 128, 0));
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox) {
		if (drawBoundingBox) {
			super.getBody().display(p, plt);;
		}
	}

}
