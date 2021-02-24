package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class PowerUp extends Entidade {

	// gravidade para os power ups cairem:
	private PVector gravity = new PVector(0, -10);

	public PowerUp(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height,  0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		// TODO Auto-generated method stub
		return new Animador(p, Entidade.resources + "powerUp.json", Entidade.resources + "powerUp.png", super.getPos(),
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
		// TODO Auto-generated method stub

		Body pwrUpBody = this.getBody();
		SpriteDef pwrSprite = this.getSpriteDef();
		//System.out.println("good coord--->"+pixelCoordFromBody[0] +" ----->"+pixelCoordFromBody[1]);
		if (drawBoundingBox) {
			pwrUpBody.display(p, plt, super.getWidth(), super.getHeight());
		}
		
		if(pwrSprite.getPos().y < 500) {
			pwrUpBody.applyForce(gravity);
			pwrUpBody.move(dt * 1);
		}
		
		makeAnimationFollowBodyAccordingToPhysics(pwrUpBody, pwrSprite, plt);
		pwrSprite.show();

	}

}





