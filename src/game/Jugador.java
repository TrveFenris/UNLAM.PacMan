package game;

import java.util.Calendar;

import javax.swing.JLabel;

public class Jugador extends GameObject{
	protected int velX;
	protected int velY;
	protected boolean superpoder;
	protected Calendar timerSuperpoder;
	//protected boolean movimientoX;
	//protected boolean movimientoY;
	
	//public Jugador(int x, int y, int r, JLabel img){
	public Jugador(JLabel img){
		super(img);
		superpoder = false;
		velX = 0;
		velY = 0;
	}
	
	public void input(){
		
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
	
	public boolean tieneSuperpoder(){
		return superpoder;
	}
	
	public void startTimer() {
		timerSuperpoder.clear();
	}
	
	public long getTimerMilliseconds() {
		return timerSuperpoder.getTimeInMillis();
	}
}
