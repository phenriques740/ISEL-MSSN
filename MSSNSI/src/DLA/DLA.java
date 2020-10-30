package DLA;

import java.util.ArrayList;
import java.util.List;

import DLA.Walker.State;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class DLA implements IProcessingApp {

	private List<Walker> walkers;
	private int NUM_WALKERS = 100;
	private int NUM_STEPS_PER_FRAME = 100;
	private int wandersAtTheStart, wandersAtTheEnd, numWandersEmFalta ;

	@Override
	public void setup(PApplet p) {
		walkers = new ArrayList<Walker>();

		/*
		// walker estatico inicial
		Walker w = new Walker(p, new PVector(p.width / 2, p.height / 2));
		walkers.add(w);
		*/
		for(int i = 800; i > 0; --i) {
			Walker w = new Walker(p, new PVector(i, -2));
			walkers.add(w);
		}
		
		Walker w;		//remover esta linha quando estiver a usar apenas um walker estatico inicial
		for (int i = 0; i < NUM_WALKERS; i++) {
			// crio um novo walker, e adiciono a lista principal walkers
			w = new Walker(p);
			walkers.add(w);

		}
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(190);
		for (int i = 0; i < NUM_STEPS_PER_FRAME; i++) {
			// repara que aqui faço o wander e todos e depois e que faço o display
			for (Walker w : walkers) {
				if (w.getState() == State.WANDER) {
					//w.wander(p);
					w.wanderUp(p);
					w.updateState(p, walkers);
					w.defineColor(p);
				}
			}
		}
		//System.out.println("Start-->"+wandersAtTheStart+" End---->"+wandersAtTheEnd);
		
		while (Walker.num_wanders < NUM_WALKERS) {
			// System.out.println("Wanders em falta------>"+numWandersEmFalta);
			// esta diferença é sempre positiva!
			Walker wToAdd = new Walker(p);
			walkers.add(wToAdd);

		}

		for (Walker w : walkers) {
			w.display(p);
		}

		System.out.println( "Stopped = "+ Walker.num_stopped + " Wanders = " + Walker.num_wanders );

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		
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
