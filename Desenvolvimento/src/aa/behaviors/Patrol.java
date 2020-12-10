package aa.behaviors;

import java.util.ArrayList;

import aa.Boid;
import processing.core.PVector;

public class Patrol extends Behavior {
	private ArrayList<PVector> patrolPoints;
	private int index;

	public Patrol(float weight, ArrayList<PVector> points) {
		super(weight);
		this.setPatrolPoints(points);
		index = 0;
		
	}

	@Override
	public PVector getDesiredVelocity(Boid me) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PVector> getPatrolPoints() {
		return patrolPoints;
	}

	public void setPatrolPoints(ArrayList<PVector> patrolPoints) {
		this.patrolPoints = patrolPoints;
	}

}
