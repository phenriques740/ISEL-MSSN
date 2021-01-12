package aulas.anosAnteriores;

import aulas.setup.IProcessingApp;
import processing.core.PApplet;

public class T1_23 implements IProcessingApp{
	
	boolean flag = true;
	private int backgroundColor = 50;
	private int logoColor = 200;
	private float easing = 0.05f, currentX, currentY, somaTemporal;
	private int lastUpdate;
	float targetX = 0, targetY = 0;

	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		boolean coordenadasGeradas = false;
		//por o background preto
		p.background(backgroundColor);
		//audi( p, (int)p.random(0,800), (int)p.random(0, 600) );

		if(coordenadasGeradas) {
			targetX = p.random(0, 800);
			targetY = p.random(0, 600);
			}
		
		int now = p.millis();
		float diferencaTemporal = (now - lastUpdate) / 1000f;	//dt e a diferenca temporal. / 1000 para dar em milisegundo
		lastUpdate= now;
		somaTemporal += diferencaTemporal;
		if(somaTemporal >= 2 ) {
			p.println("somaTemporal... passaram 2 segundos !");
			somaTemporal = 0;
			targetX = p.random(0,800);
			targetY = p.random(0,600);
			
		}
		p.print(somaTemporal);
		
		float dx = targetX - currentX;
		currentX += dx * easing;
		
		float dy = targetY - currentY;
		currentY += dy * easing;
		audi(p, currentX, currentY);
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	public void audi(PApplet p, float oX, float oY) {
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