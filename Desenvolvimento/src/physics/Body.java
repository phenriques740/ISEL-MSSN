package physics;

import processing.core.PApplet;
import processing.core.PVector;
import graph.SubPlot;

public class Body extends Mover
{
	//public static final double G = 1e-4f;
	protected int color;
	private boolean flagRemove = false;
	protected float radius;
	
	public Body(PVector pos, PVector vel, float mass, float radius, int color){
		super(pos, vel, mass);
		this.color = color;
		this.radius = radius;
		this.flagRemove = false;
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
		p.circle(pp[0], pp[1], r[0]);
		//p.circle(pp[0], pp[1], 2*r[0]);
	}
	

	public boolean isFlagRemove() {
		return flagRemove;
	}

	public void setFlagRemove(boolean flagRemove) {
		this.flagRemove = flagRemove;
	}
	
	
}