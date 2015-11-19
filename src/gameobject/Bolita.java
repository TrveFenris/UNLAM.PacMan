package gameobject;

import game.ConfiguracionSprites;
import punto.Punto;

public class Bolita extends GameObject {
	private static final long serialVersionUID = 6993990489804338135L;
	private boolean superpoder;
	public Bolita(Punto posInicial, boolean especial) {
		super();
		superpoder = especial;
		if(superpoder)
			setImagen(ConfiguracionSprites.BOLITA_ESPECIAL.getValor());
		else
			setImagen(ConfiguracionSprites.BOLITA.getValor());
		
		imagen.setLocation(posInicial.getX(),posInicial.getY());
		//imagen.setBounds(posInicial.getX(), posInicial.getY(), 21, 21);
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
