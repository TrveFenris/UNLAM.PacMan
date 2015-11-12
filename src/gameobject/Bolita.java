package gameobject;

import game.ConfiguracionSprites;

import punto.Punto;

public class Bolita extends GameObject{
	
	private boolean superpoder;
	public Bolita(Punto posInicial, boolean especial) {
		super();
		superpoder = especial;
		if(superpoder)
			setImagen(ConfiguracionSprites.BOLITA_ESPECIAL_SPRITE);
		else
			setImagen(ConfiguracionSprites.BOLITA_SPRITE);
		
		imagen.setLocation(posInicial.getX(),posInicial.getY());
		//imagen.setBounds(posInicial.getX(), posInicial.getY(), 21, 21);
	}
	
	public boolean isEspecial(){
		return superpoder;
	}
}
