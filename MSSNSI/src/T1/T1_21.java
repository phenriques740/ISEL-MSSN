package T1;

import processing.core.PApplet;

public class T1_21 implements InterfaceProcessingApp{
	
	private int backgroundColor = 50;
	private int logoColor = 200;

	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		//por o background preto
		p.background(backgroundColor);
		audi( p, 400, 300 );
		
		
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void audi(PApplet p, int oX, int oY) {
		//por o cinzento da cor do audi
		p.fill(logoColor);
		
		//desenho o circulo no meio do ecra,
		p.circle(oX, oY, 50);
		
		//agora mudo a cor do proximo circulo para dar aquele aspecto tipo anel
		p.fill(backgroundColor);
		p.circle(oX, oY, 40);
		
		//FAZER TUDO ISTO DE NOVO PARA OS RESTANTES 3 CIRCULOS
		
		p.fill(logoColor);
		p.circle(oX+30, oY, 50);
		p.fill(backgroundColor);
		p.circle(oX+30, oY, 40);
		
		p.fill(logoColor);
		p.circle(oX+60, oY, 50);
		p.fill(backgroundColor);
		p.circle(oX+60, oY, 40);
		
		p.fill(logoColor);
		p.circle(oX+90, oY, 50);
		p.fill(backgroundColor);
		p.circle(oX+90, oY, 40);
		
		
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}
	

}
