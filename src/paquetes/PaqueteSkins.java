package paquetes;

import game.ConfiguracionSprites;

public class PaqueteSkins extends Paquete{

	private static final long serialVersionUID = -6005614126722914842L;
	private ConfiguracionSprites skinPacman;
	private ConfiguracionSprites skinFantasma;
	
	public PaqueteSkins(ConfiguracionSprites pacman,ConfiguracionSprites fantasma) {
		skinPacman = pacman;
		skinFantasma = fantasma;
	}
	
	public ConfiguracionSprites getSkinPacman() {
		return skinPacman;
	}
	public ConfiguracionSprites getSkinFantasma() {
		return skinFantasma;
	}

	@Override
	public TipoPaquete getTipoPaquete() {
		return TipoPaquete.SKINS;
	}
}
