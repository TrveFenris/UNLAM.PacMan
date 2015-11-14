package paquetes;

/**
 * 
 * Esta clase es un paquete que sirve para comunicar al servidor qué bolita del ArrayList de bolitas 
 * contenido en una partida fue eliminada por algún cliente, y también permite al servidor propagar
 * entre sus clientes dicha acción.
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
