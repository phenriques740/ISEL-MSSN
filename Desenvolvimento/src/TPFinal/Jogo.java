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

public class Jogo implements InterfaceProcessingApp {

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
	private float[] MCStartingPos = new float[] { 20, 520 };
	private float MCStartingVel = 0f;
	private float enemiesStartingVel = 0.5f;
	private float[] boneColisionBox = {20, 30};
	private float attackVel = 1f;
	private int spriteH, spriteW;
	private boolean amIMoving = false;
	private int numberOfEnemies = 5;
	private boolean goingR = false, goingL = false;
	private JSONObject spritedataMCLeft;
	private PImage spritesheetMCLeft;
	private ArrayList<PImage> animationMCLeft;
	private float DT ;	//este DT e o dt minusculo que vem do processing setup. e a diferença entre frames

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
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informaï¿½ï¿½o que esta no JSON sobre cada
																// frame
																// (o numero, o x, y)
			// System.out.println("---->"+pos);
			// PImage img = spritesheet.get(pos.getInt("x"), pos.getInt("y"),
			// pos.getInt("w"), pos.getInt("h"));
			PImage img = spritesheet.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			animation.add(img);
		}

		for (int i = 0; i < numberOfEnemies; i++) {
			// vou instanciar o meu boneco principal na posiï¿½ï¿½o especificada. util ter
			// isto
			// numa lista para depois adicionar multiplo meteoritos/objectos a cair
			enemies.add(new SpriteDef(animation, 50, i * 75, enemiesStartingVel, p));
		}

		plt = new SubPlot(window, viewport, p.width, p.height);

		enemiesBody = new ArrayList<Body>();
		for (int i = 0; i < numberOfEnemies; ++i) {
			PVector worldCoordToPlt = new PVector(
					(float) plt.getWorldCoord(enemies.get(i).getX(), enemies.get(i).getY())[0],
					(float) plt.getWorldCoord(enemies.get(i).getX(), enemies.get(i).getY())[1]);
			Body enemyBody = new Body(worldCoordToPlt, new PVector(enemies.get(i).getSpeed(), 0), 1f, 1f, 1f,
					p.color(255, 128, 0));
			enemiesBody.add(enemyBody);
			// System.out.println("adicionei um body!");

		}

		// para o personagem principal: (fazer load da primeira animaï¿½ï¿½o.
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject("resources/skeletonRun.json");
		spritesheetMC = p.loadImage("resources/skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informaï¿½ï¿½o que esta no JSON sobre cada
																// frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MCStartingPos[0], MCStartingPos[1], MCStartingVel, p); // a MCStartingVel tem os
																								// valores iguais em
																								// ambas as componentes,
																								// nao faz diferenï¿½a
																								// entr [0] e [1]

		PVector worldCoordToPlt = new PVector((float) plt.getWorldCoord(MCStartingPos)[0],
				(float) plt.getWorldCoord(MCStartingPos)[1]); // fazer a conversï¿½o de coordenadas do mundo (em pixeis)
																// para
		// as cordenadas do viewport que os rigid bodies utilizam !
		MCBody = new Body(worldCoordToPlt, new PVector(MCStartingVel, 0), 1f, 1f, 1f, p.color(255, 128, 0));
		// iniciar a lista que vai ter os ossos:
		bones = new ArrayList<SpriteDef>();
		bonesBody = new ArrayList<Body>();

		animationMCLeft = new ArrayList<PImage>();
		spritedataMCLeft = p.loadJSONObject("resources/skeletonLRun.json");
		spritesheetMCLeft = p.loadImage("resources/skeleton.png");

		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject("resources/skeletonRun.json");
		spritesheetMC = p.loadImage("resources/skeleton.png");

	}

	@Override
	public void draw(PApplet p, float dt) {
		DT = dt;
		// TODO Auto-generated method stub
		p.background(127);

		MCBody.setVel(new PVector(MCStartingVel, 0));
		MCBody.move(dt * 15); // este * 15 e porque na classe da sprite multiplico por 10 para ser mais
								// rï¿½pido, para acompanhar a animaï¿½ï¿½o ponho 15
		MCBody.display(p, plt, 50, 60);
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

		// fazer animacao do personagem principal
		if (MC != null) {
			MC.show();
			MC.animate();
			makeBodyFollowAnimation(MCBody, MC);
		}

		// para remover os ossos da lista, nao posso usar for-each e tenho que comeï¿½ar
		// do fim! caso contrï¿½rio tenho uma excepï¿½ï¿½o!
		for (int i = bones.size() - 1; i >= 0; --i) {
			SpriteDef boneActual = bones.get(i);
			if (boneActual.isRemoveMe()) {
				bones.remove(boneActual);
				bonesBody.get(i);
			}
		}

		int index = 0;
		// mostrar ossos, caso existam
		if (!bones.isEmpty()) {
			// System.out.println("bones nï¿½o esta empty!");
			for (SpriteDef bone : bones) {
				Body currentBone = bonesBody.get(index);
				currentBone.display(p, plt, boneColisionBox[0], boneColisionBox[1] );
				currentBone.move(dt*15);
				makeBodyFollowAnimationBone(currentBone, bone);
				bone.show();
				bone.animateVertical();
				// bone.setSpeedUpFactor(bone.getSpeedUpFactor()); // de forma a andar para
				// trï¿½s
			}
		}

		/*
		int index = 0;   
		for (Body boneBody : bonesBody) {
			if(bones.size()>0 && !bones.isEmpty() && bones.get(index) != null ) {
				//System.out.println("size---->"+bones.size());
				boneBody.setVel(new PVector(attackVel, 0));
				boneBody.move(dt * 15);
				boneBody.display(p, plt, boneColisionBox[0], boneColisionBox[1]);
				//System.out.println("index---->"+index);
				makeBodyFollowAnimation(boneBody, bones.get(index));
				index++;
			}
			index = 0;	//tenho que por este index a zero, senão quando o voltar a percorrer a lista ja vou estar em index = 1, apesar de so ter 1 osso (que é supsto ser index[0] )
		}
		*/

		// corpos dos inimigos
		index = 0;
		for (Body enemieBody : enemiesBody) {
			enemieBody.setVel(new PVector(enemiesStartingVel, 0));
			enemieBody.move(dt * 15);
			enemieBody.display(p, plt, 60, 50);
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
	
	public void makeBodyFollowAnimationBone(Body body, SpriteDef spriteDef) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX()-5, (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
        if(p.mouseButton == p.LEFT) {
        	loadShootBoneAnimation(p);
		}

	}

	@Override
	public void keyPressed(PApplet p) {
		if (!amIMoving && (p.key == 'd' || p.key == 'a')) {
			MC.setSpeed(0.2f);
			if (p.key == 'd') {
				goingR = true;
				loadRunRightAnimation(p);
				amIMoving = true;
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
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informaï¿½ï¿½o que esta no JSON sobre cada
																// frame
			PImage imgBone = spritesheetBone.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationBone.add(imgBone);

		}
		//System.out.println("MC.getX--->"+MC.getX()+" MCgetY--->"+MC.getY());
		SpriteDef boneSpriteToAdd = new SpriteDef(animationBone, MC.getX(), MC.getY(), attackVel, p);
		bones.add(boneSpriteToAdd);
		//System.out.println("2---->MC.getX--->"+MC.getX()+" MCgetY--->"+MC.getY());
		PVector worldCoordToPlt = new PVector((float) plt.getWorldCoord(boneSpriteToAdd.getX(), boneSpriteToAdd.getY())[0], (float) plt.getWorldCoord(boneSpriteToAdd.getX(), boneSpriteToAdd.getY())[1]);
		Body boneBody = new Body(worldCoordToPlt, new PVector(attackVel, 0), 1f, boneColisionBox[0], boneColisionBox[1], p.color(255, 0, 0));
		bonesBody.add(boneBody);
	}

	public void loadRunLeftAnimation(PApplet p) {
		JSONArray framesMC = spritedataMCLeft.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informaï¿½ï¿½o que esta no JSON sobre cada
																// frame
			PImage imgMC = spritesheetMCLeft.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMCLeft.add(imgMC);
		}
		MC = new SpriteDef(animationMCLeft, MC.getX(), MC.getY(), MC.getSpeed(), p);
		MC.setSpeedUpFactor(MC.getSpeedUpFactor() * -1); // de forma a andar para trï¿½s
	}

	public void loadRunRightAnimation(PApplet p) {
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		// System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informaï¿½ï¿½o que esta no JSON sobre cada
																// frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MC.getX(), MC.getY(), MC.getSpeed(), p);
		// este IF e para saber se ja estou a andar na direï¿½ï¿½o certa ou se preciso
		// de
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
