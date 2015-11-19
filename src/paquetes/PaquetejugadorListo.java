package paquetes;

public class PaquetejugadorListo extends Paquete {
	private static final long serialVersionUID = 7216164905882313725L;
	private boolean ready;
	public PaquetejugadorListo(boolean estado) {
		ready = estado;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.JUGADOR_LISTO;
	}
	public boolean isReady() {
		return ready;
	}
}
