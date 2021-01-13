package aulas.JulaMandelBrot;

import processing.core.PApplet;
import setup.IProcessingApp;

//https://www.youtube.com/watch?v=6z7GQewK-Ks				--obrigado Daniel Shiffman pelo mandelBrott
//https://www.youtube.com/watch?v=fAsaSkmbF5s				// obrigado tambem pelo Set de Julia

public class DynamicJulia implements IProcessingApp {
	private boolean drawMandelBrott = false;
	private boolean drawJulia = true;
	private boolean mouseInteract = true;
	private boolean larguei = true;

	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		//p.noLoop();
		//p.background(255);
		//p.colorMode(p.RGB, 1);
	}

	@Override
	public void draw(PApplet p, float dt) {
		
		if( drawJulia ) {
			
			float ca, cb;
			
			if( mouseInteract ) {
				ca = p.map(p.mouseX, 0, p.width, -1, 1);
				cb = p.map(p.mouseY, 0, p.height, -1, 1);
			}
			else {
				ca = -0.8f;
				cb = - 0.156f ;
			}
			//o codigo para o julia set e praticamente igual so que soma um numero especificio para dar o efeito que quero
			//esse numero especifico eu vejo na pagina da wikipedia julia set
			
			float w = 4;
			float h = (w * p.height) / p.width;
			float xmin = -w/2;
			float ymin = -h/2;
			p.loadPixels();
			int maxiterations = 100;


			float xmax = xmin + w;
			// y goes from ymin to ymax
			float ymax = ymin + h;

			// Calculate amount we increment x,y for each pixel
			float dx = (xmax - xmin) / (p.width);
			float dy = (ymax - ymin) / (p.height);

			// Start y
			float y = ymin;
			for (int j = 0; j < p.height; j++) {
				// Start x
				float x = xmin;
				for (int i = 0; i < p.width; i++) {

					// Now we test, as we iterate z = z^2 + c does z tend towards infinity?
					float a = x;
					float b = y;
					int n = 0;
					float max = 4.0f;  // Infinity in our finite world is simple, let's just consider it 4
					float absOld = 0.0f;
					float convergeNumber = maxiterations; // this will change if the while loop breaks due to non-convergence
					while (n < maxiterations) {
						// We suppose z = a+ib
						//isto aqui dentro e aquela matematica toda complexa que esta no video e que o prof explicou
						float aa = a * a;
						float bb = b * b;
						float abs = (float) Math.sqrt(aa + bb);
						if (abs > max) { // |z| = sqrt(a^2+b^2)
							// Now measure how much we exceeded the maximum: 
							float diffToLast = (float) (abs - absOld);
							float diffToMax  = (float) (max - absOld);
							convergeNumber = n + diffToMax/diffToLast;
							break;  // Bail
						}
						float twoab = 2.0f * a * b;
						a = aa - bb + ca; //estas s�o as tais constantes que mudam aqui no julia set
						b = twoab + cb;
						n++;
						absOld = abs;
					}

					// We color each pixel based on how long it takes to get to infinity
					// If we never got there, let's pick the color black
					if (n == maxiterations) {
						p.pixels[i+j*p.width] = p.color(0);
					} else {
						// Gosh, we could make fancy colors here if we wanted
						float norm = PApplet.map(convergeNumber, 0, maxiterations, 0, 1);
						float hu = PApplet.map((float) Math.sqrt(norm), 0, 1, 0, 255);
						p.pixels[i+j*p.width] = p.color(hu*1.1f, hu*0.5f, hu);
					}
					x += dx;
				}
				y += dy;
			}
			p.updatePixels();
			
			if(p.keyPressed == true && p.key == 'o' && larguei) {
				System.out.println("ca = " + ca);
				System.out.println("cab = " + cb);
				larguei = false;
			}
			
			if(p.keyPressed == true && p.key == 'r') {
				larguei = true;
			}
			
		}
		
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

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
