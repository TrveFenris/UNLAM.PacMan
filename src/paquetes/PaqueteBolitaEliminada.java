package paquetes;

/**
 * 
 * Esta clase es un paquete que sirve para comunicar al servidor qué bolita del ArrayList de bolitas 
 * contenido en una partida fue eliminada por algún cliente, y también permite al servidor propagar
 * entre sus clientes dicha acción.
 * @author Fenris
 */
public class PaqueteBolitaEliminada extends Paquete {
	private static final long serialVersionUID = 2632281726793268562L;
	private int index;
	public PaqueteBolitaEliminada(int i) {
		index = i;
	}
	public int getIndice() {
		return index;
	}
	@Override
	public TipoPaquete getTipoPaquete() {
		return TipoPaquete.BOLITA_ELIMINADA;
	}
}
