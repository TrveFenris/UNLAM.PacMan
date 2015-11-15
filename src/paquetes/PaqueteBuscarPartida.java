package paquetes;

import java.util.AbstractMap;
import java.util.ArrayList;

public class PaqueteBuscarPartida extends Paquete {
	private static final long serialVersionUID = -656348248287909020L;
	private ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> partidas;
	
	public PaqueteBuscarPartida() {
		partidas = new ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>>();
	}
	public void agregarPartida(String nombre, int cantJugadores) {
		partidas.add(new AbstractMap.SimpleImmutableEntry<String, Integer>(nombre, cantJugadores));
	}
	public ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> getPartidas() {
		return partidas;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.BUSCAR_PARTIDA;
	}

}
