package ProcessingSetup;

import aula.aula1.BolaArcoIris;
import aula.aula1.BouncingBall;
import aula.aula1.BouncingDVD;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {
	// como PApplet so pode ser usada por 1 classe, tenho que a passar como
	// referencia
	private static IProcessingApp app;
	private int lastUpdate;

	@Override
	public void settings() {
		// vou criar uma janela com largura 800px e altura 600px
		size(800, 600);
	}

	@Override
	public void setup() {
		// vou preencher todas as formas fechadas com a cor definida agora
		// , circulos, quadrados
		// fill(255, 0, 0);
		app.setup(this);
		lastUpdate = millis();

	}

	@Override
	public void draw() {
		// digo que o circlo vai aparecer nas coordenadas do rato X e Y
		// circle(mouseX, mouseY, raio);
		int now = millis();
		float dt = (now - lastUpdate) / 1000f; // dt e a diferenca temporal. / 1000 para dar em milisegundo
		lastUpdate = now;
		app.draw(this, dt);
	}

	@Override
	public void keyPressed() {
		// fill(0, 255, 0);
		app.keyPressed(this);
	}

	@Override
	public void mousePressed() {
		// raio = raio + 5;
		app.mousePressed(this);
	}

	public static void main(String[] args) {
		// so acrescentar linhas aqui!! NAO MEXER EM MAIS NADA!
		// app = new Hello();
		// app = new BolaArcoIris();
		// app = new BouncingBall();
		app = new BouncingDVD();

		PApplet.main(ProcessingSetup.class);
	}

}

//corre em primeiro lugar 1-settings, depois em segundo lugar o 2-setup, e depois fica a correr o draw() infinitamente!		
