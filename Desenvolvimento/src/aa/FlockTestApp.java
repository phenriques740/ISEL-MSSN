package aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graph.SubPlot;
import particleSystems.PSControl;
import particleSystems.ParticleSystem;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class FlockTestApp implements IProcessingApp {
	private Flock flock;
	private float[] sacfWeights = { 0.2f, 0.7f, 0.5f, 0f };// SAC=separate,Align,Cohesion,Flee
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	private PVector posToDestroy = null;
	// particleSystems:
	private List<ParticleSystem> pss;
	private float[] velParams = { PApplet.radians(0), PApplet.radians(360), 5, 8 };
	private float[] lifetimeParams = { 2, 4 };
	private float[] radiusParams = { 0.05f, 0.1f };
	private float flow = 50f;
	//lobo a perseguir ovelha em boids
	private Boid boidSpecial, targetBoid;
	private List<Body> allTrackingBodies;
	private int randomNewTarget;
	private Eye eye;
	private Pursuit pursuit;
	private Seek seek;
	private Wander wander;
	private PVector differenceBoid;
	private int randInt;
	private Random rand;
	private int playerPoints;
	private boolean showTips;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		flock = new Flock(200, .1f, .3f, p.color(0, 128, 0), sacfWeights, p, plt);
		pss = new ArrayList<ParticleSystem>();
		
		
		boidSpecial = new Boid(new PVector(), 0.1f, 0.5f, p.color(255,0,0), p, plt);
		pursuit = new Pursuit(1f);
		seek = new Seek(1f);
		wander = new Wander(1f);
		boidSpecial.addBehavior( seek );
		rand = new Random();
		randInt = rand.nextInt( flock.boids.size()-1 );
		targetBoid = flock.boids.get( randInt );

		//adiconar a lista que o Boid v� o �nico void verde
		allTrackingBodies = new ArrayList<Body>();
		allTrackingBodies.add(targetBoid);
		//allTrackingBodies.addAll(flock.boids);
		eye = new Eye(boidSpecial, allTrackingBodies);
		boidSpecial.setEye(eye);
		
		showTips = true;
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		// p.background(255);
		float[] boundingBox = plt.getBoundingBox(); // da as coordenadas do viewport
		p.rect(boundingBox[0], boundingBox[1], boundingBox[2], boundingBox[3]);
		//p.fill(255, 64);		//original do prof
		//p.fill(50, 64);		//melhor aspecto, mas as vezes o computador fica lento
		p.background(50);	//solu��o hibrida entre os dois de cima, sem o arrasto :(
		
		
		flock.applyBehavior(dt);
		flock.display(p, plt);

		for (Body b : flock.boids) {
			if (posToDestroy != null) {
				PVector difference = PVector.sub(posToDestroy, b.getPos());
				if (difference.mag() < .5f) {
					if(b.equals(targetBoid)) {
						//se carregar no target do meu predador, tenho que lhe por os comportamentos correctos!
						boidSpecial.removeBehavior(seek);
						boidSpecial.addBehavior(wander);
						System.out.println(boidSpecial.printBehaviors() );
						playerPoints += 10;
					}
					b.setFlagRemove(true);
					// **************************************************
					int color = p.color( p.random(255), p.random(255), p.random(255) );
					PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
					ParticleSystem ps = new ParticleSystem(posToDestroy, new PVector(), 1f, .2f, psc, 2f);
					pss.add(ps);
					// **************************************************
				}
			}
		}
		
		for(int i = flock.boids.size()-1; i >= 0  ; --i) {
			Body boidActual = flock.boids.get(i);
			if(boidActual.isFlagRemove() ) {
				flock.boids.remove( boidActual );
			}
		}
		
		for(ParticleSystem ps : pss) {
			
			//ps.applyForce(new PVector(0, -1));
			ps.move(dt);
			ps.display(p, plt);
			
		}	
		
		for(int i = pss.size()-1; i >= 0; i--) {
			ParticleSystem psActual = pss.get(i);
			if(psActual.isDead()) {
				pss.remove(psActual);
			}
		}
		
		//Codigo para o Boid especial e o seu target. o meu boid especial � um predador!	
		boidSpecial.applyBehaviors(dt);
		if(!targetBoid.isFlagRemove()) {
			//apenas vejo a visibilidade da presa Boid se estiver activa! se ja tiver sido removido nao preciso de ver
			targetBoid.getEye().display(p, plt);
		}
		//boidSpecial.applyForce();
		boidSpecial.display(p, plt);
		
		//se o meu predador estiver praticamente em cima da presa, marco a presa para ser removida,e escolho um novo target, adiciono ao eye do predador!
		differenceBoid = PVector.sub(boidSpecial.getPos(), targetBoid.getPos());
		if (differenceBoid.mag() < 1f && (!targetBoid.isFlagRemove()) ) {
			//System.out.println("Comi o target!");
			//a flag de cima e absolutamente necess�ria, caso contr�rio o boidEspecial cada vez que passa na posi��o antiga da presa, 
			//fica com cada vez mais wanders, tornando o seek ineficaz! 
			targetBoid.setFlagRemove(true);
			boidSpecial.removeBehavior(seek);
			boidSpecial.addBehavior(wander);
			System.out.println(boidSpecial.printBehaviors() );
			playerPoints -= 5;
		}

		
		if(showTips) {
			p.text("LMB/RMB para destruir Boid's. Quando um Boid � destruido desta maneira, crio um sistema de particulas durante poucos segundos", 20, 50);
			p.text("Boid vermelho persegue o Boid que tem a vis�o. Ap�s o Boid predador apanhar a presa, a presa � destruida", 20, 70);
			p.text("Enquanto o Boid n�o tem um novo targer faz Wander. Para dar um novo target carrego T. para esconder mensagens H", 20, 90);
			p.text("Vou Competir contra o Boid para ver quem destr�i primeiro o target, atrav�s dos pontos", 20, 110);
		}
		p.text("Pontos: "+playerPoints, 20, p.height-30);
		
		//System.out.println("Difference Boid--->"+differenceBoid.mag() );
		//fazer reset a posi��o para destruir
		posToDestroy = null;
	}

	@Override
	public void mousePressed(PApplet p) {
		double[] pp = plt.getWorldCoord(p.mouseX, p.mouseY);
		posToDestroy = new PVector((float) pp[0], (float) pp[1]);
	}

	@Override
	public void keyPressed(PApplet p) {
		if(p.key == 't' && (targetBoid.isFlagRemove())) {
			//System.out.println("carreguei em T!");
			//se carregar em T, vou escolher um novo boid aleat�rio para perseguir!
			randInt = rand.nextInt( flock.boids.size()-1 );
			targetBoid = flock.boids.get( (int) DNA.random(0, flock.boids.size()-1) );
			boidSpecial.removeBehavior(wander);
			boidSpecial.addBehavior(seek);
			
			allTrackingBodies = new ArrayList<Body>();
			allTrackingBodies.add(targetBoid);
			//allTrackingBodies.addAll(flock.boids);
			eye = new Eye(boidSpecial, allTrackingBodies);
			boidSpecial.setEye(eye);
		}
		
		if(p.key == 'h') {
			if(showTips == false) {
				showTips = true;
			}
			else {
				showTips = false;
			}
		}
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}









