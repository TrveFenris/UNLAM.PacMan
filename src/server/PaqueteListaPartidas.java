package server;

import java.util.*;

public class PaqueteListaPartidas implements java.io.Serializable {

	private static final long serialVersionUID = 5L;
	
	private ArrayList<String> partidas;
	private ArrayList<String> cantJugadores;
	
	/**
	 * Crea un nuevo paquete con arrays vacios.
	 */
	public PaqueteListaPartidas(){
		partidas = new ArrayList<String>();
		cantJugadores = new ArrayList<String>();
	}
	
	/**
	 * Agrega la informacion de una partida al paquete
	 * @param nombre
	 * @param cantJugadores
	 */
	public void agregarPartida(String nombre, int cantJugadores){
		partidas.add(nombre);
		this.cantJugadores.add(Integer.toString(cantJugadores));
	}
	
	/**
	 * Devuelve una lista con las partidas disponibles.
	 */
	public ArrayList<String> obtenerNombres(){
		return partidas;
	}
	
	/**
	 * Devuelve una lista con la cantidad de jugadores en cada partida.
	 */
	public ArrayList<String> obtenerCantJugadores(){
		return cantJugadores;
	}
}
