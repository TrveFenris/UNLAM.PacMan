package gameobject;

import javax.swing.JLabel;

public class Bolita extends GameObject{
	
	private boolean superpoder;
	public Bolita(JLabel img, boolean especial) {
		super(img);
		superpoder = especial;
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
