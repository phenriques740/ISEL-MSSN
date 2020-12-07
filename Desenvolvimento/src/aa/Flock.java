package aa;

import java.util.ArrayList;
import java.util.List;

import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Flock {
	public List<Boid> boids;
	
	public Flock(int nBoids, float massSingleBoid, float radiusSingleBoid, int colorSingleBoid, float[] sacWeights, PApplet p, SubPlot plt){
		//o array sacWeights tem o peso de cada comportamento (separate, cohesion )
		boids = new ArrayList<Boid>();
		double[] w = plt.getWindow();
		for(int i = 0; i <nBoids; i++) {
			float x = p.random((float)w[0], (float)w[1]);
			float y = p.random((float)w[2], (float)w[3]);
			Boid b = new Boid(new PVector(x,y), massSingleBoid, radiusSingleBoid, colorSingleBoid, p , plt);
			b.addBehavior(new Separate(sacWeights[0]));
			b.addBehavior(new Align(sacWeights[1]));
			b.addBehavior(new Cohesion(sacWeights[2]));
			b.addBehavior(new Flee(sacWeights[3]));
			boids.add(b);
		}
		
		List<Body> bodies = boidList2BodyList(boids);
		for(Boid b : boids) {
			b.setEye(new Eye(b, bodies));
		}
		
	}
	
	public void applyBehavior(float dt) {
		for(Boid b : boids) {
			b.applyBehaviors(dt);
		}
	}
	
	public Boid getBoid(int i) {
		return boids.get(i);
	}
	
	private List<Body> boidList2BodyList(List<Boid> boids){
		List<Body> bodies = new ArrayList<Body>();
		for(Boid b : boids) {
			bodies.add(b);
		}
		return bodies;
	}
	
	public void display(PApplet p, SubPlot plt) {
		for(Boid b : boids) {
			b.display(p, plt);
		}
	}
	
	public void setColorSingleBoid(Boid b, int color) {
		b.setColor(color);
	}
	
}
















































































