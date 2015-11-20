package game;

import gameobject.Jugador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Partida implements Serializable {
	private static final long serialVersionUID = 2838984202343155881L;
	private Mapa mapa;
	private ArrayList<Jugador> jugadores;
	private boolean activa;
	private String nombre;
	private ArrayList<Integer> idsDisponibles;
	
	public Partida(String name) {
		jugadores = new ArrayList<Jugador>();
		activa = true;
		nombre = name;
		idsDisponibles = new ArrayList<Integer>(4);
		for(int i=0;i<Configuracion.MAX_JUGADORES_PARTIDA.getValor();i++){
			idsDisponibles.add(i+1);
		}
	}
	//No se si se va a usar, pero ante la duda...
	public ArrayList<Integer> getIdsDisponibles() {
		return idsDisponibles;
	}
	public void tomarID(Integer id) {
		idsDisponibles.remove(id);
	}
	public void liberarID(Integer id) {
		idsDisponibles.add(id);
	}
	public void sortearIDs() {
		Collections.shuffle(idsDisponibles);
	}
	
	public void setActiva(boolean state) {
		activa = state;
	}
	
	public boolean getActiva() {
		return activa;
	}
	public String getNombre() {
		return nombre;
	}
	public Mapa getMapa() {
		return mapa;
	}
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
	public Jugador getJugador(int i) {
		return jugadores.get(i);
	}
	public void agregarJugador(Jugador j) {
		jugadores.add(j);
	}
	public void agregarMapa(Mapa m) {
		mapa = m;
	}
	public int getCantJugadores(){
		return jugadores.size();
	}
}
