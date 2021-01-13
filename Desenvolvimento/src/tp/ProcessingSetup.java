package tp;

import processing.core.PApplet;
import setup.IProcessingApp;

public class ProcessingSetup extends PApplet {
	private static IProcessingApp app;
	private int lastUpdate;

	@Override
	public void settings() {
		size(800, 600);
	}

	@Override
	public void setup() {
		app.setup(this);
		lastUpdate = millis();
	}

	@Override
	public void draw() {
		int now = millis();
		float dt = (now - lastUpdate) / 1000f; // dt e a diferenca temporal. / 1000 para dar em milisegundo
		lastUpdate = now;
		app.draw(this, dt);
	}

	@Override
	public void keyPressed() {
		app.keyPressed(this);
	}

	@Override
	public void mousePressed() {
		app.mousePressed(this);
	}

	public static void main(String[] args) {
		app = new Canvas();
		PApplet.main(ProcessingSetup.class);
	}

}
