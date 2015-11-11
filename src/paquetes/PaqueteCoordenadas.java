package paquetes;

import java.io.Serializable;

import punto.Punto;

public class PaqueteCoordenadas implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6990615650451885236L;
	private Punto coordenadas;
	//private int x;
	//private int y;
	private int IDJugador;
	
	public PaqueteCoordenadas(Punto punto, int id) {
		coordenadas = punto;
		IDJugador = id;
	}
	
	public Punto getCoordenadas() {
		return coordenadas;
	}
	public int getIDJugador() {
		return IDJugador;
	}
}
