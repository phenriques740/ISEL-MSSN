package particleSystems;

import java.util.ArrayList;
import java.util.List;

import graph.SubPlot;
import physics.Body;
import physics.Mover;
import processing.core.PApplet;
import processing.core.PVector;

public class ParticleSystem extends Body {
	
	private List<Particle> particles;
	private PSControl psc;
	private float timer;		//variavel interna que conta quando tempo já passou
	private float lifespan;		//em segundos

	public ParticleSystem(PVector pos, PVector vel, float mass, float radius, PSControl psc) {																															
		super(pos, vel, mass, radius, 0);	//um particle system não tem cor,  as particulas individuais que o compõem é que têm!
		//as particulas em si não têm massa, mas um conjunto de particulas têm massa!
		//da jeito para aplicar forças!
		this.psc = psc;
		this.particles = new ArrayList<Particle>();
	}
	
	public ParticleSystem(PVector pos, PVector vel, float mass, float radius, PSControl psc, float lifespan) {																															
		super(pos, vel, mass, radius, 0);	//um particle system não tem cor,  as particulas individuais que o compõem é que têm!
		//as particulas em si não têm massa, mas um conjunto de particulas têm massa!
		//da jeito para aplicar forças!
		this.psc = psc;
		this.particles = new ArrayList<Particle>();
		this.lifespan = lifespan;
	}
	
	public PSControl getPSControl() {
		return psc;
	}
	
	
	public boolean isDead(){
		return timer > lifespan;
	}
	
	@Override
	public void move(float dt) {
		super.move(dt);		//aqui desloco o sistem a de particulas em si!
		addParticles(dt);
		for(int i = particles.size()-1; i >= 0; i--) {
			Particle p = particles.get(i);
			p.move(dt);		//depois, aqui e que desloco as particulas na sua direção com a sua velocidade random
			if( p.isDead() ) {
				particles.remove(i);
			}
		}
		timer += dt;
	}
	
	private void addParticles(float dt) {
		float particlesPerFrame = psc.getFlow() * dt;	//numero de particulas que tenho de gerar numa frame
		int n = (int) particlesPerFrame;	//fico com a parte inteira
		float f = particlesPerFrame - n;	//fico com a parte fracionária
		for(int i = 0; i < n ; i++) {
			addOneParticle();
		}
		if(Math.random() < f) {
			addOneParticle();
		}
	}
	
	
	private void addOneParticle() {

		Particle particle = new Particle(pos, psc.getRndVel(), psc.getRndRadius(), psc.getColor(), psc.getRndLifetime());
		particles.add(particle);
	}																																																					
	
	
	@Override
	public void display(PApplet p, SubPlot plt) {
		for(Particle particle: particles) {		//correr a lista "particles"
			particle.display(p, plt);
		}
	}

	
	
}





















