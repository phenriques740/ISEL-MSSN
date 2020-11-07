package aula.aula2.ca;

import processing.core.PApplet;

public class Cell {
	private int row, col;
	private int state;

	private Cell[] neighbors;
	private CellularAutomata ca;

	private int numVizinhosVivos;

	public Cell(CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;

		this.state = 0;
		this.neighbors = null;

		this.numVizinhosVivos = 0;
	}

	public void setNeighbors(Cell[] neigh) {
		this.neighbors = neigh;
	}

	public Cell[] getNeighbors() {
		return this.neighbors;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "| " + state + " ";
	}

	public void display(PApplet p) {
		p.stroke(0);
		p.fill(ca.getStateColors()[state]);
		p.rect(col * ca.getCellWidth(), row * ca.getCellHeight(), ca.getCellWidth(), ca.getCellHeight());
	}

	public void aplicarRegraJogoDaVida() {
		switch (state) {
		case 0:
			if (numVizinhosVivos == 3) {
				setState(1);
			}
			break;
		case 1:
			if (numVizinhosVivos < 2 || numVizinhosVivos > 3) {
				setState(0);
			}
			break;
		}
	}

	/**
	 * Conta os vizinhos vivos.<br>
	 * 
	 * <b> Só funciona em Automatos binarios, em que o estado vivo corresponde ao
	 * estado 1</b>
	 */
	public void contarVizinhosVivos() {
		Cell[] neigh = getNeighbors();

		int vivos = 0;
		for (Cell cell : neigh) {
			vivos += cell.getState();
		}
		numVizinhosVivos = vivos - state;
	}
}
