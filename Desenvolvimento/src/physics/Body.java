package physics;

import processing.core.PApplet;
import processing.core.PVector;
import graph.SubPlot;

public class Body extends Mover {
	// public static final double G = 1e-4f;
	protected int color;
	private boolean flagRemove = false;
	protected float radius;
	float[] boudingBox;
	private float width, height;

	public Body(PVector pos, PVector vel, float mass, float width, float height, int color) {
		super(pos, vel, mass);
		this.height = height;
		this.width = width;
		this.color = color;
		this.radius = radius;
		this.flagRemove = false;
	}

	public float getWidth() {
		return width;
	}
	
	public void selfDestruct(Body body) {
		body = null;
	}
	
	public boolean collision(Body body, SubPlot plt) {
		//System.out.println("height----->"+body.getHeight());
		//System.out.println("X--->"+body.getPos().x+" Y--->"+body.getPos().y+" width---->"+body.getWidth()+" height---->"+body.getHeight());	//estas coordenadas estao em viewport. tenho que as converter para plt
		float[] bodyCoordPix = plt.getPixelCoord(body.getPos().x, body.getPos().y);			//rect1
		float[] bodyCoordPixMyself = plt.getPixelCoord(this.getPos().x, this.getPos().y);	//rect2
		//System.out.println("localX---->"+bodyCoordPixMyself[0] +" localY----->"+bodyCoordPixMyself[1]);
		//System.out.println("X----->"+bodyCoordPix[0]+" Y---->"+bodyCoordPix[1] );
		if( bodyCoordPix[0] < bodyCoordPixMyself[0]+this.getWidth() && 
			bodyCoordPix[0] + body.getWidth() > bodyCoordPixMyself[0] &&
			bodyCoordPix[1] < bodyCoordPixMyself[1]+this.getHeight() &&
			bodyCoordPix[1] + body.getHeight() > bodyCoordPixMyself[1]) {
			
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
		float[] r = plt.getDimInPixel(radius, radius);

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

	public boolean isFlagRemove() {
		return flagRemove;
	}

	public void setFlagRemove(boolean flagRemove) {
		this.flagRemove = flagRemove;
	}

	// para a parte do pedro funcionar:
	public boolean isInside(PVector pos) {
		return PVector.dist(pos, this.pos) <= getRadius();
	}

}
