package game;

import javax.swing.JLabel;

import windows.Actions;

public class Jugador extends GameObject{
	protected int velocidad;
	protected int velX;
	protected int velY;
	protected String nombre;
	protected int puntaje;
	
	public Jugador(JLabel img, String nombre, int velocidad){
		super(img);
		this.velocidad=velocidad;
		this.nombre=nombre;
		puntaje=0;
		velX = 0;
		velY = 0;
	}
	
	public void input(){
		
	}
	
	public void cambiarSentido(Actions a){
		switch(a){
			case ARRIBA:
				velX=0;
				velY=-1*velocidad;
				break;
			case ABAJO:
				velX=0;
				velY=velocidad;
				break;
			case IZQUIERDA:
				velX=-1*velocidad;
				velY=0;
				break;
			case DERECHA:
				velX=velocidad;
				velY=0;
				break;
		}
	}
	
	public void mover(){
		setLocation(getLocation().getX() + velX, getLocation().getY() + velY);
	}
	
	public void actualizarPuntaje(int cant){
		puntaje=cant;
	}
	
	//Reescribir los siguientes metodos:
	/*
	public boolean colisionaCon(GameObject obj) {
		return (this.coordenadas.distanciaCon(obj.coordenadas) <= this.radio + obj.radio);
	}
	
	public boolean estaEn(Punto p){
		return (coordenadas.getX() == p.getX() && coordenadas.getY() == p.getY());
	}
	
	public boolean estaEn(Recta r) {
		boolean result = false;
		
		if(r.() == Rectas.VERTICAL){
			
		}
		if(r.tipo ==)
		return result;
	}
	*/
}
