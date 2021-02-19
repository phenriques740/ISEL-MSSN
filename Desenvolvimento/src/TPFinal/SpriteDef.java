package TPFinal;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Gere a classe e fornece definicoes sobre o sprite
 * 
 * @author vasco
 *
 */
public class SpriteDef {

	float x, y, w, speed, indexF;
	int len;
	ArrayList<PImage> animation;
	private PApplet p;
	private int speedUpFactor = 10;
	private boolean removeMe = false;

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

	public SpriteDef(ArrayList<PImage> animation, float x, float y, float speed, PApplet p) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.animation = animation;
		this.w = animation.get(0).width;
		this.len = animation.size();
		// System.out.println("len size----->"+len);
		this.removeMe = false;
		// System.out.println("len size----->"+len);
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

	public void animate() {
		// System.out.println("speed---->"+speed);
		indexF += speed;
		this.x += speed * speedUpFactor;
		if (x > p.width) {
			x = 0;
		}
		if (x < 0) {
			x = p.width;
		}

	}

	public void animateVertical() {
		// System.out.println("speed---->"+speed);
		indexF += speed;
		this.y += -speed * speedUpFactor;

		if (y > (p.height + 30)) {
			x = 0;
			this.removeMe = true;
		}

		if (y < -30) {
			y = p.height;
			this.removeMe = true;
		}

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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}