package paquetes;

public class PaqueteLanzarPartida extends Paquete {
	private static final long serialVersionUID = 9176381048245973322L;
	private boolean ready;
	public PaqueteLanzarPartida(boolean status) {
		ready = status;
	}
	public boolean isReady() {
		return ready;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.LANZAR_PARTIDA;
	}

}
