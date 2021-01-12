package ChaosGame;

import graph.SubPlot;
import processing.core.PApplet;
import setup.InterfaceProcessingApp;

public class ChaosGameStatic implements InterfaceProcessingApp {

	private double[] window = {-1,1,0,1};
	private float[] viewport = {0.1f,0.1f,0.8f,0.8f};
	private SubPlot plot;
	private float[] pA,pB,pCT;
	private float[] pCQ,pDQ;
	private float[] pNow = new float[2];
	private float oldRnd;
	private int num;

	@Override
	public void setup(PApplet parent) {
		parent.background(0);
		plot = new SubPlot(window,viewport,parent.width,parent.height);
		pA = plot.getPixelCoord(-1, 0);
		pB = plot.getPixelCoord(1, 0);
		
	}

	@Override
	public void draw(PApplet parent, float dt) {
		if(num % 2 == 0) {
			drawTriangle(parent);
		}else {
			drawQuad(parent);
		}
		
		parent.text("Posso Carregar em LMB/RMB para mudar de triangulo para quadrado", 200, 580);
	}

	private void drawTriangle(PApplet parent) {
		pCT = plot.getPixelCoord(0, 1);
		for (int i = 0; i <= 1000; i++) {
			int rnd = (int) parent.random(0,3);
			if (rnd == 0) {
				pNow[0] = PApplet.lerp(pNow[0], pA[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pA[1], 0.5f);
				parent.stroke(255,0,0);
			}
			else if(rnd == 1) {
				pNow[0] = PApplet.lerp(pNow[0], pB[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pB[1], 0.5f);  
				parent.stroke(0,255,0);
			}
			else if(rnd == 2) {
				pNow[0] = PApplet.lerp(pNow[0], pCT[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pCT[1], 0.5f);  
				parent.stroke(0,0,255);
			}
			parent.point(pNow[0], pNow[1]);
		}
	}

	private void drawQuad(PApplet parent) {
		pCQ = plot.getPixelCoord(-1, 1);
		pDQ = plot.getPixelCoord(1, 1);
		for (int i = 0; i < 1000; i++) {
			int rnd;
			do rnd = (int) parent.random(0,4);

			while (rnd == oldRnd);
			oldRnd = rnd;

			if (rnd == 0) {
				pNow[0] = PApplet.lerp(pNow[0], pA[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pA[1], 0.5f);
				parent.stroke(255,0,0);
			}
			else if(rnd == 1) {
				pNow[0] = PApplet.lerp(pNow[0], pB[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pB[1], 0.5f);  
				parent.stroke(0,255,0);
			}
			else if(rnd == 2) {
				pNow[0] = PApplet.lerp(pNow[0], pCQ[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pCQ[1], 0.5f);  
				parent.stroke(0,0,255);
			}
			else if(rnd == 3) {
				pNow[0] = PApplet.lerp(pNow[0], pDQ[0], 0.5f);
				pNow[1] = PApplet.lerp(pNow[1], pDQ[1], 0.5f);  
				parent.stroke(0,255,255);
			}
			parent.point(pNow[0], pNow[1]);

		}
	}

	@Override
	public void keyPressed(PApplet parent) {

	}

	@Override
	public void mousePressed(PApplet parent) {
		parent.background(0);
		num++;
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}
}