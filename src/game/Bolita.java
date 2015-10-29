package game;

public class Bolita extends GameObject{
	
	private boolean superpoder;
	
	public Bolita(int x, int y, boolean especial) {
		super(x,y,1);
		superpoder = especial;
		if(superpoder == true) {
			radio = 2;
		}
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
