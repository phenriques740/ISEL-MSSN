package TPFinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import TPFinal.entidades.Inimigo;
import TPFinal.entidades.Osso;
import graph.SubPlot;
import particleSystems.ParticleSystem;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;
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
	private mainScreen ms;
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
	
	private int[] startGameRect;
	private int[] showTipsRect;
	private Audio mainMenuMusic, fightMusic, boneAttackMusic,gameOverMusic;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);

		for (int i = 0; i < numberOfEnemies; i++) {
			Inimigo temp = new Inimigo(p, new PVector(50f, 75 * i), new PVector(enemiesStartingVel, 0),
					enemiesCollisionBox[0], enemiesCollisionBox[1]);
			inimigos.add(temp);
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

		ossos = new ArrayList<Osso>();
		pss = new ArrayList<ParticleSystem>();

		// mainMenu:
		ms = new mainScreen(p, resources + "mainScreen.json", resources + "mainScreen.png", "Play", "How to Play", resources + "aKey.json", resources + "aKey.png", resources+"dKey.json", resources+"dKey.png", 
								resources + "LMB.json", resources + "LMB.png", resources + "gameOver.json", resources+"gameOver.png");
		startGameRect = ms.getStartGameRect();
		showTipsRect = ms.getShowTipsRect();

		//som:
		// resources + "mainMenuMusic.wav"
		try {
			mainMenuMusic = new Audio(resources + "mainMenuMusic.wav");
			fightMusic = new Audio(resources + "normalMusic.wav");
			gameOverMusic = new Audio(resources+"gameOverMusic.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startMenuMusic() {
		if( fightMusic.isPlaying()) {
			fightMusic.stopAudio();
		}
		if(gameOverMusic.isPlaying()) {
			gameOverMusic.stopAudio();
		}
		if(!mainMenuMusic.isPlaying()) {
			try {
				mainMenuMusic = new Audio(resources + "mainMenuMusic.wav");
				mainMenuMusic.startAudio();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startFightMusic() {
		if(mainMenuMusic.isPlaying()) {
			mainMenuMusic.stopAudio();
		}
		if(gameOverMusic.isPlaying()) {
			gameOverMusic.stopAudio();
		}
		if(!fightMusic.isPlaying()) {		
			try {
				fightMusic = new Audio(resources + "normalMusic.wav");
				fightMusic.startAudio();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void boneSoundEffect() throws UnsupportedAudioFileException {
		try {
			boneAttackMusic = new Audio(resources + "boneAttack.wav");
			boneAttackMusic.startAudio();
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startGameOverMusic() {
		if(!gameOverMusic.isPlaying()) {
			try {
				gameOverMusic = new Audio(resources+"gameOverMusic.wav");
				gameOverMusic.startAudio();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void draw(PApplet p, float dt) {
		if (!ms.isStartGame()) {
			startMenuMusic();
			ms.drawMenu(p);
		}
		
		else if(ms.isShowGameOver()) {
			fightMusic.stopAudio();
			mainMenuMusic.stopAudio();
			ms.gameOverScreen();
			startGameOverMusic();
		}
		
		else {
			startFightMusic();
			p.background(127);

			MCBody.setVel(MCStartingVel);
			MCBody.move(dt * 15);
			// este * 15 e porque na classe da sprite multiplico por 10 para ser mais
			// rapido, para acompanhar a animacao ponho 15
			// fazer animacao do personagem principal
			if (MC != null) {
				MCBody.display(p, plt);
				MC.animateHorizontal();
				MC.show();

				mcLeft.setPos(MC.getPos());
				mcRight.setPos(MC.getPos());
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
				for (Osso osso : ossos) {
					osso.draw(p, plt, true, dt);
				}
			}

			for (Inimigo inimigo : inimigos) {
				inimigo.draw(p, plt, true, dt);
			}

			for (Osso osso : ossos) {
				for (Inimigo inimigo : inimigos) {
					if (osso.getBody().collision(inimigo.getBody(), plt)) {
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

			// remover os particle Systems se ja tiver passado o seu tempo:
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
					 * enemies.remove(enemyActual); enemiesBody.remove(i); // added
					 */
					inimigos.remove(currentEnemy);
				}
			}

			for (int i = ossos.size() - 1; i >= 0; --i) {
				Osso currentOsso = ossos.get(i);
				if (currentOsso.isFlagRemove()) {
					ossos.remove(currentOsso);
				}
			}
			
		}

	}

	private void makeBodyFollowAnimation(Body body, SpriteDef spriteDef, SubPlot plt) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX(), (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}

	@Override
	public void mousePressed(PApplet p) {
		if (p.mouseButton == PConstants.LEFT) {
			loadShootBoneAnimation(p);
		}
		
		if( isInsideRect(p.mouseX, p.mouseY, startGameRect[0], startGameRect[2], startGameRect[1], startGameRect[3] ) ) {
			ms.setStartGame(true);
		}
		
		if( isInsideRect(p.mouseX, p.mouseY, showTipsRect[0], showTipsRect[2], showTipsRect[1], showTipsRect[3] ) ) {
			ms.setShowTips(true);
		}

	}
	
	public boolean isInsideRect(int mouseX, int mouseY, int rectX, int rectWidth, int rectY, int rectHeight) {
		if (mouseX > rectX && mouseX < rectX +rectWidth && mouseY > rectY && mouseY < rectY +rectHeight){
			return true;
		}
		return false;
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
		if (p.key == 'b') {
			System.out.println("carreguei no b!");
			ms.setShowTips(false);
			ms.setStartGame(false);
		}
		
		if (p.key == 't') {
			//apenas para testar o game over screen:
			ms.setShowGameOver(true);
		}

	}
	

	public void loadShootBoneAnimation(PApplet p) {
		Osso osso = new Osso(p, MC.getPos(), new PVector(0, attackVel), 10, 20);
		ossos.add(osso);
		if( ms.isStartGame() && !ms.isShowGameOver() ) {
			try {
				boneSoundEffect();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
