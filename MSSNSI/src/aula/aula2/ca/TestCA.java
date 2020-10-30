package aula.aula2.ca;

import processing.core.PApplet;
import setup.IProcessingApp;

public class TestCA implements IProcessingApp {
	private final int nrows = 15;
	private final int ncols = 20;
	private int nStates = 4;
	private int radiusNeigh = 1;

	private CellularAutomata ca;

	@Override
	public void setup(PApplet p) {
		ca = new CellularAutomata(p, nrows, ncols, nStates, radiusNeigh);
		ca.initRandom();
	}

	@Override
	public void draw(PApplet p, float dt) {
		ca.display(p);
	}

	@Override
	public void mousePressed(PApplet p) {
		Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
		cell.setState(nStates - 1);

		Cell[] neighboors = cell.getNeighbors();

		for (int i = 0; i < neighboors.length; i++) {
			neighboors[i].setState(nStates - 1);
		}
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}
}
