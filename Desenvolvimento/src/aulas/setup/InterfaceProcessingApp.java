package aulas.setup;

import processing.core.PApplet;

public interface InterfaceProcessingApp {
	public void setup(PApplet p);
	public void draw(PApplet p, float dt);
	public void mousePressed(PApplet p);
	public void keyPressed(PApplet p);
	void mouseReleased(PApplet p);
}
