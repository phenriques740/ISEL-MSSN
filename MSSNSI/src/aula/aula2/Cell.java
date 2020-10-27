package aula.aula2;

import processing.core.PApplet;

public class Cell {
	private int row, col;
	private int state;

	private Cell[] neighbors;
	private CellularAutomata ca;

	public Cell(CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;

		this.state = 0;
		this.neighbors = null;
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
		p.fill(ca.getStateColors()[state]);
		p.rect(col * ca.getCellWidth(), row * ca.getCellHeight(), ca.getCellWidth(), ca.getCellHeight());

	}
}
