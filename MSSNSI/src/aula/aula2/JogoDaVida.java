package aula.aula2;

import processing.core.PApplet;

public class JogoDaVida extends CellularAutomata {
	public JogoDaVida(PApplet p, int nrows, int ncols, int radiusNeigh) {
		super(p, nrows, ncols, 2, radiusNeigh);

	}

	@Override
	public void setStateColors(PApplet p) {
		this.getStateColors()[0] = p.color(255, 0, 0);
		this.getStateColors()[1] = p.color(0, 255, 0);
	}

	@Override
	public void update() {
		for (int i = 0; i < this.getNrows(); i++) {
			for (int j = 0; j < this.getNcols(); j++) {
				Cell c = getCellInGrid(i, j);
				int nVizinhosVivos = countLivingNeighboors(c);

				boolean isAlive = c.getState() == 1 ? true : false;

				if (isAlive) {
					if (nVizinhosVivos > 3 || nVizinhosVivos < 2) {
						// Matar a celula
						c.setState(0);
					}
				} else {
					if (nVizinhosVivos == 3) {
						c.setState(1);
					}
				}
			}
		}
	}

	/**
	 * Como o jogo da vida só tem dois estados, se estiver no estado 0 a celula está
	 * morta, se estiver no estado 1, está viva. Basta somar os estados a 1
	 * 
	 * @param c
	 * @return numero de vizinhos vivos
	 */
	private int countLivingNeighboors(Cell c) {
		Cell[] neigh = c.getNeighbors();

		int vivos = 0;
		for (Cell cell : neigh) {
			vivos += cell.getState();
		}
		return vivos;

	}
}
