package TPFinal;

import processing.core.PApplet;
import processing.core.PVector;

public class mainScreen {
	
	private String pathToImage, pathToJson, option1, option2, aKeyJson, aKey, dKeyJson, dKey, LMBKeyJson, LMBKey;
	private boolean startGame, showTips;
	private Animador backGround, backGround2, A, D, LMB;
	private final PApplet p;
	private int[] startGameRect = {150, 275, 190, 310};
	private int[] showTipsRect = {450, 275, 500, 310};
	
		
	public mainScreen(PApplet p, String pathToJson, String pathToImage, String option1, String option2, String aKeyJson, String aKey, String dKeyJson, String dKey,  String LMBKeyJson, String LMBKey) {
		this.p = p;
		this.pathToJson = pathToJson;
		this.pathToImage = pathToImage;
		this.option1 = option1;
		this.option2 = option2;
		//imagens para as tips:
		this.aKeyJson = aKeyJson;
		this.aKey = aKey;
		this.dKeyJson = dKeyJson;
		this.dKey = dKey;
		this.LMBKeyJson = LMBKeyJson;
		this.LMBKey = LMBKey;
		
		backGround = new Animador(p, pathToJson, pathToImage, new PVector(0,50), new PVector(0.1f,0f)  );
		backGround2 = new Animador(p, pathToJson, pathToImage, new PVector(p.width,p.height-250), new PVector(0.15f,0f)  );
		//System.out.println("Check!");
	}
	

	public void drawMenu(PApplet p) {
		p.background(0);
		
		if(!this.showTips) {
			normalMenu();
		}
		else {
			//neste caso o menu com tips:
			tipsMenu();
		}
		
	}
	
	private void tipsMenu() {
		p.background(128);
		A = new Animador(p, aKeyJson, aKey, new PVector(100,100), new PVector(0.1f,0f)  );
		D = new Animador(p, dKeyJson, dKey, new PVector(100,250), new PVector(0.1f,0f)  );
		LMB = new Animador(p, LMBKeyJson, LMBKey, new PVector(50,400), new PVector(0.1f,0f) );
		
		//texto por baixo de cada imagem a explicar:
		drawText("Quando os inimigos são destruidos, existe uma chance de cair um powerup", 30, 30 );
		drawText("Posso premir a tecla b para voltar ao menu principal", 30, 70);
		drawText("Tecla A serve para andar para a Esquerda.", 220, 150 );
		drawText("Tecla D serve para andar para a Direita.", 220, 300 );
		drawText("Botão Esquerdo do Rato serve para Disparar.", 200, 500 );
		
		SpriteDef Asprite = A.getSpriteDef();
		SpriteDef Dsprite = D.getSpriteDef();
		SpriteDef LMBsprite = LMB.getSpriteDef();
		
		Asprite.show();
		Dsprite.show();
		LMBsprite.show();
		
	}
	
	private void normalMenu() {
		SpriteDef backGorundSPrite = backGround.getSpriteDef();
		//System.out.println("Check 2");
		backGorundSPrite.show();
		backGorundSPrite.animateHorizontal();
		
		SpriteDef backGroundSprite2  = backGround2.getSpriteDef();
		backGroundSprite2.show();
		backGroundSprite2.setSpeedUpFactor( -10 );
		backGroundSprite2.animateHorizontal();
		
		drawButton("Start Game!", startGameRect[0], startGameRect[1], startGameRect[2], startGameRect[3] );
		drawButton("Show Tips", showTipsRect[0], showTipsRect[1], showTipsRect[2], showTipsRect[3]);
	}
	
	
	public int[] getStartGameRect() {
		return startGameRect;
	}
	

	public int[] getShowTipsRect() {
		return showTipsRect;
	}


	public void drawText(String string, int textX, int textY) {
		p.fill(0);		//para alterar a cor do texto
		p.textSize(20);
		p.text(string, textX, textY);
	}

	public void drawButton(String actualText, int rectX, int rectY, int textX, int textY) {
		p.fill(255);	//para o botao ter uma cor diferente
		p.strokeWeight(3);
		p.stroke(128);
		p.rect(rectX, rectY, 200, 50);
		
		drawText(actualText,textX, textY );
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










