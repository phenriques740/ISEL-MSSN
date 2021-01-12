package aulas.aula.aula1;

import processing.core.PApplet;
import processing.core.PImage;
import setup.IProcessingApp;

public class BouncingDVD implements IProcessingApp {
	private int x, y;
	private float xspeed, yspeed;

	private String path = "\\imgs\\DVD_logo.png";
	private PImage image;

	@Override
	public void setup(PApplet p) {
		p.frameRate(30);

		image = p.loadImage(p.dataPath("") + path);
		image.resize(0, 50);
		p.background(255, 255, 255);
		x = (int) (p.random(p.width) / 2f);
		y = (int) (p.random(p.height) / 2f);
		xspeed = 5;
		yspeed = 5;

	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255, 255, 255);

		x += xspeed;
		y += yspeed;

		// Bounding box stuff

		if (x >= p.width - image.width) {
			xspeed *= -1 * ((p.random(1.0f) * (1.125 - 0.875)) + 0.875);
			x = p.width - image.width;
		} else if (x <= 0) {
			xspeed *= -1 * ((p.random(1.0f) * (1.125 - 0.875)) + 0.875);
			x = 0;
		}
		if (y >= p.height - image.height) {
			yspeed *= -1 * ((p.random(1.0f) * (1.125 - 0.875)) + 0.875);
			y = p.height - image.height;
		} else if (y <= 0) {
			yspeed *= -1 * ((p.random(1.0f) * (1.125 - 0.875)) + 0.875);
			y = 0;
		}

		p.image(image, x, y);
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
