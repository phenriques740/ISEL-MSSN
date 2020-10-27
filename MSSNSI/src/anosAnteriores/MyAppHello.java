package anosAnteriores;

import ProcessingSetup.IProcessingApp;
import processing.core.PApplet;

public class MyAppHello implements IProcessingApp {
	private int raio = 30;

	float radius = 0, theta = 0;
	float scrX, scrY;
	int red = 0, green = 0, blue = 0;

	public void setup(PApplet p) {
		// p.fill(255, 0 ,0);
		p.stroke(255, 0, 0);
		red = 1;
	}

	public void draw(PApplet p, float dt) {
		// p.circle(p.mouseX, p.mouseY, raio);
		// framerate = 1/tempo
		// System.out.println(1./dt);

		if (theta >= 400)
			p.noLoop();

		scrX = theta * p.cos(theta);
		scrY = theta * p.sin(theta);

		theta += 0.25;

		p.rect(scrX + p.width / 2, scrY + p.height / 2, 1, 1);
		p.println("X: " + scrX + " Y: " + scrY);

	}

	public void mousePressed(PApplet p) {
		// raio++;
		if (red == 1) {
			p.stroke(0, 255, 0);
			green = 1;
			red = 0;
			blue = 0;
		} else if (green == 1) {
			p.stroke(0, 0, 255);
			blue = 1;
			red = 0;
			green = 0;
		}

		else if (blue == 1) {
			p.stroke(255, 0, 0);
			red = 1;
			green = 0;
			blue = 0;
		}
	}

	public void keyPressed(PApplet p) {
		// p.fill(0, 255, 0);
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
