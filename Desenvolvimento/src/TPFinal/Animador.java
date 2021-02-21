package TPFinal;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Animador {
	/**
	 * Construtor da classe gestor de animacoes
	 * 
	 * Cria um objeto capaz de efetuar animacoes.
	 * 
	 * @param pathToSprite
	 * @param pathToSpriteImage
	 */
	private final PApplet p;
	private ArrayList<PImage> animation;
	private JSONObject spritedata;
	private PImage spritesheet;
	private SpriteDef animatedSprites;
	private JSONArray frames;

	public ArrayList<PImage> getAnimation() {
		return animation;
	}

	/**
	 * Constructor de animador. Parte integrante de entidade
	 * 
	 * @param p
	 * @param pathToSpriteData  ficheiro JSON que tem as informacoes do sprite
	 * @param pathToSpriteSheet imagem que pode ser dividida em imagens menores que
	 *                          em sequencia formam uma animacao
	 * @param startingPos       posicao inicial do objeto em coordenadas absolutas
	 *                          <b>(pixeis)</b> ao mundo
	 * @param startingVel       velocidade incial associada ao objeto
	 */
	public Animador(PApplet p, String pathToSpriteData, String pathToSpriteSheet, PVector startingPos,
			PVector startingVel) {
		this.p = p;
		animation = new ArrayList<PImage>();
		spritedata = p.loadJSONObject(pathToSpriteData);
		spritesheet = p.loadImage(pathToSpriteSheet);
		frames = spritedata.getJSONArray("frames");

		for (int i = 0; i < frames.size(); i++) {
			JSONObject frame = frames.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position");
			//System.out.println("pos---->"+pos);
			// Corta a imagem JPEG para obter a informacao de 1 frame
			PImage img = spritesheet.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			animation.add(img);
		}
		animatedSprites = new SpriteDef(animation, startingPos, startingVel, p);
	}

	public Animador(PApplet p, String pathToSpriteData, String pathToSpriteSheet, float[] startingPos) {
		this(p, pathToSpriteData, pathToSpriteSheet, new PVector(startingPos[0], startingPos[1]), new PVector());
	}

	public Animador(PApplet p, String pathToSpriteData, String pathToSpriteSheet, float startingX, float startingY,
			PVector startingVel) {
		this(p, pathToSpriteData, pathToSpriteSheet, new PVector(startingX, startingY), startingVel);
	}

	public Animador(PApplet p, String pathToSpriteData, String pathToSpriteSheet, float startingX, float startingY) {
		this(p, pathToSpriteData, pathToSpriteSheet, new PVector(startingX, startingY), new PVector());
	}

	/**
	 * Dado um indice, devolve as dimensoes da frame
	 * 
	 * @param index
	 * @return Devolve as dimensoes da frame em pixeis
	 */
	public int[] getFrameSize(int index) {
		PImage frame = animation.get(index);
		return new int[] { frame.width, frame.height };
	}

	public SpriteDef getSpriteDef() {
		return animatedSprites;
	}

}
