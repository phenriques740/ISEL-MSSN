package aula.aula1;

import ProcessingSetup.IProcessingApp;
import processing.core.PApplet;

public class BouncingBall implements IProcessingApp {
	private int x, y;
	private float xspeed, yspeed;

	private int raio;

	@Override
	public void setup(PApplet p) {
		p.background(255, 255, 255);
		x = p.width / 2;
		y = p.height / 2;
		xspeed = 10;
		yspeed = 10;

		raio = 50;
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255, 255, 255);
		p.circle(x, y, raio * 2);

		x += xspeed;
		y += yspeed;

		if (x >= p.width - raio || x <= 0 + raio) {
			xspeed *= -1;
		}
		if (y >= p.height - raio || y <= 0 + raio) {
			yspeed *= -1;
		}
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		raio += 1;
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
