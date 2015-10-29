package game;

import java.util.ArrayList;

public class Mapa {
	
	private ArrayList<Recta> rectas;
	
	public Mapa() {
		rectas = new ArrayList<Recta>();
		agregarRecta(new Punto(10,10), new Punto(10,50));
	}
	
	private void agregarRecta(Punto p1, Punto p2) {
		rectas.add(new Recta(p1,p2));
	}
}
