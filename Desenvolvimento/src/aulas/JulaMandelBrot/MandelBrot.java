package aulas.JulaMandelBrot;

import processing.core.PApplet;
import setup.IProcessingApp;

public class MandelBrot implements IProcessingApp {

	private boolean drawMandelBrott;
	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		drawMandelBrott = true;
	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		if( drawMandelBrott) {
			
			//este w e h s�o a range, a janela
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
						a = aa - bb + x; // this operation corresponds to z -> z^2+c where z=a+ib c=(x,y)
						b = twoab + y;
						n++;
						absOld = abs;
					}

					// We color each pixel based on how long it takes to get to infinity
					// If we never got there, let's pick the color black
					if (n == maxiterations) {
						p.pixels[i+j*p.width] = p.color(0);
					} else {
						// Gosh, we could make fancy colors here if we wanted
						float norm = p.map(convergeNumber, 0, maxiterations, 0, 1);
						p.pixels[i+j*p.width] = p.color(p.map((float) Math.sqrt(norm), 0, 1, 0, 255));
					}
					x += dx;
				}
				y += dy;
			}
			p.updatePixels();
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





