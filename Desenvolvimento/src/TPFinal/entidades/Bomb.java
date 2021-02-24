package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Bomb extends Entidade {
	private PVector gravity = new PVector(0, -10);

	public Bomb(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		// TODO Auto-generated method stub
		return new Animador(p, Entidade.resources + "bomb.json", Entidade.resources + "bomb.png", super.getPos(),
				super.getVel());
	}
	
	public SpriteDef getSpriteDef() {
		// TODO Auto-generated method stub
		return super.getAnimator().getSpriteDef();
	}

	@Override
	public Body criarBody(PApplet p) {
		// TODO Auto-generated method stub
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), p.color(255, 128, 0));
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt) {
		Body bombBody = this.getBody();
		SpriteDef bombSprite = this.getSpriteDef();
		//System.out.println("good coord--->"+pixelCoordFromBody[0] +" ----->"+pixelCoordFromBody[1]);
		if (drawBoundingBox) {
			bombBody.display(p, plt, super.getWidth(), super.getHeight());
		}
		
		bombBody.applyForce(gravity);
		bombBody.move(dt * 1);
		makeAnimationFollowBodyAccordingToPhysics(bombBody, bombSprite, plt);
		bombSprite.show();
		
	}
	
	

}










