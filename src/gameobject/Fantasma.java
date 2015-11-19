package gameobject;

import game.Configuracion;
import game.ConfiguracionSprites;

import punto.Punto;

public class Fantasma extends Jugador{

	private static final long serialVersionUID = -6977500924335097519L;
	protected int pacmansComidos;
	protected int vecesAturdido;
	protected int muertes;

	public Fantasma(Punto posInicial, String nombre, ConfiguracionSprites configSprites, int id) {
		super(nombre, Configuracion.FANTASMA_VELOCIDAD.getValor(), configSprites);
		pacmansComidos=vecesAturdido=muertes=0;
		setImagen(configSprites.getValor(Direcciones.DERECHA));
		imagen.setLocation(posInicial.getX(),posInicial.getY());
		this.ID = id;
	}

	public void reaparecer(){
		
	}
	
	public void actualizarPuntaje(){
		this.actualizarPuntaje(Configuracion.FANTASMA_PUNTAJE_POR_PACMAN_COMIDO.getValor()*pacmansComidos
								-Configuracion.FANTASMA_PERDIDA_DE_PUNTAJE_POR_ATURDIMIENTO.getValor()*vecesAturdido
								-Configuracion.FANTASMA_PERDIDA_DE_PUNTAJE_POR_MUERTE.getValor()*muertes);
	}
}
