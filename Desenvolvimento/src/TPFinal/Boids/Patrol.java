package TPFinal.Boids;

import java.util.ArrayList;

import physics.Body;
import processing.core.PVector;

public class Patrol extends Behavior {
	private ArrayList<Body> patrolPoints;
	private int index;

	public Patrol(float weight, ArrayList<Body> points) {
		super(weight);
		this.setPatrolPoints(points);
		index = 0;
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		Body bodyTarget = me.getEye().getTarget();
		PVector out = PVector.sub(bodyTarget.getPos(), me.getPos());
		//System.out.println("me---->"+me.getPos().x+" bodyTarget.getPos().x"+bodyTarget.getPos().x+" bodyTarget.getPos(.y)"+bodyTarget.getPos().y);
		if (out.mag() <= 0.1f) {
			me.getEye().setTarget(patrolPoints.get(++index % patrolPoints.size()));
		}
		return out;
	}

	public ArrayList<Body> getPatrolPoints() {
		return patrolPoints;
	}

	public void setPatrolPoints(ArrayList<Body> patrolPoints) {
		this.patrolPoints = patrolPoints;
	}

}
