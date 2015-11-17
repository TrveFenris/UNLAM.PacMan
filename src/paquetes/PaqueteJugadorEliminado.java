package paquetes;

public class PaqueteJugadorEliminado extends Paquete {

	private static final long serialVersionUID = 3350958867126001589L;
	private int IDJugador;
	public PaqueteJugadorEliminado(int id) {
		IDJugador = id;
	}
	public int getIDJugador() {
		return IDJugador;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.JUGADOR_ELIMINADO;
	}

}
