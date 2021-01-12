package aulas.ChaosGame;



import java.util.Arrays;

import aulas.graph.SubPlot;
import processing.core.PApplet;
import setup.InterfaceProcessingApp;

public class ChaosGameDynamic implements InterfaceProcessingApp {

	private double[] window = {0, 1, 0, 1};
	private float[] viewport = {0.15f, 0.15f, 0.7f, 0.7f};
	//private double[] pA = {0.5, 1};
	//private double[] pB = {0, 0};
	//private double[] pC = {1, 0};
	//float[] ppA, ppB, ppC;
	private final int MAX_VERTS = 20;
	private double[][] arrPontos = new double[MAX_VERTS][2];
	private int index = 0;
	private boolean atLeastOne = false, rightPressed = false;
	private SubPlot plt;
	private float[] pNow = new float[2];
	private boolean rightClick = false;
	
	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		plt = new SubPlot(window, viewport, p.width, p.height);
		//ppA = plt.getPixelCoord(pA);
		//ppB = plt.getPixelCoord(pB);
		//ppC = plt.getPixelCoord(pC);
		p.stroke(0);
		p.strokeWeight(4);
		//p.line(ppA[0], ppA[1], ppB[0], ppB[1]);
		//p.line(ppC[0], ppC[1], ppB[0], ppB[1]);
		//p.line(ppA[0], ppA[1], ppC[0], ppC[1]);
		p.strokeWeight(1);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.fill(100);
		int lastValidIndex = 0;;
		// TODO Auto-generated method stub
		for( int i = 0; i < arrPontos.length; i++) {
			if( (arrPontos[i][0] != 0) && (arrPontos[i][1] != 0) ) {	//se ambas as coordenadas forem diferentes de 0
				//tenho que fazer isto porque, o array e inicilizado com ZEROS inv�s de null....
				p.circle((float)arrPontos[i][0], (float)arrPontos[i][1], 4);
				lastValidIndex = i;
			}
			if( i >= 1 && ((arrPontos[i][1] != 0)) && (arrPontos[i][0] != 0) ) {
				p.line((float)arrPontos[i-1][0], (float)arrPontos[i-1][1], (float)arrPontos[i][0], (float)arrPontos[i][1]);
			}
		}
		if(rightPressed) {
			p.line((float)arrPontos[0][0], (float)arrPontos[0][1], (float)arrPontos[lastValidIndex][0], (float)arrPontos[lastValidIndex][1]);	
			//System.out.println(lastValidIndex);
			for(int test = 0; test <= 10000; test++) {

				int ix = (int) p.random(0, lastValidIndex+1);
				pNow[0] = PApplet.lerp(pNow[0], (float) arrPontos[ix][0], 1/2f);
				pNow[1] = PApplet.lerp(pNow[1], (float) arrPontos[ix][1], 1/2f); 
				p.stroke(50+10*ix, ix,50+10*ix);
				//p.stroke(100, 0, 0);
				p.point(pNow[0], pNow[1]);
			}
		}

		
		p.text("LBM para criar vertices. RMB para fechar o poligono e come�ar a simula��o", 200, 580);
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		if(p.mouseButton == p.LEFT && !rightClick) {
			double[] P = {p.mouseX, p.mouseY};
			//System.out.println("Ponto em si ---> " + Arrays.toString(P));
			arrPontos[index] = P;
			//System.out.println("Ponto metido no array "+ Arrays.toString(arrPontos[index] ));
			atLeastOne = true;
			index++;
		}

		if(p.mouseButton == p.RIGHT) {
			System.out.println("Ponto metido no array "+ Arrays.deepToString(arrPontos ));
			rightPressed = true;
			rightClick = true;
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
