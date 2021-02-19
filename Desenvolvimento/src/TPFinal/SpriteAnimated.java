package TPFinal;

import java.util.ArrayList;

import graph.SubPlot;
import physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import setup.InterfaceProcessingApp;

public class SpriteAnimated implements InterfaceProcessingApp {

	PImage spritesheet, spritesheetMC, spritesheetBone;
	JSONObject spritedata, spritedataMC, spritedataBone;

	ArrayList<PImage> animation, animationMC, animationBone; // vai ter cada frame

	ArrayList<SpriteDef> enemies, bones; // conjunto de cavalos
	ArrayList<Body> enemiesBody, bonesBody;

	private Body MCBody;
	private double[] window = { -10, 10, -10, 10 };

	private float[] viewport = { 0f, 0f, 1f, 1f };
	private SubPlot plt;

	SpriteDef MC; // MC = main Character
	private float[] MCStartingPos = new float[] { 20, 400 };
	private float MCStartingVel = 0f;
	private float enemiesStartingVel = 0.5f;
	private int spriteH, spriteW;
	private boolean amIMoving = false;
	private int numberOfEnemies = 5;
	private boolean goingR = false, goingL = false;

	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		animation = new ArrayList<PImage>();
		enemies = new ArrayList<SpriteDef>();
		spritedata = p.loadJSONObject("resources/slimeIdle.json");
		spritesheet = p.loadImage("resources/slimeIdle.png");
		JSONArray frames = spritedata.getJSONArray("frames");
		for (int i = 0; i < frames.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = frames.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
																// (o numero, o x, y)
			// System.out.println("---->"+pos);
			// PImage img = spritesheet.get(pos.getInt("x"), pos.getInt("y"),
			// pos.getInt("w"), pos.getInt("h"));
			PImage img = spritesheet.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			animation.add(img);
		}

		for (int i = 0; i < numberOfEnemies; i++) {
			// vou instanciar o meu boneco principal na posição especificada. util ter isto
			// numa lista para depois adicionar multiplo meteoritos/objectos a cair
			enemies.add(new SpriteDef(animation, 50, i * 75, enemiesStartingVel, p));
		}

		plt = new SubPlot(window, viewport, p.width, p.height);

		enemiesBody = new ArrayList<Body>();
		for (int i = 0; i < numberOfEnemies; ++i) {
			PVector worldCoordToPlt = new PVector(
					(float) plt.getWorldCoord(enemies.get(i).getX(), enemies.get(i).getY())[0],
					(float) plt.getWorldCoord(enemies.get(i).getX(), enemies.get(i).getY())[1]);
			Body enemyBody = new Body(worldCoordToPlt, new PVector(enemies.get(i).getSpeed(), 0), 1, 1,
					p.color(255, 128, 0));
			enemiesBody.add(enemyBody);
			// System.out.println("adicionei um body!");

		}

