package TPFinal;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
public class SpriteDef {

	float x, y, w, speed, indexF;
	int len;
	ArrayList<PImage> animation;
	private PApplet p;

	public SpriteDef(ArrayList<PImage> animation, float x, float y, float speed, PApplet p) {
		this.p = p;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.animation = animation;
		this.w = animation.get(0).width;
		this.len = animation.size();
		System.out.println("len size----->"+len);
	}

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public void show() {
		indexF = (this.indexF) % this.len;
		//este index e calculado com o index anterior e o tamanho total de frames na sprite. é aumentando com base no speed dado para nao haver aquele efeito de patinhar que alguns jogos têm
		//quando uso esse index para desenhar a imagem, faco o floor para eliminar a parte decimal, pois o array so tem posições inteiras e se o meu cavalo tiver speed de 0.3 significa que 
		//so na quarta frame e que vou desenhar a proxima imagem diferente!
		//System.out.println("index------->"+indexF);
		//System.out.println("indexF---->"+indexF+" floor desse resultado--->"+PApplet.floor(indexF));
		p.image(animation.get(PApplet.floor(indexF)), x, y);
	}
	
	public void animate() {
		//System.out.println("speed---->"+speed);
		indexF += speed;
	    this.x += speed * 10;

	    if (x > p.width) {
	      x = -w;
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

	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	
	
	
	

}











