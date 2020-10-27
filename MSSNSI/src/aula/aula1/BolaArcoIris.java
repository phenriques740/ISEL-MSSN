package aula.aula1;

import ProcessingSetup.IProcessingApp;
import processing.core.PApplet;

public class BolaArcoIris implements IProcessingApp {

	private int fillColor;

	@Override
	public void setup(PApplet p) {
		// Evita ter de controlar a framerate no draw com if e elses
		p.frameRate(60);
		fillColor = p.color(0, 0, 255);
	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		p.background(255, 255, 255);

		p.fill(fillColor);
		p.circle(p.mouseX, p.mouseY, 50);
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		fillColor = p.color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

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