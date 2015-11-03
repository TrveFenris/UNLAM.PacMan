package game;

import javax.swing.JLabel;

public class GameObject {
	protected JLabel imagen;

	protected GameObject(JLabel img) {
		imagen = img;
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
		return new Punto(imagen.getLocation().x,imagen.getLocation().y);
	}
	
	public void setLocation(int x, int y){
		imagen.setLocation(x,y);
	}
	
	public JLabel getImagen() {
		return imagen;
	}
}
