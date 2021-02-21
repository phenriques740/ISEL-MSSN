package TPFinal;

import processing.core.PApplet;
import processing.core.PVector;

public class mainScreen {
	
	private String pathToImage;
	private String pathToJson;
	private String option1;
	private String option2;
	private boolean startGame, showTips;
	private Animador backGround, backGround2;
	private final PApplet p;
	
		
	public mainScreen(PApplet p, String pathToJson, String pathToImage, String option1, String option2) {
		this.p = p;
		this.pathToJson = pathToJson;
		this.pathToImage = pathToImage;
		this.option1 = option1;
		this.option2 = option2;
		backGround = new Animador(p, pathToJson, pathToImage, new PVector(0,50), new PVector(0.1f,0f)  );
		backGround2 = new Animador(p, pathToJson, pathToImage, new PVector(p.width,p.height-250), new PVector(0.15f,0f)  );
		//System.out.println("Check!");
	}
	

	public void drawMenu(PApplet p) {
		p.background(16);
		SpriteDef backGorundSPrite = backGround.getSpriteDef();
		//System.out.println("Check 2");
		backGorundSPrite.show();
		backGorundSPrite.animateHorizontal();
		
		SpriteDef backGroundSprite2  = backGround2.getSpriteDef();
		backGroundSprite2.show();
		backGroundSprite2.setSpeedUpFactor( -10 );
		backGroundSprite2.animateHorizontal();
	}
	
	
	
	
	public boolean isStartGame() {
		return startGame;
	}
	public void setStartGame(boolean startGame) {
		this.startGame = startGame;
	}
	public boolean isShowTips() {
		return showTips;
	}
	public void setShowTips(boolean showTips) {
		this.showTips = showTips;
	}
	public String getPathToImage() {
		return pathToImage;
	}
	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	
	
}










