package aulas.physics;

import java.util.ArrayList;
import java.util.List;

import aulas.graph.SubPlot;
import aulas.particleSystems.PSControl;
import aulas.particleSystems.ParticleSystem;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;

public class EarthSunApp implements IProcessingApp
{
	private float sunMass = 1.989e30f;
	// https://www.sjsu.edu/faculty/watkins/orbital.htm //
	//sobre a terra:
	private float earthMass = 5.97e24f;
	private float distEarthSun = 1.496e11f;
	private float earthSpeed = 3e4f;		//velocidade por segundo
	//sobre venus:
	private float venusMass = 4.867e24f;
	private float distVenusSun = distEarthSun*0.72f;	//na internet nao encontro um valor certo
	private float venusSpeed = earthSpeed*1.174f;		//velocidade por segundo
	private double[] window = {-1.5*distEarthSun, 1.5*distEarthSun, 
			-1.5*distEarthSun, 1.5*distEarthSun};
	private float[] viewport = {0f, 0f, 1f, 1f};
	private SubPlot plt;
	private Body sun, earth, moon, venus;
	private float speedUp = 60*60*24*30;  //one second corresponds to one month
	private ArrayList<Body> planets;
	private PVector posToDestroy = null;
	//particleSystems:
	private List<ParticleSystem> pss;
	private float[] velParams = {PApplet.radians(0), PApplet.radians(360), distEarthSun/10, distEarthSun/4 };
	private float[] lifetimeParams = {3, 5};
	private float[] radiusParams = {9e8f, 1e9f};
	private float flow = 50f;
	private boolean showTips;

	@Override
	public void setup(PApplet p) 
	{
		plt = new SubPlot(window, viewport, p.width, p.height);
		sun = new Body(new PVector(), new PVector(), sunMass, distEarthSun/10, 
				p.color(255,128,0));
		earth = new Body(new PVector(0, distEarthSun), 
				new PVector(earthSpeed,0), earthMass, distEarthSun/15, p.color(0,130,190));		//tamanho do size � 20
		venus = new Body(new PVector(0, distVenusSun),
				new PVector(venusSpeed,0), venusMass, distVenusSun/20, p.color(255,0,0 ) );
		
		planets = new ArrayList<Body>();
		pss = new ArrayList<ParticleSystem>();
		
		showTips = true;
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.fill(255, 16);
		float[] box = plt.getBoundingBox();
		p.rect(box[0], box[1], box[2], box[3]);

		sun.display(p, plt);
		sun.setVel(new PVector(0,0) );
		
		PVector f = sun.attraction(earth);
		earth.applyForce(f);
		earth.move(speedUp*dt);
		earth.display(p, plt);
		//System.out.println("Terra POS --> "+earth.getPos());
		
		//parte de venus:
		PVector fSunVenus = sun.attraction(venus);
		venus.applyForce(fSunVenus);
		venus.move(speedUp*dt);
		venus.display(p, plt);
		
		//parte dinamica para acrescentar planetas:
		for(Body planeta : planets){
			PVector fgeral = sun.attraction(planeta);
			planeta.applyForce(fgeral);
			planeta.move(speedUp*dt);
			planeta.display(p, plt);
		}
		
		//para detectar se carrego em cima de alguma planeta!
		for(Body planeta : planets){
			if(posToDestroy != null) {
				//PVector difference = posToDestroy.sub(planeta.pos);
				PVector difference = PVector.sub(posToDestroy, planeta.pos);
				//System.out.println("difference "+difference.mag() +" planeta "+planets.indexOf(planeta) );
				if( difference.mag() < distEarthSun/20 ) {
					//System.out.println("difference "+difference.mag() +" planeta "+planets.indexOf(planeta) );
					//System.out.println("Carreguei em cima de um planeta!");
					planeta.setFlagRemove(true);
					//**************************************************
					int color = p.color( p.random(255), p.random(255), p.random(255) );
					PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
					ParticleSystem ps = new ParticleSystem(posToDestroy, new PVector(), earthMass, .2f, psc, 5f );																																					
					pss.add(ps);	
					//**************************************************
				}
			}
		}
		
		
		for(int i = planets.size()-1; i >= 0  ; --i) {
			Body planetaActual = planets.get(i);
			if(planetaActual.isFlagRemove() ) {
				planets.remove( planetaActual );
				//break;			//por planets.get(i) numa variavel !
			}
		}
		
		
		for(ParticleSystem ps : pss) {
			PVector fgeral = sun.attraction(ps);
			ps.applyForce(fgeral);
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
		
		p.fill(100);
		if(showTips) {
			p.text("RMB para criar planetas. LMB para destruir planetas.Quando os planetas s�o destruidos, crio um sistema de particulas", 50, 50);
			p.text("Terra (azul) e Venus (vermelho) n�o podem ser destruidos", 50, 70);
		}
		
		posToDestroy = null;
	}

	@Override
	public void keyPressed(PApplet p) {
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
	public void mousePressed(PApplet p) {
		//System.out.println("Diferen�a entre massas: "+sunMass/earthMass);
		if(p.mouseButton == PConstants.RIGHT) {
			//System.out.println("Criei um planeta");
			double[] pp = plt.getWorldCoord(p.mouseX, p.mouseY);
			PVector pos = new PVector((float)pp[0], (float)pp[1]);
			
			Body planet = new Body(pos, new PVector(earthSpeed, 0), earthMass, distEarthSun/30, p.color(p.random(100, 255), p.random(100, 255), p.random(100, 255)));
			planets.add(planet);
		}
		else if(p.mouseButton == PConstants.LEFT) {
			//System.out.println("Destruir planetas!");
			double[] pp = plt.getWorldCoord(p.mouseX, p.mouseY);
			posToDestroy = new PVector((float)pp[0], (float)pp[1]);
			//System.out.println("PosToDestroy--> "+posToDestroy);
			//System.out.println( "Size da lista: "+planets.size() );
			//System.out.println("pos("+pos+") earthPos("+earth.getPos()+")");
			
		}
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		
	}
}
























