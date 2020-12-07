package particleSystems;

import graph.SubPlot;
import physics.Body;
import physics.Mover;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends Body {

	private float lifespan;		//em segundos
	private float timer;		//variavel interna que conta quando tempo já passou
	
	
	
	protected Particle(PVector pos, PVector vel, float radius, int color, float lifespan) {
		super(pos, vel, 0f, radius, color);
		this.lifespan = lifespan;
		timer = 0;			//quando a particula é criada o timer começa a zero!
	}
	
	
	@Override
	public void move(float dt) {
		super.move(dt);		//este metodo move faz o que esta na classe Mover (dai ter o super) e ainda incrementa o timer da particula!
		timer += dt;
	}
	
	
	public boolean isDead(){
		return timer > lifespan;
	}
	
	
	@Override
	public void display(PApplet p, SubPlot plt) {
		//fazemos push e pop para guardar o estilo do desenho antes de alterar. para não estragar o que esta a ser desenhado!
		p.pushStyle();
		float alpha = PApplet.map(timer, 0, lifespan, 255, 0);	//timer varia entre 0 e lifespan.| 0 corresponde a 255! e lifespan mapeado para 0
		p.fill(color,alpha);
		
		//para fazer a conversão de coordenadas pixeis para as do meu mundo actual. necessário para mostrar a particula
		float[] pp = plt.getPixelCoord(pos.x, pos.y);		//posições X e Y
		float[] rr = plt.getDimInPixel(radius, radius);		//raio 
		p.noStroke();
		p.circle(pp[0], pp[1], 2*rr[0]);
		
		p.popStyle();
	}

}

























