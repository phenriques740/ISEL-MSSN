package TPFinal;

import java.io.IOException;

import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import TPFinal.Boids.Boid;
import TPFinal.Boids.Eye;
import TPFinal.Boids.Patrol;
import TPFinal.entidades.BackGround;
import TPFinal.entidades.Bomb;
import TPFinal.entidades.Boss;
import TPFinal.entidades.Inimigo;
import TPFinal.entidades.Osso;
import TPFinal.entidades.PowerUp;
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
	private PVector[] enemiesStartingVels = new PVector[] { new PVector(0.5f, 0), new PVector(1.0f, 0),
			new PVector(0.75f, 0) };
	private PVector[] enemiesStartingPos = new PVector[] { new PVector(50f, 50),
			// Fica aqui com o 0 no eixo dos Y porque depois quero meter a variar no
			// construtor
			new PVector(100f, 50), new PVector(150f, 50) };
	private float[] enemiesCollisionBox = { 60, 50 };
	private float[] bossCollisionBox = { 100, 150 };
	private float[] MCCollisionBox = { 50, 70 };
	private float[] bombsCollisionBox = {40, 50};
	private PVector MCStartingPos = new PVector(20, 520);
	private PVector MCStartingVel = new PVector();
	private float attackVel = 1f;
	private boolean amIMoving = false;
	private int numberOfEnemies = 5;
	private String resources = "resources/";
	private SpriteDef mcRight, mcLeft, backGroundSprite;

	private ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();
	private ArrayList<Osso> ossos = new ArrayList<Osso>();
	private ArrayList<PowerUp> powerUps = new ArrayList<>();
	private ArrayList<ParticleSystem> pss;
	private ArrayList<Bomb> bombs;
	
	private boolean bossFight = false;
	
	private int bossHP = 100;
	private int MCHealth = 3;
	private int[] startGameRect;
	private int[] showTipsRect;
	private int[] gameOverRetryAgainButton;
	private int[] showTipsRectBackButton;
	private Audio mainMenuMusic, fightMusic, boneAttackMusic, gameOverMusic;
	private boolean debugBoxes = false;

	// variavais que sao afectadas por powerup:
	private int numberOfOssosPerPress = 1;
	private int spaceBetWeenBoneSpawns = 10;
	private float timeBetweenShots = 300f;
	private long lastTimeIShot;
	private long lastTimeBossShot;
	// a vida tambem pode ser afectada por power ups apesar de nao estar aqui!

	private float powerUpDropChance = 0.1f;

	private int HPEasyEnemies = 1;
	private int HPMediumEnemies = 3;
	private int HPHardEnemies = 5;
	private int numberOfTimesISpawnedEnemies = 0;
	
	//definicao do boss e dos seus WP:
	private ArrayList<Body> Waypoints;
	private Boid boss;

	private String lastPowerupReceived = "";
	
	private BackGround backGround;

	@Override
	public void setup(PApplet p) {
		lastTimeIShot = System.nanoTime();
		lastTimeBossShot = System.nanoTime();
		plt = new SubPlot(window, viewport, p.width, p.height);

		Animador mcAanimRight = new Animador(p, resources + "skeletonRun.json", resources + "skeleton.png",
				MCStartingPos, MCStartingVel);
		Animador mcAnimLeft = new Animador(p, resources + "skeletonLRun.json", resources + "skeleton.png",
				MCStartingPos, MCStartingVel);
		mcRight = mcAanimRight.getSpriteDef();
		mcRight.setSpeedUpFactor(15);
		mcLeft = mcAnimLeft.getSpriteDef();
		mcLeft.setSpeedUpFactor(-15);

		MC = mcRight;
		
		//background:
		backGround = new BackGround(p,  new PVector(), new PVector(),0,0);

		double[] temp = plt.getWorldCoord(MCStartingPos.x, MCStartingPos.y);
		PVector worldCoordToPlt = new PVector((float) temp[0], (float) temp[1]);

		MCBody = new Body(worldCoordToPlt, MCStartingVel, 1f, MCCollisionBox[0], MCCollisionBox[1],
				p.color(255, 128, 0));
		// iniciar a lista que vai ter os ossos:

		ossos = new ArrayList<Osso>();
		pss = new ArrayList<ParticleSystem>();
		powerUps = new ArrayList<PowerUp>();
		bombs = new ArrayList<Bomb>();

		// mainMenu:
		ms = new mainScreen(p, resources + "mainScreen.json", resources + "mainScreen.png", "Play", "How to Play",
				resources + "aKey.json", resources + "aKey.png", resources + "dKey.json", resources + "dKey.png",
				resources + "LMB.json", resources + "LMB.png", resources + "gameOver.json", resources + "gameOver.png");
		startGameRect = ms.getStartGameRect();
		showTipsRect = ms.getShowTipsRect();
		gameOverRetryAgainButton = ms.getGameOverRetryAgainButton();
		showTipsRectBackButton = ms.getShowTipsBackButton();

		// som:
		// resources + "mainMenuMusic.wav"
		try {
			mainMenuMusic = new Audio(resources + "mainMenuMusic.wav");
			fightMusic = new Audio(resources + "normalMusic.wav");
			gameOverMusic = new Audio(resources + "gameOverMusic.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public void startMenuMusic() {
		if (fightMusic.isPlaying()) {
			fightMusic.stopAudio();
		}
		if (gameOverMusic.isPlaying()) {
			gameOverMusic.stopAudio();
		}
		if (!mainMenuMusic.isPlaying()) {
			try {
				mainMenuMusic = new Audio(resources + "mainMenuMusic.wav");
				mainMenuMusic.startAudio();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void spawnStrongerEnemies(PApplet p, int HP) {
		// System.out.println("fiz o spawStringer");
		int min = 1, max = 10;
		int chosenColor = 0;
		if(HP <= 1) {
			chosenColor = p.color(0,255,0);
		}
		if(HP > 1 && HP <= 3) {
			chosenColor = p.color(0,0,255);
		}
		if(HP > 3 && HP <= 5) {
			chosenColor = p.color(255,0,0);
		}
			
		// int min = 1, max = 1;
		int enemiesToSpawn = (int) ((Math.random() * (max - min)) + min);
		for (int i = 0; i < enemiesToSpawn; i++) {
			PVector startPos = enemiesStartingPos[PApplet.floor(p.random(enemiesStartingPos.length * 1.0f))];
			for (int j = 0; j < 3; j++) {
				// for (int j = 0; j < 1; j++) {
				PVector startVel = enemiesStartingVels[PApplet.floor(p.random(enemiesStartingVels.length * 1.0f))];
				Inimigo temp = new Inimigo(p, PVector.add(startPos, new PVector(100 * j, 40f * i)), startVel,
						enemiesCollisionBox[0], enemiesCollisionBox[1], HP, chosenColor);
				inimigos.add(temp);
			}
		}
	}

	public void spawnBoss(PApplet p) {
		/*
		PVector startPos = enemiesStartingPos[PApplet.floor(p.random(enemiesStartingPos.length * 1.0f))];
		PVector startVel = enemiesStartingVels[PApplet.floor(p.random(enemiesStartingVels.length * 1.0f))];
		boss = new Boss(p, PVector.add(startPos, new PVector(100, 40f)), startVel, bossCollisionBox[0],
				bossCollisionBox[1], bossHP);
		 */
		
		// tratar dos WP e do Eye do Boid Boss
		MCHealth = 5;
		boss = new Boid(p, new PVector(), bossCollisionBox[0], bossCollisionBox[1], bossHP , p.color(128, 128, 128));
		Waypoints = new ArrayList<Body>();
		Body bodyWP1 = new Body(new PVector(-8, 8), new PVector(), 1f, 20f, 30f, p.color(255, 0, 0));
		Body bodyWP2 = new Body(new PVector(6, 4), new PVector(), 1f, 20f, 30f, p.color(255, 0, 0));
		Body bodyWP3 = new Body(new PVector(-8, 0), new PVector(), 1f, 20f, 30f, p.color(255, 0, 0));
		Body bodyWP4 = new Body(new PVector(6, 8), new PVector(), 1f, 20f, 30f, p.color(255, 0, 0));
		Waypoints.add(bodyWP1);
		Waypoints.add(bodyWP2);
		Waypoints.add(bodyWP3);
		Waypoints.add(bodyWP4);
		boss.addBehavior(new Patrol(1f, Waypoints));
		Eye eye = new Eye(boss, Waypoints);
		boss.setEye(eye);
		//System.out.println("Boss Spawn!");
		
	}

	public void drawAccurateNumberOfHearts(PApplet p) {
		int MCHealthCopy = MCHealth;
		// System.out.println("vida----->"+MCHealth);
		for (int i = MCHealthCopy; i > 0; i--) {
			// System.out.println("---->"+MCHealthCopy);
			Animador heart = new Animador(p, resources + "heart.json", resources + "heart.png", 60 * i, 10);
			SpriteDef heartSprite = heart.getSpriteDef();
			heartSprite.show();
		}
		/*
		 * Animador heart1 = new Animador(p, resources + "heart.json", resources +
		 * "heart.png", 10, 10); SpriteDef heartSprite = heart1.getSpriteDef();
		 * heartSprite.show();
		 */
	}

	public void showLastPowerUpReceived(PApplet p) {
		ms.drawText(lastPowerupReceived, 250 + (MCHealth + 1) * 30, 20);
	}

	public void startFightMusic() {
		if (mainMenuMusic.isPlaying()) {
			mainMenuMusic.stopAudio();
		}
		if (gameOverMusic.isPlaying()) {
			gameOverMusic.stopAudio();
		}
		if (!fightMusic.isPlaying()) {
			try {
				fightMusic = new Audio(resources + "normalMusic.wav");
				fightMusic.startAudio();
				fightMusic.regulateVolume(-6);
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
			boneAttackMusic.regulateVolume(-10);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startGameOverMusic() {
		if (!gameOverMusic.isPlaying()) {
			try {
				gameOverMusic = new Audio(resources + "gameOverMusic.wav");
				gameOverMusic.startAudio();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void resetEverything() {
		// no caso de haver gameOver, tenho que fazer reset a tudo!
		MCHealth = 3;
		// remover todos os inimigos:
		inimigos.clear();
		// remover todos os ossos:
		ossos.clear();
		// remover powerUps:
		powerUps.clear();
		// lkimpar bnombas:
		bombs.clear();
		// reset aos powerUps:
		numberOfOssosPerPress = 1;
		// limpar as explosoes
		pss.clear();
		//
		timeBetweenShots = 300f;
		spaceBetWeenBoneSpawns = 10;
		lastPowerupReceived = "";
		numberOfTimesISpawnedEnemies = 0;
		bossFight = false;

	}
	

	@Override
	public void draw(PApplet p, float dt) {
		if (!ms.isStartGame()) {
			startMenuMusic();
			ms.drawMenu(p);
		}

		else if (ms.isShowGameOver()) {
			// System.out.println("Game over do jogo draw--->");
			fightMusic.stopAudio();
			mainMenuMusic.stopAudio();
			ms.gameOverScreen();
			startGameOverMusic();
		}

		else {
			startFightMusic();
			p.background(128);
			
			backGround.draw(p, plt, false, dt);
			
			if(debugBoxes && Waypoints!=null) {
				for(Body body : Waypoints) {
					body.display(p, plt, 50, 50);
				}
			}

			drawAccurateNumberOfHearts(p);

			// MCBody.setVel(MCStartingVel);
			MCBody.move(dt * 15);
			// este * 15 e porque na classe da sprite multiplico por 10 para ser mais
			// rapido, para acompanhar a animacao ponho 15
			// fazer animacao do personagem principal
			if (MC != null) {
				if (debugBoxes) {
					MCBody.display(p, plt, MCCollisionBox[0], MCCollisionBox[1]);
				}
				MC.animateHorizontal();
				MC.show();

				mcLeft.setPos(MC.getPos());
				mcRight.setPos(MC.getPos());
				makeBodyFollowAnimation(MCBody, MC, plt);

			}

			//de 5 em 5 waves, falo spawn de um boss! dai o resto da divisao por 5
			if (boss == null && !bossFight && (numberOfTimesISpawnedEnemies%5==0 && numberOfTimesISpawnedEnemies!=0) ) {
				//System.out.println("if spawn Boss");
				inimigos.clear();
				spawnBoss(p);
				bossFight = true;
				numberOfTimesISpawnedEnemies=0;
			}
			
			//parte do boos boid:
			if(boss != null) {
				//System.out.println("boid display!");
				boss.applySingleBehavior(0, dt);
				boss.draw(p, plt, debugBoxes, dt);
			}

			for (PowerUp powerUp : powerUps) {
				powerUp.draw(p, plt, debugBoxes, dt);
			}

			// mostrar ossos, caso existam
			if (!ossos.isEmpty()) {
				for (Osso osso : ossos) {
					osso.draw(p, plt, debugBoxes, dt);
				}
			}

			for (Inimigo inimigo : inimigos) {
				// System.out.println("entrei na lista de inimigos!");
				inimigo.draw(p, plt, debugBoxes, dt);
			}

			// caso o boss exista, faço spawn dele aqui:
			if (boss != null) {
				long duration = (System.nanoTime() - lastTimeBossShot); // divide by 1000000 to get milliseconds.
				// System.out.println("Duration---->"+duration);
				if (duration / 1000000f >= 500) {
					PVector offsetVectorBoss = new PVector(2, -5);
					PVector vectorFromWhereToSpawnTheBombs = PVector.add(boss.getBody().getPos(), offsetVectorBoss);
					Bomb bomb = new Bomb(p, vectorFromWhereToSpawnTheBombs, new PVector(), bombsCollisionBox[0], bombsCollisionBox[1]);
					bombs.add(bomb);
					lastTimeBossShot = System.nanoTime();
				}
				boss.draw(p, plt, debugBoxes, dt);
				// no caso de o boss ser derrotado, faz spawn de novo, mas tenho que arranjar
				// uma flag extra para deixar o player derrotar mais alguns inimigos antes de
				// fazer spawn de novo
				if (boss.isFlagRemove()) {
					boss = null;
				}
			}

			// para remover os ossos da lista, nao posso usar for-each e tenho que comeï¿½ar
			// do fim! caso contrï¿½rio tenho uma excepï¿½ï¿½o!
			for (int i = inimigos.size() - 1; i >= 0; --i) {
				Inimigo inimigo = inimigos.get(i);
				if (inimigo.isFlagRemove()) {
					inimigos.remove(inimigo);
				}
			}

			for (Osso osso : ossos) {
				// System.out.println("ossos---->"+ossos.size());
				for (Inimigo inimigo : inimigos) {
					// caso de o osso colidir com o inimigo:
					if (osso.getBody().collision(inimigo.getBody(), plt)) {
						// System.out.println("Osso colidiu com inimigo");
						ParticleSystem ps = inimigo.getBody().explodeMe();
						pss.add(ps);

						// tirar 1 de vida ao inimigo:
						int currentInimigoHP = inimigo.getHP();
						// System.out.println("current inimigo HP --->"+currentInimigoHP);
						inimigo.setHP(currentInimigoHP - 1);

						if (currentInimigoHP <= 1) { // por alguma razão se for 0 ele dá mais 1 de vida aos inimigos
							float min = 0, max = 1;
							double chance = (Math.random() * (max - min)) + min;
							if (chance > 0 && chance < powerUpDropChance) {
								PowerUp pwr = new PowerUp(p, inimigo.getBody().getPos(), new PVector(), 50, 70);
								powerUps.add(pwr);
							}

							inimigo.setFlagRemove(true);// inimigop tambï¿½m tem que ser removido no proximo draw!
						}

						// como as bombas estao fora do IF, cada vez que acerto num inimigo ele deixa
						// cair um bomba. podia por apenas para a bomba cair se ele morrese, mas assim o
						// gameplay e mais itneressante porque
						// o jogado se tem que ir mexendo de um lado para outro ainda mais !
						Bomb bomb = new Bomb(p, inimigo.getBody().getPos(), new PVector(), bombsCollisionBox[0], bombsCollisionBox[1]);
						bombs.add(bomb);

						// mesmo que o inimigo não desapareça, o osso é sempre removido!
						osso.setFlagRemove(true); // marcar o osso para ser removido no proximo draw. //se retirar isto
						// tenho piercing bones !

					}
				}
				// caso de o osso encontrar o boss:
				if (boss != null && osso.getBody().collision(boss.getBody(), plt)) {
					// System.out.println("Colisão osso Boss!");
					ParticleSystem ps = osso.getBody().explodeMe();
					pss.add(ps);

					int currentBossHP = boss.getHP();
					System.out.println("current inimigo HP --->"+currentBossHP);
					boss.setHP(currentBossHP - 1);

					if (currentBossHP <= 1) { // por alguma razão se for 0 ele dá mais 1 de vida aos inimigo
						boss.setFlagRemove(true);// inimigop tambï¿½m tem que ser removido no proximo draw!
						bossFight = false;
					}

					// como as bombas estao fora do IF, cada vez que acerto num inimigo ele deixa
					// cair um bomba. podia por apenas para a bomba cair se ele morrese, mas assim o
					// gameplay e mais itneressante porque
					// o jogado se tem que ir mexendo de um lado para outro ainda mais !
					Bomb bomb = new Bomb(p, osso.getBody().getPos(), new PVector(), bombsCollisionBox[0], bombsCollisionBox[1]);
					bombs.add(bomb);

					// mesmo que o inimigo não desapareça, o osso é sempre removido!
					osso.setFlagRemove(true); // marcar o osso para ser removido no proximo draw. //se retirar isto
					// tenho piercing bones !
				}
			}

			// AS bombas tï¿½m que ficar a frente dos power Ups
			// mostrar as bombas que cairam e foram adicionadas do ciclo anterior:
			for (Bomb bomb : bombs) {
				bomb.draw(p, plt, debugBoxes, dt);
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

			// para remover os ossos da lista, nao posso usar for-each e tenho que comeï¿½ar
			// do fim! caso contrï¿½rio tenho uma excepï¿½ï¿½o!
			for (int i = inimigos.size() - 1; i >= 0; --i) {
				Inimigo currentEnemy = inimigos.get(i);
				if (currentEnemy.isFlagRemove()) {
					inimigos.remove(currentEnemy);
				}
			}

			for (int i = ossos.size() - 1; i >= 0; --i) {
				Osso currentOsso = ossos.get(i);
				if (currentOsso.isFlagRemove()) {
					ossos.remove(currentOsso);
				}
			}

			collisionBetweenPlayerAndPWR();
			collisionBetweenPlayerAndBomb(p);

			if (MCHealth <= 0) {
				ms.setShowGameOver(true);
			}

			// System.out.println("inimigos size--->"+inimigos.size()); if
			if ( (inimigos.size() <= 3 || numberOfTimesISpawnedEnemies == 0) && !bossFight) {
				if (numberOfTimesISpawnedEnemies == 0) {
					spawnStrongerEnemies(p, HPEasyEnemies);
					numberOfTimesISpawnedEnemies++;
				} else if (numberOfTimesISpawnedEnemies >= 1 && numberOfTimesISpawnedEnemies < 3) {
					spawnStrongerEnemies(p, HPMediumEnemies);
					numberOfTimesISpawnedEnemies++;
				} else if (HPMediumEnemies >= 3) {
					spawnStrongerEnemies(p, HPHardEnemies);
					numberOfTimesISpawnedEnemies++;
				}
			}

			showLastPowerUpReceived(p);

		}

	}

	public void collisionBetweenPlayerAndPWR() {
		// colisao entre PowerUps e player. Para isso, Nï¿½O posso usar for-each e tenho
		// que comeï¿½ar do fim:
		for (int i = powerUps.size() - 1; i >= 0; --i) {
			if (MCBody.collision(powerUps.get(i).getBody(), plt)) {
				powerUps.remove(powerUps.get(i));
				decideWhatPRW();
			}
		}
	}

	public void collisionBetweenPlayerAndBomb(PApplet p) {
		// colisao entre PowerUps e player. Para isso, Nï¿½O posso usar for-each e tenho
		// que comeï¿½ar do fim:
		boolean didICollide = false;
		for (int i = bombs.size() - 1; i >= 0; --i) {
			if (MCBody.collision(bombs.get(i).getBody(), plt)) {
				//System.out.println("Entrei na collision!");
				bombs.remove(bombs.get(i));
				MCHealth--;
				// se houver colisao, vou tirar todas as bombas do ecra para dar hipotese ao
				// player e dar um feedback visual:
				p.background(255, 0, 0);
				didICollide = true;
			}
		}
		if (didICollide) {
			bombs.clear();
		}

	}

	public void decideWhatPRW() {
		float min = 0, max = 1;
		double chance = (Math.random() * (max - min)) + min;
		// double chance = 0.75;
		// 50% de chance para aumentar o numero de ossos!
		if (chance > 0 && chance < 0.5) {
			lastPowerupReceived = "Number of Bones per Shoot Incresed!";
			numberOfOssosPerPress++;
		}
		// 15% para diminuir o tempo entre disparos. tenho que garantir que o valor é
		// sempre positivo!
		else if (chance >= 0.5 && chance < 0.65) {
			if (timeBetweenShots > 0) {
				lastPowerupReceived = "Time Between Shots decreased!";
				timeBetweenShots -= 50f;
			}
		} else if (chance >= 0.65 && chance < 0.80) {
			lastPowerupReceived = "Extra Life Received!";
			MCHealth = Math.min(++MCHealth, 5); // tem que ser pre-inc ! ++MCHealth
		}

		// 25% de chance para os ossos irem mais dispersos. so faz sentido existir se
		// disparar mais que um osso, dai o if extra!
		else {
			if (numberOfOssosPerPress > 1) {
				lastPowerupReceived = "Bones shoots are wider now!";
				spaceBetWeenBoneSpawns += 5;
			} else {
				lastPowerupReceived = "Number of Bones per Shoot Incresed!";
				numberOfOssosPerPress++;
			}
		}

	}

	public PVector getMCStartingPos() {
		return MCStartingPos;
	}

	private void makeBodyFollowAnimation(Body body, SpriteDef spriteDef, SubPlot plt) {
		double[] coordConverted = plt.getWorldCoord((float) spriteDef.getX(), (float) spriteDef.getY());
		body.setPos(new PVector((float) coordConverted[0], (float) coordConverted[1]));
	}

	@Override
	public void mousePressed(PApplet p) {
		if (p.mouseButton == PConstants.LEFT && ms.isStartGame()) {
			loadShootBoneAnimation(p);
		}
		// botoes do menu principal:
		if (isInsideRect(p.mouseX, p.mouseY, startGameRect[0], 200, startGameRect[1], 50) && !ms.isStartGame()
				&& !ms.isShowTips() && !ms.isShowGameOver()) {
			resetEverything(); // cada vez que carrego no start game, começo o jogo de novo!
			ms.setStartGame(true);
		}

		else if (isInsideRect(p.mouseX, p.mouseY, showTipsRect[0], 200, showTipsRect[1], 50) && !ms.isStartGame()
				&& !ms.isShowTips() && !ms.isShowGameOver()) {
			ms.setShowTips(true);
		}

		// botao de voltar atras no game over
		else if (isInsideRect(p.mouseX, p.mouseY, gameOverRetryAgainButton[0], 200, gameOverRetryAgainButton[1], 50)
				&& ms.isStartGame() && !ms.isShowTips() && ms.isShowGameOver()) {
			// voltar ao main screen, ja que tive trabalho a por a animacao e a musica, o
			// utilizador vï¿½ isso mais vezes!
			ms.setShowTips(false);
			ms.setStartGame(false);
		}

		// botao do voltar para tras nas tips
		else if (isInsideRect(p.mouseX, p.mouseY, showTipsRectBackButton[0], 200, showTipsRectBackButton[1], 50)
				&& ms.isShowTips() && !ms.isShowGameOver() && !ms.isStartGame()) {
			ms.setShowTips(false);
			ms.setStartGame(false);
		}

	}

	public boolean isInsideRect(int mouseX, int mouseY, int rectX, int rectWidth, int rectY, int rectHeight) {
		if ((mouseX > rectX) && (mouseX < (rectX + rectWidth)) && (mouseY > rectY) && (mouseY < (rectY + rectHeight))) {
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
			debugBoxes = !debugBoxes;
		}

	}

	public void loadShootBoneAnimation(PApplet p) {
		long duration = (System.nanoTime() - lastTimeIShot); // divide by 1000000 to get milliseconds.
		// System.out.println("Duration---->"+duration);
		if (duration / 1000000f >= timeBetweenShots) {
			for (int i = 1; i < numberOfOssosPerPress + 1; ++i) {
				PVector posToSpawnBoneIn = new PVector(MC.getPos().x + spaceBetWeenBoneSpawns * i, MC.getPos().y);
				Osso osso = new Osso(p, posToSpawnBoneIn, new PVector(0, attackVel), 10, 20);
				ossos.add(osso);
			}
			if (ms.isStartGame() && !ms.isShowGameOver()) {
				try {
					boneSoundEffect();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lastTimeIShot = System.nanoTime();
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
