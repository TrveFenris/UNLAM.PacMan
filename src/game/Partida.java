package game;

import gameobject.Jugador;

import java.util.ArrayList;

public class Partida {
	private Mapa mapa;
	private ArrayList<Jugador> jugadores;
	private boolean activa;
	
	public Partida() {
		
	}
	
	public void setActiva(boolean state) {
		activa = state;
	}
	
	public boolean getActiva() {
		return activa;
	}
}
