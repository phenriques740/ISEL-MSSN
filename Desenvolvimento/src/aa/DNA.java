package aa;

//esta classe tem as caracteristicas iniciais do BOID! 
public class DNA {
	public float maxSpeed; // cada BOID tem um maxSpeed diferente, maxSpeed[0] � o minimo e [1] � maximo!
							// tipo PSControl
	public float maxForce;
	public float visionDistance;
	public float visionSafeDistance; // para nao chocar com obst�culos
	public float visionAngle;
	public float deltaTPursuit;
	public float radiusArrive;
	public float deltaTWander; // quanto maior, mais � projectado para a frente
	public float radiusWander;
	public float deltaPhiWander; // variacao de angulo

	public DNA() {
		// unidades SI, mas depende das unidades da window!
		// Physics
		maxSpeed = random(5, 8);
		maxForce = random(4, 7);
		// Vision
		visionDistance = random(2, 2);
		visionSafeDistance = .25f * visionDistance;
		visionAngle = (float) Math.PI * 0.9f; // vis�o para cada lado. Se puser PI � vis�o 360� !
		// Pursuit
		deltaTPursuit = random(0.5f, 1f); // em segundos
		// Arrive
		radiusArrive = random(3, 5); // raio com que os boids v�o desacelarando quando est�o a chegar a target
		// Wander
		deltaTWander = random(1f, 1f);
		radiusWander = random(3f, 3f);
		deltaPhiWander = (float) Math.PI / 8;
	}

	public static float random(float min, float max) {
		return (float) (min + (max - min) * Math.random());
	}

}
