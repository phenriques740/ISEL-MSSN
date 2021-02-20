package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Inimigo extends Entidade {

	public Inimigo(PApplet p, PVector startingPos, PVector startingVel) {
		super(p, startingPos, startingVel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		return new Animador(p, Entidade.resources + "slimeIdle.json", Entidade.resources + "slimeIdle.png",
				super.getPos(), super.getVel());
	}

	@Override
	public Body criarBody(PApplet p) {
		return new Body(super.getPos(), super.getVel(), 5f, 1f, 1f, p.color(255, 128, 0));
	}

	public SpriteDef getSpriteDef() {
		// TODO Auto-generated method stub
		return super.getAnimator().getSpriteDef();
	}

}
