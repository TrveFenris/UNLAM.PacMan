package gameobject;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import punto.Punto;

public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 5189116667650641624L;
	protected boolean alive;
	protected JLabel imagen;
	protected String nombreSprite;

	protected GameObject() {
		imagen = new JLabel();
		alive = true;
	}
	
	public Punto getCentroCoordenadas() {
		return new Punto( (imagen.getWidth()/2) + getX(), (imagen.getHeight()/2) + getY());
	}
	
	public int getCentroCoordenadasX() {
		return ((imagen.getWidth()/2) + getX());
	}
	
	public int getCentroCoordenadasY() {
		return ((imagen.getHeight()/2) + getY());
	}
	
	public int getX() {
		return imagen.getX();
	}
	
	public int getY() {
		return imagen.getY();
	}
	
	public int getWidth(){
		return imagen.getWidth();
	}
	
	public int getHeight(){
		return imagen.getHeight();
	}
	
	public Punto getLocation(){
		return new Punto(imagen.getX(), imagen.getY());
	}
	
	public void setLocation(int x, int y){
		imagen.setLocation(x,y);
	}
	
	public JLabel getImagen() {
		return imagen;
	}
	public void setImagen(String path) {
		ImageIcon i = new ImageIcon(path);
		imagen.setIcon(i);
		imagen.setSize(i.getIconWidth(), i.getIconHeight());
	}
	
	public void dibujar(JPanel area){
		if(nombreSprite != null || nombreSprite != "")
			area.add(getImagen());
	}
	
	public void borrarImagen() {
		imagen.setIcon(null);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAliveState(boolean state) {
		alive = state;
	}
}
