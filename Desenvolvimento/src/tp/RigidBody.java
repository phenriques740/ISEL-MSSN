package tp;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class RigidBody {
	private PVector pos;
	private PVector vel;
	private PVector acc;
	private float mass;
	private float hitBoxRadius;

	/**
	 * Cria um corpo que pode ser influenciado por forças externas.
	 * 
	 * @param mass         - Massa do corpo simulado
	 * @param hitBoxRadius - Neste caso significa que a hitbox é um circulo com o
	 *                     raio X
	 * @param pos          - Posicao inical do corpo
	 */
	public RigidBody(float mass, float hitBoxRadius, PVector pos) {
		this.pos = pos;
		vel = new PVector();
		acc = new PVector();

		this.mass = mass;
		this.hitBoxRadius = hitBoxRadius;
	}

	/**
	 * Metodo abstrato para desenhar objetos, deve ser implementado.
	 * 
	 * @param p - Instancia de PApplet onde desenhar.
	 */
	public abstract void display(PApplet p);

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public float getHitBoxRadius() {
		return hitBoxRadius;
	}

	public PVector getPos() {
		return pos;
	}

	public PVector getVel() {
		return vel;
	}

	public PVector getAcc() {
		return acc;
	}

	public void applyForce(PVector force) {
		this.acc = PVector.div(force, mass);
	}

	/**
	 * Metodo que indica se o vector se encontra dentro do circulo.<br>
	 * 
	 * Util para calcular se o jogador acertou na bomba<br>
	 * <br>
	 * <b> Não é simétrico. </b></br>
	 * corpo1.isInside(corpo2) é diferente de corpo2.isInside(corpo1)
	 * 
	 * @param outro - PVector de posição X,Y que se quer saber se está dentro da
	 *              hitbox do corpo
	 * @return
	 */
	public boolean isInside(PVector outro) {
		return PVector.dist(getPos(), outro) < getHitBoxRadius();
	}

	/**
	 * Metodo que diz se dois corpos se tocam, ou se parte de um está dentro do
	 * outro
	 * 
	 * Util para calcular se houve colisao entre corpos.
	 * 
	 * @param outro - Outro corpo
	 * @return
	 */
	public boolean isTouching(RigidBody outro) {
		return PVector.dist(getPos(), outro.getPos()) <= (getHitBoxRadius() + outro.getHitBoxRadius());
	}
}
