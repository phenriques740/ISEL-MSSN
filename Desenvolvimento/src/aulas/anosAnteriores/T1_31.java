package aulas.anosAnteriores;

import aulas.setup.IProcessingApp;
import processing.core.PApplet;

public class T1_31 implements IProcessingApp {
	boolean flag = false;

	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(PApplet p, float dt) {
		float mouseX =  p.mouseX;
		float mouseY =  p.mouseY;
		if(p.mousePressed) {
			p.fill(p.random(0, 255), p.random(0, 255), p.random(0, 255));
		}
		if(p.keyPressed) {
			//faser reset ao logo em termos de cor e posi��o. mudar o background para branco
			if( p.key == 'c')
				mouseX = 400;
				mouseY = 300;
				flag = true;
				p.background(255,255,255);
		}
		if(p.keyPressed) {
			// permite chegar ao logo so a mudar a posi��o X e poe o background a branco
			if( p.key == 'b')
				p.background(255,255,255);
		}
		// TODO Auto-generated method stub
		kitKat(p, mouseX, mouseY);
		//ideia: mudar estar coordenadas que s�o passadas
		//de maneira a que quando mexo o rato desfacha as letras e enventualmente 
		//volte a fazer o logo para ficar bacano!
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	public void kitKat( PApplet p, float oX, float oY ) {
		//PS:NAO VOLTES A FAZER UMA COISA ASSIM ! ISTO E MUITA FRUTA !! ja estas a fazer isto a 4 horas....
		// tens que acertar as posi��es, rota��es.....
		if(flag) {
			p.fill( p.color(255, 0, 0) );
		}
		p.ellipse(oX, oY, 420, 170);

		if(flag) {
			p.fill( p.color(255, 255, 255) );		//stroke (1) pinta de branco, stroke(0) de preto
		}
		p.ellipse(oX, oY, 400, 150);

		//fazer o K
		if(flag) {
			p.fill( p.color(255, 0, 0) );
		}
		p.rect(oX-150, oY-60, 15, 120);
		p.rotate((float)p.PI/4.0f);
		p.rect(oX+0, oY-380, 15, 120);
		//zerar a rota��o
		p.rotate(0);
		p.rotate((float)p.PI/4.0f);
		p.rotate((float)p.PI/4.0f);
		p.rect(oX-380, oY-800, 15, 100);
		//fazer o i
		p.rotate(-(float)p.PI/4.0f);
		p.rotate((float)p.PI/2.0f);
		p.rect(oX-730, oY-660, 15, 70);
		p.circle(oX-725, oY-570, 20);
		//fazer o t
		p.rect(oX-760, oY-680, 15, 100);
		p.rotate((float)p.PI/2.0f);
		p.rect(oX-700, oY+40, 15, 50);

		p.rotate(0);

		//fazer o K
		if(flag) {
			p.fill( p.color(255, 0, 0) );
		}
		p.rotate((float)p.PI/2.0f);
		p.rect(oX+30, oY-60, 15, 140);
		p.rotate((float)p.PI/4.0f);
		p.rect(oX+120, oY-500, 15, 120);
		//zerar a rota��o
		p.rotate(0);
		p.rotate((float)p.PI/4.0f);
		p.rotate((float)p.PI/4.0f);
		p.rect(oX-490, oY-930, 15, 100);
		//fazer o a....
		p.rotate(-(float)p.PI/4.0f);
		p.rotate((float)p.PI/3.0f);
		p.rect(oX-700, oY-870, 15, 80);
		//a outra parte do a
		p.rotate((float)p.PI/4.0f);
		p.rect(oX-960, oY-520, 15, 80);
		//parte final do a...
		p.rotate(-(float)p.PI/2.0f);
		if(flag) {
			p.fill(255,0,0);
		}
		p.rect(oX-230, oY-900, 15, 60);
		//fazer o t
		p.rotate((float)p.PI/2.0f);
		p.rect(oX-1020, oY-500, 15, 100);
		p.rotate((float)p.PI/2.0f);
		p.rect(oX-520, oY+300, 15, 50);
		flag = false;
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}










