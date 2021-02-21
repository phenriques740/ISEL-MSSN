package TPFinal;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Gere a classe e fornece definicoes sobre o sprite
 * 
 * @author vasco
 *
 */
public class SpriteDef {

	private float x, y, w, indexF;
	private int len;
	private ArrayList<PImage> animation;
	private PApplet p;
	private int speedUpFactor = 10;
	private boolean removeMe = false;
	private float speedX, speedY;
	private PVector speed;

	public SpriteDef(ArrayList<PImage> animation, PVector startingPos, PVector startingVel, PApplet p) {
		this.p = p;
		this.x = startingPos.x;
		this.y = startingPos.y;

		this.speed = startingVel;
		this.speedX = startingVel.x;
		this.speedY = startingVel.y;
		this.animation = animation;
		this.w = animation.get(0).width;
		this.len = animation.size();
		this.removeMe = false;
	}

	public boolean isRemoveMe() {
		return removeMe;
	}

	public void setRemoveMe(boolean removeMe) {
		this.removeMe = removeMe;
	}

	public int getSpeedUpFactor() {
		return speedUpFactor;
	}

	public void setSpeedUpFactor(int speedUpFactor) {
		this.speedUpFactor = speedUpFactor;
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	/*
	 * este index e calculado com o index anterior e o tamanho total de frames na
	 * sprite. � aumentando com base no speed dado para nao haver aquele efeito de
	 * patinhar que alguns jogos t�m quando uso esse index para desenhar a imagem,
	 * faco o floor para eliminar a parte decimal, pois o array so tem posi��es
	 * inteiras e se o meu cavalo tiver speed de 0.3 significa que so na quarta
	 * frame e que vou desenhar a proxima imagem diferente!
	 */
	public void show() {
		indexF = (this.indexF) % this.len;
		p.image(animation.get(PApplet.floor(indexF)), x, y);
	}

	public void animateHorizontal() {
//		System.out.println("speed---->" + speed);
		indexF += speed.x;
		this.x += speed.x * speedUpFactor;
		if (x > p.width) {
			x = 0;
		}
		if (x < 0) {
			x = p.width;
		}

	}

	public void animateVertical() {
		// System.out.println("speed---->"+speed);
		indexF += speed.y;
		this.y += -speed.y * speedUpFactor;

		if (y > (p.height + 30)) {
			x = 0;
			this.removeMe = true;
		}

		if (y < -30) {
			y = p.height;
			this.removeMe = true;
		}

	}

	public void setCoords(float[] coords) {
		this.x = coords[0];
		this.y = coords[1];
	}

	public float[] getCoords() {
		return new float[] { this.x, this.y };
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public PVector getVel() {
		return speed;
	}

	public void setSpeed(PVector speed) {
		this.speed = speed;
	}

	public PVector getPos() {
		// TODO Auto-generated method stub
		return new PVector(getX(), getY());
	}

	public void setPos(PVector pos) {
		this.x = pos.x;
		this.y = pos.y;
	}

}
