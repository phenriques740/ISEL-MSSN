package aulas.particleSystems;

import processing.core.PVector;

public class PSControl {
	private float averageAngle; // dire��o media
	private float dispersionAngle;
	private float minVelocity;
	private float maxVelocity;
	private float minLifeTime;
	private float maxLifeTime;
	private float minRadius;
	private float maxRadius;
	private float flow;			//quantas particulas por segundo quero criar
	private int color;
	

	public PSControl(float[] velControl, float[] lifetime, float[] radius, float flow, int color ) {
		setVelParams(velControl);
		setLifeTimeParams(lifetime);
		setRadiusParams(radius);
		setFlow(flow);
		setColor(color);
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setFlow(float flow) {
		this.flow = flow;
	}
	
	public void setRadiusParams(float[] radius) {
		this.minRadius = radius[0];
		this.maxRadius = radius[1];
	}
	
	public void setLifeTimeParams(float[] lifetime) {
		this.minLifeTime = lifetime[0];		//o this aqui e redundante
		this.maxLifeTime = lifetime[1];
	}
	
	public PVector getRndVel() {
		float angle = getRnd(averageAngle - dispersionAngle/2, averageAngle + dispersionAngle/2);
		PVector v = PVector.fromAngle(angle);			//dire��o da velocidade
		v.mult( getRnd(minVelocity, maxVelocity) );		//magnitude da velocidade
		return v;			//podia fazer logo return em cima
	}
	
	public static float getRnd(float min, float max) {
		return min + (float) (Math.random() * (max - min));
	}
	
	public void setVelParams(float [] velControl) {
		averageAngle = velControl[0];
		dispersionAngle = velControl[1];
		minVelocity = velControl[2];
		maxVelocity = velControl[3];
	}
	
	public float getFlow() {
		return flow;
	}
	
	public int getColor() {
		return color;
	}
	
	public float getRndRadius() {
		return getRnd(minRadius, maxRadius);
	}
	
	public float getRndLifetime() {
		return getRnd(minLifeTime, maxLifeTime);
	}

}











































































