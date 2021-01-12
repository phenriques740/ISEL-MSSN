package aulas.aa;

import processing.core.PVector;

public interface IBehavior {
	public PVector getDesiredVelocity(Boid me);
	public void setWeight(float weight);
	public float getWeight();
	
}
