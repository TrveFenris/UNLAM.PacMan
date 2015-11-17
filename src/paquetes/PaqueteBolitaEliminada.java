package paquetes;

import gameobject.Bolita;

/**
 * 
 * Esta clase es un paquete que sirve para comunicar al servidor qu� bolita del ArrayList de bolitas 
 * contenido en una partida fue eliminada por alg�n cliente, y tambi�n permite al servidor propagar
 * entre sus clientes dicha acci�n.
 * @author Fenris
 */
public class PaqueteBolitaEliminada extends Paquete {
	private static final long serialVersionUID = 2632281726793268562L;
	private int index;
	private Bolita bolita;
	public PaqueteBolitaEliminada(int i, Bolita b) {
		index = i;
		bolita = b;
	}
	public int getIndice() {
		return index;
	}
	public Bolita getBolita() {
		return bolita;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.BOLITA_ELIMINADA;
	}
}
