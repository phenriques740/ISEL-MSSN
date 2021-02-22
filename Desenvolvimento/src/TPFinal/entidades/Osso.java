package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Osso extends Entidade {
	
	private float[] boneColisionBox = { 20, 30 };
	
	public Osso(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		// TODO Auto-generated method stub
		return new Animador(p, Entidade.resources + "bone.json", Entidade.resources + "boneR.png",
				super.getPos(), super.getVel());
	}
	
	public boolean isFlagRemove() {
		return flagRemove || this.getAnimator().getSpriteDef().isRemoveMe();
	}

	public void setFlagRemove(boolean flagRemove) {
		this.flagRemove = flagRemove;
	}

	@Override
	public Body criarBody(PApplet p) {
		// TODO Auto-generated method stub
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), p.color(255, 128, 0));
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt ) {
		// TODO Auto-generated method stub
		Body currentBone = this.getBody();
		SpriteDef currentBoneSprite = this.getAnimator().getSpriteDef();
		// System.out.println("index---->"+index);
		if(drawBoundingBox) {
			currentBone.display(p, plt, boneColisionBox[0], boneColisionBox[1]);
			currentBone.move(dt * 15);
		}
		super.makeBodyFollowAnimationBone(currentBone, currentBoneSprite, plt);
		currentBoneSprite.show();
		currentBoneSprite.animateVertical();

	}

	public Entidade getSpriteDef() {
		// TODO Auto-generated method stub
		return this.getSpriteDef();
	}

}










