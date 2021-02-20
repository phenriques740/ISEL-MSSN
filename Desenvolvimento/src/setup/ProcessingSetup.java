package setup;

import TPFinal.Jogo;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {
	private static InterfaceProcessingApp app;
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
		float dt = (now - lastUpdate) / 1000f; // 1000 para dar em milisegundo
		lastUpdate = now;
		app.draw(this, dt);
	}

	@Override
	public void keyPressed() {
		app.keyPressed(this);
	}

	@Override
	public void keyReleased() {
		app.keyReleased(this);
	}

	@Override
	public void mousePressed() {
		app.mousePressed(this);
	}

	@Override
	public void mouseReleased() {
		app.mouseReleased(this);
	}

	public static void main(String[] args) {
		app = new Jogo();
		PApplet.main(ProcessingSetup.class);
	}
}
