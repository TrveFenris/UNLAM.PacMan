package game;

public class GameObject {
	protected Punto coordenadas;
	protected int radio;
	
	protected GameObject(int x, int y, int r) {
		coordenadas = new Punto(x,y);
		radio = r;
	}
	
	public void dibujar(){
		
	}
	
	public int getX() {
		return coordenadas.getX();
	}
	
	public int getY() {
		return coordenadas.getY();
	}
	
	public int getRadio() {
		return radio;
	}
}
