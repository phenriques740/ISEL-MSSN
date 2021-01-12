package aulas.anosAnteriores;

import aulas.graph.SubPlot;
import processing.core.PApplet;
import setup.IProcessingApp;

public class TesteSubPlot implements IProcessingApp {

	private SubPlot plot;
	private double[] window = { -2, -2, 0, 4 };
	private float[] viewport = { 0.5f, 0.6f, 0.35f, 0.20f };

	@Override
	public void setup(PApplet p) {
		// correr o constructor que fizemos em SubPlot
		plot = new SubPlot(window, viewport, p.width, p.height);
		float[] oldc = plot.getPixelCoord(-2, square(-2));
		int numeroPontos = 100;
		double step = (window[1] - window[0]) / numeroPontos;

		for (float x = -2; x <= 2; x += step) {
			float y = square(x);
			float[] c = plot.getPixelCoord(x, y);
			p.line(oldc[0], oldc[1], c[0], c[1]);
			oldc = c;
		}
	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	private float square(float x) {
		return x * x;
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
