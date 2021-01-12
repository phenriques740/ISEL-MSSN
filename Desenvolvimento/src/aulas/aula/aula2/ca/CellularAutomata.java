package aulas.aula.aula2.ca;

import processing.core.PApplet;

public class CellularAutomata {
	private final int nrows, ncols;
	private final int nStates;
	private final int radiusNeigh;

	private Cell[][] cells;

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	private int[] colors;

	private final int cellWidth, cellHeight;

	/**
	 * Metodo construtor de um automato celular
	 * 
	 * @param p           referencia a PApplet onde desenhar
	 * @param nrows       Numero de linhas a inicializar na grelha
	 * @param ncols       Numero de colunas a inicializar na grelha
	 * @param nStates     Numero de estados que uma celula individual pode tomar no
	 *                    automato cellular
	 * @param radiusNeigh Raio da vizinha de uma celula
	 */
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

	/**
	 * Devolve a largura em pixeis que deve ser utilizado para representar 1 cela
	 * 
	 * @return
	 */
	public int getCellWidth() {
		return cellWidth;
	}

	/**
	 * Devolve a altura em pixeis que deve ser utilizado para representar 1 cela
	 * 
	 * @return
	 */
	public int getCellHeight() {
		return cellHeight;
	}

	/**
	 * Metodo por defeito para definir cores. Atribui valores aleatorios a cada uma
	 * das cores
	 * 
	 * @param p referencia a PApplet onde desenhar
	 */
	public void setStateColors(PApplet p) {
		for (int i = 0; i < nStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}

	/**
	 * Devolve o array de cores
	 * 
	 * @return
	 */
	public int[] getStateColors() {
		return colors;
	}

	private void createCells() {
		for (int i = 0; i < getNrows(); i++) {
			for (int j = 0; j < getNcols(); j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		setMooreNeighbors();
	}

	private void setMooreNeighbors() {
		int NN = (int) Math.pow(2 * radiusNeigh + 1, 2);

		for (int i = 0; i < getNrows(); i++) {
			for (int j = 0; j < getNcols(); j++) {
				Cell[] neigh = new Cell[NN];

				int n = 0;

				for (int ii = -radiusNeigh; ii <= radiusNeigh; ii++) {
					int row = (i + ii + getNrows()) % getNrows();
					for (int jj = -radiusNeigh; jj <= radiusNeigh; jj++) {

						int col = (j + jj + getNcols()) % getNcols();

						neigh[n++] = getCellInGrid(row, col);

					}
				}

				getCellInGrid(i, j).setNeighbors(neigh);
			}
		}
	}

	/**
	 * Inicializa as celulas num estado aleatorio
	 */
	public void initRandom() {
		for (int i = 0; i < getNrows(); i++) {
			for (int j = 0; j < getNcols(); j++) {
				getCellInGrid(i, j).setState((int) ((nStates) * Math.random()));

			}
		}
	}

	public void initDead() {
		for (int i = 0; i < getNrows(); i++) {
			for (int j = 0; j < getNcols(); j++) {
				getCellInGrid(i, j).setState(0);
			}
		}
	}

	/**
	 * Converte um conjunto de coordenadas X e Y na cela que ocupe essa posicao
	 * 
	 * @param x mouseX
	 * @param y mouseY
	 * @return Cell na posicao clicada
	 */
	public Cell pixel2Cell(int x, int y) {
		int row = y / cellHeight;
		int col = x / cellWidth;

		if (row >= getNrows())
			row = getNrows() - 1;

		if (col >= getNcols())
			col = getNcols() - 1;

		return getCellInGrid(row, col);
	}

	/**
	 * Desenha as celas numa janela de processing
	 * 
	 * @param p referencia a PApplet onde desenhar
	 */

	public void display(PApplet p) {
		for (int i = 0; i < getNrows(); i++) {
			for (int j = 0; j < getNcols(); j++) {
				getCellInGrid(i, j).display(p);
			}
		}
	}

	/**
	 * Representa na consola o que está desenhado, estados são representados por 1
	 * valor inteiro
	 */
	@Override
	public String toString() {
		String out = "AUTOMATA (" + getNrows() + "," + getNcols() + ")\n";

		for (int i = 0; i < getNrows(); i++) {
			String line = "";
			for (int j = 0; j < getNcols(); j++) {
				line += getCellInGrid(i, j).toString();
			}
			line += "|\n";
			out += line;
		}
		return out;
	}

	/**
	 * Metodo update, corresponde a atualizar
	 */
	public void update() {
		System.err.println("Metodo update nao foi implementado em cellular automata.");
	}

	public int getNcols() {
		return ncols;
	}

	public int getNrows() {
		return nrows;
	}

	public Cell getCellInGrid(int row, int col) {
		return cells[row][col];
	}

}
