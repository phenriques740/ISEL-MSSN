package aulas.setup;

import aulas.LidenMayer.LSystemApp3;
import processing.core.PApplet;

public class Processing_Setup extends PApplet {
	// como PApplet so pode ser usada por 1 classe, tenho que a passar como
	// referencia
	private static InterfaceProcessingApp app;
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
	// este draw vai ser chamado todas as frames....tipo no unity o Update!
	public void draw() {
		// digo que o circlo vai aparecer nas coordenadas do rato X e Y
		// circle(mouseX, mouseY, raio);
		int now = millis();
		float dt = (now - lastUpdate) / 1000f; // 1000 para dar em milisegundo
		// System.out.println("FPS->"+1f/dt); //dt � a diferen�a entre a frame anterior
		// e a frame actual. isto mostra os FPS
		lastUpdate = now;
		app.draw(this, dt);
	}

	@Override
	public void keyPressed() {
		// sempre que pressiona uma tecla, qualquer tecla, este metodo corre
		// fill(0, 255, 0);
		app.keyPressed(this);
	}

	@Override
	public void mousePressed() {
		app.mousePressed(this);
	}

	public static void main(String[] args) {
		// app = new ChaosGameStatic();

		//app = new ChaosGameDynamic();

		// app = new LSystemApp1();
		// app = new LSystemApp2();
		 app = new LSystemApp3();
		// app = new LSystemApp4();
		// app = new LSystemApp5(); //mais completo

		// app = new MandelBrot();
		// app = new DynamicJulia();
		PApplet.main(Processing_Setup.class);
	}

}

//corre em primeiro lugar 1-settings, depois em segundo lugar o 2-setup, e depois fica a correr o draw() infinitamente!		
