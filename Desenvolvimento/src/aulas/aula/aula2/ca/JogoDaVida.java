package aulas.aula.aula2.ca;

import processing.core.PApplet;

public class JogoDaVida extends CellularAutomata {
	public JogoDaVida(PApplet p, int nrows, int ncols, int radiusNeigh) {
		super(p, nrows, ncols, 2, radiusNeigh);

	}

	@Override
	public void setStateColors(PApplet p) {
		this.getStateColors()[0] = p.color(255, 255, 255);
		this.getStateColors()[1] = p.color(100, 100, 100);
	}

	@Override
	public void update() {
		for (int i = 0; i < this.getNrows(); i++) {
			for (int j = 0; j < this.getNcols(); j++) {
				Cell c = getCellInGrid(i, j);
				c.contarVizinhosVivos();
			}
		}

		for (int i = 0; i < this.getNrows(); i++) {
			for (int j = 0; j < this.getNcols(); j++) {
				Cell c = getCellInGrid(i, j);
				c.aplicarRegraJogoDaVida();
			}
		}
	}


}
