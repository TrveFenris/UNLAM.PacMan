package game;

import javax.swing.JLabel;

public class Bolita extends GameObject{
	
	private boolean superpoder;
	
	//public Bolita(int x, int y, JLabel img, boolean especial) {
	public Bolita(JLabel img, boolean especial) {
		super(img);
		superpoder = especial;
		/*if(superpoder == true) {
			radio = 2;
		}*/
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
