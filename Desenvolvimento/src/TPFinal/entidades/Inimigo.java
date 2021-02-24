package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Inimigo extends Entidade {
	
	public boolean isShouldIDropBomb() {
		return shouldIDropBomb;
	}

	public void setShouldIDropBomb(boolean shouldIDropBomb) {
		this.shouldIDropBomb = shouldIDropBomb;
	}

	
	private float[] enemiesCollisionBox = { 60, 50 };
	private boolean shouldIDropBomb = false;
	private int HP = 0;

	
	
	public Inimigo(PApplet p, PVector startingPos, PVector startingVel, float width, float height, int HP, int color) {
		super(p, startingPos, startingVel, width, height, HP, color);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public Animador criarAnimador(PApplet p) {
		//System.out.println("super.getHP()---->"+this.HP );
		if(super.getHP() <= 1) {
			return new Animador(p, Entidade.resources + "slimeIdle.json", Entidade.resources + "slimeIdle.png", super.getPos(), super.getVel());
		}
		if(super.getHP() <= 3) {
			return new Animador(p, Entidade.resources + "slimeIdle.json", Entidade.resources + "slimeIdleMedium.png", super.getPos(), super.getVel());
		}
		if(super.getHP() <= 5) {
			return new Animador(p, Entidade.resources + "slimeIdle.json", Entidade.resources + "slimeIdleHard.png", super.getPos(), super.getVel());
		}
		return null;
	}

	@Override
	public Body criarBody(PApplet p) {
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), super.getColor() );
	}

	public SpriteDef getSpriteDef() {
		// TODO Auto-generated method stub
		return super.getAnimator().getSpriteDef();
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt) {
		
		Body enemieBody = this.getBody();
		
		enemieBody.move(dt * 15);
		if(drawBoundingBox) {
			enemieBody.display(p, plt, enemiesCollisionBox[0], enemiesCollisionBox[1]);
		}
		
		super.makeBodyFollowAnimation(enemieBody, this.getSpriteDef(), plt);		
		SpriteDef enemieSprite = this.getSpriteDef();
		enemieSprite.show();
		enemieSprite.animateHorizontal();

	}

}











