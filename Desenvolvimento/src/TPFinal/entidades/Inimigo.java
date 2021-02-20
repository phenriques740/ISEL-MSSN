package TPFinal.entidades;
import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Inimigo extends Entidade {

	private float width, height;
	
	public Inimigo(PApplet p, PVector startingPos, PVector startingVel, float width, float height) {
		super(p, startingPos, startingVel, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		return new Animador(p, Entidade.resources + "slimeIdle.json", Entidade.resources + "slimeIdle.png",
				super.getPos(), super.getVel());
	}

	@Override
	public Body criarBody(PApplet p, float width, float height) {
		return new Body(super.getPos(), super.getVel(), 1f, width, height, p.color(255, 128, 0));
	}

	public SpriteDef getSpriteDef() {
		// TODO Auto-generated method stub
		return super.getAnimator().getSpriteDef();
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox) {
		// TODO Auto-generated method stub
		
	}
	

}
