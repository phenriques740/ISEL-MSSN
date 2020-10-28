package aula.aula2;

import ProcessingSetup.IProcessingApp;
import processing.core.PApplet;
import processing.core.PConstants;

public class TestJogoDaVida implements IProcessingApp {
	private final int nrows = 50;
	private final int ncols = 50;

	private int radiusNeigh = 1;

	private CellularAutomata ca;

	private boolean paused = true;

	@Override
	public void setup(PApplet p) {
		p.frameRate(5);
		ca = new JogoDaVida(p, nrows, ncols, radiusNeigh);
		// ca.initRandom();
		ca.initDead();
	}

	@Override
	public void draw(PApplet p, float dt) {
		if (!paused) {
			ca.update();
		}
		ca.display(p);
	}

	@Override
	public void mousePressed(PApplet p) {
		Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
		if (p.mouseButton == PConstants.RIGHT) {
			Cell[] neighboors = cell.getNeighbors();

			for (int i = 0; i < neighboors.length; i++) {
				neighboors[i].setState(1);
			}
		} else if (p.mouseButton == PConstants.LEFT) {
			cell.setState(1);
		}

	}

	@Override
	public void keyPressed(PApplet p) {
		switch (p.key) {
		case 32: // codigo do espaco
			paused = !paused;
			break;
		case 114:
			ca.initRandom();
			break;
		case 110:
			ca.initDead();
			break;
		}
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}
}
