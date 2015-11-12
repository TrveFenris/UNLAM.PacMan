package gameobject;

import game.Configuracion;
import game.ConfiguracionSprites;

import java.util.Calendar;

import punto.Punto;

public class Pacman extends Jugador {	
	private boolean superpoder;
	private Calendar timerSuperpoder;
	private int bolitasComidas;
	private int muertes;
	private int fantasmasComidos;
	
	public Pacman(Punto posInicial, String nombre, ConfiguracionSprites configSprites) {
		super(nombre, Configuracion.PACMAN_VELOCIDAD.getValor());
		superpoder = false;
		bolitasComidas=muertes=fantasmasComidos=0;
		setImagen(configSprites);
		imagen.setLocation(posInicial.getX(), posInicial.getY());
	}
	
	public void reaparecer(){
		
	}
	
	public boolean tieneSuperpoder(){
		return superpoder;
	}
	
	public void startTimer() {
		timerSuperpoder.clear();
	}
	
	public long getTimerMilliseconds() {
		return timerSuperpoder.getTimeInMillis();
	}
	
	public void actualizarPuntaje(){
		this.actualizarPuntaje(Configuracion.PACMAN_PUNTAJE_POR_BOLITA.getValor()*bolitasComidas
								-Configuracion.PACMAN_PERDIDA_DE_PUNTAJE_POR_MUERTE.getValor()*muertes
								+Configuracion.PACMAN_PUNTAJE_POR_FANTASMA_COMIDO.getValor()*fantasmasComidos);
	}
}
