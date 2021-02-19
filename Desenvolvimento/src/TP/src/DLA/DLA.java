package TP.src.DLA;

import java.util.ArrayList;
import java.util.List;

import TP.src.DLA.Walker.State;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import TP.src.setup.InterfaceProcessingApp;

public class DLA implements InterfaceProcessingApp {

	private List<Walker> walkers;
	private int NUM_WALKERS = 100;
	private int NUM_STEPS_PER_FRAME = 100;
	private boolean number1Pressed, number2Pressed = false;
	private Walker wToAdd;
	private boolean heldLMB = false;

	@Override
	public void setup(PApplet p) {
		walkers = new ArrayList<Walker>();

		/*
		if(number1Pressed) {
			
			 Walker w = new Walker(p, new PVector(p.width / 2, p.height / 2)); //walker estatico inicial 
			 walkers.add(w); //adicionar walker estatico unico a lista
		}
		 

		if(number2Pressed) {
			for (int i = 800; i > 0; --i) {
				Walker w = new Walker(p, new PVector(i, -2));
				walkers.add(w);
			}
			Walker w; // remover esta linha quando estiver a usar apenas um walker estatico inicial
	
			for (int i = 0; i < NUM_WALKERS; i++) {
				// crio um novo walker, e adiciono a lista principal walkers
				w = new Walker(p,2);
				walkers.add(w);
	
			}
		}
		*/
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(190);
		
		if (number1Pressed || number2Pressed) {
			for (int i = 0; i < NUM_STEPS_PER_FRAME; i++) {
				// repara que aqui fa�o o wander e todos e depois e que fa�o o display
				for (Walker w : walkers) {
					if (w.getState() == State.WANDER) {
						if (number1Pressed) {
							w.wander(p);
							// w.wanderUp(p);
							w.updateState(p, walkers);
							w.defineColor(p);
							// w.defineColorWanderUp(p);
						}
						if(number2Pressed) {
							//w.wander(p);
							 w.wanderUp(p);
							w.updateState(p, walkers);
							//w.defineColor(p);
							w.defineColorWanderUp(p);
						}
					}
				}
			}
			// System.out.println("Start-->"+wandersAtTheStart+" End---->"+wandersAtTheEnd);

			while (Walker.num_wanders < NUM_WALKERS) {
				// System.out.println("Wanders em falta------>"+numWandersEmFalta);
				// esta diferen�a � sempre positiva!
				if(number1Pressed) {
					wToAdd = new Walker(p, 1);
				}
				if(number2Pressed) {
					wToAdd = new Walker(p, 2);
				}
				walkers.add(wToAdd);

			}
		} else {
			p.fill(50);
			p.text("Bot�o 1 a gravidade � para o centro. Bot�o 2 a gravidade � para cima.", 50, 50);
			p.text("LMB para fazer para criar walkers estaticos. RMB para parar.", 50, 70);
		}
		
		if(heldLMB) {
			Walker w = new Walker(p, new PVector(p.mouseX, p.mouseY)); // walker estatico inicial
			walkers.add(w);
			w.display(p);
		}

		for (Walker w : walkers) {
			w.display(p);
		}

		System.out.println("Stopped = " + Walker.num_stopped + " Wanders = " + Walker.num_wanders);
		

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		if (p.mouseButton == PConstants.LEFT) {
			//System.out.println("mouse pressed event detected!!!");
			heldLMB = true;
		}
		
		else if (p.mouseButton == PConstants.RIGHT) {
			//System.out.println("MOUSE RELEASED EVENT DETECTED!!!");
			heldLMB = false;
		}

	}
	
	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		if (p.key == '1' && !number1Pressed && !number1Pressed) {
			number1Pressed = true;
			heldLMB = false;
		}

		if (p.key == '2' && !number1Pressed && !number1Pressed) {
			number2Pressed = true;
			heldLMB = false;
		}

	}
	
	/*
	public void mouseDragged(PApplet p) {
		Walker w = new Walker(p, new PVector(p.mouseX, p.mouseY)); // walker estatico inicial
		walkers.add(w);
		w.display(p);
	}
	*/

}






