package TPFinal.entidades;

import TPFinal.Animador;
import TPFinal.SpriteDef;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Entidade {
	private PVector pos;
	private PVector vel;
	private Animador anim;
	private Body body;
	private float width, height;		//altura e largura da bouding box
	protected boolean flagRemove = false;
	private int HP = 0;

	public static final String resources = "resources/";

	public Entidade(PApplet p, PVector startingPos, PVector startingVel, float width, float height, int HP) {
		this.HP = HP;
		this.flagRemove = false;
		pos = startingPos;
		vel = startingVel;
		this.width = width;
		this.height = height;
		anim = criarAnimador(p);
		body = criarBody(p);
	}
	
	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}
	
	public PVector getPos() {
		return pos;
	}
	
	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getVel() {
		return vel;
	}
	
	public void setVel(PVector vel) {
		this.vel = vel;
	}
	
	public boolean isFlagRemove() {
		return flagRemove;
	}

	public void setFlagRemove(boolean flagRemove) {
		this.flagRemove = flagRemove;
	}

	public abstract Animador criarAnimador(PApplet p);

	public abstract Body criarBody(PApplet p);

	public abstract void draw(PApplet p, SubPlot plt, boolean drawBoundingBox, float dt);

	public Animador getAnimator() {
		return this.anim;
	}

	public Body getBody() {
		return this.body;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	
	public void makeBodyFollowAnimation(Body body, SpriteDef spriteDef, SubPlot plt) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX(), (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}
	
	public void makeBodyFollowAnimation(Body body, SpriteDef spriteDef, SubPlot plt, int offset) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX() - offset, (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}
	
	
	public void makeAnimationFollowBodyAccordingToPhysics(Body body, SpriteDef spriteDef, SubPlot plt) {
		float[] coordConverted = plt.getPixelCoord((float) body.getPos().x, (float) body.getPos().y);
		spriteDef.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));	
	}
	
	
	
}









