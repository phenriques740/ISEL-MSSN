package aa;

import java.util.ArrayList;
import java.util.List;

import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public class Eye {
	private List<Body>allTrackingBodies;//lista de todos os objectos que o BOID precisa de estar ciente
	private List<Body>farSight;//visão ao longe
	private List<Body>nearSight;//visão perto
	private Boid me;
	protected Body target;
	
	public Eye(Boid me, List<Body> allTrackingBodies ) {
		this.me = me;
		this.allTrackingBodies = allTrackingBodies;
		target = allTrackingBodies.get(0);
	}
	
	private boolean inSight(PVector target, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(target, me.getPos());
		float d = r.mag();
		float angle = PVector.angleBetween(r, me.getVel());
		return ( (d > 0) && (d < maxDistance) && (angle < maxAngle) );
	}
	
	private boolean farSight(PVector target) {
		return inSight(target, me.dna.visionDistance, me.dna.visionAngle);
	}
	
	private boolean nearSight(PVector target) {
		return inSight(target, me.dna.visionSafeDistance, (float)Math.PI);
	}
	
	
	
	public void look() {
		farSight = new ArrayList<Body>();
		nearSight = new ArrayList<Body>();
		for(Body b : allTrackingBodies) {
			if( farSight(b.getPos()) ) {
				farSight.add(b);
			}
			if( nearSight(b.getPos()) ) {
				nearSight.add(b);
			}
		}
		
	}
	
	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		p.pushMatrix();
		
		float[] pp = plt.getPixelCoord(me.getPos().x, me.getPos().y);
		p.translate(pp[0], pp[1]);
		p.rotate(-me.getVel().heading());
		p.noFill();
		p.stroke(255,0,0);
		p.strokeWeight(3);
		float[] dd = plt.getDimInPixel(me.dna.visionDistance, me.dna.visionSafeDistance);
		float[] dd2 = plt.getDimInPixel(me.dna.visionSafeDistance, me.dna.visionSafeDistance);
		p.rotate(me.dna.visionAngle);
		p.line(0, 0, dd[0], 0);
		p.rotate(-2*me.dna.visionAngle);
		p.line(0, 0, dd[0], 0);
		p.rotate(me.dna.visionAngle);
		p.arc(0, 0, 2*dd[0], 2*dd[0], -me.dna.visionAngle, me.dna.visionAngle);
		p.stroke(255,0,255);
		p.circle(0, 0, 2*dd2[0]);
		
		
		p.popMatrix();
		p.popStyle();
	}

	public List<Body> getFarSight() {
		return farSight;
	}

	public List<Body> getNearSight() {
		return nearSight;
	}
	
}

















