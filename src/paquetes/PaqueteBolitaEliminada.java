package paquetes;

/**
 * 
 * Esta clase es un paquete que sirve para comunicar al servidor qu� bolita del ArrayList de bolitas 
 * contenido en una partida fue eliminada por alg�n cliente, y tambi�n permite al servidor propagar
 * entre sus clientes dicha acci�n.
 * @author Fenris
 */
public class PaqueteBolitaEliminada {
	private int index;
	public PaqueteBolitaEliminada(int i) {
		index = i;
	}
	public int getIndice() {
		return index;
	}
}
