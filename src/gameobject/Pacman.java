package gameobject;

import game.Configuracion;
import game.ConfiguracionSprites;
import punto.Punto;

public class Pacman extends Jugador {

	private static final long	serialVersionUID	= -8404310041617236427L;
	private int					bolitasComidas;
	private int					muertes;
	private int					fantasmasComidos;

	public Pacman(Punto posInicial, String nombre, ConfiguracionSprites configSprites, int id) {
		super(nombre, Configuracion.PACMAN_VELOCIDAD.getValor(), configSprites, true);
		bolitasComidas = muertes = fantasmasComidos = 0;
		setImagen(configSprites.getValor(Direcciones.DERECHA));
		imagen.setLocation(posInicial.getX() - (imagen.getWidth() / 2), posInicial.getY() - (imagen.getHeight() / 2));
		this.ID = id;
	}

	public void reaparecer() {

	}

	public void actualizarPuntaje() {
		this.actualizarPuntaje(Configuracion.PACMAN_PUNTAJE_POR_BOLITA.getValor() * bolitasComidas - Configuracion.PACMAN_PERDIDA_DE_PUNTAJE_POR_MUERTE.getValor() * muertes
				+ Configuracion.PACMAN_PUNTAJE_POR_FANTASMA_COMIDO.getValor() * fantasmasComidos);
	}
}
