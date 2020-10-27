package aula.aula2;

import processing.core.PApplet;

public class CellularAutomata {
	private int nrows, ncols;
	private int nStates;
	private int radiusNeigh;

	private Cell[][] cells;
	private int[] colors;

	private int cellWidth, cellHeight;

	public CellularAutomata(PApplet p, int nrows, int ncols, int nStates, int radiusNeigh) {
		this.nrows = nrows;
		this.ncols = ncols;
		this.nStates = nStates;
		this.radiusNeigh = radiusNeigh;

		cells = new Cell[nrows][ncols];
		colors = new int[nStates];

		cellWidth = p.width / ncols;
		cellHeight = p.height / nrows;

		createCells();
		setStateColors(p);
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public void setStateColors(PApplet p) {
		for (int i = 0; i < nStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}

	public int[] getStateColors() {
		return colors;
	}

	private void createCells() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		setMooreNeighbors();
	}

	public void initRandom() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].setState((int) ((nStates - 1) * Math.random()));

			}
		}

	}

	public Cell pixel2Cell(int x, int y) {
		int row = y / cellHeight;
		int col = x / cellWidth;

		if (row >= nrows)
			row = nrows - 1;

		if (col >= ncols)
			col = ncols - 1;

		return cells[row][col];
	}

	private void setMooreNeighbors() {
		int NN = (int) Math.pow(2 * radiusNeigh + 1, 2);

		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				Cell[] neigh = new Cell[NN];

				int n = 0;

				for (int ii = -radiusNeigh; ii <= radiusNeigh; ii++) {
					int row = (i + ii + nrows) % nrows;

					for (int jj = -radiusNeigh; jj <= radiusNeigh; jj++) {
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}

				cells[i][j].setNeighbors(neigh);
			}
		}
	}

	public void display(PApplet p) {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[i][j].display(p);
			}
		}
	}

	@Override
	public String toString() {
		String out = "AUTOMATA (" + nrows + "," + ncols + ")\n";

		for (int i = 0; i < nrows; i++) {
			String line = "";
			for (int j = 0; j < ncols; j++) {
				line += cells[i][j].toString();
			}
			line += "|\n";
			out += line;
		}
		return out;
	}

}
