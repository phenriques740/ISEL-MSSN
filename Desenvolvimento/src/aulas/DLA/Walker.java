package DLA;

import java.awt.Color;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Walker {

	public enum State {
		STOPPED, WANDER
	}

	private PVector pos;
	private State state;
	private int color;
	private static int radius = 4;
	public static int num_wanders = 0;
	public static int num_stopped = 0;
	private Color START_COLOR = Color.BLUE;
	private Color END_COLOR = Color.red;
	private double nextRand;

	// constructor para o walker que vai andar at� encontrar um parado
	public Walker(PApplet p, int chosen) {
		if(chosen == 2)
			pos = new PVector( p.random(p.width), p.random(p.height) ); //come�a numa posi��o aleat�ria, usar quando os walkers subirem
		
		if(chosen == 1) {
			pos = new PVector(p.width / 2, p.height / 2);
			PVector r = PVector.random2D();
			r.mult(p.width / 2);
			pos.add(r);
		}
		
		 
		setState(p, State.WANDER);
	}

	// constructor para o walker que vai estar parado
	public Walker(PApplet p, PVector pos) {
		this.pos = pos;
		setState(p, State.STOPPED);
	}

	public void wander(PApplet p) {
		PVector step = PVector.random2D(); // o agente anda para uma posi��o geradao automaticamente
		pos.add(step);
		// fa�o um lerp, para me ir dirigindo par ao meio do ecra, com uma itensidade de
		// 0.002f!
		pos.lerp(new PVector(p.width / 2, p.height / 2), 0.0002f);
		// estes dois metodos constrain em baixo servem para a posi��o X e Y n�o
		// excederem os limites da janela
		pos.x = PApplet.constrain(pos.x, 0, p.width);
		pos.y = PApplet.constrain(pos.y, 0, p.height);
	}

	public void wanderUp(PApplet p) {
		PVector step = PVector.random2D();
		pos.add(step);
		// fa�o um lerp, para me ir dirigindo par ao meio do ecra, com uma itensidade de
		// 0.002f!
		pos.lerp(new PVector(pos.x, 0), 0.0002f);
		// estes dois metodos constrain em baixo servem para a posi��o X e Y n�o
		// excederem os limites da janela
		pos.x = PApplet.constrain(pos.x, 0, p.width);
		pos.y = PApplet.constrain(pos.y, 0, p.height);
	}

	

	public void display(PApplet p) {
		p.fill(color);
		p.circle(pos.x, pos.y, 2 * radius); // o radius � igual para todos os walkers, dai ser estatico
	}

	public void defineColor(PApplet p) {
		if (state == State.STOPPED) {
			float dist = PVector.dist(pos, new PVector(p.width / 2, p.height / 2));
			color = p.lerpColor(START_COLOR.getRGB(), END_COLOR.getRGB(), dist * 0.003f);
		}
	}
	
	public void defineColorWanderUp(PApplet p) {
		if (state == State.STOPPED) {
			float dist = PVector.dist(pos, new PVector(pos.x, 0)); //esquema de cores para quando os walkers sobem
			color = p.lerpColor(START_COLOR.getRGB(), END_COLOR.getRGB(), dist * 0.003f);
		}
	}

	public void updateState(PApplet p, List<Walker> walkers) {
		if (state == State.STOPPED) {
			return;
		}
		for (Walker w : walkers) {
			if (w.state == State.STOPPED) {
				float dist = PVector.dist(pos, w.pos);// distancia entre o walker actual e um da lista
				if (dist < 2 * radius) {
					setState(p, State.STOPPED);
					num_wanders--;
					break;
				}
			}
		}

	}

	public State getState() {
		return state;
	}

	public void setState(PApplet p, State state) {
		this.state = state;
		if (state == State.STOPPED) {
			color = p.color(0);
			num_stopped++;
		} else {
			color = p.color(255);
			num_wanders++;
		}
	}

}
