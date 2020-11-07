package setup;

import ca.TestJogoDaVida;
import DLA.DLA;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {
	private static IProcessingApp app;
	private int lastUpdate;

	@Override
	public void settings() {
		size(800, 800);
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
		// so acrescentar linhas aqui!! NAO MEXER EM MAIS NADA!
		//app = new TestJogoDaVida();
		app = new DLA();
		

		//app = new DLA();
		PApplet.main(ProcessingSetup.class);
	}

}

//corre em primeiro lugar 1-settings, depois em segundo lugar o 2-setup, e depois fica a correr o draw() infinitamente!		
