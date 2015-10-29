package game;

import javax.swing.JLabel;

public class GameObject {
	//protected Punto coordenadas;
	//protected int radio;
	protected JLabel imagen;
	
	//protected GameObject(int x, int y, int r, JLabel img) {
	protected GameObject(JLabel img) {
		//coordenadas = new Punto(x,y);
		//radio = r;
		imagen = img;
	}
	
	public void dibujar(){
		
	}
	
	public int getX() {
		//return coordenadas.getX();
		return imagen.getX();
	}
	
	public int getY() {
		//return coordenadas.getY();
		return imagen.getY();
	}
	
	/*
	public int getRadio() {
		return radio;
	}
	*/
	
	public JLabel getImagen() {
		return imagen;
	}
}
