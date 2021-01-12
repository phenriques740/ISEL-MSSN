package aulas.aa.behaviors;

import aulas.aa.IBehavior;

public abstract class Behavior implements IBehavior {
	private float weight;
	
	public Behavior(float weight) {
		this.weight = weight;
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public float getWeight() {
		return weight;
	}
	
}































