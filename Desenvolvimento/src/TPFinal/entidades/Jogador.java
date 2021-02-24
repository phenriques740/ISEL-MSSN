package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Jogador extends Entidade {
	private String resources = "resources/";
	private SpriteDef mcRight;
	private SpriteDef mcLeft;
	private PVector MCStartingVel = new PVector();

	public Jogador(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height, 0, 0);
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
	public Body criarBody(PApplet p) {
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), p.color(255, 128, 0));
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt) {
		Body MCBody = this.getBody();
		SpriteDef MC = this.getAnimator().getSpriteDef();
		
		this.getBody().setVel(MCStartingVel);
		this.getBody().move(dt * 15);
		if (drawBoundingBox) {
			MCBody.display(p, plt);
		}
		MC.animateHorizontal();
		MC.show();

		mcLeft.setPos(MC.getPos());
		mcRight.setPos(MC.getPos());
		makeBodyFollowAnimation(MCBody, MC, plt);

	}

}

















