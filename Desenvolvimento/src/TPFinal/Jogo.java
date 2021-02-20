package TPFinal;

import java.util.ArrayList;

import TPFinal.entidades.Inimigo;
import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import setup.InterfaceProcessingApp;

public class Jogo implements InterfaceProcessingApp {

	PImage spritesheet, spritesheetMC, spritesheetBone;
	JSONObject spritedata, spritedataMC, spritedataBone;

	ArrayList<PImage> animation, animationMC, animationBone; // vai ter cada frame

	private Body MCBody;
	private double[] window = { -10, 10, -10, 10 };

	private float[] viewport = { 0f, 0f, 1f, 1f };
	private SubPlot plt;

	SpriteDef MC; // MC = main Character
	private PVector MCStartingPos = new PVector(20, 400);
	private PVector MCStartingVel = new PVector();
	private float enemiesStartingVel = 0.5f;
	private float[] boneColisionBox = { 20, 30 };
	private float[] enemiesCollisionBox = { 60, 50 };
	private float attackVel = 1f;
	private boolean amIMoving = false;
	private int numberOfEnemies = 1;
	private String resources = "resources/";
	private SpriteDef mcRight, mcLeft;

	private ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();;
	private ArrayList<SpriteDef> enemies = new ArrayList<SpriteDef>();
	private ArrayList<Body> enemiesBody = new ArrayList<>();

