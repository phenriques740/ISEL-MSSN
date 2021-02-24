package TPFinal.Boids;

import java.util.ArrayList;



import java.util.List;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;


public class Boid extends Body {

	private SubPlot plt;
	private PShape shape;
	protected DNA dna;
	protected Eye eye;
	private List<Behavior> behaviors;
	private float phiWander; //
	private double[] window;
	private float sumWeights;

	public Boid(PVector pos, float mass, float width, float height, int color, PApplet p, SubPlot plt) {
		super(pos, new PVector(), mass, width, height, color);
		dna = new DNA(); // fica com as caracteristicas random!
		behaviors = new ArrayList<Behavior>();
		this.plt = plt;
		window = plt.getWindow();
		setShape(p, plt);
	}

	public void setShape(PApplet p, SubPlot plt, float radius, int color) {
		// para mudar de cor em runtime!
		this.radius = radius;
		this.color = color;
		setShape(p, plt);
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
		move(dt, velocidadeDesejada);
	}

	public Eye getEye() {
		return eye;
	}

	private void move(float dt, PVector velocidadeDesejada) {
		velocidadeDesejada.normalize().mult(dna.maxSpeed);
		PVector forceSterring = PVector.sub(velocidadeDesejada, vel);
		applyForce(forceSterring.limit(dna.maxForce));
		super.move(dt);//
		if (pos.x < window[0]) {
			pos.x += window[1] - window[0];
		}
		if (pos.y < window[2]) {
			pos.y += window[3] - window[2];
		}

		if (pos.x >= window[1]) {
			pos.x -= window[1] - window[0];
		}
		if (pos.y >= window[3]) {
			pos.y -= window[3] - window[2];
		}
	}

	public void setShape(PApplet p, SubPlot plt) { // posso importar uma imagem SVG
		float[] rr = plt.getDimInPixel(1, 1);

		shape = p.createShape();
		shape.beginShape();
		shape.noStroke();
		shape.fill(color);
		shape.vertex(-rr[0], rr[0] / 2);
		shape.vertex(rr[0], 0);
		shape.vertex(-rr[0], -rr[0] / 2);
		shape.vertex(-rr[0] / 2, 0);
		shape.endShape(PConstants.CLOSE);

	}

	@Override
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.shape(shape);
		p.popMatrix();
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

}
