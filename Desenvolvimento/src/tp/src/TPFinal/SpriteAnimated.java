package tp.src.TPFinal;

import java.util.ArrayList;

import aulas.graph.SubPlot;
import aulas.physics.Body;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import tp.src.setup.InterfaceProcessingApp;

public class SpriteAnimated implements InterfaceProcessingApp {

	PImage spritesheet, spritesheetMC;
	JSONObject spritedata ,spritedataMC;

	ArrayList<PImage> animation, animationMC; // vai ter cada frame

	ArrayList<SpriteDef> enemies; // conjunto de cavalos
	
	private Body MCBody;
	private double[] window = {-10, 10, 
			-10, 10};
	private float[] viewport = {0f, 0f, 1f, 1f};
	private SubPlot plt;
	
	SpriteDef MC;		//MC = main Character
	private float[] MCStartingPos = new float[] {20,400};
	private float MCStartingVel = 0f;
	private int spriteH, spriteW;
	private String resources = "tp/resources/";

	@Override
	public void setup(PApplet p) {
		// TODO Auto-generated method stub
		animation = new ArrayList<PImage>();
		enemies = new ArrayList<SpriteDef>();
		spritedata = p.loadJSONObject(resources+"slimeIdle.json");
		spritesheet = p.loadImage(resources+"slimeIdle.png");
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

		for (int i = 0; i < 5; i++) {
			// vou instanciar o meu boneco principal na posição especificada. util ter isto
			// numa lista para depois adicionar multiplo meteoritos/objectos a cair
			enemies.add(new SpriteDef(animation, 50, i*75, 0.5f, p));
		}
		
		//para o personagem principal: (fazer load da primeira animação.
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject(resources+"skeletonRun.json");
		spritesheetMC = p.loadImage(resources+"skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		//System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MCStartingPos[0], MCStartingPos[1], MCStartingVel, p);	//a MCStartingVel tem os valores iguais em ambas as componentes, nao faz diferença entr [0] e [1]
		
		plt = new SubPlot(window, viewport, p.width, p.height);
		PVector worldCoordToPlt = new PVector( (float)plt.getWorldCoord(MCStartingPos)[0] , (float)plt.getWorldCoord(MCStartingPos)[1]  ); //fazer a conversão de coordenadas do mundo (em pixeis) para
		//as cordenadas do viewport que os rigid bodies utilizam !
		MCBody = new Body( worldCoordToPlt , new PVector( MCStartingVel , 0 ), 1, 1, p.color(255,128,0));

	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub
		p.background(127);
		
		MCBody.setVel(new PVector( MCStartingVel , 0 ));
		MCBody.move(dt*15);	//este * 15 e porque na classe da sprite multiplico por 10 para ser mais rápido, para acompanhar a animação ponho 15
		MCBody.display(p, plt);
		if ( plt.getPixelCoord(MCBody.getPos().x,MCBody.getPos().y)[0] > p.width) {
			System.out.println("saiu!!");
			
			double[] arr = plt.getWorldCoord(MCBody.getPos().x, MCBody.getPos().y);
			PVector debug = new PVector((float)arr[0], (float)arr[1]);
			MCBody.setPos(debug);
			
			
		}
		
		if(MC!=null) {
			MC.show();
			MC.animate();
		}
		
		for (SpriteDef enemie : enemies) {
			enemie.show();
			enemie.animate();
		}
		
		

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		if (p.key == 'd') {
			//System.out.println("carreguei no D");
			loadRunRightAnimation(p);
			MC.setSpeed(0.2f);
			//System.out.println("value da p.key d --->"+p.keyCode);
		}
		else if (p.key == 'a') {
			loadRunLeftAnimation(p);
			MC.setSpeed(0.2f);
			//System.out.println("value da p.key d --->"+p.keyCode);
		}

	}
	
	public void loadRunLeftAnimation(PApplet p) {
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject(resources+"skeletonLRun.json");
		spritesheetMC = p.loadImage(resources+"skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		//System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MC.getX(), MC.getY(), MC.getSpeed() , p);
		MC.setSpeedUpFactor( MC.getSpeedUpFactor()*-1 );	//de forma a andar para trás
	}
	
	public void loadRunRightAnimation(PApplet p) {
		animationMC = new ArrayList<PImage>();
		spritedataMC = p.loadJSONObject(resources+"skeletonRun.json");
		spritesheetMC = p.loadImage(resources+"skeleton.png");
		JSONArray framesMC = spritedataMC.getJSONArray("frames");
		//System.out.println("framesMC size---->"+framesMC.size() );
		for (int i = 0; i < framesMC.size(); i++) {
			// System.out.println("frames size --->"+frames.size());
			JSONObject frame = framesMC.getJSONObject(i);
			JSONObject pos = frame.getJSONObject("position"); // tem toda a informação que esta no JSON sobre cada frame
			PImage imgMC = spritesheetMC.get(pos.getInt("x"), pos.getInt("y"), pos.getInt("w"), pos.getInt("h"));
			spriteH = pos.getInt("h");
			spriteW = pos.getInt("w");
			animationMC.add(imgMC);
		}
		MC = new SpriteDef(animationMC, MC.getX(), MC.getY(), MC.getSpeed() , p);
		//este IF e para saber se ja estou a andar na direção certa ou se preciso de inverter !
		if (MC.getSpeedUpFactor() > 0) {
			MC.setSpeedUpFactor( MC.getSpeedUpFactor() );
		}
		
		else if (MC.getSpeedUpFactor() < 0) {
			MC.setSpeedUpFactor( MC.getSpeedUpFactor()*-1 );
		}
		
	}
	
	@Override
	public void keyReleased(PApplet p) {
		// TODO Auto-generated method stub
		if (p.key == 'd') {
			MC.setSpeed(0f);
		}
		if (p.key == 'a') {
			MC.setSpeed(0f);
		}
	}
	
	

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

}

















