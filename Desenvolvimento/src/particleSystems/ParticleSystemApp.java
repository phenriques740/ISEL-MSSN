package particleSystems;

import java.util.ArrayList;
import java.util.List;

import graph.SubPlot;
import setup.InterfaceProcessingApp;
import processing.core.PApplet;
import processing.core.PVector;

public class ParticleSystemApp implements InterfaceProcessingApp {

	//private ParticleSystem ps;
	private List<ParticleSystem> pss;
	private double[] window = {-10 , 10, -10, 10};	//x entre -10 e 10| y entre 10 e -10. TODOS OS VALORES TÊM QUE ESTAR ENTRE ESTE INTERVALO!
	private float[] viewport = {0, 0, 1, 1};		//vamos usar todo o ecrã
	private SubPlot plt;
	private float[] velParams = {PApplet.radians(90), PApplet.radians(20), 1, 3 };
	private float[] lifetimeParams = {3, 5};
	private float[] radiusParams = {0.1f, 0.2f};
	private float flow = 50;
	
	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		plt = new SubPlot(window, viewport, p.width, p.height);
		//ps = new ParticleSystem(new PVector(), new PVector(), 1f, .2f, p.color(255,0,0), 1f, new PVector(-5,-5) );
		pss = new ArrayList<ParticleSystem>();
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);			//para dar o efeito de a particula ir desvanecendo
		
		
		/*
		//aplicar uma força tipo peso:
		for(ParticleSystem ps : pss) {
			ps.applyForce(new PVector(0,-1) );
		}
		*/
		
		
		for(ParticleSystem ps : pss) {
			ps.move(dt);
			ps.display(p, plt);
		}
		
		/*
		//as liinhas de baixo permitem que se mexer o rato, a direcao dos particleSystems é alterada!
		velParams[0] = PApplet.map(p.mouseX, 0, p.width, PApplet.radians(0), PApplet.radians(360) );	
		for(ParticleSystem ps : pss) {	//percorrer todos os particleSystem's que ja foram instanciados
			PSControl psc = ps.getPSControl();
			psc.setVelParams(velParams);
		}
		*/
		
	}

	@Override
	public void mousePressed(PApplet p) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		
		int color = p.color( p.random(255), p.random(255), p.random(255) );
		
		PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
		ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0], (float)ww[1]), new PVector(), 1f, .2f, psc );																																					
		pss.add(ps);																																									
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
