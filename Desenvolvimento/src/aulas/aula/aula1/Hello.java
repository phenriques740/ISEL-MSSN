package aulas.aula.aula1;

import processing.core.PApplet;
import setup.IProcessingApp;

public class Hello implements IProcessingApp {

	@Override
	public void setup(PApplet p) {
		p.circle(p.width / 2, p.height / 2, 50f);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.circle(p.mouseX, p.mouseY, 50);
	}

	public static void main(String[] args) {
		PApplet.main(Hello.class);
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