		// para o personagem principal: (fazer load da primeira animação.
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject("resources/skeletonRun.json");
		spritesheetMC = p.loadImage("resources/skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MCStartingPos[0], MCStartingPos[1], MCStartingVel, p); // a MCStartingVel tem os
																								// valores iguais em
																								// ambas as componentes,
																								// nao faz diferença
																								// entr [0] e [1]

		PVector worldCoordToPlt = new PVector((float) plt.getWorldCoord(MCStartingPos)[0],
				(float) plt.getWorldCoord(MCStartingPos)[1]); // fazer a conversão de coordenadas do mundo (em pixeis)
																// para
		// as cordenadas do viewport que os rigid bodies utilizam !
		MCBody = new Body(worldCoordToPlt, new PVector(MCStartingVel, 0), 1, 1, p.color(255, 128, 0));
		// iniciar a lista que vai ter os ossos:
		bones = new ArrayList<SpriteDef>();

	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		p.background(127);

		MCBody.setVel(new PVector(MCStartingVel, 0));
		MCBody.move(dt * 15); // este * 15 e porque na classe da sprite multiplico por 10 para ser mais
								// rápido, para acompanhar a animação ponho 15
		MCBody.display(p, plt);
		/*
		 * if (plt.getPixelCoord(MCBody.getPos().x, MCBody.getPos().y)[0] > p.width) {
		 * System.out.println("saiu!!");
		 * 
		 * double[] arr = plt.getWorldCoord(MCBody.getPos().x, MCBody.getPos().y);
		 * PVector debug = new PVector((float) arr[0], (float) arr[1]);
		 * MCBody.setPos(debug);
		 * 
		 * }
		 */

		// fazer animação do personagem principal
		if (MC != null) {
			/*
			if(goingR) {
				loadRunRightAnimation(p);
			}
			else if(goingL) {
				loadRunLeftAnimation(p);
			}
			*/
			MC.show();
			MC.animate();
		}

		makeBodyFollowAnimation(MCBody, MC);

		// mostrar ossos, caso existam
		if (!bones.isEmpty()) {
			// System.out.println("bones não esta empty!");
			for (SpriteDef bone : bones) {
				bone.show();
				bone.animateVertical();
				// bone.setSpeedUpFactor(bone.getSpeedUpFactor()); // de forma a andar para trás
			}
		}

		// para remover os ossos da lista, nao posso usar for-each e tenho que começar
		// do fim! caso contrário tenho uma excepção!
		for (int i = bones.size() - 1; i >= 0; --i) {
			SpriteDef boneActual = bones.get(i);
			if (boneActual.isRemoveMe()) {
				bones.remove(boneActual);
			}
		}

		// corpos dos inimigos
		int index = 0;
		for (Body enemieBody : enemiesBody) {
			enemieBody.setVel(new PVector(enemiesStartingVel, 0));
			enemieBody.move(dt * 15);
			enemieBody.display(p, plt);
			makeBodyFollowAnimation(enemieBody, enemies.get(index));
			index++;

		}
		// animacoes dos inimigos
		for (SpriteDef enemie : enemies) {
			enemie.show();
			enemie.animate();
		}

	}

	public void makeBodyFollowAnimation(Body body, SpriteDef spriteDef) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX(), (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		if (!amIMoving && (p.key == 'd' || p.key == 'a')) {
			MC.setSpeed(0.2f);
			if (p.key == 'd') {
				goingR = true;
				System.out.println("carreguei no D");
				loadRunRightAnimation(p);
				amIMoving = true;
				// System.out.println("value da p.key d --->"+p.keyCode);
			} 
			if (p.key == 'a') {
				goingL = true;
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
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgBone = spritesheetBone.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationBone.add(imgBone);

		}
		bones.add(new SpriteDef(animationBone, MC.getX(), MC.getY(), 1f, p));
		//bones.add(new SpriteDef(animationBone, MC.getX()+10, MC.getY(), 1f, p));
		
		// PVector worldCoordToPlt = new PVector((float)
		// plt.getWorldCoord(enemies.get(i).getX(),enemies.get(i).getY())[0], (float)
		// plt.getWorldCoord(enemies.get(i).getX(),enemies.get(i).getY())[1]);
		// Body enemyBody = new Body(worldCoordToPlt, new
		// PVector(enemies.get(i).getSpeed(), 0), 1, 1, p.color(255, 128, 0));
		// enemiesBody.add(enemyBody);
	}

	public void loadRunLeftAnimation(PApplet p) {
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject("resources/skeletonLRun.json");
		spritesheetMC = p.loadImage("resources/skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MC.getX(), MC.getY(), MC.getSpeed(), p);
		MC.setSpeedUpFactor(MC.getSpeedUpFactor() * -1); // de forma a andar para trás
	}

	public void loadRunRightAnimation(PApplet p) {
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject("resources/skeletonRun.json");
		spritesheetMC = p.loadImage("resources/skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MC.getX(), MC.getY(), MC.getSpeed(), p);
		// este IF e para saber se ja estou a andar na direção certa ou se preciso de
		// inverter !
		if (MC.getSpeedUpFactor() > 0) {
			MC.setSpeedUpFactor(MC.getSpeedUpFactor());
		}

		else if (MC.getSpeedUpFactor() < 0) {
			MC.setSpeedUpFactor(MC.getSpeedUpFactor() * -1);
		}

	}

	@Override
	public void keyReleased(PApplet p) {
		// TODO Auto-generated method stubdddd
		if (p.key == 'd') {
			goingR = false;
			MC.setSpeed(0f);
			amIMoving = false;
		}
		if (p.key == 'a') {
			goingL = false;
			MC.setSpeed(0f);
			amIMoving = false;
		}

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}
