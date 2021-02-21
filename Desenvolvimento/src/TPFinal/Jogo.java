package TPFinal;

import java.util.ArrayList;
import java.util.Iterator;

import TPFinal.entidades.Inimigo;
import TPFinal.entidades.Osso;
import graph.SubPlot;
import particleSystems.ParticleSystem;
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

	SpriteDef MC, bone; // MC = main Character
	private float enemiesStartingVel = 0.5f;
	private float[] enemiesCollisionBox = { 60, 50 };
	private PVector MCStartingPos = new PVector(20, 520);
	private PVector MCStartingVel = new PVector();
	private float attackVel = 1f;
	private boolean amIMoving = false;
	private int numberOfEnemies = 5;
	private String resources = "resources/";
	private SpriteDef mcRight, mcLeft, boneSprite;

	private ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();
	private ArrayList<Osso> ossos = new ArrayList<Osso>();
	private ArrayList<SpriteDef> enemies = new ArrayList<SpriteDef>();
	private ArrayList<Body> enemiesBody = new ArrayList<>();

	private ArrayList<ParticleSystem> pss;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);

		for (int i = 0; i < numberOfEnemies; i++) {
			Inimigo temp = new Inimigo(p, new PVector(50f, 75 * i), new PVector(enemiesStartingVel, 0), enemiesCollisionBox[0], enemiesCollisionBox[1]);
			inimigos.add(temp);
			
			enemies.add(temp.getSpriteDef());
			enemiesBody.add(temp.getBody());
			
		}

		Animador mcAanimRight = new Animador(p, resources + "skeletonRun.json", resources + "skeleton.png",
				MCStartingPos, MCStartingVel);
		Animador mcAnimLeft = new Animador(p, resources + "skeletonLRun.json", resources + "skeleton.png",
				MCStartingPos, MCStartingVel);
		/*
		 * animationBone = new ArrayList<PImage>(); spritedataBone =
		 * p.loadJSONObject("resources/bone.json"); spritesheetBone =
		 * p.loadImage("resources/boneR.png"); JSONArray framesBone =
		 * spritedataBone.getJSONArray("frames"); //
		 * System.out.println("framesMC size---->"+framesMC.size() ); for (int i = 0; i
		 * < framesBone.size(); i++) { //
		 * System.out.println("frames size --->"+frames.size()); JSONObject frame =
		 * framesBone.getJSONObject(i); JSONObject pos =
		 * frame.getJSONObject("position"); // tem toda a informaï¿½ï¿½o que esta no
		 * JSON sobre cada // frame PImage imgBone =
		 * spritesheetBone.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"),
		 * pos.getInt("h")); animationBone.add(imgBone);
		 * 
		 * }
		 */

		mcRight = mcAanimRight.getSpriteDef();
		mcLeft = mcAnimLeft.getSpriteDef();

		MC = mcRight;

		double[] temp = plt.getWorldCoord(MCStartingPos.x, MCStartingPos.y);
		PVector worldCoordToPlt = new PVector((float) temp[0], (float) temp[1]);

		MCBody = new Body(worldCoordToPlt, MCStartingVel, 1f, 1f, 1f, p.color(255, 128, 0));
		// iniciar a lista que vai ter os ossos:

		ossos = new ArrayList<Osso>();
		pss = new ArrayList<ParticleSystem>();
	}

	@Override
	public void draw(PApplet p, float dt) {
//		MC.draw();
//		for (Inimigo inimigo : inimigos) {
//			inimigo.draw();
//		}

		// TODO Auto-generated method stub
		p.background(127);

		MCBody.setVel(MCStartingVel);
		MCBody.move(dt * 15); // este * 15 e porque na classe da sprite multiplico por 10 para ser mais
								// r�pido, para acompanhar a anima��o ponho 15
		// fazer animacao do personagem principal
		if (MC != null) {
			MCBody.display(p, plt);
			MC.animateHorizontal();
			MC.show();

			mcLeft.setX(MC.getX());
			mcLeft.setY(MC.getY());
			mcRight.setX(MC.getX());
			mcRight.setY(MC.getY());
			makeBodyFollowAnimation(MCBody, MC, plt);
		}

		// para remover os ossos da lista, nao posso usar for-each e tenho que come�ar
		// do fim! caso contr�rio tenho uma excep��o!
		for (int i = inimigos.size() - 1; i >= 0; --i) {
			
			Inimigo inimigo = inimigos.get(i);
			if (inimigo.isFlagRemove()) {
				inimigos.remove(inimigo);
				
			}
		}

		// mostrar ossos, caso existam
		if (!ossos.isEmpty()) {
			//System.out.println("Entrei no nao empty!");
			for (Osso osso : ossos) {
				osso.draw(p, plt, true, dt);
			}
		}
		
		for(Inimigo inimigo : inimigos) {
			inimigo.draw(p, plt, true, dt);;
			
		}
		

		for (Osso osso : ossos) {
			for (Inimigo inimigo : inimigos) {
				// System.out.println("entrei");
				if (osso.getBody().collision(inimigo.getBody(), plt)) {
					// System.out.println("Colisao detectada!");
					// enemyBody.setColor(255);

					ParticleSystem ps = inimigo.getBody().explodeMe();
					pss.add(ps);

					osso.setFlagRemove(true); // marcar o osso para ser removido no proximo draw. //se retirar isto
													// tenho piercing bones !
					inimigo.setFlagRemove(true);// inimigop tamb�m tem que ser removido no proximo draw!

				}
			}
		}

		// mostrar os particle Systems
		for (ParticleSystem ps : pss) {
			ps.move(dt);
			ps.displayParticleSystem(p, plt); // usar o displayParticle de ParticleSystem!
		}

		// remover os particle Systems se j� tiver passado o seu tempo:
		for (int i = pss.size() - 1; i >= 0; i--) {
			ParticleSystem psActual = pss.get(i);
			if (psActual.isDead()) {
				pss.remove(psActual);
			}
		}

		// para remover os ossos da lista, nao posso usar for-each e tenho que come�ar
		// do fim! caso contr�rio tenho uma excep��o!
		for (int i = inimigos.size() - 1; i >= 0; --i) {
			Inimigo currentEnemy = inimigos.get(i);
			if (currentEnemy.isFlagRemove()) {
				/*
				enemies.remove(enemyActual);
				enemiesBody.remove(i); // added
				*/
				inimigos.remove(currentEnemy);
			}
		}
		
		for (int i = ossos.size() - 1; i >= 0; --i) {
			Osso currentOsso = ossos.get(i);
			if (currentOsso.isFlagRemove()) {
				/*
				enemies.remove(enemyActual);
				enemiesBody.remove(i); // added
				*/
				ossos.remove(currentOsso);
			}
		}

	}
	
	public void makeBodyFollowAnimation(Body body, SpriteDef spriteDef, SubPlot plt) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX(), (float) spriteDef.getY());
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
		// System.out.println("MC.getX--->"+MC.getX()+" MCgetY--->"+MC.getY());
		Osso osso = new Osso(p, MC.getPos(), new PVector(0,attackVel), 10, 20);
		osso.criarAnimador(p);
		osso.criarBody(p);
		ossos.add(osso);
		// System.out.println("Bones body size--->"+bonesBody.size());
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
