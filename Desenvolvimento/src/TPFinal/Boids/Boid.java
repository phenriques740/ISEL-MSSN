package TPFinal.Boids;

import java.util.ArrayList;



import java.util.List;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import TPFinal.entidades.Entidade;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;


public class Boid extends Entidade {

	private SubPlot plt;
	private PShape shape;
	protected DNA dna;
	protected Eye eye;
	private List<Behavior> behaviors;
	private float phiWander; //
	private double[] window;
	private float sumWeights;
	private int color;

	public Boid(PApplet p,PVector pos, float width, float height, int HP, int color) {
		super(p, pos, new PVector(), width, height, HP, color );
		System.out.println("HP---->"+HP);
		dna = new DNA(); // fica com as caracteristicas random!
		behaviors = new ArrayList<Behavior>();
	}

	public void setEye(Eye eye) {
		this.eye = eye;
	}

	public void addBehavior(Behavior behavior) {
		behaviors.add(behavior);
		updateSumWeights();
	}

	private void updateSumWeights() {
		sumWeights = 0;
		for (Behavior beh : behaviors) {
			sumWeights += beh.getWeight();
		}
	}

	public void removeBehavior(Behavior behavior) {
		if (behaviors.contains(behavior)) {
			behaviors.remove(behavior);
		}
		updateSumWeights();
	}

	public String printBehaviors() {
		return behaviors.toString();
	}

	public void applySingleBehavior(int i, float dt) {
		if (eye != null) {
			eye.look();
		}
		Behavior behavior = behaviors.get(i);
		PVector vd = behavior.getDesiredVelocity(this);
		move(dt, vd);
	}

	public void applyBehaviors(float dt) {
		if (eye != null) {
			eye.look();
		}
		PVector velocidadeDesejada = new PVector();
		for (Behavior behavior : behaviors) {
			PVector velocidadeDesejadaDeCadaComportamento = behavior.getDesiredVelocity(this);
			velocidadeDesejadaDeCadaComportamento.mult(behavior.getWeight() / sumWeights);
			velocidadeDesejada.add(velocidadeDesejadaDeCadaComportamento);
		}
		//System.out.println("velolcidade Desejada!!--->"+velocidadeDesejada.x+" ---->"+velocidadeDesejada.y);
		move(dt, velocidadeDesejada);
		
	}

	public Eye getEye() {
		return eye;
	}

	private void move(float dt, PVector velocidadeDesejada) {
		velocidadeDesejada.normalize().mult(dna.maxSpeed);
		PVector forceSterring = PVector.sub(velocidadeDesejada, getVel());
		this.getBody().applyForce(forceSterring.limit(dna.maxForce));
		
	}

	public DNA getDna() {
		return dna;
	}

	public void setDna(DNA dna) {
		this.dna = dna;
	}

	public float getPhiWander() {
		return phiWander;
	}

	public void setPhiWander(float phiWander) {
		this.phiWander = phiWander;
	}

	@Override
	public Animador criarAnimador(PApplet p) {
		// TODO Auto-generated method stub
		return new Animador(p, Entidade.resources + "bossV1.json", Entidade.resources + "bossV1.png", super.getPos(),
				super.getVel());
	}

	@Override
	public Body criarBody(PApplet p) {
		// TODO Auto-generated method stub
		return new Body(super.getPos(), super.getVel(), 1f, super.getWidth(), super.getHeight(), super.getColor());
	}

	@Override
	public void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt) {
		// TODO Auto-generated method stub
		Body bombBody = this.getBody();
		//System.out.println("good coord--->"+pixelCoordFromBody[0] +" ----->"+pixelCoordFromBody[1]);
		applyBehaviors(dt);
		if (drawBoundingBox) {
			bombBody.display(p, plt, super.getWidth(), super.getHeight());
		}
		
		bombBody.move(dt * 1);
		window = plt.getWindow();
		if (this.getBody().getPos().x < window[0]) {
			this.getBody().getPos().x += window[1] - window[0];
		}
		if (this.getBody().getPos().y < window[2]) {
			this.getBody().getPos().y += window[3] - window[2];
		}

		if (this.getBody().getPos().x >= window[1]) {
			this.getBody().getPos().x -= window[1] - window[0];
		}
		if (this.getBody().getPos().y >= window[3]) {
			this.getBody().getPos().y -= window[3] - window[2];
		}
		
		super.makeAnimationFollowBodyAccordingToPhysics(bombBody, this.getSpriteDef(), plt);
		SpriteDef spriteDef = this.getSpriteDef();
		spriteDef.show();
		spriteDef.animateHorizontal();
		
		
	}
	
	public PVector getPos() {
		return super.getBody().getPos();
	}

	public SpriteDef getSpriteDef() {
		// TODO Auto-generated method stub
		return super.getAnimator().getSpriteDef();
	}

}






