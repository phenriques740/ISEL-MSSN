package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Boss extends Entidade {
	
	//private boolean amISpawned = false;
	
	public boolean isShouldIDropBomb() {
		return shouldIDropBomb;
	}

	public void setShouldIDropBomb(boolean shouldIDropBomb) {
		this.shouldIDropBomb = shouldIDropBomb;
	}

	
	private boolean shouldIDropBomb = false;
	private int HP = 0;

	
	
	public Boss(PApplet p, PVector startingPos, PVector startingVel, float width, float height, int HP) {
		super(p, startingPos, startingVel, width, height, HP);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public Animador criarAnimador(PApplet p) {
		return new Animador(p, Entidade.resources + "boss.json", Entidade.resources + "boss.png", super.getPos(), super.getVel());
	}

	@Override
	public Body criarBody(PApplet p) {
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), p.color(255, 128, 0));
	}

	public SpriteDef getSpriteDef() {
		// TODO Auto-generated method stub
		return super.getAnimator().getSpriteDef();
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt) {
		
		if(!this.isFlagRemove()) {
			Body bossBody = this.getBody();
			
			bossBody.move(dt * 15);
			if(drawBoundingBox) {
				bossBody.display(p, plt, this.getWidth(), this.getHeight());
			}
			
			super.makeBodyFollowAnimation(bossBody, this.getSpriteDef(), plt, 5);		
			SpriteDef enemieSprite = this.getSpriteDef();
			enemieSprite.show();
			enemieSprite.animateHorizontal();
		}

	}

}











