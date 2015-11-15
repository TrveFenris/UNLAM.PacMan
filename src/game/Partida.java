package game;

import gameobject.Jugador;

import java.util.ArrayList;

public class Partida {
	private Mapa mapa;
	private ArrayList<Jugador> jugadores;
	private boolean activa;
	private String nombre;
	
	public Partida(String name) {
		jugadores = new ArrayList<Jugador>();
		activa = true;
		nombre = name;
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
}
