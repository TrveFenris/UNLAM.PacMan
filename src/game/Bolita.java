package game;

public class Bolita extends GameObject{
	
	private boolean superpoder;
	
	public Bolita(int x, int y, boolean especial){
		coordenadas = new Punto(x, y);
		if(superpoder == true) {
			radio = 2;
		}
		else {
			radio = 1;
		}
		superpoder = especial;
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
