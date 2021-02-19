package tp.src.physics;
import processing.core.PVector;

public abstract class Mover 
{
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float mass;
	public static final double G = 6.67e-11f;
	
	protected Mover(PVector pos, PVector vel, float mass)
	{
		this.pos = pos.copy();		//fundamental para o sistema de particulas funcionar
		this.vel = vel;
		this.mass = mass;
		this.acc = new PVector();
	}
	
	public void applyForce(PVector f)
	{
		acc.add(PVector.div(f,mass));
	}
	
	public void move(float dt)
	{
		vel.add(acc.mult(dt));
		pos.add(PVector.mult(vel,dt));
		acc.mult(0);
	}
	
	public PVector attraction(Mover m)
	{
	    PVector r = PVector.sub(pos, m.pos);
	    float dist = r.mag();
	    float strength = (float)(G*mass*m.mass/(dist*dist) );		//lei da gravita��o universal de newton!
	    return (r.normalize().mult(strength));
	}
	
	public void setPos(PVector pos) {
		this.pos = pos;
	}
		
	public PVector getPos(){
		return this.pos;
	}
	
	public PVector getVel()
	{
		return vel;
	}
	
	public void setVel(PVector vel)
	{
		this.vel = vel;
	}
	
	public float getMass()
	{
		return mass;
	}
	
}














