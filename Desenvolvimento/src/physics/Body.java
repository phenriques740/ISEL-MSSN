package physics;

import graph.SubPlot;
import particleSystems.PSControl;
import particleSystems.ParticleSystem;
import processing.core.PApplet;
import processing.core.PVector;

public class Body extends Mover {
	// public static final double G = 1e-4f;
	protected int color;
	protected float radius;
	float[] boudingBox;
	private float width, height;
	
	PSControl psc;
	private float[] velParams = {PApplet.radians(0), PApplet.radians(360), 1f, 1f };
	private float[] lifetimeParams = {1, 1.4f};
	private float[] radiusParams = {0.3f, 0.5f};
	private float flow = 20f;

	public Body(PVector pos, PVector vel, float mass, float width, float height, int color) {
		super(pos, vel, mass);
		this.height = height;
		this.width = width;
		this.color = color;
		this.radius = radius;
		psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, this.color);
	}
	
	public ParticleSystem explodeMe() {
		//vai come�ar o sistema de particulas
		return new ParticleSystem(this.getPos(), this.getVel(), 1, 1f, psc, 1.5f );	//este particle system vai ser criado quando o inimigo morrer
		//System.out.println("explodes me chamado");
	}

	public float getWidth() {
		return width;
	}

	/**
	 * Deteta se um corpo estranho colidou com ele.
	 * 
	 * É necessario
	 * 
	 * @param body corpo estranho
	 * @param plt  referencia ao subplot. É necessario pois as coordenadas são em
	 *             pontos locais, e as dimensoes em pixeis globais.
	 * @return
	 */

	public boolean collision(Body body, SubPlot plt) {
		float[] bodyCoordPix = plt.getPixelCoord(body.getPos().x, body.getPos().y); // rect1
		float[] bodyCoordPixMyself = plt.getPixelCoord(this.getPos().x, this.getPos().y); // rect2

		if (bodyCoordPix[0] < bodyCoordPixMyself[0] + this.getWidth()
				&& bodyCoordPix[0] + body.getWidth() > bodyCoordPixMyself[0]
				&& bodyCoordPix[1] < bodyCoordPixMyself[1] + this.getHeight()
				&& bodyCoordPix[1] + body.getHeight() > bodyCoordPixMyself[1]) {

			return true;
		}

		return false;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void display(PApplet p, SubPlot plt) {
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] r = plt.getDimInPixel(width, height);

		p.fill(color);
		// p.circle(pp[0], pp[1], r[0]);
		boudingBox = new float[] { pp[0], pp[1], r[0], r[1] * -2 };
		p.rect(boudingBox[0], boudingBox[1], boudingBox[2], boudingBox[3]);

		// p.circle(pp[0], pp[1], 2*r[0]);
	}

	public void display(PApplet p, SubPlot plt, float width, float height) {
		float[] pp = plt.getPixelCoord(pos.x, pos.y);

		p.fill(color);
		// p.circle(pp[0], pp[1], r[0]);
		boudingBox = new float[] { pp[0], pp[1], width, height };
		p.rect(boudingBox[0], boudingBox[1], boudingBox[2], boudingBox[3]);
	}

	public float[] getBoudingBox() {
		return boudingBox;
	}

	// para a parte do pedro funcionar:
	public boolean isInside(PVector pos) {
		return PVector.dist(pos, this.pos) <= getRadius();
	}

}












