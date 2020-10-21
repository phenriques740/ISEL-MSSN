package ProcessingSetup;

import processing.core.PApplet;

public interface IProcessingApp {

	/**
	 * Chamado a quando da inicializacao da app
	 * 
	 * @param p , instancia de PApplet que utiliza o@Override motor do processing
	 */
	public void setup(PApplet p);

	/**
	 * Atualiza a frame desenhada na janela, em intervalos de tempo regulares.
	 * 
	 * @param p
	 * @param dt intervalo entre frame
	 */
	public void draw(PApplet p, float dt);

	/**
	 * Lida com o evento mousePressed.<br>
	 * 
	 * Chamado cada vez que existe um clique no rato. Usar o metodo mouseButton para
	 * descobrir que botão foi carregado.
	 * 
	 * @param p
	 */
	public void mousePressed(PApplet p);

	/**
	 * Lida com o evento keyPressed.<br>
	 * 
	 * Chamado cada vez que um tecla for premida. Usar a variavel key para saber
	 * qual em conjunto com a variavel keyCode
	 * 
	 * @param p
	 * 
	 */
	public void keyPressed(PApplet p);

	/**
	 * Lida com o evento mouseReleased.<br>
	 * 
	 * Chamado cada vez que apos um clique no rato, o utilizador solta o botao. Usar
	 * o metodo mouseButton para descobrir que botão foi carregado.
	 * 
	 * @param p
	 */
	void mouseReleased(PApplet p);

}
