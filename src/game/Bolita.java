package game;

public class Bolita extends GameObject{
	
	private boolean superpoder;
	
<<<<<<< HEAD
	public Bolita(int x, int y, boolean especial) {
		super(x,y,1);
=======
	public Bolita(int x, int y, boolean especial){
		coordenadas = new Punto(x, y);
>>>>>>> origin/master
		superpoder = especial;
		if(superpoder == true) {
			radio = 2;
		}
<<<<<<< HEAD
=======
		else {
			radio = 1;
		}
>>>>>>> origin/master
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