	private ArrayList<SpriteDef> bones = new ArrayList<SpriteDef>();
	private ArrayList<Body> bonesBody;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);

		for (int i = 0; i < numberOfEnemies; i++) {
			Inimigo temp = new Inimigo(p, new PVector(50f, 75 * i), new PVector(enemiesStartingVel, 0));
			inimigos.add(temp);
			enemies.add(temp.getSpriteDef());
			enemiesBody.add(temp.getBody());
		}

		Animador mcAanimRight = new Animador(p, resources + "skeletonRun.json", resources + "skeleton.png",
				MCStartingPos, MCStartingVel);
		Animador mcAnimLeft = new Animador(p, resources + "skeletonLRun.json", resources + "skeleton.png",
				MCStartingPos, MCStartingVel);

		mcRight = mcAanimRight.getSpriteDef();
		mcLeft = mcAnimLeft.getSpriteDef();

		MC = mcRight;

		double[] temp = plt.getWorldCoord(MCStartingPos.x, MCStartingPos.y);
		PVector worldCoordToPlt = new PVector((float) temp[0], (float) temp[1]);

		MCBody = new Body(worldCoordToPlt, MCStartingVel, 1f, 1f, 1f, p.color(255, 128, 0));
		// iniciar a lista que vai ter os ossos:

		bones = new ArrayList<SpriteDef>();
		bonesBody = new ArrayList<Body>();
	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		p.background(127);

		MCBody.setVel(MCStartingVel);
		MCBody.move(dt * 15); // este * 15 e porque na classe da sprite multiplico por 10 para ser mais
								// r�pido, para acompanhar a anima��o ponho 15
		MCBody.display(p, plt, 50, 60);
		// fazer animacao do personagem principal
		if (MC != null) {
			MCBody.display(p, plt);
			MC.animateHorizontal();
			MC.show();

			mcLeft.setX(MC.getX());
			mcLeft.setY(MC.getY());
			mcRight.setX(MC.getX());
			mcRight.setY(MC.getY());
			makeBodyFollowAnimation(MCBody, MC);
		}

		// para remover os ossos da lista, nao posso usar for-each e tenho que come�ar
		// do fim! caso contr�rio tenho uma excep��o!
		for (int i = bones.size() - 1; i >= 0; --i) {
			SpriteDef boneActual = bones.get(i);
			if (boneActual.isRemoveMe()) {
				bones.remove(boneActual);

			}
		}

		int index = 0;
		// mostrar ossos, caso existam
		if (!bones.isEmpty()) {
			for (SpriteDef bone : bones) {
				Body currentBone = bonesBody.get(index);
				currentBone.display(p, plt, boneColisionBox[0], boneColisionBox[1]);
				currentBone.move(dt * 15);
				makeBodyFollowAnimationBone(currentBone, bone);
				bone.show();
				bone.animateVertical();
				// bone.setSpeedUpFactor(bone.getSpeedUpFactor()); // de forma a andar para
				// tras
			}
		}

		for (Body boneBody : bonesBody) {
			for (Body enemyBody : enemiesBody) {
				// System.out.println("entrei");
				if (boneBody.collision(enemyBody, plt)) {
					System.out.println("Colisao detectada!");
					enemyBody.setColor(255);
				}
			}
		}

	}

	public void makeBodyFollowAnimation(Body body, SpriteDef spriteDef) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX(), (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}

	public void makeBodyFollowAnimationBone(Body body, SpriteDef spriteDef) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX() - 5, (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		if (p.mouseButton == PConstants.LEFT) {
			loadShootBoneAnimation(p);
		}

	}

	@Override
	public void keyPressed(PApplet p) {
		if (!amIMoving && (p.key == 'd' || p.key == 'a')) {
			MC.setSpeed(new PVector(0.2f, 0f));
			if (p.key == 'd') {
				loadRunRightAnimation(p);
				amIMoving = true;
			}
			if (p.key == 'a') {
				loadRunLeftAnimation(p);
				amIMoving = true;
				// System.out.println("value da p.key d --->"+p.keyCode);
			}

		}
		if (p.key == ' ') {
			loadShootBoneAnimation(p);
			// System.out.println("value da p.key d --->"+p.keyCode);
		}

	}

	public void loadShootBoneAnimation(PApplet p) {
		animationBone = new ArrayList<PImage>();
		spritedataBone = p.loadJSONObject("resources/bone.json");
		spritesheetBone = p.loadImage("resources/boneR.png");
		JSONArray framesBone = spritedataBone.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesBone.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesBone.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informa��o que esta no JSON sobre cada
																// frame
			PImage imgBone = spritesheetBone.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			animationBone.add(imgBone);

		}
		// System.out.println("MC.getX--->"+MC.getX()+" MCgetY--->"+MC.getY());
		SpriteDef boneSpriteToAdd = new SpriteDef(animationBone, MC.getPos(), MC.getVel(), p);
		bones.add(boneSpriteToAdd);
		// System.out.println("2---->MC.getX--->"+MC.getX()+" MCgetY--->"+MC.getY());
		PVector worldCoordToPlt = new PVector(
				(float) plt.getWorldCoord(boneSpriteToAdd.getX(), boneSpriteToAdd.getY())[0],
				(float) plt.getWorldCoord(boneSpriteToAdd.getX(), boneSpriteToAdd.getY())[1]);
		Body boneBody = new Body(worldCoordToPlt, new PVector(attackVel, 0), 1f, boneColisionBox[0], boneColisionBox[1],
				p.color(255, 0, 0));
		bonesBody.add(boneBody);
	}

	public void loadRunLeftAnimation(PApplet p) {
		MC = mcLeft;
		if (MC.getSpeedUpFactor() > 0) {
			MC.setSpeedUpFactor(MC.getSpeedUpFactor() * -1);
		}
	}

	public void loadRunRightAnimation(PApplet p) {
		MC = mcRight;
		if (MC.getSpeedUpFactor() <= 0) {
			MC.setSpeedUpFactor(MC.getSpeedUpFactor() * -1);
		}
	}

	@Override
	public void keyReleased(PApplet p) {
		// TODO Auto-generated method stubdddd
		if (p.key == 'd') {
			MC.setSpeed(new PVector());
			amIMoving = false;
		}
		if (p.key == 'a') {
			MC.setSpeed(new PVector());
			amIMoving = false;
		}

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
