package game;

import gameobject.Jugador;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Partida {
	private Mapa mapa;
	private ArrayList<Jugador> jugadores;
	private boolean activa;
	private String nombre;
	private SortedSet<Integer> idsDisponibles;
	
	public Partida(String name) {
		jugadores = new ArrayList<Jugador>();
		activa = true;
		nombre = name;
		idsDisponibles = new TreeSet<Integer>();
		idsDisponibles.add(1);
		idsDisponibles.add(2);
		//idsDisponibles.add(3);
		//idsDisponibles.add(4);
	}
	//No se si se va a usar, pero ante la duda...
	public SortedSet<Integer> getIdsDisponibles() {
		return idsDisponibles;
	}
	public void tomarID(Integer id) {
		idsDisponibles.remove(id);
	}
	public void liberarID(Integer id) {
		idsDisponibles.add(id);
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
